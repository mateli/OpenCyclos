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
package nl.strohalm.cyclos.services.access;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import nl.strohalm.cyclos.access.AdminPermission;
import nl.strohalm.cyclos.access.BasicPermission;
import nl.strohalm.cyclos.access.BrokerPermission;
import nl.strohalm.cyclos.access.MemberPermission;
import nl.strohalm.cyclos.access.ModuleType;
import nl.strohalm.cyclos.access.OperatorPermission;
import nl.strohalm.cyclos.access.Permission;
import nl.strohalm.cyclos.access.PermissionCheck;
import nl.strohalm.cyclos.entities.Entity;
import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.exceptions.PermissionDeniedException;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Abstract implementation for {@link PermissionCheck}
 * 
 * @author luis
 */
public abstract class AbstractPermissionCheck implements PermissionCheck {

    public static class RequiredValuesBean {
        private final Entity[]   entities;
        private final Permission permission;

        public RequiredValuesBean(final Permission permission, final Entity[] entities) {
            this.permission = permission;
            this.entities = entities;
        }

        public Entity[] getEntities() {
            return entities;
        }

        public Permission getPermission() {
            return permission;
        }
    }

    private static final Log                      LOG = LogFactory.getLog(AbstractPermissionCheck.class);

    private static boolean                        trace;
    static {
        trace = Boolean.getBoolean("cyclos.tracePermissionChecks");
    }

    /* Map from multivalued permissions to entities. All entities mapped to a permission must be contained in the allowed permission's values */
    protected Map<Permission, RequiredValuesBean> requiredValuesMap;

    protected AdminPermission[]                   adminPermissions;
    protected BrokerPermission[]                  brokerPermissions;
    protected MemberPermission[]                  memberPermissions;
    protected OperatorPermission[]                operatorPermissions;
    protected MemberPermission[]                  operatorMemberPermissions;
    protected BasicPermission[]                   basicPermissions;
    private boolean                               permissionChecked;
    private StackTraceElement[]                   stackTrace;

    public AbstractPermissionCheck() {
        if (trace) {
            stackTrace = Thread.currentThread().getStackTrace();
        }
    }

    @Override
    public PermissionCheck admin(final AdminPermission... permissions) {
        adminPermissions = (AdminPermission[]) (adminPermissions == null ? permissions : ArrayUtils.addAll(adminPermissions, permissions));
        return this;
    }

    @Override
    public PermissionCheck adminFor(final AdminPermission permission, final Entity... entities) {
        checkMultivaluedPermission(permission, null);

        admin(new AdminPermission[] { permission });

        addRequiredValues(permission, permission, entities);

        return this;
    }

    @Override
    public PermissionCheck basic(final BasicPermission... permissions) {
        basicPermissions = (BasicPermission[]) (basicPermissions == null ? permissions : ArrayUtils.addAll(basicPermissions, permissions));
        return this;
    }

    @Override
    public PermissionCheck broker(final BrokerPermission... permissions) {
        brokerPermissions = (BrokerPermission[]) (brokerPermissions == null ? permissions : ArrayUtils.addAll(brokerPermissions, permissions));
        return this;
    }

    @Override
    public PermissionCheck brokerFor(final BrokerPermission permission, final Entity... entities) {
        checkMultivaluedPermission(permission, null);

        broker(new BrokerPermission[] { permission });

        addRequiredValues(permission, permission, entities);

        return this;
    }

    @Override
    public void check() throws PermissionDeniedException {
        if (!hasPermission()) {
            throw new PermissionDeniedException();
        }
    }

    @Override
    public final boolean hasPermission() {
        permissionChecked = true;
        return doHasPermission();
    }

    @Override
    public PermissionCheck member(final MemberPermission... permissions) {
        memberPermissions = (MemberPermission[]) (memberPermissions == null ? permissions : ArrayUtils.addAll(memberPermissions, permissions));
        return this;
    }

    @Override
    public PermissionCheck memberFor(final MemberPermission permission, final Entity... entities) {
        checkMultivaluedPermission(permission, null);

        member(new MemberPermission[] { permission });

        addRequiredValues(permission, permission, entities);

        return this;
    }

    @Override
    public PermissionCheck operator() {
        if (operatorPermissions == null) {
            // Just ensure there is an array set in operatorPermissions
            operatorPermissions = new OperatorPermission[0];
        }
        return this;
    }

    @Override
    public PermissionCheck operator(final MemberPermission... permissions) {
        operatorMemberPermissions = (MemberPermission[]) (operatorMemberPermissions == null ? permissions : ArrayUtils.addAll(operatorMemberPermissions, permissions));
        return this;
    }

    @Override
    public PermissionCheck operator(final OperatorPermission... permissions) {
        operatorPermissions = (OperatorPermission[]) (operatorPermissions == null ? permissions : ArrayUtils.addAll(operatorPermissions, permissions));
        return this;
    }

