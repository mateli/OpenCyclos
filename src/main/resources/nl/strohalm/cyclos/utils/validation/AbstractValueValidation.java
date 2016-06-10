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

import java.util.Arrays;
import java.util.Collection;

import org.apache.commons.collections.CollectionUtils;

/**
 * Validates that the property value is one / none of the given values
 * @author luis
 */
public abstract class AbstractValueValidation implements PropertyValidation {

    private static final long   serialVersionUID = 6129247646968815793L;
    private final Collection<?> values;

    protected AbstractValueValidation(final Object... values) {
        this.values = Arrays.asList(values);
    }

    public ValidationError validate(final Object object, final Object name, final Object value) {
        if (value != null && !"".equals(value)) {
            boolean contains;
            if (value instanceof Collection<?>) {
                final Collection<?> collection = (Collection<?>) value;
                if (collection.isEmpty()) {
                    return null;
                }
                contains = CollectionUtils.containsAny(values, collection);
            } else {
                contains = values.contains(value);
            }
            if (contains != expected()) {
                return new InvalidError();
            }
        }
        return null;
    }

    protected abstract boolean expected();
}
