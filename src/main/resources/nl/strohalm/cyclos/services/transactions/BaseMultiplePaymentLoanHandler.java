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
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import nl.strohalm.cyclos.entities.accounts.loans.Loan.Type;
import nl.strohalm.cyclos.entities.accounts.loans.LoanPayment;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.utils.DateHelper;

/**
 * Base loan handler for types that handles multiple payments
 * @author luis
 */
public abstract class BaseMultiplePaymentLoanHandler extends BaseLoanHandler {

    public BaseMultiplePaymentLoanHandler(final Type type) {
        super(type);
    }

    public List<LoanPayment> calculatePaymentProjection(final ProjectionDTO params) {

        // Calculate the loan total
        final int paymentCount = params.getPaymentCount();
        final BigDecimal amount = retrieveTotalAmount(params);

        // Calculate the payment amount
        BigDecimal paymentAmount;
        BigDecimal lastPaymentAmount;
        final LocalSettings localSettings = settingsService.getLocalSettings();
        final MathContext mathContext = localSettings.getMathContext();
        if (paymentCount == 1) {
            paymentAmount = lastPaymentAmount = amount; // 2 assignments!!! :-O
        } else {
            paymentAmount = localSettings.round(amount.divide(new BigDecimal(paymentCount), mathContext));
            lastPaymentAmount = amount.subtract(paymentAmount.multiply(new BigDecimal(paymentCount - 1)));
        }

        // Build the payment list
        final Calendar firstDate = DateHelper.truncate(params.getFirstExpirationDate());
        final List<LoanPayment> payments = new ArrayList<LoanPayment>();
        for (int i = 0; i < paymentCount; i++) {
            final LoanPayment loanPayment = new LoanPayment();
            loanPayment.setIndex(i);
            loanPayment.setStatus(LoanPayment.Status.OPEN);
            final Calendar expiration = (Calendar) firstDate.clone();
            expiration.add(Calendar.MONTH, i);
            loanPayment.setExpirationDate(expiration);
            if (i == paymentCount - 1) {
                loanPayment.setAmount(lastPaymentAmount);
            } else {
                loanPayment.setAmount(paymentAmount);
            }
            payments.add(loanPayment);
        }
        return payments;
    }

    protected BigDecimal retrieveTotalAmount(final ProjectionDTO params) {
        return params.getAmount();
    }

}
