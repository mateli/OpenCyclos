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

import nl.strohalm.cyclos.access.Module;
import nl.strohalm.cyclos.access.OperatorPermission;
import nl.strohalm.cyclos.access.Permission;
import nl.strohalm.cyclos.setup.TraceableMigration;
import nl.strohalm.cyclos.utils.JDBCWrapper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Changes the permissions related tables according to the general security review It drops the modules and operations tables and alter the
 * permissions table removing the operation_id FK and adding a new column with permission name.
 * @author ameyer
 */
public class ChangePermissionSchemeMigration implements TraceableMigration {
    private static final Log LOG = LogFactory.getLog(ChangePermissionSchemeMigration.class);

    @Override
    public int execute(final JDBCWrapper jdbc) throws SQLException {
        // add the permission column (temporarily nullable)
        jdbc.execute("ALTER TABLE permissions ADD COLUMN permission varchar(100)");

        // update the permissions table setting the permission's name
        ResultSet rs = jdbc.query("SELECT id, message_key FROM operations");
        int updatedRows = 0;
        while (rs.next()) {
            String messageKey = rs.getString("message_key");
            int operationId = rs.getInt("id");

            Permission p = findPermission(messageKey);
            if (p != null) {
                updatedRows += jdbc.execute("update permissions set permission=? where operation_id=?", p.getQualifiedName(), operationId);
            }
        }

        // Now, we need to remove all rows from permissions where the permission no longer exists
        updatedRows += jdbc.execute("delete from permissions where permission is null or permission = ''");

        // All done - make the permission column not null
        jdbc.execute("ALTER TABLE permissions modify permission varchar(100) not null");

        LOG.info("Dropping table modules...");
        jdbc.execute("ALTER TABLE operations DROP FOREIGN KEY FK3FD7EC7FE57DC");
        jdbc.execute("DROP TABLE modules");

        LOG.info("Dropping table operations...");
        jdbc.execute("ALTER TABLE permissions DROP FOREIGN KEY FK4392F48486D11B78");
        jdbc.execute("ALTER TABLE permissions DROP column operation_id");
        jdbc.execute("DROP TABLE operations");

        return updatedRows;
    }

    private Permission findPermission(final String messageKey) {
        // Handle permissions which have changed naming
        if (messageKey.equals("permission.operatorPayments.externalMakePayment")) {
            return OperatorPermission.PAYMENTS_POSWEB_MAKE_PAYMENT;
        } else if (messageKey.equals("permission.operatorPayments.externalReceivePayment")) {
            return OperatorPermission.PAYMENTS_POSWEB_RECEIVE_PAYMENT;
        }
        for (Module m : Module.values()) {
            for (Permission p : m.getPermissions()) {
                String tmp = "permission." + p.getValue();
                if (tmp.equals(messageKey)) {
                    return p;
                }
            }
        }
        return null;
    }

}
