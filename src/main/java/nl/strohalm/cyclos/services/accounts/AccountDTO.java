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

import nl.strohalm.cyclos.entities.accounts.Account;
import nl.strohalm.cyclos.entities.accounts.AccountOwner;
import nl.strohalm.cyclos.entities.accounts.AccountType;
import nl.strohalm.cyclos.utils.DataObject;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * DTO for retrieving an account or data related to an account
 * @author luis
 */
public class AccountDTO extends DataObject {
    private static final long serialVersionUID = -2140469861803171920L;
    private Account           account;
    private AccountOwner      owner;
    private AccountType       type;

    public AccountDTO() {
    }

    public AccountDTO(final Account account) {
        setAccount(account);
    }

    public AccountDTO(final AccountOwner owner, final AccountType type) {
        this.owner = owner;
        this.type = type;
    }

    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof AccountDTO)) {
            return false;
        }
        final AccountDTO dto = (AccountDTO) obj;
        if (owner == null || type == null || dto.owner == null || dto.type == null) {
            return false;
        }
        return new EqualsBuilder().append(owner, dto.owner).append(type.getId(), dto.type.getId()).isEquals();
    }

    public Account getAccount() {
        return account;
    }

    public AccountOwner getOwner() {
        return owner;
    }

    public AccountType getType() {
        return type;
    }

    @Override
    public int hashCode() {
        if (type == null) {
            return super.hashCode();
        }
        return new HashCodeBuilder().append(owner).append(type.getId()).toHashCode();
    }

    public void setAccount(final Account account) {
        this.account = account;
        if (account != null) {
            setOwner(account.getOwner());
            setType(account.getType());
        }
    }

    public void setOwner(final AccountOwner owner) {
        this.owner = owner;
    }

    public void setType(final AccountType type) {
        this.type = type;
    }
}
