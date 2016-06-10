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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.strohalm.cyclos.dao.access.LoginHistoryDAO;
import nl.strohalm.cyclos.entities.Entity;
import nl.strohalm.cyclos.entities.access.User;
import nl.strohalm.cyclos.entities.accounts.Currency;
import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.reports.StatisticalActivityQuery;
import nl.strohalm.cyclos.entities.reports.StatisticalNumber;
import nl.strohalm.cyclos.entities.reports.StatisticalQuery;
import nl.strohalm.cyclos.entities.reports.StatisticsWhatToShow;
import nl.strohalm.cyclos.services.stats.activity.GrossProductPerMemberStats;
import nl.strohalm.cyclos.services.stats.activity.LoginTimesPerMemberStats;
import nl.strohalm.cyclos.services.stats.activity.TransactionCountPerMemberStats;
import nl.strohalm.cyclos.utils.Pair;
import nl.strohalm.cyclos.utils.Period;
import nl.strohalm.cyclos.utils.statistics.ListOperations;
import nl.strohalm.cyclos.utils.statistics.Median;

/**
 * An implementation of the StatisticalActivityService interface; subclasses StatisticalService.
 * @author rinke, jefferson, jeancarlo
 */
public class StatisticalActivityServiceImpl extends StatisticalServiceImpl implements StatisticalActivityServiceLocal {

    private LoginHistoryDAO      loginHistoryDao;
    /**
     * a cached Map with the number of members per group per period. The key is the period.toString(), the value is the number of members in that
     * period.
     */
    private Map<String, Integer> numberOfMembersPerGroupPerPeriod;
    /**
     * cached statisticalQuery. Should not have changed when using numberOfMembersPerGroupPerPeriod.
     */
    private StatisticalQuery     cachedQueryParameters;

    /**
     * Gets the results table for comparing the gross product for two periods
     * 
     * @param queryParameters
     * @return a StatisticalResultDTO containing the median and n for gross products of members for two different periods.
     */
    @Override
    public StatisticalResultDTO getComparePeriodsGrossProduct(final StatisticalActivityQuery queryParameters) {
        final Period periodMain = queryParameters.getPeriodMain();
        final Period periodComparedTo = queryParameters.getPeriodComparedTo();
        getInitializedPaymentFilter(queryParameters);

        // Assign lists main period
        final GrossProductPerMemberStats statsMainPeriod = new GrossProductPerMemberStats(queryParameters, periodMain, getTransferDao());
        final double[] periodMainSumOfTraders = statsMainPeriod.getGrossProductPerTradingMember();
        final int periodMainNoOfTraders = periodMainSumOfTraders.length;
        final double[] periodMainSumOfMembers = statsMainPeriod.getVolumePerAllMembers(getNumberOfMembersForPeriod(queryParameters, periodMain));
        final int periodMainNoOfMembers = periodMainSumOfMembers.length;

        // Assign lists compared to period
        final GrossProductPerMemberStats statsCompared = new GrossProductPerMemberStats(queryParameters, periodComparedTo, getTransferDao());
        final double[] periodComparedSumOfTraders = statsCompared.getGrossProductPerTradingMember();
        final int periodComparedNofTraders = periodComparedSumOfTraders.length;
        final double[] periodComparedSumOfMembers = statsCompared.getVolumePerAllMembers(getNumberOfMembersForPeriod(queryParameters, periodComparedTo));
        final int periodComparedNofMembers = periodComparedSumOfMembers.length;

        // Initialize
        StatisticalResultDTO result = null;
        final String baseKey = "reports.stats.activity.comparePeriods.grossProduct";

        // Assignment of results
        if (periodMainNoOfTraders >= MINIMUM_NUMBER_OF_VALUES || periodComparedNofTraders >= MINIMUM_NUMBER_OF_VALUES || periodMainNoOfMembers >= MINIMUM_NUMBER_OF_VALUES || periodComparedNofMembers >= MINIMUM_NUMBER_OF_VALUES) {
            final StatisticalNumber periodMainMedianGrossProductPerTrader = Median.getMedian(periodMainSumOfTraders, StatisticalService.ALPHA);
            final StatisticalNumber periodComparedToMedianGrossProductPerTrader = Median.getMedian(periodComparedSumOfTraders, StatisticalService.ALPHA);
            final StatisticalNumber periodMainMedianGrossProductPerMember = Median.getMedian(periodMainSumOfMembers, StatisticalService.ALPHA);
            final StatisticalNumber periodComparedToMedianGrossProductPerMember = Median.getMedian(periodComparedSumOfMembers, StatisticalService.ALPHA);
            final StatisticalNumber pTraders = StatisticalServiceImpl.calculatePvalue(periodMainSumOfTraders, periodComparedSumOfTraders);
            final StatisticalNumber pAllMembers = StatisticalServiceImpl.calculatePvalue(periodMainSumOfMembers, periodComparedSumOfMembers);

            // Assign table values
            final Number[][] tableCells = new Number[2][6];
            tableCells[0][0] = periodComparedToMedianGrossProductPerTrader;
            tableCells[0][1] = periodMainMedianGrossProductPerTrader;
            tableCells[0][2] = new StatisticalNumber(periodComparedNofTraders);
            tableCells[0][3] = new StatisticalNumber(periodMainNoOfTraders);
            tableCells[0][4] = StatisticalNumber.createPercentage(periodMainMedianGrossProductPerTrader, periodComparedToMedianGrossProductPerTrader);
            tableCells[0][5] = pTraders;
            tableCells[1][0] = periodComparedToMedianGrossProductPerMember;
            tableCells[1][1] = periodMainMedianGrossProductPerMember;
            tableCells[1][2] = new StatisticalNumber(periodComparedNofMembers);
            tableCells[1][3] = new StatisticalNumber(periodMainNoOfMembers);
            tableCells[1][4] = StatisticalNumber.createPercentage(periodMainMedianGrossProductPerMember, periodComparedToMedianGrossProductPerMember);
            tableCells[1][5] = pAllMembers;

            result = new StatisticalResultDTO(tableCells);
            result.setBaseKey(baseKey);

            passGroupFilter(result, queryParameters);
            passPaymentFilter(result, queryParameters);
            setGeneralsForCompare2Periods(result, queryParameters, 2);
            // set the currency subHeaders AFTER setGenerals, because subHeaders are overwritten again
            final Currency currency = getCurrency(queryParameters);
            final String[] subHeaders = new String[] { parenthesizeString(currency.getSymbol()), parenthesizeString(currency.getSymbol()), queryParameters.getPeriodComparedTo().getName(), queryParameters.getPeriodMain().getName(), "", "" };
            result.setColumnSubHeaders(subHeaders);
            result.setYAxisUnits(currency.getSymbol());

            if (queryParameters.isGrossProductGraph()) {
                result.setGraphDimensions(null, 2, null);
                result.setGraphType(StatisticalResultDTO.GraphType.BAR);
            }
        } else {
            result = StatisticalResultDTO.noDataAvailable(baseKey);
        }
        return result;
    }

