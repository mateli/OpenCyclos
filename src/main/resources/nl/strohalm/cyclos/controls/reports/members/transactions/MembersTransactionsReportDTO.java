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
package nl.strohalm.cyclos.controls.reports.members.transactions;

import java.util.Collection;
import java.util.Map;

import nl.strohalm.cyclos.entities.accounts.AccountType;
import nl.strohalm.cyclos.entities.accounts.transactions.PaymentFilter;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.utils.DataObject;
import nl.strohalm.cyclos.utils.Period;

public class MembersTransactionsReportDTO extends DataObject {

    public static enum DetailsLevel {
        SUMMARY, TRANSACTIONS;
    }

    private static final long                           serialVersionUID = -5856820625601506756L;

    private boolean                                     memberName;
    private boolean                                     brokerUsername;
    private boolean                                     brokerName;
    private Collection<AccountType>                     accountTypes;
    private Collection<MemberGroup>                     memberGroups;
    private Period                                      period;

    private boolean                                     incomingTransactions;
    private boolean                                     outgoingTransactions;
    private boolean                                     includeNoTraders;
    private DetailsLevel                                detailsLevel;
    private Collection<PaymentFilter>                   transactionsPaymentFilters;
    private Map<PaymentFilter, Integer>                 transactionsColSpan;
    private Map<AccountType, Collection<PaymentFilter>> paymentFiltersByAccountType;

    public Collection<AccountType> getAccountTypes() {
        return accountTypes;
    }

    public int getBrokerColSpan() {
        int colspan = 0;
        if (isBrokerName()) {
            colspan++;
        }
        if (isBrokerUsername()) {
            colspan++;
        }
        return colspan;
    }

    public DetailsLevel getDetailsLevel() {
        return detailsLevel;
    }

    public int getMemberColSpan() {
        if (isMemberName()) {
            return 2;
        } else {
            return 1;
        }
    }

    public Collection<MemberGroup> getMemberGroups() {
        return memberGroups;
    }

    public Map<AccountType, Collection<PaymentFilter>> getPaymentFiltersByAccountType() {
        return paymentFiltersByAccountType;
    }

    public Period getPeriod() {
        return period;
    }

    public Map<PaymentFilter, Integer> getTransactionsColSpan() {
        return transactionsColSpan;
    }

    public Collection<PaymentFilter> getTransactionsPaymentFilters() {
        return transactionsPaymentFilters;
    }

    public boolean isBrokerName() {
        return brokerName;
    }

    public boolean isBrokerUsername() {
        return brokerUsername;
    }

    public boolean isDebitsAndCredits() {
        return isIncomingTransactions() && isOutgoingTransactions();
    }

    public boolean isIncludeNoTraders() {
        return includeNoTraders;
    }

    public boolean isIncomingTransactions() {
        return incomingTransactions;
    }

    public boolean isMemberName() {
        return memberName;
    }

    public boolean isOutgoingTransactions() {
        return outgoingTransactions;
    }

    public boolean isTransactions() {
        return isIncomingTransactions() || isOutgoingTransactions();
    }

    public void setAccountTypes(final Collection<AccountType> accountTypes) {
        this.accountTypes = accountTypes;
    }

    public void setBrokerName(final boolean brokerName) {
        this.brokerName = brokerName;
    }

    public void setBrokerUsername(final boolean brokerUsername) {
        this.brokerUsername = brokerUsername;
    }

    public void setDetailsLevel(final DetailsLevel detailsLevel) {
        this.detailsLevel = detailsLevel;
    }

    public void setIncludeNoTraders(final boolean includeNoTraders) {
        this.includeNoTraders = includeNoTraders;
    }

    public void setIncomingTransactions(final boolean incomingTransactions) {
        this.incomingTransactions = incomingTransactions;
    }

    public void setMemberGroups(final Collection<MemberGroup> memberGroups) {
        this.memberGroups = memberGroups;
    }

    public void setMemberName(final boolean memberName) {
        this.memberName = memberName;
    }

    public void setOutgoingTransactions(final boolean outgoingTransactions) {
        this.outgoingTransactions = outgoingTransactions;
    }

    public void setPaymentFiltersByAccountType(final Map<AccountType, Collection<PaymentFilter>> paymentFiltersByAccountType) {
        this.paymentFiltersByAccountType = paymentFiltersByAccountType;
    }

    public void setPeriod(final Period period) {
        this.period = period;
    }

    public void setTransactionsColSpan(final Map<PaymentFilter, Integer> transactionsColSpan) {
        this.transactionsColSpan = transactionsColSpan;
    }

    public void setTransactionsPaymentFilters(final Collection<PaymentFilter> transactionsPaymentFilters) {
        this.transactionsPaymentFilters = transactionsPaymentFilters;
    }

}
