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
import java.util.Collection;

import nl.strohalm.cyclos.entities.accounts.loans.Loan;
import nl.strohalm.cyclos.entities.accounts.transactions.Transfer;
import nl.strohalm.cyclos.utils.DataObject;
import nl.strohalm.cyclos.utils.FormatObject;

public class LoanPaymentDetailDTO extends DataObject {

    private static final long    serialVersionUID = 1823381847692665597L;
    private Calendar             expirationDate;
    private Loan                 loan;
    private Integer              number;
    private BigDecimal           repaidFees;
    private BigDecimal           repaidUnits;
    private BigDecimal           repaidValue;
    private Calendar             repaymentDate;
    private Loan.Status          status;
    private BigDecimal           total;
    private Collection<Transfer> transfers;
    private BigDecimal           value;

    public Calendar getExpirationDate() {
        return expirationDate;
    }

    public Loan getLoan() {
        return loan;
    }

    public Integer getNumber() {
        return number;
    }

    public BigDecimal getRepaidFees() {
        return repaidFees;
    }

    public BigDecimal getRepaidUnits() {
        return repaidUnits;
    }

    public BigDecimal getRepaidValue() {
        return repaidValue;
    }

    public Calendar getRepaymentDate() {
        return repaymentDate;
    }

    public Loan.Status getStatus() {
        return status;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public Collection<Transfer> getTransfers() {
        return transfers;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setExpirationDate(final Calendar expirationDate) {
        this.expirationDate = expirationDate;
    }

    public void setLoan(final Loan loan) {
        this.loan = loan;
    }

    public void setNumber(final Integer number) {
        this.number = number;
    }

    public void setRepaidFees(final BigDecimal repaidFees) {
        this.repaidFees = repaidFees;
    }

    public void setRepaidUnits(final BigDecimal repaidUnits) {
        this.repaidUnits = repaidUnits;
    }

    public void setRepaidValue(final BigDecimal repaidValue) {
        this.repaidValue = repaidValue;
    }

    public void setRepaymentDate(final Calendar repaymentDate) {
        this.repaymentDate = repaymentDate;
    }

    public void setStatus(final Loan.Status status) {
        this.status = status;
    }

    public void setTotal(final BigDecimal total) {
        this.total = total;
    }

    public void setTransfers(final Collection<Transfer> transfers) {
        this.transfers = transfers;
    }

    public void setValue(final BigDecimal value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return FormatObject.formatVO(this);
    }

}
