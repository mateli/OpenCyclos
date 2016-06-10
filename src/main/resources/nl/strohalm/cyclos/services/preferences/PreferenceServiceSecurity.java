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
package nl.strohalm.cyclos.services.preferences;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import nl.strohalm.cyclos.access.AdminMemberPermission;
import nl.strohalm.cyclos.access.AdminSystemPermission;
import nl.strohalm.cyclos.access.BrokerPermission;
import nl.strohalm.cyclos.access.MemberPermission;
import nl.strohalm.cyclos.access.OperatorPermission;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.guarantees.GuaranteeType;
import nl.strohalm.cyclos.entities.accounts.guarantees.GuaranteeTypeQuery;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.members.Administrator;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.members.messages.Message;
import nl.strohalm.cyclos.entities.members.preferences.AdminNotificationPreference;
import nl.strohalm.cyclos.entities.members.preferences.NotificationPreference;
import nl.strohalm.cyclos.entities.sms.MemberSmsStatus;
import nl.strohalm.cyclos.exceptions.PermissionDeniedException;
import nl.strohalm.cyclos.services.BaseServiceSecurity;
import nl.strohalm.cyclos.services.accounts.guarantees.GuaranteeTypeServiceLocal;
import nl.strohalm.cyclos.services.transfertypes.TransferTypeServiceLocal;
import nl.strohalm.cyclos.utils.access.LoggedUser;

import org.apache.commons.collections.CollectionUtils;

public class PreferenceServiceSecurity extends BaseServiceSecurity implements PreferenceService {

    private static <T> Set<T> filterAllowed(final Collection<T> elements, final Collection<T> allowed) {
        if (CollectionUtils.isEmpty(allowed)) {
            return Collections.emptySet();
        }
        if (CollectionUtils.isEmpty(elements)) {
            return Collections.emptySet();
        }
        elements.retainAll(allowed);
        return new HashSet<T>(elements);
    }

    private PreferenceServiceLocal    preferenceService;
    private TransferTypeServiceLocal  transferTypeService;
    private GuaranteeTypeServiceLocal guaranteeTypeService;

    @Override
    public MemberSmsStatus getMemberSmsStatus(final Member member) {
        checkManage(member);
        return preferenceService.getMemberSmsStatus(member);
    }

    @Override
    public List<Message.Type> listNotificationTypes(final Member member) {
        // called by notificationPreferenceAction
        checkManage(member);
        return preferenceService.listNotificationTypes(member);
    }

    @Override
    public AdminNotificationPreference load(final Administrator admin, final Relationship... fetch) {
        // called by MailPreferenceAction.prepareForm
        if (!LoggedUser.element().equals(admin)) {
            throw new PermissionDeniedException();
        }
        return preferenceService.load(admin, fetch);
    }

    @Override
    public Collection<NotificationPreference> load(final Member member) {
        // called by NotificationPreferenceAction
        checkManage(member);
        return preferenceService.load(member);
    }

    @Override
    public NotificationPreference load(final Member member, final Message.Type type) {
        // called by SendMessageAction, but only for direct message to member not by broker. We could move this to MessageService....
        permissionService.permission(member)
                .admin(AdminMemberPermission.MESSAGES_SEND_TO_MEMBER)
                .member(MemberPermission.MESSAGES_SEND_TO_MEMBER)
                .operator(OperatorPermission.MESSAGES_SEND_TO_MEMBER)
                .check();
        return preferenceService.load(member, type);
    }

    @Override
    public AdminNotificationPreference save(final AdminNotificationPreference preference) {

        preference.setTransferTypes(filterAllowed(preference.getTransferTypes(), transferTypeService.getPaymentAndSelfPaymentTTs()));
        preference.setNewPendingPayments(filterAllowed(preference.getNewPendingPayments(), transferTypeService.getAuthorizableTTs()));

        List<GuaranteeType> allowedGuaranteeTypes = Collections.emptyList();
        if (permissionService.hasPermission(AdminSystemPermission.GUARANTEE_TYPES_VIEW)) {
            final GuaranteeTypeQuery guaranteeTypeQuery = new GuaranteeTypeQuery();
            guaranteeTypeQuery.setEnabled(true);
            allowedGuaranteeTypes = guaranteeTypeService.search(guaranteeTypeQuery);
        }

        preference.setGuaranteeTypes(filterAllowed(preference.getGuaranteeTypes(), allowedGuaranteeTypes));
        final Collection<MemberGroup> allowedInitialGroups = permissionService.getManagedMemberGroups();
        preference.setNewMembers(filterAllowed(preference.getNewMembers(), allowedInitialGroups));

        return preferenceService.save(preference);
    }

    @Override
    public void save(final Member member, final Collection<NotificationPreference> prefs) {
        checkManage(member);
        preferenceService.save(member, prefs);
    }

    @Override
    public MemberSmsStatus saveSmsStatusPreferences(final Member member, final boolean isAcceptFreeMailing, final boolean isAcceptPaidMailing, final boolean isAllowChargingSms, final boolean hasNotificationsBySms) {
        checkManage(member);
        return preferenceService.saveSmsStatusPreferences(member, isAcceptFreeMailing, isAcceptPaidMailing, isAllowChargingSms, hasNotificationsBySms);
    }

    public void setGuaranteeTypeServiceLocal(final GuaranteeTypeServiceLocal guaranteeTypeService) {
        this.guaranteeTypeService = guaranteeTypeService;
    }

    public void setPreferenceServiceLocal(final PreferenceServiceLocal preferenceService) {
        this.preferenceService = preferenceService;
    }

    public void setTransferTypeServiceLocal(final TransferTypeServiceLocal transferTypeService) {
        this.transferTypeService = transferTypeService;
    }

    private void checkManage(final Member member) {
        permissionService.permission(member)
                .admin(AdminMemberPermission.PREFERENCES_MANAGE_NOTIFICATIONS)
                .broker(BrokerPermission.PREFERENCES_MANAGE_NOTIFICATIONS)
                .member(MemberPermission.PREFERENCES_MANAGE_NOTIFICATIONS)
                .check();
    }

}
