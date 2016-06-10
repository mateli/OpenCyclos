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
package nl.strohalm.cyclos.dao.accounts.transactions;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nl.strohalm.cyclos.dao.BaseDAOImpl;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.AccountOwner;
import nl.strohalm.cyclos.entities.accounts.AccountType;
import nl.strohalm.cyclos.entities.accounts.MemberGroupAccountSettings;
import nl.strohalm.cyclos.entities.accounts.SystemAccountOwner;
import nl.strohalm.cyclos.entities.accounts.transactions.AuthorizationLevel.Authorizer;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferType;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferTypeQuery;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.entities.groups.AdminGroup;
import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.services.transactions.TransactionContext;
import nl.strohalm.cyclos.utils.hibernate.HibernateHelper;

import org.apache.commons.collections.CollectionUtils;

/**
 * Implementation class for transfer type DAO
 * @author rafael
 */
public class TransferTypeDAOImpl extends BaseDAOImpl<TransferType> implements TransferTypeDAO {

    public TransferTypeDAOImpl() {
        super(TransferType.class);
    }

    @Override
    public int delete(final boolean flush, final Long... ids) {
        // We must remove the many-to-many inverse relationships manually
        int rows = 0;
        for (final Long id : ids) {
            try {
                final TransferType transferType = load(id, TransferType.Relationships.GROUPS, TransferType.Relationships.GROUPS_AS_MEMBER);
                for (final Group group : transferType.getGroups()) {
                    group.getTransferTypes().remove(transferType);
                }
                for (final Group group : transferType.getGroupsAsMember()) {
                    group.getTransferTypes().remove(transferType);
                }

                // Ensure the initial credit relationship is removed
                bulkUpdate("update " + MemberGroupAccountSettings.class.getName() + " set initialCreditTransferType=null where (initialCredit is null or initialCredit = 0) and initialCreditTransferType=:tt", Collections.singletonMap("tt", transferType));

                getHibernateTemplate().delete(transferType);
                getHibernateTemplate().flush();
                rows++;
            } catch (final EntityNotFoundException e) {
                // Ignore
            }
        }
        return rows;
    }

