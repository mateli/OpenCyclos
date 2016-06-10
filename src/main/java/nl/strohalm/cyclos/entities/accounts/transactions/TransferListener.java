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
package nl.strohalm.cyclos.entities.accounts.transactions;

import nl.strohalm.cyclos.exceptions.ExternalException;
import nl.strohalm.cyclos.utils.transaction.CurrentTransactionData;

/**
 * Listener interface notified when a transfer is created. Be advised that any long operation performed by any of the callback methods will block the
 * response to the client.
 * 
 * @author luis
 */
public interface TransferListener {

    /**
     * Callback method invoked before validating the balance of involved accounts, in order to perform a payment. Any DB writes done within this
     * method will be either committed or rolled back according to the original transaction. Throwing an exception will rollback the original
     * transaction. To be notified whether the original transaction was committed or rolled back, use
     * {@link CurrentTransactionData#addTransactionCommitListener(nl.strohalm.cyclos.utils.transaction.TransactionCommitListener)} and
     * {@link CurrentTransactionData#addTransactionRollbackListener(nl.strohalm.cyclos.utils.transaction.TransactionRollbackListener)}, respectively.
     * @throws ExternalException When an error message should be displayed to the user explaining the error, this exception type should be thrown
     */
    void onBeforeValidateBalance(Transfer transfer) throws ExternalException;

    /**
     * Callback method invoked on the same DB transaction of the payment, after the transfer is inserted, but before committing the transaction. Any
     * DB writes done within this method will be either committed or rolled back according to the original transaction. Throwing an exception will
     * rollback the original transaction. To be notified whether the original transaction was committed or rolled back, use
     * {@link CurrentTransactionData#addTransactionCommitListener(nl.strohalm.cyclos.utils.transaction.TransactionCommitListener)} and
     * {@link CurrentTransactionData#addTransactionRollbackListener(nl.strohalm.cyclos.utils.transaction.TransactionRollbackListener)}, respectively.
     * @throws ExternalException When an error message should be displayed to the user explaining the error, this exception type should be thrown
     */
    void onTransferInserted(Transfer transfer) throws ExternalException;

    /**
     * Callback method invoked when a transfer is processed: performed when no authorization is used, when the transfer is authorized or an
     * installment is processed. This method is invoked after the main DB transaction has been committed - no matter what this method does, the
     * transfer is already persisted and won't be affected. Any exceptions thrown will just be logged on the server. If the
     * {@link #onTransferInserted(Transfer)} or {@link #onBeforeValidateBalance(Transfer)} methods are implemented, and this notification is needed in
     * order to notify that the transfer is affectively processed, either {@link #onBeforeValidateBalance(Transfer)} or
     * {@link #onTransferInserted(Transfer)} can set the {@link Transfer#setTraceNumber(String)} with some external identifier, in order to
     * acknowledge the processing of the specific transfer.
     */
    void onTransferProcessed(Transfer transfer);

}
