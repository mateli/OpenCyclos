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
package nl.strohalm.cyclos.controls.access.channels;

import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseAction;
import nl.strohalm.cyclos.entities.access.MemberUser;
import nl.strohalm.cyclos.utils.ActionHelper;
import nl.strohalm.cyclos.utils.EntityHelper;

import org.apache.struts.action.ActionForward;

/**
 * Action used to unblock a member pin
 * @author luis
 */
public class UnblockPinAction extends BaseAction {

    @Override
    protected ActionForward executeAction(final ActionContext context) throws Exception {
        final UnblockPinForm form = context.getForm();
        final long memberId = form.getMemberId();
        final MemberUser user = memberId <= 0 ? context.<MemberUser> getUser() : EntityHelper.reference(MemberUser.class, memberId);
        accessService.unblockPin(user);
        context.sendMessage("pin.unblocked");
        return ActionHelper.redirectWithParam(context.getRequest(), context.getSuccessForward(), "memberId", memberId);
    }

}
