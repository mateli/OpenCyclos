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
package nl.strohalm.cyclos.dao.customizations;

import java.util.List;

import nl.strohalm.cyclos.dao.BaseDAO;
import nl.strohalm.cyclos.dao.DeletableDAO;
import nl.strohalm.cyclos.dao.InsertableDAO;
import nl.strohalm.cyclos.dao.UpdatableDAO;
import nl.strohalm.cyclos.entities.customization.documents.Document;
import nl.strohalm.cyclos.entities.customization.documents.DocumentQuery;
import nl.strohalm.cyclos.entities.exceptions.DaoException;

/**
 * Data access object interface for custom documents
 * @author luis
 */
public interface DocumentDAO extends BaseDAO<Document>, InsertableDAO<Document>, UpdatableDAO<Document>, DeletableDAO<Document> {

    /**
     * Delete documents with the given ids, returning the number of deleted documents. As there is a double relationship between documents and pages,
     * this method should also delete the document pages
     * @see nl.strohalm.cyclos.dao.DeletableDAO#delete(java.lang.Long[])
     */
    public int delete(Long... ids) throws DaoException;

    /**
     * Searches documents, ordering by name. If no entity can be found, returns an empty list. If any exception is thrown by the underlying
     * implementation, it should be wrapped by a DaoException.
     */
    List<Document> search(DocumentQuery query) throws DaoException;
}
