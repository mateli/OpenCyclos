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
import java.util.HashMap;
import java.util.Map;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseAjaxAction;
import nl.strohalm.cyclos.entities.accounts.guarantees.GuaranteeType;
import nl.strohalm.cyclos.entities.accounts.guarantees.GuaranteeType.FeeType;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.services.accounts.guarantees.GuaranteeFeeCalculationDTO;
import nl.strohalm.cyclos.services.accounts.guarantees.GuaranteeFeeVO;
import nl.strohalm.cyclos.services.accounts.guarantees.GuaranteeService;
import nl.strohalm.cyclos.services.accounts.guarantees.GuaranteeTypeService;
import nl.strohalm.cyclos.utils.EntityHelper;
import nl.strohalm.cyclos.utils.ResponseHelper;
import nl.strohalm.cyclos.utils.binding.BeanBinder;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.binding.DataBinderHelper;
import nl.strohalm.cyclos.utils.binding.PropertyBinder;
import nl.strohalm.cyclos.utils.validation.InvalidError;
import nl.strohalm.cyclos.utils.validation.ValidationException;

public class CalculateGuaranteeFeeAjaxAction extends BaseAjaxAction {

    private Map<String, BeanBinder<GuaranteeFeeCalculationDTO>> binderMap;
    private GuaranteeService                                    guaranteeService;
    private GuaranteeTypeService                                guaranteeTypeService;

    public CalculateGuaranteeFeeAjaxAction() {
        binderMap = new HashMap<String, BeanBinder<GuaranteeFeeCalculationDTO>>();
    }

    public DataBinder<GuaranteeFeeCalculationDTO> getDataBinder(final String property) {
        BeanBinder<GuaranteeFeeCalculationDTO> binder = binderMap.get(property);
        if (binder == null) {
            binder = BeanBinder.instance(GuaranteeFeeCalculationDTO.class);
            final LocalSettings localSettings = settingsService.getLocalSettings();

            binder.registerBinder("guaranteeTypeId", PropertyBinder.instance(Long.class, "guaranteeTypeId"));
            binder.registerBinder("amount", PropertyBinder.instance(BigDecimal.class, "amount", localSettings.getNumberConverter()));
            binder.registerBinder("validity", DataBinderHelper.rawPeriodBinder(localSettings, "validity"));

            final BeanBinder<GuaranteeFeeVO> creditFeeSpecBinder = BeanBinder.instance(GuaranteeFeeVO.class, property);
            creditFeeSpecBinder.registerBinder("type", PropertyBinder.instance(FeeType.class, "type"));
            creditFeeSpecBinder.registerBinder("fee", PropertyBinder.instance(BigDecimal.class, "fee", localSettings.getNumberConverter()));
            binder.registerBinder("feeSpec", creditFeeSpecBinder);
            binderMap.put(property, binder);

        }
        return binder;
    }

    @Inject
    public void setGuaranteeService(final GuaranteeService guaranteeService) {
        this.guaranteeService = guaranteeService;
    }

    @Inject
    public void setGuaranteeTypeService(final GuaranteeTypeService guaranteeTypeService) {
        this.guaranteeTypeService = guaranteeTypeService;
    }

    @Override
    protected ContentType contentType() {
        return ContentType.XML;
    }

    @Override
    protected void renderContent(final ActionContext context) throws Exception {
        final CalculateGuaranteeFeeAjaxForm form = context.getForm();

        final GuaranteeFeeCalculationDTO creditDTO = getDataBinder("creditFeeSpec").readFromString(form);
        final GuaranteeFeeCalculationDTO issueDTO = getDataBinder("issueFeeSpec").readFromString(form);

        GuaranteeType guaranteeType = EntityHelper.reference(GuaranteeType.class, creditDTO.getGuaranteeTypeId());
        guaranteeType = guaranteeTypeService.load(guaranteeType.getId(), GuaranteeType.Relationships.CURRENCY);

        try {
            final Map<String, Object> values = new HashMap<String, Object>();
            try {
                final String currentCreditFeeValueStr = getCurrentFeeValueAsString(guaranteeType, creditDTO);
                final String currentIssueFeeValueStr = getCurrentFeeValueAsString(guaranteeType, issueDTO);

                values.put("currentCreditFeeValue", currentCreditFeeValueStr);
                values.put("currentIssueFeeValue", currentIssueFeeValueStr);

                responseHelper.writeStatus(context.getResponse(), ResponseHelper.Status.SUCCESS, values);
            } catch (final IllegalArgumentException ie) {
                throw new ValidationException("validityBegin", "guarantee.validity", new InvalidError());
            }
        } catch (final ValidationException e) {
            responseHelper.writeValidationErrors(context.getResponse(), e);
        }
    }

    private String getCurrentFeeValueAsString(final GuaranteeType guaranteeType, final GuaranteeFeeCalculationDTO dto) {
        final LocalSettings localSettings = settingsService.getLocalSettings();

        if (isValid(dto)) {
            final BigDecimal currentFeeValue = guaranteeService.calculateFee(dto);
            return localSettings.getUnitsConverter(guaranteeType.getCurrency().getPattern()).toString(currentFeeValue);
        } else {
            return null;
        }
    }

    private boolean isValid(final GuaranteeFeeCalculationDTO dto) {
        if (dto.getFeeSpec() == null || dto.getFeeSpec().getType() == null) {
            return false;
        }

        switch (dto.getFeeSpec().getType()) {
            case FIXED:
                return true;
            case PERCENTAGE:
                return dto.getAmount() != null;
            case VARIABLE_ACCORDING_TO_TIME:
                return dto.getAmount() != null && dto.getValidity().getBegin() != null && dto.getValidity().getEnd() != null;
            default:
                throw new IllegalArgumentException("Can't check fee spec validity: unknown fee type: " + dto.getFeeSpec().getType());
        }
    }
}
