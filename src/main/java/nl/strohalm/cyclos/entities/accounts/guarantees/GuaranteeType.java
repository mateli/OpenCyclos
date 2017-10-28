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
package nl.strohalm.cyclos.entities.accounts.guarantees;

import nl.strohalm.cyclos.entities.Entity;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.Currency;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferType;
import nl.strohalm.cyclos.entities.utils.TimePeriod;
import nl.strohalm.cyclos.utils.StringValuedEnum;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * A guarantee type
 * @author Jefferson Magno
 */
@Cacheable
@Table(name = "guarantee_types")
@javax.persistence.Entity
public class GuaranteeType extends Entity {

    public static enum AuthorizedBy implements StringValuedEnum {
        ISSUER("I"), ADMIN("A"), BOTH("B"), NONE("N");
        private final String value;

        private AuthorizedBy(final String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public static enum FeePayer implements StringValuedEnum {
        BUYER("B"), SELLER("S");
        private final String value;

        private FeePayer(final String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public static enum FeeType implements StringValuedEnum {
        FIXED("F"), PERCENTAGE("P"), VARIABLE_ACCORDING_TO_TIME("V");
        private final String value;

        private FeeType(final String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

    }

    public static enum Model implements StringValuedEnum {
        WITH_PAYMENT_OBLIGATION("PO"), WITH_BUYER_ONLY("BO"), WITH_BUYER_AND_SELLER("BS");
        private final String value;

        private Model(final String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public static enum Relationships implements Relationship {
        CURRENCY("currency"), LOAN_TRANSFER_TYPE("loanTransferType"), CREDIT_FEE_TRANSFER_TYPE("creditFeeTransferType"), ISSUE_FEE_TRANSFER_TYPE("issueFeeTransferType"), FORWARD_TRANSFER_TYPE("forwardTransferType");
        private final String name;

        private Relationships(final String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    private static final long  serialVersionUID = -8522901316173399683L;

    @Column(name = "name", nullable = false, length = 100)
    private String             name;

    @Column(name = "description", columnDefinition = "text")
    private String             description;

    @Column(name = "model", nullable = false, length = 2)
	private Model              model;

    @Column(name = "authorized_by", nullable = false, length = 1)
	private AuthorizedBy       authorizedBy;

    @Column(name = "enabled", nullable = false)
    private boolean            enabled;

    // TODO: the loan re-payment's setup will be supported in a second stage
    // private boolean allowLoanPaymentSetup;

    @AttributeOverrides({
            @AttributeOverride(name = "number", column=@Column(name="pending_guarantee_expiration_number")),
            @AttributeOverride(name = "field", column=@Column(name="pending_guarantee_expiration_field"))
    })
    @Embedded
	private TimePeriod         pendingGuaranteeExpiration;

    @AttributeOverrides({
            @AttributeOverride(name = "number", column=@Column(name="payment_obligation_period_number")),
            @AttributeOverride(name = "field", column=@Column(name="payment_obligation_period_field"))
    })
    @Embedded
	private TimePeriod         paymentObligationPeriod;

    @ManyToOne
    @JoinColumn(name = "currency_id", nullable = false)
	private Currency           currency;

    @AttributeOverrides({
            @AttributeOverride(name = "fee", column=@Column(name="credit_fee")),
            @AttributeOverride(name = "type", column=@Column(name="credit_fee_type")),
            @AttributeOverride(name = "readonly", column=@Column(name="credit_fee_readonly"))
    })
    @Embedded
	private GuaranteeTypeFee creditFee;

    @AttributeOverrides({
            @AttributeOverride(name = "fee", column=@Column(name="issue_fee")),
            @AttributeOverride(name = "type", column=@Column(name="issue_fee_type")),
            @AttributeOverride(name = "readonly", column=@Column(name="issue_fee_readonly"))
    })
    @Embedded
	private GuaranteeTypeFee issueFee;

    @Column(name = "credit_fee_payer", nullable = false, length = 1)
	private FeePayer           creditFeePayer;

    @Column(name = "issue_fee_payer", nullable = false, length = 1)
	private FeePayer           issueFeePayer;

    @ManyToOne
    @JoinColumn(name = "loan_transfer_type_id", nullable = false)
	private TransferType       loanTransferType;

    @ManyToOne
    @JoinColumn(name = "credit_fee_transfer_type_id")
	private TransferType       creditFeeTransferType;

    @ManyToOne
    @JoinColumn(name = "issue_fee_transfer_type_id")
	private TransferType       issueFeeTransferType;

    @ManyToOne
    @JoinColumn(name = "forward_transfer_type_id")
	private TransferType       forwardTransferType;

    protected GuaranteeType() {
	}

	public AuthorizedBy getAuthorizedBy() {
        return authorizedBy;
    }

    public GuaranteeTypeFee getCreditFee() {
        return creditFee;
    }

    public FeePayer getCreditFeePayer() {
        return creditFeePayer;
    }

    public TransferType getCreditFeeTransferType() {
        return creditFeeTransferType;
    }

    public Currency getCurrency() {
        return currency;
    }

    public String getDescription() {
        return description;
    }

    public TransferType getForwardTransferType() {
        return forwardTransferType;
    }

    public GuaranteeTypeFee getIssueFee() {
        return issueFee;
    }

    public FeePayer getIssueFeePayer() {
        return issueFeePayer;
    }

    public TransferType getIssueFeeTransferType() {
        return issueFeeTransferType;
    }

    public TransferType getLoanTransferType() {
        return loanTransferType;
    }

    public Model getModel() {
        return model;
    }

    public String getName() {
        return name;
    }

    public TimePeriod getPaymentObligationPeriod() {
        return paymentObligationPeriod;
    }

    public TimePeriod getPendingGuaranteeExpiration() {
        return pendingGuaranteeExpiration;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setAuthorizedBy(final AuthorizedBy authorizedBy) {
        this.authorizedBy = authorizedBy;
    }

    public void setCreditFee(final GuaranteeTypeFee creditFee) {
        this.creditFee = creditFee;
    }

    public void setCreditFeePayer(final FeePayer creditFeePayer) {
        this.creditFeePayer = creditFeePayer;
    }

    public void setCreditFeeTransferType(final TransferType usageFeeTransferType) {
        creditFeeTransferType = usageFeeTransferType;
    }

    public void setCurrency(final Currency currency) {
        this.currency = currency;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public void setEnabled(final boolean enabled) {
        this.enabled = enabled;
    }

    public void setForwardTransferType(final TransferType forwardTransferType) {
        this.forwardTransferType = forwardTransferType;
    }

    public void setIssueFee(final GuaranteeTypeFee issueFee) {
        this.issueFee = issueFee;
    }

    public void setIssueFeePayer(final FeePayer issueFeePayer) {
        this.issueFeePayer = issueFeePayer;
    }

    public void setIssueFeeTransferType(final TransferType issueFeeTransferType) {
        this.issueFeeTransferType = issueFeeTransferType;
    }

    public void setLoanTransferType(final TransferType loanTransferType) {
        this.loanTransferType = loanTransferType;
    }

    public void setModel(final Model model) {
        this.model = model;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setPaymentObligationPeriod(final TimePeriod paymentObligationPeriod) {
        this.paymentObligationPeriod = paymentObligationPeriod;
    }

    public void setPendingGuaranteeExpiration(final TimePeriod pendingGuaranteeExpiration) {
        this.pendingGuaranteeExpiration = pendingGuaranteeExpiration;
    }

    @Override
    public String toString() {
        return getId() + " - " + name;
    }

}
