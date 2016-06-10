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

import org.jfree.data.statistics.MeanAndStandardDeviation;
import org.jfree.util.ObjectUtilities;

/**
 * Helper class storing a mean with an asymmetric error bar for use with the JFree subclasses.
 * @author Rinke
 * 
 */
class MeanWithAsymmetricErrorBar extends MeanAndStandardDeviation {

    private static final long serialVersionUID = -130817025784571296L;

    private final Number      lower;

    private final Number      upper;

    MeanWithAsymmetricErrorBar(final double mean, final double lower, final double upper) {
        this(new Double(mean), new Double(lower), new Double(upper));
    }

    MeanWithAsymmetricErrorBar(final Number mean, final Number lower, final Number upper) {
        super(mean, null);
        this.lower = lower;
        this.upper = upper;
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
        if (!(obj instanceof MeanWithAsymmetricErrorBar)) {
            return false;
        }
        final MeanWithAsymmetricErrorBar that = (MeanWithAsymmetricErrorBar) obj;
        if (!ObjectUtilities.equal(getMean(), that.getMean())) {
            return false;
        }
        if (!ObjectUtilities.equal(lower, that.lower)) {
            return false;
        }
        if (!ObjectUtilities.equal(upper, that.upper)) {
            return false;
        }
        return true;
    }

    Number getLower() {
        return lower;
    }

    Number getUpper() {
        return upper;
    }

}
