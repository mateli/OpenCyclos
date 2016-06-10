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

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseFormAction;
import nl.strohalm.cyclos.entities.accounts.Currency;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.entities.settings.events.LocalSettingsChangeListener;
import nl.strohalm.cyclos.entities.settings.events.LocalSettingsEvent;
import nl.strohalm.cyclos.services.accounts.CurrencyService;
import nl.strohalm.cyclos.services.accounts.rates.RateService;
import nl.strohalm.cyclos.services.accounts.rates.RateService.RateType;
import nl.strohalm.cyclos.services.accounts.rates.ReinitializeRatesDTO;
import nl.strohalm.cyclos.utils.ActionHelper;
import nl.strohalm.cyclos.utils.binding.BeanBinder;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.binding.PropertyBinder;
import nl.strohalm.cyclos.utils.conversion.IdConverter;

import org.apache.struts.action.ActionForward;

/**
 * Action used to reinitialize one or more rates
 * @author rinke
 */
public class ReinitializeRatesAction extends BaseFormAction implements LocalSettingsChangeListener {

    private CurrencyService                  currencyService;
    private RateService                      rateService;
    private DataBinder<ReinitializeRatesDTO> dataBinder;

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
        final ReinitializeRatesForm form = context.getForm();
        final long id = form.getCurrencyId();
        final Currency currency = currencyService.load(id);
        request.setAttribute("enabledARate", currency.isEnableARate());
        request.setAttribute("enabledDRate", currency.isEnableDRate());
        request.setAttribute("enabledIRate", currency.isEnableIRate());
        request.setAttribute("enableDateA", rateService.getEnableDate(currency, RateType.A_RATE));
        request.setAttribute("enableDateD", rateService.getEnableDate(currency, RateType.D_RATE));
        request.setAttribute("enableDateI", rateService.getEnableDate(currency, RateType.I_RATE));
        request.setAttribute("currency", currency);
        final ReinitializeRatesDTO dto = new ReinitializeRatesDTO();
        dto.setCurrencyId(id);
        dto.setMaintainPastSettings(true);
        getDataBinder().writeAsString(form.getReinitializeRatesDto(), dto);
        return new ActionForward("/pages/accounts/currencies/reinitializeRates.jsp");
    }

    @Override
    protected ActionForward handleSubmit(final ActionContext context) throws Exception {
        final ReinitializeRatesForm form = context.getForm();
        final ReinitializeRatesDTO reinitDto = getDataBinder().readFromString(form.getReinitializeRatesDto());
        reinitDto.setWhatRate(form.getWhatRate());
        reinitDto.setRequestURI(context.getRequest().getRequestURI());
        rateService.reinitializeRate(reinitDto);
        return ActionHelper.redirectWithParam(context.getRequest(), context.getSuccessForward(), "currencyId", reinitDto.getCurrencyId());
    }

    @Override
    protected void validateForm(final ActionContext context) {
        final ReinitializeRatesForm form = context.getForm();
        final ReinitializeRatesDTO reinitDto = getDataBinder().readFromString(form.getReinitializeRatesDto());
        reinitDto.setWhatRate(form.getWhatRate());
        rateService.validate(reinitDto);
    }

    private DataBinder<ReinitializeRatesDTO> getDataBinder() {
        if (dataBinder == null) {
            final LocalSettings localSettings = settingsService.getLocalSettings();
            final BeanBinder<ReinitializeRatesDTO> binder = BeanBinder.instance(ReinitializeRatesDTO.class);
            binder.registerBinder("currencyId", PropertyBinder.instance(Long.class, "currencyId", IdConverter.instance()));
            binder.registerBinder("reinitSince", PropertyBinder.instance(Calendar.class, "reinitSince", localSettings.getDateConverter()));
            binder.registerBinder("maintainPastSettings", PropertyBinder.instance(boolean.class, "maintainPastSettings"));
            dataBinder = binder;
        }
        return dataBinder;
    }

}
