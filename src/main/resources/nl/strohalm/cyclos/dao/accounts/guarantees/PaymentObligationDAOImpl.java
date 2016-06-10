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
package nl.strohalm.cyclos.dao.accounts.guarantees;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.strohalm.cyclos.dao.BaseDAOImpl;
import nl.strohalm.cyclos.entities.accounts.guarantees.PaymentObligation;
import nl.strohalm.cyclos.entities.accounts.guarantees.PaymentObligationQuery;
import nl.strohalm.cyclos.entities.exceptions.DaoException;
import nl.strohalm.cyclos.utils.hibernate.HibernateHelper;

import org.apache.commons.collections.CollectionUtils;

public class PaymentObligationDAOImpl extends BaseDAOImpl<PaymentObligation> implements PaymentObligationDAO {

    public PaymentObligationDAOImpl() {
        super(PaymentObligation.class);
    }

    @Override
    public List<PaymentObligation> loadOrderedByExpiration(final Long... ids) {
        final Map<String, Object> namedParameters = new HashMap<String, Object>();

        final StringBuilder hql = new StringBuilder("SELECT po FROM PaymentObligation po where 1=1");

        HibernateHelper.addInParameterToQuery(hql, namedParameters, "po.id", (Object[]) ids);
        HibernateHelper.appendOrder(hql, "po.expirationDate");

        return list(hql.toString(), namedParameters);
    }

    @Override
    public List<PaymentObligation> search(final PaymentObligationQuery queryParameters) throws DaoException {
        final Map<String, Object> namedParameters = new HashMap<String, Object>();
        final StringBuilder hql = HibernateHelper.getInitialQuery(getEntityType(), "po", queryParameters.getFetch());

        HibernateHelper.addInParameterToQuery(hql, namedParameters, "po.status", queryParameters.getStatusList());
        HibernateHelper.addParameterToQuery(hql, namedParameters, "po.buyer", queryParameters.getBuyer());
        HibernateHelper.addParameterToQuery(hql, namedParameters, "po.currency", queryParameters.getCurrency());
        HibernateHelper.addParameterToQuery(hql, namedParameters, "po.seller", queryParameters.getSeller());
        HibernateHelper.addParameterToQueryOperator(hql, namedParameters, "po.amount", ">=", queryParameters.getAmountLowerLimit());
        HibernateHelper.addParameterToQueryOperator(hql, namedParameters, "po.amount", "<=", queryParameters.getAmountUpperLimit());

        if (queryParameters.isApplyExpirationToMaxPublishDate()) {
            final StringBuilder tmp1 = new StringBuilder("1=1");
            final StringBuilder tmp2 = new StringBuilder("1=1");
            HibernateHelper.addPeriodParameterToQuery(tmp1, namedParameters, "po.expirationDate", queryParameters.getExpiration());
            HibernateHelper.addPeriodParameterToQuery(tmp2, namedParameters, "po.maxPublishDate", queryParameters.getExpiration());
            hql.append(" and (").append(tmp1).append(" or ").append(tmp2).append(")");
        } else {
            HibernateHelper.addPeriodParameterToQuery(hql, namedParameters, "po.expirationDate", queryParameters.getExpiration());
        }

        // this was added to support a member who has the two roles buyer and seller at the same time
        if (queryParameters.getLoggedMember() != null) {
            hql.append(" and ((po.buyer = :logged_ OR po.seller = :logged_) and not (po.status = :registeredStatus and po.buyer <> :logged_))");
            namedParameters.put("logged_", queryParameters.getLoggedMember());
            namedParameters.put("registeredStatus", PaymentObligation.Status.REGISTERED);
        }

        if (CollectionUtils.isNotEmpty(queryParameters.getManagedMemberGroups())) {
            hql.append(" and (po.buyer.group in (:groups_) and po.seller.group in (:groups_))");
            namedParameters.put("groups_", queryParameters.getManagedMemberGroups());
        }

        HibernateHelper.appendOrder(hql, "po.expirationDate");

        return list(queryParameters, hql.toString(), namedParameters);
    }
}
