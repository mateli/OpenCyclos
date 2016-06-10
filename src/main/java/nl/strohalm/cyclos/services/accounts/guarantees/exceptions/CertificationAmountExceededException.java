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

import java.math.BigDecimal;

import nl.strohalm.cyclos.entities.accounts.guarantees.Certification;
import nl.strohalm.cyclos.exceptions.ApplicationException;

/**
 * Throws when the certification's amount is exceeded
 * @author ameyer
 * 
 */
public class CertificationAmountExceededException extends ApplicationException {
    private static final long serialVersionUID = -5641892490104839162L;

    /**
     * The total amount that exceed the certification's amount. Not the exceeded difference. It always exceeds the remainingCertificationAmount
     */
    private BigDecimal        totalExceededAmount;

    /**
     * This is the unused (remaining) certification's amount
     */
    private BigDecimal        remainingCertificationAmount;

    private Certification     certification;

    public CertificationAmountExceededException() {
        super();
    }

    public CertificationAmountExceededException(final Certification certification, final BigDecimal remainingCertificationAmount, final BigDecimal exceededAmount) {
        this.certification = certification;
        totalExceededAmount = exceededAmount;
        this.remainingCertificationAmount = remainingCertificationAmount;
    }

    public Certification getCertification() {
        return certification;
    }

    public BigDecimal getRemainingCertificationAmount() {
        return remainingCertificationAmount;
    }

    public BigDecimal getTotalExceededAmount() {
        return totalExceededAmount;
    }
}
