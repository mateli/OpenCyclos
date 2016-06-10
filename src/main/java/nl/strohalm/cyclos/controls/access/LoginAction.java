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
package nl.strohalm.cyclos.controls.access;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.BasePublicFormAction;
import nl.strohalm.cyclos.entities.access.Channel;
import nl.strohalm.cyclos.entities.access.Channel.Principal;
import nl.strohalm.cyclos.entities.access.MemberUser;
import nl.strohalm.cyclos.entities.access.PrincipalType;
import nl.strohalm.cyclos.entities.access.User;
import nl.strohalm.cyclos.entities.settings.AccessSettings;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.exceptions.AccessDeniedException;
import nl.strohalm.cyclos.exceptions.PermissionDeniedException;
import nl.strohalm.cyclos.services.access.AccessService;
import nl.strohalm.cyclos.services.access.ChannelService;
import nl.strohalm.cyclos.services.access.exceptions.AlreadyConnectedException;
import nl.strohalm.cyclos.services.access.exceptions.BlockedCredentialsException;
import nl.strohalm.cyclos.services.access.exceptions.InactiveMemberException;
import nl.strohalm.cyclos.services.access.exceptions.LoginException;
import nl.strohalm.cyclos.services.access.exceptions.SystemOfflineException;
import nl.strohalm.cyclos.utils.ActionHelper;
import nl.strohalm.cyclos.utils.LoginHelper;
import nl.strohalm.cyclos.utils.StringHelper;
import nl.strohalm.cyclos.utils.validation.RequiredError;
import nl.strohalm.cyclos.utils.validation.ValidationException;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action used to login the user
 * @author luis
 */
public class LoginAction extends BasePublicFormAction {
    protected AccessService  accessService;
    protected ChannelService channelService;
    protected LoginHelper    loginHelper;

    public ChannelService getChannelService() {
        return channelService;
    }

    @Inject
    public void setAccessService(final AccessService accessService) {
        this.accessService = accessService;
    }

    @Inject
    public void setChannelService(final ChannelService channelService) {
        this.channelService = channelService;
    }

    @Inject
    public final void setLoginHelper(final LoginHelper loginHelper) {
        this.loginHelper = loginHelper;
    }

    /**
     * Returns a forward for a page preparation when there's already a logged user
     */
    protected ActionForward alreadyLoggedForward(final ActionMapping mapping, final HttpServletRequest request, final HttpServletResponse response, final LoginForm form, final User user) {
        final HttpSession session = request.getSession();
        return new ActionForward(session.getAttribute("pathPrefix") + "/home", true);
    }

    /**
     * Logins the user and returns a forward to the next action
     */
    protected ActionForward doLogin(final ActionMapping mapping, final HttpServletRequest request, final HttpServletResponse response, final LoginForm form) {
        final String member = StringUtils.trimToNull(form.getMember());
        final String principal = StringUtils.trimToNull(form.getPrincipal());
        final String password = form.getPassword();
        final String errorReturnTo = resolveErrorReturnTo(mapping, request, response, form);
        final HttpSession session = request.getSession();
        if (errorReturnTo == null) {
            session.removeAttribute("errorReturnTo");
            session.setAttribute("forceBack", true);
        } else {
            session.setAttribute("errorReturnTo", errorReturnTo);
        }
        try {
            final Class<? extends User> requiredUserType = requiredUserType(mapping, request, response, form);
            final User user = loginHelper.login(requiredUserType, form.getPrincipalType(), member, principal, password, Channel.WEB, request, response);
            return loginForward(mapping, request, response, form, user);
        } catch (final BlockedCredentialsException e) {
            return ActionHelper.sendError(mapping, request, response, "login.error.blocked");
        } catch (final InactiveMemberException e) {
            return ActionHelper.sendError(mapping, request, response, "login.error.inactive");
        } catch (final AlreadyConnectedException e) {
            return ActionHelper.sendError(mapping, request, response, "login.error.alreadyConnected");
        } catch (final AccessDeniedException e) {
            return ActionHelper.sendError(mapping, request, response, "error.accessDenied");
        } catch (final PermissionDeniedException e) {
            return ActionHelper.sendError(mapping, request, response, "error.accessDenied");
        } catch (final SystemOfflineException e) {
            return ActionHelper.sendError(mapping, request, response, "error.systemOffline");
        } catch (final LoginException e) {
            return ActionHelper.sendError(mapping, request, response, "login.error");
        }
    }

