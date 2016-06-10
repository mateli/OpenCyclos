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
package nl.strohalm.cyclos.struts.access.policies.utils;

import nl.strohalm.cyclos.access.Permission;
import nl.strohalm.cyclos.struts.access.ActionDescriptor;
import nl.strohalm.cyclos.struts.access.ActionPolicy;

/**
 * An empty or null permission's array means the access will be denied<br>
 * The subject must has at least one of the specified permissions<br>
 * 
 * In case of an all-access-granted policy use {@link AllGrantedActionPolicy}
 * @author ameyer
 */
public class PermissionActionPolicy implements ActionPolicy {
    public static PermissionActionPolicy create(final Permission... permissions) {
        return new PermissionActionPolicy(permissions);
    }

    protected Permission[] permissions;

    private PermissionActionPolicy(final Permission... permissions) {
        this.permissions = permissions;
    }

    @Override
    public boolean check(final ActionDescriptor descriptor) {
        return permissions == null ? false : descriptor.getPermissionService().hasPermission(permissions);
    }
}
