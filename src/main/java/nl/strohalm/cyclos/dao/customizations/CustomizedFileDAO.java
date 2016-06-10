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
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.customization.files.CustomizedFile;
import nl.strohalm.cyclos.entities.customization.files.CustomizedFileQuery;
import nl.strohalm.cyclos.entities.exceptions.DaoException;

/**
 * Data access object interface for customized files
 * @author rafael
 */
public interface CustomizedFileDAO extends BaseDAO<CustomizedFile>, InsertableDAO<CustomizedFile>, UpdatableDAO<CustomizedFile>, DeletableDAO<CustomizedFile> {

    /**
     * Loads a system-wide customized file using the type and name
     */
    CustomizedFile load(CustomizedFile.Type type, String name, Relationship... fetch) throws DaoException;

    /**
     * Lists customized files according to the given query parameters, ordered by type and name. If none can be found, returns an empty List. If any
     * exception is thrown by the underlying implementation, it should be wrapped by a DaoException.
     * 
     * @throws DaoException
     */
    List<CustomizedFile> search(CustomizedFileQuery query) throws DaoException;

}
