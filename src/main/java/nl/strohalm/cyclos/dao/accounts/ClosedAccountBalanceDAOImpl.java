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
import java.util.HashMap;
import java.util.Map;

import nl.strohalm.cyclos.dao.BaseDAOImpl;
import nl.strohalm.cyclos.entities.accounts.Account;
import nl.strohalm.cyclos.entities.accounts.ClosedAccountBalance;

/**
 * Implementation for {@link ClosedAccountBalanceDAO}
 * 
 * @author luis
 */
public class ClosedAccountBalanceDAOImpl extends BaseDAOImpl<ClosedAccountBalance> implements ClosedAccountBalanceDAO {

    public ClosedAccountBalanceDAOImpl() {
        super(ClosedAccountBalance.class);
    }

    @Override
    public ClosedAccountBalance get(final Account account, final Calendar date) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("account", account);
        StringBuilder hql = new StringBuilder();
        hql.append(" from ClosedAccountBalance b ");
        hql.append(" where b.account = :account ");
        if (date != null) {
            hql.append(" and b.date < :date ");
            params.put("date", date);
        }
        hql.append(" order by date desc");
        return uniqueResult(hql.toString(), params);
    }

    @Override
    public void removeClosedBalancesAfter(final Account account, final Calendar date) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("account", account);
        params.put("date", date);
        bulkUpdate("delete from ClosedAccountBalance where account = :account and date >= :date", params);
    }

}