    @Override
    public StatisticalResultDTO getComparePeriodsLoginTimes(final StatisticalActivityQuery queryParameters) {
        final Period periodMain = queryParameters.getPeriodMain();
        final Period periodComparedTo = queryParameters.getPeriodComparedTo();

        // Main period
        final LoginTimesPerMemberStats statsMainPeriod = new LoginTimesPerMemberStats(queryParameters, periodMain, loginHistoryDao);
        final List<Number> periodMainLoginTimes = statsMainPeriod.getListLoginTimes();
        final int periodMainNoOfMembers = periodMainLoginTimes.size();

        // Compared to period
        final LoginTimesPerMemberStats statsComparedPeriod = new LoginTimesPerMemberStats(queryParameters, periodComparedTo, loginHistoryDao);
        final List<Number> periodComparedLoginTimes = statsComparedPeriod.getListLoginTimes();
        final int periodComparedNoOfMembers = periodComparedLoginTimes.size();

        StatisticalResultDTO result = null;
        final String baseKey = "reports.stats.activity.comparePeriods.loginTimes";

        if (periodMainNoOfMembers >= MINIMUM_NUMBER_OF_VALUES || periodComparedNoOfMembers >= MINIMUM_NUMBER_OF_VALUES) {

            final StatisticalNumber periodMainMedianLoginTimesPerMember = Median.getMedian(periodMainLoginTimes, StatisticalService.ALPHA);
            final StatisticalNumber periodComparedToMedianLoginTimesPerMember = Median.getMedian(periodComparedLoginTimes, StatisticalService.ALPHA);
            final StatisticalNumber pValue = StatisticalServiceImpl.calculatePvalue(periodMainLoginTimes, periodComparedLoginTimes);

            final Number[][] tableCells = new Number[1][4];
            tableCells[0][1] = periodMainMedianLoginTimesPerMember;
            tableCells[0][0] = periodComparedToMedianLoginTimesPerMember;
            tableCells[0][2] = StatisticalNumber.createPercentage(periodMainMedianLoginTimesPerMember, periodComparedToMedianLoginTimesPerMember);
            tableCells[0][3] = pValue;

            result = new StatisticalResultDTO(tableCells);
            result.setBaseKey(baseKey);
            passGroupFilter(result, queryParameters);

            setGeneralsForCompare2Periods(result, queryParameters, 1);
            final String[] columnSubHeaders = { "(n=" + periodComparedNoOfMembers + ")", "(n=" + periodMainNoOfMembers + ")", "", "" };
            result.setColumnSubHeaders(columnSubHeaders);
            if (queryParameters.isLoginTimesGraph()) {
                result.setGraphDimensions(null, 2, null);
                result.setGraphType(StatisticalResultDTO.GraphType.BAR);
            }

        } else {
            result = StatisticalResultDTO.noDataAvailable(baseKey);
        }
        return result;
    }

    /**
     * Gets the results table for comparing the number of transactions per member for two periods. Transactions are incoming AND outgoing
     * transactions, in contrast to grossproduct, which concerns only incoming
     * 
     * @param queryParameters
     * @return a StatisticalResultDTO containing the median and n for number of transactions per member for two different periods.
     */
    @Override
    public StatisticalResultDTO getComparePeriodsNumberTransactions(final StatisticalActivityQuery queryParameters) {
        final Period periodMain = queryParameters.getPeriodMain();
        final Period periodComparedTo = queryParameters.getPeriodComparedTo();
        getInitializedPaymentFilter(queryParameters);

        // Main period
        final TransactionCountPerMemberStats statsMainPeriod = new TransactionCountPerMemberStats(queryParameters, periodMain, getTransferDao(), getElementDao());
        final List<Number> periodMainTransactionCountTraders = statsMainPeriod.getTransactionCountPerTradingMember();
        final int periodMainNofTraders = periodMainTransactionCountTraders.size();
        final List<Number> periodMainTransactionCountMembers = statsMainPeriod.getTransactionCountPerAllMembers(getNumberOfMembersForPeriod(queryParameters, periodMain));
        final int periodMainNofMembers = periodMainTransactionCountMembers.size();

        // Compared to period
        final TransactionCountPerMemberStats statsCompared = new TransactionCountPerMemberStats(queryParameters, periodComparedTo, getTransferDao(), getElementDao());
        final List<Number> periodComparedTransactionCountTraders = statsCompared.getTransactionCountPerTradingMember();
        final int periodComparedNofTraders = periodComparedTransactionCountTraders.size();
        final List<Number> periodComparedTransactionCountMembers = statsCompared.getTransactionCountPerAllMembers(getNumberOfMembersForPeriod(queryParameters, periodComparedTo));
        final int periodComparedNofMembers = periodComparedTransactionCountMembers.size();

        // Initialize
        StatisticalResultDTO result = null;
        final String baseKey = "reports.stats.activity.comparePeriods.numberTransactions";

        if (periodMainNofTraders >= MINIMUM_NUMBER_OF_VALUES || periodComparedNofTraders >= MINIMUM_NUMBER_OF_VALUES || periodMainNofMembers >= MINIMUM_NUMBER_OF_VALUES || periodComparedNofMembers >= MINIMUM_NUMBER_OF_VALUES) {
            final StatisticalNumber periodMainMedianTransactionCountPerTrader = Median.getMedian(periodMainTransactionCountTraders, StatisticalService.ALPHA);
            final StatisticalNumber periodComparedMedianTransactionCountPerTrader = Median.getMedian(periodComparedTransactionCountTraders, StatisticalService.ALPHA);
            final StatisticalNumber periodMainMedianTransactionCountPerMember = Median.getMedian(periodMainTransactionCountMembers, StatisticalService.ALPHA);
            final StatisticalNumber periodComparedMedianTransactionCountPerMember = Median.getMedian(periodComparedTransactionCountMembers, StatisticalService.ALPHA);
            final StatisticalNumber pTraders = StatisticalServiceImpl.calculatePvalue(ListOperations.listToArray(periodMainTransactionCountTraders), ListOperations.listToArray(periodComparedTransactionCountTraders));
            final StatisticalNumber pMembers = StatisticalServiceImpl.calculatePvalue(ListOperations.listToArray(periodMainTransactionCountMembers), ListOperations.listToArray(periodComparedTransactionCountMembers));

            final Number[][] tableCells = new Number[2][6];

            // Row for members which did the transfers
            tableCells[0][0] = periodComparedMedianTransactionCountPerTrader;
            tableCells[0][1] = periodMainMedianTransactionCountPerTrader;
            tableCells[0][2] = periodComparedNofTraders;
            tableCells[0][3] = periodMainNofTraders;
            tableCells[0][4] = StatisticalNumber.createPercentage(periodMainMedianTransactionCountPerTrader, periodComparedMedianTransactionCountPerTrader);
            tableCells[0][5] = pTraders;

            // Row for all the members.
            tableCells[1][0] = periodComparedMedianTransactionCountPerMember;
            tableCells[1][1] = periodMainMedianTransactionCountPerMember;
            tableCells[1][2] = periodComparedNofMembers;
            tableCells[1][3] = periodMainNofMembers;
            tableCells[1][4] = StatisticalNumber.createPercentage(periodMainMedianTransactionCountPerMember, periodComparedMedianTransactionCountPerMember);
            tableCells[1][5] = pMembers;

            result = new StatisticalResultDTO(tableCells);
            result.setBaseKey(baseKey);
            passGroupFilter(result, queryParameters);
            passPaymentFilter(result, queryParameters);
            setGeneralsForCompare2Periods(result, queryParameters, 2);
            if (queryParameters.isNumberTransactionsGraph()) {
                result.setGraphDimensions(null, 2, null);
                result.setGraphType(StatisticalResultDTO.GraphType.BAR);
            }
        } else {
            result = StatisticalResultDTO.noDataAvailable(baseKey);
        }
        return result;
    }

