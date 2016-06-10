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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import nl.strohalm.cyclos.utils.ClassHelper;
import nl.strohalm.cyclos.utils.PropertyHelper;
import nl.strohalm.cyclos.utils.conversion.CoercionConverter;
import nl.strohalm.cyclos.utils.conversion.CoercionHelper;
import nl.strohalm.cyclos.utils.conversion.Converter;

import org.apache.commons.collections.IteratorUtils;

/**
 * A binder for collections of a single type
 * @author luis
 */
public class SimpleCollectionBinder<T> extends DataBinder<Collection<T>> {

    private static final long serialVersionUID = -2345152330629764092L;

    public static <T> SimpleCollectionBinder<T> instance(final Class<T> elementType) {
        return instance(elementType, null, null, null);
    }

    public static <T> SimpleCollectionBinder<T> instance(final Class<T> elementType, final Class<?> collectionType) {
        return instance(elementType, collectionType, null, null);
    }

    public static <T> SimpleCollectionBinder<T> instance(final Class<T> elementType, final Class<?> collectionType, final Converter<T> converter) {
        return instance(elementType, collectionType, null, converter);
    }

    public static <T> SimpleCollectionBinder<T> instance(final Class<T> elementType, final Class<?> collectionType, final String path) {
        return instance(elementType, collectionType, path, null);
    }

    public static <T> SimpleCollectionBinder<T> instance(final Class<T> elementType, final Class<?> collectionType, final String path, final Converter<T> converter) {
        final SimpleCollectionBinder<T> binder = new SimpleCollectionBinder<T>();
        binder.setElementType(elementType);
        binder.setCollectionType(collectionType);
        binder.setPath(path);
        binder.setConverter(converter);
        return binder;
    }

    public static <T> SimpleCollectionBinder<T> instance(final Class<T> elementType, final Converter<T> converter) {
        return instance(elementType, null, null, converter);
    }

    public static <T> SimpleCollectionBinder<T> instance(final Class<T> elementType, final String path) {
        return instance(elementType, null, path, null);
    }

    public static <T> SimpleCollectionBinder<T> instance(final Class<T> elementType, final String path, final Converter<T> converter) {
        return instance(elementType, null, path, converter);
    }

    private Class<T>     elementType;
    private Class<?>     collectionType = ArrayList.class;
    private Converter<T> converter;

    public SimpleCollectionBinder() {
    }

    public Class<?> getCollectionType() {
        return collectionType;
    }

    public Converter<T> getConverter() {
        return converter;
    }

    public Class<T> getElementType() {
        return elementType;
    }

    @Override
    public Class<Collection<T>> getType() {
        final Class<Collection<T>> clazz = ClassHelper.cast(collectionType == null ? Collection.class : collectionType);
        return clazz;
    }

    @Override
    public Collection<T> read(final Object object) {
        final Collection<T> ret = instantiateCollection();
        readInto(ret, object, false);
        return ret;
    }

    @Override
    public String readAsString(final Object object) {
        final StringBuilder sb = new StringBuilder();
        sb.append('[');
        final Converter<T> converter = resolveConverter();
        for (final Iterator<?> it = IteratorUtils.getIterator(object); it.hasNext();) {
            final Object value = it.next();
            final T baseValue = CoercionHelper.coerce(elementType, value);
            sb.append('"').append(converter.toString(baseValue)).append('"');
            if (it.hasNext()) {
                sb.append(',');
            }
        }
        sb.append(']');
        return sb.toString();
    }

    @Override
    public Collection<T> readFromString(final Object object) {
        final Collection<T> ret = instantiateCollection();
        readInto(ret, object, true);
        return ret;
    }

    @SuppressWarnings({ "rawtypes" })
    public void readInto(final Collection<T> collection, final Object object, final boolean asString) {
        // Read each element on the list
        final List list = CoercionHelper.coerce(List.class, PropertyHelper.get(object, getPath()));
        if (list != null) {
            final Converter<T> converter = resolveConverter();
            for (final Object current : list) {
                if (current == null) {
                    continue;
                }
                T value;
                if (asString) {
                    value = converter.valueOf(current.toString());
                } else {
                    value = CoercionHelper.coerce(elementType, current);
                }
                if (value != null) {
                    collection.add(value);
                }
            }
        }
    }

    public void setCollectionType(final Class<?> collectionType) {
        this.collectionType = collectionType == null ? ArrayList.class : collectionType;
    }

    public void setConverter(final Converter<T> converter) {
        this.converter = converter;
    }

    public void setElementType(final Class<T> elementType) {
        this.elementType = elementType;
    }

    @Override
    public void write(final Object object, final Collection<T> values) {
        doWrite(object, values, false);
    }

    @Override
    public void writeAsString(final Object object, final Object value) {
        doWrite(object, CoercionHelper.coerce(List.class, value), true);
    }

    @SuppressWarnings("unchecked")
    private void doWrite(final Object object, final Collection<?> values, final boolean asString) {
        if (values == null) {
            return;
        }
        final Collection<Object> collection = instantiateCollection();
        final Converter<T> converter = resolveConverter();
        for (final Object value : values) {
            Object result;
            if (asString) {
                result = converter.toString((T) value);
            } else {
                result = CoercionHelper.coerce(elementType, value);
            }
            if (result != null) {
                collection.add(result);
            }
        }
        PropertyHelper.set(object, getPath(), collection);
    }

    @SuppressWarnings("unchecked")
    private <E> Collection<E> instantiateCollection() {
        return (Collection<E>) ClassHelper.instantiate(collectionType);
    }

    private Converter<T> resolveConverter() {
        final Converter<T> converter = this.converter == null ? CoercionConverter.instance(elementType) : this.converter;
        return converter;
    }

}
