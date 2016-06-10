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

import nl.strohalm.cyclos.setup.UntraceableMigration;
import nl.strohalm.cyclos.utils.JDBCWrapper;

/**
 * A migration class which will update transfers which have been charged by by setting the chargeback id
 * 
 * @author luis
 */
@SuppressWarnings("deprecation")
public class SetChargebacksMigration implements UntraceableMigration {

    public void execute(final JDBCWrapper jdbc) throws SQLException {
        final ResultSet chargebacks = jdbc.query("select t.id, t.chargeback_of_id from transfers t where t.chargeback_of_id is not null");
        try {
            while (chargebacks.next()) {
                final long chargebackId = chargebacks.getLong("id");
                final long originalId = chargebacks.getLong("t.chargeback_of_id");
                jdbc.execute("update transfers set chargedback_by_id = ? where id = ?", chargebackId, originalId);
            }
        } finally {
            JDBCWrapper.closeQuietly(chargebacks);
        }
    }

}
