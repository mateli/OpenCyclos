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
package nl.strohalm.cyclos.dao.members.brokerings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.strohalm.cyclos.dao.BaseDAOImpl;
import nl.strohalm.cyclos.entities.members.brokerings.BrokerCommissionContract;
import nl.strohalm.cyclos.entities.members.brokerings.BrokerCommissionContractQuery;
import nl.strohalm.cyclos.utils.hibernate.HibernateHelper;

import org.apache.commons.collections.CollectionUtils;

/**
 * Implementation for the broker commission contract DAO
 * @author Jefferson Magno
 */
public class BrokerCommissionContractDAOImpl extends BaseDAOImpl<BrokerCommissionContract> implements BrokerCommissionContractDAO {

    public BrokerCommissionContractDAOImpl() {
        super(BrokerCommissionContract.class);
    }

    @Override
    public boolean isConflictingContract(final BrokerCommissionContract brokerCommissionContract) {

        final StringBuilder hql = new StringBuilder("from BrokerCommissionContract oc where 1=1 ");
        if (brokerCommissionContract.isPersistent()) {
            hql.append(" and oc != :contract ");
        }
        hql.append(" and oc.brokering = :brokering ");
        hql.append(" and oc.brokerCommission = :brokerCommission ");
        hql.append(" and (oc.period.end is null or oc.period.end >= :startDate) ");
        hql.append(" and oc.status in (:statusList) ");

        final List<BrokerCommissionContract.Status> statusList = new ArrayList<BrokerCommissionContract.Status>();
        statusList.add(BrokerCommissionContract.Status.PENDING);
        statusList.add(BrokerCommissionContract.Status.ACTIVE);

        final Map<String, Object> namedParameters = new HashMap<String, Object>();
        namedParameters.put("contract", brokerCommissionContract);
        namedParameters.put("brokering", brokerCommissionContract.getBrokering());
        namedParameters.put("brokerCommission", brokerCommissionContract.getBrokerCommission());
        namedParameters.put("startDate", brokerCommissionContract.getPeriod().getBegin());
        namedParameters.put("statusList", statusList);

        final List<BrokerCommissionContract> contracts = list(hql.toString(), namedParameters);
        if (CollectionUtils.isNotEmpty(contracts)) {
            return true;
        }
        return false;
    }

    @Override
    public List<BrokerCommissionContract> search(final BrokerCommissionContractQuery query) {
        final StringBuilder hql = HibernateHelper.getInitialQuery(getEntityType(), "bcc", query.getFetch());
        final Map<String, Object> namedParameters = new HashMap<String, Object>();

        // Broker
        HibernateHelper.addParameterToQuery(hql, namedParameters, "bcc.brokering.broker", query.getBroker());

        // Member
        HibernateHelper.addParameterToQuery(hql, namedParameters, "bcc.brokering.brokered", query.getMember());

        // Broker commission
        HibernateHelper.addParameterToQuery(hql, namedParameters, "bcc.brokerCommission", query.getBrokerCommission());

        // Start period
        HibernateHelper.addPeriodParameterToQuery(hql, namedParameters, "bcc.period.begin", query.getStartPeriod());

        // End period
        HibernateHelper.addPeriodParameterToQuery(hql, namedParameters, "bcc.period.end", query.getEndPeriod());

        // Status list
        HibernateHelper.addInParameterToQuery(hql, namedParameters, "bcc.status", query.getStatusList());

        if (CollectionUtils.isNotEmpty(query.getManagedMemberGroups())) {
            hql.append(" and (bcc.brokering.broker.group in (:groups_) or bcc.brokering.brokered.group in (:groups_))");
            namedParameters.put("groups_", query.getManagedMemberGroups());

        }

        HibernateHelper.appendOrder(hql, "bcc.brokerCommission.name");
        return list(query, hql.toString(), namedParameters);
    }

}
