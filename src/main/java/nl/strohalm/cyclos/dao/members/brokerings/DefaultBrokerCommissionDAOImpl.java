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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.strohalm.cyclos.dao.BaseDAOImpl;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.exceptions.DaoException;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.members.brokerings.DefaultBrokerCommission;
import nl.strohalm.cyclos.entities.members.brokerings.DefaultBrokerCommissionQuery;
import nl.strohalm.cyclos.utils.hibernate.HibernateHelper;
import nl.strohalm.cyclos.utils.query.QueryParameters.ResultType;

/**
 * Implementation for default broker commissions DAO
 * @author Jefferson Magno
 */
public class DefaultBrokerCommissionDAOImpl extends BaseDAOImpl<DefaultBrokerCommission> implements DefaultBrokerCommissionDAO {

    public DefaultBrokerCommissionDAOImpl() {
        super(DefaultBrokerCommission.class);
    }

    public List<DefaultBrokerCommission> load(final Member broker, final Relationship... fetch) throws DaoException {
        final Map<String, Object> namedParameters = new HashMap<String, Object>();
        final StringBuilder hql = HibernateHelper.getInitialQuery(getEntityType(), "dbc");
        HibernateHelper.addParameterToQuery(hql, namedParameters, "dbc.broker", broker);
        HibernateHelper.appendOrder(hql, "dbc.brokerCommission.name");
        return list(ResultType.LIST, hql.toString(), namedParameters, null, fetch);
    }

    public List<DefaultBrokerCommission> search(final DefaultBrokerCommissionQuery query) {
        final StringBuilder hql = HibernateHelper.getInitialQuery(getEntityType(), "dbc", query.getFetch());
        final Map<String, Object> namedParameters = new HashMap<String, Object>();

        // Broker
        HibernateHelper.addParameterToQuery(hql, namedParameters, "dbc.broker", query.getBroker());

        // Broker commission
        HibernateHelper.addParameterToQuery(hql, namedParameters, "dbc.brokerCommission", query.getBrokerCommission());

        // Set by broker
        if (query.isSetByBroker()) {
            hql.append(" and dbc.setByBroker = true ");
        }

        HibernateHelper.appendOrder(hql, "dbc.brokerCommission.name");
        return list(query, hql.toString(), namedParameters);
    }
}
