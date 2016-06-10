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

import nl.strohalm.cyclos.setup.UntraceableMigration;
import nl.strohalm.cyclos.utils.JDBCWrapper;

/**
 * Populates the new table of group history logs based on data from group remarks
 * @author Jefferson Magno
 */
@SuppressWarnings("deprecation")
public class GroupHistoryLogMigration implements UntraceableMigration {

    public void execute(final JDBCWrapper jdbc) throws SQLException {
        final String elementsQuery = "select id, creation_date, group_id from members";
        final String groupRemarksQuery = "select r.old_group_id, r.new_group_id, r.date from remarks r where r.subclass='G' and r.subject_id = ? order by r.date";
        final String insertGroupHistoryLog = "insert into group_history_logs (element_id, group_id, start_date) values (?, ?, ?)";
        final String groupHistoryLogsQuery = "select * from group_history_logs where element_id = ? order by start_date";
        final ResultSet rsElements = jdbc.query(elementsQuery);
        while (rsElements.next()) {
            final Long elementId = rsElements.getLong("id");
            final Timestamp creationDate = rsElements.getTimestamp("creation_date");
            final Long currentGroupId = rsElements.getLong("group_id");

            // Create the initial group remark
            Long firstGroupId = null;
            ResultSet rsGroupRemarks = jdbc.query(groupRemarksQuery, elementId);
            if (rsGroupRemarks.next()) {
                /*
                 * The user have group remark(s) To create the initial group remark, we must use the old group of the first group remark currently
                 * available
                 */
                firstGroupId = rsGroupRemarks.getLong("old_group_id");
            } else {
                /*
                 * The user don't have group remarks. To create the initial group remark, we must use the current group
                 */
                firstGroupId = currentGroupId;
            }
            closeQuietly(rsGroupRemarks);
            Object[] insertGroupHistoryLogParameters = new Object[] { elementId, firstGroupId, creationDate };
            jdbc.execute(insertGroupHistoryLog, insertGroupHistoryLogParameters);

            // Create one group history log for each group remark of the user
            rsGroupRemarks = jdbc.query(groupRemarksQuery, elementId);
            while (rsGroupRemarks.next()) {
                final Long groupId = rsGroupRemarks.getLong("new_group_id");
                final Timestamp start = rsGroupRemarks.getTimestamp("date");
                insertGroupHistoryLogParameters = new Object[] { elementId, groupId, start };
                jdbc.execute(insertGroupHistoryLog, insertGroupHistoryLogParameters);
            }
            closeQuietly(rsGroupRemarks);

            // Update the group history logs with the end dates
            final ResultSet rsGroupHistoryLogs = jdbc.updatableQuery(groupHistoryLogsQuery, elementId);
            // Moves the cursor to the first row
            rsGroupHistoryLogs.next();
            // If the user has more than one group history log, update the previous 'end' with the current 'start'
            while (rsGroupHistoryLogs.next()) {
                final Timestamp end = rsGroupHistoryLogs.getTimestamp("start_date");
                rsGroupHistoryLogs.previous();
                rsGroupHistoryLogs.updateTimestamp("end_date", end);
                rsGroupHistoryLogs.updateRow();
                rsGroupHistoryLogs.next();
            }
            closeQuietly(rsGroupHistoryLogs);
        }
        closeQuietly(rsElements);
    }
}
