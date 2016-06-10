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
package nl.strohalm.cyclos.services.transactions.exceptions;

import java.math.BigDecimal;

import nl.strohalm.cyclos.exceptions.ApplicationException;

/**
 * Exception thrown when an user attempts to repay a loan with incomplete interests amount. Example: payment amount = 230, being 200 the base amount,
 * 20 the grant fee and 10 the monthly interests. Partial amounts are allowed up to 200. The whole interests amount (30) must be repaid together.
 * @author luis
 */
public class PartialInterestsAmountException extends ApplicationException {

    private static final long serialVersionUID = 596952476036455629L;

    private final BigDecimal  interestsAmount;
    private final BigDecimal  baseRemainingAmount;

    public PartialInterestsAmountException(final BigDecimal baseRemainingAmount, final BigDecimal interestsAmount) {
        this.baseRemainingAmount = baseRemainingAmount;
        this.interestsAmount = interestsAmount;
    }

    public BigDecimal getBaseRemainingAmount() {
        return baseRemainingAmount;
    }

    public BigDecimal getInterestsAmount() {
        return interestsAmount;
    }

}
