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
package nl.strohalm.cyclos.controls.accounts.guarantees.types;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.access.AdminSystemPermission;
import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseFormAction;
import nl.strohalm.cyclos.entities.accounts.AccountType;
import nl.strohalm.cyclos.entities.accounts.Currency;
import nl.strohalm.cyclos.entities.accounts.guarantees.GuaranteeType;
import nl.strohalm.cyclos.entities.accounts.guarantees.GuaranteeType.FeeType;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferType;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferTypeQuery;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.services.accounts.CurrencyService;
import nl.strohalm.cyclos.services.accounts.guarantees.GuaranteeTypeFeeVO;
import nl.strohalm.cyclos.services.accounts.guarantees.GuaranteeTypeService;
import nl.strohalm.cyclos.services.transactions.TransactionContext;
import nl.strohalm.cyclos.services.transfertypes.TransferTypeService;
import nl.strohalm.cyclos.utils.ActionHelper;
import nl.strohalm.cyclos.utils.RequestHelper;
import nl.strohalm.cyclos.utils.TimePeriod;
import nl.strohalm.cyclos.utils.binding.BeanBinder;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.binding.DataBinderHelper;
import nl.strohalm.cyclos.utils.binding.PropertyBinder;
import nl.strohalm.cyclos.utils.conversion.IdConverter;

import org.apache.struts.action.ActionForward;

public class EditGuaranteeTypeAction extends BaseFormAction {

    private interface StringTransformer {
        String transform(Object obj);
    }

    private GuaranteeTypeService      guaranteeTypeService;
    private CurrencyService           currencyService;
    private TransferTypeService       transferTypeService;

    private DataBinder<GuaranteeType> dataBinderGuaranteeType;

    public DataBinder<GuaranteeType> getDataBinderGuaranteeType() {
        if (dataBinderGuaranteeType == null) {
            final LocalSettings localSettings = settingsService.getLocalSettings();
            final BeanBinder<GuaranteeType> binder = BeanBinder.instance(GuaranteeType.class);
            binder.registerBinder("id", PropertyBinder.instance(Long.class, "id", IdConverter.instance()));
            binder.registerBinder("name", PropertyBinder.instance(String.class, "name"));
            binder.registerBinder("description", PropertyBinder.instance(String.class, "description"));
            binder.registerBinder("model", PropertyBinder.instance(GuaranteeType.Model.class, "model"));
            binder.registerBinder("authorizedBy", PropertyBinder.instance(GuaranteeType.AuthorizedBy.class, "authorizedBy"));
            binder.registerBinder("creditFeePayer", PropertyBinder.instance(GuaranteeType.FeePayer.class, "creditFeePayer"));
            binder.registerBinder("issueFeePayer", PropertyBinder.instance(GuaranteeType.FeePayer.class, "issueFeePayer"));
            binder.registerBinder("enabled", PropertyBinder.instance(Boolean.TYPE, "enabled"));
            // binder.registerBinder("allowLoanPaymentSetup", PropertyBinder.instance(Boolean.class, "allowLoanPaymentSetup"));
            binder.registerBinder("paymentObligationPeriod", DataBinderHelper.timePeriodBinder("paymentObligationPeriod"));
            binder.registerBinder("pendingGuaranteeExpiration", DataBinderHelper.timePeriodBinder("pendingGuaranteeExpiration"));
            binder.registerBinder("currency", PropertyBinder.instance(Currency.class, "currency"));
            binder.registerBinder("creditFeeTransferType", PropertyBinder.instance(TransferType.class, "creditFeeTransferType"));
            binder.registerBinder("issueFeeTransferType", PropertyBinder.instance(TransferType.class, "issueFeeTransferType"));
            binder.registerBinder("forwardTransferType", PropertyBinder.instance(TransferType.class, "forwardTransferType"));
            binder.registerBinder("loanTransferType", PropertyBinder.instance(TransferType.class, "loanTransferType"));

            final BeanBinder<GuaranteeTypeFeeVO> issueFeeBinder = BeanBinder.instance(GuaranteeTypeFeeVO.class, "issueFee");
            issueFeeBinder.registerBinder("type", PropertyBinder.instance(FeeType.class, "type"));
            issueFeeBinder.registerBinder("fee", PropertyBinder.instance(BigDecimal.class, "fee", localSettings.getNumberConverter()));
            issueFeeBinder.registerBinder("readonly", PropertyBinder.instance(Boolean.TYPE, "readonly"));
            binder.registerBinder("issueFee", issueFeeBinder);

            final BeanBinder<GuaranteeTypeFeeVO> creditFeeBinder = BeanBinder.instance(GuaranteeTypeFeeVO.class, "creditFee");
            creditFeeBinder.registerBinder("type", PropertyBinder.instance(FeeType.class, "type"));
            creditFeeBinder.registerBinder("fee", PropertyBinder.instance(BigDecimal.class, "fee", localSettings.getNumberConverter()));
            creditFeeBinder.registerBinder("readonly", PropertyBinder.instance(Boolean.TYPE, "readonly"));
            binder.registerBinder("creditFee", creditFeeBinder);
            dataBinderGuaranteeType = binder;
        }
        return dataBinderGuaranteeType;
    }

