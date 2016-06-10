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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nl.strohalm.cyclos.dao.BaseDAOImpl;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.fees.account.AccountFee;
import nl.strohalm.cyclos.entities.accounts.fees.account.AccountFeeQuery;
import nl.strohalm.cyclos.utils.hibernate.HibernateHelper;

/**
 * Implementation DAO for account fees
 * @author rafael
 */
public class AccountFeeDAOImpl extends BaseDAOImpl<AccountFee> implements AccountFeeDAO {

    public AccountFeeDAOImpl() {
        super(AccountFee.class);
    }

    @Override
    public List<AccountFee> search(final AccountFeeQuery query) {
        final Map<String, Object> namedParameters = new HashMap<String, Object>();
        final Set<Relationship> fetch = query.getFetch();
        final StringBuilder hql = HibernateHelper.getInitialQuery(getEntityType(), "af", fetch);
        HibernateHelper.addParameterToQuery(hql, namedParameters, "af.accountType", query.getAccountType());
        HibernateHelper.addParameterToQuery(hql, namedParameters, "af.runMode", query.getType());
        HibernateHelper.addParameterToQuery(hql, namedParameters, "af.recurrence.field", query.getRecurrence());
        HibernateHelper.addParameterToQuery(hql, namedParameters, "af.day", query.getDay());
        HibernateHelper.addParameterToQuery(hql, namedParameters, "af.hour", query.getHour());
        HibernateHelper.addParameterToQueryOperator(hql, namedParameters, "af.enabledSince", "<=", query.getEnabledBefore());
        if (query.getGroups() != null && !query.getGroups().isEmpty()) {
            hql.append(" and exists (select g.id from MemberGroup g where g in (:groups) and g in elements(af.groups)) ");
            namedParameters.put("groups", query.getGroups());
        }
        if (!query.isReturnDisabled()) {
            hql.append(" and af.enabled = true and af.enabledSince is not null");
        }
        HibernateHelper.appendOrder(hql, "af.name");
        return list(query, hql.toString(), namedParameters);
    }

}
