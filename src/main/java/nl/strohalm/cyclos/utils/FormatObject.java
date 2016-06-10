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
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import nl.strohalm.cyclos.entities.Entity;
import nl.strohalm.cyclos.entities.EntityReference;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.IteratorUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.hibernate.Hibernate;

/**
 * Helper class used to format general objects
 * @author luis
 */
public class FormatObject {

    public static final String[]      DEFAULT_EXCLUDES = { "password", "transactionPassword", "pin", "credential", "securityCode" };
    public static final String        MASKED_VALUE     = "***";
    private static final NumberFormat INTEGER_FORMAT;
    private static final NumberFormat FLOAT_FORMAT;
    private static final DateFormat   DATE_FORMAT;
    private static final DateFormat   DATE_TIME_FORMAT;

    static {
        INTEGER_FORMAT = new DecimalFormat("#,##0", new DecimalFormatSymbols(Locale.US));
        FLOAT_FORMAT = new DecimalFormat("#,##0.00", new DecimalFormatSymbols(Locale.US));
        DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
        DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    /**
     * Formats a method argument
     */
    public static String formatArgument(final Object argument) {
        final boolean quote = (argument instanceof String) || (argument instanceof Entity);
        final String str = StringUtils.replace(formatObject(argument), "\n", "\\n");
        return quote ? '"' + str + '"' : str;
    }

    /**
     * Format an object
     */
    public static String formatObject(final Object object) {
        return formatObject(object, "<null>");
    }

    /**
     * Format an object
     */
    public static String formatObject(final Object object, final String defaultValue) {
        return formatObject(object, defaultValue, "<empty>");
    }

    /**
     * Format an object
     */
    public static String formatObject(final Object object, final String defaultValue, final String emptyValue) {
        if (object == null) {
            return defaultValue;
        }
        try {
            if (object instanceof Number) {
                if ((object instanceof Float) || (object instanceof Double) || (object instanceof BigDecimal)) {
                    return FLOAT_FORMAT.format(((Number) object).doubleValue());
                }
                return INTEGER_FORMAT.format(((Number) object).longValue());
            } else if ((object instanceof Calendar) || (object instanceof Date)) {
                final Date date = (object instanceof Calendar) ? ((Calendar) object).getTime() : (Date) object;
                final Date truncated = DateUtils.truncate(date, Calendar.DATE);
                if (date.equals(truncated)) {
                    return DATE_FORMAT.format(date);
                } else {
                    return DATE_TIME_FORMAT.format(date);
                }
            } else if (object instanceof Collection<?> || object instanceof Map<?, ?> || object.getClass().isArray()) {
                if (object instanceof Iterator<?>) {
                    return "<iterator>";
                } else if (Hibernate.isInitialized(object)) {
                    Iterator<?> iterator;
                    if (object instanceof Map<?, ?>) {
                        iterator = ((Map<?, ?>) object).entrySet().iterator();
                    } else {
                        iterator = IteratorUtils.getIterator(object);
                    }
                    final StringBuilder sb = new StringBuilder();
                    sb.append('[');
                    while (iterator.hasNext()) {
                        sb.append(formatArgument(iterator.next()));
                        if (iterator.hasNext()) {
                            sb.append(", ");
                        }
                    }
                    sb.append(']');
                    return sb.toString();
                } else {
                    return "<uninitialized collection>";
                }
            } else if (object instanceof String) {
                return "".equals("object") ? emptyValue : (String) object;
            } else if (object instanceof Boolean) {
                return object.toString();
            } else if ((object instanceof Entity) && (object instanceof EntityReference) || !Hibernate.isInitialized(object)) {
                return ClassHelper.getClassName(EntityHelper.getRealClass((Entity) object)) + '#' + ((Entity) object).getId();
            } else {
                return object.toString();
            }
        } catch (final Exception e) {
            return defaultValue;
        }
    }

    /**
     * Format a generic view object
     */
    public static String formatVO(final Object vo) {
        return formatVO(vo, DEFAULT_EXCLUDES);
    }

    /**
     * Format a generic view object
     */
    public static String formatVO(final Object vo, final String... excludedProperties) {
        if (vo == null) {
            return "<null>";
        }
        try {
            final List<String> exclude = new ArrayList<String>();
            exclude.add("class");
            if (excludedProperties != null && excludedProperties.length > 0) {
                exclude.addAll(Arrays.asList(excludedProperties));
            }
            final StringBuilder sb = new StringBuilder();
            sb.append(ClassHelper.getClassName(vo.getClass())).append(" (");
            final PropertyDescriptor[] propertyDescriptors = PropertyUtils.getPropertyDescriptors(vo);
            for (final PropertyDescriptor descriptor : propertyDescriptors) {
                final String name = descriptor.getName();
                if (exclude(exclude, name)) {
                    continue;
                }
                String value;
                try {
                    value = formatArgument(PropertyHelper.get(vo, name));
                } catch (final Exception e) {
                    value = "<error retrieving - is uninitialized?>";
                }
                sb.append(name).append('=').append(value).append(';');
            }
            sb.setLength(sb.length() - 1);
            sb.append(')');
            return sb.toString();
        } catch (final Exception e) {
            return vo.toString();
        }
    }

    /**
     * Returns a masked value if the parameter name is in the {@link #DEFAULT_EXCLUDES} array
     */
    public static String maskIfNeeded(final String name, final Object value) {
        return shouldMask(name) ? MASKED_VALUE : formatObject(value);
    }

    /**
     * Returns whether the given name is in the {@link #DEFAULT_EXCLUDES} array
     */
    public static boolean shouldMask(final String name) {
        for (final String exclude : FormatObject.DEFAULT_EXCLUDES) {
            if (StringUtils.containsIgnoreCase(name, exclude)) {
                return true;
            }
        }
        return false;
    }

    private static boolean exclude(final List<String> list, final String string) {
        if (CollectionUtils.isEmpty(list)) {
            return false;
        }
        for (final String exclude : list) {
            if (StringUtils.containsIgnoreCase(string, exclude)) {
                return true;
            }
        }
        return false;
    }
}
