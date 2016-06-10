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

import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseAction;
import nl.strohalm.cyclos.entities.access.AdminUser;
import nl.strohalm.cyclos.entities.access.MemberUser;
import nl.strohalm.cyclos.entities.access.OperatorUser;
import nl.strohalm.cyclos.entities.access.User;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.services.access.exceptions.NotConnectedException;
import nl.strohalm.cyclos.utils.ActionHelper;
import nl.strohalm.cyclos.utils.EntityHelper;
import nl.strohalm.cyclos.utils.validation.ValidationException;

import org.apache.struts.action.ActionForward;

/**
 * Action used to disconnect a logged user
 * @author luis
 */
public class DisconnectUserAction extends BaseAction {

    @Override
    protected ActionForward executeAction(final ActionContext context) throws Exception {
        final DisconnectUserForm form = context.getForm();
        final long userId = form.getUserId();
        if (userId <= 0) {
            throw new ValidationException();
        }
        try {
            User user = EntityHelper.reference(User.class, userId);
            user = accessService.disconnect(user);
            String param;
            String forward;
            if (user instanceof AdminUser) {
                param = "adminId";
                forward = "adminProfile";
            } else if (user instanceof MemberUser) {
                param = "memberId";
                forward = "memberProfile";
            } else if (user instanceof OperatorUser) {
                param = "operatorId";
                forward = "operatorProfile";
            } else {
                throw new IllegalStateException("Unknown user type: " + user);
            }
            context.sendMessage("disconnect.disconnected");
            return ActionHelper.redirectWithParam(context.getRequest(), context.findForward(forward), param, userId);
        } catch (final EntityNotFoundException e) {
            throw new ValidationException();
        } catch (final NotConnectedException e) {
            return context.sendError("disconnect.error.notConnected");
        }
    }

}
