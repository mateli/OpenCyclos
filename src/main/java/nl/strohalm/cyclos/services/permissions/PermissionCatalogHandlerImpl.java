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
package nl.strohalm.cyclos.services.permissions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nl.strohalm.cyclos.access.AdminAdminPermission;
import nl.strohalm.cyclos.access.AdminMemberPermission;
import nl.strohalm.cyclos.access.AdminSystemPermission;
import nl.strohalm.cyclos.access.BrokerPermission;
import nl.strohalm.cyclos.access.MemberPermission;
import nl.strohalm.cyclos.access.OperatorPermission;
import nl.strohalm.cyclos.access.Permission;
import nl.strohalm.cyclos.entities.Entity;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.AccountType;
import nl.strohalm.cyclos.entities.accounts.Currency;
import nl.strohalm.cyclos.entities.accounts.MemberAccountType;
import nl.strohalm.cyclos.entities.accounts.SystemAccountType;
import nl.strohalm.cyclos.entities.accounts.guarantees.GuaranteeType.Model;
import nl.strohalm.cyclos.entities.accounts.guarantees.GuaranteeTypeQuery;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferType;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferType.Context;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferTypeQuery;
import nl.strohalm.cyclos.entities.customization.documents.Document;
import nl.strohalm.cyclos.entities.customization.documents.DocumentQuery;
import nl.strohalm.cyclos.entities.groups.AdminGroup;
import nl.strohalm.cyclos.entities.groups.BrokerGroup;
import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.entities.groups.GroupQuery;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.groups.OperatorGroup;
import nl.strohalm.cyclos.entities.groups.SystemGroup;
import nl.strohalm.cyclos.entities.members.records.MemberRecordTypeQuery;
import nl.strohalm.cyclos.services.access.ChannelServiceLocal;
import nl.strohalm.cyclos.services.accounts.AccountTypeServiceLocal;
import nl.strohalm.cyclos.services.accounts.CurrencyServiceLocal;
import nl.strohalm.cyclos.services.accounts.MemberAccountTypeQuery;
import nl.strohalm.cyclos.services.accounts.SystemAccountTypeQuery;
import nl.strohalm.cyclos.services.accounts.guarantees.GuaranteeTypeServiceLocal;
import nl.strohalm.cyclos.services.customization.DocumentServiceLocal;
import nl.strohalm.cyclos.services.elements.MemberRecordTypeServiceLocal;
import nl.strohalm.cyclos.services.elements.MessageCategoryServiceLocal;
import nl.strohalm.cyclos.services.fetch.FetchServiceLocal;
import nl.strohalm.cyclos.services.groups.GroupServiceLocal;
import nl.strohalm.cyclos.services.transactions.TransactionContext;
import nl.strohalm.cyclos.services.transfertypes.TransferTypeServiceLocal;
import nl.strohalm.cyclos.utils.EntityHelper;
import nl.strohalm.cyclos.utils.EntityVO;
import nl.strohalm.cyclos.utils.access.PermissionCatalogHandler;
import nl.strohalm.cyclos.utils.access.PermissionHelper;

import org.apache.commons.collections.CollectionUtils;

public class PermissionCatalogHandlerImpl implements PermissionCatalogHandler {
    /**
     * This bean contains data used by several permissions to filter its possible values. That data is loaded only once and shared between each
     * permission's query.
     * @author ameyer
     */
    private static class CommonDataBean {
        private final Collection<SystemAccountType> systemAccountTypes;
        private final Collection<MemberAccountType> memberAccountTypes;

        private CommonDataBean(final Collection<SystemAccountType> systemAccountTypes, final Collection<MemberAccountType> memberAccountTypes) {
            this.memberAccountTypes = memberAccountTypes;
            this.systemAccountTypes = systemAccountTypes;
        }
    }

    /**
     * Shared types between permissions.
     * @see permissionSharedValueMap
     * @author ameyer
     */
    private static enum SharedValue {
        DOCUMENTS, MEMBER_GROUPS,
        ACCOUNT_TYPES,
        MEMBER_TO_SYSTEM_TT,
        MEMBER_TO_MEMBER_TT,
        MEMBER_TO_SELF_TT,
        RECORD_TYPES,
        CONVERSION_TT,
        MESSAGE_CATEGORIES;
    }

