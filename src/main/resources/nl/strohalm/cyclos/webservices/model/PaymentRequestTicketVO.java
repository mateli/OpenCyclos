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

import javax.xml.bind.annotation.XmlType;

/**
 * Ticket used on payment requests
 * @author luis
 */
@XmlType(name = "paymentRequestTicket")
public class PaymentRequestTicketVO extends TicketVO {
    private static final long serialVersionUID = 5252421990934287723L;

    private String            fromChannel;
    private String            toChannel;
    private String            traceData;

    public String getFromChannel() {
        return fromChannel;
    }

    public String getToChannel() {
        return toChannel;
    }

    /**
     * Optional.
     * @return the trace data set by the client at the moment of request a payment.
     * @see {@link nl.strohalm.cyclos.webservices.payments.RequestPaymentParameters#getTraceData()}
     */
    public String getTraceData() {
        return traceData;
    }

    public void setFromChannel(final String fromChannel) {
        this.fromChannel = fromChannel;
    }

    public void setToChannel(final String toChannel) {
        this.toChannel = toChannel;
    }

    public void setTraceData(final String clientData) {
        traceData = clientData;
    }

    @Override
    public String toString() {
        return "PaymentRequestTicketVO [traceData=" + traceData + ", fromChannel=" + fromChannel + ", toChannel=" + toChannel + ", " + super.toString() + "]";
    }

}
