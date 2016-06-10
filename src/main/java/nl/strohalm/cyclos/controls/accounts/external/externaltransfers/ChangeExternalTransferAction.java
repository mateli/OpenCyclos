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
package nl.strohalm.cyclos.controls.accounts.external.externaltransfers;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseAction;
import nl.strohalm.cyclos.entities.accounts.external.ExternalTransferAction;
import nl.strohalm.cyclos.services.accounts.external.ExternalTransferService;
import nl.strohalm.cyclos.services.accounts.external.exceptions.CannotDeleteExternalTransferException;
import nl.strohalm.cyclos.services.accounts.external.exceptions.CannotMarkExternalTransferAsCheckedException;
import nl.strohalm.cyclos.services.accounts.external.exceptions.CannotMarkExternalTransferAsUncheckedException;
import nl.strohalm.cyclos.utils.ActionHelper;
import nl.strohalm.cyclos.utils.conversion.CoercionHelper;
import nl.strohalm.cyclos.utils.validation.ValidationException;

import org.apache.struts.action.ActionForward;

/**
 * Action used to change the status of external transfers
 * @author Jefferson Magno
 */
public class ChangeExternalTransferAction extends BaseAction {

    private ExternalTransferService externalTransferService;

    @Inject
    public void setExternalTransferService(final ExternalTransferService externalTransferService) {
        this.externalTransferService = externalTransferService;
    }

    @Override
    protected ActionForward executeAction(final ActionContext context) throws Exception {
        final ChangeExternalTransferForm form = context.getForm();
        final ExternalTransferAction action = CoercionHelper.coerce(ExternalTransferAction.class, form.getAction());
        final Long[] ids = form.getExternalTransferId();
        if (action == null || ids == null || ids.length == 0) {
            throw new ValidationException();
        }
        try {
            externalTransferService.performAction(action, ids);
        } catch (final CannotDeleteExternalTransferException e) {
            return context.sendError("externalAccountHistory.error.cannotDeleteExternalTransfer");
        } catch (final CannotMarkExternalTransferAsCheckedException e) {
            return context.sendError("externalAccountHistory.error.cannotMarkExternalTransferAsChecked");
        } catch (final CannotMarkExternalTransferAsUncheckedException e) {
            return context.sendError("externalAccountHistory.error.cannotMarkExternalTransferAsUnchecked");
        }
        if (action == ExternalTransferAction.DELETE) {
            context.sendMessage("externalTransfer.removed");
        }
        final long transferImportId = form.getTransferImportId();
        final long externalAccountId = form.getExternalAccountId();
        String paramName = null;
        long paramValue = 0;
        if (transferImportId > 0) {
            paramName = "transferImportId";
            paramValue = transferImportId;
        } else {
            paramName = "externalAccountId";
            paramValue = externalAccountId;
        }
        return ActionHelper.redirectWithParam(context.getRequest(), context.getSuccessForward(), paramName, paramValue);
    }

}
