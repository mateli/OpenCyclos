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

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Collection;
import java.util.Map;

import nl.strohalm.cyclos.entities.accounts.AccountType;
import nl.strohalm.cyclos.entities.accounts.MemberAccountType;
import nl.strohalm.cyclos.entities.accounts.SystemAccountType;
import nl.strohalm.cyclos.entities.groups.MemberGroup;

/**
 * Local interface. It must be used only from other services.
 */
public interface AccountTypeServiceLocal extends AccountTypeService {

    /**
     * Clears the account type cache
     */
    void clearCache();

    /**
     * Returns the sum of the balances of the accounts of the system accounts of the given types
     */
    Map<MemberAccountType, BigDecimal> getMemberAccountTypesBalance(Collection<MemberAccountType> types, Collection<MemberGroup> groups, Calendar timePoint);

    /**
     * Returns the sum of the balances of the accounts of the system accounts of the given types
     */
    Map<SystemAccountType, BigDecimal> getSystemAccountTypesBalance(Collection<SystemAccountType> types, Calendar timePoint);

    /**
     * Returns the account types which are visible by the logged user
     */
    Collection<AccountType> getVisibleAccountTypes();

    /**
     * Checks whether the given account type has any transfer types that requires authorization
     */
    boolean hasAuthorizedPayments(AccountType accountType);

}
