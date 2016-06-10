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
package nl.strohalm.cyclos.services.groups;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nl.strohalm.cyclos.access.Permission;
import nl.strohalm.cyclos.dao.accounts.AccountDAO;
import nl.strohalm.cyclos.dao.accounts.AccountLimitLogDAO;
import nl.strohalm.cyclos.dao.accounts.MemberGroupAccountSettingsDAO;
import nl.strohalm.cyclos.dao.accounts.fee.account.AccountFeeDAO;
import nl.strohalm.cyclos.dao.accounts.fee.transaction.TransactionFeeDAO;
import nl.strohalm.cyclos.dao.accounts.transactions.PaymentFilterDAO;
import nl.strohalm.cyclos.dao.customizations.CustomFieldDAO;
import nl.strohalm.cyclos.dao.customizations.CustomizedFileDAO;
import nl.strohalm.cyclos.dao.groups.GroupDAO;
import nl.strohalm.cyclos.dao.members.ElementDAO;
import nl.strohalm.cyclos.dao.members.MemberRecordTypeDAO;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.access.Channel;
import nl.strohalm.cyclos.entities.access.Channel.Credentials;
import nl.strohalm.cyclos.entities.accounts.AccountType;
import nl.strohalm.cyclos.entities.accounts.MemberAccountType;
import nl.strohalm.cyclos.entities.accounts.MemberGroupAccountSettings;
import nl.strohalm.cyclos.entities.accounts.SystemAccountType;
import nl.strohalm.cyclos.entities.accounts.cards.CardType;
import nl.strohalm.cyclos.entities.accounts.fees.account.AccountFee;
import nl.strohalm.cyclos.entities.accounts.fees.transaction.TransactionFee;
import nl.strohalm.cyclos.entities.accounts.guarantees.GuaranteeType;
import nl.strohalm.cyclos.entities.accounts.transactions.PaymentFilter;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferType;
import nl.strohalm.cyclos.entities.customization.documents.Document;
import nl.strohalm.cyclos.entities.customization.fields.AdminCustomField;
import nl.strohalm.cyclos.entities.customization.fields.CustomField;
import nl.strohalm.cyclos.entities.customization.files.CustomizedFile;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.entities.exceptions.UnexpectedEntityException;
import nl.strohalm.cyclos.entities.groups.AdminGroup;
import nl.strohalm.cyclos.entities.groups.BasicGroupSettings;
import nl.strohalm.cyclos.entities.groups.BasicGroupSettings.PasswordPolicy;
import nl.strohalm.cyclos.entities.groups.BrokerGroup;
import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.entities.groups.GroupFilter;
import nl.strohalm.cyclos.entities.groups.GroupQuery;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.groups.MemberGroupSettings;
import nl.strohalm.cyclos.entities.groups.OperatorGroup;
import nl.strohalm.cyclos.entities.groups.SystemGroup;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.RegistrationAgreement;
import nl.strohalm.cyclos.entities.members.messages.Message;
import nl.strohalm.cyclos.entities.members.messages.Message.Type;
import nl.strohalm.cyclos.entities.members.messages.MessageCategory;
import nl.strohalm.cyclos.entities.members.records.MemberRecordType;
import nl.strohalm.cyclos.entities.settings.AccessSettings;
import nl.strohalm.cyclos.exceptions.PermissionDeniedException;
import nl.strohalm.cyclos.scheduling.polling.AccountActivationPollingTask;
import nl.strohalm.cyclos.services.access.ChannelServiceLocal;
import nl.strohalm.cyclos.services.accounts.AccountServiceLocal;
import nl.strohalm.cyclos.services.accounts.AccountTypeServiceLocal;
import nl.strohalm.cyclos.services.accounts.BulkUpdateAccountDTO;
import nl.strohalm.cyclos.services.accounts.MemberAccountHandler;
import nl.strohalm.cyclos.services.application.ApplicationServiceLocal;
import nl.strohalm.cyclos.services.customization.AdminCustomFieldServiceLocal;
import nl.strohalm.cyclos.services.customization.MemberCustomFieldServiceLocal;
import nl.strohalm.cyclos.services.customization.OperatorCustomFieldServiceLocal;
import nl.strohalm.cyclos.services.elements.ElementServiceLocal;
import nl.strohalm.cyclos.services.fetch.FetchServiceLocal;
import nl.strohalm.cyclos.services.permissions.PermissionServiceLocal;
import nl.strohalm.cyclos.services.settings.SettingsServiceLocal;
import nl.strohalm.cyclos.services.sms.ISmsContext;
import nl.strohalm.cyclos.utils.PropertyHelper;
import nl.strohalm.cyclos.utils.RelationshipHelper;
import nl.strohalm.cyclos.utils.TimePeriod;
import nl.strohalm.cyclos.utils.access.LoggedUser;
import nl.strohalm.cyclos.utils.validation.GeneralValidation;
import nl.strohalm.cyclos.utils.validation.InvalidError;
import nl.strohalm.cyclos.utils.validation.PropertyValidation;
import nl.strohalm.cyclos.utils.validation.RequiredError;
import nl.strohalm.cyclos.utils.validation.RequiredValidation;
import nl.strohalm.cyclos.utils.validation.ValidationError;
import nl.strohalm.cyclos.utils.validation.Validator;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

/**
 * Implementation for group service.
 * @author rafael
 * @author luis
 * @author Jefferson Magno
 */
public class GroupServiceImpl implements GroupServiceLocal {

    /**
     * After group creation its nature never change.
     * @author ameyer
     */
    private final class ChangeNatureValidation implements PropertyValidation {
        private static final long serialVersionUID = 1L;

        @Override
        public ValidationError validate(final Object object, final Object property, final Object value) {
            Group group = (Group) object;
            if (group.isPersistent()) {
                Group current = load(group.getId());
                Group.Nature nature = (Group.Nature) value;
                if (nature != current.getNature()) {
                    return new ValidationError("group.invalidNature");
                }
            }
            return null;
        }
    }

    /**
     * A custom validation that process the given validation only when the member group is set to expire members after a given time period
     * @author luis
     */
    private final class ExpirationValidation implements PropertyValidation {

        private static final long        serialVersionUID = 7237205242667756245L;
        private final PropertyValidation validation;

        private ExpirationValidation(final PropertyValidation validation) {
            this.validation = validation;
        }

        @Override
        public ValidationError validate(final Object object, final Object property, final Object value) {
            final MemberGroup group = (MemberGroup) object;
            final TimePeriod expireMembersAfter = group.getMemberSettings().getExpireMembersAfter();
            if (expireMembersAfter != null && expireMembersAfter.getNumber() > 0) {
                return validation.validate(object, property, value);
            }
            return null;
        }
    }

    /**
     * Ensures that a password policy is consistent with the access settings
     * @author luis
     */
    private final class PasswordPolicyValidation implements PropertyValidation {

        private static final long serialVersionUID = 5513735809903251255L;

        @Override
        public ValidationError validate(final Object object, final Object property, final Object value) {
            final PasswordPolicy policy = (PasswordPolicy) value;
            if (policy == null) {
                return null;
            }
            final Group group = (Group) object;
            final AccessSettings accessSettings = settingsService.getAccessSettings();
            final boolean virtualKeyboard = accessSettings.isVirtualKeyboard();
            final boolean numericPassword = accessSettings.isNumericPassword();

            if (policy == PasswordPolicy.AVOID_OBVIOUS_LETTERS_NUMBERS_SPECIAL && virtualKeyboard) {
                // Special chars cannot be typed with the virtual keyboard
                return new ValidationError("group.error.passwordPolicySpecialVirtualKeyboard");
            } else if (group.getNature() != Group.Nature.ADMIN && policy.isForceCharacters() && numericPassword) {
                // Admin groups don't use this, but ensure for other groups that if numeric password, there are no characters enforcements
                return new ValidationError("group.error.passwordPolicyNumeric");
            }
            return null;
        }

    }

    private final class PasswordTrialsValidation implements PropertyValidation {
        private static final long serialVersionUID = -5445950747956604765L;

        @Override
        public ValidationError validate(final Object object, final Object property, final Object value) {
            final Group group = (Group) object;

            if (((Integer) value == 0) && (group.getBasicSettings().getMaxPasswordWrongTries() > 0)) {
                return new InvalidError();
            }
            return null;
        }
    }

    private final class PINBlockTimeValidation implements PropertyValidation {

        private static final long        serialVersionUID = 7237205242667756245L;
        private final PropertyValidation validation;

        private PINBlockTimeValidation(final PropertyValidation validation) {
            this.validation = validation;
        }

        @Override
        public ValidationError validate(final Object object, final Object property, final Object value) {
            final MemberGroup group = (MemberGroup) object;
            final TimePeriod expireMembersAfter = group.getMemberSettings().getPinBlockTimeAfterMaxTries();
            if (expireMembersAfter != null && expireMembersAfter.getNumber() > 0) {
                return validation.validate(object, property, value);
            }
            return null;
        }
    }

    private final class PinTrialsValidation implements PropertyValidation {
        private static final long serialVersionUID = -5445950747956604765L;

