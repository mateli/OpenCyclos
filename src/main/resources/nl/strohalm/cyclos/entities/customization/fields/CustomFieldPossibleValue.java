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
package nl.strohalm.cyclos.entities.customization.fields;

import nl.strohalm.cyclos.entities.Entity;
import nl.strohalm.cyclos.entities.Relationship;

/**
 * Represents a possible value of an enumerated custom field
 * @author luis
 */
public class CustomFieldPossibleValue extends Entity {
    public static enum Relationships implements Relationship {
        FIELD("field"), PARENT("parent");
        private final String name;

        private Relationships(final String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    private static final long        serialVersionUID = -6580907184218500656L;

    private CustomField              field;
    private CustomFieldPossibleValue parent;
    private String                   value;
    private boolean                  enabled          = true;
    private boolean                  defaultValue     = false;

    public CustomFieldPossibleValue() {
    }

    public CustomFieldPossibleValue(final CustomField field, final String value) {
        this.field = field;
        this.value = value;
    }

    public CustomField getField() {
        return field;
    }

    public CustomFieldPossibleValue getParent() {
        return parent;
    }

    public String getValue() {
        return value;
    }

    public boolean isDefaultValue() {
        return defaultValue;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setDefaultValue(final boolean defaultValue) {
        this.defaultValue = defaultValue;
    }

    public void setEnabled(final boolean enabled) {
        this.enabled = enabled;
    }

    public void setField(final CustomField field) {
        this.field = field;
    }

    public void setParent(final CustomFieldPossibleValue parent) {
        this.parent = parent;
    }

    public void setValue(final String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return getId() + " - " + value;
    }
}
