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
package nl.strohalm.cyclos.entities.accounts;

import java.math.BigDecimal;
import java.util.Calendar;

import nl.strohalm.cyclos.entities.Entity;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.utils.FormatObject;

/**
 * Contains the pre-calculated balance and amount reservation for an account at a given date. The date is EXCLUSIVE: transfers which happened on the
 * exact time of the closing date are NOT included in this ClosedAccountBalance.
 * 
 * @author luis
 */
public class ClosedAccountBalance extends Entity {

    public static enum Relationships implements Relationship {
        ACCOUNT("account");
        private final String name;

        private Relationships(final String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }
    }

    private static final long serialVersionUID = -7158093358048047225L;
    private Account           account;
    private Calendar          date;
    private BigDecimal        balance          = BigDecimal.ZERO;
    private BigDecimal        reserved         = BigDecimal.ZERO;

    public Account getAccount() {
        return account;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public Calendar getDate() {
        return date;
    }

    public BigDecimal getReserved() {
        return reserved;
    }

    public void setAccount(final Account account) {
        this.account = account;
    }

    public void setBalance(final BigDecimal balance) {
        this.balance = balance;
    }

    public void setDate(final Calendar date) {
        this.date = date;
    }

    public void setReserved(final BigDecimal reserved) {
        this.reserved = reserved;
    }

    @Override
    public String toString() {
        return getId() + ", Account: " + account + ", date: " + FormatObject.formatArgument(date) + ", balance: " + balance + ", reserved: " + reserved;
    }

}
