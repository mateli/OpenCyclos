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

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import nl.strohalm.cyclos.dao.BaseDAO;
import nl.strohalm.cyclos.dao.DeletableDAO;
import nl.strohalm.cyclos.dao.InsertableDAO;
import nl.strohalm.cyclos.dao.UpdatableDAO;
import nl.strohalm.cyclos.entities.accounts.AccountType;
import nl.strohalm.cyclos.entities.accounts.AccountTypeQuery;
import nl.strohalm.cyclos.entities.accounts.MemberAccountType;
import nl.strohalm.cyclos.entities.accounts.SystemAccountType;
import nl.strohalm.cyclos.entities.exceptions.DaoException;
import nl.strohalm.cyclos.entities.groups.MemberGroup;

/**
 * Interface for account type DAO
 * @author rafael
 */
public interface AccountTypeDAO extends BaseDAO<AccountType>, InsertableDAO<AccountType>, UpdatableDAO<AccountType>, DeletableDAO<AccountType> {

    /**
     * Returns all account types
     */
    public List<? extends AccountType> listAll();

    /**
     * Searches for account types, ordering by name. If no entity can be found, returns an empty list. If any exception is thrown by the underlying
     * implementation, it should be wrapped by a DaoException.
     */
    public List<AccountType> search(AccountTypeQuery query) throws DaoException;

    /**
     * Returns the total balance of the accounts of a given system account type
     */
    BigDecimal getBalance(MemberAccountType accountType, Collection<MemberGroup> groups, Calendar timePoint);

    /**
     * Returns the total balance of the accounts of a given system account type
     */
    BigDecimal getBalance(SystemAccountType accountType, Calendar timePoint);

}
