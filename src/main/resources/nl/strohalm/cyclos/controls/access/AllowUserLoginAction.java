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
import nl.strohalm.cyclos.entities.access.User;
import nl.strohalm.cyclos.utils.ActionHelper;
import nl.strohalm.cyclos.utils.EntityHelper;
import nl.strohalm.cyclos.utils.validation.ValidationException;

import org.apache.struts.action.ActionForward;

/**
 * Action used to reenable a user to login immediately if his login was blocked by wrong password tries
 * @author luis
 */
public class AllowUserLoginAction extends BaseAction {

    @Override
    protected ActionForward executeAction(final ActionContext context) throws Exception {
        final AllowUserLoginForm form = context.getForm();
        final long id = form.getUserId();
        if (id <= 0L) {
            throw new ValidationException();
        }
        String param;
        String forward;
        User user = EntityHelper.reference(User.class, id);
        user = accessService.reenableLogin(user);
        if (user instanceof MemberUser) {
            param = "memberId";
            forward = "memberProfile";
        } else if (user instanceof AdminUser) {
            param = "adminId";
            forward = "adminProfile";
        } else { // user instanceof OperatorUser
            param = "operatorId";
            forward = "operatorProfile";
        }
        context.sendMessage("profile.userAllowedToLogin");
        return ActionHelper.redirectWithParam(context.getRequest(), context.findForward(forward), param, id);
    }

}
