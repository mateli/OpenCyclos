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
package nl.strohalm.cyclos.controls.general;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.BasePublicAction;
import nl.strohalm.cyclos.entities.Entity;
import nl.strohalm.cyclos.entities.access.AdminUser;
import nl.strohalm.cyclos.entities.access.MemberUser;
import nl.strohalm.cyclos.entities.access.OperatorUser;
import nl.strohalm.cyclos.entities.access.User;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.entities.groups.GroupFilter;
import nl.strohalm.cyclos.entities.groups.SystemGroup;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.services.groups.GroupFilterService;
import nl.strohalm.cyclos.utils.LoginHelper;
import nl.strohalm.cyclos.utils.ResponseHelper;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action used to build the frameset
 * @author luis
 */
public class IndexAction extends BasePublicAction {
    private GroupFilterService groupFilterService;
    private ResponseHelper     responseHelper;

    @Inject
    public void setGroupFilterService(final GroupFilterService groupFilterService) {
        this.groupFilterService = groupFilterService;
    }

    @Inject
    public void setResponseHelper(final ResponseHelper responseHelper) {
        this.responseHelper = responseHelper;
    }

    @Override
    protected ActionForward executeAction(final ActionMapping mapping, final ActionForm actionForm, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final HttpSession session = request.getSession(false);
        if (session != null) {
            final String instantRedirectTo = (String) session.getAttribute("instantRedirectTo");
            if (instantRedirectTo != null) {
                session.removeAttribute("instantRedirectTo");
                response.sendRedirect(instantRedirectTo);
                return null;
            }
            session.setAttribute("isWebShop", false);
            session.setAttribute("isPosWeb", false);
        }

        final String queryString = StringUtils.trimToNull(request.getQueryString());
        Entity entity = null;
        String containerUrl = null;

        if (queryString != null) {
            // Try to find a group filter
            try {
                final GroupFilter groupFilter = groupFilterService.findByLoginPageName(queryString);
                containerUrl = groupFilter.getContainerUrl();
                entity = groupFilter;
            } catch (final EntityNotFoundException e) {
                // Try to find a group
                try {
                    final SystemGroup group = groupService.findByLoginPageName(queryString);
                    containerUrl = group.getContainerUrl();
                    entity = group;
                } catch (final EntityNotFoundException e1) {
                    // Ignore
                }
            }
        }

        // When no container url was found for group filter / group, try the global
        if (StringUtils.isEmpty(containerUrl)) {
            final LocalSettings localSettings = settingsService.getLocalSettings();
            containerUrl = localSettings.getContainerUrl();
        }
        if (StringUtils.isNotEmpty(containerUrl)) {
            // There is a container url. Now, for non-absolute urls, make them relative to the context path
            final String lower = containerUrl.toLowerCase();
            if (!lower.startsWith("http://") && !lower.startsWith("https://")) {
                containerUrl = request.getContextPath() + (containerUrl.startsWith("/") ? "" : "/") + containerUrl;
            }
            request.getSession().setAttribute("containerUrl", containerUrl);
        }

        // Update the cookies for the login page
        responseHelper.setLoginCookies(request, response, entity);

        // Find the forward
        String forward;
        final User loggedUser = LoginHelper.getLoggedUser(request);
        if (loggedUser == null) {
            forward = "login";
        } else if (loggedUser instanceof AdminUser) {
            forward = "adminHome";
        } else if (loggedUser instanceof MemberUser) {
            forward = "memberHome";
        } else if (loggedUser instanceof OperatorUser) {
            forward = "operatorHome";
        } else {
            throw new IllegalStateException("Invalid logged user: " + loggedUser);
        }
        return mapping.findForward(forward);
    }

}
