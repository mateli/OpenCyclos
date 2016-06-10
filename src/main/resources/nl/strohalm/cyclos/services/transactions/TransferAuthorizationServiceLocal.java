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

import java.util.Collection;

import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.transactions.Transfer;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferAuthorization;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferAuthorizationDTO;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.entities.exceptions.UnexpectedEntityException;
import nl.strohalm.cyclos.services.transactions.exceptions.AlreadyAuthorizedException;
import nl.strohalm.cyclos.utils.lock.LockHandler;

/**
 * Local interface. It must be used only from other services.
 */
public interface TransferAuthorizationServiceLocal extends TransferAuthorizationService {

    /**
     * Same as {@link #authorize(TransferAuthorizationDTO)}, but allowing to determine whether a new transaction is used
     */
    Transfer authorize(TransferAuthorizationDTO transferAuthorizationDto, boolean newTransaction) throws AlreadyAuthorizedException, EntityNotFoundException, UnexpectedEntityException;

    /**
     * Automatically called when a transfer is inserted to authorize instantly when the one making a payment as member is the one that authorizes
     */
    Transfer authorizeOnInsert(LockHandler lockHandler, Transfer transfer);

    /**
     * Returns whether the logged user can authorize or deny the given transfer
     */
    boolean canAuthorizeOrDeny(Transfer transfer);

    /**
     * Returns whether the logged user can cancel the given transfer that was submitted to authorization
     */
    boolean canCancel(Transfer transfer);

    /**
     * Loads a collection of transfer authorization fetching the relationships for each one.
     */
    Collection<TransferAuthorization> load(Collection<Long> ids, Relationship... fetch) throws EntityNotFoundException;

    /**
     * Loads a transfer authorization fetching the relationships
     */
    TransferAuthorization load(Long id, Relationship... fetch) throws EntityNotFoundException;
}
