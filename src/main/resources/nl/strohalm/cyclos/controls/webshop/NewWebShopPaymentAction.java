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
package nl.strohalm.cyclos.controls.webshop;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.BasePublicAction;
import nl.strohalm.cyclos.entities.access.Channel;
import nl.strohalm.cyclos.entities.accounts.transactions.Ticket;
import nl.strohalm.cyclos.entities.accounts.transactions.WebShopTicket;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.services.transactions.DoPaymentDTO;
import nl.strohalm.cyclos.services.transactions.TicketService;
import nl.strohalm.cyclos.services.transactions.TransactionContext;
import nl.strohalm.cyclos.utils.validation.ValidationException;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action used by a member to redirect his client to a new webshop payment
 * @author luis
 */
public class NewWebShopPaymentAction extends BasePublicAction {
    private TicketService ticketService;

    @Override
    public ActionForward executeAction(final ActionMapping mapping, final ActionForm actionForm, final HttpServletRequest request, final HttpServletResponse response) throws IOException {
        final NewWebShopPaymentForm form = (NewWebShopPaymentForm) actionForm;

        // Retrieve the ticket
        final String ticketStr = form.getTicket();
        WebShopTicket ticket;
        try {
            final Ticket loaded = ticketService.loadPendingWebShopTicket(ticketStr, request.getRemoteAddr(), Ticket.Relationships.CURRENCY, Ticket.Relationships.TO);
            ticket = (WebShopTicket) loaded;
        } catch (final EntityNotFoundException e) {
            throw new ValidationException("webshop.error.ticket");
        }

        // Build the payment DTO
        final DoPaymentDTO payment = new DoPaymentDTO();
        payment.setChannel(Channel.WEBSHOP);
        payment.setContext(TransactionContext.PAYMENT);
        payment.setTo(ticket.getTo());
        payment.setTicket(ticket);
        payment.setAmount(ticket.getAmount());
        payment.setCurrency(ticket.getCurrency());
        payment.setDescription(ticket.getDescription());

        // Store the session attributes
        final HttpSession session = request.getSession();
        WebShopHelper.setNewPayment(session, payment);
        session.setAttribute("errorReturnTo", WebShopHelper.returnForward(ticket).getPath());

        session.setAttribute("isWebShop", true);
        session.setAttribute("isPosWeb", false);

        return mapping.findForward("success");
    }

    @Inject
    public void setTicketService(final TicketService ticketService) {
        this.ticketService = ticketService;
    }
}
