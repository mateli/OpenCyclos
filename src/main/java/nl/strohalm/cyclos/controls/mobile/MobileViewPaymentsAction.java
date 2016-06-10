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
package nl.strohalm.cyclos.controls.mobile;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.mobile.exceptions.MobileException;
import nl.strohalm.cyclos.entities.accounts.transactions.Payment;
import nl.strohalm.cyclos.entities.accounts.transactions.Transfer;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferQuery;
import nl.strohalm.cyclos.services.transactions.PaymentService;
import nl.strohalm.cyclos.utils.query.Page;
import nl.strohalm.cyclos.utils.query.PageParameters;
import nl.strohalm.cyclos.utils.query.QueryParameters.ResultType;

import org.apache.struts.action.ActionForward;

/**
 * Action used to view payments
 * @author luis
 */
public class MobileViewPaymentsAction extends MobileBaseAction {

    private PaymentService paymentService;

    public PaymentService getPaymentService() {
        return paymentService;
    }

    @Inject
    public void setPaymentService(final PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @Override
    protected ActionForward executeAction(final MobileActionContext context) throws Exception {
        final MobileViewPaymentsForm form = context.getForm();
        final HttpServletRequest request = context.getRequest();

        // Retrieve the current payment
        final TransferQuery query = new TransferQuery();
        query.setPageParameters(new PageParameters(1, form.getCurrent()));
        query.setOwner(context.getAccountOwner());
        query.setType(context.getCurrentAccountType());
        query.fetch(Payment.Relationships.FROM, Payment.Relationships.TO, Payment.Relationships.TYPE);
        query.setReverseOrder(true);
        query.setResultType(ResultType.PAGE);
        final Page<Transfer> page = (Page<Transfer>) paymentService.search(query);
        final int total = page.getPageCount();
        final int current = page.getCurrentPage();
        if (page == null || page.isEmpty() || total == 0) {
            throw new MobileException(true, "mobile.viewPayments.title", "mobile.viewPayments.noPayment");
        }
        final Transfer payment = page.get(0);
        final boolean isDebit = context.getAccountOwner().equals(payment.getFrom().getOwner());

        // Store the request data
        request.setAttribute("payment", payment);
        request.setAttribute("isDebit", isDebit);
        BigDecimal amount = payment.getAmount();
        if (isDebit) {
            amount = amount.negate();
        }
        request.setAttribute("amount", getUnitsConverter(context).toString(amount));
        request.setAttribute("previous", current > 0 ? (Integer) (current - 1) : null);
        request.setAttribute("next", current < total - 1 ? (Integer) (current + 1) : null);

        return context.getInputForward();
    }

}
