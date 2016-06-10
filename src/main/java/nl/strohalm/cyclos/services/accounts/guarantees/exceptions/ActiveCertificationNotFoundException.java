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

import nl.strohalm.cyclos.entities.accounts.Currency;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.exceptions.ApplicationException;

/**
 * Throws when there isn't an active certification for the buyer, issuer and currency specified
 * @author ameyer
 * 
 */
public class ActiveCertificationNotFoundException extends ApplicationException {
    private static final long serialVersionUID = -5711673337986754711L;
    private Member            buyer;
    private Member            issuer;
    private Currency          currency;

    public ActiveCertificationNotFoundException() {
        super();
    }

    public ActiveCertificationNotFoundException(final Member buyer, final Member issuer, final Currency currency) {
        this.buyer = buyer;
        this.currency = currency;
        this.issuer = issuer;
    }

    public Member getBuyer() {
        return buyer;
    }

    public Currency getCurrency() {
        return currency;
    }

    public Member getIssuer() {
        return issuer;
    }
}
