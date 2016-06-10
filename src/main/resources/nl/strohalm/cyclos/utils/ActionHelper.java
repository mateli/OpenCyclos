/*
    This file is part of Cyclos (www.cyclos.org).
    A project of the Social Trade Organisation (www.socialtrade.org).

    Cyclos is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    Cyclos is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with Cyclos; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA

 */
package nl.strohalm.cyclos.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.entities.Entity;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.Operator;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.services.alerts.ErrorLogService;
import nl.strohalm.cyclos.services.settings.SettingsService;
import nl.strohalm.cyclos.services.transactions.exceptions.CreditsException;
import nl.strohalm.cyclos.services.transactions.exceptions.MaxAmountPerDayExceededException;
import nl.strohalm.cyclos.services.transactions.exceptions.NotEnoughCreditsException;
import nl.strohalm.cyclos.services.transactions.exceptions.TransferMinimumPaymentException;
import nl.strohalm.cyclos.services.transactions.exceptions.UpperCreditLimitReachedException;
import nl.strohalm.cyclos.utils.transaction.CurrentTransactionData;
import nl.strohalm.cyclos.utils.validation.ValidationError;
import nl.strohalm.cyclos.utils.validation.ValidationException;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Contains helper methods for struts actions
 * @author luis
 */
public final class ActionHelper {

    /**
     * Interface for implementations that extracts the by element from an entity.
     * @author ameyer
     * 
     */
    public interface ByElementExtractor {
        Element getByElement(Entity entity);
    }

    /**
     * Returns an action forward to go back to the previous action
     */
    public static ActionForward back(final ActionMapping actionMapping) {
        return actionMapping.findForward("back");
    }

    /**
     * Extracts the elements from the entities and returns a map that might contain two items: the by element and the type of element. When the
     * extracted element denotes a system task or an Administrator and the logged user is not an Administrator, only the type of element will appear
     * in the corresponding map.
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static Collection<Map<String, Object>> getByElements(final ActionContext context, final Collection<? extends Entity> entities, final ByElementExtractor extractor) {
        if (entities.isEmpty()) {
            return Collections.EMPTY_LIST;
        }
        final Collection<Map<String, Object>> byCollection = new ArrayList<Map<String, Object>>();
        for (final Entity entity : entities) {
            final Element by = extractor.getByElement(entity);
            final Map map = new HashMap<String, Object>();

            if (by == null) {
                map.put("byType", "SystemTask");
            } else if (by.getNature() == Element.Nature.ADMIN) {
                if (context.isAdmin()) {
                    map.put("by", by);
                    map.put("byType", "Admin");
                } else {
                    map.put("byType", "System");
                }
            } else if ((by.getNature() == Element.Nature.OPERATOR) && (context.isMemberOf(EntityHelper.reference(Operator.class, by.getId())) || context.getElement().equals(by))) {
                map.put("by", by);
                map.put("byType", "Operator");
            } else {
                map.put("by", by.getAccountOwner());
                map.put("byType", "Member");
            }

            byCollection.add(map);
        }

        return byCollection;
    }

    /**
     * It returns the request's parameters map and in case of the error action it adds as a parameter the errorKey request's attribute.<br>
     * This is necessary because the {@link #sendError(ActionMapping, HttpServletRequest, HttpServletResponse, String, Object...)} method sets the
     * errorKey as an attribute and send a redirect (to the error action) to the client
     * @see sendError(ActionMapping, HttpServletRequest, HttpServletResponse, String, Object...)
     */
    public static Map<String, String[]> getParameterMap(final HttpServletRequest request) {
        final String uri = request.getRequestURI();

        @SuppressWarnings("unchecked")
        final Map<String, String[]> clientParameterMap = request.getParameterMap();
        // in case of the error action we must retrieve the error key as an attribute's request and not as a parameter
        if (uri.endsWith("/error")) {
            final HttpSession session = request.getSession(false);
            if (session != null) {
                final Map<String, String[]> map = new HashMap<String, String[]>();
                map.putAll(clientParameterMap);
                map.put("errorKey", new String[] { (String) request.getAttribute("errorKey") });
                return map;
            }
        }

        return clientParameterMap;
    }

