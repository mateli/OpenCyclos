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
package nl.strohalm.cyclos.dao.access;

import java.util.Calendar;
import java.util.List;

import nl.strohalm.cyclos.dao.BaseDAO;
import nl.strohalm.cyclos.dao.InsertableDAO;
import nl.strohalm.cyclos.entities.access.LoginHistoryLog;
import nl.strohalm.cyclos.entities.access.User;
import nl.strohalm.cyclos.entities.exceptions.DaoException;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.entities.reports.StatisticalDTO;
import nl.strohalm.cyclos.utils.Pair;

public interface LoginHistoryDAO extends BaseDAO<LoginHistoryLog>, InsertableDAO<LoginHistoryLog> {

    public Calendar getFirstLoginHistoryDate();

    /**
     * gets a List with <code>User</code>s and their number of Logins. Used by Activity Stats > all using number of Logins
     * @param dto the value object containing the parameters for the query
     * @return a List with <code>Pair</code>s, where the first element represents the User, and the second element represents his number of logins.
     * @throws DaoException
     * @throws EntityNotFoundException
     */
    public List<Pair<User, Number>> list(StatisticalDTO dto) throws DaoException, EntityNotFoundException;

}