    /**
     * gets the percentage of members not trading, where trade concerns incoming and outgoing transactions.
     */
    @Override
    public StatisticalResultDTO getComparePeriodsPercentageNoTrade(final StatisticalActivityQuery queryParameters) {
        final Period periodMain = queryParameters.getPeriodMain();
        final Period periodComparedTo = queryParameters.getPeriodComparedTo();
        getInitializedPaymentFilter(queryParameters);

        // Main period
        final TransactionCountPerMemberStats statsMainPeriod = new TransactionCountPerMemberStats(queryParameters, periodMain, getTransferDao(), getElementDao());
        final int periodMainNoOfTraders = statsMainPeriod.getTransactionCountPerTradingMember().size();
        final int periodMainNoOfMembers = statsMainPeriod.getTransactionCountPerAllMembers(getNumberOfMembersForPeriod(queryParameters, periodMain)).size();

        // Compared to period
        final TransactionCountPerMemberStats statsCompared = new TransactionCountPerMemberStats(queryParameters, periodComparedTo, getTransferDao(), getElementDao());
        final int periodComparedNoOfTraders = statsCompared.getTransactionCountPerTradingMember().size();
        final int periodComparedNoOfMembers = statsCompared.getTransactionCountPerAllMembers(getNumberOfMembersForPeriod(queryParameters, periodComparedTo)).size();

        // Data structure to build the table
        final Number[][] tableCells = new Number[1][4];
        StatisticalResultDTO result = null;
        final String baseKey = "reports.stats.activity.comparePeriods.percentageNoTrade";

        if (periodMainNoOfMembers >= StatisticalService.MINIMUM_NUMBER_OF_VALUES || periodComparedNoOfMembers >= StatisticalService.MINIMUM_NUMBER_OF_VALUES) {

            final StatisticalNumber percentageNoTradersMain = TransactionCountPerMemberStats.getPercentageNoTraders(periodMainNoOfTraders, periodMainNoOfMembers);
            final StatisticalNumber percentageNoTradersCompared = TransactionCountPerMemberStats.getPercentageNoTraders(periodComparedNoOfTraders, periodComparedNoOfMembers);
            final StatisticalNumber pValue = StatisticalServiceImpl.calculatePvalue(periodMainNoOfTraders, periodMainNoOfMembers, periodComparedNoOfTraders, periodComparedNoOfMembers);

            tableCells[0][0] = percentageNoTradersCompared;
            tableCells[0][1] = percentageNoTradersMain;
            tableCells[0][2] = StatisticalNumber.createPercentage(percentageNoTradersMain, percentageNoTradersCompared);
            tableCells[0][3] = pValue;

            result = new StatisticalResultDTO(tableCells);
            result.setBaseKey(baseKey);
            passGroupFilter(result, queryParameters);
            passPaymentFilter(result, queryParameters);
            setGeneralsForCompare2Periods(result, queryParameters, 1);
            final String[] columnSubHeaders = { "(n=" + periodComparedNoOfMembers + ")", "(n=" + periodMainNoOfMembers + ")", "", "" };
            result.setColumnSubHeaders(columnSubHeaders);
            if (queryParameters.isPercentageNoTradeGraph()) {
                result.setGraphDimensions(null, 2, null);
                result.setGraphType(StatisticalResultDTO.GraphType.BAR);
            }
        } else {
            result = StatisticalResultDTO.noDataAvailable(baseKey);
        }
        return result;
    }

    @Override
    public StatisticalResultDTO getHistogramGrossProduct(final StatisticalActivityQuery queryParameters) {
        Period period = queryParameters.getPeriodMain();
        final GrossProductPerMemberStats statsPeriod = new GrossProductPerMemberStats(queryParameters, period, getTransferDao());
        final List<Number> listGrossProductPerAllMembers = statsPeriod.getListVolumePerAllMembers(getNumberOfMembersForPeriod(queryParameters, period));
        final HistogramDTOFactory histoFactory = new HistogramDTOFactory(listGrossProductPerAllMembers, getLocalSettings());
        final String baseKey = "reports.stats.activity.histogram.grossProduct";
        final StatisticalResultDTO result = histoFactory.getResultObject(baseKey);
        result.setColumnKeys(new String[] { "reports.stats.activity.histogram.grossProduct.xAxis" });
        final Currency currency = getCurrency(queryParameters);
        result.setXAxisUnits(currency.getSymbol());
        passGroupFilter(result, queryParameters);
        passPaymentFilter(result, queryParameters);
        return result;
    }