    private static final Relationship[]               FETCH = { Group.Relationships.PERMISSIONS, Group.Relationships.TRANSFER_TYPES, SystemGroup.Relationships.DOCUMENTS, SystemGroup.Relationships.MESSAGE_CATEGORIES, BrokerGroup.Relationships.BROKER_DOCUMENTS, SystemGroup.Relationships.CHARGEBACK_TRANSFER_TYPES, AdminGroup.Relationships.MANAGES_GROUPS, AdminGroup.Relationships.TRANSFER_TYPES_AS_MEMBER, AdminGroup.Relationships.VIEW_INFORMATION_OF, AdminGroup.Relationships.VIEW_CONNECTED_ADMINS_OF, MemberGroup.Relationships.CAN_VIEW_ADS_OF_GROUPS, MemberGroup.Relationships.CAN_VIEW_PROFILE_OF_GROUPS };

    /**
     * Map from permission to shared value type. All permissions having the same shared value means they share the same possible values<br>
     * Permissions that don't share its values with any other permission are not added to this map<br>
     * NOTE: To avoid load the same collection more than once, each new permission sharing values with any other should be added to this map.
     */
    private static final Map<Permission, SharedValue> permissionSharedValueMap;
    static {
        permissionSharedValueMap = new HashMap<Permission, SharedValue>();

        permissionSharedValueMap.put(AdminMemberPermission.DOCUMENTS_DETAILS, SharedValue.DOCUMENTS);
        permissionSharedValueMap.put(MemberPermission.DOCUMENTS_VIEW, SharedValue.DOCUMENTS);

        permissionSharedValueMap.put(MemberPermission.ADS_VIEW, SharedValue.MEMBER_GROUPS);
        permissionSharedValueMap.put(MemberPermission.GUARANTEES_BUY_WITH_PAYMENT_OBLIGATIONS, SharedValue.MEMBER_GROUPS);
        permissionSharedValueMap.put(MemberPermission.GUARANTEES_ISSUE_CERTIFICATIONS, SharedValue.MEMBER_GROUPS);
        permissionSharedValueMap.put(MemberPermission.PROFILE_VIEW, SharedValue.MEMBER_GROUPS);

        permissionSharedValueMap.put(BrokerPermission.REPORTS_SHOW_ACCOUNT_INFORMATION, SharedValue.ACCOUNT_TYPES);
        permissionSharedValueMap.put(MemberPermission.REPORTS_SHOW_ACCOUNT_INFORMATION, SharedValue.ACCOUNT_TYPES);

        permissionSharedValueMap.put(AdminMemberPermission.PAYMENTS_PAYMENT_AS_MEMBER_TO_SYSTEM, SharedValue.MEMBER_TO_SYSTEM_TT);
        permissionSharedValueMap.put(BrokerPermission.MEMBER_PAYMENTS_PAYMENT_AS_MEMBER_TO_SYSTEM, SharedValue.MEMBER_TO_SYSTEM_TT);

        permissionSharedValueMap.put(AdminMemberPermission.PAYMENTS_PAYMENT_AS_MEMBER_TO_MEMBER, SharedValue.MEMBER_TO_MEMBER_TT);
        permissionSharedValueMap.put(BrokerPermission.MEMBER_PAYMENTS_PAYMENT_AS_MEMBER_TO_MEMBER, SharedValue.MEMBER_TO_MEMBER_TT);

        permissionSharedValueMap.put(AdminMemberPermission.PAYMENTS_PAYMENT_AS_MEMBER_TO_SELF, SharedValue.MEMBER_TO_SELF_TT);
        permissionSharedValueMap.put(BrokerPermission.MEMBER_PAYMENTS_PAYMENT_AS_MEMBER_TO_SELF, SharedValue.MEMBER_TO_SELF_TT);

        permissionSharedValueMap.put(AdminAdminPermission.RECORDS_CREATE, SharedValue.RECORD_TYPES);
        permissionSharedValueMap.put(AdminAdminPermission.RECORDS_MODIFY, SharedValue.RECORD_TYPES);
        permissionSharedValueMap.put(AdminAdminPermission.RECORDS_DELETE, SharedValue.RECORD_TYPES);
        permissionSharedValueMap.put(AdminAdminPermission.RECORDS_VIEW, SharedValue.RECORD_TYPES);
        permissionSharedValueMap.put(AdminMemberPermission.RECORDS_CREATE, SharedValue.RECORD_TYPES);
        permissionSharedValueMap.put(AdminMemberPermission.RECORDS_MODIFY, SharedValue.RECORD_TYPES);
        permissionSharedValueMap.put(AdminMemberPermission.RECORDS_DELETE, SharedValue.RECORD_TYPES);
        permissionSharedValueMap.put(AdminMemberPermission.RECORDS_VIEW, SharedValue.RECORD_TYPES);
        permissionSharedValueMap.put(BrokerPermission.MEMBER_RECORDS_CREATE, SharedValue.RECORD_TYPES);
        permissionSharedValueMap.put(BrokerPermission.MEMBER_RECORDS_MODIFY, SharedValue.RECORD_TYPES);
        permissionSharedValueMap.put(BrokerPermission.MEMBER_RECORDS_DELETE, SharedValue.RECORD_TYPES);
        permissionSharedValueMap.put(BrokerPermission.MEMBER_RECORDS_VIEW, SharedValue.RECORD_TYPES);


        permissionSharedValueMap.put(AdminMemberPermission.MESSAGES_VIEW, SharedValue.MESSAGE_CATEGORIES);
        permissionSharedValueMap.put(MemberPermission.MESSAGES_SEND_TO_ADMINISTRATION, SharedValue.MESSAGE_CATEGORIES);

    }

