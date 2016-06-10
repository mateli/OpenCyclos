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
 * Service interface for Finances Statistics. It shows the in- and outgoing finances for system accounts.
 * 
 * @author rinke
 */
public interface StatisticalFinancesService extends StatisticalService {

    /**
     * gets the Statistics on Expenditure for Compare Periods
     * 
     * @param queryParameters a StatisticalFinancesQuery containing the parameters of the form
     * @return a StatisticalResultDTO, a raw Data object needed to create graphs and tables.
     */
    StatisticalResultDTO getComparePeriodsExpenditure(StatisticalFinancesQuery queryParameters);

    /**
     * gets the Statistics on Income for Compare Periods
     * 
     * @param queryParameters a StatisticalFinancesQuery containing the parameters of the form
     * @return a StatisticalResultDTO, a raw Data object needed to create graphs and tables.
     */
    StatisticalResultDTO getComparePeriodsIncome(StatisticalFinancesQuery queryParameters);

    /**
     * gets the Statistics on Expenditure for a Single Period
     * 
     * @param queryParameters a StatisticalFinancesQuery containing the parameters of the form
     * @return a StatisticalResultDTO, a raw Data object needed to create graphs and tables.
     */
    StatisticalResultDTO getSinglePeriodExpenditure(StatisticalFinancesQuery queryParameters);

    /**
     * gets the Statistics on Income for a Single Period
     * 
     * @param queryParameters a StatisticalFinancesQuery containing the parameters of the form
     * @return a StatisticalResultDTO, a raw Data object needed to create graphs and tables.
     */
    StatisticalResultDTO getSinglePeriodIncome(StatisticalFinancesQuery queryParameters);

    /**
     * gets the Statistics on the general overview for the single period
     * 
     * @param queryParameters a StatisticalFinancesQuery containing the parameters of the form
     * @return a StatisticalResultDTO, a raw Data object needed to create graphs and tables.
     */
    StatisticalResultDTO getSinglePeriodOverview(StatisticalFinancesQuery queryParameters);

    /**
     * gets the Statistics on income through time
     * 
     * @param queryParameters a StatisticalFinancesQuery containing the parameters of the form
     * @return a StatisticalResultDTO, a raw Data object needed to create graphs and tables.
     */
    StatisticalResultDTO getThroughTimeExpenditure(StatisticalFinancesQuery queryParameters);

    /**
     * gets the Statistics on Expenditure throught time
     * 
     * @param queryParameters a StatisticalFinancesQuery containing the parameters of the form
     * @return a StatisticalResultDTO, a raw Data object needed to create graphs and tables.
     */
    StatisticalResultDTO getThroughTimeIncome(StatisticalFinancesQuery queryParameters);

}
