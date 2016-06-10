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
package nl.strohalm.cyclos.dao.ads;

import java.util.Iterator;
import java.util.List;

import nl.strohalm.cyclos.dao.BaseDAO;
import nl.strohalm.cyclos.dao.DeletableDAO;
import nl.strohalm.cyclos.dao.InsertableDAO;
import nl.strohalm.cyclos.dao.UpdatableDAO;
import nl.strohalm.cyclos.entities.ads.AdCategory;
import nl.strohalm.cyclos.entities.ads.AdCategoryQuery;
import nl.strohalm.cyclos.entities.exceptions.DaoException;

/**
 * Interface for category DAO
 * @author rafael
 * @author Lucas Geiss
 */
public interface AdCategoryDAO extends BaseDAO<AdCategory>, InsertableDAO<AdCategory>, UpdatableDAO<AdCategory>, DeletableDAO<AdCategory> {

    public List<Long> getActiveCategoriesId();

    /**
     * Iterates over all categories, with no assumed ordering
     */
    public Iterator<AdCategory> iterateAll();;

    /**
     * Returns a List containing Categories related parameters. If no Category can be found, returns an empty List. If any exception is thrown by the
     * underlying implementation, it should be wrapped by a DaoException. All returned categories should have fetched the parent and children
     * relationships
     * @throws DaoException
     */
    public List<AdCategory> search(AdCategoryQuery queryParameters) throws DaoException;

    /**
     * Returns a List containing all leaf Categories. If no Category can be found, returns an empty List.
     * @throws DaoException
     */
    public List<AdCategory> searchLeafAdCategories(final AdCategoryQuery query) throws DaoException;

}
