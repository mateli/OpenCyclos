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

import java.util.Collection;

import nl.strohalm.cyclos.entities.accounts.AccountType;
import nl.strohalm.cyclos.entities.accounts.transactions.Transfer;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferType;
import nl.strohalm.cyclos.entities.customization.fields.MemberCustomFieldValue;
import nl.strohalm.cyclos.entities.customization.fields.PaymentCustomFieldValue;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.utils.query.QueryParameters;

/**
 * Base parameters for loan related queries
 * @author luis
 */
public abstract class BaseLoanQuery extends QueryParameters {

    private static final long                   serialVersionUID = -1699206745935659180L;
    private Member                              broker;
    private Collection<PaymentCustomFieldValue> loanCustomValues;
    private LoanGroup                           loanGroup;
    private Member                              member;
    private Collection<MemberGroup>             groups;
    private AccountType                         accountType;
    private Collection<MemberCustomFieldValue>  memberCustomValues;
    private TransferType                        transferType;
    private String                              transactionNumber;
    private Transfer.Status                     transferStatus   = Transfer.Status.PROCESSED;

    public AccountType getAccountType() {
        return accountType;
    }

    public Member getBroker() {
        return broker;
    }

    public Collection<MemberGroup> getGroups() {
        return groups;
    }

    public Collection<PaymentCustomFieldValue> getLoanCustomValues() {
        return loanCustomValues;
    }

    public LoanGroup getLoanGroup() {
        return loanGroup;
    }

    public Member getMember() {
        return member;
    }

    public Collection<MemberCustomFieldValue> getMemberCustomValues() {
        return memberCustomValues;
    }

    public String getTransactionNumber() {
        return transactionNumber;
    }

    public Transfer.Status getTransferStatus() {
        return transferStatus;
    }

    public TransferType getTransferType() {
        return transferType;
    }

    public void setAccountType(final AccountType accountType) {
        this.accountType = accountType;
    }

    public void setBroker(final Member broker) {
        this.broker = broker;
    }

    public void setGroups(final Collection<MemberGroup> groups) {
        this.groups = groups;
    }

    public void setLoanCustomValues(final Collection<PaymentCustomFieldValue> loanCustomValues) {
        this.loanCustomValues = loanCustomValues;
    }

    public void setLoanGroup(final LoanGroup loanGroup) {
        this.loanGroup = loanGroup;
    }

    public void setMember(final Member member) {
        this.member = member;
    }

    public void setMemberCustomValues(final Collection<MemberCustomFieldValue> memberCustomValues) {
        this.memberCustomValues = memberCustomValues;
    }

    public void setTransactionNumber(final String transactionNumber) {
        this.transactionNumber = transactionNumber;
    }

    public void setTransferStatus(final Transfer.Status transferStatus) {
        this.transferStatus = transferStatus;
    }

    public void setTransferType(final TransferType transferType) {
        this.transferType = transferType;
    }

}
