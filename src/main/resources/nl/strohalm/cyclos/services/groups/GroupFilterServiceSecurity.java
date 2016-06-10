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

import nl.strohalm.cyclos.access.AdminSystemPermission;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.groups.AdminGroup;
import nl.strohalm.cyclos.entities.groups.GroupFilter;
import nl.strohalm.cyclos.entities.groups.GroupFilterQuery;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.exceptions.PermissionDeniedException;
import nl.strohalm.cyclos.services.BaseServiceSecurity;
import nl.strohalm.cyclos.utils.access.LoggedUser;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

/**
 * Security layer for {@link GroupFilterService}
 * 
 * @author luis
 */
public class GroupFilterServiceSecurity extends BaseServiceSecurity implements GroupFilterService {

    private GroupFilterServiceLocal groupFilterService;

    @Override
    public GroupFilter findByLoginPageName(final String loginPageName) {
        // No permission check, as this is called by the login page (no active user)
        return groupFilterService.findByLoginPageName(loginPageName);
    }

    @Override
    public Collection<GroupFilter> load(final Collection<Long> ids, final Relationship... fetch) {
        Collection<GroupFilter> groupFilters = groupFilterService.load(ids, fetch);
        for (GroupFilter groupFilter : groupFilters) {
            checkVisible(groupFilter);
        }
        return groupFilters;
    }

    @Override
    public GroupFilter load(final Long id, final Relationship... fetch) {
        GroupFilter groupFilter = groupFilterService.load(id, fetch);
        checkVisible(groupFilter);
        return groupFilter;
    }

    @Override
    public int remove(final Long... ids) {
        checkManage();
        return groupFilterService.remove(ids);
    }

    @Override
    public GroupFilter save(final GroupFilter groupFilter) {
        // Check applied groups
        if (CollectionUtils.isNotEmpty(groupFilter.getGroups()) && !permissionService.getManagedMemberGroups().containsAll(groupFilter.getGroups())) {
            throw new PermissionDeniedException();
        }

        // Check viewable by groups
        if (CollectionUtils.isNotEmpty(groupFilter.getViewableBy()) && !permissionService.getManagedMemberGroups().containsAll(groupFilter.getViewableBy())) {
            throw new PermissionDeniedException();
        }

        checkManage();
        return groupFilterService.save(groupFilter);
    }

    @Override
    public List<GroupFilter> search(final GroupFilterQuery query) {
        if (LoggedUser.isAdministrator()) {
            AdminGroup group = LoggedUser.group();
            query.setAdminGroup(group);
        } else {
            Member member = LoggedUser.member();
            if (member != null) {
                query.setViewableBy(member.getMemberGroup());
            }
        }
        return groupFilterService.search(query);
    }

    public void setGroupFilterServiceLocal(final GroupFilterServiceLocal groupFilterService) {
        this.groupFilterService = groupFilterService;
    }

    @Override
    public void validate(final GroupFilter groupFilter) {
        // Nothing to check on validate
        groupFilterService.validate(groupFilter);
    }

    private void checkManage() {
        permissionService.permission().admin(AdminSystemPermission.GROUP_FILTERS_MANAGE).check();
    }

    private void checkVisible(final GroupFilter groupFilter) {
        if (!isVisible(groupFilter)) {
            throw new PermissionDeniedException();
        }
    }

    private boolean isVisible(final GroupFilter groupFilter) {
        if (permissionService.hasPermission(AdminSystemPermission.GROUP_FILTERS_VIEW)) {
            return true;
        }
        if (LoggedUser.isAdministrator()) {
            // Admins can view any group filter related to any of his managed groups
            Collection<MemberGroup> groups = permissionService.getManagedMemberGroups();
            for (MemberGroup group : groups) {
                if (group.getGroupFilters().contains(groupFilter)) {
                    return true;
                }
            }
        } else if (!LoggedUser.hasUser()) {
            // Guests should see group filters that have a login page name
            if (StringUtils.isNotEmpty(groupFilter.getLoginPageName())) {
                return true;
            }
        } else {
            Member member = LoggedUser.member();
            if (member != null) {
                return groupFilter.getViewableBy().contains(member.getMemberGroup());
            }
        }
        throw new PermissionDeniedException();
    }

}
