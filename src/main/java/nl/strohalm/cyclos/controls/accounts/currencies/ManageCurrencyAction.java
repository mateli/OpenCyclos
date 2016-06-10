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
import nl.strohalm.cyclos.controls.BaseAction;
import nl.strohalm.cyclos.entities.accounts.Currency;
import nl.strohalm.cyclos.services.accounts.CurrencyService;
import nl.strohalm.cyclos.services.accounts.rates.RateService;

import org.apache.struts.action.ActionForward;

/**
 * Action used to prepare the edit Currency / reinitialize rate screen
 * @author rinke
 */
public class ManageCurrencyAction extends BaseAction {

    private CurrencyService currencyService;
    private RateService     rateService;

    @Inject
    public void setCurrencyService(final CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @Inject
    public void setRateService(final RateService rateService) {
        this.rateService = rateService;
    }

    @Override
    protected ActionForward executeAction(final ActionContext context) throws Exception {
        final HttpServletRequest request = context.getRequest();
        final ManageCurrencyForm manageForm = context.getForm();
        final long id = manageForm.getCurrencyId();
        final boolean isInsert = id <= 0L;
        Currency currency;
        if (isInsert) {
            currency = new Currency();
        } else {
            currency = currencyService.load(id);
        }
        request.setAttribute("currency", currency);
        final boolean ratesEnabled = rateService.isAnyRateEnabled(currency, null);
        request.setAttribute("ratesEnabled", ratesEnabled);
        // get the progress on any pending rate initialization
        final Calendar pendingRateInitProgression = rateService.checkPendingRateInitializations(currency);
        request.setAttribute("pendingRateInit", pendingRateInitProgression);
        return context.getInputForward();
    }

}
