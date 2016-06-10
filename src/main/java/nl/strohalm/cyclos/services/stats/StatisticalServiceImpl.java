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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import javastat.inference.nonparametric.RankSumTest;
import javastat.inference.twosamples.TwoSampProps;
import nl.strohalm.cyclos.dao.accounts.CurrencyDAO;
import nl.strohalm.cyclos.dao.accounts.transactions.TransferDAO;
import nl.strohalm.cyclos.dao.members.ElementDAO;
import nl.strohalm.cyclos.entities.accounts.Currency;
import nl.strohalm.cyclos.entities.accounts.SystemAccountType;
import nl.strohalm.cyclos.entities.accounts.transactions.PaymentFilter;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferType;
import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.entities.reports.StatisticalFinancesQuery;
import nl.strohalm.cyclos.entities.reports.StatisticalNumber;
import nl.strohalm.cyclos.entities.reports.StatisticalQuery;
import nl.strohalm.cyclos.entities.reports.StatisticsWhatToShow;
import nl.strohalm.cyclos.entities.reports.ThroughTimeRange;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.services.fetch.FetchServiceLocal;
import nl.strohalm.cyclos.services.settings.SettingsServiceLocal;
import nl.strohalm.cyclos.services.transfertypes.PaymentFilterServiceLocal;
import nl.strohalm.cyclos.utils.Month;
import nl.strohalm.cyclos.utils.Period;
import nl.strohalm.cyclos.utils.Quarter;
import nl.strohalm.cyclos.utils.statistics.ListOperations;
import nl.strohalm.cyclos.utils.validation.GeneralValidation;
import nl.strohalm.cyclos.utils.validation.ValidationError;
import nl.strohalm.cyclos.utils.validation.Validator;

/**
 * general implementation of StatisticalService; contains general methods for all child classes.
 * @author Rinke
 */
public abstract class StatisticalServiceImpl implements StatisticalServiceLocal {

    /**
     * validates if any item for statistics calculation is checked.
     * @author Rinke
     * 
     */
    class ItemsCheckedValidation implements GeneralValidation {

        private static final long serialVersionUID = -3678161970744028864L;

        public ValidationError validate(final Object queryObj) {
            final StatisticalQuery query = (StatisticalQuery) queryObj;
            int nItems;
            try {
                nItems = query.countItemsChecked();
            } catch (final IllegalAccessException e) {
                nItems = 0;
            }
            if (nItems == 0) {
                return new ValidationError("global.error.nothingSelected");
            }
            return null;
        }

    }

    /**
     * Limits the number of dataPoints which will be processed, in order to limit the server load and to prevent users to request data over extreme
     * long time ranges. The number of dataPoints is calculated as follows:<br>
     * 
     * <pre>
     * numberOfItemSubjects x numberOfThroughTimeXAxisPoints x numberOfPaymentFilters x heavyness.
     * </pre>
     * 
     * where
     * 
     * <pre>
     * heavyness
     * </pre>
     * 
     * is a factor indicating how "heavy" each datapoint is. The following applies to this:
     * <ul>
     * <li>years: heavyness = 2;
     * <li>quarters: heavyness = 1.5;
     * <li>months: heavyness = 1;
     * </ul>
     * So year points count more heavy than month data points.
     * 
     * <p>
     * This may not exceed maxNumbers (which can be different for different statistics types). For now, this validator applies only for Through time
     * stats. Note that this Validation internally calls ThroughTimeRangeValidation, so if you call this class, you need not to call
     * ThroughTimeRangeValidation.
     * 
     * @author Rinke
     */
    class NumberOfDataPointsValidation implements GeneralValidation {

        private static final long serialVersionUID = -8903460874373624675L;

