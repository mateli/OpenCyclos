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
package nl.strohalm.cyclos.entities.members;

import java.util.Calendar;
import java.util.Collection;

import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.utils.query.QueryParameters;

/**
 * Query for brokerings
 * @author luis
 */
public class BrokeringQuery extends QueryParameters {

    public static enum Status {
        ACTIVE, COMMISSION_COMPLETE, PENDING;
    }

    private static final long       serialVersionUID = 1892733819952540734L;
    private Member                  broker;
    private Member                  brokered;
    private String                  username;
    private String                  name;
    private Collection<MemberGroup> groups;
    private Calendar                startExpirationDate;
    private Status                  status;
    private boolean                 returnFinished;

    public Member getBroker() {
        return broker;
    }

    public Member getBrokered() {
        return brokered;
    }

    public Collection<MemberGroup> getGroups() {
        return groups;
    }

    public String getName() {
        return name;
    }

    public Calendar getStartExpirationDate() {
        return startExpirationDate;
    }

    public Status getStatus() {
        return status;
    }

    public String getUsername() {
        return username;
    }

    public boolean isReturnFinished() {
        return returnFinished;
    }

    public void setBroker(final Member broker) {
        this.broker = broker;
    }

    public void setBrokered(final Member brokered) {
        this.brokered = brokered;
    }

    public void setGroups(final Collection<MemberGroup> groups) {
        this.groups = groups;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setReturnFinished(final boolean returnFinished) {
        this.returnFinished = returnFinished;
    }

    public void setStartExpirationDate(final Calendar startExpirationDate) {
        this.startExpirationDate = startExpirationDate;
    }

    public void setStatus(final Status status) {
        this.status = status;
    }

    public void setUsername(final String username) {
        this.username = username;
    }
}
