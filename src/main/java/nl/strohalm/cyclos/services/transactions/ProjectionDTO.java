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
package nl.strohalm.cyclos.services.transactions;

import java.math.BigDecimal;
import java.util.Calendar;

import nl.strohalm.cyclos.entities.accounts.AccountOwner;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferType;
import nl.strohalm.cyclos.utils.DataObject;
import nl.strohalm.cyclos.utils.TimePeriod;

/**
 * Helper class for holding projection data
 */
public class ProjectionDTO extends DataObject {
    private static final long serialVersionUID = -7424637914667913913L;
    private AccountOwner      from;
    private BigDecimal        amount;
    private Calendar          date;
    private Calendar          firstExpirationDate;
    private int               paymentCount;
    private TransferType      transferType;
    private TimePeriod        recurrence       = TimePeriod.ONE_MONTH;

    public BigDecimal getAmount() {
        return amount;
    }

    public Calendar getDate() {
        return date;
    }

    public Calendar getFirstExpirationDate() {
        return firstExpirationDate;
    }

    public AccountOwner getFrom() {
        return from;
    }

    public int getPaymentCount() {
        return paymentCount;
    }

    public TimePeriod getRecurrence() {
        return recurrence;
    }

    public TransferType getTransferType() {
        return transferType;
    }

    public void setAmount(final BigDecimal amount) {
        this.amount = amount;
    }

    public void setDate(final Calendar date) {
        this.date = date;
    }

    public void setFirstExpirationDate(final Calendar firstExpirationDate) {
        this.firstExpirationDate = firstExpirationDate;
    }

    public void setFrom(final AccountOwner from) {
        this.from = from;
    }

    public void setPaymentCount(final int paymentCount) {
        this.paymentCount = paymentCount;
    }

    public void setRecurrence(final TimePeriod recurrence) {
        this.recurrence = recurrence;
    }

    public void setTransferType(final TransferType transferType) {
        this.transferType = transferType;
    }

}
