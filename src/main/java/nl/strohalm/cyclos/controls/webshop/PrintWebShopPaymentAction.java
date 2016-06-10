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

import nl.strohalm.cyclos.controls.BasePublicAction;
import nl.strohalm.cyclos.entities.accounts.transactions.Payment;
import nl.strohalm.cyclos.entities.accounts.transactions.Transfer;
import nl.strohalm.cyclos.entities.accounts.transactions.WebShopTicket;
import nl.strohalm.cyclos.utils.validation.ValidationException;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action used to print a webshop payment performed
 * @author luis
 */
public class PrintWebShopPaymentAction extends BasePublicAction {

    @Override
    protected ActionForward executeAction(final ActionMapping mapping, final ActionForm actionForm, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final Payment payment = WebShopHelper.getPerformedPayment(request.getSession());
        if (!(payment instanceof Transfer)) {
            throw new ValidationException();
        }
        request.setAttribute("transfer", payment);

        // Store the return path
        final WebShopTicket ticket = (WebShopTicket) WebShopHelper.getUpdatedPayment(request.getSession()).getTicket();
        final String returnUrl = WebShopHelper.returnForward(ticket).getPath();
        request.setAttribute("returnUrl", returnUrl);

        return mapping.getInputForward();
    }

}
