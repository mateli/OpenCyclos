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
package nl.strohalm.cyclos.utils;

import java.sql.SQLException;

import nl.strohalm.cyclos.entities.exceptions.LockingException;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.hibernate.PessimisticLockException;
import org.hibernate.exception.LockAcquisitionException;

/**
 * Contains helper methods for exceptions
 * @author luis
 */
public class ExceptionHelper {
    // Mysql Error: 1205 SQLSTATE: HY000 (ER_LOCK_WAIT_TIMEOUT)
    private final static int    ER_LOCK_WAIT_TIMEOUT = 1205;

    private final static String ST_LOCK              = "40001";

    /**
     * Returns whether the given throwable represents a locking exception
     */
    public static boolean isLockingException(final Throwable t) {
        return isLockingException(t, true);
    }

    private static boolean isLockingException(final Throwable t, final boolean recurse) {
        if (t instanceof LockingException || t instanceof LockAcquisitionException || t instanceof PessimisticLockException) {
            return true;
        }
        if (t instanceof SQLException) {
            final SQLException e = (SQLException) t;
            return e.getErrorCode() == ER_LOCK_WAIT_TIMEOUT || ST_LOCK.equals(e.getSQLState());
        }
        if (recurse) {
            for (final Throwable thr : ExceptionUtils.getThrowables(t)) {
                if (isLockingException(thr, false)) {
                    return true;
                }
            }
        }
        return false;
    }

}
