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
package nl.strohalm.cyclos.webservices.model;

import java.math.BigDecimal;
import java.util.Calendar;

import nl.strohalm.cyclos.webservices.utils.ObjectHelper;

/**
 * Ticket for web services
 * @author luis
 */
public abstract class TicketVO extends EntityVO {

    private static final long serialVersionUID      = 7596721124085616450L;
    private String            ticket;
    private MemberVO          toMember;
    private MemberVO          fromMember;
    private BigDecimal        amount;
    private String            formattedAmount;
    private Calendar          creationDate;
    private String            formattedCreationDate;
    private String            description;
    private Boolean           ok                    = false;
    private Boolean           cancelled             = false;
    private Boolean           pending               = false;
    private Boolean           expired               = false;
    private Boolean           awaitingAuthorization = false;

    public BigDecimal getAmount() {
        return amount;
    }

    public boolean getAwaitingAuthorization() {
        return ObjectHelper.valueOf(awaitingAuthorization);
    }

    public boolean getCancelled() {
        return ObjectHelper.valueOf(cancelled);
    }

    public Calendar getCreationDate() {
        return creationDate;
    }

    public String getDescription() {
        return description;
    }

    public boolean getExpired() {
        return ObjectHelper.valueOf(expired);
    }

    public String getFormattedAmount() {
        return formattedAmount;
    }

    public String getFormattedCreationDate() {
        return formattedCreationDate;
    }

    public MemberVO getFromMember() {
        return fromMember;
    }

    public boolean getOk() {
        return ObjectHelper.valueOf(ok);
    }

    public boolean getPending() {
        return ObjectHelper.valueOf(pending);
    }

    public String getTicket() {
        return ticket;
    }

    public MemberVO getToMember() {
        return toMember;
    }

    public void setAmount(final BigDecimal amount) {
        this.amount = amount;
    }

    public void setAwaitingAuthorization(final boolean awaitingAuthorization) {
        this.awaitingAuthorization = awaitingAuthorization;
    }

    public void setCancelled(final boolean cancelled) {
        this.cancelled = cancelled;
    }

    public void setCreationDate(final Calendar creationDate) {
        this.creationDate = creationDate;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public void setExpired(final boolean expired) {
        this.expired = expired;
    }

    public void setFormattedAmount(final String formattedAmount) {
        this.formattedAmount = formattedAmount;
    }

    public void setFormattedCreationDate(final String formattedCreationDate) {
        this.formattedCreationDate = formattedCreationDate;
    }

    public void setFromMember(final MemberVO from) {
        fromMember = from;
    }

    public void setOk(final boolean ok) {
        this.ok = ok;
    }

    public void setPending(final boolean pending) {
        this.pending = pending;
    }

    public void setTicket(final String ticket) {
        this.ticket = ticket;
    }

    public void setToMember(final MemberVO toMember) {
        this.toMember = toMember;
    }

    @Override
    public String toString() {
        return "TicketVO[ticket=" + ticket + ", toMember=" + toMember + ", fromMember=" + fromMember + ", amount=" + amount + ", formattedAmount=" + formattedAmount + ", creationDate=" + creationDate + ", formattedCreationDate=" + formattedCreationDate + ", description=" + description + ", isOk=" + ok + ", isCancelled=" + cancelled + ", isPending=" + pending + ", isExpired=" + expired + ", isAwaitingAuthorization=" + awaitingAuthorization + ")";

    }

}
