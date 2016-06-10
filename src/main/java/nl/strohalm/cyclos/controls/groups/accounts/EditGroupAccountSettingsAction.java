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
package nl.strohalm.cyclos.controls.groups.accounts;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseFormAction;
import nl.strohalm.cyclos.entities.accounts.MemberAccountType;
import nl.strohalm.cyclos.entities.accounts.MemberGroupAccountSettings;
import nl.strohalm.cyclos.entities.accounts.SystemAccountOwner;
import nl.strohalm.cyclos.entities.accounts.loans.Loan;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferType;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferTypeQuery;
import nl.strohalm.cyclos.entities.groups.AdminGroup;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.entities.settings.events.LocalSettingsChangeListener;
import nl.strohalm.cyclos.entities.settings.events.LocalSettingsEvent;
import nl.strohalm.cyclos.services.accounts.AccountTypeService;
import nl.strohalm.cyclos.services.accounts.MemberAccountTypeQuery;
import nl.strohalm.cyclos.services.transactions.TransactionContext;
import nl.strohalm.cyclos.services.transfertypes.TransferTypeService;
import nl.strohalm.cyclos.utils.ActionHelper;
import nl.strohalm.cyclos.utils.binding.BeanBinder;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.binding.PropertyBinder;
import nl.strohalm.cyclos.utils.conversion.IdConverter;
import nl.strohalm.cyclos.utils.conversion.ReferenceConverter;
import nl.strohalm.cyclos.utils.validation.ValidationException;

import org.apache.struts.action.ActionForward;

/**
 * Action used to edit a group account settings or associate a new account to a group
 * @author luis
 */
public class EditGroupAccountSettingsAction extends BaseFormAction implements LocalSettingsChangeListener {

    private AccountTypeService                     accountTypeService;
    private TransferTypeService                    transferTypeService;
    private DataBinder<MemberGroupAccountSettings> dataBinder;

    public AccountTypeService getAccountTypeService() {
        return accountTypeService;
    }

    public DataBinder<MemberGroupAccountSettings> getDataBinder() {
        if (dataBinder == null) {

            final LocalSettings localSettings = settingsService.getLocalSettings();

            final BeanBinder<MemberGroupAccountSettings> binder = BeanBinder.instance(MemberGroupAccountSettings.class);
            binder.registerBinder("id", PropertyBinder.instance(Long.class, "id", IdConverter.instance()));
            binder.registerBinder("group", PropertyBinder.instance(MemberGroup.class, "group", ReferenceConverter.instance(MemberGroup.class)));
            binder.registerBinder("accountType", PropertyBinder.instance(MemberAccountType.class, "accountType", ReferenceConverter.instance(MemberAccountType.class)));
            binder.registerBinder("default", PropertyBinder.instance(Boolean.TYPE, "default"));
            binder.registerBinder("transactionPasswordRequired", PropertyBinder.instance(Boolean.TYPE, "transactionPasswordRequired"));
            binder.registerBinder("defaultCreditLimit", PropertyBinder.instance(BigDecimal.class, "defaultCreditLimit", localSettings.getNumberConverter().negativeToAbsolute()));
            binder.registerBinder("defaultUpperCreditLimit", PropertyBinder.instance(BigDecimal.class, "defaultUpperCreditLimit", localSettings.getNumberConverter()));
            binder.registerBinder("initialCredit", PropertyBinder.instance(BigDecimal.class, "initialCredit", localSettings.getNumberConverter()));
            binder.registerBinder("initialCreditTransferType", PropertyBinder.instance(TransferType.class, "initialCreditTransferType", ReferenceConverter.instance(TransferType.class)));
            binder.registerBinder("lowUnits", PropertyBinder.instance(BigDecimal.class, "lowUnits", localSettings.getNumberConverter()));
            binder.registerBinder("lowUnitsMessage", PropertyBinder.instance(String.class, "lowUnitsMessage"));
            binder.registerBinder("hideWhenNoCreditLimit", PropertyBinder.instance(Boolean.TYPE, "hideWhenNoCreditLimit"));

            dataBinder = binder;
        }
        return dataBinder;
    }

    public TransferTypeService getTransferTypeService() {
        return transferTypeService;
    }

    @Override
    public void onLocalSettingsUpdate(final LocalSettingsEvent event) {
        dataBinder = null;
    }

