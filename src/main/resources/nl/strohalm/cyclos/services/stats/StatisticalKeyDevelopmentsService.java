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

/**
 * Service interface for Key Developments Statistics.
 * @author rinke
 */
public interface StatisticalKeyDevelopmentsService extends StatisticalService {

    /**
     * gets a data value object for key statistics concerning the gross product. It should get the following data:
     * <ul>
     * <li>the gross product for the specific selected payment filter
     * <li>the gross product for all transactions
     * </ul>
     * All of these items must collect the value for two periods, plus calculated the growth percentage between these periods
     * @return a common result value object storing the data
     */
    public StatisticalResultDTO getComparePeriodsGrossProduct(StatisticalKeyDevelopmentsQuery queryParameter);

    public StatisticalResultDTO getComparePeriodsHighestTransactionAmount(StatisticalKeyDevelopmentsQuery queryParameter);

    /**
     * gets a data value object for key statistics concerning the transaction amount. It should get the following data:
     * <ul>
     * <li>median transaction amount for the specific selected payment filter
     * <li>the highest transaction amount for the specific selected payment filter
     * </ul>
     * All of these items must collect the value for two periods, plus calculated the growth percentage between these periods
     * @return a common result value object storing the data
     */
    public StatisticalResultDTO getComparePeriodsMedianAmountPerTransaction(StatisticalKeyDevelopmentsQuery queryParameter);

    /**
     * gets a data value object for key statistics concerning the number of ads. It should get the following data:
     * <ul>
     * <li>the number of ads in the period
     * <li>the number of scheduled ads
     * <li>the number of expired ads
     * </ul>
     * All of these items must collect the value for two periods, plus calculated the growth percentage between these periods
     * @return a common result value object storing the data
     */
    public StatisticalResultDTO getComparePeriodsNumberOfAds(StatisticalKeyDevelopmentsQuery queryParameter);

    /**
     * gets a data value object for key statistics concerning the number of members. It should get the following data:
     * <ul>
     * <li>the number of members
     * <li>the number of new members
     * <li>the number of disappeared members
     * </ul>
     * All of these items must collect the value for two periods, plus calculated the growth percentage between these periods
     * @return a common result value object storing the data
     */
    public StatisticalResultDTO getComparePeriodsNumberOfMembers(StatisticalKeyDevelopmentsQuery queryParameters);

    /**
     * gets a data value object for key statistics concerning the number of Transactions. It should get the following data:
     * <ul>
     * <li>the number of transactions for the specific selected payment filter
     * <li>the number of transactions for all transactions
     * </ul>
     * All of these items must collect the value for two periods, plus calculated the growth percentage between these periods
     * @return a common result value object storing the data
     */
    public StatisticalResultDTO getComparePeriodsNumberOfTransactions(StatisticalKeyDevelopmentsQuery queryParameter);

    /**
     * gets a data value object for key statistics concerning the gross product. It should get the following data:
     * <ul>
     * <li>the gross product for the specific selected payment filter
     * <li>the gross product for all transactions
     * </ul>
     * All of these items must collect the value for a single period
     * @return a common result value object storing the data
     */
    public StatisticalResultDTO getSinglePeriodGrossProduct(StatisticalKeyDevelopmentsQuery queryParameter);

    public StatisticalResultDTO getSinglePeriodHighestTransactionAmount(StatisticalKeyDevelopmentsQuery queryParameter);

    /**
     * gets a data value object for key statistics concerning the transaction amount. It should get the following data:
     * <ul>
     * <li>median transaction amount for the specific selected payment filter
     * <li>the highest transaction amount for the specific selected payment filter
     * </ul>
     * All of these items must collect the value for a single period
     * 
     * @return a common result value object storing the data
     */
    public StatisticalResultDTO getSinglePeriodMedianAmountPerTransaction(StatisticalKeyDevelopmentsQuery queryParameter);

    /**
     * gets a data value object for key statistics concerning the number of ads. It should get the following data:
     * <ul>
     * <li>the number of ads in the period
     * <li>the number of scheduled ads
     * <li>the number of expired ads
     * </ul>
     * All of these items must collect the value for a single period
     * @return a common result value object storing the data
     */
    public StatisticalResultDTO getSinglePeriodNumberOfAds(StatisticalKeyDevelopmentsQuery queryParameter);

    /**
     * gets a data value object for key statistics concerning the number of members. It should get the following data:
     * <ul>
     * <li>the number of members
     * <li>the number of new members
     * <li>the number of disappeared members
     * </ul>
     * All of these items must collect the value for a single period
     * @return a common result value object storing the data
     */
    public StatisticalResultDTO getSinglePeriodNumberOfMembers(StatisticalKeyDevelopmentsQuery queryParameters);

    /**
     * gets a data value object for key statistics concerning the number of Transactions. It should get the following data:
     * <ul>
     * <li>the number of transactions for the specific selected payment filter
     * <li>the number of transactions for all transactions
     * </ul>
     * All of these items must collect the value for a single period
     * @return a common result value object storing the data
     */
    public StatisticalResultDTO getSinglePeriodNumberOfTransactions(StatisticalKeyDevelopmentsQuery queryParameter);

    /**
     * gets a data value object for key statistics concerning the overview through the years. It should get data with all available years, for each of
     * the previous selected items (so, number of members, gross product, etc).
     * @return a common result value object storing the data
     */
    public StatisticalResultDTO getThroughTheTime(StatisticalKeyDevelopmentsQuery queryParameter);

}
