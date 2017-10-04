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
package nl.strohalm.cyclos.entities.groups;

import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.SystemAccountType;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferType;
import nl.strohalm.cyclos.entities.customization.fields.AdminCustomField;
import nl.strohalm.cyclos.entities.members.records.MemberRecordType;

import javax.persistence.DiscriminatorValue;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.util.Collection;

/**
 * A group of administrators
 * @author luis
 */
@DiscriminatorValue("A")
@javax.persistence.Entity
public class AdminGroup extends SystemGroup {

    public static enum Relationships implements Relationship {
        TRANSFER_TYPES_AS_MEMBER("transferTypesAsMember"), MANAGES_GROUPS("managesGroups"), VIEW_INFORMATION_OF("viewInformationOf"), VIEW_CONNECTED_ADMINS_OF("viewConnectedAdminsOf"), CONNECTED_ADMINS_VIEWED_BY("connectedAdminsViewedBy"), ADMIN_CUSTOM_FIELDS("adminCustomFields"), VIEW_MEMBER_RECORD_TYPES("viewMemberRecordTypes"), CREATE_MEMBER_RECORD_TYPES("createMemberRecordTypes"), MODIFY_MEMBER_RECORD_TYPES("modifyMemberRecordTypes"), DELETE_MEMBER_RECORD_TYPES("deleteMemberRecordTypes"), VIEW_ADMIN_RECORD_TYPES("viewAdminRecordTypes"), CREATE_ADMIN_RECORD_TYPES("createAdminRecordTypes"), MODIFY_ADMIN_RECORD_TYPES("modifyAdminRecordTypes"), DELETE_ADMIN_RECORD_TYPES("deleteAdminRecordTypes");

        private final String name;

        private Relationships(final String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }
    }

    private static final long             serialVersionUID = 327847242923171219L;

