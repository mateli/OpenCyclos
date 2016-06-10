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
package nl.strohalm.cyclos.controls.invoices;

import java.util.HashMap;
import java.util.Map;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseAction;
import nl.strohalm.cyclos.entities.accounts.transactions.Invoice;
import nl.strohalm.cyclos.services.transactions.InvoiceService;
import nl.strohalm.cyclos.utils.ActionHelper;
import nl.strohalm.cyclos.utils.EntityHelper;
import nl.strohalm.cyclos.utils.validation.ValidationException;

import org.apache.struts.action.ActionForward;

/**
 * Action used to cancel an invoice
 * @author luis
 */
public class CancelInvoiceAction extends BaseAction {

    private InvoiceService invoiceService;

    public InvoiceService getInvoiceService() {
        return invoiceService;
    }

    @Inject
    public void setInvoiceService(final InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @Override
    protected ActionForward executeAction(final ActionContext context) throws Exception {
        final InvoiceDetailsForm form = context.getForm();
        final long id = form.getInvoiceId();
        if (id <= 0) {
            throw new ValidationException();
        }
        Invoice invoice = EntityHelper.reference(Invoice.class, id);
        invoice = invoiceService.cancel(invoice);
        context.sendMessage("invoice.cancelled");
        final Map<String, Object> params = new HashMap<String, Object>();
        params.put("invoiceId", invoice.getId());
        params.put("accountFeeLogId", form.getAccountFeeLogId());
        return ActionHelper.redirectWithParams(context.getRequest(), context.getSuccessForward(), params);
    }
}
