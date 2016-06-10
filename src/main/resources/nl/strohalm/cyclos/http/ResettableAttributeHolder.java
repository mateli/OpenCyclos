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
package nl.strohalm.cyclos.http;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.iterators.IteratorEnumeration;

/**
 * An {@link AttributeHolder} which has another {@link AttributeHolder} as base, and keeps a local copy of the attributes. It allows resetting to the
 * original state and applying the local attribute modifications to the original {@link AttributeHolder}
 * @author luis
 */
public class ResettableAttributeHolder implements AttributeHolder, Resettable {

    /**
     * Determines an operation over attributes
     * @author luis
     */
    private abstract static class AttributeOperation {

        /**
         * Creates an operation for removing an attribute
         */
        private static AttributeOperation remove(final String name) {
            return new AttributeOperation() {
                @Override
                public void apply(final AttributeHolder holder) {
                    holder.removeAttribute(name);
                }
            };
        }

        /**
         * Creates an operation for setting an attribute
         */
        private static AttributeOperation set(final String name, final Object value) {
            return new AttributeOperation() {
                @Override
                public void apply(final AttributeHolder holder) {
                    holder.setAttribute(name, value);
                }
            };
        }

        /**
         * Applies this operation over the given {@link AttributeHolder}
         */
        public abstract void apply(AttributeHolder holder);
    }

    private AttributeHolder          holder;
    private Map<String, Object>      attributes;
    private List<AttributeOperation> operations;

    public ResettableAttributeHolder(final AttributeHolder holder) {
        this.holder = holder;
        attributes = new HashMap<String, Object>();
        operations = new ArrayList<AttributeOperation>();
        resetState();
    }

    /**
     * Apply any pending operation to the original state
     */
    @Override
    public void applyState() {
        for (final AttributeOperation operation : operations) {
            operation.apply(holder);
        }
        operations.clear();
    }

    @Override
    public Object getAttribute(final String name) {
        return attributes.get(name);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Enumeration<String> getAttributeNames() {
        return new IteratorEnumeration(attributes.keySet().iterator());
    }

    @Override
    public void removeAttribute(final String name) {
        operations.add(AttributeOperation.remove(name));
        attributes.remove(name);
    }

    /**
     * Resets this object to the original state
     */
    @Override
    public void resetState() {
        attributes.clear();
        operations.clear();
        // Copy each original attribute
        final Enumeration<String> attributeNames = holder.getAttributeNames();
        while (attributeNames.hasMoreElements()) {
            final String key = attributeNames.nextElement();
            attributes.put(key, holder.getAttribute(key));
        }
    }

    @Override
    public void setAttribute(final String name, final Object value) {
        operations.add(AttributeOperation.set(name, value));
        attributes.put(name, value);
    }
}
