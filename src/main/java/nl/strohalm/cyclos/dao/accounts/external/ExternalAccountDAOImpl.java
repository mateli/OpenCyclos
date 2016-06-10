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
package nl.strohalm.cyclos.dao.accounts.external;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.strohalm.cyclos.dao.BaseDAOImpl;
import nl.strohalm.cyclos.entities.accounts.external.ExternalAccount;
import nl.strohalm.cyclos.entities.accounts.external.ExternalAccountDetailsVO;
import nl.strohalm.cyclos.entities.accounts.external.ExternalAccountQuery;
import nl.strohalm.cyclos.entities.accounts.external.ExternalTransfer;
import nl.strohalm.cyclos.utils.conversion.CoercionHelper;
import nl.strohalm.cyclos.utils.hibernate.HibernateHelper;

/**
 * Implementation DAO for external accounts
 * @author Lucas Geiss
 */
public class ExternalAccountDAOImpl extends BaseDAOImpl<ExternalAccount> implements ExternalAccountDAO {

    public ExternalAccountDAOImpl() {
        super(ExternalAccount.class);
    }

    public List<ExternalAccount> listAll() {
        return list("from ExternalAccount ea order by ea.name", null);
    }

    public List<ExternalAccountDetailsVO> listExternalAccountOverview(final ExternalAccountQuery query) {

        Map<String, Object> namedParameters = new HashMap<String, Object>();
        StringBuilder hql = new StringBuilder();
        hql.append(" select ea");
        hql.append(" from ExternalAccount ea");
        hql.append(" where 1=1 ");
        HibernateHelper.addInParameterToQuery(hql, namedParameters, "ea.systemAccountType", query.getSystemAccountTypes());
        hql.append(" order by ea.name");
        final List<ExternalAccount> accounts = list(hql.toString(), namedParameters);

        // Get the balances
        final List<ExternalAccountDetailsVO> result = new ArrayList<ExternalAccountDetailsVO>(accounts.size());
        for (final ExternalAccount account : accounts) {
            namedParameters = new HashMap<String, Object>();
            namedParameters.put("account", account);
            namedParameters.put("possibleTransferStatus", EnumSet.of(ExternalTransfer.Status.CHECKED, ExternalTransfer.Status.PROCESSED));
            hql = new StringBuilder();
            hql.append(" select sum(t.amount)");
            hql.append(" from ExternalTransfer t ");
            hql.append(" where t.account = :account ");
            hql.append("   and t.status in (:possibleTransferStatus) ");
            final BigDecimal balance = CoercionHelper.coerce(BigDecimal.class, uniqueResult(hql.toString(), namedParameters));
            result.add(new ExternalAccountDetailsVO(account.getId(), account.getName(), balance));
        }
        return result;
    }

    public List<ExternalAccount> search(final ExternalAccountQuery query) {

        final Map<String, Object> namedParameters = new HashMap<String, Object>();
        final StringBuilder hql = HibernateHelper.getInitialQuery(ExternalAccount.class, "ea", query.getFetch());
        HibernateHelper.addInParameterToQuery(hql, namedParameters, "ea.systemAccountType", query.getSystemAccountTypes());
        HibernateHelper.appendOrder(hql, "ea.name");

        return list(query, hql.toString(), namedParameters);

    }

}
