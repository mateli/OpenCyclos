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

import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.entity.CategoryItemEntity;
import org.jfree.chart.entity.EntityCollection;
import org.jfree.chart.event.RendererChangeEvent;
import org.jfree.chart.labels.CategoryToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.CategoryItemRendererState;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.statistics.StatisticalCategoryDataset;
import org.jfree.io.SerialUtilities;
import org.jfree.ui.RectangleEdge;
import org.jfree.util.PaintUtilities;
import org.jfree.util.PublicCloneable;
import org.jfree.util.ShapeUtilities;

/**
 * An alternative for StatisticalLineAndShapeRenderer from JFreeChart. Where the JFree classes can only handle symmetrical error bars, this one can
 * handle error bars which are assymmetrical. The class is used for drawing error bars in line graphs. For error bars in bar graphs, use
 * AsymmetricStatisticalBarRenderer.
 * 
 * @author Usuario
 * 
 */
public class AsymmetricStatisticalLineAndShapeRenderer extends LineAndShapeRenderer implements Cloneable, PublicCloneable, Serializable {

    private static final long serialVersionUID = 464408755825675305L;

    /** The paint used to show the error indicator. */
    private transient Paint   errorIndicatorPaint;

    // *************** Changed items, compared to StatisticalLineAndShapeRenderer ************
    // ***********************************************************************************
    public AsymmetricStatisticalLineAndShapeRenderer() {
        super();
    }

    public AsymmetricStatisticalLineAndShapeRenderer(final boolean lines, final boolean shapes) {
        super(lines, shapes);
    }

