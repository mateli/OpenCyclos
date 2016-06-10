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

import nl.strohalm.cyclos.webservices.utils.ObjectHelper;

/**
 * The way users are identified in the POS channel
 * @author luis
 */
public class WSPrincipalTypeVO {

    public static enum WSPrincipal {

        /**
         * Username
         */
        USER,

        /**
         * Card number
         */
        CARD,

        /**
         * The e-mail
         */
        EMAIL,

        /**
         * Custom field
         */
        CUSTOM_FIELD

    }

    private WSPrincipal principal;
    private String      customFieldInternalName;
    private String      label;
    private Boolean     isDefault = false;

    public String getCustomFieldInternalName() {
        return customFieldInternalName;
    }

    public boolean getDefault() {
        return ObjectHelper.valueOf(isDefault);
    }

    public String getLabel() {
        return label;
    }

    public WSPrincipal getPrincipal() {
        return principal;
    }

    public void setCustomFieldInternalName(final String customFieldInternalName) {
        this.customFieldInternalName = customFieldInternalName;
    }

    public void setDefault(final boolean isDefault) {
        this.isDefault = isDefault;
    }

    public void setLabel(final String label) {
        this.label = label;
    }

    public void setPrincipal(final WSPrincipal principal) {
        this.principal = principal;
    }

    @Override
    public String toString() {
        return "WSPrincipalType(principal=" + principal + ", customFieldInternalName=" + customFieldInternalName + ", label=" + label + "isDefault=" + isDefault + ")";

    }
}
