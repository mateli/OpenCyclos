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
import java.util.List;
import java.util.Map;

import nl.strohalm.cyclos.dao.BaseDAOImpl;
import nl.strohalm.cyclos.entities.accounts.Currency;
import nl.strohalm.cyclos.entities.accounts.RateParameters;
import nl.strohalm.cyclos.utils.Period;

/**
 * general DAO impl for any RateParameters type.
 * 
 * @author Rinke
 */
public abstract class RateParametersDAOImpl<R extends RateParameters> extends BaseDAOImpl<R> implements RateParametersDAO<R> {

    public RateParametersDAOImpl(final Class<R> entityClass) {
        super(entityClass);
    }

    @Override
    public R getByDate(final Currency currency, final Calendar date) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("currency", currency);
        StringBuilder hql = new StringBuilder();
        hql.append(" from ").append(getEntityType().getName()).append(" r ");
        hql.append(" where r.currency = :currency ");
        if (date != null) {
            hql.append(" and r.enabledSince <= :date ");
            hql.append(" and (r.disabledSince is null or r.disabledSince > :date) ");
            params.put("date", date);
        }
        hql.append(" order by r.enabledSince desc");
        return uniqueResult(hql.toString(), params);
    }

    @Override
    public List<R> getByPeriod(final Currency currency, final Period period) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("currency", currency);
        StringBuilder hql = new StringBuilder();
        hql.append(" from ").append(getEntityType().getName()).append(" r ");
        hql.append(" where r.currency = :currency ");
        // don't use HibernateHelper.addPeriodParameterToQuery, because that tests if the stored enabledSince date falls in the period.
        // In stead, we want to get a list of all entities with the period between enabledSince and DisabledSince overlapping the period.
        // So that includes entities with enabledSince BEFORE the period.
        if (period != null && period.getBegin() != null) {
            params.put("startDate", period.getBegin());
            hql.append(" and (r.disabledSince is null or (r.disabledSince is not null and r.disabledSince > :startDate)) ");
        }
        if (period != null && period.getEnd() != null) {
            params.put("endDate", period.getEnd());
            hql.append(" and (r.enabledSince <= :endDate)) ");
        }
        hql.append(" order by r.enabledSince desc");
        return list(hql.toString(), params);
    }

}
