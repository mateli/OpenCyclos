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
package nl.strohalm.cyclos.entities.alerts;

import java.util.Collection;

import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.utils.Period;
import nl.strohalm.cyclos.utils.query.QueryParameters;

/**
 * Query parameters for alerts
 * @author luis
 */
public class AlertQuery extends QueryParameters {

    private static final long       serialVersionUID = -6089337261822449544L;
    private Member                  member;
    private Collection<MemberGroup> groups;
    private Period                  period;
    private boolean                 showRemoved;
    private Alert.Type              type;
    private String                  key;

    public AlertQuery() {
        super();
    }

    public Collection<MemberGroup> getGroups() {
        return groups;
    }

    public String getKey() {
        return key;
    }

    public Member getMember() {
        return member;
    }

    public Period getPeriod() {
        return period;
    }

    public Alert.Type getType() {
        return type;
    }

    public boolean isShowRemoved() {
        return showRemoved;
    }

    public void setGroups(final Collection<MemberGroup> groups) {
        this.groups = groups;
    }

    public void setKey(final String key) {
        this.key = key;
    }

    public void setMember(final Member member) {
        this.member = member;
    }

    public void setPeriod(final Period period) {
        this.period = period;
    }

    public void setShowRemoved(final boolean showRemoved) {
        this.showRemoved = showRemoved;
    }

    public void setType(final Alert.Type type) {
        this.type = type;
    }

}
