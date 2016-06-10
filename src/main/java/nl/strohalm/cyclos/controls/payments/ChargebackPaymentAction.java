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
package nl.strohalm.cyclos.controls.payments;

import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.accounts.details.ViewTransactionAction;
import nl.strohalm.cyclos.entities.accounts.transactions.Payment;
import nl.strohalm.cyclos.entities.accounts.transactions.Transfer;
import nl.strohalm.cyclos.services.transactions.exceptions.CreditsException;
import nl.strohalm.cyclos.utils.ActionHelper;
import nl.strohalm.cyclos.utils.validation.ValidationException;

import org.apache.struts.action.ActionForward;

/**
 * Action used to chargeback a payment
 * 
 * @author luis
 */
public class ChargebackPaymentAction extends ViewTransactionAction {

    @Override
    protected ActionForward handleSubmit(final ActionContext context) throws Exception {
        final ChargebackPaymentForm form = context.getForm();
        final long transferId = form.getTransferId();
        if (transferId <= 0L) {
            throw new ValidationException();
        }
        final Transfer transfer = paymentService.load(transferId, Payment.Relationships.FROM);

        checkTransactionPassword(context, transfer);

        Transfer chargeback;
        try {
            chargeback = paymentService.chargeback(transfer);
        } catch (final CreditsException e) {
            return context.sendError(actionHelper.resolveErrorKey(e), actionHelper.resolveParameters(e));
        }
        context.sendMessage("payment.chargedBack");
        return ActionHelper.redirectWithParam(context.getRequest(), context.getSuccessForward(), "transferId", chargeback.getId());
    }
}
