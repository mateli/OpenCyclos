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
import nl.strohalm.cyclos.services.Service;
import nl.strohalm.cyclos.services.stats.StatisticalResultDTO;

/**
 * interface for all rates logic.
 * 
 * @author Rinke
 * 
 */
public interface RateService extends Service {

    public enum RateType {
        A_RATE, D_RATE, I_RATE
    };

    /**
     * checks if there is any pending rate initialization which is not finished. The currency parameter is optional; if you pass it null, it will
     * check this for any currency in the system.
     * @return if there is any pending rate initialization, the method returns the earliest date until which this is processed. If there is no pending
     * rate available, it returns null.
     */
    Calendar checkPendingRateInitializations(final Currency currency);

    /**
     * gets the enabling date of the specified rate type. Returns null if no rateType specified, or no rate enabled. This is not necessarily the
     * enableDate of the present valid RateParameters. This method seeks the enabling date of the rate regardless of any field value changes in the
     * meantime.
     */
    Calendar getEnableDate(Currency currency, RateType rateType);

    /**
     * gets the data for producing a graph of the configuration curve for any rate.
     * @return a StatisticalResultDTO which must be used in the constructor of a StatisticalDataProducer. The statisticsResult.jsp automatically
     * handles a request attribute as a list of statisticalDataProducers, so all graphs are displayed.
     */
    StatisticalResultDTO getRateConfigGraph(final RatedFeeDTO inputParameters);

    /**
     * checks if any of the rates is enabled on the given date.
     * @param currency
     * @param date
     * @return true if any rate enabled
     */
    boolean isAnyRateEnabled(final Currency currency, final Calendar date);

    /**
     * reinitializes the specified rates, by rewriting the complete rate history since startDate. The method will only work with the system set
     * offline.<br>
     * Rates are recalculated and written to AccountRates for any account that is found to have been in a transfer since the specified date. This
     * happens even if the rate was not yet enabled at that time. If rates were not enabled at a certain date, the method uses the earliest available
     * rateParameters settings after that date. Afterwards, the RateParameters enabledSince field is reset to the starting date (or the date of the
     * first encountered transfer, if starting date is null).
     * 
     * @param reinitializeRatesDto a dto containing the following:
     * <ul>
     * <li>Currency (obligatory)
     * <li>reinitSince, the date from which all is recalculated. If null, calculates from the earliest known transfer.
     * <li>whatRate specifies for which rates (A, D, I) this must be done. Note that rates are recalculated
     * <li>maintainPastSettings, a boolean indicating if past settings must be maintained. The specified rates will be marked as enabled for the
     * complete period between the given reinitialization date and now. If a rate was already enabled for part of that period, then you can choose to
     * keep the original rate parameters as they were then valid. In such a case, this field should be true. If it is false, it means that all
     * specified rates will use the present parameter settings.<br>
     * <u>Example:</u><br>
     * Suppose A-rate was enabled on january 1st 2008, with a creation value parameter of 4. Then suppose it was decided on august 1st 2009 to change
     * that creation value parameter to the value of 0. This setting is still valid on february 1st 2010. On this day you decide to reinitialize the
     * A-rate from 2005 up to now.<br>
     * If you set this field to true, it means that A-rate will be enabled from 2005 off. Up to august 1st 2009 the creation value of 4 will be used
     * for the recalculations; from august 1st 2009 up to now the creation value of 0 will be used for the recalculations.<br>
     * If you had this field set to false, then for the whole period only the present settings will be used. A-rate will be recalculated with a
     * creation value of 0 for over the complete period from 2005 up to now.
     * 
     * </ul>
     */
    void reinitializeRate(ReinitializeRatesDTO reinitializeRatesDto);

    /**
     * validate the RateConfigSimulation
     */
    void validate(RatedFeeDTO dto, ARateRelation rateRelation);

    /**
     * validate the rate reinitialization
     */
    void validate(ReinitializeRatesDTO reinitDto);

}
