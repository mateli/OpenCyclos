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

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.strohalm.cyclos.dao.BaseDAOImpl;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.access.OperatorUser;
import nl.strohalm.cyclos.entities.access.User;
import nl.strohalm.cyclos.entities.exceptions.DaoException;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.utils.hibernate.HibernateHelper;
import nl.strohalm.cyclos.utils.query.PageParameters;
import nl.strohalm.cyclos.utils.query.QueryParameters.ResultType;

/**
 * Default implementation for UserDAO. It just delegates operations do InsertableDAO, UpdatableDAO and DeletableDAO.
 * 
 * @author fireblade
 * @author luis
 */
public class UserDAOImpl extends BaseDAOImpl<User> implements UserDAO {

    public UserDAOImpl() {
        super(User.class);
    }

    @Override
    public <T extends User> T load(final String username, final Relationship... fetch) throws EntityNotFoundException, DaoException {
        final Map<String, Object> namedParameters = new HashMap<String, Object>();
        namedParameters.put("username", username);
        namedParameters.put("admin", Element.Nature.ADMIN.getValue());
        namedParameters.put("member", Element.Nature.MEMBER.getValue());
        namedParameters.put("removed", Group.Status.REMOVED);
        final T user = this.<T> uniqueResult("select u from User u left join fetch u.element e where u.username = :username and e.class in (:admin, :member) and e.group.status <> :removed", namedParameters);
        if (user == null) {
            throw new EntityNotFoundException(getEntityType());
        }
        return getFetchDao().fetch(user, fetch);
    }

    @Override
    public OperatorUser loadOperator(final Member member, final String username, final Relationship... fetch) throws EntityNotFoundException, DaoException {
        final Map<String, Object> namedParameters = new HashMap<String, Object>();
        final StringBuilder hql = new StringBuilder();
        hql.append("select u from OperatorUser u, Operator o ");
        HibernateHelper.appendJoinFetch(hql, OperatorUser.class, "u", Arrays.asList(fetch));
        hql.append(" where u.id = o.id");
        HibernateHelper.addParameterToQuery(hql, namedParameters, "o.member", member);
        HibernateHelper.addParameterToQuery(hql, namedParameters, "u.username", username);
        final List<User> list = list(ResultType.LIST, hql.toString(), namedParameters, PageParameters.unique(), fetch);
        if (list.isEmpty()) {
            throw new EntityNotFoundException(OperatorUser.class);
        }
        return (OperatorUser) list.iterator().next();
    }

}
