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

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

import nl.strohalm.cyclos.entities.accounts.transactions.Payment;
import nl.strohalm.cyclos.setup.TraceableMigration;
import nl.strohalm.cyclos.utils.JDBCWrapper;

import org.apache.commons.lang.time.DateUtils;

/**
 * A migration class which replaces the old AccountStatus by the new ClosedAccountBalance approach
 * 
 * @author luis
 */
public class ClosedAccountBalancesMigration implements TraceableMigration {

    @Override
    public int execute(final JDBCWrapper jdbc) throws SQLException {

        // First, ensure the account status still exists, ie, not already migrated
        ResultSet accountStatusExists = null;
        try {
            accountStatusExists = jdbc.query("select 1 from account_status limit 1");
        } catch (final SQLException e) {
            // The already does not exists. Exit.
            return 0;
        } finally {
            JDBCWrapper.closeQuietly(accountStatusExists);
        }

        // Populate the account limit logs from both the account status and the pending account status tables
        jdbc.execute("insert into account_limit_logs " +
                " (account_id, date, by_id, credit_limit, upper_credit_limit) " +
                " select account_id, date, credit_limit_by_id, credit_limit, upper_credit_limit" +
                " from account_status" +
                " where credit_limit_by_id is not null");

        jdbc.execute("insert into account_limit_logs " +
                " (account_id, date, by_id, credit_limit, upper_credit_limit) " +
                " select account_id, date, by_id, lower_limit, upper_limit" +
                " from pending_account_status" +
                " where type = 'lim'");

        // Populate the amount_reservations table from pending transfers, scheduled payments which reserves the total amount and their installments
        jdbc.execute("insert into amount_reservations" +
                " (subclass, account_id, date, amount, transfer_id)" +
                " select 'P', from_account_id, date, amount, id " +
                " from transfers t " +
                " where t.status = ? ", Payment.Status.PENDING.getValue());
        jdbc.execute("insert into amount_reservations" +
                " (subclass, account_id, date, amount, scheduled_payment_id)" +
                " select 'S', from_account_id, date, amount, id " +
                " from scheduled_payments " +
                " where reserve_amount = true ");
        jdbc.execute("insert into amount_reservations" +
                " (subclass, account_id, date, amount, transfer_id)" +
                " select 'I', t.from_account_id, ifnull(t.process_date, t.date), -t.amount, t.id " +
                " from transfers t inner join scheduled_payments sp on t.scheduled_payment_id = sp.id" +
                " where sp.reserve_amount = true and t.status <> ? ", Payment.Status.SCHEDULED.getValue());

        // Iterate each account
        int results = 0;
        final ResultSet accounts = jdbc.query("select id, creation_date from accounts");
        try {
            while (accounts.next()) {
                final long accountId = accounts.getLong("id");
                final Date creationDate = new Date(DateUtils.truncate(accounts.getTimestamp("creation_date"), Calendar.DAY_OF_MONTH).getTime());
                // Get, by day, each diff, either for balance or reserved amount
                ResultSet diffs = jdbc.query(" select * from ( " +
                        "     select 'B' as type, b.date, b.balance as diff" +
                        "     from ( " +
                        "         select date(date) as date, sum(amount) as balance " +
                        "         from ( " +
                        "             select t.process_date as date, " +
                        "             case when t.chargeback_of_id is null then " +
                        "                 case when t.from_account_id = ? then -t.amount else t.amount end " +
                        "             else " +
                        "                 case when t.to_account_id = ? then t.amount else -t.amount end " +
                        "             end as amount " +
                        "             from transfers t " +
                        "             where (t.from_account_id = ? or t.to_account_id = ?) " +
                        "               and t.process_date is not null " +
                        "         ) t " +
                        "         group by date(date) " +
                        "     ) b " +
                        "     union " +
                        "     select 'R', date(r.date), sum(r.amount) " +
                        "     from amount_reservations r " +
                        "     where r.account_id = ? " +
                        "     group by date(r.date) " +
                        " ) t " +
                        " where date < current_date() " +
                        " order by date", accountId, accountId, accountId, accountId, accountId);
                Date lastDate = creationDate;
                double balance = 0;
                double reserved = 0;
                try {
                    boolean hasData = false;
                    while (diffs.next()) {
                        hasData = true;
                        boolean isBalance = "B".equals(diffs.getString("type"));
                        Date date = diffs.getDate("date");
                        double diff = diffs.getDouble("diff");
                        if (!lastDate.equals(date)) {
                            // Insert a closed balance when the date changes
                            results += jdbc.execute("insert into closed_account_balances (date, account_id, balance, reserved) values (?, ?, ?, ?)", nextDay(lastDate), accountId, balance, reserved);
                        }
                        if (isBalance) {
                            balance += diff;
                        } else {
                            reserved += diff;
                        }
                        lastDate = date;
                    }
                    if (hasData) {
                        // There is a last closed balance to insert
                        results += jdbc.execute("insert into closed_account_balances (date, account_id, balance, reserved) values (?, ?, ?, ?)", nextDay(lastDate), accountId, balance, reserved);
                    }
                } finally {
                    JDBCWrapper.closeQuietly(diffs);
                }
                // Set the last closing date
                jdbc.execute("update accounts set last_closing_date = ? where id = ?", lastDate, accountId);
            }
        } finally {
            JDBCWrapper.closeQuietly(accounts);
        }

        // Now it is safe to drop the account_status table
        jdbc.execute("drop table account_status");
        jdbc.execute("drop table pending_account_status");

        return results;
    }

    private Date nextDay(final Date date) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        return new Date(calendar.getTimeInMillis());
    }
}