    @Override
    protected ActionForward handleDisplay(final ActionMapping mapping, final ActionForm actionForm, final HttpServletRequest request, final HttpServletResponse response) {
        final LoginForm form = (LoginForm) actionForm;
        final HttpSession session = request.getSession();
        final LocalSettings localSettings = settingsService.getLocalSettings();
        final AccessSettings accessSettings = settingsService.getAccessSettings();
        session.removeAttribute("errorReturnTo");
        if (session.getAttribute("loggedOut") != null) {
            request.setAttribute("loggedOut", true);
            session.removeAttribute("loggedOut");
        }
        final boolean allowOperatorLogin = accessSettings.isAllowOperatorLogin();
        final boolean isOperator = Boolean.parseBoolean(request.getParameter("operator"));

        // Build the access links
        final List<Map<String, String>> accessLinks = new ArrayList<Map<String, String>>();
        PrincipalType selectedPrincipalType = channelService.resolvePrincipalType(Channel.WEB, form.getPrincipalType());
        if (isOperator) {
            // Accessing as operator: there is only the option to login as member again
            accessLinks.add(createLink(request, messageHelper.message("login.action.loginAsMember"), null, null));
            selectedPrincipalType = Channel.DEFAULT_PRINCIPAL_TYPE;
        } else {
            // Get the principal types
            final Channel web = channelService.loadByInternalName(Channel.WEB);
            final Set<PrincipalType> allPrincipalTypes = web.getPrincipalTypes();
            for (final PrincipalType principalType : allPrincipalTypes) {
                if (principalType.getPrincipal() == Principal.EMAIL && !localSettings.isEmailUnique()) {
                    // A very specific case: e-mail was default, then the settings for unique e-mail was unchecked
                    if (selectedPrincipalType.equals(principalType)) {
                        selectedPrincipalType = Channel.DEFAULT_PRINCIPAL_TYPE;
                    }
                    // If the e-mail was not set as unique, skip the e-mail principal
                    continue;
                } else if (principalType.equals(selectedPrincipalType)) {
                    // Don't show the selected principal type as link
                    continue;
                }
                final String label = messageHelper.message("login.accessUsing", resolvePrincipalLabel(principalType));
                accessLinks.add(createLink(request, label, "principalType", principalType.toString()));
            }
            if (allowOperatorLogin) {
                accessLinks.add(createLink(request, messageHelper.message("login.action.loginAsOperator"), "operator", "true"));
            }
        }
        request.setAttribute("selectedPrincipalType", selectedPrincipalType);
        request.setAttribute("selectedPrincipalLabel", resolvePrincipalLabel(selectedPrincipalType));
        request.setAttribute("accessLinks", accessLinks);
        request.setAttribute("singleAccessLink", accessLinks.size() == 1 ? accessLinks.get(0) : null);

        final User user = LoginHelper.getLoggedUser(request);
        if (user != null) {
            return alreadyLoggedForward(mapping, request, response, form, user);
        }
        storeCookie(request, response, true);
        return mapping.getInputForward();
    }

    @Override
    protected ActionForward handleSubmit(final ActionMapping mapping, final ActionForm actionForm, final HttpServletRequest request, final HttpServletResponse response) {
        return doLogin(mapping, request, response, (LoginForm) actionForm);
    }

    /**
     * Returns whether the member is required - only in case of operator-only logins
     */
    protected boolean isMemberRequired(final HttpServletRequest request) {
        return "true".equals(request.getParameter("operatorLogin"));
    }

