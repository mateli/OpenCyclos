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

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

import nl.strohalm.cyclos.entities.accounts.loans.Loan;
import nl.strohalm.cyclos.entities.accounts.loans.LoanPayment;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.services.transactions.exceptions.InvalidLoanPaymentAmountException;
import nl.strohalm.cyclos.services.transactions.exceptions.UnsortedLoanPaymentsException;

/**
 * Handler for multi payment loans
 * @author luis
 */
public class MultiPaymentLoanHandler extends BaseMultiplePaymentLoanHandler {

    private static final float PRECISION_DELTA = 0.001F;

    public MultiPaymentLoanHandler() {
        super(Loan.Type.MULTI_PAYMENT);
    }

    @Override
    protected void processGrant(final Loan loan, final GrantLoanDTO params) {
        // Build the payments
        final GrantMultiPaymentLoanDTO dto = (GrantMultiPaymentLoanDTO) params;
        final List<LoanPayment> payments = dto.getPayments();
        BigDecimal amount = BigDecimal.ZERO;
        Calendar lastDate = null;
        final LocalSettings localSettings = settingsService.getLocalSettings();
        for (final LoanPayment payment : payments) {
            if (lastDate == null) {
                lastDate = payment.getExpirationDate();
            } else {
                if (!lastDate.before(payment.getExpirationDate())) {
                    throw new UnsortedLoanPaymentsException();
                }
            }
            payment.setAmount(localSettings.round(payment.getAmount()));
            amount = amount.add(payment.getAmount());
        }
        amount = localSettings.round(amount);

        // Check if the payments values total == the loan total
        if (dto.getAmount().subtract(amount).abs().floatValue() > PRECISION_DELTA) {
            throw new InvalidLoanPaymentAmountException();
        }

        loan.setTotalAmount(amount);
        loan.setPayments(payments);
    }

}
