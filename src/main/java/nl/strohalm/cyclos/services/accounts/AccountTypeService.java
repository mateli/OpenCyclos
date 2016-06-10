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

import java.util.Collection;
import java.util.List;

import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.AccountType;
import nl.strohalm.cyclos.entities.accounts.AccountTypeQuery;
import nl.strohalm.cyclos.entities.accounts.MemberAccountType;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.services.Service;

/**
 * Service interface for account types
 * @author luis
 */
public interface AccountTypeService extends Service {
    /**
     * Returns the default account type for a given group, with the given relationships initialized
     */
    MemberAccountType getDefault(MemberGroup group, Relationship... fetch);

    /**
     * Lists all account types
     */
    List<? extends AccountType> listAll();

    /**
     * Loads a collection of account types
     */
    Collection<AccountType> load(Collection<Long> ids);

    /**
     * Loads an account type
     */
    AccountType load(Long id);

    /**
     * Removes the specified account types. Also removes all related transfer types, payment filters, unless there are transactions from or to an
     * account of the given type. In that case, an exception is thrown.
     * @return The number of removed account types
     */
    int remove(Long... ids);

    /**
     * Inserts or updates the account type. When a is a system account type, there are some rules:
     * <ul>
     * <li>When inserting, should create a related system account, using the transient properties, creditLimit and upperCreditLimit, and setting the
     * type name as owner name</li>
     * <li>When updating, the account must be updated accordingly to the type. But if the account was created with creditLimit = null, it should never
     * be changed to a not-null value</li>
     * </ul>
     */
    <AT extends AccountType> AT save(AT accountType);

    /**
     * Searchs account types according to the given query
     */
    List<? extends AccountType> search(AccountTypeQuery query);

    /**
     * Validate the specified account type
     */
    void validate(AccountType accountType);

}