    /**
     * Draw a single data item.
     * 
     * @param g2 the graphics device.
     * @param state the renderer state.
     * @param dataArea the area in which the data is drawn.
     * @param plot the plot.
     * @param domainAxis the domain axis.
     * @param rangeAxis the range axis.
     * @param dataset the dataset (a {@link StatisticalCategoryDataset} is required).
     * @param row the row index (zero-based).
     * @param column the column index (zero-based).
     * @param pass the pass.
     */
    @Override
    public void drawItem(final Graphics2D g2, final CategoryItemRendererState state, final Rectangle2D dataArea, final CategoryPlot plot, final CategoryAxis domainAxis, final ValueAxis rangeAxis, final CategoryDataset dataset, final int row, final int column, final int pass) {

        // nothing is drawn for null...
        final Number v = dataset.getValue(row, column);
        if (v == null) {
            return;
        }
        // *************** This line was changed relative to StatisticalLineAndShapeRenderer*****
        final AsymmetricStatisticalCategoryDataset statData = (AsymmetricStatisticalCategoryDataset) dataset;
        // *************** end of changed line **********************************************

        final Number meanValue = statData.getMeanValue(row, column);

        final PlotOrientation orientation = plot.getOrientation();

        // current data point...
        final double x1 = domainAxis.getCategoryMiddle(column, getColumnCount(), dataArea, plot.getDomainAxisEdge());

        final double y1 = rangeAxis.valueToJava2D(meanValue.doubleValue(), dataArea, plot.getRangeAxisEdge());

        Shape shape = getItemShape(row, column);
        if (orientation == PlotOrientation.HORIZONTAL) {
            shape = ShapeUtilities.createTranslatedShape(shape, y1, x1);
        } else if (orientation == PlotOrientation.VERTICAL) {
            shape = ShapeUtilities.createTranslatedShape(shape, x1, y1);
        }
        if (getItemShapeVisible(row, column)) {

            if (getItemShapeFilled(row, column)) {
                g2.setPaint(getItemPaint(row, column));
                g2.fill(shape);
            } else {
                if (getUseOutlinePaint()) {
                    g2.setPaint(getItemOutlinePaint(row, column));
                } else {
                    g2.setPaint(getItemPaint(row, column));
                }
                g2.setStroke(getItemOutlineStroke(row, column));
                g2.draw(shape);
            }
        }

        if (getItemLineVisible(row, column)) {
            if (column != 0) {

                final Number previousValue = statData.getValue(row, column - 1);
                if (previousValue != null) {

                    // previous data point...
                    final double previous = previousValue.doubleValue();
                    final double x0 = domainAxis.getCategoryMiddle(column - 1, getColumnCount(), dataArea, plot.getDomainAxisEdge());
                    final double y0 = rangeAxis.valueToJava2D(previous, dataArea, plot.getRangeAxisEdge());

                    Line2D line = null;
                    if (orientation == PlotOrientation.HORIZONTAL) {
                        line = new Line2D.Double(y0, x0, y1, x1);
                    } else if (orientation == PlotOrientation.VERTICAL) {
                        line = new Line2D.Double(x0, y0, x1, y1);
                    }
                    g2.setPaint(getItemPaint(row, column));
                    g2.setStroke(getItemStroke(row, column));
                    g2.draw(line);
                }
            }
        }

        final RectangleEdge yAxisLocation = plot.getRangeAxisEdge();
        final RectangleEdge xAxisLocation = plot.getDomainAxisEdge();
        double rectX = domainAxis.getCategoryStart(column, getColumnCount(), dataArea, xAxisLocation);

        rectX = rectX + row * state.getBarWidth();

        g2.setPaint(getItemPaint(row, column));
        // ************* This is the block with changes relative to StatisticalLineAndShapeRenderer *********
        // standard deviation lines
        final Number highValObj = statData.getUpperValue(row, column);
        final Number lowValObj = statData.getLowerValue(row, column);

        if (highValObj != null && lowValObj != null) { // rinke added this test
            double highVal = highValObj.doubleValue();
            double lowVal = lowValObj.doubleValue();
            if (highVal > rangeAxis.getRange().getUpperBound()) {
                highVal = rangeAxis.valueToJava2D(rangeAxis.getRange().getUpperBound(), dataArea, yAxisLocation);
            } else {
                highVal = rangeAxis.valueToJava2D(highVal, dataArea, yAxisLocation);
            }

            if (lowVal < rangeAxis.getRange().getLowerBound()) {
                lowVal = rangeAxis.valueToJava2D(rangeAxis.getRange().getLowerBound(), dataArea, yAxisLocation);
            } else {
                lowVal = rangeAxis.valueToJava2D(lowVal, dataArea, yAxisLocation);
            }
            // ****************** end of changed block **********************************

            if (errorIndicatorPaint != null) {
                g2.setPaint(errorIndicatorPaint);
            } else {
                g2.setPaint(getItemPaint(row, column));
            }
            final Line2D line = new Line2D.Double();
            if (orientation == PlotOrientation.HORIZONTAL) {
                line.setLine(lowVal, x1, highVal, x1);
                g2.draw(line);
                line.setLine(lowVal, x1 - 5.0d, lowVal, x1 + 5.0d);
                g2.draw(line);
                line.setLine(highVal, x1 - 5.0d, highVal, x1 + 5.0d);
                g2.draw(line);
            } else { // PlotOrientation.VERTICAL
                line.setLine(x1, lowVal, x1, highVal);
                g2.draw(line);
                line.setLine(x1 - 5.0d, highVal, x1 + 5.0d, highVal);
                g2.draw(line);
                line.setLine(x1 - 5.0d, lowVal, x1 + 5.0d, lowVal);
                g2.draw(line);
            }

        }

        // draw the item label if there is one...
        if (isItemLabelVisible(row, column)) {
            if (orientation == PlotOrientation.HORIZONTAL) {
                drawItemLabel(g2, orientation, dataset, row, column, y1, x1, (meanValue.doubleValue() < 0.0));
            } else if (orientation == PlotOrientation.VERTICAL) {
                drawItemLabel(g2, orientation, dataset, row, column, x1, y1, (meanValue.doubleValue() < 0.0));
            }
        }

        // collect entity and tool tip information...
        if (state.getInfo() != null) {
            final EntityCollection entities = state.getEntityCollection();
            if (entities != null && shape != null) {
                String tip = null;
                final CategoryToolTipGenerator tipster = getToolTipGenerator(row, column);
                if (tipster != null) {
                    tip = tipster.generateToolTip(dataset, row, column);
                }
                String url = null;
                if (getItemURLGenerator(row, column) != null) {
                    url = getItemURLGenerator(row, column).generateURL(dataset, row, column);
                }
                final CategoryItemEntity entity = new CategoryItemEntity(shape, tip, url, dataset, row, dataset.getColumnKey(column), column);
                entities.add(entity);

            }

        }

    }

    // ******************* copied items from StatisticalLineAndShapeRenderer **************
    // *******************************************************************************

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
        if (!(obj instanceof AsymmetricStatisticalLineAndShapeRenderer)) {
            return false;
        }
        if (!super.equals(obj)) {
            return false;
        }
        final AsymmetricStatisticalLineAndShapeRenderer that = (AsymmetricStatisticalLineAndShapeRenderer) obj;
        if (!PaintUtilities.equal(errorIndicatorPaint, that.errorIndicatorPaint)) {
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
