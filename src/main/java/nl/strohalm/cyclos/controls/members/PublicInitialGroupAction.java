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
package nl.strohalm.cyclos.controls.members;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nl.strohalm.cyclos.controls.BasePublicAction;
import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.entities.groups.GroupFilter;
import nl.strohalm.cyclos.utils.ActionHelper;
import nl.strohalm.cyclos.utils.EntityHelper;
import nl.strohalm.cyclos.utils.RequestHelper;
import nl.strohalm.cyclos.utils.conversion.IdConverter;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action used to select a initial group for public members
 * @author Lucas Geiss
 */
public class PublicInitialGroupAction extends BasePublicAction {

    @Override
    protected ActionForward executeAction(final ActionMapping mapping, final ActionForm actionForm, final HttpServletRequest request, final HttpServletResponse response) throws Exception {

        // When there's a logged user, redirect to home
        if (request.getSession().getAttribute("loggedUser") != null) {
            response.sendRedirect("/" + request.getContextPath());
            return null;
        }

        final Long groupFilterId = IdConverter.instance().valueOf(RequestHelper.getCookieValue(request, "groupFilterId"));
        final GroupFilter groupFilter = EntityHelper.reference(GroupFilter.class, groupFilterId);
        final List<? extends Group> groups = groupService.getPossibleInitialGroups(groupFilter);
        request.setAttribute("isPublic", Boolean.TRUE);

        if (groups.isEmpty()) {
            return ActionHelper.sendError(mapping, request, response, "createMember.error.noPossibleGroup");
        } else if (groups.size() == 1) {
            return ActionHelper.redirectWithParam(request, mapping.findForward("success"), "groupId", groups.get(0).getId());
        } else {
            request.setAttribute("groups", groups);
            return mapping.getInputForward();
        }
    }
}
