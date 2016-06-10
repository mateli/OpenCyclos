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
package nl.strohalm.cyclos.utils.validation;

import org.apache.commons.lang.StringUtils;

public class NumericValidation implements PropertyValidation {

    private static NumericValidation INSTANCE         = new NumericValidation();
    private static final long        serialVersionUID = -5841372577792943157L;

    public static NumericValidation instance() {
        return INSTANCE;
    }

    /**
     * This class could be used as a class validator in the custom field edition, then it must contains a public constructor
     */
    public NumericValidation() {

    }

    /**
     * Validates that the value only contains numeric chars
     * @see nl.strohalm.cyclos.utils.validation.PropertyValidation#validate(java.lang.Object, java.lang.Object, java.lang.Object)
     */
    public ValidationError validate(final Object object, final Object property, final Object value) {
        if (value != null && !StringUtils.isNumeric((String) value)) {
            return new ValidationError("errors.numeric");
        } else {
            return null;
        }
    }

}
