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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nl.strohalm.cyclos.dao.BaseDAOImpl;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.MemberAccount;
import nl.strohalm.cyclos.entities.accounts.fees.account.AccountFee;
import nl.strohalm.cyclos.entities.accounts.fees.account.AccountFeeLog;
import nl.strohalm.cyclos.entities.accounts.fees.account.AccountFeeLogQuery;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.utils.Period;
import nl.strohalm.cyclos.utils.hibernate.HibernateHelper;

/**
 * Default implementation for AccountFeeLogDAO component. Extends Spring's Hibernate Support. Delegates basic operations to instances of BaseDAO and
 * InsertableDAO.
 * 
 * @author rafael
 * @author fireblade
 */
public class AccountFeeLogDAOImpl extends BaseDAOImpl<AccountFeeLog> implements AccountFeeLogDAO {

    public AccountFeeLogDAOImpl() {
        super(AccountFeeLog.class);
    }

    @Override
    public AccountFeeLog forDate(final AccountFee accountFee, final Calendar date) throws EntityNotFoundException {
        final Map<String, Object> params = new HashMap<String, Object>();
        params.put("accountFee", accountFee);
        params.put("date", date == null ? Calendar.getInstance() : date);
        final AccountFeeLog log = uniqueResult("from AccountFeeLog l where l.accountFee = :accountFee and :date between l.period.begin and l.period.end", params);
        if (log == null) {
            throw new EntityNotFoundException(getEntityType());
        }
        return log;
    }

    @Override
    public Iterator<MemberAccount> iterateOverAccountsWithAccountFeeChargesFor(final AccountFeeLog log) {
        final Map<String, Object> namedParameters = new HashMap<String, Object>();
        final StringBuilder hql = HibernateHelper.getInitialQuery(MemberAccount.class, "a");
        hql.append(" and exists(select c.id from AccountFeeCharge c where c.account = a and c.accountFeeLog = :log)");
        namedParameters.put("log", log);
        return iterate(hql.toString(), namedParameters);
    }

    @Override
    public AccountFeeLog nextToCharge() {
        return uniqueResult("from AccountFeeLog l where l.date <= now() and l.finishDate is null or l.rechargingFailed = true", null);
    }

    @Override
    public List<AccountFeeLog> search(final AccountFeeLogQuery query) {
        final Map<String, Object> namedParameters = new HashMap<String, Object>();
        final Set<Relationship> fetch = query.getFetch();
        final StringBuilder hql = HibernateHelper.getInitialQuery(getEntityType(), "l", fetch);
        HibernateHelper.addInParameterToQuery(hql, namedParameters, "l.accountFee", query.getAccountFees());
        HibernateHelper.addParameterToQuery(hql, namedParameters, "l.accountFee.enabled", query.getAccountFeeEnabled());
        HibernateHelper.addParameterToQuery(hql, namedParameters, "l.accountFee.accountType", query.getAccountType());
        if (query.getPeriodStartAt() != null) {
            HibernateHelper.addPeriodParameterToQuery(hql, namedParameters, "l.period.begin", Period.day(query.getPeriodStartAt()));
        }
        HibernateHelper.appendOrder(hql, "l.date desc, l.id desc");
        return list(query, hql.toString(), namedParameters);
    }
}
