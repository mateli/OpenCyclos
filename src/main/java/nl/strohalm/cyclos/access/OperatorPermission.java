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
import nl.strohalm.cyclos.entities.groups.OperatorGroup;
import nl.strohalm.cyclos.utils.access.PermissionHelper;

/**
 * This enum contains all permissions related to the OPERATOR module type
 * @author ameyer
 */
public enum OperatorPermission implements Permission {
    /* Permissions for the OPERATOR_ACCOUNT module */
    ACCOUNT_AUTHORIZED_INFORMATION(Module.OPERATOR_ACCOUNT, MemberPermission.ACCOUNT_AUTHORIZED_INFORMATION),
    ACCOUNT_SCHEDULED_INFORMATION(Module.OPERATOR_ACCOUNT, MemberPermission.ACCOUNT_SCHEDULED_INFORMATION),
    ACCOUNT_ACCOUNT_INFORMATION(Module.OPERATOR_ACCOUNT, OperatorGroup.Relationships.CAN_VIEW_INFORMATION_OF),

    /* Permissions for the OPERATOR_PAYMENTS module */
    PAYMENTS_PAYMENT_TO_SELF(Module.OPERATOR_PAYMENTS, MemberPermission.PAYMENTS_PAYMENT_TO_SELF),
    PAYMENTS_PAYMENT_TO_MEMBER(Module.OPERATOR_PAYMENTS, MemberPermission.PAYMENTS_PAYMENT_TO_MEMBER),
    PAYMENTS_PAYMENT_TO_SYSTEM(Module.OPERATOR_PAYMENTS, MemberPermission.PAYMENTS_PAYMENT_TO_SYSTEM),
    PAYMENTS_POSWEB_MAKE_PAYMENT(Module.OPERATOR_PAYMENTS, MemberPermission.PAYMENTS_PAYMENT_TO_MEMBER), // PosWeb permission
    PAYMENTS_POSWEB_RECEIVE_PAYMENT(Module.OPERATOR_PAYMENTS), // PosWeb permission
    PAYMENTS_AUTHORIZE(Module.OPERATOR_PAYMENTS, MemberPermission.PAYMENTS_AUTHORIZE),
    PAYMENTS_CANCEL_AUTHORIZED(Module.OPERATOR_PAYMENTS, MemberPermission.PAYMENTS_CANCEL_AUTHORIZED),
    PAYMENTS_CANCEL_SCHEDULED(Module.OPERATOR_PAYMENTS, MemberPermission.PAYMENTS_CANCEL_SCHEDULED),
    PAYMENTS_BLOCK_SCHEDULED(Module.OPERATOR_PAYMENTS, MemberPermission.PAYMENTS_BLOCK_SCHEDULED),
    PAYMENTS_REQUEST(Module.OPERATOR_PAYMENTS, MemberPermission.PAYMENTS_REQUEST),

    /* Permissions for the OPERATOR_INVOICES module */
    INVOICES_VIEW(Module.OPERATOR_INVOICES, MemberPermission.INVOICES_VIEW),
    INVOICES_SEND_TO_MEMBER(Module.OPERATOR_INVOICES, MemberPermission.INVOICES_SEND_TO_MEMBER),
    INVOICES_SEND_TO_SYSTEM(Module.OPERATOR_INVOICES, MemberPermission.INVOICES_SEND_TO_SYSTEM),
    INVOICES_MANAGE(Module.OPERATOR_INVOICES, MemberPermission.INVOICES_SEND_TO_MEMBER, MemberPermission.INVOICES_SEND_TO_SYSTEM),

    /* Permissions for the OPERATOR_REFERENCES module */
    REFERENCES_VIEW(Module.OPERATOR_REFERENCES, MemberPermission.REFERENCES_VIEW),
    REFERENCES_MANAGE_MEMBER_REFERENCES(Module.OPERATOR_REFERENCES, MemberPermission.REFERENCES_GIVE),
    REFERENCES_MANAGE_MEMBER_TRANSACTION_FEEDBACKS(Module.OPERATOR_REFERENCES, MemberPermission.REFERENCES_GIVE),

    /* Permissions for the OPERATOR_LOANS module */
    LOANS_VIEW(Module.OPERATOR_LOANS, MemberPermission.LOANS_VIEW),
    LOANS_REPAY(Module.OPERATOR_LOANS, MemberPermission.LOANS_REPAY),

    /* Permissions for the OPERATOR_ADS module */
    ADS_PUBLISH(Module.OPERATOR_ADS, MemberPermission.ADS_PUBLISH),

    /* Permissions for the OPERATOR_REPORTS module */
    REPORTS_VIEW_MEMBER(Module.OPERATOR_REPORTS),

    /* Permissions for the OPERATOR_CONTACTS module */
    CONTACTS_MANAGE(Module.OPERATOR_CONTACTS),
    CONTACTS_VIEW(Module.OPERATOR_CONTACTS),

    /* Permissions for the OPERATOR_GUARANTEES module */
    GUARANTEES_ISSUE_GUARANTEES(Module.OPERATOR_GUARANTEES, Group.Relationships.GUARANTEE_TYPES, MemberPermission.GUARANTEES_ISSUE_GUARANTEES),
    GUARANTEES_ISSUE_CERTIFICATIONS(Module.OPERATOR_GUARANTEES, MemberPermission.GUARANTEES_ISSUE_CERTIFICATIONS),
    GUARANTEES_BUY_WITH_PAYMENT_OBLIGATIONS(Module.OPERATOR_GUARANTEES, MemberPermission.GUARANTEES_BUY_WITH_PAYMENT_OBLIGATIONS),
    GUARANTEES_SELL_WITH_PAYMENT_OBLIGATIONS(Module.OPERATOR_GUARANTEES, MemberPermission.GUARANTEES_SELL_WITH_PAYMENT_OBLIGATIONS),

    /* Permissions for the OPERATOR_MESSAGES module */
    MESSAGES_VIEW(Module.OPERATOR_MESSAGES, MemberPermission.MESSAGES_VIEW),
    MESSAGES_SEND_TO_MEMBER(Module.OPERATOR_MESSAGES, MemberPermission.MESSAGES_SEND_TO_MEMBER),
    MESSAGES_SEND_TO_ADMINISTRATION(Module.OPERATOR_MESSAGES, MemberPermission.MESSAGES_SEND_TO_ADMINISTRATION),
    MESSAGES_MANAGE(Module.OPERATOR_MESSAGES, MemberPermission.MESSAGES_MANAGE);

    private final Module       module;
    private String             value;
    private MemberPermission[] parentPermissions;
    private String             qualifiedName;
    private Relationship       relationship;

    private OperatorPermission(final Module module) {
        this(module, (Relationship) null);
    }

    private OperatorPermission(final Module module, final MemberPermission... parentPermissions) {
        this(module, null, parentPermissions);
    }

    private OperatorPermission(final Module module, final Relationship relationship) {
        this(module, relationship, new MemberPermission[0]);
    }

    private OperatorPermission(final Module module, final Relationship relationship, final MemberPermission... parentPermissions) {
        this.module = module;
        this.parentPermissions = parentPermissions;
        this.relationship = relationship;
    }

    @Override
    public Module getModule() {
        return module;
    }

    /**
     * @return the member permissions that will be checked along with this operator permission.<br>
     * If this is not null (or empty) then before checking the operator permission we'll check if the operator's owner member has at least one of
     * these (related) permissions.
     */
    public MemberPermission[] getParentPermissions() {
        return parentPermissions;
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
