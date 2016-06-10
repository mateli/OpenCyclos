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
import nl.strohalm.cyclos.entities.members.Administrator;

/**
 * Logs every change of either lower or upper credit limit on an account
 * 
 * @author luis
 */
public class AccountLimitLog extends Entity {

    private static final long serialVersionUID = 5214058723063900505L;
    private Account           account;
    private Administrator     by;
    private Calendar          date;
    private BigDecimal        creditLimit;
    private BigDecimal        upperCreditLimit;

    public Account getAccount() {
        return account;
    }

    public Administrator getBy() {
        return by;
    }

    public BigDecimal getCreditLimit() {
        return creditLimit;
    }

    public Calendar getDate() {
        return date;
    }

    public BigDecimal getUpperCreditLimit() {
        return upperCreditLimit;
    }

    public void setAccount(final Account account) {
        this.account = account;
    }

    public void setBy(final Administrator by) {
        this.by = by;
    }

    public void setCreditLimit(final BigDecimal creditLimit) {
        this.creditLimit = creditLimit;
    }

    public void setDate(final Calendar date) {
        this.date = date;
    }

    public void setUpperCreditLimit(final BigDecimal upperCreditLimit) {
        this.upperCreditLimit = upperCreditLimit;
    }

    @Override
    public String toString() {
        return getId() + ", Account: " + account + ", Limit: " + creditLimit + ", Upper limit: " + upperCreditLimit;
    }

}
