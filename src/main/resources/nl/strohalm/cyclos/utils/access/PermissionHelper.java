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
package nl.strohalm.cyclos.utils.access;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import nl.strohalm.cyclos.access.AdminAdminPermission;
import nl.strohalm.cyclos.access.AdminMemberPermission;
import nl.strohalm.cyclos.access.AdminSystemPermission;
import nl.strohalm.cyclos.access.BasicPermission;
import nl.strohalm.cyclos.access.BrokerPermission;
import nl.strohalm.cyclos.access.MemberPermission;
import nl.strohalm.cyclos.access.Module;
import nl.strohalm.cyclos.access.ModuleType;
import nl.strohalm.cyclos.access.OperatorPermission;
import nl.strohalm.cyclos.access.Permission;
import nl.strohalm.cyclos.entities.Entity;
import nl.strohalm.cyclos.entities.groups.AdminGroup;
import nl.strohalm.cyclos.entities.groups.BrokerGroup;
import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.groups.OperatorGroup;
import nl.strohalm.cyclos.exceptions.PermissionDeniedException;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;

/**
 * Contains utility methods to manipulate and check permissions
 * 
 * @author luis
 */
public class PermissionHelper {

    private static final char[]          NAME_DELIMITERS = new char[] { '_' };

    private static Map<String, Class<?>> permissionsBySimpleName;

    static {
        permissionsBySimpleName = new HashMap<String, Class<?>>();
        permissionsBySimpleName.put(BasicPermission.class.getSimpleName(), BasicPermission.class);
        permissionsBySimpleName.put(AdminSystemPermission.class.getSimpleName(), AdminSystemPermission.class);
        permissionsBySimpleName.put(AdminAdminPermission.class.getSimpleName(), AdminAdminPermission.class);
        permissionsBySimpleName.put(AdminMemberPermission.class.getSimpleName(), AdminMemberPermission.class);
        permissionsBySimpleName.put(MemberPermission.class.getSimpleName(), MemberPermission.class);
        permissionsBySimpleName.put(OperatorPermission.class.getSimpleName(), OperatorPermission.class);
        permissionsBySimpleName.put(BrokerPermission.class.getSimpleName(), BrokerPermission.class);
    }

    /**
     * Throws a {@link PermissionDeniedException} allowed is empty or not contains the given element
     */
    public static <T> void checkContains(final Collection<? super T> allowed, final T element) {
        if (allowed == null || !allowed.contains(element)) {
            throw new PermissionDeniedException();
        }
    }

    /**
     * Throws a {@link PermissionDeniedException} allowed is empty or not contains the given element
     */
    public static void checkEquals(final Object expected, final Object actual) {
        if (!ObjectUtils.equals(expected, actual)) {
            throw new PermissionDeniedException();
        }
    }

    /**
     * Same as {@link checkSelection} but not supporting an empty collection for the allowed elements.
     * @see #checkSelection(Collection, Collection, boolean)
     */
    public static <T> Collection<T> checkSelection(final Collection<T> allowed, final Collection<T> selection) {
        return checkSelection(allowed, selection, false);
    }

    /**
     * Used for query filter semantics with collections.<br>
     * Given a collection with the selected elements and another one with the allowed elements:<br>
     * If the allowed elements are empty (and empty for the allowed is not supported) it throws a PermissionDeniedException. Else, if elements is
     * empty, then allowed is returned. Otherwise, the selected elements must be contained in the allowed collection.
     */
    public static <T> Collection<T> checkSelection(final Collection<T> allowed, final Collection<T> selection, final boolean isEmptyAllowedSupported) {
        if (CollectionUtils.isEmpty(allowed)) {
            if (!isEmptyAllowedSupported || CollectionUtils.isNotEmpty(selection)) {
                throw new PermissionDeniedException();
            } else {
                return null;
            }
        }

        if (CollectionUtils.isEmpty(selection)) {
            return allowed;
        }
        if (!allowed.containsAll(selection)) {
            throw new PermissionDeniedException();
        }
        return selection;
    }

