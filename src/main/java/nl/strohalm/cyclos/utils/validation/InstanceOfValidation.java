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
import org.springframework.beans.factory.DisposableBean;

/**
 * A validation for a property which should be a class name assignable to the given class
 * 
 * @author luis
 */
public class InstanceOfValidation implements PropertyValidation {

    private static final long serialVersionUID = 953633627172013154L;
    private final Class<?>    expectedType;

    public InstanceOfValidation(final Class<?> expectedType) {
        this.expectedType = expectedType;
    }

    @Override
    public ValidationError validate(final Object object, final Object property, final Object value) {
        final String className = (String) value;
        if (StringUtils.isEmpty(className)) {
            return null;
        }
        try {
            final Class<?> type = Class.forName(className);
            if (!expectedType.isAssignableFrom(type)) {
                throw new Exception();
            }
            // Attempt to instantiate
            final Object instance = type.newInstance();
            // Make sure to dispose it if needed
            if (instance instanceof DisposableBean) {
                try {
                    ((DisposableBean) instance).destroy();
                } catch (final Exception e) {
                    // Ignore
                }
            }
            return null;
        } catch (final Exception e) {
            return new ValidationError("errors.javaClass", expectedType.getName());
        }
    }

}
