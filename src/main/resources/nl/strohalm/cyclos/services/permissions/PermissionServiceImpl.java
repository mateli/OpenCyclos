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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import nl.strohalm.cyclos.access.AdminAdminPermission;
import nl.strohalm.cyclos.access.MemberPermission;
import nl.strohalm.cyclos.access.Module;
import nl.strohalm.cyclos.access.ModuleType;
import nl.strohalm.cyclos.access.OperatorPermission;
import nl.strohalm.cyclos.access.Permission;
import nl.strohalm.cyclos.access.PermissionCheck;
import nl.strohalm.cyclos.entities.Entity;
import nl.strohalm.cyclos.entities.groups.AdminGroup;
import nl.strohalm.cyclos.entities.groups.BrokerGroup;
import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.entities.groups.GroupQuery;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.groups.OperatorGroup;
import nl.strohalm.cyclos.entities.members.Administrator;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.members.Operator;
import nl.strohalm.cyclos.exceptions.PermissionDeniedException;
import nl.strohalm.cyclos.services.access.AbstractPermissionCheck;
import nl.strohalm.cyclos.services.fetch.FetchServiceLocal;
import nl.strohalm.cyclos.services.groups.GroupServiceLocal;
import nl.strohalm.cyclos.services.permissions.exceptions.PermissionCatalogInitializationException;
import nl.strohalm.cyclos.utils.DataIteratorHelper;
import nl.strohalm.cyclos.utils.EntityHelper;
import nl.strohalm.cyclos.utils.RelationshipHelper;
import nl.strohalm.cyclos.utils.access.LoggedUser;
import nl.strohalm.cyclos.utils.access.LoggedUser.AccessType;
import nl.strohalm.cyclos.utils.access.PermissionCatalogHandler;
import nl.strohalm.cyclos.utils.access.PermissionHelper;
import nl.strohalm.cyclos.utils.cache.Cache;
import nl.strohalm.cyclos.utils.cache.CacheCallback;
import nl.strohalm.cyclos.utils.cache.CacheManager;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Implementation class for permission services.
 * @author rafael
 */
public class PermissionServiceImpl implements PermissionServiceLocal, ApplicationContextAware {

    private static class CacheKey implements Serializable {
        private static final long serialVersionUID = -7967894427520131064L;

        public static CacheKey fromLoggedUser() {
            return fromLoggedUser(null);
        }

        public static CacheKey fromLoggedUser(final Serializable qualifier) {
            AccessType accessType = LoggedUser.getAccessType();
            Group group = LoggedUser.hasUser() ? LoggedUser.group() : null;
            return new CacheKey(accessType, group, qualifier);
        }

        private final AccessType   accessType;
        private final Group        group;
        private final Serializable qualifier;

        public CacheKey(final AccessType accessType, final Group group, final Serializable qualifier) {
            this.accessType = accessType;
            this.group = group;
            this.qualifier = qualifier;
        }

