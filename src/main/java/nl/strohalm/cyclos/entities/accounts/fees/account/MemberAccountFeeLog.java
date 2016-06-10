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

import nl.strohalm.cyclos.entities.Entity;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.transactions.Invoice;
import nl.strohalm.cyclos.entities.accounts.transactions.Transfer;
import nl.strohalm.cyclos.entities.members.Member;

/**
 * Relates a member to an account fee log
 * 
 * @author luis
 */
public class MemberAccountFeeLog extends Entity {

    public static enum Relationships implements Relationship {
        ACCOUNT_FEE_LOG("accountFeeLog"), MEMBER("member"), TRANSFER("transfer"), INVOICE("invoice");
        private final String name;

        private Relationships(final String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }
    }

    public static enum Status {
        ERROR, SKIPPED, TRANSFER, OPEN_INVOICE, ACCEPTED_INVOICE
    }

    private static final long serialVersionUID = -3632964253062346212L;
    private Calendar          date;
    private boolean           success;
    private int               rechargeAttempt;
    private Member            member;
    private BigDecimal        amount;
    private AccountFeeLog     accountFeeLog;
    private Transfer          transfer;
    private Invoice           invoice;

    public AccountFeeLog getAccountFeeLog() {
        return accountFeeLog;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Calendar getDate() {
        return date;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public Member getMember() {
        return member;
    }

    public int getRechargeAttempt() {
        return rechargeAttempt;
    }

    public Status getStatus() {
        if (!success) {
            return Status.ERROR;
        } else if (transfer != null) {
            return Status.TRANSFER;
        } else if (invoice != null) {
            return invoice.getStatus() == Invoice.Status.ACCEPTED ? Status.ACCEPTED_INVOICE : Status.OPEN_INVOICE;
        } else {
            return Status.SKIPPED;
        }
    }

    public Transfer getTransfer() {
        return transfer;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setAccountFeeLog(final AccountFeeLog accountFeeLog) {
        this.accountFeeLog = accountFeeLog;
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

    public void setMember(final Member member) {
        this.member = member;
    }

    public void setRechargeAttempt(final int rechargeAttempt) {
        this.rechargeAttempt = rechargeAttempt;
    }

    public void setSuccess(final boolean success) {
        this.success = success;
    }

    public void setTransfer(final Transfer transfer) {
        this.transfer = transfer;
    }

    @Override
    public String toString() {
        return getId() + ", log: " + accountFeeLog + ", member: " + member;
    }

}
