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

import java.util.Collection;

import nl.strohalm.cyclos.entities.accounts.Currency;
import nl.strohalm.cyclos.entities.accounts.SystemAccountType;
import nl.strohalm.cyclos.entities.accounts.transactions.PaymentFilter;
import nl.strohalm.cyclos.entities.reports.StatisticalFinancesQuery;
import nl.strohalm.cyclos.entities.reports.StatisticalQuery;
import nl.strohalm.cyclos.services.stats.finances.FinanceStatsComparePeriods;
import nl.strohalm.cyclos.services.stats.finances.FinanceStatsSinglePeriod;
import nl.strohalm.cyclos.services.stats.finances.FinanceStatsThroughTime;
import nl.strohalm.cyclos.utils.NamedPeriod;
import nl.strohalm.cyclos.utils.Period;

/**
 * implementation of the StatisticalFinancesService.<br>
 * This class is responsible of the gathering of the Finance Statistics. The calculations are delegated to the {@link FinanceStatsSinglePeriod} class.
 * 
 * @author Rinke
 * 
 */

public class StatisticalFinancesServiceImpl extends StatisticalServiceImpl implements StatisticalFinancesServiceLocal {

    public StatisticalResultDTO getComparePeriodsExpenditure(final StatisticalFinancesQuery queryParameters) {
        final FinanceStatsComparePeriods compareStats = getCompareStats();
        final NamedPeriod periodMain = queryParameters.getPeriodMain();
        final NamedPeriod periodAlt = queryParameters.getPeriodComparedTo();
        final Collection<PaymentFilter> paymentFilters = getInitializedPaymentFilters(queryParameters);
        final SystemAccountType systemAccountFilter = getInitializedSystemAccountFilter(queryParameters);
        // Initialize
        StatisticalResultDTO result = null;
        final String baseKey = "reports.stats.finances.ComparePeriods.expenditure";
        // Assignment of results
        if (paymentFilters.size() > 0) {
            final Number[][] tableCells = compareStats.getTableCellsExpenditureComparePeriods(periodMain, periodAlt, paymentFilters, systemAccountFilter);
            result = new StatisticalResultDTO(tableCells);
            result.setBaseKey(baseKey);
            applyColumnHeadersAndKeys(result, queryParameters);
            assignRowKeysCompare(result, paymentFilters);
            // filter assignment
            passSystemAccountFilter(result, queryParameters);
            applyCurrency(result, queryParameters, 3, 2);
            // rendering of graph or table
            if (queryParameters.isExpenditureGraph()) {
                result.setGraphDimensions(StatisticalResultDTO.TableToGraph.COLUMN_IS_CATEGORY, null, 2);
                result.setGraphType(StatisticalResultDTO.GraphType.BAR);
            }
        } else {
            result = StatisticalResultDTO.noDataAvailable(baseKey);
        }
        return result;
    }

    public StatisticalResultDTO getComparePeriodsIncome(final StatisticalFinancesQuery queryParameters) {
        final FinanceStatsComparePeriods compareStats = getCompareStats();
        final NamedPeriod periodMain = queryParameters.getPeriodMain();
        final NamedPeriod periodAlt = queryParameters.getPeriodComparedTo();
        final Collection<PaymentFilter> paymentFilters = getInitializedPaymentFilters(queryParameters);
        final SystemAccountType systemAccountFilter = getInitializedSystemAccountFilter(queryParameters);
        // Initialize
        StatisticalResultDTO result = null;
        final String baseKey = "reports.stats.finances.ComparePeriods.income";
        // Assignment of results
        if (paymentFilters.size() > 0) {
            final Number[][] tableCells = compareStats.getTableCellsIncomeComparePeriods(periodMain, periodAlt, paymentFilters, systemAccountFilter);
            result = new StatisticalResultDTO(tableCells);
            result.setBaseKey(baseKey);
            applyColumnHeadersAndKeys(result, queryParameters);
            assignRowKeysCompare(result, paymentFilters);
            // filter assignment
            passSystemAccountFilter(result, queryParameters);
            applyCurrency(result, queryParameters, 3, 2);
            // rendering of graph or table
            if (queryParameters.isIncomeGraph()) {
                result.setGraphDimensions(StatisticalResultDTO.TableToGraph.COLUMN_IS_CATEGORY, null, 2);
                result.setGraphType(StatisticalResultDTO.GraphType.BAR);
            }
        } else {
            result = StatisticalResultDTO.noDataAvailable(baseKey);
        }
        return result;
    }

