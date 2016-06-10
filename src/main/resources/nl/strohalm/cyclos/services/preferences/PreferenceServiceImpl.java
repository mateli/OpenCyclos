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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import nl.strohalm.cyclos.access.MemberPermission;
import nl.strohalm.cyclos.access.Module;
import nl.strohalm.cyclos.dao.members.AdminNotificationPreferenceDAO;
import nl.strohalm.cyclos.dao.members.NotificationPreferenceDAO;
import nl.strohalm.cyclos.dao.members.brokerings.DefaultBrokerCommissionDAO;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.access.Channel;
import nl.strohalm.cyclos.entities.accounts.Account;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.members.Administrator;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.members.Reference.Nature;
import nl.strohalm.cyclos.entities.members.brokerings.DefaultBrokerCommission;
import nl.strohalm.cyclos.entities.members.messages.Message;
import nl.strohalm.cyclos.entities.members.messages.Message.Type;
import nl.strohalm.cyclos.entities.members.preferences.AdminNotificationPreference;
import nl.strohalm.cyclos.entities.members.preferences.AdminNotificationPreferenceQuery;
import nl.strohalm.cyclos.entities.members.preferences.NotificationPreference;
import nl.strohalm.cyclos.entities.sms.MemberSmsStatus;
import nl.strohalm.cyclos.services.accounts.AccountServiceLocal;
import nl.strohalm.cyclos.services.accounts.guarantees.GuaranteeServiceLocal;
import nl.strohalm.cyclos.services.accounts.guarantees.GuaranteeTypeServiceLocal;
import nl.strohalm.cyclos.services.elements.MemberServiceLocal;
import nl.strohalm.cyclos.services.elements.ReferenceServiceLocal;
import nl.strohalm.cyclos.services.fetch.FetchServiceLocal;
import nl.strohalm.cyclos.services.permissions.PermissionServiceLocal;
import nl.strohalm.cyclos.utils.RelationshipHelper;
import nl.strohalm.cyclos.utils.TransactionHelper;
import nl.strohalm.cyclos.utils.Transactional;
import nl.strohalm.cyclos.utils.access.LoggedUser;

import org.springframework.transaction.TransactionStatus;

/**
 * Service implementation for broker default commissions and notification preferences
 * @author jeancarlo
 * @author Jefferson Magno
 */
public class PreferenceServiceImpl implements PreferenceServiceLocal {

    private TransactionHelper              transactionHelper;
    private AdminNotificationPreferenceDAO adminNotificationPreferenceDao;
    private DefaultBrokerCommissionDAO     defaultBrokerCommissionDao;
    private FetchServiceLocal              fetchService;
    private NotificationPreferenceDAO      notificationPreferenceDao;
    private AccountServiceLocal            accountService;
    private MemberServiceLocal             memberService;
    private ReferenceServiceLocal          referenceService;
    private GuaranteeServiceLocal          guaranteeService;
    private GuaranteeTypeServiceLocal      guaranteeTypeService;
    private PermissionServiceLocal         permissionService;

    @Override
    public MemberSmsStatus getMemberSmsStatus(final Member member) {
        return memberService.getSmsStatus(member, false);
    }

    @Override
    public List<Administrator> listAdminsForNotification(final AdminNotificationPreferenceQuery query) {
        return adminNotificationPreferenceDao.searchAdmins(query);
    }

    @Override
    public List<Type> listNotificationTypes(final Member member) {
        final List<Message.Type> types = new ArrayList<Message.Type>(Arrays.asList(Message.Type.values()));
        final Iterator<Message.Type> iterator = types.iterator();
        while (iterator.hasNext()) {
            final Message.Type type = iterator.next();

            // Check if the type should be removed
            if (!useType(type, member)) {
                iterator.remove();
                continue;
            }
        }
        return types;
    }

    @Override
    public AdminNotificationPreference load(final Administrator admin, final Relationship... fetch) {
        return adminNotificationPreferenceDao.load(admin, fetch);
    }

    @Override
    public Collection<NotificationPreference> load(final Member member) {
        return notificationPreferenceDao.load(member);
    }

