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
 * This enum contains all permissions related to the ADMIN_MEMBER module type
 * @author ameyer
 */
public enum AdminMemberPermission implements AdminPermission {
    /* Permissions for the ADMIN_MEMBERS module */
    MEMBERS_VIEW(Module.ADMIN_MEMBERS, AdminGroup.Relationships.MANAGES_GROUPS),
    MEMBERS_REGISTER(Module.ADMIN_MEMBERS),
    MEMBERS_MANAGE_PENDING(Module.ADMIN_MEMBERS),
    MEMBERS_CHANGE_PROFILE(Module.ADMIN_MEMBERS),
    MEMBERS_CHANGE_NAME(Module.ADMIN_MEMBERS),
    MEMBERS_CHANGE_USERNAME(Module.ADMIN_MEMBERS),
    MEMBERS_CHANGE_EMAIL(Module.ADMIN_MEMBERS),
    MEMBERS_REMOVE(Module.ADMIN_MEMBERS),
    MEMBERS_CHANGE_GROUP(Module.ADMIN_MEMBERS),
    MEMBERS_IMPORT(Module.ADMIN_MEMBERS),

    /* Permissions for the ADMIN_MEMBER_ACCESS module */
    ACCESS_CHANGE_PASSWORD(Module.ADMIN_MEMBER_ACCESS),
    ACCESS_RESET_PASSWORD(Module.ADMIN_MEMBER_ACCESS),
    ACCESS_TRANSACTION_PASSWORD(Module.ADMIN_MEMBER_ACCESS),
    ACCESS_DISCONNECT(Module.ADMIN_MEMBER_ACCESS),
    ACCESS_DISCONNECT_OPERATOR(Module.ADMIN_MEMBER_ACCESS),
    ACCESS_ENABLE_LOGIN(Module.ADMIN_MEMBER_ACCESS),
    ACCESS_CHANGE_PIN(Module.ADMIN_MEMBER_ACCESS),
    ACCESS_UNBLOCK_PIN(Module.ADMIN_MEMBER_ACCESS),
    ACCESS_CHANGE_CHANNELS_ACCESS(Module.ADMIN_MEMBER_ACCESS),

    /* Permissions for the ADMIN_MEMBER_BROKERINGS module */
    BROKERINGS_CHANGE_BROKER(Module.ADMIN_MEMBER_BROKERINGS),
    BROKERINGS_VIEW_MEMBERS(Module.ADMIN_MEMBER_BROKERINGS),
    BROKERINGS_VIEW_LOANS(Module.ADMIN_MEMBER_BROKERINGS),
    BROKERINGS_MANAGE_COMMISSIONS(Module.ADMIN_MEMBER_BROKERINGS),

    /* Permissions for the ADMIN_MEMBER_ACCOUNTS module */
    ACCOUNTS_INFORMATION(Module.ADMIN_MEMBER_ACCOUNTS),
    ACCOUNTS_AUTHORIZED_INFORMATION(Module.ADMIN_MEMBER_ACCOUNTS),
    ACCOUNTS_SCHEDULED_INFORMATION(Module.ADMIN_MEMBER_ACCOUNTS),
    ACCOUNTS_CREDIT_LIMIT(Module.ADMIN_MEMBER_ACCOUNTS),

    /* Permissions for the ADMIN_MEMBER_GROUPS module */
    GROUPS_VIEW(Module.ADMIN_MEMBER_GROUPS),
    GROUPS_MANAGE_ACCOUNT_SETTINGS(Module.ADMIN_MEMBER_GROUPS),
    GROUPS_MANAGE_MEMBER_CUSTOMIZED_FILES(Module.ADMIN_MEMBER_GROUPS),

    /* Permissions for the ADMIN_MEMBER_REPORTS module */
    REPORTS_VIEW(Module.ADMIN_MEMBER_REPORTS),
    REPORTS_SHOW_ACCOUNT_INFORMATION(Module.ADMIN_MEMBER_REPORTS),

    /* Permissions for the ADMIN_MEMBER_PAYMENTS module */
    PAYMENTS_PAYMENT(Module.ADMIN_MEMBER_PAYMENTS, Group.Relationships.TRANSFER_TYPES),
    PAYMENTS_PAYMENT_WITH_DATE(Module.ADMIN_MEMBER_PAYMENTS),
    PAYMENTS_PAYMENT_AS_MEMBER_TO_MEMBER(Module.ADMIN_MEMBER_PAYMENTS, AdminGroup.Relationships.TRANSFER_TYPES_AS_MEMBER),
    PAYMENTS_PAYMENT_AS_MEMBER_TO_SELF(Module.ADMIN_MEMBER_PAYMENTS, AdminGroup.Relationships.TRANSFER_TYPES_AS_MEMBER),
    PAYMENTS_PAYMENT_AS_MEMBER_TO_SYSTEM(Module.ADMIN_MEMBER_PAYMENTS, AdminGroup.Relationships.TRANSFER_TYPES_AS_MEMBER),
    PAYMENTS_AUTHORIZE(Module.ADMIN_MEMBER_PAYMENTS),
    PAYMENTS_CANCEL_AUTHORIZED_AS_MEMBER(Module.ADMIN_MEMBER_PAYMENTS),
    PAYMENTS_CANCEL_SCHEDULED_AS_MEMBER(Module.ADMIN_MEMBER_PAYMENTS),
    PAYMENTS_BLOCK_SCHEDULED_AS_MEMBER(Module.ADMIN_MEMBER_PAYMENTS),
    PAYMENTS_CHARGEBACK(Module.ADMIN_MEMBER_PAYMENTS, SystemGroup.Relationships.CHARGEBACK_TRANSFER_TYPES),

