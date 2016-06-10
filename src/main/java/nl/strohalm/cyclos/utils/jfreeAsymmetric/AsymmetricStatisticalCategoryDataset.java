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
package nl.strohalm.cyclos.utils.jfreeAsymmetric;

import java.util.List;

import org.jfree.data.KeyedObjects2D;
import org.jfree.data.Range;
import org.jfree.data.RangeInfo;
import org.jfree.data.general.AbstractDataset;
import org.jfree.data.statistics.MeanAndStandardDeviation;
import org.jfree.data.statistics.StatisticalCategoryDataset;

/**
 * This class is a replacement of the JFreeChart DefaultStatisticalCategoryDataset as such, that it can handle asymmetrical error bars around numbers.
 * It is not a subClass, but it subclasses the parent of DefaultStatisticalCategoryDataset, and copies the code which does not need change.
 * 
 * @author Rinke
 * 
 */
public class AsymmetricStatisticalCategoryDataset extends AbstractDataset implements StatisticalCategoryDataset, RangeInfo {

    private static final long    serialVersionUID = -2787641442478041088L;

    // ******************** CHANGED OR NEW CODE (COMPARED TO DefaultStatisticalCategoryDataset)************
    // ****************************************************************************************************

    // ********************* UNCHANGED CODE (copied from DefaultStatisticalCategoryDataset) **************
    // **************************************************************************************************
    /** Storage for the data. */
    private final KeyedObjects2D data;

    /** The minimum range value. */
    private double               minimumRangeValue;

    /** The minimum range value including the standard deviation. */
    private double               minimumRangeValueIncStdDev;

    /** The maximum range value. */
    private double               maximumRangeValue;

    /** The maximum range value including the standard deviation. */
    private double               maximumRangeValueIncStdDev;

    /**
     * Creates a new dataset.
     */
    public AsymmetricStatisticalCategoryDataset() {
        data = new KeyedObjects2D();
        minimumRangeValue = Double.NaN;
        maximumRangeValue = Double.NaN;
        minimumRangeValueIncStdDev = Double.NaN;
        maximumRangeValueIncStdDev = Double.NaN;
    }

    /**
     * Adds a mean with an asymmetrical error bar around it to the table.
     * 
     * @param mean the mean.
     * @param lower the lower limit of the confidence or error bar
     * @param upper the upper limit of the confidence or error bar
     * @param rowKey the row key.
     * @param columnKey the column key.
     */
    @SuppressWarnings("rawtypes")
    public void add(final double mean, final double lower, final double upper, final Comparable rowKey, final Comparable columnKey) {
        add(new Double(mean), new Double(lower), new Double(upper), rowKey, columnKey);
    }

    /**
     * Adds a mean with an asymmetrical error bar around it to the table.
     * 
     * @param mean the mean.
     * @param lower the lower limit of the confidence or error bar
     * @param upper the upper limit of the confidence or error bar
     * @param rowKey the row key.
     * @param columnKey the column key.
     */
    @SuppressWarnings("rawtypes")
    public void add(final Number mean, final Number lower, final Number upper, final Comparable rowKey, final Comparable columnKey) {
        final MeanWithAsymmetricErrorBar item = new MeanWithAsymmetricErrorBar(mean, lower, upper);
        data.addObject(item, rowKey, columnKey);
        double m = 0.0;
        double l = 0.0;
        double u = 0.0;
        if (mean != null) {
            m = mean.doubleValue();
        }
        if (lower != null) {
            l = lower.doubleValue();
        }
        if (upper != null) {
            u = upper.doubleValue();
        }

        if (!Double.isNaN(m)) {
            if (Double.isNaN(maximumRangeValue) || m > maximumRangeValue) {
                maximumRangeValue = m;
            }
        }

        if (!Double.isNaN(u)) {
            if (Double.isNaN(maximumRangeValueIncStdDev) || u > maximumRangeValueIncStdDev) {
                maximumRangeValueIncStdDev = u;
            }
        }

        if (!Double.isNaN(m)) {
            if (Double.isNaN(minimumRangeValue) || m < minimumRangeValue) {
                minimumRangeValue = m;
            }
        }

        if (!Double.isNaN(l)) {
            if (Double.isNaN(minimumRangeValueIncStdDev) || l < minimumRangeValueIncStdDev) {
                minimumRangeValueIncStdDev = l;
            }
        }

        fireDatasetChanged();
    }

