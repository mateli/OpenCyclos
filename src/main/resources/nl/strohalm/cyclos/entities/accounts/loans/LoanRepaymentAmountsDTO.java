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
package nl.strohalm.cyclos.entities.accounts.loans;

import java.math.BigDecimal;

import nl.strohalm.cyclos.utils.DataObject;

/**
 * Contains data about a loan repayment
 * @author luis
 */
public class LoanRepaymentAmountsDTO extends DataObject {
    private static final long serialVersionUID = -2627273648631985684L;
    private LoanPayment       loanPayment;
    private BigDecimal        remainingAmountAtExpirationDate;
    private BigDecimal        remainingAmountAtDate;

    public BigDecimal getDiscount() {
        final BigDecimal diff = remainingAmountAtExpirationDate.subtract(remainingAmountAtDate);
        if (diff.doubleValue() > 0.001) {
            return diff;
        }
        return BigDecimal.ZERO;
    }

    public BigDecimal getInterest() {
        final BigDecimal diff = remainingAmountAtDate.subtract(remainingAmountAtExpirationDate);
        if (diff.doubleValue() > 0.001) {
            return diff;
        }
        return BigDecimal.ZERO;
    }

    public LoanPayment getLoanPayment() {
        return loanPayment;
    }

    public BigDecimal getRemainingAmountAtDate() {
        return remainingAmountAtDate;
    }

    public BigDecimal getRemainingAmountAtExpirationDate() {
        return remainingAmountAtExpirationDate;
    }

    public BigDecimal getTotalAmount() {
        final BigDecimal repaidAmount = loanPayment.getRepaidAmount();
        return (repaidAmount == null ? BigDecimal.ZERO : repaidAmount).add(remainingAmountAtDate);
    }

    public void setLoanPayment(final LoanPayment payment) {
        loanPayment = payment;
    }

    public void setRemainingAmountAtDate(final BigDecimal remainingAmountToday) {
        remainingAmountAtDate = remainingAmountToday;
    }

    public void setRemainingAmountAtExpirationDate(final BigDecimal remainingAmountAtExpirationDate) {
        this.remainingAmountAtExpirationDate = remainingAmountAtExpirationDate;
    }

}
