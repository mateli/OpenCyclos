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
package nl.strohalm.cyclos.controls.accounts.guarantees.guarantees;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.access.AdminSystemPermission;
import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseFormAction;
import nl.strohalm.cyclos.entities.accounts.guarantees.Guarantee;
import nl.strohalm.cyclos.entities.accounts.guarantees.GuaranteeType;
import nl.strohalm.cyclos.entities.accounts.guarantees.GuaranteeType.FeeType;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferType;
import nl.strohalm.cyclos.entities.customization.fields.CustomFieldValue;
import nl.strohalm.cyclos.entities.customization.fields.PaymentCustomField;
import nl.strohalm.cyclos.entities.customization.fields.PaymentCustomFieldValue;
import nl.strohalm.cyclos.entities.exceptions.UnexpectedEntityException;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.services.accounts.guarantees.GuaranteeFeeVO;
import nl.strohalm.cyclos.services.accounts.guarantees.GuaranteeService;
import nl.strohalm.cyclos.services.accounts.guarantees.exceptions.GuaranteeStatusChangeException;
import nl.strohalm.cyclos.services.customization.PaymentCustomFieldService;
import nl.strohalm.cyclos.services.transactions.exceptions.AuthorizedPaymentInPastException;
import nl.strohalm.cyclos.services.transactions.exceptions.CreditsException;
import nl.strohalm.cyclos.utils.ActionHelper;
import nl.strohalm.cyclos.utils.CustomFieldHelper;
import nl.strohalm.cyclos.utils.RequestHelper;
import nl.strohalm.cyclos.utils.binding.BeanBinder;
import nl.strohalm.cyclos.utils.binding.BeanCollectionBinder;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.binding.DataBinderHelper;
import nl.strohalm.cyclos.utils.binding.MapBean;
import nl.strohalm.cyclos.utils.binding.PropertyBinder;
import nl.strohalm.cyclos.utils.conversion.CalendarConverter;
import nl.strohalm.cyclos.utils.conversion.HtmlConverter;

import org.apache.struts.action.ActionForward;

public class AuthorizeGuaranteeAction extends BaseFormAction {

    private GuaranteeService          guaranteeService;
    private PaymentCustomFieldService paymentCustomFieldService;
    private DataBinder<Guarantee>     dataBinder;

    private DataBinder<Guarantee>     readDataBinder;

    private CustomFieldHelper         customFieldHelper;

    @Inject
    public void setCustomFieldHelper(final CustomFieldHelper customFieldHelper) {
        this.customFieldHelper = customFieldHelper;
    }

    @Inject
    public void setGuaranteeService(final GuaranteeService guaranteeService) {
        this.guaranteeService = guaranteeService;
    }

    @Inject
    public void setPaymentCustomFieldService(final PaymentCustomFieldService paymentCustomFieldService) {
        this.paymentCustomFieldService = paymentCustomFieldService;
    }

    @Override
    protected ActionForward handleSubmit(final ActionContext context) throws Exception {
        final AuthorizeGuaranteeForm form = context.getForm();
        final Guarantee guarantee = guaranteeService.load(form.getGuaranteeId(), Guarantee.Relationships.GUARANTEE_TYPE);
        updateGuarantee(context, form, guarantee);
        try {
            guaranteeService.acceptGuarantee(guarantee, form.isAutomaticLoanAuthorization());
            return ActionHelper.redirectWithParam(context.getRequest(), context.getSuccessForward(), "guaranteeId", guarantee.getId());
        } catch (final GuaranteeStatusChangeException e) {
            context.sendMessage("guarantee.error.changeStatus", context.message("guarantee.status." + e.getNewstatus()));
            return ActionHelper.redirectWithParam(context.getRequest(), context.getSuccessForward(), "guaranteeId", guarantee.getId());
        } catch (final CreditsException e) {
            return context.sendError(actionHelper.resolveErrorKey(e), actionHelper.resolveParameters(e));
        } catch (final UnexpectedEntityException e) {
            return context.sendError("payment.error.invalidTransferType");
        } catch (final AuthorizedPaymentInPastException e) {
            return context.sendError("payment.error.authorizedInPast");
        }
    }