    public List<DefaultBrokerCommission> load(final Member broker, final Relationship... fetch) {
        return defaultBrokerCommissionDao.load(broker, fetch);
    }

    @Override
    public NotificationPreference load(final Member member, final Type type) {
        final Collection<NotificationPreference> preferences = load(member);
        for (final NotificationPreference preference : preferences) {
            if (preference.getType() == type) {
                return preference;
            }
        }
        return null;
    }

    @Override
    public Set<MessageChannel> receivedChannels(final Member member, final Type type) {
        try {
            final NotificationPreference preference = notificationPreferenceDao.load(member, type);
            final EnumSet<MessageChannel> channels = EnumSet.noneOf(MessageChannel.class);
            if (preference.isEmail()) {
                channels.add(MessageChannel.EMAIL);
            }
            if (preference.isMessage()) {
                channels.add(MessageChannel.MESSAGE);
            }
            if (preference.isSms()) {
                channels.add(MessageChannel.SMS);
            }
            return channels;
        } catch (final EntityNotFoundException e) {
            return Collections.emptySet();
        }
    }

    @Override
    public boolean receivesMessage(final Member member, final Message.Type type) {
        try {
            final NotificationPreference preference = notificationPreferenceDao.load(member, type);
            return preference.isEmail() || preference.isMessage() || preference.isSms();
        } catch (final EntityNotFoundException e) {
            return false;
        }
    }

    @Override
    public AdminNotificationPreference save(final AdminNotificationPreference preference) {
        final Administrator loggedAdmin = LoggedUser.element();
        preference.setAdmin(loggedAdmin);
        try {
            final AdminNotificationPreference current = load(loggedAdmin);
            preference.setId(current.getId());
            return adminNotificationPreferenceDao.update(preference);
        } catch (final EntityNotFoundException e) {
            return adminNotificationPreferenceDao.insert(preference);
        }
    }

    @Override
    public void save(Member member, final Collection<NotificationPreference> prefs) {
        member = fetchService.fetch(member, RelationshipHelper.nested(Element.Relationships.GROUP, MemberGroup.Relationships.SMS_MESSAGES));
        final Collection<Type> smsMessages = member.getMemberGroup().getSmsMessages();
        for (final NotificationPreference preference : prefs) {
            // If SMS is not enabled for this message type (group setting), disable it
            if (preference.isSms() && !smsMessages.contains(preference.getType())) {
                preference.setSms(false);
            }
            preference.setMember(member);
            save(preference);
        }
    }

    @Override
    public MemberSmsStatus saveSmsStatusPreferences(final Member member, final boolean isAcceptFreeMailing, final boolean isAcceptPaidMailing, final boolean isAllowChargingSms, final boolean hasNotificationsBySms) {
        return transactionHelper.runInNewTransaction(new Transactional<MemberSmsStatus>() {
            @Override
            public MemberSmsStatus afterCommit(final MemberSmsStatus result) {
                return fetchService.fetch(result);
            }

            @Override
            public MemberSmsStatus doInTransaction(final TransactionStatus status) {
                final MemberSmsStatus memberSmsStatus = memberService.getSmsStatus(member, true);
                memberSmsStatus.setAcceptFreeMailing(isAcceptFreeMailing);
                memberSmsStatus.setAcceptPaidMailing(isAcceptPaidMailing);
                memberSmsStatus.setAllowChargingSms(isAllowChargingSms);

                memberService.ensureAllowChargingSms(memberSmsStatus, hasNotificationsBySms);

                return memberService.updateSmsStatus(memberSmsStatus);
            }
        });
    }

    public void setAccountServiceLocal(final AccountServiceLocal accountService) {
        this.accountService = accountService;
    }

    public void setAdminNotificationPreferenceDao(final AdminNotificationPreferenceDAO adminNotificationPreferenceDao) {
        this.adminNotificationPreferenceDao = adminNotificationPreferenceDao;
    }

    public void setDefaultBrokerCommissionDao(final DefaultBrokerCommissionDAO brokerPreferenceDao) {
        defaultBrokerCommissionDao = brokerPreferenceDao;
    }

