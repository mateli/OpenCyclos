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
package nl.strohalm.cyclos.entities.accounts.transactions;

import java.math.BigDecimal;
import java.util.Calendar;

import nl.strohalm.cyclos.entities.Entity;
import nl.strohalm.cyclos.entities.Relationship;

/**
 * Represents a scheduled payment for invoice
 * @author luis
 */
public class InvoicePayment extends Entity {

    public static enum Relationships implements Relationship {
        INVOICE("invoice"), TRANSFER("transfer");
        private final String name;

        private Relationships(final String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    private static final long serialVersionUID = -2871353808455047476L;
    private Invoice           invoice;
    private BigDecimal        amount;
    private Calendar          date;
    private Transfer          transfer;

    public BigDecimal getAmount() {
        return amount;
    }

    public Calendar getDate() {
        return date;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public Transfer getTransfer() {
        return transfer;
    }

    public void setAmount(final BigDecimal amount) {
        this.amount = amount;
    }

    public void setDate(final Calendar date) {
        this.date = date;
    }

    public void setInvoice(final Invoice invoice) {
        this.invoice = invoice;
    }

    public void setTransfer(final Transfer transfer) {
        this.transfer = transfer;
    }

    @Override
    public String toString() {
        return getId() + " - " + invoice;
    }
}
