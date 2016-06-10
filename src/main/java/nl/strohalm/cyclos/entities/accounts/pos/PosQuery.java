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
package nl.strohalm.cyclos.entities.accounts.pos;

import java.util.Collection;

import nl.strohalm.cyclos.entities.groups.AdminGroup;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.utils.query.QueryParameters;

/**
 * 
 * @author rodrigo
 */
public class PosQuery extends QueryParameters {

    public static enum QueryStatus {
        UNASSIGNED(Pos.Status.UNASSIGNED), PENDING(MemberPos.Status.PENDING), ACTIVE(MemberPos.Status.ACTIVE), BLOCKED(MemberPos.Status.BLOCKED), PIN_BLOCKED(MemberPos.Status.PIN_BLOCKED), DISCARDED(Pos.Status.DISCARDED);
        private final Pos.Status       posStatus;
        private final MemberPos.Status memberPosStatus;

        private QueryStatus(final MemberPos.Status memberPosStatus) {
            this.memberPosStatus = memberPosStatus;
            posStatus = null;
        }

        private QueryStatus(final Pos.Status posStatus) {
            this.posStatus = posStatus;
            memberPosStatus = null;
        }

        public MemberPos.Status getMemberPosStatus() {
            return memberPosStatus;
        }

        public Pos.Status getPosStatus() {
            return posStatus;
        }
    }

    private static final long       serialVersionUID = 3951574281577086245L;
    private Collection<QueryStatus> statuses;
    private Member                  member;
    private Member                  broker;
    private String                  posId;
    private AdminGroup              managedBy;

    public Member getBroker() {
        return broker;
    }

    public AdminGroup getManagedBy() {
        return managedBy;
    }

    public Member getMember() {
        return member;
    }

    public String getPosId() {
        return posId;
    }

    public Collection<QueryStatus> getStatuses() {
        return statuses;
    }

    public void setBroker(final Member broker) {
        this.broker = broker;
    }

    public void setManagedBy(final AdminGroup managedBy) {
        this.managedBy = managedBy;
    }

    public void setMember(final Member member) {
        this.member = member;
    }

    public void setPosId(final String posId) {
        this.posId = posId;
    }

    public void setStatuses(final Collection<QueryStatus> statuses) {
        this.statuses = statuses;
    }

}
