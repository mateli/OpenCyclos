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
package nl.strohalm.cyclos.services.accounts;

import java.util.Calendar;

import nl.strohalm.cyclos.entities.accounts.Account;
import nl.strohalm.cyclos.entities.accounts.AccountOwner;
import nl.strohalm.cyclos.entities.accounts.AccountType;

/**
 * Holds an account and a date
 * 
 * @author luis
 */
public class AccountDateDTO extends AccountDTO {

    private static final long serialVersionUID = -7775249758648642519L;
    private Calendar          date;

    public AccountDateDTO() {
        super();
    }

    public AccountDateDTO(final Account account) {
        super(account);
    }

    public AccountDateDTO(final Account account, final Calendar date) {
        super(account);
        this.date = date;
    }

    public AccountDateDTO(final AccountOwner owner, final AccountType type) {
        super(owner, type);
    }

    public AccountDateDTO(final AccountOwner owner, final AccountType type, final Calendar date) {
        super(owner, type);
        this.date = date;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(final Calendar date) {
        this.date = date;
    }

}
