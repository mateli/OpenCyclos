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

import java.util.HashMap;
import java.util.Map;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseAction;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferType;
import nl.strohalm.cyclos.entities.customization.fields.PaymentCustomField;
import nl.strohalm.cyclos.services.customization.PaymentCustomFieldService;
import nl.strohalm.cyclos.utils.ActionHelper;
import nl.strohalm.cyclos.utils.EntityHelper;

import org.apache.struts.action.ActionForward;

/**
 * Action used to link an existing custom field to another payment type
 * 
 * @author luis
 */
public class LinkPaymentCustomFieldAction extends BaseAction {

    private PaymentCustomFieldService paymentCustomFieldService;

    @Inject
    public void setPaymentCustomFieldService(final PaymentCustomFieldService paymentCustomFieldService) {
        this.paymentCustomFieldService = paymentCustomFieldService;
    }

    @Override
    protected ActionForward executeAction(final ActionContext context) throws Exception {
        final LinkPaymentCustomFieldForm form = context.getForm();
        final TransferType transferType = EntityHelper.reference(TransferType.class, form.getTransferTypeId());
        final PaymentCustomField customField = EntityHelper.reference(PaymentCustomField.class, form.getCustomFieldId());

        paymentCustomFieldService.link(transferType, customField);

        final Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("transferTypeId", form.getTransferTypeId());
        parameters.put("accountTypeId", form.getAccountTypeId());
        return ActionHelper.redirectWithParams(context.getRequest(), context.getSuccessForward(), parameters);
    }
}
