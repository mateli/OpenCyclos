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
package nl.strohalm.cyclos.utils.lock;

import nl.strohalm.cyclos.utils.Pair;

/**
 * Handler to be used for each token
 * @author ameyer
 */
public interface UniqueObjectHandler {

    /**
     * Try to mark (non-blocking operation) for use the given lock key. This method is reentrant, that is, the same thread can repeatedly call this
     * method,and will always get true if the first time it returned true.
     * @return true Only if the key was not already marked as in use. Otherwise it returns false.
     */

    boolean tryAcquire(Pair<Object, Object> pair);
}
