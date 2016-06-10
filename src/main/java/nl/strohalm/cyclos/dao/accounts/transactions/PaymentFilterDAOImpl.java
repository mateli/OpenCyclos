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
package nl.strohalm.cyclos.dao.accounts.transactions;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nl.strohalm.cyclos.dao.BaseDAOImpl;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.Account;
import nl.strohalm.cyclos.entities.accounts.AccountType;
import nl.strohalm.cyclos.entities.accounts.MemberAccountType;
import nl.strohalm.cyclos.entities.accounts.MemberGroupAccountSettings;
import nl.strohalm.cyclos.entities.accounts.SystemAccountType;
import nl.strohalm.cyclos.entities.accounts.transactions.PaymentFilter;
import nl.strohalm.cyclos.entities.accounts.transactions.PaymentFilterQuery;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.utils.hibernate.HibernateHelper;

import org.apache.commons.collections.CollectionUtils;

/**
 * Implementation class for payment filter DAO
 * @author rafael, jefferson
 */
public class PaymentFilterDAOImpl extends BaseDAOImpl<PaymentFilter> implements PaymentFilterDAO {

    public PaymentFilterDAOImpl() {
        super(PaymentFilter.class);
    }

    public List<PaymentFilter> search(final PaymentFilterQuery query) {
        final Map<String, Object> namedParameters = new HashMap<String, Object>();
        final Set<Relationship> fetch = query.getFetch();
        final StringBuilder hql = HibernateHelper.getInitialQuery(getEntityType(), "pf", fetch);
        HibernateHelper.addLikeParameterToQuery(hql, namedParameters, "pf.description", query.getDescription());
        HibernateHelper.addLikeParameterToQuery(hql, namedParameters, "pf.name", query.getName());

        // Account or account types
        Collection<AccountType> accountTypes = query.getAccountTypes();
        final Account ac = query.getAccount();
        if (CollectionUtils.isEmpty(accountTypes) && ac != null) {
            final AccountType at = getFetchDao().fetch(ac, Account.Relationships.TYPE).getType();
            accountTypes = Collections.singletonList(at);
        }
        HibernateHelper.addInParameterToQuery(hql, namedParameters, "pf.accountType", accountTypes);

        // Context
        if (query.getContext() == PaymentFilterQuery.Context.ACCOUNT_HISTORY) {
            hql.append(" and pf.showInAccountHistory = true ");
        }
        if (query.getContext() == PaymentFilterQuery.Context.REPORT) {
            hql.append(" and pf.showInReports = true ");
        }

        // Element
        if (query.getElement() != null) {
            hql.append(" and exists (select e.id from ").append(Element.class.getName()).append(" e where e = :element and pf in elements(e.group.paymentFilters)) ");
            namedParameters.put("element", query.getElement());
        }

        // Member groups
        if (!CollectionUtils.isEmpty(query.getMemberGroups())) {
            hql.append(" and exists (select mgas.id from ").append(MemberGroupAccountSettings.class.getName()).append(" mgas where mgas.group in (:memberGroups) and pf.accountType = mgas.accountType ) ");
            namedParameters.put("memberGroups", query.getMemberGroups());
        }

        // Nature
        if (query.getNature() != null) {
            Class<?> clazz = SystemAccountType.class;
            if (query.getNature() == AccountType.Nature.MEMBER) {
                clazz = MemberAccountType.class;
            }
            hql.append(" and exists (select 1 from ").append(clazz.getName()).append(" nat where nat = pf.accountType) ");
        }
        HibernateHelper.appendOrder(hql, "pf.accountType.name", "pf.name");
        return list(query, hql.toString(), namedParameters);
    }

}
