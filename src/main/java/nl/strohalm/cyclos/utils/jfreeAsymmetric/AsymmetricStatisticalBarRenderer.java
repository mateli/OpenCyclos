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

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.entity.EntityCollection;
import org.jfree.chart.event.RendererChangeEvent;
import org.jfree.chart.labels.CategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.CategoryItemRendererState;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.statistics.StatisticalCategoryDataset;
import org.jfree.io.SerialUtilities;
import org.jfree.ui.RectangleEdge;
import org.jfree.util.PaintUtilities;
import org.jfree.util.PublicCloneable;

/**
 * A StatisticalBarRenderer which can display error bars inside a normal bar, which are asymmetric (that is: the lower and upper half of the bar need
 * not be equal). Can be used in stead of the JFreeChart statisticalBarRenderer. Is an alternative for StatisticalBarRenderer of JFreeChart; it
 * subclasses the parent of StatisticalBarRenderer, alters the needed code, and copies the rest of the code.
 * 
 * @author Rinke
 * 
 */
public class AsymmetricStatisticalBarRenderer extends BarRenderer implements CategoryItemRenderer, Cloneable, PublicCloneable, Serializable {

    private static final long serialVersionUID = 193574839451876016L;

    // *************** ALTERED METHODS (COMPARED TO STATISTICALBARRENDERER) ****************
    // ***********************************************************************************

    /** The paint used to show the error indicator. */
    private transient Paint   errorIndicatorPaint;

    /**
     * Default constructor.
     */
    public AsymmetricStatisticalBarRenderer() {
        super();
        errorIndicatorPaint = Color.gray;
    }

    /**
     * Draws the bar with its standard deviation line range for a single (series, category) data item.
     * 
     * @param g2 the graphics device.
     * @param state the renderer state.
     * @param dataArea the data area.
     * @param plot the plot.
     * @param domainAxis the domain axis.
     * @param rangeAxis the range axis.
     * @param data the data.
     * @param row the row index (zero-based).
     * @param column the column index (zero-based).
     * @param pass the pass index.
     */
    @Override
    public void drawItem(final Graphics2D g2, final CategoryItemRendererState state, final Rectangle2D dataArea, final CategoryPlot plot, final CategoryAxis domainAxis, final ValueAxis rangeAxis, final CategoryDataset data, final int row, final int column, final int pass) {

        // defensive check
        if (!(data instanceof StatisticalCategoryDataset)) {
            throw new IllegalArgumentException("Requires StatisticalCategoryDataset.");
        }
        final StatisticalCategoryDataset statData = (StatisticalCategoryDataset) data;

        final PlotOrientation orientation = plot.getOrientation();
        if (orientation == PlotOrientation.HORIZONTAL) {
            drawHorizontalItem(g2, state, dataArea, plot, domainAxis, rangeAxis, statData, row, column);
        } else if (orientation == PlotOrientation.VERTICAL) {
            drawVerticalItem(g2, state, dataArea, plot, domainAxis, rangeAxis, statData, row, column);
        }
    }

    // ****************** COPIED CODE FROM StatisticalBarRenderer *******************************
    // *****************************************************************************************

    /**
     * Tests this renderer for equality with an arbitrary object.
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
        if (!(obj instanceof AsymmetricStatisticalBarRenderer)) {
            return false;
        }
        if (!super.equals(obj)) {
            return false;
        }
        final AsymmetricStatisticalBarRenderer that = (AsymmetricStatisticalBarRenderer) obj;
        if (!PaintUtilities.equal(getErrorIndicatorPaint(), that.getErrorIndicatorPaint())) {
            return false;
        }
        return true;
    }

    /**
     * Returns the paint used for the error indicators.
     * 
     * @return The paint used for the error indicators (possibly <code>null</code>).
     */
    public Paint getErrorIndicatorPaint() {
        return errorIndicatorPaint;
    }

    /**
     * Sets the paint used for the error indicators (if <code>null</code>, the item outline paint is used instead)
     * 
     * @param paint the paint (<code>null</code> permitted).
     */
    public void setErrorIndicatorPaint(final Paint paint) {
        errorIndicatorPaint = paint;
        notifyListeners(new RendererChangeEvent(this));
    }

