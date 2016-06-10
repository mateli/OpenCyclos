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
package nl.strohalm.cyclos.controls.customization.fields;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseAjaxAction;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferType;
import nl.strohalm.cyclos.entities.customization.fields.PaymentCustomField;
import nl.strohalm.cyclos.services.transfertypes.TransferTypeService;
import nl.strohalm.cyclos.utils.binding.BeanBinder;
import nl.strohalm.cyclos.utils.binding.BeanCollectionBinder;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.binding.PropertyBinder;
import nl.strohalm.cyclos.utils.conversion.CoercionHelper;

/**
 * Action used to list the payment fields directly related to a transfer type
 * 
 * @author luis
 */
public class ListPaymentFieldsAjaxAction extends BaseAjaxAction {

    private TransferTypeService                        transferTypeService;
    private DataBinder<Collection<PaymentCustomField>> customFieldBinder;

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
        final HttpServletRequest request = context.getRequest();
        final Long id = CoercionHelper.coerce(Long.class, request.getParameter("transferTypeId"));
        final TransferType transferType = transferTypeService.load(id, TransferType.Relationships.CUSTOM_FIELDS);

        final String json = getCustomFieldBinder().readAsString(transferType.getCustomFields());
        responseHelper.writeJSON(context.getResponse(), json);
    }

    private DataBinder<Collection<PaymentCustomField>> getCustomFieldBinder() {
        if (customFieldBinder == null) {
            final BeanBinder<PaymentCustomField> beanBinder = BeanBinder.instance(PaymentCustomField.class);
            beanBinder.registerBinder("id", PropertyBinder.instance(Long.class, "id"));
            beanBinder.registerBinder("name", PropertyBinder.instance(String.class, "name"));
            customFieldBinder = BeanCollectionBinder.instance(beanBinder);
        }
        return customFieldBinder;
    }

}
