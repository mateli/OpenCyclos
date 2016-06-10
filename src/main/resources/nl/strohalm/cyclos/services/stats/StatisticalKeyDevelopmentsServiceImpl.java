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
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import nl.strohalm.cyclos.dao.ads.AdDAO;
import nl.strohalm.cyclos.entities.accounts.Currency;
import nl.strohalm.cyclos.entities.accounts.transactions.PaymentFilter;
import nl.strohalm.cyclos.entities.ads.Ad;
import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.entities.members.MemberQuery;
import nl.strohalm.cyclos.entities.reports.StatisticalDTO;
import nl.strohalm.cyclos.entities.reports.StatisticalKeyDevelopmentsQuery;
import nl.strohalm.cyclos.entities.reports.StatisticalNumber;
import nl.strohalm.cyclos.entities.reports.StatisticalQuery;
import nl.strohalm.cyclos.entities.reports.ThroughTimeRange;
import nl.strohalm.cyclos.services.stats.StatisticalResultDTO.MultiGraph;
import nl.strohalm.cyclos.services.stats.general.FilterUsed;
import nl.strohalm.cyclos.services.stats.general.KeyDevelopmentsStatsPerMonthVO;
import nl.strohalm.cyclos.utils.Period;
import nl.strohalm.cyclos.utils.query.PageHelper;
import nl.strohalm.cyclos.utils.statistics.ListOperations;
import nl.strohalm.cyclos.utils.statistics.Median;

/**
 * An implementation of the StatisticalKeyDevelopmentsService interface; subclasses StatisticalService. As Statistical Key Developments are not
 * statistics (in the sense that numbers are not retrieved by taking the mean or median from a set of observations), but are just totals, there is no
 * need to apply any limit on the minimum number of values.
 * @author rinke
 * 
 */
public class StatisticalKeyDevelopmentsServiceImpl extends StatisticalServiceImpl implements StatisticalKeyDevelopmentsServiceLocal {

    private AdDAO                           adDao;
    private StatisticalKeyDevelopmentsQuery savedParameters;
    private List<Number>                    transactionAmounts;
    private List<Number>                    transactionAmounts2;

    public StatisticalResultDTO getComparePeriodsGrossProduct(final StatisticalKeyDevelopmentsQuery queryParameters) {
        final byte precision = (byte) getLocalSettings().getPrecision().getValue();
        final Period periodMain = queryParameters.getPeriodMain();
        final Period periodComparedTo = queryParameters.getPeriodComparedTo();
        final Collection<? extends Group> groups = queryParameters.getGroups();
        final PaymentFilter paymentFilter = getInitializedPaymentFilter(queryParameters);

        // Data structure to build the table
        final Number[][] tableCells = new Number[1][3];
        // Sum of amounts of transfers (main period with payment filter)
        final BigDecimal periodMainSumFilter = getSumOfTransactions(periodMain, groups, paymentFilter);
        // Sum of amounts of transfers (compared to period with payment filter)
        final BigDecimal periodComparedToSumFilter = getSumOfTransactions(periodComparedTo, groups, paymentFilter);
        // Row for payment filter
        tableCells[0][1] = new StatisticalNumber(periodMainSumFilter.doubleValue(), precision);
        tableCells[0][0] = new StatisticalNumber(periodComparedToSumFilter.doubleValue(), precision);
        tableCells[0][2] = StatisticalNumber.createPercentage(periodMainSumFilter, periodComparedToSumFilter);

        final StatisticalResultDTO result = new StatisticalResultDTO(tableCells);
        result.setBaseKey("reports.stats.keydevelopments.grossProduct");
        String[] rowKeys;
        if (paymentFilter != null) {
            result.setRowHeader(paymentFilter.getName(), 0);
            rowKeys = new String[] { "" };
        } else {
            rowKeys = new String[] { "reports.stats.keydevelopments.grossProduct.allTransactions" };
        }
        passCurrencyCompared(result, queryParameters);
        try {
            result.setRowKeys(rowKeys);
            applyColumnHeadersAndKeys(result, queryParameters);
            passGroupFilter(result, queryParameters);
            passPaymentFilter(result, queryParameters);
        } catch (final Exception e) {
            System.out.println("Error in Gross Product");
            e.printStackTrace();
        }

        if (queryParameters.isGrossProductGraph()) {
            result.setGraphDimensions(null, 2, null);
            result.setGraphType(StatisticalResultDTO.GraphType.BAR);
        }

        return result;
    }