    /**
     * Returns a forward to the next action in case of a successful login
     */
    protected ActionForward loginForward(final ActionMapping mapping, final HttpServletRequest request, final HttpServletResponse response, final LoginForm form, final User user) {
        final String returnTo = (String) request.getSession().getAttribute("returnTo");
        ActionForward forward = null;

        if (user instanceof MemberUser && elementService.shallAcceptAgreement(((MemberUser) user).getMember())) {
            // Should accept a registration agreement first
            request.getSession().setAttribute("shallAcceptRegistrationAgreement", true);
            forward = mapping.findForward("acceptRegistrationAgreement");
        } else if (accessService.hasPasswordExpired()) {
            // Should change an expired password
            request.getSession().setAttribute("expiredPassword", true);
            forward = actionHelper.getForwardFor(user.getElement().getNature(), "changeExpiredPassword", true);
        } else if (StringUtils.isNotEmpty(returnTo)) {
            // When redirecting to the previous page, remove from session to avoid a next unwanted redirection
            final HttpSession session = request.getSession();
            session.removeAttribute("returnTo");
            forward = new ActionForward(!returnTo.startsWith("/do") ? "/do" + returnTo : returnTo, true);
        } else {
            forward = actionHelper.getForwardFor(user.getElement().getNature(), "home", true);
        }
        return forward;
    }

    /**
     * May be overridden in order to return the required user type
     */
    protected Class<? extends User> requiredUserType(final ActionMapping mapping, final HttpServletRequest request, final HttpServletResponse response, final LoginForm form) {
        return User.class;
    }

    protected String resolveErrorReturnTo(final ActionMapping mapping, final HttpServletRequest request, final HttpServletResponse response, final LoginForm form) {
        final String member = StringUtils.trimToNull(form.getMember());
        if (StringUtils.isEmpty(member)) {
            return null;
        } else {
            return "/do/login?operator=true";
        }
    }

    @Override
    protected void validateForm(final ActionMapping mapping, final ActionForm actionForm, final HttpServletRequest request, final HttpServletResponse response) throws ValidationException {
        final LoginForm form = (LoginForm) actionForm;
        String member = null;
        final boolean memberRequired = isMemberRequired(request);
        if (memberRequired) {
            member = StringUtils.trimToNull(form.getMember());
        }
        final String principal = StringUtils.trimToNull(form.getPrincipal());
        final String password = form.getPassword();
        final ValidationException vex = new ValidationException();
        if (memberRequired && member == null) {
            vex.setPropertyKey("member", "login.memberUsername");
            vex.addPropertyError("member", new RequiredError());
        }
        if (principal == null) {
            vex.setPropertyKey("username", "login.username");
            vex.addPropertyError("username", new RequiredError());
        }
        if (StringUtils.isEmpty(password)) {
            vex.setPropertyKey("_password", "login.password");
            vex.addPropertyError("_password", new RequiredError());
        }
        vex.throwIfHasErrors();
    }

    private Map<String, String> createLink(final HttpServletRequest request, final String label, final String paramName, final String paramValue) {
        final Map<String, String> link = new HashMap<String, String>();
        link.put("label", label);
        link.put("paramName", paramName);
        link.put("paramValue", paramValue);
        return link;
    }

    private String resolvePrincipalLabel(final PrincipalType principalType) {
        final Principal principal = principalType.getPrincipal();
        if (principal == Principal.CUSTOM_FIELD) {
            return principalType.getCustomField().getName();
        } else {
            return messageHelper.message(principal.getKey());
        }
    }

    private void storeCookie(final HttpServletRequest request, final HttpServletResponse response, final boolean force) {
        final String queryString = StringHelper.removeMarkupTags(request.getQueryString());
        if (force || StringUtils.isNotEmpty(queryString)) {
            // Store the query string as a cookie in order to be restored on logout
            final Cookie queryStringCookie = new Cookie("loginQueryString", StringHelper.encodeUrl(queryString));
            queryStringCookie.setPath(request.getContextPath());
            response.addCookie(queryStringCookie);

        }
        if (force) {
            // Remove the after logout cookie (received on external login)
            final Cookie afterLogoutCookie = new Cookie("afterLogout", null);
            afterLogoutCookie.setPath(request.getContextPath());
            response.addCookie(afterLogoutCookie);
        }
    }
}
