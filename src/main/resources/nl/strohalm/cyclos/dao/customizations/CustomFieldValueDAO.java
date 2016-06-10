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
package nl.strohalm.cyclos.dao.customizations;

import nl.strohalm.cyclos.dao.BaseDAO;
import nl.strohalm.cyclos.dao.DeletableDAO;
import nl.strohalm.cyclos.dao.InsertableDAO;
import nl.strohalm.cyclos.dao.UpdatableDAO;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.customization.fields.CustomField;
import nl.strohalm.cyclos.entities.customization.fields.CustomFieldPossibleValue;
import nl.strohalm.cyclos.entities.customization.fields.CustomFieldValue;
import nl.strohalm.cyclos.entities.customization.fields.MemberCustomField;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.entities.exceptions.UnexpectedEntityException;

/**
 * Data access object interface for custom fields values
 * @author rafael
 */
public interface CustomFieldValueDAO extends BaseDAO<CustomFieldValue>, InsertableDAO<CustomFieldValue>, UpdatableDAO<CustomFieldValue>, DeletableDAO<CustomFieldValue> {

    /**
     * Loads a custom field value by field type and owner
     * @throws EntityNotFoundException No such value exists
     * @throws UnexpectedEntityException Invalid owner
     */
    CustomFieldValue load(CustomField field, Object owner, Relationship... fetch) throws EntityNotFoundException, UnexpectedEntityException;

    /**
     * Moves all occurences of the old value to the new value, returning the number of affected values
     */
    int moveValues(CustomFieldPossibleValue oldValue, CustomFieldPossibleValue newValue);

    /**
     * Unhides all occurrences of a custom member field, that is, sets hidden to false.
     * @param field a MemberCustomField
     * @return number of changed/updated field values.
     */
    int unHideValues(final MemberCustomField field);

    /**
     * Checks if the given custom field value exists. The value owner is not counted on the check. This method is used to check uniqueness.
     */
    boolean valueExists(CustomFieldValue value);
}