        public ValidationError validate(final Object queryObj) {
            final StatisticalQuery query = (StatisticalQuery) queryObj;
            if (query.getWhatToShow() != StatisticsWhatToShow.THROUGH_TIME) {
                return null;
            }
            // first do a ThroughTimeRangeValidation, because if that's wrong, it makes no sense to continue
            final ValidationError error = new ThroughTimeRangeValidation().validate(queryObj);
            if (error != null) {
                return error;
            }
            // number of itemSubjects
            int nItems;
            try {
                nItems = query.countItemsChecked();
            } catch (final IllegalAccessException e) {
                // form could not be read via reflection, so assume a high number to be safe
                nItems = 5;
            }
            // number of paymentFilters
            final int nFilters = (query.getPaymentFilters().size() > 0) ? query.getPaymentFilters().size() : 1;
            // number of timePoints
            int nTimePoints = 0;
            float heavyness = 1;
            final ThroughTimeRange throughTimeRange = query.getThroughTimeRange();
            if (throughTimeRange == ThroughTimeRange.YEAR) {
                final int initialYear = query.getInitialYear();
                final int finalYear = query.getFinalYear();
                nTimePoints = finalYear + 1 - initialYear;
                heavyness = 2;
            } else if (throughTimeRange == ThroughTimeRange.MONTH) {
                final int initialMonthYear = query.getInitialMonthYear();
                final int finalMonthYear = query.getFinalMonthYear();
                final int initialMonth = query.getInitialMonth().getValue();
                final int finalMonth = query.getFinalMonth().getValue();
                nTimePoints = (12 * (finalMonthYear - initialMonthYear)) + finalMonth + 1 - initialMonth;
            } else { // 'QUARTER'
                final int initialQuarterYear = query.getInitialQuarterYear();
                final int finalQuarterYear = query.getFinalQuarterYear();
                final int initialQuarter = query.getInitialQuarter().getValue();
                final int finalQuarter = query.getFinalQuarter().getValue();
                nTimePoints = (4 * (finalQuarterYear - initialQuarterYear)) + finalQuarter + 1 - initialQuarter;
                heavyness = 1.5f;
            }
            // total = number of data points
            final int nDataPoints = nItems * nFilters * nTimePoints;
            final int maxPointsCorrectedForHeavyness = Math.round(maximumDataPoints / heavyness);
            if (nDataPoints > maxPointsCorrectedForHeavyness) {
                return new ValidationError("reports.stats.general.maxItemsExceded", maxPointsCorrectedForHeavyness, nDataPoints);
            }
            return null;
        }
    }

    /**
     * validates that the maximum number of paymentFilters is not exceeded. Due to a few preconditions, this is done as a GeneralValidation, and not
     * via <code>Validator.property("paymentFilters").maxLength(int i)</code>
     * 
     * @author Rinke
     */
    class NumberOfPaymentFiltersValidation implements GeneralValidation {
        private static final long serialVersionUID        = 2470331423042433415L;
        private static final int  MAX_FILTERS             = 20;
        private static final int  MAX_FILTERS_THROUGHTIME = 5;

        public ValidationError validate(final Object queryObj) {
            final StatisticalQuery query = (StatisticalQuery) queryObj;
            if (!query.anyGraphChecked()) {
                return null;
            }
            int maxFilters = MAX_FILTERS;
            if (query.getWhatToShow() == StatisticsWhatToShow.THROUGH_TIME) {
                maxFilters = MAX_FILTERS_THROUGHTIME;
            }
            final int paymentFiltersSize = query.getPaymentFilters().size();
            if (paymentFiltersSize > maxFilters) {
                return new ValidationError("reports.stats.paymentFilters.maxItemsExceded", maxFilters);
            }
            return null;
        }
    }

    /**
     * validates that the paymentFilters input is not empty, and contains no overlapping paymentfilters. In other words: two selected paymentFilters
     * may not contain the same transferType.
     * 
     * @author Rinke
     */
    class PaymentFiltersNotOverlappingValidation implements GeneralValidation {
        private static final long serialVersionUID = 3261867340688839935L;

        public ValidationError validate(final Object queryObj) {
            final StatisticalQuery query = (StatisticalQuery) queryObj;
            final Collection<PaymentFilter> paymentFilters = query.getPaymentFilters();
            if (paymentFilters.equals(Collections.emptyList())) {
                return new ValidationError("reports.stats.paymentFilters.nothingSelected");
            }
            final HashSet<TransferType> transferTypes = new HashSet<TransferType>();
            for (PaymentFilter filter : paymentFilters) {
                filter = paymentFilterService.load(filter.getId(), PaymentFilter.Relationships.TRANSFER_TYPES);
                final Collection<TransferType> filterTransferTypes = filter.getTransferTypes();
                for (final TransferType transferType : filterTransferTypes) {
                    if (!transferTypes.add(transferType)) {
                        return new ValidationError("reports.stats.paymentFilters.noOverlap");
                    }
                }
            }
            return null;
        }
    }