    public StatisticalResultDTO getComparePeriodsHighestTransactionAmount(final StatisticalKeyDevelopmentsQuery queryParameters) {
        final byte precision = (byte) getLocalSettings().getPrecision().getValue();
        final PaymentFilter paymentFilter = getInitializedPaymentFilter(queryParameters);
        // Data structure to build the table
        final String baseKey = "reports.stats.keydevelopments.highestAmountPerTransaction";
        final Number[][] tableCells = new Number[1][3];
        final List<Number> periodMainAmounts = getTransactionAmounts(queryParameters, paymentFilter);
        final List<Number> periodComparedAmounts = getTransactionAmountsComparedTo(queryParameters, paymentFilter);
        if (periodMainAmounts.size() < StatisticalService.MINIMUM_NUMBER_OF_VALUES && periodComparedAmounts.size() < StatisticalService.MINIMUM_NUMBER_OF_VALUES) {
            return StatisticalResultDTO.noDataAvailable(baseKey);
        }
        final Number periodMainHighestAmount = ListOperations.getMax(periodMainAmounts);
        final Number periodComparedToHighestAmount = ListOperations.getMax(periodComparedAmounts);

        tableCells[0][1] = (periodMainHighestAmount == null) ? new StatisticalNumber() : new StatisticalNumber(periodMainHighestAmount.doubleValue(), precision);
        tableCells[0][0] = (periodComparedToHighestAmount == null) ? new StatisticalNumber() : new StatisticalNumber(periodComparedToHighestAmount.doubleValue(), precision);
        tableCells[0][2] = StatisticalNumber.createPercentage(periodMainHighestAmount, periodComparedToHighestAmount);

        final StatisticalResultDTO result = new StatisticalResultDTO(tableCells);
        result.setBaseKey(baseKey);
        String[] rowKeys;
        if (paymentFilter != null) {
            result.setRowHeader(paymentFilter.getName(), 0);
            rowKeys = new String[] { "" };
        } else {
            rowKeys = new String[] { "reports.stats.keydevelopments.grossProduct.allTransactions" };
        }
        passCurrencyCompared(result, queryParameters);
        try {
            result.setRowKeys(rowKeys);
            if (paymentFilter != null) {
                result.setRowHeader(paymentFilter.getName(), 0);
            }
            applyColumnHeadersAndKeys(result, queryParameters);
            passGroupFilter(result, queryParameters);
            passPaymentFilter(result, queryParameters);
        } catch (final Exception e) {
            System.out.println("Error in highest transaction amount per transaction");
            e.printStackTrace();
        }

        if (queryParameters.isTransactionAmountGraph()) {
            result.setGraphDimensions(null, 2, null);
            result.setGraphType(StatisticalResultDTO.GraphType.BAR);
        }

        return result;
    }

    public StatisticalResultDTO getComparePeriodsMedianAmountPerTransaction(final StatisticalKeyDevelopmentsQuery queryParameters) {
        final PaymentFilter paymentFilter = getInitializedPaymentFilter(queryParameters);

        // Data structure to build the table
        final Number[][] tableCells = new Number[1][4];
        final String baseKey = "reports.stats.keydevelopments.averageAmountPerTransaction";
        final List<Number> periodMainAmounts = getTransactionAmounts(queryParameters, paymentFilter);
        final List<Number> periodComparedAmounts = getTransactionAmountsComparedTo(queryParameters, paymentFilter);

        if (periodMainAmounts.size() < StatisticalService.MINIMUM_NUMBER_OF_VALUES && periodComparedAmounts.size() < StatisticalService.MINIMUM_NUMBER_OF_VALUES) {
            return StatisticalResultDTO.noDataAvailable(baseKey);
        }
        // Row for payment filter
        tableCells[0][1] = Median.getMedian(periodMainAmounts, StatisticalService.ALPHA);
        tableCells[0][0] = Median.getMedian(periodComparedAmounts, StatisticalService.ALPHA);
        tableCells[0][2] = StatisticalNumber.createPercentage(tableCells[0][1], tableCells[0][0]);
        tableCells[0][3] = StatisticalServiceImpl.calculatePvalue(periodMainAmounts, periodComparedAmounts);

        final StatisticalResultDTO result = new StatisticalResultDTO(tableCells);
        result.setBaseKey(baseKey);
        String[] rowKeys = null;
        if (paymentFilter != null) {
            result.setRowHeader(paymentFilter.getName(), 0);
            rowKeys = new String[] { "" };
        } else {
            rowKeys = new String[] { "reports.stats.keydevelopments.grossProduct.allTransactions" };
        }
        final Currency currency = getCurrency(queryParameters);
        result.setColumnSubHeaders(new String[] { parenthesizeString(currency.getSymbol()), parenthesizeString(currency.getSymbol()), "", "" });
        result.setYAxisUnits(currency.getSymbol());
        try {
            result.setRowKeys(rowKeys);
            final String[] columnKeys = { "", "", "reports.stats.general.growth", "reports.stats.general.p" };
            result.setColumnKeys(columnKeys);
            result.setColumnHeader(queryParameters.getPeriodMain().getName(), 1);
            result.setColumnHeader(queryParameters.getPeriodComparedTo().getName(), 0);
            passGroupFilter(result, queryParameters);
            passPaymentFilter(result, queryParameters);
        } catch (final Exception e) {
            System.out.println("Error in average amount per transaction");
            e.printStackTrace();
        }

        if (queryParameters.isTransactionAmountGraph()) {
            result.setGraphDimensions(null, 2, null);
            result.setGraphType(StatisticalResultDTO.GraphType.BAR);
        }

        return result;
    }

