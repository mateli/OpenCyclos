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
import nl.strohalm.cyclos.services.accounts.guarantees.GuaranteeTypeFeeVO;
import nl.strohalm.cyclos.utils.StringValuedEnum;
import nl.strohalm.cyclos.utils.TimePeriod;

/**
 * A guarantee type
 * @author Jefferson Magno
 */
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

    private String             name;
    private String             description;
    private Model              model;
    private AuthorizedBy       authorizedBy;
    private boolean            enabled;
    // TODO: the loan re-payment's setup will be supported in a second stage
    // private boolean allowLoanPaymentSetup;
    private TimePeriod         pendingGuaranteeExpiration;
    private TimePeriod         paymentObligationPeriod;
    private Currency           currency;
    private GuaranteeTypeFeeVO creditFee;
    private GuaranteeTypeFeeVO issueFee;
    private FeePayer           creditFeePayer;
    private FeePayer           issueFeePayer;
    private TransferType       loanTransferType;
    private TransferType       creditFeeTransferType;
    private TransferType       issueFeeTransferType;
    private TransferType       forwardTransferType;

    public AuthorizedBy getAuthorizedBy() {
        return authorizedBy;
    }

    public GuaranteeTypeFeeVO getCreditFee() {
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

    public GuaranteeTypeFeeVO getIssueFee() {
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

    public void setCreditFee(final GuaranteeTypeFeeVO creditFee) {
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

    public void setIssueFee(final GuaranteeTypeFeeVO issueFee) {
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
