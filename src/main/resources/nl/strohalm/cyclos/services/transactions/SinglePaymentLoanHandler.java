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
package nl.strohalm.cyclos.services.transactions;

import java.util.Collections;
import java.util.List;

import nl.strohalm.cyclos.entities.accounts.loans.Loan;
import nl.strohalm.cyclos.entities.accounts.loans.LoanPayment;
import nl.strohalm.cyclos.utils.TimePeriod;

/**
 * Handler for single payment loans
 * @author luis
 */
public class SinglePaymentLoanHandler extends BaseLoanHandler {

    public SinglePaymentLoanHandler() {
        super(Loan.Type.SINGLE_PAYMENT);
    }

    public List<LoanPayment> calculatePaymentProjection(final ProjectionDTO params) {
        // Get the number of days to pay by default
        final Integer repaymentDays = params.getTransferType().getLoan().getRepaymentDays();
        final TimePeriod timePeriod = new TimePeriod(repaymentDays == null ? 30 : repaymentDays, TimePeriod.Field.DAYS);

        // Build the loan payment
        final LoanPayment payment = new LoanPayment();
        payment.setExpirationDate(timePeriod.add(params.getDate()));
        payment.setAmount(params.getAmount());
        payment.setIndex(0);
        payment.setStatus(LoanPayment.Status.OPEN);
        return Collections.singletonList(payment);
    }

    @Override
    protected void processGrant(final Loan loan, final GrantLoanDTO params) {
        final GrantSinglePaymentLoanDTO dto = (GrantSinglePaymentLoanDTO) params;
        final LoanPayment loanPayment = new LoanPayment();
        loanPayment.setExpirationDate(dto.getRepaymentDate());
        loanPayment.setAmount(params.getAmount());
        loan.setPayments(Collections.singletonList(loanPayment));
        loan.setTotalAmount(loanPayment.getAmount());
    }
}
