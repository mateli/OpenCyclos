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

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Calendar;

import nl.strohalm.cyclos.entities.accounts.transactions.TransferType;
import nl.strohalm.cyclos.utils.Amount;
import nl.strohalm.cyclos.utils.DateHelper;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Contains parameters on TransferTypes to configure loans
 * @author luis
 */
public class LoanParameters implements Serializable, Cloneable {
    private static final long serialVersionUID = -2308200961589222771L;
    private Loan.Type         type;
    private Integer           repaymentDays;
    private TransferType      repaymentType;
    private BigDecimal        monthlyInterest;
    private TransferType      monthlyInterestRepaymentType;
    private Amount            grantFee;
    private TransferType      grantFeeRepaymentType;
    private Amount            expirationFee;
    private TransferType      expirationFeeRepaymentType;
    private BigDecimal        expirationDailyInterest;
    private TransferType      expirationDailyInterestRepaymentType;
    private TransferType      originalTransferType;

    /**
     * Calculates the grant fee to the given amount
     * @return 0 if no grant fee, the applied grant fee otherwise (ie: if grant fee = 3%, returns 30 for amount = 1000)
     */
    public BigDecimal calculateGrantFee(final BigDecimal amount) {
        if (grantFee == null || grantFee.getValue() == null || amount.compareTo(BigDecimal.ZERO) != 1) {
            return BigDecimal.ZERO;
        }
        return grantFee.apply(amount);
    }

    /**
     * Calculates the total loan value for the given parameters
     */
    public BigDecimal calculateLoanTotal(final BigDecimal amount, final int paymentCount, final Calendar grantDate, final Calendar firstExpirationDate, final MathContext mathContext) {
        final BigDecimal grantFee = calculateGrantFee(amount);
        final BigDecimal monthlyInterests = calculateMonthlyInterests(amount, paymentCount, grantDate, firstExpirationDate, mathContext);
        return amount.add(grantFee).add(monthlyInterests);
    }

    /**
     * Calculates the monthly interests for the given parameters
     * @return 0 if no monthly interests, or the applied interests otherwise (ie: if monthly interests = 1%, paymentCount = 1 and delay = 0, returns
     * 10 for amount = 1000)
     */
    public BigDecimal calculateMonthlyInterests(final BigDecimal amount, final int paymentCount, Calendar grantDate, Calendar firstExpirationDate, final MathContext mathContext) {
        if (monthlyInterest == null || amount.compareTo(BigDecimal.ZERO) != 1 || paymentCount < 1) {
            return BigDecimal.ZERO;
        }
        // Calculate the delay
        final Calendar now = Calendar.getInstance();
        grantDate = grantDate == null ? now : grantDate;
        firstExpirationDate = firstExpirationDate == null ? (Calendar) now.clone() : firstExpirationDate;
        final Calendar shouldBeFirstExpiration = (Calendar) grantDate.clone();
        shouldBeFirstExpiration.add(Calendar.MONTH, 1);
        int delay = DateHelper.daysBetween(shouldBeFirstExpiration, firstExpirationDate);
        if (delay < 0) {
            delay = 0;
        }

        final BigDecimal grantFee = calculateGrantFee(amount);
        final BigDecimal baseAmount = amount.add(grantFee);
        final BigDecimal interests = monthlyInterest.divide(new BigDecimal(100), mathContext);
        final BigDecimal numerator = new BigDecimal(Math.pow(1 + interests.doubleValue(), paymentCount + delay / 30F)).multiply(interests);
        final BigDecimal denominator = new BigDecimal(Math.pow(1 + interests.doubleValue(), paymentCount) - 1);
        final BigDecimal paymentAmount = baseAmount.multiply(numerator).divide(denominator, mathContext);
        final BigDecimal totalAmount = paymentAmount.multiply(new BigDecimal(paymentCount));
        return totalAmount.subtract(baseAmount);
    }

    /**
     * Calculates the expiration fee, if any, for the given loan payment parameters
     * @return 0 if no expiration fee, the applied expiration fee otherwise (ie: if expiration fee = 3%, returns 3 for amount = 100)
     */
    public BigDecimal calculatePaymentExpirationFee(final BigDecimal amount, final int diff) {
        if (expirationFee != null && expirationFee.getValue() != null) {
            if (diff > 0) {
                return expirationFee.apply(amount);
            }
        }
        return BigDecimal.ZERO;
    }

