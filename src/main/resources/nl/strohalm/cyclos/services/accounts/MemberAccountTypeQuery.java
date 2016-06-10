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
import java.util.Collections;

import nl.strohalm.cyclos.entities.accounts.AccountOwner;
import nl.strohalm.cyclos.entities.accounts.AccountTypeQuery;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.members.Member;

/**
 * Query parameters for member account types
 * @author luis
 */
public class MemberAccountTypeQuery extends AccountTypeQuery {

    private static final long       serialVersionUID = 4576159781359784607L;
    private AccountOwner            canPay;
    private boolean                 priority;
    private Member                  owner;
    private Collection<MemberGroup> relatedToGroups;
    private Collection<MemberGroup> notRelatedToGroups;

    public AccountOwner getCanPay() {
        return canPay;
    }

    public MemberGroup getNotRelatedToGroup() {
        return (notRelatedToGroups == null || notRelatedToGroups.isEmpty()) ? null : notRelatedToGroups.iterator().next();
    }

    public Collection<MemberGroup> getNotRelatedToGroups() {
        return notRelatedToGroups;
    }

    public Member getOwner() {
        return owner;
    }

    public MemberGroup getRelatedToGroup() {
        return (relatedToGroups == null || relatedToGroups.isEmpty()) ? null : relatedToGroups.iterator().next();
    }

    public Collection<MemberGroup> getRelatedToGroups() {
        return relatedToGroups;
    }

    public boolean isPriority() {
        return priority;
    }

    public void setCanPay(final AccountOwner canPay) {
        this.canPay = canPay;
    }

    public void setNotRelatedToGroup(final MemberGroup notRelatedToGroup) {
        notRelatedToGroups = Collections.singleton(notRelatedToGroup);
    }

    public void setNotRelatedToGroups(final Collection<MemberGroup> notRelatedToGroups) {
        this.notRelatedToGroups = notRelatedToGroups;
    }

    public void setOwner(final Member owner) {
        this.owner = owner;
    }

    public void setPriority(final boolean priority) {
        this.priority = priority;
    }

    public void setRelatedToGroup(final MemberGroup relatedToGroup) {
        relatedToGroups = Collections.singleton(relatedToGroup);
    }

    public void setRelatedToGroups(final Collection<MemberGroup> relatedToGroups) {
        this.relatedToGroups = relatedToGroups;
    }

}
