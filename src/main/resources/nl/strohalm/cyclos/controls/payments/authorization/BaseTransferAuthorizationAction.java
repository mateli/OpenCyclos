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
import nl.strohalm.cyclos.controls.accounts.details.ViewTransactionAction;
import nl.strohalm.cyclos.entities.accounts.transactions.Transfer;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferAuthorizationDTO;
import nl.strohalm.cyclos.utils.ActionHelper;
import nl.strohalm.cyclos.utils.binding.BeanBinder;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.binding.PropertyBinder;
import nl.strohalm.cyclos.utils.conversion.ReferenceConverter;

import org.apache.struts.action.ActionForward;

public abstract class BaseTransferAuthorizationAction extends ViewTransactionAction {

    private DataBinder<TransferAuthorizationDTO> dataBinder;

    public DataBinder<TransferAuthorizationDTO> getDataBinder() {
        if (dataBinder == null) {
            final BeanBinder<TransferAuthorizationDTO> binder = BeanBinder.instance(TransferAuthorizationDTO.class);
            binder.registerBinder("transfer", PropertyBinder.instance(Transfer.class, "transferId", ReferenceConverter.instance(Transfer.class)));
            binder.registerBinder("comments", PropertyBinder.instance(String.class, "comments"));
            binder.registerBinder("showToMember", PropertyBinder.instance(Boolean.TYPE, "showToMember"));
            dataBinder = binder;
        }
        return dataBinder;
    }

    @Override
    protected ActionForward handleSubmit(final ActionContext context) throws Exception {
        final TransferAuthorizationDTO transferAuthorizationDto = resolveAuthorizationDto(context);
        if (shouldValidateTransactionPassword(context, transferAuthorizationDto.getTransfer())) {
            context.validateTransactionPassword();
        }

        performAction(context);
        return ActionHelper.redirectWithParams(context.getRequest(), context.getSuccessForward(), resolveForwardParams(context));
    }

    protected void initializeTransfer(final TransferAuthorizationDTO transferAuthorizationDto) {
        Transfer transfer = transferAuthorizationDto.getTransfer();
        transfer = paymentService.load(transfer.getId(), FETCH);
        transferAuthorizationDto.setTransfer(transfer);
    }

    protected abstract Transfer performAction(ActionContext context);

    protected TransferAuthorizationDTO resolveAuthorizationDto(final ActionContext context) {
        final TransferAuthorizationDTO transferAuthorizationDto = getDataBinder().readFromString(context.getForm());
        initializeTransfer(transferAuthorizationDto);
        return transferAuthorizationDto;
    }
}
