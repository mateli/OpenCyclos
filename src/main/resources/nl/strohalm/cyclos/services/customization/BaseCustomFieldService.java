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

import java.util.Collection;
import java.util.List;

import nl.strohalm.cyclos.entities.customization.fields.CustomField;
import nl.strohalm.cyclos.entities.customization.fields.CustomFieldPossibleValue;
import nl.strohalm.cyclos.entities.exceptions.DaoException;
import nl.strohalm.cyclos.services.Service;
import nl.strohalm.cyclos.utils.validation.ValidationException;
import nl.strohalm.cyclos.webservices.model.FieldVO;
import nl.strohalm.cyclos.webservices.model.PossibleValueVO;

/**
 * Base interface for services of each custom field nature
 * @param <CF> The custom field type
 * @author luis
 */
public interface BaseCustomFieldService<CF extends CustomField> extends Service {

    /**
     * Returns a FieldVO based on the given customField
     */
    FieldVO getFieldVO(Long customFieldId);

    /**
     * Returns a list of FieldVO based on the given customFields
     */
    List<FieldVO> getFieldVOs(List<Long> customFieldIds);

    /**
     * Returns a list of the possible values for a given custom field. If the possibleValueParentId is not null the method filters by the given
     * parentId
     * @return
     */
    List<PossibleValueVO> getPossibleValueVOs(Long customFieldId, Long possibleValueParentId);

    /**
     * Lists the custom fields which may be set as parent fields of the given one
     */
    List<CF> listPossibleParentFields(CF field);

    /**
     * Loads all the given custom fields
     */
    List<CF> load(Collection<Long> ids);

    /**
     * Loads a custom field
     */
    CF load(Long id);

    /**
     * Loads the given possible field value
     */
    CustomFieldPossibleValue loadPossibleValue(Long id);

    /**
     * Loads all the given possible field values
     */
    List<CustomFieldPossibleValue> loadPossibleValues(Collection<Long> ids);

    /**
     * Removes the specified fields fields, returning the number of removed objects
     */
    int remove(Long... ids);

    /**
     * Removes the possible value with the given identifiers
     */
    int removePossibleValue(Long... ids);

    /**
     * Replace all field values with the given old possible value with the given new value
     */
    int replacePossibleValues(CustomFieldPossibleValue oldValue, CustomFieldPossibleValue newValue);

    /**
     * Saves the given field
     */
    CF save(CF field) throws ValidationException, DaoException;

    /**
     * Saves the given possible value
     */
    CustomFieldPossibleValue save(CustomFieldPossibleValue possibleValue) throws ValidationException, DaoException;

    /**
     * Sets the order for the given fields
     */
    void setOrder(List<Long> ids);

    /**
     * Validates the given field
     */
    void validate(CF field) throws ValidationException;

    /**
     * Validates the given possible value
     */
    void validate(CustomFieldPossibleValue possibleValue) throws ValidationException;

}