    @Inject
    public void setAccountTypeService(final AccountTypeService accountTypeService) {
        this.accountTypeService = accountTypeService;
    }

    @Inject
    public void setTransferTypeService(final TransferTypeService transferTypeService) {
        this.transferTypeService = transferTypeService;
    }

    @Override
    protected ActionForward handleSubmit(final ActionContext context) throws Exception {
        final EditGroupAccountSettingsForm form = context.getForm();
        MemberGroupAccountSettings groupAccountSettings = getDataBinder().readFromString(form.getSetting());
        final boolean isInsert = groupAccountSettings.getId() == null;
        if (isInsert) {
            groupAccountSettings = groupService.insertAccountSettings(groupAccountSettings);
            context.sendMessage("group.account.inserted");
        } else {
            groupAccountSettings = groupService.updateAccountSettings(groupAccountSettings, form.isUpdateAccountLimits());
            context.sendMessage("group.account.modified");
        }
        final Map<String, Object> params = new HashMap<String, Object>();
        params.put("groupId", groupAccountSettings.getGroup().getId());
        params.put("accountTypeId", groupAccountSettings.getAccountType().getId());
        return ActionHelper.redirectWithParams(context.getRequest(), context.getSuccessForward(), params);
    }

    @Override
    protected void prepareForm(final ActionContext context) throws Exception {
        final HttpServletRequest request = context.getRequest();
        final EditGroupAccountSettingsForm form = context.getForm();
        final long groupId = form.getGroupId();
        MemberGroup group;
        try {
            group = groupService.load(groupId);
        } catch (final Exception e) {
            throw new ValidationException();
        }
        boolean editable = false;
        final long accountTypeId = form.getAccountTypeId();
        final boolean isInsert = accountTypeId <= 0L;
        MemberGroupAccountSettings settings;
        if (isInsert) {
            settings = new MemberGroupAccountSettings();

            settings.setTransactionPasswordRequired(group.getBasicSettings().getTransactionPassword().isUsed());

            final MemberAccountTypeQuery atQuery = new MemberAccountTypeQuery();
            atQuery.setNotRelatedToGroup(group);
            request.setAttribute("possibleAccountTypes", accountTypeService.search(atQuery));
            editable = true;
        } else {
            settings = groupService.loadAccountSettings(groupId, accountTypeId, MemberGroupAccountSettings.Relationships.ACCOUNT_TYPE, MemberGroupAccountSettings.Relationships.INITIAL_CREDIT_TRANSFER_TYPE);

            AdminGroup adminGroup = context.getGroup();
            adminGroup = groupService.load(adminGroup.getId(), AdminGroup.Relationships.MANAGES_GROUPS);
            if (adminGroup.getManagesGroups().contains(group)) {
                editable = true;
            }

            final TransferTypeQuery ttQuery = new TransferTypeQuery();
            ttQuery.setContext(TransactionContext.ANY);
            ttQuery.setFromOwner(SystemAccountOwner.instance());
            ttQuery.setToAccountType(settings.getAccountType());
            final List<TransferType> transferTypes = transferTypeService.search(ttQuery);
            // Remove the loan types
            for (final Iterator<TransferType> iterator = transferTypes.iterator(); iterator.hasNext();) {
                final TransferType transferType = iterator.next();
                final Loan.Type loanType = transferType.getLoan() == null ? null : transferType.getLoan().getType();
                // Non-loan (loanType == null) or simple (loanType == SINGLE_PAYMENT) are allowed
                if (loanType == Loan.Type.MULTI_PAYMENT || loanType == Loan.Type.WITH_INTEREST) {
                    iterator.remove();
                }
            }
            request.setAttribute("possibleTransferTypes", transferTypes);

            // Counts how many initial credits are pending
            final int pendingAccounts = groupService.countPendingAccounts(settings.getGroup(), settings.getAccountType());
            request.setAttribute("pendingAccounts", pendingAccounts);
        }
        getDataBinder().writeAsString(form.getSetting(), settings);
        request.setAttribute("settings", settings);
        request.setAttribute("group", group);
        request.setAttribute("isInsert", isInsert);
        request.setAttribute("editable", editable);
    }

    @Override
    protected void validateForm(final ActionContext context) {
        final EditGroupAccountSettingsForm form = context.getForm();
        final MemberGroupAccountSettings groupAccountSettings = getDataBinder().readFromString(form.getSetting());
        groupService.validate(groupAccountSettings);
    }
}