    @ManyToMany
    @JoinTable(name = "groups_transfer_types_as_member",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "transfer_type_id")
    )
    private Collection<TransferType>      transferTypesAsMember;

    @ManyToMany
    @JoinTable(name = "admin_manages_member_groups",
            joinColumns = @JoinColumn(name = "manager_group_id"),
            inverseJoinColumns = @JoinColumn(name = "managed_group_id")
    )
	private Collection<MemberGroup>       managesGroups;

    @ManyToMany
    @JoinTable(name = "admin_view_account_information",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "account_type_id")
    )
	private Collection<SystemAccountType> viewInformationOf;

    @ManyToMany
    @JoinTable(name = "admin_view_connected_users_of",
            joinColumns = @JoinColumn(name = "viewer_group_id"),
            inverseJoinColumns = @JoinColumn(name = "viewed_group_id")
    )
	private Collection<AdminGroup>        viewConnectedAdminsOf;

    @ManyToMany
    @JoinTable(name = "admin_view_connected_users_of",
            joinColumns = @JoinColumn(name = "viewed_group_id"),
            inverseJoinColumns = @JoinColumn(name = "viewer_group_id")
    )
	private Collection<AdminGroup>        connectedAdminsViewedBy;

    @ManyToMany
    @JoinTable(name = "admin_groups_custom_fields",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "custom_field_id")
    )
	private Collection<AdminCustomField>  adminCustomFields;

    @ManyToMany
    @JoinTable(name = "admin_groups_admin_record_types",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "member_record_type_id")
    )
	private Collection<MemberRecordType>  viewAdminRecordTypes;

    @ManyToMany
    @JoinTable(name = "admin_groups_create_admin_record_types",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "member_record_type_id")
    )
	private Collection<MemberRecordType>  createAdminRecordTypes;

    @ManyToMany
    @JoinTable(name = "admin_groups_modify_admin_record_types",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "member_record_type_id")
    )
	private Collection<MemberRecordType>  modifyAdminRecordTypes;

    @ManyToMany
    @JoinTable(name = "admin_groups_delete_admin_record_types",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "member_record_type_id")
    )
	private Collection<MemberRecordType>  deleteAdminRecordTypes;

    @ManyToMany
    @JoinTable(name = "admin_groups_member_record_types",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "member_record_type_id")
    )
	private Collection<MemberRecordType>  viewMemberRecordTypes;

    @ManyToMany
    @JoinTable(name = "admin_groups_create_member_record_types",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "member_record_type_id")
    )
	private Collection<MemberRecordType>  createMemberRecordTypes;

    @ManyToMany
    @JoinTable(name = "admin_groups_modify_member_record_types",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "member_record_type_id")
    )
	private Collection<MemberRecordType>  modifyMemberRecordTypes;

    @ManyToMany
    @JoinTable(name = "admin_groups_delete_member_record_types",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "member_record_type_id")
    )
	private Collection<MemberRecordType>  deleteMemberRecordTypes;

    protected AdminGroup() {
	}

	public Collection<AdminCustomField> getAdminCustomFields() {
        return adminCustomFields;
    }

    public Collection<AdminGroup> getConnectedAdminsViewedBy() {
        return connectedAdminsViewedBy;
    }

    public Collection<MemberRecordType> getCreateAdminRecordTypes() {
        return createAdminRecordTypes;
    }

    public Collection<MemberRecordType> getCreateMemberRecordTypes() {
        return createMemberRecordTypes;
    }

    public Collection<MemberRecordType> getDeleteAdminRecordTypes() {
        return deleteAdminRecordTypes;
    }

    public Collection<MemberRecordType> getDeleteMemberRecordTypes() {
        return deleteMemberRecordTypes;
    }

    public Collection<MemberGroup> getManagesGroups() {
        return managesGroups;
    }

    public Collection<MemberRecordType> getModifyAdminRecordTypes() {
        return modifyAdminRecordTypes;
    }

    public Collection<MemberRecordType> getModifyMemberRecordTypes() {
        return modifyMemberRecordTypes;
    }

    @Override
    public Nature getNature() {
        return Nature.ADMIN;
    }

    public Collection<TransferType> getTransferTypesAsMember() {
        return transferTypesAsMember;
    }

    public Collection<MemberRecordType> getViewAdminRecordTypes() {
        return viewAdminRecordTypes;
    }

    public Collection<AdminGroup> getViewConnectedAdminsOf() {
        return viewConnectedAdminsOf;
    }

    public Collection<SystemAccountType> getViewInformationOf() {
        return viewInformationOf;
    }

    public Collection<MemberRecordType> getViewMemberRecordTypes() {
        return viewMemberRecordTypes;
    }

    public void setAdminCustomFields(final Collection<AdminCustomField> adminCustomFields) {
        this.adminCustomFields = adminCustomFields;
    }

    public void setConnectedAdminsViewedBy(final Collection<AdminGroup> connectedAdminsViewedBy) {
        this.connectedAdminsViewedBy = connectedAdminsViewedBy;
    }

    public void setCreateAdminRecordTypes(final Collection<MemberRecordType> createAdminRecordTypes) {
        this.createAdminRecordTypes = createAdminRecordTypes;
    }

    public void setCreateMemberRecordTypes(final Collection<MemberRecordType> createMemberRecordTypes) {
        this.createMemberRecordTypes = createMemberRecordTypes;
    }

    public void setDeleteAdminRecordTypes(final Collection<MemberRecordType> deleteAdminRecordTypes) {
        this.deleteAdminRecordTypes = deleteAdminRecordTypes;
    }

    public void setDeleteMemberRecordTypes(final Collection<MemberRecordType> deleteMemberRecordTypes) {
        this.deleteMemberRecordTypes = deleteMemberRecordTypes;
    }

    public void setManagesGroups(final Collection<MemberGroup> managesGroups) {
        this.managesGroups = managesGroups;
    }

    public void setModifyAdminRecordTypes(final Collection<MemberRecordType> modifyAdminRecordTypes) {
        this.modifyAdminRecordTypes = modifyAdminRecordTypes;
    }

    public void setModifyMemberRecordTypes(final Collection<MemberRecordType> modifyMemberRecordTypes) {
        this.modifyMemberRecordTypes = modifyMemberRecordTypes;
    }

    public void setTransferTypesAsMember(final Collection<TransferType> transferTypesAsMember) {
        this.transferTypesAsMember = transferTypesAsMember;
    }

    public void setViewAdminRecordTypes(final Collection<MemberRecordType> viewAdminRecordTypes) {
        this.viewAdminRecordTypes = viewAdminRecordTypes;
    }

    public void setViewConnectedAdminsOf(final Collection<AdminGroup> viewConnectedAdminsOf) {
        this.viewConnectedAdminsOf = viewConnectedAdminsOf;
    }

    public void setViewInformationOf(final Collection<SystemAccountType> viewInformationOf) {
        this.viewInformationOf = viewInformationOf;
    }

    public void setViewMemberRecordTypes(final Collection<MemberRecordType> viewMemberRecordTypes) {
        this.viewMemberRecordTypes = viewMemberRecordTypes;
    }

}
