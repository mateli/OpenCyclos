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
package nl.strohalm.cyclos.webservices.payments;

import java.io.Serializable;

/**
 * The result from a request payment call
 * @author luis
 */
public class RequestPaymentResult implements Serializable {
    private static final long    serialVersionUID = 6333744053246981201L;
    private PaymentRequestStatus status;
    private String               ticket;

    public PaymentRequestStatus getStatus() {
        return status;
    }

    public String getTicket() {
        return ticket;
    }

    public void setStatus(final PaymentRequestStatus status) {
        this.status = status;
    }

    public void setTicket(final String ticket) {
        this.ticket = ticket;
    }
}