    /**
     * validates the through time range and all of the connected fields on correct syntax
     * 
     * @author Rinke
     */
    class ThroughTimeRangeValidation implements GeneralValidation {
        private static final long serialVersionUID = -8598196174248973591L;

        public ValidationError validate(final Object queryObj) {
            final StatisticalQuery query = (StatisticalQuery) queryObj;
            final StatisticsWhatToShow whatToShow = query.getWhatToShow();
            if (whatToShow != StatisticsWhatToShow.THROUGH_TIME) {
                return null;
            }
            final ThroughTimeRange throughTimeRange = query.getThroughTimeRange();
            try {
                if (throughTimeRange == ThroughTimeRange.YEAR) {
                    final int initialYear = query.getInitialYear();
                    final int finalYear = query.getFinalYear();
                    if (initialYear >= finalYear) {
                        return new ValidationError("reports.stats.error.finalDateLesserThanInitialDate");
                    }
                } else if (throughTimeRange == ThroughTimeRange.MONTH) {
                    final int initialYear = query.getInitialMonthYear();
                    final int finalYear = query.getFinalMonthYear();
                    if (initialYear > finalYear) {
                        return new ValidationError("reports.stats.error.finalDateLesserThanInitialDate");
                    } else if (initialYear == finalYear) {
                        final Month initialMonth = query.getInitialMonth();
                        final Month finalMonth = query.getFinalMonth();
                        if (initialMonth.getValue() >= finalMonth.getValue()) {
                            return new ValidationError("reports.stats.error.finalDateLesserThanInitialDate");
                        }
                    }
                } else { // 'QUARTER'
                    final int initialYear = query.getInitialQuarterYear();
                    final int finalYear = query.getFinalQuarterYear();
                    if (initialYear > finalYear) {
                        return new ValidationError("reports.stats.error.finalDateLesserThanInitialDate");
                    } else if (initialYear == finalYear) {
                        final Quarter initialQuarter = query.getInitialQuarter();
                        final Quarter finalQuarter = query.getFinalQuarter();
                        if (initialQuarter.getValue() >= finalQuarter.getValue()) {
                            return new ValidationError("reports.stats.error.finalDateLesserThanInitialDate");
                        }
                    }
                }
            } catch (final NullPointerException npe) {
                return new ValidationError("reports.stats.error.initialAndFinalYearsRequired");
            }
            return null;
        }
    }

    /**
     * Calculates the p-value from two sample arrays. It is basically a wrapper around the if's.
     * @param array1
     * @param array2
     * @return a StatisticalNumber indicating the p-value. If one of the sizes is too small, then a null pvalue is returned.
     */
    protected static StatisticalNumber calculatePvalue(final double[] array1, final double[] array2) {
        StatisticalNumber p = StatisticalNumber.createNullPvalue();
        if (array1.length >= MINIMUM_NUMBER_OF_VALUES && array2.length >= MINIMUM_NUMBER_OF_VALUES && MINIMUM_NUMBER_OF_VALUES > 2) {
            final RankSumTest rst = new RankSumTest(StatisticalService.ALPHA, "equal", array1, array2);
            if (!Double.isNaN(rst.pValue)) { // in case of NO variation then the p will be NaN (because division by variance = 0)
                p = StatisticalNumber.createPvalue(rst.pValue);
            }
        }
        return p;
    }

    /**
     * Calculates the p-value from two proportion samples.
     * @param proportion1 the number of observations fulfilling the specific criteria in group 1, for example the number of members not trading.
     * @param population1 the total number of observations in group 1, for example the total number of members not trading.
     * @param proportion2 same as proportion1, but for the other group.
     * @param population2 same as population2, but for the other group.
     * @return the pvalue as a statisticalNumber.
     */
    protected static StatisticalNumber calculatePvalue(final int proportion1, final int population1, final int proportion2, final int population2) {
        StatisticalNumber pValue = StatisticalNumber.createNullPvalue();
        if (population1 >= StatisticalService.MINIMUM_NUMBER_OF_VALUES && population2 >= StatisticalService.MINIMUM_NUMBER_OF_VALUES && proportion1 > 0 && proportion2 > 0) {
            final TwoSampProps twoSampProps = new TwoSampProps(StatisticalService.ALPHA, 0, "equal", proportion1, population1, proportion2, population2);
            pValue = StatisticalNumber.createPvalue(twoSampProps.pValue);
        }
        return pValue;
    }

