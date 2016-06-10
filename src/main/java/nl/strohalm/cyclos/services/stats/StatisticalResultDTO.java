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
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import nl.strohalm.cyclos.entities.accounts.SystemAccountType;
import nl.strohalm.cyclos.entities.accounts.transactions.PaymentFilter;
import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.entities.reports.StatisticalNumber;
import nl.strohalm.cyclos.services.stats.exceptions.InconsistentDataDimensionsException;
import nl.strohalm.cyclos.services.stats.general.FilterUsed;
import nl.strohalm.cyclos.utils.DataObject;
import nl.strohalm.cyclos.utils.NamedPeriod;
import nl.strohalm.cyclos.utils.StringValuedEnum;
import nl.strohalm.cyclos.utils.statistics.ListOperations;

import org.jfree.chart.plot.Marker;

/**
 * this class is the base value object containing the statistical data to be shown in the jsp. It is used for rendering the data tables. For each
 * statistic table, the following <b>must</b> be done:
 * <ul>
 * <li>Assign the data array via the constructor. This is a two dimensional array of Numbers
 * <li>set the baseKey. This is the basekey for looking up strings in the language resource bundles <br>
 * The baseKey is used for the following items, so these keys must be provided in the language resource bundle:
 * <ul>
 * <li><b>plain basekey</b> is used for the graph title
 * <li><b><code>basekey</code>.title</b> is used as the title of the surrounding window
 * <li><b><code>basekey</code>.yAxis</b> is used as the title along the y-axis of the graph
 * <li><b><code>basekey</code>.xAxis</b> is used as the title along the x-axis of the graph
 * <li><b><code>basekey</code>.row<code>N</code></b> is used as the name of the Nth data row in the table
 * <li><b><code>basekey</code>.row<code>N</code>.short</b> is used as a shorter version of this name, to appear at the x-Axis below the Nth category
 * in the graph. Note that this string must be as short as possible to fit in the graph.
 * <li><b>Helpfile</b>: all the dots (.) are deleted from the basekey, the elements between the dots are capitalized for their first letter, and a
 * <code>.jsp</code> is appended. This needs to be provided as a help file.
 * </ul>
 * <li>set the row keys, or the row Headers. See setRowKeys method for details.
 * <li>set the column keys or the column headers.
 * <li>If any filters were used, you <b>must</b> pass the used filters via the <code>setFilter</code> methods. Best use the corresponding methods of
 * StatisticalService: passPaymentFilter and passGroupFilter.
 * </ul>
 * 
 * Optionally, the following may be set:
 * <ul>
 * <li>set graphType
 * <li>in addition to row keys, set one or more row headers for specific strings which need not to be taken from the language resource bundle. You can
 * set one at a time, or an array with all of the headers.
 * <li>same for column headers, you can also set one or more.
 * <li>you can set column subheaders; usually this is used to indicate the units in the column. This will only show up in the table, not in the graph
 * <li>set a subtitle for the graph and table
 * <li>set the multiGraph field in case you want to display a separate graph for each table row or column
 * <li>set showTable to false - this makes that the table is not shown. The default is showTable = true. Don't forget to set a graphtype if showTable
 * is false, otherwise nothing is shown.
 * <li>set a scale Factor for the x-axis. (like "x 1000")
 * <li>set the graph dimensions. See {@link #setGraphDimensions(TableToGraph, Integer, Integer)}
 * <li>if any unit along the x or y-axis is to be displayed, you can pass this via the <code>setXAxisUnits</code> method or <code>setYAxisUnits</code>
 * method.
 * <li>You can set markers for the graph. A marker is a horizontal or vertical line which is drawn through the whole graph in order to indicate a
 * special result value or domain value. For example, a vertical line to indicate "today" in a graph with dates along the x-axis.
 * </ul>
 * 
 * @author rinke
 */
public class StatisticalResultDTO extends DataObject {

    /**
     * enum indicating the graph type.
     * 
     * @author Rinke
     * 
     */
    public enum GraphType implements StringValuedEnum {
        /**
         * no graph
         */
        NONE("none"),
        /**
         * a vertical bar graph
         */
        BAR("verticalbar"),
        /**
         * a line graph
         */
        LINE("line"),
        /**
         * a pie chart
         */
        PIE("pie"),
        /**
         * a chart where each serie is an area, and areas are stacked upon each other.
         */
        STACKED_AREA("stackedarea");

