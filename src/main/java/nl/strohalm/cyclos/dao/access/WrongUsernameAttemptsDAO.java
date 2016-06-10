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

import nl.strohalm.cyclos.entities.access.WrongUsernameAttempt;

/**
 * DAO interface for wrong username attempts
 * @author luis
 */
public interface WrongUsernameAttemptsDAO {

    /**
     * Removes all traces prior to the given limit
     */
    void clear(Calendar limit);

    /**
     * Clears all wrong attempts for the given ip address
     */
    void clear(String remoteAddress);

    /**
     * Counts the wrong attempts for the given remote address after the given time limit
     */
    int count(Calendar limit, String remoteAddress);

    /**
     * Adds a wrong attempt for the given remote address, returning the current number of wrong attempts for it (including this one)
     */
    WrongUsernameAttempt record(String remoteAddress);

}
