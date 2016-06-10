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

import nl.strohalm.cyclos.entities.accounts.AccountOwner;
import nl.strohalm.cyclos.entities.accounts.AccountType;
import nl.strohalm.cyclos.entities.accounts.Currency;
import nl.strohalm.cyclos.entities.accounts.transactions.AuthorizationLevel.Authorizer;
import nl.strohalm.cyclos.entities.groups.AdminGroup;
import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.services.transactions.TransactionContext;
import nl.strohalm.cyclos.utils.query.QueryParameters;

/**
 * Parameters for transfer type searches
 * @author luis
 */
public class TransferTypeQuery extends QueryParameters {

    private static final long                 serialVersionUID = 5512738126774682465L;
    private TransactionContext                context;
    private String                            name;
    private String                            description;
    private String                            channel;
    private Currency                          currency;
    private AccountOwner                      fromOwner;
    private AccountOwner                      toOwner;
    private AccountOwner                      fromOrToOwner;
    private Collection<? extends AccountType> fromAccountTypes;
    private Collection<? extends AccountType> toAccountTypes;
    private Collection<? extends AccountType> fromOrToAccountTypes;
    private Collection<MemberGroup>           fromGroups;
    private Collection<MemberGroup>           toGroups;
    private Collection<MemberGroup>           fromOrToGroups;
    private AccountType.Nature                toNature;
    private AccountType.Nature                fromOrToNature;
    private AccountType.Nature                fromNature;
    private AccountType.LimitType             toLimitType;
    private AccountType.LimitType             fromLimitType;
    private Group                             group;
    private Element                           by;
    private boolean                           usePriority;
    private boolean                           priority;
    private boolean                           conciliable;
    private boolean                           authorizable;
    private Collection<Authorizer>            authorizers;
    private AdminGroup                        authorizerGroup;
    private boolean                           schedulable;
    // contains the list of all possible transfer type if the query has no filters
    private Collection<TransferType>          possibleTransferTypes;

    public AdminGroup getAuthorizerGroup() {
        return authorizerGroup;
    }

    public Collection<Authorizer> getAuthorizers() {
        return authorizers;
    }

    public Element getBy() {
        return by;
    }

    public String getChannel() {
        return channel;
    }

    public TransactionContext getContext() {
        return context;
    }

    public Currency getCurrency() {
        return currency;
    }

    public String getDescription() {
        return description;
    }

    public AccountType getFromAccountType() {
        return (fromAccountTypes == null || fromAccountTypes.isEmpty()) ? null : fromAccountTypes.iterator().next();
    }

    public Collection<? extends AccountType> getFromAccountTypes() {
        return fromAccountTypes;
    }

    public Collection<MemberGroup> getFromGroups() {
        return fromGroups;
    }

    public AccountType.LimitType getFromLimitType() {
        return fromLimitType;
    }

    public AccountType.Nature getFromNature() {
        return fromNature;
    }

    public AccountType getFromOrToAccountType() {
        return (fromOrToAccountTypes == null || fromOrToAccountTypes.isEmpty()) ? null : fromOrToAccountTypes.iterator().next();
    }

    public Collection<? extends AccountType> getFromOrToAccountTypes() {
        return fromOrToAccountTypes;
    }

    public Collection<MemberGroup> getFromOrToGroups() {
        return fromOrToGroups;
    }

    public AccountType.Nature getFromOrToNature() {
        return fromOrToNature;
    }

    public AccountOwner getFromOrToOwner() {
        return fromOrToOwner;
    }

    public AccountOwner getFromOwner() {
        return fromOwner;
    }

    public Group getGroup() {
        return group;
    }

    public String getName() {
        return name;
    }

    public Collection<TransferType> getPossibleTransferTypes() {
        return possibleTransferTypes;
    }

    public AccountType getToAccountType() {
        return (toAccountTypes == null || toAccountTypes.isEmpty()) ? null : toAccountTypes.iterator().next();
    }

    public Collection<? extends AccountType> getToAccountTypes() {
        return toAccountTypes;
    }

    public Collection<MemberGroup> getToGroups() {
        return toGroups;
    }

    public AccountType.LimitType getToLimitType() {
        return toLimitType;
    }

