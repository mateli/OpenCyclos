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

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.entities.reports.StatisticalNumber;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.services.stats.StatisticalResultDTO;
import nl.strohalm.cyclos.services.stats.general.FilterUsed;
import nl.strohalm.cyclos.utils.conversion.KeyToHelpNameConverter;

import org.jfree.chart.plot.Marker;
import org.jfree.data.Values2D;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.Dataset;
import org.jfree.data.general.PieDataset;

import de.laures.cewolf.DatasetProduceException;
import de.laures.cewolf.DatasetProducer;
import de.laures.cewolf.tooltips.CategoryToolTipGenerator;
import de.laures.cewolf.tooltips.PieToolTipGenerator;

/**
 * A wrapper class around the data value object called StatisticalResultDTO, adding functionality for rendering graphs with CeWolf. If showTable and
 * showGraph are both false, a small table is shown indicating too little data is available.
 * <p>
 * The help file is abstracted from the baseKey. If "bla.blah.blaaah" is the baseKey, then the appropriate helpfile will be blaBlahBlaaah.jsp. This is
 * the helpfile for the table, or for the graph in case no table is shown. The help for the graph in case a table is shown too, is not determined by
 * this class; it is a general file which is set in the results jsp.
 * 
 * @author rinke
 */
public class StatisticalDataProducer implements Serializable, DatasetProducer, CategoryToolTipGenerator, PieToolTipGenerator {

    private static final long          serialVersionUID = 3323675832104771077L;

    /**
     * the dataset for the graph. If this is null, the graph hasn't been produced yet
     */
    protected Dataset                  dataset;
    /**
     * an unique identifier for Cewolf.
     */
    private final String               producerId;
    /**
     * the valueObject with the data
     */
    private final StatisticalResultDTO resultObject;
    /**
     * the localSettings, in order to retrieve number formatting for tooltips
     */
    private LocalSettings              settings;
    /**
     * the actionContext is needed to get language resource bundles
     */
    private ActionContext              context;
    /**
     * rowHeaders are the final Strings of the headers of the table rows. This class changes any header resource bundle keys into the final Strings
     * needed.
     */
    private String[]                   rowHeaders;
    /**
     * the graph categories along the x-axis. This corresponds (usually, but not always) to the tables row headers, only that this is a shorter
     * version (as there is usually less space)
     */
    private final String[]             categories;
    /**
     * the table column headers.
     */
    private final String[]             columnHeaders;
    /**
     * the title above the Graph
     */
    private String                     title;
    /**
     * the string along the x-axis of the graph
     */
    private String                     x_axis;
    /**
     * the string along the y-axis of the graph
     */
    private String                     y_axis;
    /**
     * the total value of all sections in a pie chart;
     */
    private Double                     totalForPie;

    /**
     * a factor (in Math.pow(1000,scaleFactor)) for scaling the y-axis
     */
    private byte                       scaleFactor;

    // ######################## CONSTRUCTOR ###############################################33
    // ########################################################################################

    /**
     * This constructor wraps a dataProducer around the raw DTO object
     * @param valueObject the raw DTO data object
     * @param context needed to get the language resource bundle, in order to set table headers and graph strings
     */
    public StatisticalDataProducer(final StatisticalResultDTO valueObject, final ActionContext context) {
        resultObject = valueObject;
        producerId = "graphProducer";
        final int rows = valueObject.getTableCells().length;
        categories = new String[resultObject.getCategoriesCount()];
        rowHeaders = new String[rows];
        final int columns = (rows > 0) ? valueObject.getTableCells()[0].length : 0;
        columnHeaders = new String[columns];
        setResourceStrings(context);
        /*
         * The calling of setDataset is complicated, especially for child classes. It MUST be called in the constructor, and cannot be called at first
         * prior to returning the dataset in produceDataset, because in the jsp cewolf first creates the axis labels before calling produceDataset. As
         * setDataset also performs the data scaling, the axis labels would not be scaled because they would then be created before scaling occurs. As
         * the constructor for a child class MUST call the super constructor in its first line, setDataset would be run before the droppedColumns can
         * be set. This would create wrong scaling also based on columns to be dropped. Therefore, in this constructor, setDataset may only be called
         * for StatisticalDataproducer itself, not for its child classes. The child classes MUST call setDataset AFTER setting the number of columns
         * to drop, but still INSIDE their constructor.
         */
        if (this.getClass() == StatisticalDataProducer.class) {
            setDataset();
        }
    }

