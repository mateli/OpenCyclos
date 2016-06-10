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

import java.sql.ResultSet;
import java.sql.SQLException;

import nl.strohalm.cyclos.setup.UntraceableMigration;
import nl.strohalm.cyclos.utils.HashHandler;
import nl.strohalm.cyclos.utils.JDBCWrapper;

import org.apache.commons.lang.StringUtils;

/**
 * Migrates the existing passwords, applying the SHA-256 hash over the MD5 hash
 * @author luis
 */
@SuppressWarnings("deprecation")
public class PasswordHashMigration implements UntraceableMigration {

    public void execute(final JDBCWrapper jdbc) throws SQLException {
        final String select = "select id, password, transaction_password from users";
        final String update = "update users set password = ?, transaction_password = ? where id = ?";
        final ResultSet rs = jdbc.query(select);
        try {
            while (rs.next()) {
                final long id = rs.getLong("id");
                final String password = StringUtils.trimToNull(rs.getString("password"));
                final String transactionPassword = StringUtils.trimToNull(rs.getString("transaction_password"));
                // When no password is defined, skip this user
                if (password == null && transactionPassword == null) {
                    continue;
                }
                final String newPassword = password == null ? null : HashHandler.sha2(password.toUpperCase());
                final String newTransactionPassword = transactionPassword == null ? null : HashHandler.sha2(transactionPassword.toUpperCase());
                jdbc.execute(update, newPassword, newTransactionPassword, id);
            }
        } finally {
            JDBCWrapper.closeQuietly(rs);
        }
    }

}