    /**
     * Finds (by name) the specified permission as a valid value in the permission enum.
     * @return the permission if found otherwise null.
     */
    public static <T extends Enum<T>> Permission find(final Permission perm, final Class<T> enumType) {
        try {
            final T enumItem = Enum.<T> valueOf(enumType, perm.name());
            return (Permission) enumItem;
        } catch (final IllegalArgumentException e) {
            return null;
        }
    }

    public static Collection<? extends Entity> getAllowedValues(final Group group, final Permission permission) {
        Collection<? extends Entity> current;

        final Class<?> clazz = permission.getClass();
        if (clazz == BrokerPermission.class) {
            current = getCurrentValues((BrokerGroup) group, (BrokerPermission) permission);
        } else if (clazz == MemberPermission.class) {
            current = getCurrentValues((MemberGroup) group, (MemberPermission) permission);
        } else if (clazz == OperatorPermission.class) {
            current = getCurrentValues((OperatorGroup) group, (OperatorPermission) permission);
        } else if (clazz == AdminAdminPermission.class) {
            current = getCurrentValues((AdminGroup) group, (AdminAdminPermission) permission);
        } else if (clazz == AdminMemberPermission.class) {
            current = getCurrentValues((AdminGroup) group, (AdminMemberPermission) permission);
        } else if (clazz == AdminSystemPermission.class) {
            current = getCurrentValues((AdminGroup) group, (AdminSystemPermission) permission);
        } else {
            throw new IllegalArgumentException("Unsupported permission class: " + clazz);
        }
        return current;
    }

