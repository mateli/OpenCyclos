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

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import nl.strohalm.cyclos.entities.reports.StatisticalNumber;
import nl.strohalm.cyclos.services.stats.StatisticalService;
import JSci.maths.statistics.NormalDistribution;

/**
 * This class calculates stuff about the Median of a population. The median is used in case a population is normally distributed, and it is likely
 * that the distribution is skewed or has extreme outliers. In such cases, the median is a more robust and better estimator for the center of the
 * population than the average is.
 * 
 * The class calculates the median from a give list of Numbers or array of doubles. It is also able to produce a confidence interval. It may be used
 * in a static way if you only want to retrieve the median. If you need the confidence interval too, then it must be used non-static.
 * 
 * @author Rinke
 * 
 */
public class Median {

    /*
     * if the ratio between the upper half and lower half of the confidence interval is less than this, we will consider the confidence interval as
     * symmetric.
     */
    private static final double CONFIDENCE_SYMMETRY_LIMIT = 0.05;

    /**
     * Gets the median of a range of numbers. Takes a double[] as input
     * @param data an array of doubles for which the median must be calculated
     * @return a double being the median of the range
     */
    public static double getMedian(final double[] data) {
        return Median.getMedian(ListOperations.arrayToList(data));
    }

    /**
     * Gets the median of a range of numbers.
     * @param data an array of doubles for which the median must be calculated
     * @param alpha an int indicating the level of the confidence interval. A level of 0.05 gives a 95% confidence interval
     * @return a StatisticalNumber indicating the median, complete with confidence interval.
     */
    public static StatisticalNumber getMedian(final double[] data, final double alpha) {
        return Median.getMedian(ListOperations.arrayToList(data), alpha);
    }

    /**
     * static method for retrieving the median
     * @param l the list of Numbers to retrieve the median from
     * @return the median as a simple double.
     */
    public static double getMedian(final List<Number> l) {
        if (l.size() == 1) {
            return l.get(0).doubleValue();
        }
        final Median median = new Median(l);
        return median.getMedian();
    }

    /**
     * static method returning the median as a statisticalNumber, including the confidence interval.
     * @param l the data
     * @param alpha the level. A level of 0.05 brings a 95% confidence interval
     * @return a statistical Number representing the number PLUS a confidence interval around it. In case of too little elements, a StatisticalNumber
     * is returned with the <code>isNull</code> field set to true.
     */
    public static StatisticalNumber getMedian(final List<Number> l, final double alpha) {
        if (l.size() < StatisticalService.MINIMUM_NUMBER_OF_VALUES || l.size() == 0) {
            return new StatisticalNumber();
        }
        if (l.size() == 1) {
            return new StatisticalNumber(l.get(0).doubleValue(), (byte) 2);
        }
        final Median median = new Median(l, alpha);
        if (median.getHalfOfConfidenceInterval() == null) {
            return new StatisticalNumber(median.getMedian(), median.getLowerLimitConfidenceInterval(), median.getUpperLimitConfidenceInterval(), (byte) 2);
        }
        return new StatisticalNumber(median.getMedian(), median.getHalfOfConfidenceInterval(), (byte) 2);
    }

    private final List<Number> list;

    /**
     * the test level, usually 5% (0.05). This would generate a 95% confidence interval.
     */
    private double             alpha;

    /**
     * the result value
     */
    private Double             median;

    /**
     * the upper limit of the confidence interval
     */
    private Double             upper = null;

    /**
     * the lower limit of the confidence interval
     */
    private Double             lower = null;

    public Median(final double[] d) {
        this(ListOperations.arrayToList(d));
    }

    /**
     * as previous, but with an array in stead of a list.
     * @param d
     * @param alpha
     */
    public Median(final double[] d, final double alpha) {
        this(ListOperations.arrayToList(d));
        this.alpha = alpha;
    }

