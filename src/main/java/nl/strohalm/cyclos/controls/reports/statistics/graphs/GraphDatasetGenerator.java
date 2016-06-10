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
package nl.strohalm.cyclos.controls.reports.statistics.graphs;

import nl.strohalm.cyclos.entities.reports.StatisticalNumber;
import nl.strohalm.cyclos.utils.jfreeAsymmetric.AsymmetricStatisticalCategoryDataset;

import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.CategoryToPieDataset;
import org.jfree.data.category.DefaultIntervalCategoryDataset;
import org.jfree.data.general.Dataset;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.util.TableOrder;

/**
 * generates the dataset which is needed for the cewolf graphs, and takes care of needed manipulations of this dataset.<br>
 * Is only called from StatisticalDataProducer; therefor the class has default and not public access. All of its methods are static.
 * 
 * @author Rinke
 * 
 */
class GraphDatasetGenerator {

    /**
     * method calculates the scalefactor from the input dataset. Scaling means for example: if the numbers are in the range of millions (so: from 0 to
     * 10 million), numbers 1 to 10 are shown on the y-axis, and the y-axis label indicates " (x 1.000.000)". Scaling is done per thousands.
     * 
     * @param dataset
     * @return the scaleFactor. A value of 1 correspondents with "x1000", a value of 2 with "x1.000.000".
     */
    static byte calculateScaleFactor(final Object dataset) {
        final CategoryDataset lDataset = (CategoryDataset) dataset;
        final Number maxValue = DatasetUtilities.findMaximumRangeValue(lDataset);
        return (byte) getThousands(maxValue.doubleValue());
    }

    /**
     * Limits the series names for the graph to the indicated number of columns.<br>
     * A table may have more columns than the graph has series (often, the growth column indicating the difference between the first two columns is
     * shown in the table, but not in the graph).<br>
     * This methods copies the columnHeaders to a String[] seriesNames, but strips the last columns. The original input param is not effected.
     * 
     * @param tableSeries a <code>String[]</code> representing the original seriesNames as shown in the table. This may be the table column headers
     * or the table row headers, depending if the data is "switched".
     * @param numberOfColumnsToDrop if 1, it drops the last column; if 2, it drops the two last columns... etc...
     * @return the seriesNames
     */
    protected static String[] createGraphSeries(final String[] tableSeries, final int seriesCount) {
        final String[] seriesNames = new String[seriesCount];
        System.arraycopy(tableSeries, 0, seriesNames, 0, seriesCount);
        return seriesNames;
    }

    /**
     * generates the CategoryDataset for the graph from the raw input params
     * 
     * @param tableData a <code>Number[][]</code> containing <code>StatisticalNumber</code>s, and representing the raw data
     * @param seriesNames a <code>String[]</code> with the series names
     * @param categories a <code>String[]</code> with the categories names
     * @param hasErrorBars <code>true</code> if the graph should show error bars.
     * @return a <code>CategoryDataset</code> which can be directly returned by <code>StatisticalDataProducer.getDataProducer</code>.
     */
    static CategoryDataset generateDataset(final Number[][] tableData, final String[] seriesNames, final String[] categories, final boolean hasErrorBars) {
        final int series = seriesNames.length;
        final int cats = categories.length;
        if (hasErrorBars) {
            final AsymmetricStatisticalCategoryDataset ds = new AsymmetricStatisticalCategoryDataset();
            for (int s = 0; s < series; s++) {
                for (int c = 0; c < cats; c++) {
                    final StatisticalNumber data = (StatisticalNumber) tableData[s][c];
                    ds.add((data.isNull() ? null : data), data.getLowerBound(), data.getUpperBound(), seriesNames[s], categories[c]);
                }
            }
            return ds;
        }
        final Number[][] endValues = new Number[series][cats];
        final Number[][] startValues = new Number[series][cats];
        for (int s = 0; s < series; s++) {
            for (int c = 0; c < cats; c++) {
                final Number value = tableData[s][c];
                if (!((value.getClass() == StatisticalNumber.class) && ((StatisticalNumber) value).isNull())) {
                    endValues[s][c] = value;
                    startValues[s][c] = new Integer(0);
                }
            }
        }
        final CategoryDataset ds = new DefaultIntervalCategoryDataset(seriesNames, categories, startValues, endValues);
        return ds;
    }

