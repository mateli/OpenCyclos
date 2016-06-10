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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.strohalm.cyclos.dao.BaseDAOImpl;
import nl.strohalm.cyclos.dao.accounts.AccountDAO;
import nl.strohalm.cyclos.entities.accounts.Account;
import nl.strohalm.cyclos.entities.accounts.AccountQuery;
import nl.strohalm.cyclos.entities.accounts.MemberAccount;
import nl.strohalm.cyclos.entities.accounts.MemberAccountType;
import nl.strohalm.cyclos.entities.accounts.transactions.Payment;
import nl.strohalm.cyclos.entities.accounts.transactions.ScheduledPayment;
import nl.strohalm.cyclos.entities.accounts.transactions.ScheduledPaymentQuery;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.utils.hibernate.HibernateHelper;

import org.apache.commons.collections.CollectionUtils;

/**
 * Implementation for ScheduledPaymentDAO
 * @author luis, Jefferson Magno
 */
public class ScheduledPaymentDAOImpl extends BaseDAOImpl<ScheduledPayment> implements ScheduledPaymentDAO {

    private AccountDAO accountDao;

    public ScheduledPaymentDAOImpl() {
        super(ScheduledPayment.class);
    }

    @Override
    public List<ScheduledPayment> getUnrelatedPendingPayments(final Member member, final Collection<MemberAccountType> accountTypes) {
        final Map<String, Object> namedParameters = new HashMap<String, Object>();
        namedParameters.put("_accountTypes_", accountTypes);
        namedParameters.put("_pending_", Arrays.asList(Payment.Status.PENDING, Payment.Status.SCHEDULED, Payment.Status.BLOCKED));
        namedParameters.put("_member_", member);

        final StringBuilder hql = new StringBuilder("SELECT sp from ");
        hql.append(ScheduledPayment.class.getName()).append(" ");
        hql.append("sp WHERE sp.status in (:_pending_) ");
        if (accountTypes.isEmpty()) {
            hql.append("AND (sp.from.member = :_member_ OR sp.to.member = :_member_) ");
        } else {
            hql.append("AND (sp.from.member = :_member_ AND sp.from.type NOT IN (:_accountTypes_) OR sp.to.member = :_member_ AND sp.to.type NOT IN (:_accountTypes_)) ");
        }

        return list(hql.toString(), namedParameters);
    }

    @Override
    public List<ScheduledPayment> search(final ScheduledPaymentQuery query) {
        final Map<String, Object> namedParameters = new HashMap<String, Object>();
        final StringBuilder hql = HibernateHelper.getInitialQuery(ScheduledPayment.class, "sp", query.getFetch());
        HibernateHelper.addInParameterToQuery(hql, namedParameters, "sp.status", query.getStatusList());
        HibernateHelper.addPeriodParameterToQuery(hql, namedParameters, "sp.date", query.getPeriod());

        // Account owner
        List<? extends Account> ownerAccounts = new ArrayList<Account>();
        final AccountQuery accountQuery = new AccountQuery();
        accountQuery.setOwner(query.getOwner());
        if (query.getAccountType() != null) {
            accountQuery.setType(query.getAccountType());
        }
        ownerAccounts = accountDao.search(accountQuery);
        if (CollectionUtils.isEmpty(ownerAccounts)) {
            // No accounts - nothing will be returned
            return Collections.emptyList();
        }
        namedParameters.put("ownerAccounts", ownerAccounts);

        // Member
        List<? extends Account> otherAccounts = new ArrayList<MemberAccount>();
        if (query.getMember() != null) {
            final AccountQuery otherAccountsQuery = new AccountQuery();
            otherAccountsQuery.setOwner(query.getMember());
            otherAccounts = accountDao.search(otherAccountsQuery);
            if (CollectionUtils.isEmpty(otherAccounts)) {
                // No accounts - nothing will be returned
                return Collections.emptyList();
            }
            namedParameters.put("otherAccounts", otherAccounts);
        }

        // Search type
        if (query.getSearchType() == ScheduledPaymentQuery.SearchType.OUTGOING) {
            hql.append(" and sp.from in (:ownerAccounts) ");
            if (CollectionUtils.isNotEmpty(otherAccounts)) {
                hql.append(" and sp.to in (:otherAccounts) ");
            }
        } else {
            hql.append(" and sp.to in (:ownerAccounts) ");
            if (CollectionUtils.isNotEmpty(otherAccounts)) {
                hql.append(" and sp.from in (:otherAccounts) ");
            }
            hql.append(" and sp.showToReceiver = true");
        }

        HibernateHelper.appendOrder(hql, "sp.date desc");

        return list(query, hql.toString(), namedParameters);
    }

    public void setAccountDao(final AccountDAO accountDao) {
        this.accountDao = accountDao;
    }

}
