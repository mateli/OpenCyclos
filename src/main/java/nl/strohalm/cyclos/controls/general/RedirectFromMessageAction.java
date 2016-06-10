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

import java.util.concurrent.Callable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.BasePublicAction;
import nl.strohalm.cyclos.entities.access.User;
import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.entities.groups.GroupFilter;
import nl.strohalm.cyclos.entities.groups.SystemGroup;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.Operator;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.utils.LoginHelper;
import nl.strohalm.cyclos.utils.RelationshipHelper;
import nl.strohalm.cyclos.utils.ResponseHelper;
import nl.strohalm.cyclos.utils.access.LoggedUser;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action used to redirect the user to the correct page when coming from a message or e-mail link
 * 
 * @author luis
 */
public class RedirectFromMessageAction extends BasePublicAction {

    private ResponseHelper responseHelper;

    @Inject
    public void setResponseHelper(final ResponseHelper responseHelper) {
        this.responseHelper = responseHelper;
    }

    @Override
    protected ActionForward executeAction(final ActionMapping mapping, final ActionForm actionForm, final HttpServletRequest request, final HttpServletResponse response) throws Exception {

        final RedirectFromMessageForm form = (RedirectFromMessageForm) actionForm;

        // Get the path
        final String path = StringUtils.trimToNull(form.getPath());
        if (path == null) {
            return null;
        }

        // Get the element
        final long userId = form.getUserId();
        Element element = null;
        if (userId > 0L) {
            element = LoggedUser.runAsSystem(new Callable<Element>() {
                @Override
                public Element call() throws Exception {
                    try {
                        return elementService.load(userId, RelationshipHelper.nested(Element.Relationships.GROUP, Group.Relationships.GROUP_FILTERS));
                    } catch (final Exception e) {
                        // ok, leave element null
                        return null;
                    }
                }
            });
        }

        // Find the currently logged user
        final User loggedUser = LoginHelper.getLoggedUser(request);
        HttpSession session = request.getSession();
        if (userId > 0L && loggedUser != null) {
            if (loggedUser.getId().equals(userId)) {
                // The expected user is already logged in. Redirect to the path directly
                return new ActionForward(path, true);
            } else {
                // When there was another user logged in, invalidate the session, because we expect a fixed user
                session.invalidate();
                session = request.getSession();
            }
        }

        String containerUrl = null;
        SystemGroup group = null;
        if (element != null) {
            // Find the container url
            if (element instanceof Operator) {
                group = (SystemGroup) ((Operator) element).getMember().getGroup();
            } else {
                group = (SystemGroup) element.getGroup();
            }
            if (StringUtils.isNotEmpty(group.getContainerUrl())) {
                containerUrl = group.getContainerUrl();
            } else {
                for (final GroupFilter groupFilter : group.getGroupFilters()) {
                    if (StringUtils.isNotEmpty(groupFilter.getContainerUrl())) {
                        containerUrl = groupFilter.getContainerUrl();
                        break;
                    }
                }
            }
        }
        if (StringUtils.isEmpty(containerUrl)) {
            // Get the default container url
            final LocalSettings localSettings = settingsService.getLocalSettings();
            containerUrl = localSettings.getContainerUrl();
        }
        // Set the containerUrl to session
        session.setAttribute("containerUrl", containerUrl);

        // Update the cookie for the group
        responseHelper.setLoginCookies(request, response, group);

        // Set the returnTo on the session, so that after logging in, the user will be redirected to this page
        session.setAttribute("returnTo", path);

        return mapping.findForward("login");
    }
}
