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
package nl.strohalm.cyclos.services.customization;

import java.util.Arrays;
import java.util.Collection;

import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.customization.fields.AdminCustomField;
import nl.strohalm.cyclos.entities.customization.fields.CustomField;
import nl.strohalm.cyclos.entities.groups.AdminGroup;
import nl.strohalm.cyclos.entities.members.Administrator;
import nl.strohalm.cyclos.utils.validation.Validator;

/**
 * Implementation for {@link AdminCustomFieldServiceLocal}
 * 
 * @author luis
 */
public class AdminCustomFieldServiceImpl extends BaseGlobalCustomFieldServiceImpl<AdminCustomField> implements AdminCustomFieldServiceLocal {

    public AdminCustomFieldServiceImpl() {
        super(AdminCustomField.class, CustomField.Nature.ADMIN);
    }

    @Override
    public Validator getValueValidator(final AdminGroup group) {
        return getValueValidator(customFieldHelper.onlyForGroup(list(), group));
    }

    @Override
    public void saveValues(final Administrator admin) {
        getValueValidator(admin.getAdminGroup()).validate(admin);
        doSaveValues(admin);
    }

    @Override
    protected Collection<? extends Relationship> resolveAdditionalFetch() {
        return Arrays.asList(AdminCustomField.Relationships.GROUPS);
    }

}
