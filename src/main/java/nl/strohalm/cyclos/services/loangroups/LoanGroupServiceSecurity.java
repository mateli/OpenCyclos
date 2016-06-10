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
package nl.strohalm.cyclos.services.loangroups;

import java.util.Collections;
import java.util.List;

import nl.strohalm.cyclos.access.AdminMemberPermission;
import nl.strohalm.cyclos.access.AdminSystemPermission;
import nl.strohalm.cyclos.access.BrokerPermission;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.loans.LoanGroup;
import nl.strohalm.cyclos.entities.accounts.loans.LoanGroupQuery;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.exceptions.PermissionDeniedException;
import nl.strohalm.cyclos.services.BaseServiceSecurity;
import nl.strohalm.cyclos.services.loangroups.exceptions.MemberAlreadyInListException;
import nl.strohalm.cyclos.services.loangroups.exceptions.MemberNotInListException;
import nl.strohalm.cyclos.utils.access.LoggedUser;

import org.apache.commons.collections.CollectionUtils;

/**
 * Security implementation for {@link LoanGroupService}
 * 
 * @author jcomas
 */
public class LoanGroupServiceSecurity extends BaseServiceSecurity implements LoanGroupService {

    private LoanGroupServiceLocal loanGroupService;

    @Override
    public void addMember(final Member member, final LoanGroup loanGroup) throws MemberAlreadyInListException {
        permissionService.permission(member).admin(AdminMemberPermission.LOAN_GROUPS_MANAGE).check();
        loanGroupService.addMember(member, loanGroup);
    }

    @Override
    public LoanGroup load(final Long id, final Relationship... fetch) {
        permissionService.permission()
                .admin(AdminSystemPermission.LOAN_GROUPS_VIEW)
                .broker(BrokerPermission.LOAN_GROUPS_VIEW)
                .member()
                .check();
        LoanGroup loanGroup = loanGroupService.load(id, fetch);
        loanGroup = fetchService.fetch(loanGroup, LoanGroup.Relationships.MEMBERS);
        if (!CollectionUtils.isEmpty(loanGroup.getMembers())) {
            // At least the logged user needs to manage one of the members.
            boolean manages = false;
            for (Member m : loanGroup.getMembers()) {
                if (permissionService.manages(m)) {
                    manages = true;
                    break;
                }
            }
            if (!manages) {
                throw new PermissionDeniedException();
            }
        }

        return loanGroup;
    }

    @Override
    public int remove(final Long... ids) {
        permissionService.permission().admin(AdminSystemPermission.LOAN_GROUPS_MANAGE).check();
        return loanGroupService.remove(ids);
    }

    @Override
    public void removeMember(final Member member, final LoanGroup loanGroup) throws MemberNotInListException {
        permissionService.permission(member).admin(AdminMemberPermission.LOAN_GROUPS_MANAGE).check();
        loanGroupService.removeMember(member, loanGroup);
    }

    @Override
    public LoanGroup save(final LoanGroup loanGroup) {
        permissionService.permission().admin(AdminSystemPermission.LOAN_GROUPS_MANAGE).check();
        return loanGroupService.save(loanGroup);
    }

    @Override
    public List<LoanGroup> search(final LoanGroupQuery query) {
        if (!applyQueryRestrictions(query)) {
            return Collections.emptyList();
        }
        return loanGroupService.search(query);
    }

    public void setLoanGroupServiceLocal(final LoanGroupServiceLocal loanGroupService) {
        this.loanGroupService = loanGroupService;
    }

    @Override
    public void validate(final LoanGroup loanGroup) {
        // Nothing to check
        loanGroupService.validate(loanGroup);
    }

    private boolean applyQueryRestrictions(final LoanGroupQuery query) {
        if (!permissionService.permission()
                .admin(AdminSystemPermission.LOAN_GROUPS_VIEW, AdminMemberPermission.LOAN_GROUPS_VIEW)
                .broker(BrokerPermission.LOAN_GROUPS_VIEW)
                .member()
                .hasPermission()) {
            return false;
        }

        // Only administrators should use this flag in true.
        if (!LoggedUser.isAdministrator()) {
            query.setNotOfMember(false);
        }

        // Only administrators should use this attribute in null.
        if (query.getMember() == null && !LoggedUser.isAdministrator()) {
            return false;
        }

        // If there's a member, then it must be managed.
        if (query.getMember() != null && !permissionService.manages(query.getMember())) {
            return false;
        }

        return true;
    }

}
