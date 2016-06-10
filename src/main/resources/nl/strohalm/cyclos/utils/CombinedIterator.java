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

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

/**
 * An iterator which will iterate through a delegate "master" iterator and several details, inner iterators. All inner iterators are assumed to have
 * data in the same order as the master (not necessary for every single element on the master iterator, but on the same order). The result is a
 * combined value, which is
 * @param <C> The type of the combined results
 * @param <M> The type of the elements in the master iterator
 * @param <I> The type of the elements in the nested iterators
 * @param <K> The type which will serve as key to identify the nested iterators
 */
public abstract class CombinedIterator<C, M, I, K> implements Iterator<C> {

    private Iterator<M>         masterIterator;
    private Map<K, Iterator<I>> innerIterators;
    private Map<K, I>           currentElements;
    private Map<K, I>           innerElements;

    public CombinedIterator(final Iterator<M> masterIterator) {
        this.masterIterator = masterIterator;
        this.innerElements = new HashMap<K, I>();
    }

    public boolean hasNext() {
        if (innerIterators == null) {
            innerIterators = new LinkedHashMap<K, Iterator<I>>();
            registerInnerIterators();
        }
        return masterIterator.hasNext();
    }

    public C next() {
        final M masterElement = masterIterator.next();
        if (currentElements == null) {
            readCurrentElements();
        }
        innerElements.clear();
        for (final Entry<K, I> entry : currentElements.entrySet()) {
            final K key = entry.getKey();
            final I value = entry.getValue();
            if (belongsToMasterElement(masterElement, key, value)) {
                innerElements.put(key, value);
                advanceIterator(key);
            }
        }
        return combine(masterElement, innerElements);
    }

    public void remove() {
        throw new UnsupportedOperationException();
    }

    /**
     * Should check whether the given inner element belongs to the given master element
     */
    protected abstract boolean belongsToMasterElement(M masterElement, K key, I innerElement);

    /**
     * Combine the master element, together with all nested elements into a new value. It is possible that not all expected values are present on the
     * map, and that should be properly handled
     */
    protected abstract C combine(M masterElement, Map<K, I> elements);

    /**
     * Registers an inner iterator
     */
    protected void registerInnerIterator(final K key, final Iterator<I> iterator) {
        innerIterators.put(key, iterator);
    }

    /**
     * Should be implemented in order to register all inner iterators
     */
    protected abstract void registerInnerIterators();

    /**
     * Advances the iterator with the given key, returning it's next element, or null when no more elements
     */
    private I advanceIterator(final K key) {
        final Iterator<I> iterator = innerIterators.get(key);
        if (iterator.hasNext()) {
            final I element = iterator.next();
            currentElements.put(key, element);
            return element;
        } else {
            currentElements.remove(key);
            return null;
        }
    }

    /**
     * Ensures that the currentElements Map contains updated elements for all keys (null when iterators are exhausted)
     */
    private void readCurrentElements() {
        if (currentElements == null) {
            currentElements = new ConcurrentHashMap<K, I>();
        }
        for (final K key : innerIterators.keySet()) {
            final I element = advanceIterator(key);
            if (element == null) {
                currentElements.remove(key);
            } else {
                currentElements.put(key, element);
            }
        }
    }

}
