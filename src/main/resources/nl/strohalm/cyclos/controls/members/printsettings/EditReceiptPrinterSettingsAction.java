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
package nl.strohalm.cyclos.controls.members.printsettings;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseFormAction;
import nl.strohalm.cyclos.entities.members.printsettings.ReceiptPrinterSettings;
import nl.strohalm.cyclos.services.preferences.ReceiptPrinterSettingsService;
import nl.strohalm.cyclos.utils.ActionHelper;
import nl.strohalm.cyclos.utils.binding.BeanBinder;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.binding.PropertyBinder;
import nl.strohalm.cyclos.utils.conversion.IdConverter;

import org.apache.struts.action.ActionForward;

/**
 * Action used to edit a receipt printer settings
 * 
 * @author luis
 */
public class EditReceiptPrinterSettingsAction extends BaseFormAction {

    private ReceiptPrinterSettingsService      receiptPrinterSettingsService;
    private DataBinder<ReceiptPrinterSettings> dataBinder;

    @Inject
    public void setReceiptPrinterSettingsService(final ReceiptPrinterSettingsService receiptPrinterSettingsService) {
        this.receiptPrinterSettingsService = receiptPrinterSettingsService;
    }

    @Override
    protected ActionForward handleSubmit(final ActionContext context) throws Exception {
        final ReceiptPrinterSettings receiptPrinterSettings = read(context);
        final boolean isInsert = receiptPrinterSettings.isTransient();
        receiptPrinterSettingsService.save(receiptPrinterSettings);
        if (isInsert) {
            context.sendMessage("receiptPrinterSettings.created");
        } else {
            context.sendMessage("receiptPrinterSettings.modified");
        }
        return ActionHelper.redirectWithParam(context.getRequest(), context.getSuccessForward(), "id", receiptPrinterSettings.getId());
    }

    @Override
    protected void prepareForm(final ActionContext context) throws Exception {
        final EditReceiptPrinterSettingsForm form = context.getForm();
        final Long id = form.getId();
        ReceiptPrinterSettings receiptPrinterSettings;
        if (id == null) {
            receiptPrinterSettings = new ReceiptPrinterSettings();
        } else {
            receiptPrinterSettings = receiptPrinterSettingsService.load(id);
        }
        getDataBinder().writeAsString(form.getReceiptPrinterSettings(), receiptPrinterSettings);
        final HttpServletRequest request = context.getRequest();
        request.setAttribute("receiptPrinterSettings", receiptPrinterSettings);
        request.setAttribute("editable", context.isMember());
    }

    @Override
    protected void validateForm(final ActionContext context) {
        final ReceiptPrinterSettings receiptPrinterSettings = read(context);
        receiptPrinterSettingsService.validate(receiptPrinterSettings);
    }

    private DataBinder<ReceiptPrinterSettings> getDataBinder() {
        if (dataBinder == null) {
            final BeanBinder<ReceiptPrinterSettings> binder = BeanBinder.instance(ReceiptPrinterSettings.class);
            binder.registerBinder("id", PropertyBinder.instance(Long.class, "id", IdConverter.instance()));
            binder.registerBinder("name", PropertyBinder.instance(String.class, "name"));
            binder.registerBinder("printerName", PropertyBinder.instance(String.class, "printerName"));
            binder.registerBinder("beginOfDocCommand", PropertyBinder.instance(String.class, "beginOfDocCommand"));
            binder.registerBinder("endOfDocCommand", PropertyBinder.instance(String.class, "endOfDocCommand"));
            binder.registerBinder("paymentAdditionalMessage", PropertyBinder.instance(String.class, "paymentAdditionalMessage"));
            dataBinder = binder;
        }
        return dataBinder;
    }

    private ReceiptPrinterSettings read(final ActionContext context) {
        final EditReceiptPrinterSettingsForm form = context.getForm();
        final ReceiptPrinterSettings receiptPrinterSettings = getDataBinder().readFromString(form.getReceiptPrinterSettings());
        receiptPrinterSettings.setMember(context.getMember());
        return receiptPrinterSettings;
    }

}
