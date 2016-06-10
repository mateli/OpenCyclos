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
package nl.strohalm.cyclos.services.accounts.rates;

import java.util.Calendar;

import nl.strohalm.cyclos.entities.accounts.Currency;
import nl.strohalm.cyclos.entities.accounts.fees.transaction.SimpleTransactionFee.ARateRelation;
import nl.strohalm.cyclos.services.BaseServiceSecurity;
import nl.strohalm.cyclos.services.stats.StatisticalResultDTO;

public class RateServiceSecurityMock extends BaseServiceSecurity implements RateService {

    private RateServiceLocal rateService;

    @Override
    public Calendar checkPendingRateInitializations(final Currency currency) {
        return rateService.checkPendingRateInitializations(currency);
    }

    @Override
    public Calendar getEnableDate(final Currency currency, final RateType rateType) {
        return rateService.getEnableDate(currency, rateType);
    }

    @Override
    public StatisticalResultDTO getRateConfigGraph(final RatedFeeDTO inputParameters) {
        return rateService.getRateConfigGraph(inputParameters);
    }

    @Override
    public boolean isAnyRateEnabled(final Currency currency, final Calendar date) {
        return false;
    }

    @Override
    public void reinitializeRate(final ReinitializeRatesDTO reinitializeRatesDto) {
        rateService.reinitializeRate(reinitializeRatesDto);
    }

    public void setRateServiceLocal(final RateServiceLocal rateService) {
        this.rateService = rateService;
    }

    @Override
    public void validate(final RatedFeeDTO dto, final ARateRelation rateRelation) {
        rateService.validate(dto, rateRelation);
    }

    @Override
    public void validate(final ReinitializeRatesDTO reinitDto) {
        rateService.validate(reinitDto);
    }

}
