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
package nl.strohalm.cyclos.dao.accounts.fee.account;

import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import nl.strohalm.cyclos.dao.BaseDAO;
import nl.strohalm.cyclos.dao.DeletableDAO;
import nl.strohalm.cyclos.dao.InsertableDAO;
import nl.strohalm.cyclos.dao.UpdatableDAO;
import nl.strohalm.cyclos.entities.accounts.MemberAccount;
import nl.strohalm.cyclos.entities.accounts.fees.account.AccountFee;
import nl.strohalm.cyclos.entities.accounts.fees.account.AccountFeeAmount;
import nl.strohalm.cyclos.entities.accounts.fees.account.AccountFeeLog;
import nl.strohalm.cyclos.entities.accounts.fees.account.AccountFeeLogQuery;
import nl.strohalm.cyclos.entities.exceptions.DaoException;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;

/**
 * Interface for account fee log DAO
 * 
 * @author rafael
 * @author fireblade
 */
public interface AccountFeeLogDAO extends BaseDAO<AccountFeeLog>, InsertableDAO<AccountFeeLog>, UpdatableDAO<AccountFeeLog>, DeletableDAO<AccountFeeLog> {

    /**
     * Returns an existing log for the given fee and date
     * @throws EntityNotFoundException When no such log exists
     */
    AccountFeeLog forDate(AccountFee accountFee, Calendar date) throws EntityNotFoundException;

    /**
     * Returns the next account fee log to charge, or null if none
     */
    AccountFeeLog nextToCharge();

    /**
     * Iterates over all member accounts with {@link AccountFeeAmount}s related to the given {@link AccountFeeLog}
     */
    Iterator<MemberAccount> iterateOverAccountsWithAccountFeeChargesFor(AccountFeeLog log);

    /**
     * Searches for account fee logs, ordering by date descending. If no entity can be found, returns an empty List. If any exception is thrown by the
     * underlying implementation, it should be wrapped by a DaoException.
     * 
     * @throws DaoException
     */
    List<AccountFeeLog> search(AccountFeeLogQuery queryParameters) throws DaoException;
}
