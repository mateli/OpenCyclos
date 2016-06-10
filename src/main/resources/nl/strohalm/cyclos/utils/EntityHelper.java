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
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.TreeSet;

import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyFactory;
import javassist.util.proxy.ProxyObject;
import nl.strohalm.cyclos.entities.Entity;
import nl.strohalm.cyclos.entities.EntityReference;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.proxy.HibernateProxy;

/**
 * Helper class for entities
 * @author luis
 */
public class EntityHelper {

    /**
     * A method handler for entity references
     * @author luis
     */
    private static final class InstanceMethodHandler implements MethodHandler {
        private final Class<? extends Entity> entityType;
        private final Long                    id;

        private InstanceMethodHandler(final Class<? extends Entity> entityType, final Long id) {
            this.entityType = entityType;
            this.id = id;
        }

        @Override
        public Object invoke(final Object object, final Method thisMethod, final Method proceed, final Object[] args) throws Throwable {
            if (isExpectedMethod("toString", 0, thisMethod, args)) {
                return entityType.getSimpleName() + "#" + id;
            } else {
                return proceed.invoke(object, args);
            }
        }

        private boolean isExpectedMethod(final String expectedMethod, final int expectedArgs, final Method currentMethod, final Object[] currentArgs) {
            return expectedMethod.equals(currentMethod.getName()) && expectedArgs == (currentArgs == null ? 0 : currentArgs.length);
        }
    }

    private static Long[]                                                              EMPTY_ARRAY             = new Long[0];
    private static Map<Class<? extends Entity>, SortedMap<String, PropertyDescriptor>> cachedPropertiesByClass = new HashMap<Class<? extends Entity>, SortedMap<String, PropertyDescriptor>>();
    private static Map<Class<? extends Entity>, Class<? extends Entity>>               cachedEntityTypes       = new HashMap<Class<? extends Entity>, Class<? extends Entity>>();

    /**
     * Returns the real class of the given entity. If it is a proxy, return the entity class, not the proxy class
     */
    @SuppressWarnings("unchecked")
    public static Class<? extends Entity> getRealClass(final Entity entity) {
        final Class<? extends Entity> type = entity.getClass();
        if ((entity instanceof EntityReference) || (entity instanceof HibernateProxy)) {
            return (Class<? extends Entity>) type.getSuperclass();
        }
        return type;
    }

    /**
     * Returns the real root class of the given entity. If it is a proxy, return the entity class, not the proxy class. For example, if trying
     * getRootRealClass(MemberAccountProxy), the result will be Account.
     */
    @SuppressWarnings("unchecked")
    public static Class<? extends Entity> getRealRootClass(final Entity entity) {
        Class<? extends Entity> type = getRealClass(entity);
        while (!type.getSuperclass().equals(Entity.class)) {
            type = (Class<? extends Entity>) type.getSuperclass();
        }
        return type;
    }

    /**
     * Returns true if the specified id contains a possible entity identifier. A possible entity identifier is considered valid if it is not null and
     * positive.
     */
    public static boolean isValidId(final Long id) {
        return id != null && id > 0;
    }

    /**
     * Returns true if the given string value contains a valid identifier
     */
    public static boolean isValidId(final String value) {
        try {
            final long id = Long.parseLong(value);
            return isValidId(id);
        } catch (final NumberFormatException e) {
            return false;
        }
    }

    /**
     * Parses a list of ids from a given string
     */
    public static Set<Long> parseIds(final String string) {
        final Set<Long> ids = new HashSet<Long>();
        final String[] parts = StringUtils.trimToEmpty(string).split(",");
        for (String part : parts) {
            part = part.trim();
            if (part.isEmpty()) {
                continue;
            }
            long id;
            try {
                id = Long.parseLong(part);
                if (id <= 0) {
                    throw new Exception();
                }
            } catch (final Exception e) {
                throw new IllegalStateException("Invalid id value:" + part);
            }
            ids.add(id);
        }
        return ids;
    }

    /**
     * Returns a Map with basic properties for the given entity
     */
    public static Map<String, PropertyDescriptor> propertyDescriptorsFor(final Entity entity) {
        final Class<? extends Entity> clazz = getRealClass(entity);
        SortedMap<String, PropertyDescriptor> properties = cachedPropertiesByClass.get(clazz);
        if (properties == null) {
            properties = new TreeMap<String, PropertyDescriptor>();
            final PropertyDescriptor[] propertyDescriptors = PropertyUtils.getPropertyDescriptors(clazz);
            for (final PropertyDescriptor descriptor : propertyDescriptors) {
                final String name = descriptor.getName();
                boolean ok = name.equals("id");
                if (!ok) {
                    final Method readMethod = descriptor.getReadMethod();
                    if (readMethod != null) {
                        final Class<?> declaringClass = readMethod.getDeclaringClass();
                        ok = !declaringClass.equals(Entity.class) && !declaringClass.equals(CustomFieldsContainer.class);
                    }
                }
                if (ok) {
                    properties.put(name, descriptor);
                }
            }
            properties = Collections.unmodifiableSortedMap(properties);
            cachedPropertiesByClass.put(clazz, properties);
        }
        return properties;
    }

