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
package nl.strohalm.cyclos.controls.reports.members.list;

import java.util.Calendar;
import java.util.Collection;

import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.members.Member;

public class MembersListReportDTO {

    public enum PeriodType {
        PERIOD_CURRENT, PERIOD_HISTORY;
    }

    private Collection<MemberGroup> groups;

    private Member                  broker;

    private boolean                 memberName;
    private boolean                 brokerName;
    private boolean                 brokerUsername;
    private boolean                 activeAds;
    private boolean                 expiredAds;
    private boolean                 permanentAds;

    private boolean                 scheduledAds;
    private boolean                 givenReferences;

    private boolean                 receivedReferences;
    private boolean                 accountsBalances;
    private boolean                 accountsCredits;
    private boolean                 accountsUpperCredits;
    private Calendar                period;

    private PeriodType              periodType;

    public int getAccountsColSpan() {
        int count = 0;
        if (isAccountsCredits()) {
            count++;
        }
        if (isAccountsUpperCredits()) {
            count++;
        }
        if (isAccountsBalances()) {
            count++;
        }
        return count;
    }

    public int getAdsColSpan() {
        int count = 0;
        if (isActiveAds()) {
            count++;
        }
        if (isExpiredAds()) {
            count++;
        }
        if (isPermanentAds()) {
            count++;
        }
        if (isScheduledAds()) {
            count++;
        }
        return count;
    }

    public Member getBroker() {
        return broker;
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

    public Collection<MemberGroup> getGroups() {
        return groups;
    }

    public int getMemberColSpan() {
        if (isMemberName()) {
            return 2;
        } else {
            return 1;
        }
    }

    public Calendar getPeriod() {
        return period;
    }

    public PeriodType getPeriodType() {
        return periodType;
    }

    public boolean isAccountsBalances() {
        return accountsBalances;
    }

    public boolean isAccountsCredits() {
        return accountsCredits;
    }

    public boolean isAccountsInformation() {
        return isAccountsCredits() || isAccountsUpperCredits() || isAccountsBalances();
    }

    public boolean isAccountsUpperCredits() {
        return accountsUpperCredits;
    }

    public boolean isActiveAds() {
        return activeAds;
    }

    public boolean isAds() {
        return activeAds || expiredAds || permanentAds || scheduledAds;
    }

    public boolean isBrokerName() {
        return brokerName;
    }

    public boolean isBrokerUsername() {
        return brokerUsername;
    }

    public boolean isExpiredAds() {
        return expiredAds;
    }

    public boolean isGivenReferences() {
        return givenReferences;
    }

    public boolean isMemberName() {
        return memberName;
    }

    public boolean isPermanentAds() {
        return permanentAds;
    }

    public boolean isReceivedReferences() {
        return receivedReferences;
    }

    public boolean isScheduledAds() {
        return scheduledAds;
    }

    public void setAccountsBalances(final boolean accountsBalances) {
        this.accountsBalances = accountsBalances;
    }

    public void setAccountsCredits(final boolean accountsCredits) {
        this.accountsCredits = accountsCredits;
    }

    public void setAccountsUpperCredits(final boolean accountsUpperCredits) {
        this.accountsUpperCredits = accountsUpperCredits;
    }

    public void setActiveAds(final boolean activeAds) {
        this.activeAds = activeAds;
    }

    public void setBroker(final Member broker) {
        this.broker = broker;
    }

    public void setBrokerName(final boolean brokerName) {
        this.brokerName = brokerName;
    }

    public void setBrokerUsername(final boolean brokerUsername) {
        this.brokerUsername = brokerUsername;
    }

    public void setExpiredAds(final boolean expiredAds) {
        this.expiredAds = expiredAds;
    }

    public void setGivenReferences(final boolean givenReferences) {
        this.givenReferences = givenReferences;
    }

    public void setGroups(final Collection<MemberGroup> groups) {
        this.groups = groups;
    }

    public void setMemberName(final boolean memberName) {
        this.memberName = memberName;
    }

    public void setPeriod(final Calendar period) {
        this.period = period;
    }

    public void setPeriodType(final PeriodType periodType) {
        this.periodType = periodType;
    }

    public void setPermanentAds(final boolean permanentAds) {
        this.permanentAds = permanentAds;
    }

    public void setReceivedReferences(final boolean receivedReferences) {
        this.receivedReferences = receivedReferences;
    }

    public void setScheduledAds(final boolean scheduledAds) {
        this.scheduledAds = scheduledAds;
    }

}
