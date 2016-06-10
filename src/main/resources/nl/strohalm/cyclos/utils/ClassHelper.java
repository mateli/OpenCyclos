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

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import nl.strohalm.cyclos.utils.binding.BindingException;

import org.apache.commons.lang.ClassUtils;

/**
 * Helper class for getting data about classes
 * @author luis
 */
public final class ClassHelper {

    /**
     * Returns all implemented types for a given class - itself if a class and interfaces
     */
    public static List<Class<?>> allImplementedTypes(Class<?> clazz) {
        final Class<?>[] interfaces = clazz.getInterfaces();
        final List<Class<?>> classes = new ArrayList<Class<?>>(interfaces.length + 1);
        while (clazz != null && !clazz.equals(Object.class)) {
            classes.add(clazz);
            clazz = clazz.getSuperclass();
        }
        classes.addAll(Arrays.asList(interfaces));
        return classes;
    }

    /**
     * Casts a class to a required type
     */
    @SuppressWarnings("unchecked")
    public static <T> Class<T> cast(final Class<?> clazz) {
        return (Class<T>) clazz;
    }

    /**
     * If the class is concrete, return it. Else, try to find a well known subclass
     * @param <T> The specified class type
     * @param <C> The resulting class type
     * @param clazz The specified class
     * @return The class if it is concrete, or a well-known subclass.
     * @throws IllegalArgumentException The specified class is abstract (or interface) and not a well-known one
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static <T, C extends T> Class<C> concreteClass(final Class<T> clazz) {
        if (clazz == null) {
            throw new NullPointerException("null.clazz");
        }
        Class<C> ret = null;
        final int modifiers = clazz.getModifiers();
        if (Modifier.isAbstract(modifiers) || Modifier.isInterface(modifiers) || Collection.class.isAssignableFrom(clazz)) {
            if (Calendar.class.isAssignableFrom(clazz)) {
                ret = (Class) GregorianCalendar.class;
            } else if (SortedSet.class.isAssignableFrom(clazz)) {
                ret = (Class) TreeSet.class;
            } else if (Set.class.isAssignableFrom(clazz)) {
                ret = (Class) LinkedHashSet.class;
            } else if (Collection.class.isAssignableFrom(clazz)) {
                ret = (Class) ArrayList.class;
            } else if (SortedMap.class.isAssignableFrom(clazz)) {
                ret = (Class) TreeMap.class;
            } else if (Map.class.isAssignableFrom(clazz)) {
                ret = (Class) LinkedHashMap.class;
            } else {
                throw new IllegalArgumentException("Unknown concrete class for " + clazz.getName());
            }
        } else {
            ret = (Class) clazz;
        }
        return ret;
    }

    /**
     * Find a method annotation on the declaring class or in any of it's implemented interfaces
     */
    public static <A extends Annotation> A findAnnotation(final Class<?> clazz, final Class<A> type) {
        final List<Class<?>> classes = allImplementedTypes(clazz);
        for (final Class<?> c : classes) {
            try {
                final A annotation = c.getAnnotation(type);
                if (annotation != null) {
                    return annotation;
                }
            } catch (final Exception e) {
                // Try next one
            }
        }
        return null;
    }

    /**
     * Find a method annotation on the method in any of it's implemented interfaces
     */
    public static <A extends Annotation> A findAnnotation(final Method method, final Class<A> type) {
        return findAnnotation(method, type, false);
    }

    /**
     * Find a method annotation on the method or declaring class or in any of it's implemented interfaces
     */
    public static <A extends Annotation> A findAnnotation(final Method method, final Class<A> type, final boolean searchInDeclaringClass) {
        final Class<?> declaringClass = method.getDeclaringClass();
        final List<Class<?>> classes = allImplementedTypes(declaringClass);
        for (final Class<?> c : classes) {
            try {
                final Method m = c.getMethod(method.getName(), method.getParameterTypes());
                final A annotation = m.getAnnotation(type);
                if (annotation != null) {
                    return annotation;
                }
            } catch (final Exception e) {
                // Try next one
            }
        }
        // at this point the annotation was not found
        if (searchInDeclaringClass) {
            return findAnnotation(method.getDeclaringClass(), type);
        } else {
            return null;
        }
    }

    /**
     * Returns only the unqualified class name
     * @param clazz The class
     * @return The unqualified name
     */
    public static String getClassName(final Class<?> clazz) {
        final String name = clazz.getName();
        final int pos = name.lastIndexOf('.');
        if (pos < 0) {
            return name;
        } else {
            return name.substring(pos + 1);
        }
    }

    /**
     * Create an instance of the class. If the class is a well known interface or abstract class (like Collection, List or Calendar), a concrete
     * @param <T> The instance type
     * @param clazz The class
     * @return The instance
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static <T> T instantiate(final Class<T> clazz) {
        try {
            final Object object = concreteClass((Class) clazz).newInstance();
            return (T) object;
        } catch (final Exception e) {
            throw new BindingException("Could not instantiate bean of class " + clazz.getName(), e);
        }
    }

    /**
     * Returns if the given object is an instance of the specified class, handling primitives as objects
     */
    public static boolean isInstance(Class<?> clazz, final Object object) {
        if (clazz.isPrimitive()) {
            clazz = ClassUtils.primitiveToWrapper(clazz);
        }
        return clazz.isInstance(object);
    }
}
