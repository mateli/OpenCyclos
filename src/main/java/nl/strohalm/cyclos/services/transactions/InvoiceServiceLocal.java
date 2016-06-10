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

import java.util.Calendar;

import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.Currency;
import nl.strohalm.cyclos.entities.accounts.transactions.Invoice;
import nl.strohalm.cyclos.entities.accounts.transactions.InvoiceSummaryDTO;
import nl.strohalm.cyclos.entities.accounts.transactions.Payment;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.entities.exceptions.UnexpectedEntityException;
import nl.strohalm.cyclos.entities.reports.InvoiceSummaryType;

/**
 * Local interface. It must be used only from other services.
 */
public interface InvoiceServiceLocal extends InvoiceService {

    /**
     * Process expired invoices, generating a member alert for system to member invoices that expires today, according to the
     * AlertSettings.idleInvoiceExpiration
     * @return The number of expired invoices
     */
    public int alertExpiredSystemInvoices(Calendar time);

    /**
     * Expires all scheduled invoices which haven't been accepted and expired before today
     */
    void expireScheduledInvoices(Calendar time);

    /**
     * Returns a summary for the invoices
     */
    TransactionSummaryVO getSummary(InvoiceSummaryDTO dto);

    /**
     * Returns a summary for the invoices of a given type
     */
    TransactionSummaryVO getSummaryByType(Currency currency, InvoiceSummaryType invoiceSummaryType);

    /**
     * Loads an invoice using an associated payment
     * @throws EntityNotFoundException There's no invoice associated with the given payment
     */
    Invoice loadByPayment(Payment payment, Relationship... fetch) throws EntityNotFoundException;

    /**
     * Sends an invoice, without testing the logged member. Should be called from internal procedures that send invoice (like account fees)
     * @throws UnexpectedEntityException
     */
    Invoice sendAutomatically(Invoice invoice) throws UnexpectedEntityException;

}
