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

import nl.strohalm.cyclos.services.Service;
import nl.strohalm.cyclos.utils.IntValuedEnum;

/**
 * Service interface for common Statistics. This interface is extended for the different categories of statistics (key developments, activity, etc)
 * @author rinke
 */
public interface StatisticalService extends Service {

    /**
     * TableType, always comparing two periods in two different columns.
     * <ul>
     * <li>GROWTH: this table has a growth column as last (third) column, indicating the growth percentage from the second column to the first column
     * period.
     * <li>P: this table has a last (third) column indicating the p-value of the difference between column 1 and 2.
     * <li>GROWTH_AND_P: both previous mentioned columns are shown, growth as 3rd, p-value as 4th column
     * </ul>
     * @author rinke
     * 
     */
    public static enum TableType implements IntValuedEnum {
        GROWTH(1), P(1), GROWTH_AND_P(2);
        private final int value;

        private TableType(final int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    /**
     * Minimum number of data values required to generate statistics.
     * <p>
     * <b>Policy in cases of limited amount of data:</b><br>
     * <ul>
     * <li><B>Medians</b> will only be calculated if N >= this field.
     * <li><B>p-values</b> will only be calculated if <i>both</i> N's are bigger than or equal to this field (so: <code>AND</code>)
     * <li><b>complete graphs and tables</b> will not be shown if <i>all</i> N's are too small (smaller than this value).
     * <li><b>one point in the graph or table</b> will not be shown normally if N &lt; this field. In graphs, it will be shown as 0; in tables, it
     * will be shown as "-".
     * </ul>
     */
    public static final int    MINIMUM_NUMBER_OF_VALUES = 15;

    /**
     * The level on which is tested. This means that all p-values in statistics will be on this level, and all confidence intervals will be on the (1
     * - alpha) level. By default this is 0.05
     */
    public static final double ALPHA                    = 0.05;

    /**
     * sets the maximum number of datapoints which the user can request. A datapoint is one statistical calculation via sql. The default value is
     * 1000. Set this from the prepareForm method in the action, and only if you want another maximum than the default of 1000.
     * 
     * @param maximumDataPoints
     */
    public void setMaximumDataPoints(final int maximumDataPoints);

    public void validate(Object query);

}
