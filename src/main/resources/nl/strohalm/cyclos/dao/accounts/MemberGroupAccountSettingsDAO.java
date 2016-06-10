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
package nl.strohalm.cyclos.dao.accounts;

import nl.strohalm.cyclos.dao.BaseDAO;
import nl.strohalm.cyclos.dao.DeletableDAO;
import nl.strohalm.cyclos.dao.InsertableDAO;
import nl.strohalm.cyclos.dao.UpdatableDAO;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.MemberGroupAccountSettings;
import nl.strohalm.cyclos.entities.exceptions.DaoException;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;

/**
 * Data access object interface for the settings between account types and groups
 * @author rafael
 */
public interface MemberGroupAccountSettingsDAO extends BaseDAO<MemberGroupAccountSettings>, InsertableDAO<MemberGroupAccountSettings>, UpdatableDAO<MemberGroupAccountSettings>, DeletableDAO<MemberGroupAccountSettings> {

    /**
     * Removes an existing setting associating MemberGroup identified by <code>groupId</code> and AccountType identified by
     * <code>accountTypeId</code>. If there's no such setting, do nothing. If any exception is thrown by the underlying implementation, it should
     * be wrapped by a DaoException.
     * 
     * @throws EntityNotFoundException When there is no such relationship between the given group and account type
     * @throws DaoException
     */
    void delete(long groupId, long accountTypeId) throws EntityNotFoundException, DaoException;

    /**
     * Returns an existing setting associating MemberGroup identified by <code>groupId</code> and AccountType identified by
     * <code>accountTypeId</code>. If there's no such setting, returns null. This method may also expect a third parameter <code>fetch</code>,
     * specifying relationships that should be populated by the underlying implementation. If any exception is thrown by the underlying
     * implementation, it should be wrapped by a DaoException.
     * 
     * @throws EntityNotFoundException When there is no such relationship between the given group and account type
     * @throws DaoException
     */
    MemberGroupAccountSettings load(long groupId, long accountTypeId, Relationship... fetch) throws EntityNotFoundException, DaoException;
}
