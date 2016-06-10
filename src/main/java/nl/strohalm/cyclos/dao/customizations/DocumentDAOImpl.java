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
package nl.strohalm.cyclos.dao.customizations;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.strohalm.cyclos.dao.BaseDAOImpl;
import nl.strohalm.cyclos.entities.customization.documents.Document;
import nl.strohalm.cyclos.entities.customization.documents.DocumentQuery;
import nl.strohalm.cyclos.entities.customization.documents.MemberDocument;
import nl.strohalm.cyclos.entities.exceptions.DaoException;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.utils.access.LoggedUser;
import nl.strohalm.cyclos.utils.hibernate.HibernateHelper;

/**
 * Implementation for DocumentDAO
 * @author luis
 */
public class DocumentDAOImpl extends BaseDAOImpl<Document> implements DocumentDAO {

    public DocumentDAOImpl() {
        super(Document.class);
    }

    @Override
    public List<Document> search(final DocumentQuery query) throws DaoException {
        final Map<String, Object> namedParameters = new HashMap<String, Object>();
        final StringBuilder hql = HibernateHelper.getInitialQuery(getEntityType(), "doc", query.getFetch());

        // Save named parameters values
        namedParameters.put("dynamicType", Document.Nature.DYNAMIC.getValue());
        namedParameters.put("memberType", Document.Nature.MEMBER.getValue());
        namedParameters.put("staticType", Document.Nature.STATIC.getValue());
        namedParameters.put("memberVisibility", MemberDocument.Visibility.MEMBER.getValue());
        namedParameters.put("brokerVisibility", MemberDocument.Visibility.BROKER.getValue());

        // Document id
        if (query.getId() != null) {
            HibernateHelper.addParameterToQuery(hql, namedParameters, "doc.id", query.getId());
        }

        // Nature
        if (query.getNatures() != null) {
            hql.append(" and doc.class in (:natures) ");
            Collection<Document.Nature> natures = query.getNatures();
            Collection<String> natureValues = new ArrayList<String>();
            for (Document.Nature nature : natures) {
                natureValues.add(nature.getValue());
            }
            namedParameters.put("natures", natureValues);
        }

        boolean searchingForABrokeredMemberDocuments = false;

        // The allowed dynamic and static documents
        Element viewer = query.getViewer();
        if (viewer != null) {
            viewer = getFetchDao().fetch(viewer, Element.Relationships.GROUP);
            if (query.getMember() != null && LoggedUser.isBroker()) {
                final Member member = getFetchDao().fetch(query.getMember(), Member.Relationships.BROKER);
                // If searching documents for a brokered member
                if (viewer.equals(member.getBroker())) {
                    searchingForABrokeredMemberDocuments = true;
                    hql.append(" and ( ");
                    if (query.isBrokerCanViewMemberDocuments()) {
                        hql.append(" (doc.class = :memberType) or ");
                    }
                    hql.append(" exists (select g.id from BrokerGroup g where doc in elements(g.brokerDocuments) and g = :group)) ");
                }
            }

            if (!searchingForABrokeredMemberDocuments) {
                hql.append(" and (doc.class = :memberType or exists (select g.id from Group g where doc in elements(g.documents) and g = :group)) ");
            }

            namedParameters.put("group", viewer.getGroup());
        }

        // The allowed member documents
        if (query.getMember() != null) {
            final Member member = getFetchDao().fetch(query.getMember(), Member.Relationships.BROKER);
            hql.append(" and (doc.class in (:dynamicType, :staticType) or ");
            hql.append(" exists (select id from MemberDocument md where md = doc and md.member = :member and md.member.group in (:groups) ");
            namedParameters.put("groups", query.getVisibleGroups());
            // Viewer
            if (viewer instanceof Member) {
                // Member (or broker) searching his own documents
                if (viewer.equals(member)) {
                    hql.append(" and md.visibility = :memberVisibility ");
                }
                // Broker searching member documents
                if (member.getBroker() != null && member.getBroker().equals(viewer)) {
                    hql.append(" and md.visibility in (:memberVisibility, :brokerVisibility) ");
                }
            }
            hql.append(" )) ");
            namedParameters.put("member", query.getMember());
        }

        HibernateHelper.appendOrder(hql, "doc.name");
        return list(query, hql.toString(), namedParameters);
    }
}
