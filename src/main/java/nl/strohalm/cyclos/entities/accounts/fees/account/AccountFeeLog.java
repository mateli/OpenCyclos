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

import nl.strohalm.cyclos.entities.Entity;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.transactions.Invoice;
import nl.strohalm.cyclos.entities.accounts.transactions.Transfer;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.utils.Period;
import nl.strohalm.cyclos.utils.Amount;
import nl.strohalm.cyclos.utils.FormatObject;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Collection;

/**
 * An account fee log records an account fee execution (either manual or scheduled fees)
 * @author luis
 */
@Cacheable
@Table(name = "account_fee_logs")
@javax.persistence.Entity
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

    @ManyToOne
    @JoinColumn(name = "account_fee_id", nullable = false)
	private AccountFee           accountFee;

    @Column(name = "date", nullable = false)
    private Calendar             date;

    @Column(name = "finish_date")
    private Calendar             finishDate;

    @Column(name = "free_base", precision = 15, scale = 6)
    private BigDecimal           freeBase;

    @AttributeOverrides({
            @AttributeOverride(name = "begin", column=@Column(name="begin_date")),
            @AttributeOverride(name = "end", column=@Column(name="end_date"))
    })
    @Embedded
	private Period               period;

    @Column(name = "amount", nullable = false, precision = 15, scale = 6)
    private BigDecimal           amount;

    @Column(name = "total_members")
    private Integer              totalMembers;

    @Column(name = "failed_members", nullable = false)
    private int                  failedMembers;

    @Column(name = "recharging_failed", nullable = false)
    private boolean              rechargingFailed;

    @Column(name = "recharge_attempt", nullable = false)
    private int                  rechargeAttempt;

    @OneToMany(mappedBy = "accountFeeLog")
	private Collection<Transfer> transfers;

    @OneToMany(mappedBy = "accountFeeLog")
	private Collection<Invoice>  invoices;

    @ManyToMany
    @JoinTable(name = "members_pending_charge",
            joinColumns = @JoinColumn(name="account_fee_log_id"),
            inverseJoinColumns = @JoinColumn(name="member_id")
    )
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
