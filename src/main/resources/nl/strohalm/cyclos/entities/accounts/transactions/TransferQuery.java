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

import java.util.Collection;
import java.util.Collections;

import nl.strohalm.cyclos.entities.accounts.AccountOwner;
import nl.strohalm.cyclos.entities.accounts.AccountType;
import nl.strohalm.cyclos.entities.accounts.loans.LoanPayment;
import nl.strohalm.cyclos.entities.customization.fields.PaymentCustomFieldValue;
import nl.strohalm.cyclos.entities.groups.GroupFilter;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.utils.Period;
import nl.strohalm.cyclos.utils.query.QueryParameters;

import org.apache.commons.collections.CollectionUtils;

/**
 * Contains data for searching the account history
 * @author luis, jefferson
 */
public class TransferQuery extends QueryParameters {

    private static final long                   serialVersionUID = 2190013517134381440L;
    private String                              description;
    private LoanPayment                         loanPayment;
    private Member                              member;
    private Collection<MemberGroup>             groups;
    private Collection<GroupFilter>             groupFilters;
    private Element                             by;
    private AccountOwner                        owner;
    private AccountOwner                        fromAccountOwner;
    private AccountOwner                        toAccountOwner;
    private Transfer                            parent;
    private Collection<PaymentFilter>           paymentFilters;
    private TransferType                        transferType;
    private TransferType                        excludeTransferType;
    private Period                              period;
    private AccountType                         type;
    private AccountType                         fromAccountType;
    private AccountType                         toAccountType;
    private String                              transactionNumber;
    private boolean                             reverseOrder;
    private Boolean                             conciliated;
    private boolean                             rootOnly;
    private Transfer.Status                     status           = Transfer.Status.PROCESSED;
    private Boolean                             requiresAuthorization;
    private boolean                             unordered;
    private Collection<PaymentCustomFieldValue> customValues;
    private Boolean                             loanTransfer;

    public Element getBy() {
        return by;
    }

    public Boolean getConciliated() {
        return conciliated;
    }

    public Collection<PaymentCustomFieldValue> getCustomValues() {
        return customValues;
    }

    public String getDescription() {
        return description;
    }

    public TransferType getExcludeTransferType() {
        return excludeTransferType;
    }

    public AccountOwner getFromAccountOwner() {
        return fromAccountOwner;
    }

    public AccountType getFromAccountType() {
        return fromAccountType;
    }

    public Collection<GroupFilter> getGroupFilters() {
        return groupFilters;
    }

    public Collection<MemberGroup> getGroups() {
        return groups;
    }

    public LoanPayment getLoanPayment() {
        return loanPayment;
    }

    public Boolean getLoanTransfer() {
        return loanTransfer;
    }

    public Member getMember() {
        return member;
    }

    public AccountOwner getOwner() {
        return owner;
    }

    public Transfer getParent() {
        return parent;
    }

    public PaymentFilter getPaymentFilter() {
        return CollectionUtils.isEmpty(paymentFilters) ? null : paymentFilters.iterator().next();
    }

    public Collection<PaymentFilter> getPaymentFilters() {
        return paymentFilters;
    }

    public Period getPeriod() {
        return period;
    }

    public Boolean getRequiresAuthorization() {
        return requiresAuthorization;
    }

    public Transfer.Status getStatus() {
        return status;
    }

    public AccountOwner getToAccountOwner() {
        return toAccountOwner;
    }

    public AccountType getToAccountType() {
        return toAccountType;
    }

    public String getTransactionNumber() {
        return transactionNumber;
    }

    public TransferType getTransferType() {
        return transferType;
    }

    public AccountType getType() {
        return type;
    }

    public boolean isReverseOrder() {
        return reverseOrder;
    }

    public boolean isRootOnly() {
        return rootOnly;
    }

    public boolean isUnordered() {
        return unordered;
    }

    public void setBy(final Element by) {
        this.by = by;
    }

    public void setConciliated(final Boolean conciliated) {
        this.conciliated = conciliated;
    }

    public void setCustomValues(final Collection<PaymentCustomFieldValue> customValues) {
        this.customValues = customValues;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public void setExcludeTransferType(final TransferType excludeTransferType) {
        this.excludeTransferType = excludeTransferType;
    }

    public void setFromAccountOwner(final AccountOwner fromAccountOwner) {
        this.fromAccountOwner = fromAccountOwner;
    }

    public void setFromAccountType(final AccountType fromAccountType) {
        this.fromAccountType = fromAccountType;
    }

    public void setGroupFilters(final Collection<GroupFilter> groupFilters) {
        this.groupFilters = groupFilters;
    }

    public void setGroups(final Collection<MemberGroup> groups) {
        this.groups = groups;
    }

    public void setLoanPayment(final LoanPayment loanPayment) {
        this.loanPayment = loanPayment;
    }

    public void setLoanTransfer(final Boolean loanTransfer) {
        this.loanTransfer = loanTransfer;
    }

    public void setMember(final Member member) {
        this.member = member;
    }

    public void setOwner(final AccountOwner owner) {
        this.owner = owner;
    }

    public void setParent(final Transfer parent) {
        this.parent = parent;
    }

    public void setPaymentFilter(final PaymentFilter paymentFilter) {
        paymentFilters = (paymentFilter == null) ? null : Collections.singletonList(paymentFilter);
    }

    public void setPaymentFilters(final Collection<PaymentFilter> paymentFilters) {
        this.paymentFilters = paymentFilters;
    }

    public void setPeriod(final Period period) {
        this.period = period;
    }

    public void setRequiresAuthorization(final Boolean requiresAuthorization) {
        this.requiresAuthorization = requiresAuthorization;
    }

    public void setReverseOrder(final boolean reverseOrder) {
        this.reverseOrder = reverseOrder;
    }

    public void setRootOnly(final boolean rootOnly) {
        this.rootOnly = rootOnly;
    }

    public void setStatus(final Transfer.Status status) {
        this.status = status;
    }

    public void setToAccountOwner(final AccountOwner toAccountOwner) {
        this.toAccountOwner = toAccountOwner;
    }

    public void setToAccountType(final AccountType toAccountType) {
        this.toAccountType = toAccountType;
    }

    public void setTransactionNumber(final String transactionNumber) {
        this.transactionNumber = transactionNumber;
    }

    public void setTransferType(final TransferType transferType) {
        this.transferType = transferType;
    }

    public void setType(final AccountType type) {
        this.type = type;
    }

    public void setUnordered(final boolean unordered) {
        this.unordered = unordered;
    }

}
