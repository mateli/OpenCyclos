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
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

import nl.strohalm.cyclos.entities.reports.StatisticalNumber;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.utils.StringValuedEnum;
import nl.strohalm.cyclos.utils.statistics.ListOperations;

/**
 * This class takes a simple list with data, retrieved straight from a database query, and uses it to create a StatisticalResultDTO value object for
 * the creation of a Histogram. The list of data is divided into histogram categories of reasonable size for the statistics graphs.
 * <p>
 * <b>Beware</b>: the class cannot handle negative x-axis values. It starts counting at 0, so if the x-axis factor has negative values, then the first
 * category on the x-axis will be VERY big, because it also contains all x-values < 0. In this case, no exception is raised, but the figure looks
 * rather silly, with one big first category, and the rest of the x-axis categories very tiny.
 * <p>
 * <b>Beware 2:</b><br>
 * Note that lists of Integers and Lists of Doubles are treated differently, especially where it concerns the division over x-axis categories. You
 * should NOT enter a List of numbers which represent integer values as a List of Doubles. If you do so, the x-axis categories will use irrelevant
 * broken numbers and your histograph may contain a lot of silly gaps..
 * 
 * @author Rinke
 * 
 */
public class HistogramDTOFactory {

    /**
     * and enum determining the prefix for the label of the last bar in the graph.
     * @author rinke
     * 
     */
    public enum LastRowHeaderPrefix implements StringValuedEnum {
        /**
         * no prefix
         */
        NONE(""),
        /**
         * a ">" sign, in case of rest categories and an input list with Doubles
         */
        GREATER(">"),
        /**
         * a greater than or equal to sing, in case of rest categories and an input list with Integers
         */
        GREATER_EQUAL("\u2265");
        /**
         * a String describing the value. This String value is eventually displayed in the output
         */
        private final String value;

