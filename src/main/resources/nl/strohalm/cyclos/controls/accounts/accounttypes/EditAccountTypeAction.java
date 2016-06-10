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
package nl.strohalm.cyclos.controls.accounts.accounttypes;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.access.AdminSystemPermission;
import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseFormAction;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.AccountType;
import nl.strohalm.cyclos.entities.accounts.Currency;
import nl.strohalm.cyclos.entities.accounts.MemberAccountType;
import nl.strohalm.cyclos.entities.accounts.SystemAccountType;
import nl.strohalm.cyclos.entities.accounts.fees.account.AccountFee;
import nl.strohalm.cyclos.entities.accounts.fees.account.AccountFeeQuery;
import nl.strohalm.cyclos.entities.accounts.transactions.PaymentFilterQuery;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferType;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferTypeQuery;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.entities.settings.events.LocalSettingsChangeListener;
import nl.strohalm.cyclos.entities.settings.events.LocalSettingsEvent;
import nl.strohalm.cyclos.services.accountfees.AccountFeeService;
import nl.strohalm.cyclos.services.accounts.AccountTypeService;
import nl.strohalm.cyclos.services.accounts.CurrencyService;
import nl.strohalm.cyclos.services.transactions.TransactionContext;
import nl.strohalm.cyclos.services.transfertypes.PaymentFilterService;
import nl.strohalm.cyclos.services.transfertypes.TransferTypeService;
import nl.strohalm.cyclos.utils.ActionHelper;
import nl.strohalm.cyclos.utils.RelationshipHelper;
import nl.strohalm.cyclos.utils.RequestHelper;
import nl.strohalm.cyclos.utils.binding.BeanBinder;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.binding.PropertyBinder;
import nl.strohalm.cyclos.utils.conversion.IdConverter;
import nl.strohalm.cyclos.utils.validation.RequiredError;
import nl.strohalm.cyclos.utils.validation.ValidationException;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForward;

/**
 * Action used to edit an account type
 * @author luis
 */
public class EditAccountTypeAction extends BaseFormAction implements LocalSettingsChangeListener {

    private CurrencyService                                            currencyService;
    private AccountTypeService                                         accountTypeService;
    private TransferTypeService                                        transferTypeService;
    private AccountFeeService                                          accountFeeService;
    private PaymentFilterService                                       paymentFilterService;
    private Map<AccountType.Nature, DataBinder<? extends AccountType>> dataBinders;
    private ReadWriteLock                                              lock = new ReentrantReadWriteLock(true);

    public AccountFeeService getAccountFeeService() {
        return accountFeeService;
    }

    public AccountTypeService getAccountTypeService() {
        return accountTypeService;
    }

    public DataBinder<? extends AccountType> getDataBinder(final AccountType.Nature nature) {
        try {
            lock.readLock().lock();
            if (dataBinders == null) {
                final HashMap<AccountType.Nature, DataBinder<? extends AccountType>> temp = new HashMap<AccountType.Nature, DataBinder<? extends AccountType>>();
                final LocalSettings localSettings = settingsService.getLocalSettings();

                final BeanBinder<SystemAccountType> systemBinder = BeanBinder.instance(SystemAccountType.class);
                initBasic(systemBinder);
                systemBinder.registerBinder("creditLimit", PropertyBinder.instance(BigDecimal.class, "creditLimit", localSettings.getNumberConverter()));
                systemBinder.registerBinder("upperCreditLimit", PropertyBinder.instance(BigDecimal.class, "upperCreditLimit", localSettings.getNumberConverter()));
                temp.put(AccountType.Nature.SYSTEM, systemBinder);

                final BeanBinder<MemberAccountType> memberBinder = BeanBinder.instance(MemberAccountType.class);
                initBasic(memberBinder);
                temp.put(AccountType.Nature.MEMBER, memberBinder);
                dataBinders = temp;
            }
            return dataBinders.get(nature);
        } finally {
            lock.readLock().unlock();
        }
    }

    public PaymentFilterService getPaymentFilterService() {
        return paymentFilterService;
    }

    public TransferTypeService getTransferTypeService() {
        return transferTypeService;
    }

