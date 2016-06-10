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
import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.entities.groups.SystemGroup;
import nl.strohalm.cyclos.utils.access.PermissionHelper;

/**
 * This enum contains all permissions related to the ADMIN_SYSTEM module type
 * @author ameyer
 */
public enum AdminSystemPermission implements AdminPermission {
    /* Permissions for the SYSTEM_CURRENCIES module */
    CURRENCIES_MANAGE(Module.SYSTEM_CURRENCIES),
    CURRENCIES_VIEW(Module.SYSTEM_CURRENCIES), // Having this permission means that the administrator can view all the currencies of the system.

    /* Permissions for the SYSTEM_ACCOUNTS module */
    ACCOUNTS_MANAGE(Module.SYSTEM_ACCOUNTS),
    ACCOUNTS_VIEW(Module.SYSTEM_ACCOUNTS),
    ACCOUNTS_INFORMATION(Module.SYSTEM_ACCOUNTS, AdminGroup.Relationships.VIEW_INFORMATION_OF),
    ACCOUNTS_AUTHORIZED_INFORMATION(Module.SYSTEM_ACCOUNTS),
    ACCOUNTS_SCHEDULED_INFORMATION(Module.SYSTEM_ACCOUNTS),

    /* Permissions for the SYSTEM_SETTINGS module */
    SETTINGS_MANAGE_LOCAL(Module.SYSTEM_SETTINGS),
    SETTINGS_MANAGE_ALERT(Module.SYSTEM_SETTINGS),
    SETTINGS_MANAGE_ACCESS(Module.SYSTEM_SETTINGS),
    SETTINGS_MANAGE_MAIL(Module.SYSTEM_SETTINGS),
    SETTINGS_MANAGE_LOG(Module.SYSTEM_SETTINGS),
    SETTINGS_VIEW(Module.SYSTEM_SETTINGS),
    SETTINGS_FILE(Module.SYSTEM_SETTINGS),

    /* Permissions for the SYSTEM_CUSTOMIZED_FILES module */
    CUSTOMIZED_FILES_MANAGE(Module.SYSTEM_CUSTOMIZED_FILES),
    CUSTOMIZED_FILES_VIEW(Module.SYSTEM_CUSTOMIZED_FILES),

    /* Permissions for the SYSTEM_CUSTOM_IMAGES module */
    CUSTOM_IMAGES_MANAGE(Module.SYSTEM_CUSTOM_IMAGES),
    CUSTOM_IMAGES_VIEW(Module.SYSTEM_CUSTOM_IMAGES),

    /* Permissions for the SYSTEM_CUSTOM_FIELDS module */
    CUSTOM_FIELDS_MANAGE(Module.SYSTEM_CUSTOM_FIELDS),
    CUSTOM_FIELDS_VIEW(Module.SYSTEM_CUSTOM_FIELDS),

    /* Permissions for the SYSTEM_TRANSLATION module */
    TRANSLATION_MANAGE(Module.SYSTEM_TRANSLATION),
    TRANSLATION_VIEW(Module.SYSTEM_TRANSLATION),
    TRANSLATION_FILE(Module.SYSTEM_TRANSLATION),
    TRANSLATION_MANAGE_MAIL_TRANSLATION(Module.SYSTEM_TRANSLATION),
    TRANSLATION_MANAGE_NOTIFICATION(Module.SYSTEM_TRANSLATION),

    /* Permissions for the SYSTEM_THEMES module */
    THEMES_SELECT(Module.SYSTEM_THEMES),
    THEMES_REMOVE(Module.SYSTEM_THEMES),
    THEMES_IMPORT(Module.SYSTEM_THEMES),
    THEMES_EXPORT(Module.SYSTEM_THEMES),