    @Inject
    public void setCurrencyService(final CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @Inject
    public void setGuaranteeTypeService(final GuaranteeTypeService guaranteeTypeService) {
        this.guaranteeTypeService = guaranteeTypeService;
    }

    @Inject
    public void setTransferTypeService(final TransferTypeService transferTypeService) {
        this.transferTypeService = transferTypeService;
    }

    /**
     * Handles form submission, returning the ActionForward
     */
    @Override
    protected ActionForward handleSubmit(final ActionContext context) throws Exception {
        final EditGuaranteeTypeForm form = context.getForm();
        GuaranteeType guaranteeType = getDataBinderGuaranteeType().readFromString(form.getGuaranteeType());
        final boolean isInsert = guaranteeType.isTransient();
        guaranteeType = guaranteeTypeService.save(guaranteeType);
        if (isInsert) {
            context.sendMessage("guaranteeType.inserted");
        } else {
            context.sendMessage("guaranteeType.updated");
        }
        return ActionHelper.redirectWithParam(context.getRequest(), context.getSuccessForward(), "guaranteeTypeId", guaranteeType.getId());
    }

    /**
     * Method use to prepare a form for being displayed
     */
    @Override
    protected void prepareForm(final ActionContext context) throws Exception {
        final HttpServletRequest request = context.getRequest();
        final EditGuaranteeTypeForm form = context.getForm();
        final Long id = form.getGuaranteeTypeId();

        final boolean isInsert = id == null || id <= 0L;
        if (!isInsert) {
            final GuaranteeType guaranteeType = guaranteeTypeService.load(id, GuaranteeType.Relationships.CURRENCY, GuaranteeType.Relationships.LOAN_TRANSFER_TYPE, GuaranteeType.Relationships.CREDIT_FEE_TRANSFER_TYPE, GuaranteeType.Relationships.ISSUE_FEE_TRANSFER_TYPE, GuaranteeType.Relationships.FORWARD_TRANSFER_TYPE);
            request.setAttribute("guaranteeType", guaranteeType);
            final Currency currency = guaranteeType.getCurrency();
            getDataBinderGuaranteeType().writeAsString(form.getGuaranteeType(), guaranteeType);
            searchTrasferTypes(request, currency);
        }

        final StringTransformer javaScriptTransformer = new StringTransformer() {
            @Override
            public String transform(final Object value) {
                return "'" + value.toString() + "'";
            }
        };

        final StringTransformer i18nTransformer = new StringTransformer() {
            @Override
            public String transform(final Object value) {
                return javaScriptTransformer.transform(context.message("guaranteeType.authorizedBy." + value));
            }
        };

        final GuaranteeType.AuthorizedBy[] paymentObligationAuthorizers = new GuaranteeType.AuthorizedBy[] { GuaranteeType.AuthorizedBy.ISSUER, GuaranteeType.AuthorizedBy.BOTH };
        request.setAttribute("allAuthorizersStr", arrayToString(GuaranteeType.AuthorizedBy.values(), javaScriptTransformer));
        request.setAttribute("paymentObligationAuthorizersStr", arrayToString(paymentObligationAuthorizers, javaScriptTransformer));
        request.setAttribute("feePayers", Arrays.asList(GuaranteeType.FeePayer.BUYER, GuaranteeType.FeePayer.SELLER));

        request.setAttribute("paymentObligationAuthorizersI18N", arrayToString(paymentObligationAuthorizers, i18nTransformer));
        request.setAttribute("allAuthorizersI18N", arrayToString(GuaranteeType.AuthorizedBy.values(), i18nTransformer));
        // request.setAttribute("paymentObligationModelIdx", GuaranteeType.Model.WITH_PAYMENT_OBLIGATION.ordinal());
        // request.setAttribute("withBuyerOnlyIdx", GuaranteeType.Model.WITH_BUYER_ONLY.ordinal());

        request.setAttribute("currencies", currencyService.listAll());
        request.setAttribute("isInsert", isInsert);
        request.setAttribute("editable", permissionService.hasPermission(AdminSystemPermission.GUARANTEE_TYPES_MANAGE));
        request.setAttribute("paymentObligationPeriod", Arrays.asList(TimePeriod.Field.DAYS, TimePeriod.Field.MONTHS, TimePeriod.Field.YEARS));
        request.setAttribute("pendingGuaranteeExpiration", Arrays.asList(TimePeriod.Field.DAYS, TimePeriod.Field.MONTHS, TimePeriod.Field.YEARS));

        RequestHelper.storeEnum(request, GuaranteeType.Model.class, "model");
        RequestHelper.storeEnum(request, GuaranteeType.AuthorizedBy.class, "allAuthorizers");
        RequestHelper.storeEnum(request, GuaranteeType.FeeType.class, "feeTypes");
    }

    @Override
    protected void validateForm(final ActionContext context) {
        final EditGuaranteeTypeForm form = context.getForm();
        final GuaranteeType guaranteeType = getDataBinderGuaranteeType().readFromString(form.getGuaranteeType());
        guaranteeTypeService.validate(guaranteeType);
    }

    private String arrayToString(final Object[] values, final StringTransformer transformer) {
        if (values == null || values.length == 0) {
            return "[]";
        }
        final StringBuilder str = new StringBuilder("[");
        for (final Object value : values) {
            str.append(transformer.transform(value)).append(",");
        }

        str.delete(str.length() - 1, str.length()).append("]");
        return str.toString();
    }

    private void searchTrasferTypes(final HttpServletRequest request, final Currency currency) {
        final TransferTypeQuery ttQuery = new TransferTypeQuery();
        ttQuery.setCurrency(currency);

        // Credit fee TT query
        ttQuery.setContext(TransactionContext.ANY);
        ttQuery.setFromNature(AccountType.Nature.MEMBER);
        ttQuery.setToNature(AccountType.Nature.SYSTEM);
        request.setAttribute("creditFeeTransferType", transferTypeService.search(ttQuery));

        // Issue fee TT query
        ttQuery.setContext(TransactionContext.ANY);
        ttQuery.setFromNature(AccountType.Nature.MEMBER);
        ttQuery.setToNature(AccountType.Nature.MEMBER);
        final List<TransferType> issueFeeQueryResult = transferTypeService.search(ttQuery);
        request.setAttribute("issueFeeTransferType", issueFeeQueryResult);

        // Forward TT query
        request.setAttribute("forwardTransferType", issueFeeQueryResult);

        // Loan TT query
        ttQuery.setContext(TransactionContext.AUTOMATIC_LOAN);
        ttQuery.setFromNature(AccountType.Nature.SYSTEM);
        ttQuery.setToNature(AccountType.Nature.MEMBER);
        request.setAttribute("loanTransferType", transferTypeService.search(ttQuery));
    }
}
