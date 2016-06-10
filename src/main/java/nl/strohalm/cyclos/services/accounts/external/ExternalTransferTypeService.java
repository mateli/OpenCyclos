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
package nl.strohalm.cyclos.services.accounts.external;

import java.util.List;

import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.external.ExternalAccount;
import nl.strohalm.cyclos.entities.accounts.external.ExternalTransferType;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.services.Service;

/**
 * Service interface for External Transfer Type Service
 * @author Lucas Geiss
 */
public interface ExternalTransferTypeService extends Service {

    /**
     * Lists External Transfer Types By Account, ordering results by name
     */
    List<ExternalTransferType> listByAccount(ExternalAccount account);

    /**
     * Loads a External Transfer Type by id
     * @throws EntityNotFoundException There is no such transfer type
     */
    ExternalTransferType load(Long id, Relationship... fetch) throws EntityNotFoundException;

    /**
     * Removes the specified External Transfer Type
     */
    int remove(Long... ids);

    /**
     * Saves the EExternal Transfer Type
     */
    ExternalTransferType save(ExternalTransferType externalTransferType);

    /**
     * Validate the specified External Transfer Type
     */
    void validate(ExternalTransferType externalTransferType);

}
