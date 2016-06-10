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
import java.util.Iterator;

import nl.strohalm.cyclos.utils.conversion.Transformer;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;

/**
 * Wraps a Hibernate ScrollableResults in an iterator
 * 
 * @author luis
 */
public class ScrollableResultsIterator<T> implements Iterator<T>, Closeable {

    private ScrollableResults        results;
    private T                        nextObject;
    private Transformer<Object[], T> transformer;
    private boolean                  array;

    public ScrollableResultsIterator(final Query query, final Transformer<Object[], T> transformer) {
        this.results = query.scroll(ScrollMode.FORWARD_ONLY);
        if (query instanceof SQLQuery) {
            // The getReturnTypes doesn't work for SQLQueries... Assume an array
            array = true;
        } else {
            // this (extra) check to see if the query starts with "select new" is just to support the
            // following case: SELECT new A(e.prop1, e.prop2) FROM Entity e ...
            // in that case we musn't return an array in the next() method.
            array = query.getReturnTypes().length > 1 && !query.getQueryString().trim().toLowerCase().startsWith("select new");
        }
        this.transformer = transformer;
        getNextObject();

        DataIteratorHelper.registerOpen(this, true);
    }

    @Override
    public void close() {
        results.close();
    }

    @Override
    public boolean hasNext() {
        return nextObject != null;
    }

    @Override
    public T next() {
        final T currentObject = nextObject;
        getNextObject();
        return currentObject;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }

    @SuppressWarnings("unchecked")
    private void getNextObject() {
        if (results.next()) {
            if (transformer != null) {
                nextObject = transformer.transform(results.get());
            } else {
                nextObject = (T) (array ? results.get() : results.get(0));
            }
        } else {
            nextObject = null;
        }
    }
}
