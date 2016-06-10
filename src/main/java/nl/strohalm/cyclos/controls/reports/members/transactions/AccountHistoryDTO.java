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
package nl.strohalm.cyclos.controls.reports.members.transactions;

import java.math.BigDecimal;
import java.util.List;

import nl.strohalm.cyclos.controls.accounts.details.AccountHistoryAction.Entry;
import nl.strohalm.cyclos.entities.accounts.AccountType;
import nl.strohalm.cyclos.services.transactions.TransactionSummaryVO;
import nl.strohalm.cyclos.utils.DataObject;

public class AccountHistoryDTO extends DataObject {

    private static final long    serialVersionUID = 7617029058111868104L;
    private AccountType          accountType;
    private BigDecimal           initialBalance;
    private BigDecimal           finalBalance;
    private TransactionSummaryVO debits;
    private TransactionSummaryVO credits;
    private List<Entry>          transfers;

    public AccountType getAccountType() {
        return accountType;
    }

    public TransactionSummaryVO getCredits() {
        return credits;
    }

    public TransactionSummaryVO getDebits() {
        return debits;
    }

    public BigDecimal getFinalBalance() {
        return finalBalance;
    }

    public BigDecimal getInitialBalance() {
        return initialBalance;
    }

    public List<Entry> getTransfers() {
        return transfers;
    }

    public void setAccountType(final AccountType accountType) {
        this.accountType = accountType;
    }

    public void setCredits(final TransactionSummaryVO credits) {
        this.credits = credits;
    }

    public void setDebits(final TransactionSummaryVO debits) {
        this.debits = debits;
    }

    public void setFinalBalance(final BigDecimal finalBalance) {
        this.finalBalance = finalBalance;
    }

    public void setInitialBalance(final BigDecimal initialBalance) {
        this.initialBalance = initialBalance;
    }

    public void setTransfers(final List<Entry> transfers) {
        this.transfers = transfers;
    }

}
