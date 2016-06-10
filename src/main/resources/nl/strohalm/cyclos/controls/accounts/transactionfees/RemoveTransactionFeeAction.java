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
package nl.strohalm.cyclos.controls.accounts.transactionfees;

import java.util.HashMap;
import java.util.Map;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseAction;
import nl.strohalm.cyclos.entities.accounts.fees.transaction.TransactionFee;
import nl.strohalm.cyclos.services.transfertypes.TransactionFeeService;
import nl.strohalm.cyclos.utils.ActionHelper;
import nl.strohalm.cyclos.utils.validation.ValidationException;

import org.apache.struts.action.ActionForward;

/**
 * Action used to remove a transaction fee
 * @author luis
 */
public class RemoveTransactionFeeAction extends BaseAction {

    private TransactionFeeService transactionFeeService;

    public TransactionFeeService getTransactionFeeService() {
        return transactionFeeService;
    }

    @Inject
    public void setTransactionFeeService(final TransactionFeeService transactionFeeService) {
        this.transactionFeeService = transactionFeeService;
    }

    @Override
    protected ActionForward executeAction(final ActionContext context) throws Exception {
        final RemoveTransactionFeeForm form = context.getForm();
        final long accountTypeId = form.getAccountTypeId();
        final long transferTypeId = form.getTransferTypeId();
        final long transactionFeeId = form.getTransactionFeeId();
        if (accountTypeId <= 0L || transferTypeId <= 0L || transactionFeeId <= 0L) {
            throw new ValidationException();
        }
        TransactionFee fee = null;
        try {
            fee = transactionFeeService.load(transactionFeeId);
            transactionFeeService.remove(transactionFeeId);
            if (fee.getNature() == TransactionFee.Nature.SIMPLE) {
                context.sendMessage("transactionFee.removed");
            } else {
                context.sendMessage("brokerCommission.removed");
            }
        } catch (final Exception e) {
            if (fee.getNature() == TransactionFee.Nature.SIMPLE) {
                context.sendMessage("transactionFee.error.removing");
            } else {
                context.sendMessage("brokerCommission.error.removing");
            }
        }
        final Map<String, Object> params = new HashMap<String, Object>();
        params.put("accountTypeId", accountTypeId);
        params.put("transferTypeId", transferTypeId);
        return ActionHelper.redirectWithParams(context.getRequest(), context.getSuccessForward(), params);
    }

}
