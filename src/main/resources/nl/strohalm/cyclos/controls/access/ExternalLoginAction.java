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

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.BasePublicFormAction;
import nl.strohalm.cyclos.entities.access.Channel;
import nl.strohalm.cyclos.entities.access.MemberUser;
import nl.strohalm.cyclos.entities.access.User;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.exceptions.PermissionDeniedException;
import nl.strohalm.cyclos.services.access.exceptions.AlreadyConnectedException;
import nl.strohalm.cyclos.services.access.exceptions.BlockedCredentialsException;
import nl.strohalm.cyclos.services.access.exceptions.InactiveMemberException;
import nl.strohalm.cyclos.services.access.exceptions.LoginException;
import nl.strohalm.cyclos.utils.LoginHelper;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action used to login the user from within external sites
 * @author luis
 */
public class ExternalLoginAction extends BasePublicFormAction {

    /**
     * The possible statuses
     * @author luis
     */
    public static enum Status {
        SUCCESS(null), MISSING_USERNAME("errors.required", "login.username"), ALREADY_CONNECTED("login.error.alreadyConnected"), MISSING_PASSWORD("errors.required", "login.password"), INVALID("login.error"), BLOCKED("login.error.blocked"), INACTIVE("login.error.inactive"), PERMISSION_DENIED("error.accessDenied"), UNKNOWN_ERROR("error.general");

        private final String key;
        private final String argument;

        private Status(final String key) {
            this(key, null);
        }

        private Status(final String key, final String argument) {
            this.key = key;
            this.argument = argument;
        }

        public String getArgument() {
            return argument;
        }

        public String getKey() {
            return key;
        }
    }

    private LoginHelper loginHelper;

    @Override
    public void prepareForm(final ActionMapping mapping, final ActionForm actionForm, final HttpServletRequest request, final HttpServletResponse response) {

        // Resolve the translation messages for each status
        final Map<String, String> statusMessages = new HashMap<String, String>();
        for (final Status status : Status.values()) {
            final String key = status.getKey();
            if (key != null) {
                String argument = status.getArgument();
                if (argument != null) {
                    // The argument is actually a key to another message
                    argument = messageHelper.message(argument);
                }
                statusMessages.put(status.name(), messageHelper.message(key, argument));
            }
        }
        request.setAttribute("statusMessages", statusMessages);

        // Store a cookie in order to know where to go after logout
        String afterLogout = request.getParameter("afterLogout");
        afterLogout = StringUtils.trimToEmpty(afterLogout);
        try {
            final LocalSettings settings = settingsService.getLocalSettings();
            afterLogout = URLEncoder.encode(StringUtils.trimToEmpty(afterLogout), settings.getCharset());
        } catch (final UnsupportedEncodingException e) {
        }
        final Cookie cookie = new Cookie("afterLogout", afterLogout);
        cookie.setPath(request.getContextPath());
        response.addCookie(cookie);
    }

    @Inject
    public final void setLoginHelper(final LoginHelper loginHelper) {
        this.loginHelper = loginHelper;
    }

    @Override
    protected ActionForward handleDisplay(final ActionMapping mapping, final ActionForm actionForm, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final User loggedUser = LoginHelper.getLoggedUser(request);
        if (loggedUser != null) {
            final HttpSession session = request.getSession();
            return new ActionForward(session.getAttribute("pathPrefix") + "/home", true);
        }

        return super.handleDisplay(mapping, actionForm, request, response);
    }

    @Override
    protected ActionForward handleSubmit(final ActionMapping mapping, final ActionForm actionForm, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final Status status = doLogin(actionForm, request, response);
        if (status == Status.SUCCESS) {
            final User user = LoginHelper.getLoggedUser(request);
            if (user instanceof MemberUser && elementService.shallAcceptAgreement(((MemberUser) user).getMember())) {
                // Should accept a registration agreement first
                request.getSession().setAttribute("shallAcceptRegistrationAgreement", true);
            }
        }
        responseHelper.setTextNoCache(response);
        response.getWriter().print(status.name());
        return null;
    }

    private Status doLogin(final ActionForm actionForm, final HttpServletRequest request, final HttpServletResponse response) {
        final LoginForm form = (LoginForm) actionForm;
        final HttpSession session = request.getSession();

        // Get the parameters
        final String member = StringUtils.trimToNull(form.getMember());
        final String principal = StringUtils.trimToNull(form.getPrincipal());
        final String password = StringUtils.trimToNull(form.getPassword());

        // Check for missing parameters
        if (principal == null) {
            return Status.MISSING_USERNAME;
        } else if (password == null) {
            return Status.MISSING_PASSWORD;
        }

        // Perform the login
        try {
            loginHelper.login(User.class, form.getPrincipalType(), member, principal, password, Channel.WEB, request, response);
            return Status.SUCCESS;
        } catch (final BlockedCredentialsException e) {
            return Status.BLOCKED;
        } catch (final InactiveMemberException e) {
            return Status.INACTIVE;
        } catch (final AlreadyConnectedException e) {
            return Status.ALREADY_CONNECTED;
        } catch (final PermissionDeniedException e) {
            session.invalidate();
            return Status.PERMISSION_DENIED;
        } catch (final LoginException e) {
            return Status.INVALID;
        } catch (final Exception e) {
            actionHelper.generateLog(request, getServlet().getServletContext(), e);
            return Status.UNKNOWN_ERROR;
        }
    }
}
