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

import java.util.Collection;

import nl.strohalm.cyclos.entities.accounts.SystemAccountType;
import nl.strohalm.cyclos.entities.accounts.guarantees.GuaranteeType;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferType;
import nl.strohalm.cyclos.entities.alerts.MemberAlert;
import nl.strohalm.cyclos.entities.alerts.SystemAlert;
import nl.strohalm.cyclos.entities.groups.AdminGroup;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.members.messages.MessageCategory;
import nl.strohalm.cyclos.utils.query.QueryParameters;

/**
 * Query for admin notification preferences according to events
 * @author luis
 * @author Lucas Geiss
 */
public class AdminNotificationPreferenceQuery extends QueryParameters {
    private static final long             serialVersionUID = -724692784969808657L;
    private boolean                       applicationErrors;
    private boolean                       systemInvoices;
    private TransferType                  transferType;
    private TransferType                  newPendingPayment;
    private GuaranteeType                 guaranteeType;
    private MessageCategory               messageCategory;
    private SystemAlert.Alerts            systemAlert;
    private MemberAlert.Alerts            memberAlert;
    private MemberGroup                   memberGroup;
    private MemberGroup                   newMemberGroup;
    private Collection<SystemAccountType> accountTypes;
    private Collection<AdminGroup>        adminGroups;

    public AdminNotificationPreferenceQuery() {
        // The default result type is iterator
        setResultType(ResultType.ITERATOR);
    }

    public Collection<SystemAccountType> getAccountTypes() {
        return accountTypes;
    }

    public Collection<AdminGroup> getAdminGroups() {
        return adminGroups;
    }

    public GuaranteeType getGuaranteeType() {
        return guaranteeType;
    }

    public MemberAlert.Alerts getMemberAlert() {
        return memberAlert;
    }

    public MemberGroup getMemberGroup() {
        return memberGroup;
    }

    public MessageCategory getMessageCategory() {
        return messageCategory;
    }

    public MemberGroup getNewMemberGroup() {
        return newMemberGroup;
    }

    public TransferType getNewPendingPayment() {
        return newPendingPayment;
    }

    public SystemAlert.Alerts getSystemAlert() {
        return systemAlert;
    }

    public TransferType getTransferType() {
        return transferType;
    }

    public boolean isApplicationErrors() {
        return applicationErrors;
    }

    public boolean isSystemInvoices() {
        return systemInvoices;
    }

    public void setAccountTypes(final Collection<SystemAccountType> accountTypes) {
        this.accountTypes = accountTypes;
    }

    public void setAdminGroups(final Collection<AdminGroup> adminGroups) {
        this.adminGroups = adminGroups;
    }

    public void setApplicationErrors(final boolean applicationErrors) {
        this.applicationErrors = applicationErrors;
    }

    public void setGuaranteeType(final GuaranteeType guaranteeType) {
        this.guaranteeType = guaranteeType;
    }

    public void setMemberAlert(final MemberAlert.Alerts memberAlert) {
        this.memberAlert = memberAlert;
    }

    public void setMemberGroup(final MemberGroup memberGroup) {
        this.memberGroup = memberGroup;
    }

    public void setMessageCategory(final MessageCategory messageCategory) {
        this.messageCategory = messageCategory;
    }

    public void setNewMemberGroup(final MemberGroup newMemberGroup) {
        this.newMemberGroup = newMemberGroup;
    }

    public void setNewPendingPayment(final TransferType newPendingPayment) {
        this.newPendingPayment = newPendingPayment;
    }

    public void setSystemAlert(final SystemAlert.Alerts systemAlert) {
        this.systemAlert = systemAlert;
    }

    public void setSystemInvoices(final boolean systemInvoices) {
        this.systemInvoices = systemInvoices;
    }

    public void setTransferType(final TransferType transferType) {
        this.transferType = transferType;
    }

}