    @Override
    public List<TransferType> search(final TransferTypeQuery query) {
        final Map<String, Object> namedParameters = new HashMap<String, Object>();
        namedParameters.put("system", AccountType.Nature.SYSTEM.getValue());
        final Set<Relationship> fetch = query.getFetch();
        final StringBuilder hql = HibernateHelper.getInitialQuery(getEntityType(), "tt", fetch);
        HibernateHelper.addLikeParameterToQuery(hql, namedParameters, "tt.description", query.getDescription());
        HibernateHelper.addLikeParameterToQuery(hql, namedParameters, "tt.name", query.getName());

        // Context
        final TransactionContext context = query.getContext();
        if (context != null && context != TransactionContext.ANY) {
            switch (context) {
                case PAYMENT:
                    hql.append(" and tt.loan.type is null and tt.context.payment=true ");
                    break;
                case SELF_PAYMENT:
                    hql.append(" and tt.loan.type is null and tt.context.selfPayment=true ");
                    break;
                case LOAN:
                    hql.append(" and tt.loan.type is not null and tt.context.payment=true ");
                    break;
                case AUTOMATIC:
                    hql.append(" and tt.loan.type is null ");
                    break;
                case AUTOMATIC_LOAN:
                    hql.append(" and tt.loan.type is not null ");
                    break;
            }
        }

        // Channel
        if (query.getChannel() != null) {
            hql.append(" and exists (select c.id from Channel c where c in elements(tt.channels) and c.internalName = :channel) ");
            namedParameters.put("channel", query.getChannel());
        }

        // Currency
        if (query.getCurrency() != null) {
            hql.append(" and tt.from.currency = :currency");
            namedParameters.put("currency", query.getCurrency());
        }

        // By another element
        if (query.getBy() != null) {
            final Element by = getFetchDao().fetch(query.getBy(), Element.Relationships.GROUP);
            final Group group = by.getGroup();
            hql.append(" and exists (select amtt.id from " + group.getClass().getName() + " g join g.transferTypesAsMember amtt where g = :byGroup and amtt = tt)");
            namedParameters.put("byGroup", group);
        }

        final AccountOwner fromOwner = query.getFromOwner();
        if (fromOwner != null) {
            if (fromOwner instanceof SystemAccountOwner) {
                // SystemAccountOwner is the same as SYSTEM nature, so, set the nature to handle it later
                query.setFromNature(AccountType.Nature.SYSTEM);
            } else {
                // Member is the same as setting the group
                final Member member = getFetchDao().fetch((Member) fromOwner, Element.Relationships.GROUP);
                query.setFromGroups(Collections.singleton(member.getMemberGroup()));
            }
        }

        final AccountOwner toOwner = query.getToOwner();
        if (toOwner != null) {
            if (toOwner instanceof SystemAccountOwner) {
                // SystemAccountOwner is the same as SYSTEM nature, so, set the nature to handle it later
                query.setToNature(AccountType.Nature.SYSTEM);
            } else {
                // Member is the same as setting the group, except that it may be to a fixed member
                final Member member = getFetchDao().fetch((Member) toOwner, Element.Relationships.GROUP);
                query.setToGroups(Collections.singleton(member.getMemberGroup()));
                hql.append(" and (tt.fixedDestinationMember = :toMember or tt.fixedDestinationMember is null) ");
                namedParameters.put("toMember", member);
            }
        }

        final AccountOwner fromOrToOwner = query.getFromOrToOwner();
        if (fromOrToOwner != null) {
            if (fromOrToOwner instanceof SystemAccountOwner) {
                // SystemAccountOwner is the same as SYSTEM nature, so, set the nature to handle it later
                query.setFromOrToNature(AccountType.Nature.SYSTEM);
            } else {
                // Member is the same as setting the group
                final Member member = getFetchDao().fetch((Member) fromOrToOwner, Element.Relationships.GROUP);
                query.setFromOrToGroups(Collections.singleton(member.getMemberGroup()));
            }
        }

        // Groups
        if (CollectionUtils.isNotEmpty(query.getFromGroups())) {
            hql.append(" and exists (select mgas.id from MemberGroupAccountSettings mgas where mgas.group in (:fromGroups) and mgas.accountType = tt.from) ");
            namedParameters.put("fromGroups", query.getFromGroups());
        }
        if (CollectionUtils.isNotEmpty(query.getToGroups())) {
            hql.append(" and exists (select mgas.id from MemberGroupAccountSettings mgas where mgas.group in (:toGroups) and mgas.accountType = tt.to) ");
            namedParameters.put("toGroups", query.getToGroups());
        }
        if (CollectionUtils.isNotEmpty(query.getFromOrToGroups())) {
            hql.append(" and exists (select mgas.id from MemberGroupAccountSettings mgas where mgas.group in (:fromOrToGroups) and (mgas.accountType = tt.from or mgas.accountType = tt.to)) ");
            namedParameters.put("fromOrToGroups", query.getFromOrToGroups());
        }

        // Nature
        if (query.getFromNature() != null) {
            hql.append(" and tt.from.class = :fromNature");
            namedParameters.put("fromNature", query.getFromNature().getValue());
        }
        if (query.getToNature() != null) {
            hql.append(" and tt.to.class = :toNature");
            namedParameters.put("toNature", query.getToNature().getValue());
        }
        if (query.getFromOrToNature() != null) {
            hql.append(" and (tt.from.class = :fromOrToNature or tt.to.class = :fromOrToNature)");
            namedParameters.put("fromOrToNature", query.getFromOrToNature().getValue());
        }

        // LimitType
        if (query.getFromLimitType() != null) {
            if (query.getFromLimitType().equals(AccountType.LimitType.UNLIMITED)) {
                hql.append(" and tt.from.account.creditLimit is null");
            } else {
                hql.append(" and tt.from.account.creditLimit is not null");
            }
        }
        if (query.getToLimitType() != null) {
            if (query.getToLimitType().equals(AccountType.LimitType.UNLIMITED)) {
                hql.append(" and tt.to.account.creditLimit is null");
            } else {
                hql.append(" and tt.to.account.creditLimit is not null");
            }
        }

        // Account types
        HibernateHelper.addInParameterToQuery(hql, namedParameters, "tt.from", query.getFromAccountTypes());
        HibernateHelper.addInParameterToQuery(hql, namedParameters, "tt.to", query.getToAccountTypes());
        final Collection<? extends AccountType> accountTypes = query.getFromOrToAccountTypes();
        if (accountTypes != null && !accountTypes.isEmpty()) {
            hql.append(" and (tt.to in (:fromOrToAT) or tt.from in (:fromOrToAT))");
            namedParameters.put("fromOrToAT", accountTypes);
        }

        // Group
        if (query.getGroup() != null) {
            hql.append(" and :group in elements(tt.groups)");
            namedParameters.put("group", query.getGroup());
        }

        // Flags
        if (query.isPriority()) {
            hql.append(" and tt.priority = true ");
        }
        if (query.isConciliable()) {
            hql.append(" and tt.conciliable = true ");
        }
        if (query.isAuthorizable()) {
            hql.append(" and tt.requiresAuthorization = true");
            // Test the authorizers
            final Collection<Authorizer> authorizers = query.getAuthorizers();
            if (CollectionUtils.isNotEmpty(authorizers)) {
                final AdminGroup authorizerGroup = query.getAuthorizerGroup();
                namedParameters.put("authorizerGroup", authorizerGroup);
                hql.append(" and (1<>1"); // This first condition is false, to not interfere on the OR conditions below
                for (final Authorizer authorizer : authorizers) {
                    final String name = "authorizer_" + authorizer.ordinal();
                    hql.append(" or exists (select l.id from AuthorizationLevel l where l.transferType = tt and l.authorizer = :" + name);
                    // It may also have an admin group for authorizations
                    if (authorizerGroup != null) {
                        hql.append(" and :authorizerGroup in elements(l.adminGroups)");
                    }
                    hql.append(")");
                    namedParameters.put(name, authorizer);
                }
                hql.append(")");
            }
        }
        if (query.isSchedulable()) {
            hql.append(" and tt.allowsScheduledPayments = true");
        }

        if (CollectionUtils.isNotEmpty(query.getPossibleTransferTypes())) {
            hql.append(" and tt in (:_possible)");
            namedParameters.put("_possible", query.getPossibleTransferTypes());
        }

        HibernateHelper.appendOrder(hql, "tt.name");
        return list(query, hql.toString(), namedParameters);
    }
}