    /**
     * This private constructor is used when creating a multiple chart. See the {@link #getMultiGraphProducers} method.
     * 
     * @param original the original statisticalDataProducer from which this constructor is called.
     * @param index the index of this producer in the array of producers
     * @param numberOfPoints the number of points (categories) to show in the graph.
     */
    private StatisticalDataProducer(final StatisticalDataProducer original, final int index, final int numberOfPoints) {
        final boolean byColumn = original.resultObject.getMultiGraph() == StatisticalResultDTO.MultiGraph.BY_COLUMN;
        final Number[][] data = new Number[numberOfPoints][1];
        for (int i = 0; i < numberOfPoints; i++) {
            data[i][0] = byColumn ? original.getTableCells()[i][index] : original.getTableCells()[index][i];
        }
        resultObject = new StatisticalResultDTO(data);
        resultObject.setGraphType(original.resultObject.getGraphType());
        // any column subHeaders are passed as units for the y-axis, but the parenthesis must be removed.
        String units = original.getColumnSubHeaders()[index];
        if (units.indexOf("(") == 0 && units.lastIndexOf(")") == units.length() - 1) {
            units = units.substring(1, units.length() - 1);
        }
        resultObject.setYAxisUnits(units);
        producerId = "graphProducer";
        categories = new String[numberOfPoints];
        System.arraycopy(byColumn ? original.categories : original.getColumnHeaders(), 0, categories, 0, numberOfPoints);
        columnHeaders = new String[1];
        columnHeaders[0] = byColumn ? original.columnHeaders[index] : original.categories[index];
        final int numberOfGraphs = byColumn ? original.columnHeaders.length : original.categories.length;
        x_axis = (index == numberOfGraphs - 1) ? original.getX_axis() : "";
        y_axis = byColumn ? original.columnHeaders[index] : original.categories[index];
        settings = original.settings;
        setDataset();
    }

    /**
     * Cewolf needs this method for generation of tooltips when you hoover the mouse over a graph bar or point If the settings are available, use
     * them, if not, then unformatted numbers will be shown As tooltips are not essential, and as generation of them should never allow the rendering
     * of graphs to fail due to exceptions, all is inside a try catch block.
     */
    @Override
    public String generateToolTip(final CategoryDataset lDataset, final int series, final int lCategories) {
        try {
            final Number number = lDataset.getValue(series, lCategories);
            try {
                final byte precision = (number instanceof StatisticalNumber) ? ((StatisticalNumber) number).getPrecision() : 0;
                if (settings != null) {
                    final BigDecimal value = (new BigDecimal(1000).pow(scaleFactor)).multiply(new BigDecimal(number.floatValue()));
                    final String result = settings.getNumberConverterForPrecision(precision).toString(value);
                    return result;
                }
            } catch (final Exception e) {
                // if anything goes wrong, do nothing but continue with String.valueOf
            }
            return String.valueOf(number.doubleValue());
        } catch (final Exception e) {
            // if all failed, just return no tooltips
            return "";
        }
    }

