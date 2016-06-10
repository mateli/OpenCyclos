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
 * DAO interface for DAO's that can perform inserts on their entities
 * @author Ivan "Fireblade" Diana
 */
public interface InsertableDAO<E extends Entity> {

    /**
     * Persists the new <code>entity</code> in the persistence layer, flushing the changes to the database. Any exception thrown by the underlying
     * implementation is encapsulated in a DaoException. Returns the persisted entity with the modifications made during the process. Generally, it
     * means an identifier will be defined to the new persistent entity by the persistence layer. No guarantees are made about identifiers configured
     * manually for transiente entities. In other words, implementors are not obligated to respect such identifiers.
     * 
     * <P>
     * If an already persistent entity is provided, just flush its state to persistence layer. Note that de identifier, in this case, cannot be
     * changed to keep data integrity.
     * 
     * @param entity
     * @return the persisted entity
     * @throws UnexpectedEntityException If the entity is null or persistent
     * @throws DaoException if any problem happens in the underlying implementation
     */
    public <T extends E> T insert(T entity) throws UnexpectedEntityException, DaoException;

    /**
     * Persists the new <code>entity</code> in the persistence layer, optionally flushing the changes to the database. Any exception thrown by the
     * underlying implementation is encapsulated in a DaoException. Returns the persisted entity with the modifications made during the process.
     * Generally, it means an identifier will be defined to the new persistent entity by the persistence layer. No guarantees are made about
     * identifiers configured manually for transiente entities. In other words, implementors are not obligated to respect such identifiers.
     * 
     * <P>
     * If an already persistent entity is provided, just flush its state to persistence layer. Note that de identifier, in this case, cannot be
     * changed to keep data integrity.
     * 
     * @param entity
     * @return the persisted entity
     * @throws UnexpectedEntityException If the entity is null or persistent
     * @throws DaoException if any problem happens in the underlying implementation
     */
    public <T extends E> T insert(T entity, boolean flush) throws UnexpectedEntityException, DaoException;

}
