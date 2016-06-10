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
package nl.strohalm.cyclos.utils.cache.ehcache;

import java.io.Serializable;

/**
 * A message which is published for cache eviction. When no key, evict the entire cache.
 * 
 * @author luis
 */
public class CacheEvictionEvent implements Serializable {
    private static final long serialVersionUID = -2915040249167366871L;
    private final String      name;
    private final Object      key;

    public CacheEvictionEvent(final String name, final Object key) {
        this.name = name;
        this.key = key;
    }

    public Object getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name + " " + (key == null ? "ALL" : key);
    }
}
