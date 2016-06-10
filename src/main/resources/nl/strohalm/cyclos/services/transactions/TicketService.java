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

import java.util.List;

import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.transactions.PaymentRequestTicket;
import nl.strohalm.cyclos.entities.accounts.transactions.Ticket;
import nl.strohalm.cyclos.entities.accounts.transactions.TicketQuery;
import nl.strohalm.cyclos.entities.accounts.transactions.WebShopTicket;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.entities.exceptions.UnexpectedEntityException;
import nl.strohalm.cyclos.services.Service;
import nl.strohalm.cyclos.services.transactions.exceptions.AuthorizedPaymentInPastException;
import nl.strohalm.cyclos.services.transactions.exceptions.InvalidChannelException;
import nl.strohalm.cyclos.services.transactions.exceptions.MaxAmountPerDayExceededException;
import nl.strohalm.cyclos.services.transactions.exceptions.NotEnoughCreditsException;
import nl.strohalm.cyclos.services.transactions.exceptions.UpperCreditLimitReachedException;

/**
 * Service interface for tickets used in case of external (web-shop) payment. A ticket is a temporary unique authorization granted by Cyclos to the
 * member (web-shop) to receive an external payment. The authorization code is passed on by the web-shop to the member (buyer) for validation.
 * @author luis
 */
public interface TicketService extends Service {

    /**
     * Cancels the specified web shop ticket
     * @param clientIP the client request's IP used to check if this client can cancel the ticket<br>
     * Security note: we can't enforce permission control because there isn't a logged user.
     */
    WebShopTicket cancelWebShopTicket(long ticketId, String clientIP);

    /**
     * Generates a ticket, based on the parameter data. It invokes PaymentService#simulatePayment(TransferDTO), and only if the payment would be
     * successful, send the request
     * @throws InvalidChannelException The destination member is invalid for the channel
     * @throws NotEnoughCreditsException The account does not have enough credits
     * @throws MaxAmountPerDayExceededException The account has exceeded the maximum transaction amount per day for the specified transfer type
     * @throws UnexpectedEntityException Either transfer type or payment receiver are invalid
     * @throws UpperCreditLimitReachedException The payment cannot be performed because it would make the receiving account go beyond the upper credit
     * limit
     * @throws InvalidPaymentReceiverException When the given <code>DoPaymentDTO</code> is not the system
     * @throws AuthorizedPaymentInPastException When the payment would require authorization and is set to a past date
     */
    PaymentRequestTicket generate(PaymentRequestTicket ticket) throws InvalidChannelException, NotEnoughCreditsException, MaxAmountPerDayExceededException, UnexpectedEntityException, UpperCreditLimitReachedException, AuthorizedPaymentInPastException;

    /**
     * Loads a pending webshop ticket
     * @param clientIP the client request's IP used to check if this client can load the ticket<br>
     * Security note: we can't enforce permission control because there isn't a logged user.
     */
    WebShopTicket loadPendingWebShopTicket(String ticket, String clientIP, Relationship... fetch) throws EntityNotFoundException;

    /**
     * Searches for tickets
     */
    List<? extends Ticket> search(TicketQuery query);

    /**
     * Validates the specified ticket
     */
    void validate(Ticket ticket);
}
