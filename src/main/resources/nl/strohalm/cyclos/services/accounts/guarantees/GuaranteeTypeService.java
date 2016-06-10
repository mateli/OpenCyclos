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
package nl.strohalm.cyclos.services.accounts.guarantees;

import java.util.List;

import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.guarantees.GuaranteeType;
import nl.strohalm.cyclos.entities.accounts.guarantees.GuaranteeTypeQuery;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.services.Service;
import nl.strohalm.cyclos.utils.validation.ValidationException;

/**
 * Service interface for guarantee types
 * @author Jefferson Magno
 */
public interface GuaranteeTypeService extends Service {

    /**
     * Loads the guarantee type, fetching the specified relationships
     */
    GuaranteeType load(Long id, Relationship... fetch) throws EntityNotFoundException;

    /**
     * Removes the specified guarantee types
     */
    int remove(Long... ids);

    /**
     * Saves the specified guarantee type, returning it with the generated id if it doesn't exist yet.
     */
    GuaranteeType save(GuaranteeType guaranteeType);

    /**
     * Search guarantee types
     */
    List<GuaranteeType> search(GuaranteeTypeQuery guaranteeTypeQuery);

    /**
     * Validates the specified guarantee type
     */
    void validate(GuaranteeType guaranteeType) throws ValidationException;

}
