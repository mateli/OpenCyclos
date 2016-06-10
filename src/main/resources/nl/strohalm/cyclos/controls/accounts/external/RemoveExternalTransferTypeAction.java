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
package nl.strohalm.cyclos.controls.accounts.external;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseAction;
import nl.strohalm.cyclos.entities.accounts.external.ExternalTransferType;
import nl.strohalm.cyclos.exceptions.PermissionDeniedException;
import nl.strohalm.cyclos.services.accounts.external.ExternalTransferTypeService;
import nl.strohalm.cyclos.utils.ActionHelper;
import nl.strohalm.cyclos.utils.validation.ValidationException;

import org.apache.struts.action.ActionForward;

/**
 * Action used to remove a External Transfer Type
 * @author Lucas Geiss
 */
public class RemoveExternalTransferTypeAction extends BaseAction {

    private ExternalTransferTypeService externalTransferTypeService;

    @Inject
    public void setExternalTransferTypeService(final ExternalTransferTypeService externalTransferTypeService) {
        this.externalTransferTypeService = externalTransferTypeService;
    }

    @Override
    protected ActionForward executeAction(final ActionContext context) throws Exception {
        final RemoveExternalTransferTypeForm form = context.getForm();
        final long id = form.getExternalTransferTypeId();
        if (id <= 0L) {
            throw new ValidationException();
        }
        final ExternalTransferType transferType = externalTransferTypeService.load(id);
        try {
            externalTransferTypeService.remove(id);
            context.sendMessage("externalTransferType.removed");
        } catch (final PermissionDeniedException e) {
            throw e;
        } catch (final Exception e) {
            context.sendMessage("externalTransferType.error.removing");
        }
        return ActionHelper.redirectWithParam(context.getRequest(), context.getSuccessForward(), "externalAccountId", transferType.getAccount().getId());

    }
}