    /**
     * Cewolf needs this method for generation of tooltips when you hoover the mouse over a Pie graph section. If the settings are available, use
     * them, if not, then unformatted numbers will be shown. As tooltips are not essential, and as generation of them should never allow the rendering
     * of graphs to fail due to exceptions, all is inside a try catch block.
     * 
     * @param dataset a <code>PieDataset</code>
     * @param key for identifying the section.
     * @param pieIndex in case of multiple Pie charts. Not used in cyclos.
     */
    @Override
    @SuppressWarnings("rawtypes")
    public String generateToolTip(final PieDataset dataset, final Comparable key, final int pieIndex) {
        try {
            final Number number = dataset.getValue(key);
            try {
                final byte precision = (number instanceof StatisticalNumber) ? ((StatisticalNumber) number).getPrecision() : 0;
                if (settings != null) {
                    final BigDecimal value = (new BigDecimal(1000).pow(scaleFactor)).multiply(new BigDecimal(number.floatValue()));
                    final int percentage = (int) Math.round(value.divide(new BigDecimal(getTotalForPie())).doubleValue() * 100);
                    final String result = settings.getNumberConverterForPrecision(precision).toString(value) + " (=" + percentage + "%)";
                    return result;
                }
            } catch (final Exception e) {
                // if anything goes wrong, do nothing but continue with String.valueOf
            }
            final String result = String.valueOf(number.doubleValue());
            final int percentage = (int) Math.round(100 * (number.doubleValue() / getTotalForPie()));
            return result + " (=" + percentage + "%)";
        } catch (final Exception e) {
            // if all failed, just return no tooltips
            return "";
        }
    }

    public String getBaseKey() {
        return resultObject.getBaseKey();
    }

    public String[] getColumnHeaders() {
        return columnHeaders;
    }

    public String[] getColumnSubHeaders() {
        return resultObject.getColumnSubHeaders();
    }

    /**
     * gets the domain markers for the graph from the encapsulated data object
     */
    public Marker[] getDomainMarkers() {
        return resultObject.getDomainMarkers();
    }

    /**
     * gets the number of filter specs to be displayed with this data.
     * 
     * @return the number of filters mentioned in the "filters used" box below the graph or table
     */
    public int getFilterCount() {
        return resultObject.getFiltersUsed().size();
    }

    /**
     * gets the list with used Filters, to be shown below graph or table (if anything is in it). It manipulates the list also in the following ways:
     * <ul>
     * <li>Changes keys to Strings by using the language resource bundle
     * <li>fills up the list of values with "" so all columns have equal length.
     * </ul>
     * 
     * @return a <code>List</code> with <code>FilterUsed</code> objects, one for each relevant filter.
     */
    public List<FilterUsed> getFiltersUsed() {
        final List<FilterUsed> filterList = resultObject.getFiltersUsed();
        // determine max size
        int maxSize = 0;
        for (final FilterUsed filterUsed : filterList) {
            final int size = filterUsed.getValues().size();
            if (size > maxSize) {
                maxSize = size;
            }
        }
        for (final FilterUsed filterUsed : filterList) {
            // change keys with Strings
            if (filterUsed.getFilterUse() == FilterUsed.FilterUse.NO_SELECT || filterUsed.getFilterUse() == FilterUsed.FilterUse.NOT_USED) {
                for (final String key : filterUsed.getValues()) {
                    filterUsed.changeKeyToValue(key, context.message(key));
                }
            }
            // fill up with "" if needed
            final int size = filterUsed.getValues().size();
            filterUsed.addBlankRows(maxSize - size);
        }
        return filterList;
    }

    public String getGraphTypeValue() {
        return resultObject.getGraphType().getValue();
    }

    /**
     * the height of the graph. This is dynamic because in case of long series, the legend tends to eat up all available space, resulting in a huge
     * legend and a small graph.
     * 
     * @return an int which is the graph height. This is read by the jsp.
     */
    public int getHeight() {
        if (getShowLegend().equals("false")) {
            return 300;
        }
        return (int) Math.round(300 + (getRowHeaders().length * 150.0 / 20.0));
    }

    /**
     * the help anchor (=name of the #anchor in the help file), generated from the basekey. If the basekey is one.two.three, the helpName will be
     * oneTwoThree.
     */
    public String getHelpAnchor() {
        final KeyToHelpNameConverter converter = new KeyToHelpNameConverter();
        return converter.toString(getBaseKey());
    }

    public String getHelpFile() {
        return resultObject.getHelpFile();
    }

