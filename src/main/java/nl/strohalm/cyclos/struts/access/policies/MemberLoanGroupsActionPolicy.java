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

import nl.strohalm.cyclos.access.AdminMemberPermission;
import nl.strohalm.cyclos.access.BrokerPermission;
import nl.strohalm.cyclos.controls.members.loangroups.MemberLoanGroupsForm;
import nl.strohalm.cyclos.struts.access.ActionDescriptor;
import nl.strohalm.cyclos.struts.access.ActionPolicy;
import nl.strohalm.cyclos.struts.access.policies.utils.AbstractActionPolicy;
import nl.strohalm.cyclos.utils.access.LoggedUser;

public class MemberLoanGroupsActionPolicy extends AbstractActionPolicy {
    private final static MemberLoanGroupsActionPolicy INSTANCE = new MemberLoanGroupsActionPolicy();

    public static ActionPolicy instance() {
        return INSTANCE;
    }

    private MemberLoanGroupsActionPolicy() {
    }

    @Override
    protected boolean doCheck(final ActionDescriptor descriptor) {
        final MemberLoanGroupsForm form = getForm();
        if (form.getMemberId() > 0) {
            return hasPermission(AdminMemberPermission.LOAN_GROUPS_VIEW, BrokerPermission.LOAN_GROUPS_VIEW);
        } else {
            return LoggedUser.isMember();
        }
        // return Boolean.valueOf((String) descriptor.getContext().getSession().getAttribute("loggedMemberHasLoanGroups"));
    }
}
