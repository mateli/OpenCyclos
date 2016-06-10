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
package nl.strohalm.cyclos.dao.groups;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nl.strohalm.cyclos.dao.BaseDAOImpl;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.exceptions.DaoException;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.entities.groups.AdminGroup;
import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.entities.groups.GroupFilter;
import nl.strohalm.cyclos.entities.groups.GroupFilterQuery;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.utils.hibernate.HibernateHelper;

import org.apache.commons.collections.CollectionUtils;

public class GroupFilterDAOImpl extends BaseDAOImpl<GroupFilter> implements GroupFilterDAO {

    public GroupFilterDAOImpl() {
        super(GroupFilter.class);
    }

    public GroupFilter findByLoginPageName(final String loginPageName) {
        final GroupFilter groupFilter = uniqueResult("from GroupFilter gf where gf.loginPageName = :name", Collections.singletonMap("name", loginPageName));
        if (groupFilter == null) {
            throw new EntityNotFoundException(Group.class);
        }
        return groupFilter;
    }

    public List<GroupFilter> search(final GroupFilterQuery query) throws DaoException {
        final Map<String, Object> namedParameters = new HashMap<String, Object>();
        final Set<Relationship> fetch = query.getFetch();
        final StringBuilder hql = HibernateHelper.getInitialQuery(getEntityType(), "gf", fetch);
        HibernateHelper.addLikeParameterToQuery(hql, namedParameters, "gf.description", query.getDescription());
        HibernateHelper.addLikeParameterToQuery(hql, namedParameters, "gf.name", query.getName());
        if (query.getGroup() != null) {
            hql.append(" and :group in elements(gf.groups) ");
            namedParameters.put("group", query.getGroup());
        }
        if (query.getViewableBy() != null) {
            hql.append(" and :viewerGroup in elements(gf.viewableBy) ");
            namedParameters.put("viewerGroup", query.getViewableBy());
        }
        if (query.getAdminGroup() != null) {
            final AdminGroup adminGroup = getFetchDao().fetch(query.getAdminGroup(), AdminGroup.Relationships.MANAGES_GROUPS);
            final Collection<MemberGroup> adminManagedGroups = adminGroup.getManagesGroups();
            if (CollectionUtils.isNotEmpty(adminManagedGroups)) {
                hql.append(" and exists (select g.id from Group g where g in elements(gf.groups) and g in (:adminManagedGroups)) ");
                namedParameters.put("adminManagedGroups", adminManagedGroups);
            }
        }
        HibernateHelper.appendOrder(hql, "gf.name");
        return list(query, hql.toString(), namedParameters);
    }

}
