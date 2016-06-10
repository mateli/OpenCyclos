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

import java.util.List;

import nl.strohalm.cyclos.dao.BaseDAO;
import nl.strohalm.cyclos.dao.InsertableDAO;
import nl.strohalm.cyclos.dao.UpdatableDAO;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.transactions.Ticket;
import nl.strohalm.cyclos.entities.accounts.transactions.TicketQuery;
import nl.strohalm.cyclos.entities.exceptions.DaoException;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;

/**
 * Data access object interface for transfers
 * @author rafael
 */
public interface TicketDAO extends BaseDAO<Ticket>, UpdatableDAO<Ticket>, InsertableDAO<Ticket> {

    /**
     * Returns true if there is a ticket with the specified <code>code</code>. Such parameter should be compared with the attribute
     * <code>Ticket.getTicket()</code>. If there's is no ticket with the given <code>code</code>, returns false. If any exception is thrown by the
     * underlying implementation, wrapped it in a DaoException.
     * 
     * @throws DaoException
     * 
     */
    boolean exists(String ticket) throws DaoException;

    /**
     * Retrieves the ticket identified by the attribute <code>code</code>, as returned by <code>Ticket.getTicket()</code>. If no ticket can be found,
     * throws EntityNotFoundException. If any exception is thrown by the underlying implementation, wrapped it in a DaoException.
     * 
     * @throws DaoException
     */
    <T extends Ticket> T load(String ticket, Relationship... fetch) throws EntityNotFoundException, DaoException;

    /**
     * Searches tickets given the query, ordering results by date descending. If no entity can be found, returns an empty list. If any exception is
     * thrown by the underlying implementation, it should be wrapped by a DaoException.
     */
    List<? extends Ticket> search(TicketQuery query);
}
