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
package nl.strohalm.cyclos.utils.statistics;

import java.util.ArrayList;

/**
 * Some helper methods for calculating and rendering graphs.
 * @author Rinke
 * 
 */
public class GraphHelper {

    /**
     * maximum number of points on the x-axis.
     */
    private static final int    MAX_X_RANGE_VALUE = 24;

    /**
     * minimal number of points on the x-axis.
     */
    private static final int    MIN_X_RANGE_VALUE = 8;

    /**
     * reasonable category widths for the getAxisCategory method. (we don't want x-axis labels like 0 - 2.17 - 4.34 - etc etc)
     */
    private static final double FACTORS[]         = { 1, 2, 5 };

    /**
     * calculates the optimal division of a a range of number into a reasonable amount of axis categories.<br>
     * Example: if the x-axis of a graph should run from 0 to 1000, how to divide this into a reasonable number of ticks over the x-axis? Clearly not
     * all 1000 points can be drawn. The method takes the start and end value of the x-axis domain, and calculates what the x-axis ticks should show
     * to get not too many, and not too few points. Makes use of the FACTORS constant.<br>
     * Tested up to a range of a million wide, but should theoretically work up to infinity (so for any range).
     * @param start a Number indicating the start of the x-axis range.
     * @param end a Number indicating the end of the x-axis range. Swapping with start (so start is the highest and end is the lowest value) is no
     * problem.
     * @return a range of number to be put on the x-axis of the graph, in one array of numbers.
     */
    public static Number[] getAxisCategories(final Number start, final Number end) {
        final double highest = Math.max(start.doubleValue(), end.doubleValue());
        final double lowest = Math.min(start.doubleValue(), end.doubleValue());
        final double difference = highest - lowest;
        double newDifference = difference;
        double factor = 1;
        if (difference > MAX_X_RANGE_VALUE) {
            // first determine the log scale, as steps of 10 are the most nice.
            final double logBase = 10.0;
            final double logScale = Math.floor(Math.log(difference) / Math.log(logBase));
            factor = Math.pow(logBase, logScale);
            // now start repeating or multiplying until in range.
            newDifference = difference / factor;
            if (newDifference < MIN_X_RANGE_VALUE) {
                factor = factor / logBase;
                newDifference = difference / factor;
            }
            boolean success = false;
            int tenFactor = 0;
            do {
                // multiply the category width (= factor) by the next item in factors until we get a reasonable amount of categories
                for (final double element : FACTORS) {
                    final double newfactor = factor * element * Math.pow(10.0, tenFactor);
                    newDifference = difference / newfactor;
                    if (newDifference <= MAX_X_RANGE_VALUE) {
                        factor = newfactor;
                        success = true;
                        break;
                    }
                }
                // if still no success, multiply the factors array by 10 (or 100, 1000, 10000, etc) until we are successful
                tenFactor++;
            } while (!success);
        }
        final int categoryNumber = (int) Math.round(newDifference);
        final Number[] resultArray = new Number[categoryNumber + 1];
        final int direction = (start.doubleValue() < end.doubleValue()) ? 1 : -1;
        for (int i = 0; i < categoryNumber + 1; i++) {
            resultArray[i] = start.doubleValue() + (i * direction * factor);
        }
        return resultArray;
    }

    /**
     * Creates a nice range around a "midpoint", based on the optimum number of points along the x-axis. The created range will have a width 80% of
     * the maximum width as defined in
     * 
     * <pre>
     * MAX_X_RANGE_VALUE
     * </pre>
     * 
     * .
     * @param midPoint a Number, being the point around which the range is built. Note that this method only makes much sense when midPoint is an
     * integer.
     * @param percentile a double; the midPoint is on the
     * 
     * <pre>
     * percentile
     * </pre>
     * 
     * percentile of the range. Example: if percentile is 33, the "midPoint" is on 1/3 of the range, having 1/3 of the points smaller, and 2/3 of the
     * points larger than midPoint.
     * @param precision an int indicating the scale factor. A MidPoint of 0 and a percentile of 50 creates -9, -8 ... 0.. 8, 9 when scaling is 0; it
     * creates -90, -80... 80, 90 when scaling is 1. Scaling may be negative too.
     * @param rangeWidth a double indicating the width of the range, as a fraction of the maximum range width MAX_X_RANGE_VALUE. A value of 0.8 means
     * that the rangeWidth is 80% of the max range width. From this, it follows that values must be between 0 and 1, or else an
     * IllegalArgumenException is thrown.
     * @param lowerLimit a Double indicating the lowest value a point in the range can get. Any points in the range lower than this limit are just
     * skipped / left out. This parameter may be null, which means that it is just ignored.
     * @throws IllegalArgumentException if rangeWidth < 0 or > 1; and if percentile < 0 or > 100
     */
    public static Number[] getOptimalRangeAround(final Number midPoint, final double percentile, final int precision, final double rangeWidth, final Double lowerLimit) {
        if (percentile < 0 || percentile > 100) {
            throw new IllegalArgumentException("Percentile must be value between 0 and 100.");
        }
        double lowerPart = percentile * MAX_X_RANGE_VALUE * Math.pow(10.0, precision) / 100.0;
        double upperPart = (100.0 - percentile) * MAX_X_RANGE_VALUE * Math.pow(10.0, precision) / 100.0;
        // apply rangeWidth
        if (rangeWidth < 0 || rangeWidth > 1) {
            throw new IllegalArgumentException("Rangewidth must be value between 0 and 1.");
        }
        lowerPart = rangeWidth * lowerPart;
        upperPart = rangeWidth * upperPart;
        if (midPoint instanceof Integer) {
            final Number start = Math.round((midPoint.doubleValue() - lowerPart) / Math.pow(10.0, precision)) * Math.pow(10.0, precision);
            final Number end = Math.round((midPoint.doubleValue() + upperPart) / Math.pow(10.0, precision)) * Math.pow(10.0, precision);
            final double step = Math.pow(10.0, precision);
            final ArrayList<Number> result = new ArrayList<Number>();
            double next = start.doubleValue();
            while (next < end.doubleValue()) {
                if (lowerLimit == null || next >= lowerLimit) {
                    result.add(next);
                }
                next += step;
            }
            final Number[] resultArray = new Number[result.size()];
            return result.toArray(resultArray);
        }
        return getAxisCategories(midPoint.doubleValue() - lowerPart, midPoint.doubleValue() + upperPart);
    }

}
