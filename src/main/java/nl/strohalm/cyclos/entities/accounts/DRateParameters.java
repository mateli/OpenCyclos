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
package nl.strohalm.cyclos.entities.accounts;

import java.math.BigDecimal;

import nl.strohalm.cyclos.utils.BigDecimalHelper;
import nl.strohalm.cyclos.utils.FormatObject;

/**
 * The parameters needed for a currency when D-rate is enabled.
 * 
 * @author Rinke
 * 
 */
public class DRateParameters extends InitializableRateParameters {

    private static final long serialVersionUID = -1758988593868498202L;
    private BigDecimal        interest;
    private BigDecimal        baseMalus;
    private BigDecimal        minimalD;

    public BigDecimal getBaseMalus() {
        return baseMalus;
    }

    /**
     * returns 1 - M0 as a fraction. This term is very often used in the d-rate formulas. For example: if baseMalus is 3%, this method returns 0.97
     */
    public BigDecimal getBaseMalusTerm() {
        return BigDecimal.ONE.subtract(BigDecimalHelper.asPercentFraction(baseMalus));
    }

    public BigDecimal getInterest() {
        return interest;
    }

    public BigDecimal getMinimalD() {
        return minimalD;
    }

    public void setBaseMalus(final BigDecimal baseMalus) {
        this.baseMalus = baseMalus;
    }

    public void setInterest(final BigDecimal interest) {
        this.interest = interest;
    }

    public void setMinimalD(final BigDecimal minimalD) {
        this.minimalD = minimalD;
    }

    @Override
    public String toString() {
        final StringBuilder result = new StringBuilder();
        result.append(getId());
        result.append(" - interest = ");
        result.append(FormatObject.formatObject(getInterest()));
        result.append(", base malus = ");
        result.append(FormatObject.formatObject(getBaseMalus()));
        result.append(", minimal D = ");
        result.append(FormatObject.formatObject(getMinimalD()));
        result.append(", init value = ");
        result.append(FormatObject.formatObject(getInitValue()));
        return result.toString();
    }

}
