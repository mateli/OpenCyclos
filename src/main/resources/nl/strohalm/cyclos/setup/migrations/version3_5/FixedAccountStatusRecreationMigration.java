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

import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.entities.settings.LocalSettings.Precision;
import nl.strohalm.cyclos.setup.UntraceableMigration;
import nl.strohalm.cyclos.utils.JDBCWrapper;
import nl.strohalm.cyclos.utils.conversion.CoercionHelper;

/**
 * Populates the account_status and account_fee_charges tables
 * @author luis
 */
@SuppressWarnings("deprecation")
public class FixedAccountStatusRecreationMigration implements UntraceableMigration {

    public void execute(final JDBCWrapper jdbc) throws SQLException {
        final String accountQuery = "select id, subclass, credit_limit, upper_credit_limit from accounts";
        final String transferQuery = "select id, ifnull(process_date, date) as date, amount, status, from_account_id, to_account_id, parent_id, account_fee_log_id, chargeback_of_id from transfers where status in ('O', 'P') and (from_account_id = ? or to_account_id = ?) order by ifnull(process_date, date), id";
        final String initialStatusInsert = "insert into account_status (subclass, account_id, date, credit_limit, upper_credit_limit) select subclass, id, creation_date, credit_limit, upper_credit_limit from accounts";
        final String statusInsert = "insert into account_status (subclass, account_id, date, root_credits_count, root_credits_amount, root_debits_count, root_debits_amount, nested_credits_count, nested_credits_amount, nested_debits_count, nested_debits_amount, pending_credits_amount, pending_credits_count, pending_debits_amount, pending_debits_count, credit_limit, upper_credit_limit, transfer_id) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        final ResultSet rsAccounts = jdbc.query(accountQuery);

        // Ensure the account status table is empty
        jdbc.execute("delete from account_status");

        // Read the local settings
        final LocalSettings settings = new LocalSettings();
        final Precision precision = CoercionHelper.coerce(Precision.class, jdbc.readScalarAsString("select value from settings where name=?", "precision"));
        if (precision != null) {
            settings.setPrecision(precision);
        }

        // Insert an account status for every account creation
        jdbc.execute(initialStatusInsert);

        // Get each account
        while (rsAccounts.next()) {
            final long accountId = rsAccounts.getLong("id");
            final String subclass = rsAccounts.getString("subclass");
            final BigDecimal creditLimit = settings.round(rsAccounts.getBigDecimal("credit_limit"));
            final BigDecimal upperCreditLimit = settings.round(rsAccounts.getBigDecimal("upper_credit_limit"));
            BigDecimal rootCreditsAmount = BigDecimal.ZERO;
            int rootCreditsCount = 0;
            BigDecimal rootDebitsAmount = BigDecimal.ZERO;
            int rootDebitsCount = 0;
            BigDecimal nestedCreditsAmount = BigDecimal.ZERO;
            int nestedCreditsCount = 0;
            BigDecimal nestedDebitsAmount = BigDecimal.ZERO;
            int nestedDebitsCount = 0;
            //
            BigDecimal pendingCreditsAmount = BigDecimal.ZERO;
            int pendingCreditsCount = 0;
            BigDecimal pendingDebitsAmount = BigDecimal.ZERO;
            int pendingDebitsCount = 0;

            // Insert a new status for every transfer from or to that account
            final ResultSet rsTransfers = jdbc.query(transferQuery, accountId, accountId);
            while (rsTransfers.next()) {
                final long transferId = rsTransfers.getLong("id");
                final Timestamp date = rsTransfers.getTimestamp("date");
                final BigDecimal amount = settings.round(rsTransfers.getBigDecimal("amount")).abs();
                final long fromAccountId = rsTransfers.getLong("from_account_id");
                Long chargebackOfId = rsTransfers.getLong("chargeback_of_id");
                if (rsTransfers.wasNull()) {
                    chargebackOfId = null;
                }
                final boolean isRoot = rsTransfers.getString("parent_id") == null && rsTransfers.getString("account_fee_log_id") == null;
                final String status = rsTransfers.getString("status");

                // Check if is a credit or a debit
                final boolean isDebit = chargebackOfId == null ? accountId == fromAccountId : accountId != fromAccountId;
                final boolean isProcessed = status.equals("O");
                final boolean isPending = status.equals("P");
                if (isDebit) {
                    if (isRoot) {
                        if (isProcessed) {
                            rootDebitsAmount = rootDebitsAmount.add(amount);
                            rootDebitsCount++;
                        } else if (isPending) {
                            pendingDebitsAmount = pendingDebitsAmount.add(amount);
                            pendingDebitsCount++;
                        }
                    } else {
                        if (isProcessed) {
                            nestedDebitsAmount = nestedDebitsAmount.add(amount);
                            nestedDebitsCount++;
                        } else if (isPending) {
                            pendingDebitsAmount = pendingDebitsAmount.add(amount);
                            pendingDebitsCount++;
                        }
                    }
                } else {
                    if (isRoot) {
                        if (isProcessed) {
                            rootCreditsAmount = rootCreditsAmount.add(amount);
                            rootCreditsCount++;
                        } else if (isPending) {
                            pendingCreditsAmount = pendingCreditsAmount.add(amount);
                            pendingCreditsCount++;
                        }
                    } else {
                        if (isProcessed) {
                            nestedCreditsAmount = nestedCreditsAmount.add(amount);
                            nestedCreditsCount++;
                        } else if (isPending) {
                            pendingCreditsAmount = pendingCreditsAmount.add(amount);
                            pendingCreditsCount++;
                        }
                    }
                }

                // Insert the account status
                jdbc.execute(statusInsert, subclass, accountId, date, rootCreditsCount, rootCreditsAmount, rootDebitsCount, rootDebitsAmount, nestedCreditsCount, nestedCreditsAmount, nestedDebitsCount, nestedDebitsAmount, pendingCreditsAmount, pendingCreditsCount, pendingDebitsAmount, pendingDebitsCount, creditLimit, upperCreditLimit, transferId);
            }
            closeQuietly(rsTransfers);
        }
        closeQuietly(rsAccounts);
    }
}
