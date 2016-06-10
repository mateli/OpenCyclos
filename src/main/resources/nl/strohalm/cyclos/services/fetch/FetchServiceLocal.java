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
package nl.strohalm.cyclos.services.fetch;

import java.util.Collection;
import java.util.List;

import nl.strohalm.cyclos.entities.Entity;
import nl.strohalm.cyclos.entities.Relationship;

/**
 * Local interface. It must be used only from other services.
 */
public interface FetchServiceLocal extends FetchService {
    /**
     * Fetch the relationships for each entity in the collection. When entities is null, returns null
     */
    <E extends Entity> List<E> fetch(Collection<E> entities, Relationship... fetch);

    /**
     * Fetches the give relationships for the given entity. When entity is null, returns null
     */
    <E extends Entity> E fetch(E entity, Relationship... fetch);

    /**
     * Returns if the given value is initialized. when value is null, or not an entity or persistent collection, returns true
     */
    boolean isInitialized(Object value);

    /**
     * Fetches the give relationships for the given entity, forcing the entity to be reloaded from the database, ignoring any possibly existing cache
     * When entity is null, returns null
     */
    <E extends Entity> E reload(E entity, Relationship... fetch);

    /**
     * Remove the given entity from the first level cache
     */
    void removeFromCache(Entity entity);
}
