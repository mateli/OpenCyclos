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

import nl.strohalm.cyclos.utils.PropertyHelper;
import nl.strohalm.cyclos.utils.conversion.CoercionHelper;
import nl.strohalm.cyclos.utils.conversion.ConversionException;
import nl.strohalm.cyclos.utils.conversion.Converter;
import nl.strohalm.cyclos.utils.conversion.StringTrimmerConverter;

/**
 * A property binder for simple properties
 * @author luis
 */
public class PropertyBinder<T> extends DataBinder<T> {

    private static final long serialVersionUID = -2107269320674334749L;

    @SuppressWarnings("unchecked")
    public static <T> PropertyBinder<T> instance(final Class<T> propertyType, final String propertyName) {
        Converter<T> converter = null;
        if (String.class.isAssignableFrom(propertyType)) {
            converter = (Converter<T>) new StringTrimmerConverter();
        }

        return instance(propertyType, propertyName, converter);
    }

    public static <T> PropertyBinder<T> instance(final Class<T> propertyType, final String propertyName, final Converter<T> converter) {
        final PropertyBinder<T> binder = new PropertyBinder<T>();
        binder.setPath(propertyName);
        binder.setType(propertyType);
        binder.setConverter(converter);
        return binder;
    }

    private Converter<T> converter;

    public Converter<T> getConverter() {
        return converter;
    }

    @Override
    public T read(final Object object) {
        return CoercionHelper.coerce(getType(), PropertyHelper.get(object, getPath()));
    }

    @Override
    public String readAsString(final Object object) {
        try {
            return PropertyHelper.getAsString(read(object), getConverter());
        } catch (final ConversionException e) {
            return null;
        }
    }

    @Override
    public T readFromString(final Object object) {
        final Object objectValue = PropertyHelper.get(object, getPath());
        final String value = CoercionHelper.coerce(String.class, objectValue);
        try {
            return PropertyHelper.getAsObject(getType(), value, getConverter());
        } catch (final ConversionException e) {
            return null;
        }
    }

    public void setConverter(final Converter<T> converter) {
        this.converter = converter;
    }

    @Override
    public void write(final Object object, final T value) {
        PropertyHelper.set(object, getPath(), CoercionHelper.coerce(getType(), value));
    }

    @Override
    public void writeAsString(final Object object, final Object value) {
        write(object, PropertyHelper.getAsObject(getType(), CoercionHelper.coerce(String.class, value), getConverter()));
    }
}