    /**
     * generates a pie dataset by extracting it from a categoryDataset. It always takes the first column of this categoryDataset, so any surpluss
     * columns are just ignored.
     * @param keys a <code>String</code> array showing the legend Strings.
     * @param tableData the two dimensional array which is the basis of the categoryDataset.
     * @return a <code>PieDataset</code>.
     */
    static Dataset generatePieDataset(final String[] keys, final Number[][] tableData) {
        final CategoryDataset catDataset = GraphDatasetGenerator.generateDataset(tableData, keys, new String[] { "" }, false);
        final CategoryToPieDataset dataset = new CategoryToPieDataset(catDataset, TableOrder.BY_COLUMN, 0);
        return dataset;
    }

    /**
     * gives the scaleFactor in thousands, for example for a graph axis. The axis can be scaled to reasonable numbers, where all numbers should be
     * multiplied by 1000 to the power of the result. Example: if the number entered is 345, then the method returns 0. All numbers for the axis may
     * be divided by 1000 to the power of 0. Example 2: if the number entered is 345000000, then the method returns 2. All numbers for the axis may
     * divided by 1000 to the power of 2 (=1.000.000). Usually, the y-axis labels is appended with a string like "(x 1.000.000).
     * 
     * @param number, usually the maximum value of a range
     * @return a scaling factor, which is 1000 to the power of the result
     */
    private static int getThousands(final double number) {
        return (int) Math.floor(Math.log(number) / Math.log(1000));
    }

    /**
     * scales the data for better presentation in the y-axis of the graph. For example: if the numbers are in the range of millions (so: from 0 to 10
     * million), numbers 1 to 10 are shown on the y-axis, and the y-axis label indicates " (x 1.000.000)".
     * 
     * @param dataset a <code>CategoryDataset</code> to be scaled.
     * @param scaleFactor a <code>byte</code> indicating the factor by which it should be scaled. The real factor is Math.pow(1000, scaleFactor), so
     * a scaleFactor of 1 means "x 1000".
     * @param hasErrorBars a <code>boolean</code> if the graph should show error bars.
     * @return a <code>CategoryDataset</code> which is scaled.
     */
    static CategoryDataset scaleData(final CategoryDataset dataset, final byte scaleFactor, final boolean hasErrorBars) {
        if (scaleFactor == 0) {
            return dataset;
        }
        final double factor = Math.pow(1000, scaleFactor);
        if (hasErrorBars) {
            final AsymmetricStatisticalCategoryDataset scaledData = new AsymmetricStatisticalCategoryDataset();
            for (int r = 0; r < dataset.getRowCount(); r++) {
                for (int c = 0; c < dataset.getColumnCount(); c++) {
                    final StatisticalNumber dataPoint = (StatisticalNumber) StatisticalNumber.scale(dataset.getValue(r, c), factor);
                    final Comparable<?> columnKey = dataset.getColumnKey(c);
                    final Comparable<?> rowKey = dataset.getRowKey(r);
                    scaledData.add(dataPoint, (dataPoint == null) ? null : dataPoint.getLowerBound(), (dataPoint == null) ? null : dataPoint.getUpperBound(), rowKey, columnKey);
                }
            }
            return scaledData;
        }
        final DefaultIntervalCategoryDataset scaledData = (DefaultIntervalCategoryDataset) dataset;
        for (int r = 0; r < dataset.getRowCount(); r++) {
            for (int c = 0; c < dataset.getColumnCount(); c++) {
                final Number scaledValue = StatisticalNumber.scale(dataset.getValue(r, c), factor);
                scaledData.setEndValue(r, dataset.getColumnKey(c), scaledValue);
            }
        }
        return scaledData;
    }

    /**
     * switches the x and y of a two dimensional array of numbers.
     * 
     * @param input the input array
     * @return a copy of the input array, but with rows and columns switched.
     */
    static Number[][] switchXYData(final Number[][] input) {
        final Number[][] switchedData = new Number[input[0].length][input.length];
        for (int x = 0; x < input.length; x++) {
            for (int y = 0; y < input[x].length; y++) {
                switchedData[y][x] = input[x][y];
            }
        }
        return switchedData;
    }

    private GraphDatasetGenerator() {
        // do nothing private default constructor so it cannot be instantiated
    }

}
