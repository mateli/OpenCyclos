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

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.access.AdminSystemPermission;
import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseFormAction;
import nl.strohalm.cyclos.entities.accounts.external.ExternalAccount;
import nl.strohalm.cyclos.entities.accounts.external.ExternalTransferType;
import nl.strohalm.cyclos.entities.accounts.external.ExternalTransferType.Action;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferType;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferTypeQuery;
import nl.strohalm.cyclos.services.accounts.external.ExternalAccountService;
import nl.strohalm.cyclos.services.accounts.external.ExternalTransferTypeService;
import nl.strohalm.cyclos.services.transactions.TransactionContext;
import nl.strohalm.cyclos.services.transfertypes.TransferTypeService;
import nl.strohalm.cyclos.utils.ActionHelper;
import nl.strohalm.cyclos.utils.RequestHelper;
import nl.strohalm.cyclos.utils.binding.BeanBinder;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.binding.PropertyBinder;
import nl.strohalm.cyclos.utils.conversion.IdConverter;
import nl.strohalm.cyclos.utils.validation.ValidationException;

import org.apache.struts.action.ActionForward;

/**
 * Action used to edit a External Transfer Type
 * @author Lucas Geiss
 */
public class EditExternalTransferTypeAction extends BaseFormAction {

    private ExternalTransferTypeService      externalTransferTypeService;
    private DataBinder<ExternalTransferType> dataBinder;
    private TransferTypeService              transferTypeService;
    private ExternalAccountService           externalAccountService;

    @Inject
    public void setExternalAccountService(final ExternalAccountService externalAccountService) {
        this.externalAccountService = externalAccountService;
    }

    @Inject
    public void setExternalTransferTypeService(final ExternalTransferTypeService externalTransferTypeService) {
        this.externalTransferTypeService = externalTransferTypeService;
    }

    @Inject
    public void setTransferTypeService(final TransferTypeService transferTypeService) {
        this.transferTypeService = transferTypeService;
    }

    @Override
    protected ActionForward handleSubmit(final ActionContext context) throws Exception {
        final EditExternalTransferTypeForm form = context.getForm();
        ExternalTransferType externalTransferType = getDataBinder().readFromString(form.getExternalTransferType());
        final boolean isInsert = externalTransferType.isTransient();
        externalTransferType = externalTransferTypeService.save(externalTransferType);
        if (isInsert) {
            context.sendMessage("externalTransferType.inserted");
        } else {
            context.sendMessage("externalTransferType.modified");
        }
        return ActionHelper.redirectWithParam(context.getRequest(), context.getSuccessForward(), "externalAccountId", externalTransferType.getAccount().getId());
    }

    @Override
    protected void prepareForm(final ActionContext context) throws Exception {
        final HttpServletRequest request = context.getRequest();
        final EditExternalTransferTypeForm form = context.getForm();
        final long id = form.getExternalTransferTypeId();
        final boolean isInsert = id <= 0L;
        boolean editable = permissionService.hasPermission(AdminSystemPermission.EXTERNAL_ACCOUNTS_MANAGE);
        ExternalTransferType externalTransferType;
        ExternalAccount externalAccount;

        if (isInsert) {
            externalTransferType = new ExternalTransferType();
            final long account = form.getAccount();
            if (account <= 0L) {
                throw new ValidationException();
            }
            externalAccount = externalAccountService.load(account);
            externalTransferType.setAccount(externalAccount);
            editable = true;
        } else {
            externalTransferType = externalTransferTypeService.load(id, ExternalTransferType.Relationships.ACCOUNT);
            externalAccount = externalTransferType.getAccount();

        }
        getDataBinder().writeAsString(form.getExternalTransferType(), externalTransferType);
        RequestHelper.storeEnum(request, ExternalTransferType.Action.class, "actions");

        final TransferTypeQuery toMemberQuery = new TransferTypeQuery();
        toMemberQuery.setContext(TransactionContext.AUTOMATIC);
        toMemberQuery.setFromAccountType(externalAccount.getSystemAccountType());
        toMemberQuery.setToAccountType(externalAccount.getMemberAccountType());
        final List<TransferType> toMemberTransferTypes = transferTypeService.search(toMemberQuery);
        request.setAttribute("toMemberTransferTypes", toMemberTransferTypes);

        final TransferTypeQuery toSystemQuery = new TransferTypeQuery();
        toSystemQuery.setContext(TransactionContext.AUTOMATIC);
        toSystemQuery.setFromAccountType(externalAccount.getMemberAccountType());
        toSystemQuery.setToAccountType(externalAccount.getSystemAccountType());
        final List<TransferType> toSystemTransferTypes = transferTypeService.search(toSystemQuery);
        request.setAttribute("toSystemTransferTypes", toSystemTransferTypes);

        request.setAttribute("externalTransferType", externalTransferType);
        request.setAttribute("isInsert", isInsert);
        request.setAttribute("editable", editable);
        request.setAttribute("account", externalAccount);
    }

    @Override
    protected void validateForm(final ActionContext context) {
        final EditExternalTransferTypeForm form = context.getForm();
        final ExternalTransferType externalTransferType = getDataBinder().readFromString(form.getExternalTransferType());
        externalTransferTypeService.validate(externalTransferType);
    }

    private DataBinder<ExternalTransferType> getDataBinder() {
        if (dataBinder == null) {

            final BeanBinder<ExternalTransferType> binder = BeanBinder.instance(ExternalTransferType.class);
            binder.registerBinder("id", PropertyBinder.instance(Long.class, "id", IdConverter.instance()));
            binder.registerBinder("name", PropertyBinder.instance(String.class, "name"));
            binder.registerBinder("code", PropertyBinder.instance(String.class, "code"));
            binder.registerBinder("description", PropertyBinder.instance(String.class, "description"));
            binder.registerBinder("action", PropertyBinder.instance(Action.class, "action"));
            binder.registerBinder("account", PropertyBinder.instance(ExternalAccount.class, "account"));
            binder.registerBinder("transferType", PropertyBinder.instance(TransferType.class, "transferType"));

            dataBinder = binder;
        }
        return dataBinder;
    }
}
