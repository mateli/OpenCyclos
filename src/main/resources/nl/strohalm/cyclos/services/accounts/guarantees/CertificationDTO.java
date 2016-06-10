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
package nl.strohalm.cyclos.services.accounts.guarantees;

import java.math.BigDecimal;

import nl.strohalm.cyclos.entities.accounts.guarantees.Certification;
import nl.strohalm.cyclos.utils.DataObject;

/**
 * DTO with additional certification data: used certification amount
 * @author ameyer
 * 
 */
public class CertificationDTO extends DataObject {

    private static final long serialVersionUID = -2988690490599325166L;

    private Certification     certification;
    private BigDecimal        usedAmount;

    public CertificationDTO(final Certification certification, final BigDecimal usedAmount) {
        super();
        this.certification = certification;
        this.usedAmount = usedAmount;
    }

    public Certification getCertification() {
        return certification;
    }

    public BigDecimal getUsedAmount() {
        return usedAmount;
    }
}