    /**
     * @param groupNature the group's nature
     * @return a collection of permissions having a relationship to its possible values according to the specified group's nature
     */
    public static Collection<Permission> getMultivaluedPermissions(final Group.Nature groupNature) {
        final Collection<Permission> list = new ArrayList<Permission>();

        for (final ModuleType moduleType : ModuleType.getModuleTypes(groupNature)) {
            for (final Module module : moduleType.getModules()) {
                for (final Permission permission : module.getPermissions()) {
                    addListPermission(list, permission);
                }
            }
        }

        return Collections.unmodifiableCollection(list);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static Permission getPermission(final String qualifiedPermissionName) {
        final String[] valueParts = StringUtils.split(qualifiedPermissionName, ".");
        final Class permissionClass = permissionsBySimpleName.get(valueParts[0]);
        if (permissionClass == null) {
            throw new IllegalArgumentException("Invalid permission class simple name: " + valueParts[0]);
        }

        return (Permission) Enum.valueOf(permissionClass, valueParts[1]);
    }

    public static String getQualifiedPermissionName(final Permission permission) {
        return permission.getClass().getSimpleName() + "." + permission.name();
    }

    public static String getValue(final Module module) {
        return process(getValue(module.name()));
    }

    public static String getValue(final Permission permission) {
        final String prefix = getValue(permission.getModule().name());
        String suffix = getValue(permission.name());
        final String[] parts = suffix.split("\\_");
        // Remove the redundant part from the suffix
        for (int i = 0; i < parts.length; i++) {
            final StringBuilder sb = new StringBuilder();
            for (int j = 0; j <= i; j++) {
                if (sb.length() > 0) {
                    sb.append('_');
                }
                sb.append(parts[j]);
            }
            if (prefix.endsWith(sb.toString())) {
                suffix = suffix.substring(sb.length());
                if (suffix.startsWith("_")) {
                    suffix = suffix.substring(1);
                }
                break;
            }
        }
        return process(prefix) + "." + process(suffix);
    }

    private static void addListPermission(final Collection<Permission> list, final Permission permission) {
        if (permission.relationship() != null) {
            list.add(permission);
        }
    }

    private static Collection<? extends Entity> getCurrentValues(final AdminGroup adminGroup, final AdminAdminPermission permission) {
        Collection<? extends Entity> current;
        switch (permission) {
            case RECORDS_CREATE:
                current = adminGroup.getCreateAdminRecordTypes();
                break;
            case RECORDS_DELETE:
                current = adminGroup.getDeleteAdminRecordTypes();
                break;
            case RECORDS_MODIFY:
                current = adminGroup.getModifyAdminRecordTypes();
                break;
            case RECORDS_VIEW:
                current = adminGroup.getViewAdminRecordTypes();
                break;
            default:
                throw new IllegalArgumentException(String.format("Unsupported list permission: %1$s.%2$s", permission.getClass().getSimpleName(), permission));
        }
        return current;
    }

    private static Collection<? extends Entity> getCurrentValues(final AdminGroup adminGroup, final AdminMemberPermission permission) {
        Collection<? extends Entity> current;
        switch (permission) {
            case MEMBERS_VIEW:
                current = adminGroup.getManagesGroups();
                break;
            case DOCUMENTS_DETAILS:
                current = adminGroup.getDocuments();
                break;
            case GUARANTEES_REGISTER_GUARANTEES:
                current = adminGroup.getGuaranteeTypes();
                break;
            case LOANS_GRANT:
                current = adminGroup.getTransferTypes();
                break;
            case MESSAGES_VIEW:
                current = adminGroup.getMessageCategories();
                break;
            case PAYMENTS_CHARGEBACK:
                current = adminGroup.getChargebackTransferTypes();
                break;
            case PAYMENTS_PAYMENT:
                current = adminGroup.getTransferTypes();
                break;
            case PAYMENTS_PAYMENT_AS_MEMBER_TO_MEMBER:
                current = adminGroup.getTransferTypesAsMember();
                break;
            case PAYMENTS_PAYMENT_AS_MEMBER_TO_SELF:
                current = adminGroup.getTransferTypesAsMember();
                break;
            case PAYMENTS_PAYMENT_AS_MEMBER_TO_SYSTEM:
                current = adminGroup.getTransferTypesAsMember();
                break;
            case RECORDS_CREATE:
                current = adminGroup.getCreateMemberRecordTypes();
                break;
            case RECORDS_DELETE:
                current = adminGroup.getDeleteMemberRecordTypes();
                break;
            case RECORDS_MODIFY:
                current = adminGroup.getModifyMemberRecordTypes();
                break;
            case RECORDS_VIEW:
                current = adminGroup.getViewMemberRecordTypes();
                break;
            default:
                throw new IllegalArgumentException(String.format("Unsupported list permission: %1$s.%2$s", permission.getClass().getSimpleName(), permission));
        }
        return current;

    }

    private static Collection<? extends Entity> getCurrentValues(final AdminGroup adminGroup, final AdminSystemPermission permission) {
        Collection<? extends Entity> current;
        switch (permission) {
            case ACCOUNTS_INFORMATION:
                current = adminGroup.getViewInformationOf();
                break;
            case PAYMENTS_CHARGEBACK:
                current = adminGroup.getChargebackTransferTypes();
                break;
            case PAYMENTS_PAYMENT:
                current = adminGroup.getTransferTypes();
                break;
            case STATUS_VIEW_CONNECTED_ADMINS:
                current = adminGroup.getViewConnectedAdminsOf();
                break;
            default:
                throw new IllegalArgumentException(String.format("Unsupported list permission: %1$s.%2$s", permission.getClass().getSimpleName(), permission));
        }

        return current;
    }

    private static Collection<? extends Entity> getCurrentValues(final BrokerGroup brokerGroup, final BrokerPermission permission) {
        Collection<? extends Entity> current;
        switch (permission) {
            case DOCUMENTS_VIEW:
                current = brokerGroup.getBrokerDocuments();
                break;
            case MEMBER_PAYMENTS_PAYMENT_AS_MEMBER_TO_MEMBER:
                current = brokerGroup.getTransferTypesAsMember();
                break;
            case MEMBER_PAYMENTS_PAYMENT_AS_MEMBER_TO_SELF:
                current = brokerGroup.getTransferTypesAsMember();
                break;
            case MEMBER_PAYMENTS_PAYMENT_AS_MEMBER_TO_SYSTEM:
                current = brokerGroup.getTransferTypesAsMember();
                break;
            case MEMBER_RECORDS_CREATE:
                current = brokerGroup.getBrokerCreateMemberRecordTypes();
                break;
            case MEMBER_RECORDS_DELETE:
                current = brokerGroup.getBrokerDeleteMemberRecordTypes();
                break;
            case MEMBER_RECORDS_MODIFY:
                current = brokerGroup.getBrokerModifyMemberRecordTypes();
                break;
            case MEMBER_RECORDS_VIEW:
                current = brokerGroup.getBrokerMemberRecordTypes();
                break;
            case REPORTS_SHOW_ACCOUNT_INFORMATION:
                current = brokerGroup.getBrokerCanViewInformationOf();
                break;
            default:
                throw new IllegalArgumentException(String.format("Unsupported list permission: %1$s.%2$s", permission.getClass().getSimpleName(), permission));
        }

        return current;
    }

    private static Collection<? extends Entity> getCurrentValues(final MemberGroup memberGroup, final MemberPermission permission) {
        Collection<? extends Entity> current;
        switch (permission) {
            case ADS_VIEW:
                current = memberGroup.getCanViewAdsOfGroups();
                break;
            case DOCUMENTS_VIEW:
                current = memberGroup.getDocuments();
                break;
            case GUARANTEES_BUY_WITH_PAYMENT_OBLIGATIONS:
                current = memberGroup.getCanBuyWithPaymentObligationsFromGroups();
                break;
            case GUARANTEES_ISSUE_CERTIFICATIONS:
                current = memberGroup.getCanIssueCertificationToGroups();
                break;
            case GUARANTEES_ISSUE_GUARANTEES:
                current = memberGroup.getGuaranteeTypes();
                break;
            case MESSAGES_SEND_TO_ADMINISTRATION:
                current = memberGroup.getMessageCategories();
                break;
            case PAYMENTS_CHARGEBACK:
                current = memberGroup.getChargebackTransferTypes();
                break;
            case PAYMENTS_PAYMENT_TO_MEMBER:
                current = memberGroup.getTransferTypes();
                break;
            case PAYMENTS_PAYMENT_TO_SELF:
                current = memberGroup.getTransferTypes();
                break;
            case PAYMENTS_PAYMENT_TO_SYSTEM:
                current = memberGroup.getTransferTypes();
                break;
            case PAYMENTS_REQUEST:
                current = memberGroup.getRequestPaymentByChannels();
                break;
            case PROFILE_VIEW:
                current = memberGroup.getCanViewProfileOfGroups();
                break;
            case REPORTS_SHOW_ACCOUNT_INFORMATION:
                current = memberGroup.getCanViewInformationOf();
                break;
            default:
                throw new IllegalArgumentException(String.format("Unsupported list permission: %1$s.%2$s", permission.getClass().getSimpleName(), permission));
        }

        return current;

    }

    private static Collection<? extends Entity> getCurrentValues(final OperatorGroup operatorGroup, final OperatorPermission permission) {
        Collection<? extends Entity> current;
        switch (permission) {
            case ACCOUNT_ACCOUNT_INFORMATION:
                current = operatorGroup.getCanViewInformationOf();
                break;
            case GUARANTEES_ISSUE_GUARANTEES:
                current = operatorGroup.getGuaranteeTypes();
                break;
            default:
                throw new IllegalArgumentException(String.format("Unsupported list permission: %1$s.%2$s", permission.getClass().getSimpleName(), permission));
        }
        return current;
    }

    private static String getValue(final String name) {
        return name.toLowerCase();
    }

    private static String process(final String string) {
        return StringUtils.uncapitalize(WordUtils.capitalizeFully(string, NAME_DELIMITERS).replaceAll("\\_", ""));
    }
}
