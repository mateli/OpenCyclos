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
package nl.strohalm.cyclos.utils.hibernate;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Properties;

import org.apache.commons.lang.ObjectUtils;
import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.type.Type;
import org.hibernate.type.TypeFactory;
import org.hibernate.usertype.ParameterizedType;
import org.hibernate.usertype.UserType;

/**
 * An abstract custom type for storing enumerations
 * @author luis
 */
public abstract class AbstractEnumType<EnumType> implements UserType, ParameterizedType, Serializable {

    private static final long serialVersionUID = 5977505341208670187L;

    @SuppressWarnings({ "unchecked", "rawtypes" })
    protected static <E extends AbstractEnumType<?>, T extends Enum<?>> Type getType(final Class<E> enumType, final Class<T> enumClass) {
        final Properties properties = new Properties();
        properties.setProperty("enumClassName", enumClass.getName());
        return new TypeFactory().custom((Class) enumType, properties);
    }

    private Class<EnumType> enumType;
    private EnumType[]      enumValues;

    public Object assemble(final Serializable cached, final Object owner) throws HibernateException {
        return cached;
    }

    public Object deepCopy(final Object value) throws HibernateException {
        return value;
    }

    public Serializable disassemble(final Object value) throws HibernateException {
        return (Serializable) value;
    }

    public boolean equals(final Object x, final Object y) throws HibernateException {
        return ObjectUtils.equals(x, y);
    }

    public int hashCode(final Object x) throws HibernateException {
        return x == null ? -1 : x.hashCode();
    }

    public boolean isMutable() {
        return false;
    }

    public Object replace(final Object original, final Object target, final Object owner) throws HibernateException {
        return original;
    }

    public Class<?> returnedClass() {
        return this.enumType;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void setParameterValues(final Properties params) {
        // Read the enum class name from the parameters
        final String enumClassName = params.getProperty("enumClassName");
        if (enumClassName == null) {
            throw new MappingException("enumClassName parameter not specified");
        }

        Class clazz;
        try {
            clazz = Class.forName(enumClassName);
        } catch (final java.lang.ClassNotFoundException e) {
            throw new MappingException("enumClass " + enumClassName + " not found", e);
        }

        if (Enum.class.isAssignableFrom(clazz)) {
            this.enumType = clazz;
        } else {
            throw new MappingException("enumClass " + enumClassName + " is not an enumeration");
        }

        try {
            final Method values = this.enumType.getMethod("values");
            this.enumValues = (EnumType[]) values.invoke(null);
        } catch (final Exception e) {
            throw new MappingException("enumClass " + enumClassName + " does not have the values() method");
        }
    }

    protected Class<EnumType> getEnumType() {
        return this.enumType;
    }

    protected EnumType[] getEnumValues() {
        return this.enumValues;
    }
}
