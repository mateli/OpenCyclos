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

import javax.servlet.http.HttpSession;

import nl.strohalm.cyclos.entities.accounts.transactions.Payment;
import nl.strohalm.cyclos.entities.accounts.transactions.WebShopTicket;
import nl.strohalm.cyclos.services.transactions.DoPaymentDTO;

import org.apache.struts.action.ActionForward;

/**
 * Helper methods for webshop payments
 * 
 * @author luis
 */
public class WebShopHelper {

    private static final String PERFORMED_PAYMENT_KEY = "webshopPerformedPayment";
    private static final String NEW_PAYMENT_KEY       = "webshopNewPayment";
    private static final String UPDATED_PAYMENT_KEY   = "webshopUpdatedPayment";

    /**
     * Returns the new payment DTO
     */
    public static DoPaymentDTO getNewPayment(final HttpSession session) {
        return (DoPaymentDTO) session.getAttribute(NEW_PAYMENT_KEY);
    }

    /**
     * Sets the performed payment
     */
    public static Payment getPerformedPayment(final HttpSession session) {
        return (Payment) session.getAttribute(PERFORMED_PAYMENT_KEY);
    }

    /**
     * Returns the payment DTO, updated with context data
     */
    public static DoPaymentDTO getUpdatedPayment(final HttpSession session) {
        return (DoPaymentDTO) session.getAttribute(UPDATED_PAYMENT_KEY);
    }

    /**
     * Returns a forward to the ticket's return url
     */
    public static ActionForward returnForward(final WebShopTicket ticket) {
        final String url = ticket.getReturnUrl();
        final String separator = url.contains("?") ? "&" : "?";
        final String fullUrl = url + separator + "ticket=" + ticket.getTicket();
        return new ActionForward(fullUrl, true);
    }

    /**
     * Sets the new payment DTO
     */
    public static void setNewPayment(final HttpSession session, final DoPaymentDTO payment) {
        session.setAttribute(NEW_PAYMENT_KEY, payment);
    }

    /**
     * Sets the performed payment
     */
    public static void setPerformedPayment(final HttpSession session, final Payment payment) {
        session.setAttribute(PERFORMED_PAYMENT_KEY, payment);
    }

    /**
     * Sets the payment DTO, updated with context data
     */
    public static void setUpdatedPayment(final HttpSession session, final DoPaymentDTO payment) {
        session.setAttribute(UPDATED_PAYMENT_KEY, payment);
    }
}
