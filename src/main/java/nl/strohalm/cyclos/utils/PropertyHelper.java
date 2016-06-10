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

import java.beans.PropertyDescriptor;

import nl.strohalm.cyclos.utils.binding.PropertyException;
import nl.strohalm.cyclos.utils.conversion.CoercionHelper;
import nl.strohalm.cyclos.utils.conversion.Converter;

import org.apache.commons.beanutils.NestedNullException;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.NullValueInNestedPathException;

/**
 * Helper class for getting / setting properties by reflection
 * @author luis
 */
public class PropertyHelper {

    /**
     * Copies all possible properties from source to dest, ignoring the given properties list. Exceptions are ignored
     */
    public static void copyProperties(final Object source, final Object dest, final String... ignored) {
        if (source == null || dest == null) {
            return;
        }
        final PropertyDescriptor[] propertyDescriptors = PropertyUtils.getPropertyDescriptors(source);
        for (final PropertyDescriptor sourceDescriptor : propertyDescriptors) {
            try {
                final String name = sourceDescriptor.getName();
                // Check for ignored properties
                if (ArrayUtils.contains(ignored, name)) {
                    continue;
                }
                final PropertyDescriptor destProperty = PropertyUtils.getPropertyDescriptor(dest, name);
                if (destProperty.getWriteMethod() == null) {
                    // Ignore read-only properties
                    continue;
                }
                final Object value = CoercionHelper.coerce(destProperty.getPropertyType(), get(source, name));
                set(dest, name, value);
            } catch (final Exception e) {
                // Ignore this property
            }
        }
    }

    /**
     * Returns the first property on a nested path. Ex: a.b.c => a
     */
    public static String firstProperty(final String path) {
        if (path == null) {
            return null;
        }
        final int pos = path.indexOf('.');
        return pos < 0 ? path : path.substring(0, pos);
    }

    /**
     * Returns a property value by reflection, handling nested nulls
     */
    @SuppressWarnings("unchecked")
    public static <T> T get(final Object object, final String property) {
        if (object == null) {
            return null;
        }
        if (property == null) {
            return (T) object;
        }
        try {
            return (T) PropertyUtils.getProperty(object, property);
        } catch (final NestedNullException e) {
            // Ignore... we just had a null
            return null;
        } catch (final NullValueInNestedPathException e) {
            // Ignore... we just had a null
            return null;
        } catch (final Exception e) {
            throw new PropertyException(object, property, e);
        }
    }

    /**
     * Converts a String into an Object with the given converter
     */
    public static <T> T getAsObject(final Class<T> toType, final String value, final Converter<T> converter) {
        if (StringUtils.isEmpty(value) && !toType.isPrimitive()) {
            return null;
        }
        Object ret = null;
        if (converter != null) {
            ret = converter.valueOf(value);
        } else {
            ret = value;
        }
        return CoercionHelper.coerce(toType, ret);
    }

    /**
     * Converts a value to String with the given converter
     */
    public static <T> String getAsString(final T value, final Converter<T> converter) {
        if (value == null) {
            return null;
        }
        Object ret = null;
        if (converter != null) {
            ret = converter.toString(value);
        } else {
            ret = value;
        }
        return CoercionHelper.coerce(String.class, ret);
    }

    /**
     * Returns the last property on a path. Ex: a.b.c => c
     */
    public static String lastProperty(final String path) {
        if (path == null) {
            return null;
        }
        final int pos = path.lastIndexOf('.');
        return pos < 0 ? null : path.substring(pos + 1);
    }

    /**
     * Returns the nested path after the first property. Ex: a.b.c => b.c
     */
    public static String nestedPath(final String path) {
        if (path == null) {
            return null;
        }
        final int pos = path.indexOf('.');
        return pos < 0 ? null : path.substring(pos + 1);
    }

    /**
     * Returns the nested path after until the last property. Ex: a.b.c => a.b
     */
    public static String pathUntilLast(final String path) {
        if (path == null) {
            return null;
        }
        final int pos = path.lastIndexOf('.');
        return pos < 0 ? null : path.substring(0, pos);
    }

    /**
     * Sets a property value by reflection, handling nested nulls
     */
    public static void set(final Object object, final String property, final Object value) {
        if (object == null) {
            return;
        }
        try {
            PropertyUtils.setProperty(object, property, value);
        } catch (final NullValueInNestedPathException e) {
            // Ignore... we just had a null
        } catch (final Exception e) {
            throw new PropertyException(object, property, e);
        }
    }
}
