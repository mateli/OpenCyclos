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
package nl.strohalm.cyclos.controls.accounts.currencies;

import java.math.BigDecimal;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.access.AdminSystemPermission;
import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseFormAction;
import nl.strohalm.cyclos.entities.accounts.ARateParameters;
import nl.strohalm.cyclos.entities.accounts.Currency;
import nl.strohalm.cyclos.entities.accounts.DRateParameters;
import nl.strohalm.cyclos.entities.accounts.IRateParameters;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.entities.settings.events.LocalSettingsChangeListener;
import nl.strohalm.cyclos.entities.settings.events.LocalSettingsEvent;
import nl.strohalm.cyclos.services.accounts.CurrencyService;
import nl.strohalm.cyclos.services.accounts.rates.RateService;
import nl.strohalm.cyclos.services.accounts.rates.WhatRate;
import nl.strohalm.cyclos.utils.ActionHelper;
import nl.strohalm.cyclos.utils.binding.BeanBinder;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.binding.PropertyBinder;
import nl.strohalm.cyclos.utils.conversion.IdConverter;

import org.apache.struts.action.ActionForward;

/**
 * Action used to edit a currency
 * @author luis
 */
public class EditCurrencyAction extends BaseFormAction implements LocalSettingsChangeListener {

    private CurrencyService      currencyService;
    private RateService          rateService;
    private DataBinder<Currency> dataBinder;

    @Override
    public void onLocalSettingsUpdate(final LocalSettingsEvent event) {
        dataBinder = null;
    }

    @Inject
    public void setCurrencyService(final CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @Inject
    public void setRateService(final RateService rateService) {
        this.rateService = rateService;
    }

    @Override
    protected ActionForward handleDisplay(final ActionContext context) throws Exception {
        final HttpServletRequest request = context.getRequest();
        final EditCurrencyForm form = context.getForm();
        final long id = form.getCurrencyId();
        final boolean isInsert = id <= 0L;
        boolean editable = permissionService.hasPermission(AdminSystemPermission.CURRENCIES_MANAGE);
        Currency currency;
        if (isInsert) {
            currency = new Currency();
            editable = true;
        } else {
            currency = currencyService.load(id);
        }
        getDataBinder().writeAsString(form.getCurrency(), currency);
        form.setEnableARate(currency.isEnableARate());
        form.setEnableDRate(currency.isEnableDRate());
        form.setEnableIRate(currency.isEnableIRate());
        // get the progress on any pending rate initialization
        final Calendar pendingRateInitProgression = rateService.checkPendingRateInitializations(currency);
        request.setAttribute("pendingRateInitProgression", pendingRateInitProgression);
        request.setAttribute("currency", currency);
        request.setAttribute("isInsert", isInsert);
        request.setAttribute("editable", editable);
        return new ActionForward("/pages/accounts/currencies/editCurrency.jsp");
    }

    @Override
    protected ActionForward handleSubmit(final ActionContext context) throws Exception {
        final EditCurrencyForm form = context.getForm();
        Currency currency = getDataBinder().readFromString(form.getCurrency());
        final boolean isInsert = currency.isTransient();
        final WhatRate whatRate = new WhatRate();
        whatRate.setaRate(form.isEnableARate());
        whatRate.setdRate(form.isEnableDRate());
        whatRate.setiRate(form.isEnableIRate());
        currency = currencyService.save(currency, whatRate);
        if (isInsert) {
            context.sendMessage("currency.inserted");
        } else {
            context.sendMessage("currency.modified");
        }
        return ActionHelper.redirectWithParam(context.getRequest(), context.getSuccessForward(), "currencyId", currency.getId());
    }

    @Override
    protected void validateForm(final ActionContext context) {
        final EditCurrencyForm form = context.getForm();
        final Currency currency = getDataBinder().readFromString(form.getCurrency());
        final WhatRate whatRate = new WhatRate();
        whatRate.setaRate(form.isEnableARate());
        whatRate.setdRate(form.isEnableDRate());
        // Skip the validation for i-rate here, as it is done on the currencyService.save method anyways.
        whatRate.setiRate(false);
        currencyService.validate(currency, whatRate);
    }

    private DataBinder<Currency> getDataBinder() {
        if (dataBinder == null) {
            final LocalSettings localSettings = settingsService.getLocalSettings();

            final BeanBinder<Currency> binder = BeanBinder.instance(Currency.class);
            binder.registerBinder("id", PropertyBinder.instance(Long.class, "id", IdConverter.instance()));
            binder.registerBinder("name", PropertyBinder.instance(String.class, "name"));
            binder.registerBinder("symbol", PropertyBinder.instance(String.class, "symbol"));
            binder.registerBinder("pattern", PropertyBinder.instance(String.class, "pattern"));
            binder.registerBinder("description", PropertyBinder.instance(String.class, "description"));

            final BeanBinder<ARateParameters> aRate = BeanBinder.instance(ARateParameters.class, "aRateParameters");
            aRate.registerBinder("id", PropertyBinder.instance(Long.class, "id", IdConverter.instance()));
            aRate.registerBinder("initValue", PropertyBinder.instance(BigDecimal.class, "initValue", localSettings.getNumberConverter()));
            aRate.registerBinder("initDate", PropertyBinder.instance(Calendar.class, "initDate", localSettings.getDateTimeConverter()));
            aRate.registerBinder("creationValue", PropertyBinder.instance(BigDecimal.class, "creationValue", localSettings.getNumberConverter()));
            binder.registerBinder("aRateParameters", aRate);

            final BeanBinder<DRateParameters> dRate = BeanBinder.instance(DRateParameters.class, "dRateParameters");
            dRate.registerBinder("id", PropertyBinder.instance(Long.class, "id", IdConverter.instance()));
            dRate.registerBinder("interest", PropertyBinder.instance(BigDecimal.class, "interest", localSettings.getHighPrecisionConverter()));
            dRate.registerBinder("baseMalus", PropertyBinder.instance(BigDecimal.class, "baseMalus", localSettings.getHighPrecisionConverter()));
            dRate.registerBinder("minimalD", PropertyBinder.instance(BigDecimal.class, "minimalD", localSettings.getNumberConverter()));
            dRate.registerBinder("initValue", PropertyBinder.instance(BigDecimal.class, "initValue", localSettings.getNumberConverter()));
            dRate.registerBinder("initDate", PropertyBinder.instance(Calendar.class, "initDate", localSettings.getDateTimeConverter()));
            dRate.registerBinder("creationValue", PropertyBinder.instance(BigDecimal.class, "creationValue", localSettings.getNumberConverter()));
            binder.registerBinder("dRateParameters", dRate);

            final BeanBinder<IRateParameters> iRate = BeanBinder.instance(IRateParameters.class, "iRateParameters");
            iRate.registerBinder("id", PropertyBinder.instance(Long.class, "id", IdConverter.instance()));
            binder.registerBinder("iRateParameters", iRate);

            dataBinder = binder;
        }
        return dataBinder;
    }

}
