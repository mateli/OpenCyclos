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

import nl.strohalm.cyclos.utils.conversion.CoercionHelper;

/**
 * Invokes compareTo, the three booleans in the constructor determines whether -1, 0 or 1 are accepted as valid
 * @author luis
 */
public class CompareToValidation implements PropertyValidation {

    private static final long serialVersionUID = 6393982502703438094L;

    /**
     * A greater or equals validation
     */
    public static CompareToValidation greaterEquals(final Comparable<?> comparable) {
        return new CompareToValidation(comparable, false, true, true, new ValidationError("errors.greaterEquals", comparable));
    }

    /**
     * A greater than validation
     */
    public static CompareToValidation greaterThan(final Comparable<?> comparable) {
        return new CompareToValidation(comparable, false, false, true, new ValidationError("errors.greaterThan", comparable));
    }

    /**
     * A less or equals validation
     */
    public static CompareToValidation lessEquals(final Comparable<?> comparable) {
        return new CompareToValidation(comparable, true, true, false, new ValidationError("errors.lessEquals", comparable));
    }

    /**
     * A less than validation
     */
    public static CompareToValidation lessThan(final Comparable<?> comparable) {
        return new CompareToValidation(comparable, true, false, false, new ValidationError("errors.lessThan", comparable));
    }

    private final Comparable<?> comparable;
    private final boolean       acceptLess;
    private final boolean       acceptEqual;
    private final boolean       acceptGreater;
    private ValidationError     error;

    public CompareToValidation(final Comparable<?> comparable, final boolean acceptLess, final boolean acceptEqual, final boolean acceptGreater, final ValidationError error) {
        this.comparable = comparable;
        this.acceptLess = acceptLess;
        this.acceptEqual = acceptEqual;
        this.acceptGreater = acceptGreater;
        this.error = error == null ? new InvalidError() : error;
    }

    @SuppressWarnings("unchecked")
    public ValidationError validate(final Object object, final Object name, final Object value) {
        if (comparable != null && value instanceof Comparable<?>) {
            final Object theComparable = CoercionHelper.coerce(value.getClass(), comparable);
            final int result = ((Comparable<Object>) value).compareTo(theComparable);
            boolean isError;
            if (result < 0) {
                isError = !acceptLess;
            } else if (result == 0) {
                isError = !acceptEqual;
            } else { // result > 0
                isError = !acceptGreater;
            }
            if (isError) {
                return error;
            }
        }
        return null;
    }

}