    /**
     * Draws an item for a plot with a horizontal orientation.
     * 
     * @param g2 the graphics device.
     * @param state the renderer state.
     * @param dataArea the data area.
     * @param plot the plot.
     * @param domainAxis the domain axis.
     * @param rangeAxis the range axis.
     * @param dataset the data.
     * @param row the row index (zero-based).
     * @param column the column index (zero-based).
     */
    protected void drawHorizontalItem(final Graphics2D g2, final CategoryItemRendererState state, final Rectangle2D dataArea, final CategoryPlot plot, final CategoryAxis domainAxis, final ValueAxis rangeAxis, final StatisticalCategoryDataset dataset, final int row, final int column) {
        // nothing is drawn for null... //added this test block, Rinke
        final Number v = dataset.getValue(row, column);
        if (v == null) {
            return;
        }

        final RectangleEdge xAxisLocation = plot.getDomainAxisEdge();

        // BAR Y
        double rectY = domainAxis.getCategoryStart(column, getColumnCount(), dataArea, xAxisLocation);

        final int seriesCount = getRowCount();
        final int categoryCount = getColumnCount();
        if (seriesCount > 1) {
            final double seriesGap = dataArea.getHeight() * getItemMargin() / (categoryCount * (seriesCount - 1));
            rectY = rectY + row * (state.getBarWidth() + seriesGap);
        } else {
            rectY = rectY + row * state.getBarWidth();
        }

        // BAR X
        final Number meanValue = dataset.getMeanValue(row, column);

        double value = meanValue.doubleValue();
        double base = 0.0;
        final double lclip = getLowerClip();
        final double uclip = getUpperClip();

        if (uclip <= 0.0) { // cases 1, 2, 3 and 4
            if (value >= uclip) {
                return; // bar is not visible
            }
            base = uclip;
            if (value <= lclip) {
                value = lclip;
            }
        } else if (lclip <= 0.0) { // cases 5, 6, 7 and 8
            if (value >= uclip) {
                value = uclip;
            } else {
                if (value <= lclip) {
                    value = lclip;
                }
            }
        } else { // cases 9, 10, 11 and 12
            if (value <= lclip) {
                return; // bar is not visible
            }
            base = getLowerClip();
            if (value >= uclip) {
                value = uclip;
            }
        }

        final RectangleEdge yAxisLocation = plot.getRangeAxisEdge();
        final double transY1 = rangeAxis.valueToJava2D(base, dataArea, yAxisLocation);
        final double transY2 = rangeAxis.valueToJava2D(value, dataArea, yAxisLocation);
        final double rectX = Math.min(transY2, transY1);

        final double rectHeight = state.getBarWidth();
        final double rectWidth = Math.abs(transY2 - transY1);

        final Rectangle2D bar = new Rectangle2D.Double(rectX, rectY, rectWidth, rectHeight);
        final Paint seriesPaint = getItemPaint(row, column);
        g2.setPaint(seriesPaint);
        g2.fill(bar);
        if (state.getBarWidth() > 3) {
            g2.setStroke(getItemStroke(row, column));
            g2.setPaint(getItemOutlinePaint(row, column));
            g2.draw(bar);
        }
        // ********** BLOCK WITH CHANGES RELATIVE TO StatisticalBarRenderere *********************
        // standard deviation lines
        final AsymmetricStatisticalCategoryDataset asymmDataset = (AsymmetricStatisticalCategoryDataset) dataset;
        final Number highValRaw = asymmDataset.getUpperValue(row, column);
        final Number lowValRaw = asymmDataset.getLowerValue(row, column);
        if (highValRaw != null && lowValRaw != null) { // ADDED THIS IF, RINKE
            final double highVal = rangeAxis.valueToJava2D(highValRaw.doubleValue(), dataArea, yAxisLocation);
            final double lowVal = rangeAxis.valueToJava2D(lowValRaw.doubleValue(), dataArea, yAxisLocation);
            // *************************** end of block with changes ******************************

            if (getErrorIndicatorPaint() != null) {
                g2.setPaint(getErrorIndicatorPaint());
            } else {
                g2.setPaint(getItemOutlinePaint(row, column));
            }
            Line2D line = null;
            line = new Line2D.Double(lowVal, rectY + rectHeight / 2.0d, highVal, rectY + rectHeight / 2.0d);
            g2.draw(line);
            line = new Line2D.Double(highVal, rectY + rectHeight * 0.25, highVal, rectY + rectHeight * 0.75);
            g2.draw(line);
            line = new Line2D.Double(lowVal, rectY + rectHeight * 0.25, lowVal, rectY + rectHeight * 0.75);
            g2.draw(line);
        }

        final CategoryItemLabelGenerator generator = getItemLabelGenerator(row, column);
        if (generator != null && isItemLabelVisible(row, column)) {
            drawItemLabel(g2, dataset, row, column, plot, generator, bar, (value < 0.0));
        }

        // add an item entity, if this information is being collected
        final EntityCollection entities = state.getEntityCollection();
        if (entities != null) {
            addItemEntity(entities, dataset, row, column, bar);
        }

    }