    private AccountTypeServiceLocal                   accountTypeService;

    private ChannelServiceLocal                       channelService;
    private CurrencyServiceLocal                      currencyService;
    private DocumentServiceLocal                      documentService;
    private GroupServiceLocal                         groupService;

    private GuaranteeTypeServiceLocal                 guaranteeTypeService;
    private MessageCategoryServiceLocal               messageCategoryService;

    private TransferTypeServiceLocal                  transferTypeService;

    private MemberRecordTypeServiceLocal              memberRecordTypeService;

    private FetchServiceLocal                         fetchService;

    private PermissionServiceLocal                    permissionService;

    private Map<Permission, Set<EntityVO>>            possibleValuesMap;

    private Group                                     group;

    @Override
    public Set<EntityVO> currentValues(final Permission permission) {
        return EntityHelper.toEntityVO(PermissionHelper.getAllowedValues(group, permission));
    }

    @Override
    public Set<EntityVO> possibleValues(final Permission permission) {
        return possibleValuesMap.get(permission);
    }

    public void setAccountTypeServiceLocal(final AccountTypeServiceLocal accountTypeService) {
        this.accountTypeService = accountTypeService;
    }

    public void setChannelServiceLocal(final ChannelServiceLocal channelService) {
        this.channelService = channelService;
    }

    public void setCurrencyServiceLocal(final CurrencyServiceLocal currencyService) {
        this.currencyService = currencyService;
    }

    public void setDocumentServiceLocal(final DocumentServiceLocal documentService) {
        this.documentService = documentService;
    }

    public void setFetchServiceLocal(final FetchServiceLocal fetchService) {
        this.fetchService = fetchService;
    }

    public void setGroupServiceLocal(final GroupServiceLocal groupService) {
        this.groupService = groupService;
    }

    public void setGuaranteeTypeServiceLocal(final GuaranteeTypeServiceLocal guaranteeTypeService) {
        this.guaranteeTypeService = guaranteeTypeService;
    }

    public void setMemberRecordTypeServiceLocal(final MemberRecordTypeServiceLocal memberRecordTypeService) {
        this.memberRecordTypeService = memberRecordTypeService;
    }

    public void setMessageCategoryServiceLocal(final MessageCategoryServiceLocal messageCategoryService) {
        this.messageCategoryService = messageCategoryService;
    }

    public void setPermissionServiceLocal(final PermissionServiceLocal permissionService) {
        this.permissionService = permissionService;
    }

    public void setTransferTypeServiceLocal(final TransferTypeServiceLocal transferTypeService) {
        this.transferTypeService = transferTypeService;
    }

    @SuppressWarnings("unchecked")
    private Collection<MemberAccountType> getMemberAccountTypes() {
        // Get the associated account types
        Collection<MemberAccountType> memberAccountTypes = null;
        if (group instanceof BrokerGroup) {
            memberAccountTypes = (Collection<MemberAccountType>) accountTypeService.search(new MemberAccountTypeQuery());
        } else if (group instanceof MemberGroup) {
            memberAccountTypes = ((MemberGroup) group).getAccountTypes();
        } else {
            final AdminGroup adminGroup = (AdminGroup) group;
            final Collection<MemberGroup> managesGroups = adminGroup.getManagesGroups();
            memberAccountTypes = new HashSet<MemberAccountType>();
            for (final MemberGroup memberGroup : managesGroups) {
                memberAccountTypes.addAll(memberGroup.getAccountTypes());
            }
        }
        return memberAccountTypes;
    }

