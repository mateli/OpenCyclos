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

import java.awt.Color;
import java.awt.Font;
import java.util.Map;

import nl.strohalm.cyclos.utils.jfreeAsymmetric.AsymmetricStatisticalBarRenderer;
import nl.strohalm.cyclos.utils.jfreeAsymmetric.AsymmetricStatisticalCategoryDataset;
import nl.strohalm.cyclos.utils.jfreeAsymmetric.AsymmetricStatisticalLineAndShapeRenderer;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryMarker;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.Marker;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.category.StackedAreaRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.ui.RectangleAnchor;
import org.jfree.ui.TextAnchor;

import de.laures.cewolf.ChartPostProcessor;

/**
 * This is the general chart postprocessor. A Postprocessor is needed to customize the default appearrance of graphs in cewolf. This postprocessor is
 * a "one class fits all" general postprocessor for the complete application. If you want extra postprocessing, use this one by adding methods, and
 * specifying which methods to use via the param tags of cewolf. For example: <cewolf:chartpostprocessor id="chartPostProcessorImpl" > <cewolf:param
 * name="subtitle" value="${dataTable.subTitle}"/> </cewolf:chartpostprocessor>
 * 
 * The following params are yet available: * subtitle (String) - to render a subtitle * xlabelRotation (Double) - to rotate the labels on the x-axis
 * in order to create more space
 * 
 * @author rinke
 */
public final class ChartPostProcessorImpl implements ChartPostProcessor {

    private static final int ROTATE_XLABELS_IF_MORE_THAN = 10;

    /**
     * standard color range for graphs defining 21 colors.
     */
    private final Color[]    colorRange                  = { Color.BLUE, Color.RED, new Color(0x00FF00), Color.MAGENTA, Color.YELLOW, Color.CYAN, new Color(0x008000), new Color(0x800000), new Color(0x000080), new Color(0x800080), new Color(0x808000), new Color(0x008080), new Color(0x808080), new Color(0x000000), new Color(0xFFFFFF), new Color(0xC0C0C0), new Color(0xFF8000), new Color(0xFF0080), new Color(0x8000FF), new Color(0xEA8072), new Color(0xE5F5DC) };

    /**
     * This is the basic and only method which is called from the jsp. Any distinctions on what to process must be made inside this method, according
     * to params values.
     * 
     * @param chart the chart from which it is called
     * @param params a Map with any optional params which can be called from the jsp. Params in the jsp must be passed according to the following
     * example:
     * 
     * <pre>
     * &lt;cewolf:chartpostprocessor id=&quot;chartPostProcessorImpl&quot;&gt;
     *     &lt;cewolf:param name=&quot;subtitle&quot; value=&quot;this is a subtitle&quot;/&gt;
     * &lt;/cewolf:chartpostprocessor&gt;
     * </pre>
     */
    @Override
    @SuppressWarnings("rawtypes")
    public void processChart(final Object chart, final Map params) {
        // general method calls for all chart types
        setSubTitle(chart, params);
        setTitleFont(chart);
        // calls specific to chart types
        if (((JFreeChart) chart).getPlot().getClass() == CategoryPlot.class) {
            // GRAPH and BAR charts
            final CategoryPlot plot = (CategoryPlot) ((JFreeChart) chart).getPlot();
            setMarkers(plot, params);
            final CategoryItemRenderer renderer = plot.getRenderer();
            setRotatedXaxisLabels(plot, params);
            // Bar-specific methods
            if (renderer.getClass() == BarRenderer.class) {
                setErrorBars(plot);
                setBarColors(plot, colorRange);
                setMargins(plot);
            }
            // line-specific methods
            if (renderer.getClass() == LineAndShapeRenderer.class) {
                setLineAndShapesAndErrorBars(plot);
            }
            if (renderer.getClass() == StackedAreaRenderer.class) {
                applyStackedAreaRendering(plot);
            }
        } else {
            // PIE charts
            final PiePlot plot = (PiePlot) ((JFreeChart) chart).getPlot();
            setPieColors(plot, colorRange);
            setPieLabels(plot);
        }
    }