    @Override
    public StatisticalResultDTO getHistogramLoginTimes(final StatisticalActivityQuery queryParameters) {
        final LoginTimesPerMemberStats statsPeriod = new LoginTimesPerMemberStats(queryParameters, queryParameters.getPeriodMain(), loginHistoryDao);
        final List<Number> listLoginTimes = statsPeriod.getListLoginTimes();
        final HistogramDTOFactory histoFactory = new HistogramDTOFactory(listLoginTimes, getLocalSettings());
        final String baseKey = "reports.stats.activity.histogram.logins";
        final StatisticalResultDTO result = histoFactory.getResultObject(baseKey);
        result.setColumnKeys(new String[] { "reports.stats.activity.histogram.logins.xAxis" });
        passGroupFilter(result, queryParameters);
        return result;
    }

    @Override
    public StatisticalResultDTO getHistogramNumberTransactions(final StatisticalActivityQuery queryParameters) {
        Period periodMain = queryParameters.getPeriodMain();
        final TransactionCountPerMemberStats statsPeriod = new TransactionCountPerMemberStats(queryParameters, periodMain, getTransferDao(), getElementDao());
        final List<Number> transactionCountPerAllMembers = statsPeriod.getTransactionCountPerAllMembers(getNumberOfMembersForPeriod(queryParameters, periodMain));
        final HistogramDTOFactory histoFactory = new HistogramDTOFactory(transactionCountPerAllMembers, getLocalSettings());
        final String baseKey = "reports.stats.activity.histogram.numberTransactions";
        final StatisticalResultDTO result = histoFactory.getResultObject(baseKey);
        result.setColumnKeys(new String[] { "reports.stats.activity.histogram.numberTransactions.xAxis" });
        passGroupFilter(result, queryParameters);
        passPaymentFilter(result, queryParameters);
        return result;
    }

    /**
     * Gets the results table for the gross product of a single period
     * 
     * @param queryParameters
     * @return a StatisticalResultDTO containing the median gross product and the number of members for a single period.
     */
    @Override
    public StatisticalResultDTO getSinglePeriodGrossProduct(final StatisticalActivityQuery queryParameters) {
        getInitializedPaymentFilter(queryParameters);

        // Assign lists
        final Period periodMain = queryParameters.getPeriodMain();
        final GrossProductPerMemberStats statsMainPeriod = new GrossProductPerMemberStats(queryParameters, periodMain, getTransferDao());

        final double[] periodMainSumOfTraders = statsMainPeriod.getGrossProductPerTradingMember();
        final int periodMainNofTraders = periodMainSumOfTraders.length;

        // Trading members
        // All members
        final double[] periodMainSumOfMembers = statsMainPeriod.getVolumePerAllMembers(getNumberOfMembersForPeriod(queryParameters, periodMain));
        final int periodMainNofMembers = periodMainSumOfMembers.length;

        // Initialize
        StatisticalResultDTO result = null;
        final String baseKey = "reports.stats.activity.singlePeriod.grossProduct";

        // Assignment of results
        if (periodMainNofTraders >= MINIMUM_NUMBER_OF_VALUES || periodMainNofMembers >= MINIMUM_NUMBER_OF_VALUES) {
            final StatisticalNumber periodMainMedianGrossProductPerTrader = Median.getMedian(periodMainSumOfTraders, StatisticalService.ALPHA);
            final StatisticalNumber periodMainMedianGrossProductPerMember = Median.getMedian(periodMainSumOfMembers, StatisticalService.ALPHA);

            // Assign table values
            final Number[][] tableCells = new Number[2][2];

            // Trading members
            tableCells[0][0] = periodMainMedianGrossProductPerTrader;
            tableCells[0][1] = new StatisticalNumber(periodMainNofTraders);

            // All members
            tableCells[1][0] = periodMainMedianGrossProductPerMember;
            tableCells[1][1] = new StatisticalNumber(periodMainNofMembers);

            result = new StatisticalResultDTO(tableCells);
            result.setBaseKey(baseKey);

            passGroupFilter(result, queryParameters);
            passPaymentFilter(result, queryParameters);
            setGeneralsForSinglePeriod(result, queryParameters, 2, true);
            // set the currency subHeaders AFTER setGenerals, because subHeaders are overwritten again
            final Currency currency = getCurrency(queryParameters);
            final String[] subHeaders = new String[] { parenthesizeString(currency.getSymbol()), queryParameters.getPeriodMain().getName() };
            result.setColumnSubHeaders(subHeaders);
            result.setYAxisUnits(currency.getSymbol());

        } else {
            result = StatisticalResultDTO.noDataAvailable(baseKey);
        }
        return result;
    }

    @Override
    public StatisticalResultDTO getSinglePeriodLoginTimes(final StatisticalActivityQuery queryParameters) {
        final Period periodMain = queryParameters.getPeriodMain();
        final LoginTimesPerMemberStats statsMainPeriod = new LoginTimesPerMemberStats(queryParameters, periodMain, loginHistoryDao);
        final List<Number> periodMainLoginTimes = statsMainPeriod.getListLoginTimes();
        final int periodMainNoOfMembers = periodMainLoginTimes.size();
        StatisticalResultDTO result = null;
        final String baseKey = "reports.stats.activity.singlePeriod.loginTimes";
        if (periodMainNoOfMembers >= MINIMUM_NUMBER_OF_VALUES) {
            final StatisticalNumber periodMainMedianLoginTimesPerMember = Median.getMedian(periodMainLoginTimes, StatisticalService.ALPHA);
            final Number[][] tableCells = new Number[1][1];
            tableCells[0][0] = periodMainMedianLoginTimesPerMember;
            result = new StatisticalResultDTO(tableCells);
            result.setBaseKey(baseKey);
            passGroupFilter(result, queryParameters);
            setGeneralsForSinglePeriod(result, queryParameters, 1, false);
            final String[] columnSubHeaders = { "(n=" + periodMainNoOfMembers + ")" };
            result.setColumnSubHeaders(columnSubHeaders);
        } else {
            result = StatisticalResultDTO.noDataAvailable(baseKey);
        }
        return result;
    }