        @Override
        public boolean equals(final Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof CacheKey)) {
                return false;
            }
            CacheKey key = (CacheKey) obj;
            return accessType == key.accessType && ObjectUtils.equals(group, key.group) && ObjectUtils.equals(qualifier, key.qualifier);
        }

        @Override
        public int hashCode() {
            return 13 * (accessType == null ? 1 : accessType.hashCode()) * (group == null ? 1 : group.hashCode() * (qualifier == null ? 1 : qualifier.hashCode()));
        }

        @Override
        public String toString() {
            return (accessType == null ? "Guest" : accessType.name()) + (group == null ? "" : "#" + group.getId());
        }
    }

    private static final Log LOG = LogFactory.getLog(PermissionServiceImpl.class);

    private static void debug(final Group group, final List<Permission> permissions, final boolean result) {
        if (LOG.isDebugEnabled()) {
            StringBuilder sb = new StringBuilder("Checked [");
            for (int i = 0; i < permissions.size(); i++) {
                if (i > 0) {
                    sb.append(", ");
                }
                sb.append(permissions.get(i).getValue());
            }
            sb.append("] for group id=" + group.getId() + " (" + group.getName() + ")");
            sb.append(" with result ").append(result);
            LOG.debug(sb.toString());
        }
    }

    private static void debug(final String message) {
        if (LOG.isDebugEnabled()) {
            LOG.debug(message);
        }
    }

    private FetchServiceLocal  fetchService;
    private GroupServiceLocal  groupService;
    private CacheManager       cacheManager;
    private ApplicationContext applicationContext;

    @Override
    public void checkManages(final Element element) throws PermissionDeniedException {
        if (!manages(element)) {
            throw new PermissionDeniedException();
        }
    }

    @Override
    public void checkManages(final Group group) throws PermissionDeniedException {
        if (!manages(group)) {
            throw new PermissionDeniedException();
        }
    }

    @Override
    public void checkRelatesTo(final Element element) throws PermissionDeniedException {
        if (!relatesTo(element)) {
            throw new PermissionDeniedException();
        }
    }

    @Override
    public void evictCache(Group group) {
        // We must invalidate all other caches (for visibility / management) whenever a group changes
        getAllVisibleGroupsCache().clear();
        getManagedMemberGroupsCache().clear();
        getVisibleMemberGroupsCache().clear();

        // Evict this group's permission cache
        group = fetchService.fetch(group);
        Cache cache = getPermissionsCache();
        cache.remove(group.getId());
        if (group instanceof MemberGroup) {
            // When a member group, refresh the cache for all operator groups of members of that group
            final MemberGroup memberGroup = (MemberGroup) group;
            final List<OperatorGroup> operatorGroups = groupService.iterateOperatorGroups(memberGroup);
            try {
                for (final OperatorGroup operatorGroup : operatorGroups) {
                    cache.remove(operatorGroup.getId());
                }
            } finally {
                DataIteratorHelper.close(operatorGroups);
            }
        }
    }

    @Override
    public Collection<Group> getAllVisibleGroups() {
        // Get from cache all visible groups, except for operator groups
        CacheKey cacheKey = CacheKey.fromLoggedUser();
        Collection<Group> groups = getAllVisibleGroupsCache().get(cacheKey, new CacheCallback() {
            @Override
            public Object retrieve() {
                if (LoggedUser.getAccessType() == null) {
                    // Guests can only see initial groups
                    return groupService.getPossibleInitialGroups(null);
                }

                Collection<Group> result = new ArrayList<Group>();
                if (LoggedUser.isSystem() || hasPermission(AdminAdminPermission.ADMINS_REGISTER, AdminAdminPermission.ADMINS_CHANGE_PROFILE)) {
                    GroupQuery query = new GroupQuery();
                    query.setNatures(Group.Nature.ADMIN);
                    result.addAll(groupService.search(query));
                }
                if (LoggedUser.hasUser()) {
                    // No matter what, the own group is always visible
                    result.add(LoggedUser.group());
                }
                result.addAll(getVisibleMemberGroups());
                return result;
            }
        });

        if (hasPermission(MemberPermission.OPERATORS_MANAGE)) {
            // As the cache is by logged user's group, and operator groups are per logged member,
            // we simply list the operator groups for members which can manage operators
            GroupQuery query = new GroupQuery();
            query.setNature(Group.Nature.OPERATOR);
            query.setMember(LoggedUser.member());
            groups.addAll(groupService.search(query));
        }
        return groups;
    }

    @Override
    public Collection<MemberGroup> getManagedMemberGroups() {
        CacheKey cacheKey = CacheKey.fromLoggedUser();
        return getManagedMemberGroupsCache().get(cacheKey, new CacheCallback() {
            @Override
            public Object retrieve() {
                if (LoggedUser.isSystemOrUnrestrictedClient() || LoggedUser.isAdministrator()) {
                    return getVisibleMemberGroups();
                } else if (LoggedUser.isBroker()) {
                    // Make sure the own group, plus the initial groups are present
                    Set<MemberGroup> groups = new HashSet<MemberGroup>();
                    groups.addAll(getVisibleMemberGroups());
                    MemberGroup group = LoggedUser.group();
                    groups.add(group);
                    return groups;
                } else { // member or operator
                    return Collections.<MemberGroup> singleton(LoggedUser.member().getMemberGroup());
                }
            }
        });
    }

    @Override
    public PermissionCatalogHandler getPermissionCatalogHandler(final Group group) {
        final AutowireCapableBeanFactory factory = applicationContext.getAutowireCapableBeanFactory();
        try {
            PermissionCatalogHandlerImpl handler = (PermissionCatalogHandlerImpl) factory.createBean(PermissionCatalogHandlerImpl.class, AutowireCapableBeanFactory.AUTOWIRE_BY_NAME, false);
            handler.load(group);
            return handler;
        } catch (Exception e) {
            throw new PermissionCatalogInitializationException(group.getNature(), e.getMessage(), e);
        }
    }

    @Override
    public Collection<MemberGroup> getVisibleMemberGroups() {
        return getVisibleMemberGroups(true);
    }

    @Override
    public Collection<MemberGroup> getVisibleMemberGroups(final boolean addLoggedMemberGroup) {
        CacheKey cacheKey = CacheKey.fromLoggedUser(addLoggedMemberGroup);
        return getVisibleMemberGroupsCache().get(cacheKey, new CacheCallback() {
            @Override
            public Object retrieve() {
                if (LoggedUser.getAccessType() == null) {
                    // Guests can only see initial groups
                    return groupService.getPossibleInitialGroups(null);
                }

                // Will show all groups for either
                boolean isSystem = LoggedUser.isSystem();
                boolean isUnrestrictedClient = LoggedUser.isUnrestrictedClient();
                if (isSystem || isUnrestrictedClient) {
                    // System can view all member / broker groups
                    GroupQuery query = new GroupQuery();
                    query.setNatures(Group.Nature.MEMBER, Group.Nature.BROKER);
                    if (isUnrestrictedClient) {
                        // For web services, removed groups don't count
                        query.setStatus(Group.Status.NORMAL);
                        query.setOnlyActive(true);
                    }
                    return groupService.search(query);
                }
                Group group = LoggedUser.group();
                if (group instanceof AdminGroup) {
                    return fetchService.fetch((AdminGroup) group, AdminGroup.Relationships.MANAGES_GROUPS).getManagesGroups();
                } else {
                    Set<MemberGroup> memberGroups = new HashSet<MemberGroup>();
                    MemberGroup memberGroup = fetchService.fetch(LoggedUser.member().getMemberGroup(), MemberGroup.Relationships.CAN_VIEW_PROFILE_OF_GROUPS);
                    if (addLoggedMemberGroup) {
                        memberGroups.add(memberGroup);
                    }
                    memberGroups.addAll(memberGroup.getCanViewProfileOfGroups());

                    // For brokers, ensure all possible initial groups are visible
                    if (LoggedUser.isBroker()) {
                        BrokerGroup brokerGroup = fetchService.fetch(LoggedUser.<BrokerGroup> group(), BrokerGroup.Relationships.POSSIBLE_INITIAL_GROUPS);
                        memberGroups.addAll(brokerGroup.getPossibleInitialGroups());
                    }

                    return memberGroups;
                }
            }
        });
    }

    @Override
    public boolean hasPermission(final Group group, final Module module) {
        return ensureGroupPermissions(group).containsKey(module);
    }

    @Override
    public boolean hasPermission(final Group group, final Permission... permissions) {
        return hasPermission(group, Arrays.asList(permissions), Collections.<Permission, AbstractPermissionCheck.RequiredValuesBean> emptyMap());
    }

    @Override
    public boolean hasPermission(final Module module) {
        if (LoggedUser.isSystem()) {
            return true;
        } else if (LoggedUser.isUnrestrictedClient()) {
            return module.getType() == ModuleType.MEMBER;
        } else {
            return hasPermission(LoggedUser.group(), module);
        }
    }

    @Override
    public boolean hasPermission(final Permission... permissions) {
        if (LoggedUser.isSystem()) {
            return true;
        } else if (LoggedUser.isUnrestrictedClient()) {
            if (permissions == null || permissions.length == 0) {
                return false;
            } else {
                // at least one permission must be a member permission
                for (Permission p : permissions) {
                    if (p.getModule().getType() == ModuleType.MEMBER) {
                        return true;
                    }
                }
                return false;
            }
        } else {
            return hasPermission(LoggedUser.group(), permissions);
        }
    }

    @Override
    public boolean hasPermissionFor(final Group group, final Permission permission, final Entity... required) {
        if (permission.relationship() == null) {
            throw new IllegalArgumentException(String.format("Invalid permission: %1$s.%2$s. The permission must has a relationship to allow ensuring entity membership", permission.getClass().getSimpleName(), permission));
        }

        return hasPermission(group, Collections.singletonList(permission), Collections.singletonMap(permission, new AbstractPermissionCheck.RequiredValuesBean(permission, required)));
    }

    @Override
    public boolean hasPermissionFor(final Permission permission, final Entity... required) {
        if (permission.relationship() == null) {
            throw new IllegalArgumentException(String.format("Invalid permission: %1$s.%2$s. The permission must has a relationship to allow ensuring entity membership", permission.getClass().getSimpleName(), permission));
        }

        boolean hasPermission = hasPermission(permission);
        if (!hasPermission) {
            return false;
        } else if (LoggedUser.isSystemOrUnrestrictedClient()) {
            return true; // nothing to do with the required in this case
        } else {
            return checkRequiredValues(LoggedUser.group(), permission, required);
        }
    }

    @Override
    public boolean manages(Element element) {
        if (element == null) {
            throw new NullPointerException();
        }

        // System access manages all users
        if (LoggedUser.isSystem()) {
            return true;
        }

        element = fetchService.fetch(element, Element.Relationships.GROUP);
        if (LoggedUser.isUnrestrictedClient()) {
            return element instanceof Member && ((Member) element).isActive();
        }

        // Check the logged user
        Element logged = LoggedUser.element();
        if (logged.equals(element)) {
            // Logged as the user himself
            return true;
        } else if (logged instanceof Administrator) {
            // Logged as admin
            AdminGroup group = LoggedUser.group();
            if (element instanceof Administrator) {
                // For other admins, there is no group restriction - only a permission check
                return hasPermission(group, AdminAdminPermission.ADMINS_VIEW);
            } else if (element instanceof Member) {
                // Administrators can view or manage specific member groups
                Collection<MemberGroup> managedGroups = fetchService.fetch(group, AdminGroup.Relationships.MANAGES_GROUPS).getManagesGroups();
                return managedGroups.contains(element.getGroup());
            }
        } else if (logged instanceof Member) {
            // Logged as member - may manage other member if is his broker, or can manage his own operators
            if (element instanceof Member) {
                return logged.equals(((Member) element).getBroker());
            } else if (element instanceof Operator) {
                return logged.equals(((Operator) element).getMember());
            }
        } else if (logged instanceof Operator) {
            // Logged as operator - only manages his own member
            return ((Operator) logged).getMember().equals(element);
        }
        return false;
    }

    @Override
    public boolean manages(final Group group) {
        if (group instanceof AdminGroup) {
            return permission().admin(AdminAdminPermission.ADMINS_REGISTER, AdminAdminPermission.ADMINS_CHANGE_PROFILE).hasPermission();
        } else if (group instanceof MemberGroup) {
            return getManagedMemberGroups().contains(group);
        } else if (group instanceof OperatorGroup) {
            OperatorGroup operatorGroup = (OperatorGroup) group;
            return LoggedUser.isSystem() || (LoggedUser.isMember() && LoggedUser.element().equals(operatorGroup.getMember()));
        }
        return false;
    }

    @Override
    public PermissionCheck permission() {
        if (LoggedUser.isSystemOrUnrestrictedClient()) {
            return new AbstractPermissionCheck() {
                @Override
                protected boolean doHasPermission() {
                    if (LoggedUser.isSystem()) {
                        // System have all permissions
                        return true;
                    } else { // unrestricted client
                        // unrestricted clients has only member permissions
                        return memberPermissions != null;
                    }
                }
            };
        } else {
            // Get the permissions for the logged user's group
            return permission(LoggedUser.group());
        }
    }

    @Override
    public PermissionCheck permission(final Element element) {
        return new AbstractPermissionCheck() {
            @Override
            public boolean doHasPermission() {
                if (element == null) {
                    throw new NullPointerException("Checking permission over a null element");
                }
                // There is no need to check permission as system
                if (LoggedUser.isSystem()) {
                    debug("Checking permission as system. Assuming true");
                    return true;
                }
                // Check management over the given element
                boolean canManage = manages(element);
                debug("Checking management over " + element + (LoggedUser.isWebService() ? " (as webservice)" : "") + ". Result is " + canManage);

                if (!canManage) {
                    return false;
                } else if (LoggedUser.isUnrestrictedClient()) {
                    return memberPermissions != null;
                } else {
                    // Get the logged user's group, which is used to check the permissions
                    Element logged = LoggedUser.element();
                    Group group = logged.getGroup();
                    ModuleType onlyOfType = getModuleTypeFilter(logged, element);
                    List<Permission> permissions = getPermissions(group.getNature(), onlyOfType);
                    return hasPermissionOrIsEmpty(group, permissions, requiredValuesMap);
                }
            }

            /**
             * According to the given logged user and reference element, returns the ModuleType which should be used to filter the permissions to
             * check, or null if no filter should be applied
             */
            private ModuleType getModuleTypeFilter(final Element logged, final Element element) {
                if (logged instanceof Administrator) {
                    if (element instanceof Administrator) {
                        return ModuleType.ADMIN_ADMIN;
                    } else if (element instanceof Member) {
                        return ModuleType.ADMIN_MEMBER;
                    }
                } else if (logged instanceof Member) {
                    if (element instanceof Operator) {
                        return ModuleType.MEMBER;
                    }
                    Member toCheck = fetchService.fetch((Member) element, Member.Relationships.BROKER);
                    if (logged.equals(toCheck.getBroker())) {
                        return ModuleType.BROKER;
                    } else {
                        return ModuleType.MEMBER;
                    }
                }
                return null;
            }
        };
    }

    @Override
    public PermissionCheck permission(final Group group) {
        return new AbstractPermissionCheck() {
            @Override
            public boolean doHasPermission() {
                return hasPermissionOrIsEmpty(group, getPermissions(group.getNature(), null), requiredValuesMap);
            }
        };
    }

    @Override
    public boolean relatesTo(Element element) {
        if (manages(element)) {
            return true;
        } else if (LoggedUser.isUnrestrictedClient()) {
            // in case of unrestricted clients the manages and the relatesTo relationships are the same
            return false;
        }

        element = fetchService.fetch(element, Element.Relationships.GROUP);

        // When not manages, there are a few other cases where an used can be related to another one...
        Element logged = LoggedUser.element();

        if (element instanceof Member) {
            // A member or his operators are allowed to view other member's by group
            Member loggedMember = null;
            if (logged instanceof Member) {
                loggedMember = (Member) logged;
            } else if (logged instanceof Operator) {
                loggedMember = ((Operator) logged).getMember();
            }
            if (loggedMember != null) {
                MemberGroup memberGroup = fetchService.fetch(loggedMember, RelationshipHelper.nested(Element.Relationships.GROUP, MemberGroup.Relationships.CAN_VIEW_PROFILE_OF_GROUPS)).getMemberGroup();
                return memberGroup.getCanViewProfileOfGroups().contains(element.getGroup());
            }
        } else if (element instanceof Operator && logged instanceof Operator) {
            // Operators of the same member are related
            return ((Operator) element).getMember().equals(((Operator) logged).getMember());
        }
        return false;
    }

    @Override
    public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public void setCacheManager(final CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    public void setFetchServiceLocal(final FetchServiceLocal fetchService) {
        this.fetchService = fetchService;
    }

    public void setGroupServiceLocal(final GroupServiceLocal groupService) {
        this.groupService = groupService;
    }

    /**
     * Checks if every required entity is an allowed one. If permission is a MemberPermission then it ensure the required entities for the memberGroup
     */
    private boolean checkRequiredValues(Group group, final MemberGroup memberGroup, final Permission permission, final Map<Permission, AbstractPermissionCheck.RequiredValuesBean> requiredValues) {
        if (requiredValues == null) {
            return true;
        } else {
            AbstractPermissionCheck.RequiredValuesBean required = requiredValues.get(permission);
            if (required == null) {
                return true;
            } else {
                if (group.getNature() == Group.Nature.OPERATOR && required.getPermission().getModule().getType() == ModuleType.MEMBER) {
                    group = memberGroup;
                }
                return checkRequiredValues(group, required.getPermission(), required.getEntities());
            }
        }
    }

    /**
     * Checks if every required entity is an allowed one
     */
    private boolean checkRequiredValues(Group group, final Permission permission, final Entity[] required) {
        if (required == null || required.length == 0) {
            return true;
        }

        group = fetchService.fetch(group, permission.relationship());
        Collection<? extends Entity> allowed = PermissionHelper.getAllowedValues(group, permission);
        boolean found = true;
        for (int i = 0; i < required.length && found; i++) {
            found = allowed.contains(required[i]);
        }
        return found;
    }

    /**
     * Returns the permissions, keyed by module, for the given group
     */
    private Map<Module, SortedSet<Permission>> ensureGroupPermissions(final Group group) {
        final Long id = group.getId();
        return getPermissionsCache().get(id, new CacheCallback() {
            @Override
            public Object retrieve() {
                Map<Module, SortedSet<Permission>> groupPermissions = new HashMap<Module, SortedSet<Permission>>();

                Group group = EntityHelper.reference(Group.class, id);
                group = fetchService.reload(group, Group.Relationships.PERMISSIONS, RelationshipHelper.nested(OperatorGroup.Relationships.MEMBER, Element.Relationships.GROUP));
                boolean isOperatorGroup = group.getNature() == Group.Nature.OPERATOR;

                for (final Permission permission : group.getPermissions()) {
                    SortedSet<Permission> modulePermissions = groupPermissions.get(permission.getModule());
                    if (modulePermissions == null) {
                        modulePermissions = new TreeSet<Permission>();
                        groupPermissions.put(permission.getModule(), modulePermissions);
                    }
                    boolean addPermission = true;
                    if (isOperatorGroup) {
                        if (!ModuleType.getModuleTypes(group.getNature()).contains(permission.getModule().getType())) {
                            throw new IllegalStateException("Invalid permission for operator group: " + permission);
                        } else {
                            final MemberGroup memberGroup = ((OperatorGroup) group).getMember().getMemberGroup();
                            if (hasPermission(memberGroup, MemberPermission.OPERATORS_MANAGE)) { // if the member doesn't have the manage permission
                                                                                                 // then his operators can't operate!
                                if (permission instanceof OperatorPermission) {
                                    for (Permission memberPermission : ((OperatorPermission) permission).getParentPermissions()) {
                                        addPermission = hasPermission(memberGroup, memberPermission);
                                        if (addPermission) { // if the member has one of them then the operator too
                                            break;
                                        }
                                    }
                                }
                            } else {
                                addPermission = false;
                            }
                        }
                    }
                    if (addPermission) {
                        modulePermissions.add(permission);
                    }
                }

                return groupPermissions;
            }
        });
    }

    private Cache getAllVisibleGroupsCache() {
        return cacheManager.getCache("cyclos.AllVisibleGroups");
    }

    private Cache getManagedMemberGroupsCache() {
        return cacheManager.getCache("cyclos.ManagedMemberGroups");
    }

    private Cache getPermissionsCache() {
        return cacheManager.getCache("cyclos.Permissions");
    }

    private Cache getVisibleMemberGroupsCache() {
        return cacheManager.getCache("cyclos.VisibleMemberGroups");
    }

    private boolean hasPermission(final Group group, final List<Permission> permissions, final Map<Permission, AbstractPermissionCheck.RequiredValuesBean> requiredValues) {
        boolean result = false;
        Group groupToCheck;
        if (permissions != null) {
            MemberGroup memberGroup = null;
            if (group.getNature() == Group.Nature.OPERATOR) {
                // fetch the operator's owner member group outside the loop
                final OperatorGroup operatorGroup = fetchService.fetch((OperatorGroup) group, RelationshipHelper.nested(OperatorGroup.Relationships.MEMBER, Element.Relationships.GROUP));
                memberGroup = operatorGroup.getMember().getMemberGroup();
            }
            for (Permission permission : permissions) {
                groupToCheck = group;
                // When an operator is logged in and the module is member, test the operator's member's permissions
                if (group.getNature() == Group.Nature.OPERATOR && permission.getModule().getType() == ModuleType.MEMBER) {
                    groupToCheck = memberGroup;
                }

                // Get the permissions from the cache
                Map<Module, SortedSet<Permission>> groupPermissions = ensureGroupPermissions(groupToCheck);
                SortedSet<Permission> permissionsSet = groupPermissions.get(permission.getModule());
                if (permissionsSet != null && permissionsSet.contains(permission)) {
                    // at this point the group has the permission, now we must ensure the permission was granted to the required entities (if any)
                    result = checkRequiredValues(groupToCheck, memberGroup, permission, requiredValues);
                    if (result) {
                        break;
                    }
                }
            }
        }
        return result;
    }

    /**
     * Like {@link #hasPermission(Permission...)}, but returns true if permissions is not null and empty. It stills returns false if permissions is
     * null, as the semantics is that there is no possible permissions, while if empty is like no permission check is needed
     */
    private boolean hasPermissionOrIsEmpty(final Group group, final List<Permission> permissions, final Map<Permission, AbstractPermissionCheck.RequiredValuesBean> requiredValues) {
        if (permissions == null) {
            return false;
        } else if (permissions.isEmpty()) {
            debug("Passing with empty permissions on group id=" + group.getId() + " (" + group.getName() + ")");
            return true;
        } else {
            boolean result = hasPermission(group, permissions, requiredValues);
            debug(group, permissions, result);
            return result;
        }
    }
}
