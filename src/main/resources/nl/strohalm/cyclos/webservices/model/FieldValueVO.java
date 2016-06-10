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
package nl.strohalm.cyclos.webservices.model;

import javax.xml.bind.annotation.XmlType;

/**
 * A custom field x value representation for web services
 * @author luis
 */
@XmlType(name = "fieldValue")
public class FieldValueVO implements Cloneable {

    // field attributes
    private String internalName;
    private Long   fieldId;
    private String displayName;

    // value attributes
    private String value;
    private Long   possibleValueId;
    private Long   memberValueId;

    public FieldValueVO() {
    }

    public FieldValueVO(final String internalName, final String value) {
        this.internalName = internalName;
        this.value = value;
    }

    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (final CloneNotSupportedException e) {
            return null;
        }
    }

    public String getDisplayName() {
        return displayName;
    }

    public Long getFieldId() {
        return fieldId;
    }

    public String getInternalName() {
        return internalName;
    }

    public Long getMemberValueId() {
        return memberValueId;
    }

    public Long getPossibleValueId() {
        return possibleValueId;
    }

    public String getValue() {
        return value;
    }

    public void setDisplayName(final String displayName) {
        this.displayName = displayName;
    }

    public void setFieldId(final Long fieldId) {
        this.fieldId = fieldId;
    }

    public void setInternalName(final String internalName) {
        this.internalName = internalName;
    }

    public void setMemberValueId(final Long memberValueId) {
        this.memberValueId = memberValueId;
    }

    public void setPossibleValueId(final Long possibleValueId) {
        this.possibleValueId = possibleValueId;
    }

    public void setValue(final String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "FieldValueVO [internalName=" + internalName + ", fieldId=" + fieldId + ", displayName=" + displayName + ", value=" + value + ", possibleValueId=" + possibleValueId + ", memberValueId=" + memberValueId + "]";
    }

}
