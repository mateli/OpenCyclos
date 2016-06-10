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
package nl.strohalm.cyclos.entities.groups;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

import nl.strohalm.cyclos.entities.accounts.MemberAccountType;
import nl.strohalm.cyclos.entities.accounts.transactions.PaymentFilter;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.utils.query.QueryParameters;

import org.apache.commons.collections.CollectionUtils;

/**
 * Query parameters for user groups. All parameters are optional. They are:
 * <ul>
 * <li>natures: Which group natures will be returned. They are not inherited, (ie: MEMBER nature do not return BROKERs)</li>
 * <li>status: Which group status (NORMAL or REMOVED) will be returned</li>
 * </ul>
 * @author luis
 */
public class GroupQuery extends QueryParameters {

    private static final long       serialVersionUID = -7827163668106032570L;

    private Collection<Group>       possibleGroups;
    private Collection<GroupFilter> groupFilters;
    private Group.Nature[]          natures;
    private Group.Status[]          status;
    private MemberAccountType       memberAccountType;
    private PaymentFilter           paymentFilter;
    private AdminGroup              managedBy;
    private Member                  member;
    private boolean                 ignoreManagedBy;
    private boolean                 sortByNature;
    private boolean                 onlyActive;
    private Member                  broker;

    public Member getBroker() {
        return broker;
    }

    public GroupFilter getGroupFilter() {
        return CollectionUtils.isNotEmpty(groupFilters) ? groupFilters.iterator().next() : null;
    }

    public Collection<GroupFilter> getGroupFilters() {
        return groupFilters;
    }

    public AdminGroup getManagedBy() {
        return managedBy;
    }

    public Member getMember() {
        return member;
    }

    public MemberAccountType getMemberAccountType() {
        return memberAccountType;
    }

    public Group.Nature getNature() {
        if (natures == null || natures.length == 0) {
            return null;
        }
        return natures[0];
    }

    public Collection<String> getNatureDiscriminators() {
        if (natures == null || natures.length == 0) {
            return null;
        }
        final Collection<String> discriminators = new HashSet<String>();
        for (final Group.Nature nature : natures) {
            discriminators.add(nature.getDiscriminator());
        }
        return discriminators;
    }

    public Group.Nature[] getNatures() {
        return natures;
    }

    public Collection<Group.Nature> getNaturesCollection() {
        return natures == null ? null : Arrays.asList(natures);
    }

    public Collection<Group.Nature> getNaturesList() {
        if (natures != null) {
            return Arrays.asList(natures);
        } else {
            return null;
        }
    }

    public PaymentFilter getPaymentFilter() {
        return paymentFilter;
    }

    public Collection<Group> getPossibleGroups() {
        return possibleGroups;
    }

    public Group.Status[] getStatus() {
        return status;
    }

    public Collection<Group.Status> getStatusCollection() {
        return status == null || status.length == 0 ? null : Arrays.asList(status);
    }

    public boolean isIgnoreManagedBy() {
        return ignoreManagedBy;
    }

    public boolean isOnlyActive() {
        return onlyActive;
    }

    public boolean isSortByNature() {
        return sortByNature;
    }

    public void setBroker(final Member broker) {
        this.broker = broker;
    }

    public void setGroupFilter(final GroupFilter groupFilter) {
        groupFilters = groupFilter == null ? null : Collections.singletonList(groupFilter);
    }

    public void setGroupFilters(final Collection<GroupFilter> groupFilters) {
        this.groupFilters = groupFilters;
    }

    public void setIgnoreManagedBy(final boolean ignoreManagedBy) {
        this.ignoreManagedBy = ignoreManagedBy;
    }

    public void setManagedBy(final AdminGroup managedBy) {
        this.managedBy = managedBy;
    }

    public void setMember(final Member member) {
        this.member = member;
    }

    public void setMemberAccountType(final MemberAccountType memberAccountType) {
        this.memberAccountType = memberAccountType;
    }

    public void setNature(final Group.Nature nature) {
        natures = nature == null ? null : new Group.Nature[] { nature };
    }

    public void setNatures(final Group.Nature... natures) {
        this.natures = natures;
    }

    public void setNaturesCollection(final Collection<Group.Nature> natures) {
        this.natures = natures == null ? null : natures.toArray(new Group.Nature[natures.size()]);
    }

    public void setOnlyActive(final boolean onlyActive) {
        this.onlyActive = onlyActive;
    }

    public void setPaymentFilter(final PaymentFilter paymentFilter) {
        this.paymentFilter = paymentFilter;
    }

    public void setPossibleGroups(final Collection<Group> possibleGroups) {
        this.possibleGroups = possibleGroups;
    }

    public void setSortByNature(final boolean sortByNature) {
        this.sortByNature = sortByNature;
    }

    public void setStatus(final Group.Status... status) {
        this.status = status;
    }

    public void setStatusCollection(final Collection<Group.Status> status) {
        this.status = status == null ? null : status.toArray(new Group.Status[status.size()]);
    }

}
