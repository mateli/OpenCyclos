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
package nl.strohalm.cyclos.setup.migrations.version3_7;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

import nl.strohalm.cyclos.CyclosConfiguration;
import nl.strohalm.cyclos.entities.settings.Setting;
import nl.strohalm.cyclos.setup.TraceableMigration;
import nl.strohalm.cyclos.utils.JDBCWrapper;

import org.apache.commons.lang.StringUtils;

/**
 * A migration that copies the root url from properties into the database
 * @author luis
 */
public class RootUrlMigration implements TraceableMigration {

    @Override
    public int execute(final JDBCWrapper jdbc) throws SQLException {
        String rootUrl;
        try {
            Properties properties = CyclosConfiguration.getCyclosProperties();
            rootUrl = StringUtils.trimToNull(properties.getProperty("cyclos.host.url"));
        } catch (IOException e) {
            throw new SQLException(e);
        }
        if (rootUrl == null) {
            return 0;
        }
        return jdbc.execute(
                "insert into settings (type, name, value) values (?, ?, ?)",
                Setting.Type.LOCAL.getValue(), "rootUrl", rootUrl);
    }

}
