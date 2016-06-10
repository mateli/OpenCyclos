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
package nl.strohalm.cyclos.entities.customization.documents;

import java.util.Collection;

import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.utils.query.QueryParameters;

/**
 * Query parameters for documents
 * @author luis
 * @author Jefferson Magno
 */
public class DocumentQuery extends QueryParameters {

    private static final long           serialVersionUID             = 3786759993229052921L;
    private boolean                     brokerCanViewMemberDocuments = false;
    private Member                      member;
    private Collection<Document.Nature> natures;
    private Element                     viewer;
    private Long                        id;
    private Collection<Group>           visibleGroups;

    public Long getId() {
        return id;
    }

    public Member getMember() {
        return member;
    }

    public Collection<Document.Nature> getNatures() {
        return natures;
    }

    public Element getViewer() {
        return viewer;
    }

    public Collection<Group> getVisibleGroups() {
        return visibleGroups;
    }

    public boolean isBrokerCanViewMemberDocuments() {
        return brokerCanViewMemberDocuments;
    }

    public void setBrokerCanViewMemberDocuments(final boolean brokerCanViewMemberDocuments) {
        this.brokerCanViewMemberDocuments = brokerCanViewMemberDocuments;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public void setMember(final Member member) {
        this.member = member;
    }

    public void setNatures(final Collection<Document.Nature> natures) {
        this.natures = natures;
    }

    public void setViewer(final Element viewer) {
        this.viewer = viewer;
    }

    public void setVisibleGroups(final Collection<Group> visibleGroups) {
        this.visibleGroups = visibleGroups;
    }
}
