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

import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.transactions.Invoice;
import nl.strohalm.cyclos.entities.accounts.transactions.InvoiceQuery;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferType;
import nl.strohalm.cyclos.entities.exceptions.UnexpectedEntityException;
import nl.strohalm.cyclos.exceptions.PermissionDeniedException;
import nl.strohalm.cyclos.services.Service;
import nl.strohalm.cyclos.services.transactions.exceptions.MaxAmountPerDayExceededException;
import nl.strohalm.cyclos.services.transactions.exceptions.NotEnoughCreditsException;
import nl.strohalm.cyclos.services.transactions.exceptions.UpperCreditLimitReachedException;

/**
 * Service interface for invoices (to member/broker and to system)
 * @author luis
 */
public interface InvoiceService extends Service {

    /**
     * Accepts the given invoice
     * @throws NotEnoughCreditsException No funds to accept the invoice
     * @throws UpperCreditLimitReachedException The receiver account would pass the upper limit
     * @throws MaxAmountPerDayExceededException The payer account has exceeded the maximum amount today for the invoice's transfer type
     * @throws UnexpectedEntityException The given invoice cannot be accepted (for example, is not pending)
     */
    Invoice accept(Invoice invoice) throws NotEnoughCreditsException, UpperCreditLimitReachedException, MaxAmountPerDayExceededException, UnexpectedEntityException;

    /**
     * Returns whether the logged user can accept the given invoice in behalf of the given member
     */
    boolean canAccept(Invoice invoice);

    /**
     * Returns whether the logged user can cancel the given invoice in behalf of the given member
     */
    boolean canCancel(Invoice invoice);

    /**
     * Cancels the given invoice
     * @throws UnexpectedEntityException When the invoice is invalid
     */
    Invoice cancel(Invoice invoice) throws UnexpectedEntityException, PermissionDeniedException;

    /**
     * Returns whether the logged user can deny the given invoice in behalf of the given member
     */
    boolean canDeny(Invoice invoice);

    /**
     * Denies the specified invoice
     * @throws UnexpectedEntityException When the invoice is invalid
     */
    Invoice deny(Invoice invoice) throws UnexpectedEntityException, PermissionDeniedException;

    /**
     * Returns the possible transfer types for the given invoice
     */
    List<TransferType> getPossibleTransferTypes(Invoice invoice);

    /**
     * Loads an invoice
     */
    Invoice load(final Long id, final Relationship... fetch);

    /**
     * Searches for invoices
     */
    List<Invoice> search(InvoiceQuery queryParameters);

    /**
     * Sends an invoice, returning the populated object
     */
    Invoice send(Invoice invoice) throws UnexpectedEntityException;

    /**
     * Validates the invoice
     */
    void validate(Invoice invoice);

    /**
     * Validates the invoice for accept
     */
    void validateForAccept(Invoice invoice);

}
