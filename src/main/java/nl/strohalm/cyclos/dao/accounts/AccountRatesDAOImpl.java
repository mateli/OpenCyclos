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
import nl.strohalm.cyclos.entities.accounts.Account;
import nl.strohalm.cyclos.entities.accounts.AccountRates;
import nl.strohalm.cyclos.entities.accounts.Currency;
import nl.strohalm.cyclos.services.accounts.rates.WhatRate;
import nl.strohalm.cyclos.utils.DateHelper;
import nl.strohalm.cyclos.utils.Period;
import nl.strohalm.cyclos.utils.hibernate.HibernateHelper;

/**
 * Implementation for {@link AccountRatesDAO}
 * @author rinke
 */
public class AccountRatesDAOImpl extends BaseDAOImpl<AccountRates> implements AccountRatesDAO {

    public AccountRatesDAOImpl() {
        super(AccountRates.class);
    }

    @Override
    public void bulkDelete(final Account account, final Period period) {
        final Map<String, Object> namedParameters = new HashMap<String, Object>();
        final StringBuilder hql = new StringBuilder();
        hql.append("delete from AccountRates where ");
        hql.append(" account = :account ");
        namedParameters.put("account", account);
        // because joins in bulk deletes are not supported, we must do it via this subQuery
        hql.append(" and lastTransfer in ");
        hql.append("      (from Transfer t where ");
        // strictly seen, this could be omitted (and replaced by 1=1) because it just matters if the id is in the subquery, and it will be even
        // without the account clause
        hql.append(" (t.from.id = :account or t.to.id = :account) ");
        HibernateHelper.addPeriodParameterToQuery(hql, namedParameters, "t.processDate", period);
        hql.append(" )      ");
        bulkUpdate(hql.toString(), namedParameters);
    }

    @Override
    public void bulkUpdateWithNull(final Currency currency, final Period period, final WhatRate whatRate) {
        final Map<String, Object> namedParameters = new HashMap<String, Object>();
        boolean any = whatRate.isaRate() || whatRate.isdRate() || whatRate.isiRate();
        if (!any) {
            return;
        }
        final StringBuilder hql = new StringBuilder();
        hql.append("update AccountRates set ");
        boolean commaNeeded = false;
        if (whatRate.isaRate()) {
            hql.append(" emissionDate = null ");
            commaNeeded = true;
        }
        if (whatRate.isdRate()) {
            hql.append(commaNeeded ? " , " : "").append(" expirationDate = null ");
            commaNeeded = true;
        }
        if (whatRate.isiRate()) {
            hql.append(commaNeeded ? " , " : "").append(" iRate = null ");
            commaNeeded = true;
        }
        if (any) {
            hql.append(" , rateBalanceCorrection = null ");
        }
        hql.append(" where 1 = 1 ");
        if (currency != null) {
            // because joins in bulk deletes are not supported, we must do it via this tricky subQuery
            hql.append(" and account in ");
            hql.append("      (from Account a where 1 = 1 ");
            hql.append("       and a.type.currency = :currency ) ");
            namedParameters.put("currency", currency);
        }
        // same construction with subQuery here
        hql.append(" and lastTransfer in ");
        hql.append("      (from Transfer t where 1 = 1 ");
        HibernateHelper.addPeriodParameterToQuery(hql, namedParameters, "t.processDate", period);
        hql.append(" )      ");
        bulkUpdate(hql.toString(), namedParameters);
    }

