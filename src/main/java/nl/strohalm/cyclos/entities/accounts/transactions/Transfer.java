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
package nl.strohalm.cyclos.entities.accounts.transactions;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Collection;
import java.util.Map;

import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.Account;
import nl.strohalm.cyclos.entities.accounts.AccountOwner;
import nl.strohalm.cyclos.entities.accounts.Rated;
import nl.strohalm.cyclos.entities.accounts.SystemAccountOwner;
import nl.strohalm.cyclos.entities.accounts.external.ExternalTransfer;
import nl.strohalm.cyclos.entities.accounts.fees.account.AccountFeeLog;
import nl.strohalm.cyclos.entities.accounts.fees.transaction.TransactionFee;
import nl.strohalm.cyclos.entities.accounts.loans.LoanPayment;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.brokerings.BrokerCommissionContract;
import nl.strohalm.cyclos.entities.settings.LocalSettings;

import org.apache.commons.lang.ObjectUtils;

/**
 * A unit transfer between two accounts. A transaction may be performed with several transfers (ex: additional fees)
 * @author luis
 */
public class Transfer extends Payment implements Rated {

    public static enum Relationships implements Relationship {
        ACCOUNT_FEE_LOG("accountFeeLog"), CHILDREN("children"),
        CHARGED_BACK_BY("chargedBackBy"), LOAN_PAYMENT("loanPayment"),
        PARENT("parent"), CHARGEBACK_OF("chargebackOf"),
        TRANSACTION_FEE("transactionFee"), RECEIVER("receiver"),
        EXTERNAL_TRANSFER("externalTransfer"), AUTHORIZATIONS("authorizations"),
        NEXT_AUTHORIZATION_LEVEL("nextAuthorizationLevel"), SCHEDULED_PAYMENT("scheduledPayment");

        private final String name;

        private Relationships(final String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }
    }

    private static final long                 serialVersionUID = -4749387454425166686L;
    private AccountFeeLog                     accountFeeLog;
    private Collection<Transfer>              children;
    private Transfer                          chargedBackBy;
    private String                            transactionNumber;
    private String                            traceNumber;
    private Long                              clientId;
    private String                            traceData;
    private LoanPayment                       loanPayment;
    private Transfer                          parent;
    private Transfer                          chargebackOf;
    private TransactionFee                    transactionFee;
    private Element                           receiver;
    private ExternalTransfer                  externalTransfer;
    private Collection<TransferAuthorization> authorizations;
    private AuthorizationLevel                nextAuthorizationLevel;
    private ScheduledPayment                  scheduledPayment;
    private BrokerCommissionContract          brokerCommissionContract;
    private Calendar                          expirationDate;
    private Calendar                          emissionDate;
    private BigDecimal                        iRate;
    private transient Transfer                root;

    public AccountFeeLog getAccountFeeLog() {
        return accountFeeLog;
    }

    /**
     * Returns the amount as a positive number, even when it's negative (i.e. chargeback)
     */
    public BigDecimal getActualAmount() {
        final BigDecimal amount = getAmount();
        return amount == null ? null : amount.abs();
    }

    /**
     * Returns the process date if not null, date otherwise
     */
    public Calendar getActualDate() {
        return getProcessDate() == null ? getDate() : getProcessDate();
    }

    /**
     * When the amount is negative (i.e. chargeback), returns the to account instead
     */
    public Account getActualFrom() {
        final BigDecimal amount = getAmount();
        return amount == null || amount.compareTo(BigDecimal.ZERO) >= 0 ? getFrom() : getTo();
    }

    /**
     * When the amount is negative (i.e. chargeback), returns the to account owner instead
     */
    public AccountOwner getActualFromOwner() {
        return getActualFrom().getOwner();
    }

    /**
     * When the amount is negative (i.e. chargeback), returns the from account instead
     */
    public Account getActualTo() {
        final BigDecimal amount = getAmount();
        return amount == null || amount.compareTo(BigDecimal.ZERO) >= 0 ? getTo() : getFrom();
    }

    /**
     * When the amount is negative (i.e. chargeback), returns the from account owner instead
     */
    public AccountOwner getActualToOwner() {
        return getActualTo().getOwner();
    }

    public Collection<TransferAuthorization> getAuthorizations() {
        return authorizations;
    }

    public BrokerCommissionContract getBrokerCommissionContract() {
        return brokerCommissionContract;
    }

    public Transfer getChargebackOf() {
        return chargebackOf;
    }

    public Transfer getChargedBackBy() {
        return chargedBackBy;
    }

    public Collection<Transfer> getChildren() {
        return children;
    }

    /**
     * @return the (optional) client id that generates the transfer
     */
    public Long getClientId() {
        return clientId;
    }

