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
package nl.strohalm.cyclos.entities.accounts.fees.account;

import java.util.Collection;

import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.utils.query.QueryParameters;

/**
 * Parameters to search for charged members in an account fee log
 * @author luis
 */
public class MemberAccountFeeLogQuery extends QueryParameters {

    public static enum Status {
        ERROR, PROCESSED, SKIPPED, TRANSFER, INVOICE, OPEN_INVOICE, ACCEPTED_INVOICE
    }

    private static final long       serialVersionUID = 1L;

    private AccountFeeLog           accountFeeLog;
    private Collection<MemberGroup> groups;
    private Member                  member;
    private Status                  status;

    public AccountFeeLog getAccountFeeLog() {
        return accountFeeLog;
    }

    public Collection<MemberGroup> getGroups() {
        return groups;
    }

    public Member getMember() {
        return member;
    }

    public Status getStatus() {
        return status;
    }

    public void setAccountFeeLog(final AccountFeeLog accountFeeLog) {
        this.accountFeeLog = accountFeeLog;
    }

    public void setGroups(final Collection<MemberGroup> groups) {
        this.groups = groups;
    }

    public void setMember(final Member member) {
        this.member = member;
    }

    public void setStatus(final Status status) {
        this.status = status;
    }

}