        @Override
        public ValidationError validate(final Object object, final Object property, final Object value) {
            final MemberGroup group = (MemberGroup) object;

            if ((value == null || (Integer) value == 0) && (group.getMemberSettings().getMaxPinWrongTries() > 0)) {
                return new InvalidError();
            }
            return null;
        }
    }

    private static final Relationship[]     FETCH_TO_KEEP_DATA = { Group.Relationships.PERMISSIONS, Group.Relationships.TRANSFER_TYPES, Group.Relationships.CONVERSION_SIMULATION_TTS, BrokerGroup.Relationships.BROKER_CONVERSION_SIMULATION_TTS, SystemGroup.Relationships.DOCUMENTS, SystemGroup.Relationships.MESSAGE_CATEGORIES, BrokerGroup.Relationships.BROKER_DOCUMENTS, BrokerGroup.Relationships.BROKER_MEMBER_RECORD_TYPES, AdminGroup.Relationships.MANAGES_GROUPS, SystemGroup.Relationships.CHARGEBACK_TRANSFER_TYPES, AdminGroup.Relationships.TRANSFER_TYPES_AS_MEMBER, AdminGroup.Relationships.VIEW_INFORMATION_OF, AdminGroup.Relationships.VIEW_CONNECTED_ADMINS_OF, AdminGroup.Relationships.VIEW_ADMIN_RECORD_TYPES, MemberGroup.Relationships.CAN_VIEW_ADS_OF_GROUPS, MemberGroup.Relationships.CAN_VIEW_PROFILE_OF_GROUPS, MemberGroup.Relationships.MANAGED_BY_GROUPS, Group.Relationships.GUARANTEE_TYPES };
    private static final Relationship[]     FETCH_TO_REMOVE    = { Group.Relationships.PAYMENT_FILTERS, AdminGroup.Relationships.CONNECTED_ADMINS_VIEWED_BY, AdminGroup.Relationships.ADMIN_CUSTOM_FIELDS, MemberGroup.Relationships.ACCOUNT_FEES, MemberGroup.Relationships.MANAGED_BY_GROUPS, MemberGroup.Relationships.CUSTOM_FIELDS, MemberGroup.Relationships.FROM_TRANSACTION_FEES, MemberGroup.Relationships.TO_TRANSACTION_FEES, MemberGroup.Relationships.MEMBER_RECORD_TYPES };

    private AccountFeeDAO                   accountFeeDao;
    private AccountTypeServiceLocal         accountTypeService;
    private AccountServiceLocal             accountService;
    private CustomFieldDAO                  customFieldDao;
    private CustomizedFileDAO               customizedFileDao;
    private GroupDAO                        groupDao;
    private ElementDAO                      elementDao;
    private ElementServiceLocal             elementService;
    private MemberGroupAccountSettingsDAO   memberGroupAccountSettingsDao;
    private MemberRecordTypeDAO             memberRecordTypeDao;
    private PaymentFilterDAO                paymentFilterDao;
    private TransactionFeeDAO               transactionFeeDao;
    private MemberAccountHandler            memberAccountHandler;
    private PermissionServiceLocal          permissionService;
    private FetchServiceLocal               fetchService;
    private AdminCustomFieldServiceLocal    adminCustomFieldService;
    private MemberCustomFieldServiceLocal   memberCustomFieldService;
    private OperatorCustomFieldServiceLocal operatorCustomFieldService;
    private SettingsServiceLocal            settingsService;
    private ChannelServiceLocal             channelService;
    private ApplicationServiceLocal         applicationService;
    private AccountDAO                      accountDao;
    private AccountLimitLogDAO              accountLimitLogDao;

    @Override
    public int countPendingAccounts(final MemberGroup group, final MemberAccountType accountType) {
        return accountService.countPendingActivation(group, accountType);
    }

    @Override
    public SystemGroup findByLoginPageName(final String loginPageName) {
        return groupDao.findByLoginPageName(loginPageName);
    }

    public CustomFieldDAO getCustomFieldDao() {
        return customFieldDao;
    }

    public CustomizedFileDAO getCustomizedFileDao() {
        return customizedFileDao;
    }

    public GroupDAO getGroupDao() {
        return groupDao;
    }

    public MemberAccountHandler getMemberAccountHandler() {
        return memberAccountHandler;
    }

    public MemberGroupAccountSettingsDAO getMemberGroupAccountSettingsDao() {
        return memberGroupAccountSettingsDao;
    }

    public MemberRecordTypeDAO getMemberRecordTypeDao() {
        return memberRecordTypeDao;
    }

    public PaymentFilterDAO getPaymentFilterDao() {
        return paymentFilterDao;
    }

    @Override
    public List<MemberGroup> getPossibleInitialGroups(GroupFilter groupFilter) {
        if (LoggedUser.hasUser() && LoggedUser.isBroker()) {
            BrokerGroup brokerGroup = LoggedUser.group();
            brokerGroup = fetchService.fetch(brokerGroup, BrokerGroup.Relationships.POSSIBLE_INITIAL_GROUPS);
            return (List<MemberGroup>) brokerGroup.getPossibleInitialGroups();
        }
        groupFilter = fetchService.fetch(groupFilter, GroupFilter.Relationships.GROUPS);
        final GroupQuery query = new GroupQuery();
        query.setNatures(Group.Nature.BROKER, Group.Nature.MEMBER);
        final List<MemberGroup> initialGroups = new ArrayList<MemberGroup>();
        for (Group group : search(query)) {
            final MemberGroup memberGroup = (MemberGroup) fetchService.fetch(group);
            if (memberGroup.isInitialGroup()) {
                initialGroups.add(memberGroup);
            }
        }
        if (groupFilter != null) {
            // If group filter is used, only return groups in that group filter
            initialGroups.retainAll(groupFilter.getGroups());
        }
        return initialGroups;
    }

