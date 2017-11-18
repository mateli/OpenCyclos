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

import nl.strohalm.cyclos.entities.Entity;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.transactions.Payment;
import nl.strohalm.cyclos.entities.accounts.transactions.Transfer;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferType;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.utils.FormatObject;
import nl.strohalm.cyclos.utils.StringValuedEnum;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * A loan generates a transfer and one or more loan payments
 * @author luis
 */
@Table(name = "loans")
@javax.persistence.Entity
public class Loan extends Entity {

    public static enum Relationships implements Relationship {
        PAYMENTS("payments"), LOAN_GROUP("loanGroup"), TO_MEMBERS("toMembers"), TRANSFER("transfer"), REPAYMENT_TYPE("parameters.repaymentType");
        private final String name;

        private Relationships(final String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }
    }

    public static enum Status implements StringValuedEnum {
        OPEN("O"), CLOSED("C"), PENDING_AUTHORIZATION("P"), AUTHORIZATION_DENIED("D");
        private final String value;

        private Status(final String value) {
            this.value = value;
        }

        @Override
        public String getValue() {
            return value;
        }

        public boolean isClosed() {
            return this == CLOSED;
        }

        public boolean isRelatedToAuthorization() {
            return this == PENDING_AUTHORIZATION || this == AUTHORIZATION_DENIED;
        }
    }

    public static enum Type implements StringValuedEnum {
        SINGLE_PAYMENT("S"), MULTI_PAYMENT("M"), WITH_INTEREST("I");
        private final String value;

        private Type(final String value) {
            this.value = value;
        }

        public boolean allowsPartialRepayments() {
            return this != WITH_INTEREST;
        }

        @Override
        public String getValue() {
            return value;
        }
    }

    private static final long  serialVersionUID = 7890624598546777599L;

    @OneToMany(mappedBy = "loan", cascade = CascadeType.REMOVE)
    @OrderBy("index")
	private List<LoanPayment>  payments;

    @ManyToOne
    @JoinColumn(name = "loan_group_id")
	private LoanGroup          loanGroup;

    @AttributeOverrides({
            @AttributeOverride(name = "type", column=@Column(name="type")),
            @AttributeOverride(name = "monthlyInterest", column=@Column(name="monthly_interest")),
            @AttributeOverride(name = "grantFeeValue", column=@Column(name="grant_fee_value")),
            @AttributeOverride(name = "grantFeeType", column=@Column(name="grant_fee_type")),
            @AttributeOverride(name = "expirationFeeValue", column=@Column(name="expiration_fee_value")),
            @AttributeOverride(name = "expirationFeeType", column=@Column(name="expiration_fee_type", length = 1)),
            @AttributeOverride(name = "expirationDailyInterest", column=@Column(name="expiration_daily_interest")),
    })
    @Embedded
	private LoanParametersLight     parameters;

    @ManyToMany
    @JoinTable(name = "members_loans",
            joinColumns = @JoinColumn(name = "loan_id"),
            inverseJoinColumns = @JoinColumn(name = "member_id"))
	private Collection<Member> toMembers;

    @Column(name = "total_amount", nullable = false, precision = 15, scale = 6)
    private BigDecimal         totalAmount;

    @ManyToOne
    @JoinColumn(name = "transfer_id", nullable = false)
	private Transfer           transfer;

	public BigDecimal getAmount() {
        return transfer == null ? null : transfer.getAmount();
    }

    public int getClosedPaymentsCount() {
        int count = 0;
        for (final LoanPayment payment : payments) {
            if (payment.getStatus().isClosed()) {
                count++;
            }
        }
        return count;
    }

    public Calendar getExpirationDate() {
        LoanPayment payment = getFirstOpenPayment();
        if (payment == null) {
            payment = payments == null || payments.isEmpty() ? null : payments.get(payments.size() - 1);
        }
        return payment == null ? null : payment.getExpirationDate();
    }

    public LoanPayment getFirstOpenPayment() {
        if (payments != null) {
            for (final LoanPayment payment : payments) {
                if (payment.getStatus().isOpen()) {
                    return payment;
                }
            }
        }
        return null;
    }

    public LoanPayment getFirstPaymentWithStatus(final LoanPayment.Status status) {
        if (payments != null) {
            for (final LoanPayment payment : payments) {
                if (status == payment.getStatus()) {
                    return payment;
                }
            }
        }
        return null;
    }

    public Calendar getGrantDate() {
        return transfer == null ? null : transfer.getProcessDate();
    }

    public LoanGroup getLoanGroup() {
        return loanGroup;
    }

    public Member getMember() {
        try {
            return (Member) transfer.getTo().getOwner();
        } catch (final NullPointerException e) {
            return null;
        }
    }

    public LoanParametersLight getParameters() {
        return parameters;
    }

    public int getPaymentCount() {
        return payments == null ? 0 : payments.size();
    }

    public List<LoanPayment> getPayments() {
        return payments;
    }

    public BigDecimal getRemainingAmount() {
        return totalAmount.subtract(getRepaidAmount());
    }

    public BigDecimal getRepaidAmount() {
        BigDecimal total = BigDecimal.ZERO;
        if (payments != null) {
            for (final LoanPayment payment : payments) {
                final LoanPayment.Status status = payment.getStatus();
                if (status.isClosed() && status != LoanPayment.Status.UNRECOVERABLE) {
                    total = total.add(payment.getAmount());
                } else {
                    total = total.add(payment.getRepaidAmount());
                }
            }
        }
        return total;
    }

    public Calendar getRepaymentDate() {
        if (payments == null || payments.isEmpty()) {
            return null;
        }
        final LoanPayment payment = payments.get(payments.size() - 1);
        return payment.getStatus().isClosed() ? payment.getRepaymentDate() : null;
    }

    public Status getStatus() {
        if (transfer.getStatus() == Payment.Status.PENDING) {
            return Status.PENDING_AUTHORIZATION;
        }
        if (transfer.getStatus() == Payment.Status.DENIED) {
            return Status.AUTHORIZATION_DENIED;
        }
        for (final LoanPayment payment : getPayments()) {
            if (payment.getStatus().isOpen()) {
                return Status.OPEN;
            }
        }
        return Status.CLOSED;
    }

    public Collection<Member> getToMembers() {
        return toMembers;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public Transfer getTransfer() {
        return transfer;
    }

    public TransferType getTransferType() {
        return transfer == null ? null : transfer.getType();
    }

    public boolean isToGroup() {
        return loanGroup != null;
    }

    public void setLoanGroup(final LoanGroup loanGroup) {
        this.loanGroup = loanGroup;
    }

    public void setParameters(final LoanParametersLight parameters) {
        this.parameters = parameters;
    }

    public void setPayments(final List<LoanPayment> components) {
        payments = components;
    }

    public void setToMembers(final Collection<Member> toMembers) {
        this.toMembers = toMembers;
    }

    public void setTotalAmount(final BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public void setTransfer(final Transfer transfer) {
        this.transfer = transfer;
    }

    @Override
    public String toString() {
        return getId() + " - amount: " + FormatObject.formatObject(getAmount()) + ", to: " + getMember() + ", type: " + getTransferType();
    }

    @Override
    protected void appendVariableValues(final Map<String, Object> variables, final LocalSettings localSettings) {
        variables.put("grant_date", localSettings.getDateConverter().toString(transfer.getDate()));
        variables.put("amount", localSettings.getUnitsConverter(transfer.getTo().getType().getCurrency().getPattern()).toString(transfer.getAmount()));
    }

}