    public StatisticalResultDTO getComparePeriodsNumberOfAds(final StatisticalKeyDevelopmentsQuery queryParameters) {
        final Period periodMain = queryParameters.getPeriodMain();
        final Period periodComparedTo = queryParameters.getPeriodComparedTo();
        final Collection<? extends Group> groups = queryParameters.getGroups();

        // Period "main" data
        final Integer activeAdsPeriodMain = adDao.getNumberOfAds(periodMain.getEnd(), groups, Ad.Status.ACTIVE);
        final Integer scheduledAdsPeriodMain = adDao.getNumberOfAds(periodMain.getEnd(), groups, Ad.Status.SCHEDULED);
        final Integer expiredAdsPeriodMain = adDao.getNumberOfAds(periodMain.getEnd(), groups, Ad.Status.EXPIRED);
        final Integer createdAdsPeriodMain = adDao.getNumberOfCreatedAds(periodMain, groups);

        // Period "compared to" data
        final Integer activeAdsPeriodComparedTo = adDao.getNumberOfAds(periodComparedTo.getEnd(), groups, Ad.Status.ACTIVE);
        final Integer scheduledAdsPeriodComparedTo = adDao.getNumberOfAds(periodComparedTo.getEnd(), groups, Ad.Status.SCHEDULED);
        final Integer expiredAdsPeriodComparedTo = adDao.getNumberOfAds(periodComparedTo.getEnd(), groups, Ad.Status.EXPIRED);
        final Integer createdAdsPeriodComparedTo = adDao.getNumberOfCreatedAds(periodComparedTo, groups);

        // Data structure to build the table
        final Number[][] tableCells = new Number[4][3];

        // First row (active ads at the end of the period)
        tableCells[0][1] = new StatisticalNumber(activeAdsPeriodMain);
        tableCells[0][0] = new StatisticalNumber(activeAdsPeriodComparedTo);
        tableCells[0][2] = StatisticalNumber.createPercentage(activeAdsPeriodMain, activeAdsPeriodComparedTo);

        // Second row (scheduled ads at the end of the period)
        tableCells[1][1] = new StatisticalNumber(scheduledAdsPeriodMain);
        tableCells[1][0] = new StatisticalNumber(scheduledAdsPeriodComparedTo);
        tableCells[1][2] = StatisticalNumber.createPercentage(scheduledAdsPeriodMain, scheduledAdsPeriodComparedTo);

        // Third row (expired ads at the end of the period)
        tableCells[2][1] = new StatisticalNumber(expiredAdsPeriodMain);
        tableCells[2][0] = new StatisticalNumber(expiredAdsPeriodComparedTo);
        tableCells[2][2] = StatisticalNumber.createPercentage(expiredAdsPeriodMain, expiredAdsPeriodComparedTo);

        // Forth row (created ads in period)
        tableCells[3][1] = new StatisticalNumber(createdAdsPeriodMain);
        tableCells[3][0] = new StatisticalNumber(createdAdsPeriodComparedTo);
        tableCells[3][2] = StatisticalNumber.createPercentage(createdAdsPeriodMain, createdAdsPeriodComparedTo);

        final StatisticalResultDTO result = new StatisticalResultDTO(tableCells);
        result.setBaseKey("reports.stats.keydevelopments.numberOfAds");
        final String[] rowKeys = { "reports.stats.keydevelopments.numberOfAds.active", "reports.stats.keydevelopments.numberOfAds.scheduled", "reports.stats.keydevelopments.numberOfAds.expired", "reports.stats.keydevelopments.numberOfAds.created" };
        result.setFilterAsNotUsed(FilterUsed.FilterType.PAYMENT);

        try {
            result.setRowKeys(rowKeys);
            applyColumnHeadersAndKeys(result, queryParameters);
            passGroupFilter(result, queryParameters);
        } catch (final Exception e) {
            System.out.println("Error in number of ads");
            e.printStackTrace();
        }

        if (queryParameters.isNumberOfAdsGraph()) {
            result.setGraphDimensions(null, 2, null);
            result.setGraphType(StatisticalResultDTO.GraphType.BAR);
        }

        return result;
    }

    public StatisticalResultDTO getComparePeriodsNumberOfMembers(final StatisticalKeyDevelopmentsQuery queryParameters) {
        final Period periodMain = queryParameters.getPeriodMain();
        final Period periodComparedTo = queryParameters.getPeriodComparedTo();
        final Collection<? extends Group> groups = queryParameters.getGroups();

        // Number of members / main period
        final int periodMainMembersCount = getElementDao().getNumberOfMembersInGroupsInPeriod(groups, periodMain);
        final int periodComparedToMembersCount = getElementDao().getNumberOfMembersInGroupsInPeriod(groups, periodComparedTo);

        // Number of new members / main period
        final int periodMainNewMembersCount = getNewMembersCount(periodMain, groups);

        // Number of new members / compared to period
        final int periodComparedToNewMembersCount = getNewMembersCount(periodComparedTo, groups);

        // Number of disappeared members / main period
        final int periodMainDisappearedMembersCount = getDisappearedMembersCount(periodMain, groups);

        // Number of disappeared members / compared to period
        final int periodComparedToDisappearedMembersCount = getDisappearedMembersCount(periodComparedTo, groups);

        // Data structure to build the table
        final Number[][] tableCells = new Number[3][3];

        // First row
        tableCells[0][1] = new StatisticalNumber(periodMainMembersCount);
        tableCells[0][0] = new StatisticalNumber(periodComparedToMembersCount);
        tableCells[0][2] = StatisticalNumber.createPercentage(periodMainMembersCount, periodComparedToMembersCount);

        // Second row
        tableCells[1][1] = new StatisticalNumber(periodMainNewMembersCount);
        tableCells[1][0] = new StatisticalNumber(periodComparedToNewMembersCount);
        tableCells[1][2] = StatisticalNumber.createPercentage(periodMainNewMembersCount, periodComparedToNewMembersCount);

        // Third row
        tableCells[2][1] = new StatisticalNumber(periodMainDisappearedMembersCount);
        tableCells[2][0] = new StatisticalNumber(periodComparedToDisappearedMembersCount);
        tableCells[2][2] = StatisticalNumber.createPercentage(periodMainDisappearedMembersCount, periodComparedToDisappearedMembersCount);

        final StatisticalResultDTO result = new StatisticalResultDTO(tableCells);
        result.setBaseKey("reports.stats.keydevelopments.numberOfMembers");
        final String[] rowKeys = { "reports.stats.keydevelopments.numberOfMembers.numberOfMembers", "reports.stats.keydevelopments.numberOfMembers.numberOfNewMembers", "reports.stats.keydevelopments.numberOfMembers.numberOfDisappearedMembers" };
        try {
            result.setRowKeys(rowKeys);
            applyColumnHeadersAndKeys(result, queryParameters);
            passGroupFilter(result, queryParameters);
        } catch (final Exception e) {
            System.out.println("Error in NumberOfMembers!");
            e.printStackTrace();
        }

        if (queryParameters.isNumberOfMembersGraph()) {
            result.setGraphDimensions(null, 2, null);
            result.setGraphType(StatisticalResultDTO.GraphType.BAR);
        }

        return result;
    }

