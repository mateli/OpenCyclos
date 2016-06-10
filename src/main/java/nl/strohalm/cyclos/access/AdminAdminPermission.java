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
package nl.strohalm.cyclos.access;

import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.groups.AdminGroup;
import nl.strohalm.cyclos.utils.access.PermissionHelper;

/**
 * This enum contains all permissions related to the ADMIN_ADMIN module type
 * @author ameyer
 */
public enum AdminAdminPermission implements AdminPermission {
    /* Permissions for the ADMIN_ADMINS module */
    ADMINS_VIEW(Module.ADMIN_ADMINS),
    ADMINS_REGISTER(Module.ADMIN_ADMINS),
    ADMINS_CHANGE_PROFILE(Module.ADMIN_ADMINS),
    ADMINS_CHANGE_GROUP(Module.ADMIN_ADMINS),
    ADMINS_REMOVE(Module.ADMIN_ADMINS),

    /* Permissions for the ADMIN_ADMIN_ACCESS module */
    ACCESS_CHANGE_PASSWORD(Module.ADMIN_ADMIN_ACCESS),
    ACCESS_TRANSACTION_PASSWORD(Module.ADMIN_ADMIN_ACCESS),
    ACCESS_DISCONNECT(Module.ADMIN_ADMIN_ACCESS),
    ACCESS_ENABLE_LOGIN(Module.ADMIN_ADMIN_ACCESS),

    /* Permissions for the ADMIN_ADMIN_RECORDS module */
    RECORDS_VIEW(Module.ADMIN_ADMIN_RECORDS, AdminGroup.Relationships.VIEW_ADMIN_RECORD_TYPES),
    RECORDS_CREATE(Module.ADMIN_ADMIN_RECORDS, AdminGroup.Relationships.CREATE_ADMIN_RECORD_TYPES),
    RECORDS_MODIFY(Module.ADMIN_ADMIN_RECORDS, AdminGroup.Relationships.MODIFY_ADMIN_RECORD_TYPES),
    RECORDS_DELETE(Module.ADMIN_ADMIN_RECORDS, AdminGroup.Relationships.DELETE_ADMIN_RECORD_TYPES);

    private final Module module;
    private String       value;
    private String       qualifiedName;
    private Relationship relationship;

    /**
     * Constructor for boolean permissions
     */
    private AdminAdminPermission(final Module module) {
        this(module, null);
    }

    private AdminAdminPermission(final Module module, final Relationship relationship) {
        this.module = module;
        this.relationship = relationship;
    }

    @Override
    public Module getModule() {
        return module;
    }

    @Override
    public String getQualifiedName() {
        if (qualifiedName == null) {
            qualifiedName = PermissionHelper.getQualifiedPermissionName(this);
        }
        return qualifiedName;
    }

    @Override
    public String getValue() {
        if (value == null) {
            value = PermissionHelper.getValue(this);
        }
        return value;
    }

    @Override
    public Relationship relationship() {
        return relationship;
    }
}
