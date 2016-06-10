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
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;

import nl.strohalm.cyclos.setup.UntraceableMigration;
import nl.strohalm.cyclos.utils.JDBCWrapper;
import nl.strohalm.cyclos.utils.Period;
import nl.strohalm.cyclos.utils.TimePeriod;
import nl.strohalm.cyclos.utils.TimePeriod.Field;
import nl.strohalm.cyclos.utils.conversion.CoercionHelper;

/**
 * Fixes periods of scheduled fees which were generated incorrectly
 * @author luis
 */
@SuppressWarnings("deprecation")
public class AccountFeeLogPeriodMigration implements UntraceableMigration {

    public void execute(final JDBCWrapper jdbc) throws SQLException {
        // March 12 was the launch date of 3.5_RC1. We don't want to mess with fees created before that
        final Timestamp startingAt = CoercionHelper.coerce(Timestamp.class, new GregorianCalendar(2009, Calendar.MARCH, 12));
        final ResultSet fees = jdbc.query("select * from account_fees where run_mode = ? and enabled_since >= ?", "S", startingAt);
        try {
            while (fees.next()) {
                final long feeId = fees.getLong("id");
                final int recurrenceNumber = fees.getInt("recurrence_number");
                final Field recurrenceField = Field.findByCalendarField(fees.getInt("recurrence_field"));
                if (recurrenceField.compareTo(Field.WEEKS) <= 0) {
                    // Recurrences of weeks, days or less don't need to be fixed
                    continue;
                }
                final TimePeriod recurrence = new TimePeriod(recurrenceNumber, recurrenceField);
                final ResultSet logs = jdbc.updatableQuery("select * from account_fee_logs where account_fee_id = ?", feeId);
                try {
                    while (logs.next()) {
                        final Calendar begin = CoercionHelper.coerce(Calendar.class, logs.getTimestamp("begin_date"));
                        Period period = recurrence.currentPeriod(begin);
                        final Calendar executionDate = CoercionHelper.coerce(Calendar.class, logs.getTimestamp("date"));
                        boolean inconsistent = false;
                        if (period.includes(executionDate)) {
                            // INCONSISTENT!!! - The execution date is inside the charge period, which is caused by a fixed bug
                            period = recurrence.periodEndingAt(period.getBegin());
                            inconsistent = true;
                        } else if (begin.get(Calendar.DAY_OF_MONTH) != 1) {
                            // INCONSISTENT!!! - A period starting in a day other than the first day in the period, which is caused by a fixed bug
                            inconsistent = true;
                        }
                        if (inconsistent) {
                            logs.updateTimestamp("begin_date", CoercionHelper.coerce(Timestamp.class, period.getBegin()));
                            logs.updateTimestamp("end_date", CoercionHelper.coerce(Timestamp.class, period.getEnd()));
                            logs.updateRow();
                        }
                    }
                } finally {
                    JDBCWrapper.closeQuietly(logs);
                }
            }
        } finally {
            JDBCWrapper.closeQuietly(fees);
        }
    }

}