    /* Permissions for the SYSTEM_PAYMENTS module */
    PAYMENTS_PAYMENT(Module.SYSTEM_PAYMENTS, Group.Relationships.TRANSFER_TYPES),
    PAYMENTS_AUTHORIZE(Module.SYSTEM_PAYMENTS),
    PAYMENTS_CANCEL(Module.SYSTEM_PAYMENTS),
    PAYMENTS_CHARGEBACK(Module.SYSTEM_PAYMENTS, SystemGroup.Relationships.CHARGEBACK_TRANSFER_TYPES),
    PAYMENTS_CANCEL_SCHEDULED(Module.SYSTEM_PAYMENTS),
    PAYMENTS_BLOCK_SCHEDULED(Module.SYSTEM_PAYMENTS),

    /* Permissions for the SYSTEM_ACCOUNT_FEES module */
    ACCOUNT_FEES_VIEW(Module.SYSTEM_ACCOUNT_FEES),
    ACCOUNT_FEES_CHARGE(Module.SYSTEM_ACCOUNT_FEES),

    /* Permissions for the SYSTEM_AD_CATEGORIES module */
    AD_CATEGORIES_MANAGE(Module.SYSTEM_AD_CATEGORIES),
    AD_CATEGORIES_VIEW(Module.SYSTEM_AD_CATEGORIES),
    AD_CATEGORIES_FILE(Module.SYSTEM_AD_CATEGORIES),

    /* Permissions for the SYSTEM_MESSAGE_CATEGORIES module */
    MESSAGE_CATEGORIES_MANAGE(Module.SYSTEM_MESSAGE_CATEGORIES),
    MESSAGE_CATEGORIES_VIEW(Module.SYSTEM_MESSAGE_CATEGORIES),

    /* Permissions for the SYSTEM_ALERTS module */
    ALERTS_MANAGE_MEMBER_ALERTS(Module.SYSTEM_ALERTS),
    ALERTS_MANAGE_SYSTEM_ALERTS(Module.SYSTEM_ALERTS),
    ALERTS_VIEW_MEMBER_ALERTS(Module.SYSTEM_ALERTS),
    ALERTS_VIEW_SYSTEM_ALERTS(Module.SYSTEM_ALERTS),

    /* Permissions for the SYSTEM_ERROR_LOG module */
    ERROR_LOG_MANAGE(Module.SYSTEM_ERROR_LOG),
    ERROR_LOG_VIEW(Module.SYSTEM_ERROR_LOG),

    /* Permissions for the SYSTEM_GROUPS module */
    GROUPS_MANAGE_MEMBER(Module.SYSTEM_GROUPS),
    GROUPS_MANAGE_BROKER(Module.SYSTEM_GROUPS),
    GROUPS_MANAGE_ADMIN(Module.SYSTEM_GROUPS),

    /* Permissions for the SYSTEM_REGISTRATION_AGREEMENTS module */
    REGISTRATION_AGREEMENTS_VIEW(Module.SYSTEM_REGISTRATION_AGREEMENTS),
    REGISTRATION_AGREEMENTS_MANAGE(Module.SYSTEM_REGISTRATION_AGREEMENTS),

    /* Permissions for the SYSTEM_ADMIN_GROUPS module */
    ADMIN_GROUPS_VIEW(Module.SYSTEM_ADMIN_GROUPS),
    ADMIN_GROUPS_MANAGE_ADMIN_CUSTOMIZED_FILES(Module.SYSTEM_ADMIN_GROUPS),

    /* Permissions for the SYSTEM_GROUP_FILTERS module */
    GROUP_FILTERS_MANAGE(Module.SYSTEM_GROUP_FILTERS),
    GROUP_FILTERS_VIEW(Module.SYSTEM_GROUP_FILTERS),
    GROUP_FILTERS_MANAGE_CUSTOMIZED_FILES(Module.SYSTEM_GROUP_FILTERS),

    /* Permissions for the SYSTEM_LOAN_GROUPS module */
    LOAN_GROUPS_MANAGE(Module.SYSTEM_LOAN_GROUPS),
    LOAN_GROUPS_VIEW(Module.SYSTEM_LOAN_GROUPS),

