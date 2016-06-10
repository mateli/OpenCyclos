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
import nl.strohalm.cyclos.entities.Entity;
import nl.strohalm.cyclos.entities.access.User;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.entities.groups.GroupFilter;
import nl.strohalm.cyclos.services.groups.GroupFilterService;
import nl.strohalm.cyclos.utils.LoginHelper;
import nl.strohalm.cyclos.utils.ResponseHelper;
import nl.strohalm.cyclos.utils.access.LoggedUser;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action used to set the cookies regarding guest pages customization, then redirect to a given path
 * @author luis
 */
public class RedirectAction extends BasePublicAction {
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
        final RedirectForm form = (RedirectForm) actionForm;

        final HttpSession session = request.getSession(false);

        Entity entity = null;
        final String name = StringUtils.trimToNull(form.getName());
        final Long groupId = form.getGroupId() > 0 ? form.getGroupId() : null;
        final Long groupFilterId = form.getGroupFilterId() > 0 ? form.getGroupFilterId() : null;

        if (groupId != null) {
            entity = LoggedUser.runAsSystem(new Callable<Group>() {
                @Override
                public Group call() throws Exception {
                    try {
                        return groupService.load(groupId);
                    } catch (final Exception e) {
                        return null;
                    }
                }
            });
        } else if (groupFilterId != null) {
            entity = LoggedUser.runAsSystem(new Callable<GroupFilter>() {
                @Override
                public GroupFilter call() throws Exception {
                    try {
                        return groupFilterService.load(groupFilterId);
                    } catch (final Exception e) {
                        return null;
                    }
                }
            });
        } else if (name != null) {
            // Try to find a group filter
            try {
                entity = groupFilterService.findByLoginPageName(name);
            } catch (final EntityNotFoundException e) {
                // Try to find a group
                try {
                    entity = groupService.findByLoginPageName(name);
                } catch (final EntityNotFoundException e1) {
                    // Ignore
                }
            }
        }

        // Update the cookies for the login page
        responseHelper.setLoginCookies(request, response, entity);

        // Find the path
        final User loggedUser = LoginHelper.getLoggedUser(request);
        String path = StringUtils.trimToNull(form.getPath());
        if (path == null) {
            if (loggedUser == null) {
                path = "/do/login";
            } else {
                path = session.getAttribute("pathPrefix") + "/home";
            }
        }
        return new ActionForward(path, true);
    }

}
