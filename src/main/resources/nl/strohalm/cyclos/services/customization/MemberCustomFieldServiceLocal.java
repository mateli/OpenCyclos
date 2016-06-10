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

import nl.strohalm.cyclos.entities.customization.fields.MemberCustomField;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.members.RegisteredMember;
import nl.strohalm.cyclos.entities.members.imports.ImportedMember;
import nl.strohalm.cyclos.utils.validation.Validator;

/**
 * Local interface for {@link MemberCustomFieldService}
 * 
 * @author luis
 */
public interface MemberCustomFieldServiceLocal extends MemberCustomFieldService, BaseCustomFieldServiceLocal<MemberCustomField> {

    /**
     * Returns a member custom field validator for the given group
     */
    Validator getValueValidator(MemberGroup group, MemberCustomField.Access access);

    /**
     * Saves a collection of imported members field values
     */
    void saveValues(ImportedMember importedMember);

    /**
     * Saves a collection of member custom field values
     */
    void saveValues(RegisteredMember registeredMember);

}
