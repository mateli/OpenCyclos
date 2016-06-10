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

import java.util.Calendar;
import java.util.Collection;

import nl.strohalm.cyclos.entities.accounts.loans.Loan;
import nl.strohalm.cyclos.entities.accounts.loans.LoanPayment;
import nl.strohalm.cyclos.entities.customization.fields.PaymentCustomFieldValue;
import nl.strohalm.cyclos.utils.DataObject;

/**
 * Class containing a loan and a loan payment
 * @author luis
 */
public class LoanPaymentDTO extends DataObject {

    private static final long                     serialVersionUID = -5636162107477450931L;
    protected Loan                                loan;
    protected LoanPayment                         loanPayment;
    protected Calendar                            date;
    protected Collection<PaymentCustomFieldValue> customValues;

    public LoanPaymentDTO() {
        super();
    }

    public Collection<PaymentCustomFieldValue> getCustomValues() {
        return customValues;
    }

    public Calendar getDate() {
        return date;
    }

    public Loan getLoan() {
        return loan;
    }

    public LoanPayment getLoanPayment() {
        return loanPayment;
    }

    public void setCustomValues(final Collection<PaymentCustomFieldValue> customValues) {
        this.customValues = customValues;
    }

    public void setDate(final Calendar date) {
        this.date = date;
    }

    public void setLoan(final Loan loan) {
        this.loan = loan;
    }

    public void setLoanPayment(final LoanPayment loanPayment) {
        this.loanPayment = loanPayment;
    }

}