    /**
     * Draws an item for a plot with a vertical orientation.
     * 
     * @param g2 the graphics device.
     * @param state the renderer state.
     * @param dataArea the data area.
     * @param plot the plot.
     * @param domainAxis the domain axis.
     * @param rangeAxis the range axis.
     * @param dataset the data.
     * @param row the row index (zero-based).
     * @param column the column index (zero-based).
     */
    protected void drawVerticalItem(final Graphics2D g2, final CategoryItemRendererState state, final Rectangle2D dataArea, final CategoryPlot plot, final CategoryAxis domainAxis, final ValueAxis rangeAxis, final StatisticalCategoryDataset dataset, final int row, final int column) {
        // nothing is drawn for null... //ADDED THIS BLOCK, RINKE
        final Number v = dataset.getValue(row, column);
        if (v == null) {
            return;
        }

        final RectangleEdge xAxisLocation = plot.getDomainAxisEdge();

        // BAR X
        double rectX = domainAxis.getCategoryStart(column, getColumnCount(), dataArea, xAxisLocation);

        final int seriesCount = getRowCount();
        final int categoryCount = getColumnCount();
        if (seriesCount > 1) {
            final double seriesGap = dataArea.getWidth() * getItemMargin() / (categoryCount * (seriesCount - 1));
            rectX = rectX + row * (state.getBarWidth() + seriesGap);
        } else {
            rectX = rectX + row * state.getBarWidth();
        }

        // BAR Y
        final Number meanValue = dataset.getMeanValue(row, column);

        double value = meanValue.doubleValue();
        double base = 0.0;
        final double lclip = getLowerClip();
        final double uclip = getUpperClip();

        if (uclip <= 0.0) { // cases 1, 2, 3 and 4
            if (value >= uclip) {
                return; // bar is not visible
            }
            base = uclip;
            if (value <= lclip) {
                value = lclip;
            }
        } else if (lclip <= 0.0) { // cases 5, 6, 7 and 8
            if (value >= uclip) {
                value = uclip;
            } else {
                if (value <= lclip) {
                    value = lclip;
                }
            }
        } else { // cases 9, 10, 11 and 12
            if (value <= lclip) {
                return; // bar is not visible
            }
            base = getLowerClip();
            if (value >= uclip) {
                value = uclip;
            }
        }

        final RectangleEdge yAxisLocation = plot.getRangeAxisEdge();
        final double transY1 = rangeAxis.valueToJava2D(base, dataArea, yAxisLocation);
        final double transY2 = rangeAxis.valueToJava2D(value, dataArea, yAxisLocation);
        final double rectY = Math.min(transY2, transY1);

        final double rectWidth = state.getBarWidth();
        final double rectHeight = Math.abs(transY2 - transY1);

        final Rectangle2D bar = new Rectangle2D.Double(rectX, rectY, rectWidth, rectHeight);
        final Paint seriesPaint = getItemPaint(row, column);
        g2.setPaint(seriesPaint);
        g2.fill(bar);
        if (state.getBarWidth() > 3) {
            g2.setStroke(getItemStroke(row, column));
            g2.setPaint(getItemOutlinePaint(row, column));
            g2.draw(bar);
        }

        // standard deviation lines
        final AsymmetricStatisticalCategoryDataset asymmDataset = (AsymmetricStatisticalCategoryDataset) dataset;
        final Number highValRaw = asymmDataset.getUpperValue(row, column);
        final Number lowValRaw = asymmDataset.getLowerValue(row, column);
        // only draw if both error bars items are not null
        if (highValRaw != null && lowValRaw != null) { // ADDED THIS IF, RINKE
            final double highVal = rangeAxis.valueToJava2D(highValRaw.doubleValue(), dataArea, yAxisLocation);
            final double lowVal = rangeAxis.valueToJava2D(lowValRaw.doubleValue(), dataArea, yAxisLocation);

            if (getErrorIndicatorPaint() != null) {
                g2.setPaint(getErrorIndicatorPaint());
            } else {
                g2.setPaint(getItemOutlinePaint(row, column));
            }
            Line2D line = null;
            line = new Line2D.Double(rectX + rectWidth / 2.0d, lowVal, rectX + rectWidth / 2.0d, highVal);
            g2.draw(line);
            line = new Line2D.Double(rectX + rectWidth / 2.0d - 5.0d, highVal, rectX + rectWidth / 2.0d + 5.0d, highVal);
            g2.draw(line);
            line = new Line2D.Double(rectX + rectWidth / 2.0d - 5.0d, lowVal, rectX + rectWidth / 2.0d + 5.0d, lowVal);
            g2.draw(line);
        }

        final CategoryItemLabelGenerator generator = getItemLabelGenerator(row, column);
        if (generator != null && isItemLabelVisible(row, column)) {
            drawItemLabel(g2, dataset, row, column, plot, generator, bar, (value < 0.0));
        }

        // add an item entity, if this information is being collected
        final EntityCollection entities = state.getEntityCollection();
        if (entities != null) {
            addItemEntity(entities, dataset, row, column, bar);
        }
    }

    /**
     * Provides serialization support.
     * 
     * @param stream the input stream.
     * 
     * @throws IOException if there is an I/O error.
     * @throws ClassNotFoundException if there is a classpath problem.
     */
    private void readObject(final ObjectInputStream stream) throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
        errorIndicatorPaint = SerialUtilities.readPaint(stream);
    }

    /**
     * Provides serialization support.
     * 
     * @param stream the output stream.
     * 
     * @throws IOException if there is an I/O error.
     */
    private void writeObject(final ObjectOutputStream stream) throws IOException {
        stream.defaultWriteObject();
        SerialUtilities.writePaint(errorIndicatorPaint, stream);
    }

}
