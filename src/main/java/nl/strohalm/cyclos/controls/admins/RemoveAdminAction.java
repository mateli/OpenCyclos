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
package nl.strohalm.cyclos.controls.admins;

import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.elements.RemoveElementAction;
import nl.strohalm.cyclos.entities.members.Element.Nature;

import org.apache.struts.action.ActionForward;

/**
 * Action used to remove an admin
 * @author luis
 */
public class RemoveAdminAction extends RemoveElementAction {

    @Override
    protected ActionForward doRemove(final long id, final ActionContext context) {
        try {
            elementService.remove(id);
            context.sendMessage("changeGroup.admin.permanentlyRemovedMessage");
            return context.getSuccessForward();
        } catch (final Exception e) {
            return context.sendError("changeGroup.error.remove.activeAdmin");
        }
    }

    @Override
    protected Nature expectedNature() {
        return Nature.ADMIN;
    }

}
