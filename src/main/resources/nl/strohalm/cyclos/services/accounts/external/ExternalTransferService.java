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

import java.util.Collection;
import java.util.List;

import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.external.ExternalTransfer;
import nl.strohalm.cyclos.entities.accounts.external.ExternalTransferAction;
import nl.strohalm.cyclos.entities.accounts.external.ExternalTransferQuery;
import nl.strohalm.cyclos.entities.exceptions.UnexpectedEntityException;
import nl.strohalm.cyclos.services.Service;

/**
 * Service interface for handling external transfers
 * @author luis, Jefferson Magno
 */
public interface ExternalTransferService extends Service {

    /**
     * Loads an external transfer by id
     */
    ExternalTransfer load(Long id, Relationship... fetch);

    /**
     * Perform a given action on multiple external transfers
     */
    void performAction(ExternalTransferAction action, Long... ids);

    /**
     * Process the given external transfers
     * @throws UnexpectedEntityException When one of the the processing parameters are inconsistent
     */
    int process(Collection<ProcessExternalTransferDTO> dtos) throws UnexpectedEntityException;

    /**
     * Saves the external transfer
     */
    ExternalTransfer save(ExternalTransfer fieldMapping);

    /**
     * Searches for external transfers
     */
    List<ExternalTransfer> search(ExternalTransferQuery query);

    /**
     * Validate the specified external transfer
     */
    void validate(ExternalTransfer externalTransfer);
}
