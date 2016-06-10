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

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Collection;

import nl.strohalm.cyclos.entities.Entity;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.transactions.Invoice;
import nl.strohalm.cyclos.entities.accounts.transactions.Transfer;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.utils.Amount;
import nl.strohalm.cyclos.utils.FormatObject;
import nl.strohalm.cyclos.utils.Period;

/**
 * An account fee log records an account fee execution (either manual or scheduled fees)
 * @author luis
 */
public class AccountFeeLog extends Entity {

    public static enum Relationships implements Relationship {
        ACCOUNT_FEE("accountFee"), TRANSFERS("transfers"), INVOICES("invoices"), PENDING_TO_CHARGE("pendingToCharge");
        private final String name;

        private Relationships(final String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }
    }

    private static final long    serialVersionUID = -1715437658356438694L;

    private AccountFee           accountFee;
    private Calendar             date;
    private Calendar             finishDate;
    private BigDecimal           freeBase;
    private Period               period;
    private BigDecimal           amount;
    private Integer              totalMembers;
    private int                  failedMembers;
    private boolean              rechargingFailed;
    private int                  rechargeAttempt;
    private Collection<Transfer> transfers;
    private Collection<Invoice>  invoices;
    private Collection<Member>   pendingToCharge;

    public AccountFeeLog() {
    }

    public AccountFee getAccountFee() {
        return accountFee;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Amount getAmountValue() {
        final Amount amount = new Amount();
        amount.setType(accountFee.getChargeMode().getAmountType());
        amount.setValue(this.amount);
        return amount;
    }

    public Calendar getDate() {
        return date;
    }

    public int getFailedMembers() {
        return failedMembers;
    }

    public Calendar getFinishDate() {
        return finishDate;
    }

    public BigDecimal getFreeBase() {
        return freeBase;
    }

    public Collection<Invoice> getInvoices() {
        return invoices;
    }

    public Collection<Member> getPendingToCharge() {
        return pendingToCharge;
    }

    public Period getPeriod() {
        return period;
    }

    public int getRechargeAttempt() {
        return rechargeAttempt;
    }

    public Integer getTotalMembers() {
        return totalMembers;
    }

    public Collection<Transfer> getTransfers() {
        return transfers;
    }

    public boolean isFinished() {
        return finishDate != null;
    }

    public boolean isRechargingFailed() {
        return rechargingFailed;
    }

    public boolean isRunning() {
        return finishDate == null;
    }

    public void setAccountFee(final AccountFee accountFee) {
        this.accountFee = accountFee;
    }

    public void setAmount(final BigDecimal value) {
        amount = value;
    }

    public void setDate(final Calendar date) {
        this.date = date;
    }

    public void setFailedMembers(final int failedMembers) {
        this.failedMembers = failedMembers;
    }

    public void setFinishDate(final Calendar finishDate) {
        this.finishDate = finishDate;
    }

    public void setFreeBase(final BigDecimal freeBase) {
        this.freeBase = freeBase;
    }

    public void setInvoices(final Collection<Invoice> invoices) {
        this.invoices = invoices;
    }

    public void setPendingToCharge(final Collection<Member> pendingToCharge) {
        this.pendingToCharge = pendingToCharge;
    }

    public void setPeriod(final Period period) {
        this.period = period;
    }

    public void setRechargeAttempt(final int rechargeAttempt) {
        this.rechargeAttempt = rechargeAttempt;
    }

    public void setRechargingFailed(final boolean rechargingFailed) {
        this.rechargingFailed = rechargingFailed;
    }

    public void setTotalMembers(final Integer totalMembers) {
        this.totalMembers = totalMembers;
    }

    public void setTransfers(final Collection<Transfer> transfers) {
        this.transfers = transfers;
    }

    @Override
    public String toString() {
        return getId() + " - " + accountFee + " at " + FormatObject.formatObject(date);
    }

}
