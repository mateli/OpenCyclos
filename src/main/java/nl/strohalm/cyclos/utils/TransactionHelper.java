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
package nl.strohalm.cyclos.utils;

import java.util.concurrent.Future;

import nl.strohalm.cyclos.entities.accounts.LockedAccountsOnPayments;
import nl.strohalm.cyclos.utils.access.LoggedUser;

import org.springframework.transaction.support.TransactionCallback;

/**
 * Helper for transaction manipulation
 * @author luis
 */
public interface TransactionHelper {

    /**
     * Returns whether there is an active transaction in this thread
     */
    boolean hasActiveTransaction();

    /**
     * Runs the given callback in a new transaction if the current {@link LockedAccountsOnPayments} is at least
     * {@link LockedAccountsOnPayments#ORIGIN}
     */
    <T> T maybeRunInNewTransaction(final TransactionCallback<T> callback);

    /**
     * Runs the given callback in a new transaction if the newTransaction flag is true and if the current {@link LockedAccountsOnPayments} is at least
     * {@link LockedAccountsOnPayments#ORIGIN}
     */
    <T> T maybeRunInNewTransaction(final TransactionCallback<T> callback, final boolean newTransaction);

    /**
     * Runs the given callback in a new transaction if the newTransaction flag is true and if the current {@link LockedAccountsOnPayments} is at least
     * the given one
     */
    <T> T maybeRunInNewTransaction(final TransactionCallback<T> callback, final boolean newTransaction, final LockedAccountsOnPayments minForNewTx);

    /**
     * Executes the given {@link #TransactionCallback} in a separate transaction, without waiting for the result. Propagates the current
     * {@link LoggedUser} context to the new thread.
     */
    <T> Future<T> runAsync(final TransactionCallback<T> callback);

    /**
     * Runs the current callback in the current thread. Doesn't touch the current {@link LoggedUser} context, but does ensure that
     * CurrentTransactionData is processed (for example, commit / rollback listeners are notified) and cleared
     */
    <T> T runInCurrentThread(final TransactionCallback<T> callback);

    /**
     * Executes the given {@link TransactionCallback} in a separate transaction. A new thread is used to prevent the new and old transactions from
     * clashing. Propagates the current {@link LoggedUser} context to the new thread.
     */
    <T> T runInNewTransaction(final TransactionCallback<T> callback);

}
