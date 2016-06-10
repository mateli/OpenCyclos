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
import nl.strohalm.cyclos.entities.groups.BrokerGroup;
import nl.strohalm.cyclos.utils.access.PermissionHelper;

/**
 * This enum contains all permissions related to the BROKER module type
 * @author ameyer
 */
public enum BrokerPermission implements Permission {
    /* Permissions for the BROKER_MEMBERS module */
    MEMBERS_REGISTER(Module.BROKER_MEMBERS),
    MEMBERS_MANAGE_PENDING(Module.BROKER_MEMBERS),
    MEMBERS_CHANGE_PROFILE(Module.BROKER_MEMBERS),
    MEMBERS_CHANGE_NAME(Module.BROKER_MEMBERS),
    MEMBERS_CHANGE_USERNAME(Module.BROKER_MEMBERS),
    MEMBERS_CHANGE_EMAIL(Module.BROKER_MEMBERS),
    MEMBERS_MANAGE_DEFAULTS(Module.BROKER_MEMBERS),
    MEMBERS_MANAGE_CONTRACTS(Module.BROKER_MEMBERS),

    /* Permissions for the BROKER_ACCOUNTS module */
    ACCOUNTS_INFORMATION(Module.BROKER_ACCOUNTS),
    ACCOUNTS_AUTHORIZED_INFORMATION(Module.BROKER_ACCOUNTS),
    ACCOUNTS_SCHEDULED_INFORMATION(Module.BROKER_ACCOUNTS),

    /* Permissions for the BROKER_REPORTS module */
    REPORTS_VIEW(Module.BROKER_REPORTS),
    REPORTS_SHOW_ACCOUNT_INFORMATION(Module.BROKER_REPORTS, BrokerGroup.Relationships.BROKER_CAN_VIEW_INFORMATION_OF),

    /* Permissions for the BROKER_ADS module */
    ADS_VIEW(Module.BROKER_ADS),
    ADS_MANAGE(Module.BROKER_ADS),

    /* Permissions for the BROKER_REFERENCES module */
    REFERENCES_MANAGE(Module.BROKER_REFERENCES),

    /* Permissions for the BROKER_INVOICES module */
    INVOICES_VIEW(Module.BROKER_INVOICES),
    INVOICES_SEND_AS_MEMBER_TO_MEMBER(Module.BROKER_INVOICES),
    INVOICES_SEND_AS_MEMBER_TO_SYSTEM(Module.BROKER_INVOICES),
    INVOICES_ACCEPT_AS_MEMBER_FROM_MEMBER(Module.BROKER_INVOICES),
    INVOICES_ACCEPT_AS_MEMBER_FROM_SYSTEM(Module.BROKER_INVOICES),
    INVOICES_DENY_AS_MEMBER(Module.BROKER_INVOICES),
    INVOICES_CANCEL_AS_MEMBER(Module.BROKER_INVOICES),

    /* Permissions for the BROKER_LOANS module */
    LOANS_VIEW(Module.BROKER_LOANS),

    /* Permissions for the BROKER_LOAN_GROUPS module */
    LOAN_GROUPS_VIEW(Module.BROKER_LOAN_GROUPS),

    /* Permissions for the BROKER_DOCUMENTS module */
    DOCUMENTS_VIEW(Module.BROKER_DOCUMENTS, BrokerGroup.Relationships.BROKER_DOCUMENTS),
    DOCUMENTS_VIEW_MEMBER(Module.BROKER_DOCUMENTS),
    DOCUMENTS_MANAGE_MEMBER(Module.BROKER_DOCUMENTS),

    /* Permissions for the BROKER_MESSAGES module */
    MESSAGES_SEND_TO_MEMBERS(Module.BROKER_MESSAGES),

    /* Permissions for the BROKER_MEMBER_ACCESS module */
    MEMBER_ACCESS_CHANGE_PASSWORD(Module.BROKER_MEMBER_ACCESS),
    MEMBER_ACCESS_RESET_PASSWORD(Module.BROKER_MEMBER_ACCESS),
    MEMBER_ACCESS_TRANSACTION_PASSWORD(Module.BROKER_MEMBER_ACCESS),
    MEMBER_ACCESS_CHANGE_PIN(Module.BROKER_MEMBER_ACCESS),
    MEMBER_ACCESS_UNBLOCK_PIN(Module.BROKER_MEMBER_ACCESS),
    MEMBER_ACCESS_CHANGE_CHANNELS_ACCESS(Module.BROKER_MEMBER_ACCESS),