    /**
     * Calculates the p-value from two sample <code>List</code>s. Is exactly the same as <code>calculatePvalue(double[], double[]), only
     * accepts different input type params.
     * @param list1 a List<Number> of Numbers being the first sample
     * @param list2 a List<Number> of Numbers being the second sample
     * @return a StatisticalNumber indicating the p-value. If one of the sizes is too small, then a null pvalue is returned.
     */
    protected static StatisticalNumber calculatePvalue(final List<Number> list1, final List<Number> list2) {
        final double[] array1 = ListOperations.listToArray(list1);
        final double[] array2 = ListOperations.listToArray(list2);
        return calculatePvalue(array1, array2);
    }

    private int                  maximumDataPoints = 1000;

    /**
     * a Validator for the paymentFilters.
     */

    protected FetchServiceLocal       fetchService;
    // the settingsService is needed for formating the x-axis numbers on the histogram graphs.
    private SettingsServiceLocal      settingsService;

    private PaymentFilterServiceLocal paymentFilterService;

    private TransferDAO          transferDao;

    private CurrencyDAO          currencyDao;

    private ElementDAO           elementDao;

    public void setCurrencyDao(final CurrencyDAO currencyDao) {
        this.currencyDao = currencyDao;
    }

    public void setElementDao(final ElementDAO elementDao) {
        this.elementDao = elementDao;
    }

    public void setFetchServiceLocal(final FetchServiceLocal fetchService) {
        this.fetchService = fetchService;
    }

    public void setMaximumDataPoints(final int maximumDataPoints) {
        this.maximumDataPoints = maximumDataPoints;
    }

    public void setPaymentFilterServiceLocal(final PaymentFilterServiceLocal paymentFilterService) {
        this.paymentFilterService = paymentFilterService;
    }

    public void setSettingsServiceLocal(final SettingsServiceLocal settingsService) {
        this.settingsService = settingsService;
    }

    public void setTransferDao(final TransferDAO transferDao) {
        this.transferDao = transferDao;
    }

    public void validate(final Object query) {
        final Validator statsValidator = new Validator("");
        statsValidator.general(new ItemsCheckedValidation(), new NumberOfDataPointsValidation());
        if (query instanceof StatisticalFinancesQuery) {
            statsValidator.general(new PaymentFiltersNotOverlappingValidation(), new NumberOfPaymentFiltersValidation());
        }
        statsValidator.validate(query);
    }

    /**
     * This version is used for other typed tables, which just simply show any kind of data in rows or columns. There is no growth or p-value column,
     * as in the overloaded version of this method.
     * @return the StatisticalResultDTO object, with only the data set. Other elements still need to be set
     * @deprecated only used for testing
     */
    @Deprecated
    protected StatisticalResultDTO createDataObject(final int rows, final int columns, final int factor, final byte precision) {
        final int[] rowFactors = new int[rows];
        Arrays.fill(rowFactors, factor);
        return this.createDataObject(rows, columns, rowFactors, precision);
    }

    /**
     * See createDataObject(int rows, int columns, int factor). This overloaded version takes an int array (int[]) for the factor param, so every row
     * can have its own factor.
     * @param rows
     * @param columns
     * @deprecated only used for testing
     */
    @Deprecated
    protected StatisticalResultDTO createDataObject(final int rows, final int columns, final int[] rowFactor, final byte precision) {
        final Number[][] data = new Number[rows][columns];
        fillTwoDimensionalArray(data, rows, columns, rowFactor, precision, false);
        return new StatisticalResultDTO(data);
    }

    /**
     * Creates a result table with random values for a table comparing two periods. So generally, the first column is the result for this period, 2nd
     * column is compared period. There is always a third, column, and sometimes a 4th, depending on the tableType param.
     * @return the StatisticalResultDTO object, with only the data set. Other elements still need to be set
     * @deprecated only used for testing
     */
    @Deprecated
    protected StatisticalResultDTO createDataObject(final int rows, final int tableFactor, final TableType tableType, final byte precision) {
        final int[] rowFactors = new int[rows];
        Arrays.fill(rowFactors, tableFactor);
        return this.createDataObject(rows, rowFactors, tableType, precision);
    }

