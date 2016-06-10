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

import java.io.Closeable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import nl.strohalm.cyclos.utils.query.IteratorList;

import org.hibernate.Hibernate;

/**
 * Helper class for data iterators
 * 
 * @author luis
 */
public class DataIteratorHelper {

    private static final ThreadLocal<Map<Iterator<?>, Boolean>> OPEN_ITERATORS = new ThreadLocal<Map<Iterator<?>, Boolean>>();

    /**
     * Closes a data iterator. Internally invokes Hibernate.close(), but exists to avoid clients having to rely on hibernate
     */
    public static void close(final Iterator<?> iterator) {
        close(iterator, true);
    }

    /**
     * Closes the given {@link IteratorList}'s underlying iterator, by invoking {@link #close(Iterator)}
     */
    public static void close(final IteratorList<?> iteratorList) {
        close(iteratorList.getIterator(), true);
    }

    /**
     * Invokes {@link #close(Iterator)} with the list iterator, handling {@link IteratorList}s by using {@link IteratorList#getIterator()}
     */
    public static void close(final List<?> list) {
        Iterator<?> iterator;
        if (list instanceof IteratorList<?>) {
            iterator = ((IteratorList<?>) list).getIterator();
        } else {
            iterator = list.iterator();
        }
        close(iterator);
    }

    /**
     * Closes all open iterators in this thread
     */
    public static void closeOpenIterators() {
        final Map<Iterator<?>, Boolean> iterators = OPEN_ITERATORS.get();
        if (iterators == null) {
            return;
        }
        for (final Iterator<?> iterator : iterators.keySet()) {
            close(iterator, false);
        }
        iterators.clear();
        OPEN_ITERATORS.remove();
    }

    /**
     * Returns whether there is at least one open iterator which requires an open connection
     */
    public static boolean hasOpenIteratorsRequiringOpenConnection() {
        final Map<Iterator<?>, Boolean> iterators = OPEN_ITERATORS.get();
        if (iterators != null) {
            for (final Boolean requiresOpenConnection : iterators.values()) {
                if (Boolean.TRUE.equals(requiresOpenConnection)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Registers an open iterator for this thread, so that in future, it may be forcefully closed to avoid open data iterators
     */
    public static void registerOpen(final Iterator<?> iterator, final boolean requiresOpenConnection) {
        if (iterator == null) {
            return;
        }
        Map<Iterator<?>, Boolean> iterators = OPEN_ITERATORS.get();
        if (iterators == null) {
            iterators = new HashMap<Iterator<?>, Boolean>();
            OPEN_ITERATORS.set(iterators);
        }
        iterators.put(iterator, requiresOpenConnection);
    }

    private static void close(final Iterator<?> iterator, final boolean remove) {
        if (iterator instanceof Closeable) {
            // Is an index reader. Invoke the close() method
            final Closeable closeable = (Closeable) iterator;
            try {
                closeable.close();
            } catch (final Exception e) {
                // Silently ignore
            }
        } else {
            // Close the iterator with Hibernate
            try {
                Hibernate.close(iterator);
            } catch (final Exception e) {
                // Silently ignore
            }
        }
        // Remove the iterator from the thread-bound set
        if (remove) {
            final Map<Iterator<?>, Boolean> iterators = OPEN_ITERATORS.get();
            if (iterators != null) {
                iterators.remove(iterator);
            }
        }
    }
}
