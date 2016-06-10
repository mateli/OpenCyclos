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
package nl.strohalm.cyclos.setup.migrations.version3_5;

import static nl.strohalm.cyclos.utils.JDBCWrapper.closeQuietly;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.ResourceBundle;

import nl.strohalm.cyclos.entities.settings.LocalSettings.Language;
import nl.strohalm.cyclos.setup.Setup;
import nl.strohalm.cyclos.setup.UntraceableMigration;
import nl.strohalm.cyclos.utils.JDBCWrapper;
import nl.strohalm.cyclos.utils.conversion.CoercionHelper;

/**
 * Creates a new record type named "Remark" and populates it with data from the old general remarks. After copying the data, removes the general
 * remarks entries in the database, including modules, operations and permissions.
 * @author Jefferson Magno
 */
@SuppressWarnings("deprecation")
public class GeneralRemarksMigration implements UntraceableMigration {

    public void execute(final JDBCWrapper jdbc) throws SQLException {

        // Get resource bundle object
        final String languageStr = jdbc.readScalarAsString("select value from settings where name='language'");
        final Language language = CoercionHelper.coerce(Language.class, languageStr);
        final ResourceBundle resourceBundle = Setup.getResourceBundle(language.getLocale());

        // Get resource bundle values
        final String remarkName = resourceBundle.getString("remarks.name");
        final String remarkLabel = resourceBundle.getString("remarks.label");
        final String remarkDescription = resourceBundle.getString("remarks.description");
        final String commentsName = resourceBundle.getString("remarks.comments");
        final String commentsInternalName = resourceBundle.getString("remarks.comments.internalName");
        final String commentsDescription = resourceBundle.getString("remarks.comments.description");

        // Insert record type
        final String insertRemarkRecordType = "insert into member_record_types (name, label, editable, layout, description, show_menu_item) values (?, ?, false, 'F', ?, true)";
        final Object[] remarkRecordTypeParams = new Object[] { remarkName, remarkLabel, remarkDescription };
        jdbc.execute(insertRemarkRecordType, remarkRecordTypeParams);
        final Long memberRecordTypeId = jdbc.readScalarAsLong("select last_insert_id()");

        // Insert 'comments' custom field for the 'Remark' record type
        final String insertCommentsCustomField = "insert into custom_fields (subclass, name, internal_name, order_number, type, control, size, val_required, val_unique, description, member_record_type_id, record_broker_access, record_show_in_search, record_show_in_list) values ('record', ?, ?, 1, 'string', 'textarea', 'F', true, false, ?, ?, 'E', false, true)";
        final Object[] commentsCustomFieldParams = new Object[] { commentsName, commentsInternalName, commentsDescription, memberRecordTypeId };
        jdbc.execute(insertCommentsCustomField, commentsCustomFieldParams);
        final Long commentsCustomFieldId = jdbc.readScalarAsLong("select last_insert_id()");

        // All groups can use 'Remark' record type
        final List<Long> groupsIds = jdbc.readScalarAsLongList("select id from groups");
        for (final Long groupId : groupsIds) {
            final String insertGroupRecordTypes = "insert into groups_member_record_types (group_id, member_record_type_id) values (?, ?)";
            final Object[] groupRecordTypesParams = new Object[] { groupId, memberRecordTypeId };
            jdbc.execute(insertGroupRecordTypes, groupRecordTypesParams);
        }

        // Create modules and operations

        // Module adminAdminRecords
        Long adminAdminRecordsModuleId;
        try {
            final String insertAdminAdminRecordsModule = "insert into modules (type, name, message_key) values ('AA', 'adminAdminRecords', 'permission.adminAdminRecords')";
            jdbc.execute(insertAdminAdminRecordsModule);
            adminAdminRecordsModuleId = jdbc.readScalarAsLong("select last_insert_id()");
        } catch (final Exception e) {
            adminAdminRecordsModuleId = jdbc.readScalarAsLong("select id from modules where name='adminAdminRecords'");
        }

        // Operation create of module adminAdminRecords
        Long createAdminRecordsOperationId;
        try {
            final String insertCreateAdminRecords = "insert into operations (module_id, name, message_key) values (?, 'create', 'permission.adminAdminRecords.create')";
            final Object[] createAdminRecordsParams = new Object[] { adminAdminRecordsModuleId };
            jdbc.execute(insertCreateAdminRecords, createAdminRecordsParams);
            createAdminRecordsOperationId = jdbc.readScalarAsLong("select last_insert_id()");
        } catch (final Exception e) {
            createAdminRecordsOperationId = jdbc.readScalarAsLong("select id from operations where message_key ='permission.adminAdminRecords.create'");
        }

        // Operation modify of module adminAdminRecords
        Long modifyAdminRecordsOperationId;
        try {
            final String insertModifyAdminRecords = "insert into operations (module_id, name, message_key) values (?, 'modify', 'permission.adminAdminRecords.modify')";
            final Object[] modifyAdminRecordsParams = new Object[] { adminAdminRecordsModuleId };
            jdbc.execute(insertModifyAdminRecords, modifyAdminRecordsParams);
            modifyAdminRecordsOperationId = jdbc.readScalarAsLong("select last_insert_id()");
        } catch (final Exception e) {
            modifyAdminRecordsOperationId = jdbc.readScalarAsLong("select id from operations where message_key ='permission.adminAdminRecords.modify'");
        }

        // Operation delete of module adminAdminRecords
        Long deleteAdminRecordsOperationId;
        try {
            final String insertDeleteAdminRecords = "insert into operations (module_id, name, message_key) values (?, 'delete', 'permission.adminAdminRecords.delete')";
            final Object[] deleteAdminRecordsParams = new Object[] { adminAdminRecordsModuleId };
            jdbc.execute(insertDeleteAdminRecords, deleteAdminRecordsParams);
            deleteAdminRecordsOperationId = jdbc.readScalarAsLong("select last_insert_id()");
        } catch (final Exception e) {
            deleteAdminRecordsOperationId = jdbc.readScalarAsLong("select id from operations where message_key ='permission.adminAdminRecords.delete'");
        }

        // Operation view of module adminAdminRecords
        Long viewAdminRecordsOperationId;
        try {
            final String insertViewAdminRecords = "insert into operations (module_id, name, message_key) values (?, 'view', 'permission.adminAdminRecords.view')";
            final Object[] viewAdminRecordsParams = new Object[] { adminAdminRecordsModuleId };
            jdbc.execute(insertViewAdminRecords, viewAdminRecordsParams);
            viewAdminRecordsOperationId = jdbc.readScalarAsLong("select last_insert_id()");
        } catch (final Exception e) {
            viewAdminRecordsOperationId = jdbc.readScalarAsLong("select id from operations where message_key='permission.adminAdminRecords.view'");
        }

        // Module adminMemberRecords
        Long adminMemberRecordsModuleId;
        try {
            final String insertAdminMemberRecordsModule = "insert into modules (type, name, message_key) values ('AM', 'adminMemberRecords', 'permission.adminMemberRecords')";
            jdbc.execute(insertAdminMemberRecordsModule);
            adminMemberRecordsModuleId = jdbc.readScalarAsLong("select last_insert_id()");
        } catch (final Exception e) {
            adminMemberRecordsModuleId = jdbc.readScalarAsLong("select id from modules where name='adminMemberRecords'");
        }

        // Operation create of module adminMemberRecords
        Long createMemberRecordsOperationId;
        try {
            final String insertCreateMemberRecords = "insert into operations (module_id, name, message_key) values (?, 'create', 'permission.adminMemberRecords.create')";
            final Object[] createMemberRecordsParams = new Object[] { adminMemberRecordsModuleId };
            jdbc.execute(insertCreateMemberRecords, createMemberRecordsParams);
            createMemberRecordsOperationId = jdbc.readScalarAsLong("select last_insert_id()");
        } catch (final Exception e) {
            createMemberRecordsOperationId = jdbc.readScalarAsLong("select id from operations where message_key='permission.adminMemberRecords.create'");
        }

        // Operation modify of module adminMemberRecords
        Long modifyMemberRecordsOperationId;
        try {
            final String insertModifyMemberRecords = "insert into operations (module_id, name, message_key) values (?, 'modify', 'permission.adminMemberRecords.modify')";
            final Object[] modifyMemberRecordsParams = new Object[] { adminMemberRecordsModuleId };
            jdbc.execute(insertModifyMemberRecords, modifyMemberRecordsParams);
            modifyMemberRecordsOperationId = jdbc.readScalarAsLong("select last_insert_id()");
        } catch (final Exception e) {
            modifyMemberRecordsOperationId = jdbc.readScalarAsLong("select id from operations where message_key='permission.adminMemberRecords.modify'");
        }

        // Operation delete of module adminMemberRecords
        Long deleteMemberRecordsOperationId;
        try {
            final String insertDeleteMemberRecords = "insert into operations (module_id, name, message_key) values (?, 'delete', 'permission.adminMemberRecords.delete')";
            final Object[] deleteMemberRecordsParams = new Object[] { adminMemberRecordsModuleId };
            jdbc.execute(insertDeleteMemberRecords, deleteMemberRecordsParams);
            deleteMemberRecordsOperationId = jdbc.readScalarAsLong("select last_insert_id()");
        } catch (final Exception e) {
            deleteMemberRecordsOperationId = jdbc.readScalarAsLong("select id from operations where message_key='permission.adminMemberRecords.delete'");
        }

        // Operation view of module adminMemberRecords
        Long viewMemberRecordsOperationId;
        try {
            final String insertViewMemberRecords = "insert into operations (module_id, name, message_key) values (?, 'view', 'permission.adminMemberRecords.view')";
            final Object[] viewMemberRecordsParams = new Object[] { adminMemberRecordsModuleId };
            jdbc.execute(insertViewMemberRecords, viewMemberRecordsParams);
            viewMemberRecordsOperationId = jdbc.readScalarAsLong("select last_insert_id()");
        } catch (final Exception e) {
            viewMemberRecordsOperationId = jdbc.readScalarAsLong("select id from operations where message_key='permission.adminMemberRecords.view'");
        }

        // Module brokerMemberRecords
        Long brokerMemberRecordsModuleId;
        try {
            final String insertBrokerMemberRecordsModule = "insert into modules (type, name, message_key) values ('BK', 'brokerMemberRecords', 'permission.brokerMemberRecords')";
            jdbc.execute(insertBrokerMemberRecordsModule);
            brokerMemberRecordsModuleId = jdbc.readScalarAsLong("select last_insert_id()");
        } catch (final Exception e) {
            brokerMemberRecordsModuleId = jdbc.readScalarAsLong("select id from modules where name='brokerMemberRecords'");
        }

        // Operation create of module brokerMemberRecords
        Long brokerCreateMemberRecordsOperationId;
        try {
            final String insertBrokerCreateMemberRecords = "insert into operations (module_id, name, message_key) values (?, 'create', 'permission.brokerMemberRecords.create')";
            final Object[] brokerCreateMemberRecordsParams = new Object[] { brokerMemberRecordsModuleId };
            jdbc.execute(insertBrokerCreateMemberRecords, brokerCreateMemberRecordsParams);
            brokerCreateMemberRecordsOperationId = jdbc.readScalarAsLong("select last_insert_id()");
        } catch (final Exception e) {
            brokerCreateMemberRecordsOperationId = jdbc.readScalarAsLong("select id from operations where message_key='permission.brokerMemberRecords.create'");
        }

        // Operation modify of module brokerMemberRecords
        Long brokerModifyMemberRecordsOperationId;
        try {
            final String insertBrokerModifyMemberRecords = "insert into operations (module_id, name, message_key) values (?, 'modify', 'permission.brokerMemberRecords.modify')";
            final Object[] brokerModifyMemberRecordsParams = new Object[] { brokerMemberRecordsModuleId };
            jdbc.execute(insertBrokerModifyMemberRecords, brokerModifyMemberRecordsParams);
            brokerModifyMemberRecordsOperationId = jdbc.readScalarAsLong("select last_insert_id()");
        } catch (final Exception e) {
            brokerModifyMemberRecordsOperationId = jdbc.readScalarAsLong("select id from operations where message_key='permission.brokerMemberRecords.modify'");
        }

        // Operation delete of module brokerMemberRecords
        Long brokerDeleteMemberRecordsOperationId;
        try {
            final String insertBrokerDeleteMemberRecords = "insert into operations (module_id, name, message_key) values (?, 'delete', 'permission.brokerMemberRecords.delete')";
            final Object[] brokerDeleteMemberRecordsParams = new Object[] { brokerMemberRecordsModuleId };
            jdbc.execute(insertBrokerDeleteMemberRecords, brokerDeleteMemberRecordsParams);
            brokerDeleteMemberRecordsOperationId = jdbc.readScalarAsLong("select last_insert_id()");
        } catch (final Exception e) {
            brokerDeleteMemberRecordsOperationId = jdbc.readScalarAsLong("select id from operations where message_key='permission.brokerMemberRecords.delete'");
        }

        // Operation view of module brokerMemberRecords
        Long brokerViewMemberRecordsOperationId;
        try {
            final String insertBrokerViewMemberRecords = "insert into operations (module_id, name, message_key) values (?, 'view', 'permission.brokerMemberRecords.view')";
            final Object[] brokerViewMemberRecordsParams = new Object[] { brokerMemberRecordsModuleId };
            jdbc.execute(insertBrokerViewMemberRecords, brokerViewMemberRecordsParams);
            brokerViewMemberRecordsOperationId = jdbc.readScalarAsLong("select last_insert_id()");
        } catch (final Exception e) {
            brokerViewMemberRecordsOperationId = jdbc.readScalarAsLong("select id from operations where message_key='permission.brokerMemberRecords.view'");
        }

        // SQLs and objects for select and insert group permissions
        final String selectGroupsWithPermission = "select group_id from permissions where operation_id = ?";
        Object[] groupsWithPermissionParams;
        List<Long> ids;
        final String insertGroupPermission = "insert into permissions (operation_id, group_id) values (?, ?)";
        Object[] groupPermissionParams;

        // SQLs for insert association between groups and member record types (view permission)
        final String insertAdminViewMemberRecordTypes = "insert into admin_groups_member_record_types (group_id, member_record_type_id) values (?, ?)";
        Object[] adminViewMemberRecordTypesParams;

        final String insertAdminViewAdminRecordTypes = "insert into admin_groups_admin_record_types (group_id, member_record_type_id) values (?, ?)";
        Object[] adminViewAdminRecordTypesParams;

        final String insertBrokerViewMemberRecordTypes = "insert into broker_groups_member_record_types (group_id, member_record_type_id) values (? , ?)";
        Object[] brokerViewMemberRecordTypesParams;

        // Grant permissions to admin groups
        final Long adminViewMemberRemarksOperationId = jdbc.readScalarAsLong("select id from operations where message_key='permission.adminMemberRemarks.view'");
        groupsWithPermissionParams = new Object[] { adminViewMemberRemarksOperationId };
        ids = jdbc.readScalarAsLongList(selectGroupsWithPermission, groupsWithPermissionParams);
        for (final Long groupId : ids) {
            try {
                groupPermissionParams = new Object[] { viewMemberRecordsOperationId, groupId };
                jdbc.execute(insertGroupPermission, groupPermissionParams);
            } catch (final Exception e) {
                e.printStackTrace();
            }
            try {
                adminViewMemberRecordTypesParams = new Object[] { groupId, memberRecordTypeId };
                jdbc.execute(insertAdminViewMemberRecordTypes, adminViewMemberRecordTypesParams);
            } catch (final Exception e) {
                e.printStackTrace();
            }
        }

        final Long adminManageMemberRemarksOperationId = jdbc.readScalarAsLong("select * from operations where message_key='permission.adminMemberRemarks.manage'");
        groupsWithPermissionParams = new Object[] { adminManageMemberRemarksOperationId };
        ids = jdbc.readScalarAsLongList(selectGroupsWithPermission, groupsWithPermissionParams);
        for (final Long groupId : ids) {
            try {
                groupPermissionParams = new Object[] { createMemberRecordsOperationId, groupId };
                jdbc.execute(insertGroupPermission, groupPermissionParams);

                groupPermissionParams = new Object[] { modifyMemberRecordsOperationId, groupId };
                jdbc.execute(insertGroupPermission, groupPermissionParams);

                groupPermissionParams = new Object[] { deleteMemberRecordsOperationId, groupId };
                jdbc.execute(insertGroupPermission, groupPermissionParams);
            } catch (final Exception e) {
                e.printStackTrace();
            }
        }

        final Long adminViewAdminRemarksOperationId = jdbc.readScalarAsLong("select id from operations where message_key='permission.adminAdminRemarks.view'");
        groupsWithPermissionParams = new Object[] { adminViewAdminRemarksOperationId };
        ids = jdbc.readScalarAsLongList(selectGroupsWithPermission, groupsWithPermissionParams);
        for (final Long groupId : ids) {
            try {
                groupPermissionParams = new Object[] { viewAdminRecordsOperationId, groupId };
                jdbc.execute(insertGroupPermission, groupPermissionParams);
            } catch (final Exception e) {
                e.printStackTrace();
            }

            try {
                adminViewAdminRecordTypesParams = new Object[] { groupId, memberRecordTypeId };
                jdbc.execute(insertAdminViewAdminRecordTypes, adminViewAdminRecordTypesParams);
            } catch (final Exception e) {
                e.printStackTrace();
            }
        }

        final Long adminManageAdminRemarksOperationId = jdbc.readScalarAsLong("select * from operations where message_key='permission.adminAdminRemarks.manage'");
        groupsWithPermissionParams = new Object[] { adminManageAdminRemarksOperationId };
        ids = jdbc.readScalarAsLongList(selectGroupsWithPermission, groupsWithPermissionParams);
        for (final Long groupId : ids) {
            try {
                groupPermissionParams = new Object[] { createAdminRecordsOperationId, groupId };
                jdbc.execute(insertGroupPermission, groupPermissionParams);

                groupPermissionParams = new Object[] { modifyAdminRecordsOperationId, groupId };
                jdbc.execute(insertGroupPermission, groupPermissionParams);

                groupPermissionParams = new Object[] { deleteAdminRecordsOperationId, groupId };
                jdbc.execute(insertGroupPermission, groupPermissionParams);
            } catch (final Exception e) {
                e.printStackTrace();
            }
        }

        // Grant permissions to broker groups
        final Long brokerViewMemberRemarksOperationId = jdbc.readScalarAsLong("select * from operations where message_key='permission.brokerRemarks.view'");
        groupsWithPermissionParams = new Object[] { brokerViewMemberRemarksOperationId };
        ids = jdbc.readScalarAsLongList(selectGroupsWithPermission, groupsWithPermissionParams);
        for (final Long groupId : ids) {
            try {
                groupPermissionParams = new Object[] { brokerViewMemberRecordsOperationId, groupId };
                jdbc.execute(insertGroupPermission, groupPermissionParams);
            } catch (final Exception e) {
                e.printStackTrace();
            }

            try {
                brokerViewMemberRecordTypesParams = new Object[] { groupId, memberRecordTypeId };
                jdbc.execute(insertBrokerViewMemberRecordTypes, brokerViewMemberRecordTypesParams);
            } catch (final Exception e) {
                e.printStackTrace();
            }
        }

        final Long brokerManageMemberRemarksOperationId = jdbc.readScalarAsLong("select * from operations where message_key='permission.brokerRemarks.manage'");
        groupsWithPermissionParams = new Object[] { brokerManageMemberRemarksOperationId };
        ids = jdbc.readScalarAsLongList(selectGroupsWithPermission, groupsWithPermissionParams);
        for (final Long groupId : ids) {
            try {
                groupPermissionParams = new Object[] { brokerCreateMemberRecordsOperationId, groupId };
                jdbc.execute(insertGroupPermission, groupPermissionParams);

                groupPermissionParams = new Object[] { brokerModifyMemberRecordsOperationId, groupId };
                jdbc.execute(insertGroupPermission, groupPermissionParams);

                groupPermissionParams = new Object[] { brokerDeleteMemberRecordsOperationId, groupId };
                jdbc.execute(insertGroupPermission, groupPermissionParams);
            } catch (final Exception e) {
                e.printStackTrace();
            }
        }

        // Remove remarks permissions
        final String deletePermissions = "delete from permissions where operation_id in (?, ?, ?, ?, ?, ?)";
        final Object[] deletePermissionsParams = new Object[] { adminViewMemberRemarksOperationId, adminManageMemberRemarksOperationId, adminViewAdminRemarksOperationId, adminManageAdminRemarksOperationId, brokerViewMemberRemarksOperationId, brokerManageMemberRemarksOperationId };
        jdbc.execute(deletePermissions, deletePermissionsParams);

        // Remove remarks operations
        final String deleteOperations = "delete from operations where id in (?, ?, ?, ?, ?, ?)";
        final Object[] deleteOperationsParams = new Object[] { adminViewMemberRemarksOperationId, adminManageMemberRemarksOperationId, adminViewAdminRemarksOperationId, adminManageAdminRemarksOperationId, brokerViewMemberRemarksOperationId, brokerManageMemberRemarksOperationId };
        jdbc.execute(deleteOperations, deleteOperationsParams);

        // Remove remarks modules
        final Long adminAdminRemarksModuleId = jdbc.readScalarAsLong("select id from modules where name='adminAdminRemarks'");
        final Long adminMemberRemarksModuleId = jdbc.readScalarAsLong("select id from modules where name='adminMemberRemarks'");
        final Long brokerMemberRemarksModuleId = jdbc.readScalarAsLong("select id from modules where name='brokerRemarks'");
        final String deleteModules = "delete from modules where id in (?, ?, ?)";
        final Object[] deleteModulesParams = new Object[] { adminAdminRemarksModuleId, adminMemberRemarksModuleId, brokerMemberRemarksModuleId };
        jdbc.execute(deleteModules, deleteModulesParams);

        // Search remarks of type 'general'
        final String remarksQuery = "select subject_id, writer_id, date, comments from remarks where subclass='R'";
        final ResultSet rsRemarks = jdbc.query(remarksQuery);
        while (rsRemarks.next()) {
            final Long elementId = rsRemarks.getLong("subject_id");
            final Long byId = rsRemarks.getLong("writer_id");
            final Timestamp date = rsRemarks.getTimestamp("date");
            final String comments = rsRemarks.getString("comments");

            // Create a new member record for each remark
            final String insertMemberRecord = "insert into member_records (member_record_type_id, element_id, by_id, date) values (?, ?, ?, ?)";
            final Object[] memberRecordParams = new Object[] { memberRecordTypeId, elementId, byId, date };
            jdbc.execute(insertMemberRecord, memberRecordParams);
            final Long memberRecordId = jdbc.readScalarAsLong("select last_insert_id()");

            // Create a new custom field value for each remark
            final String insertComments = "insert into custom_field_values (subclass, field_id, string_value, member_record_id) values ('record', ?, ?, ?)";
            final Object[] commentsParams = new Object[] { commentsCustomFieldId, comments, memberRecordId };
            jdbc.execute(insertComments, commentsParams);
        }
        closeQuietly(rsRemarks);

        // Remove general remarks
        final String removeGeneralRemarks = "delete from remarks where subclass='R'";
        jdbc.execute(removeGeneralRemarks);
    }
}
