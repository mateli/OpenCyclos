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
package nl.strohalm.cyclos.entities.members.brokerings;

import java.util.Collection;
import java.util.Collections;

import nl.strohalm.cyclos.entities.accounts.fees.transaction.BrokerCommission;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.members.brokerings.BrokerCommissionContract.Status;
import nl.strohalm.cyclos.utils.Period;
import nl.strohalm.cyclos.utils.query.QueryParameters;

import org.apache.commons.collections.CollectionUtils;

/**
 * Parameters for broker commission contracts query
 * @author Jefferson Magno
 */
public class BrokerCommissionContractQuery extends QueryParameters {

    private static final long       serialVersionUID = 2667844652910282641L;

    private Member                  member;
    private Member                  broker;
    private BrokerCommission        brokerCommission;
    private Period                  startPeriod;
    private Period                  endPeriod;
    private Collection<Status>      statusList;

    // used to restrict the result in case of admin (because for broker/member we filter by itself)
    private Collection<MemberGroup> managedMemberGroups;

    public Member getBroker() {
        return broker;
    }

    public BrokerCommission getBrokerCommission() {
        return brokerCommission;
    }

    public Period getEndPeriod() {
        return endPeriod;
    }

    public Collection<MemberGroup> getManagedMemberGroups() {
        return managedMemberGroups;
    }

    public Member getMember() {
        return member;
    }

    public Period getStartPeriod() {
        return startPeriod;
    }

    public Status getStatus() {
        return CollectionUtils.isEmpty(statusList) ? null : statusList.iterator().next();
    }

    public Collection<Status> getStatusList() {
        return statusList;
    }

    public void setBroker(final Member broker) {
        this.broker = broker;
    }

    public void setBrokerCommission(final BrokerCommission brokerCommission) {
        this.brokerCommission = brokerCommission;
    }

    public void setEndPeriod(final Period endPeriod) {
        this.endPeriod = endPeriod;
    }

    public void setManagedMemberGroups(final Collection<MemberGroup> managedMemberGroups) {
        this.managedMemberGroups = managedMemberGroups;
    }

    public void setMember(final Member member) {
        this.member = member;
    }

    public void setStartPeriod(final Period startPeriod) {
        this.startPeriod = startPeriod;
    }

    public void setStatus(final Status status) {
        if (status != null) {
            statusList = Collections.singletonList(status);
        }
    }

    public void setStatusList(final Collection<Status> statusList) {
        this.statusList = statusList;
    }

}
