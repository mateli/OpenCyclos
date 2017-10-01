package nl.strohalm.cyclos.entities.accounts.loans;

import nl.strohalm.cyclos.entities.utils.Amount;
import nl.strohalm.cyclos.utils.DateHelper;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Calendar;

@Embeddable
public class LoanParametersLight  implements Serializable, Cloneable {

    private static final long serialVersionUID = -2308200961589222771L;

    @Column(name = "loan_type", length = 1)
    protected Loan.Type         type;

    @Column(name = "loan_monthly_interest", precision = 15, scale = 6)
    protected BigDecimal        monthlyInterest;

    @Column(name = "loan_grant_fee_value", precision = 15, scale = 6)
    protected BigDecimal        grantFeeValue;

    @Column(name = "loan_grant_fee_type", length = 1)
    protected Amount.Type       grantFeeType;

    @Column(name = "loan_expiration_fee_value", precision = 15, scale = 6)
    protected BigDecimal        expirationFeeValue;

    @Column(name = "loan_expiration_fee_type", length = 1)
    protected Amount.Type       expirationFeeType;

    @Column(name = "loan_expiration_daily_interest", precision = 15, scale = 6)
    protected BigDecimal        expirationDailyInterest;

    public BigDecimal getExpirationDailyInterest() {
        return expirationDailyInterest;
    }

    public Amount getExpirationDailyInterestAmount() {
        return expirationDailyInterest == null ? null : Amount.percentage(expirationDailyInterest);
    }

    public Amount getExpirationFee() {
        if (expirationFeeValue == null ||expirationFeeType == null) {
            return null;
        }
        return new Amount(expirationFeeValue, expirationFeeType);
    }

    public Amount getGrantFee() {
        if (grantFeeValue == null || grantFeeType == null) {
            return null;
        }
        return new Amount(grantFeeValue, grantFeeType);
    }

    public BigDecimal getMonthlyInterest() {
        return monthlyInterest;
    }

    public Loan.Type getType() {
        return type;
    }

    public void setExpirationDailyInterest(final BigDecimal expirationDailyInterests) {
        expirationDailyInterest = expirationDailyInterests;
    }

    public void setExpirationFee(final Amount expirationFee) {
        if (expirationFee == null) {
            expirationFeeValue = null;
            expirationFeeType = null;
        } else {
            expirationFeeValue = expirationFee.getValue();
            expirationFeeType = expirationFee.getType();
        }
    }

    public void setGrantFee(final Amount grantFee) {
        if (grantFee == null) {
            grantFeeValue = null;
            grantFeeType = null;
        } else {
            grantFeeValue = grantFee.getValue();
            grantFeeType = grantFee.getType();
        }
    }

    public void setMonthlyInterest(final BigDecimal monthlyInterests) {
        monthlyInterest = monthlyInterests;
    }

    public void setType(final Loan.Type type) {
        this.type = type;
    }

    /**
     * Calculates the expiration fee to the given amount
     * @return 0 if no grant fee, the applied grant fee otherwise (ie: if grant fee = 3%, returns 30 for amount = 1000)
     */
    public BigDecimal calculateGrantFee(final BigDecimal amount) {
        if (getGrantFee() == null || getGrantFee().getValue() == null || amount.compareTo(BigDecimal.ZERO) != 1) {
            return BigDecimal.ZERO;
        }
        return getGrantFee().apply(amount);
    }

    public Amount getMonthlyInterestAmount() {
        return monthlyInterest == null ? null : Amount.percentage(monthlyInterest);
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
        if (getExpirationFee() != null && getExpirationFee().getValue() != null) {
            if (diff > 0) {
                return getExpirationFee().apply(amount);
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


    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof LoanParameters)) {
            return false;
        }
        final LoanParameters lp = (LoanParameters) obj;
        final EqualsBuilder eb = new EqualsBuilder();
        eb.append(type, lp.type);
        eb.append(getGrantFee(), lp.getGrantFee());
        eb.append(monthlyInterest, lp.monthlyInterest);
        eb.append(getExpirationFee(), lp.getExpirationFee());
        eb.append(expirationDailyInterest, lp.expirationDailyInterest);
        return eb.isEquals();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder hcb = new HashCodeBuilder();
        hcb.append(type);
        hcb.append(getGrantFee());
        hcb.append(monthlyInterest);
        hcb.append(getExpirationFee());
        hcb.append(expirationDailyInterest);
        return hcb.toHashCode();
    }

    @Override
    public LoanParametersLight clone() {
        try {
            return (LoanParametersLight) super.clone();
        } catch (final CloneNotSupportedException e) {
            return null;
        }
    }
}