    public StatisticalResultDTO getSinglePeriodExpenditure(final StatisticalFinancesQuery queryParameters) {
        final FinanceStatsSinglePeriod singlePeriodStats = getFinanceStats();
        final NamedPeriod period = queryParameters.getPeriodMain();
        final Collection<PaymentFilter> paymentFilters = getInitializedPaymentFilters(queryParameters);
        final SystemAccountType systemAccountFilter = getInitializedSystemAccountFilter(queryParameters);
        // Initialize
        StatisticalResultDTO result = null;
        final String baseKey = "reports.stats.finances.singlePeriod.expenditure";
        // Assignment of results
        if (paymentFilters.size() > 0) {
            final Number[][] tableCells = singlePeriodStats.getTableCellsExpenditureSinglePeriod(period, paymentFilters, systemAccountFilter);
            result = new StatisticalResultDTO(tableCells);
            result.setBaseKey(baseKey);
            final String[] colKeys = { "reports.stats.finances.expenditure" };
            result.setColumnKeys(colKeys);
            assignRowKeysSingle(result, paymentFilters, tableCells.length);
            // filter assignment
            passSystemAccountFilter(result, queryParameters);
            passPaymentFilter(result, queryParameters);
            result.setFilter(period);
            applyCurrency(result, queryParameters, 1, 1);
            // rendering of graph or table
            if (queryParameters.isOverview()) {
                result.setShowTable(false);
                result.setGraphDimensions(StatisticalResultDTO.TableToGraph.COLUMN_IS_CATEGORY, null, 1);
                result.setGraphType(StatisticalResultDTO.GraphType.PIE);
            } else {
                if (queryParameters.isIncomeGraph()) {
                    result.setGraphDimensions(StatisticalResultDTO.TableToGraph.COLUMN_IS_CATEGORY, null, 1);
                    result.setGraphType(StatisticalResultDTO.GraphType.PIE);
                }
            }
        } else {
            result = StatisticalResultDTO.noDataAvailable(baseKey);
        }
        return result;
    }

    /**
     * gets the results for single period income.
     */
    public StatisticalResultDTO getSinglePeriodIncome(final StatisticalFinancesQuery queryParameters) {
        final FinanceStatsSinglePeriod singlePeriodStats = getFinanceStats();
        final NamedPeriod period = queryParameters.getPeriodMain();
        final Collection<PaymentFilter> paymentFilters = getInitializedPaymentFilters(queryParameters);
        final SystemAccountType systemAccountFilter = getInitializedSystemAccountFilter(queryParameters);
        // Initialize
        StatisticalResultDTO result = null;
        final String baseKey = "reports.stats.finances.singlePeriod.income";
        // Assignment of results
        if (paymentFilters.size() > 0) {
            final Number[][] tableCells = singlePeriodStats.getTableCellsIncomeSinglePeriod(period, paymentFilters, systemAccountFilter);
            result = new StatisticalResultDTO(tableCells);
            result.setBaseKey(baseKey);
            final String[] colKeys = { "reports.stats.finances.income" };
            result.setColumnKeys(colKeys);
            assignRowKeysSingle(result, paymentFilters, tableCells.length);
            // filter assignment
            passSystemAccountFilter(result, queryParameters);
            passPaymentFilter(result, queryParameters);
            result.setFilter(period);
            applyCurrency(result, queryParameters, 1, 1);
            // rendering of graph or table
            if (queryParameters.isOverview()) {
                result.setShowTable(false);
                result.setGraphDimensions(StatisticalResultDTO.TableToGraph.COLUMN_IS_CATEGORY, null, 1);
                result.setGraphType(StatisticalResultDTO.GraphType.PIE);
            } else {
                if (queryParameters.isIncomeGraph()) {
                    result.setGraphDimensions(StatisticalResultDTO.TableToGraph.COLUMN_IS_CATEGORY, null, 1);
                    result.setGraphType(StatisticalResultDTO.GraphType.PIE);
                }
            }
        } else {
            result = StatisticalResultDTO.noDataAvailable(baseKey);
        }
        return result;
    }

