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
package nl.strohalm.cyclos.entities.accounts.fees.account;

import nl.strohalm.cyclos.services.transactions.TransactionSummaryVO;
import nl.strohalm.cyclos.utils.DataObject;

/**
 * Contains details for an {@link AccountFeeLog}
 * @author luis
 */
public class AccountFeeLogDetailsDTO extends DataObject {

    private static final long    serialVersionUID = 1L;

    private AccountFeeLog        accountFeeLog;
    private int                  skippedMembers;
    private TransactionSummaryVO transfers;
    private TransactionSummaryVO invoices;
    private TransactionSummaryVO acceptedInvoices;
    private TransactionSummaryVO openInvoices;

    public TransactionSummaryVO getAcceptedInvoices() {
        return acceptedInvoices;
    }

    public AccountFeeLog getAccountFeeLog() {
        return accountFeeLog;
    }

    public TransactionSummaryVO getInvoices() {
        return invoices;
    }

    public TransactionSummaryVO getOpenInvoices() {
        return openInvoices;
    }

    public int getSkippedMembers() {
        return skippedMembers;
    }

    public TransactionSummaryVO getTransfers() {
        return transfers;
    }

    public void setAcceptedInvoices(final TransactionSummaryVO acceptedInvoices) {
        this.acceptedInvoices = acceptedInvoices;
    }

    public void setAccountFeeLog(final AccountFeeLog accountFeeLog) {
        this.accountFeeLog = accountFeeLog;
    }

    public void setInvoices(final TransactionSummaryVO invoices) {
        this.invoices = invoices;
    }

    public void setOpenInvoices(final TransactionSummaryVO openInvoices) {
        this.openInvoices = openInvoices;
    }

    public void setSkippedMembers(final int skippedMembers) {
        this.skippedMembers = skippedMembers;
    }

    public void setTransfers(final TransactionSummaryVO transfers) {
        this.transfers = transfers;
    }

}
