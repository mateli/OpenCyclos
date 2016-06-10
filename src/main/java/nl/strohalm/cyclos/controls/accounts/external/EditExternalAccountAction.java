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
import nl.strohalm.cyclos.entities.accounts.AccountTypeQuery;
import nl.strohalm.cyclos.entities.accounts.MemberAccountType;
import nl.strohalm.cyclos.entities.accounts.SystemAccountType;
import nl.strohalm.cyclos.entities.accounts.external.ExternalAccount;
import nl.strohalm.cyclos.services.accounts.AccountTypeService;
import nl.strohalm.cyclos.services.accounts.MemberAccountTypeQuery;
import nl.strohalm.cyclos.services.accounts.SystemAccountTypeQuery;
import nl.strohalm.cyclos.services.accounts.external.ExternalAccountService;
import nl.strohalm.cyclos.utils.ActionHelper;
import nl.strohalm.cyclos.utils.binding.BeanBinder;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.binding.PropertyBinder;
import nl.strohalm.cyclos.utils.conversion.IdConverter;

import org.apache.struts.action.ActionForward;

/**
 * Action used to edit an external account
 * @author Lucas Geiss
 */
public class EditExternalAccountAction extends BaseFormAction {

    private ExternalAccountService      externalAccountService;
    private DataBinder<ExternalAccount> dataBinder;
    private AccountTypeService          accountTypeService;

    @Inject
    public void setAccountTypeService(final AccountTypeService accountTypeService) {
        this.accountTypeService = accountTypeService;
    }

    @Inject
    public void setExternalAccountService(final ExternalAccountService externalAccountService) {
        this.externalAccountService = externalAccountService;
    }

    @Override
    protected ActionForward handleSubmit(final ActionContext context) throws Exception {
        final EditExternalAccountForm form = context.getForm();
        ExternalAccount externalAccount = getDataBinder().readFromString(form.getExternalAccount());
        final boolean isInsert = externalAccount.isTransient();
        externalAccount = externalAccountService.save(externalAccount);
        if (isInsert) {
            context.sendMessage("externalAccount.inserted");
        } else {
            context.sendMessage("externalAccount.modified");
        }
        return ActionHelper.redirectWithParam(context.getRequest(), context.getSuccessForward(), "externalAccountId", externalAccount.getId());
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void prepareForm(final ActionContext context) throws Exception {
        final HttpServletRequest request = context.getRequest();
        final EditExternalAccountForm form = context.getForm();

        final long id = form.getExternalAccountId();
        final boolean isInsert = id <= 0L;
        boolean editable = permissionService.hasPermission(AdminSystemPermission.EXTERNAL_ACCOUNTS_MANAGE);
        ExternalAccount externalAccount;
        if (isInsert) {
            externalAccount = new ExternalAccount();
            editable = true;
        } else {
            externalAccount = externalAccountService.load(id);
        }
        getDataBinder().writeAsString(form.getExternalAccount(), externalAccount);
        // getDataBinder().writeAsString(form.getExternalTransferType(), externalTransferType);
        final AccountTypeQuery querySystem = new SystemAccountTypeQuery();
        final AccountTypeQuery queryMember = new MemberAccountTypeQuery();
        final List<SystemAccountType> accountSystems = (List<SystemAccountType>) accountTypeService.search(querySystem);
        final List<MemberAccountType> accountMembers = (List<MemberAccountType>) accountTypeService.search(queryMember);

        request.setAttribute("systemAccounts", accountSystems);
        request.setAttribute("memberAccounts", accountMembers);
        request.setAttribute("externalAccount", externalAccount);
        request.setAttribute("isInsert", isInsert);
        request.setAttribute("editable", editable);
    }

    @Override
    protected void validateForm(final ActionContext context) {
        final EditExternalAccountForm form = context.getForm();
        final ExternalAccount externalAccount = getDataBinder().readFromString(form.getExternalAccount());
        externalAccountService.validate(externalAccount);
    }

    private DataBinder<ExternalAccount> getDataBinder() {
        if (dataBinder == null) {
            final BeanBinder<ExternalAccount> binder = BeanBinder.instance(ExternalAccount.class);
            binder.registerBinder("id", PropertyBinder.instance(Long.class, "id", IdConverter.instance()));
            binder.registerBinder("name", PropertyBinder.instance(String.class, "name"));
            binder.registerBinder("systemAccountType", PropertyBinder.instance(SystemAccountType.class, "systemAccountType"));
            binder.registerBinder("memberAccountType", PropertyBinder.instance(MemberAccountType.class, "memberAccountType"));
            binder.registerBinder("description", PropertyBinder.instance(String.class, "description"));
            dataBinder = binder;
        }
        return dataBinder;
    }
}
