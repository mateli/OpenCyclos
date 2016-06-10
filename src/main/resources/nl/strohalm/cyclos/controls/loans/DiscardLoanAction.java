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

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.entities.accounts.loans.Loan;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.services.transactions.LoanPaymentDTO;
import nl.strohalm.cyclos.utils.ActionHelper;
import nl.strohalm.cyclos.utils.binding.BeanBinder;
import nl.strohalm.cyclos.utils.binding.PropertyBinder;

import org.apache.struts.action.ActionForward;

/**
 * Action used to discard a loan
 * @author luis
 */
public class DiscardLoanAction extends LoanDetailsAction {

    @Override
    protected ActionForward handleSubmit(final ActionContext context) throws Exception {
        final DiscardLoanForm form = context.getForm();

        final LoanPaymentDTO dto = resolveLoanDTO(context);
        final Loan loan = dto.getLoan();
        if (shouldValidateTransactionPassword(context, loan)) {
            context.checkTransactionPassword(form.getTransactionPassword());
        }
        loanService.discard(dto);

        final Map<String, Object> params = new HashMap<String, Object>();
        params.put("loanId", form.getLoanId());
        params.put("memberId", form.getMemberId());
        params.put("loanGroupId", form.getLoanGroupId());
        context.sendMessage("loan.discarded");
        return ActionHelper.redirectWithParams(context.getRequest(), context.getSuccessForward(), params);
    }

    @Override
    protected void initDataBinder(final BeanBinder<? extends LoanPaymentDTO> binder) {
        super.initDataBinder(binder);
        final LocalSettings localSettings = settingsService.getLocalSettings();
        binder.registerBinder("date", PropertyBinder.instance(Calendar.class, "date", localSettings.getRawDateConverter()));
    }

}
