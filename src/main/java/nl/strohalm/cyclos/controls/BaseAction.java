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
package nl.strohalm.cyclos.controls;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.access.ChangeExpiredPasswordAction;
import nl.strohalm.cyclos.entities.access.AdminUser;
import nl.strohalm.cyclos.entities.access.MemberUser;
import nl.strohalm.cyclos.entities.access.OperatorUser;
import nl.strohalm.cyclos.entities.access.User;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.entities.exceptions.QueryParseException;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.exceptions.AccessDeniedException;
import nl.strohalm.cyclos.exceptions.ExternalException;
import nl.strohalm.cyclos.exceptions.LoggedOutException;
import nl.strohalm.cyclos.exceptions.PermissionDeniedException;
import nl.strohalm.cyclos.services.access.AccessService;
import nl.strohalm.cyclos.services.customization.exceptions.ImageException;
import nl.strohalm.cyclos.services.elements.ElementService;
import nl.strohalm.cyclos.services.groups.GroupService;
import nl.strohalm.cyclos.services.permissions.PermissionService;
import nl.strohalm.cyclos.services.settings.SettingsService;
import nl.strohalm.cyclos.struts.access.ActionAccessMonitor;
import nl.strohalm.cyclos.utils.ActionHelper;
import nl.strohalm.cyclos.utils.ImageHelper;
import nl.strohalm.cyclos.utils.ImageHelper.ImageType;
import nl.strohalm.cyclos.utils.LoginHelper;
import nl.strohalm.cyclos.utils.MessageHelper;
import nl.strohalm.cyclos.utils.Navigation;
import nl.strohalm.cyclos.utils.RequestHelper;
import nl.strohalm.cyclos.utils.access.LoggedUser;
import nl.strohalm.cyclos.utils.transaction.CurrentTransactionData;
import nl.strohalm.cyclos.utils.validation.ValidationException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.MultipartRequestHandler;

/**
 * Abstract action intented to be subclasses by all actions that need a logged user (not LoginAction, for example). The standard execute method is
 * implemented and marked as final. Another method, executeAction, is exposed for subclasses to implement it in order to perform the specific action
 * @author luis
 */
public abstract class BaseAction extends Action {

    private static final Log    LOG = LogFactory.getLog(BaseAction.class);

    protected LoginHelper       loginHelper;
    protected ActionHelper      actionHelper;
    protected PermissionService permissionService;
    protected AccessService     accessService;
    protected ElementService    elementService;
    protected GroupService      groupService;
    protected MessageHelper     messageHelper;
    protected SettingsService   settingsService;
    private ActionAccessMonitor actionAccessMonitor;

    /**
     * The Struts standard execute method is reserved, being the executeAction the one that subclasses must implement
     */
    @Override
    public final ActionForward execute(final ActionMapping actionMapping, final ActionForm actionForm, final HttpServletRequest request, final HttpServletResponse response) throws Exception {

        // Check for uploads that exceeded the max length
        final Boolean maxLengthExceeded = (Boolean) request.getAttribute(MultipartRequestHandler.ATTRIBUTE_MAX_LENGTH_EXCEEDED);
        if (maxLengthExceeded != null && maxLengthExceeded) {
            final LocalSettings settings = settingsService.getLocalSettings();
            return ActionHelper.sendError(actionMapping, request, response, "error.maxUploadSizeExceeded", FileUtils.byteCountToDisplaySize(settings.getMaxUploadBytes()));
        }
        HttpSession session = request.getSession(false);

        // Validate the logged user
        User user = null;
        try {
            user = validate(request, response, actionMapping);
            if (user == null) {
                return null;
            }
        } catch (final LoggedOutException e) {
            // Invalidate the current session
            if (session != null) {
                session.invalidate();
            }
            // Create a new session
            session = request.getSession();
            ActionForward forward = resolveLoginForward(actionMapping, request);
            // If the action is stored on navigation path, we should return to it after re-login
            if (storePath(actionMapping, request)) {
                // Store the path on session, so that after logging in, the user will stay on this page
                String path = actionMapping.getPath();
                final String queryString = request.getQueryString();
                if (StringUtils.isNotEmpty(queryString)) {
                    path += "?" + queryString;
                }
                session.setAttribute("returnTo", path);
                session.setAttribute("loggedOut", true);

                // Redirect to the login page
                final Map<String, Object> params = new HashMap<String, Object>();
                if (path.contains("/operator/")) {
                    params.put("operator", true);
                }
                forward = ActionHelper.redirectWithParams(request, forward, params);
            }
            return forward;
        } catch (final AccessDeniedException e) {
            if (session != null) {
                session.invalidate();
            }
            return ActionHelper.sendError(actionMapping, request, response, "error.accessDenied");
        } catch (final Exception e) {
            CurrentTransactionData.setError(e);
            actionHelper.generateLog(request, getServlet().getServletContext(), e);
            LOG.error("Application error on " + getClass().getName(), e);
            return ActionHelper.sendError(actionMapping, request, response, null);
        }

        // Check if the member shall accept a registration agreement or change the expired password before proceeding
        if (session != null) {
            String forwardName = null;
            if (Boolean.TRUE.equals(session.getAttribute("shallAcceptRegistrationAgreement"))) {
                // AcceptRegistrationAgreementAction is a BasePublicFormAction, not BaseAction, so, we don't need to check if this is not that action
                forwardName = RequestHelper.isPosWeb(request) ? "poswebAcceptRegistrationAgreement" : "acceptRegistrationAgreement";
            } else if (Boolean.TRUE.equals(session.getAttribute("expiredPassword")) && !(this instanceof ChangeExpiredPasswordAction)) {
                // Only redirect to change expired password if this is not that action

                if (RequestHelper.isPosWeb(request)) {
                    forwardName = "poswebChangeExpiredPassword";
                } else {
                    return actionHelper.getForwardFor(LoggedUser.element().getNature(), "changeExpiredPassword", true);
                }
            }
            if (forwardName != null) {
                return actionMapping.findForward(forwardName);
            }
        }

        // Perform special actions when the request is coming from menu
        if (RequestHelper.isFromMenu(request)) {
            request.setAttribute("fromMenu", true);
        }

        // Create an action context
        final ActionContext context = new ActionContext(actionMapping, actionForm, request, response, user, messageHelper);
        request.setAttribute("formAction", actionMapping.getPath());

        try {
            // checks access to the action
            actionAccessMonitor.requestAccess(context);

            // Store the navigation data
            final Navigation navigation = context.getNavigation();
            if (storePath(actionMapping, request)) {
                navigation.setLastAction(actionMapping);
                navigation.store(context);
            }

            // Process the action
            final ActionForward forward = executeAction(context);
            return forward;
        } catch (final PermissionDeniedException e) {
            CurrentTransactionData.setError(e);
            final boolean userBlocked = accessService.notifyPermissionDeniedException();
            if (userBlocked) {
                request.getSession(false).invalidate();
                return ActionHelper.sendError(actionMapping, request, response, "login.error.blocked");
            } else {
                return ActionHelper.sendError(actionMapping, request, response, "error.permissionDenied");
            }
        } catch (final QueryParseException e) {
            CurrentTransactionData.setError(e);
            return ActionHelper.sendError(actionMapping, request, response, "error.queryParse");
        } catch (final ImageHelper.UnknownImageTypeException e) {
            CurrentTransactionData.setError(e);
            final String recognizedTypes = StringUtils.join(ImageType.values(), ", ");
            return ActionHelper.sendError(actionMapping, request, response, "error.unknownImageType", recognizedTypes);
        } catch (final ImageException e) {
            CurrentTransactionData.setError(e);
            return ActionHelper.sendError(actionMapping, request, response, e.getKey());
        } catch (final ValidationException e) {
            CurrentTransactionData.setError(e);
            return ActionHelper.handleValidationException(actionMapping, request, response, e);
        } catch (final EntityNotFoundException e) {
            // An entity not found is handled as a validation exception
            return ActionHelper.handleValidationException(actionMapping, request, response, new ValidationException());
        } catch (final ExternalException e) {
            CurrentTransactionData.setError(e);
            return ActionHelper.sendErrorWithMessage(actionMapping, request, response, e.getMessage());
        } catch (final Exception e) {
            CurrentTransactionData.setError(e);
            actionHelper.generateLog(request, getServlet().getServletContext(), e);
            LOG.error("Application error on " + getClass().getName(), e);
            return ActionHelper.sendError(actionMapping, request, response, null);
        }
    }

