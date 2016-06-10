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

import nl.strohalm.cyclos.entities.accounts.loans.Loan;
import nl.strohalm.cyclos.entities.accounts.loans.LoanPayment;

/**
 * Parameters for repaying a loan
 * @author luis
 */
public class RepayLoanDTO extends LoanPaymentDTO {
    private static final long serialVersionUID = -2009644716091915242L;
    private BigDecimal        amount;

    public RepayLoanDTO() {
    }

    public RepayLoanDTO(final Loan loan, final BigDecimal amount) {
        this.loan = loan;
        this.amount = amount;
    }

    public RepayLoanDTO(final LoanPayment loanPayment, final BigDecimal amount) {
        loan = loanPayment.getLoan();
        this.loanPayment = loanPayment;
        this.amount = amount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(final BigDecimal amount) {
        this.amount = amount;
    }

}
