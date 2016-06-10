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

import nl.strohalm.cyclos.entities.Entity;

/**
 * A trace number identifies a transfer made by a service client (client id)<br>
 * It's used to support the reverse transfer functionality.
 * @author ameyer
 */
public class TraceNumber extends Entity {
    private static final long serialVersionUID = 1L;

    private Calendar          date;
    private String            traceNumber;
    private Long              clientId;

    public Long getClientId() {
        return clientId;
    }

    public Calendar getDate() {
        return date;
    }

    public String getTraceNumber() {
        return traceNumber;
    }

    public void setClientId(final Long clientId) {
        this.clientId = clientId;
    }

    public void setDate(final Calendar date) {
        this.date = date;
    }

    public void setTraceNumber(final String traceNumber) {
        this.traceNumber = traceNumber;
    }

    @Override
    public String toString() {
        return "Reverse [id=" + getId() + "clientId=" + clientId + ", traceNumber=" + traceNumber + "]";
    }

}
