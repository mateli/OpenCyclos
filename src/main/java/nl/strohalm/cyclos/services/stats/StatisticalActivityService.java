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
 * Service interface for Activity Statistics.
 * @author rinke
 */
public interface StatisticalActivityService extends StatisticalService {

    /**
     * gets a data value object for the comparison of the gross product per member for two periods. It should get the values for the two periods, the
     * relative growth percentage between them, and the p-value (indicating if the difference was statistically significant). A value for a period
     * consists of the median amount, plus a number indication the 95%-confidence interval.
     * @param queryParameters the form parameters
     * @return a common result value object storing the data
     */
    StatisticalResultDTO getComparePeriodsGrossProduct(StatisticalActivityQuery queryParameters);

    /**
     * gets the frequency of logging in of members (per week), comparing the two periods. It should get the values for the two periods, the relative
     * growth percentage between them, and the p-value (indicating if the difference was statistically significant). A value for a period consists of
     * the median amount, plus a number indication the 95%-confidence interval.
     * @param queryParameters the form params
     * @return a common result value object storing the data
     */
    StatisticalResultDTO getComparePeriodsLoginTimes(StatisticalActivityQuery queryParameters);

    /**
     * gets the data for comparison of the number of transactions for two periods. It should get the values for the two periods, the relative growth
     * percentage between them, and the p-value (indicating if the difference was statistically significant). A value for a period consists of the
     * median amount, plus a number indication the 95%-confidence interval.
     * @param queryParameters the form params
     * @return a common result value object storing the data
     */
    StatisticalResultDTO getComparePeriodsNumberTransactions(StatisticalActivityQuery queryParameters);

    /**
     * gets the data on the percentage of members which is not trading, for comparing these. over two periods. It should get the values for the two
     * periods, the relative growth percentage between them, and the p-value (indicating if the difference was statistically significant). A value for
     * a period consists of the median amount, plus a number indication the 95%-confidence interval.
     * @param queryParameters the form params
     * @return a common result value object storing the data
     */
    StatisticalResultDTO getComparePeriodsPercentageNoTrade(StatisticalActivityQuery queryParameters);

    /**
     * gets the data for creating a histogram of the distribution of gross product over the members. This data should split up a set of numbers (for
     * each member the personal gross product), and split this set up into reasonable categories, indicating the number of members found in each
     * categorie. This splitting up is done by the class <code>HistogramDTOFactory</code>.
     * @param queryParameters the form params
     * @return a common result value object storing the data
     * @see HistogramDTOFactory
     */
    StatisticalResultDTO getHistogramGrossProduct(StatisticalActivityQuery queryParameters);

    /**
     * gets the data for creating a histogram of the distribution of login frequency over the members. This data should split up a set of numbers (for
     * each member the personal login frequency per week), and split this set up into reasonable categories, indicating the number of members found in
     * each categorie. This splitting up is done by the class <code>HistogramDTOFactory</code>.
     * @param queryParameters the form params
     * @return a common result value object storing the data
     * @see HistogramDTOFactory
     */
    StatisticalResultDTO getHistogramLoginTimes(StatisticalActivityQuery queryParameters);

    /**
     * gets the data for creating a histogram of the distribution of the number of transactions over the members. This data should split up a set of
     * numbers (for each member the personal number of transactions), and split this set up into reasonable categories, indicating the number of
     * members found in each categorie. This splitting up is done by the class <code>HistogramDTOFactory</code>.
     * @param queryParameters the form params
     * @return a common result value object storing the data
     * @see HistogramDTOFactory
     */
    StatisticalResultDTO getHistogramNumberTransactions(StatisticalActivityQuery queryParameters);

    /**
     * gets the data value object for the gross product of a single period. A value for a period consists of the median amount, plus a number
     * indication the 95%-confidence interval.
     * @param queryParameters the form parameters
     * @return a common result value object storing the data
     */
    StatisticalResultDTO getSinglePeriodGrossProduct(StatisticalActivityQuery queryParameters);

    /**
     * gets the data value object for the frequency of logging in of members on a single period. A value for the period consists of the median amount,
     * plus a number indication the 95%-confidence interval.
     * @param queryParameters the form params
     * @return a common result value object storing the data
     */
    StatisticalResultDTO getSinglePeriodLoginTimes(StatisticalActivityQuery queryParameters);

    /**
     * gets the data value object for the number of transactions of a single period. A value for a period consists of the median amount, plus a number
     * indication the 95%-confidence interval.
     * @param queryParameters the form params
     * @return a common result value object storing the data
     */
    StatisticalResultDTO getSinglePeriodNumberTransactions(StatisticalActivityQuery queryParameters);

    /**
     * gets the data value object for the percentage of members which is not trading. A value for the period consists of the median amount, plus a
     * number indication the 95%-confidence interval.
     * @param queryParameters the form params
     * @return a common result value object storing the data
     */
    StatisticalResultDTO getSinglePeriodPercentageNoTrade(StatisticalActivityQuery queryParameters);

    /**
     * gets the data for the gross product per member, for all available years. A value for a year consists of the median amount, plus a number
     * indication the 95%-confidence interval.
     * @param queryParameters the form params
     * @return a common result value object storing the data
     */
    StatisticalResultDTO getThroughTheTimeGrossProduct(final StatisticalActivityQuery queryParameters);

    /**
     * gets the data for the login frequency per member, for all available years. A value for a year consists of the median amount, plus a number
     * indication the 95%-confidence interval.
     * @param queryParameters the form params
     * @return a common result value object storing the data
     */
    StatisticalResultDTO getThroughTheTimeLoginTimes(StatisticalActivityQuery queryParameters);

    /**
     * gets the data for the number of transactions per member, for all available years. A value for a year consists of the median amount, plus a
     * number indication the 95%-confidence interval.
     * @param queryParameters the form params
     * @return a common result value object storing the data
     */
    StatisticalResultDTO getThroughTheTimeNumberTransactions(StatisticalActivityQuery queryParameters);

    /**
     * gets the data for the percentage of members not trading during that specific year, for all available years. A value for a year consists of the
     * median amount, plus a number indication the 95%-confidence interval.
     * @param queryParameters the form params
     * @return a common result value object storing the data
     */
    StatisticalResultDTO getThroughTheTimePercentageNoTrade(StatisticalActivityQuery queryParameters);

    /**
     * Top 10 for persons: who are the most active members, for personal gross product.
     * @param queryParameters the form params
     * @return a common result valur object storing the data
     */
    StatisticalResultDTO getToptenPersonalGrossProduct(final StatisticalActivityQuery queryParameters);

    /**
     * Top 10 for persons: who are the most active members, for personal login times.
     * @param queryParameters the form params
     * @return a common result valur object storing the data
     */
    StatisticalResultDTO getToptenPersonalLoginTimes(final StatisticalActivityQuery queryParameters);

    /**
     * Top 10 for persons: who are the most active members, for personal number transactions.
     * @param queryParameters the form params
     * @return a common result valur object storing the data
     */
    StatisticalResultDTO getToptenPersonalNumberTransactions(final StatisticalActivityQuery queryParameters);

}
