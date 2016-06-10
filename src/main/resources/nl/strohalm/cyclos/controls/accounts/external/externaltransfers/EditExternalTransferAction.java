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

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.EnumSet;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.access.AdminSystemPermission;
import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseFormAction;
import nl.strohalm.cyclos.entities.accounts.external.ExternalAccount;
import nl.strohalm.cyclos.entities.accounts.external.ExternalTransfer;
import nl.strohalm.cyclos.entities.accounts.external.ExternalTransferImport;
import nl.strohalm.cyclos.entities.accounts.external.ExternalTransferType;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.entities.settings.events.LocalSettingsChangeListener;
import nl.strohalm.cyclos.entities.settings.events.LocalSettingsEvent;
import nl.strohalm.cyclos.services.accounts.external.ExternalAccountService;
import nl.strohalm.cyclos.services.accounts.external.ExternalTransferService;
import nl.strohalm.cyclos.utils.ActionHelper;
import nl.strohalm.cyclos.utils.binding.BeanBinder;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.binding.PropertyBinder;
import nl.strohalm.cyclos.utils.conversion.IdConverter;
import nl.strohalm.cyclos.utils.conversion.ReferenceConverter;
import nl.strohalm.cyclos.utils.validation.ValidationException;

import org.apache.struts.action.ActionForward;

/**
 * Action used to edit an external transfer
 * @author Jefferson Magno
 */
public class EditExternalTransferAction extends BaseFormAction implements LocalSettingsChangeListener {

    private ExternalAccountService       externalAccountService;
    private ExternalTransferService      externalTransferService;
    private DataBinder<ExternalTransfer> dataBinder;

    public DataBinder<ExternalTransfer> getDataBinder() {
        if (dataBinder == null) {
            final LocalSettings localSettings = settingsService.getLocalSettings();
            final BeanBinder<ExternalTransfer> externalTransferBinder = BeanBinder.instance(ExternalTransfer.class);
            externalTransferBinder.registerBinder("id", PropertyBinder.instance(Long.class, "id", IdConverter.instance()));
            externalTransferBinder.registerBinder("account", PropertyBinder.instance(ExternalAccount.class, "account", ReferenceConverter.instance(ExternalAccount.class)));
            externalTransferBinder.registerBinder("transferImport", PropertyBinder.instance(ExternalTransferImport.class, "transferImport", ReferenceConverter.instance(ExternalTransferImport.class)));
            externalTransferBinder.registerBinder("type", PropertyBinder.instance(ExternalTransferType.class, "type", ReferenceConverter.instance(ExternalTransferType.class)));
            externalTransferBinder.registerBinder("date", PropertyBinder.instance(Calendar.class, "date", localSettings.getRawDateConverter()));
            externalTransferBinder.registerBinder("amount", PropertyBinder.instance(BigDecimal.class, "amount", localSettings.getNumberConverter()));
            externalTransferBinder.registerBinder("member", PropertyBinder.instance(Member.class, "member", ReferenceConverter.instance(Member.class)));
            externalTransferBinder.registerBinder("description", PropertyBinder.instance(String.class, "description"));
            dataBinder = externalTransferBinder;
        }
        return dataBinder;
    }

    @Override
    public void onLocalSettingsUpdate(final LocalSettingsEvent event) {
        dataBinder = null;
    }

    @Inject
    public void setExternalAccountService(final ExternalAccountService externalAccountService) {
        this.externalAccountService = externalAccountService;
    }

    @Inject
    public void setExternalTransferService(final ExternalTransferService externalTransferService) {
        this.externalTransferService = externalTransferService;
    }

    @Override
    protected ActionForward handleSubmit(final ActionContext context) throws Exception {
        final EditExternalTransferForm form = context.getForm();
        ExternalTransfer externalTransfer = readExternalTransfer(context);
        final boolean isInsert = externalTransfer.isTransient();
        externalTransfer = externalTransferService.save(externalTransfer);
        context.sendMessage(isInsert ? "externalTransfer.inserted" : "externalTransfer.modified");
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

    @Override
    protected void prepareForm(final ActionContext context) throws Exception {
        final HttpServletRequest request = context.getRequest();
        final EditExternalTransferForm form = context.getForm();

        final long externalAccountId = form.getExternalAccountId();
        if (externalAccountId <= 0) {
            throw new ValidationException();
        }

        final ExternalAccount externalAccount = externalAccountService.load(externalAccountId, ExternalAccount.Relationships.TYPES);
        request.setAttribute("externalAccount", externalAccount);

        final long transferImportId = form.getTransferImportId();
        request.setAttribute("transferImportId", transferImportId);

        final long externalTransferId = form.getExternalTransferId();
        final boolean isInsert = (externalTransferId <= 0);
        ExternalTransfer externalTransfer = null;
        if (isInsert) {
            externalTransfer = new ExternalTransfer();
        } else {
            externalTransfer = externalTransferService.load(externalTransferId, ExternalTransfer.Relationships.TRANSFER_IMPORT, ExternalTransfer.Relationships.TYPE, ExternalTransfer.Relationships.MEMBER);
        }
        getDataBinder().writeAsString(form.getExternalTransfer(), externalTransfer);
        final boolean editable = permissionService.hasPermission(AdminSystemPermission.EXTERNAL_ACCOUNTS_MANAGE_PAYMENT) && externalTransfer.getStatus() == ExternalTransfer.Status.PENDING;
        request.setAttribute("externalTransfer", externalTransfer);
        request.setAttribute("editable", editable);
        request.setAttribute("isInsert", isInsert);
        request.setAttribute("statusList", EnumSet.of(ExternalTransfer.Status.PENDING, ExternalTransfer.Status.CHECKED));
    }

    @Override
    protected void validateForm(final ActionContext context) {
        final ExternalTransfer externalTransfer = readExternalTransfer(context);
        externalTransferService.validate(externalTransfer);
    }

    private ExternalTransfer readExternalTransfer(final ActionContext context) {
        final EditExternalTransferForm form = context.getForm();
        return getDataBinder().readFromString(form.getExternalTransfer());
    }

}
