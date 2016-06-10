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
import java.util.HashMap;
import java.util.Map;

import nl.strohalm.cyclos.dao.BaseDAOImpl;
import nl.strohalm.cyclos.entities.accounts.Account;
import nl.strohalm.cyclos.entities.accounts.AmountReservation;
import nl.strohalm.cyclos.utils.BigDecimalHelper;
import nl.strohalm.cyclos.utils.Period;
import nl.strohalm.cyclos.utils.hibernate.HibernateHelper;

/**
 * Implementation for {@link AmountReservationDAO}
 * 
 * @author luis
 */
public class AmountReservationDAOImpl extends BaseDAOImpl<AmountReservation> implements AmountReservationDAO {

    public AmountReservationDAOImpl() {
        super(AmountReservation.class);
    }

    @Override
    public BigDecimal reservationDiff(final Account account, final Period period) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("account", account);
        StringBuilder hql = new StringBuilder();
        hql.append("select sum(r.amount) from AmountReservation r where r.account = :account ");
        HibernateHelper.addPeriodParameterToQuery(hql, params, "r.date", period);
        BigDecimal sum = uniqueResult(hql.toString(), params);
        return BigDecimalHelper.nvl(sum);
    }

}
