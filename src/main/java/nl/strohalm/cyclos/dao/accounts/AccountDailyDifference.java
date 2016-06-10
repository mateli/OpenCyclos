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
package nl.strohalm.cyclos.dao.accounts;

import java.math.BigDecimal;
import java.util.Calendar;

import nl.strohalm.cyclos.utils.DataObject;

/**
 * Contains a difference, by day, of either balance or reserved amount
 * 
 * @author luis
 */
public class AccountDailyDifference extends DataObject {

    private static final long serialVersionUID = -3804524886757493406L;
    private Calendar          day;
    private BigDecimal        balance;
    private BigDecimal        reserved;

    public BigDecimal getBalance() {
        return balance;
    }

    public Calendar getDay() {
        return day;
    }

    public BigDecimal getReserved() {
        return reserved;
    }

    public void setBalance(final BigDecimal balance) {
        this.balance = balance;
    }

    public void setDay(final Calendar day) {
        this.day = day;
    }

    public void setReserved(final BigDecimal reserved) {
        this.reserved = reserved;
    }

}
