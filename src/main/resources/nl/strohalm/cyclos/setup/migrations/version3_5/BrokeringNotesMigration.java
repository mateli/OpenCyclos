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
import nl.strohalm.cyclos.utils.JDBCWrapper;

/**
 * Migrates the field "notes" from the brokering to the contact
 * @author Jefferson Magno
 */
@SuppressWarnings("deprecation")
public class BrokeringNotesMigration implements UntraceableMigration {

    public void execute(final JDBCWrapper jdbc) throws SQLException {
        final String selectBrokeringNotes = "select broker_id, brokered_id, notes from brokerings where notes is not null order by start_date desc";
        final String selectContact = "select id, notes from contacts where owner_id = ? and contact_id = ?";
        final String updateContact = "update contacts set notes = ? where id = ?";
        final String insertContact = "insert into contacts (owner_id, contact_id, notes) values (?, ?, ?)";

        final ResultSet rsBrokeringNotes = jdbc.query(selectBrokeringNotes);
        while (rsBrokeringNotes.next()) {
            final Long brokerId = rsBrokeringNotes.getLong("broker_id");
            final Long brokeredId = rsBrokeringNotes.getLong("brokered_id");
            final String notes = rsBrokeringNotes.getString("notes");
            final Object[] selectContactParams = new Object[] { brokerId, brokeredId };
            final ResultSet rsContact = jdbc.query(selectContact, selectContactParams);

            if (rsContact.next()) {
                // Update current contact entry if it already exists
                final Long contactId = rsContact.getLong("id");
                String contactNotes = rsContact.getString("notes");
                contactNotes = contactNotes + "\n\n" + notes;

                final Object[] updateContactParams = new Object[] { contactNotes, contactId };
                jdbc.execute(updateContact, updateContactParams);
            } else {
                // Create a contact entry
                final Object[] insertContactParams = new Object[] { brokerId, brokeredId, notes };
                jdbc.execute(insertContact, insertContactParams);
            }
        }
    }

}
