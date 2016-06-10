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
package nl.strohalm.cyclos.controls.accounts.paymentfilters;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseFormAction;
import nl.strohalm.cyclos.entities.accounts.AccountType;
import nl.strohalm.cyclos.entities.accounts.MemberAccountType;
import nl.strohalm.cyclos.entities.accounts.transactions.PaymentFilter;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferType;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferTypeQuery;
import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.entities.groups.GroupQuery;
import nl.strohalm.cyclos.services.accounts.AccountTypeService;
import nl.strohalm.cyclos.services.transactions.TransactionContext;
import nl.strohalm.cyclos.services.transfertypes.PaymentFilterService;
import nl.strohalm.cyclos.services.transfertypes.TransferTypeService;
import nl.strohalm.cyclos.utils.ActionHelper;
import nl.strohalm.cyclos.utils.binding.BeanBinder;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.binding.PropertyBinder;
import nl.strohalm.cyclos.utils.binding.SimpleCollectionBinder;
import nl.strohalm.cyclos.utils.conversion.IdConverter;
import nl.strohalm.cyclos.utils.conversion.ReferenceConverter;
import nl.strohalm.cyclos.utils.validation.ValidationException;

import org.apache.struts.action.ActionForward;

/**
 * Action used to edit a payment filter
 * @author luis
 */
public class EditPaymentFilterAction extends BaseFormAction {

    private PaymentFilterService      paymentFilterService;
    private TransferTypeService       transferTypeService;
    private AccountTypeService        accountTypeService;
    private DataBinder<PaymentFilter> dataBinder;

    public AccountTypeService getAccountTypeService() {
        return accountTypeService;
    }

    public DataBinder<PaymentFilter> getDataBinder() {
        if (dataBinder == null) {
            final BeanBinder<PaymentFilter> binder = BeanBinder.instance(PaymentFilter.class);
            binder.registerBinder("id", PropertyBinder.instance(Long.class, "id", IdConverter.instance()));
            binder.registerBinder("accountType", PropertyBinder.instance(AccountType.class, "accountType", ReferenceConverter.instance(AccountType.class)));
            binder.registerBinder("name", PropertyBinder.instance(String.class, "name"));
            binder.registerBinder("description", PropertyBinder.instance(String.class, "description"));
            binder.registerBinder("showInAccountHistory", PropertyBinder.instance(Boolean.TYPE, "showInAccountHistory"));
            binder.registerBinder("showInReports", PropertyBinder.instance(Boolean.TYPE, "showInReports"));
            binder.registerBinder("groups", SimpleCollectionBinder.instance(Group.class, "groups"));
            binder.registerBinder("transferTypes", SimpleCollectionBinder.instance(TransferType.class, "transferTypes"));
            dataBinder = binder;
        }
        return dataBinder;
    }

    public PaymentFilterService getPaymentFilterService() {
        return paymentFilterService;
    }

    public TransferTypeService getTransferTypeService() {
        return transferTypeService;
    }

    @Inject
    public void setAccountTypeService(final AccountTypeService accountTypeService) {
        this.accountTypeService = accountTypeService;
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
        final EditPaymentFilterForm form = context.getForm();
        PaymentFilter paymentFilter = getDataBinder().readFromString(form.getPaymentFilter());
        final boolean isInsert = paymentFilter.getId() == null;
        paymentFilter = paymentFilterService.save(paymentFilter);
        context.sendMessage(isInsert ? "paymentFilter.inserted" : "paymentFilter.modified");
        final Map<String, Object> params = new HashMap<String, Object>();
        params.put("accountTypeId", form.getAccountTypeId());
        params.put("paymentFilterId", paymentFilter.getId());
        return ActionHelper.redirectWithParams(context.getRequest(), context.getSuccessForward(), params);
    }

    @Override
    protected void prepareForm(final ActionContext context) throws Exception {
        final HttpServletRequest request = context.getRequest();
        final EditPaymentFilterForm form = context.getForm();
        final long accountTypeId = form.getAccountTypeId();
        if (accountTypeId <= 0L) {
            throw new ValidationException();
        }
        final AccountType accountType = accountTypeService.load(accountTypeId);
        final long id = form.getPaymentFilterId();
        final boolean isInsert = id <= 0L;
        if (!isInsert) {
            final PaymentFilter paymentFilter = paymentFilterService.load(id, PaymentFilter.Relationships.GROUPS, PaymentFilter.Relationships.TRANSFER_TYPES);
            request.setAttribute("paymentFilter", paymentFilter);
            getDataBinder().writeAsString(form.getPaymentFilter(), paymentFilter);
        }

        // Get the groups that may access this payment filter
        final GroupQuery query = new GroupQuery();
        query.setStatus(Group.Status.NORMAL);
        if (accountType.getNature() == AccountType.Nature.SYSTEM) {
            query.setNatures(Group.Nature.ADMIN);
        } else {
            query.setNatures(Group.Nature.MEMBER, Group.Nature.BROKER);
            query.setMemberAccountType((MemberAccountType) accountType);
        }

        final Collection<? extends Group> groups = groupService.search(query);
        // Get the transfer types that may be in this payment filter
        final TransferTypeQuery ttQuery = new TransferTypeQuery();
        ttQuery.setContext(TransactionContext.ANY);
        ttQuery.setFromOrToAccountType(accountType);
        final List<TransferType> transferTypes = transferTypeService.search(ttQuery);

        request.setAttribute("isInsert", isInsert);
        request.setAttribute("accountType", accountType);
        request.setAttribute("groups", groups);
        request.setAttribute("transferTypes", transferTypes);
    }

    @Override
    protected void validateForm(final ActionContext context) {
        final EditPaymentFilterForm form = context.getForm();
        final PaymentFilter paymentFilter = getDataBinder().readFromString(form.getPaymentFilter());
        paymentFilterService.validate(paymentFilter);
    }

}