    /**
     * Gets the results table for the number of transactions per member for a single period
     * 
     * @param queryParameters
     * @return a StatisticalResultDTO containing the median and n for number of transactions per member for two different periods.
     */
    @Override
    public StatisticalResultDTO getSinglePeriodNumberTransactions(final StatisticalActivityQuery queryParameters) {
        getInitializedPaymentFilter(queryParameters);
        final Period periodMain = queryParameters.getPeriodMain();
        final TransactionCountPerMemberStats statsMainPeriod = new TransactionCountPerMemberStats(queryParameters, periodMain, getTransferDao(), getElementDao());

        // Trading members
        final List<Number> periodMainTransactionCountTraders = statsMainPeriod.getTransactionCountPerTradingMember();
        final int periodMainNofTraders = periodMainTransactionCountTraders.size();

        // All members
        final List<Number> periodMainTransactionCountMembers = statsMainPeriod.getTransactionCountPerAllMembers(getNumberOfMembersForPeriod(queryParameters, periodMain));
        final int periodMainNofMembers = periodMainTransactionCountMembers.size();

        // Initialize
        StatisticalResultDTO result = null;
        final String baseKey = "reports.stats.activity.singlePeriod.numberTransactions";

        if (periodMainNofTraders >= MINIMUM_NUMBER_OF_VALUES || periodMainNofMembers >= MINIMUM_NUMBER_OF_VALUES) {
            // Calculate median
            final StatisticalNumber periodMainMedianTransactionCountPerTrader = Median.getMedian(periodMainTransactionCountTraders, StatisticalService.ALPHA);
            final StatisticalNumber periodMainMedianTransactionCountPerMember = Median.getMedian(periodMainTransactionCountMembers, StatisticalService.ALPHA);

            final Number[][] tableCells = new Number[2][2];

            // Row for members which did the transfers
            tableCells[0][0] = periodMainMedianTransactionCountPerTrader;
            tableCells[0][1] = periodMainNofTraders;

            // Row for all the members.
            tableCells[1][0] = periodMainMedianTransactionCountPerMember;
            tableCells[1][1] = periodMainNofMembers;

            result = new StatisticalResultDTO(tableCells);
            result.setBaseKey(baseKey);
            passGroupFilter(result, queryParameters);
            passPaymentFilter(result, queryParameters);
            setGeneralsForSinglePeriod(result, queryParameters, 2, true);
        } else {
            result = StatisticalResultDTO.noDataAvailable(baseKey);
        }
        return result;
    }

    @Override
    public StatisticalResultDTO getSinglePeriodPercentageNoTrade(final StatisticalActivityQuery queryParameters) {
        getInitializedPaymentFilter(queryParameters);
        final Period periodMain = queryParameters.getPeriodMain();
        final TransactionCountPerMemberStats statsMainPeriod = new TransactionCountPerMemberStats(queryParameters, periodMain, getTransferDao(), getElementDao());
        final int periodMainNoOfTraders = statsMainPeriod.getTransactionCountPerTradingMember().size();
        final int periodMainNoOfMembers = statsMainPeriod.getTransactionCountPerAllMembers(getNumberOfMembersForPeriod(queryParameters, periodMain)).size();

        // Data structure to build the table
        final Number[][] tableCells = new Number[1][1];
        StatisticalResultDTO result = null;
        final String baseKey = "reports.stats.activity.singlePeriod.percentageNoTrade";

        if (periodMainNoOfMembers >= StatisticalService.MINIMUM_NUMBER_OF_VALUES) {
            final StatisticalNumber percentageNoTradersMain = TransactionCountPerMemberStats.getPercentageNoTraders(periodMainNoOfTraders, periodMainNoOfMembers);
            tableCells[0][0] = percentageNoTradersMain;
            result = new StatisticalResultDTO(tableCells);
            result.setBaseKey(baseKey);
            passGroupFilter(result, queryParameters);
            passPaymentFilter(result, queryParameters);
            setGeneralsForSinglePeriod(result, queryParameters, 1, false);
            final String[] columnSubHeaders = { "(n=" + periodMainNoOfMembers + ")" };
            result.setColumnSubHeaders(columnSubHeaders);
        } else {
            result = StatisticalResultDTO.noDataAvailable(baseKey);
        }
        return result;
    }

    @Override
    public StatisticalResultDTO getThroughTheTimeGrossProduct(final StatisticalActivityQuery queryParameters) {
        getInitializedPaymentFilter(queryParameters);
        final Period[] periods = queryParameters.getPeriods();
        final String[] rowHeaders = new String[periods.length];
        final Number[][] tableData = new Number[periods.length][4];

        int countEnoughData = 0;
        int index = 0;
        for (final Period period : periods) {
            final GrossProductPerMemberStats statsPeriod = new GrossProductPerMemberStats(queryParameters, period, getTransferDao());

            // Gross product of trading members
            final double[] periodSumOfTraders = statsPeriod.getGrossProductPerTradingMember();
            final StatisticalNumber periodMedianGrossProductPerTrader = Median.getMedian(periodSumOfTraders, StatisticalService.ALPHA);

            // Gross product of all members
            final double[] periodSumOfMembers = statsPeriod.getVolumePerAllMembers(getNumberOfMembersForPeriod(queryParameters, period));
            final StatisticalNumber periodMedianGrossProductPerMember = Median.getMedian(periodSumOfMembers, StatisticalService.ALPHA);

            rowHeaders[index] = getRowHeaders(queryParameters.getThroughTimeRange(), period);
            tableData[index][0] = periodMedianGrossProductPerTrader; // trading members
            tableData[index][1] = periodMedianGrossProductPerMember; // all members
            tableData[index][2] = new StatisticalNumber(periodSumOfTraders.length, (byte) 0);
            tableData[index][3] = new StatisticalNumber(periodSumOfMembers.length, (byte) 0);
            if (periodMedianGrossProductPerTrader.hasEnoughData()) {
                countEnoughData++;
            }
            index++;
        }

        final String baseKey = "reports.stats.activity.throughTime.grossProduct";
        StatisticalResultDTO result = null;
        if (countEnoughData > 1) {
            final String[] columnKeys = { "reports.stats.activity.throughTime.grossProduct.col1", "reports.stats.activity.throughTime.grossProduct.col2", "reports.stats.activity.throughTime.grossProduct.col3", // Traders
                    "reports.stats.activity.throughTime.grossProduct.col4" }; // All
            result = new StatisticalResultDTO(tableData);
            result.setRowHeaders(rowHeaders);
            result.setBaseKey(baseKey);
            result.setColumnKeys(columnKeys);
            passGroupFilter(result, queryParameters);
            passPaymentFilter(result, queryParameters);
            final Currency currency = getCurrency(queryParameters);
            result.setColumnSubHeaders(new String[] { parenthesizeString(currency.getSymbol()), parenthesizeString(currency.getSymbol()), "", "" });
            result.setYAxisUnits(currency.getSymbol());
            if (queryParameters.isGrossProductGraph()) {
                result.setGraphDimensions(null, 2, null);
                result.setGraphType(StatisticalResultDTO.GraphType.LINE);
            }
        } else {
            result = StatisticalResultDTO.noDataAvailable(baseKey);
        }
        return result;
    }