    /* Permissions for the ADMIN_MEMBER_INVOICES module */
    INVOICES_SEND(Module.ADMIN_MEMBER_INVOICES),
    INVOICES_VIEW(Module.ADMIN_MEMBER_INVOICES),
    INVOICES_ACCEPT(Module.ADMIN_MEMBER_INVOICES),
    INVOICES_CANCEL(Module.ADMIN_MEMBER_INVOICES),
    INVOICES_DENY(Module.ADMIN_MEMBER_INVOICES),
    INVOICES_SEND_AS_MEMBER_TO_MEMBER(Module.ADMIN_MEMBER_INVOICES),
    INVOICES_SEND_AS_MEMBER_TO_SYSTEM(Module.ADMIN_MEMBER_INVOICES),
    INVOICES_ACCEPT_AS_MEMBER_FROM_MEMBER(Module.ADMIN_MEMBER_INVOICES),
    INVOICES_ACCEPT_AS_MEMBER_FROM_SYSTEM(Module.ADMIN_MEMBER_INVOICES),
    INVOICES_DENY_AS_MEMBER(Module.ADMIN_MEMBER_INVOICES),
    INVOICES_CANCEL_AS_MEMBER(Module.ADMIN_MEMBER_INVOICES),

    /* Permissions for the ADMIN_MEMBER_ADS module */
    ADS_VIEW(Module.ADMIN_MEMBER_ADS),
    ADS_MANAGE(Module.ADMIN_MEMBER_ADS),
    ADS_IMPORT(Module.ADMIN_MEMBER_ADS),

    /* Permissions for the ADMIN_MEMBER_PREFERENCES module */
    REFERENCES_VIEW(Module.ADMIN_MEMBER_REFERENCES),
    REFERENCES_MANAGE(Module.ADMIN_MEMBER_REFERENCES),

    /* Permissions for the ADMIN_MEMBER_TRANSACTION_FEEDBACKS module */
    TRANSACTION_FEEDBACKS_VIEW(Module.ADMIN_MEMBER_TRANSACTION_FEEDBACKS),
    TRANSACTION_FEEDBACKS_MANAGE(Module.ADMIN_MEMBER_TRANSACTION_FEEDBACKS),

    /* Permissions for the ADMIN_MEMBER_LOANS module */
    LOANS_VIEW(Module.ADMIN_MEMBER_LOANS),
    LOANS_VIEW_AUTHORIZED(Module.ADMIN_MEMBER_LOANS),
    LOANS_GRANT(Module.ADMIN_MEMBER_LOANS, Group.Relationships.TRANSFER_TYPES),
    LOANS_GRANT_WITH_DATE(Module.ADMIN_MEMBER_LOANS),
    LOANS_DISCARD(Module.ADMIN_MEMBER_LOANS),
    LOANS_REPAY(Module.ADMIN_MEMBER_LOANS),
    LOANS_REPAY_WITH_DATE(Module.ADMIN_MEMBER_LOANS),
    LOANS_MANAGE_EXPIRED_STATUS(Module.ADMIN_MEMBER_LOANS),

    /* Permissions for the ADMIN_MEMBER_LOAN_GROUPS module */
    LOAN_GROUPS_MANAGE(Module.ADMIN_MEMBER_LOAN_GROUPS),
    LOAN_GROUPS_VIEW(Module.ADMIN_MEMBER_LOAN_GROUPS),

    /* Permissions for the ADMIN_MEMBER_MESSAGES module */
    MESSAGES_VIEW(Module.ADMIN_MEMBER_MESSAGES, SystemGroup.Relationships.MESSAGE_CATEGORIES),
    MESSAGES_SEND_TO_MEMBER(Module.ADMIN_MEMBER_MESSAGES),
    MESSAGES_SEND_TO_GROUP(Module.ADMIN_MEMBER_MESSAGES),
    MESSAGES_MANAGE(Module.ADMIN_MEMBER_MESSAGES),

    /* Permissions for the ADMIN_MEMBER_DOCUMENTS module */
    DOCUMENTS_DETAILS(Module.ADMIN_MEMBER_DOCUMENTS, SystemGroup.Relationships.DOCUMENTS),
    DOCUMENTS_MANAGE_DYNAMIC(Module.ADMIN_MEMBER_DOCUMENTS),
    DOCUMENTS_MANAGE_STATIC(Module.ADMIN_MEMBER_DOCUMENTS),
    DOCUMENTS_MANAGE_MEMBER(Module.ADMIN_MEMBER_DOCUMENTS),

