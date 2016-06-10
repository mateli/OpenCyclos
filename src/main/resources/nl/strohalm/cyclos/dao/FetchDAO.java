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
package nl.strohalm.cyclos.dao;

import nl.strohalm.cyclos.entities.Entity;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.exceptions.DaoException;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.entities.exceptions.UnexpectedEntityException;

/**
 * This componente provides mechanisms for lazy loading and fetching lazy relationships for persistent entities in the system. Implementors are free
 * to provide such behavior in the prefered fashion. Using lazy loading and fetching only the relevant attributes of entities can be loaded, while
 * secondary relationships may be only loaded on demand, providing an overall performance boost.
 * 
 * @author rafael
 * @author Ivan "Fireblade" Diana
 */
public interface FetchDAO {

    /**
     * Clears the first level entity cache
     */
    void clearCache();

    /**
     * Fetches the given relationships for the given <code>entity</code>, if they are still not materialized. Implementors are free to provide such
     * behavior in the prefered fashion. No guarantees are made about the instance returned being the same provided as parameter (situations can
     * happen where the parameter is in fact a proxy or runtime-generated subclass).
     * 
     * <p>
     * If any exception is thrown by the underlying implementation, it should be wrapped by a DaoException.
     * 
     * @throws UnexpectedEntityException When the entity is null or transient
     * @throws EntityNotFoundException When the given does not exists on the database
     * @throws DaoException
     */
    <E extends Entity> E fetch(E entity, Relationship... fetch) throws UnexpectedEntityException, EntityNotFoundException, DaoException;

    /**
     * Returns <code>true</code> if the given value is initialized. <code>False</code> otherwise. If any exception is thrown by the underlying
     * implementation, it should be wrapped by a DaoException. If a transient object is provided as parameter returns <code>true</code>.
     * 
     * @throws DaoException
     */
    boolean isInitialized(Object value) throws DaoException;

    /**
     * Fetches the given relationships for the given <code>entity</code>, forcing their state to be read from database . Implementors are free to
     * provide such behavior in the prefered fashion. No guarantees are made about the instance returned being the same provided as parameter
     * (situations can happen where the parameter is in fact a proxy or runtime-generated subclass).
     * 
     * <p>
     * If any exception is thrown by the underlying implementation, it should be wrapped by a DaoException.
     * 
     * @throws UnexpectedEntityException When the entity is null or transient
     * @throws EntityNotFoundException When the given does not exists on the database
     * @throws DaoException
     */
    <E extends Entity> E reload(E entity, Relationship... fetch) throws UnexpectedEntityException, EntityNotFoundException, DaoException;

    /**
     * Removes the given entity from entity cache
     */
    void removeFromCache(Entity entity);

}
