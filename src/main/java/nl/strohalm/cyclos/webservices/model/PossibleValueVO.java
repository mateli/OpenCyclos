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
 * Possible custom field value for web services
 * @author luis
 */
@XmlType(name = "possibleValue")
public class PossibleValueVO extends EntityVO {
    private static final long serialVersionUID = -1218562552352420468L;
    private String            value;
    private Long              parentId;
    private boolean           defaultValue;

    public Long getParentId() {
        return parentId;
    }

    public String getValue() {
        return value;
    }

    public boolean isDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(final boolean defaultValue) {
        this.defaultValue = defaultValue;
    }

    public void setParentId(final Long parentId) {
        this.parentId = parentId;
    }

    public void setValue(final String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "PossibleValueVO [value=" + value + ", parentId=" + parentId + ", defaultValue=" + defaultValue + "]";
    }

}
