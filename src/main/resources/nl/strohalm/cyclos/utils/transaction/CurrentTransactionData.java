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
package nl.strohalm.cyclos.utils.transaction;

import nl.strohalm.cyclos.exceptions.MailSendingException;

/**
 * Holds data about the current transaction
 * @author luis
 */
public class CurrentTransactionData {

    /**
     * Contains the current transaction data
     * @author luis
     */
    public static class Entry {
        private Throwable            error;
        private MailSendingException mailError;
        private boolean              write;
        private TransactionListeners transactionListeners;

        public Throwable getError() {
            return error;
        }

        public MailSendingException getMailError() {
            return mailError;
        }

        public TransactionListeners getTransactionListeners() {
            return transactionListeners;
        }

        public boolean isWrite() {
            return write;
        }
    }

    private static ThreadLocal<Entry> HOLDER = new ThreadLocal<Entry>();

    /**
     * Adds a transaction commit listener
     */
    public static void addTransactionCommitListener(final TransactionCommitListener listener) {
        ensureListeners().addCommitListener(listener);
    }

    /**
     * Adds a transaction end listener
     */
    public static void addTransactionEndListener(final TransactionEndListener listener) {
        ensureListeners().addEndListener(listener);
    }

    /**
     * Adds a transaction rollback listener
     */
    public static void addTransactionRollbackListener(final TransactionRollbackListener listener) {
        ensureListeners().addRollbackListener(listener);
    }

    /**
     * Removes any reference
     */
    public static void cleanup() {
        try {
            HOLDER.remove();
        } catch (final Throwable e) {
            // Ignored
        }
    }

    /**
     * Removes the current error, if any
     */
    public static void clearError() {
        final Entry entry = getEntry();
        if (entry != null) {
            entry.error = null;
        }
    }

    /**
     * Removes the {@link TransactionListeners} from the current transaction. The result is never null. If there were no registered listeners, or no
     * current transaction, {@link TransactionListeners#EMPTY} is returned.
     */
    public static TransactionListeners detachListeners() {
        final Entry entry = getEntry();
        final TransactionListeners transactionListeners = entry == null ? null : entry.getTransactionListeners();
        if (transactionListeners == null) {
            return TransactionListeners.EMPTY;
        }
        entry.transactionListeners = null;
        return transactionListeners;
    }

    /**
     * Returns the current entry, or null if nothing set
     */
    public static Entry getEntry() {
        return HOLDER.get();
    }

    /**
     * Returns the the error in this transaction, or null if none
     */
    public static Throwable getError() {
        final Entry entry = getEntry();
        return entry == null ? null : entry.error;
    }

    /**
     * Returns the mail error for this transaction, if any
     */
    public static MailSendingException getMailError() {
        final Entry entry = getEntry();
        return entry == null ? null : entry.mailError;
    }

    /**
     * Checks if there's an error in this transaction
     */
    public static boolean hasError() {
        return getError() != null;
    }

    /**
     * Checks if there's a mail error in this transaction
     */
    public static boolean hasMailError() {
        return getMailError() != null;
    }

    /**
     * Check if there were database writes in this transaction
     */
    public static boolean hasWrite() {
        final Entry entry = getEntry();
        return entry != null && entry.write;
    }

    /**
     * Sets the current throwable
     */
    public static void setError(final Throwable throwable) {
        final Entry entry = ensureEntry();
        // The error is set only if there was no previous error already
        if (entry.error == null) {
            entry.error = throwable;
        }
    }

    /**
     * Sets the current mail sending error
     */
    public static void setMailError(final MailSendingException error) {
        ensureEntry().mailError = error;
    }

    /**
     * Mark the current transaction as having database writes
     */
    public static void setWrite() {
        ensureEntry().write = true;
    }

    /**
     * Returns the current entry, initializing it when no previous entry
     */
    private static Entry ensureEntry() {
        Entry entry = getEntry();
        if (entry == null) {
            entry = new Entry();
            HOLDER.set(entry);
        }
        return entry;
    }

    private static TransactionListeners ensureListeners() {
        final Entry entry = ensureEntry();
        TransactionListeners transactionListeners = entry.getTransactionListeners();
        if (transactionListeners == null) {
            transactionListeners = entry.transactionListeners = new TransactionListeners(); // 2 assignments
        }
        return transactionListeners;
    }
}
