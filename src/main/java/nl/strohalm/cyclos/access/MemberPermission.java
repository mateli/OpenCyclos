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
import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.groups.SystemGroup;
import nl.strohalm.cyclos.utils.access.PermissionHelper;

/**
 * This enum contains all permissions related to the MEMBER module type
 * @author ameyer
 */
public enum MemberPermission implements Permission {
    /* Permissions for the MEMBER_PROFILE module */
    PROFILE_VIEW(Module.MEMBER_PROFILE, MemberGroup.Relationships.CAN_VIEW_PROFILE_OF_GROUPS),
    PROFILE_CHANGE_USERNAME(Module.MEMBER_PROFILE),
    PROFILE_CHANGE_NAME(Module.MEMBER_PROFILE),
    PROFILE_CHANGE_EMAIL(Module.MEMBER_PROFILE),

    /* Permissions for the MEMBER_ACCESS module */
    ACCESS_CHANGE_CHANNELS_ACCESS(Module.MEMBER_ACCESS),
    ACCESS_UNBLOCK_PIN(Module.MEMBER_ACCESS),

    /* Permissions for the MEMBER_ACCOUNT module */
    ACCOUNT_AUTHORIZED_INFORMATION(Module.MEMBER_ACCOUNT),
    ACCOUNT_SCHEDULED_INFORMATION(Module.MEMBER_ACCOUNT),

    /* Permissions for the MEMBER_PAYMENTS module */
    PAYMENTS_PAYMENT_TO_SELF(Module.MEMBER_PAYMENTS, Group.Relationships.TRANSFER_TYPES),
    PAYMENTS_PAYMENT_TO_MEMBER(Module.MEMBER_PAYMENTS, Group.Relationships.TRANSFER_TYPES),
    PAYMENTS_PAYMENT_TO_SYSTEM(Module.MEMBER_PAYMENTS, Group.Relationships.TRANSFER_TYPES),
    PAYMENTS_TICKET(Module.MEMBER_PAYMENTS),
    PAYMENTS_AUTHORIZE(Module.MEMBER_PAYMENTS),
    PAYMENTS_CANCEL_AUTHORIZED(Module.MEMBER_PAYMENTS),
    PAYMENTS_CANCEL_SCHEDULED(Module.MEMBER_PAYMENTS),
    PAYMENTS_BLOCK_SCHEDULED(Module.MEMBER_PAYMENTS),
    PAYMENTS_REQUEST(Module.MEMBER_PAYMENTS, MemberGroup.Relationships.REQUEST_PAYMENT_BY_CHANNELS),
    PAYMENTS_CHARGEBACK(Module.MEMBER_PAYMENTS, SystemGroup.Relationships.CHARGEBACK_TRANSFER_TYPES),

    /* Permissions for the MEMBER_INVOICES module */
    INVOICES_VIEW(Module.MEMBER_INVOICES),
    INVOICES_SEND_TO_MEMBER(Module.MEMBER_INVOICES),
    INVOICES_SEND_TO_SYSTEM(Module.MEMBER_INVOICES),

    /* Permissions for the MEMBER_REFERENCES module */
    REFERENCES_VIEW(Module.MEMBER_REFERENCES),
    REFERENCES_GIVE(Module.MEMBER_REFERENCES),

    /* Permissions for the MEMBER_DOCUMENTS module */
    DOCUMENTS_VIEW(Module.MEMBER_DOCUMENTS, SystemGroup.Relationships.DOCUMENTS),

    /* Permissions for the MEMBER_LOANS module */
    LOANS_VIEW(Module.MEMBER_LOANS),
    LOANS_REPAY(Module.MEMBER_LOANS),

    /* Permissions for the MEMBER_ADS module */
    ADS_VIEW(Module.MEMBER_ADS, MemberGroup.Relationships.CAN_VIEW_ADS_OF_GROUPS),
    ADS_PUBLISH(Module.MEMBER_ADS),

    /* Permissions for the MEMBER_PREFERENCES module */
    PREFERENCES_MANAGE_NOTIFICATIONS(Module.MEMBER_PREFERENCES),
    PREFERENCES_MANAGE_AD_INTERESTS(Module.MEMBER_PREFERENCES),
    PREFERENCES_MANAGE_RECEIPT_PRINTER_SETTINGS(Module.MEMBER_PREFERENCES),

    /* Permissions for the MEMBER_REPORTS module */
    REPORTS_VIEW(Module.MEMBER_REPORTS),
    REPORTS_SHOW_ACCOUNT_INFORMATION(Module.MEMBER_REPORTS, MemberGroup.Relationships.CAN_VIEW_INFORMATION_OF),

    /* Permissions for the MEMBER_MESSAGES module */
    MESSAGES_VIEW(Module.MEMBER_MESSAGES),
    MESSAGES_SEND_TO_MEMBER(Module.MEMBER_MESSAGES),
    MESSAGES_SEND_TO_ADMINISTRATION(Module.MEMBER_MESSAGES, SystemGroup.Relationships.MESSAGE_CATEGORIES),
    MESSAGES_MANAGE(Module.MEMBER_MESSAGES),

    /* Permissions for the MEMBER_OPERATORS module */
    OPERATORS_MANAGE(Module.MEMBER_OPERATORS),

    /* Permissions for the MEMBER_SMS module */
    SMS_VIEW(Module.MEMBER_SMS),

    /* Permissions for the MEMBER_GUARANTEES module */
    GUARANTEES_ISSUE_GUARANTEES(Module.MEMBER_GUARANTEES, Group.Relationships.GUARANTEE_TYPES),
    GUARANTEES_ISSUE_CERTIFICATIONS(Module.MEMBER_GUARANTEES, MemberGroup.Relationships.CAN_ISSUE_CERTIFICATION_TO_GROUPS),
    GUARANTEES_BUY_WITH_PAYMENT_OBLIGATIONS(Module.MEMBER_GUARANTEES, MemberGroup.Relationships.CAN_BUY_WITH_PAYMENT_OBLIGATIONS_FROM_GROUPS),
    GUARANTEES_SELL_WITH_PAYMENT_OBLIGATIONS(Module.MEMBER_GUARANTEES),

    /* Permissions for the MEMBER_CARDS module */
    CARDS_VIEW(Module.MEMBER_CARDS),
    CARDS_BLOCK(Module.MEMBER_CARDS),
    CARDS_UNBLOCK(Module.MEMBER_CARDS),
    CARDS_CHANGE_CARD_SECURITY_CODE(Module.MEMBER_CARDS);

    private final Module module;
    private String       value;
    private String       qualifiedName;
    private Relationship relationship;

    /**
     * Constructor for boolean permissions
     */
    private MemberPermission(final Module module) {
        this.module = module;
    }

    private MemberPermission(final Module module, final Relationship relationship) {
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
