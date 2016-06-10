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
package nl.strohalm.cyclos.utils.binding;

import java.beans.IndexedPropertyDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaClass;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.commons.beanutils.MappedPropertyDescriptor;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;

/**
 * Customized bean utils in order to check if a mapped property refers to an array or single value
 * @author luis
 */
public class CustomBeanUtilsBean extends BeanUtilsBean {
    @Override
    public void setProperty(final Object bean, String name, final Object value) throws IllegalAccessException, InvocationTargetException {

        // Resolve any nested expression to get the actual target bean
        Object target = bean;
        final int delim = findLastNestedIndex(name);
        if (delim >= 0) {
            try {
                target = getPropertyUtils().getProperty(bean, name.substring(0, delim));
            } catch (final NoSuchMethodException e) {
                return; // Skip this property setter
            }
            name = name.substring(delim + 1);
        }

        // Declare local variables we will require
        String propName = null; // Simple name of target property
        Class<?> type = null; // Java type of target property
        int index = -1; // Indexed subscript value (if any)
        String key = null; // Mapped key value (if any)

        // Calculate the property name, index, and key values
        propName = name;
        final int i = propName.indexOf(PropertyUtils.INDEXED_DELIM);
        if (i >= 0) {
            final int k = propName.indexOf(PropertyUtils.INDEXED_DELIM2);
            try {
                index = Integer.parseInt(propName.substring(i + 1, k));
            } catch (final NumberFormatException e) {
                // Ignore
            }
            propName = propName.substring(0, i);
        }
        final int j = propName.indexOf(PropertyUtils.MAPPED_DELIM);
        if (j >= 0) {
            final int k = propName.indexOf(PropertyUtils.MAPPED_DELIM2);
            try {
                key = propName.substring(j + 1, k);
            } catch (final IndexOutOfBoundsException e) {
                // Ignore
            }
            propName = propName.substring(0, j);
        }

        // Calculate the property type
        if (target instanceof DynaBean) {
            final DynaClass dynaClass = ((DynaBean) target).getDynaClass();
            final DynaProperty dynaProperty = dynaClass.getDynaProperty(propName);
            if (dynaProperty == null) {
                return; // Skip this property setter
            }
            type = dynaProperty.getType();
            if (type.isArray() || Collection.class.isAssignableFrom(type)) {
                type = Object[].class;
            }
        } else {
            PropertyDescriptor descriptor = null;
            try {
                descriptor = getPropertyUtils().getPropertyDescriptor(target, name);
                if (descriptor == null) {
                    return; // Skip this property setter
                }
            } catch (final NoSuchMethodException e) {
                return; // Skip this property setter
            }
            if (descriptor instanceof MappedPropertyDescriptor) {
                if (((MappedPropertyDescriptor) descriptor).getMappedWriteMethod() == null) {
                    return; // Read-only, skip this property setter
                }
                type = ((MappedPropertyDescriptor) descriptor).getMappedPropertyType();

                /**
                 * Overriden behaviour ------------------- When a type is Object on a mapped property, retrieve the value to check if it's an array
                 */
                if (Object.class.equals(type)) {
                    try {
                        final Object retrieved = getPropertyUtils().getMappedProperty(target, propName, key);
                        if (retrieved != null) {
                            final Class<?> retrievedType = retrieved.getClass();
                            if (retrievedType.isArray() || Collection.class.isAssignableFrom(retrievedType)) {
                                type = Object[].class;
                            }
                        }
                    } catch (final NoSuchMethodException e) {
                        throw new PropertyException(target, propName + "(" + key + ")");
                    }
                }
            } else if (descriptor instanceof IndexedPropertyDescriptor) {
                if (((IndexedPropertyDescriptor) descriptor).getIndexedWriteMethod() == null) {
                    return; // Read-only, skip this property setter
                }
                type = ((IndexedPropertyDescriptor) descriptor).getIndexedPropertyType();
            } else {
                if (descriptor.getWriteMethod() == null) {
                    return; // Read-only, skip this property setter
                }
                type = descriptor.getPropertyType();
            }
        }

        /**
         * Overriden behaviour ------------------- When a type is Map on a mapped property, retrieve the value to check if it's an array
         */
        if (Map.class.isAssignableFrom(type) && StringUtils.isNotEmpty(key)) {
            try {
                final Map<?, ?> map = (Map<?, ?>) getPropertyUtils().getProperty(target, propName);
                final Object retrieved = map.get(key);
                if (retrieved != null) {
                    final Class<?> retrievedType = retrieved.getClass();
                    if (retrievedType.isArray() || Collection.class.isAssignableFrom(retrievedType)) {
                        type = Object[].class;
                    }
                }
            } catch (final NoSuchMethodException e) {
                throw new PropertyException(target, propName + "(" + key + ")");
            }
        }

        // Convert the specified value to the required type
        Object newValue = null;
        if (type.isArray() && (index < 0)) { // Scalar value into array
            if (value == null) {
                final String values[] = new String[1];
                values[0] = (String) value;
                newValue = getConvertUtils().convert(values, type);
            } else if (value instanceof String) {
                final String values[] = new String[1];
                values[0] = (String) value;
                newValue = getConvertUtils().convert(values, type);
            } else if (value instanceof String[]) {
                newValue = getConvertUtils().convert((String[]) value, type);
            } else {
                newValue = value;
            }
        } else if (type.isArray()) { // Indexed value into array
            if (value instanceof String) {
                newValue = getConvertUtils().convert((String) value, type.getComponentType());
            } else if (value instanceof String[]) {
                newValue = getConvertUtils().convert(((String[]) value)[0], type.getComponentType());
            } else {
                newValue = value;
            }
        } else { // Value into scalar
            if ((value instanceof String) || (value == null)) {
                newValue = getConvertUtils().convert((String) value, type);
            } else if (value instanceof String[]) {
                newValue = getConvertUtils().convert(((String[]) value)[0], type);
            } else if (getConvertUtils().lookup(value.getClass()) != null) {
                newValue = getConvertUtils().convert(value.toString(), type);
            } else {
                newValue = value;
            }
        }

        // Invoke the setter method
        try {
            if (index >= 0) {
                getPropertyUtils().setIndexedProperty(target, propName, index, newValue);
            } else if (key != null) {
                getPropertyUtils().setMappedProperty(target, propName, key, newValue);
            } else {
                getPropertyUtils().setProperty(target, propName, newValue);
            }
        } catch (final NoSuchMethodException e) {
            throw new InvocationTargetException(e, "Cannot set " + propName);
        }

    }

    private int findLastNestedIndex(final String expression) {
        // walk back from the end to the start
        // and find the first index that
        int bracketCount = 0;
        for (int i = expression.length() - 1; i >= 0; i--) {
            final char at = expression.charAt(i);
            switch (at) {
                case PropertyUtils.NESTED_DELIM:
                    if (bracketCount < 1) {
                        return i;
                    }
                    break;

                case PropertyUtils.MAPPED_DELIM:
                case PropertyUtils.INDEXED_DELIM:
                    // not bothered which
                    --bracketCount;
                    break;

                case PropertyUtils.MAPPED_DELIM2:
                case PropertyUtils.INDEXED_DELIM2:
                    // not bothered which
                    ++bracketCount;
                    break;
            }
        }
        // can't find any
        return -1;
    }

}
