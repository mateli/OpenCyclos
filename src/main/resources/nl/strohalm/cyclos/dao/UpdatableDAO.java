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
import nl.strohalm.cyclos.entities.exceptions.DaoException;
import nl.strohalm.cyclos.entities.exceptions.UnexpectedEntityException;

/**
 * DAO interface for DAO's that can perform updates on their entities
 * @author Ivan "Fireblade" Diana
 */
public interface UpdatableDAO<E extends Entity> {

    /**
     * Persists <code>entity</code> in the persistence layer, flushing the changes to the database. Any exception thrown by the underlying
     * implementation is encapsulated in a DaoException. Returns the persisted entity with the modifications made during the process.
     * 
     * <p>
     * The parameter <code>entity</code> cannot be a transient entity, it <strong>must</code> be an already persisted one. If the entity is still
     * transient a TransientEntityException will be thrown.
     * @param entity
     * @return the persisted entity
     * @throws UnexpectedEntityException If the entity is null or persistent
     * @throws DaoException
     */
    public <T extends E> T update(T entity) throws DaoException;

    /**
     * Persists <code>entity</code> in the persistence layer, optionally flushing the changes to the database. Any exception thrown by the underlying
     * implementation is encapsulated in a DaoException. Returns the persisted entity with the modifications made during the process.
     * 
     * <p>
     * The parameter <code>entity</code> cannot be a transient entity, it <strong>must</code> be an already persisted one. If the entity is still
     * transient a TransientEntityException will be thrown.
     * @param entity
     * @return the persisted entity
     * @throws UnexpectedEntityException If the entity is null or persistent
     * @throws DaoException
     */
    public <T extends E> T update(T entity, boolean flush) throws DaoException;

}
