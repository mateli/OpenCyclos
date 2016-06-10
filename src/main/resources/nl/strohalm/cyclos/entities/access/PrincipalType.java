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
package nl.strohalm.cyclos.entities.access;

import nl.strohalm.cyclos.entities.access.Channel.Principal;
import nl.strohalm.cyclos.entities.customization.fields.MemberCustomField;

/**
 * A principal type
 * 
 * @author luis
 */
public class PrincipalType {
    private Principal         principal;
    private MemberCustomField customField;

    public PrincipalType(final MemberCustomField customField) {
        this(Principal.CUSTOM_FIELD);
        this.customField = customField;
    }

    public PrincipalType(final Principal principal) {
        this.principal = principal;
    }

    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof PrincipalType)) {
            return false;
        }
        final PrincipalType type = (PrincipalType) obj;
        if (principal.equals(type.principal)) {
            if (principal == Principal.CUSTOM_FIELD) {
                return customField.equals(type.getCustomField());
            }
            return true;
        }
        return false;
    }

    public MemberCustomField getCustomField() {
        return customField;
    }

    public Principal getPrincipal() {
        return principal;
    }

    @Override
    public int hashCode() {
        if (principal == Principal.CUSTOM_FIELD) {
            return customField.hashCode();
        }
        return principal.hashCode();
    }

    @Override
    public String toString() {
        if (principal == Principal.CUSTOM_FIELD) {
            return customField == null ? "<null custom field>" : customField.getInternalName();
        }
        if (principal == null) {
            return "<null principal>";
        }
        return principal.name();
    }
}
