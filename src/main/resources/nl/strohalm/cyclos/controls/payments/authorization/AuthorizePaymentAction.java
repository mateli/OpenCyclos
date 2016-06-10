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
package nl.strohalm.cyclos.controls.payments.authorization;

import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.entities.accounts.transactions.Transfer;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferAuthorizationDTO;
import nl.strohalm.cyclos.services.transactions.exceptions.AlreadyAuthorizedException;
import nl.strohalm.cyclos.utils.validation.ValidationException;

public class AuthorizePaymentAction extends BaseTransferAuthorizationAction {

    @Override
    protected Transfer performAction(final ActionContext context) {
        final TransferAuthorizationDTO transferAuthorizationDto = resolveAuthorizationDto(context);
        Transfer transfer = transferAuthorizationDto.getTransfer();
        checkTransactionPassword(context, transfer);
        try {
            transfer = transferAuthorizationService.authorize(transferAuthorizationDto);
            context.sendMessage(transfer.getProcessDate() == null ? "payment.nextAuthorizationLevel" : "payment.authorized");
        } catch (final AlreadyAuthorizedException e) {
            throw new ValidationException("transferAuthorization.error.alreadyAuthorized");
        }
        return transfer;
    }

}
