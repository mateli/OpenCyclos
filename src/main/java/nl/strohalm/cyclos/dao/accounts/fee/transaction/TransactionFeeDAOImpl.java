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
package nl.strohalm.cyclos.dao.accounts.fee.transaction;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nl.strohalm.cyclos.dao.BaseDAOImpl;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.AccountType;
import nl.strohalm.cyclos.entities.accounts.fees.transaction.BrokerCommission;
import nl.strohalm.cyclos.entities.accounts.fees.transaction.TransactionFee;
import nl.strohalm.cyclos.entities.accounts.fees.transaction.TransactionFee.Nature;
import nl.strohalm.cyclos.entities.accounts.fees.transaction.TransactionFeeQuery;
import nl.strohalm.cyclos.utils.hibernate.HibernateHelper;

/**
 * Implementation DAO for transaction fees
 * @author rafael
 */
public class TransactionFeeDAOImpl extends BaseDAOImpl<TransactionFee> implements TransactionFeeDAO {

    public TransactionFeeDAOImpl() {
        super(TransactionFee.class);
    }

    public List<TransactionFee> search(final TransactionFeeQuery query) {
        final Map<String, Object> namedParameters = new HashMap<String, Object>();
        final Set<Relationship> fetch = query.getFetch();
        Class<? extends TransactionFee> entityType = getEntityType();
        if (query.getEntityType() != null) {
            entityType = query.getEntityType();
        }
        final StringBuilder hql = HibernateHelper.getInitialQuery(entityType, "f", fetch);
        HibernateHelper.addLikeParameterToQuery(hql, namedParameters, "f.description", query.getDescription());
        HibernateHelper.addLikeParameterToQuery(hql, namedParameters, "f.name", query.getName());
        HibernateHelper.addParameterToQuery(hql, namedParameters, "f.originalTransferType", query.getTransferType());
        if (!query.isReturnDisabled()) {
            hql.append(" and f.enabled = true ");
        }

        // Search by nature
        final Nature nature = query.getNature();
        if (nature != null) {
            HibernateHelper.addParameterToQuery(hql, namedParameters, "f.class", nature.getValue());
        }

        // Generated transfer type from nature
        final AccountType.Nature genTTFromNature = query.getGeneratedTransferTypeFromNature();
        HibernateHelper.addParameterToQuery(hql, namedParameters, "f.generatedTransferType.from.class", genTTFromNature == null ? null : genTTFromNature.getValue());

        // Broker group
        if (entityType == BrokerCommission.class && query.getBrokerGroup() != null) {
            hql.append(" and (f.allBrokerGroups = true or :brokerGroup in elements (f.brokerGroups) ) ");
            namedParameters.put("brokerGroup", query.getBrokerGroup());
        }
        // Member group
        if (query.getBrokerGroup() != null) {
            hql.append(" and (");
            hql.append("  (f.fromAllGroups = true or :memberGroup in elements (f.fromGroups)) or ");
            hql.append("  (f.toAllGroups = true or :memberGroup in elements (f.toGroups)) ");
            hql.append(" ) ");
            namedParameters.put("memberGroup", query.getMemberGroup());
        }
        HibernateHelper.appendOrder(hql, "f.name");
        return list(query, hql.toString(), namedParameters);
    }
}
