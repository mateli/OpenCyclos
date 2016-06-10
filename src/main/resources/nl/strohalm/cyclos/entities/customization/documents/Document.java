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
package nl.strohalm.cyclos.entities.customization.documents;

import java.util.ArrayList;
import java.util.Collection;

import nl.strohalm.cyclos.entities.Entity;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.groups.BrokerGroup;
import nl.strohalm.cyclos.entities.groups.SystemGroup;
import nl.strohalm.cyclos.utils.StringValuedEnum;

/**
 * Is a customized document, it can be static or dynamic
 * @author luis
 * @author Jefferson Magno
 */
public abstract class Document extends Entity {

    public static enum Nature implements StringValuedEnum {
        DYNAMIC("D"), STATIC("S"), MEMBER("M");

        private String value;

        private Nature(final String value) {
            this.value = value;
        }

        public Class<? extends Document> getType() {
            return this == DYNAMIC ? DynamicDocument.class : (this == STATIC ? StaticDocument.class : MemberDocument.class);
        }

        @Override
        public String getValue() {
            return value;
        }
    }

    public static enum Relationships implements Relationship {
        GROUPS("groups"), BROKER_GROUPS("brokerGroups");
        private final String name;

        private Relationships(final String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }
    }

    private static final long       serialVersionUID = -2006379424571494005L;
    private String                  name;
    private String                  description;
    private Collection<SystemGroup> groups;
    private Collection<BrokerGroup> brokerGroups;

    public void addGroup(final SystemGroup g) {
        if (groups == null) {
            groups = new ArrayList<SystemGroup>();
        }

        groups.add(g);
    }

    public Collection<BrokerGroup> getBrokerGroups() {
        return brokerGroups;
    }

    public String getDescription() {
        return description;
    }

    public Collection<SystemGroup> getGroups() {
        return groups;
    }

    public String getName() {
        return name;
    }

    public abstract Nature getNature();

    public void setBrokerGroups(final Collection<BrokerGroup> brokerGroups) {
        this.brokerGroups = brokerGroups;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public void setGroups(final Collection<SystemGroup> groups) {
        this.groups = groups;
    }

    public void setName(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return getId() + " - " + name;
    }

}