    /**
     * Overloaded version of createDataObject(int rows, int factor, TableType tableType), which takes in stead of the int factor, an array of ints.
     * Each row then gets its own factor.
     * @deprecated only used for testing
     */
    @Deprecated
    protected StatisticalResultDTO createDataObject(final int rows, final int[] rowFactors, final TableType tableType, final byte precision) {
        final int columns = 2 + tableType.getValue();
        final Number[][] data = new Number[rows][columns];
        fillTwoDimensionalArray(data, rows, 2, rowFactors, precision, (tableType != TableType.GROWTH));
        for (final Number[] row : data) {
            if (tableType != TableType.P) { // calculate growth %% in column 3
                row[2] = StatisticalNumber.createPercentage(row[0], row[1]);
            }
            if (tableType != TableType.GROWTH) { // assign random p-values
                row[columns - 1] = StatisticalNumber.createPvalue(Math.random() * 0.4);
            }
        }
        return new StatisticalResultDTO(data);
    }

    /**
     * tries to get the associated <code>Currency</code> via several methods. If all of these fail, an empty currency is returned, resulting in empty
     * strings as name and symbol for the currency.
     * 
     * @param queryParameters
     * @return the associated currency.
     */
    protected Currency getCurrency(final StatisticalQuery queryParameters) {
        // first check if a system account type is defined, and if so, get it via this
        final SystemAccountType systemAccountFilter = getInitializedSystemAccountFilter(queryParameters);
        if (systemAccountFilter != null) {
            return systemAccountFilter.getCurrency();
        }
        // if this did not succeed, try to get it via the paymentFilter
        final PaymentFilter paymentFilter = getInitializedPaymentFilter(queryParameters);
        // if a paymentFilter was specified, get its associated currency
        if (paymentFilter != null) {
            return paymentFilter.getAccountType().getCurrency();
        }
        // if no paymentFilter was specified, check if only one currency is installed, and if so, use that one.
        final List<Currency> currencyList = currencyDao.listAll();
        if (currencyList.size() == 1) {
            return currencyList.get(0);
        }
        // if all of the above failed, use empty currency
        final Currency result = new Currency();
        result.setName("");
        result.setSymbol("");
        return result;
    }

    protected ElementDAO getElementDao() {
        return elementDao;
    }

    /**
     * gets the paymentFilter from the query, and initializes it via the fetchService. The paymentFilter is then reset in the query, but also returned
     * by the method.
     * 
     * @param queryParameters
     * @return an initialized paymentFilter.
     */
    protected PaymentFilter getInitializedPaymentFilter(final StatisticalQuery queryParameters) {
        PaymentFilter paymentFilter = queryParameters.getPaymentFilter();
        if (paymentFilter != null && paymentFilter.getName() == null) {
            paymentFilter = fetchService.fetch(paymentFilter, PaymentFilter.Relationships.TRANSFER_TYPES, PaymentFilter.Relationships.ACCOUNT_TYPE);
            queryParameters.setPaymentFilter(paymentFilter);
        }
        return paymentFilter;
    }

    /**
     * gets the paymentFilters from the query, and initializes each of the containing filter via the fetchService. PaymentFilters is then reset in the
     * query, but it is also returned by this method. <br>
     * <b>Note</b> the difference between <code>getInitializedPaymentFilter</code> and <code>getInitializedPaymentFilters</code>. The first is for a
     * selector in which only one paymentFilter may be selected; the second is for a selector where multiple payment filters may be selected.
     * 
     * @param queryParameters the query
     * @return a collection with initialized paymentFilters
     */
    protected Collection<PaymentFilter> getInitializedPaymentFilters(final StatisticalQuery queryParameters) {
        final Collection<PaymentFilter> paymentFilters = queryParameters.getPaymentFilters();
        final ArrayList<PaymentFilter> newList = new ArrayList<PaymentFilter>(paymentFilters.size());
        boolean anyChanges = false;
        for (PaymentFilter paymentFilter : paymentFilters) {
            if (paymentFilter.getName() == null) {
                paymentFilter = fetchService.fetch(paymentFilter, PaymentFilter.Relationships.TRANSFER_TYPES);
                anyChanges = true;
            }
            newList.add(paymentFilter);
        }
        if (anyChanges) {
            queryParameters.setPaymentFilters(newList);
        }
        return newList;
    }

    protected SystemAccountType getInitializedSystemAccountFilter(final StatisticalQuery queryParameters) {
        SystemAccountType systemAccountFilter = queryParameters.getSystemAccountFilter();
        if (systemAccountFilter != null) {
            if (systemAccountFilter.getName() == null) {
                systemAccountFilter = fetchService.fetch(systemAccountFilter);
            }
            queryParameters.setSystemAccountFilter(systemAccountFilter);
        }
        return systemAccountFilter;
    }

