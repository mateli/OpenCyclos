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
package nl.strohalm.cyclos.setup.migrations.version3_6;

import java.sql.ResultSet;
import java.sql.SQLException;

import nl.strohalm.cyclos.setup.TraceableMigration;
import nl.strohalm.cyclos.utils.JDBCWrapper;
import nl.strohalm.cyclos.utils.StringHelper;

/**
 * Migration used to remove masks on field values
 * 
 * @author luis
 */
public class RemoveMaskFromCustomFieldsMigration implements TraceableMigration {

    public int execute(final JDBCWrapper jdbc) throws SQLException {
        String sql = " select fv.id, fv.string_value, f.pattern";
        sql += " from custom_field_values fv";
        sql += " inner join custom_fields f on fv.field_id = f.id";
        sql += " where f.pattern is not null";
        sql += "   and fv.string_value is not null";
        sql += "   and length(fv.string_value) > 0";
        final ResultSet values = jdbc.query(sql);
        try {
            int modifiedRows = 0;
            while (values.next()) {
                final String value = values.getString("string_value");
                final long id = values.getLong("id");
                final String mask = values.getString("pattern");
                final String newValue = StringHelper.removeMask(mask, value);
                if (!value.equals(newValue)) {
                    modifiedRows += jdbc.execute("update custom_field_values set string_value = ? where id = ?", newValue, id);
                }
            }
            return modifiedRows;
        } finally {
            JDBCWrapper.closeQuietly(values);
        }
    }
}
