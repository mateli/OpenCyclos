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

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Maintains a list of transaction commit and rollback listeners
 * 
 * @author luis
 */
public class TransactionListeners {

    private static final Log                  LOG = LogFactory.getLog(TransactionListeners.class);
    public static final TransactionListeners  EMPTY;
    static {
        EMPTY = new TransactionListeners() {
            @Override
            public void addCommitListener(final TransactionCommitListener listener) {
                // no-op
            }

            @Override
            public void addEndListener(final TransactionEndListener listener) {
                // no-op
            }

            @Override
            public void addRollbackListener(final TransactionRollbackListener listener) {
                // no-op
            }
        };
    }
    private List<TransactionCommitListener>   commitListeners;
    private List<TransactionRollbackListener> rollbackListeners;

    /**
     * Adds a commit listener
     */
    public void addCommitListener(final TransactionCommitListener listener) {
        if (commitListeners == null) {
            commitListeners = new ArrayList<TransactionCommitListener>();
        }
        commitListeners.add(listener);
    }

    /**
     * Adds an end listener
     */
    public void addEndListener(final TransactionEndListener listener) {
        addCommitListener(listener);
        addRollbackListener(listener);
    }

    /**
     * Adds a rollback listener
     */
    public void addRollbackListener(final TransactionRollbackListener listener) {
        if (rollbackListeners == null) {
            rollbackListeners = new ArrayList<TransactionRollbackListener>();
        }
        rollbackListeners.add(listener);
    }

    /**
     * Either invokes {@link #runCommitListeners()} when the flag is true or {@link #runRollbackListeners()} when the flag is
     * false
     */
    public void runListeners(final boolean commit) {
        if (commit) {
            runCommitListeners();
        } else {
            runRollbackListeners();
        }
    }

    /**
     * Runs the commit listeners
     */
    public void runCommitListeners() {
        if (CollectionUtils.isEmpty(commitListeners)) {
            return;
        }
        for (final TransactionCommitListener listener : commitListeners) {
            try {
                listener.onTransactionCommit();
            } catch (final Exception e) {
                LOG.warn("Error running transaction commit listener " + listener, e);
            }
        }
    }

    /**
     * Runs the rollback listeners
     */
    public void runRollbackListeners() {
        if (CollectionUtils.isEmpty(rollbackListeners)) {
            return;
        }
        for (final TransactionRollbackListener listener : rollbackListeners) {
            try {
                listener.onTransactionRollback();
            } catch (final Exception e) {
                LOG.warn("Error running transaction rollback listener " + listener, e);
            }
        }
    }

}