    @Override
    public boolean hasGroupsWhichRequiresSpecialOnPassword() {
        final GroupQuery query = new GroupQuery();
        query.setStatus(Group.Status.NORMAL);
        for (final Group group : search(query)) {
            if (group.getBasicSettings().getPasswordPolicy() == PasswordPolicy.AVOID_OBVIOUS_LETTERS_NUMBERS_SPECIAL) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean hasMemberGroupsWhichEnforcesCharactersOnPassword() {
        final GroupQuery query = new GroupQuery();
        query.setStatus(Group.Status.NORMAL);
        query.setNatures(Group.Nature.MEMBER, Group.Nature.BROKER);
        for (final Group group : search(query)) {
            if (group.getBasicSettings().getPasswordPolicy().isForceCharacters()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public <G extends Group> G insert(G group, final G baseGroup) {
        if (baseGroup != null) {
            // Copy settings from base group
            group = copyBaseGroupSettings(group, baseGroup);
        } else {
            group.setBasicSettings(new BasicGroupSettings());
            if (group instanceof MemberGroup) {
                MemberGroup memberGroup = (MemberGroup) group;
                memberGroup.setMemberSettings(new MemberGroupSettings());
            }
        }

        // Validate and save
        validate(group);
        group = save(group);

        // Copy inverse collections from base group
        if (baseGroup != null) {
            copyInverseCollections(group, baseGroup);
        }

        if (group instanceof MemberGroup) {
            MemberGroup memberGroup = (MemberGroup) group;

            // Add permission to admin group over the member group
            AdminGroup adminGroup = LoggedUser.group();
            adminGroup = fetchService.fetch(adminGroup, AdminGroup.Relationships.MANAGES_GROUPS);
            final Collection<MemberGroup> managesGroups = adminGroup.getManagesGroups();
            managesGroups.add(memberGroup);
            groupDao.update(adminGroup);
        }

        clearRelatedCaches(group);
        return group;
    }

    @Override
    public MemberGroupAccountSettings insertAccountSettings(final MemberGroupAccountSettings settings) {
        // Check if the admin group has the permission to manage the member group
        final MemberGroup memberGroup = fetchService.fetch(settings.getGroup());
        AdminGroup adminGroup = LoggedUser.group();
        adminGroup = fetchService.fetch(adminGroup, AdminGroup.Relationships.MANAGES_GROUPS);
        final Collection<MemberGroup> managesGroups = adminGroup.getManagesGroups();
        if (!managesGroups.contains(memberGroup)) {
            throw new PermissionDeniedException();
        }

        validate(settings);
        // Check if the account type isn't already related to the group
        try {
            memberGroupAccountSettingsDao.load(settings.getGroup().getId(), settings.getAccountType().getId());
            throw new UnexpectedEntityException();
        } catch (final EntityNotFoundException e) {
            // Ok, the account is not related to the group, so we can go on
        }
        final MemberAccountType currentDefault = accountTypeService.getDefault(memberGroup);
        if (currentDefault == null) {
            // When there's no current default, set this one as default
            settings.setDefault(true);
        } else if (settings.isDefault()) {
            // When there was a default already and this one is marked as default, unmark the previous one
            final MemberGroupAccountSettings defaultSettings = memberGroupAccountSettingsDao.load(memberGroup.getId(), currentDefault.getId());
            defaultSettings.setDefault(false);
            memberGroupAccountSettingsDao.update(defaultSettings);
        }
        final MemberGroupAccountSettings saved = memberGroupAccountSettingsDao.insert(settings);

        // When inserting an account to an inactive group, activate it
        if (!memberGroup.isActive()) {
            memberGroup.setActive(true);
            groupDao.update(memberGroup);
            elementDao.activateMembersOfGroup(memberGroup);
        }

        // create and mark the member accounts for activation so MemberAccountHandler.ActivateAccountThread can pick them up
        BulkUpdateAccountDTO dto = new BulkUpdateAccountDTO();
        dto.setGroup(saved.getGroup());
        dto.setType(saved.getAccountType());
        dto.setCreditLimit(saved.getDefaultCreditLimit());
        dto.setUpperCreditLimit(saved.getDefaultUpperCreditLimit());
        accountDao.markForActivation(dto);

        applicationService.awakePollingTaskOnTransactionCommit(AccountActivationPollingTask.class);

        return saved;
    }

    @Override
    public List<OperatorGroup> iterateOperatorGroups(final MemberGroup memberGroup) {
        return groupDao.iterateOperatorGroups(memberGroup);
    }

    @Override
    public <T extends Group> Collection<T> load(final Collection<Long> ids, final Relationship... fetch) {
        return groupDao.<T> load(ids, fetch);
    }

    @Override
    public <T extends Group> T load(final Long id, final Relationship... fetch) {
        return groupDao.<T> load(id, fetch);
    }

    @Override
    public MemberGroupAccountSettings loadAccountSettings(final long groupId, final long accountTypeId, final Relationship... fetch) {
        return memberGroupAccountSettingsDao.load(groupId, accountTypeId, fetch);
    }

    @Override
    public <T extends Group> T reload(final Long id, final Relationship... fetch) {
        return groupDao.<T> reload(id, fetch);
    }

    @Override
    public void remove(final Long id) throws EntityNotFoundException {
        final Group group = load(id, FETCH_TO_REMOVE);
        if (!(group instanceof OperatorGroup)) {
            removeFromInverseCollections(group);
        }
        // Ensure the permissions cache for this group is evicted
        permissionService.evictCache(group);
        groupDao.delete(id);
    }

    @Override
    public void removeAccountTypeRelationship(final MemberGroup group, final MemberAccountType type) {
        // Remove the account settings
        memberGroupAccountSettingsDao.delete(group.getId(), type.getId());
        // mark all accounts for deactivation
        accountDao.markForDeactivation(type, group);

        applicationService.awakePollingTaskOnTransactionCommit(AccountActivationPollingTask.class);
    }

    @Override
    public List<? extends Group> search(final GroupQuery query) {
        if (!query.isIgnoreManagedBy() && LoggedUser.hasUser() && LoggedUser.isAdministrator()) {
            final AdminGroup adminGroup = LoggedUser.group();
            query.setManagedBy(adminGroup);
        }
        return groupDao.search(query);
    }

    public void setAccountDao(final AccountDAO accountDao) {
        this.accountDao = accountDao;
    }

    public void setAccountFeeDao(final AccountFeeDAO accountFeeDao) {
        this.accountFeeDao = accountFeeDao;
    }

    public void setAccountLimitLogDao(final AccountLimitLogDAO accountLimitLogDao) {
        this.accountLimitLogDao = accountLimitLogDao;
    }

    public void setAccountServiceLocal(final AccountServiceLocal accountService) {
        this.accountService = accountService;
    }

    public void setAccountTypeServiceLocal(final AccountTypeServiceLocal accountTypeService) {
        this.accountTypeService = accountTypeService;
    }

    public void setAdminCustomFieldServiceLocal(final AdminCustomFieldServiceLocal adminCustomFieldService) {
        this.adminCustomFieldService = adminCustomFieldService;
    }

    public void setApplicationServiceLocal(final ApplicationServiceLocal applicationService) {
        this.applicationService = applicationService;
    }

    public void setChannelServiceLocal(final ChannelServiceLocal channelService) {
        this.channelService = channelService;
    }

    public void setCustomFieldDao(final CustomFieldDAO customFieldDao) {
        this.customFieldDao = customFieldDao;
    }

    public void setCustomizedFileDao(final CustomizedFileDAO customizedFileDao) {
        this.customizedFileDao = customizedFileDao;
    }

    public void setElementDao(final ElementDAO elementDao) {
        this.elementDao = elementDao;
    }

    public void setElementServiceLocal(final ElementServiceLocal elementService) {
        this.elementService = elementService;
    }

    public void setFetchServiceLocal(final FetchServiceLocal fetchService) {
        this.fetchService = fetchService;
    }

    public void setGroupDao(final GroupDAO groupDao) {
        this.groupDao = groupDao;
    }

    public void setMemberAccountHandler(final MemberAccountHandler memberAccountHandler) {
        this.memberAccountHandler = memberAccountHandler;
    }

    public void setMemberCustomFieldServiceLocal(final MemberCustomFieldServiceLocal memberCustomFieldService) {
        this.memberCustomFieldService = memberCustomFieldService;
    }

    public void setMemberGroupAccountSettingsDao(final MemberGroupAccountSettingsDAO memberGroupAccountSettingsDao) {
        this.memberGroupAccountSettingsDao = memberGroupAccountSettingsDao;
    }

    public void setMemberRecordTypeDao(final MemberRecordTypeDAO memberRecordTypeDao) {
        this.memberRecordTypeDao = memberRecordTypeDao;
    }

    public void setOperatorCustomFieldServiceLocal(final OperatorCustomFieldServiceLocal operatorCustomFieldService) {
        this.operatorCustomFieldService = operatorCustomFieldService;
    }

    public void setPaymentFilterDao(final PaymentFilterDAO paymentFilterDao) {
        this.paymentFilterDao = paymentFilterDao;
    }

    @Override
    public <G extends Group> G setPermissions(final GroupPermissionsDTO<G> dto) {
        G group = fetchService.fetch(dto.getGroup());
        dto.update(group);
        group = groupDao.update(group);
        permissionService.evictCache(group);
        return group;
    }

    public void setPermissionServiceLocal(final PermissionServiceLocal permissionService) {
        this.permissionService = permissionService;
    }

    public void setSettingsServiceLocal(final SettingsServiceLocal settingsService) {
        this.settingsService = settingsService;
    }

    public void setTransactionFeeDao(final TransactionFeeDAO transactionFeeDao) {
        this.transactionFeeDao = transactionFeeDao;
    }

    @Override
    public <G extends Group> G update(G group, final boolean forceMembersToAcceptAgreement) {
        validate(group);

        final Group currentGroup = load(group.getId());
        if (group.isRemoved()) { // only take into account name and description
            currentGroup.setName(group.getName());
            currentGroup.setDescription(group.getDescription());

            return groupDao.update(group);
        }

        // Those variables are only used for member groups
        boolean wasTPUsed = false;
        boolean wasActive = false;
        boolean isTPUsed = false;
        RegistrationAgreement oldAgreement = null;

        if (group instanceof MemberGroup) {
            // Member groups have special handling
            MemberGroup memberGroup = (MemberGroup) group;
            final MemberGroup currentMemberGroup = (MemberGroup) load(memberGroup.getId());
            oldAgreement = fetchService.fetch(currentMemberGroup.getRegistrationAgreement());
            try {
                wasTPUsed = currentMemberGroup.getBasicSettings().getTransactionPassword().isUsed();
            } catch (final Exception e) {
                wasTPUsed = false;
            }
            try {
                isTPUsed = memberGroup.getBasicSettings().getTransactionPassword().isUsed();
            } catch (final Exception e) {
                isTPUsed = false;
            }
            wasActive = currentMemberGroup.isActive();
        }

        group = save(group);

        if (group instanceof MemberGroup) {
            // Member groups have special handling
            MemberGroup memberGroup = (MemberGroup) group;
            // When the transaction password was not used and now is, or was used and now is not, update the accounts
            if ((wasTPUsed && !isTPUsed) || (!wasTPUsed && isTPUsed)) {
                memberGroup = fetchService.reload(memberGroup, MemberGroup.Relationships.ACCOUNT_SETTINGS);
                for (final MemberGroupAccountSettings mgas : memberGroup.getAccountSettings()) {
                    mgas.setTransactionPasswordRequired(isTPUsed);
                    memberGroupAccountSettingsDao.update(mgas);
                }
            }

            // When the group is becoming active, activate all members on it
            if (!wasActive && memberGroup.isActive()) {
                elementDao.activateMembersOfGroup(memberGroup);
            }

            // Check if the registration agreement has changed
            final RegistrationAgreement registrationAgreement = fetchService.fetch(memberGroup.getRegistrationAgreement());
            if (registrationAgreement != null && !registrationAgreement.equals(oldAgreement)) {
                // It did change. When not forcing members to accept it again, we should create all accepting logs
                if (!forceMembersToAcceptAgreement) {
                    elementService.createAgreementForAllMembers(registrationAgreement, memberGroup);
                }
            }
        }

        return group;
    }

    @Override
    public MemberGroupAccountSettings updateAccountSettings(final MemberGroupAccountSettings settings, final boolean updateAccountLimits) {

        // Check if the admin group has the permission to manage the member group
        final MemberGroup memberGroup = settings.getGroup();
        AdminGroup adminGroup = LoggedUser.group();
        adminGroup = fetchService.fetch(adminGroup, AdminGroup.Relationships.MANAGES_GROUPS);
        final Collection<MemberGroup> managesGroups = adminGroup.getManagesGroups();
        if (!managesGroups.contains(memberGroup)) {
            throw new PermissionDeniedException();
        }

        validate(settings);

        final MemberAccountType currentDefault = accountTypeService.getDefault(memberGroup);
        if (currentDefault == null || currentDefault.equals(settings.getAccountType())) {
            // When there's no current default, or is this one, set as default
            settings.setDefault(true);
        } else if (settings.isDefault()) {
            // When there was a default already and this one is marked as default, unmark the previous one
            final MemberGroupAccountSettings defaultSettings = memberGroupAccountSettingsDao.load(memberGroup.getId(), currentDefault.getId());
            defaultSettings.setDefault(false);
            memberGroupAccountSettingsDao.update(defaultSettings);
        }

        final MemberGroupAccountSettings saved = memberGroupAccountSettingsDao.update(settings);

        if (updateAccountLimits) {
            BulkUpdateAccountDTO dto = new BulkUpdateAccountDTO();
            dto.setType(saved.getAccountType());
            dto.setGroup(settings.getGroup());
            dto.setCreditLimit(saved.getDefaultCreditLimit());
            dto.setUpperCreditLimit(saved.getDefaultUpperCreditLimit());
            accountDao.bulkUpdateCreditLimites(dto);
            accountLimitLogDao.insertAfterCreditLimitBulkUpdate(saved.getAccountType(), settings.getGroup());
        }
        return saved;
    }

    @Override
    public boolean usesPin(MemberGroup group) {
        group = fetchService.fetch(group, MemberGroup.Relationships.CHANNELS);
        final Collection<Channel> channels = group.getChannels();
        for (final Channel channel : channels) {
            if (channel.getCredentials() == Credentials.PIN) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void validate(final Group group) {
        if (group.isTransient()) {
            getInsertValidator().validate(group);
        } else if (Group.Status.REMOVED.equals(group.getStatus())) {
            getRemovedValidator().validate(group);
        } else if (group instanceof AdminGroup) {
            getAdminValidator().validate(group);
        } else if (group instanceof BrokerGroup) {
            getBrokerValidator().validate(group);
        } else if (group instanceof MemberGroup) {
            getMemberValidator().validate(group);
        } else if (group instanceof OperatorGroup) {
            getOperatorValidator().validate(group);
        }
    }

    @Override
    public void validate(final MemberGroupAccountSettings settings) {
        getAccountSettingsValidator().validate(settings);
    }

    private void clearRelatedCaches(final Group group) {
        if (group instanceof AdminGroup) {
            adminCustomFieldService.clearCache();
        } else if (group instanceof MemberGroup) {
            memberCustomFieldService.clearCache();
        } else if (group instanceof OperatorGroup) {
            operatorCustomFieldService.clearCache();
        }
        accountTypeService.clearCache();
    }

    private <G extends Group> G copyBaseGroupSettings(final G group, G baseGroup) {
        baseGroup = fetchService.fetch(baseGroup, Group.Relationships.PAYMENT_FILTERS, Group.Relationships.PERMISSIONS, Group.Relationships.TRANSFER_TYPES, SystemGroup.Relationships.DOCUMENTS, Group.Relationships.CUSTOMIZED_FILES, SystemGroup.Relationships.MESSAGE_CATEGORIES, AdminGroup.Relationships.TRANSFER_TYPES_AS_MEMBER, AdminGroup.Relationships.MANAGES_GROUPS, AdminGroup.Relationships.VIEW_INFORMATION_OF, AdminGroup.Relationships.VIEW_CONNECTED_ADMINS_OF, AdminGroup.Relationships.CONNECTED_ADMINS_VIEWED_BY, AdminGroup.Relationships.ADMIN_CUSTOM_FIELDS, MemberGroup.Relationships.ACCOUNT_SETTINGS, MemberGroup.Relationships.CAN_VIEW_PROFILE_OF_GROUPS, MemberGroup.Relationships.CAN_VIEW_ADS_OF_GROUPS, MemberGroup.Relationships.CAN_VIEW_INFORMATION_OF, MemberGroup.Relationships.ACCOUNT_FEES, MemberGroup.Relationships.MANAGED_BY_GROUPS, MemberGroup.Relationships.CUSTOM_FIELDS, MemberGroup.Relationships.FROM_TRANSACTION_FEES, MemberGroup.Relationships.TO_TRANSACTION_FEES, BrokerGroup.Relationships.TRANSFER_TYPES_AS_MEMBER, BrokerGroup.Relationships.BROKER_DOCUMENTS, BrokerGroup.Relationships.BROKER_CAN_VIEW_INFORMATION_OF, OperatorGroup.Relationships.MEMBER, OperatorGroup.Relationships.MAX_AMOUNT_PER_DAY_BY_TRANSFER_TYPE, OperatorGroup.Relationships.CAN_VIEW_INFORMATION_OF, Group.Relationships.GUARANTEE_TYPES, MemberGroup.Relationships.CAN_ISSUE_CERTIFICATION_TO_GROUPS, MemberGroup.Relationships.CAN_BUY_WITH_PAYMENT_OBLIGATIONS_FROM_GROUPS, MemberGroup.Relationships.CARD_TYPE);

        // Copy permissions
        final Set<Permission> permissions = new HashSet<Permission>(baseGroup.getPermissions());
        group.setPermissions(permissions);

        // Status
        group.setStatus(baseGroup.getStatus());

        // Transfer types
        final List<TransferType> transferTypes = new ArrayList<TransferType>(baseGroup.getTransferTypes());
        group.setTransferTypes(transferTypes);

        // Conversion Simulation TTs
        final List<TransferType> conversionSimulationTTs = new ArrayList<TransferType>(baseGroup.getConversionSimulationTTs());
        group.setConversionSimulationTTs(conversionSimulationTTs);

        // Basic settings
        if (!(group instanceof OperatorGroup)) {
            group.setBasicSettings((BasicGroupSettings) baseGroup.getBasicSettings().clone());
        }

        // Guarantee types
        final List<GuaranteeType> guaranteeTypes = new ArrayList<GuaranteeType>(baseGroup.getGuaranteeTypes());
        group.setGuaranteeTypes(guaranteeTypes);

        if (group instanceof SystemGroup) {
            final SystemGroup systemGroup = (SystemGroup) group;
            final SystemGroup baseSystemGroup = (SystemGroup) baseGroup;

            // Documents
            final List<Document> documents = new ArrayList<Document>(baseSystemGroup.getDocuments());
            systemGroup.setDocuments(documents);

            // Message categories
            final List<MessageCategory> messageCategories = new ArrayList<MessageCategory>(baseSystemGroup.getMessageCategories());
            systemGroup.setMessageCategories(messageCategories);

            // Chargeback transfer types
            final List<TransferType> chargebackTransferTypes = new ArrayList<TransferType>(baseSystemGroup.getChargebackTransferTypes());
            systemGroup.setChargebackTransferTypes(chargebackTransferTypes);
        }

        if (group instanceof AdminGroup) {
            final AdminGroup adminGroup = (AdminGroup) group;
            final AdminGroup baseAdminGroup = (AdminGroup) baseGroup;

            // Transfer types as member
            final List<TransferType> transferTypesAsMember = new ArrayList<TransferType>(baseAdminGroup.getTransferTypesAsMember());
            adminGroup.setTransferTypesAsMember(transferTypesAsMember);

            // Manages groups
            final List<MemberGroup> managesGroups = new ArrayList<MemberGroup>(baseAdminGroup.getManagesGroups());
            adminGroup.setManagesGroups(managesGroups);

            // View information of
            final List<SystemAccountType> viewInformationOf = new ArrayList<SystemAccountType>(baseAdminGroup.getViewInformationOf());
            adminGroup.setViewInformationOf(viewInformationOf);

            // View connected admins of
            final List<AdminGroup> viewConnectedAdminsOf = new ArrayList<AdminGroup>(baseAdminGroup.getViewConnectedAdminsOf());
            adminGroup.setViewConnectedAdminsOf(viewConnectedAdminsOf);

            // View member record types
            final List<MemberRecordType> viewMemberRecordTypes = new ArrayList<MemberRecordType>(baseAdminGroup.getViewMemberRecordTypes());
            adminGroup.setViewMemberRecordTypes(viewMemberRecordTypes);

            // Create member record types
            final List<MemberRecordType> createMemberRecordTypes = new ArrayList<MemberRecordType>(baseAdminGroup.getCreateMemberRecordTypes());
            adminGroup.setCreateMemberRecordTypes(createMemberRecordTypes);

            // Modify member record types
            final List<MemberRecordType> modifyMemberRecordTypes = new ArrayList<MemberRecordType>(baseAdminGroup.getModifyMemberRecordTypes());
            adminGroup.setModifyMemberRecordTypes(modifyMemberRecordTypes);

            // Delete member record types
            final List<MemberRecordType> deleteMemberRecordTypes = new ArrayList<MemberRecordType>(baseAdminGroup.getDeleteMemberRecordTypes());
            adminGroup.setDeleteMemberRecordTypes(deleteMemberRecordTypes);

            // View admin record types
            final List<MemberRecordType> viewAdminRecordTypes = new ArrayList<MemberRecordType>(baseAdminGroup.getViewAdminRecordTypes());
            adminGroup.setViewAdminRecordTypes(viewAdminRecordTypes);

            // Create admin record types
            final List<MemberRecordType> createAdminRecordTypes = new ArrayList<MemberRecordType>(baseAdminGroup.getCreateAdminRecordTypes());
            adminGroup.setCreateAdminRecordTypes(createAdminRecordTypes);

            // Modify admin record types
            final List<MemberRecordType> modifyAdminRecordTypes = new ArrayList<MemberRecordType>(baseAdminGroup.getModifyAdminRecordTypes());
            adminGroup.setModifyAdminRecordTypes(modifyAdminRecordTypes);

            // Delete admin record types
            final List<MemberRecordType> deleteAdminRecordTypes = new ArrayList<MemberRecordType>(baseAdminGroup.getDeleteAdminRecordTypes());
            adminGroup.setDeleteAdminRecordTypes(deleteAdminRecordTypes);
        }

        if (group instanceof MemberGroup) {
            final MemberGroup memberGroup = (MemberGroup) group;
            final MemberGroup baseMemberGroup = (MemberGroup) baseGroup;

            // Member group settings
            memberGroup.setMemberSettings((MemberGroupSettings) baseMemberGroup.getMemberSettings().clone());

            // Copy the e-mail validation
            memberGroup.getMemberSettings().setEmailValidation(new HashSet<MemberGroupSettings.EmailValidation>(baseMemberGroup.getMemberSettings().getEmailValidation()));

            // Initial group & agreement
            memberGroup.setInitialGroup(baseMemberGroup.isInitialGroup());
            memberGroup.setRegistrationAgreement(baseMemberGroup.getRegistrationAgreement());

            // Active
            memberGroup.setActive(baseMemberGroup.isActive());

            // Can view profile of groups
            final List<MemberGroup> canViewProfileOfGroups = new ArrayList<MemberGroup>(baseMemberGroup.getCanViewProfileOfGroups());
            canViewProfileOfGroups.add(baseMemberGroup);
            memberGroup.setCanViewProfileOfGroups(canViewProfileOfGroups);

            // Can view ads of groups
            final List<MemberGroup> canViewAdsOfGroups = new ArrayList<MemberGroup>(baseMemberGroup.getCanViewAdsOfGroups());
            canViewAdsOfGroups.add(baseMemberGroup);
            memberGroup.setCanViewAdsOfGroups(canViewAdsOfGroups);

            // Can view information of
            final List<AccountType> canViewInformationOf = new ArrayList<AccountType>(baseMemberGroup.getCanViewInformationOf());
            memberGroup.setCanViewInformationOf(canViewInformationOf);

            // Default mail messages
            final List<Message.Type> defaultMailMessages = new ArrayList<Message.Type>(baseMemberGroup.getDefaultMailMessages());
            memberGroup.setDefaultMailMessages(defaultMailMessages);

            // SMS messages
            final List<Message.Type> smsMessages = new ArrayList<Message.Type>(baseMemberGroup.getSmsMessages());
            memberGroup.setSmsMessages(smsMessages);

            // Default SMS messages
            final List<Message.Type> defaultSmsMessages = new ArrayList<Message.Type>(baseMemberGroup.getDefaultSmsMessages());
            memberGroup.setDefaultSmsMessages(defaultSmsMessages);

            // Channels
            final List<Channel> channels = new ArrayList<Channel>(baseMemberGroup.getChannels());
            memberGroup.setChannels(channels);

            // Default channels
            final List<Channel> defaultChannels = new ArrayList<Channel>(baseMemberGroup.getDefaultChannels());
            memberGroup.setDefaultChannels(defaultChannels);

            // Request payment by channels
            final List<Channel> requestPaymentByChannels = new ArrayList<Channel>(baseMemberGroup.getRequestPaymentByChannels());
            memberGroup.setRequestPaymentByChannels(requestPaymentByChannels);

            // Can issue certification to groups
            final List<MemberGroup> canIssueCertificationToGroups = new ArrayList<MemberGroup>(baseMemberGroup.getCanIssueCertificationToGroups());
            memberGroup.setCanIssueCertificationToGroups(canIssueCertificationToGroups);

            // Can buy with payment obligations from groups
            final List<MemberGroup> canBuyWithPaymentObligationsFromGroups = new ArrayList<MemberGroup>(baseMemberGroup.getCanBuyWithPaymentObligationsFromGroups());
            memberGroup.setCanBuyWithPaymentObligationsFromGroups(canBuyWithPaymentObligationsFromGroups);

            // Card type
            final CardType cardType = baseMemberGroup.getCardType();
            memberGroup.setCardType(cardType);
        }

        if (group instanceof BrokerGroup) {
            final BrokerGroup brokerGroup = (BrokerGroup) group;
            final BrokerGroup baseBrokerGroup = (BrokerGroup) baseGroup;

            // Transfer types as member
            final List<TransferType> transferTypesAsMember = new ArrayList<TransferType>(baseBrokerGroup.getTransferTypesAsMember());
            brokerGroup.setTransferTypesAsMember(transferTypesAsMember);

            final List<TransferType> brokerConversionSimulationTTs = new ArrayList<TransferType>(baseBrokerGroup.getBrokerConversionSimulationTTs());
            brokerGroup.setBrokerConversionSimulationTTs(brokerConversionSimulationTTs);

            // Broker documents
            final List<Document> brokerDocuments = new ArrayList<Document>(baseBrokerGroup.getBrokerDocuments());
            brokerGroup.setBrokerDocuments(brokerDocuments);

            // Broker can view information of
            final List<AccountType> brokerCanViewInformationOf = new ArrayList<AccountType>(baseBrokerGroup.getBrokerCanViewInformationOf());
            brokerGroup.setBrokerCanViewInformationOf(brokerCanViewInformationOf);

            // Broker view member record types
            final List<MemberRecordType> brokerMemberRecordTypes = new ArrayList<MemberRecordType>(baseBrokerGroup.getBrokerMemberRecordTypes());
            brokerGroup.setBrokerMemberRecordTypes(brokerMemberRecordTypes);

            // Broker create member record types
            final List<MemberRecordType> brokerCreateMemberRecordTypes = new ArrayList<MemberRecordType>(baseBrokerGroup.getBrokerCreateMemberRecordTypes());
            brokerGroup.setBrokerCreateMemberRecordTypes(brokerCreateMemberRecordTypes);

            // Broker modify member record types
            final List<MemberRecordType> brokerModifyMemberRecordTypes = new ArrayList<MemberRecordType>(baseBrokerGroup.getBrokerModifyMemberRecordTypes());
            brokerGroup.setBrokerModifyMemberRecordTypes(brokerModifyMemberRecordTypes);

            // Broker delete member record types
            final List<MemberRecordType> brokerDeleteMemberRecordTypes = new ArrayList<MemberRecordType>(baseBrokerGroup.getBrokerDeleteMemberRecordTypes());
            brokerGroup.setBrokerDeleteMemberRecordTypes(brokerDeleteMemberRecordTypes);

            // Possible initial groups
            final List<MemberGroup> possibleInitialGroups = new ArrayList<MemberGroup>(baseBrokerGroup.getPossibleInitialGroups());
            brokerGroup.setPossibleInitialGroups(possibleInitialGroups);
        }

        if (group instanceof OperatorGroup) {
            final OperatorGroup operatorGroup = (OperatorGroup) group;
            final OperatorGroup baseOperatorGroup = (OperatorGroup) baseGroup;

            // Member
            operatorGroup.setMember(baseOperatorGroup.getMember());

            // Max amount per day by transfer type
            final Map<TransferType, BigDecimal> maxAmountPerDayByTransferType = new HashMap<TransferType, BigDecimal>(baseOperatorGroup.getMaxAmountPerDayByTransferType());
            operatorGroup.setMaxAmountPerDayByTransferType(maxAmountPerDayByTransferType);

            // Can view information of
            final List<AccountType> canViewInformationOf = new ArrayList<AccountType>(baseOperatorGroup.getCanViewInformationOf());
            operatorGroup.setCanViewInformationOf(canViewInformationOf);
        }

        return group;
    }

    @SuppressWarnings("unchecked")
    private Group copyInverseCollections(final Group group, final Group baseGroup) {

        // Payment filters
        final Collection<PaymentFilter> paymentFilters = baseGroup.getPaymentFilters();
        for (final PaymentFilter paymentFilter : paymentFilters) {
            final Collection<Group> groups = (Collection<Group>) paymentFilter.getGroups();
            groups.add(group);
            paymentFilterDao.update(paymentFilter);
        }

        // Customized files
        final Collection<CustomizedFile> customizedFiles = baseGroup.getCustomizedFiles();
        for (final CustomizedFile baseGroupCustomizedFile : customizedFiles) {
            final CustomizedFile customizedFile = (CustomizedFile) baseGroupCustomizedFile.clone();
            customizedFile.setId(null);
            customizedFile.setGroup(group);
            customizedFileDao.insert(customizedFile);
        }

        // Record types
        final Collection<MemberRecordType> recordTypes = baseGroup.getMemberRecordTypes();
        for (final MemberRecordType recordType : recordTypes) {
            recordType.getGroups().add(group);
            memberRecordTypeDao.update(recordType);
        }

        if (group instanceof AdminGroup) {
            final AdminGroup adminGroup = (AdminGroup) group;
            final AdminGroup baseAdminGroup = (AdminGroup) baseGroup;

            // Connected admins viewed by
            final Collection<AdminGroup> connectedAdminsViewedBy = baseAdminGroup.getConnectedAdminsViewedBy();
            for (final AdminGroup viewerAdminGroup : connectedAdminsViewedBy) {
                viewerAdminGroup.getViewConnectedAdminsOf().add(adminGroup);
                groupDao.update(viewerAdminGroup);
            }

            // Admin custom fields
            final Collection<AdminCustomField> adminCustomFields = baseAdminGroup.getAdminCustomFields();
            for (final AdminCustomField adminCustomField : adminCustomFields) {
                adminCustomField.getGroups().add(adminGroup);
                customFieldDao.update(adminCustomField);
            }
        } else if (group instanceof MemberGroup) {
            final MemberGroup memberGroup = (MemberGroup) group;
            final MemberGroup baseMemberGroup = (MemberGroup) baseGroup;

            // Managed by groups
            final Collection<AdminGroup> managedByGroups = baseMemberGroup.getManagedByGroups();
            for (final AdminGroup adminGroup : managedByGroups) {
                adminGroup.getManagesGroups().add(memberGroup);
                groupDao.update(adminGroup);
            }

            // Account settings
            final Collection<MemberGroupAccountSettings> baseMemberGroupAccountSettings = baseMemberGroup.getAccountSettings();
            for (final MemberGroupAccountSettings baseAccountSettings : baseMemberGroupAccountSettings) {
                final MemberGroupAccountSettings accountSettings = (MemberGroupAccountSettings) baseAccountSettings.clone();
                accountSettings.setId(null);
                accountSettings.setGroup(memberGroup);
                insertAccountSettings(accountSettings);
            }

            // Account fees
            final Collection<AccountFee> accountFees = baseMemberGroup.getAccountFees();
            for (final AccountFee accountFee : accountFees) {
                final Collection<MemberGroup> groups = accountFee.getGroups();
                groups.add(memberGroup);
                accountFeeDao.update(accountFee);
            }

            // Member custom fields and general remark custom fields)
            final Collection<CustomField> customFields = baseMemberGroup.getCustomFields();
            for (final CustomField customField : customFields) {
                // Get the groups using reflection
                final Collection<MemberGroup> groups = PropertyHelper.get(customField, "groups");
                groups.add(memberGroup);
                customFieldDao.update(customField);
            }

            // From transaction fees
            final Collection<TransactionFee> fromTransactionFees = baseMemberGroup.getFromTransactionFees();
            for (final TransactionFee transactionFee : fromTransactionFees) {
                transactionFee.getFromGroups().add(memberGroup);
                transactionFeeDao.update(transactionFee);
            }

            // To transaction fees
            final Collection<TransactionFee> toTransactionFees = baseMemberGroup.getToTransactionFees();
            for (final TransactionFee transactionFee : toTransactionFees) {
                transactionFee.getToGroups().add(memberGroup);
                transactionFeeDao.update(transactionFee);
            }

            // View profile of
            for (final MemberGroup other : memberGroup.getCanViewProfileOfGroups()) {
                other.getCanViewProfileOfGroups().add(memberGroup);
            }
            memberGroup.getCanViewProfileOfGroups().add(memberGroup);

            // View ads of
            for (final MemberGroup other : memberGroup.getCanViewAdsOfGroups()) {
                other.getCanViewAdsOfGroups().add(memberGroup);
            }
            memberGroup.getCanViewAdsOfGroups().add(memberGroup);

            // Group filters
            for (final GroupFilter groupFilter : baseMemberGroup.getGroupFilters()) {
                groupFilter.getGroups().add(memberGroup);
            }
            for (final GroupFilter groupFilter : baseMemberGroup.getCanViewGroupFilters()) {
                groupFilter.getViewableBy().add(memberGroup);
            }
        }

        return group;
    }

    private Validator getAccountSettingsValidator() {
        final Validator accountSettingsValidator = new Validator("account");
        accountSettingsValidator.property("group").displayName("group").required();
        accountSettingsValidator.property("accountType").displayName("account type").required();
        accountSettingsValidator.property("initialCredit").positive();
        accountSettingsValidator.general(new GeneralValidation() {
            private static final long serialVersionUID = 1L;

            @Override
            public ValidationError validate(final Object object) {

                final MemberGroupAccountSettings mgas = (MemberGroupAccountSettings) object;
                if (mgas.getInitialCreditTransferType() != null) {
                    TransferType tt = fetchService.fetch(mgas.getInitialCreditTransferType());
                    BigDecimal minAmount = tt.getMinAmount();
                    BigDecimal initialCredit = mgas.getInitialCredit();
                    boolean initialCreditIsPossitive = initialCredit != null && initialCredit.compareTo(new BigDecimal(0)) > 0;

                    if (initialCreditIsPossitive && minAmount != null && (initialCredit.compareTo(minAmount) < 0)) {
                        return new ValidationError("group.account.error.minInitialCredit", initialCredit, minAmount);
                    }
                }
                return null;
            }
        });
        accountSettingsValidator.property("initialCreditTransferType").add(new PropertyValidation() {
            private static final long serialVersionUID = 8284432136349418154L;

            @Override
            public ValidationError validate(final Object object, final Object name, final Object value) {
                final MemberGroupAccountSettings mgas = (MemberGroupAccountSettings) object;
                final TransferType tt = fetchService.fetch((TransferType) value, TransferType.Relationships.FROM, TransferType.Relationships.TO);

                final BigDecimal initialCredit = mgas.getInitialCredit();
                // When there is initial credit, there must be a transfer type
                if (initialCredit != null && (initialCredit.compareTo(BigDecimal.ZERO) == 1) && tt == null) {
                    return new RequiredError();
                }
                // Must be from system to member
                if (tt != null && !(tt.isFromSystem() && !tt.isToSystem())) {
                    return new InvalidError();
                }
                return null;
            }
        });
        accountSettingsValidator.property("defaultCreditLimit").required().positive();
        accountSettingsValidator.property("defaultUpperCreditLimit").positive();
        accountSettingsValidator.property("lowUnits").positive();
        accountSettingsValidator.property("lowUnitsMessage").add(new PropertyValidation() {
            private static final long serialVersionUID = -6086632981851357180L;

            @Override
            public ValidationError validate(final Object object, final Object name, final Object value) {
                final MemberGroupAccountSettings mgas = (MemberGroupAccountSettings) object;
                final BigDecimal lowUnits = mgas.getLowUnits();
                // When there are low units, the message is required
                if (lowUnits != null && (lowUnits.compareTo(BigDecimal.ZERO) == 1) && StringUtils.isEmpty(mgas.getLowUnitsMessage())) {
                    return new RequiredError();
                }
                return null;
            }
        });
        return accountSettingsValidator;
    }

    private Validator getAdminValidator() {
        final Validator adminValidator = new Validator("group");
        initSystem(adminValidator, true);
        return adminValidator;
    }

    private Validator getBrokerValidator() {
        final Validator brokerValidator = new Validator("group");
        initSystem(brokerValidator, true);
        initMember(brokerValidator);
        return brokerValidator;
    }

    private Validator getInsertValidator() {
        final Validator insertValidator = new Validator("group");
        insertValidator.property("name").required().maxLength(100);
        insertValidator.property("description").maxLength(2000);
        insertValidator.property("nature").required();
        insertValidator.property("status").required();
        return insertValidator;
    }

    private Validator getMemberValidator() {
        final Validator memberValidator = new Validator("group");
        initSystem(memberValidator, true);
        initMember(memberValidator);
        return memberValidator;
    }

    private Validator getOperatorValidator() {
        final Validator operatorValidator = new Validator("group");
        initBasic(operatorValidator, false);
        return operatorValidator;
    }

    private Validator getRemovedValidator() {
        final Validator removedValidator = new Validator("group");
        removedValidator.property("name").required().maxLength(100);
        removedValidator.property("description").maxLength(2000);
        removedValidator.property("nature").add(new ChangeNatureValidation());

        return removedValidator;
    }

    private void initBasic(final Validator validator, final boolean addSettings) {
        validator.property("name").required().maxLength(100);
        validator.property("description").maxLength(2000);
        validator.property("nature").add(new ChangeNatureValidation());

        if (addSettings) {
            validator.property("basicSettings.passwordLength.min").key("group.settings.passwordLength.min").between(1, 32);
            validator.property("basicSettings.passwordLength.max").key("group.settings.passwordLength.max").between(1, 32);
            validator.property("basicSettings.maxPasswordWrongTries").key("group.settings.passwordTries.maximum").between(0, 99);
            validator.property("basicSettings.deactivationAfterMaxPasswordTries.number").key("group.settings.passwordTries.deactivationTime.number").between(0, 999);
            validator.property("basicSettings.deactivationAfterMaxPasswordTries.field").key("group.settings.passwordTries.deactivationTime.field").required();
            validator.property("basicSettings.passwordExpiresAfter.number").key("group.settings.passwordExpiresAfter.number").between(0, 999);
            validator.property("basicSettings.passwordExpiresAfter.field").key("group.settings.passwordExpiresAfter.field").required();
            validator.property("basicSettings.transactionPassword").key("group.settings.transactionPassword").required();
            validator.property("basicSettings.transactionPasswordLength").key("group.settings.transactionPassword.length").between(1, 32);
            validator.property("basicSettings.maxTransactionPasswordWrongTries").key("group.settings.maxTransactionPasswordWrongTries").between(0, 99);
            validator.property("basicSettings.deactivationAfterMaxPasswordTries.number").key("group.settings.passwordTries.deactivationTime.number").add(new PasswordTrialsValidation());
            validator.property("basicSettings.passwordPolicy").key("group.settings.passwordPolicy").add(new PasswordPolicyValidation());
        }
    }

    private void initMember(final Validator validator) {
        validator.property("memberSettings.defaultAdPublicationTime.number").key("group.settings.defaultAdPublicationTime.number").between(1, 999);
        validator.property("memberSettings.defaultAdPublicationTime.field").key("group.settings.defaultAdPublicationTime.field").required();
        validator.property("memberSettings.maxAdPublicationTime.number").key("group.settings.maxAdPublicationTime.number").between(1, 999);
        validator.property("memberSettings.maxAdPublicationTime.field").key("group.settings.maxAdPublicationTime.field").required();
        validator.property("memberSettings.maxAdDescriptionSize").key("group.settings.maxAdDescriptionSize").required().between(16, 16000);
        validator.property("memberSettings.maxAdsPerMember").key("group.settings.maxAdsPerMember").between(0, 999);
        validator.property("memberSettings.maxAdImagesPerMember").key("group.settings.maxAdImagesPerMember").between(0, 999);
        validator.property("memberSettings.maxImagesPerMember").key("group.settings.maxImagesPerMember").between(0, 999);
        validator.property("memberSettings.expireMembersAfter.number").key("group.settings.expireMembersAfter").between(0, 999);
        validator.property("memberSettings.expireMembersAfter.field").key("group.settings.expireMembersAfter").add(new ExpirationValidation(RequiredValidation.instance()));
        validator.property("memberSettings.groupAfterExpiration").key("group.settings.groupAfterExpiration").add(new ExpirationValidation(RequiredValidation.instance()));
        validator.property("memberSettings.pinBlockTimeAfterMaxTries.number").key("group.settings.pinBlockTimeAfterMaxTries.number").between(0, 999);
        validator.property("memberSettings.pinBlockTimeAfterMaxTries.field").key("group.settings.pinBlockTimeAfterMaxTries.field").add(new PINBlockTimeValidation(RequiredValidation.instance()));
        validator.property("memberSettings.smsContextClassName").key("group.settings.smsContextClassName").instanceOf(ISmsContext.class);
        validator.property("memberSettings.pinBlockTimeAfterMaxTries.number").key("group.settings.pinBlockTimeAfterMaxTries.number").add(new PinTrialsValidation());
    }

    private void initSystem(final Validator validator, final boolean addSettings) {
        initBasic(validator, addSettings);
        validator.property("rootUrl").maxLength(100).url();
        validator.property("loginPageName").maxLength(20);
        validator.property("containerUrl").maxLength(100).url();
    }

    @SuppressWarnings("unchecked")
    private void removeFromInverseCollections(final Group group) {

        // Payment filters
        final Collection<PaymentFilter> paymentFilters = group.getPaymentFilters();
        for (final PaymentFilter paymentFilter : paymentFilters) {
            final Collection<Group> groups = (Collection<Group>) paymentFilter.getGroups();
            groups.remove(group);
            paymentFilterDao.update(paymentFilter);
        }

        // Record types
        final Collection<MemberRecordType> recordTypes = group.getMemberRecordTypes();
        for (final MemberRecordType recordType : recordTypes) {
            recordType.getGroups().remove(group);
            memberRecordTypeDao.update(recordType);
        }

        if (group instanceof AdminGroup) {
            final AdminGroup adminGroup = (AdminGroup) group;

            // Connected admins viewed by
            final Collection<AdminGroup> connectedAdminsViewedBy = adminGroup.getConnectedAdminsViewedBy();
            for (final AdminGroup viewerAdminGroup : connectedAdminsViewedBy) {
                viewerAdminGroup.getViewConnectedAdminsOf().remove(adminGroup);
                groupDao.update(viewerAdminGroup);
            }

            // Admin custom fields
            final Collection<AdminCustomField> adminCustomFields = adminGroup.getAdminCustomFields();
            for (final AdminCustomField adminCustomField : adminCustomFields) {
                adminCustomField.getGroups().remove(adminGroup);
                customFieldDao.update(adminCustomField);
            }
        }

        if (group instanceof MemberGroup) {
            final MemberGroup memberGroup = (MemberGroup) group;

            // Account fees
            final Collection<AccountFee> accountFees = memberGroup.getAccountFees();
            for (final AccountFee accountFee : accountFees) {
                final Collection<MemberGroup> groups = accountFee.getGroups();
                groups.remove(memberGroup);
                accountFeeDao.update(accountFee);
            }

            // Managed by groups
            final Collection<AdminGroup> managedByGroups = memberGroup.getManagedByGroups();
            for (final AdminGroup adminGroup : managedByGroups) {
                adminGroup.getManagesGroups().remove(memberGroup);
                groupDao.update(adminGroup);
            }

            // Member custom fields and general remark custom fields)
            final Collection<CustomField> customFields = memberGroup.getCustomFields();
            for (final CustomField customField : customFields) {
                // Get the groups using reflection
                final Collection<MemberGroup> groups = PropertyHelper.get(customField, "groups");
                groups.remove(memberGroup);
                customFieldDao.update(customField);
            }

            // From transaction fees
            final Collection<TransactionFee> fromTransactionFees = memberGroup.getFromTransactionFees();
            for (final TransactionFee transactionFee : fromTransactionFees) {
                transactionFee.getFromGroups().remove(memberGroup);
                transactionFeeDao.update(transactionFee);
            }

            // To transaction fees
            final Collection<TransactionFee> toTransactionFees = memberGroup.getToTransactionFees();
            for (final TransactionFee transactionFee : toTransactionFees) {
                transactionFee.getToGroups().remove(memberGroup);
                transactionFeeDao.update(transactionFee);
            }

        }

    }

    @SuppressWarnings("unchecked")
    private <G extends Group> G save(G group) {
        if (group.isTransient()) {
            group = groupDao.insert(group);
        } else {
            // We must keep the many-to-many relationships, or they would be cleared...
            final Group currentGroup = load(group.getId(), FETCH_TO_KEEP_DATA);

            group.setPermissions(new HashSet<Permission>(currentGroup.getPermissions()));
            group.setTransferTypes(new ArrayList<TransferType>(currentGroup.getTransferTypes()));
            group.setConversionSimulationTTs(new ArrayList<TransferType>(currentGroup.getConversionSimulationTTs()));
            group.setGuaranteeTypes(new ArrayList<GuaranteeType>(currentGroup.getGuaranteeTypes()));

            if (group instanceof SystemGroup) {
                final SystemGroup systemGroup = (SystemGroup) group;
                final SystemGroup currentSystemGroup = ((SystemGroup) currentGroup);
                systemGroup.setDocuments(new ArrayList<Document>(currentSystemGroup.getDocuments()));
                systemGroup.setMessageCategories(new ArrayList<MessageCategory>(currentSystemGroup.getMessageCategories()));
                systemGroup.setChargebackTransferTypes(new ArrayList<TransferType>(currentSystemGroup.getChargebackTransferTypes()));
            }

            if (group instanceof AdminGroup) {
                final AdminGroup adminGroup = (AdminGroup) group;
                final AdminGroup currentAdminGroup = ((AdminGroup) currentGroup);
                adminGroup.setTransferTypesAsMember(new ArrayList<TransferType>(currentAdminGroup.getTransferTypesAsMember()));
                adminGroup.setManagesGroups(new ArrayList<MemberGroup>(currentAdminGroup.getManagesGroups()));
                adminGroup.setViewConnectedAdminsOf(new ArrayList<AdminGroup>(currentAdminGroup.getViewConnectedAdminsOf()));
                adminGroup.setViewInformationOf(new ArrayList<SystemAccountType>(currentAdminGroup.getViewInformationOf()));
                adminGroup.setViewAdminRecordTypes(new ArrayList<MemberRecordType>(currentAdminGroup.getViewAdminRecordTypes()));
                adminGroup.setCreateAdminRecordTypes(new ArrayList<MemberRecordType>(currentAdminGroup.getCreateAdminRecordTypes()));
                adminGroup.setModifyAdminRecordTypes(new ArrayList<MemberRecordType>(currentAdminGroup.getModifyAdminRecordTypes()));
                adminGroup.setDeleteAdminRecordTypes(new ArrayList<MemberRecordType>(currentAdminGroup.getDeleteAdminRecordTypes()));
                adminGroup.setViewMemberRecordTypes(new ArrayList<MemberRecordType>(currentAdminGroup.getViewMemberRecordTypes()));
                adminGroup.setCreateMemberRecordTypes(new ArrayList<MemberRecordType>(currentAdminGroup.getCreateMemberRecordTypes()));
                adminGroup.setModifyMemberRecordTypes(new ArrayList<MemberRecordType>(currentAdminGroup.getModifyMemberRecordTypes()));
                adminGroup.setDeleteMemberRecordTypes(new ArrayList<MemberRecordType>(currentAdminGroup.getDeleteMemberRecordTypes()));
            }
            if (group instanceof BrokerGroup) {
                final BrokerGroup brokerGroup = (BrokerGroup) group;
                final BrokerGroup currentBrokerGroup = (BrokerGroup) currentGroup;

                final List<Document> brokerDocuments = new ArrayList<Document>();
                if (currentBrokerGroup.getBrokerDocuments() != null) {
                    brokerDocuments.addAll(currentBrokerGroup.getBrokerDocuments());
                }
                brokerGroup.setBrokerDocuments(brokerDocuments);

                final List<AccountType> brokerCanViewInformationOf = new ArrayList<AccountType>();
                if (brokerGroup.getBrokerCanViewInformationOf() != null) {
                    brokerCanViewInformationOf.addAll(brokerGroup.getBrokerCanViewInformationOf());
                }
                brokerGroup.setBrokerCanViewInformationOf(brokerCanViewInformationOf);

                brokerGroup.setTransferTypesAsMember(new ArrayList<TransferType>(currentBrokerGroup.getTransferTypesAsMember()));
                brokerGroup.setBrokerConversionSimulationTTs(new ArrayList<TransferType>(currentBrokerGroup.getBrokerConversionSimulationTTs()));
                brokerGroup.setBrokerMemberRecordTypes(new ArrayList<MemberRecordType>(currentBrokerGroup.getBrokerMemberRecordTypes()));
                brokerGroup.setBrokerCreateMemberRecordTypes(new ArrayList<MemberRecordType>(currentBrokerGroup.getBrokerCreateMemberRecordTypes()));
                brokerGroup.setBrokerModifyMemberRecordTypes(new ArrayList<MemberRecordType>(currentBrokerGroup.getBrokerModifyMemberRecordTypes()));
                brokerGroup.setBrokerDeleteMemberRecordTypes(new ArrayList<MemberRecordType>(currentBrokerGroup.getBrokerDeleteMemberRecordTypes()));

                // "possibleInitialGroups" is updated at edit group screen, so it doesn't need to be copied
            }
            if (group instanceof MemberGroup) {
                final MemberGroup memberGroup = (MemberGroup) group;
                final MemberGroup currentMemberGroup = (MemberGroup) currentGroup;
                memberGroup.setAccountSettings(currentMemberGroup.getAccountSettings());

                // Ensure that no channel will be set by default if it's not accessible
                memberGroup.getDefaultChannels().retainAll(memberGroup.getChannels());

                // Ensure the removedChannels collection contains the channels which were removed
                final Collection<Channel> removedChannels = new HashSet<Channel>();
                removedChannels.addAll(currentMemberGroup.getChannels());
                removedChannels.removeAll(memberGroup.getChannels());

                final List<MemberGroup> viewProfile = new ArrayList<MemberGroup>();
                if (currentMemberGroup.getCanViewProfileOfGroups() != null) {
                    viewProfile.addAll(currentMemberGroup.getCanViewProfileOfGroups());
                }
                memberGroup.setCanViewProfileOfGroups(viewProfile);

                final List<AccountType> canViewInformationOf = new ArrayList<AccountType>();
                if (currentMemberGroup.getCanViewInformationOf() != null) {
                    canViewInformationOf.addAll(currentMemberGroup.getCanViewInformationOf());
                }
                memberGroup.setCanViewInformationOf(canViewInformationOf);

                final List<MemberGroup> viewAds = new ArrayList<MemberGroup>();
                if (currentMemberGroup.getCanViewAdsOfGroups() != null) {
                    viewAds.addAll(currentMemberGroup.getCanViewAdsOfGroups());
                }
                memberGroup.setCanViewAdsOfGroups(viewAds);

                final List<AdminGroup> managedByGroups = new ArrayList<AdminGroup>();
                if (currentMemberGroup.getManagedByGroups() != null) {
                    managedByGroups.addAll(currentMemberGroup.getManagedByGroups());
                }
                memberGroup.setManagedByGroups(managedByGroups);

                // "defaultMailMessages" is updated at edit group screen, so it doesn't need to be copied
                // "smsMessages" is updated at edit group screen, so it doesn't need to be copied
                // "defaultSmsMessages" is updated at edit group screen, so it doesn't need to be copied
                // "channels" is updated at edit group screen, so it doesn't need to be copied
                // "defaultChannels" is updated at edit group screen, so it doesn't need to be copied

                // Add the main web access to default and accessible channels.
                final Channel webChannel = channelService.loadByInternalName(Channel.WEB);
                memberGroup.setChannels(CollectionUtils.union(memberGroup.getChannels(), Collections.singleton(webChannel)));
                memberGroup.setDefaultChannels(CollectionUtils.union(memberGroup.getDefaultChannels(), Collections.singleton(webChannel)));

                final List<Channel> requestPaymentByChannels = new ArrayList<Channel>();
                if (currentMemberGroup.getRequestPaymentByChannels() != null) {
                    requestPaymentByChannels.addAll(currentMemberGroup.getRequestPaymentByChannels());
                }
                memberGroup.setRequestPaymentByChannels(requestPaymentByChannels);

                final MemberGroupSettings memberSettings = memberGroup.getMemberSettings();
                memberSettings.setGroupAfterExpiration(fetchService.fetch(memberSettings.getGroupAfterExpiration()));

                // Update the basic settings of operator groups for members in this group
                final GroupQuery operatorQuery = new GroupQuery();
                operatorQuery.setNature(Group.Nature.OPERATOR);
                operatorQuery.fetch(RelationshipHelper.nested(OperatorGroup.Relationships.MEMBER, Element.Relationships.GROUP));
                final List<OperatorGroup> operatorGroups = (List<OperatorGroup>) groupDao.search(operatorQuery);
                for (final OperatorGroup operatorGroup : operatorGroups) {
                    if (operatorGroup.getMember().getGroup().equals(memberGroup)) {
                        groupDao.update(operatorGroup);
                    }
                }
                final List<MemberGroup> canIssueCertificationToGroups = new ArrayList<MemberGroup>();
                if (currentMemberGroup.getCanIssueCertificationToGroups() != null) {
                    canIssueCertificationToGroups.addAll(currentMemberGroup.getCanIssueCertificationToGroups());
                }
                memberGroup.setCanIssueCertificationToGroups(canIssueCertificationToGroups);

                final List<MemberGroup> canBuyWithPaymentObligationsFromGroups = new ArrayList<MemberGroup>();
                if (currentMemberGroup.getCanBuyWithPaymentObligationsFromGroups() != null) {
                    canBuyWithPaymentObligationsFromGroups.addAll(currentMemberGroup.getCanBuyWithPaymentObligationsFromGroups());
                }
                memberGroup.setCanBuyWithPaymentObligationsFromGroups(canBuyWithPaymentObligationsFromGroups);

                // Ensure the message notification types are not present on the group for SMS
                final Collection<Message.Type> smsMessages = memberGroup.getSmsMessages();
                if (smsMessages != null) {
                    smsMessages.remove(Message.Type.FROM_MEMBER);
                    smsMessages.remove(Message.Type.FROM_ADMIN_TO_GROUP);
                    smsMessages.remove(Message.Type.FROM_ADMIN_TO_MEMBER);
                }
                final Collection<Type> defaultSmsMessages = memberGroup.getDefaultSmsMessages();
                if (defaultSmsMessages != null) {
                    defaultSmsMessages.remove(Message.Type.FROM_MEMBER);
                    defaultSmsMessages.remove(Message.Type.FROM_ADMIN_TO_GROUP);
                    defaultSmsMessages.remove(Message.Type.FROM_ADMIN_TO_MEMBER);
                }

                // Remove from all members channels which are no longer accessible
                elementDao.removeChannelsFromMembers(memberGroup, removedChannels);

                // ensure activation status
                if (memberGroup.isRemoved()) {
                    memberGroup.setActive(false);
                }
            }
            if (group instanceof OperatorGroup) {
                final OperatorGroup operatorGroup = (OperatorGroup) group;
                final OperatorGroup currentOperatorGroup = (OperatorGroup) currentGroup;

                // Check the account types
                final List<AccountType> canViewInformationOf = new ArrayList<AccountType>();
                if (currentOperatorGroup.getCanViewInformationOf() != null) {
                    canViewInformationOf.addAll(currentOperatorGroup.getCanViewInformationOf());
                }
                operatorGroup.setCanViewInformationOf(canViewInformationOf);
            }
            group = groupDao.update(group);
        }
        // Ensure the permissions cache for this group is evicted
        permissionService.evictCache(group);
        return group;
    }

}
