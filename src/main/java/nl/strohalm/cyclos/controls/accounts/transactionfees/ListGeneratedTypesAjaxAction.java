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

import java.util.List;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.accounts.transfertypes.SearchTransferTypesAjaxAction;
import nl.strohalm.cyclos.entities.accounts.fees.transaction.BrokerCommission;
import nl.strohalm.cyclos.entities.accounts.fees.transaction.BrokerCommission.WhichBroker;
import nl.strohalm.cyclos.entities.accounts.fees.transaction.SimpleTransactionFee;
import nl.strohalm.cyclos.entities.accounts.fees.transaction.TransactionFee;
import nl.strohalm.cyclos.entities.accounts.fees.transaction.TransactionFee.Nature;
import nl.strohalm.cyclos.entities.accounts.fees.transaction.TransactionFee.Subject;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferType;
import nl.strohalm.cyclos.services.transfertypes.TransactionFeeService;
import nl.strohalm.cyclos.utils.ClassHelper;
import nl.strohalm.cyclos.utils.validation.ValidationException;

/**
 * Ajax action used to retrieve the possible transfer types for
 * @author luis
 */
public class ListGeneratedTypesAjaxAction extends SearchTransferTypesAjaxAction {

    private TransactionFeeService transactionFeeService;

    @Inject
    public void setTransactionFeeService(final TransactionFeeService transactionFeeService) {
        this.transactionFeeService = transactionFeeService;
    }

    @Override
    protected List<TransferType> executeQuery(final ActionContext context) {
        try {
            final ListGeneratedTypesAjaxForm form = context.getForm();
            final TransferType transferType = getTransferTypeService().load(form.getTransferTypeId());
            final Nature nature = Nature.valueOf(form.getNature());

            // Create a transaction fee to be used on the buildGeneratedTypeQuery method
            final TransactionFee fee = ClassHelper.instantiate(nature.getFeeClass());
            fee.setOriginalTransferType(transferType);
            fee.setPayer(Subject.valueOf(form.getPayer()));
            switch (nature) {
                case SIMPLE:
                    ((SimpleTransactionFee) fee).setReceiver(Subject.valueOf(form.getReceiver()));
                    break;
                case BROKER:
                    ((BrokerCommission) fee).setWhichBroker(WhichBroker.valueOf(form.getWhichBroker()));
                    break;
            }
            return transactionFeeService.searchGeneratedTransferTypes(fee, form.isAllowAnyAccount(), false);

        } catch (final Exception e) {
            throw new ValidationException();
        }
    }

    @Override
    protected Options[] resolveOptions(final ActionContext context) {
        return new Options[] { Options.DIRECTION };
    }

}