        /**
         * a String describing the value. This String value is used in the jsp to set the graphtype attribute.
         */
        private final String value;

        private GraphType(final String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    /**
     * an enum indicating how one table should be split up into multiple graphs.
     * 
     * @author Rinke
     * 
     */
    public enum MultiGraph {
        /**
         * no splitting up
         */
        NONE,
        /**
         * each column in the table should get its own graph
         */
        BY_COLUMN,
        /**
         * each row in the table should get its own graph.
         */
        BY_ROW;
    }

    /**
     * static innner class defining the Key Object for a language resouce bundle. A simple String for ResourceKey will not do the job, because
     * sometimes a placeholder argument is needed for the key (like "{0}"). This Object allows for placeholder arguments to be passed. Presently only
     * used for row keys, not for column keys. See setRowKeys method for usage.
     * 
     * @author rinke
     * 
     */
    public static class ResourceKey {
        private String   key;
        private Object[] args;

        public ResourceKey(final String key, final Object... args) {
            this.key = key;
            this.args = args;
        }

        public Object[] getArgs() {
            return args;
        }

        public String getKey() {
            return key;
        }

    }

    /**
     * This enum determines which orientation is used when the table is converted to a graph.
     * 
     * @author Rinke
     */
    public enum TableToGraph {
        /**
         * the table columns become the categories along the x-axis of the graph. This means that the table rows become the series in the legend of
         * the graph.
         */
        COLUMN_IS_CATEGORY,
        /**
         * the table columns become the series in the legend of the graph. This means that the table rows become the categories along the x-axis of
         * the graph.
         */
        COLUMN_IS_SERIES;
    }

    private static final long serialVersionUID = 7243923567624382393L;

    /**
     * public pseudo-constructor; calls the private constructor.
     * @return a StatisticalResultDTO instance, containing no data, and display graph nor table.
     */
    static StatisticalResultDTO noDataAvailable(final String key) {
        final StatisticalResultDTO result = new StatisticalResultDTO();
        result.setBaseKey(key);
        return result;
    }

    /**
     * the number of rows
     */
    private int                    rows;
    /**
     * the number of columns
     */
    private int                    columns;
    /**
     * the keys for the row headers of the table. The real headers must be fetched from the language resource bundle with these keys.
     */
    private ResourceKey[]          rowKeys;
    /**
     * the real strings for the row headers. In some cases, these strings are NOT fetched from the language resource bundle, for example in case of a
     * payment filter name. In such a case, the actual name is stored in this field.
     */
    private String[]               rowHeaders;
    /**
     * the keys for the column headers of the table. The real headers are fetched from the language resource bundle with these keys.
     */
    private String[]               columnKeys;
    /**
     * the headers above the table columns; these are also the seriesNames of the graph. This is used in case the strings are not fetched from the
     * resource bundles, for example with strings like "2006" or "%".
     */
    private String[]               columnHeaders;
    /**
     * the subheaders above the table columns; usually these are used to indicate the units in the column.
     */
    private String[]               columnSubHeaders;
    /**
     * The data for the table; can only be set via the constructor
     */
    private Number[][]             tableCells;
    /**
     * The number of categories in the graph. Categories are set on the x-axis; usually, this correspondents to the rows in the table, or in some
     * exceptional cases (when <code>
     * dontSwitchXY == true</code) in columns in the table. <br>
     * It can have the following values:
     * <ul>
     * <li><code>null</code>: all categories in the table will be rendered in the graph. <li><i>value > number of table categories</i>: ignored,
     * treated as if it were <code>null</code>. <li><i>value < number of table categories</i>: only this amount of categories is shown in the table,
     * starting from the first category. So the last or the last few categories which are in the table will not be shown in the graph. </ul> <br>
     * So set this field only if there are categories you want to show in the table but exclude from the graph.
     */
    private Integer                categoryCount;
    /**
     * The number of series in the graph. Series are set in the legend of the graph; usually, this correspondents to the columns in the table, or in
     * some exceptional cases (when <code>
     * dontSwitchXY == true</code) in rows in the table. <br>
     * It can have the following values:
     * <ul>
     * <li><code>null</code>: all series in the table will be rendered in the graph. <li><i>value > number of table series</i>: ignored, treated as if
     * it were <code>null</code>. <li><i>value < number of table series</i>: only this amount of series is shown in the table, starting from the first
     * serie. So the last or the last few series which are in the table will not be shown in the graph. </ul> <br>
     * So set this field only if there are series you want to show in the table but exclude from the graph.
     */
    private Integer                seriesCount;

    /**
     * this is the base key for the language resource files. <br>
     * Language Strings are being taken from this resource file by this base key.<br>
     * The basekey is used for the following, so all of these must be provided in the language resource bundle:
     * <ul>
     * <li><b>plain basekey</b> is used for the graph title
     * <li><b><code>basekey</code>.title</b> is used as the title of the surrounding window
     * <li>
     * <b><code>basekey</code>.yAxis</b> is used as the title along the y-axis of the graph
     * <li><b><code>basekey</code>.xAxis</b> is used as the title along the x-axis of the graph
     * <li><b><code>basekey</code>.row<code>N</code></b> is used as the name of the Nth data row in the table
     * <li><b> <code>basekey</code>.row<code>N</code>.short</b> is used as a shorter version of this name, to appear at the x-Axis below the Nth
     * category in the graph. Note that this string must be as short as possible to fit in the graph.
     * <li><b>Helpfile</b>: all the dots (.) are deleted from the basekey, the elements between the dots are capitalized for their first letter, and a
     * <code>.jsp</code> is appended. This needs to be provided as a help file.
     * </ul>
     */
    private String                 baseKey;
    /**
     * subtitle above the graph or above the leftmost column in the table
     */
    private String                 subTitle;
    /**
     * the scalefactor for the x-axis. This is usually not used, but it may contains Strings like "(x 1000)".
     */
    private String                 scaleFactorX;
    /**
     * the type of the graph, can be one of BAR, LINE, PIE or NONE
     */
    private GraphType              graphType;
    /**
     * indicates if the chart is a combined chart. If so, for each series (column) OR for each category (table row) a separate graph is created. The
     * graphs are tiled in vertical direction, while there is one combined x-axis. If none (the default), all series and categories are shown in one
     * single graph, having both x-axis and y-axis in common. The table just shows all the series and categories, independant of the value of this
     * field. So this field only effects the presentation of the graph, not of the table.
     */
    private MultiGraph             multiGraph;
    /**
     * true if the table must NOT be shown. This is true in case of histograms.
     */
    private boolean                showTable;
    /**
     * For most graphs, x and y of the table need to be switched in order to display the graph correctly.<br>
     * In special cases, this does <b>NOT</b> need to be switched. In such a case, set <code>dontSwitchXY</code> to true.
     */
    private boolean                dontSwitchXY;

    /**
     * the list with used filters, to be displayed in the "Filters used" box below each graph or table.
     */
    private final List<FilterUsed> filtersUsed = new ArrayList<FilterUsed>();

    /**
     * a String representing the units to be displayed along the y axis of a graph.
     */
    private String                 yAxisUnits  = "";
    /**
     * a String representing the units to be displayed along the x axis of a graph.
     */
    private String                 xAxisUnits  = "";
    /**
     * a String used to set the help file. The default is &quot;statistics&quot;. Only use in case this is another file than statistics.
     */
    private String                 helpFile    = "statistics";
    /**
     * an array of Markers. Markers are horizontal or vertical lines to draw through the graph to mark a special result value or domain value. In this
     * case, these are the domain markers (= vertical lines), but a future version may also include a special field for range markers (= horizontal
     * lines). Markers should be set to the jsp with the following construct:
     * 
     * <pre>
     *      &lt;cewolf:chartpostprocessor id=&quot;chartPostProcessorImpl&quot; &gt;
     *          &lt;cewolf:param name=&quot;domainMarkers&quot; value=&quot;${dataTable.domainMarkers}&quot; /&gt;
     *      &lt;/cewolf:chartpostprocessor&gt;
     * </pre>
     * 
     * For each Marker in the array, you MUST set the place where the marker is to be drawn in the graph. In case of Category plots, this is the label
     * which is placed on the x-axis for which you want to put the vertical line in the graph.<br>
     * Optionally, you can set a color (setPaint) and a label (setLabel) to the Marker. The label is a string literal; if you want to put a language
     * string in this label, you must set the keys for the marker on the service, and read these and replace them on the action. See for example
     * paymentService.getSimulateConversionGraph and SimulateConversionAction.
     */
    private Marker[]               domainMarkers;

    /**
     * This constructor only sets the data without column and row names.
     * @param tableCells a Number[][], where: Series = j = columnHeaders; Categories = i = rowKeys
     */
    public StatisticalResultDTO(final Number[][] tableCells) {
        this.tableCells = tableCells;
        rows = tableCells.length;
        rowKeys = new ResourceKey[rows];
        rowHeaders = new String[rows];
        columns = tableCells[0].length;
        columnKeys = new String[columns];
        columnHeaders = new String[columns];
        columnSubHeaders = new String[columns];
        graphType = GraphType.NONE;
        multiGraph = MultiGraph.NONE;
        showTable = true;
    }

    /**
     * private constructor, displays no graphs nor tables, and contains no data
     * 
     */
    private StatisticalResultDTO() {
        graphType = GraphType.NONE;
        tableCells = new Number[0][0];
    }

    // ########################### Column Getters And Setters ###############################
    // #######################################################################################

    public String getBaseKey() {
        return baseKey;
    }

    /**
     * 
     * @return the number of categories for the graph.
     */
    public int getCategoriesCount() {
        if (categoryCount != null) {
            return categoryCount;
        }
        return (dontSwitchXY) ? columns : rows;
    }

    public String getColumnHeader(final int index) {
        return columnHeaders[index];
    }

    public String[] getColumnHeaders() {
        return columnHeaders;
    }

    public String getColumnKey(final int index) {
        return columnKeys[index];
    }

    public String[] getColumnKeys() {
        return columnKeys;
    }

    public String[] getColumnSubHeaders() {
        return columnSubHeaders;
    }

    /**
     * gets domain markers for the graph. See the markers field for further explanation.
     */
    public Marker[] getDomainMarkers() {
        return domainMarkers;
    }

    /**
     * gets the List with the used filter specs.
     * 
     * @return a List with FilterUsed Objects, one object for every relevant filter.
     */
    public List<FilterUsed> getFiltersUsed() {
        return filtersUsed;
    }

    public GraphType getGraphType() {
        return graphType;
    }

    public String getHelpFile() {
        return helpFile;
    }

    public MultiGraph getMultiGraph() {
        return multiGraph;
    }

    public String getRowHeader(final int index) {
        return rowHeaders[index];
    }

    public String[] getRowHeaders() {
        return rowHeaders;
    }

    /**
     * returns the rowKey with the given index as a String. Any placeholder arguments are ignored.
     * 
     * @param index
     * @return a String containing the row key, without any placeholder arguments (like "{0}")
     */
    public String getRowKey(final int index) {
        return rowKeys[index].getKey();
    }

    /**
     * returns the arguments for the row key with the given index, for use in keys having {n} placeholder arguments.
     * @param index the index of the row key
     * @return an array of Objects holding the placeholder arguments for the key. If no arguments are available, the length of the array is 0.
     */
    public Object[] getRowKeyArgs(final int index) {
        return rowKeys[index].getArgs();
    }

    public ResourceKey[] getRowKeys() {
        return rowKeys;
    }

    public String getScaleFactorX() {
        return scaleFactorX;
    }

    /**
     * 
     * @return the number of series for the graph
     */
    public int getSeriesCount() {
        if (seriesCount != null) {
            return seriesCount;
        }
        return (dontSwitchXY) ? rows : columns;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public Number[][] getTableCells() {
        return tableCells;
    }

    /**
     * gets the units for the x-axis. Use <code>getParenthesizedYAxisUnits()</code> if you need the units between ().
     */
    public String getXAxisUnits() {
        return xAxisUnits;
    }

    /**
     * gets the units for the y-axis. Use <code>getParenthesizedYAxisUnits()</code> if you need the units between ().
     */
    public String getYAxisUnits() {
        return yAxisUnits;
    }

    /**
     * checks if the numbers in this object do have errors defined. It checks if the complete range has any error bar.
     * @return true if error bars are defined.
     */
    public boolean hasErrorBars() {
        for (final Number[] element : tableCells) {
            for (final Number element2 : element) {
                if (element2 instanceof StatisticalNumber) {
                    if (((StatisticalNumber) element2).hasErrorBar()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean isDontSwitchXY() {
        return dontSwitchXY;
    }

    public boolean isShowTable() {
        return showTable;
    }

    /**
     * Allows you to order the series according to an array of bytes with order numbers. Of course, this can only be done after the tableCells and
     * seriesNames have been set to the graph/table. All corresponding headers and keys, as well as the data is reordered.<br>
     * Example:<br>
     * Suppose the tableCells contain the following points:
     * 
     * <pre>
     * { { 5, 7, 2 }, { 5, 7, 2 }, { 5, 7, 2 }, { 5, 7, 2 }, { 5, 7, 2 } }
     * </pre>
     * 
     * Suppose the seriesOrder param is {2, 1, 3};<br>
     * After running this method, the tableCells will look like this:
     * 
     * <pre>
     * { { 7, 5, 2 }, { 7, 5, 2 }, { 7, 5, 2 }, { 7, 5, 2 }, { 7, 5, 2 } }
     * </pre>
     * 
     * @param seriesOrder an array of bytes containing the index numbers of the series. The values do not need to be subsequent numbers; also {16, 8,
     * 10} would be possible. If a value is encountered more than once, corresponding elements are considered equal at ordering.
     */
    public void orderSeries(final byte[] seriesOrder) {
        // helper class for bundling all relevant elements in order to index them in one go, with one rule
        class IndexedSerie {
            private Number[] numbers;
            private String   seriesHeader;
            private String   seriesSubHeader;
            private String   seriesKey;
            private byte     index;

            IndexedSerie(final Number[] numbers, final int originalIndex, final byte index) {
                this.numbers = numbers;
                if (columnHeaders != null && columnHeaders.length > 0) {
                    seriesHeader = columnHeaders[originalIndex];
                }
                // the key by def exists, else IllegalArgumentException
                seriesKey = columnKeys[originalIndex];
                if (columnSubHeaders != null && columnSubHeaders.length > 0) {
                    seriesSubHeader = columnSubHeaders[originalIndex];
                }
                this.index = index;
            }
        }

        final Comparator<IndexedSerie> comparator = new Comparator<IndexedSerie>() {
            public int compare(final IndexedSerie o1, final IndexedSerie o2) {
                if (o1.index == o2.index) {
                    // first the smallest
                    final double result = o1.numbers[0].doubleValue() - o2.numbers[0].doubleValue();
                    return (int) Math.signum(result);
                }
                // first the one with the lowest index
                return o1.index - o2.index;
            }
        };

        // first some checks
        if (tableCells == null || columnKeys == null) {
            throw new IllegalArgumentException("Method orderSeries may only be called after setting columnKeys.");
        }
        if (tableCells.length == 0 || tableCells[0] == null || tableCells[0].length == 0) {
            // nothing to order, so return
            return;
        }
        if (seriesOrder == null || seriesOrder.length != columnKeys.length || seriesOrder.length != tableCells[0].length) {
            throw new InconsistentDataDimensionsException("SeriesNames / dataset length does not match order length.");
        }

        // transpose so that we get an array of different series, in stead of an array of points
        final Number[][] transposedMatrix = ListOperations.transposeMatrix(tableCells);
        // rewrite transposed Matrix as an array of series and add the indexes
        final IndexedSerie[] arrayOfSeries = new IndexedSerie[seriesOrder.length];
        for (int i = 0; i < seriesOrder.length; i++) {
            arrayOfSeries[i] = new IndexedSerie(transposedMatrix[i], i, seriesOrder[i]);
        }
        // sort array of series on their added index
        Arrays.sort(arrayOfSeries, comparator);
        // write back the array of series to a matrix
        final Number[][] transposedOrderedMatrix = new Number[seriesOrder.length][transposedMatrix[0].length];
        for (int i = 0; i < seriesOrder.length; i++) {
            transposedOrderedMatrix[i] = arrayOfSeries[i].numbers;
            // keys and headers can already be written back
            columnKeys[i] = arrayOfSeries[i].seriesKey;
            if (columnHeaders != null && columnHeaders.length > 0) {
                columnHeaders[i] = arrayOfSeries[i].seriesHeader;
            }
            if (columnSubHeaders != null && columnSubHeaders.length > 0) {
                columnSubHeaders[i] = arrayOfSeries[i].seriesSubHeader;
            }
        }
        // transpose back
        final Number[][] orderedMatrix = ListOperations.transposeMatrix(transposedOrderedMatrix);
        // write back
        tableCells = orderedMatrix;
    }

    public void setBaseKey(final String baseKey) {
        this.baseKey = baseKey;
    }

    /**
     * a setter to set one specific indexed columnheader.
     * @param header
     * @param index
     */
    public void setColumnHeader(final String header, final int index) {
        columnHeaders[index] = header;
    }

    /**
     * Simple setter for the columnKeys (seriesnames)
     * @param lColumnKeys a String[], the header keys above the printed table (and series names keys of the graph)
     * @throws InconsistentDataDimensionsException. Raised if length of the String array param is inconsistent with data (and the number of columns >
     * 0).
     */
    public void setColumnKeys(final String[] lColumnKeys) throws InconsistentDataDimensionsException {
        if (columns > 0 && lColumnKeys.length != columns) {
            throw new InconsistentDataDimensionsException("SeriesNames length and dataset length do not match.");
        }
        columnKeys = lColumnKeys;
    }

    /**
     * Sets domain markers for the graph. See the domainMarkers field for further explanation
     * @param markers
     */
    public void setDomainMarkers(final Marker[] markers) {
        domainMarkers = markers;
    }

    /**
     * This setter sets three properties which can only be set at once (because the second and third may only be set AFTER the first is set, I created
     * a 3-in-1-setter).<br>
     * It takes care for the number of categories and series in the graph. There is no obligation to set this; if you do not set it, it follows the
     * default behaviour. All params accept <code>null</code> values, which also corresponds to the default behaviour. <br>
     * The default behaviour is:
     * <ul>
     * <li>a table column in the graph will be shown as a serie in the graph, meaning that it shows up in the legend of the graph.
     * <li>the number of table columns equals the number of series in the graph.
     * <li>the number of table rows equals the number of categories along the x-axis of the graph.
     * </ul>
     * @param tableToGraph a <code>TableToGraph</code> enum which determines how the orientation of the graph is. If this is set to <code>null</code>
     * the default behaviour will be kept, meaning that table columns are converted to graph series.<br>
     * Note that this param only needs to be set in exceptional cases. Normally you can just pass <code>null</code>.
     * @param seriesCount an <code>Integer</code> which indicates the number of series in the graph. If (and only if) this is smaller than the number
     * of columns in the table (or the number of rows, depending on the <code>columnIsCategory</code> param), then only part of the available series
     * is shown in the graph, starting the count at the first series.<br>
     * A <code>null</code> parameter enforces the default behaviour.<br>
     * Example: if <code>columnIsCategory</code> is false, the table has 5 columns, and seriesCount = 4, then only the first 4 columns of the table
     * are shown in the graph; the last column of the table is not shown.
     * @param categoriesCount as with seriesCount, only now for categories along the x-axis of the graph.
     */
    public void setGraphDimensions(final TableToGraph tableToGraph, final Integer seriesCount, final Integer categoriesCount) {
        if (tableToGraph != null && tableToGraph == TableToGraph.COLUMN_IS_CATEGORY) {
            dontSwitchXY = true;
        } else {
            dontSwitchXY = false;
        }
        final int tableSeriesCount = (dontSwitchXY) ? rows : columns;
        if (seriesCount != null && seriesCount < tableSeriesCount) {
            this.seriesCount = seriesCount;
        } else {
            this.seriesCount = null;
        }
        final int tableCategoriesCount = (dontSwitchXY) ? columns : rows;
        if (categoriesCount != null && categoriesCount < tableCategoriesCount) {
            categoryCount = categoriesCount;
        } else {
            categoryCount = null;
        }
    }

    public void setGraphType(final GraphType graphType) {
        this.graphType = graphType;
    }

    /**
     * sets the help file. The default is &quot;statistics&quot;. Use this only in case the help file is another than statistics, so in case this
     * class is used outside the context of the statistics module.
     * @param fileName
     */
    public void setHelpFile(final String fileName) {
        helpFile = fileName;
    }

    /**
     * a setter to set one specific indexed rowheader.
     * @param header
     * @param index
     */
    public void setRowHeader(final String header, final int index) {
        rowHeaders[index] = header;
    }

    /**
     * A setter for the categories or table row headers. Use this version if you need a param inside one or more of the keys. Usage:
     * <code>final StatisticalResultDTO.ResourceKey[] rowKeys = new StatisticalResultDTO.ResourceKey[n];
     * rowKeys[k] = new StatisticalResultDTO.ResourceKey(keyStringForKeyWithoutArgument);
     * rowKeys[m] = new StatisticalResultDTO.ResourceKey(keyStringForKeyWithArgument, new Object[] { arg0, arg1 });
     * statisticalResultDTO.setRowKeys(rowKeys);</code>
     * 
     * @param rowKeys = categories. An Array containing ResourceKey Objects. The headers for each row, and the categories along the x-axis of the
     * graph. Note that these are the KEYS for the row header strings; these corresponding strings are found in the language resource bundle.
     * @throws InconsistentDataDimensionsException. Raised if the length of the String array parameter does not match the length of the already set 2
     * dimensional array with the data (and if the number of rows > 0).
     */
    public void setRowKeys(final ResourceKey[] rowKeys) throws InconsistentDataDimensionsException {
        if (rows > 0 && rowKeys.length != rows) {
            throw new InconsistentDataDimensionsException("Number of rowKeys and dataset length do not match.");
        }
        this.rowKeys = rowKeys;
    }

    /**
     * a Simple setter for the categories or table row headers. Use this version simply when only needing Strings for keys, without any parameters.
     * @param rowKeys = categories. The headers for each row, and the categories along the x-axis of the graph. Note that these are the KEYS for the
     * row header strings; these corresponding strings are found in the language resource bundle.
     * @throws InconsistentDataDimensionsException. Raised if the length of the String array parameter does not match the length of the already set 2
     * dimensional array with the data (and if the number of rows > 0).
     */
    public void setRowKeys(final String[] rowKeys) throws InconsistentDataDimensionsException {
        if (rows > 0 && rowKeys.length != rows) {
            throw new InconsistentDataDimensionsException("Number of rowKeys and dataset length do not match.");
        }
        final ResourceKey[] newRowKeys = new ResourceKey[rowKeys.length];
        for (int i = 0; i < rowKeys.length; i++) {
            newRowKeys[i] = new ResourceKey(rowKeys[i]);
        }
        this.rowKeys = newRowKeys;
    }

    /**
     * makes that the table is not shown. You MUST set a graphtype then.
     * @param showTable
     */
    public void setShowTable(final boolean showTable) {
        this.showTable = showTable;
    }

    public void setSubTitle(final String subTitle) {
        this.subTitle = subTitle;
    }

    /**
     * if any x-axis unit is to be displayed with the x-axis label, you can pass it via this method. If you need a key in stead of a string, we
     * suggest you don't use this method, but just place the unit between parenthesis behind the <baseKey>.xAxis string in the resouce bundle.
     * @param axisUnits the String to be displayed along the x-axis. It will be placed between () after the normal x-axis label.
     */
    public void setXAxisUnits(final String axisUnits) {
        xAxisUnits = axisUnits;
    }

    /**
     * if any y-axis unit is to be displayed with the y-axis label, you can pass it via this method.
     * @param axisUnits the String to be displayed along the y-axis. It will be placed between () after the normal y-axis label.
     */
    public void setYAxisUnits(final String axisUnits) {
        yAxisUnits = axisUnits;
    }

    /**
     * A simple setter for the columnHeaders (= seriesNames).
     * @param columnHeaders. The headers above the printed table (and the series names of the graph)
     * @throws InconsistenDataDimensionsException. Raised if the length of the String array parameter does not match the length of the allready set 2
     * dimensional array with the data (and the number of columns > 0).
     */
    void setColumnHeaders(final String[] lColumnHeaders) throws InconsistentDataDimensionsException {
        if (columns > 0 && lColumnHeaders.length != columns) {
            throw new InconsistentDataDimensionsException("SeriesNames length and dataset length do not match.");
        }
        columnHeaders = lColumnHeaders;
    }

    /**
     * see setColumnHeaders. This works the same, but for columnSubHeaders (which are usually used for unit indication).
     * @param lColumnSubHeaders
     * @throws InconsistentDataDimensionsException
     */
    void setColumnSubHeaders(final String[] lColumnSubHeaders) throws InconsistentDataDimensionsException {
        if (columns > 0 && lColumnSubHeaders.length != columns) {
            throw new InconsistentDataDimensionsException("SeriesNames length and dataset length do not match.");
        }
        columnSubHeaders = lColumnSubHeaders;
    }

    /**
     * sets the filter for the Member Groups. If you set this, it is shown below the graph or table as "used filters".
     * 
     * @param groupFilter a <code>Collection</code> of <code>Group</code>s.
     */
    void setFilter(final Collection<Group> groupFilter) {
        FilterUsed filterUsed;
        if (groupFilter == null) {
            setFilterAsNotUsed(FilterUsed.FilterType.GROUP);
            return;
        } else if (groupFilter.size() == 0) {
            filterUsed = FilterUsed.nothingSelected(FilterUsed.FilterType.GROUP, "member.search.allGroups");
        } else {
            final List<String> names = new ArrayList<String>(groupFilter.size());
            for (final Group g : groupFilter) {
                names.add(g.toString());
            }
            filterUsed = new FilterUsed(FilterUsed.FilterType.GROUP, names);
        }
        filtersUsed.add(filterUsed);
    }

    /**
     * Displays a <code>NamedPeriod</code> in the "filters used" box below every graph or table.
     * 
     * @param period a <code>NamedPeriod</code> of which the name will be displayed
     */
    void setFilter(final NamedPeriod period) {
        final FilterUsed filterUsed = new FilterUsed(FilterUsed.FilterType.PERIOD, period.getName());
        filtersUsed.add(filterUsed);
    }

    /**
     * Mark a <code>PaymentFilter</code> so that it is displayed in the "filters used" box below each graph or table.
     * 
     * @param paymentFilter the <code>PaymentFilter</code>. If <code>null</code> then it is shown as "all payments". Otherwise, the name is used. Use
     * <code>setFilter(filterType)</code> if you want to display "not used" for the payment filter.
     */
    void setFilter(final PaymentFilter paymentFilter) {
        final FilterUsed filterUsed;
        if (paymentFilter == null) {
            filterUsed = FilterUsed.nothingSelected(FilterUsed.FilterType.PAYMENT, "reports.stats.general.allPaymentTypes");
        } else {
            filterUsed = new FilterUsed(FilterUsed.FilterType.PAYMENT, paymentFilter.toString());
        }
        filtersUsed.add(filterUsed);
    }

    /**
     * Mark a <code>SystemAccountType</code> so that it is displayed in the "filters used" box below each graph or table.
     * 
     * @param systemAccountFilter the <code>SystemAccountType</code> which was used. Note that this may not be null.
     */
    void setFilter(final SystemAccountType systemAccountFilter) {
        final FilterUsed filterUsed = new FilterUsed(FilterUsed.FilterType.SYSTEM_ACCOUNT, systemAccountFilter.toString());
        filtersUsed.add(filterUsed);
    }

    /**
     * Mark a Filter so that it is displayed as "not used" in the results.
     */
    void setFilterAsNotUsed(final FilterUsed.FilterType filterType) {
        final FilterUsed filterUsed = FilterUsed.noFilterUsed(filterType);
        filtersUsed.add(filterUsed);
    }

    void setMultiGraph(final MultiGraph multiGraph) {
        this.multiGraph = multiGraph;
    }

    /**
     * A simple setter for the table row strings. See also setRowKeys. Generally, when a client uses this class, first the rowHeaders are checked. For
     * each missing rowHeaders, the rowKey is used to fetch the string from the language resource bundle.
     * @param rowHeaders
     * @throws InconsistentDataDimensionsException if the number of headers does not match the number of rows, and the number of rows > 0.
     * 
     */
    void setRowHeaders(final String[] rowHeaders) throws InconsistentDataDimensionsException {
        if (rows > 0 && rowHeaders.length != rows) {
            throw new InconsistentDataDimensionsException("Number of rowKeys and dataset length do not match.");
        }
        this.rowHeaders = rowHeaders;
    }

    void setScaleFactorX(final String scaleFactorX) {
        this.scaleFactorX = scaleFactorX;
    }

}
