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

/**
 * DAO interface for DAO's that can perform delete on their entities
 * @author Ivan "Fireblade" Diana
 */
public interface DeletableDAO<E extends Entity> {

    /**
     * Remove permanently the entities whose identifiers are contained in the array <code>ids</code>, optionally flushing the changes to the
     * database. Returns the number of entities removed. If any identifier does not correspond to any existent entity, do nothing on it and continue
     * the deletion. Any exception thrown by the underlying implementation is encapsulated in a DaoException.
     * @param ids
     * @return the number of removed entities
     * @throws DaoException
     */
    public int delete(boolean flush, Long... ids) throws DaoException;

    /**
     * Remove permanently the entities whose identifiers are contained in the array <code>ids</code>, flushing the changes to the database. Returns
     * the number of entities removed. If any identifier does not correspond to any existent entity, do nothing on it and continue the deletion. Any
     * exception thrown by the underlying implementation is encapsulated in a DaoException.
     * @param ids
     * @return the number of removed entities
     * @throws DaoException
     */
    public int delete(Long... ids) throws DaoException;

}
