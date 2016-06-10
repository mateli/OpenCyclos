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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.BasePublicAction;
import nl.strohalm.cyclos.entities.accounts.transactions.WebShopTicket;
import nl.strohalm.cyclos.services.transactions.DoPaymentDTO;
import nl.strohalm.cyclos.services.transactions.TicketService;
import nl.strohalm.cyclos.utils.validation.ValidationException;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action used to cancel a webshop payment
 * @author luis
 */
public class CancelWebShopPaymentAction extends BasePublicAction {

    private TicketService ticketService;

    @Override
    public ActionForward executeAction(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final DoPaymentDTO payment = resolvePayment(request);
        WebShopTicket ticket = (WebShopTicket) payment.getTicket();
        try {
            ticket = ticketService.cancelWebShopTicket(ticket.getId(), request.getRemoteAddr());
        } catch (final Exception e) {
            // Ignore
        }
        return WebShopHelper.returnForward(ticket);
    }

    @Inject
    public void setTicketService(final TicketService ticketService) {
        this.ticketService = ticketService;
    }

    private DoPaymentDTO resolvePayment(final HttpServletRequest request) {
        final DoPaymentDTO payment = WebShopHelper.getNewPayment(request.getSession());
        if (payment == null) {
            throw new ValidationException();
        }
        return payment;
    }
}
