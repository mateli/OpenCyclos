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

import nl.strohalm.cyclos.entities.accounts.AccountType;
import nl.strohalm.cyclos.entities.accounts.fees.transaction.BrokerCommission.WhichBroker;
import nl.strohalm.cyclos.entities.accounts.fees.transaction.TransactionFee;
import nl.strohalm.cyclos.entities.accounts.fees.transaction.TransactionFee.Subject;
import nl.strohalm.cyclos.setup.UntraceableMigration;
import nl.strohalm.cyclos.utils.JDBCWrapper;

/**
 * Migrate the transaction fees
 * @author luis
 */
@SuppressWarnings("deprecation")
public class TransactionFeesMigration implements UntraceableMigration {

    private static class TransferTypeDescriptor {
        private static TransferTypeDescriptor fromId(final long id, final JDBCWrapper jdbc) throws SQLException {
            final StringBuilder sql = new StringBuilder();
            sql.append(" select f.subclass as from_class, t.subclass as to_class");
            sql.append(" from transfer_types tt");
            sql.append("    inner join account_types as f on tt.from_account_type_id = f.id");
            sql.append("    inner join account_types as t on tt.to_account_type_id = t.id");
            sql.append(" where tt.id = ?");
            final ResultSet rs = jdbc.query(sql.toString(), id);
            TransferTypeDescriptor tt = null;
            if (rs.next()) {
                final String system = AccountType.Nature.SYSTEM.getValue();
                tt = new TransferTypeDescriptor();
                tt.fromSystem = system.equals(rs.getString("from_class"));
                tt.toSystem = system.equals(rs.getString("to_class"));
            }
            JDBCWrapper.closeQuietly(rs);
            return tt;
        }

        private boolean fromSystem;
        private boolean toSystem;
    }

    public void execute(final JDBCWrapper jdbc) throws SQLException {
        final String simple = TransactionFee.Nature.SIMPLE.getValue();

        final String feesSelect = "select * from transaction_fees";
        final String feesUpdate = "update transaction_fees set payer=?, receiver=?, which_broker=? where id=?";
        final ResultSet fees = jdbc.query(feesSelect);
        try {
            while (fees.next()) {
                final long id = fees.getLong("id");
                final String subclass = fees.getString("subclass");
                final TransferTypeDescriptor original = TransferTypeDescriptor.fromId(fees.getLong("original_type_id"), jdbc);
                final TransferTypeDescriptor generated = TransferTypeDescriptor.fromId(fees.getLong("generated_type_id"), jdbc);

                // These are the variables we need to find out
                Subject payer = null;
                Subject receiver = null;
                WhichBroker whichBroker = null;

                // Find out the payer
                if (generated.fromSystem) {
                    payer = Subject.SYSTEM;
                } else {
                    final String whoPays = fees.getString("who_pays");
                    payer = "D".equals(whoPays) ? Subject.DESTINATION : Subject.SOURCE;
                }

                if (simple.equals(subclass)) {
                    // Find out the receiver
                    if (generated.toSystem) {
                        receiver = Subject.SYSTEM;
                    } else {
                        // When the fee was not paid to system, it's either the from or to member
                        if (!original.fromSystem) {
                            receiver = Subject.SOURCE;
                        } else if (!original.toSystem) {
                            receiver = Subject.DESTINATION;
                        }
                    }
                } else {
                    // Until Cyclos 3.0, it was always the source broker
                    whichBroker = WhichBroker.SOURCE;
                }

                // update the data
                final String payerValue = payer == null ? null : payer.getValue();
                final String receiverValue = receiver == null ? null : receiver.getValue();
                final String whichBrokerValue = whichBroker == null ? null : whichBroker.getValue();
                jdbc.execute(feesUpdate, payerValue, receiverValue, whichBrokerValue, id);
            }
        } finally {
            JDBCWrapper.closeQuietly(fees);
        }

        // Drop the who_pays column, as it's no longer used
        jdbc.execute("alter table transaction_fees drop column who_pays");
    }
}
