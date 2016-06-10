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

import java.util.Collections;
import java.util.List;

import nl.strohalm.cyclos.utils.Period;

/**
 * Query parameters for loan payment search
 * @author luis
 */
public class LoanPaymentQuery extends BaseLoanQuery {

    private static final long        serialVersionUID = 5511627770942539793L;
    private Period                   expirationPeriod;
    private Period                   repaymentPeriod;
    private Loan                     loan;
    private List<LoanPayment.Status> status;

    public Period getExpirationPeriod() {
        return expirationPeriod;
    }

    public Loan getLoan() {
        return loan;
    }

    public Period getRepaymentPeriod() {
        return repaymentPeriod;
    }

    public LoanPayment.Status getStatus() {
        return status == null || status.isEmpty() ? null : status.iterator().next();
    }

    public List<LoanPayment.Status> getStatusList() {
        return status;
    }

    public void setExpirationPeriod(final Period expirationPeriod) {
        this.expirationPeriod = expirationPeriod;
    }

    public void setLoan(final Loan loan) {
        this.loan = loan;
    }

    public void setRepaymentPeriod(final Period repaymentPeriod) {
        this.repaymentPeriod = repaymentPeriod;
    }

    public void setStatus(final LoanPayment.Status status) {
        this.status = Collections.singletonList(status);
    }

    public void setStatusList(final List<LoanPayment.Status> status) {
        this.status = status;
    }

}
