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
package nl.strohalm.cyclos.exceptions;

/**
 * Common class for all exceptions in cyclos
 * @author luis
 */
public abstract class ApplicationException extends RuntimeException {

    private static final long serialVersionUID = -2414811295910098928L;

    private boolean           shouldRollback   = true;

    public ApplicationException() {
        super();
    }

    public ApplicationException(final String message) {
        super(message);
    }

    public ApplicationException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public ApplicationException(final Throwable cause) {
        super(cause);
    }

    public boolean isShouldRollback() {
        return shouldRollback;
    }

    public ApplicationException noRollBack() {
        shouldRollback = false;
        return this;
    }

    public void setShouldRollback(final boolean shouldRollback) {
        this.shouldRollback = shouldRollback;
    }
}