    @Override
    public StatisticalResultDTO getThroughTheTimeLoginTimes(final StatisticalActivityQuery queryParameters) {
        final Period[] periods = queryParameters.getPeriods();
        final String[] rowHeaders = new String[periods.length];
        final Number[][] tableData = new Number[periods.length][2];

        int countEnoughData = 0;
        int index = 0;
        for (final Period period : periods) {
            final LoginTimesPerMemberStats statsPeriod = new LoginTimesPerMemberStats(queryParameters, period, loginHistoryDao);

            final List<Number> periodLoginTimesPerMember = statsPeriod.getListLoginTimes();
            final StatisticalNumber periodMedianLoginTimesPerMember = Median.getMedian(periodLoginTimesPerMember, StatisticalService.ALPHA);

            rowHeaders[index] = getRowHeaders(queryParameters.getThroughTimeRange(), period);
            tableData[index][0] = periodMedianLoginTimesPerMember;
            tableData[index][1] = new StatisticalNumber(periodLoginTimesPerMember.size(), (byte) 0);
            if (periodMedianLoginTimesPerMember.hasEnoughData()) {
                countEnoughData++;
            }
            index++;
        }
        final String baseKey = "reports.stats.activity.throughTime.loginTimes";
        StatisticalResultDTO result = null;
        if (countEnoughData > 1) {
            final String[] columnKeys = { "reports.stats.activity.throughTime.loginTimes.col1", "" };
            result = new StatisticalResultDTO(tableData);
            result.setBaseKey(baseKey);
            result.setRowHeaders(rowHeaders);
            result.setColumnKeys(columnKeys);
            result.setColumnHeader("n", 1);
            passGroupFilter(result, queryParameters);
            if (queryParameters.isLoginTimesGraph()) {
                result.setGraphDimensions(null, 1, null);
                result.setGraphType(StatisticalResultDTO.GraphType.LINE);
            }
        } else {
            result = StatisticalResultDTO.noDataAvailable(baseKey);
        }
        return result;
    }

    @Override
    public StatisticalResultDTO getThroughTheTimeNumberTransactions(final StatisticalActivityQuery queryParameters) {
        getInitializedPaymentFilter(queryParameters);
        final Period[] periods = queryParameters.getPeriods();
        final String[] rowHeaders = new String[periods.length];
        final Number[][] tableData = new Number[periods.length][4];

        int countEnoughData = 0;
        int index = 0;
        for (final Period period : periods) {
            final TransactionCountPerMemberStats statsPeriod = new TransactionCountPerMemberStats(queryParameters, period, getTransferDao(), getElementDao());

            // Transactions count of trading members
            final List<Number> periodTransactionsCountTraders = statsPeriod.getTransactionCountPerTradingMember();
            final StatisticalNumber periodMedianTransactionsCountPerTrader = Median.getMedian(periodTransactionsCountTraders, StatisticalService.ALPHA);

            // Transactions count of all members
            final List<Number> periodTransactionsCountMembers = statsPeriod.getTransactionCountPerAllMembers(getNumberOfMembersForPeriod(queryParameters, period));
            final StatisticalNumber periodMedianTransactionsCountPerMember = Median.getMedian(periodTransactionsCountMembers, StatisticalService.ALPHA);

            rowHeaders[index] = getRowHeaders(queryParameters.getThroughTimeRange(), period);
            tableData[index][0] = periodMedianTransactionsCountPerTrader;
            tableData[index][1] = periodMedianTransactionsCountPerMember;
            tableData[index][2] = new StatisticalNumber(periodTransactionsCountTraders.size(), (byte) 0);
            tableData[index][3] = new StatisticalNumber(periodTransactionsCountMembers.size(), (byte) 0);
            if (periodMedianTransactionsCountPerTrader.hasEnoughData()) {
                countEnoughData++;
            }
            index++;
        }
        final String[] columnKeys = { "reports.stats.activity.throughTime.numberTransactions.col1", "reports.stats.activity.throughTime.numberTransactions.col2", "reports.stats.activity.throughTime.numberTransactions.col3", // Traders
                "reports.stats.activity.throughTime.numberTransactions.col4" }; // All
        final String baseKey = "reports.stats.activity.throughTime.numberTransactions";
        StatisticalResultDTO result = null;
        if (countEnoughData > 1) {
            result = new StatisticalResultDTO(tableData);
            result.setBaseKey(baseKey);
            result.setRowHeaders(rowHeaders);
            result.setColumnKeys(columnKeys);
            passGroupFilter(result, queryParameters);
            passPaymentFilter(result, queryParameters);
            if (queryParameters.isNumberTransactionsGraph()) {
                result.setGraphDimensions(null, 2, null);
                result.setGraphType(StatisticalResultDTO.GraphType.LINE);
            }
        } else {
            result = StatisticalResultDTO.noDataAvailable(baseKey);
        }
        return result;
    }