    private Collection<SystemAccountType> getSystemAccountTypes() {
        // Get the associated account types
        Collection<SystemAccountType> systemAccountTypes = null;
        if (group instanceof AdminGroup) {
            systemAccountTypes = ((AdminGroup) group).getViewInformationOf();
        }
        return systemAccountTypes;
    }

    private Collection<? extends Entity> load(final CommonDataBean commonData, final AdminAdminPermission permission) {
        switch (permission) {
            case RECORDS_VIEW:
            case RECORDS_CREATE:
            case RECORDS_MODIFY:
            case RECORDS_DELETE:
                return load(commonData, permissionSharedValueMap.get(permission));
            default:
                throw new IllegalArgumentException(String.format("Unsupported list permission: %1$s.%2$s", permission.getClass().getSimpleName(), permission));
        }
    }

    private Collection<? extends Entity> load(final CommonDataBean commonData, final AdminMemberPermission permission) {
        switch (permission) {
            case PAYMENTS_PAYMENT_AS_MEMBER_TO_SYSTEM:
            case PAYMENTS_PAYMENT_AS_MEMBER_TO_MEMBER:
            case PAYMENTS_PAYMENT_AS_MEMBER_TO_SELF:
            case RECORDS_VIEW:
            case RECORDS_CREATE:
            case RECORDS_MODIFY:
            case RECORDS_DELETE:
            case DOCUMENTS_DETAILS:
            case MESSAGES_VIEW:
                return load(commonData, permissionSharedValueMap.get(permission));
            case MEMBERS_VIEW:
                // Member groups
                GroupQuery groupQuery = new GroupQuery();
                groupQuery.setIgnoreManagedBy(true);
                groupQuery.setNatures(Group.Nature.MEMBER, Group.Nature.BROKER);
                return groupService.search(groupQuery);
            case PAYMENTS_PAYMENT:
                // System to member payments
                TransferTypeQuery ttQuery = new TransferTypeQuery();
                ttQuery.setContext(TransactionContext.PAYMENT);
                ttQuery.setFromNature(AccountType.Nature.SYSTEM);
                ttQuery.setToNature(AccountType.Nature.MEMBER);
                ttQuery.setFromAccountTypes(commonData.systemAccountTypes);
                ttQuery.setToAccountTypes(commonData.memberAccountTypes);
                return transferTypeService.search(ttQuery);
            case PAYMENTS_CHARGEBACK:
                // Member chargebacks
                final TransferTypeQuery memberChargebackQuery = new TransferTypeQuery();
                memberChargebackQuery.setToNature(AccountType.Nature.MEMBER);
                final List<TransferType> memberChargebacks = transferTypeService.search(memberChargebackQuery);
                for (final Iterator<TransferType> iter = memberChargebacks.iterator(); iter.hasNext();) {
                    final TransferType tt = iter.next();
                    final Context ctx = tt.getContext();
                    if (!ctx.isPayment() && !ctx.isSelfPayment()) {
                        iter.remove();
                    }
                }
                return memberChargebacks;
            case LOANS_GRANT:
                // Loans
                ttQuery = new TransferTypeQuery();
                ttQuery.setContext(TransactionContext.LOAN);
                ttQuery.setFromAccountTypes(commonData.systemAccountTypes);
                ttQuery.setToAccountTypes(commonData.memberAccountTypes);
                return transferTypeService.search(ttQuery);
            case GUARANTEES_REGISTER_GUARANTEES:
                final GuaranteeTypeQuery guaranteeTypeQuery = new GuaranteeTypeQuery();
                guaranteeTypeQuery.setEnabled(true);
                final Collection<Model> models = new ArrayList<Model>();
                models.add(Model.WITH_BUYER_AND_SELLER);
                models.add(Model.WITH_BUYER_ONLY);
                guaranteeTypeQuery.setModels(models);
                return guaranteeTypeService.search(guaranteeTypeQuery);
            default:
                throw new IllegalArgumentException(String.format("Unsupported list permission: %1$s.%2$s", permission.getClass().getSimpleName(), permission));
        }
    }

