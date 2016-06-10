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
import nl.strohalm.cyclos.entities.customization.fields.CustomFieldPossibleValue;
import nl.strohalm.cyclos.entities.exceptions.DaoException;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;

/**
 * Data access object interface for custom field possible values
 * @author rafael
 */
public interface CustomFieldPossibleValueDAO extends BaseDAO<CustomFieldPossibleValue>, UpdatableDAO<CustomFieldPossibleValue>, InsertableDAO<CustomFieldPossibleValue>, DeletableDAO<CustomFieldPossibleValue> {

    /**
     * Ensures the given value is the default for it's field
     */
    void ensureDefault(CustomFieldPossibleValue possibleValue);

    /**
     * Loads a possible value by field id and value.
     * @throws EntityNotFoundException When no such possible value exists
     */
    CustomFieldPossibleValue load(Long customFieldId, String value) throws EntityNotFoundException, DaoException;
}
