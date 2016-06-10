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
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import nl.strohalm.cyclos.utils.ClassHelper;
import nl.strohalm.cyclos.utils.PropertyHelper;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.IteratorUtils;

/**
 * A binder for maps
 * @author luis
 */
public class MapBinder<K, V> extends DataBinder<Map<K, V>> {

    private static final long serialVersionUID = -234513355349764092L;

    public static <K, V> MapBinder<K, V> instance(final DataBinder<K> keyBinder, final DataBinder<V> valueBinder) {
        return instance(keyBinder, valueBinder, null);
    }

    public static <K, V> MapBinder<K, V> instance(final DataBinder<K> keyBinder, final DataBinder<V> valueBinder, final String path) {
        final MapBinder<K, V> binder = new MapBinder<K, V>(keyBinder, valueBinder);
        binder.setPath(path);
        return binder;
    }

    private DataBinder<K> keyBinder;
    private DataBinder<V> valueBinder;

    public MapBinder() {
        final Class<Map<K, V>> clazz = ClassHelper.cast(Map.class);
        setType(clazz);
    }

    public MapBinder(final DataBinder<K> keyBinder, final DataBinder<V> valueBinder) {
        this();
        setKeyBinder(keyBinder);
        setValueBinder(valueBinder);
    }

    public DataBinder<K> getKeyBinder() {
        return keyBinder;
    }

    public DataBinder<V> getValueBinder() {
        return valueBinder;
    }

    @Override
    public Map<K, V> read(final Object object) {
        final Map<K, V> ret = new LinkedHashMap<K, V>();
        readInto(ret, object, false);
        return ret;
    }

    @Override
    public String readAsString(final Object object) {
        validateNestedBinders();
        final StringBuilder sb = new StringBuilder();
        sb.append('{');
        for (final Iterator<?> it = IteratorUtils.getIterator(object); it.hasNext();) {
            final Object value = it.next();
            sb.append(keyBinder.readAsString(value)).append('=').append(valueBinder.readAsString(value));
            if (it.hasNext()) {
                sb.append(',');
            }
        }
        sb.append('}');
        return sb.toString();
    }

    @Override
    public Map<K, V> readFromString(final Object object) {
        validateNestedBinders();
        final Map<K, V> ret = new LinkedHashMap<K, V>();
        readInto(ret, object, true);
        return ret;
    }

    public void readInto(final Map<K, V> map, final Object object, final boolean asString) {
        validateNestedBinders();
        Iterator<Object> elements = null;
        final Object bean = PropertyHelper.get(object, getPath());
        // if (isReadFlat()) {
        // In this case we have separate properties, each one with a collection of values of a single property.
        int maxSize = 0;
        final List<Object> keyValues = new ArrayList<Object>();
        final List<Object> valueValues = new ArrayList<Object>();

        Object obj = PropertyHelper.get(bean, keyBinder.getPath());
        CollectionUtils.addAll(keyValues, IteratorUtils.getIterator(obj));

        obj = PropertyHelper.get(bean, valueBinder.getPath());
        CollectionUtils.addAll(valueValues, IteratorUtils.getIterator(obj));

        maxSize = Math.max(keyValues.size(), valueValues.size());
        final List<Object> list = new ArrayList<Object>();
        for (int i = 0; i < maxSize; i++) {
            final Map<String, Object> current = new HashMap<String, Object>();
            current.put(keyBinder.getPath(), keyValues.get(i));
            current.put(valueBinder.getPath(), valueValues.get(i));
            list.add(current);
        }
        elements = list.iterator();
        // } else {
        // //Here, there is a single property with a collection of values
        // elements = IteratorUtils.getIterator(bean);
        // }
        // Asseble the resulting collection
        if (elements != null) {
            while (elements.hasNext()) {
                final Object current = elements.next();
                map.put(asString ? keyBinder.readFromString(current) : keyBinder.read(current), asString ? valueBinder.readFromString(current) : valueBinder.read(current));
            }
        }
    }

    public void setKeyBinder(final DataBinder<K> keyBinder) {
        this.keyBinder = keyBinder;
    }

    public void setValueBinder(final DataBinder<V> valueBinder) {
        this.valueBinder = valueBinder;
    }

    @Override
    public void write(final Object object, final Map<K, V> values) {
        validateNestedBinders();
        doWrite(object, values);
    }

    @Override
    public void writeAsString(final Object object, final Object value) {
        validateNestedBinders();
        final Map<String, String> values = new HashMap<String, String>();
        if (value instanceof Map<?, ?>) {
            for (final Map.Entry<?, ?> entry : ((Map<?, ?>) value).entrySet()) {
                final String keyAsString = keyBinder.readAsString(Collections.singletonMap(keyBinder.getPath(), entry.getKey()));
                final String valueAsString = valueBinder.readAsString(Collections.singletonMap(valueBinder.getPath(), entry.getValue()));
                values.put(keyAsString, valueAsString);
            }
        } else {
            for (final Iterator<?> it = IteratorUtils.getIterator(value); it.hasNext();) {
                final Object current = it.next();
                values.put(keyBinder.readAsString(current), valueBinder.readAsString(current));
            }
        }
        doWrite(object, values);
    }

    private void doWrite(final Object object, final Map<?, ?> map) {
        final List<Object> keys = new ArrayList<Object>();
        final List<Object> values = new ArrayList<Object>();

        for (final Map.Entry<?, ?> entry : map.entrySet()) {
            keys.add(entry.getKey());
            values.add(entry.getValue());
        }

        Object bean = PropertyHelper.get(object, getPath());
        if (bean == null) {
            bean = new HashMap<String, Object>();
            PropertyHelper.set(object, getPath(), bean);
        }

        PropertyHelper.set(bean, keyBinder.getPath(), keys);
        PropertyHelper.set(bean, valueBinder.getPath(), values);
    }

    private void validateNestedBinders() {
        if (this.keyBinder == null) {
            throw new IllegalStateException("null.key-binder");
        }
        if (this.valueBinder == null) {
            throw new IllegalStateException("null.value-binder");
        }
    }
}
