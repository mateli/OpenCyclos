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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import nl.strohalm.cyclos.entities.customization.fields.PaymentCustomField;
import nl.strohalm.cyclos.setup.UntraceableMigration;
import nl.strohalm.cyclos.utils.JDBCWrapper;

import org.apache.commons.lang.StringUtils;

/**
 * Migrates loan custom fields into payment custom fields
 * @author luis
 */
@SuppressWarnings("deprecation")
public class LoanCustomFieldMigration implements UntraceableMigration {

    public static void main(final String[] args) throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
        final Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/cyclos3_new", "root", "");
        try {
            conn.setAutoCommit(false);
            final JDBCWrapper jdbc = new JDBCWrapper(conn);
            new LoanCustomFieldMigration().execute(jdbc);
            conn.commit();
        } catch (final Exception e) {
            conn.rollback();
            throw e;
        } finally {
            conn.close();
        }
    }

    public void execute(final JDBCWrapper jdbc) throws SQLException {

        final ResultSet loanCustomFields = jdbc.query("select * from custom_fields where subclass = ?", "loan");
        try {
            while (loanCustomFields.next()) {
                final long loanCustomFieldId = loanCustomFields.getLong("id");

                // Map by transfer type the new field id
                final Map<Long, Long> loanCustomFieldMap = new HashMap<Long, Long>();

                // Map by old custom field id the old / new custom field possible values
                final Map<Long, Long> possibleValuesMap = new HashMap<Long, Long>();

                // For each loan type, create a custom field for it
                final ResultSet loanTransferTypes = jdbc.query("select id from transfer_types where loan_type is not null");
                try {
                    while (loanTransferTypes.next()) {
                        final long transferTypeId = loanTransferTypes.getLong("id");

                        final Map<String, Object> values = new LinkedHashMap<String, Object>();
                        values.put("subclass", "pmt");
                        values.put("internal_name", loanCustomFields.getString("internal_name"));
                        values.put("name", loanCustomFields.getString("name"));
                        values.put("order_number", loanCustomFields.getString("order_number"));
                        values.put("type", loanCustomFields.getString("type"));
                        values.put("control", loanCustomFields.getString("control"));
                        values.put("size", loanCustomFields.getString("size"));
                        values.put("val_required", loanCustomFields.getBoolean("val_required"));
                        values.put("val_unique", loanCustomFields.getBoolean("val_unique"));
                        values.put("val_min_length", loanCustomFields.getString("val_min_length"));
                        values.put("val_max_length", loanCustomFields.getString("val_max_length"));
                        values.put("all_selected_label", loanCustomFields.getString("all_selected_label"));
                        values.put("pattern", loanCustomFields.getString("pattern"));
                        values.put("description", loanCustomFields.getString("description"));
                        values.put("parent_id", loanCustomFields.getString("parent_id"));
                        values.put("transfer_type_id", transferTypeId);
                        values.put("payment_search_access", loanCustomFields.getBoolean("loan_show_in_search") ? PaymentCustomField.Access.BOTH_ACCOUNTS.getValue() : PaymentCustomField.Access.NONE.getValue());
                        values.put("payment_list_access", loanCustomFields.getBoolean("loan_show_in_payment_results") ? PaymentCustomField.Access.BOTH_ACCOUNTS.getValue() : PaymentCustomField.Access.NONE.getValue());

                        final String[] placeHolders = new String[values.size()];
                        Arrays.fill(placeHolders, "?");

                        jdbc.execute("insert into custom_fields (" + StringUtils.join(values.keySet().iterator(), ',') + ") values (" + StringUtils.join(placeHolders, ',') + ")", values.values().toArray());

                        // Add the new id to the mapping
                        final long newCustomFieldId = jdbc.readScalarAsLong("select last_insert_id()");
                        loanCustomFieldMap.put(transferTypeId, newCustomFieldId);

                        // Insert the new the possible values
                        final ResultSet possibleValues = jdbc.query("select * from custom_field_possible_values where field_id = ?", loanCustomFieldId);
                        try {
                            while (possibleValues.next()) {
                                final long oldPossibleValueId = possibleValues.getLong("id");
                                final String value = possibleValues.getString("value");

                                jdbc.execute("insert into custom_field_possible_values (field_id, value) values (?, ?)", newCustomFieldId, value);
                                final long newPossibleValueId = jdbc.readScalarAsLong("select last_insert_id()");
                                possibleValuesMap.put(oldPossibleValueId, newPossibleValueId);
                            }
                        } finally {
                            JDBCWrapper.closeQuietly(possibleValues);
                        }
                    }
                } finally {
                    JDBCWrapper.closeQuietly(loanTransferTypes);
                }

                // Replace the field values to the new field
                final ResultSet fieldValues = jdbc.query("select fv.*, t.type_id as transfer_type_id from custom_field_values fv inner join loans l on fv.loan_id = l.id inner join transfers t on l.transfer_id = t.id where fv.field_id = ?", loanCustomFieldId);
                try {
                    while (fieldValues.next()) {
                        final long fieldValueId = fieldValues.getLong("id");
                        final long loanId = fieldValues.getLong("loan_id");
                        final long newCustomFieldId = loanCustomFieldMap.get(fieldValues.getLong("transfer_type_id"));
                        final Long newPossibleValueId = possibleValuesMap.get(fieldValues.getLong("possible_value_id"));

                        // Get the loan's transfer
                        final long transferId = jdbc.readScalarAsLong("select transfer_id from loans where id=?", loanId);

                        jdbc.execute("update custom_field_values set subclass=?, loan_id=null, transfer_id=?, field_id=?, possible_value_id=? where id=?", "pmt", transferId, newCustomFieldId, newPossibleValueId, fieldValueId);
                    }
                } finally {
                    JDBCWrapper.closeQuietly(fieldValues);
                }

                // Remove the old possible values
                jdbc.execute("delete from custom_field_possible_values where field_id=?", loanCustomFieldId);

                // Remove the old custom field
                jdbc.execute("delete from custom_fields where id=?", loanCustomFieldId);
            }
        } finally {
            JDBCWrapper.closeQuietly(loanCustomFields);
        }

        // Drop the unused columns
        try {
            jdbc.execute("alter table custom_field_values drop foreign key FK8AE18A15F9B21025");
        } catch (final Exception e) {
            // ignore
        }
        try {
            jdbc.execute("alter table custom_field_values drop column loan_id");
        } catch (final Exception e) {
            // ignore
        }
        try {
            jdbc.execute("alter table custom_fields drop column loan_show_in_search");
        } catch (final Exception e) {
            // ignore
        }
        try {
            jdbc.execute("alter table custom_fields drop column loan_show_in_payment_results");
        } catch (final Exception e) {
            // ignore
        }
    }
}
