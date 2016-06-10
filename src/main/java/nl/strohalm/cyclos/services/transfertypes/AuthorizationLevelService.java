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
package nl.strohalm.cyclos.services.transfertypes;

import nl.strohalm.cyclos.entities.accounts.transactions.AuthorizationLevel;
import nl.strohalm.cyclos.services.Service;
import nl.strohalm.cyclos.utils.validation.ValidationException;

/**
 * Service interface for authorization levels
 * @author luis
 */
public interface AuthorizationLevelService extends Service {

    /**
     * Removes the specified authorization levels
     * @return The number of removed objects
     */
    int remove(Long... ids);

    /**
     * Saves the specified authorization level, returning the resulting object
     */
    AuthorizationLevel save(AuthorizationLevel level);

    /**
     * Validates the given authorization level, throwing a {@link ValidationException} when invalid
     */
    void validate(AuthorizationLevel level) throws ValidationException;
}
