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
package nl.strohalm.cyclos.entities.accounts.transactions;

import java.util.Collection;
import java.util.Collections;

import nl.strohalm.cyclos.entities.accounts.Account;
import nl.strohalm.cyclos.entities.accounts.AccountType;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.utils.query.QueryParameters;

import org.apache.commons.collections.CollectionUtils;

/**
 * Parameters for payment filter queries
 * @author luis
 */
public class PaymentFilterQuery extends QueryParameters {
    public static enum Context {
        ACCOUNT_HISTORY, REPORT;
    }

    private static final long       serialVersionUID = -6321466109312785276L;
    private Account                 account;
    private Collection<AccountType> accountTypes;
    private Collection<MemberGroup> memberGroups;
    private Context                 context;
    private String                  description;
    private Element                 element;
    private String                  name;
    private AccountType.Nature      nature;

    public Account getAccount() {
        return account;
    }

    public AccountType getAccountType() {
        return CollectionUtils.isEmpty(accountTypes) ? null : accountTypes.iterator().next();
    }

    public Collection<AccountType> getAccountTypes() {
        return accountTypes;
    }

    public Context getContext() {
        return context;
    }

    public String getDescription() {
        return description;
    }

    public Element getElement() {
        return element;
    }

    public Collection<MemberGroup> getMemberGroups() {
        return memberGroups;
    }

    public String getName() {
        return name;
    }

    public AccountType.Nature getNature() {
        return nature;
    }

    public void setAccount(final Account account) {
        this.account = account;
    }

    public void setAccountType(final AccountType accountType) {
        accountTypes = accountType == null ? null : Collections.singletonList(accountType);
    }

    public void setAccountTypes(final Collection<AccountType> accountTypes) {
        this.accountTypes = accountTypes;
    }

    public void setContext(final Context context) {
        this.context = context;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public void setElement(final Element getter) {
        element = getter;
    }

    public void setMemberGroups(final Collection<MemberGroup> memberGroups) {
        this.memberGroups = memberGroups;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setNature(final AccountType.Nature nature) {
        this.nature = nature;
    }

}
