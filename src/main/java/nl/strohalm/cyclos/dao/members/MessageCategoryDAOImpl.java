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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.strohalm.cyclos.dao.BaseDAOImpl;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.entities.groups.SystemGroup;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.messages.MessageCategory;
import nl.strohalm.cyclos.entities.members.messages.MessageCategoryQuery;

import org.apache.commons.collections.CollectionUtils;

/**
 * Implementation class for MessageCategory.
 * @author jeancarlo
 */
public class MessageCategoryDAOImpl extends BaseDAOImpl<MessageCategory> implements MessageCategoryDAO {

    public MessageCategoryDAOImpl() {
        super(MessageCategory.class);
    }

    @Override
    public int delete(final boolean flush, final Long... ids) {
        // We must manually remove the relationship with groups
        int rows = 0;
        for (final Long id : ids) {
            try {
                final MessageCategory category = load(id, MessageCategory.Relationships.GROUPS);
                for (final SystemGroup group : category.getGroups()) {
                    group.getMessageCategories().remove(category);
                }
                getHibernateTemplate().delete(category);
                getHibernateTemplate().flush();
                rows++;
            } catch (final EntityNotFoundException e) {
                // Ignore
            }
        }
        return rows;
    }

    @Override
    public List<MessageCategory> findAll() {
        return list("from " + getEntityType().getName() + " c order by c.name", null);
    }

    @Override
    public List<MessageCategory> search(final MessageCategoryQuery query) {
        final Map<String, Object> namedParameters = new HashMap<String, Object>();

        final StringBuilder hql = new StringBuilder();
        hql.append("from MessageCategory mc where 1=1 ");

        final List<Group> groups = new ArrayList<Group>();

        // The message category must be viewable to the sender of the message
        if (query.getFromElement() != null) {
            final Element fromElement = getFetchDao().fetch(query.getFromElement(), Element.Relationships.GROUP);
            final Group fromGroup = fromElement.getGroup();
            groups.add(fromGroup);
        }

        // The message category must be viewable to the receiver of the message
        if (query.getToElement() != null) {
            final Element toElement = getFetchDao().fetch(query.getToElement(), Element.Relationships.GROUP);
            final Group toGroup = toElement.getGroup();
            groups.add(toGroup);
        }

        // The message category must be viewable to all the groups in the query
        if (!CollectionUtils.isEmpty(query.getGroups())) {
            groups.addAll(query.getGroups());
        }

        // Build the query
        if (!CollectionUtils.isEmpty(groups)) {
            for (int i = 0; i < groups.size(); i++) {
                final Group group = groups.get(i);
                hql.append("and exists (select g.id from Group g where mc in elements(g.messageCategories) and g = :group" + i + ") ");
                namedParameters.put("group" + i, group);
            }
        }

        return list(hql.toString(), namedParameters);
    }

}