    @Inject
    public final void setAccessService(final AccessService accessService) {
        this.accessService = accessService;
    }

    @Inject
    public void setActionAccessMonitor(final ActionAccessMonitor actionAccessMonitor) {
        this.actionAccessMonitor = actionAccessMonitor;
    }

    @Inject
    public final void setActionHelper(final ActionHelper actionHelper) {
        this.actionHelper = actionHelper;
    }

    @Inject
    public final void setElementService(final ElementService elementService) {
        this.elementService = elementService;
    }

    @Inject
    public final void setGroupService(final GroupService groupService) {
        this.groupService = groupService;
    }

    @Inject
    public final void setLoginHelper(final LoginHelper loginHelper) {
        this.loginHelper = loginHelper;
    }

    @Inject
    public final void setMessageHelper(final MessageHelper messageHelper) {
        this.messageHelper = messageHelper;
    }

    @Inject
    public final void setPermissionService(final PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @Inject
    public final void setSettingsService(final SettingsService settingsService) {
        this.settingsService = settingsService;
    }

    /**
     * This method must be implemented to perform the action itself
     */
    protected abstract ActionForward executeAction(ActionContext context) throws Exception;

    /**
     * Returns a forward to the login page. Will be invoked when the member was disconnected.
     */
    protected ActionForward resolveLoginForward(final ActionMapping actionMapping, final HttpServletRequest request) {
        return actionMapping.findForward("login");
    }

    /**
     * Should be overriden by subclasses to return false on actions that are not stored on navigation path. By default, returns true when the current
     * request is a GET and the action has an input page.
     */
    protected boolean storePath(final ActionMapping actionMapping, final HttpServletRequest request) {
        final String path = actionMapping.getPath();
        return RequestHelper.isGet(request) && StringUtils.isNotEmpty(actionMapping.getInput()) && (path.contains("admin/") || path.contains("member/") || path.contains("operator/"));
    }

    /**
     * Validate the user access, returning the logged user
     */
    protected User validate(final HttpServletRequest request, final HttpServletResponse response, final ActionMapping actionMapping) throws Exception {

        final User user = loginHelper.validateLoggedUser(request);

        // Ensure the logged user is not accessing another user's type url's
        List<String> pathShouldNotInclude;
        if (user instanceof AdminUser) {
            pathShouldNotInclude = Arrays.asList("/member/", "/operator/");
        } else if (user instanceof MemberUser) {
            pathShouldNotInclude = Arrays.asList("/admin/", "/operator/");
        } else if (user instanceof OperatorUser) {
            pathShouldNotInclude = Arrays.asList("/member/", "/admin/");
        } else {
            throw new AccessDeniedException();
        }
        final String path = actionMapping.getPath();
        for (final String current : pathShouldNotInclude) {
            if (path.contains(current)) {
                throw new AccessDeniedException();
            }
        }
        return user;
    }
}
