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
package nl.strohalm.cyclos.controls.access.transactionpassword;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseAjaxAction;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferType;
import nl.strohalm.cyclos.services.transfertypes.TransferTypeService;
import nl.strohalm.cyclos.utils.JSONBuilder;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.binding.PropertyBinder;

public class IsUsedTransactionPasswordAjaxAction extends BaseAjaxAction {

    private TransferTypeService transferTypeService;

    @Inject
    public void setTransferTypeService(final TransferTypeService transferTypeService) {
        this.transferTypeService = transferTypeService;
    }

    @Override
    protected ContentType contentType() {
        return ContentType.JSON;
    }

    @Override
    protected void renderContent(final ActionContext context) throws Exception {
        final IsUsedTransactionPasswordAjaxForm form = context.getForm();
        boolean isUsed = false;

        try {
            final Long transferTypeId = getDataBinder().readFromString(form);
            // Load the TT
            final TransferType transferType = transferTypeService.load(transferTypeId, TransferType.Relationships.FROM);
            isUsed = context.isTransactionPasswordEnabled(transferType.getFrom());
        } catch (final Exception e) {
            // if error then nothing to, just return false.
        }

        final JSONBuilder json = new JSONBuilder();
        json.set("isUsed", isUsed);
        responseHelper.writeJSON(context.getResponse(), json);
    }

    private DataBinder<Long> getDataBinder() {
        return PropertyBinder.instance(Long.class, "transferTypeId");
    }
}