    public static ActionForward handleValidationException(final ActionMapping actionMapping, final HttpServletRequest request, final HttpServletResponse response, final ValidationException e) {
        if (e == null) {
            return null;
        }
        String key = "error.validation";
        List<Object> args = Collections.emptyList();
        if (!e.getGeneralErrors().isEmpty()) {
            final ValidationError error = e.getGeneralErrors().iterator().next();
            key = error.getKey();
            args = error.getArguments();
        } else if (!e.getErrorsByProperty().isEmpty()) {
            final Entry<String, Collection<ValidationError>> entry = e.getErrorsByProperty().entrySet().iterator().next();
            final Collection<ValidationError> errors = entry.getValue();
            if (!errors.isEmpty()) {
                // We must show the validation error in a friendly way
                final String propertyName = entry.getKey();
                final ValidationError error = errors.iterator().next();
                key = error.getKey();
                args = new ArrayList<Object>();
                // First, check if there's a fixed display name for the property...
                String propertyLabel = e.getPropertyDisplayName(propertyName);
                if (StringUtils.isEmpty(propertyLabel)) {
                    // ... it doesn't. Check if there's a message key...
                    final String propertyKey = e.getPropertyKey(propertyName);
                    if (StringUtils.isNotEmpty(propertyKey)) {
                        // ... the key is set! Get the property label from the message bundle.
                        final MessageHelper messageHelper = SpringHelper.bean(request.getSession().getServletContext(), MessageHelper.class);
                        propertyLabel = messageHelper.message(e.getPropertyKey(propertyName));
                    } else {
                        // ... we're out of luck! There's no property key. Use the raw property name as label, which is ugly!
                        propertyLabel = propertyName;
                    }
                }
                // The first message argument is always the property label
                args.add(propertyLabel);
                if (error.getArguments() != null) {
                    // If there are more, add them as well.
                    args.addAll(error.getArguments());
                }
            }
        }
        // With the key and arguments, we can show a friendly message to the user
        return sendError(actionMapping, request, response, key, args.toArray());
    }

    /**
     * Return a redirect for the ActionForward with the specified parameter
     */
    public static ActionForward redirectWithParam(final HttpServletRequest request, final ActionForward forward, final String name, final Object value) {
        return redirectWithParams(request, forward, Collections.singletonMap(name, value));
    }

    /**
     * Return a redirect for the ActionForward with the specified parameters
     */
    public static ActionForward redirectWithParams(final HttpServletRequest request, ActionForward forward, final Map<String, Object> params) {
        if (forward == null) {
            return null;
        }
        final LocalSettings settings = SpringHelper.bean(request.getSession().getServletContext(), SettingsService.class).getLocalSettings();
        forward = new ActionForward(forward);
        final StringBuilder path = new StringBuilder();
        path.append(forward.getPath());
        if (MapUtils.isNotEmpty(params)) {
            path.append('?');
            for (final Entry<String, Object> entry : params.entrySet()) {
                final Object value = entry.getValue();
                try {
                    path.append(entry.getKey()).append('=').append(URLEncoder.encode(value == null ? "" : value.toString(), settings.getCharset()));
                } catch (final UnsupportedEncodingException e) {
                }
                path.append('&');
            }
            if (path.charAt(path.length() - 1) == '&') {
                path.setLength(path.length() - 1);
            }
            forward.setPath(path.toString());
        }
        forward.setRedirect(true);
        return forward;
    }

    /**
     * Sends an error message to the error page via a translation key
     * @return The ActionForward to the error page
     */
    public static ActionForward sendError(final ActionMapping actionMapping, final HttpServletRequest request, final HttpServletResponse response, final String key, final Object... arguments) {
        final HttpSession session = request.getSession();
        session.setAttribute("errorKey", key);
        session.setAttribute("errorArguments", arguments);
        return actionMapping.findForward("error");
    }

    /**
     * Sends a direct error message to the error page
     * @return The ActionForward to the error page
     */
    public static ActionForward sendErrorWithMessage(final ActionMapping actionMapping, final HttpServletRequest request, final HttpServletResponse response, final String message) {
        final HttpSession session = request.getSession();
        session.setAttribute("errorMessage", message);
        return actionMapping.findForward("error");
    }