    /**
     * Calculates the expiration daily interest, if any, for the given loan payment parameters
     * @return 0 if no interest, the applied interest otherwise (ie: if expiration daily interest fee = 1%, on 5 days after expiration, returns 1 for
     * amount = 100)
     */
    public BigDecimal calculatePaymentExpirationInterest(final BigDecimal amount, final int diff, final MathContext mathContext) {
        if (expirationDailyInterest != null) {
            if (diff > 0) {
                return expirationDailyInterest.multiply(amount).multiply(new BigDecimal(diff)).divide(new BigDecimal(100), mathContext);
            }
        }
        return BigDecimal.ZERO;
    }

    /**
     * Calculates the total amount of a loan payment on the given date
     */
    public BigDecimal calculatePaymentTotal(final BigDecimal amount, final Calendar date, final Calendar expirationDate, final MathContext mathContext) {
        final int diff = DateHelper.daysBetween(date, expirationDate);
        return amount.add(calculatePaymentExpirationFee(amount, diff)).add(calculatePaymentExpirationInterest(amount, diff, mathContext));
    }

    @Override
    public LoanParameters clone() {
        try {
            return (LoanParameters) super.clone();
        } catch (final CloneNotSupportedException e) {
            return null;
        }
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
        eb.append(grantFee, lp.grantFee);
        eb.append(monthlyInterest, lp.monthlyInterest);
        eb.append(expirationFee, lp.expirationFee);
        eb.append(expirationDailyInterest, lp.expirationDailyInterest);
        eb.append(repaymentDays, lp.repaymentDays);
        return eb.isEquals();
    }

    public BigDecimal getExpirationDailyInterest() {
        return expirationDailyInterest;
    }

    public Amount getExpirationDailyInterestAmount() {
        return expirationDailyInterest == null ? null : Amount.percentage(expirationDailyInterest);
    }

    public TransferType getExpirationDailyInterestRepaymentType() {
        return expirationDailyInterestRepaymentType;
    }

    public Amount getExpirationFee() {
        return expirationFee;
    }

    public TransferType getExpirationFeeRepaymentType() {
        return expirationFeeRepaymentType;
    }

    public Amount getGrantFee() {
        return grantFee;
    }

    public TransferType getGrantFeeRepaymentType() {
        return grantFeeRepaymentType;
    }

    public BigDecimal getMonthlyInterest() {
        return monthlyInterest;
    }

    public Amount getMonthlyInterestAmount() {
        return monthlyInterest == null ? null : Amount.percentage(monthlyInterest);
    }

    public TransferType getMonthlyInterestRepaymentType() {
        return monthlyInterestRepaymentType;
    }

    public TransferType getOriginalTransferType() {
        return originalTransferType;
    }

    public Integer getRepaymentDays() {
        return repaymentDays;
    }

    public TransferType getRepaymentType() {
        return repaymentType;
    }

    public Loan.Type getType() {
        return type;
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder hcb = new HashCodeBuilder();
        hcb.append(type);
        hcb.append(repaymentType);
        hcb.append(grantFee);
        hcb.append(monthlyInterest);
        hcb.append(expirationFee);
        hcb.append(expirationDailyInterest);
        hcb.append(repaymentDays);
        return hcb.toHashCode();
    }

    public void setExpirationDailyInterest(final BigDecimal expirationDailyInterests) {
        expirationDailyInterest = expirationDailyInterests;
    }

    public void setExpirationDailyInterestRepaymentType(final TransferType expirationDailyInterestRepaymentType) {
        this.expirationDailyInterestRepaymentType = expirationDailyInterestRepaymentType;
    }

    public void setExpirationFee(final Amount expirationFee) {
        this.expirationFee = expirationFee;
    }

    public void setExpirationFeeRepaymentType(final TransferType expirationFeeRepaymentType) {
        this.expirationFeeRepaymentType = expirationFeeRepaymentType;
    }

    public void setGrantFee(final Amount grantFee) {
        this.grantFee = grantFee;
    }

    public void setGrantFeeRepaymentType(final TransferType grantFeeRepaymentType) {
        this.grantFeeRepaymentType = grantFeeRepaymentType;
    }

    public void setMonthlyInterest(final BigDecimal monthlyInterests) {
        monthlyInterest = monthlyInterests;
    }

    public void setMonthlyInterestRepaymentType(final TransferType monthlyInterestRepaymentType) {
        this.monthlyInterestRepaymentType = monthlyInterestRepaymentType;
    }

    public void setOriginalTransferType(final TransferType originalTransferType) {
        this.originalTransferType = originalTransferType;
    }

    public void setRepaymentDays(final Integer repaymentDays) {
        this.repaymentDays = repaymentDays;
    }

    public void setRepaymentType(final TransferType repaymentType) {
        this.repaymentType = repaymentType;
    }

    public void setType(final Loan.Type type) {
        this.type = type;
    }

}
