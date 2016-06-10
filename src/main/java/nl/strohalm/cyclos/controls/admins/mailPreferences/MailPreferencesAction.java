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
package nl.strohalm.cyclos.controls.admins.mailPreferences;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.access.AdminSystemPermission;
import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseFormAction;
import nl.strohalm.cyclos.entities.accounts.guarantees.GuaranteeType;
import nl.strohalm.cyclos.entities.accounts.guarantees.GuaranteeTypeQuery;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferType;
import nl.strohalm.cyclos.entities.alerts.MemberAlert;
import nl.strohalm.cyclos.entities.alerts.SystemAlert;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.entities.groups.AdminGroup;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.groups.SystemGroup;
import nl.strohalm.cyclos.entities.members.Administrator;
import nl.strohalm.cyclos.entities.members.messages.MessageCategory;
import nl.strohalm.cyclos.entities.members.preferences.AdminNotificationPreference;
import nl.strohalm.cyclos.services.accounts.guarantees.GuaranteeTypeService;
import nl.strohalm.cyclos.services.preferences.PreferenceService;
import nl.strohalm.cyclos.services.transfertypes.TransferTypeService;
import nl.strohalm.cyclos.utils.RequestHelper;
import nl.strohalm.cyclos.utils.binding.BeanBinder;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.binding.PropertyBinder;
import nl.strohalm.cyclos.utils.binding.SimpleCollectionBinder;

/**
 * Action used to save the mail preferences
 * @author Lucas Geiss
 */
public class MailPreferencesAction extends BaseFormAction {

    private GuaranteeTypeService                    guaranteeTypeService;
    private PreferenceService                       preferenceService;
    private TransferTypeService                     transferTypeService;
    private DataBinder<AdminNotificationPreference> dataBinder;

    @Inject
    public void setGuaranteeTypeService(final GuaranteeTypeService guaranteeTypeService) {
        this.guaranteeTypeService = guaranteeTypeService;
    }

    @Inject
    public void setPreferenceService(final PreferenceService preferenceService) {
        this.preferenceService = preferenceService;
    }

    @Inject
    public void setTransferTypeService(final TransferTypeService transferTypeService) {
        this.transferTypeService = transferTypeService;
    }

    @Override
    protected void formAction(final ActionContext context) throws Exception {
        final MailPreferencesForm form = context.getForm();
        AdminNotificationPreference notificationPreference = getDataBinder().readFromString(form.getAdminNotificationPreference());
        notificationPreference = preferenceService.save(notificationPreference);
        context.sendMessage("mailPreferences.saved");
    }

    @Override
    protected void prepareForm(final ActionContext context) throws Exception {
        final HttpServletRequest request = context.getRequest();
        final MailPreferencesForm form = context.getForm();

        final Administrator admin = context.getElement();
        AdminGroup group = admin.getAdminGroup();
        group = groupService.load(group.getId(), AdminGroup.Relationships.VIEW_INFORMATION_OF, SystemGroup.Relationships.MESSAGE_CATEGORIES);

        final List<MemberGroup> memberGroups = new ArrayList<MemberGroup>(permissionService.getManagedMemberGroups());
        for (final Iterator<MemberGroup> it = memberGroups.iterator(); it.hasNext();) {
            if (it.next().isRemoved()) {
                it.remove();
            }
        }
        Collections.sort(memberGroups);
        final List<TransferType> transferTypes = transferTypeService.getPaymentAndSelfPaymentTTs();
        final List<TransferType> newPendingPayments = transferTypeService.getAuthorizableTTs();

        List<GuaranteeType> guaranteeTypes = Collections.emptyList();
        if (permissionService.hasPermission(AdminSystemPermission.GUARANTEE_TYPES_VIEW)) {
            final GuaranteeTypeQuery guaranteeTypeQuery = new GuaranteeTypeQuery();
            guaranteeTypeQuery.setEnabled(true);
            guaranteeTypes = guaranteeTypeService.search(guaranteeTypeQuery);
        }

        final List<MessageCategory> messageCategories = new ArrayList<MessageCategory>(group.getMessageCategories());
        Collections.sort(messageCategories);

        AdminNotificationPreference notificationPreference = null;
        try {
            notificationPreference = preferenceService.load(admin, AdminNotificationPreference.Relationships.TRANSFER_TYPES, AdminNotificationPreference.Relationships.MESSAGE_CATEGORIES, AdminNotificationPreference.Relationships.MEMBER_ALERTS, AdminNotificationPreference.Relationships.SYSTEM_ALERTS);
            form.setAdminNotificationPreference("applicationErrors", notificationPreference.isApplicationErrors());
            form.setAdminNotificationPreference("systemInvoices", notificationPreference.isSystemInvoices());
            request.setAttribute("selectedTransferTypes", notificationPreference.getTransferTypes());
            request.setAttribute("selectedNewPendingPayments", notificationPreference.getNewPendingPayments());
            request.setAttribute("selectedGuaranteeTypes", notificationPreference.getGuaranteeTypes());
            request.setAttribute("selectedMessageCategories", notificationPreference.getMessageCategories());
            request.setAttribute("selectedNewMembers", notificationPreference.getNewMembers());
            request.setAttribute("selectedSystemAlerts", notificationPreference.getSystemAlerts());
            request.setAttribute("selectedMemberAlerts", notificationPreference.getMemberAlerts());
        } catch (final EntityNotFoundException e) {
            // Ignore - no current preference
        }

        RequestHelper.storeEnum(request, MemberAlert.Alerts.class, "memberAlerts");
        RequestHelper.storeEnum(request, SystemAlert.Alerts.class, "systemAlerts");

        request.setAttribute("transferTypes", transferTypes);
        request.setAttribute("newPendingPayments", newPendingPayments);
        request.setAttribute("guaranteeTypes", guaranteeTypes);
        request.setAttribute("messageCategories", messageCategories);
        request.setAttribute("memberGroups", memberGroups);
        request.setAttribute("notificationPreference", notificationPreference);
    }

    private DataBinder<AdminNotificationPreference> getDataBinder() {
        if (dataBinder == null) {
            final BeanBinder<AdminNotificationPreference> binder = BeanBinder.instance(AdminNotificationPreference.class);
            binder.registerBinder("transferTypes", SimpleCollectionBinder.instance(TransferType.class, Set.class, "transferTypes"));
            binder.registerBinder("newPendingPayments", SimpleCollectionBinder.instance(TransferType.class, Set.class, "newPendingPayments"));
            binder.registerBinder("messageCategories", SimpleCollectionBinder.instance(MessageCategory.class, Set.class, "messageCategories"));
            binder.registerBinder("guaranteeTypes", SimpleCollectionBinder.instance(GuaranteeType.class, Set.class, "guaranteeTypes"));
            binder.registerBinder("newMembers", SimpleCollectionBinder.instance(MemberGroup.class, Set.class, "newMembers"));
            binder.registerBinder("systemAlerts", SimpleCollectionBinder.instance(SystemAlert.Alerts.class, Set.class, "systemAlerts"));
            binder.registerBinder("memberAlerts", SimpleCollectionBinder.instance(MemberAlert.Alerts.class, Set.class, "memberAlerts"));
            binder.registerBinder("applicationErrors", PropertyBinder.instance(Boolean.TYPE, "applicationErrors"));
            binder.registerBinder("systemInvoices", PropertyBinder.instance(Boolean.TYPE, "systemInvoices"));
            dataBinder = binder;
        }
        return dataBinder;
    }

}
