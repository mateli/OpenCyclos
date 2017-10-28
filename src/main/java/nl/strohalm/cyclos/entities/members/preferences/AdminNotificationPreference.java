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
package nl.strohalm.cyclos.entities.members.preferences;

import nl.strohalm.cyclos.entities.Entity;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.guarantees.GuaranteeType;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferType;
import nl.strohalm.cyclos.entities.alerts.MemberAlert;
import nl.strohalm.cyclos.entities.alerts.SystemAlert;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.members.Administrator;
import nl.strohalm.cyclos.entities.members.messages.MessageCategory;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Set;

/**
 * Notification Preference for an administrator
 * @author luis
 * @author Lucas Geiss
 */
@Table(name = "admin_notification_preferences")
@javax.persistence.Entity
public class AdminNotificationPreference extends Entity {

    public static enum Relationships implements Relationship {
        ADMIN("admin"), TRANSFER_TYPES("transferTypes"), MESSAGE_CATEGORIES("messageCategories"), SYSTEM_ALERTS("systemAlerts"), MEMBER_ALERTS("memberAlerts"), NEW_PENDING_PAYMENTS("newPendingPayments"), NEW_MEMBERS("newMembers");
        private final String name;

        private Relationships(final String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }
    }

    private static final long       serialVersionUID = -4791497274620475610L;

    @Column(name = "application_errors", nullable = false)
    private boolean                 applicationErrors;

    @Column(name = "system_invoices", nullable = false)
    private boolean                 systemInvoices;

    @ManyToOne
    @JoinColumn(name = "admin_id")
	private Administrator           admin;

    @ManyToMany
    @JoinTable(name = "admin_preferences_transfer_types",
            joinColumns = @JoinColumn(name = "preference_id"),
            inverseJoinColumns = @JoinColumn(name = "transfer_type_id"))
	private Set<TransferType>       transferTypes;

    @ManyToMany
    @JoinTable(name = "admin_preferences_new_pending_payments",
            joinColumns = @JoinColumn(name = "preference_id"),
            inverseJoinColumns = @JoinColumn(name = "transfer_type_id"))
	private Set<TransferType>       newPendingPayments;

    @ManyToMany
    @JoinTable(name = "admin_preferences_guarantee_types",
            joinColumns = @JoinColumn(name = "preference_id"),
            inverseJoinColumns = @JoinColumn(name = "guarantee_type_id"))
	private Set<GuaranteeType>      guaranteeTypes;

    @ManyToMany
    @JoinTable(name = "admin_preferences_message_categories",
            joinColumns = @JoinColumn(name = "preference_id"),
            inverseJoinColumns = @JoinColumn(name = "message_category_id"))
	private Set<MessageCategory>    messageCategories;

    @ElementCollection
    @CollectionTable(name = "admin_preferences_system_alerts", joinColumns = @JoinColumn(name = "preference_id"))
    @Column(name = "type", length = 70)
	private Set<SystemAlert.Alerts> systemAlerts;

    @ElementCollection
    @CollectionTable(name = "admin_preferences_member_alerts", joinColumns = @JoinColumn(name = "preference_id"))
    @Column(name = "type", length = 70)
	private Set<MemberAlert.Alerts> memberAlerts;

    @ManyToMany
    @JoinTable(name = "admin_preferences_new_members",
            joinColumns = @JoinColumn(name = "preference_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id"))
	private Set<MemberGroup>        newMembers;

	public Administrator getAdmin() {
        return admin;
    }

    public Set<GuaranteeType> getGuaranteeTypes() {
        return guaranteeTypes;
    }

    public Set<MemberAlert.Alerts> getMemberAlerts() {
        return memberAlerts;
    }

    public Set<MessageCategory> getMessageCategories() {
        return messageCategories;
    }

    public Set<MemberGroup> getNewMembers() {
        return newMembers;
    }

    public Set<TransferType> getNewPendingPayments() {
        return newPendingPayments;
    }

    public Set<SystemAlert.Alerts> getSystemAlerts() {
        return systemAlerts;
    }

    public Set<TransferType> getTransferTypes() {
        return transferTypes;
    }

    public boolean isApplicationErrors() {
        return applicationErrors;
    }

    public boolean isSystemInvoices() {
        return systemInvoices;
    }

    public void setAdmin(final Administrator admin) {
        this.admin = admin;
    }

    public void setApplicationErrors(final boolean applicationErrors) {
        this.applicationErrors = applicationErrors;
    }

    public void setGuaranteeTypes(final Set<GuaranteeType> guaranteeTypes) {
        this.guaranteeTypes = guaranteeTypes;
    }

    public void setMemberAlerts(final Set<MemberAlert.Alerts> memberAlerts) {
        this.memberAlerts = memberAlerts;
    }

    public void setMessageCategories(final Set<MessageCategory> messageCategories) {
        this.messageCategories = messageCategories;
    }

    public void setNewMembers(final Set<MemberGroup> newMembers) {
        this.newMembers = newMembers;
    }

    public void setNewPendingPayments(final Set<TransferType> newPendingPayments) {
        this.newPendingPayments = newPendingPayments;
    }

    public void setSystemAlerts(final Set<SystemAlert.Alerts> systemAlerts) {
        this.systemAlerts = systemAlerts;
    }

    public void setSystemInvoices(final boolean systemInvoices) {
        this.systemInvoices = systemInvoices;
    }

    public void setTransferTypes(final Set<TransferType> transferTypes) {
        this.transferTypes = transferTypes;
    }

    @Override
    public String toString() {
        return getId() + " - " + admin;
    }
}