    @Override
    public Calendar getEmissionDate() {
        return emissionDate;
    }

    @Override
    public Calendar getExpirationDate() {
        return expirationDate;
    }

    public ExternalTransfer getExternalTransfer() {
        return externalTransfer;
    }

    @Override
    public BigDecimal getiRate() {
        return iRate;
    }

    public LoanPayment getLoanPayment() {
        return loanPayment;
    }

    @Override
    public Nature getNature() {
        return Nature.TRANSFER;
    }

    public AuthorizationLevel getNextAuthorizationLevel() {
        return nextAuthorizationLevel;
    }

    public Transfer getParent() {
        return parent;
    }

    public Element getReceiver() {
        return receiver;
    }

    public Transfer getRootTransfer() {
        if (root == null) {
            Transfer topMost = this;
            while (topMost.getParent() != null) {
                topMost = topMost.getParent();
            }
            root = topMost;
        }

        return root;
    }

    public ScheduledPayment getScheduledPayment() {
        return scheduledPayment;
    }

    /**
     * Optional.
     * @returns the data set by the client making a payment.<br>
     * As an example: it could be used to link a payment with its notifications (payment received).<br>
     * It depends on the client side then there is no guarantee of uniqueness between different transfer.<br>
     * Note: It has nothing to do with the (unique) pair <traceNumber, clientId> (used to query transactions by those values).
     */
    public String getTraceData() {
        return traceData;
    }

    /**
     * @return the (optional) trace number generated by a client
     */
    public String getTraceNumber() {
        return traceNumber;
    }

    public TransactionFee getTransactionFee() {
        return transactionFee;
    }

    public String getTransactionNumber() {
        return transactionNumber;
    }

    /**
     * When the amount is negative (i.e. chargeback), returns whether the to account is a system account
     */
    public boolean isActuallyFromSystem() {
        return getActualFromOwner() instanceof SystemAccountOwner;
    }

    /**
     * When the amount is negative (i.e. chargeback), returns whether the from account is a system account
     */
    public boolean isActuallyToSystem() {
        return getActualToOwner() instanceof SystemAccountOwner;
    }

    public boolean isProcessedAtDifferentDate() {
        return !ObjectUtils.equals(getDate(), getProcessDate());
    }

    public boolean isRoot() {
        return parent == null;
    }

    public void setAccountFeeLog(final AccountFeeLog feeLog) {
        accountFeeLog = feeLog;
    }

    public void setAuthorizations(final Collection<TransferAuthorization> authorizations) {
        this.authorizations = authorizations;
    }

    public void setBrokerCommissionContract(final BrokerCommissionContract brokerCommissionContract) {
        this.brokerCommissionContract = brokerCommissionContract;
    }

    public void setChargebackOf(final Transfer chargebackOf) {
        this.chargebackOf = chargebackOf;
    }

    public void setChargedBackBy(final Transfer chargedBackBy) {
        this.chargedBackBy = chargedBackBy;
    }

    public void setChildren(final Collection<Transfer> children) {
        this.children = children;
    }

    public void setClientId(final Long clientId) {
        this.clientId = clientId;
    }

    public void setEmissionDate(final Calendar emissionDate) {
        this.emissionDate = emissionDate;
    }

    public void setExpirationDate(final Calendar expirationDate) {
        this.expirationDate = expirationDate;
    }

    public void setExternalTransfer(final ExternalTransfer externalTransfer) {
        this.externalTransfer = externalTransfer;
    }

    public void setiRate(final BigDecimal iRate) {
        this.iRate = iRate;
    }

    public void setLoanPayment(final LoanPayment loanComponent) {
        loanPayment = loanComponent;
    }

    public void setNextAuthorizationLevel(final AuthorizationLevel nextAuthorizationLevel) {
        this.nextAuthorizationLevel = nextAuthorizationLevel;
    }

    public void setParent(final Transfer parent) {
        this.parent = parent;
        root = null;
    }

    public void setReceiver(final Element receiver) {
        this.receiver = receiver;
    }

    public void setScheduledPayment(final ScheduledPayment scheduledPayment) {
        this.scheduledPayment = scheduledPayment;
    }

    public void setTraceData(final String traceData) {
        this.traceData = traceData;
    }

    public void setTraceNumber(final String traceNumber) {
        this.traceNumber = traceNumber;
    }

    public void setTransactionFee(final TransactionFee fee) {
        transactionFee = fee;
    }

    public void setTransactionNumber(final String transactionNumber) {
        this.transactionNumber = transactionNumber;
    }

    @Override
    protected void appendVariableValues(final Map<String, Object> variables, final LocalSettings localSettings) {
        super.appendVariableValues(variables, localSettings);
        variables.put("transaction_number", getTransactionNumber());
    }

}
