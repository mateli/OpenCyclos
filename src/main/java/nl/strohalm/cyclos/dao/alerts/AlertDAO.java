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
package nl.strohalm.cyclos.dao.alerts;

import java.util.List;

import nl.strohalm.cyclos.dao.BaseDAO;
import nl.strohalm.cyclos.dao.DeletableDAO;
import nl.strohalm.cyclos.dao.InsertableDAO;
import nl.strohalm.cyclos.entities.alerts.Alert;
import nl.strohalm.cyclos.entities.alerts.AlertQuery;
import nl.strohalm.cyclos.entities.exceptions.DaoException;

/**
 * Interface for alert DAO. Note that AlertDAO extends DeletableDAO, but does not discard entities permanently, only marks them as removed in the
 * persistence layer.
 * @author rafael
 */
public interface AlertDAO extends BaseDAO<Alert>, InsertableDAO<Alert>, DeletableDAO<Alert> {

    /**
     * Returns the alert count for the given alert type. If any exception is thrown by the underlying implementation, it should be wrapped by a
     * DaoException.
     * 
     * @throws DaoException
     */
    public int getCount(Alert.Type type) throws DaoException;

    /**
     * Searches for alerts, ordering by date descending. If no entity can be found, returns an empty list. If any exception is thrown by the
     * underlying implementation, it should be wrapped by a DaoException.
     * 
     * @throws DaoException
     */
    public List<Alert> search(AlertQuery queryParameters) throws DaoException;

}