    @Override
    public PermissionCheck operatorFor(final MemberPermission permission, final Entity... entities) {
        checkMultivaluedPermission(permission, null);

        operator(new MemberPermission[] { permission });

        addRequiredValues(permission, permission, entities);

        return this;
    }

    @Override
    public PermissionCheck operatorFor(final OperatorPermission permission, final Entity... entities) {
        checkMultivaluedPermission(permission, null);

        operator(new OperatorPermission[] { permission });

        addRequiredValues(permission, permission, entities);

        return this;
    }

    @Override
    public PermissionCheck operatorFor(final OperatorPermission permission, final MemberPermission parentPermission, final Entity... entities) {
        checkMultivaluedPermission(permission, parentPermission);

        operator(new OperatorPermission[] { permission });

        addRequiredValues(permission, parentPermission, entities);

        return this;
    }

    protected abstract boolean doHasPermission();

    @Override
    protected void finalize() throws Throwable {
        if (!permissionChecked) {
            if (stackTrace == null) {
                LOG.warn("PermissionCheck object created without actually checking permission. Did you forget a call to check() or hasPermission()? Set the -Dcyclos.tracePermissionChecks=true system argument to view where this permission object was created");
            } else {
                Exception ex = new Exception();
                ex.setStackTrace(stackTrace);
                LOG.warn("PermissionCheck object created without actually checking permission. Did you forget a call to check() or hasPermission()?", ex);
            }
        }
    }

    protected List<Permission> getPermissions(final Group.Nature groupNature, final ModuleType onlyOfType) {
        // Get the raw list of permissions
        List<Permission> permissions = null;
        if (groupNature != null) {
            switch (groupNature) {
                case ADMIN:
                    permissions = join(basicPermissions, adminPermissions);
                    break;
                case BROKER:
                    if (onlyOfType == ModuleType.MEMBER) {
                        permissions = join(basicPermissions, memberPermissions);
                    } else {
                        permissions = join(basicPermissions, memberPermissions, brokerPermissions);
                    }
                    break;
                case MEMBER:
                    permissions = join(basicPermissions, memberPermissions);
                    break;
                case OPERATOR:
                    permissions = join(basicPermissions, operatorPermissions, operatorMemberPermissions);
                    break;
            }
        }
        boolean initiallyEmpty = permissions != null && permissions.isEmpty();

        // Apply the filter
        if (permissions != null && onlyOfType != null) {
            for (Iterator<Permission> iterator = permissions.iterator(); iterator.hasNext();) {
                Permission permission = iterator.next();
                if (permission.getModule().getType() != onlyOfType) {
                    iterator.remove();
                }
            }
            // None of the permissions matched the expected type. Return null, which will make the permission check fail, as initially,
            // permissions were not empty (which happens when we just want someone to have a role, not an specific permission)
            if (permissions.isEmpty() && !initiallyEmpty) {
                permissions = null;
            }
        }
        return permissions;
    }

    private void addRequiredValues(final Permission key, final Permission multivaluedPermission, final Entity... entities) {
        if (requiredValuesMap == null) {
            requiredValuesMap = new HashMap<Permission, RequiredValuesBean>();
        } else if (requiredValuesMap.containsKey(key)) {
            throw new IllegalArgumentException(String.format("Permission (%1$s) already added to the allowed values map", key));
        }

        requiredValuesMap.put(key, new RequiredValuesBean(multivaluedPermission, entities));
    }

    /**
     * Checks the multivalued permission<br>
     * @param permission the multivalued permission or a boolean operator permission with a multivalued parent permission.
     * @param parentPermission not null only if permission is an operator permission.
     * @throws IllegalArgumentException if the specified permission doesn't allow get the related relationship from which retrieve the allowed values
     * or if it was already added.
     */
    private void checkMultivaluedPermission(final Permission permission, final MemberPermission parentPermission) {
        // we allow operator permissions without a relationship only if its parent has
        if (permission.relationship() == null && (!(permission instanceof OperatorPermission) || parentPermission == null || parentPermission.relationship() == null)) {
            throw new IllegalArgumentException(String.format("Invalid permission: %1$s.%2$s. The permission (or its parent if any) must has a relationship to allow ensuring entity membership", permission.getClass().getSimpleName(), permission));
        }
    }

    private List<Permission> join(final Permission[]... permissions) {
        List<Permission> result = new ArrayList<Permission>();
        boolean hasNonNull = false;
        for (Permission[] current : permissions) {
            if (current != null) {
                hasNonNull = true;
                CollectionUtils.addAll(result, current);
            }
        }
        if (!hasNonNull) {
            return null;
        }
        return result;
    }

}
