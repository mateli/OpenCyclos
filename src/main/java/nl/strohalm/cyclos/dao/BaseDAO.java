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

import java.io.InputStream;
import java.sql.Blob;
import java.util.Collection;

import nl.strohalm.cyclos.entities.Entity;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.exceptions.DaoException;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;

/**
 * Base Data Access Object which all DAO interface should extend. Specialized interfaces should define which Entity should replace generic type
 * <code>E</code>.
 * 
 * @author rafael
 * @author fireblade
 */
public interface BaseDAO<E extends Entity> {

    Blob createBlob(final InputStream stream, final int length);

    /**
     * Duplicates the entity, without copying collections
     * <ul>
     * <li>The identifier is not copied
     * <li>All primitive and direct relationships (many to one) are copied
     * <li>Collections are not copied
     * </ul>
     */
    <T extends E> T duplicate(T entity);

    /**
     * Returns the <code>java.lang.Class</code> of the type binded to generic type <code>E</code>.
     */
    Class<E> getEntityType();

    /**
     * Loads a collection of entities for the specified <code>ids</code>, populating the relationships defined by <code>fetch</code>, if any for each
     * loaded entity. Returns the loaded entity. If the entity cannot be found, throws EntityNotFoundException.
     */
    <T extends E> Collection<T> load(Collection<Long> ids, Relationship... fetch) throws DaoException, EntityNotFoundException;

    /**
     * Loads the entity identified by <code>id</code>, populating the relationships defined by <code>fetch</code>, if any. Returns the loaded entity.
     * If the entity cannot be found, throws EntityNotFoundException.
     */
    <T extends E> T load(Long id, Relationship... fetch) throws DaoException, EntityNotFoundException;

    /**
     * Force the entity to be reloaded from the database, populating the relationships defined by <code>fetch</code>, if any.
     */
    <T extends E> T reload(Long id, Relationship... fetch) throws DaoException, EntityNotFoundException;
}
