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

import java.util.Collections;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

import nl.strohalm.cyclos.utils.access.PermissionHelper;

/**
 * This enum contains all modules defined in Cyclos
 * @author ameyer
 */
public enum Module {
    /* Modules for the BASIC module type */
    BASIC(ModuleType.BASIC),

    /* Modules for the ADMIN_SYSTEM module type */
    SYSTEM_CURRENCIES(ModuleType.ADMIN_SYSTEM),
    SYSTEM_ACCOUNTS(ModuleType.ADMIN_SYSTEM),
    SYSTEM_SETTINGS(ModuleType.ADMIN_SYSTEM),
    SYSTEM_CUSTOMIZED_FILES(ModuleType.ADMIN_SYSTEM),
    SYSTEM_CUSTOM_IMAGES(ModuleType.ADMIN_SYSTEM),
    SYSTEM_CUSTOM_FIELDS(ModuleType.ADMIN_SYSTEM),
    SYSTEM_TRANSLATION(ModuleType.ADMIN_SYSTEM),
    SYSTEM_THEMES(ModuleType.ADMIN_SYSTEM),
    SYSTEM_PAYMENTS(ModuleType.ADMIN_SYSTEM),
    SYSTEM_ACCOUNT_FEES(ModuleType.ADMIN_SYSTEM),
    SYSTEM_AD_CATEGORIES(ModuleType.ADMIN_SYSTEM),
    SYSTEM_MESSAGE_CATEGORIES(ModuleType.ADMIN_SYSTEM),
    SYSTEM_ALERTS(ModuleType.ADMIN_SYSTEM),
    SYSTEM_ERROR_LOG(ModuleType.ADMIN_SYSTEM),
    SYSTEM_GROUPS(ModuleType.ADMIN_SYSTEM),
    SYSTEM_REGISTRATION_AGREEMENTS(ModuleType.ADMIN_SYSTEM),
    SYSTEM_ADMIN_GROUPS(ModuleType.ADMIN_SYSTEM),
    SYSTEM_GROUP_FILTERS(ModuleType.ADMIN_SYSTEM),
    SYSTEM_LOAN_GROUPS(ModuleType.ADMIN_SYSTEM),
    SYSTEM_REPORTS(ModuleType.ADMIN_SYSTEM),
    SYSTEM_TASKS(ModuleType.ADMIN_SYSTEM),
    SYSTEM_STATUS(ModuleType.ADMIN_SYSTEM),
    SYSTEM_EXTERNAL_ACCOUNTS(ModuleType.ADMIN_SYSTEM),
    SYSTEM_MEMBER_RECORD_TYPES(ModuleType.ADMIN_SYSTEM),
    SYSTEM_SERVICE_CLIENTS(ModuleType.ADMIN_SYSTEM),
    SYSTEM_CHANNELS(ModuleType.ADMIN_SYSTEM),
    SYSTEM_GUARANTEE_TYPES(ModuleType.ADMIN_SYSTEM),
    SYSTEM_CARD_TYPES(ModuleType.ADMIN_SYSTEM),
    SYSTEM_INFO_TEXTS(ModuleType.ADMIN_SYSTEM),

    /* Modules for the ADMIN_ADMIN module type */
    ADMIN_ADMINS(ModuleType.ADMIN_ADMIN),
    ADMIN_ADMIN_ACCESS(ModuleType.ADMIN_ADMIN),
    ADMIN_ADMIN_RECORDS(ModuleType.ADMIN_ADMIN),

    /* Modules for the ADMIN_MEMBER module type */
    ADMIN_MEMBERS(ModuleType.ADMIN_MEMBER),
    ADMIN_MEMBER_ACCESS(ModuleType.ADMIN_MEMBER),
    ADMIN_MEMBER_BROKERINGS(ModuleType.ADMIN_MEMBER),
    ADMIN_MEMBER_ACCOUNTS(ModuleType.ADMIN_MEMBER),
    ADMIN_MEMBER_GROUPS(ModuleType.ADMIN_MEMBER),
    ADMIN_MEMBER_REPORTS(ModuleType.ADMIN_MEMBER),
    ADMIN_MEMBER_PAYMENTS(ModuleType.ADMIN_MEMBER),
    ADMIN_MEMBER_INVOICES(ModuleType.ADMIN_MEMBER),
    ADMIN_MEMBER_ADS(ModuleType.ADMIN_MEMBER),
    ADMIN_MEMBER_REFERENCES(ModuleType.ADMIN_MEMBER),
    ADMIN_MEMBER_TRANSACTION_FEEDBACKS(ModuleType.ADMIN_MEMBER),
    ADMIN_MEMBER_LOANS(ModuleType.ADMIN_MEMBER),
    ADMIN_MEMBER_LOAN_GROUPS(ModuleType.ADMIN_MEMBER),
    ADMIN_MEMBER_MESSAGES(ModuleType.ADMIN_MEMBER),
    ADMIN_MEMBER_DOCUMENTS(ModuleType.ADMIN_MEMBER),
    ADMIN_MEMBER_RECORDS(ModuleType.ADMIN_MEMBER),
    ADMIN_MEMBER_BULK_ACTIONS(ModuleType.ADMIN_MEMBER),
    ADMIN_MEMBER_SMS(ModuleType.ADMIN_MEMBER),
    ADMIN_MEMBER_SMS_MAILINGS(ModuleType.ADMIN_MEMBER),
    ADMIN_MEMBER_GUARANTEES(ModuleType.ADMIN_MEMBER),
    ADMIN_MEMBER_CARDS(ModuleType.ADMIN_MEMBER),
    ADMIN_MEMBER_POS(ModuleType.ADMIN_MEMBER),
    ADMIN_MEMBER_PREFERENCES(ModuleType.ADMIN_MEMBER),

