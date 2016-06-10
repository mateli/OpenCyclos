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
package nl.strohalm.cyclos.services.accounts.external;

import java.math.BigDecimal;
import java.util.Calendar;

import nl.strohalm.cyclos.entities.accounts.external.ExternalTransfer;
import nl.strohalm.cyclos.entities.accounts.loans.Loan;
import nl.strohalm.cyclos.entities.accounts.transactions.Transfer;
import nl.strohalm.cyclos.utils.DataObject;

/**
 * Parameters for processing an external transfer
 * @author luis
 */
public class ProcessExternalTransferDTO extends DataObject {
    private static final long serialVersionUID = 3927630671659487384L;
    private ExternalTransfer  externalTransfer;
    private Calendar          date;
    private BigDecimal        amount;
    private Transfer          transfer;
    private Loan              loan;

    public BigDecimal getAmount() {
        return amount;
    }

    public Calendar getDate() {
        return date;
    }

    public ExternalTransfer getExternalTransfer() {
        return externalTransfer;
    }

    public Loan getLoan() {
        return loan;
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

    public void setExternalTransfer(final ExternalTransfer externalTransfer) {
        this.externalTransfer = externalTransfer;
    }

    public void setLoan(final Loan loan) {
        this.loan = loan;
    }

    public void setTransfer(final Transfer transfer) {
        this.transfer = transfer;
    }
}
