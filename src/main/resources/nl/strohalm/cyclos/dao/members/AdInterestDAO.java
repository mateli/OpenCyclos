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
package nl.strohalm.cyclos.dao.members;

import java.util.Iterator;
import java.util.List;

import nl.strohalm.cyclos.dao.BaseDAO;
import nl.strohalm.cyclos.dao.DeletableDAO;
import nl.strohalm.cyclos.dao.InsertableDAO;
import nl.strohalm.cyclos.dao.UpdatableDAO;
import nl.strohalm.cyclos.entities.ads.Ad;
import nl.strohalm.cyclos.entities.exceptions.DaoException;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.members.adInterests.AdInterest;
import nl.strohalm.cyclos.entities.members.adInterests.AdInterestQuery;

/**
 * Interface for ad interest dao
 * @author jefferson
 */
public interface AdInterestDAO extends BaseDAO<AdInterest>, InsertableDAO<AdInterest>, DeletableDAO<AdInterest>, UpdatableDAO<AdInterest> {

    /**
     * Iterates over all ad interests for all members
     */
    public Iterator<Member> resolveMembersToNotify(Ad ad);

    /**
     * Searches for ad interests. If no entity can be found, returns an empty list. If any exception is thrown by the underlying implementation, it
     * should be wrapped by a DaoException.
     */
    public List<AdInterest> search(AdInterestQuery queryParameters) throws DaoException;

}
