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

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaClass;
import org.apache.commons.beanutils.LazyDynaClass;
import org.apache.commons.beanutils.PropertyUtils;

/**
 * Map class implementing the DynaBean interface
 * @author luis
 */
public class MapBean extends HashMap<String, Object> implements DynaBean {

    private static final long     serialVersionUID = -3168550184931640176L;

    private final boolean         isArray;
    private Map<String, Class<?>> propertyTypes    = new HashMap<String, Class<?>>();

    public MapBean(final boolean isArray, final String... properties) {
        this.isArray = isArray;
        if (properties != null) {
            for (final String name : properties) {
                propertyTypes.put(name, isArray ? Object[].class : Object.class);
            }
        }
    }

    public MapBean(final String... properties) {
        this(false, properties);
    }

    public boolean contains(final String name, final String key) {
        return get(name, key) != null;
    }

    @Override
    public Object get(final Object key) {
        final String name = (String) key;
        if (!propertyTypes.containsKey(key)) {
            propertyTypes.put(name, isArray ? Object[].class : Object.class);
        }
        return super.get(key);
    }

    public Object get(final String name) {
        return this.get((Object) name);
    }

    public Object get(final String name, final int index) {
        final Object value = this.get(name);
        if (value != null) {
            if (value instanceof List<?>) {
                return ((List<?>) value).get(index);
            } else if (value.getClass().isArray()) {
                return Array.get(value, index);
            }
        }
        return null;
    }

    public Object get(final String name, final String key) {
        final Object value = this.get(name);
        if (value != null) {
            if (value instanceof DynaBean) {
                return ((DynaBean) value).get(key);
            } else {
                try {
                    return PropertyUtils.getProperty(value, key);
                } catch (final Exception e) {
                    // Keep on
                }
            }
        }
        return null;
    }

    public DynaClass getDynaClass() {
        final LazyDynaClass dynaClass = new LazyDynaClass();
        for (final Map.Entry<String, Class<?>> entry : propertyTypes.entrySet()) {
            dynaClass.add(entry.getKey(), entry.getValue());
        }
        return dynaClass;
    }

    @Override
    public Object put(final String key, final Object value) {
        final String name = key;
        if (!propertyTypes.containsKey(key)) {
            propertyTypes.put(name, isArray ? Object[].class : Object.class);
        }
        return super.put(key, value);
    }

    public void remove(final String name, final String key) {
        final Object value = this.get(name);
        if (value != null) {
            if (value instanceof DynaBean) {
                ((DynaBean) value).set(key, null);
            } else {
                try {
                    PropertyUtils.setProperty(value, key, null);
                } catch (final Exception e) {
                    // Keep on
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    public void set(final String name, final int index, final Object value) {
        final Object bean = this.get(name);
        if (bean != null) {
            if (bean instanceof List) {
                ((List<Object>) bean).set(index, value);
            } else if (value.getClass().isArray()) {
                Array.set(bean, index, value);
            }
        }
    }

    public void set(final String name, final Object value) {
        put(name, value);
    }

    public void set(final String name, final String key, final Object value) {
        final Object bean = this.get(name);
        if (bean != null) {
            if (bean instanceof DynaBean) {
                ((DynaBean) bean).set(key, value);
            } else {
                try {
                    PropertyUtils.setProperty(bean, key, value);
                } catch (final Exception e) {
                    // Keep on
                }
            }
        }
    }

    public void setArray(final String name) {
        setType(name, Object[].class);
    }

    public void setType(final String name, final Class<?> type) {
        propertyTypes.put(name, type);
    }

}
