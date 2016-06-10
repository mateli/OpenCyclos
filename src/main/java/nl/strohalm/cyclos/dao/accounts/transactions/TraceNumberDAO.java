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

import java.util.Calendar;

import nl.strohalm.cyclos.dao.BaseDAO;
import nl.strohalm.cyclos.dao.DeletableDAO;
import nl.strohalm.cyclos.dao.InsertableDAO;
import nl.strohalm.cyclos.entities.accounts.transactions.TraceNumber;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;

public interface TraceNumberDAO extends BaseDAO<TraceNumber>, InsertableDAO<TraceNumber>, DeletableDAO<TraceNumber> {

    /**
     * Deletes the trace numbers before (or at) the specified date
     * @param c
     */
    void delete(Calendar upperBound);

    /**
     * Returns the TN entity with the given client id and trace number, or throws an {@link EntityNotFoundException} when the given combination does
     * not exist.
     */
    TraceNumber load(Long clientId, String traceNumber) throws EntityNotFoundException;
}