    @Override
    public AccountRates getLatestRates(final Account account, final Calendar date) throws IllegalArgumentException {
        // get the last available rate entity
        AccountRates lastKnownRates = getRates(account, null);
        if (lastKnownRates == null) {
            // if rates have just been introduced/enabled, there will be no AccountRates available. Just create one then.
            AccountRates result = new AccountRates();
            result.setAccount(account);
            return result;
        }
        final Calendar requestedDate = (date == null) ? Calendar.getInstance() : date;
        if (DateHelper.sameDay(requestedDate, lastKnownRates.getLastTransfer().getProcessDate())) {
            // IF the last known rate is from the same date as the requested, just return it.
            return lastKnownRates;
        }
        if (requestedDate.before(lastKnownRates.getLastTransfer().getProcessDate())) {
            // if a lastKnownRate is known after the requested date, we're dealing with payments in past, which is not allowed with rates.
            throw new IllegalArgumentException("payments in past not allowed with rates enabled.");
        }
        // if the requestedDate is of a later day then the last known rate, a new record must be made, so duplicated and return.
        AccountRates newRates = this.duplicate(lastKnownRates);
        return newRates;
    }

    @Override
    public AccountRates getLatestReinitializedRates(final Account account, final Calendar date, final WhatRate whatRate) {
        Period period = Period.endingAt(date);
        // no period.setUseTime(true), because we should just find the latest reinitialized entiy, while the saved lastTransfer may be of a later time
        // that day.
        Map<String, Object> params = new HashMap<String, Object>();
        StringBuilder hql = new StringBuilder();
        hql.append(" from AccountRates r ");
        hql.append(" where 1=1 ");
        hql.append(" and r.account = :account ");
        params.put("account", account);
        HibernateHelper.addPeriodParameterToQuery(hql, params, "r.lastTransfer.processDate", period);
        hql.append(" order by r.lastTransfer.processDate desc");
        List<AccountRates> list = list(hql.toString(), params);
        AccountRates entity = null;
        boolean empty = false;
        if (list.size() > 0) {
            entity = list.get(0);
            if ((whatRate.isaRate() && entity.getEmissionDate() == null)
                    || (whatRate.isdRate() && entity.getExpirationDate() == null)
                    || (whatRate.isiRate() && entity.getiRate() == null)) {
                // this one is not yet processed, check out the previous if available
                if (list.size() > 1) {
                    // we need to look back only one item, as this is called from an initializing iteration, and earlier items must be initialized yet
                    // or non-existing
                    entity = list.get(1);
                    if ((whatRate.isaRate() && entity.getEmissionDate() == null)
                            || (whatRate.isdRate() && entity.getExpirationDate() == null)
                            || (whatRate.isiRate() && entity.getiRate() == null)) {
                        empty = true;
                    }
                } else {
                    empty = true;
                }
            }
        } else {
            empty = true;
        }
        return (empty) ? new AccountRates() : entity;
    }

    @Override
    public AccountRates getRates(final Account account, final Calendar date) {
        Map<String, Object> params = new HashMap<String, Object>();
        StringBuilder hql = new StringBuilder();
        hql.append(" from AccountRates r ");
        hql.append(" where 1=1 ");
        hql.append(" and r.account = :account ");
        params.put("account", account);
        if (date != null) {
            hql.append(" and r.lastTransfer.processDate <= :date ");
            params.put("date", date);
        }
        hql.append(" order by r.lastTransfer.processDate desc");
        return uniqueResult(hql.toString(), params);
    }

    @Override
    public AccountRates getTodaysRates(final Account account, final Calendar date) {
        Period period = Period.exact(date);
        Map<String, Object> params = new HashMap<String, Object>();
        StringBuilder hql = new StringBuilder();
        hql.append(" from AccountRates r ");
        hql.append(" where 1=1 ");
        hql.append(" and r.account = :account ");
        params.put("account", account);
        HibernateHelper.addPeriodParameterToQuery(hql, params, "r.lastTransfer.processDate", period);
        hql.append(" order by r.lastTransfer.processDate desc");
        AccountRates result = uniqueResult(hql.toString(), params);
        if (result == null) {
            // if none available for today, get the last available
            result = duplicate(getRates(account, date));
            // if still null, there is none available, just create a new and empty one
            if (result == null) {
                result = new AccountRates();
                result.setAccount(account);
            }
        }
        return result;
    }

}