    /**
     * gathers the results for the single period overview, and stores this in the <code>StatisticalResultDTO</code>.
     */
    public StatisticalResultDTO getSinglePeriodOverview(final StatisticalFinancesQuery queryParameters) {
        final FinanceStatsSinglePeriod singlePeriodStats = getFinanceStats();
        final NamedPeriod period = queryParameters.getPeriodMain();
        final Collection<PaymentFilter> paymentFilters = getInitializedPaymentFilters(queryParameters);
        final SystemAccountType systemAccountFilter = getInitializedSystemAccountFilter(queryParameters);
        // Initialize
        StatisticalResultDTO result = null;
        final String baseKey = "reports.stats.finances.singlePeriod.overview";

        // Assignment of results
        if (paymentFilters.size() > 0) {
            final Number[][] tableCells = singlePeriodStats.getTableCellsOverviewSinglePeriod(period, paymentFilters, systemAccountFilter);
            result = new StatisticalResultDTO(tableCells);
            result.setBaseKey(baseKey);
            final String[] colKeys = { "reports.stats.finances.income", "reports.stats.finances.expenditure", "reports.stats.finances.balance" };
            result.setColumnKeys(colKeys);
            assignRowKeysSingle(result, paymentFilters, tableCells.length);
            // filter assignment
            passSystemAccountFilter(result, queryParameters);
            passPaymentFilter(result, queryParameters);
            result.setFilter(period);
            applyCurrency(result, queryParameters, 3, 3);
            // graph
            if (queryParameters.isOverviewGraph()) {
                result.setGraphDimensions(StatisticalResultDTO.TableToGraph.COLUMN_IS_CATEGORY, tableCells.length - 2, 2);
                result.setGraphType(StatisticalResultDTO.GraphType.BAR);
            }
        } else {
            result = StatisticalResultDTO.noDataAvailable(baseKey);
        }
        return result;
    }

    public StatisticalResultDTO getThroughTimeExpenditure(final StatisticalFinancesQuery queryParameters) {
        final FinanceStatsThroughTime thruStats = getThruStats();
        final Period[] periods = queryParameters.getPeriods();
        final Collection<PaymentFilter> paymentFilters = getInitializedPaymentFilters(queryParameters);
        final SystemAccountType systemAccountFilter = getInitializedSystemAccountFilter(queryParameters);
        // Initialize
        StatisticalResultDTO result = null;
        final String baseKey = "reports.stats.finances.ThroughTime.expenditure";
        // Assignment of results
        if (paymentFilters.size() > 0) {
            final Number[][] tableCells = thruStats.getTableCellsExpenditureThroughTime(periods, paymentFilters, systemAccountFilter);
            result = new StatisticalResultDTO(tableCells);
            result.setBaseKey(baseKey);
            applyColumnHeadersAndKeys(result, paymentFilters);
            assignRowHeaders(result, queryParameters, periods);
            // filter assignment
            passSystemAccountFilter(result, queryParameters);
            applyCurrency(result, queryParameters, paymentFilters.size(), paymentFilters.size());
            // rendering of graph or table
            if (queryParameters.isExpenditureGraph()) {
                result.setGraphType(StatisticalResultDTO.GraphType.LINE);
            }
        } else {
            result = StatisticalResultDTO.noDataAvailable(baseKey);
        }
        return result;
    }

    public StatisticalResultDTO getThroughTimeIncome(final StatisticalFinancesQuery queryParameters) {
        final FinanceStatsThroughTime thruStats = getThruStats();
        final Period[] periods = queryParameters.getPeriods();
        final Collection<PaymentFilter> paymentFilters = getInitializedPaymentFilters(queryParameters);
        final SystemAccountType systemAccountFilter = getInitializedSystemAccountFilter(queryParameters);
        // Initialize
        StatisticalResultDTO result = null;
        final String baseKey = "reports.stats.finances.ThroughTime.income";
        // Assignment of results
        if (paymentFilters.size() > 0) {
            final Number[][] tableCells = thruStats.getTableCellsIncomeThroughTime(periods, paymentFilters, systemAccountFilter);
            result = new StatisticalResultDTO(tableCells);
            result.setBaseKey(baseKey);
            applyColumnHeadersAndKeys(result, paymentFilters);
            assignRowHeaders(result, queryParameters, periods);
            // filter assignment
            passSystemAccountFilter(result, queryParameters);
            applyCurrency(result, queryParameters, paymentFilters.size(), paymentFilters.size());
            // rendering of graph or table
            if (queryParameters.isIncomeGraph()) {
                result.setGraphType(StatisticalResultDTO.GraphType.LINE);
            }
        } else {
            result = StatisticalResultDTO.noDataAvailable(baseKey);
        }
        return result;
    }