    /**
     * this method takes the object (this), and splits the data up into an array of separated StatisticalDataProducers, where each serie or category
     * in the original object gets a separate StatisticalDataProducer in the resulting array. So, in case of a StatisticalDataProducer object with 4
     * series, the result of this method will be an array with 4 StatisticalDataProducerObjects, each of them having one of the original series as a
     * single series in its data. This method is needed to be able to produce a combined graph. A combined graph is useful in case the y-axis ranges
     * of the different series can not reasonably be combined in one single graph with a common y-axis (for example: if series 1 ranges from y=100.000
     * to y=200.000, and series 2 ranges from y=1 to y=10.) Graphs can be split into multiple graphs by series, or by categories. In the latter case,
     * for each category a separate graph is created.
     * <p>
     * <b>Notes:</b><br>
     * The following fields of the resultObject are treated as follows:
     * <ul>
     * <li>The <code>dontSwitchXY</code> boolean field is ignored by this method.
     * <li>The <code>seriesCount</code> field is ignored too, as each graph has per definition one series.
     * <li>The <code>categoriesCount</code> field sets the number of datapoints on the x-axis. Not setting this just shows all points.
     * </ul>
     * 
     * @return an array of separated StatisticalDataProducers, where each serie or category in the original object gets a separate
     * StatisticalDataProducer in the resulting array
     */
    public StatisticalDataProducer[] getMultiGraphProducers() {
        if (!isMultiGraph()) {
            return null;
        }
        final int numberOfGraphs;
        if (resultObject.getMultiGraph() == StatisticalResultDTO.MultiGraph.BY_COLUMN) {
            numberOfGraphs = getColumnHeaders().length;
        } else {
            numberOfGraphs = getRowHeaders().length;
        }
        final StatisticalDataProducer[] producerArray = new StatisticalDataProducer[numberOfGraphs];
        for (int i = 0; i < numberOfGraphs; i++) {
            final StatisticalDataProducer item = new StatisticalDataProducer(this, i, resultObject.getCategoriesCount());
            producerArray[i] = item;
        }
        return producerArray;
    }

    /**
     * This method is needed for Cewolf to produce the graphs. See remarks at the hasExpired method.
     */
    @Override
    public String getProducerId() {
        return producerId;
    }

    public String[] getRowHeaders() {
        return rowHeaders;
    }

    /**
     * determines if the legend is shown or not. It is always shown by the pie charts. If a bar or line, then it is shown if there are more than one
     * series to be displayed. This method is read by the jsp.
     * 
     * @return a jsp readable string which can either be "true" or "false".
     */
    public String getShowLegend() {
        if (resultObject.getGraphType() == StatisticalResultDTO.GraphType.PIE) {
            return "true";
        }
        final Values2D values2D = (Values2D) dataset;
        if (values2D.getRowCount() == 1) {
            return "false";
        }
        return "true";
    }

    /**
     * the subtitle for the graph
     */
    public String getSubTitle() {
        final String subTitle = resultObject.getSubTitle();
        if (subTitle == null) {
            return "";
        }
        return subTitle;
    }

    /**
     * gets the raw data
     * @return a 2 dimensional array of <code>Number</code>s with the raw data
     */
    public Number[][] getTableCells() {
        return resultObject.getTableCells();
    }

    // derived getters and setters, getting their values from the wrapped valueObject

    public int getTableColumnCount() {
        return getColumnHeaders().length + 1;
    }

    public String getTitle() {
        if (title == null && context != null) {
            return context.message(getBaseKey());
        }
        return title;
    }

    /**
     * gets the X-axis label. If the language resource key for this label was not defined, it returns an empty string. It adjusts the label
     * automatically for any scaling done: if so, the scalefactor is appended to the label, between parenthesis (like "( x 1000)")
     * @return the x-axis label of the graph
     */
    public String getX_axis() {
        if (x_axis.startsWith("???") & x_axis.endsWith("???")) {// in case of key not defined
            return "";
        }
        final StringBuilder sb = new StringBuilder();
        sb.append(x_axis);
        if (resultObject.getScaleFactorX() == null) {
            if (resultObject.getXAxisUnits().length() > 0) {
                sb.append("  ").append("(").append(resultObject.getXAxisUnits()).append(")");
            }
            return sb.toString();
        }
        sb.append("   ").append(resultObject.getScaleFactorX());
        if (resultObject.getXAxisUnits().length() > 0) {
            sb.insert(sb.length() - 1, " ").insert(sb.length() - 1, resultObject.getXAxisUnits());
        }
        return sb.toString();

    }

