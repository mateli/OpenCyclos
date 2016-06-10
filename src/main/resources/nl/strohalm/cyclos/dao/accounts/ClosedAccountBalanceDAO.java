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

import java.util.Calendar;

import nl.strohalm.cyclos.dao.BaseDAO;
import nl.strohalm.cyclos.dao.InsertableDAO;
import nl.strohalm.cyclos.entities.accounts.Account;
import nl.strohalm.cyclos.entities.accounts.ClosedAccountBalance;

/**
 * DAO interface for {@link ClosedAccountBalance}
 * 
 * @author luis
 */
public interface ClosedAccountBalanceDAO extends BaseDAO<ClosedAccountBalance>, InsertableDAO<ClosedAccountBalance> {

    /**
     * Returns the last closed account balance for the given account, or null if none found. Note that the date param is EXCLUSIVE, that is,
     * ClosedAccountBalances dated on the exact date/time of the date param are NOT returned.
     */
    ClosedAccountBalance get(Account account, Calendar date);

    /**
     * Removes closed account balances for the given account after the given date
     */
    void removeClosedBalancesAfter(Account account, Calendar date);

}
