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
 * Listener for cache events
 * 
 * @author luis
 */
public interface CacheListener {

    /**
     * Callback method invoked when the entire cache was cleared
     */
    void onCacheCleared(Cache cache);

    /**
     * Callback method invoked when an element was added to the cache
     */
    void onValueAdded(Cache cache, Serializable key, Object value);

    /**
     * Callback method invoked when an element was removed from the cache
     */
    void onValueRemoved(Cache cache, Serializable key, Object value);

}
