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

import nl.strohalm.cyclos.entities.Entity;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.customization.fields.MemberRecordCustomField;
import nl.strohalm.cyclos.entities.groups.AdminGroup;
import nl.strohalm.cyclos.entities.groups.BrokerGroup;
import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.utils.StringValuedEnum;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Collection;

/**
 * Defines a group of custom fields for a member
 * @author Jefferson Magno
 */
@Cacheable
@Table(name = "member_record_types")
@javax.persistence.Entity
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

    @Column(name = "name", nullable = false, length = 100)
    private String                              name;

    @Column(name = "label", nullable = false, length = 100)
    private String                              label;

    @Column(name = "description", columnDefinition = "text")
    private String                              description;

    @OneToMany(mappedBy = "memberRecordType", cascade = CascadeType.REMOVE)
	private Collection<MemberRecordCustomField> fields;

    @ManyToMany
    @JoinTable(name = "groups_member_record_types",
            joinColumns = @JoinColumn(name = "member_record_type_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id"))
	private Collection<Group>                   groups;

    @ManyToMany(mappedBy = "viewMemberRecordTypes")
	private Collection<AdminGroup>              viewableByAdminGroups;

    @ManyToMany(mappedBy = "createMemberRecordTypes")
	private Collection<AdminGroup>              creatableByAdminGroups;

    @ManyToMany(mappedBy = "modifyMemberRecordTypes")
	private Collection<AdminGroup>              updatableByAdminGroups;

    @ManyToMany(mappedBy = "deleteMemberRecordTypes")
	private Collection<AdminGroup>              deletableByAdminGroups;

    @ManyToMany(mappedBy = "brokerMemberRecordTypes")
	private Collection<BrokerGroup>             viewableByBrokerGroups;

    @ManyToMany(mappedBy = "brokerCreateMemberRecordTypes")
	private Collection<BrokerGroup>             creatableByBrokerGroups;

    @ManyToMany(mappedBy = "brokerModifyMemberRecordTypes")
	private Collection<BrokerGroup>             updatableByBrokerGroups;

    @ManyToMany(mappedBy = "brokerDeleteMemberRecordTypes")
	private Collection<BrokerGroup>             deletableByBrokerGroups;

    @Convert(converter = MemberRecordTypeLayoutAttributeConverter.class)
    @Column(name = "layout", nullable = false, length = 1)
	private Layout                              layout;

    @Column(name = "editable", nullable = false)
    private boolean                             editable;

    @Column(name = "show_menu_item", nullable = false)
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
