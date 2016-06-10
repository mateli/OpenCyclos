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

import nl.strohalm.cyclos.entities.customization.fields.MemberRecordCustomField;
import nl.strohalm.cyclos.entities.members.imports.ImportedMemberRecord;
import nl.strohalm.cyclos.entities.members.records.MemberRecord;
import nl.strohalm.cyclos.entities.members.records.MemberRecordType;
import nl.strohalm.cyclos.utils.validation.Validator;

/**
 * Local interface for {@link MemberRecordCustomFieldService}
 * 
 * @author luis
 */
public interface MemberRecordCustomFieldServiceLocal extends MemberRecordCustomFieldService, BaseCustomFieldServiceLocal<MemberRecordCustomField> {

    /**
     * Returns an custom field validator for the given member record type
     */
    Validator getValueValidator(MemberRecordType recordType);

    /**
     * Saves the given imported record values
     */
    void saveValues(ImportedMemberRecord record);

    /**
     * Saves the custom values for a record
     */
    void saveValues(MemberRecord record);

}
