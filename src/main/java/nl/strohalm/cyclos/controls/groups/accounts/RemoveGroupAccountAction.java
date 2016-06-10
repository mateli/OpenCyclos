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
package nl.strohalm.cyclos.controls.groups.accounts;

import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseAction;
import nl.strohalm.cyclos.entities.accounts.MemberAccountType;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.utils.ActionHelper;
import nl.strohalm.cyclos.utils.EntityHelper;
import nl.strohalm.cyclos.utils.validation.ValidationException;

import org.apache.struts.action.ActionForward;

/**
 * Action used to remove an account from a group
 * @author luis
 */
public class RemoveGroupAccountAction extends BaseAction {

    @Override
    protected ActionForward executeAction(final ActionContext context) throws Exception {
        final RemoveGroupAccountForm form = context.getForm();
        final long groupId = form.getGroupId();
        final long accountTypeId = form.getAccountTypeId();
        if (groupId <= 0L || accountTypeId <= 0L) {
            throw new ValidationException();
        }
        try {
            groupService.removeAccountTypeRelationship(EntityHelper.reference(MemberGroup.class, groupId), EntityHelper.reference(MemberAccountType.class, accountTypeId));
            context.sendMessage("group.account.removed");
        } catch (final Exception e) {
            context.sendMessage("group.account.error.removing");
        }
        return ActionHelper.redirectWithParam(context.getRequest(), context.getSuccessForward(), "groupId", groupId);
    }

}
