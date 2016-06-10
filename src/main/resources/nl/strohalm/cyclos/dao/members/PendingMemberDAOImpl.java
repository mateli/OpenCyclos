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
package nl.strohalm.cyclos.dao.members;

import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import nl.strohalm.cyclos.dao.BaseDAOImpl;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.entities.members.PendingMember;
import nl.strohalm.cyclos.entities.members.PendingMemberQuery;
import nl.strohalm.cyclos.utils.DataIteratorHelper;
import nl.strohalm.cyclos.utils.hibernate.HibernateCustomFieldHandler;
import nl.strohalm.cyclos.utils.hibernate.HibernateHelper;

import org.apache.commons.lang.StringUtils;

/**
 * Implementation for PendingMemberDAO
 * 
 * @author luis
 */
public class PendingMemberDAOImpl extends BaseDAOImpl<PendingMember> implements PendingMemberDAO {

    private HibernateCustomFieldHandler hibernateCustomFieldHandler;

    public PendingMemberDAOImpl() {
        super(PendingMember.class);
    }

    @Override
    public void deleteBefore(final Calendar date) {
        final Map<String, ?> namedParameters = Collections.singletonMap("date", date);
        final Iterator<PendingMember> iterator = iterate("from " + getEntityType().getName() + " pm where pm.creationDate < :date", namedParameters);
        try {
            while (iterator.hasNext()) {
                getHibernateTemplate().delete(iterator.next());
            }
        } finally {
            DataIteratorHelper.close(iterator);
        }
    }

    @Override
    public boolean emailExists(final PendingMember pendingMember, final String email) {
        if (StringUtils.isEmpty(email)) {
            return false;
        }
        final Map<String, Object> namedParameters = new HashMap<String, Object>();
        final StringBuilder hql = new StringBuilder();
        hql.append(" select count(*)");
        hql.append(" from PendingMember pm");
        hql.append(" where 1 = 1");
        HibernateHelper.addParameterToQuery(hql, namedParameters, "upper(pm.email)", email.toUpperCase());
        if (pendingMember != null && pendingMember.isPersistent()) {
            HibernateHelper.addParameterToQueryOperator(hql, namedParameters, "pm", "<>", pendingMember);
        }
        final Number count = uniqueResult(hql.toString(), namedParameters);
        return count != null && count.intValue() > 0;
    }

    @Override
    public PendingMember loadByKey(final String key, final Relationship... fetch) {
        return loadBy("validationKey", key, fetch);
    }

    @Override
    public PendingMember loadByUsername(final String username, final Relationship... fetch) {
        return loadBy("username", username, fetch);
    }

    @Override
    public List<PendingMember> search(final PendingMemberQuery params) {
        final Map<String, Object> namedParameters = new HashMap<String, Object>();
        final StringBuilder hql = new StringBuilder();
        hql.append(" select pm");
        hql.append(" from ").append(getEntityType().getName()).append(" pm ");
        hibernateCustomFieldHandler.appendJoins(hql, "pm.customValues", params.getCustomValues());
        HibernateHelper.appendJoinFetch(hql, getEntityType(), "pm", params.getFetch());
        hql.append(" where 1=1");
        HibernateHelper.addLikeParameterToQuery(hql, namedParameters, "pm.name", params.getName());
        HibernateHelper.addParameterToQuery(hql, namedParameters, "pm.broker", params.getBroker());
        HibernateHelper.addPeriodParameterToQuery(hql, namedParameters, "pm.creationDate", params.getCreationPeriod());
        HibernateHelper.addInParameterToQuery(hql, namedParameters, "pm.memberGroup", params.getGroups());
        hibernateCustomFieldHandler.appendConditions(hql, namedParameters, params.getCustomValues());
        HibernateHelper.appendOrder(hql, "pm.creationDate desc");
        return list(params, hql.toString(), namedParameters);
    }

    public void setHibernateCustomFieldHandler(final HibernateCustomFieldHandler hibernateCustomFieldHandler) {
        this.hibernateCustomFieldHandler = hibernateCustomFieldHandler;
    }

    private PendingMember loadBy(final String property, final String value, final Relationship[] fetch) {
        final Map<String, ?> params = Collections.singletonMap("value", value);
        final PendingMember pendingMember = uniqueResult("from PendingMember pm where pm." + property + " = :value", params);
        if (pendingMember == null) {
            throw new EntityNotFoundException(PendingMember.class);
        }
        return getFetchDao().fetch(pendingMember, fetch);
    }

}