    /* Permissions for the ADMIN_MEMBER_RECORDS module */
    RECORDS_VIEW(Module.ADMIN_MEMBER_RECORDS, AdminGroup.Relationships.VIEW_MEMBER_RECORD_TYPES),
    RECORDS_CREATE(Module.ADMIN_MEMBER_RECORDS, AdminGroup.Relationships.CREATE_MEMBER_RECORD_TYPES),
    RECORDS_MODIFY(Module.ADMIN_MEMBER_RECORDS, AdminGroup.Relationships.MODIFY_MEMBER_RECORD_TYPES),
    RECORDS_DELETE(Module.ADMIN_MEMBER_RECORDS, AdminGroup.Relationships.DELETE_MEMBER_RECORD_TYPES),

    /* Permissions for the ADMIN_MEMBER_BULK_ACTIONS module */
    BULK_ACTIONS_CHANGE_GROUP(Module.ADMIN_MEMBER_BULK_ACTIONS),
    BULK_ACTIONS_CHANGE_BROKER(Module.ADMIN_MEMBER_BULK_ACTIONS),
    BULK_ACTIONS_GENERATE_CARD(Module.ADMIN_MEMBER_BULK_ACTIONS),
    BULK_ACTIONS_CHANGE_CHANNELS(Module.ADMIN_MEMBER_BULK_ACTIONS),

    /* Permissions for the ADMIN_MEMBER_SMS module */
    SMS_VIEW(Module.ADMIN_MEMBER_SMS),

    /* Permissions for the ADMIN_MEMBER_SMS_MAILINGS module */
    SMS_MAILINGS_VIEW(Module.ADMIN_MEMBER_SMS_MAILINGS),
    SMS_MAILINGS_FREE_SMS_MAILINGS(Module.ADMIN_MEMBER_SMS_MAILINGS),
    SMS_MAILINGS_PAID_SMS_MAILINGS(Module.ADMIN_MEMBER_SMS_MAILINGS),

    /* Permissions for the ADMIN_MEMBER_BULK_GUARANTEES module */
    GUARANTEES_VIEW_PAYMENT_OBLIGATIONS(Module.ADMIN_MEMBER_GUARANTEES),
    GUARANTEES_VIEW_CERTIFICATIONS(Module.ADMIN_MEMBER_GUARANTEES),
    GUARANTEES_VIEW_GUARANTEES(Module.ADMIN_MEMBER_GUARANTEES),
    GUARANTEES_REGISTER_GUARANTEES(Module.ADMIN_MEMBER_GUARANTEES, Group.Relationships.GUARANTEE_TYPES),
    GUARANTEES_CANCEL_CERTIFICATIONS_AS_MEMBER(Module.ADMIN_MEMBER_GUARANTEES),
    GUARANTEES_CANCEL_GUARANTEES_AS_MEMBER(Module.ADMIN_MEMBER_GUARANTEES),
    GUARANTEES_ACCEPT_GUARANTEES_AS_MEMBER(Module.ADMIN_MEMBER_GUARANTEES),

    /* Permissions for the ADMIN_MEMBER_BULK_CARDS module */
    CARDS_VIEW(Module.ADMIN_MEMBER_CARDS),
    CARDS_GENERATE(Module.ADMIN_MEMBER_CARDS),
    CARDS_CANCEL(Module.ADMIN_MEMBER_CARDS),
    CARDS_BLOCK(Module.ADMIN_MEMBER_CARDS),
    CARDS_UNBLOCK(Module.ADMIN_MEMBER_CARDS),
    CARDS_CHANGE_CARD_SECURITY_CODE(Module.ADMIN_MEMBER_CARDS),
    CARDS_UNBLOCK_SECURITY_CODE(Module.ADMIN_MEMBER_CARDS),

    /* Permissions for the ADMIN_MEMBER_BULK_POS module */
    POS_VIEW(Module.ADMIN_MEMBER_POS),
    POS_MANAGE(Module.ADMIN_MEMBER_POS),
    POS_ASSIGN(Module.ADMIN_MEMBER_POS),
    POS_BLOCK(Module.ADMIN_MEMBER_POS),
    POS_DISCARD(Module.ADMIN_MEMBER_POS),
    POS_UNBLOCK_PIN(Module.ADMIN_MEMBER_POS),
    POS_CHANGE_PIN(Module.ADMIN_MEMBER_POS),
    POS_CHANGE_PARAMETERS(Module.ADMIN_MEMBER_POS),

    /* Permissions for the ADMIN_MEMBER_BULK_PREFERENCES module */
    PREFERENCES_MANAGE_NOTIFICATIONS(Module.ADMIN_MEMBER_PREFERENCES);

    private final Module module;
    private String       value;
    private String       qualifiedName;
    private Relationship relationship;

    /**
     * Constructor for boolean permissions
     */
    private AdminMemberPermission(final Module module) {
        this(module, null);
    }

    private AdminMemberPermission(final Module module, final Relationship relationship) {
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
