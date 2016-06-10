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
package nl.strohalm.cyclos.services.permissions;

import java.util.Collection;

import nl.strohalm.cyclos.access.AdminSystemPermission;
import nl.strohalm.cyclos.access.MemberPermission;
import nl.strohalm.cyclos.access.Module;
import nl.strohalm.cyclos.access.Permission;
import nl.strohalm.cyclos.access.PermissionCheck;
import nl.strohalm.cyclos.entities.Entity;
import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.exceptions.PermissionDeniedException;
import nl.strohalm.cyclos.services.BaseServiceSecurity;
import nl.strohalm.cyclos.utils.access.PermissionCatalogHandler;

/**
 * Security implementation for {@link PermissionService}
 * 
 * @author jcomas
 */
public class PermissionServiceSecurity extends BaseServiceSecurity implements PermissionService {

    @Override
    public void checkManages(final Element element) throws PermissionDeniedException {
        permissionService.checkManages(element);
    }

    @Override
    public void checkManages(final Group group) throws PermissionDeniedException {
        permissionService.checkManages(group);
    }

    @Override
    public void checkRelatesTo(final Element element) throws PermissionDeniedException {
        permissionService.checkRelatesTo(element);
    }

    @Override
    public Collection<Group> getAllVisibleGroups() {
        return permissionService.getAllVisibleGroups();
    }

    @Override
    public Collection<MemberGroup> getManagedMemberGroups() {
        return permissionService.getManagedMemberGroups();
    }

    @Override
    public PermissionCatalogHandler getPermissionCatalogHandler(final Group group) {
        PermissionCheck check = permissionService.permission();
        switch (group.getNature()) {
            case ADMIN:
                check.admin(AdminSystemPermission.GROUPS_MANAGE_ADMIN);
                break;
            case BROKER:
                check.admin(AdminSystemPermission.GROUPS_MANAGE_BROKER);
                break;
            case MEMBER:
                check.admin(AdminSystemPermission.GROUPS_MANAGE_MEMBER);
                break;
            case OPERATOR:
                check.member(MemberPermission.OPERATORS_MANAGE);
                break;
            default:
                throw new IllegalArgumentException("Unsupported group's nature: " + group.getNature());
        }

        check.check();
        return permissionService.getPermissionCatalogHandler(group);
    }

    @Override
    public Collection<MemberGroup> getVisibleMemberGroups() {
        return permissionService.getVisibleMemberGroups();
    }

    @Override
    public boolean hasPermission(final Module module) {
        return permissionService.hasPermission(module);
    }

    @Override
    public boolean hasPermission(final Permission... permission) {
        return permissionService.hasPermission(permission);
    }

    @Override
    public boolean hasPermissionFor(final Permission permission, final Entity... required) {
        return permissionService.hasPermissionFor(permission, required);
    }

    @Override
    public boolean manages(final Element element) {
        return permissionService.manages(element);
    }

    @Override
    public boolean manages(final Group group) {
        return false;
    }

    @Override
    public PermissionCheck permission() {
        return permissionService.permission();
    }

    @Override
    public PermissionCheck permission(final Element element) {
        return permissionService.permission(element);
    }

    @Override
    public boolean relatesTo(final Element element) {
        return permissionService.relatesTo(element);
    }
}
