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
package nl.strohalm.cyclos.dao.accounts.pos;

import java.util.List;

import nl.strohalm.cyclos.dao.BaseDAO;
import nl.strohalm.cyclos.dao.DeletableDAO;
import nl.strohalm.cyclos.dao.InsertableDAO;
import nl.strohalm.cyclos.dao.UpdatableDAO;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.pos.Pos;
import nl.strohalm.cyclos.entities.accounts.pos.PosQuery;
import nl.strohalm.cyclos.entities.exceptions.DaoException;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.entities.members.Member;

/**
 * 
 * @author rodrigo
 */
public interface PosDAO extends BaseDAO<Pos>, InsertableDAO<Pos>, UpdatableDAO<Pos>, DeletableDAO<Pos> {

    /**
     * Returns a list with all POS from the member
     */
    List<Pos> getAllMemberPos(Member member);

    /**
     * Search for a POS with given posId
     */
    Pos loadByPosId(String posId, Relationship... fetch) throws EntityNotFoundException;

    /**
     * Searches for Pos accordingly the parameters provided by <code>queryParameters</code>. If no Pos can be found, returns an empty List. Any
     * exception thrown by the underlying implementation should be wrapped by a DaoException.
     */
    List<Pos> search(PosQuery query) throws DaoException;

}
