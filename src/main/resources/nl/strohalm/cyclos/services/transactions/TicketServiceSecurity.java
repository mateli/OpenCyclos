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

import nl.strohalm.cyclos.access.MemberPermission;
import nl.strohalm.cyclos.access.OperatorPermission;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.transactions.PaymentRequestTicket;
import nl.strohalm.cyclos.entities.accounts.transactions.Ticket;
import nl.strohalm.cyclos.entities.accounts.transactions.TicketQuery;
import nl.strohalm.cyclos.entities.accounts.transactions.WebShopTicket;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.entities.exceptions.UnexpectedEntityException;
import nl.strohalm.cyclos.services.BaseServiceSecurity;
import nl.strohalm.cyclos.services.transactions.exceptions.AuthorizedPaymentInPastException;
import nl.strohalm.cyclos.services.transactions.exceptions.InvalidChannelException;
import nl.strohalm.cyclos.services.transactions.exceptions.MaxAmountPerDayExceededException;
import nl.strohalm.cyclos.services.transactions.exceptions.NotEnoughCreditsException;
import nl.strohalm.cyclos.services.transactions.exceptions.UpperCreditLimitReachedException;

/**
 * Security implementation for {@link TicketService}
 * 
 * @author jcomas
 */
public class TicketServiceSecurity extends BaseServiceSecurity implements TicketService {

    private TicketServiceLocal ticketService;

    @Override
    public WebShopTicket cancelWebShopTicket(final long ticketId, final String clientIP) {
        // Nothing to check.
        return ticketService.cancelWebShopTicket(ticketId, clientIP);
    }

    @Override
    public PaymentRequestTicket generate(final PaymentRequestTicket ticket) throws InvalidChannelException, NotEnoughCreditsException, MaxAmountPerDayExceededException, UnexpectedEntityException, UpperCreditLimitReachedException, AuthorizedPaymentInPastException {
        permissionService.permission(ticket.getTo())
                .member(MemberPermission.PAYMENTS_REQUEST)
                .operator(OperatorPermission.PAYMENTS_REQUEST)
                .check();
        return ticketService.generate(ticket);
    }

    @Override
    public WebShopTicket loadPendingWebShopTicket(final String ticket, final String clientIP, final Relationship... fetch) throws EntityNotFoundException {
        // Nothing to check.
        return ticketService.loadPendingWebShopTicket(ticket, clientIP, fetch);
    }

    @Override
    public List<? extends Ticket> search(final TicketQuery query) {
        permissionService.permission(query.getTo())
                .member(MemberPermission.PAYMENTS_REQUEST)
                .operator(OperatorPermission.PAYMENTS_REQUEST)
                .check();
        return ticketService.search(query);
    }

    public void setTicketServiceLocal(final TicketServiceLocal ticketService) {
        this.ticketService = ticketService;
    }

    @Override
    public void validate(final Ticket ticket) {
        // Nothing to check
        ticketService.validate(ticket);
    }

}