    /**
     * Constructor taking a list as input. Sorts the list immediately
     * @param list
     */
    public Median(final List<Number> list) {
        Collections.sort(list, new Comparator<Number>() {
            public int compare(final Number a, final Number b) {
                if (a.equals(b)) {
                    return 0;
                }
                ;
                return (a.doubleValue() < b.doubleValue()) ? -1 : 1;
            }
        });
        this.list = list;
    }

    /**
     * This constructor is to be used in case a confidence interval is requested.
     * @param list - the data
     * @param alpha - the level for testing. Usually 5% (0.05), thus generating a 95% confidence interval.
     */
    public Median(final List<Number> list, final double alpha) {
        this(list);
        this.alpha = alpha;
    }

    /**
     * This method gives the half value of the confidence interval width, so that it can be used in this form: 12 +/- 1.2, where the 1.2 would be the
     * half of the confidence interval width. If the confidence interval is NOT symmetric (which can happen with medians), then null is returned. The
     * method tests for this symmetry: if lower and upper half range differ more than 5% it is considered a-symmetrical.
     * 
     * @return a Double indicating HALF of the confidence interval width. If the confidence interval is NOT symmetric, null is returned.
     */
    public Double getHalfOfConfidenceInterval() {
        if (list.size() < StatisticalService.MINIMUM_NUMBER_OF_VALUES) {
            return null;
        }
        if (median == null) {
            this.getMedian();
        }
        if (lower == null) {
            calculateConfidenceInterval();
        }
        final double lowerHalfWidth = median - lower;
        final double upperHalfWidth = upper - median;
        final double halfWidth = (lowerHalfWidth + upperHalfWidth) / 2.0;
        final double asymmetricality = Math.abs((lowerHalfWidth - upperHalfWidth) / halfWidth);
        if (asymmetricality < Median.CONFIDENCE_SYMMETRY_LIMIT) {
            return new Double(halfWidth);
        }
        return null;
    }

    /**
     * calculates the lower limit of the confidence interval. The level alpha must be set via the constructor.
     * 
     * @return the lower limit of the confidence interval.
     */
    public double getLowerLimitConfidenceInterval() {
        if (list.size() < StatisticalService.MINIMUM_NUMBER_OF_VALUES) {
            return this.getMedian();
        }
        if (lower == null) {
            calculateConfidenceInterval();
        }
        return lower.doubleValue();
    }

    /**
     * This method returns the median.
     * @return the median.
     * 
     */
    public double getMedian() {
        final double center = (list.size() - 1.0) / 2.0;
        median = ListOperations.getElementFromIndex(list, center);
        return median;
    }

    /**
     * calculates the upper limit of the confidence interval. The level alpha must be set via the constructor.
     * 
     * @return the upper limit of the confidence interval.
     */
    public double getUpperLimitConfidenceInterval() {
        if (list.size() < StatisticalService.MINIMUM_NUMBER_OF_VALUES) {
            return this.getMedian();
        }
        if (upper == null) {
            calculateConfidenceInterval();
        }
        return upper.doubleValue();
    }

    /*
     * does the actual calculation of the confidence interval.
     */
    private void calculateConfidenceInterval() {
        if (alpha <= 0) {
            throw new NullPointerException("Trying to calculate a confidence interval without setting alpha. " + "Use the other constructor of Median.");
        }
        final int n = list.size();
        final double zAlpha = new NormalDistribution().inverse((1.0 - alpha / 2.0));
        final double halfWidth = zAlpha * Math.sqrt(n);
        double lowerIndex = ((n + 1) / 2.0) - halfWidth - 1; // the final -1 is not according to the official formula, but because computer indexes
        // start at 0 in stead of starting at 1
        double upperIndex = ((n + 1) / 2.0) + halfWidth - 1;
        if (lowerIndex < 0) {
            lowerIndex = 0;
        }
        if (upperIndex >= list.size()) {
            upperIndex = list.size() - 1;
        }
        lower = new Double(ListOperations.getElementFromIndex(list, lowerIndex));
        upper = new Double(ListOperations.getElementFromIndex(list, upperIndex));
    }

}
