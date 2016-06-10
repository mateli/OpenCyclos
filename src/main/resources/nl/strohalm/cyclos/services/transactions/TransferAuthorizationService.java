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
package nl.strohalm.cyclos.services.transactions;

import java.util.List;

import nl.strohalm.cyclos.entities.accounts.transactions.Transfer;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferAuthorization;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferAuthorizationDTO;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferAuthorizationQuery;
import nl.strohalm.cyclos.entities.accounts.transactions.TransfersAwaitingAuthorizationQuery;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.entities.exceptions.UnexpectedEntityException;
import nl.strohalm.cyclos.services.Service;
import nl.strohalm.cyclos.services.transactions.exceptions.AlreadyAuthorizedException;

/**
 * Service used to handle autorized payments
 * 
 * @author luis
 */
public interface TransferAuthorizationService extends Service {

    /**
     * Authorizes a payment awaiting for authorization
     */
    Transfer authorize(TransferAuthorizationDTO transferAuthorizationDto) throws AlreadyAuthorizedException, EntityNotFoundException, UnexpectedEntityException;

    /**
     * Cancels a payment awaiting for authorization
     */
    Transfer cancel(TransferAuthorizationDTO transferAuthorizationDto) throws EntityNotFoundException, UnexpectedEntityException;

    /**
     * Cancels a payment awaiting for authorization
     */
    Transfer deny(TransferAuthorizationDTO transferAuthorizationDto) throws EntityNotFoundException, UnexpectedEntityException;

    /**
     * Returns whether the logged user has already authorized the given transfer
     */
    boolean hasAlreadyAuthorized(Transfer transfer);

    /**
     * Searches for transfer authorizations
     */
    List<TransferAuthorization> searchAuthorizations(TransferAuthorizationQuery query);

    /**
     * Searches for transfers awaiting authorization of the logged element
     */
    List<Transfer> searchTransfersAwaitingAuthorization(TransfersAwaitingAuthorizationQuery query);
}
