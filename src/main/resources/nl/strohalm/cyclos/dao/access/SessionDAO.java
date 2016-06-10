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
import nl.strohalm.cyclos.dao.DeletableDAO;
import nl.strohalm.cyclos.dao.InsertableDAO;
import nl.strohalm.cyclos.entities.access.Session;
import nl.strohalm.cyclos.entities.access.SessionQuery;
import nl.strohalm.cyclos.entities.access.User;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.utils.query.IteratorList;

/**
 * DAO interface for sessions
 * 
 * @author luis
 */
public interface SessionDAO extends BaseDAO<Session>, InsertableDAO<Session>, DeletableDAO<Session> {

    /**
     * Deletes all sessions for the given user
     */
    int delete(User user);

    /**
     * Returns whether the given user is logged in
     */
    boolean isLoggedIn(User user);

    /**
     * Lists all users
     */
    IteratorList<User> listLoggedUsers();

    /**
     * Loads a session by sessionId
     */
    Session load(String sessionId, boolean allowExpired) throws EntityNotFoundException;

    /**
     * Deletes all expired sessions
     */
    void purgeExpired();

    /**
     * Searches for sessions according to the given parameters
     */
    List<Session> search(SessionQuery query);

    /**
     * Updates the expiration for the session with the given id
     */
    void updateExpiration(Long id, Calendar newExpiration);

}