    public StatisticalResultDTO getComparePeriodsNumberOfTransactions(final StatisticalKeyDevelopmentsQuery queryParameters) {
        final PaymentFilter paymentFilter = getInitializedPaymentFilter(queryParameters);
        // Data structure to build the table
        final Number[][] tableCells = new Number[1][3];
        final Integer periodMainCount = getTransactionAmounts(queryParameters, paymentFilter).size();
        final Integer periodComparedToCount = getTransactionAmountsComparedTo(queryParameters, paymentFilter).size();
        tableCells[0][1] = new StatisticalNumber(periodMainCount);
        tableCells[0][0] = new StatisticalNumber(periodComparedToCount);
        tableCells[0][2] = StatisticalNumber.createPercentage(periodMainCount, periodComparedToCount);

        final StatisticalResultDTO result = new StatisticalResultDTO(tableCells);
        result.setBaseKey("reports.stats.keydevelopments.numberOfTransactions");
        String[] rowKeys;
        if (paymentFilter != null) {
            result.setRowHeader(paymentFilter.getName(), 0);
            rowKeys = new String[] { "" };
        } else {
            rowKeys = new String[] { "reports.stats.keydevelopments.grossProduct.allTransactions" };
        }
        try {
            result.setRowKeys(rowKeys);
            if (paymentFilter != null) {
                result.setRowHeader(paymentFilter.getName(), 0);
            }
            applyColumnHeadersAndKeys(result, queryParameters);
            passGroupFilter(result, queryParameters);
            passPaymentFilter(result, queryParameters);
        } catch (final Exception e) {
            System.out.println("Error in NumberOfTransactions");
            e.printStackTrace();
        }

        if (queryParameters.isNumberOfTransactionsGraph()) {
            result.setGraphDimensions(null, 2, null);
            result.setGraphType(StatisticalResultDTO.GraphType.BAR);
        }

        return result;
    }

    public StatisticalResultDTO getSinglePeriodGrossProduct(final StatisticalKeyDevelopmentsQuery queryParameters) {
        final byte precision = (byte) getLocalSettings().getPrecision().getValue();
        final Period periodMain = queryParameters.getPeriodMain();
        final Collection<? extends Group> groups = queryParameters.getGroups();
        final PaymentFilter paymentFilter = getInitializedPaymentFilter(queryParameters);
        // Data structure to build the table
        final Number[][] tableCells = new Number[1][1];
        tableCells[0][0] = new StatisticalNumber(getSumOfTransactions(periodMain, groups, paymentFilter).doubleValue(), precision);

        final StatisticalResultDTO result = new StatisticalResultDTO(tableCells);
        result.setBaseKey("reports.stats.keydevelopments.grossProduct");
        String[] rowKeys;
        if (paymentFilter != null) {
            result.setRowHeader(paymentFilter.getName(), 0);
            rowKeys = new String[] { "" };
        } else {
            rowKeys = new String[] { "reports.stats.keydevelopments.grossProduct.allTransactions" };
        }
        passCurrencySingle(result, queryParameters);
        try {
            result.setRowKeys(rowKeys);
            applySinglePeriodColumnHeadersAndKeys(result, queryParameters);
            passGroupFilter(result, queryParameters);
            passPaymentFilter(result, queryParameters);
        } catch (final Exception e) {
            System.out.println("Error in Gross Product");
            e.printStackTrace();
        }

        return result;
    }

    public StatisticalResultDTO getSinglePeriodHighestTransactionAmount(final StatisticalKeyDevelopmentsQuery queryParameters) {
        final byte precision = (byte) getLocalSettings().getPrecision().getValue();
        final PaymentFilter paymentFilter = getInitializedPaymentFilter(queryParameters);
        // Data structure to build the table
        final String baseKey = "reports.stats.keydevelopments.highestAmountPerTransaction";
        final Number[][] tableCells = new Number[1][1];
        final List<Number> amounts = getTransactionAmounts(queryParameters, paymentFilter);
        if (amounts.size() < StatisticalService.MINIMUM_NUMBER_OF_VALUES) {
            return StatisticalResultDTO.noDataAvailable(baseKey);
        }

        // getting a list and iterating over it is much faster than using MySql's max function in a query.
        tableCells[0][0] = new StatisticalNumber(ListOperations.getMax(amounts).doubleValue(), precision);

        final StatisticalResultDTO result = new StatisticalResultDTO(tableCells);
        result.setBaseKey(baseKey);
        String[] rowKeys;
        if (paymentFilter != null) {
            result.setRowHeader(paymentFilter.getName(), 0);
            rowKeys = new String[] { "" };
        } else {
            rowKeys = new String[] { "reports.stats.keydevelopments.grossProduct.allTransactions" };
        }
        passCurrencySingle(result, queryParameters);
        try {
            result.setRowKeys(rowKeys);
            if (paymentFilter != null) {
                result.setRowHeader(paymentFilter.getName(), 0);
            }
            applySinglePeriodColumnHeadersAndKeys(result, queryParameters);
            passGroupFilter(result, queryParameters);
            passPaymentFilter(result, queryParameters);
        } catch (final Exception e) {
            System.out.println("Error in highest transaction amount per transaction");
            e.printStackTrace();
        }

        return result;
    }