    private Collection<? extends Entity> load(final CommonDataBean commonData, final AdminSystemPermission permission) {
        switch (permission) {
            case ACCOUNTS_INFORMATION:
                return accountTypeService.search(new SystemAccountTypeQuery());
            case PAYMENTS_PAYMENT:
                // System to system payments
                TransferTypeQuery ttQuery = new TransferTypeQuery();
                ttQuery.setContext(TransactionContext.SELF_PAYMENT);
                ttQuery.setFromNature(AccountType.Nature.SYSTEM);
                ttQuery.setToNature(AccountType.Nature.SYSTEM);
                ttQuery.setFromAccountTypes(commonData.systemAccountTypes);
                ttQuery.setToAccountTypes(commonData.systemAccountTypes);
                return transferTypeService.search(ttQuery);
            case PAYMENTS_CHARGEBACK:
                // System chargebacks
                final TransferTypeQuery systemChargebackQuery = new TransferTypeQuery();
                systemChargebackQuery.setToNature(AccountType.Nature.SYSTEM);
                final List<TransferType> systemChargebacks = transferTypeService.search(systemChargebackQuery);
                for (final Iterator<TransferType> iter = systemChargebacks.iterator(); iter.hasNext();) {
                    final TransferType tt = iter.next();
                    final Context ctx = tt.getContext();
                    if (!ctx.isPayment() && !ctx.isSelfPayment()) {
                        iter.remove();
                    }
                }
                return systemChargebacks;
            case STATUS_VIEW_CONNECTED_ADMINS:
                // View connected admins
                GroupQuery groupQuery = new GroupQuery();
                groupQuery.setNatures(Group.Nature.ADMIN);
                return groupService.search(groupQuery);
            default:
                throw new IllegalArgumentException(String.format("Unsupported list permission: %1$s.%2$s", permission.getClass().getSimpleName(), permission));
        }
    }

    private Collection<? extends Entity> load(final CommonDataBean commonData, final BrokerPermission permission) {
        switch (permission) {
            case REPORTS_SHOW_ACCOUNT_INFORMATION:
            case MEMBER_PAYMENTS_PAYMENT_AS_MEMBER_TO_SYSTEM:
            case MEMBER_PAYMENTS_PAYMENT_AS_MEMBER_TO_MEMBER:
            case MEMBER_PAYMENTS_PAYMENT_AS_MEMBER_TO_SELF:
            case MEMBER_RECORDS_VIEW:
            case MEMBER_RECORDS_CREATE:
            case MEMBER_RECORDS_MODIFY:
            case MEMBER_RECORDS_DELETE:
                return load(commonData, permissionSharedValueMap.get(permission));
            case DOCUMENTS_VIEW:
                final DocumentQuery documentQuery = new DocumentQuery();
                final ArrayList<Document.Nature> natures = new ArrayList<Document.Nature>();
                natures.add(Document.Nature.DYNAMIC);
                documentQuery.setNatures(natures);
                return documentService.search(documentQuery);
            default:
                throw new IllegalArgumentException(String.format("Unsupported list permission: %1$s.%2$s", permission.getClass().getSimpleName(), permission));
        }
    }

