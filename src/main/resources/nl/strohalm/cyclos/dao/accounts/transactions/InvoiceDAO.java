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
package nl.strohalm.cyclos.dao.accounts.transactions;

import java.util.Collection;
import java.util.List;

import nl.strohalm.cyclos.dao.BaseDAO;
import nl.strohalm.cyclos.dao.InsertableDAO;
import nl.strohalm.cyclos.dao.UpdatableDAO;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.Currency;
import nl.strohalm.cyclos.entities.accounts.transactions.Invoice;
import nl.strohalm.cyclos.entities.accounts.transactions.InvoiceQuery;
import nl.strohalm.cyclos.entities.accounts.transactions.InvoiceSummaryDTO;
import nl.strohalm.cyclos.entities.accounts.transactions.Payment;
import nl.strohalm.cyclos.entities.exceptions.DaoException;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.reports.InvoiceSummaryType;
import nl.strohalm.cyclos.services.transactions.TransactionSummaryVO;

/**
 * Data access object interface for invoices
 * @author rafael
 */
public interface InvoiceDAO extends BaseDAO<Invoice>, UpdatableDAO<Invoice>, InsertableDAO<Invoice> {

    /**
     * Returns a summary for the invoices. Any exception thrown by the underlying implementation should be wrapped by a DaoException.
     * 
     * @throws DaoException
     */
    TransactionSummaryVO getSummary(InvoiceSummaryDTO dto) throws DaoException;

    /**
     * Returns the summary (count and amount) of the invoices of a given type
     * @param currency The currency
     * @param invoiceSummaryType the type of the invoice summary
     * @return the count and amount of invoices of a given type
     */
    TransactionSummaryVO getSummaryByType(Currency currency, InvoiceSummaryType invoiceSummaryType, Collection<MemberGroup> memberGroups);

    /**
     * Loads an invoice associated with the given payment
     * @throws EntityNotFoundException There's no invoice associated with the given payment
     */
    Invoice loadByPayment(Payment payment, Relationship... fetch) throws EntityNotFoundException;

    /**
     * Searches for invoices accordingly the parameters provided by <code>queryParameters</code>, ordering results by date descending. The direction
     * and owner / relatedOwner parameters can only be used together If no invoice can be found, returns an empty List. Any exception thrown by the
     * underlying implementation should be wrapped by a DaoException.
     * 
     * @throws DaoException
     */
    List<Invoice> search(InvoiceQuery queryParameters) throws DaoException;

}