    public StatisticalResultDTO getSinglePeriodMedianAmountPerTransaction(final StatisticalKeyDevelopmentsQuery queryParameters) {
        // a separate query is needed, as gross product is only on incoming, and number of trans is on all transactions
        final PaymentFilter paymentFilter = getInitializedPaymentFilter(queryParameters);
        final String baseKey = "reports.stats.keydevelopments.averageAmountPerTransaction";
        final StatisticalNumber amount = Median.getMedian(getTransactionAmounts(queryParameters, paymentFilter), StatisticalService.ALPHA);
        if (amount.isNull()) {
            return StatisticalResultDTO.noDataAvailable(baseKey);
        }

        final Number[][] tableCells = new Number[1][1];
        tableCells[0][0] = amount;

        final StatisticalResultDTO result = new StatisticalResultDTO(tableCells);
        result.setBaseKey(baseKey);
        String[] rowKeys;
        if (paymentFilter != null) {
            result.setRowHeader(paymentFilter.getName(), 0);
            rowKeys = new String[] { "" };
        } else {
            rowKeys = new String[] { "reports.stats.keydevelopments.grossProduct.allTransactions" };
        }
        passCurrencySingle(result, queryParameters);
        try {
            result.setRowKeys(rowKeys);
            applySinglePeriodColumnHeadersAndKeys(result, queryParameters);
            passGroupFilter(result, queryParameters);
            passPaymentFilter(result, queryParameters);
        } catch (final Exception e) {
            System.out.println("Error in average amount per transaction");
            e.printStackTrace();
        }
        return result;
    }

    public StatisticalResultDTO getSinglePeriodNumberOfAds(final StatisticalKeyDevelopmentsQuery queryParameters) {
        final Period periodMain = queryParameters.getPeriodMain();
        final Collection<? extends Group> groups = queryParameters.getGroups();

        // Period main data
        final Integer activeAdsPeriodMain = adDao.getNumberOfAds(periodMain.getEnd(), groups, Ad.Status.ACTIVE);
        final Integer scheduledAdsPeriodMain = adDao.getNumberOfAds(periodMain.getEnd(), groups, Ad.Status.SCHEDULED);
        final Integer expiredAdsPeriodMain = adDao.getNumberOfAds(periodMain.getEnd(), groups, Ad.Status.EXPIRED);
        final Integer createdAdsPeriodMain = adDao.getNumberOfCreatedAds(periodMain, groups);

        // Data structure to build the table
        final Number[][] tableCells = new Number[4][1];

        // First row (active ads at the end of the period)
        tableCells[0][0] = new StatisticalNumber(activeAdsPeriodMain);

        // Second row (scheduled ads at the end of the period)
        tableCells[1][0] = new StatisticalNumber(scheduledAdsPeriodMain);

        // Third row (expired ads at the end of the period)
        tableCells[2][0] = new StatisticalNumber(expiredAdsPeriodMain);

        // Forth row (created ads in period)
        tableCells[3][0] = new StatisticalNumber(createdAdsPeriodMain);

        final StatisticalResultDTO result = new StatisticalResultDTO(tableCells);
        result.setBaseKey("reports.stats.keydevelopments.numberOfAds");
        result.setFilterAsNotUsed(FilterUsed.FilterType.PAYMENT);
        final String[] rowKeys = { "reports.stats.keydevelopments.numberOfAds.active", "reports.stats.keydevelopments.numberOfAds.scheduled", "reports.stats.keydevelopments.numberOfAds.expired", "reports.stats.keydevelopments.numberOfAds.created" };

        try {
            result.setRowKeys(rowKeys);
            applySinglePeriodColumnHeadersAndKeys(result, queryParameters);
            passGroupFilter(result, queryParameters);
        } catch (final Exception e) {
            System.out.println("Error in number of ads");
            e.printStackTrace();
        }

        return result;
    }

