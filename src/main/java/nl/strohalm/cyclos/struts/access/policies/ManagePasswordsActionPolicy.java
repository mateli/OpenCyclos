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

import java.util.concurrent.Callable;

import nl.strohalm.cyclos.access.AdminAdminPermission;
import nl.strohalm.cyclos.access.AdminMemberPermission;
import nl.strohalm.cyclos.access.BrokerPermission;
import nl.strohalm.cyclos.access.MemberPermission;
import nl.strohalm.cyclos.controls.access.ManagePasswordsForm;
import nl.strohalm.cyclos.entities.access.User;
import nl.strohalm.cyclos.struts.access.ActionDescriptor;
import nl.strohalm.cyclos.struts.access.ActionPolicy;
import nl.strohalm.cyclos.struts.access.policies.utils.AbstractActionPolicy;
import nl.strohalm.cyclos.utils.access.LoggedUser;

public class ManagePasswordsActionPolicy extends AbstractActionPolicy {
    private final static ManagePasswordsActionPolicy INSTANCE = new ManagePasswordsActionPolicy();

    public static ActionPolicy instance() {
        return INSTANCE;
    }

    private ManagePasswordsActionPolicy() {
    }

    @Override
    protected boolean doCheck(final ActionDescriptor descriptor) {
        final ManagePasswordsForm form = getForm();
        final User user = LoggedUser.runAsSystem(new Callable<User>() {
            @Override
            public User call() throws Exception {
                return descriptor.getElementService().loadUser(form.getUserId(), User.Relationships.ELEMENT);
            }
        });
        switch (user.getElement().getNature()) {
            case ADMIN:
                return hasPermission(AdminAdminPermission.ACCESS_CHANGE_PASSWORD, AdminAdminPermission.ACCESS_TRANSACTION_PASSWORD);
            case MEMBER:
                return hasPermission(AdminMemberPermission.ACCESS_CHANGE_PASSWORD, AdminMemberPermission.ACCESS_RESET_PASSWORD, AdminMemberPermission.ACCESS_TRANSACTION_PASSWORD, BrokerPermission.MEMBER_ACCESS_CHANGE_PASSWORD, BrokerPermission.MEMBER_ACCESS_RESET_PASSWORD, BrokerPermission.MEMBER_ACCESS_TRANSACTION_PASSWORD);
            case OPERATOR:
                return hasPermission(MemberPermission.OPERATORS_MANAGE);
            default:
                return false;
        }
    }
}
