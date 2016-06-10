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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;



/**
 * Utility methods for collections
 * @author luis
 */
public class CollectionHelper {

    /**
     * Returns a defensive copy for either Collections, Maps or Arrays
     */
    @SuppressWarnings("unchecked")
    public static <T> T defensiveCopy(final Object object) {
        if (object == null) {
            return null;
        } else if (object instanceof Set) {
            return (T) new LinkedHashSet<Object>((Set<?>) object);
        } else if (object instanceof Collection) {
            return (T) new ArrayList<Object>((Collection<?>) object);
        } else if (object instanceof Properties) {
            final Properties properties = new SortedProperties();
            properties.putAll((Properties) object);
            return (T) properties;
        } else if (object instanceof Map<?, ?>) {
            return (T) new LinkedHashMap<Object, Object>((Map<?, ?>) object);
        } else if (object.getClass().isArray()) {
            final int length = Array.getLength(object);
            final Object newArray = Array.newInstance(object.getClass().getComponentType(), length);
            System.arraycopy(object, 0, newArray, 0, length);
            return (T) newArray;
        } else {
            return (T) object;
        }
    }

}