    public StatisticalResultDTO getSinglePeriodNumberOfMembers(final StatisticalKeyDevelopmentsQuery queryParameters) {
        final Period periodMain = queryParameters.getPeriodMain();
        final Collection<? extends Group> groups = queryParameters.getGroups();

        // Number of members
        final int periodMainMembersCount = getElementDao().getNumberOfMembersInGroupsInPeriod(groups, periodMain);

        // Number of new members
        final int periodMainNewMembersCount = getNewMembersCount(periodMain, groups);

        // Number of disappeared members
        final int periodMainDisappearedMembersCount = getDisappearedMembersCount(periodMain, groups);

        // Data structure to build the table
        final Number[][] tableCells = new Number[3][1];

        // First row
        tableCells[0][0] = new StatisticalNumber(periodMainMembersCount);

        // Second row
        tableCells[1][0] = new StatisticalNumber(periodMainNewMembersCount);

        // Third row
        tableCells[2][0] = new StatisticalNumber(periodMainDisappearedMembersCount);

        final StatisticalResultDTO result = new StatisticalResultDTO(tableCells);
        result.setBaseKey("reports.stats.keydevelopments.numberOfMembers");
        final String[] rowKeys = { "reports.stats.keydevelopments.numberOfMembers.numberOfMembers", "reports.stats.keydevelopments.numberOfMembers.numberOfNewMembers", "reports.stats.keydevelopments.numberOfMembers.numberOfDisappearedMembers" };
        try {
            result.setRowKeys(rowKeys);
            applySinglePeriodColumnHeadersAndKeys(result, queryParameters);
            passGroupFilter(result, queryParameters);
        } catch (final Exception e) {
            System.out.println("Error in NumberOfMembers!");
            e.printStackTrace();
        }

        return result;
    }

    public StatisticalResultDTO getSinglePeriodNumberOfTransactions(final StatisticalKeyDevelopmentsQuery queryParameters) {
        final PaymentFilter paymentFilter = getInitializedPaymentFilter(queryParameters);
        // Data structure to build the table
        final Number[][] tableCells = new Number[1][1];
        tableCells[0][0] = new StatisticalNumber(getTransactionAmounts(queryParameters, paymentFilter).size());

        final StatisticalResultDTO result = new StatisticalResultDTO(tableCells);
        result.setBaseKey("reports.stats.keydevelopments.numberOfTransactions");
        String[] rowKeys;
        if (paymentFilter != null) {
            result.setRowHeader(paymentFilter.getName(), 0);
            rowKeys = new String[] { "" };
        } else {
            rowKeys = new String[] { "reports.stats.keydevelopments.grossProduct.allTransactions" };
        }
        try {
            result.setRowKeys(rowKeys);
            if (paymentFilter != null) {
                result.setRowHeader(paymentFilter.getName(), 0);
            }
            applySinglePeriodColumnHeadersAndKeys(result, queryParameters);
            passGroupFilter(result, queryParameters);
            passPaymentFilter(result, queryParameters);
        } catch (final Exception e) {
            System.out.println("Error in NumberOfTransactions");
            e.printStackTrace();
        }

        return result;
    }

    public StatisticalResultDTO getThroughTheTime(final StatisticalKeyDevelopmentsQuery queryParameters) {
        // Query parameters
        final boolean isNumberOfMembers = queryParameters.isNumberOfMembers();
        final boolean isGrossProduct = queryParameters.isGrossProduct();
        final boolean isNumberOfTransactions = queryParameters.isNumberOfTransactions();
        final boolean isTransactionAmount = queryParameters.isTransactionAmount();
        final boolean isAds = queryParameters.isNumberOfAds();
        final ThroughTimeRange throughTimeRange = queryParameters.getThroughTimeRange();
        // get the units
        final Currency currency = getCurrency(queryParameters);
        // Prepare columns metadata
        final ArrayList<String> columnKeysList = new ArrayList<String>(5);
        final ArrayList<String> columnSubHeaderList = new ArrayList<String>(5);
        int columns = 0;
        if (isNumberOfMembers) {
            columnKeysList.add("reports.stats.keydevelopments.numberOfMembers.numberOfMembers");
            columnSubHeaderList.add("");
            columns++;
        }
        if (isGrossProduct) {
            columnKeysList.add("reports.stats.keydevelopments.grossProduct");
            columnSubHeaderList.add(parenthesizeString(currency.getSymbol()));
            columns++;
        }
        if (isNumberOfTransactions) {
            columnKeysList.add("reports.stats.keydevelopments.numberOfTransactions");
            columnSubHeaderList.add("");
            columns++;
        }
        if (isTransactionAmount) {
            columnKeysList.add("reports.stats.keydevelopments.transactionAmount.median");
            columnSubHeaderList.add(parenthesizeString(currency.getSymbol()));
            columns++;
        }
        if (isAds) {
            columnKeysList.add("reports.stats.keydevelopments.numberOfAds.active");
            columnSubHeaderList.add("");
            columns++;
        }

        final String[] columnKeys = columnKeysList.toArray(new String[0]);
        final String[] columnSubHeaders = columnSubHeaderList.toArray(new String[0]);

        // Prepare table dates
        final Period[] periods = queryParameters.getPeriods();

        // Prepare data for table and chart
        final String[] rowHeaders = new String[periods.length];
        final Number[][] tableCells = getThroughTheTimeCalculation(periods, queryParameters, columns, rowHeaders);

        final StatisticalResultDTO result = new StatisticalResultDTO(tableCells);
        String baseKey = "reports.stats.keydevelopments.throughTime";
        passGroupFilter(result, queryParameters);
        passPaymentFilter(result, queryParameters);
        if (throughTimeRange == ThroughTimeRange.YEAR) {
            baseKey += ".years";
        } else if (throughTimeRange == ThroughTimeRange.QUARTER) {
            baseKey += ".quarters";
        } else {
            baseKey += ".months";
        }
        result.setBaseKey(baseKey);
        result.setRowHeaders(rowHeaders);
        result.setColumnKeys(columnKeys);
        result.setColumnSubHeaders(columnSubHeaders);
        result.setMultiGraph(MultiGraph.BY_COLUMN);
        if (queryParameters.isThruTimeGraph()) {
            result.setGraphType(StatisticalResultDTO.GraphType.LINE);
        }
        return result;
    }