    public void setFetchServiceLocal(final FetchServiceLocal fetchService) {
        this.fetchService = fetchService;
    }

    public void setGuaranteeServiceLocal(final GuaranteeServiceLocal guaranteeService) {
        this.guaranteeService = guaranteeService;
    }

    public void setGuaranteeTypeServiceLocal(final GuaranteeTypeServiceLocal guaranteeTypeService) {
        this.guaranteeTypeService = guaranteeTypeService;
    }

    public void setMemberServiceLocal(final MemberServiceLocal memberService) {
        this.memberService = memberService;
    }

    public void setNotificationPreferenceDao(final NotificationPreferenceDAO notificationPreferenceDAO) {
        notificationPreferenceDao = notificationPreferenceDAO;
    }

    public void setPermissionServiceLocal(final PermissionServiceLocal permissionService) {
        this.permissionService = permissionService;
    }

    public void setReferenceServiceLocal(final ReferenceServiceLocal referenceService) {
        this.referenceService = referenceService;
    }

    public void setTransactionHelper(final TransactionHelper transactionHelper) {
        this.transactionHelper = transactionHelper;
    }

    private NotificationPreference save(NotificationPreference notificationPreference) {
        if (notificationPreference.isTransient()) {
            notificationPreference = notificationPreferenceDao.insert(notificationPreference);
        } else {
            notificationPreference = notificationPreferenceDao.update(notificationPreference);
        }
        return notificationPreference;
    }

    /**
     * Checks if a given type will be used for the member
     */
    private boolean useType(final Message.Type type, final Member member) {
        final MemberGroup group = member.getMemberGroup();
        boolean useType;

        switch (type) {
            case ACCOUNT:
                final List<? extends Account> accounts = accountService.getAccounts(member);
                useType = !accounts.isEmpty();
                break;
            case AD_EXPIRATION:
                useType = permissionService.hasPermission(group, MemberPermission.ADS_PUBLISH);
                break;
            case AD_INTEREST:
                useType = permissionService.hasPermission(group, MemberPermission.PREFERENCES_MANAGE_AD_INTERESTS);
                break;
            case BROKERING:
                useType = member.getGroup().getNature() == Group.Nature.BROKER || member.getBroker() != null;
                break;
            case FROM_MEMBER:
                useType = permissionService.hasPermission(group, MemberPermission.PROFILE_VIEW);
                break;
            case INVOICE:
                useType = permissionService.hasPermission(group, MemberPermission.INVOICES_VIEW);
                break;
            case LOAN:
                useType = permissionService.hasPermission(group, MemberPermission.LOANS_VIEW);
                break;
            case PAYMENT:
                useType = permissionService.hasPermission(group, Module.MEMBER_PAYMENTS);
                break;
            case EXTERNAL_PAYMENT:
                useType = false;
                // External payment notifications will be enabled when the member has access to non-builtin channels
                for (final Channel channel : group.getChannels()) {
                    if (Channel.listBuiltin().contains(channel.getInternalName())) {
                        useType = true;
                        break;
                    }
                }
                break;
            case REFERENCE:
                useType = permissionService.hasPermission(group, MemberPermission.REFERENCES_VIEW);
                break;
            case TRANSACTION_FEEDBACK:
                final Collection<Nature> referenceNatures = referenceService.getNaturesByGroup(member.getMemberGroup());
                useType = referenceNatures.contains(Nature.TRANSACTION);
                break;
            case CERTIFICATION:
                useType = guaranteeService.isBuyer() || permissionService.hasPermission(group, MemberPermission.GUARANTEES_ISSUE_CERTIFICATIONS);
                break;
            case GUARANTEE:
                // every member could have a guarantee, only check if there are some GT defined
                useType = guaranteeTypeService.areEnabledGuaranteeTypes();
                break;
            case PAYMENT_OBLIGATION:
                useType = guaranteeService.isBuyer() || guaranteeService.isSeller();
                break;
            default:
                useType = true;
                break;
        }
        return useType;
    }

}
