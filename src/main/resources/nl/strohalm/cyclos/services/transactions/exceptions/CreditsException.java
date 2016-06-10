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

import nl.strohalm.cyclos.entities.accounts.Account;

/**
 * Common exception for credit issues
 * @author luis
 */
public abstract class CreditsException extends PaymentException {

    private static final long serialVersionUID = 5856724550835550418L;
    private final Account     account;
    private final BigDecimal  transactionAmount;

    public CreditsException(final Account account, final BigDecimal transactionAmount) {
        this.account = account;
        this.transactionAmount = transactionAmount;
    }

    public Account getAccount() {
        return account;
    }

    public BigDecimal getTransactionAmount() {
        return transactionAmount;
    }

}
