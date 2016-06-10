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
package nl.strohalm.cyclos.services.stats;

import nl.strohalm.cyclos.entities.reports.StatisticalActivityQuery;

/**
 * Security implementation for {@link StatisticalActivityService}
 * 
 * @author Rinke
 */
public class StatisticalActivityServiceSecurity extends StatisticalServiceSecurity implements StatisticalActivityService {

    @Override
    public StatisticalResultDTO getComparePeriodsGrossProduct(final StatisticalActivityQuery queryParameters) {
        checkPermission();
        return ((StatisticalActivityServiceLocal) getStatisticalService()).getComparePeriodsGrossProduct(queryParameters);
    }

    @Override
    public StatisticalResultDTO getComparePeriodsLoginTimes(final StatisticalActivityQuery queryParameters) {
        checkPermission();
        return ((StatisticalActivityServiceLocal) getStatisticalService()).getComparePeriodsLoginTimes(queryParameters);
    }

    @Override
    public StatisticalResultDTO getComparePeriodsNumberTransactions(final StatisticalActivityQuery queryParameters) {
        checkPermission();
        return ((StatisticalActivityServiceLocal) getStatisticalService()).getComparePeriodsNumberTransactions(queryParameters);
    }

    @Override
    public StatisticalResultDTO getComparePeriodsPercentageNoTrade(final StatisticalActivityQuery queryParameters) {
        checkPermission();
        return ((StatisticalActivityServiceLocal) getStatisticalService()).getComparePeriodsPercentageNoTrade(queryParameters);
    }

    @Override
    public StatisticalResultDTO getHistogramGrossProduct(final StatisticalActivityQuery queryParameters) {
        checkPermission();
        return ((StatisticalActivityServiceLocal) getStatisticalService()).getHistogramGrossProduct(queryParameters);
    }

    @Override
    public StatisticalResultDTO getHistogramLoginTimes(final StatisticalActivityQuery queryParameters) {
        checkPermission();
        return ((StatisticalActivityServiceLocal) getStatisticalService()).getHistogramLoginTimes(queryParameters);
    }

    @Override
    public StatisticalResultDTO getHistogramNumberTransactions(final StatisticalActivityQuery queryParameters) {
        checkPermission();
        return ((StatisticalActivityServiceLocal) getStatisticalService()).getHistogramNumberTransactions(queryParameters);
    }

    @Override
    public StatisticalResultDTO getSinglePeriodGrossProduct(final StatisticalActivityQuery queryParameters) {
        checkPermission();
        return ((StatisticalActivityServiceLocal) getStatisticalService()).getSinglePeriodGrossProduct(queryParameters);
    }

    @Override
    public StatisticalResultDTO getSinglePeriodLoginTimes(final StatisticalActivityQuery queryParameters) {
        checkPermission();
        return ((StatisticalActivityServiceLocal) getStatisticalService()).getSinglePeriodLoginTimes(queryParameters);
    }

    @Override
    public StatisticalResultDTO getSinglePeriodNumberTransactions(final StatisticalActivityQuery queryParameters) {
        checkPermission();
        return ((StatisticalActivityServiceLocal) getStatisticalService()).getSinglePeriodNumberTransactions(queryParameters);
    }

    @Override
    public StatisticalResultDTO getSinglePeriodPercentageNoTrade(final StatisticalActivityQuery queryParameters) {
        checkPermission();
        return ((StatisticalActivityServiceLocal) getStatisticalService()).getSinglePeriodPercentageNoTrade(queryParameters);
    }

    @Override
    public StatisticalResultDTO getThroughTheTimeGrossProduct(final StatisticalActivityQuery queryParameters) {
        checkPermission();
        return ((StatisticalActivityServiceLocal) getStatisticalService()).getThroughTheTimeGrossProduct(queryParameters);
    }

    @Override
    public StatisticalResultDTO getThroughTheTimeLoginTimes(final StatisticalActivityQuery queryParameters) {
        checkPermission();
        return ((StatisticalActivityServiceLocal) getStatisticalService()).getThroughTheTimeLoginTimes(queryParameters);
    }

    @Override
    public StatisticalResultDTO getThroughTheTimeNumberTransactions(final StatisticalActivityQuery queryParameters) {
        checkPermission();
        return ((StatisticalActivityServiceLocal) getStatisticalService()).getThroughTheTimeNumberTransactions(queryParameters);
    }

    @Override
    public StatisticalResultDTO getThroughTheTimePercentageNoTrade(final StatisticalActivityQuery queryParameters) {
        checkPermission();
        return ((StatisticalActivityServiceLocal) getStatisticalService()).getThroughTheTimePercentageNoTrade(queryParameters);
    }

    @Override
    public StatisticalResultDTO getToptenPersonalGrossProduct(final StatisticalActivityQuery queryParameters) {
        checkPermission();
        return ((StatisticalActivityServiceLocal) getStatisticalService()).getToptenPersonalGrossProduct(queryParameters);
    }

    @Override
    public StatisticalResultDTO getToptenPersonalLoginTimes(final StatisticalActivityQuery queryParameters) {
        checkPermission();
        return ((StatisticalActivityServiceLocal) getStatisticalService()).getToptenPersonalLoginTimes(queryParameters);
    }

    @Override
    public StatisticalResultDTO getToptenPersonalNumberTransactions(final StatisticalActivityQuery queryParameters) {
        checkPermission();
        return ((StatisticalActivityServiceLocal) getStatisticalService()).getToptenPersonalNumberTransactions(queryParameters);
    }

    public void setStatisticalActivityServiceLocal(final StatisticalActivityServiceLocal statisticalService) {
        setStatisticalServiceLocal(statisticalService);
    }

}