    private Collection<? extends Entity> load(final CommonDataBean commonData, final MemberPermission permission) {
        switch (permission) {
            case PROFILE_VIEW:
            case ADS_VIEW:
            case GUARANTEES_ISSUE_CERTIFICATIONS:
            case GUARANTEES_BUY_WITH_PAYMENT_OBLIGATIONS:
            case DOCUMENTS_VIEW:
            case REPORTS_SHOW_ACCOUNT_INFORMATION:
            case MESSAGES_SEND_TO_ADMINISTRATION:
                return load(commonData, permissionSharedValueMap.get(permission));
            case PAYMENTS_PAYMENT_TO_SELF:
                // Self payment
                TransferTypeQuery ttQuery = new TransferTypeQuery();
                ttQuery.setContext(TransactionContext.SELF_PAYMENT);
                ttQuery.setFromNature(AccountType.Nature.MEMBER);
                ttQuery.setToNature(AccountType.Nature.MEMBER);
                ttQuery.setFromAccountTypes(commonData.memberAccountTypes);
                ttQuery.setToAccountTypes(commonData.memberAccountTypes);
                return transferTypeService.search(ttQuery);
            case PAYMENTS_PAYMENT_TO_MEMBER:
                // Member to member payment
                ttQuery = new TransferTypeQuery();
                ttQuery.setFromNature(AccountType.Nature.MEMBER);
                ttQuery.setToNature(AccountType.Nature.MEMBER);
                ttQuery.setFromAccountTypes(commonData.memberAccountTypes);
                final List<TransferType> memberMemberTTs = new ArrayList<TransferType>(transferTypeService.search(ttQuery));
                for (final Iterator<TransferType> iter = memberMemberTTs.iterator(); iter.hasNext();) {
                    final Context ctx = iter.next().getContext();
                    if (!ctx.isPayment()) {
                        iter.remove();
                    }
                }
                return memberMemberTTs;
            case PAYMENTS_PAYMENT_TO_SYSTEM:
                // Member to system payments
                ttQuery = new TransferTypeQuery();
                ttQuery.setContext(TransactionContext.PAYMENT);
                ttQuery.setFromNature(AccountType.Nature.MEMBER);
                ttQuery.setToNature(AccountType.Nature.SYSTEM);
                ttQuery.setFromAccountTypes(commonData.memberAccountTypes);
                ttQuery.setToAccountTypes(commonData.systemAccountTypes);
                return transferTypeService.search(ttQuery);
            case PAYMENTS_REQUEST:
                return channelService.listSupportingPaymentRequest();
            case PAYMENTS_CHARGEBACK:
                // Chargeback received payment
                ttQuery = new TransferTypeQuery();
                ttQuery.setContext(TransactionContext.PAYMENT);
                ttQuery.setToAccountTypes(commonData.memberAccountTypes);
                return transferTypeService.search(ttQuery);
            case GUARANTEES_ISSUE_GUARANTEES:
                final GuaranteeTypeQuery guaranteeTypeQuery = new GuaranteeTypeQuery();
                guaranteeTypeQuery.setEnabled(true);
                final List<Currency> currencies = currencyService.listByMemberGroup((MemberGroup) group);
                guaranteeTypeQuery.setCurrencies(currencies);
                return guaranteeTypeService.search(guaranteeTypeQuery);
            default:
                throw new IllegalArgumentException(String.format("Unsupported list permission: %1$s.%2$s", permission.getClass().getSimpleName(), permission));
        }
    }

    private Collection<? extends Entity> load(final CommonDataBean commonData, final SharedValue shared) {
        switch (shared) {
            case DOCUMENTS:
                // Static and dynamic documents
                final DocumentQuery documentQuery = new DocumentQuery();
                final ArrayList<Document.Nature> natures = new ArrayList<Document.Nature>();
                natures.add(Document.Nature.DYNAMIC);
                natures.add(Document.Nature.STATIC);
                documentQuery.setNatures(natures);
                return documentService.search(documentQuery);
            case MEMBER_GROUPS:
                // Groups
                final GroupQuery groupQuery = new GroupQuery();
                groupQuery.setNatures(Group.Nature.MEMBER, Group.Nature.BROKER);
                groupQuery.setStatus(Group.Status.NORMAL);
                groupQuery.setIgnoreManagedBy(true);
                return groupService.search(groupQuery);
            case ACCOUNT_TYPES:
                // Account information
                final MemberGroup memberGroup = (MemberGroup) group;
                final Collection<MemberGroup> canViewProfileOfGroups = memberGroup.getCanViewProfileOfGroups();
                if (CollectionUtils.isNotEmpty(canViewProfileOfGroups)) {
                    final MemberAccountTypeQuery accountTypeQuery = new MemberAccountTypeQuery();
                    accountTypeQuery.setRelatedToGroups(canViewProfileOfGroups);
                    return accountTypeService.search(accountTypeQuery);
                } else {
                    return Collections.emptyList();
                }
            case MEMBER_TO_SYSTEM_TT:
                // As member to system payments
                TransferTypeQuery ttQuery = new TransferTypeQuery();
                ttQuery.setContext(TransactionContext.PAYMENT);
                ttQuery.setFromNature(AccountType.Nature.MEMBER);
                ttQuery.setToNature(AccountType.Nature.SYSTEM);
                ttQuery.setToAccountTypes(commonData.systemAccountTypes);
                ttQuery.setFromAccountTypes(commonData.memberAccountTypes);
                return transferTypeService.search(ttQuery);
            case MEMBER_TO_MEMBER_TT:
                // As member to member payment
                ttQuery = new TransferTypeQuery();
                ttQuery.setFromNature(AccountType.Nature.MEMBER);
                ttQuery.setToNature(AccountType.Nature.MEMBER);
                ttQuery.setFromAccountTypes(commonData.memberAccountTypes);
                ttQuery.setToAccountTypes(commonData.memberAccountTypes);
                final List<TransferType> memberMemberTTs = new ArrayList<TransferType>(transferTypeService.search(ttQuery));
                for (final Iterator<TransferType> iter = memberMemberTTs.iterator(); iter.hasNext();) {
                    final Context ctx = iter.next().getContext();
                    if (!ctx.isPayment()) {
                        iter.remove();
                    }
                }
                return memberMemberTTs;
            case MEMBER_TO_SELF_TT:
                // As member to self payment
                ttQuery = new TransferTypeQuery();
                ttQuery.setContext(TransactionContext.SELF_PAYMENT);
                ttQuery.setFromNature(AccountType.Nature.MEMBER);
                ttQuery.setToNature(AccountType.Nature.MEMBER);
                ttQuery.setFromAccountTypes(commonData.memberAccountTypes);
                ttQuery.setToAccountTypes(commonData.memberAccountTypes);
                return transferTypeService.search(ttQuery);
            case RECORD_TYPES:
                // Member record types
                final MemberRecordTypeQuery memberRecordTypeQuery = new MemberRecordTypeQuery();
                return memberRecordTypeService.search(memberRecordTypeQuery);
            case CONVERSION_TT:
                return transferTypeService.getConversionTTs();
            case MESSAGE_CATEGORIES:
                return messageCategoryService.listAll();
            default:
                throw new IllegalArgumentException("Unknown shared value: " + shared);
        }
    }