    /**
     * gets the y-axis label. The label is adjusted for automatic scaling: if scaling was applied, the scalefactor is appended between parenthesis
     * (like "(x 1000)"). If units were used, the units are placed behind the scale factor number, inside the parenthesis, so like "(x 1000 units)".
     * @return the y-axis label for the graph.
     */
    public String getY_axis() {
        if (scaleFactor == 0) {
            final StringBuilder sb = new StringBuilder();
            sb.append(y_axis);
            if (resultObject.getYAxisUnits().length() > 0) {
                sb.append("  ").append("(").append(resultObject.getYAxisUnits()).append(")");
            }
            return sb.toString();
        }
        // apply scaling suffix
        final String factorSign = (scaleFactor < 0) ? resultObject.getYAxisUnits() + "/" : "x";
        final int factor = (int) Math.pow(1000, Math.abs(scaleFactor));
        final StringBuilder sb = new StringBuilder();
        if (settings != null) {
            sb.append(settings.getNumberConverterForPrecision(0).toString(new BigDecimal(factor)));
        } else {
            sb.append(String.valueOf(factor));
        }
        sb.insert(0, factorSign).insert(0, "   (");
        if (scaleFactor > 0 && resultObject.getYAxisUnits().length() > 0) {
            sb.append(" ").append(resultObject.getYAxisUnits());
        }
        sb.append(")");
        sb.insert(0, y_axis);
        return sb.toString();
    }

    /**
     * This method is needed for Cewolf to produce the graphs. It always returns true. If not, DataProducers with equal producerId's return the same
     * chart (as it is cached). As ProducerId is often equal for a lot of graphs, hasExpired is always set to true.
     */
    @Override
    @SuppressWarnings("rawtypes")
    public boolean hasExpired(final Map arg0, final Date arg1) {
        return true;
    }

    public boolean isMultiGraph() {
        return resultObject.getMultiGraph() != StatisticalResultDTO.MultiGraph.NONE;
    }

    /**
     * if this is false, the applied filters are not shown in the result. The default is true.
     */
    public boolean isShowAppliedFilters() {
        return resultObject.getFiltersUsed().size() > 0;
    }

    public boolean isShowGraph() {
        return resultObject.getGraphType() != StatisticalResultDTO.GraphType.NONE;
    }

    public boolean isShowTable() {
        return resultObject.isShowTable();
    }

    /**
     * This method is needed for creating graphs with Cewolf.
     */
    @Override
    @SuppressWarnings("rawtypes")
    public Object produceDataset(final Map params) throws DatasetProduceException {
        return dataset;
    }

    public void setSettings(final LocalSettings settings) {
        this.settings = settings;
    }

    /**
     * gets the value of resultObject.hasErrorBars() for descendant classes
     * @return true if there are error bars defined
     */
    protected boolean hasErrorBars() {
        return resultObject.hasErrorBars();
    }

