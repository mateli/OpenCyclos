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
package nl.strohalm.cyclos.utils.conversion;

import nl.strohalm.cyclos.access.Permission;
import nl.strohalm.cyclos.utils.access.PermissionHelper;

/**
 * Converts a string of the form PermissionSimpleClassName.permissionName to the corresponding permission and vice versa
 * @author ameyer
 */
public class PermissionConverter implements Converter<Permission> {

    private static final long                serialVersionUID = 1L;
    private static final PermissionConverter INSTANCE         = new PermissionConverter();

    public static PermissionConverter instance() {
        return INSTANCE;
    }

    private PermissionConverter() {

    }

    @Override
    public String toString(final Permission permission) {
        return PermissionHelper.getQualifiedPermissionName(permission);
    }

    @Override
    public Permission valueOf(final String qualifiedPermissionName) {
        return PermissionHelper.getPermission(qualifiedPermissionName);
    }
}
