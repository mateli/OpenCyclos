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

import java.util.Collection;
import java.util.Map;

import nl.strohalm.cyclos.utils.RangeConstraint;

/**
 * Validates a length constraint
 * @author luis
 */
public class LengthValidation implements PropertyValidation {

    private static final long     serialVersionUID = 6393982502703438094L;
    private final RangeConstraint constraint;

    public LengthValidation(final RangeConstraint constraint) {
        this.constraint = constraint;
    }

    public ValidationError validate(final Object object, final Object name, final Object value) {
        if (value == null || "".equals(value)) {
            return null;
        }
        int length = 0;
        if (value instanceof String) {
            length = ((String) value).length();
        } else if (value instanceof Collection<?>) {
            length = ((Collection<?>) value).size();
        } else if (value instanceof Map<?, ?>) {
            length = ((Map<?, ?>) value).size();
        } else if (value.getClass().isArray()) {
            length = ((Object[]) value).length;
        }
        return constraint.errorFor(length);
    }

}