    private void applyStackedAreaRendering(final CategoryPlot plot) {
        // set domain grid lines visible
        plot.setDomainGridlinesVisible(true);
    }

    /**
     * This method sets the Colors for a bar chart with two series. It ONLY applies to bar charts
     * @param plot
     * @param aColorRange an <code>array</code> of <code>Color</code>s, where each color will be assigned to a consequetive series.
     */
    private void setBarColors(final CategoryPlot plot, final Color[] aColorRange) {
        final BarRenderer renderer = (BarRenderer) plot.getRenderer();
        for (int i = 0; i < aColorRange.length; i++) {
            renderer.setSeriesPaint(i, aColorRange[i]);
        }
    }

    /**
     * This method enables the rendering of error bars on each data bar.
     * @param plot
     */
    private void setErrorBars(final CategoryPlot plot) {
        if (plot.getDataset().getClass() == AsymmetricStatisticalCategoryDataset.class) {
            final AsymmetricStatisticalBarRenderer barRenderer = new AsymmetricStatisticalBarRenderer();
            plot.setRenderer(barRenderer);
        }
    }

    /**
     * not in use yet. This method generates labels above each bar. At this very moment, these labels just show the value of the underlying data
     * (which is not very usefull). It could be used to display for example n-values.
     * @param plot
     */
    @SuppressWarnings("unused")
    private void setItemLabels(final CategoryPlot plot) {
        final BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setItemLabelGenerator(new StandardCategoryItemLabelGenerator()); // TODO change to a custom ItemLabelGenerator; see JFreeChart
        // developer Guide, version 0.9.18 (april 2004), page 80 and
        // following pages
        renderer.setItemLabelsVisible(true);
        renderer.setItemLabelFont(new Font("SansSerif", Font.PLAIN, 9));
        renderer.setItemLabelPaint(null);
        renderer.setSeriesItemLabelPaint(0, renderer.getSeriesPaint(0));
        renderer.setSeriesItemLabelPaint(1, renderer.getSeriesPaint(1));
    }

    /**
     * sets the lines, shapes and error bars for the line graphs
     * @param plot
     */
    private void setLineAndShapesAndErrorBars(final CategoryPlot plot) {
        final LineAndShapeRenderer renderer = (plot.getDataset().getClass() == AsymmetricStatisticalCategoryDataset.class) ? new AsymmetricStatisticalLineAndShapeRenderer() : new LineAndShapeRenderer();
        plot.setRenderer(renderer);
    }

    /**
     * This method sets the width of the bars, and the spacing between the bars. It sets a general, hard coded standard for this. In JFreeChart, you
     * cannot set the bar width. In stead, you set the gaps: - lowerMargin is the gap between the start of the x-axis and the first bar - upperMargin
     * is the gap between the end of the x-axis and the last bar - categoryMargin is the gap between the categories. Note that the number you provide
     * here, is divided over all the gaps. So if you set this margin to 0.5 (is 50%) and there are 6 categories, this means that there are 5 gaps, so
     * each gap will get 10%. - itemMargin is the gap between the bars within a category. Again, this number is divided over all the itemgaps in the
     * whole graph. The method also takes care that extreme cases (with a very small amount of bars) still look acceptable.
     * @param plot
     */
    private void setMargins(final CategoryPlot plot) {
        final CategoryAxis axis = plot.getDomainAxis();
        axis.setTickLabelFont(new Font("SansSerif", Font.PLAIN, 9));
        final int categoryCount = plot.getCategories().size();
        final int seriesCount = plot.getDataset().getRowCount();
        axis.setCategoryMargin(0.23); // sets spacing between categories on x%
        // set spacing between bars inside a catgory (in fractions, so 1 = 100%)
        final BarRenderer renderer = (BarRenderer) plot.getRenderer();
        if (categoryCount == 1) {
            renderer.setItemMargin(0.15);
        } else {
            renderer.setItemMargin(0.03);
        }
        // extreme cases
        if (categoryCount * seriesCount < 4) {
            final double outerMargins = (4.0 - (categoryCount * seriesCount)) / 10.0;
            axis.setLowerMargin(outerMargins);
            axis.setUpperMargin(outerMargins);
        }
    }