    /**
     * Creates the column headers and keys from the query for through time.
     * 
     * @param result the raw data object
     * @param queryParameters
     */
    private void applyColumnHeadersAndKeys(final StatisticalResultDTO result, final Collection<PaymentFilter> paymentFilters) {
        final String[] columnKeys = new String[paymentFilters.size()];
        result.setColumnKeys(columnKeys);
        int i = 0;
        for (final PaymentFilter paymentFilter : paymentFilters) {
            result.setColumnHeader(paymentFilter.getName(), i++);
        }
    }

    /**
     * Creates the column headers and keys from the query for Compare periods. This is (usually) namePeriod1, namePeriod2 and "Growth". Beware that it
     * also sets the columnSubHeaders. If you reset this, the third column subHeader will be overwritten.
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
     * Takes care that the units of the currency are placed in the Column subHeader, between perenthesis. Also passes the units to the y-axis label of
     * the graph.
     * 
     * @param result the <code>StatisticalResultDTO</code> result object to which all must be passed to.
     * @param queryParameters the <code>StatisticalQuery</code> object containing the form params
     * @param totalCols an int specifying the total amount of column headers.
     * @param applyToCols an int specifying the number of column subheaders in which the units of the currency must be placed. If this equals
     * totalCols, all columns get this subHeader. SubHeaders are assigned starting from index 0, so if this equals totalCols - 1, only the last column
     * gets NO subHeader.
     * @throws IllegalArgumentException if applyToCols > totalCols
     */
    private void applyCurrency(final StatisticalResultDTO result, final StatisticalQuery queryParameters, final int totalCols, final int applyToCols) {
        if (applyToCols > totalCols) {
            throw new IllegalArgumentException("Too many column subHeaders specified");
        }
        final Currency currency = getCurrency(queryParameters);
        final String[] columnSubHeaders = new String[totalCols];
        for (int i = 0; i < totalCols; i++) {
            columnSubHeaders[i] = (i < applyToCols) ? parenthesizeString(currency.getSymbol()) : "";
        }
        result.setColumnSubHeaders(columnSubHeaders);
        result.setYAxisUnits(currency.getSymbol());
    }

    /**
     * assigns row headers for through time
     * @param result
     * @param queryParameters
     * @param periods
     */
    private void assignRowHeaders(final StatisticalResultDTO result, final StatisticalQuery queryParameters, final Period[] periods) {
        final String[] rowHeaders = new String[periods.length];
        int i = 0;
        for (final Period period : periods) {
            rowHeaders[i++] = getRowHeaders(queryParameters.getThroughTimeRange(), period);
        }
        result.setRowHeaders(rowHeaders);
    }

    /**
     * assigns row keys to the result object, for all compare periods cases.
     * 
     * @param result the <code>StatisticalResultDTO</code> object
     * @param paymentFilters the names of these paymentfilters will simply be added.
     */
    private void assignRowKeysCompare(final StatisticalResultDTO result, final Collection<PaymentFilter> paymentFilters) {
        final String[] rowHeaders = new String[paymentFilters.size()];
        int i = 0;
        for (final PaymentFilter filter : paymentFilters) {
            rowHeaders[i++] = filter.getName();
        }
        result.setRowHeaders(rowHeaders);
    }

    /**
     * assigns the rowKeys for all Single period cases.
     * 
     * @param result the <code>StatisticalResultDTO</code> object
     * @param paymentFilters
     * @param length the total number of needed row keys. If bigger than the size of the paymentFilters collection, than "other" and "sum" may be
     * added to the rowkeys.
     */
    private void assignRowKeysSingle(final StatisticalResultDTO result, final Collection<PaymentFilter> paymentFilters, final int length) {
        final String[] filterNames = getFinanceStats().getPaymentFilterNames(paymentFilters);
        final String[] rowKeys = new String[length];
        final int extraKeysCount = length - filterNames.length;
        if (extraKeysCount == 2) {
            rowKeys[length - 2] = "reports.stats.finances.other";
            rowKeys[length - 1] = "reports.stats.general.sum";
        }
        if (extraKeysCount == 1) {
            rowKeys[length - 1] = "reports.stats.finances.other";
        }
        result.setRowKeys(rowKeys);
        for (int i = 0; i < filterNames.length; i++) {
            result.setRowHeader(filterNames[i], i);
        }
    }

    private FinanceStatsComparePeriods getCompareStats() {
        return new FinanceStatsComparePeriods(getTransferDao());
    }

    private FinanceStatsSinglePeriod getFinanceStats() {
        return new FinanceStatsSinglePeriod(getTransferDao());
    }

    private FinanceStatsThroughTime getThruStats() {
        return new FinanceStatsThroughTime(getTransferDao());
    }

}