        private LastRowHeaderPrefix(final String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    /**
     * the maximum number of bars in the histogram.
     */
    private static final int     MAXBARS                = 39;

    /**
     * In the higher tail, no more cats/bars are shown, if for 2 consequetive bars a value lower than (LOW_BAR_LEVEL * highestBarValue) is found.
     * Example: if LOW_BAR_LEVEL = 0.01, and the highest bar value = 200, when bar N and N+1 are < 2 (1% of 200), then iteration is stopped and all
     * restvalues go in the final (extra) bar. NOTE: the number of 2 consequetive bars is not hard coded, but this is a constant too:
     * ALLOWABLE_TAIL_GAP.
     */
    private static final double  LOW_BAR_LEVEL          = 0.008;

    /**
     * In the higher tail, if more than ALLOWABLE_TAIL_GAP of bars have a too low value, then iteration is stopped and all restvalues go in the final
     * extra bar.
     */
    private static final int     ALLOWABLE_TAIL_GAP     = 3;

    /**
     * In the higher tail, the rest category should not be shown if it contains more than this fraction of all observations. So, if
     * MAX_REST_CAT_PERCENTAGES = 0.10, this means that the rest category may not contain more than 10% of all observations. In such a case, the
     * iteration should just continue.
     */
    private static final double  MAX_REST_CAT_FRACTION  = 0.09;

    /**
     * This factor is used for calculating the optimum category width. If 70, it takes the 70 percentile as calibration point to base this calculation
     * upon. Slight adjustments can give better results. Optimum value seems to be between 70 and 95. Higher value results in broader classes, but
     * then (of course) a smaller number of bars. As it is a percentile (so derived from percentage) it should by definition be between 0 and 100; if
     * not, an ArrayIndexOutOfBoundsException will result.
     */
    private static final int     CALIBRATION_PERCENTILE = 89;

    private LastRowHeaderPrefix  lastRowHeaderPrefix;

    /**
     * the resulting data value object with all the data for creating the graph. Typically, it contains the graph bar values
     */
    private StatisticalResultDTO result;

    /**
     * The input list with all the data retrieved from the database query. This is for example a list with all the personal gross products of all
     * members.
     */
    private final List<Number>   input;

    /**
     * the output list with all the category centers.
     */
    private List<Number>         xResult;

    /**
     * the output list with all results. Each number gives the amount of members found in the category. The corresponding category value is in the
     * xResult list.
     */
    private List<Number>         yResult;

    /**
     * a factor indicating how the x-axis is scaled. For example: if the x-axis has values around 5000, the axis labels will show numbers like 1.0,
     * 2.0, ...5.0, 6.0, and the scalefactor will be 1000. This factor should be used as a extension to the x-axis label (showing "x 1000").
     */
    private double               scaleFactorX;

    /**
     * the local settings, needed in order to format the numbers of the x axis categories according to the local settings.
     */
    private final LocalSettings  settings;

    /**
     * constructor, storing the input in its appropriate field, and storing the settings.
     * 
     * @param input - the List&lt;Number&gt; with the data to put in the histogram. Note that lists of Integers and Lists of Doubles are treated
     * differently, especially where it concerns the division over x-axis categories. You should NOT enter a List of numbers which represent integer
     * values as a List of Doubles. If you do so, the x-axis categories will use irrelevant broken numbers and your histograph may contain a lot of
     * silly gaps..
     * @param settings - a LocalSettings, in order to format the numbers. Null value is allowed.
     */
    public HistogramDTOFactory(final List<Number> input, final LocalSettings settings) {
        this.input = input;
        this.settings = settings;
        scaleFactorX = 1;
    }

    /**
     * assigning the x-axis labels to the result object. Uses the xResult field as input.
     */
    private void assignRowHeaders() {
        if (result == null) {
            return;
        }
        final String[] rowHeaders = new String[xResult.size()];
        if (settings == null) {
            for (int i = 0; i < xResult.size(); i++) {
                rowHeaders[i] = xResult.get(i).toString();
            }
        } else {
            for (int i = 0; i < xResult.size(); i++) {
                rowHeaders[i] = settings.getNumberConverterForPrecision(1).toString(new BigDecimal(xResult.get(i).floatValue()));
            }
        }
        rowHeaders[xResult.size() - 1] = lastRowHeaderPrefix.getValue() + rowHeaders[xResult.size() - 1];
        result.setRowHeaders(rowHeaders);
    }

    /**
     * complicated method to calculate optimum classwidth. The method tries to find a nice round value.
     * 
     * @param calibrationValue a double representing the value of the CALIBRATION_PERCENTILE index. This is the value for which about 80% of the list
     * is lesser, and about 20% is bigger.
     * @param integers true if the list contains integers.
     * @return the optimal class width for each bar in the graph.
     */
    private double calcClassWidth(final double calibrationValue, final boolean integers) {
        if (calibrationValue == 0.0) {
            scaleFactorX = 1.0;
            return 1.0;
        }
        final double divideBy = Math.floor(logBase(10, calibrationValue));
        scaleFactorX = Math.pow(10, divideBy);
        final double superRoundedValue = Math.ceil(calibrationValue / scaleFactorX);
        double width = superRoundedValue * (Math.pow(10, divideBy - 1));
        if (integers) {
            width = (width >= 0.5) ? Math.round(width) : 1.0;
        }
        // rescale scalefactor to 1000's in stead of to 10's
        // scaleFactorX = Math.pow(1000, Math.floor(logBase(10, scaleFactorX) / 3));
        return width;
    }

    /**
     * scales a number according to the scaleFactorX. This makes that x-axis labels will be shown as for example 2.0 and not 200, and the x-axis will
     * then be labeled with "(x 100)"
     */
    private double formatX(final double number) {
        return number / scaleFactorX;
    }

    /**
     * formats the scalefactor field into a String which can be used as a suffix to the x-axis label
     * 
     * @param baseKey a String which is the base key for the language resource bundle. All Strings to be built for the rendered graph will be built
     * with this key as the basis.
     * @return a string, showing for example "( x 1000)" if the x-axis categories are in thousands
     */
    private String getScaleFactorString(final String baseKey) {
        if (xResult == null) {
            produceResultArrays(baseKey);
        }
        if (scaleFactorX == 1) {
            return "";
        } else if (scaleFactorX < 1) {
            return " ( / " + (int) (1 / scaleFactorX) + ")";
        } else {
            return " ( x " + (int) scaleFactorX + ")";
        }
    }

    /**
     * simple helper function to calculate (base)log(value). Needed in order to calculate optimum barClass width
     * 
     * @param base, the base of the logarithm
     * @param value, the number you want to take the logarithm from
     * @return (base)log(value)
     */
    private double logBase(final double base, final double value) {
        return Math.log(value) / Math.log(base);
    }

    /**
     * this method turns the results of the main calculations into a descent usable StatisticalResultDTO
     * 
     * @param baseKey a String which is the base key for the language resource bundle. All Strings to be built for the rendered graph will be built
     * with this key as the basis.
     */
    private void produceDTO(final String baseKey) {
        final Number[][] data = new Number[yResult.size()][1];
        for (int i = 0; i < yResult.size(); i++) {
            data[i][0] = new StatisticalNumber(yResult.get(i).doubleValue(), (byte) 0);
        }
        result = new StatisticalResultDTO(data);
        result.setGraphType(StatisticalResultDTO.GraphType.BAR);
        assignRowHeaders();
        result.setScaleFactorX(getScaleFactorString(baseKey));
        result.setShowTable(false);
    }

    /**
     * creates a <code>StatisticalResultDTO</code> with just one bar. Called if all values in the list are equal.
     * 
     * @param baseKey the baseKey as used by the language resource bundle in order to create language labels.
     */
    private void produceJustOneBarDTO(final String baseKey) {
        xResult.add(formatX(input.get(0).doubleValue()));
        yResult.add(input.size());
        lastRowHeaderPrefix = LastRowHeaderPrefix.NONE;
        produceDTO(baseKey);
    }

    /**
     * method doing all the calculations. It calculates the optimum class width, and after that, it spreads all the results over the bars in the
     * graph, and creates a <code>StatisticalResultDTO</code> ready to use by the <code>StatisticalDataProducer</code> and action. Method is
     * highly commented because the algorithm and calculation is rather complicated.
     * 
     * @param baseKey the baseKey as used by the language resource bundle in order to create language labels.
     */
    private void produceResultArrays(final String baseKey) {
        // if too small set of data don't do anything
        if (input.size() < StatisticalService.MINIMUM_NUMBER_OF_VALUES) {
            produceTooSmallDatasetDTO(baseKey);
            return;
        }
        yResult = new ArrayList<Number>();
        xResult = new ArrayList<Number>();
        // sort input
        final List<Double> lInput = ListOperations.convertToDoubleList(input);
        Collections.sort(lInput);
        // if only one bar, just use that value as x-axis label and exit, skipping all complicated stuff below
        if (lInput.get(0).equals(lInput.get(lInput.size() - 1))) {
            produceJustOneBarDTO(baseKey);
            return;
        }
        // a list of integers is treated slightly different. Class width for integers should always be an integer value
        final boolean integers = (input.get(0).getClass() == Integer.class);
        // determine optimum classWidth via the 70-percentile
        final int calibrationPoint = (int) (Math.round(CALIBRATION_PERCENTILE * lInput.size() / 100.0) - 1);
        final double calibrationValue = lInput.get(calibrationPoint);
        final double classwidth = calcClassWidth(calibrationValue, integers);
        // initialize counters and indexes
        int maxElementIndexThisBar = 0;
        byte lows = 0;
        double highest = 100;
        int barIndex;
        for (barIndex = 0; barIndex < MAXBARS; barIndex++) {
            final ListIterator<Double> it = lInput.listIterator(maxElementIndexThisBar);
            // elementIndex is the basic counter when iterating over the inputlist
            // At the start of the iteration loop, it is set to the maximum index remaining from the previous loop
            int elementIndex = maxElementIndexThisBar;
            // maxElementIndexLastBar keeps track of how many elements were already placed in previous bars
            final int maxElementIndexLastBar = maxElementIndexThisBar;
            // the value of the element. Initialized to 0 because the compiler wants initialization. It will be reassigned by definition
            double value = 0;
            while (it.hasNext()) {
                value = it.next();
                // if the first value belonging to the next bar is reached, stop iterating
                if (value >= ((barIndex + 1) * classwidth)) {
                    // but before stopping iteration, of course set the maximum element which has been reached
                    maxElementIndexThisBar = elementIndex;
                    break;
                }
                elementIndex++;
            }
            // the loop above stops and resets maxElementIndexThisBar if the first element for the next bar is reached.
            // if there is no next bar, because of reaching the end of the input list, this would go wrong.
            // Therefore, check if the last element's value would fall in the present bar
            // see LocalSettings.BIG_DECIMAL_DIVISION_PRECISION (is 6)
            if (!it.hasNext() && (Math.abs(value - barIndex * classwidth) < 0.000001)) {
                // if so, put remaining elements in next bar by setting maxElementIndexThisBar. This takes care that the next block will write it
                maxElementIndexThisBar = elementIndex;
            }
            // if no more elements, and if all elements have been assigned to bars, don't write
            final boolean dontwrite = ((it.hasNext() == false) && (maxElementIndexThisBar - maxElementIndexLastBar == 0));
            if (!dontwrite) {
                if (integers) {
                    xResult.add(formatX(Math.floor((barIndex + 0.5) * classwidth)));
                } else {
                    xResult.add(formatX((barIndex + 0.5) * classwidth));
                }
                yResult.add(maxElementIndexThisBar - maxElementIndexLastBar);
                if ((maxElementIndexThisBar - maxElementIndexLastBar) > highest) {
                    highest = (maxElementIndexThisBar - maxElementIndexLastBar);
                }
            }
            // if no more elements, previous block took care of the last assignment to a bar. So the loop can safely stop if no more elements
            if (!it.hasNext()) {
                break;
            }
            // count the number of consequetive bars with LOW_BAR_LEVEL.
            lows = (byte) (((maxElementIndexThisBar - maxElementIndexLastBar) < (highest * LOW_BAR_LEVEL)) ? (lows + 1) : 0);
            // If too much consequetive bars with low values are found at the tail, iteration should stop.
            // Conditions for stopping:
            // 1) enough consequetive low value bars (>= ALLOWABLE_TAIL_GAP)
            // 2) should be in the tail, so beyond the calibrationPoint (so, after having had CALIBRATION_PERCENTILE of the elements (about 70% of the
            // elements).
            // 3) the remaining fraction may not be bigger than MAX_REST_CAT_FRACTION
            if (lows >= ALLOWABLE_TAIL_GAP && maxElementIndexThisBar > calibrationPoint) {
                final double remainingFraction = ((double) (input.size() - maxElementIndexThisBar)) / (double) input.size();
                if (remainingFraction < MAX_REST_CAT_FRACTION) {
                    break; // only stop if the restcategory does not contain more than MAX_REST_CAT_FRACTION
                }
            }
        }
        // and the last element (if any)
        final int remaining = input.size() - maxElementIndexThisBar;
        if (remaining > 0) { // only add last element if anything is left
            xResult.add(formatX((barIndex + 1) * classwidth));
            yResult.add(remaining);
            lastRowHeaderPrefix = integers ? LastRowHeaderPrefix.GREATER_EQUAL : LastRowHeaderPrefix.GREATER;
        } else {
            lastRowHeaderPrefix = LastRowHeaderPrefix.NONE;
        }
        // Make a DTO object from the results
        produceDTO(baseKey);
    }

    /**
     * this method takes care of too small datasets; in such a case, it does not show anything, just a message.
     * 
     * @param baseKey the baseKey as used by the language resource bundle in order to create language labels.
     */
    private void produceTooSmallDatasetDTO(final String baseKey) {
        result = StatisticalResultDTO.noDataAvailable(baseKey);
    }

    /**
     * the main public method, call this to receive a value object containing the histogram data
     * 
     * @param baseKey a String which is the base key for the language resource bundle. All Strings to be built for the rendered graph will be built
     * with this key as the basis.
     * @return a StatisticalResultDTO containing the histogram data.
     */
    StatisticalResultDTO getResultObject(final String baseKey) {
        if (result == null) {
            produceResultArrays(baseKey);
        }
        result.setBaseKey(baseKey);
        return result;
    }

}
