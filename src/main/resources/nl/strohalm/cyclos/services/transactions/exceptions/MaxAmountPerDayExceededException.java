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
import java.util.Calendar;

import nl.strohalm.cyclos.entities.accounts.Account;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferType;

/**
 * Exception thrown when the maximum amount per day has been exceeded on a given account
 * @author luis
 */
public class MaxAmountPerDayExceededException extends CreditsException {

    private static final long  serialVersionUID = -4652970626499405382L;
    private final TransferType transferType;
    private final Calendar     date;

    public MaxAmountPerDayExceededException(final Calendar date, final TransferType transferType, final Account account, final BigDecimal transactionAmount) {
        super(account, transactionAmount);
        this.transferType = transferType;
        this.date = date;
    }

    public Calendar getDate() {
        return date;
    }

    public TransferType getTransferType() {
        return transferType;
    }

}
