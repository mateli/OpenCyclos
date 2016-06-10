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
package nl.strohalm.cyclos.controls.loangroups;

import java.util.HashMap;
import java.util.Map;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseAction;
import nl.strohalm.cyclos.entities.accounts.loans.LoanGroup;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.services.loangroups.LoanGroupService;
import nl.strohalm.cyclos.services.loangroups.exceptions.MemberAlreadyInListException;
import nl.strohalm.cyclos.utils.ActionHelper;
import nl.strohalm.cyclos.utils.EntityHelper;
import nl.strohalm.cyclos.utils.validation.ValidationException;

import org.apache.struts.action.ActionForward;

/**
 * Base action for loan group memberships
 * @author luis
 */
public abstract class BaseLoanGroupMembershipAction extends BaseAction {

    private LoanGroupService loanGroupService;

    public LoanGroupService getLoanGroupService() {
        return loanGroupService;
    }

    @Inject
    public void setLoanGroupService(final LoanGroupService loanGroupService) {
        this.loanGroupService = loanGroupService;
    }

    protected abstract boolean add();

    @Override
    protected ActionForward executeAction(final ActionContext context) throws Exception {
        final LoanGroupMembershipForm form = context.getForm();
        final long loanGroupId = form.getLoanGroupId();
        final long memberId = form.getMemberId();
        if (loanGroupId <= 0L || memberId <= 0L) {
            throw new ValidationException();
        }
        final LoanGroup loanGroup = EntityHelper.reference(LoanGroup.class, loanGroupId);
        final Member member = EntityHelper.reference(Member.class, memberId);
        if (add()) {
            try {
                loanGroupService.addMember(member, loanGroup);
                context.sendMessage("loanGroup.memberAdded");
            } catch (final MemberAlreadyInListException e) {
                context.sendMessage("loanGroup.error.memberAlreadyInList");
            }
        } else {
            loanGroupService.removeMember(member, loanGroup);
            context.sendMessage("loanGroup.memberRemoved");
        }
        final Map<String, Object> params = new HashMap<String, Object>();
        params.put("loanGroupId", loanGroupId);
        params.put("memberId", memberId);
        return ActionHelper.redirectWithParams(context.getRequest(), context.getSuccessForward(), params);
    }

}