    @Override
    public StatisticalResultDTO getThroughTheTimePercentageNoTrade(final StatisticalActivityQuery queryParameters) {
        getInitializedPaymentFilter(queryParameters);
        final Period[] periods = queryParameters.getPeriods();
        final String[] rowHeaders = new String[periods.length];
        final Number[][] tableData = new Number[periods.length][2];

        int countEnoughData = 0;
        int index = 0;

        for (final Period period : periods) {
            final TransactionCountPerMemberStats statsPeriod = new TransactionCountPerMemberStats(queryParameters, period, getTransferDao(), getElementDao());

            // Number of traders
            final int periodNoOfTraders = statsPeriod.getTransactionCountPerTradingMember().size();

            // Number of members
            final int periodNoOfMembers = statsPeriod.getTransactionCountPerAllMembers(getNumberOfMembersForPeriod(queryParameters, period)).size();

            // Calculate the percentage of no traders
            final StatisticalNumber percentageOfNoTraders = TransactionCountPerMemberStats.getPercentageNoTraders(periodNoOfTraders, periodNoOfMembers);

            // StatisticalNumber representing the total number of members
            final StatisticalNumber n = new StatisticalNumber(periodNoOfMembers, (byte) 0);

            rowHeaders[index] = getRowHeaders(queryParameters.getThroughTimeRange(), period);
            tableData[index][0] = percentageOfNoTraders;
            tableData[index][1] = n;
            index++;
            if (periodNoOfMembers >= StatisticalService.MINIMUM_NUMBER_OF_VALUES) {
                countEnoughData++;
            }
        }

        final String[] columnKeys = { "reports.stats.activity.throughTime.percentageNoTrade.col1", "" };
        final String baseKey = "reports.stats.activity.throughTime.percentageNoTrade";
        StatisticalResultDTO result = null;
        if (countEnoughData > 1) {
            result = new StatisticalResultDTO(tableData);
            result.setBaseKey(baseKey);
            result.setRowHeaders(rowHeaders);
            result.setColumnKeys(columnKeys);
            result.setColumnHeader("n", 1);
            passGroupFilter(result, queryParameters);
            passPaymentFilter(result, queryParameters);
            if (queryParameters.isPercentageNoTradeGraph()) {
                result.setGraphDimensions(null, 1, null);
                result.setGraphType(StatisticalResultDTO.GraphType.LINE);
            }
        } else {
            result = StatisticalResultDTO.noDataAvailable(baseKey);
        }
        return result;
    }

    /**
     * get a top ten with members with the highest personal grossproduct
     */
    @Override
    public StatisticalResultDTO getToptenPersonalGrossProduct(final StatisticalActivityQuery queryParameters) {
        getInitializedPaymentFilter(queryParameters);
        final GrossProductPerMemberStats statsPeriod = new GrossProductPerMemberStats(queryParameters, queryParameters.getPeriodMain(), getTransferDao());
        final List<Pair<Member, BigDecimal>> rawDataListFromQueryPair = statsPeriod.getSumOfTransfersPerTrader();
        final StatisticalResultDTO result = getTopTenFromPairList(rawDataListFromQueryPair, "reports.stats.activity.topten.grossProduct", queryParameters);
        final Currency currency = getCurrency(queryParameters);
        result.setColumnSubHeaders(new String[] { parenthesizeString(currency.getSymbol()) });
        return result;
    }

    /**
     * get a top ten with members with the highest number of logins.
     */
    @Override
    public StatisticalResultDTO getToptenPersonalLoginTimes(final StatisticalActivityQuery queryParameters) {
        final LoginTimesPerMemberStats statsPeriod = new LoginTimesPerMemberStats(queryParameters, queryParameters.getPeriodMain(), loginHistoryDao);
        final List<Pair<User, Number>> rawDataListFromQueryPair = statsPeriod.getLoginTimesPerMemberWithoutZeros();
        return getTopTenFromPairList(rawDataListFromQueryPair, "reports.stats.activity.topten.login", queryParameters);
    }

    /**
     * get a top ten with members with the highest number of transactions
     */
    @Override
    public StatisticalResultDTO getToptenPersonalNumberTransactions(final StatisticalActivityQuery queryParameters) {
        final TransactionCountPerMemberStats statsPeriod = new TransactionCountPerMemberStats(queryParameters, queryParameters.getPeriodMain(), getTransferDao(), getElementDao());
        final List<Pair<Member, Integer>> rawDataListFromQueryPair = statsPeriod.getTransfersPerTrader();
        return getTopTenFromPairList(rawDataListFromQueryPair, "reports.stats.activity.topten.numberTransactions", queryParameters);
    }

    public void setLoginHistoryDao(final LoginHistoryDAO loginHistoryDao) {
        this.loginHistoryDao = loginHistoryDao;
    }

    /**
     * sets the column headers and keys
     * 
     * @param result the result value object
     * @param queryParameters
     * @param sixColumns
     */
    private void applyColumnHeadersAndKeys(final StatisticalResultDTO result, final StatisticalActivityQuery queryParameters, final boolean sixColumns) {
        if (sixColumns) {
            final String[] columnKeys = { "", "", "", "", "reports.stats.general.growth", "reports.stats.general.p" };
            result.setColumnKeys(columnKeys);
            result.setColumnHeader("N", 3);
            result.setColumnHeader("N", 2);
            final String[] columnSubHeaders = { "", "", queryParameters.getPeriodComparedTo().getName(), queryParameters.getPeriodMain().getName(), "", "" };
            result.setColumnSubHeaders(columnSubHeaders);
        } else {
            final String[] columnKeys = { "", "", "reports.stats.general.growth", "reports.stats.general.p" };
            result.setColumnKeys(columnKeys);
        }
        result.setColumnHeader(queryParameters.getPeriodMain().getName(), 1);
        result.setColumnHeader(queryParameters.getPeriodComparedTo().getName(), 0);
    }

    private void applyRowKeys(final StatisticalResultDTO result, final int numberOfRows) {
        final ArrayList<String> rowKeyList = new ArrayList<String>(numberOfRows);
        for (int i = 1; i <= numberOfRows; i++) {
            rowKeyList.add(result.getBaseKey() + ".row" + i);
        }
        String[] rowKeys = new String[numberOfRows];
        rowKeys = rowKeyList.toArray(rowKeys);
        result.setRowKeys(rowKeys);
    }

    private void applySinglePeriodColumnHeadersAndKeys(final StatisticalResultDTO result, final StatisticalActivityQuery queryParameters, final boolean twoColumns) {
        if (twoColumns) {
            final String[] columnKeys = { "", "" };
            result.setColumnKeys(columnKeys);
            result.setColumnHeader("N", 1);
            final String[] columnSubHeaders = { "", queryParameters.getPeriodMain().getName() };
            result.setColumnSubHeaders(columnSubHeaders);
        } else {
            final String[] columnKeys = { "" };
            result.setColumnKeys(columnKeys);
        }
        result.setColumnHeader(queryParameters.getPeriodMain().getName(), 0);
    }

