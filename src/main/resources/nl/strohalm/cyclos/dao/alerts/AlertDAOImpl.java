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
package nl.strohalm.cyclos.dao.alerts;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nl.strohalm.cyclos.dao.BaseDAOImpl;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.alerts.Alert;
import nl.strohalm.cyclos.entities.alerts.Alert.Type;
import nl.strohalm.cyclos.entities.alerts.AlertQuery;
import nl.strohalm.cyclos.utils.hibernate.HibernateHelper;

/**
 * Implementation class for AlertDAO component. It delegates basic operations to a InsertableDAO and a BaseDAO. Extends Spring's Hibernate Support.
 * 
 * @author rafael
 * @author fireblade
 */
public class AlertDAOImpl extends BaseDAOImpl<Alert> implements AlertDAO {

    public AlertDAOImpl() {
        super(Alert.class);
    }

    /**
     * Alerts are never physically removed, just have their removed property changed to true
     */
    @Override
    public int delete(final boolean flush, final Long... ids) {
        if (ids == null || ids.length == 0) {
            return 0;
        }
        final String hql = "update " + getEntityType().getName() + " a set a.removed=true where a.id in (:ids)";
        final Map<String, ?> namedParameters = Collections.singletonMap("ids", Arrays.asList(ids));
        final int results = bulkUpdate(hql.toString(), namedParameters);
        if (flush) {
            flush();
        }
        return results;
    }

    @Override
    public int getCount(final Type type) {
        final Integer count = uniqueResult("select count(*) from " + type.getEntityType().getName() + " a where a.removed=false", null);
        return count;
    }

    @Override
    public List<Alert> search(final AlertQuery query) {
        Class<? extends Alert> entityType = getEntityType();
        if (query.getMember() != null) {
            entityType = Type.MEMBER.getEntityType();
        } else if (query.getType() != null) {
            entityType = query.getType().getEntityType();
        }
        final Map<String, Object> namedParameters = new HashMap<String, Object>();
        final Set<Relationship> fetch = query.getFetch();
        final StringBuilder hql = HibernateHelper.getInitialQuery(entityType, "a", fetch);
        if (!query.isShowRemoved()) {
            hql.append(" and a.removed = false ");
        }
        if (entityType.equals(Type.MEMBER.getEntityType())) {
            HibernateHelper.addParameterToQuery(hql, namedParameters, "a.member", query.getMember());

            if (query.getGroups() != null && !query.getGroups().isEmpty()) {
                hql.append(" and a.member.group in (:groups) ");
                namedParameters.put("groups", query.getGroups());
            }
        }
        HibernateHelper.addParameterToQuery(hql, namedParameters, "a.key", query.getKey());
        HibernateHelper.addPeriodParameterToQuery(hql, namedParameters, "a.date", query.getPeriod());

        HibernateHelper.appendOrder(hql, "a.date desc, a.id desc");
        return list(query, hql.toString(), namedParameters);
    }

}
