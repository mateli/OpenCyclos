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
package nl.strohalm.cyclos.struts.access.policies;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.access.AdminMemberPermission;
import nl.strohalm.cyclos.access.AdminSystemPermission;
import nl.strohalm.cyclos.access.BrokerPermission;
import nl.strohalm.cyclos.controls.AbstractActionContext;
import nl.strohalm.cyclos.services.permissions.PermissionService;
import nl.strohalm.cyclos.struts.access.ActionDescriptor;
import nl.strohalm.cyclos.struts.access.ActionPolicy;
import nl.strohalm.cyclos.utils.access.LoggedUser;
import nl.strohalm.cyclos.utils.conversion.IdConverter;

public class AccountOverviewActionPolicy implements ActionPolicy {
    private static final AccountOverviewActionPolicy INSTANCE = new AccountOverviewActionPolicy();

    public static ActionPolicy instance() {
        return INSTANCE;
    }

    private AccountOverviewActionPolicy() {
    }

    @Override
    public boolean check(final ActionDescriptor descriptor) {
        final PermissionService permissionService = descriptor.getPermissionService();
        final AbstractActionContext context = descriptor.getContext();
        final HttpServletRequest request = context.getRequest();

        final Long memberId = IdConverter.instance().valueOf(request.getParameter("memberId"));
        // TODO: review this because for operator there is a permission OperatorPermission.ACCOUNT_ACCOUNT_INFORMATION we should check this
        if (LoggedUser.isBroker() && (memberId != null && !memberId.equals(context.getUser().getId()))) {
            return permissionService.hasPermission(BrokerPermission.ACCOUNTS_INFORMATION);
        } else if (LoggedUser.isMember() || LoggedUser.isOperator()) {
            return true;
        } else { // administrator
            if (memberId != null) {
                return permissionService.hasPermission(AdminMemberPermission.ACCOUNTS_INFORMATION);
            }
            return permissionService.hasPermission(AdminSystemPermission.ACCOUNTS_INFORMATION);
        }
    }
}
