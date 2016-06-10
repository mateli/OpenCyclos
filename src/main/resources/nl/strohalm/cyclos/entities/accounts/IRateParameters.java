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
import java.util.Calendar;

import nl.strohalm.cyclos.utils.FormatObject;

/**
 * Parameters for configuration of the I-rate. Contains not much parameters, except for the creationValue (which is by definition 0), and enabling
 * date and disabling date.
 * @author rinke
 */
public class IRateParameters extends RateParameters {

    private static final long serialVersionUID = -6527552933018241467L;

    public IRateParameters() {
        super();
        setCreationValue(BigDecimal.ZERO);
    }

    @Override
    public void setCreationValue(final BigDecimal creationValue) {
        super.setCreationValue(BigDecimal.ZERO);
    }

    @Override
    public void setEnabledSince(final Calendar enabledSince) {
        super.setEnabledSince(enabledSince);
        super.setDate(enabledSince);
    }

    @Override
    public String toString() {
        final StringBuilder result = new StringBuilder();
        result.append(getId());
        result.append(", enabledSince ");
        result.append(FormatObject.formatObject(getEnabledSince()));
        return result.toString();
    }

}
