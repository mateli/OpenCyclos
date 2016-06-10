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
package nl.strohalm.cyclos.entities.accounts.transactions;

import java.util.Calendar;

import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.utils.query.QueryParameters;

/**
 * Query parameters for searching tickets
 * @author luis
 */
public class TicketQuery extends QueryParameters {

    public static enum GroupedStatus {
        OK_PENDING(Ticket.Status.OK, Ticket.Status.PENDING), PENDING(Ticket.Status.PENDING), OK(Ticket.Status.OK), FAILED(Ticket.Status.CANCELLED, Ticket.Status.EXPIRED, Ticket.Status.FAILED);

        private final Ticket.Status[] normalStatus;

        private GroupedStatus(final Ticket.Status... normalStatus) {
            this.normalStatus = normalStatus;
        }

        public Ticket.Status[] getNormalStatus() {
            return normalStatus;
        }
    }

    private static final long serialVersionUID = 4169405658024871677L;
    private Ticket.Nature     nature;
    private Ticket.Status     status;
    private GroupedStatus     groupedStatus;
    private Member            to;
    private Member            from;
    private Calendar          createdBefore;

    public Calendar getCreatedBefore() {
        return createdBefore;
    }

    public Member getFrom() {
        return from;
    }

    public GroupedStatus getGroupedStatus() {
        return groupedStatus;
    }

    public Ticket.Nature getNature() {
        return nature;
    }

    public Ticket.Status getStatus() {
        return status;
    }

    public Member getTo() {
        return to;
    }

    public void setCreatedBefore(final Calendar createdBefore) {
        this.createdBefore = createdBefore;
    }

    public void setFrom(final Member from) {
        this.from = from;
    }

    public void setGroupedStatus(final GroupedStatus groupedStatus) {
        this.groupedStatus = groupedStatus;
    }

    public void setNature(final Ticket.Nature nature) {
        this.nature = nature;
    }

    public void setStatus(final Ticket.Status status) {
        this.status = status;
    }

    public void setTo(final Member to) {
        this.to = to;
    }
}
