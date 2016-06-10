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

import java.util.List;

import javax.xml.bind.annotation.XmlType;

import nl.strohalm.cyclos.webservices.utils.ObjectHelper;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Custom field data for web services
 * @author luis
 */
@XmlType(name = "field")
public class FieldVO extends EntityVO {

    /**
     * A custom field control type
     * @author luis
     */
    @XmlType(name = "control")
    public static enum FieldVOControl {
        TEXT, TEXTAREA, RICH_EDITOR, SELECT, RADIO, CHECKBOX, MEMBER_AUTOCOMPLETE;
    }

    /**
     * A custom field data type
     * @author luis
     */
    @XmlType(name = "type")
    public static enum FieldVOType {
        STRING, ENUMERATED, INTEGER, DECIMAL, DATE, BOOLEAN, URL, MEMBER;
    }

    private static final long     serialVersionUID = -1218562552352420468L;
    private String                displayName;
    private String                internalName;
    private Boolean               required         = false;
    private FieldVOType           type;
    private FieldVOControl        control;
    private String                mask;
    private Long                  parentId;
    private List<PossibleValueVO> possibleValues;
    private Integer               minLength        = 0;
    private Integer               maxLength        = 0;

    public FieldVOControl getControl() {
        return control;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getInternalName() {
        return internalName;
    }

    public String getMask() {
        return mask;
    }

    public int getMaxLength() {
        return ObjectHelper.valueOf(maxLength);
    }

    public int getMinLength() {
        return ObjectHelper.valueOf(minLength);
    }

    public Long getParentId() {
        return parentId;
    }

    public List<PossibleValueVO> getPossibleValues() {
        return possibleValues;
    }

    public boolean getRequired() {
        return ObjectHelper.valueOf(required);
    }

    public FieldVOType getType() {
        return type;
    }

    @JsonIgnore
    public boolean isEnumerated() {
        return type == FieldVOType.ENUMERATED;
    }

    public void setControl(final FieldVOControl control) {
        this.control = control;
    }

    public void setDisplayName(final String displayName) {
        this.displayName = displayName;
    }

    public void setInternalName(final String internalName) {
        this.internalName = internalName;
    }

    public void setMask(final String mask) {
        this.mask = mask;
    }

    public void setMaxLength(final int maxLength) {
        this.maxLength = maxLength;
    }

    public void setMinLength(final int minLength) {
        this.minLength = minLength;
    }

    public void setParentId(final Long parentId) {
        this.parentId = parentId;
    }

    public void setPossibleValues(final List<PossibleValueVO> possibleValues) {
        this.possibleValues = possibleValues;
    }

    public void setRequired(final boolean required) {
        this.required = required;
    }

    public void setType(final FieldVOType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "FieldVO [displayName=" + displayName + ", internalName=" + internalName + ", required=" + required + ", type=" + type + ", control=" + control + ", mask=" + mask + ", parentId=" + parentId + ", possibleValues=" + possibleValues + "]";
    }

}