    @Override
    public void onLocalSettingsUpdate(final LocalSettingsEvent event) {
        try {
            lock.writeLock().lock();
            dataBinders = null;
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Inject
    public void setAccountFeeService(final AccountFeeService accountFeeService) {
        this.accountFeeService = accountFeeService;
    }

    @Inject
    public void setAccountTypeService(final AccountTypeService accountTypeService) {
        this.accountTypeService = accountTypeService;
    }

    @Inject
    public void setCurrencyService(final CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @Inject
    public void setPaymentFilterService(final PaymentFilterService paymentFilterService) {
        this.paymentFilterService = paymentFilterService;
    }

    @Inject
    public void setTransferTypeService(final TransferTypeService transferTypeService) {
        this.transferTypeService = transferTypeService;
    }

    @Override
    protected ActionForward handleSubmit(final ActionContext context) throws Exception {
        final EditAccountTypeForm form = context.getForm();
        AccountType accountType = resolveAccountType(form);
        final boolean isInsert = accountType.getId() == null;
        accountType = accountTypeService.save(accountType);
        context.sendMessage(isInsert ? "accountType.inserted" : "accountType.modified");
        return ActionHelper.redirectWithParam(context.getRequest(), context.getSuccessForward(), "accountTypeId", accountType.getId());
    }

    @Override
    protected void prepareForm(final ActionContext context) throws Exception {
        final HttpServletRequest request = context.getRequest();
        final EditAccountTypeForm form = context.getForm();
        final long id = form.getAccountTypeId();
        final boolean isInsert = id <= 0L;
        final boolean editable = permissionService.hasPermission(AdminSystemPermission.ACCOUNTS_MANAGE);
        boolean isSystem = false;
        if (isInsert) {
            RequestHelper.storeEnum(request, AccountType.Nature.class, "natures");
            RequestHelper.storeEnum(request, AccountType.LimitType.class, "limitTypes");
        } else {
            final AccountType accountType = accountTypeService.load(id);
            isSystem = accountType instanceof SystemAccountType;
            request.setAttribute("accountType", accountType);
            getDataBinder(accountType.getNature()).writeAsString(form.getAccountType(), accountType);

            final TransferTypeQuery ttQuery = new TransferTypeQuery();
            ttQuery.fetch(TransferType.Relationships.FROM, TransferType.Relationships.TO);
            ttQuery.setContext(TransactionContext.ANY);
            ttQuery.setFromAccountType(accountType);
            request.setAttribute("transferTypes", transferTypeService.search(ttQuery));

            if (!isSystem) {
                final AccountFeeQuery feeQuery = new AccountFeeQuery();
                final Set<Relationship> fetch = new HashSet<Relationship>();
                fetch.add(RelationshipHelper.nested(AccountFee.Relationships.ACCOUNT_TYPE, AccountType.Relationships.CURRENCY));
                feeQuery.setFetch(fetch);
                feeQuery.setAccountType(accountType);
                feeQuery.setReturnDisabled(true);
                request.setAttribute("accountFees", accountFeeService.search(feeQuery));
            }

            final PaymentFilterQuery filterQuery = new PaymentFilterQuery();
            filterQuery.setAccountType(accountType);
            request.setAttribute("paymentFilters", paymentFilterService.search(filterQuery));

        }
        request.setAttribute("currencies", currencyService.listAll());
        request.setAttribute("isInsert", isInsert);
        request.setAttribute("isSystem", isSystem);
        request.setAttribute("editable", editable);
    }

    @Override
    protected void validateForm(final ActionContext context) {
        final EditAccountTypeForm form = context.getForm();
        ValidationException val;
        try {
            accountTypeService.validate(resolveAccountType(form));
            val = new ValidationException();
            val.setPropertyKey("creditLimit", "account.creditLimit");
        } catch (final ValidationException e) {
            val = e;
        }
        AccountType.Nature nature;
        try {
            nature = AccountType.Nature.valueOf(form.getAccountType("nature").toString());
        } catch (final Exception e) {
            throw new ValidationException();
        }
        if (nature == AccountType.Nature.SYSTEM && AccountType.LimitType.LIMITED.name().equals(form.getAccountType("limitType")) && StringUtils.isEmpty((String) form.getAccountType("creditLimit"))) {
            val.addPropertyError("creditLimit", new RequiredError());
        }
        val.throwIfHasErrors();
    }

    private void initBasic(final BeanBinder<? extends AccountType> binder) {
        binder.registerBinder("id", PropertyBinder.instance(Long.class, "id", IdConverter.instance()));
        binder.registerBinder("name", PropertyBinder.instance(String.class, "name"));
        binder.registerBinder("description", PropertyBinder.instance(String.class, "description"));
        binder.registerBinder("currency", PropertyBinder.instance(Currency.class, "currency"));
    }

    private AccountType resolveAccountType(final EditAccountTypeForm form) {
        final long id = form.getAccountTypeId();
        AccountType.Nature nature;
        if (id <= 0L) {
            try {
                nature = AccountType.Nature.valueOf(form.getAccountType("nature").toString());
            } catch (final Exception e) {
                throw new ValidationException();
            }
        } else {
            nature = accountTypeService.load(id).getNature();
        }
        return getDataBinder(nature).readFromString(form.getAccountType());
    }
}
