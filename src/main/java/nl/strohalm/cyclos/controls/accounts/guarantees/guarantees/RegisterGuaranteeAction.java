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
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseFormAction;
import nl.strohalm.cyclos.entities.accounts.guarantees.Guarantee;
import nl.strohalm.cyclos.entities.accounts.guarantees.GuaranteeType;
import nl.strohalm.cyclos.entities.accounts.guarantees.GuaranteeType.FeeType;
import nl.strohalm.cyclos.entities.customization.fields.CustomFieldValue;
import nl.strohalm.cyclos.entities.customization.fields.PaymentCustomField;
import nl.strohalm.cyclos.entities.customization.fields.PaymentCustomFieldValue;
import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.services.accounts.guarantees.GuaranteeFeeVO;
import nl.strohalm.cyclos.services.accounts.guarantees.GuaranteeService;
import nl.strohalm.cyclos.services.accounts.guarantees.GuaranteeTypeService;
import nl.strohalm.cyclos.services.customization.PaymentCustomFieldService;
import nl.strohalm.cyclos.utils.ActionHelper;
import nl.strohalm.cyclos.utils.EntityHelper;
import nl.strohalm.cyclos.utils.RequestHelper;
import nl.strohalm.cyclos.utils.binding.BeanBinder;
import nl.strohalm.cyclos.utils.binding.BeanCollectionBinder;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.binding.DataBinderHelper;
import nl.strohalm.cyclos.utils.binding.PropertyBinder;
import nl.strohalm.cyclos.utils.conversion.HtmlConverter;
import nl.strohalm.cyclos.utils.conversion.IdConverter;
import nl.strohalm.cyclos.utils.validation.ValidationException;

import org.apache.commons.collections.CollectionUtils;
import org.apache.struts.action.ActionForward;

public class RegisterGuaranteeAction extends BaseFormAction {
    private GuaranteeTypeService      guaranteeTypeService;
    private GuaranteeService          guaranteeService;
    private PaymentCustomFieldService paymentCustomFieldService;
    private DataBinder<Guarantee>     dataBinder;

    public void setDataBinder(final DataBinder<Guarantee> dataBinder) {
        this.dataBinder = dataBinder;
    }

    @Inject
    public void setGuaranteeService(final GuaranteeService guaranteeService) {
        this.guaranteeService = guaranteeService;
    }

    @Inject
    public void setGuaranteeTypeService(final GuaranteeTypeService guaranteeTypeService) {
        this.guaranteeTypeService = guaranteeTypeService;
    }

    @Inject
    public void setPaymentCustomFieldService(final PaymentCustomFieldService paymentCustomFieldService) {
        this.paymentCustomFieldService = paymentCustomFieldService;
    }

    @Override
    protected ActionForward handleSubmit(final ActionContext context) throws Exception {
        final RegisterGuaranteeForm form = context.getForm();
        Guarantee guarantee = getDataBinder().readFromString(form.getGuarantee());
        final boolean isInsert = guarantee.isTransient();
        guarantee = guaranteeService.registerGuarantee(guarantee);
        if (isInsert) {
            context.sendMessage("guarantee.inserted");
        } else {
            context.sendMessage("guarantee.modified");
        }
        return ActionHelper.redirectWithParam(context.getRequest(), context.getSuccessForward(), "guaranteeId", guarantee.getId());
    }

    /**
     * Method use to prepare a form for being displayed
     */
    @Override
    protected void prepareForm(final ActionContext context) throws Exception {
        final HttpServletRequest request = context.getRequest();
        final RegisterGuaranteeForm form = context.getForm();
        final Long id = form.getGuaranteeTypeId();
        final GuaranteeType guaranteeType = guaranteeTypeService.load(id);

        final Collection<? extends Group> issuers = guaranteeService.getIssuers(guaranteeType);
        if (CollectionUtils.isEmpty(issuers)) {
            throw new ValidationException("guarantee.error.noIssuer");
        }
        final Collection<? extends Group> sellers = guaranteeService.getSellers();

        request.setAttribute("issuerGroupsId", EntityHelper.toIdsAsString(issuers));
        // only with this model we must filter the buyers groups
        if (guaranteeType.getModel() != GuaranteeType.Model.WITH_BUYER_ONLY) {
            final Collection<? extends Group> buyers = guaranteeService.getBuyers();
            request.setAttribute("buyerGroupsId", EntityHelper.toIdsAsString(buyers));
        }

        final List<PaymentCustomField> customFields = paymentCustomFieldService.list(guaranteeType.getLoanTransferType(), false);
        request.setAttribute("customFields", customFields);

        request.setAttribute("sellerGroupsId", EntityHelper.toIdsAsString(sellers));
        request.setAttribute("isWithBuyerAndSeller", guaranteeType.getModel() == GuaranteeType.Model.WITH_BUYER_AND_SELLER);
        request.setAttribute("guaranteeType", guaranteeType);
        RequestHelper.storeEnum(request, GuaranteeType.FeeType.class, "feeTypes");
    }

    @Override
    protected void validateForm(final ActionContext context) {
        final RegisterGuaranteeForm form = context.getForm();
        final Guarantee guarantee = getDataBinder().readFromString(form.getGuarantee());
        guaranteeService.validate(guarantee, false);
    }

    private DataBinder<Guarantee> getDataBinder() {
        if (dataBinder == null) {

            final BeanBinder<Guarantee> binder = BeanBinder.instance(Guarantee.class);
            final LocalSettings localSettings = settingsService.getLocalSettings();
            binder.registerBinder("id", PropertyBinder.instance(Long.class, "id", IdConverter.instance()));
            binder.registerBinder("buyer", PropertyBinder.instance(Member.class, "buyer"));
            binder.registerBinder("seller", PropertyBinder.instance(Member.class, "seller"));
            binder.registerBinder("issuer", PropertyBinder.instance(Member.class, "issuer"));
            binder.registerBinder("amount", PropertyBinder.instance(BigDecimal.class, "amount", localSettings.getNumberConverter()));
            binder.registerBinder("guaranteeType", PropertyBinder.instance(GuaranteeType.class, "guaranteeType"));
            binder.registerBinder("validity", DataBinderHelper.rawPeriodBinder(localSettings, "validity"));

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
}
