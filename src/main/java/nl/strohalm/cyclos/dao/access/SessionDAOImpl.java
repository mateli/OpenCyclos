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
package nl.strohalm.cyclos.dao.access;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import nl.strohalm.cyclos.dao.BaseDAOImpl;
import nl.strohalm.cyclos.entities.access.Session;
import nl.strohalm.cyclos.entities.access.SessionQuery;
import nl.strohalm.cyclos.entities.access.User;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.entities.groups.Group.Nature;
import nl.strohalm.cyclos.utils.IteratorListImpl;
import nl.strohalm.cyclos.utils.hibernate.HibernateHelper;
import nl.strohalm.cyclos.utils.query.IteratorList;
import nl.strohalm.cyclos.utils.query.PageParameters;
import nl.strohalm.cyclos.utils.query.QueryParameters.ResultType;

import org.apache.commons.collections.CollectionUtils;

/**
 * DAO interface for {@link Session}
 * 
 * @author luis
 */
public class SessionDAOImpl extends BaseDAOImpl<Session> implements SessionDAO {

    public SessionDAOImpl() {
        super(Session.class);
    }

    @Override
    public int delete(final User user) {
        Map<String, User> params = Collections.singletonMap("user", user);
        return bulkUpdate("delete from Session where user = :user", params);
    }

    @Override
    public boolean isLoggedIn(final User user) {
        Map<String, ?> params = Collections.singletonMap("user", user);
        List<?> list = list(ResultType.LIST, "select s.id from Session s where s.user = :user and s.expirationDate > now()", params, PageParameters.max(1));
        return !list.isEmpty();
    }

    @Override
    public IteratorList<User> listLoggedUsers() {
        Iterator<User> iterator = this.iterate("select distinct s.user from Session s where s.expirationDate > now()", null);
        return new IteratorListImpl<User>(iterator);
    }

    @Override
    public Session load(final String sessionId, final boolean allowExpired) throws EntityNotFoundException {
        Map<String, ?> params = Collections.singletonMap("identifier", sessionId);
        StringBuilder hql = new StringBuilder();
        hql.append(" select s");
        hql.append(" from Session s");
        hql.append("   left join fetch s.user u");
        hql.append("   left join fetch u.element e");
        hql.append("   left join fetch e.group g");
        hql.append("   left join fetch e.member m");
        hql.append("   left join fetch m.group mg");
        hql.append(" where s.identifier = :identifier");
        if (!allowExpired) {
            hql.append(" and s.expirationDate > now()");
        }
        Session session = uniqueResult(hql.toString(), params);
        if (session == null) {
            throw new EntityNotFoundException();
        }
        return session;
    }

    @Override
    public void purgeExpired() {
        bulkUpdate("delete from Session where expirationDate <= now()", null);
    }

    @Override
    public List<Session> search(final SessionQuery query) {
        Map<String, Object> params = new HashMap<String, Object>();
        StringBuilder hql = new StringBuilder();
        hql.append(" select s ");
        hql.append(" from Session s left join fetch s.user u left join fetch u.element e left join fetch e.group g ");
        hql.append(" where s.expirationDate > now() ");

        // Filter by nature - use the discriminator directly
        Collection<Nature> natures = query.getNatures();
        if (CollectionUtils.isNotEmpty(natures)) {
            Collection<String> values = new ArrayList<String>(natures.size());
            for (Nature nature : natures) {
                values.add(nature.getDiscriminator());
            }
            HibernateHelper.addInParameterToQuery(hql, params, "s.user.element.group.class", values);
        }

        // Apply the filter by group, which has a distinct semantic on operators
        boolean hasOperator = CollectionUtils.isEmpty(natures) || natures.contains(Group.Nature.OPERATOR);
        if (hasOperator) {
            // The groups may be for either the group or, if operators, the member group
            hql.append("and (s.user.element.group in (:groups) or exists (");
            hql.append("    select o.id");
            hql.append("    from Operator o");
            hql.append("    where o = s.user.element");
            hql.append("      and o.member.group in (:groups)");
            hql.append("))");
            params.put("groups", query.getGroups());
        } else {
            // Group filter will apply directly
            HibernateHelper.addInParameterToQuery(hql, params, "s.user.element.group", query.getGroups());
        }

        // Filter by operator member
        if (query.getMember() != null) {
            hql.append("and exists (");
            hql.append("    select o.id");
            hql.append("    from Operator o");
            hql.append("    where o = s.user.element");
            hql.append("      and o.member = :member");
            hql.append(")");
            params.put("member", query.getMember());
        }
        HibernateHelper.appendOrder(hql, "s.user.element.name");
        return list(query, hql.toString(), params);
    }

    @Override
    public void updateExpiration(final Long id, final Calendar newExpiration) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);
        params.put("newExpiration", newExpiration);
        bulkUpdate("update Session set expirationDate = :newExpiration where id = :id and expirationDate < :newExpiration", params);
    }

}
