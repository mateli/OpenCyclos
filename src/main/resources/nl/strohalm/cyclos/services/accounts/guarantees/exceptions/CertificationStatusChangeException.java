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
package nl.strohalm.cyclos.services.accounts.guarantees.exceptions;

import nl.strohalm.cyclos.entities.accounts.guarantees.Certification;
import nl.strohalm.cyclos.exceptions.ApplicationException;

/**
 * Thrown when an invalid certification's status action was executed
 * @author ameyer
 * 
 */
public class CertificationStatusChangeException extends ApplicationException {

    private static final long    serialVersionUID = -8400887015925242700L;

    private Certification.Status newstatus;

    public CertificationStatusChangeException(final Certification.Status newstatus) {
        super();
        this.newstatus = newstatus;
    }

    public CertificationStatusChangeException(final Certification.Status newstatus, final String message) {
        super(message);
        this.newstatus = newstatus;
    }

    public CertificationStatusChangeException(final Certification.Status newstatus, final String message, final Throwable cause) {
        super(message, cause);
        this.newstatus = newstatus;
    }

    public CertificationStatusChangeException(final Certification.Status newstatus, final Throwable cause) {
        super(cause);
        this.newstatus = newstatus;
    }

    public Certification.Status getNewstatus() {
        return newstatus;
    }
}