    @SuppressWarnings("rawtypes")
    private void setMarkers(final CategoryPlot plot, final Map params) {
        final Marker[] domainMarkers = (Marker[]) params.get("domainMarkers");
        // this method may be extended for range markers in future.
        if (domainMarkers != null && domainMarkers.length > 0) {
            for (final Marker marker : domainMarkers) {
                final CategoryMarker cmarker = (CategoryMarker) marker;
                cmarker.setDrawAsLine(true);
                if (cmarker.getLabel() != null) {
                    cmarker.setLabelAnchor(RectangleAnchor.TOP_LEFT);
                    cmarker.setLabelTextAnchor(TextAnchor.TOP_RIGHT);
                }
                plot.addDomainMarker(cmarker);
            }
        }
    }

    /**
     * sets the section colors of a pie chart to the passed color range.
     * 
     * @param plot a <code>PiePlot</code>
     * @param aColorRange an array of Colors, where each consequetive color is assigned to the next pie section.
     */
    private void setPieColors(final PiePlot plot, final Color[] aColorRange) {
        for (int i = 0; i < aColorRange.length; i++) {
            plot.setSectionPaint(i, aColorRange[i]);
        }
    }

    /**
     * turns label generation for pie charts off.
     * 
     * @param plot a PiePlot plot.
     */
    private void setPieLabels(final PiePlot plot) {
        plot.setLabelGenerator(null);
    }

    /**
     * This method sets the x-axis labels in a rotated position. Use this only if there are a lot of bars in the graph, so there is very limited space
     * for eacht label on the x-axis. By rotating the labels, they wont overlap each other, and there will be more space per label. Set this in the
     * jsp via the param tag, using the keyword "xlabelRotation": <cewolf:chartpostprocessor id="chartPostProcessorImpl" > <cewolf:param
     * name="xlabelRotation" value="<%= new Double(0.60)%>"/> </cewolf:chartpostprocessor> The parameter is a double, indicating the rotation angle in
     * radials. <b>NOTE:</b> the rotation is set to 0.60 by default if the number of categories > 14; otherwise, the default rotation is 0. Setting
     * the parameter overrides this default.
     * @param plot
     * @param params
     */
    @SuppressWarnings("rawtypes")
    private void setRotatedXaxisLabels(final CategoryPlot plot, final Map params) {
        Double rotation = (Double) params.get("xlabelRotation");
        final int numberOfCategories = plot.getCategories().size();
        if (rotation == null && numberOfCategories > ROTATE_XLABELS_IF_MORE_THAN) {
            rotation = new Double(0.60);
        }
        if (rotation != null && rotation != 0) {
            final CategoryAxis axis = plot.getDomainAxis();
            axis.setCategoryLabelPositions(CategoryLabelPositions.createUpRotationLabelPositions(rotation.doubleValue()));
        }
    }

    /**
     * This method renders a subtitle above the graph, in case the subtitle string is not null or "". Set the subTitle in the jsp via the param tag,
     * using the keyword "subtitle": <cewolf:chartpostprocessor id="chartPostProcessorImpl" > <cewolf:param name="subtitle"
     * value="${dataTable.subTitle}"/> </cewolf:chartpostprocessor>
     * @param chart
     * @param params
     */
    @SuppressWarnings("rawtypes")
    private void setSubTitle(final Object chart, final Map params) {
        final String subtitleString = (String) params.get("subtitle");
        if (subtitleString != null && subtitleString.trim().length() > 0) {
            final TextTitle subtitle = new TextTitle(subtitleString);
            subtitle.setFont(subtitle.getFont().deriveFont(11f)); // TODO font size may be parameterized later on???
            ((JFreeChart) chart).addSubtitle(subtitle);
        }
    }

    /**
     * This method sets the font of the title to a smaller size
     * @param chart
     */
    private void setTitleFont(final Object chart) {
        final JFreeChart freeChart = (JFreeChart) chart;
        if (freeChart.getTitle() != null) {
            freeChart.getTitle().setFont(freeChart.getTitle().getFont().deriveFont(14f)); // TODO font size may be parameterized later on??
            // freeChart.getTitle().setPaint(new Color(60,60,60));
        }
    }

}
