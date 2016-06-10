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
package nl.strohalm.cyclos.controls.loans;

import java.util.HashMap;
import java.util.Map;

import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.entities.accounts.loans.Loan;
import nl.strohalm.cyclos.entities.accounts.loans.LoanPayment;
import nl.strohalm.cyclos.utils.ActionHelper;
import nl.strohalm.cyclos.utils.validation.ValidationException;

import org.apache.struts.action.ActionForward;

/**
 * Action used to manage an expired loan status
 * @author luis
 */
public class ManageExpiredStatusAction extends LoanDetailsAction {

    @Override
    protected ActionForward handleSubmit(final ActionContext context) throws Exception {
        final ManageExpiredStatusForm form = context.getForm();

        LoanPayment.Status status;
        try {
            status = LoanPayment.Status.valueOf(form.getStatus());
        } catch (final Exception e) {
            throw new ValidationException();
        }

        final Loan loan = resolveLoanDTO(context).getLoan();
        if (shouldValidateTransactionPassword(context, loan)) {
            context.checkTransactionPassword(form.getTransactionPassword());
        }

        switch (status) {
            case IN_PROCESS:
                loanService.markAsInProcess(loan);
                break;
            case RECOVERED:
                loanService.markAsRecovered(loan);
                break;
            case UNRECOVERABLE:
                loanService.markAsUnrecoverable(loan);
                break;
            default:
                throw new ValidationException();
        }

        final String statusMsg = context.message("loan.status." + status.name());
        context.sendMessage("loan.changedExpiredStatus", statusMsg);

        final Map<String, Object> params = new HashMap<String, Object>();
        params.put("loanId", form.getLoanId());
        params.put("memberId", form.getMemberId());
        params.put("loanGroupId", form.getLoanGroupId());
        return ActionHelper.redirectWithParams(context.getRequest(), context.getSuccessForward(), params);
    }
}
