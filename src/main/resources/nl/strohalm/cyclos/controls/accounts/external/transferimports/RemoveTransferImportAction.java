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
package nl.strohalm.cyclos.controls.accounts.external.transferimports;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseAction;
import nl.strohalm.cyclos.entities.exceptions.UnexpectedEntityException;
import nl.strohalm.cyclos.services.accounts.external.ExternalTransferImportService;
import nl.strohalm.cyclos.utils.validation.ValidationException;

import org.apache.struts.action.ActionForward;

/**
 * Action used to remove a transfer import
 * @author luis
 */
public class RemoveTransferImportAction extends BaseAction {

    private ExternalTransferImportService externalTransferImportService;

    @Inject
    public void setExternalTransferImportService(final ExternalTransferImportService externalTransferImportService) {
        this.externalTransferImportService = externalTransferImportService;
    }

    @Override
    protected ActionForward executeAction(final ActionContext context) throws Exception {
        final RemoveTransferImportForm form = context.getForm();
        final long id = form.getTransferImportId();
        if (id <= 0L) {
            throw new ValidationException();
        }
        try {
            externalTransferImportService.remove(id);
            context.sendMessage("externalTransferImport.removed");
        } catch (final UnexpectedEntityException e) {
            context.sendMessage("externalTransferImport.error.removing");
        }
        return context.getSuccessForward();
    }
}
