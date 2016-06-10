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

import java.util.Calendar;
import java.util.Collection;

import nl.strohalm.cyclos.entities.Entity;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.members.Member;

/**
 * Contains data about brokering
 * @author luis
 */
public class Brokering extends Entity {

    public static enum Relationships implements Relationship {
        BROKER("broker"), BROKERED("brokered"), CONTRACTS("contracts"), STATUSES("statuses");
        private final String name;

        private Relationships(final String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }
    }

    private static final long                     serialVersionUID = 5481485302378656979L;

    private Member                                broker;
    private Member                                brokered;
    private Calendar                              endDate;
    private Calendar                              startDate;
    private Collection<BrokerCommissionContract>  contracts;
    private Collection<BrokeringCommissionStatus> statuses;

    public Member getBroker() {
        return broker;
    }

    public Member getBrokered() {
        return brokered;
    }

    public Collection<BrokerCommissionContract> getContracts() {
        return contracts;
    }

    public Calendar getEndDate() {
        return endDate;
    }

    public Calendar getStartDate() {
        return startDate;
    }

    public Collection<BrokeringCommissionStatus> getStatuses() {
        return statuses;
    }

    public void setBroker(final Member broker) {
        this.broker = broker;
    }

    public void setBrokered(final Member brokered) {
        this.brokered = brokered;
    }

    public void setContracts(final Collection<BrokerCommissionContract> contracts) {
        this.contracts = contracts;
    }

    public void setEndDate(final Calendar commissionEndDate) {
        endDate = commissionEndDate;
    }

    public void setStartDate(final Calendar startDate) {
        this.startDate = startDate;
    }

    public void setStatuses(final Collection<BrokeringCommissionStatus> statuses) {
        this.statuses = statuses;
    }

    @Override
    public String toString() {
        return getId() + " - " + broker + " broker of " + brokered;
    }
}
