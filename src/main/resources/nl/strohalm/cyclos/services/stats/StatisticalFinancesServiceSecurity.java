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

import nl.strohalm.cyclos.entities.reports.StatisticalFinancesQuery;

/**
 * Security implementation for {@link StatisticalFinancesService}
 * 
 * @author Rinke
 */
public class StatisticalFinancesServiceSecurity extends StatisticalServiceSecurity implements StatisticalFinancesService {

    @Override
    public StatisticalResultDTO getComparePeriodsExpenditure(final StatisticalFinancesQuery queryParameters) {
        checkPermission();
        return ((StatisticalFinancesServiceLocal) getStatisticalService()).getComparePeriodsExpenditure(queryParameters);
    }

    @Override
    public StatisticalResultDTO getComparePeriodsIncome(final StatisticalFinancesQuery queryParameters) {
        checkPermission();
        return ((StatisticalFinancesServiceLocal) getStatisticalService()).getComparePeriodsIncome(queryParameters);
    }

    @Override
    public StatisticalResultDTO getSinglePeriodExpenditure(final StatisticalFinancesQuery queryParameters) {
        checkPermission();
        return ((StatisticalFinancesServiceLocal) getStatisticalService()).getSinglePeriodExpenditure(queryParameters);
    }

    @Override
    public StatisticalResultDTO getSinglePeriodIncome(final StatisticalFinancesQuery queryParameters) {
        checkPermission();
        return ((StatisticalFinancesServiceLocal) getStatisticalService()).getSinglePeriodIncome(queryParameters);
    }

    @Override
    public StatisticalResultDTO getSinglePeriodOverview(final StatisticalFinancesQuery queryParameters) {
        checkPermission();
        return ((StatisticalFinancesServiceLocal) getStatisticalService()).getSinglePeriodOverview(queryParameters);
    }

    @Override
    public StatisticalResultDTO getThroughTimeExpenditure(final StatisticalFinancesQuery queryParameters) {
        checkPermission();
        return ((StatisticalFinancesServiceLocal) getStatisticalService()).getThroughTimeExpenditure(queryParameters);
    }

    @Override
    public StatisticalResultDTO getThroughTimeIncome(final StatisticalFinancesQuery queryParameters) {
        checkPermission();
        return ((StatisticalFinancesServiceLocal) getStatisticalService()).getThroughTimeIncome(queryParameters);
    }

    public void setStatisticalFinancesServiceLocal(final StatisticalFinancesServiceLocal statisticalService) {
        setStatisticalServiceLocal(statisticalService);
    }

}
