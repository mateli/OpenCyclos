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

import java.util.Collection;

import nl.strohalm.cyclos.entities.customization.fields.MemberCustomFieldValue;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.utils.Period;
import nl.strohalm.cyclos.utils.query.QueryParameters;

/**
 * Query parameters for pending members search
 * @author luis
 */
public class PendingMemberQuery extends QueryParameters {

    private static final long                  serialVersionUID = -3310790371627071751L;
    private Collection<MemberGroup>            groups;
    private String                             name;
    private Period                             creationPeriod;
    private Member                             broker;
    private Collection<MemberCustomFieldValue> customValues;

    public Member getBroker() {
        return broker;
    }

    public Period getCreationPeriod() {
        return creationPeriod;
    }

    public Collection<MemberCustomFieldValue> getCustomValues() {
        return customValues;
    }

    public Collection<MemberGroup> getGroups() {
        return groups;
    }

    public String getName() {
        return name;
    }

    public void setBroker(final Member broker) {
        this.broker = broker;
    }

    public void setCreationPeriod(final Period creationPeriod) {
        this.creationPeriod = creationPeriod;
    }

    public void setCustomValues(final Collection<MemberCustomFieldValue> customValues) {
        this.customValues = customValues;
    }

    public void setGroups(final Collection<MemberGroup> groups) {
        this.groups = groups;
    }

    public void setName(final String name) {
        this.name = name;
    }

}