    /**
     * In case of operator permissions we must check the parent members permissions (if any)
     * @param permission
     * @return
     */
    private Collection<? extends Entity> load(final OperatorPermission permission) {
        MemberGroup loggedMemberGroup = (MemberGroup) ((OperatorGroup) group).getMember().getGroup();

        if (permission.getParentPermissions().length > 0 && !permissionService.hasPermission(permission.getParentPermissions())) {
            return Collections.emptyList();
        } else {
            switch (permission) {
                case ACCOUNT_ACCOUNT_INFORMATION:
                    // Get the associated account types
                    return loggedMemberGroup.getAccountTypes();
                case GUARANTEES_ISSUE_GUARANTEES:
                    // Guarantee types
                    return loggedMemberGroup.getGuaranteeTypes();
                default:
                    throw new IllegalArgumentException(String.format("Unsupported list permission: %1$s.%2$s", permission.getClass().getSimpleName(), permission));
            }
        }
    }

    void load(final Group group) {
        this.group = fetchService.fetch(group, FETCH);
        possibleValuesMap = new HashMap<Permission, Set<EntityVO>>();

        CommonDataBean commonData = null;
        if (group.getNature() != Group.Nature.OPERATOR) { // for operator it's not necessary to load the acc types
            commonData = new CommonDataBean(getSystemAccountTypes(), getMemberAccountTypes());
        }

        Map<SharedValue, Set<EntityVO>> loaded = new HashMap<SharedValue, Set<EntityVO>>();
        for (Permission permission : PermissionHelper.getMultivaluedPermissions(group.getNature())) {
            SharedValue shared = permissionSharedValueMap.get(permission);
            Set<EntityVO> values = loaded.get(shared);
            if (values == null) {
                Collection<? extends Entity> possibleValues;
                Class<?> clazz = permission.getClass();
                if (clazz == BrokerPermission.class) {
                    possibleValues = load(commonData, (BrokerPermission) permission);
                } else if (clazz == MemberPermission.class) {
                    possibleValues = load(commonData, (MemberPermission) permission);
                } else if (clazz == OperatorPermission.class) {
                    possibleValues = load((OperatorPermission) permission);
                } else if (clazz == AdminAdminPermission.class) {
                    possibleValues = load(commonData, (AdminAdminPermission) permission);
                } else if (clazz == AdminMemberPermission.class) {
                    possibleValues = load(commonData, (AdminMemberPermission) permission);
                } else if (clazz == AdminSystemPermission.class) {
                    possibleValues = load(commonData, (AdminSystemPermission) permission);
                } else {
                    throw new IllegalArgumentException("Unknown permission class: " + clazz);
                }
                values = EntityHelper.toEntityVO(possibleValues);
                if (shared != null) { // only add to loaded if it's a sharing values permission
                    loaded.put(shared, values);
                }
            }
            possibleValuesMap.put(permission, values);
        }
    }
}
