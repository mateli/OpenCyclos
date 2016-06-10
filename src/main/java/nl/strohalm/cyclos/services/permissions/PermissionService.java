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

import nl.strohalm.cyclos.access.AdminAdminPermission;
import nl.strohalm.cyclos.access.Module;
import nl.strohalm.cyclos.access.Permission;
import nl.strohalm.cyclos.access.PermissionCheck;
import nl.strohalm.cyclos.entities.Entity;
import nl.strohalm.cyclos.entities.groups.AdminGroup;
import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.exceptions.PermissionDeniedException;
import nl.strohalm.cyclos.services.Service;
import nl.strohalm.cyclos.utils.access.LoggedUser;
import nl.strohalm.cyclos.utils.access.PermissionCatalogHandler;

/**
 * Service interface for group permissions This service use to control permissions and to list the permissions by group.
 * @author rafael
 * @author luis
 */
public interface PermissionService extends Service {

    /**
     * Throws a {@link PermissionDeniedException} if {@link #manages(Element)} returns false
     */
    void checkManages(Element element) throws PermissionDeniedException;

    /**
     * Throws a {@link PermissionDeniedException} if {@link #manages(Group)} returns false
     */
    void checkManages(Group group) throws PermissionDeniedException;

    /**
     * Throws a {@link PermissionDeniedException} if {@link #relatesTo(Element)} returns false
     */
    void checkRelatesTo(Element element) throws PermissionDeniedException;

    /**
     * Returns all the visible groups for the logged user. For members / brokers / operators, it's the same as {@link #getVisibleMemberGroups()}. For
     * admins, those who have the permission to manage other admins or to view admin groups, will also include all admin groups.
     */
    Collection<Group> getAllVisibleGroups();

    /**
     * Returns the managed member groups for the logged user. It works like this:
     * <ul>
     * <li>For administrators, the result is {@link AdminGroup#getManagesGroups()}</li>
     * <li>For members, the result is only his own group</li>
     * <li>For brokers, as brokers could potentially manage members in any visible groups (its a per-user relationship), all visible groups are
     * returned</li>
     * <li>For operators, it's only his owner's group</li>
     * <li>For system, all member groups</li>
     * </ul>
     */
    Collection<MemberGroup> getManagedMemberGroups();

    /**
     * This method must be used only for group permission's edition
     * @param group the group to which edit the permissions
     * @return a handler for all multivalued group's permissions
     */
    PermissionCatalogHandler getPermissionCatalogHandler(Group group);

    /**
     * Returns the visible member groups for the logged user. It works like this:
     * <ul>
     * <li>For administrators, the result is {@link AdminGroup#getManagesGroups()}</li>
     * <li>For members / brokers, the result is {@link MemberGroup#getCanViewProfileOfGroups()}</li>
     * <li>For operators, is his owner group's {@link MemberGroup#getCanViewProfileOfGroups()}</li>
     * <li>For system, all member groups</li>
     */
    Collection<MemberGroup> getVisibleMemberGroups();

    /**
     * Checks if the group of the logged member has at least one of the module's permissions. Preferentially, use the {@link #permission()} method
     * @return The boolean result for permission check
     */
    boolean hasPermission(Module module);

    /**
     * Checks if the group of the logged member has at least one of the specified permissions. Preferentially, use the {@link #permission()} method
     * @return The boolean result for permission check
     */
    boolean hasPermission(Permission... permission);

    /**
     * @param permission a multivalued permission having a relationship from which get its allowed values
     * @return true if the logged member has permission for all required entities
     */
    boolean hasPermissionFor(Permission permission, Entity... required);

    /**
     * Returns whether the logged user can manage the given element. Manage means one of the following:
     * <ul>
     * <li>The current invocation is a system task {@link LoggedUser#isSystem()}</li>
     * <li>The logged user is the same as the given element</li>
     * <li>The logged user is the broker of the given element</li>
     * <li>The logged user is the member and the element is one of its operators</li>
     * <li>Both logged user and the given element are administrators, and logged user has permissions over other administrators</li>
     * <li>The logged user is administrator with permission to manage the given element's group, being the given element a member</li>
     * <li>if the logged user is an operator, and the element is the member to which it belongs</li>
     * </ul>
     * @throws NullPointerException if the element parameter is null. However, the method passes with a null element parameter if (and only if)
     * LoggedUser.isSystem().
     */
    boolean manages(Element element);

    /**
     * Returns whether the logged user can manage the given group. If the group is an admin group, the logged user must be an admin with either
     * {@link AdminAdminPermission#ADMINS_REGISTER} or {@link AdminAdminPermission#ADMINS_CHANGE_PROFILE} permissions. If the group is a member group,
     * checks whether it is returned by {@link #getManagedMemberGroups()}. Finally, for operator groups, only their owner can manage them.
     */
    boolean manages(Group group);

    /**
     * Returns a {@link PermissionCheck}, which can be used to enforce the logged user's permissions which are not related to an specific user.
     */
    PermissionCheck permission();

    /**
     * Returns a {@link PermissionCheck}, which will, besides of checking static permissions, ensure that the logged user is allowed to manage the
     * given {@link Element}, according to {@link #manages(Element)}
     */
    PermissionCheck permission(Element element);

    /**
     * Returns true if the logged user is related to the given element. Related to means one of the following:
     * <ul>
     * <li>The logged user manages the given element, according to {@link #manages(Element)}</li>
     * <li>The logged user is a member with permission to view the profile of the given member, according to
     * {@link MemberGroup#getCanViewProfileOfGroups()}</li>
     * <li>Both the logged user and the given element are operators of the same member</li>
     * </ul>
     */
    boolean relatesTo(Element element);
}
