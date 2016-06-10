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
package nl.strohalm.cyclos.entities.members;

import java.util.Collection;

import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.access.AdminUser;
import nl.strohalm.cyclos.entities.accounts.AccountOwner;
import nl.strohalm.cyclos.entities.accounts.SystemAccountOwner;
import nl.strohalm.cyclos.entities.customization.fields.AdminCustomField;
import nl.strohalm.cyclos.entities.customization.fields.AdminCustomFieldValue;
import nl.strohalm.cyclos.entities.groups.AdminGroup;
import nl.strohalm.cyclos.utils.CustomFieldsContainer;

/**
 * An administrator is an user with system privilleges
 * @author luis
 */
public class Administrator extends Element implements CustomFieldsContainer<AdminCustomField, AdminCustomFieldValue> {

    public static enum Relationships implements Relationship {
        CUSTOM_VALUES("customValues");
        private final String name;

        private Relationships(final String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    private static final long                 serialVersionUID = -3378150810991395557L;
    private Collection<AdminCustomFieldValue> customValues;

    @Override
    public AccountOwner getAccountOwner() {
        return SystemAccountOwner.instance();
    }

    public AdminGroup getAdminGroup() {
        return (AdminGroup) super.getGroup();
    }

    public AdminUser getAdminUser() {
        return (AdminUser) super.getUser();
    }

    public Class<AdminCustomField> getCustomFieldClass() {
        return AdminCustomField.class;
    }

    public Class<AdminCustomFieldValue> getCustomFieldValueClass() {
        return AdminCustomFieldValue.class;
    }

    public Collection<AdminCustomFieldValue> getCustomValues() {
        return customValues;
    }

    @Override
    public Element.Nature getNature() {
        return Element.Nature.ADMIN;
    }

    public void setCustomValues(final Collection<AdminCustomFieldValue> customValues) {
        this.customValues = customValues;
    }
}
