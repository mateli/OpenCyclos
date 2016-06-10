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
package nl.strohalm.cyclos.utils.conversion;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.Locale;

import nl.strohalm.cyclos.entities.Entity;
import nl.strohalm.cyclos.utils.ClassHelper;
import nl.strohalm.cyclos.utils.EntityHelper;
import nl.strohalm.cyclos.utils.IntValuedEnum;
import nl.strohalm.cyclos.utils.StringValuedEnum;

import org.apache.commons.beanutils.ConstructorUtils;
import org.apache.commons.collections.IteratorUtils;
import org.apache.commons.lang.ClassUtils;

/**
 * Class used to convert from a data type to another
 * @author luis
 */
public class CoercionHelper {

    /**
     * Converts the given value to the given type, using heuristics. The supported types are: primitives, arrays, Number, String, Boolean, Character,
     * Calendar, Date, Enum, Entity, Locale and Collection
     */
    @SuppressWarnings("unchecked")
    public static <T> T coerce(final Class<T> toType, final Object value) {
        try {
            return (T) convert(toType, value);
        } catch (final ConversionException e) {
            throw e;
        } catch (final Exception e) {
            throw new ConversionException("Cannot convert " + value + " to " + toType.getName(), e);
        }
    }

    /**
     * Converts an object to a collection of the given type, using the given element type
     * @return The resulting collection - it may be empty, but never null
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static <T> Collection<T> coerceCollection(final Class<T> elementType, final Class<? extends Collection> collectionType, final Object value) {
        final Collection<T> collection = ClassHelper.instantiate(collectionType == null ? ArrayList.class : collectionType);
        final Iterator<?> iterator = IteratorUtils.getIterator(value);
        while (iterator.hasNext()) {
            collection.add(coerce(elementType, iterator.next()));
        }
        return collection;
    }

    /**
     * Converts an object to a collection with the given element type.
     * @return The resulting collection - it may be empty, but never null
     */
    public static <T> Collection<T> coerceCollection(final Class<T> elementType, final Object value) {
        return coerceCollection(elementType, null, value);
    }

