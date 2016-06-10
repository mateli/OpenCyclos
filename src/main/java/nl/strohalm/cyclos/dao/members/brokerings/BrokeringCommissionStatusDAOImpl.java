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

import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.strohalm.cyclos.dao.BaseDAOImpl;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.fees.transaction.BrokerCommission;
import nl.strohalm.cyclos.entities.members.brokerings.Brokering;
import nl.strohalm.cyclos.entities.members.brokerings.BrokeringCommissionStatus;
import nl.strohalm.cyclos.entities.members.brokerings.BrokeringCommissionStatusQuery;
import nl.strohalm.cyclos.utils.hibernate.HibernateHelper;

import org.apache.commons.collections.CollectionUtils;

/**
 * Implementation for BrokeringCommissionStatusDAO
 * @author Jefferson Magno
 */
public class BrokeringCommissionStatusDAOImpl extends BaseDAOImpl<BrokeringCommissionStatus> implements BrokeringCommissionStatusDAO {

    public BrokeringCommissionStatusDAOImpl() {
        super(BrokeringCommissionStatus.class);
    }

    public void expireBrokeringCommissionStatus(final Calendar date) {
        final StringBuilder hql = new StringBuilder();
        hql.append(" update BrokeringCommissionStatus bcs ");
        hql.append(" set bcs.period.end = bcs.expiryDate ");
        hql.append(" where bcs.period.end is null ");
        hql.append("   and bcs.expiryDate is not null ");
        hql.append("   and bcs.expiryDate <= :date ");

        final Map<String, Object> namedParameters = new HashMap<String, Object>();
        namedParameters.put("date", date);

        bulkUpdate(hql.toString(), namedParameters);
    }

    public BrokeringCommissionStatus load(final Brokering brokering, final BrokerCommission brokerCommission, final Relationship... fetch) {
        final List<Relationship> fetchList = Arrays.asList(fetch);
        final StringBuilder hql = HibernateHelper.getInitialQuery(getEntityType(), "bcs", fetchList);
        final Map<String, Object> namedParameters = new HashMap<String, Object>();

        // Brokering
        HibernateHelper.addParameterToQuery(hql, namedParameters, "bcs.brokering", brokering);

        // Broker commission
        HibernateHelper.addParameterToQuery(hql, namedParameters, "bcs.brokerCommission", brokerCommission);

        // Order by broker commission name
        HibernateHelper.appendOrder(hql, "bcs.brokerCommission.name");

        final List<BrokeringCommissionStatus> list = list(hql.toString(), namedParameters);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        } else {
            return list.iterator().next();
        }
    }

    public List<BrokeringCommissionStatus> search(final BrokeringCommissionStatusQuery query) {
        final StringBuilder hql = HibernateHelper.getInitialQuery(getEntityType(), "bcs", query.getFetch());
        final Map<String, Object> namedParameters = new HashMap<String, Object>();

        // Broker
        HibernateHelper.addParameterToQuery(hql, namedParameters, "bcs.brokering.broker", query.getBroker());

        // Brokering
        HibernateHelper.addParameterToQuery(hql, namedParameters, "bcs.brokering", query.getBrokering());

        // Broker commission
        HibernateHelper.addParameterToQuery(hql, namedParameters, "bcs.brokerCommission", query.getBrokerCommission());

        // Only active
        if (query.isOnlyActive()) {
            hql.append(" and bcs.period.end is null ");
        }

        // Only already charged
        if (query.isAlreadyCharged()) {
            hql.append(" and bcs.total.count > 0 ");
        }

        // Order by broker commission name
        HibernateHelper.appendOrder(hql, "bcs.brokerCommission.name");

        return list(hql.toString(), namedParameters);
    }

}