    /**
     * sets the dataset for the graph. Takes the following actions:
     * <ul>
     * <li>checks if a graph is needed
     * <li>it may switch x and y, depending on the <code>resultObject</code>'s <code>dontSwitchXY</code> property
     * <li>
     * generates the dataset
     * <li>scales it.
     * </ul>
     */
    protected void setDataset() {
        if (resultObject.getGraphType() == StatisticalResultDTO.GraphType.PIE) {
            dataset = GraphDatasetGenerator.generatePieDataset(getRowHeaders(), getTableCells());
            return;
        }
        if ((isShowGraph()) && (!isMultiGraph())) {
            final String[] seriesNames;
            final boolean errorBars = resultObject.hasErrorBars();
            final Number[][] dataArray;
            if (resultObject.isDontSwitchXY()) {
                // dont switch, exceptional behavior
                seriesNames = GraphDatasetGenerator.createGraphSeries(getRowHeaders(), resultObject.getSeriesCount());
                dataArray = getTableCells();
            } else {
                // switch, default behavior
                seriesNames = GraphDatasetGenerator.createGraphSeries(getColumnHeaders(), resultObject.getSeriesCount());
                dataArray = GraphDatasetGenerator.switchXYData(getTableCells());
            }
            final CategoryDataset dataset = GraphDatasetGenerator.generateDataset(dataArray, seriesNames, categories, errorBars);
            scaleFactor = calculateScaleFactor(dataset);
            this.dataset = GraphDatasetGenerator.scaleData(dataset, scaleFactor, errorBars);
        }
    }

    /**
     * method calculates the scalefactor from the input dataset. Scaling means for example: if the numbers are in the range of millions (so: from 0 to
     * 10 million), numbers 1 to 10 are shown on the y-axis, and the y-axis label indicates " (x 1.000.000)". Scaling is done per thousands.
     * 
     * @param dataset
     * @return the scaleFactor. A value of 1 correspondents with "x1000", a value of 2 with "x1.000.000".
     */
    private byte calculateScaleFactor(final Object dataset) {
        if (isShowGraph() && (!isMultiGraph())) {
            return GraphDatasetGenerator.calculateScaleFactor(dataset);
        }
        return (byte) 0;
    }

    /**
     * gets the sum value of all pie sections, in order to calculate percentages in tooltips.
     * @return a double which is the total value of all sections of the pie.
     */
    private double getTotalForPie() {
        if (totalForPie == null) {
            double result = 0;
            for (int i = 0; i < resultObject.getTableCells().length; i++) {
                result += resultObject.getTableCells()[i][0].doubleValue();
            }
            totalForPie = result;
        }
        return totalForPie.doubleValue();
    }

    /**
     * Takes the keys, and uses these to look up the real strings in the language resource bundle. sets the title, the x- and y-axis labels, and the
     * categories. This should always be called from the constructor.
     * 
     * @param context
     */
    private void setResourceStrings(final ActionContext context) {
        this.context = context;
        title = context.message(getBaseKey());
        x_axis = context.message(getBaseKey() + ".xAxis");
        y_axis = context.message(getBaseKey() + ".yAxis");
        for (int i = 0; i < rowHeaders.length; i++) {
            if (resultObject.getRowHeader(i) != null) {
                rowHeaders[i] = resultObject.getRowHeader(i);
                if (!resultObject.isDontSwitchXY() && (i < resultObject.getCategoriesCount())) {
                    categories[i] = rowHeaders[i];
                }
            } else {
                rowHeaders[i] = context.message(resultObject.getRowKey(i), resultObject.getRowKeyArgs(i));
                if (!resultObject.isDontSwitchXY() && (i < resultObject.getCategoriesCount())) {
                    categories[i] = context.message(resultObject.getRowKey(i) + ".short");
                    if (categories[i].startsWith("???") & categories[i].endsWith("???")) {// in case of key not defined
                        categories[i] = "";
                    }
                }
            }
        }
        for (int i = 0; i < columnHeaders.length; i++) {
            if (resultObject.getColumnHeader(i) != null) {
                columnHeaders[i] = resultObject.getColumnHeader(i);
                if (resultObject.isDontSwitchXY() && (i < resultObject.getCategoriesCount())) {
                    categories[i] = columnHeaders[i];
                }
            } else {
                columnHeaders[i] = context.message(resultObject.getColumnKey(i));
                if (resultObject.isDontSwitchXY() && (i < resultObject.getCategoriesCount())) {
                    categories[i] = context.message(resultObject.getColumnKey(i) + ".short");
                    if (categories[i].startsWith("???") & categories[i].endsWith("???")) {// in case of key not defined
                        categories[i] = "";
                    }
                }
            }
        }
    }

}
