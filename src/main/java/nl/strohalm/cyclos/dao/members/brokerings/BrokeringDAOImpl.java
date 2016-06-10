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
import java.util.Set;

import nl.strohalm.cyclos.dao.BaseDAOImpl;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.entities.members.BrokeringQuery;
import nl.strohalm.cyclos.entities.members.brokerings.BrokerCommissionContract;
import nl.strohalm.cyclos.entities.members.brokerings.Brokering;
import nl.strohalm.cyclos.utils.DateHelper;
import nl.strohalm.cyclos.utils.hibernate.HibernateHelper;

import org.apache.commons.collections.CollectionUtils;

/**
 * Implementation class for brokering DAO
 * @author rafael
 */
public class BrokeringDAOImpl extends BaseDAOImpl<Brokering> implements BrokeringDAO {

    public BrokeringDAOImpl() {
        super(Brokering.class);
    }

    public List<Brokering> search(final BrokeringQuery query) {
        final Map<String, Object> namedParameters = new HashMap<String, Object>();
        final Set<Relationship> fetch = query.getFetch();
        final StringBuilder hql = HibernateHelper.getInitialQuery(getEntityType(), "b", fetch);
        HibernateHelper.addParameterToQuery(hql, namedParameters, "b.broker", query.getBroker());
        HibernateHelper.addParameterToQuery(hql, namedParameters, "b.brokered", query.getBrokered());
        HibernateHelper.addLikeParameterToQuery(hql, namedParameters, "b.brokered.name", query.getName());
        HibernateHelper.addLikeParameterToQuery(hql, namedParameters, "b.brokered.user.username", query.getUsername());
        HibernateHelper.addParameterToQueryOperator(hql, namedParameters, "b.startDate", "<", DateHelper.truncate(query.getStartExpirationDate()));
        if (CollectionUtils.isNotEmpty(query.getGroups())) {
            hql.append(" and b.brokered.group in (:groups) ");
            namedParameters.put("groups", query.getGroups());
        }
        if (query.getStatus() != null) {
            hql.append(" and b.endDate is null ");
            if (query.getStatus() == BrokeringQuery.Status.ACTIVE) {
                hql.append(" and b.brokered.activationDate is not null and b.brokered.group.status = :normalGroup");
                namedParameters.put("normalGroup", Group.Status.NORMAL);
            } else if (query.getStatus() == BrokeringQuery.Status.COMMISSION_COMPLETE) {
                hql.append(" and not exists (select bcs.id from BrokeringCommissionStatus bcs where bcs.brokering = b and bcs.period.end is null) ");
                hql.append(" and not exists (select bcc.id from BrokerCommissionContract bcc where bcc.brokering = b and (bcc.status = :activeContract or bcc.status = :pendingContract)) ");
                namedParameters.put("activeContract", BrokerCommissionContract.Status.ACTIVE);
                namedParameters.put("pendingContract", BrokerCommissionContract.Status.PENDING);
            } else if (query.getStatus() == BrokeringQuery.Status.PENDING) {
                hql.append(" and b.brokered.activationDate is null ");
            }
        }
        if (!query.isReturnFinished()) {
            hql.append(" and b.endDate is null");
        }
        HibernateHelper.appendOrder(hql, "b.brokered.user.username");
        return list(query, hql.toString(), namedParameters);
    }
}