    /* Permissions for the BROKER_MEMBER_PAYMENTS module */
    MEMBER_PAYMENTS_PAYMENT_AS_MEMBER_TO_MEMBER(Module.BROKER_MEMBER_PAYMENTS, BrokerGroup.Relationships.TRANSFER_TYPES_AS_MEMBER),
    MEMBER_PAYMENTS_PAYMENT_AS_MEMBER_TO_SELF(Module.BROKER_MEMBER_PAYMENTS, BrokerGroup.Relationships.TRANSFER_TYPES_AS_MEMBER),
    MEMBER_PAYMENTS_PAYMENT_AS_MEMBER_TO_SYSTEM(Module.BROKER_MEMBER_PAYMENTS, BrokerGroup.Relationships.TRANSFER_TYPES_AS_MEMBER),
    MEMBER_PAYMENTS_AUTHORIZE(Module.BROKER_MEMBER_PAYMENTS),
    MEMBER_PAYMENTS_CANCEL_AUTHORIZED_AS_MEMBER(Module.BROKER_MEMBER_PAYMENTS),
    MEMBER_PAYMENTS_CANCEL_SCHEDULED_AS_MEMBER(Module.BROKER_MEMBER_PAYMENTS),
    MEMBER_PAYMENTS_BLOCK_SCHEDULED_AS_MEMBER(Module.BROKER_MEMBER_PAYMENTS),

    /* Permissions for the BROKER_MEMBER_RECORDS module */
    MEMBER_RECORDS_VIEW(Module.BROKER_MEMBER_RECORDS, BrokerGroup.Relationships.BROKER_MEMBER_RECORD_TYPES),
    MEMBER_RECORDS_CREATE(Module.BROKER_MEMBER_RECORDS, BrokerGroup.Relationships.BROKER_CREATE_MEMBER_RECORD_TYPES),
    MEMBER_RECORDS_MODIFY(Module.BROKER_MEMBER_RECORDS, BrokerGroup.Relationships.BROKER_MODIFY_MEMBER_RECORD_TYPES),
    MEMBER_RECORDS_DELETE(Module.BROKER_MEMBER_RECORDS, BrokerGroup.Relationships.BROKER_DELETE_MEMBER_RECORD_TYPES),

    /* Permissions for the BROKER_MEMBER_SMS module */
    MEMBER_SMS_VIEW(Module.BROKER_MEMBER_SMS),

    /* Permissions for the BROKER_CARDS module */
    CARDS_VIEW(Module.BROKER_CARDS),
    CARDS_GENERATE(Module.BROKER_CARDS),
    CARDS_CANCEL(Module.BROKER_CARDS),
    CARDS_BLOCK(Module.BROKER_CARDS),
    CARDS_UNBLOCK(Module.BROKER_CARDS),
    CARDS_CHANGE_CARD_SECURITY_CODE(Module.BROKER_CARDS),
    CARDS_UNBLOCK_SECURITY_CODE(Module.BROKER_CARDS),

    /* Permissions for the BROKER_POS module */
    POS_VIEW(Module.BROKER_POS),
    POS_MANAGE(Module.BROKER_POS),
    POS_ASSIGN(Module.BROKER_POS),
    POS_BLOCK(Module.BROKER_POS),
    POS_DISCARD(Module.BROKER_POS),
    POS_UNBLOCK_PIN(Module.BROKER_POS),
    POS_CHANGE_PIN(Module.BROKER_POS),
    POS_CHANGE_PARAMETERS(Module.BROKER_POS),

    /* Permissions for the BROKER_SMS_MAILINGS module */
    SMS_MAILINGS_FREE_SMS_MAILINGS(Module.BROKER_SMS_MAILINGS),
    SMS_MAILINGS_PAID_SMS_MAILINGS(Module.BROKER_SMS_MAILINGS),

    /* Permissions for the BROKER_PREFERENCES module */
    PREFERENCES_MANAGE_NOTIFICATIONS(Module.BROKER_PREFERENCES);

    private final Module module;
    private String       value;
    private String       qualifiedName;
    private Relationship relationship;

    /**
     * Constructor for boolean permissions
     */
    private BrokerPermission(final Module module) {
        this(module, null);
    }

    private BrokerPermission(final Module module, final Relationship relationship) {
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
