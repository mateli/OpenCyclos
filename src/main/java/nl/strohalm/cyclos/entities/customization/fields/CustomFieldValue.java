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
import nl.strohalm.cyclos.entities.members.Member;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Inheritance;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * The association between a custom field and a given entity. The value property is transient, containing one of stringValue or the value of the
 * related possibleValue. Persistent are stringValue (when the field is not enumerated) or possibleValue (when enumerated).
 * @author luis
 */
@Inheritance
@Table(name = "custom_field_values")
@DiscriminatorColumn(name = "subclass", length = 10)
@javax.persistence.Entity
public abstract class CustomFieldValue extends Entity {

    public static enum Relationships implements Relationship {
        FIELD("field"), POSSIBLE_VALUE("possibleValue");
        private final String name;

        private Relationships(final String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }
    }

    private static final long        serialVersionUID = -1364762150524373384L;

    @ManyToOne
    @JoinColumn(name = "field_id")
	private CustomField              field;

    @ManyToOne
    @JoinColumn(name = "possible_value_id")
	private CustomFieldPossibleValue possibleValue;

    @ManyToOne
    @JoinColumn(name = "member_value_id")
	private Member                   memberValue;

    @Transient
    private String                   value;

    @Column(name = "string_value") // index="ix_string_value"
    private String                   stringValue;

	public CustomField getField() {
        return field;
    }

    public Member getMemberValue() {
        return memberValue;
    }

    public abstract Object getOwner();

    public CustomFieldPossibleValue getPossibleValue() {
        return possibleValue;
    }

    public String getStringValue() {
        return stringValue;
    }

    public String getValue() {
        return value;
    }

    public void setField(final CustomField field) {
        this.field = field;
    }

    public void setMemberValue(final Member memberValue) {
        this.memberValue = memberValue;
        try {
            value = memberValue.getId().toString();
        } catch (final Exception e) {
            // Ok
        }
    }

    public abstract void setOwner(Object owner);

    public void setPossibleValue(final CustomFieldPossibleValue possibleValue) {
        this.possibleValue = possibleValue;
        try {
            value = possibleValue.getValue();
        } catch (final Exception e) {
            // Ok
        }
    }

    public void setStringValue(final String stringValue) {
        this.stringValue = stringValue;
        value = stringValue;
    }

    public void setValue(final String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return getId() + " - field: " + field + " = " + value;
    }
}
