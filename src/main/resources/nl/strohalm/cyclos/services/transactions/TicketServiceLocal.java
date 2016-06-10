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
package nl.strohalm.cyclos.services.transactions;

import java.util.Calendar;

import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.transactions.PaymentRequestTicket;
import nl.strohalm.cyclos.entities.accounts.transactions.Ticket;
import nl.strohalm.cyclos.entities.accounts.transactions.WebShopTicket;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;

/**
 * Local interface. It must be used only from other services.
 */
public interface TicketServiceLocal extends TicketService {

    /**
     * Expires the specified payment request ticket
     */
    PaymentRequestTicket expirePaymentRequestTicket(final PaymentRequestTicket ticket);

    /**
     * Generates a ticket, based on the parameter data
     */
    WebShopTicket generate(WebShopTicket ticket);

    /**
     * Loads the ticket object by the string<br>
     */
    <T extends Ticket> T load(String ticket, Relationship... fetch) throws EntityNotFoundException;

    /**
     * Loads a payment request ticket which is in the pending state
     */
    PaymentRequestTicket loadPendingPaymentRequest(String ticket, Relationship... fetch);

    /**
     * The payment request ticket couldn't be sent
     */
    PaymentRequestTicket markAsFailedtoSend(final PaymentRequestTicket ticket);

    /**
     * Process expired tickets
     * @return Number of processed tickets
     */
    int processExpiredTickets(Calendar time);

}
