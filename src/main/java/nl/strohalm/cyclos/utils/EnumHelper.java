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

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;

/**
 * Helper class for enumerations
 * @author luis
 */
public final class EnumHelper {

    /**
     * Capitalizes an enum item name
     */
    public static String capitalizeName(final Enum<?> item) {
        final String capitalized = StringUtils.replace(WordUtils.capitalizeFully(item.name(), new char[] { '_' }), "_", "");
        return Character.toLowerCase(capitalized.charAt(0)) + capitalized.substring(1);
    }

    /**
     * Finds a enum item by string value
     */
    public static <E extends Enum<?> & StringValuedEnum> E findByValue(final Class<E> enumType, final String value) {
        for (final E e : values(enumType)) {
            if (ObjectUtils.equals(value, ((StringValuedEnum) e).getValue())) {
                return e;
            }
        }
        return null;
    }

    /**
     * Return an array with all values on a given enum type
     */
    @SuppressWarnings("unchecked")
    public static <E extends Enum<?>> E[] values(final Class<E> enumType) {
        try {
            return (E[]) getValuesMethod(enumType).invoke(null);
        } catch (final Exception e) {
            throw new IllegalStateException("Couldn't invoke values method on " + enumType + " (error: " + e.getMessage() + ")");
        }
    }

    /**
     * Return a map with instances of the enum by name
     */
    public static <E extends Enum<?> & IntValuedEnum> Map<Integer, E> valuesByIntValue(final Class<E> enumType) {
        final Map<Integer, E> map = new LinkedHashMap<Integer, E>();
        for (final E e : values(enumType)) {
            map.put(e.getValue(), e);
        }
        return map;
    }

    /**
     * Return a map with instances of the enum by name
     */
    public static <E extends Enum<?>> Map<String, E> valuesByName(final Class<E> enumType) {
        final Map<String, E> map = new LinkedHashMap<String, E>();
        for (final E e : values(enumType)) {
            map.put(e.name(), e);
        }
        return map;
    }

    /**
     * Return a map with instances of the enum by name
     */
    public static <E extends Enum<?>> Map<Integer, E> valuesByOrdinal(final Class<E> enumType) {
        final Map<Integer, E> map = new LinkedHashMap<Integer, E>();
        for (final E e : values(enumType)) {
            map.put(e.ordinal(), e);
        }
        return map;
    }

    /**
     * Return a map with instances of the enum by name
     */
    public static <E extends Enum<?> & StringValuedEnum> Map<String, E> valuesByStringValue(final Class<E> enumType) {
        final Map<String, E> map = new LinkedHashMap<String, E>();
        for (final E e : values(enumType)) {
            map.put(e.getValue(), e);
        }
        return map;
    }

    /**
     * Return the public static values method
     */
    private static <E extends Enum<?>> Method getValuesMethod(final Class<E> enumType) {
        try {
            return enumType.getMethod("values");
        } catch (final Exception e) {
            throw new IllegalStateException("No values method on " + enumType);
        }
    }
}
