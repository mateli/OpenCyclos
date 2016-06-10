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

import nl.strohalm.cyclos.entities.reports.StatisticalKeyDevelopmentsQuery;

public class StatisticalKeyDevelopmentsServiceSecurity extends StatisticalServiceSecurity implements StatisticalKeyDevelopmentsService {

    @Override
    public StatisticalResultDTO getComparePeriodsGrossProduct(final StatisticalKeyDevelopmentsQuery queryParameter) {
        checkPermission();
        return ((StatisticalKeyDevelopmentsServiceLocal) getStatisticalService()).getComparePeriodsGrossProduct(queryParameter);
    }

    @Override
    public StatisticalResultDTO getComparePeriodsHighestTransactionAmount(final StatisticalKeyDevelopmentsQuery queryParameter) {
        checkPermission();
        return ((StatisticalKeyDevelopmentsServiceLocal) getStatisticalService()).getComparePeriodsHighestTransactionAmount(queryParameter);
    }

    @Override
    public StatisticalResultDTO getComparePeriodsMedianAmountPerTransaction(final StatisticalKeyDevelopmentsQuery queryParameter) {
        checkPermission();
        return ((StatisticalKeyDevelopmentsServiceLocal) getStatisticalService()).getComparePeriodsMedianAmountPerTransaction(queryParameter);
    }

    @Override
    public StatisticalResultDTO getComparePeriodsNumberOfAds(final StatisticalKeyDevelopmentsQuery queryParameter) {
        checkPermission();
        return ((StatisticalKeyDevelopmentsServiceLocal) getStatisticalService()).getComparePeriodsNumberOfAds(queryParameter);
    }

    @Override
    public StatisticalResultDTO getComparePeriodsNumberOfMembers(final StatisticalKeyDevelopmentsQuery queryParameters) {
        checkPermission();
        return ((StatisticalKeyDevelopmentsServiceLocal) getStatisticalService()).getComparePeriodsNumberOfMembers(queryParameters);
    }

    @Override
    public StatisticalResultDTO getComparePeriodsNumberOfTransactions(final StatisticalKeyDevelopmentsQuery queryParameter) {
        checkPermission();
        return ((StatisticalKeyDevelopmentsServiceLocal) getStatisticalService()).getComparePeriodsNumberOfTransactions(queryParameter);
    }

    @Override
    public StatisticalResultDTO getSinglePeriodGrossProduct(final StatisticalKeyDevelopmentsQuery queryParameter) {
        checkPermission();
        return ((StatisticalKeyDevelopmentsServiceLocal) getStatisticalService()).getSinglePeriodGrossProduct(queryParameter);
    }

    @Override
    public StatisticalResultDTO getSinglePeriodHighestTransactionAmount(final StatisticalKeyDevelopmentsQuery queryParameter) {
        checkPermission();
        return ((StatisticalKeyDevelopmentsServiceLocal) getStatisticalService()).getSinglePeriodHighestTransactionAmount(queryParameter);
    }

    @Override
    public StatisticalResultDTO getSinglePeriodMedianAmountPerTransaction(final StatisticalKeyDevelopmentsQuery queryParameter) {
        checkPermission();
        return ((StatisticalKeyDevelopmentsServiceLocal) getStatisticalService()).getSinglePeriodMedianAmountPerTransaction(queryParameter);
    }

    @Override
    public StatisticalResultDTO getSinglePeriodNumberOfAds(final StatisticalKeyDevelopmentsQuery queryParameter) {
        checkPermission();
        return ((StatisticalKeyDevelopmentsServiceLocal) getStatisticalService()).getSinglePeriodNumberOfAds(queryParameter);
    }

    @Override
    public StatisticalResultDTO getSinglePeriodNumberOfMembers(final StatisticalKeyDevelopmentsQuery queryParameters) {
        checkPermission();
        return ((StatisticalKeyDevelopmentsServiceLocal) getStatisticalService()).getSinglePeriodNumberOfMembers(queryParameters);
    }

    @Override
    public StatisticalResultDTO getSinglePeriodNumberOfTransactions(final StatisticalKeyDevelopmentsQuery queryParameter) {
        checkPermission();
        return ((StatisticalKeyDevelopmentsServiceLocal) getStatisticalService()).getSinglePeriodNumberOfTransactions(queryParameter);
    }

    @Override
    public StatisticalResultDTO getThroughTheTime(final StatisticalKeyDevelopmentsQuery queryParameter) {
        checkPermission();
        return ((StatisticalKeyDevelopmentsServiceLocal) getStatisticalService()).getThroughTheTime(queryParameter);
    }

    public void setStatisticalKeyDevelopmentsServiceLocal(final StatisticalKeyDevelopmentsServiceLocal statisticalService) {
        setStatisticalServiceLocal(statisticalService);
    }

}