    public void setAdDao(final AdDAO adDao) {
        this.adDao = adDao;
    }

    /**
     * Creates the column headers and keys from the query. This is (usually) namePeriod1, namePeriod2 and "Growth". Beware that it also sets the
     * columnSubHeaders. If you reset this, the third column subHeader will be overwritten.
     * @param result the raw data object
     * @param queryParameters
     */
    private void applyColumnHeadersAndKeys(final StatisticalResultDTO result, final StatisticalQuery queryParameters) {
        final String[] columnKeys = { "", "", "reports.stats.general.growth" };
        result.setColumnKeys(columnKeys);
        result.setColumnHeader(queryParameters.getPeriodMain().getName(), 1);
        result.setColumnHeader(queryParameters.getPeriodComparedTo().getName(), 0);
    }

    /**
     * Creates the column headers and keys from the query. This is namePeriod1. Beware that it also sets the columnSubHeaders. If you reset this, the
     * third column subHeader will be overwritten.
     * @param result the raw data object
     * @param queryParameters
     */
    private void applySinglePeriodColumnHeadersAndKeys(final StatisticalResultDTO result, final StatisticalQuery queryParameters) {
        final String[] columnKeys = { "" };
        result.setColumnKeys(columnKeys);
        result.setColumnHeader(queryParameters.getPeriodMain().getName(), 0);
    }

    private int getDisappearedMembersCount(final Period period, final Collection<? extends Group> groups) {
        final MemberQuery memberQuery = new MemberQuery();
        memberQuery.setDeactivationPeriod(period);
        memberQuery.setGroups(groups);
        memberQuery.setPageForCount();
        return PageHelper.getTotalCount(getElementDao().searchHistoryRemoved(memberQuery));
    }

    private int getNewMembersCount(final Period period, final Collection<? extends Group> groups) {
        final MemberQuery memberQuery = new MemberQuery();
        memberQuery.setCreationPeriod(period);
        memberQuery.setGroups(groups);
        memberQuery.setPageForCount();
        return PageHelper.getTotalCount(getElementDao().searchHistoryNew(memberQuery));
    }

    private BigDecimal getSumOfTransactions(final Period period, final Collection<? extends Group> groups, final PaymentFilter paymentFilter) {
        final StatisticalDTO dto = new StatisticalDTO();
        dto.setPeriod(period);
        dto.setGroups(groups);
        dto.setPaymentFilter(paymentFilter);
        return getTransferDao().getSumOfTransactions(dto);
    }

    private Number[][] getThroughTheTimeCalculation(final Period[] periods, final StatisticalKeyDevelopmentsQuery queryParameters, final int columns, final String[] rowHeaders) {
        final byte precision = (byte) getLocalSettings().getPrecision().getValue();
        final Collection<? extends Group> groups = queryParameters.getGroups();
        final PaymentFilter paymentFilter = getInitializedPaymentFilter(queryParameters);
        final Number[][] tableCells = new Number[periods.length][columns];
        final Period totalPeriod = new Period(periods[0].getBegin(), periods[periods.length - 1].getEnd());
        final ThroughTimeRange throughTimeRange = queryParameters.getThroughTimeRange();

        final boolean isNumberOfMembers = queryParameters.isNumberOfMembers();
        final boolean isGrossProduct = queryParameters.isGrossProduct();
        final boolean isNumberOfTransactions = queryParameters.isNumberOfTransactions();
        final boolean isTransactionAmount = queryParameters.isTransactionAmount();
        final boolean isAds = queryParameters.isNumberOfAds();

        final StatisticalDTO dto = new StatisticalDTO();
        dto.setGroups(groups);
        dto.setPaymentFilter(paymentFilter);
        dto.setPeriod(totalPeriod);

        final List<KeyDevelopmentsStatsPerMonthVO> grossProductPerMonth = isGrossProduct ? getTransferDao().getGrossProductPerMonth(dto) : null;
        final List<KeyDevelopmentsStatsPerMonthVO> numberOfTransactionsPerMonth = isNumberOfTransactions ? getTransferDao().getNumberOfTransactionsPerMonth(dto) : null;

        int rowIndex = 0;
        for (final Period period : periods) {
            int columnIndex = 0;
            if (throughTimeRange == ThroughTimeRange.YEAR) {
                rowHeaders[rowIndex] = "" + period.getBegin().get(Calendar.YEAR);
            } else if (throughTimeRange == ThroughTimeRange.QUARTER) {
                rowHeaders[rowIndex] = "" + period.getBegin().get(Calendar.YEAR) + " - " + period.getBeginQuarter().toStringRepresentation();
            } else {
                rowHeaders[rowIndex] = period.getBegin().get(Calendar.YEAR) + "-" + (period.getBegin().get(Calendar.MONTH) + 1 >= 10 ? period.getBegin().get(Calendar.MONTH) + 1 : "0" + (period.getBegin().get(Calendar.MONTH) + 1));
            }

            if (isNumberOfMembers) {
                final int periodMainMembersCount = getElementDao().getNumberOfMembersInGroupsInPeriod(groups, period);
                tableCells[rowIndex][columnIndex] = new StatisticalNumber(periodMainMembersCount);
                columnIndex++;
            }

            if (isGrossProduct) {
                final Double grossProduct = periodize(grossProductPerMonth, period);
                tableCells[rowIndex][columnIndex] = new StatisticalNumber(grossProduct.doubleValue(), precision);
                columnIndex++;
            }

            if (isNumberOfTransactions) {
                final Double numberOfTransactions = periodize(numberOfTransactionsPerMonth, period);
                tableCells[rowIndex][columnIndex] = new StatisticalNumber(numberOfTransactions);
                columnIndex++;
            }
            if (isTransactionAmount) {
                final StatisticalDTO tempDto = new StatisticalDTO();
                tempDto.setGroups(groups);
                tempDto.setPaymentFilter(paymentFilter);
                tempDto.setPeriod(period);
                final List<Number> transactionAmounts = getTransferDao().getTransactionAmounts(tempDto);
                tableCells[rowIndex][columnIndex] = Median.getMedian(transactionAmounts, StatisticalService.ALPHA);
                columnIndex++;
            }

            if (isAds) {
                final Integer activeAdsPeriodMain = adDao.getNumberOfAds(period.getEnd(), groups, Ad.Status.ACTIVE);
                tableCells[rowIndex][columnIndex++] = new StatisticalNumber(activeAdsPeriodMain);
            }
            rowIndex++;
        }
        return tableCells;
    }