    /**
     * Method use to prepare a form for being displayed
     */
    @Override
    protected void prepareForm(final ActionContext context) throws Exception {
        final HttpServletRequest request = context.getRequest();
        final AuthorizeGuaranteeForm form = context.getForm();
        final Long id = form.getGuaranteeId();
        final Guarantee guarantee = guaranteeService.load(id, Guarantee.Relationships.GUARANTEE_TYPE);
        final boolean canAcceptLoan = permissionService.hasPermission(AdminSystemPermission.PAYMENTS_AUTHORIZE);
        getReadDataBinder().writeAsString(form.getGuarantee(), guarantee);

        // suggest the validity begin as the current date
        if (guarantee.getValidity() == null || guarantee.getValidity().getBegin() == null) {
            final LocalSettings localSettings = settingsService.getLocalSettings();
            final CalendarConverter calendarConverter = localSettings.getRawDateConverter();

            ((MapBean) form.getGuarantee("validity")).set("begin", calendarConverter.toString(Calendar.getInstance()));
        }

        final TransferType transferType = guarantee.getGuaranteeType().getLoanTransferType();
        final List<PaymentCustomField> customFields = paymentCustomFieldService.list(transferType, false);
        request.setAttribute("customFields", customFieldHelper.buildEntries(customFields, guarantee.getCustomValues()));
        request.setAttribute("canAcceptLoan", canAcceptLoan);

        request.setAttribute("guarantee", guarantee);
        RequestHelper.storeEnum(request, GuaranteeType.FeeType.class, "feeTypes");
    }

    @Override
    protected void validateForm(final ActionContext context) {
        final AuthorizeGuaranteeForm form = context.getForm();
        final Guarantee guarantee = getDataBinder().readFromString(form.getGuarantee());
        guarantee.setId(form.getGuaranteeId()); // the id is not read by the binder
        guaranteeService.validate(guarantee, true);
    }

    private DataBinder<Guarantee> getDataBinder() {
        if (dataBinder == null) {

            final BeanBinder<Guarantee> binder = BeanBinder.instance(Guarantee.class);
            final LocalSettings localSettings = settingsService.getLocalSettings();
            binder.registerBinder("validity", DataBinderHelper.rawPeriodBinder(localSettings, "validity"));
            binder.registerBinder("amount", PropertyBinder.instance(BigDecimal.class, "amount"));

            final BeanBinder<? extends CustomFieldValue> customValueBinder = BeanBinder.instance(PaymentCustomFieldValue.class);
            customValueBinder.registerBinder("field", PropertyBinder.instance(PaymentCustomField.class, "field"));
            customValueBinder.registerBinder("value", PropertyBinder.instance(String.class, "value", HtmlConverter.instance()));
            binder.registerBinder("customValues", BeanCollectionBinder.instance(customValueBinder, "customValues"));

            final BeanBinder<GuaranteeFeeVO> issueFeeBinder = BeanBinder.instance(GuaranteeFeeVO.class, "issueFeeSpec");
            issueFeeBinder.registerBinder("type", PropertyBinder.instance(FeeType.class, "type"));
            issueFeeBinder.registerBinder("fee", PropertyBinder.instance(BigDecimal.class, "fee", localSettings.getNumberConverter()));
            binder.registerBinder("issueFeeSpec", issueFeeBinder);

            final BeanBinder<GuaranteeFeeVO> creditFeeBinder = BeanBinder.instance(GuaranteeFeeVO.class, "creditFeeSpec");
            creditFeeBinder.registerBinder("type", PropertyBinder.instance(FeeType.class, "type"));
            creditFeeBinder.registerBinder("fee", PropertyBinder.instance(BigDecimal.class, "fee", localSettings.getNumberConverter()));
            binder.registerBinder("creditFeeSpec", creditFeeBinder);

            dataBinder = binder;
        }
        return dataBinder;
    }

    private DataBinder<Guarantee> getReadDataBinder() {
        if (readDataBinder == null) {
            readDataBinder = getDataBinder();
            dataBinder = null;
            final BeanBinder<Guarantee> beanBinder = (BeanBinder<Guarantee>) readDataBinder;
            beanBinder.getMappings().remove("customValues");
        }
        return readDataBinder;
    }

    private void updateGuarantee(final ActionContext context, final AuthorizeGuaranteeForm form, final Guarantee guarantee) {
        final Guarantee updatedGuarantee = getDataBinder().readFromString(form.getGuarantee());

        guarantee.setValidity(updatedGuarantee.getValidity());
        guarantee.setCustomValues(updatedGuarantee.getCustomValues());
        if (context.isAdmin() && !guarantee.getGuaranteeType().getCreditFee().isReadonly()) { // only the admin can change the credit fee
            guarantee.setCreditFeeSpec(updatedGuarantee.getCreditFeeSpec());
        }
        if (!guarantee.getGuaranteeType().getIssueFee().isReadonly()) {
            guarantee.setIssueFeeSpec(updatedGuarantee.getIssueFeeSpec());
        }
    }
}
