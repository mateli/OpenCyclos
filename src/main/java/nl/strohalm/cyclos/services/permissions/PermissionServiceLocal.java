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

import nl.strohalm.cyclos.access.Module;
import nl.strohalm.cyclos.access.Permission;
import nl.strohalm.cyclos.access.PermissionCheck;
import nl.strohalm.cyclos.entities.Entity;
import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.entities.groups.MemberGroup;

/**
 * Local interface. It must be used only from other services.
 */
public interface PermissionServiceLocal extends PermissionService {

    /**
     * Refreshes cached list of permissions for a given group
     */
    void evictCache(Group group);

    /**
     * @see #getVisibleMemberGroups()
     * @param addLoggedMemberGroup If false, the group of the logged user isn't added to the result.
     * @return
     */
    Collection<MemberGroup> getVisibleMemberGroups(boolean addLoggedMemberGroup);

    /**
     * Checks if the specified group member has at least one of the module's permissions. Preferentially, use the {@link #permission()} method
     * @return The boolean result for permission check
     */
    boolean hasPermission(Group group, Module module);

    /**
     * Checks if the specified group member has at least one of the specified permissions. Preferentially, use the {@link #permission()} method
     * @return The boolean result for permission check
     */
    boolean hasPermission(Group group, Permission... permission);

    /**
     * @param permission a multivalued permission having a relationship from which get its allowed values
     * @return true if the specified group has permission for all required entities
     */
    boolean hasPermissionFor(Group group, final Permission permission, final Entity... required);

    /**
     * Returns a {@link PermissionCheck}, which will, check permissions for the given group
     */
    PermissionCheck permission(Group group);

}
