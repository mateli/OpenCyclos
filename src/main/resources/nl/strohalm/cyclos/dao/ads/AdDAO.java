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

import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import nl.strohalm.cyclos.dao.BaseDAO;
import nl.strohalm.cyclos.dao.DeletableDAO;
import nl.strohalm.cyclos.dao.IndexedDAO;
import nl.strohalm.cyclos.dao.InsertableDAO;
import nl.strohalm.cyclos.dao.UpdatableDAO;
import nl.strohalm.cyclos.entities.ads.Ad;
import nl.strohalm.cyclos.entities.ads.AdCategory;
import nl.strohalm.cyclos.entities.ads.AdCategoryWithCounterQuery;
import nl.strohalm.cyclos.entities.ads.AdCategoryWithCounterVO;
import nl.strohalm.cyclos.entities.ads.AdQuery;
import nl.strohalm.cyclos.entities.ads.FullTextAdQuery;
import nl.strohalm.cyclos.entities.exceptions.DaoException;
import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.utils.Period;

/**
 * Interface for ad DAO
 * @author rafael
 */
public interface AdDAO extends BaseDAO<Ad>, InsertableDAO<Ad>, UpdatableDAO<Ad>, DeletableDAO<Ad>, IndexedDAO<Ad> {

    /**
     * Searches for Ads using a full text search, sorting results by relevance. The searched fields using keywords are:
     * <ul>
     * <li>title</li>
     * <li>description</li>
     * <li>custom fields</li>
     * <li>owner name</li>
     * <li>owner username</li>
     * <li>owner email</li>
     * <li>owner custom fields</li>
     * </ul>
     */
    public List<Ad> fullTextSearch(FullTextAdQuery query) throws DaoException;

    /**
     * Returns the counters for each of the given ad category, given the ad query
     */
    public List<AdCategoryWithCounterVO> getCategoriesWithCounters(final List<AdCategory> categories, final AdCategoryWithCounterQuery query);

    /**
     * Get the number of ads of the given status and at the given date, and with the member belonging to the given group.
     * 
     * @param date usually the enddate of a period
     * @param groups a Collection containing the group of which the number of ads is requested. If this is an empty collection, the param is ignored.
     * @param status the ad status requested
     * @return an Integer with the number of ads.
     */
    public Integer getNumberOfAds(Calendar date, final Collection<? extends Group> groups, Ad.Status status) throws DaoException;

    /**
     * Get the number of ads created in the given period
     */
    public Integer getNumberOfCreatedAds(Period period, final Collection<? extends Group> groups) throws DaoException;

    /**
     * Get the number of active members with ads at the given date, and with the member belonging to the given groups.
     */
    public Integer getNumberOfMembersWithAds(Calendar date, final Collection<? extends Group> groups) throws DaoException;

    /**
     * Searches for Ads. The results are expected to be ordered by publication start descending, or randomly if randomOrder is true on the query
     * parameters. If no entity can be found, returns an empty list. If any exception is thrown by the underlying implementation, it should be wrapped
     * by a DaoException.
     */
    public List<Ad> search(AdQuery queryParameters) throws DaoException;

}
