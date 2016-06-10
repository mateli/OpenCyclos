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

import java.util.Arrays;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.collections.IteratorUtils;
import org.apache.commons.collections.iterators.ArrayIterator;

/**
 * Extended properties used to sort the output of the store method
 * @author luis
 */
public class SortedProperties extends Properties {

    private static final long serialVersionUID = 2361510798290892779L;

    @Override
    @SuppressWarnings("unchecked")
    public synchronized Enumeration<Object> keys() {
        final Object[] keys = super.keySet().toArray();
        Arrays.sort(keys);
        return IteratorUtils.asEnumeration(new ArrayIterator(keys));
    }

    @Override
    public Set<Object> keySet() {
        return new TreeSet<Object>(super.keySet());
    }
}
