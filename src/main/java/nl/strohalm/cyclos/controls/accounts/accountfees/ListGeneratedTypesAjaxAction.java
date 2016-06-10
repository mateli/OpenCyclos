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
package nl.strohalm.cyclos.controls.accounts.accountfees;

import java.util.List;

import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.accounts.transfertypes.SearchTransferTypesAjaxAction;
import nl.strohalm.cyclos.entities.accounts.MemberAccountType;
import nl.strohalm.cyclos.entities.accounts.fees.account.AccountFee.PaymentDirection;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferType;
import nl.strohalm.cyclos.utils.EntityHelper;
import nl.strohalm.cyclos.utils.validation.ValidationException;

/**
 * Ajax action used to retrieve the possible transfer types for
 * @author luis
 */
public class ListGeneratedTypesAjaxAction extends SearchTransferTypesAjaxAction {

    @Override
    protected List<TransferType> executeQuery(final ActionContext context) {
        final EditAccountFeeForm form = context.getForm();
        MemberAccountType accountType;
        PaymentDirection paymentDirection;
        try {
            final long accountTypeId = form.getAccountTypeId();
            if (accountTypeId <= 0L) {
                throw new Exception();
            }
            accountType = EntityHelper.reference(MemberAccountType.class, accountTypeId);
            paymentDirection = PaymentDirection.valueOf(form.getPaymentDirection());
        } catch (final Exception e) {
            throw new ValidationException();
        }
        return transferTypeService.getPosibleTTsForAccountFee(accountType, paymentDirection);
    }

    @Override
    protected Options[] resolveOptions(final ActionContext context) {
        return null;
    }
}
