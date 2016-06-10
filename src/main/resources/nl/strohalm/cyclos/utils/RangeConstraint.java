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
package nl.strohalm.cyclos.utils;

import java.io.Serializable;

import nl.strohalm.cyclos.utils.validation.MaxLengthError;
import nl.strohalm.cyclos.utils.validation.MinLengthError;
import nl.strohalm.cyclos.utils.validation.ValidationError;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Contains a range constraint
 * @author luis
 */
public class RangeConstraint implements Serializable, Cloneable {

    private static final long serialVersionUID = -7227958658299214274L;

    /**
     * Returns a range from min to max
     */
    public static RangeConstraint between(final int min, final int max) {
        return new RangeConstraint(min, max);
    }

    /**
     * Returns a range from and to the parameter
     */
    public static RangeConstraint fixed(final int length) {
        return new RangeConstraint(length, length);
    }

    /**
     * Returns a range from min with no max
     */
    public static RangeConstraint from(final int min) {
        return new RangeConstraint(min, null);
    }

    /**
     * Returns a range from to max with no min
     */
    public static RangeConstraint to(final int max) {
        return new RangeConstraint(null, max);
    }

    private Integer max;
    private Integer min;

    public RangeConstraint() {
    }

    public RangeConstraint(final Integer min, final Integer max) {
        this.min = min;
        this.max = max;
    }

    @Override
    public RangeConstraint clone() {
        try {
            return (RangeConstraint) super.clone();
        } catch (final CloneNotSupportedException e) {
            return null;
        }
    }

    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof RangeConstraint)) {
            return false;
        }
        final RangeConstraint rc = (RangeConstraint) obj;
        return new EqualsBuilder().append(min, rc.min).append(max, rc.max).isEquals();
    }

    /**
     * Return a validation error if the number is out of range, or null if ok
     */
    public ValidationError errorFor(final int number) {
        if (min != null && min > 0 && number < min) {
            return new MinLengthError(min);
        }
        if (max != null && max > 0 && number > max) {
            return new MaxLengthError(max);
        }
        return null;
    }

    public Integer getMax() {
        return max;
    }

    public Integer getMin() {
        return min;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(min).append(max).toHashCode();
    }

    /**
     * Check if the given number is in range
     */
    public boolean inRange(final int number) {
        if (min != null && min < number) {
            return false;
        }
        if (max != null && max > number) {
            return false;
        }
        return true;
    }

    public void setMax(final Integer max) {
        this.max = max;
    }

    public void setMin(final Integer min) {
        this.min = min;
    }

    @Override
    public String toString() {
        return min + " - " + max;
    }
}
