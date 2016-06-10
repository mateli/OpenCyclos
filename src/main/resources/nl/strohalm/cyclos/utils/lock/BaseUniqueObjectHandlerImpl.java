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
package nl.strohalm.cyclos.utils.lock;

import java.util.HashSet;
import java.util.Set;

import nl.strohalm.cyclos.utils.Pair;
import nl.strohalm.cyclos.utils.transaction.CurrentTransactionData;
import nl.strohalm.cyclos.utils.transaction.TransactionEndListener;

public abstract class BaseUniqueObjectHandlerImpl implements UniqueObjectHandler {

    private static ThreadLocal<Set<Pair<Object, Object>>> ACQUIRED = new ThreadLocal<Set<Pair<Object, Object>>>();

    @Override
    public boolean tryAcquire(final Pair<Object, Object> pair) {
        // If this thread has the lock already, return
        Set<Pair<Object, Object>> acquiredKeys = ACQUIRED.get();
        if (acquiredKeys != null && acquiredKeys.contains(pair)) {
            // Already acquired by this thread
            return true;
        }
        boolean acquired = getLocks().add(pair);
        if (acquired) {
            // Store on the thread local
            if (acquiredKeys == null) {
                acquiredKeys = new HashSet<Pair<Object, Object>>();
                ACQUIRED.set(acquiredKeys);
            }
            acquiredKeys.add(pair);

            // Add an end transaction listener to ensure we'll release the lock
            CurrentTransactionData.addTransactionEndListener(new TransactionEndListener() {
                @Override
                protected void onTransactionEnd(final boolean commit) {
                    release(pair);
                }
            });
        }
        return acquired;
    }

    protected abstract Set<Pair<Object, Object>> getLocks();

    /**
     * Stop using the specified object.
     */
    private void release(final Pair<Object, Object> pair) {
        try {
            // Release from the thread local
            Set<Pair<Object, Object>> acquiredKeys = ACQUIRED.get();
            if (acquiredKeys != null) {
                acquiredKeys.remove(pair);
                if (acquiredKeys.isEmpty()) {
                    ACQUIRED.remove();
                }
            }
        } finally {
            // Remove from the shared set
            getLocks().remove(pair);
        }
    }

}