    /**
     * Returns the first array item, or null if an empty array
     */
    public static <T> T first(final T[] array) {
        if (array == null || array.length == 0) {
            return null;
        }
        return array[0];
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private static Object convert(Class toType, Object value) {
        if ("".equals(value)) {
            value = null;
        }
        // If we do not want a collection, but the value is one, use the first value
        if (value != null && !(Collection.class.isAssignableFrom(toType) || toType.isArray()) && (value.getClass().isArray() || value instanceof Collection)) {
            final Iterator it = IteratorUtils.getIterator(value);
            if (!it.hasNext()) {
                value = null;
            } else {
                value = it.next();
            }
        }

        // Check for null values
        if (value == null) {
            if (toType.isPrimitive()) {
                // On primitives, use the default value
                if (toType == Boolean.TYPE) {
                    value = Boolean.FALSE;
                } else if (toType == Character.TYPE) {
                    value = '\0';
                } else {
                    value = 0;
                }
            } else {
                // For objects, return null
                return null;
            }
        }

        // Check if the value is already of the expected type
        if (toType.isInstance(value)) {
            return value;
        }

        // If the class is primitive, use the wrapper, so we have an easier work of testing instances
        if (toType.isPrimitive()) {
            toType = ClassUtils.primitiveToWrapper(toType);
        }

        // Convert to well-known types
        if (String.class.isAssignableFrom(toType)) {
            if (value instanceof Entity) {
                final Long entityId = ((Entity) value).getId();
                return entityId == null ? null : entityId.toString();
            }
            return value.toString();
        } else if (Number.class.isAssignableFrom(toType)) {
            if (!(value instanceof Number)) {
                if (value instanceof String) {
                    value = new BigDecimal((String) value);
                } else if (value instanceof Date) {
                    value = ((Date) value).getTime();
                } else if (value instanceof Calendar) {
                    value = ((Calendar) value).getTimeInMillis();
                } else if (value instanceof Entity) {
                    value = ((Entity) value).getId();
                    if (value == null) {
                        return null;
                    }
                } else {
                    throw new ConversionException("Invalid number: " + value);
                }
            }
            final Number number = (Number) value;
            if (Byte.class.isAssignableFrom(toType)) {
                return number.byteValue();
            } else if (Short.class.isAssignableFrom(toType)) {
                return number.shortValue();
            } else if (Integer.class.isAssignableFrom(toType)) {
                return number.intValue();
            } else if (Long.class.isAssignableFrom(toType)) {
                return number.longValue();
            } else if (Float.class.isAssignableFrom(toType)) {
                return number.floatValue();
            } else if (Double.class.isAssignableFrom(toType)) {
                return number.doubleValue();
            } else if (BigInteger.class.isAssignableFrom(toType)) {
                return new BigInteger(number.toString());
            } else if (BigDecimal.class.isAssignableFrom(toType)) {
                return new BigDecimal(number.toString());
            }
        } else if (Boolean.class.isAssignableFrom(toType)) {
            if (value instanceof Number) {
                return ((Number) value).intValue() != 0;
            } else if ("on".equalsIgnoreCase(value.toString())) {
                return true;
            } else {
                return Boolean.parseBoolean(value.toString());
            }
        } else if (Character.class.isAssignableFrom(toType)) {
            final String str = value.toString();
            return (str.length() == 0) ? null : str.charAt(0);
        } else if (Calendar.class.isAssignableFrom(toType)) {
            if (value instanceof Date) {
                final Calendar cal = new GregorianCalendar();
                cal.setTime((Date) value);
                return cal;
            }
        } else if (Date.class.isAssignableFrom(toType)) {
            if (value instanceof Calendar) {
                final long millis = ((Calendar) value).getTimeInMillis();
                try {
                    return ConstructorUtils.invokeConstructor(toType, millis);
                } catch (final Exception e) {
                    throw new IllegalStateException(e);
                }
            }
        } else if (Enum.class.isAssignableFrom(toType)) {
            Object ret;
            try {
                ret = Enum.valueOf(toType, value.toString());
            } catch (final Exception e) {
                ret = null;
            }
            if (ret == null) {
                Object[] possible;
                try {
                    possible = (Object[]) toType.getMethod("values").invoke(null);
                } catch (final Exception e) {
                    throw new IllegalStateException("Couldn't invoke the 'values' method for enum " + toType.getName());
                }
                if (StringValuedEnum.class.isAssignableFrom(toType)) {
                    final String test = coerce(String.class, value);
                    for (final Object item : possible) {
                        if (((StringValuedEnum) item).getValue().equals(test)) {
                            ret = item;
                            break;
                        }
                    }
                } else if (IntValuedEnum.class.isAssignableFrom(toType)) {
                    final int test = coerce(Integer.TYPE, value);
                    for (final Object item : possible) {
                        if (((IntValuedEnum) item).getValue() == test) {
                            ret = item;
                            break;
                        }
                    }
                } else {
                    throw new ConversionException("Invalid enum: " + value);
                }
            }
            return ret;
        } else if (Entity.class.isAssignableFrom(toType)) {
            final Long id = coerce(Long.class, value);
            return EntityHelper.reference(toType, id);
        } else if (Locale.class.isAssignableFrom(toType)) {
            return LocaleConverter.instance().valueOf(value.toString());
        } else if (Collection.class.isAssignableFrom(toType)) {
            final Collection collection = (Collection) ClassHelper.instantiate(toType);
            final Iterator iterator = IteratorUtils.getIterator(value);
            while (iterator.hasNext()) {
                collection.add(iterator.next());
            }
            return collection;
        } else if (toType.isArray()) {
            final Collection collection = coerceCollection(toType.getComponentType(), value);
            final Object[] array = (Object[]) Array.newInstance(toType.getComponentType(), collection.size());
            return collection.toArray(array);
        }

        // We don't know how to convert the value
        throw new ConversionException("Cannot coerce value to: " + toType.getName());
    }
}
