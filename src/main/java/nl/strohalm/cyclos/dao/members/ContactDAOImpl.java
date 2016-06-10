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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.strohalm.cyclos.dao.BaseDAOImpl;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.members.Contact;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.utils.hibernate.HibernateHelper;
import nl.strohalm.cyclos.utils.query.QueryParameters.ResultType;

/**
 * Implementation class for contact DAO
 * @author rafael
 * @author luis
 */
public class ContactDAOImpl extends BaseDAOImpl<Contact> implements ContactDAO {

    private static final Relationship[] FETCH = { Contact.Relationships.CONTACT };

    public ContactDAOImpl() {
        super(Contact.class);
    }

    @Override
    public List<Contact> listByMember(Member owner) {
        owner = getFetchDao().fetch(owner);
        final Map<String, Object> namedParameters = new HashMap<String, Object>();
        final StringBuilder hql = HibernateHelper.getInitialQuery(getEntityType(), "c");
        HibernateHelper.addParameterToQuery(hql, namedParameters, "c.owner", owner);
        HibernateHelper.addInParameterToQuery(hql, namedParameters, "c.contact.group", ((MemberGroup) owner.getGroup()).getCanViewProfileOfGroups());
        HibernateHelper.appendOrder(hql, "c.contact.user.username");
        return list(ResultType.LIST, hql.toString(), namedParameters, null, FETCH);
    }

    @Override
    public Contact load(final Member owner, final Member member, final Relationship... fetch) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("owner", owner);
        params.put("member", member);
        Contact contact = uniqueResult("from Contact c where c.owner = :owner and c.contact = :member", params);
        if (contact == null) {
            throw new EntityNotFoundException();
        }
        return contact;
    }

}
