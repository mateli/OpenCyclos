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

import java.util.AbstractMap;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import nl.strohalm.cyclos.entities.Entity;
import nl.strohalm.cyclos.entities.customization.fields.CustomField;
import nl.strohalm.cyclos.entities.customization.fields.CustomFieldValue;

import org.apache.commons.collections.CollectionUtils;

/**
 * Wrapper around an entity with custom fields, allowing, through a Map interface, access to properties and custom fields
 */
public class EntityWithCustomFieldsWrapper extends AbstractMap<String, Object> {
    /**
     * Entry implementation for Wrapper
     * @author luis
     */
    public class EntryImpl implements Map.Entry<String, Object> {

        private final String  key;
        private final boolean isCustom;

        EntryImpl(final String key, final boolean isCustom) {
            this.key = key;
            this.isCustom = isCustom;
        }

        @Override
        public String getKey() {
            return key;
        }

        @Override
        public Object getValue() {
            if (isCustom) {
                return resolveCustomValues().get(key);
            } else {
                return PropertyHelper.get(entity, key);
            }
        }

        @Override
        public Object setValue(final Object value) {
            throw new UnsupportedOperationException();
        }

        @Override
        public String toString() {
            return getKey() + "=" + getValue();
        }
    }

    private Map<String, String>                     cachedCustomValues;
    private final Collection<? extends CustomField> customFields;
    private final CustomFieldsContainer<?, ?>       entity;
    private Set<Map.Entry<String, Object>>          cachedEntrySet;
    private CustomFieldHelper                       customFieldHelper;

    public EntityWithCustomFieldsWrapper(final CustomFieldsContainer<?, ?> entity, final Collection<? extends CustomField> customFields, final CustomFieldHelper customFieldHelper) {
        this.entity = entity;
        this.customFields = customFields;
        this.customFieldHelper = customFieldHelper;
    }

    @Override
    public Set<Map.Entry<String, Object>> entrySet() {
        if (cachedEntrySet == null) {
            cachedEntrySet = createEntrySet();
        }
        return cachedEntrySet;
    }

    public CustomFieldValue getFieldValue(final String internalName) {
        if (CollectionUtils.isEmpty(customFields)) {
            return null;
        }
        for (final CustomFieldValue fieldValue : entity.getCustomValues()) {
            if (fieldValue.getField().getInternalName().equals(internalName)) {
                return fieldValue;
            }
        }
        return null;
    }

    private Set<Map.Entry<String, Object>> createEntrySet() {
        final Set<Map.Entry<String, Object>> set = new HashSet<Entry<String, Object>>();
        // Add the custom fields
        for (final CustomField field : customFields) {
            set.add(new EntryImpl(field.getInternalName(), true));
        }
        // Add the basic properties
        final Collection<String> basicProperties = EntityHelper.propertyNamesFor((Entity) entity);
        for (final String property : basicProperties) {
            set.add(new EntryImpl(property, false));
        }
        return set;
    }

    private Map<String, String> resolveCustomValues() {
        if (cachedCustomValues == null) {
            cachedCustomValues = customFieldHelper.getFields(entity);
        }
        return cachedCustomValues;
    }
}