    /**
     * Sends a message to the next page
     */
    public static void sendMessage(final HttpServletRequest request, final HttpServletResponse response, final String key, final Object... arguments) {
        final HttpSession session = request.getSession();
        session.setAttribute("messageKey", key);
        session.setAttribute("messageArguments", arguments);
        response.addCookie(new Cookie("showMessage", "true"));
    }

    /**
     * Throws an exception compatible to Servlet's exceptions
     */
    public static void throwException(final Throwable th) throws IOException, ServletException {
        if (th instanceof RuntimeException) {
            throw (RuntimeException) th;
        } else if (th instanceof ServletException) {
            throw (ServletException) th;
        } else if (th instanceof IOException) {
            throw (IOException) th;
        } else {
            throw new ServletException(th);
        }
    }

    private ErrorLogService errorLogService;

    private SettingsService settingsService;

    /**
     * Generate an error log for the given exception, on a separate transaction
     */
    public void generateLog(final HttpServletRequest request, final ServletContext servletContext, final Throwable error) {
        CurrentTransactionData.setError(error);
        // Create a defensive copy of the parameters map
        @SuppressWarnings("unchecked")
        final Map<String, Object> parameters = new HashMap<String, Object>(request.getParameterMap());
        errorLogService.insert(error, request.getRequestURI(), parameters);
    }

    /**
     * Returns an ActionForward that corresponds to the given path and nature.
     */
    public ActionForward getForwardFor(final Element.Nature nature, final String actionName, final boolean redirect) {
        final ActionForward actionForward = new ActionForward("/do/" + nature.toString().toLowerCase() + "/" + actionName);
        actionForward.setRedirect(redirect);
        return actionForward;
    }

    /**
     * Given a credits exception, resolve it's error key
     */
    public String resolveErrorKey(final CreditsException exception) {
        if (exception instanceof MaxAmountPerDayExceededException) {
            final MaxAmountPerDayExceededException e = (MaxAmountPerDayExceededException) exception;
            final Calendar date = e.getDate();
            if (date == null || DateHelper.sameDay(date, Calendar.getInstance())) {
                return "payment.error.maxAmountOnDayExceeded";
            } else {
                return "payment.error.maxAmountOnDayExceeded.at";
            }
        } else if (exception instanceof NotEnoughCreditsException) {
            if (((NotEnoughCreditsException) exception).isOriginalAccount()) {
                return "payment.error.enoughCredits";
            } else {
                return "payment.error.enoughCreditsOtherAccount";
            }
        } else if (exception instanceof TransferMinimumPaymentException) {
            return "payment.error.transferMinimum";
        } else if (exception instanceof UpperCreditLimitReachedException) {
            return "payment.error.upperCreditLimit";
        } else {
            return "error.general";
        }
    }

    public Object[] resolveParameters(final CreditsException exception) {
        if (exception instanceof MaxAmountPerDayExceededException) {
            final MaxAmountPerDayExceededException e = (MaxAmountPerDayExceededException) exception;
            return new Object[] { e.getTransferType().getName(), settingsService.getLocalSettings().getRawDateConverter().toString(e.getDate()) };
        } else if (exception instanceof NotEnoughCreditsException) {
            // TODO: This may cause lazy init exception
            return new Object[] { exception.getAccount().getType().getName() };
        } else if (exception instanceof TransferMinimumPaymentException) {
            final TransferMinimumPaymentException e = (TransferMinimumPaymentException) exception;
            return new Object[] { e.getMinimunPayment() };
        } else if (exception instanceof UpperCreditLimitReachedException) {
            final UpperCreditLimitReachedException e = (UpperCreditLimitReachedException) exception;
            return new Object[] { exception.getAccount().getType().getName(), e.getUpperLimit() };
        } else {
            return new Object[] {};
        }
    }

    public void setErrorLogService(final ErrorLogService errorLogService) {
        this.errorLogService = errorLogService;
    }

    public void setSettingsService(final SettingsService settingsService) {
        this.settingsService = settingsService;
    }
}