    /**
     * Tests this instance for equality with an arbitrary object.
     * 
     * @param obj the object (<code>null</code> permitted).
     * 
     * @return A boolean.
     */
    @Override
    public boolean equals(final Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof AsymmetricStatisticalCategoryDataset)) {
            return false;
        }
        final AsymmetricStatisticalCategoryDataset that = (AsymmetricStatisticalCategoryDataset) obj;
        if (!data.equals(that.data)) {
            return false;
        }
        return true;
    }

    /**
     * Returns the number of columns in the table.
     * 
     * @return The column count.
     */
    @Override
    public int getColumnCount() {
        return data.getColumnCount();
    }

    /**
     * Returns the column index for a given key.
     * 
     * @param key the column key.
     * 
     * @return The column index.
     */
    @Override
    @SuppressWarnings("rawtypes")
    public int getColumnIndex(final Comparable key) {
        return data.getColumnIndex(key);
    }

    /**
     * Returns a column key.
     * 
     * @param column the column index (zero-based).
     * 
     * @return The column key.
     */
    @Override
    @SuppressWarnings("rawtypes")
    public Comparable getColumnKey(final int column) {
        return data.getColumnKey(column);
    }

    /**
     * Returns the column keys.
     * 
     * @return The keys.
     */
    @Override
    @SuppressWarnings("rawtypes")
    public List getColumnKeys() {
        return data.getColumnKeys();
    }

    /**
     * returns the lower bound of the error bar
     * 
     * @param row
     * @param column
     * @return the lower value of the error bar
     */
    public Number getLowerValue(final int row, final int column) {
        Number result = null;
        final MeanWithAsymmetricErrorBar mwaeb = (MeanWithAsymmetricErrorBar) data.getObject(row, column);
        if (mwaeb != null) {
            result = mwaeb.getLower();
        }
        return result;
    }

    /**
     * Returns the mean value for an item.
     * 
     * @param rowKey the row key.
     * @param columnKey the columnKey.
     * 
     * @return The mean value.
     */
    @Override
    @SuppressWarnings("rawtypes")
    public Number getMeanValue(final Comparable rowKey, final Comparable columnKey) {
        Number result = null;
        final MeanAndStandardDeviation masd = (MeanAndStandardDeviation) data.getObject(rowKey, columnKey);
        if (masd != null) {
            result = masd.getMean();
        }
        return result;
    }

    /**
     * Returns the mean value for an item.
     * 
     * @param row the row index (zero-based).
     * @param column the column index (zero-based).
     * 
     * @return The mean value.
     */
    @Override
    public Number getMeanValue(final int row, final int column) {
        Number result = null;
        final MeanAndStandardDeviation masd = (MeanAndStandardDeviation) data.getObject(row, column);
        if (masd != null) {
            result = masd.getMean();
        }
        return result;
    }

    /**
     * Returns the range of the values in this dataset's range.
     * 
     * @param includeInterval a flag that determines whether or not the y-interval is taken into account.
     * 
     * @return The range.
     */
    @Override
    public Range getRangeBounds(final boolean includeInterval) {
        Range result = null;
        if (includeInterval) {
            if (!Double.isNaN(minimumRangeValueIncStdDev) && !Double.isNaN(maximumRangeValueIncStdDev)) {
                result = new Range(minimumRangeValueIncStdDev, maximumRangeValueIncStdDev);
            }
        } else {
            if (!Double.isNaN(minimumRangeValue) && !Double.isNaN(maximumRangeValue)) {
                result = new Range(minimumRangeValue, maximumRangeValue);
            }
        }
        return result;
    }

    /**
     * Returns the minimum y-value in the dataset.
     * 
     * @param includeInterval a flag that determines whether or not the y-interval is taken into account (ignored for this dataset).
     * 
     * @return The minimum value.
     */
    @Override
    public double getRangeLowerBound(final boolean includeInterval) {
        return minimumRangeValue;
    }

    /**
     * Returns the maximum y-value in the dataset.
     * 
     * @param includeInterval a flag that determines whether or not the y-interval is taken into account (ignored for this dataset).
     * 
     * @return The maximum value.
     */
    @Override
    public double getRangeUpperBound(final boolean includeInterval) {
        return maximumRangeValue;
    }

    /**
     * Returns the number of rows in the table.
     * 
     * @return The row count.
     */
    @Override
    public int getRowCount() {
        return data.getRowCount();
    }

    /**
     * Returns the row index for a given key.
     * 
     * @param key the row key.
     * 
     * @return The row index.
     */
    @Override
    @SuppressWarnings("rawtypes")
    public int getRowIndex(final Comparable key) {
        return data.getRowIndex(key);
    }

    /**
     * Returns a row key.
     * 
     * @param row the row index (zero-based).
     * 
     * @return The row key.
     */
    @Override
    @SuppressWarnings("rawtypes")
    public Comparable getRowKey(final int row) {
        return data.getRowKey(row);
    }

    /**
     * Returns the row keys.
     * 
     * @return The keys.
     */
    @Override
    @SuppressWarnings("rawtypes")
    public List getRowKeys() {
        return data.getRowKeys();
    }

    @Override
    @SuppressWarnings("rawtypes")
    public Number getStdDevValue(final Comparable rowKey, final Comparable columnKey) {
        throw new UnsupportedOperationException("getStdDevValue method is not supported. " + "Use the getUpper and getLower methods in stead.");
    }

    @Override
    public Number getStdDevValue(final int row, final int column) {
        throw new UnsupportedOperationException("getStdDevValue method is not supported. " + "Use the getUpper and getLower methods in stead.");
    }

    /**
     * returns the upper bound of the error bar
     * 
     * @param row
     * @param column
     * @return the upper value of the error bar
     */
    public Number getUpperValue(final int row, final int column) {
        Number result = null;
        final MeanWithAsymmetricErrorBar mwaeb = (MeanWithAsymmetricErrorBar) data.getObject(row, column);
        if (mwaeb != null) {
            result = mwaeb.getUpper();
        }
        return result;
    }

    // **************** Not copied from DefaultStatisticalCategoryDataset ******************

    /**
     * Returns the value for an item (for this dataset, the mean value is returned).
     * 
     * @param rowKey the row key.
     * @param columnKey the columnKey.
     * 
     * @return The value.
     */
    @SuppressWarnings("rawtypes")
    @Override
    public Number getValue(final Comparable rowKey, final Comparable columnKey) {
        return getMeanValue(rowKey, columnKey);
    }

    /**
     * Returns the value for an item (for this dataset, the mean value is returned).
     * 
     * @param row the row index.
     * @param column the column index.
     * 
     * @return The value.
     */
    @Override
    public Number getValue(final int row, final int column) {
        return getMeanValue(row, column);
    }

}