    /* Modules for the BROKER module type */
    BROKER_MEMBERS(ModuleType.BROKER),
    BROKER_ACCOUNTS(ModuleType.BROKER),
    BROKER_REPORTS(ModuleType.BROKER),
    BROKER_ADS(ModuleType.BROKER),
    BROKER_REFERENCES(ModuleType.BROKER),
    BROKER_INVOICES(ModuleType.BROKER),
    BROKER_LOANS(ModuleType.BROKER),
    BROKER_LOAN_GROUPS(ModuleType.BROKER),
    BROKER_DOCUMENTS(ModuleType.BROKER),
    BROKER_MESSAGES(ModuleType.BROKER),
    BROKER_MEMBER_ACCESS(ModuleType.BROKER),
    BROKER_MEMBER_PAYMENTS(ModuleType.BROKER),
    BROKER_MEMBER_RECORDS(ModuleType.BROKER),
    BROKER_MEMBER_SMS(ModuleType.BROKER),
    BROKER_CARDS(ModuleType.BROKER),
    BROKER_POS(ModuleType.BROKER),
    BROKER_SMS_MAILINGS(ModuleType.BROKER),
    BROKER_PREFERENCES(ModuleType.BROKER),

    /* Modules for the MEMBER module type */
    MEMBER_PROFILE(ModuleType.MEMBER),
    MEMBER_ACCESS(ModuleType.MEMBER),
    MEMBER_ACCOUNT(ModuleType.MEMBER),
    MEMBER_PAYMENTS(ModuleType.MEMBER),
    MEMBER_INVOICES(ModuleType.MEMBER),
    MEMBER_REFERENCES(ModuleType.MEMBER),
    MEMBER_DOCUMENTS(ModuleType.MEMBER),
    MEMBER_LOANS(ModuleType.MEMBER),
    MEMBER_ADS(ModuleType.MEMBER),
    MEMBER_PREFERENCES(ModuleType.MEMBER),
    MEMBER_REPORTS(ModuleType.MEMBER),
    MEMBER_MESSAGES(ModuleType.MEMBER),
    MEMBER_OPERATORS(ModuleType.MEMBER),
    MEMBER_SMS(ModuleType.MEMBER),
    MEMBER_GUARANTEES(ModuleType.MEMBER),
    MEMBER_CARDS(ModuleType.MEMBER),

    /* Modules for the OPERATOR module type */
    OPERATOR_ACCOUNT(ModuleType.OPERATOR),
    OPERATOR_PAYMENTS(ModuleType.OPERATOR),
    OPERATOR_INVOICES(ModuleType.OPERATOR),
    OPERATOR_REFERENCES(ModuleType.OPERATOR),
    OPERATOR_LOANS(ModuleType.OPERATOR),
    OPERATOR_ADS(ModuleType.OPERATOR),
    OPERATOR_REPORTS(ModuleType.OPERATOR),
    OPERATOR_CONTACTS(ModuleType.OPERATOR),
    OPERATOR_GUARANTEES(ModuleType.OPERATOR),
    OPERATOR_MESSAGES(ModuleType.OPERATOR);

    private static void processModules() {
        if (PERMISSIONS_BY_MODULE != null) {
            return;
        }

        final Map<Module, Set<Permission>> byModule = new EnumMap<Module, Set<Permission>>(Module.class);

        for (final Module module : values()) {
            switch (module.type) {
                case BASIC:
                    module.mapPermissions(BasicPermission.class, byModule);
                    break;
                case ADMIN_ADMIN:
                    module.mapPermissions(AdminAdminPermission.class, byModule);
                    break;
                case ADMIN_MEMBER:
                    module.mapPermissions(AdminMemberPermission.class, byModule);
                    break;
                case ADMIN_SYSTEM:
                    module.mapPermissions(AdminSystemPermission.class, byModule);
                    break;
                case BROKER:
                    module.mapPermissions(BrokerPermission.class, byModule);
                    break;
                case MEMBER:
                    module.mapPermissions(MemberPermission.class, byModule);
                    break;
                case OPERATOR:
                    module.mapPermissions(OperatorPermission.class, byModule);
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported module type: " + module.type);
            }
        }
        PERMISSIONS_BY_MODULE = byModule;
    }

    private final ModuleType                    type;
    private String                              value;

    private static Map<Module, Set<Permission>> PERMISSIONS_BY_MODULE;

    private Module(final ModuleType type) {
        this.type = type;
        value = PermissionHelper.getValue(this);
    }

    public Set<Permission> getPermissions() {
        processModules();
        return PERMISSIONS_BY_MODULE.get(this);
    }

    public ModuleType getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private <T extends Enum<T>> void mapPermissions(final Class<T> permissionEnumType, final Map<Module, Set<Permission>> byModule) {
        final Set<Permission> permissions = EnumSet.noneOf((Class) type.getPermissionClass());
        for (final Enum<T> enumValue : permissionEnumType.getEnumConstants()) {
            final Permission perm = (Permission) enumValue;
            if (perm.getModule() == this) {
                permissions.add(perm);
            }
        }
        byModule.put(this, Collections.unmodifiableSet(permissions));
    }
}
