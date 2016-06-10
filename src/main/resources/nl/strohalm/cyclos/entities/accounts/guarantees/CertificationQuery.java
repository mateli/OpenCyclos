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
package nl.strohalm.cyclos.entities.accounts.guarantees;

import java.util.Collection;
import java.util.List;

import nl.strohalm.cyclos.entities.accounts.guarantees.Certification.Status;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.utils.Period;
import nl.strohalm.cyclos.utils.query.QueryParameters;

public class CertificationQuery extends QueryParameters {

    private static final long       serialVersionUID = 9119200361841618445L;

    private List<Status>            statusList;
    private Member                  buyer;
    private Member                  issuer;
    private Period                  startIn;
    private Period                  endIn;

    // this collection is used to add an IN operator to the query to restrict the result to those certifications of issuers whose group
    // belong to this collection
    private Collection<MemberGroup> managedMemberGroups;

    private Member                  viewer;                                 // it could be the issuer or buyer

    public Member getBuyer() {
        return buyer;
    }

    public Period getEndIn() {
        return endIn;
    }

    public Member getIssuer() {
        return issuer;
    }

    public Collection<MemberGroup> getManagedMemberGroups() {
        return managedMemberGroups;
    }

    public Period getStartIn() {
        return startIn;
    }

    public List<Status> getStatusList() {
        return statusList;
    }

    public Member getViewer() {
        return viewer;
    }

    public void setBuyer(final Member member) {
        buyer = member;
    }

    public void setEndIn(final Period endIn) {
        this.endIn = endIn;
    }

    public void setIssuer(final Member issuer) {
        this.issuer = issuer;
    }

    public void setManagedMemberGroups(final Collection<MemberGroup> managedMemberGroups) {
        this.managedMemberGroups = managedMemberGroups;
    }

    public void setStartIn(final Period startIn) {
        this.startIn = startIn;
    }

    public void setStatusList(final List<Status> status) {
        statusList = status;
    }

    public void setViewer(final Member viewer) {
        this.viewer = viewer;
    }
}
