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
package nl.strohalm.cyclos.utils.cache;

import java.io.Serializable;

/**
 * Defines the API to access cached items
 * @author luis
 */
public interface Cache {

    /**
     * Adds a cache listener for the given scope, if it was not yet registered
     */
    boolean addListener(CacheListener listener);

    /**
     * Removes all cached elements
     */
    void clear();

    /**
     * Returns an element from cache, given the key. If the object is not present, the callback will be invoked, returning adding the returned object
     * to cache and returning it
     */
    <T> T get(Serializable key, CacheCallback callback);

    /**
     * Returns the name of this cache
     */
    String getName();

    /**
     * Removes an item from the cache
     */
    <T> T remove(Serializable key);

    /**
     * Shuts down the given cache, making it invalid to invoke any other method after this invocation
     */
    void shutdown();
}