    protected LocalSettings getLocalSettings() {
        return settingsService.getLocalSettings();
    }

    /**
     * Generates the row headers in the table in through time: the names of the months, quarters or years
     * 
     * @param throughTimeRange the type of the range (month, year, etc)
     * @param period one period inside this range. It is called once for every period inside the range.
     * @return a String representing the row header of the table.
     */
    protected String getRowHeaders(final ThroughTimeRange throughTimeRange, final Period period) {
        String result = "";
        if (throughTimeRange == ThroughTimeRange.MONTH) {
            result = period.getBegin().get(Calendar.YEAR) + " - " + (period.getBegin().get(Calendar.MONTH) + 1 >= 10 ? period.getBegin().get(Calendar.MONTH) + 1 : "0" + (period.getBegin().get(Calendar.MONTH) + 1));
        } else if (throughTimeRange == ThroughTimeRange.QUARTER) {
            result = period.getBegin().get(Calendar.YEAR) + " - " + period.getBeginQuarter().toStringRepresentation();
        } else {
            result = "" + period.getBegin().get(Calendar.YEAR);
        }
        return result;
    }

    protected TransferDAO getTransferDao() {
        return transferDao;
    }

    /**
     * parentesizes a String, which means that it puts the String inside (). Used for graph axis units and column subheaders.
     * 
     * @param inputString the String to be parentesized
     * @return the inputString with ( ) around it. Only if the inputString is empty, an empty string without () is returned.
     */
    protected String parenthesizeString(final String inputString) {
        if (inputString.length() == 0) {
            return "";
        }
        return "(" + inputString + ")";
    }

    /**
     * passes the used GroupFilter to the result object.
     * 
     * @param result a StatisticalResultDTO object containing the results.
     * @param queryParameters
     */
    protected void passGroupFilter(final StatisticalResultDTO result, final StatisticalQuery queryParameters) {
        Collection<Group> groupFilter = queryParameters.getGroups();
        groupFilter = fetchService.fetch(groupFilter);
        result.setFilter(groupFilter);
    }

    /**
     * passes the used PaymentFilter to the result object. The following possibilities are there:
     * <ul>
     * <li><i>paymentFilter property</i>: passes the paymentFilter
     * <li><i>paymentFilter<b>s</b> property</i>: only passes the paymentFilter if the size of the paymentFilters collection is 1. Then it passes the
     * only element. If the size > 1, then nothing is passed.
     * </ul>
     * <br>
     * On the fly, the paymentfilter is initialized.
     * 
     * @param result a StatisticalResultDTO object containing the results.
     * @param queryParameters
     */
    protected void passPaymentFilter(final StatisticalResultDTO result, final StatisticalQuery queryParameters) {
        final PaymentFilter paymentFilter = getInitializedPaymentFilter(queryParameters);
        if (paymentFilter == null) {
            final Collection<? extends PaymentFilter> paymentFilters = getInitializedPaymentFilters(queryParameters);
            if (paymentFilters.size() == 0) {
                // in this case apparantly the paymentFilter was used, and not paymentFilterS.
                result.setFilter(paymentFilter);
            } else if (paymentFilters.size() == 1) {
                for (final PaymentFilter paymentFilterItem : paymentFilters) {
                    result.setFilter(paymentFilterItem);
                    return;
                }
            }
        } else {
            result.setFilter(paymentFilter);
        }
    }

    /**
     * passes the used System account Filter to the result object.
     * 
     * @param result a StatisticalResultDTO object containing the results.
     * @param queryParameters
     */
    protected void passSystemAccountFilter(final StatisticalResultDTO result, final StatisticalQuery queryParameters) {
        final SystemAccountType systemAccountFilter = getInitializedSystemAccountFilter(queryParameters);
        result.setFilter(systemAccountFilter);
    }

    private Number[][] fillTwoDimensionalArray(final Number[][] data, final int rows, final int columns, final int[] rowFactors, final byte precision, final boolean hasErrors) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                final double datavalue = Math.random() * rowFactors[i];
                final Double errorvalue = (hasErrors) ? Math.random() * datavalue : null;
                data[i][j] = new StatisticalNumber(datavalue, errorvalue, precision);
            }
        }
        return data;
    }

}
