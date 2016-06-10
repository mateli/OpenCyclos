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
package nl.strohalm.cyclos.entities.members.records;

import java.util.Collection;

import nl.strohalm.cyclos.entities.Entity;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.customization.fields.MemberRecordCustomField;
import nl.strohalm.cyclos.entities.groups.AdminGroup;
import nl.strohalm.cyclos.entities.groups.BrokerGroup;
import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.utils.StringValuedEnum;

/**
 * Defines a group of custom fields for a member
 * @author Jefferson Magno
 */
public class MemberRecordType extends Entity {

    public static enum Layout implements StringValuedEnum {
        FLAT("F"), LIST("L");
        private final String value;

        private Layout(final String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public static enum Relationships implements Relationship {
        FIELDS("fields"), GROUPS("groups"), VIEWABLE_BY_ADMIN_GROUPS("viewableByAdminGroups"), CREATABLE_BY_ADMIN_GROUPS("creatableByAdminGroups"), UPDATABLE_BY_ADMIN_GROUPS("updatableByAdminGroups"), DELETABLE_BY_ADMIN_GROUPS("deletableByAdminGroups"), VIEWABLE_BY_BROKER_GROUPS("viewableByBrokerGroups"), CREATABLE_BY_BROKER_GROUPS("creatableByBrokerGroups"), UPDATABLE_BY_BROKER_GROUPS("updatableByBrokerGroups"), DELETABLE_BY_BROKER_GROUPS("deletableByBrokerGroups"), ;
        private final String name;

        private Relationships(final String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    private static final long                   serialVersionUID = -1708482580670924301L;
    private String                              name;
    private String                              label;
    private String                              description;
    private Collection<MemberRecordCustomField> fields;
    private Collection<Group>                   groups;
    private Collection<AdminGroup>              viewableByAdminGroups;
    private Collection<AdminGroup>              creatableByAdminGroups;
    private Collection<AdminGroup>              updatableByAdminGroups;
    private Collection<AdminGroup>              deletableByAdminGroups;
    private Collection<BrokerGroup>             viewableByBrokerGroups;
    private Collection<BrokerGroup>             creatableByBrokerGroups;
    private Collection<BrokerGroup>             updatableByBrokerGroups;
    private Collection<BrokerGroup>             deletableByBrokerGroups;
    private Layout                              layout;
    private boolean                             editable;
    private boolean                             showMenuItem;

    public Collection<AdminGroup> getCreatableByAdminGroups() {
        return creatableByAdminGroups;
    }

    public Collection<BrokerGroup> getCreatableByBrokerGroups() {
        return creatableByBrokerGroups;
    }

    public Collection<AdminGroup> getDeletableByAdminGroups() {
        return deletableByAdminGroups;
    }

    public Collection<BrokerGroup> getDeletableByBrokerGroups() {
        return deletableByBrokerGroups;
    }

    public String getDescription() {
        return description;
    }

    public Collection<MemberRecordCustomField> getFields() {
        return fields;
    }

    public Collection<Group> getGroups() {
        return groups;
    }

    public String getLabel() {
        return label;
    }

    public Layout getLayout() {
        return layout;
    }

    public String getName() {
        return name;
    }

    public Collection<AdminGroup> getUpdatableByAdminGroups() {
        return updatableByAdminGroups;
    }

    public Collection<BrokerGroup> getUpdatableByBrokerGroups() {
        return updatableByBrokerGroups;
    }

    public Collection<AdminGroup> getViewableByAdminGroups() {
        return viewableByAdminGroups;
    }

    public Collection<BrokerGroup> getViewableByBrokerGroups() {
        return viewableByBrokerGroups;
    }

    public boolean isEditable() {
        return editable;
    }

    public boolean isShowMenuItem() {
        return showMenuItem;
    }

    public void setCreatableByAdminGroups(final Collection<AdminGroup> creatableByAdminGroups) {
        this.creatableByAdminGroups = creatableByAdminGroups;
    }

    public void setCreatableByBrokerGroups(final Collection<BrokerGroup> creatableByBrokerGroups) {
        this.creatableByBrokerGroups = creatableByBrokerGroups;
    }

    public void setDeletableByAdminGroups(final Collection<AdminGroup> deletableByAdminGroups) {
        this.deletableByAdminGroups = deletableByAdminGroups;
    }

    public void setDeletableByBrokerGroups(final Collection<BrokerGroup> deletableByBrokerGroups) {
        this.deletableByBrokerGroups = deletableByBrokerGroups;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public void setEditable(final boolean editable) {
        this.editable = editable;
    }

    public void setFields(final Collection<MemberRecordCustomField> fields) {
        this.fields = fields;
    }

    public void setGroups(final Collection<Group> groups) {
        this.groups = groups;
    }

    public void setLabel(final String label) {
        this.label = label;
    }

    public void setLayout(final Layout layout) {
        this.layout = layout;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setShowMenuItem(final boolean showMenuItem) {
        this.showMenuItem = showMenuItem;
    }

    public void setUpdatableByAdminGroups(final Collection<AdminGroup> updatableByAdminGroups) {
        this.updatableByAdminGroups = updatableByAdminGroups;
    }

    public void setUpdatableByBrokerGroups(final Collection<BrokerGroup> updatableByBrokerGroups) {
        this.updatableByBrokerGroups = updatableByBrokerGroups;
    }

    public void setViewableByAdminGroups(final Collection<AdminGroup> viewableByAdminGroups) {
        this.viewableByAdminGroups = viewableByAdminGroups;
    }

    public void setViewableByBrokerGroups(final Collection<BrokerGroup> viewableByBrokerGroups) {
        this.viewableByBrokerGroups = viewableByBrokerGroups;
    }

    @Override
    public String toString() {
        return getId() + " - " + getName();
    }

}
