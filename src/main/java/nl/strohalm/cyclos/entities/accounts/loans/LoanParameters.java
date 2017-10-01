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

import nl.strohalm.cyclos.entities.accounts.transactions.TransferType;
import nl.strohalm.cyclos.utils.DateHelper;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Calendar;

/**
 * Contains parameters on TransferTypes to configure loans
 * @author luis
 */
@Embeddable
public class LoanParameters extends LoanParametersLight {

    private static final long serialVersionUID = -2308200961589222771L;

    @Column(name = "loan_repayment_days")
    private Integer           repaymentDays;

    @ManyToOne
    @JoinColumn(name = "loan_repayment_type_id")
    private TransferType      repaymentType;

    @ManyToOne
    @JoinColumn(name = "loan_monthly_interest_type_id")
    private TransferType      monthlyInterestRepaymentType;

    @ManyToOne
    @JoinColumn(name = "loan_grant_fee_type_id")
    private TransferType      grantFeeRepaymentType;

    @ManyToOne
    @JoinColumn(name = "loan_expiration_fee_type_id")
    private TransferType      expirationFeeRepaymentType;

    @ManyToOne
    @JoinColumn(name = "loan_exp_daily_interest_type_id")
    private TransferType      expirationDailyInterestRepaymentType;

    // Commented: no use, and no field in database
//    @ManyToOne
//    private TransferType      originalTransferType;

    /**
     * Calculates the total amount of a loan payment on the given date
     */
    public BigDecimal calculatePaymentTotal(final BigDecimal amount, final Calendar date, final Calendar expirationDate, final MathContext mathContext) {
        final int diff = DateHelper.daysBetween(date, expirationDate);
        return amount.add(calculatePaymentExpirationFee(amount, diff)).add(calculatePaymentExpirationInterest(amount, diff, mathContext));
    }

    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof LoanParameters)) {
            return false;
        }
        final LoanParameters lp = (LoanParameters) obj;
        final EqualsBuilder eb = new EqualsBuilder();
        eb.append(type, lp.type);
        eb.append(repaymentType, lp.repaymentType);
        eb.append(getGrantFee(), lp.getGrantFee());
        eb.append(monthlyInterest, lp.monthlyInterest);
        eb.append(getExpirationFee(), lp.getExpirationFee());
        eb.append(expirationDailyInterest, lp.expirationDailyInterest);
        eb.append(repaymentDays, lp.repaymentDays);
        return eb.isEquals();
    }

    public TransferType getExpirationDailyInterestRepaymentType() {
        return expirationDailyInterestRepaymentType;
    }

    public TransferType getExpirationFeeRepaymentType() {
        return expirationFeeRepaymentType;
    }

    public TransferType getGrantFeeRepaymentType() {
        return grantFeeRepaymentType;
    }

    public TransferType getMonthlyInterestRepaymentType() {
        return monthlyInterestRepaymentType;
    }

    public Integer getRepaymentDays() {
        return repaymentDays;
    }

    public TransferType getRepaymentType() {
        return repaymentType;
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder hcb = new HashCodeBuilder();
        hcb.append(type);
        hcb.append(repaymentType);
        hcb.append(getGrantFee());
        hcb.append(monthlyInterest);
        hcb.append(getExpirationFee());
        hcb.append(expirationDailyInterest);
        hcb.append(repaymentDays);
        return hcb.toHashCode();
    }

    public void setExpirationDailyInterestRepaymentType(final TransferType expirationDailyInterestRepaymentType) {
        this.expirationDailyInterestRepaymentType = expirationDailyInterestRepaymentType;
    }

    public void setExpirationFeeRepaymentType(final TransferType expirationFeeRepaymentType) {
        this.expirationFeeRepaymentType = expirationFeeRepaymentType;
    }

    public void setGrantFeeRepaymentType(final TransferType grantFeeRepaymentType) {
        this.grantFeeRepaymentType = grantFeeRepaymentType;
    }

    public void setMonthlyInterestRepaymentType(final TransferType monthlyInterestRepaymentType) {
        this.monthlyInterestRepaymentType = monthlyInterestRepaymentType;
    }

    public void setRepaymentDays(final Integer repaymentDays) {
        this.repaymentDays = repaymentDays;
    }

    public void setRepaymentType(final TransferType repaymentType) {
        this.repaymentType = repaymentType;
    }

}
