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

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import nl.strohalm.cyclos.entities.accounts.AccountOwner;
import nl.strohalm.cyclos.entities.accounts.AccountType;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.utils.Period;
import nl.strohalm.cyclos.utils.query.QueryParameters;

public class ScheduledPaymentQuery extends QueryParameters {

    public static enum SearchType {
        OUTGOING, INCOMING;
    }

    public static enum StatusGroup {
        OPEN, CLOSED_WITHOUT_ERRORS, CLOSED_WITH_ERRORS;

        public List<Payment.Status> getStatusList() {
            switch (this) {
                case OPEN:
                    return Arrays.asList(Payment.Status.SCHEDULED, Payment.Status.PENDING, Payment.Status.BLOCKED, Payment.Status.FAILED);
                case CLOSED_WITHOUT_ERRORS:
                    return Arrays.asList(Payment.Status.PROCESSED);
                default: // CLOSED_WITH_ERRORS
                    return Arrays.asList(Payment.Status.CANCELED, Payment.Status.DENIED);
            }
        }
    }

    private static final long          serialVersionUID = -8537384458915898501L;
    private AccountOwner               owner;
    private AccountType                accountType;
    private Member                     member;
    private Period                     period;
    private StatusGroup                statusGroup;
    private Collection<Payment.Status> statusList;
    private SearchType                 searchType;

    public AccountType getAccountType() {
        return accountType;
    }

    public Member getMember() {
        return member;
    }

    public AccountOwner getOwner() {
        return owner;
    }

    public Period getPeriod() {
        return period;
    }

    public SearchType getSearchType() {
        return searchType;
    }

    public StatusGroup getStatusGroup() {
        return statusGroup;
    }

    public Collection<Payment.Status> getStatusList() {
        return statusList;
    }

    public void setAccountType(final AccountType accountType) {
        this.accountType = accountType;
    }

    public void setMember(final Member member) {
        this.member = member;
    }

    public void setOwner(final AccountOwner owner) {
        this.owner = owner;
    }

    public void setPeriod(final Period period) {
        this.period = period;
    }

    public void setSearchType(final SearchType searchType) {
        this.searchType = searchType;
    }

    public void setStatus(final Payment.Status status) {
        statusList = Collections.singletonList(status);
    }

    public void setStatusGroup(final StatusGroup statusGroup) {
        this.statusGroup = statusGroup;
        statusList = statusGroup == null ? null : statusGroup.getStatusList();
    }

    public void setStatusList(final Collection<Payment.Status> statusList) {
        this.statusList = statusList;
    }

}