    private Integer getNumberOfMembersForPeriod(final StatisticalActivityQuery queryParameters, final Period requestedPeriod) {
        // we only have to get it anew if that hasn't been done before with exactly the same queryParameters (hence no overridden
        // StatisticalActivityQuery.equals)
        if (numberOfMembersPerGroupPerPeriod == null || !cachedQueryParameters.equals(queryParameters)) {
            // first, get all the periods from ...
            final Collection<Period> periods = new ArrayList<Period>();
            // ... through the time periods
            if (queryParameters.getWhatToShow() == StatisticsWhatToShow.THROUGH_TIME) {
                periods.addAll(Arrays.asList(queryParameters.getPeriods()));
            }
            // ... compare periods: two periods and ...
            if (queryParameters.getWhatToShow() == StatisticsWhatToShow.COMPARE_PERIODS) {
                final Period periodMain = queryParameters.getPeriodMain();
                final Period periodComparedTo = queryParameters.getPeriodComparedTo();
                periods.add(periodMain);
                periods.add(periodComparedTo);
            }
            // (Caution) ... single period or histogram and Top ten -> only MAIN PERIOD.
            if (queryParameters.getWhatToShow() == StatisticsWhatToShow.SINGLE_PERIOD || queryParameters.getWhatToShow() == StatisticsWhatToShow.DISTRIBUTION) {
                final Period periodMain = queryParameters.getPeriodMain();
                periods.add(periodMain);
            }
            final Collection<Group> groups = queryParameters.getGroups();
            numberOfMembersPerGroupPerPeriod = new HashMap<String, Integer>();

            // loop to get member numbers
            for (final Period period : periods) {
                final Integer numberOfMembers = getElementDao().getNumberOfMembersInGroupsInPeriod(groups, period);
                numberOfMembersPerGroupPerPeriod.put(period.toString(), numberOfMembers);
            }
            cachedQueryParameters = queryParameters;
        }

        // now we are sure the map exists, just request its data
        return numberOfMembersPerGroupPerPeriod.get(requestedPeriod.toString());
    }

    /**
     * Common method for getting the personal top ten from a Pair List. Fits for all top tens.
     * 
     * @param <S> The first element Type of the Pair which is in the <code>rawDataPairList</code>. This extends <code>Entity</code> and is
     * <code>Member</code> for grossProduct and Number of transactions, and <code>User</code> for logins.
     * @param <T> The second element Type of the Pair which is in the <code>rawDataPairList</code>. This type extends <code>Number</code> and contains
     * the score of the member.
     * @param rawDataPairList. The List with results. The List contains <code>Pair</code>s, where the first element represents the member, and the
     * second element the score of that member.
     * @param baseKey a String representing the base key for the language resource bundle.
     * @param queryParameters a <code>StatisticalActivityQuery</code> containing the parameters of the form.
     * @return a <code>StatisticalResultDTO</code> containing all information to build the table in the jsp.
     */
    private <S extends Entity, T extends Number> StatisticalResultDTO getTopTenFromPairList(final List<Pair<S, T>> rawDataPairList, final String baseKey, final StatisticalActivityQuery queryParameters) {
        int last = 10;
        if (rawDataPairList.size() < last) {
            last = rawDataPairList.size();
        }
        // determine if there are any more members beyond number 10 having the same score as number 10
        int extraItems = 0;
        if (last == 10) {
            final List<T> loginTimes = ListOperations.getSecondFromPairCollection(rawDataPairList);
            final int lastIndex = loginTimes.lastIndexOf(loginTimes.get(9));
            if (lastIndex > 9) {
                extraItems = lastIndex - 9;
            }
        }
        final String[] rowHeaders = new String[last + (extraItems > 0 ? 1 : 0)];
        final StatisticalResultDTO.ResourceKey[] rowKeys = new StatisticalResultDTO.ResourceKey[last + (extraItems > 0 ? 1 : 0)];
        final Number[][] tableData = new Number[last + (extraItems > 0 ? 1 : 0)][1];
        for (int i = 0; i < last; i++) {
            final Pair<S, T> pair = rawDataPairList.get(i);
            tableData[i][0] = pair.getSecond();
            rowKeys[i] = new StatisticalResultDTO.ResourceKey("");
            final S entity = pair.getFirst();
            if (entity instanceof Member) {
                rowHeaders[i] = "" + (i + 1) + " - " + ((Member) entity).getName();
            } else {
                rowHeaders[i] = "" + (i + 1) + " - " + ((User) entity).getUsername();
            }
            // if there are more members with the same score as number 10, add an extra item saying how many
            if (i == 9 && extraItems > 0) {
                tableData[10][0] = pair.getSecond();
                rowKeys[10] = new StatisticalResultDTO.ResourceKey("reports.stats.activity.topten.andMore", new Object[] { extraItems });
            }
        }
        StatisticalResultDTO result = null;
        if (last > 0) {
            result = new StatisticalResultDTO(tableData);
            result.setBaseKey(baseKey);
            result.setRowKeys(rowKeys);
            for (int i = 0; i < rowHeaders.length - (extraItems > 0 ? 1 : 0); i++) {
                result.setRowHeader(rowHeaders[i], i);
            }
            result.setColumnKeys(new String[] { baseKey + ".col1" });
            passGroupFilter(result, queryParameters);
            passPaymentFilter(result, queryParameters);
        } else {
            result = StatisticalResultDTO.noDataAvailable(baseKey);
        }
        return result;
    }

    private void setGeneralsForCompare2Periods(final StatisticalResultDTO result, final StatisticalActivityQuery queryParameters, final int numberOfRows) {
        applyRowKeys(result, numberOfRows);
        final boolean sixColumns = (numberOfRows == 2);
        applyColumnHeadersAndKeys(result, queryParameters, sixColumns);
    }

    private void setGeneralsForSinglePeriod(final StatisticalResultDTO result, final StatisticalActivityQuery queryParameters, final int numberOfRows, final boolean twoColumns) {
        applyRowKeys(result, numberOfRows);
        applySinglePeriodColumnHeadersAndKeys(result, queryParameters, twoColumns);
    }

}
