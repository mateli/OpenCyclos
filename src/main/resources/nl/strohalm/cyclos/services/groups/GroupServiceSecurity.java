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
package nl.strohalm.cyclos.services.groups;

import java.util.Collection;
import java.util.List;

import nl.strohalm.cyclos.access.AdminMemberPermission;
import nl.strohalm.cyclos.access.AdminSystemPermission;
import nl.strohalm.cyclos.access.MemberPermission;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.MemberAccountType;
import nl.strohalm.cyclos.entities.accounts.MemberGroupAccountSettings;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.entities.exceptions.UnexpectedEntityException;
import nl.strohalm.cyclos.entities.groups.AdminGroup;
import nl.strohalm.cyclos.entities.groups.BrokerGroup;
import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.entities.groups.GroupFilter;
import nl.strohalm.cyclos.entities.groups.GroupQuery;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.groups.OperatorGroup;
import nl.strohalm.cyclos.entities.groups.SystemGroup;
import nl.strohalm.cyclos.exceptions.PermissionDeniedException;
import nl.strohalm.cyclos.services.BaseServiceSecurity;
import nl.strohalm.cyclos.utils.access.LoggedUser;
import nl.strohalm.cyclos.utils.validation.ValidationException;

import org.apache.commons.lang.StringUtils;

/**
 * Security implementation for {@link GroupService}
 * 
 * @author luis
 */
public class GroupServiceSecurity extends BaseServiceSecurity implements GroupService {

    private GroupServiceLocal groupService;

    @Override
    public int countPendingAccounts(final MemberGroup group, final MemberAccountType accountType) {
        permissionService.checkManages(group);
        return groupService.countPendingAccounts(group, accountType);
    }

    @Override
    public SystemGroup findByLoginPageName(final String loginPageName) {
        // No permission check, as this method is invoked before the user logs in
        return groupService.findByLoginPageName(loginPageName);
    }

    @Override
    public List<? extends MemberGroup> getPossibleInitialGroups(final GroupFilter groupFilter) {
        // No permission check, as this method is invoked before the user logs in
        return groupService.getPossibleInitialGroups(groupFilter);
    }

    @Override
    public <G extends Group> G insert(final G group, final G baseGroup) {
        checkManage(group);
        return groupService.insert(group, baseGroup);
    }

    @Override
    public MemberGroupAccountSettings insertAccountSettings(final MemberGroupAccountSettings settings) throws UnexpectedEntityException {
        checkManageAccountSettings();
        return groupService.insertAccountSettings(settings);
    }

    @Override
    public <T extends Group> Collection<T> load(final Collection<Long> ids, final Relationship... fetch) {
        Collection<T> groups = groupService.load(ids, fetch);
        for (Group group : groups) {
            checkView(group);
        }
        return groups;
    }

    @Override
    public <T extends Group> T load(final Long id, final Relationship... fetch) {
        T group = groupService.<T> load(id, fetch);
        checkView(group);
        return group;
    }

    @Override
    public MemberGroupAccountSettings loadAccountSettings(final long groupId, final long accountTypeId, final Relationship... fetch) {
        Group group = groupService.load(groupId);
        permissionService.checkManages(group);
        return groupService.loadAccountSettings(groupId, accountTypeId, fetch);
    }

    @Override
    public <T extends Group> T reload(final Long id, final Relationship... fetch) {
        T group = groupService.<T> reload(id, fetch);
        checkView(group);
        return group;
    }

    @Override
    public void remove(final Long id) throws EntityNotFoundException {
        Group group = groupService.load(id);
        checkManage(group);
        groupService.remove(id);
    }

    @Override
    public void removeAccountTypeRelationship(final MemberGroup group, final MemberAccountType type) {
        checkManageAccountSettings();
        groupService.removeAccountTypeRelationship(group, type);
    }

    @Override
    public List<? extends Group> search(final GroupQuery query) {
        // As a quick way to only return visible groups, this was added. Works for groups, as we have a controlled number of records
        query.setPossibleGroups(permissionService.getAllVisibleGroups());
        return groupService.search(query);
    }

    public void setGroupServiceLocal(final GroupServiceLocal groupService) {
        this.groupService = groupService;
    }

    @Override
    public <G extends Group> G setPermissions(final GroupPermissionsDTO<G> permissions) {
        G group = fetchService.fetch(permissions.getGroup());
        checkManage(group);
        return groupService.setPermissions(permissions);
    }

    @Override
    public <G extends Group> G update(final G group, final boolean forceMembersToAcceptAgreement) {
        checkManage(group);
        return groupService.update(group, forceMembersToAcceptAgreement);
    }

    @Override
    public MemberGroupAccountSettings updateAccountSettings(final MemberGroupAccountSettings settings, final boolean updateAccountLimits) {
        checkManageAccountSettings();
        return groupService.updateAccountSettings(settings, updateAccountLimits);
    }

    @Override
    public boolean usesPin(final MemberGroup group) {
        permissionService.checkManages(group);
        return groupService.usesPin(group);
    }

    @Override
    public void validate(final Group group) throws ValidationException {
        // No permission check needed for validate
        groupService.validate(group);
    }

    @Override
    public void validate(final MemberGroupAccountSettings settings) throws ValidationException {
        // No permission check needed for validate
        groupService.validate(settings);
    }

    private boolean canView(final Group group) {
        switch (group.getNature()) {
            case ADMIN:
                // Admin groups are visible if either has the permission to view groups or to view other admins
                if (hasPermission(AdminSystemPermission.ADMIN_GROUPS_VIEW)) {
                    return true;
                }
                break;
            case BROKER:
            case MEMBER:
                // Member / operator groups are visible either with the view permission or if within the permissionService.getVisibleMemberGroups()
                if (LoggedUser.hasUser() && hasPermission(AdminMemberPermission.GROUPS_VIEW)) {
                    return true;
                }
                MemberGroup memberGroup = (MemberGroup) group;
                // Guests should see initial groups and those customized for login
                if (!LoggedUser.hasUser() && (memberGroup.isInitialGroup() || StringUtils.isNotEmpty(memberGroup.getLoginPageName()))) {
                    return true;
                }
                break;
            case OPERATOR:
                OperatorGroup operatorGroup = fetchService.fetch((OperatorGroup) group);
                boolean hasPermission = permissionService.permission(operatorGroup.getMember())
                        .member(MemberPermission.OPERATORS_MANAGE)
                        .operator()
                        .hasPermission();
                if (hasPermission) {
                    return true;
                }
                break;
        }
        return permissionService.getAllVisibleGroups().contains(group);
    }

    private void checkManage(final Group group) {
        if (group instanceof AdminGroup) {
            permissionService.permission().admin(AdminSystemPermission.GROUPS_MANAGE_ADMIN).check();
        } else if (group instanceof BrokerGroup) {
            permissionService.permission().admin(AdminSystemPermission.GROUPS_MANAGE_BROKER).check();
        } else if (group instanceof MemberGroup) {
            permissionService.permission().admin(AdminSystemPermission.GROUPS_MANAGE_MEMBER).check();
        } else if (group instanceof OperatorGroup) {
            OperatorGroup operatorGroup = (OperatorGroup) group;
            permissionService.permission(operatorGroup.getMember()).member(MemberPermission.OPERATORS_MANAGE).check();
        }
    }

    private void checkManageAccountSettings() {
        permissionService.permission().admin(AdminMemberPermission.GROUPS_MANAGE_ACCOUNT_SETTINGS).check();
    }

    private void checkView(final Group group) {
        if (!canView(group)) {
            throw new PermissionDeniedException();
        }
    }
}
