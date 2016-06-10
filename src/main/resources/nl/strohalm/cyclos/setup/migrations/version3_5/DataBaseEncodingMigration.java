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

import java.sql.SQLException;
import java.util.List;

import nl.strohalm.cyclos.setup.UntraceableMigration;
import nl.strohalm.cyclos.utils.JDBCWrapper;

/**
 * Migration of all tables to unicode character set (utf8)
 * 
 * @author luis
 */
@SuppressWarnings("deprecation")
public class DataBaseEncodingMigration implements UntraceableMigration {

    public void execute(final JDBCWrapper jdbc) throws SQLException {
        jdbc.commit();
        jdbc.execute("alter database character set utf8");
        final List<String> tables = jdbc.readScalarAsStringList("show tables");
        for (final String table : tables) {
            jdbc.execute("alter table " + table + " convert to character set utf8");
        }
    }

}