    /* Permissions for the SYSTEM_REPORTS module */
    REPORTS_CURRENT(Module.SYSTEM_REPORTS),
    REPORTS_MEMBER_LIST(Module.SYSTEM_REPORTS),
    REPORTS_SMS_LOGS(Module.SYSTEM_REPORTS),
    REPORTS_STATISTICS(Module.SYSTEM_REPORTS),
    REPORTS_SIMULATIONS(Module.SYSTEM_REPORTS),

    /* Permissions for the SYSTEM_TASKS module */
    TASKS_ONLINE_STATE(Module.SYSTEM_TASKS),
    TASKS_MANAGE_INDEXES(Module.SYSTEM_TASKS),

    /* Permissions for the SYSTEM_STATUS module */
    STATUS_VIEW(Module.SYSTEM_STATUS),
    STATUS_VIEW_CONNECTED_ADMINS(Module.SYSTEM_STATUS, AdminGroup.Relationships.VIEW_CONNECTED_ADMINS_OF),
    STATUS_VIEW_CONNECTED_BROKERS(Module.SYSTEM_STATUS),
    STATUS_VIEW_CONNECTED_MEMBERS(Module.SYSTEM_STATUS),
    STATUS_VIEW_CONNECTED_OPERATORS(Module.SYSTEM_STATUS),

    /* Permissions for the SYSTEM_EXTERNAL_ACCOUNTS module */
    EXTERNAL_ACCOUNTS_MANAGE(Module.SYSTEM_EXTERNAL_ACCOUNTS),
    EXTERNAL_ACCOUNTS_VIEW(Module.SYSTEM_EXTERNAL_ACCOUNTS),
    EXTERNAL_ACCOUNTS_DETAILS(Module.SYSTEM_EXTERNAL_ACCOUNTS),
    EXTERNAL_ACCOUNTS_PROCESS_PAYMENT(Module.SYSTEM_EXTERNAL_ACCOUNTS),
    EXTERNAL_ACCOUNTS_CHECK_PAYMENT(Module.SYSTEM_EXTERNAL_ACCOUNTS),
    EXTERNAL_ACCOUNTS_MANAGE_PAYMENT(Module.SYSTEM_EXTERNAL_ACCOUNTS),

    /* Permissions for the SYSTEM_MEMBER_RECORD_TYPES module */
    MEMBER_RECORD_TYPES_MANAGE(Module.SYSTEM_MEMBER_RECORD_TYPES),
    MEMBER_RECORD_TYPES_VIEW(Module.SYSTEM_MEMBER_RECORD_TYPES),

    /* Permissions for the SYSTEM_SERVICE_CLIENTS module */
    SERVICE_CLIENTS_MANAGE(Module.SYSTEM_SERVICE_CLIENTS),
    SERVICE_CLIENTS_VIEW(Module.SYSTEM_SERVICE_CLIENTS),

    /* Permissions for the SYSTEM_CHANNELS module */
    CHANNELS_MANAGE(Module.SYSTEM_CHANNELS),
    CHANNELS_VIEW(Module.SYSTEM_CHANNELS),

    /* Permissions for the SYSTEM_GUARANTEE_TYPES module */
    GUARANTEE_TYPES_MANAGE(Module.SYSTEM_GUARANTEE_TYPES),
    GUARANTEE_TYPES_VIEW(Module.SYSTEM_GUARANTEE_TYPES),

    /* Permissions for the SYSTEM_CARD_TYPES module */
    CARD_TYPES_MANAGE(Module.SYSTEM_CARD_TYPES),
    CARD_TYPES_VIEW(Module.SYSTEM_CARD_TYPES),

    /* Permissions for the SYSTEM_INFO_TEXTS module */
    INFO_TEXTS_MANAGE(Module.SYSTEM_INFO_TEXTS),
    INFO_TEXTS_VIEW(Module.SYSTEM_INFO_TEXTS);

    private final Module module;
    private String       value;
    private String       qualifiedName;
    private Relationship relationship;

    /**
     * Constructor for boolean permissions
     */
    private AdminSystemPermission(final Module module) {
        this(module, null);
    }

    private AdminSystemPermission(final Module module, final Relationship relationship) {
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
