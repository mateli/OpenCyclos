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

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;

import nl.strohalm.cyclos.entities.accounts.fees.transaction.BrokerCommission.When;
import nl.strohalm.cyclos.setup.UntraceableMigration;
import nl.strohalm.cyclos.utils.DateHelper;
import nl.strohalm.cyclos.utils.JDBCWrapper;
import nl.strohalm.cyclos.utils.conversion.CoercionHelper;

/**
 * Creates a brokering commission status record for each pair (brokering / brokering commission)
 * @author Jefferson Magno
 */
@SuppressWarnings("deprecation")
public class BrokeringCommissionStatusMigration implements UntraceableMigration {

    @Override
    public void execute(final JDBCWrapper jdbc) throws SQLException {
        final String selectBrokerCommissions = "select id, when_apply, when_count, amount, amount_type from transaction_fees where subclass = 'B'";
        final String selectBrokerings = "select id, broker_id, start_date, commission_end_date from brokerings";
        final String selectFees = "select sum(t.amount) as total_sum, count(*) as total_count, max(t.date) as max_date from transfers t inner join accounts a on t.to_account_id = a.id where t.transaction_fee_id = ? and a.member_id = ?";
        final String insertBrokeringCommissionStatus = "insert into brokering_commission_status (creation_date, brokering_id, broker_commission_id, start_date, end_date, expiry_date, total_count, total_amount, amount, amount_type, when_apply) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        final Timestamp today = new Timestamp(DateHelper.truncate(Calendar.getInstance()).getTimeInMillis());

        // Search for broker commissions
        final ResultSet rsBrokerCommissions = jdbc.query(selectBrokerCommissions);
        while (rsBrokerCommissions.next()) {

            final Long brokerCommissionId = rsBrokerCommissions.getLong("id");
            final String whenString = rsBrokerCommissions.getString("when_apply");
            final When when = CoercionHelper.coerce(When.class, whenString);
            final Integer whenCount = rsBrokerCommissions.getInt("when_count");
            final String feeAmount = rsBrokerCommissions.getString("amount");
            final String feeAmountType = rsBrokerCommissions.getString("amount_type");

            // Search for brokerings
            final ResultSet rsBrokerings = jdbc.query(selectBrokerings);
            while (rsBrokerings.next()) {
                final Long brokeringId = rsBrokerings.getLong("id");
                final Long brokerId = rsBrokerings.getLong("broker_id");
                final Timestamp brokeringStartDate = rsBrokerings.getTimestamp("start_date");
                Timestamp brokeringCommissionEndDate = rsBrokerings.getTimestamp("commission_end_date");

                // Search for paid fees
                final Object[] feesParams = new Object[] { brokerCommissionId, brokerId };
                final ResultSet rsFees = jdbc.query(selectFees, feesParams);
                rsFees.next();
                BigDecimal amount = rsFees.getBigDecimal("total_sum");
                if (amount == null) {
                    amount = new BigDecimal(0.0);
                }
                final Integer count = rsFees.getInt("total_count");
                final Timestamp maxDate = rsFees.getTimestamp("max_date");
                closeQuietly(rsFees);
                Timestamp expiryDate = null;

                if (when == When.COUNT) {
                    if (count >= whenCount && brokeringCommissionEndDate == null) {
                        brokeringCommissionEndDate = maxDate;
                    }
                } else if (when == When.DAYS) {
                    expiryDate = calculateExpiryDate(brokeringStartDate, count);

                    if (today.compareTo(expiryDate) > 0 && brokeringCommissionEndDate == null) {
                        brokeringCommissionEndDate = expiryDate;
                    }
                }

                // Insert brokering commission status
                final Object[] brokeringCommissionStatusParams = new Object[] { Calendar.getInstance(), brokeringId, brokerCommissionId, brokeringStartDate, brokeringCommissionEndDate, expiryDate, count, amount, feeAmount, feeAmountType, when.getValue() };
                jdbc.execute(insertBrokeringCommissionStatus, brokeringCommissionStatusParams);
            }
            closeQuietly(rsBrokerings);

        }
        closeQuietly(rsBrokerCommissions);

        // Drop brokering commission end date column
        final String dropBrokeringCommissionEndDate = "alter table brokerings drop column commission_end_date";
        jdbc.execute(dropBrokeringCommissionEndDate);

        // Drop
        final String dropBrokeringNotes = "alter table brokerings drop column notes";
        jdbc.execute(dropBrokeringNotes);
    }

    private Timestamp calculateExpiryDate(final Timestamp startDate, final int days) {
        final Calendar date = Calendar.getInstance();
        date.setTime(startDate);
        date.add(Calendar.DATE, days);
        return new Timestamp(date.getTimeInMillis());
    }

}
