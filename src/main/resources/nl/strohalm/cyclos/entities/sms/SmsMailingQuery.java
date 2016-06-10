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
package nl.strohalm.cyclos.entities.sms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.utils.Period;
import nl.strohalm.cyclos.utils.query.QueryParameters;

import org.apache.commons.collections.CollectionUtils;

/**
 * Query parameter for sms mailings searches
 * 
 * @author luis
 */
public class SmsMailingQuery extends QueryParameters {

    public static enum Recipient {
        GROUPS, MEMBER
    }

    private static final long       serialVersionUID = 2235103662175315237L;
    private Period                  period;
    private Member                  broker;
    private Member                  member;
    private Collection<MemberGroup> groups;
    private Boolean                 finished;
    private Recipient               recipient;

    public Member getBroker() {
        return broker;
    }

    public Boolean getFinished() {
        return finished;
    }

    public MemberGroup getGroup() {
        return CollectionUtils.isEmpty(groups) ? null : groups.iterator().next();
    }

    public Collection<MemberGroup> getGroups() {
        return groups;
    }

    public Member getMember() {
        return member;
    }

    public Period getPeriod() {
        return period;
    }

    public Recipient getRecipient() {
        return recipient;
    }

    public void setBroker(final Member broker) {
        this.broker = broker;
    }

    public void setFinished(final Boolean finished) {
        this.finished = finished;
    }

    public void setGroup(final MemberGroup group) {
        if (group == null) {
            groups = null;
        } else {
            groups = new ArrayList<MemberGroup>(Arrays.asList(group));
        }
    }

    public void setGroups(final Collection<MemberGroup> groups) {
        this.groups = groups;
    }

    public void setMember(final Member member) {
        this.member = member;
    }

    public void setPeriod(final Period period) {
        this.period = period;
    }

    public void setRecipient(final Recipient recipient) {
        this.recipient = recipient;
    }

}