    /**
     * Contains the decision logic on how to retrieve a list with all relevant transactionAmounts. Does this by checking if a list was already
     * retrieved. If so, it uses the saved list; if not, it calls getTransactionAmountsFromDatabase.
     * @param queryParameters
     * @param paymentFilter
     * @return a list with all relevant transactionAmounts.
     */
    private List<Number> getTransactionAmounts(final StatisticalKeyDevelopmentsQuery queryParameters, final PaymentFilter paymentFilter) {
        if (queryParameters.equals(savedParameters) && transactionAmounts != null) {
            // if a list was already retrieved, use that
            return transactionAmounts;
        }
        final Period periodMain = queryParameters.getPeriodMain();
        final Collection<? extends Group> groups = queryParameters.getGroups();
        final List<Number> amounts = getTransactionAmountsFromDatabase(periodMain, groups, paymentFilter);
        savedParameters = queryParameters;
        transactionAmounts = amounts;
        return amounts;
    }

    private List<Number> getTransactionAmountsComparedTo(final StatisticalKeyDevelopmentsQuery queryParameters, final PaymentFilter paymentFilter) {
        if (queryParameters.equals(savedParameters) && transactionAmounts2 != null) {
            // if a list was already retrieved, use that
            return transactionAmounts2;
        }
        final Period period = queryParameters.getPeriodComparedTo();
        final Collection<? extends Group> groups = queryParameters.getGroups();
        final List<Number> amounts = getTransactionAmountsFromDatabase(period, groups, paymentFilter);
        savedParameters = queryParameters;
        transactionAmounts2 = amounts;
        return amounts;
    }

    /**
     * gets the transactionAmounts from the database via TransferDao. Don't call directly; Call getTransactionAmounts because this checks if there are
     * cached lists to use, in stead of doing a very heavy database query.
     */
    private List<Number> getTransactionAmountsFromDatabase(final Period period, final Collection<? extends Group> groups, final PaymentFilter paymentFilter) {
        final StatisticalDTO dto = new StatisticalDTO();
        dto.setPeriod(period);
        dto.setGroups(groups);
        dto.setPaymentFilter(paymentFilter);
        final List<Number> list = getTransferDao().getTransactionAmounts(dto);
        return list;
    }

    /**
     * passes the currency for Compared Periods as columnSubHeaders to columns with indexes 0 and 1, and as y-axis units
     * @param result
     * @param queryParameters
     */
    private void passCurrencyCompared(final StatisticalResultDTO result, final StatisticalKeyDevelopmentsQuery queryParameters) {
        final Currency currency = getCurrency(queryParameters);
        result.setColumnSubHeaders(new String[] { parenthesizeString(currency.getSymbol()), parenthesizeString(currency.getSymbol()), "" });
        result.setYAxisUnits(currency.getSymbol());
    }

    /**
     * passes the currency for Single Periods as columnSubHeaders to column with index 0 and as y-axis units
     * @param result
     * @param queryParameters
     */
    private void passCurrencySingle(final StatisticalResultDTO result, final StatisticalKeyDevelopmentsQuery queryParameters) {
        final Currency currency = getCurrency(queryParameters);
        result.setColumnSubHeaders(new String[] { parenthesizeString(currency.getSymbol()) });
    }

    /**
     * gets the data (which may be number of members, number of transactions, gross product, transaction amounts, etc) for one period for through time
     * from the big list with the data for all periods. Summarizes several months together if necessary.
     * 
     * @param grossProductPerMonth, a <code>KeyDevelopmentsStatsPerMonthVO</code>, which is a wrapper aroumnd the data per month.
     * @param period
     * @return the summarized data.
     */
    private double periodize(final List<KeyDevelopmentsStatsPerMonthVO> grossProductPerMonthList, final Period period) {
        final int year = period.getBegin().get(Calendar.YEAR);
        final int startMonth = period.getBegin().get(Calendar.MONTH) + 1;
        final int endMonth = period.getEnd().get(Calendar.MONTH) + 1;
        double result = 0.0;
        for (final KeyDevelopmentsStatsPerMonthVO monthData : grossProductPerMonthList) {
            if (monthData.getMonth() >= startMonth && monthData.getMonth() <= endMonth && monthData.getYear() == year) {
                result += monthData.getDataField().doubleValue();
            }
        }
        return result;
    }

}
