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

import nl.strohalm.cyclos.utils.conversion.Transformer;

import java.io.Closeable;
import java.util.Iterator;

/**
 * Wraps a Hibernate ScrollableResults in an iterator
 * 
 * @author luis
 */
public class ScrollableResultsIterator<T> implements Iterator<T>, Closeable {

    private T                        nextObject;
    private Transformer<Object[], T> transformer;
    private Iterator                 jpaIterator;

    public ScrollableResultsIterator(final javax.persistence.Query query, final Transformer<Object[], T> transformer) {
        this.jpaIterator = query.getResultList().iterator();
        this.transformer = transformer;
        getNextObject();
    }

    @Override
    public void close() {
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
        if (jpaIterator != null) {
            if (transformer != null) {
                nextObject = transformer.transform((Object[]) jpaIterator.next());
            } else {
                nextObject = (T) jpaIterator.next();
            }
        } else {
            nextObject = null;
        }
    }
}
