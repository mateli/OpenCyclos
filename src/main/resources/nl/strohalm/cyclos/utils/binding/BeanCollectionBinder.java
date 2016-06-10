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
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import nl.strohalm.cyclos.utils.ClassHelper;
import nl.strohalm.cyclos.utils.PropertyHelper;

import org.apache.commons.collections.IteratorUtils;

/**
 * A binder for collections of beans
 * @author luis
 */
public class BeanCollectionBinder<T> extends DataBinder<Collection<T>> {

    private static final long serialVersionUID = -2345152330629764092L;

    public static <T> BeanCollectionBinder<T> instance(final BeanBinder<T> elementBinder) {
        return instance(elementBinder, null, null);
    }

    public static <T> BeanCollectionBinder<T> instance(final BeanBinder<T> elementBinder, final Class<?> collectionType) {
        return instance(elementBinder, collectionType, null);
    }

    public static <T> BeanCollectionBinder<T> instance(final BeanBinder<T> elementBinder, final Class<?> collectionType, final String path) {
        final BeanCollectionBinder<T> binder = new BeanCollectionBinder<T>(elementBinder);
        binder.setPath(path);
        binder.setCollectionType(collectionType);
        return binder;
    }

    public static <T> BeanCollectionBinder<T> instance(final BeanBinder<T> elementBinder, final String path) {
        return instance(elementBinder, null, path);
    }

    private BeanBinder<T> elementBinder;
    private Class<?>      collectionType = ArrayList.class;

    public BeanCollectionBinder() {
        this(null);
    }

    public BeanCollectionBinder(final BeanBinder<T> elementBinder) {
        setElementBinder(elementBinder);
    }

    public Class<?> getCollectionType() {
        return collectionType;
    }

    public DataBinder<T> getElementBinder() {
        return elementBinder;
    }

    @Override
    public Class<Collection<T>> getType() {
        final Class<Collection<T>> clazz = ClassHelper.cast(Collection.class);
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
        validateElementBinder();
        final StringBuilder sb = new StringBuilder();
        sb.append('[');
        for (final Iterator<?> it = IteratorUtils.getIterator(object); it.hasNext();) {
            final Object value = it.next();
            sb.append(elementBinder.readAsString(value));
            if (it.hasNext()) {
                sb.append(',');
            }
        }
        sb.append(']');
        return sb.toString();
    }

    @Override
    public Collection<T> readFromString(final Object object) {
        validateElementBinder();
        final Collection<T> ret = instantiateCollection();
        readInto(ret, object, true);
        return ret;
    }

    public void readInto(final Collection<T> collection, final Object object, final boolean asString) {
        validateElementBinder();
        final Object bean = PropertyHelper.get(object, getPath());

        // We have separate properties, each one with a collection of values of a single property.
        final Map<String, DataBinder<?>> mappings = elementBinder.getMappings();
        if (mappings.isEmpty() || bean == null) {
            return;
        }

        // Get each iterator
        final Map<String, Iterator<?>> iterators = new LinkedHashMap<String, Iterator<?>>();
        for (final Map.Entry<String, DataBinder<?>> entry : mappings.entrySet()) {
            final String name = entry.getKey();
            final Iterator<?> iterator = IteratorUtils.getIterator(PropertyHelper.get(bean, name));
            iterators.put(name, iterator);
        }

        // Iterate the lists
        while (true) {
            boolean hasData = true;
            final Map<String, Object> current = new HashMap<String, Object>();
            hasData = false;
            for (final Map.Entry<String, Iterator<?>> entry : iterators.entrySet()) {
                final String name = entry.getKey();
                final Iterator<?> iterator = entry.getValue();
                if (iterator.hasNext()) {
                    hasData = true;
                    current.put(name, iterator.next());
                }
            }
            if (hasData) {
                // Read the current element
                collection.add(asString ? elementBinder.readFromString(current) : elementBinder.read(current));
            } else {
                // Our iteration has ended
                break;
            }
        }
    }

    public void setCollectionType(final Class<?> collectionType) {
        this.collectionType = collectionType == null ? ArrayList.class : collectionType;
    }

    public void setElementBinder(final BeanBinder<T> elementBinder) {
        this.elementBinder = elementBinder;
    }

    @Override
    public void write(final Object object, final Collection<T> values) {
        validateElementBinder();
        doWrite(object, values, false);
    }

    @Override
    public void writeAsString(final Object object, final Object value) {
        validateElementBinder();
        final Collection<String> values = instantiateCollection();
        for (final Iterator<?> it = IteratorUtils.getIterator(object); it.hasNext();) {
            final Object current = it.next();
            values.add(elementBinder.readAsString(current));
        }
        doWrite(object, values, true);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private void doWrite(final Object object, final Collection<?> values, final boolean asString) {
        // We have separate properties, each one with a collection of values of a single property.
        final Map<String, DataBinder<?>> mappings = ((BeanBinder) elementBinder).getMappings();
        if (mappings.isEmpty()) {
            return;
        }
        final Map<String, List<Object>> map = new HashMap<String, List<Object>>();
        for (final Object value : values) {
            for (final Map.Entry<String, DataBinder<?>> entry : mappings.entrySet()) {
                final String name = entry.getKey();
                final DataBinder<?> nestedBinder = entry.getValue();
                final String path = nestedBinder.getPath();
                List<Object> propValues = map.get(path);
                if (propValues == null) {
                    propValues = new ArrayList<Object>();
                    map.put(path, propValues);
                }
                propValues.add(PropertyHelper.get(value, name));
            }
        }
        for (final Map.Entry<String, List<Object>> entry : map.entrySet()) {
            PropertyHelper.set(object, entry.getKey(), entry.getValue());
        }
    }

    @SuppressWarnings("unchecked")
    private <E> Collection<E> instantiateCollection() {
        return (Collection<E>) ClassHelper.instantiate(collectionType);
    }

    private void validateElementBinder() {
        if (this.elementBinder == null) {
            throw new IllegalStateException("null.element-binder");
        }
    }
}