    /**
     * Returns a collection with basic property names for the given entity
     */
    public static Collection<String> propertyNamesFor(final Entity entity) {
        return propertyDescriptorsFor(entity).keySet();
    }

    /**
     * Returns a reference to the given entity type and the give id
     */
    @SuppressWarnings("unchecked")
    public static <E extends Entity> E reference(final Class<E> entityType, final Long id) {
        if (id == null || id.longValue() <= 0L) {
            return null;
        }

        final Class<? extends Entity> proxyClass = resolveEntityClass(entityType);
        final E proxy = (E) ClassHelper.instantiate(proxyClass);
        ((ProxyObject) proxy).setHandler(new InstanceMethodHandler(entityType, id));
        proxy.setId(id);
        return proxy;
    }

    /**
     * Returns a reference array to the given entity type and the give ids
     */
    @SuppressWarnings("unchecked")
    public static <E extends Entity> E[] references(final Class<E> entityType, final List<Long> ids) {
        if (ids == null || ids.size() == 0) {
            return (E[]) Array.newInstance(entityType, 0);
        }
        final E[] entities = (E[]) Array.newInstance(entityType, ids.size());
        for (int i = 0; i < ids.size(); i++) {
            final Long id = ids.get(i);
            entities[i] = reference(entityType, id);
        }
        return entities;
    }

    /**
     * @return return an ordered set of EntityVO
     */
    public static Set<EntityVO> toEntityVO(final Collection<? extends Entity> entities) {
        if (CollectionUtils.isEmpty(entities)) {
            return Collections.emptySet();
        }
        final TreeSet<EntityVO> orderedSet = new TreeSet<EntityVO>();
        for (final Entity entity : entities) {
            orderedSet.add(entity.readOnlyView());
        }
        return orderedSet;
    }

    /**
     * Converts entities into an array of identifiers
     */
    public static Long[] toIds(final Collection<? extends Entity> entities) {
        if (entities == null || entities.isEmpty()) {
            return EMPTY_ARRAY;
        }
        final Long[] ids = new Long[entities.size()];
        int i = 0;
        for (final Entity entity : entities) {
            ids[i++] = entity.getId();
        }
        return ids;
    }

    /**
     * Converts entities into an array of identifiers
     */
    public static Long[] toIds(final Entity... entities) {
        if (entities == null || entities.length == 0) {
            return EMPTY_ARRAY;
        }
        return toIds(Arrays.asList(entities));
    }

    public static Long[] toIds(final String... idsAsString) {
        if (idsAsString == null || idsAsString.length == 0) {
            return EMPTY_ARRAY;
        }
        final Long[] ids = new Long[idsAsString.length];
        int i = 0;
        for (final String idAsString : idsAsString) {
            ids[i++] = Long.valueOf(idAsString);
        }
        return ids;
    }

    /**
     * converts a Collection of Entities into an ArrayList of their id's.
     * @param entities. The input list of Entities. Can be null or empty.
     * @return An ArrayList of the ids. Returns an empty list if the input was null or an empty list.
     */
    public static Collection<Long> toIdsAsList(final Collection<? extends Entity> entities) {
        if (entities == null || entities.isEmpty()) {
            return Collections.emptyList();
        }
        final List<Long> ids = new ArrayList<Long>(entities.size());
        for (final Entity entity : entities) {
            ids.add(entity.getId());
        }
        return ids;
    }

    /**
     * Converts entities into a JSON representation of ids
     */
    public static String toIdsAsString(final Collection<? extends Entity> entities) {
        return '[' + StringUtils.join(toIds(entities), ',') + ']';
    }

    /**
     * Converts entities into a JSON representation of ids
     */
    public static String toIdsAsString(final Entity... entities) {
        return '[' + StringUtils.join(toIds(entities), ',') + ']';
    }

    @SuppressWarnings("unchecked")
    private synchronized static Class<? extends Entity> resolveEntityClass(final Class<? extends Entity> entityType) {
        Class<? extends Entity> proxyType = cachedEntityTypes.get(entityType);
        if (proxyType == null) {
            final ProxyFactory factory = new ProxyFactory();
            factory.setInterfaces(new Class[] { EntityReference.class });
            factory.setSuperclass(entityType);
            proxyType = factory.createClass();
            cachedEntityTypes.put(entityType, proxyType);
        }
        return proxyType;
    }

}