    public AccountType.Nature getToNature() {
        return toNature;
    }

    public AccountOwner getToOwner() {
        return toOwner;
    }

    public boolean isAuthorizable() {
        return authorizable;
    }

    public boolean isConciliable() {
        return conciliable;
    }

    public boolean isPriority() {
        return priority;
    }

    public boolean isSchedulable() {
        return schedulable;
    }

    public boolean isUsePriority() {
        return usePriority;
    }

    public void setAuthorizable(final boolean authorizable) {
        this.authorizable = authorizable;
    }

    public void setAuthorizerGroup(final AdminGroup authorizerGroup) {
        this.authorizerGroup = authorizerGroup;
    }

    public void setAuthorizers(final Collection<Authorizer> authorizers) {
        this.authorizers = authorizers;
    }

    public void setBy(final Element by) {
        this.by = by;
    }

    public void setChannel(final String channel) {
        this.channel = channel;
    }

    public void setConciliable(final boolean conciliable) {
        this.conciliable = conciliable;
    }

    public void setContext(final TransactionContext context) {
        this.context = context;
    }

    public void setCurrency(final Currency currency) {
        this.currency = currency;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public void setFromAccountType(final AccountType fromAccountType) {
        if (fromAccountType == null) {
            fromAccountTypes = null;
        } else {
            fromAccountTypes = Collections.singleton(fromAccountType);
        }
    }

    public void setFromAccountTypes(final Collection<? extends AccountType> fromAccountTypes) {
        this.fromAccountTypes = fromAccountTypes;
    }

    public void setFromGroups(final Collection<MemberGroup> fromGroups) {
        this.fromGroups = fromGroups;
    }

    public void setFromLimitType(final AccountType.LimitType fromLimitType) {
        this.fromLimitType = fromLimitType;
    }

    public void setFromNature(final AccountType.Nature fromNature) {
        this.fromNature = fromNature;
    }

    public void setFromOrToAccountType(final AccountType fromOrToAccountType) {
        if (fromOrToAccountType == null) {
            fromOrToAccountTypes = null;
        } else {
            fromOrToAccountTypes = Collections.singleton(fromOrToAccountType);
        }
    }

    public void setFromOrToAccountTypes(final Collection<? extends AccountType> fromOrToAccountTypes) {
        this.fromOrToAccountTypes = fromOrToAccountTypes;
    }

    public void setFromOrToGroups(final Collection<MemberGroup> fromOrToGroups) {
        this.fromOrToGroups = fromOrToGroups;
    }

    public void setFromOrToNature(final AccountType.Nature fromOrToNature) {
        this.fromOrToNature = fromOrToNature;
    }

    public void setFromOrToOwner(final AccountOwner fromOrToOwner) {
        this.fromOrToOwner = fromOrToOwner;
    }

    public void setFromOwner(final AccountOwner from) {
        fromOwner = from;
    }

    public void setGroup(final Group group) {
        this.group = group;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setPossibleTransferTypes(final Collection<TransferType> possibleTransferTypes) {
        this.possibleTransferTypes = possibleTransferTypes;
    }

    public void setPriority(final boolean priority) {
        this.priority = priority;
    }

    public void setSchedulable(final boolean schedulable) {
        this.schedulable = schedulable;
    }

    public void setToAccountType(final AccountType toAccountType) {
        if (toAccountType == null) {
            toAccountTypes = null;
        } else {
            toAccountTypes = Collections.singleton(toAccountType);
        }
    }

    public void setToAccountTypes(final Collection<? extends AccountType> toAccountTypes) {
        this.toAccountTypes = toAccountTypes;
    }

    public void setToGroups(final Collection<MemberGroup> toGroups) {
        this.toGroups = toGroups;
    }

    public void setToLimitType(final AccountType.LimitType toLimitType) {
        this.toLimitType = toLimitType;
    }

    public void setToNature(final AccountType.Nature toNature) {
        this.toNature = toNature;
    }

    public void setToOwner(final AccountOwner to) {
        toOwner = to;
    }

    public void setUsePriority(final boolean usePriority) {
        this.usePriority = usePriority;
    }

}
