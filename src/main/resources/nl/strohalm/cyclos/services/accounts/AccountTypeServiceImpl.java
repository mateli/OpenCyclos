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
package nl.strohalm.cyclos.services.accounts;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import nl.strohalm.cyclos.access.AdminSystemPermission;
import nl.strohalm.cyclos.dao.accounts.AccountDAO;
import nl.strohalm.cyclos.dao.accounts.AccountLimitLogDAO;
import nl.strohalm.cyclos.dao.accounts.AccountTypeDAO;
import nl.strohalm.cyclos.dao.groups.GroupDAO;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.access.Channel;
import nl.strohalm.cyclos.entities.accounts.AccountLimitLog;
import nl.strohalm.cyclos.entities.accounts.AccountOwner;
import nl.strohalm.cyclos.entities.accounts.AccountType;
import nl.strohalm.cyclos.entities.accounts.AccountTypeQuery;
import nl.strohalm.cyclos.entities.accounts.MemberAccountType;
import nl.strohalm.cyclos.entities.accounts.MemberGroupAccountSettings;
import nl.strohalm.cyclos.entities.accounts.SystemAccount;
import nl.strohalm.cyclos.entities.accounts.SystemAccountOwner;
import nl.strohalm.cyclos.entities.accounts.SystemAccountType;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferType;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferTypeQuery;
import nl.strohalm.cyclos.entities.groups.AdminGroup;
import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.groups.OperatorGroup;
import nl.strohalm.cyclos.entities.members.Administrator;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.services.fetch.FetchServiceLocal;
import nl.strohalm.cyclos.services.permissions.PermissionServiceLocal;
import nl.strohalm.cyclos.services.transactions.TransactionContext;
import nl.strohalm.cyclos.services.transfertypes.TransferTypeServiceLocal;
import nl.strohalm.cyclos.utils.access.LoggedUser;
import nl.strohalm.cyclos.utils.cache.Cache;
import nl.strohalm.cyclos.utils.cache.CacheCallback;
import nl.strohalm.cyclos.utils.cache.CacheManager;
import nl.strohalm.cyclos.utils.query.PageHelper;
import nl.strohalm.cyclos.utils.validation.Validator;

import org.apache.commons.collections.CollectionUtils;

/**
 * Implementation for account type service
 * @author luis
 */
public class AccountTypeServiceImpl implements AccountTypeServiceLocal {

    private static final String      ALL_KEY = "_ALL_";
    private TransferTypeServiceLocal transferTypeService;
    private AccountDAO               accountDao;
    private AccountTypeDAO           accountTypeDao;
    private AccountLimitLogDAO       accountLimitLogDao;
    private GroupDAO                 groupDao;
    private FetchServiceLocal        fetchService;
    private CacheManager             cacheManager;
    private PermissionServiceLocal   permissionService;

    @Override
    public void clearCache() {
        getCache().clear();
    }

    @Override
    public MemberAccountType getDefault(MemberGroup group, final Relationship... fetch) {
        group = fetchService.fetch(group, MemberGroup.Relationships.ACCOUNT_SETTINGS);
        Collection<MemberGroupAccountSettings> accountSettings = group.getAccountSettings();
        MemberGroupAccountSettings defaultAccount = null;
        if (CollectionUtils.isNotEmpty(accountSettings)) {
            accountSettings = fetchService.fetch(accountSettings, MemberGroupAccountSettings.Relationships.ACCOUNT_TYPE);
            for (final MemberGroupAccountSettings current : accountSettings) {
                if (current.isDefault()) {
                    // Found the default account
                    defaultAccount = current;
                    break;
                }
            }
            if (defaultAccount == null) {
                // None found: get the first one
                defaultAccount = accountSettings.iterator().next();
            }
        }
        return defaultAccount == null ? null : fetchService.fetch(defaultAccount.getAccountType(), fetch);
    }

    @Override
    public Map<MemberAccountType, BigDecimal> getMemberAccountTypesBalance(final Collection<MemberAccountType> types, final Collection<MemberGroup> groups, final Calendar timePoint) {
        Map<MemberAccountType, BigDecimal> balances = new TreeMap<MemberAccountType, BigDecimal>();
        for (final MemberAccountType type : types) {
            final BigDecimal balance = accountTypeDao.getBalance(type, groups, timePoint);
            balances.put(fetchService.fetch(type, AccountType.Relationships.CURRENCY), balance);
        }
        return balances;
    }

    @Override
    public Map<SystemAccountType, BigDecimal> getSystemAccountTypesBalance(final Collection<SystemAccountType> types, final Calendar timePoint) {
        Map<SystemAccountType, BigDecimal> balances = new TreeMap<SystemAccountType, BigDecimal>();
        for (final SystemAccountType type : types) {
            final BigDecimal balance = accountTypeDao.getBalance(type, timePoint);
            balances.put(fetchService.fetch(type, AccountType.Relationships.CURRENCY), balance);
        }
        return balances;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Collection<AccountType> getVisibleAccountTypes() {
        if (LoggedUser.isSystem()) {
            return (Collection<AccountType>) listAll();
        }
        if (!LoggedUser.hasUser()) {
            // Not system and no user - nothing is visible
            return Collections.emptyList();
        }
        final Group group = LoggedUser.group();
        return getCache().get("_VISIBLE_" + group.getId(), new CacheCallback() {
            @Override
            public Object retrieve() {
                Collection<AccountType> result;
                if (permissionService.permission().admin(AdminSystemPermission.ACCOUNTS_VIEW).hasPermission()) {
                    result = (Collection<AccountType>) listAll();
                } else {
                    if (LoggedUser.isOperator()) {
                        OperatorGroup group = LoggedUser.group();
                        result = fetchService.fetch(group, OperatorGroup.Relationships.CAN_VIEW_INFORMATION_OF).getCanViewInformationOf();
                    } else {
                        MemberAccountTypeQuery memberQuery = new MemberAccountTypeQuery();
                        memberQuery.setRelatedToGroups(permissionService.getManagedMemberGroups());
                        result = (Collection<AccountType>) search(memberQuery);

                        // A logged admin can see both system and member account types
                        if (LoggedUser.isAdministrator()) {
                            AdminGroup group = LoggedUser.group();
                            Collection<SystemAccountType> systemTypes = fetchService.fetch(group, AdminGroup.Relationships.VIEW_INFORMATION_OF).getViewInformationOf();
                            result = CollectionUtils.union(result, systemTypes);
                        }
                    }
                }
                return fetchService.fetch(result, AccountType.Relationships.CURRENCY);
            }
        });
    }

    @Override
    public boolean hasAuthorizedPayments(AccountType accountType) {
        accountType = fetchService.fetch(accountType, AccountType.Relationships.FROM_TRANSFER_TYPES);
        for (final TransferType transferType : accountType.getFromTransferTypes()) {
            if (transferType.isRequiresAuthorization()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<? extends AccountType> listAll() {
        return getCache().get(ALL_KEY, new CacheCallback() {
            @Override
            public Object retrieve() {
                return accountTypeDao.listAll();
            }
        });
    }

    @Override
    public Collection<AccountType> load(final Collection<Long> ids) {
        Collection<AccountType> accountTypes = new ArrayList<AccountType>(ids.size());
        for (Long id : ids) {
            accountTypes.add(load(id));
        }
        return accountTypes;
    }

    @Override
    public AccountType load(final Long id) {
        return getCache().get(id, new CacheCallback() {
            @Override
            public Object retrieve() {
                final AccountType accountType = accountTypeDao.load(id, AccountType.Relationships.CURRENCY, SystemAccountType.Relationships.EXTERNAL_ACCOUNTS);
                fillSystemLimits(accountType);
                return accountType;
            }
        });
    }

    @Override
    public int remove(final Long... ids) {
        for (final Long id : ids) {
            final AccountType accountType = accountTypeDao.load(id);
            if (accountType instanceof SystemAccountType) {
                final SystemAccountType systemAccountType = ((SystemAccountType) accountType);
                final SystemAccount account = systemAccountType.getAccount();
                systemAccountType.setAccount(null);
                accountTypeDao.update(systemAccountType);
                accountDao.delete(account.getId());
            }
        }
        getCache().clear();
        return accountTypeDao.delete(ids);
    }

    @Override
    public <AT extends AccountType> AT save(final AT accountType) {
        AT saved = null;
        validate(accountType);
        SystemAccount systemAccount = null;
        if (accountType.isTransient()) {
            saved = accountTypeDao.insert(accountType);
            if (saved instanceof SystemAccountType) {
                // Create the system account now
                final SystemAccountType systemAccountType = ((SystemAccountType) accountType);
                systemAccount = new SystemAccount();
                systemAccount.setCreationDate(Calendar.getInstance());
                systemAccount.setCreditLimit(systemAccountType.getCreditLimit());
                systemAccount.setUpperCreditLimit(systemAccountType.getUpperCreditLimit());
                systemAccount.setType(saved);
                systemAccount.setOwnerName(saved.getName());
                systemAccount = accountDao.insert(systemAccount);

                // Add permission to the admin group
                AdminGroup group = (AdminGroup) LoggedUser.group();
                group = groupDao.load(group.getId(), AdminGroup.Relationships.VIEW_INFORMATION_OF);
                final Collection<SystemAccountType> systemAccountTypes = group.getViewInformationOf();
                systemAccountTypes.add(systemAccountType);
                groupDao.update(group);
            }
            // Member accounts are created when an account type gets related to a group
        } else {
            if (accountType instanceof SystemAccountType) {
                final SystemAccountType currentAccountType = (SystemAccountType) accountTypeDao.load(accountType.getId(), SystemAccountType.Relationships.VIEWED_BY_GROUPS);
                final Collection<AdminGroup> viewedByGroups = new ArrayList<AdminGroup>();
                if (currentAccountType.getViewedByGroups() != null) {
                    viewedByGroups.addAll(currentAccountType.getViewedByGroups());
                }

                final SystemAccountType systemAccountType = (SystemAccountType) accountType;
                systemAccountType.setViewedByGroups(viewedByGroups);

                // When updating a system account type, should update it's account too
                systemAccount = (SystemAccount) accountDao.load(SystemAccountOwner.instance(), systemAccountType);

                final BigDecimal oldLimit = systemAccount.getCreditLimit() == null ? null : systemAccount.getCreditLimit().abs();
                final BigDecimal oldUpperLimit = systemAccount.getUpperCreditLimit();

                // When there was a credit limit, and it has changed, we must update the account
                final BigDecimal newLimit = systemAccountType.getCreditLimit() == null ? null : systemAccountType.getCreditLimit().abs();
                final BigDecimal newUpperLimit = systemAccountType.getUpperCreditLimit();
                final boolean updateLimit = (newLimit != null && !newLimit.equals(oldLimit)) || ((newUpperLimit != null) && !newUpperLimit.equals(oldUpperLimit));
                if (updateLimit) {
                    systemAccount.setCreditLimit(newLimit);
                    systemAccount.setUpperCreditLimit(newUpperLimit);
                }
                systemAccount.setOwnerName(systemAccountType.getName());
                systemAccountType.setAccount(systemAccount);
                accountDao.update(systemAccount);

                if (updateLimit) {
                    // Generate the log
                    AccountLimitLog log = new AccountLimitLog();
                    log.setAccount(systemAccount);
                    log.setBy((Administrator) LoggedUser.element());
                    log.setDate(Calendar.getInstance());
                    log.setCreditLimit(newLimit);
                    log.setUpperCreditLimit(newUpperLimit);
                    accountLimitLogDao.insert(log);
                }
            }
            saved = accountTypeDao.update(accountType);
        }
        if (systemAccount != null) {
            ((SystemAccountType) saved).setAccount(systemAccount);
            saved = accountTypeDao.update(saved);
        }
        getCache().clear();
        return saved;
    }

    @Override
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public List<? extends AccountType> search(final AccountTypeQuery query) {
        if (query instanceof MemberAccountTypeQuery) {
            final MemberAccountTypeQuery memberQuery = (MemberAccountTypeQuery) query;
            final AccountOwner canPay = memberQuery.getCanPay();
            if (canPay != null) {
                Group group = null;
                if (canPay instanceof Member) {
                    final Member member = fetchService.fetch((Member) canPay, Element.Relationships.GROUP);
                    group = member.getGroup();
                }
                Member owner = memberQuery.getOwner();
                if (owner == null && LoggedUser.hasUser() && LoggedUser.isMember()) {
                    owner = LoggedUser.element();
                }
                // Can pay is handled differently: let's reuse the TransferTypeService to check which accounts have possible payment types
                final List<MemberAccountType> accountTypes = new ArrayList<MemberAccountType>();
                // I know: double casts looks awful, but...
                for (final MemberAccountType accountType : (List<MemberAccountType>) (List) accountTypeDao.search(new MemberAccountTypeQuery())) {
                    final TransferTypeQuery transferTypeQuery = new TransferTypeQuery();
                    transferTypeQuery.setPageForCount();
                    transferTypeQuery.setContext(TransactionContext.PAYMENT);
                    transferTypeQuery.setChannel(Channel.WEB);
                    transferTypeQuery.setUsePriority(true);
                    transferTypeQuery.setToAccountType(accountType);
                    transferTypeQuery.setToOwner(owner);
                    transferTypeQuery.setFromOwner(canPay);
                    transferTypeQuery.setGroup(group);
                    if (PageHelper.getTotalCount(transferTypeService.search(transferTypeQuery)) > 0) {
                        accountTypes.add(accountType);
                    }
                }
                return accountTypes;
            }
        }
        return accountTypeDao.search(query);
    }

    public void setAccountDao(final AccountDAO accountDao) {
        this.accountDao = accountDao;
    }

    public void setAccountLimitLogDao(final AccountLimitLogDAO accountLimitLogDao) {
        this.accountLimitLogDao = accountLimitLogDao;
    }

    public void setAccountTypeDao(final AccountTypeDAO accountTypeDao) {
        this.accountTypeDao = accountTypeDao;
    }

    public void setCacheManager(final CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    public void setFetchServiceLocal(final FetchServiceLocal fetchService) {
        this.fetchService = fetchService;
    }

    public void setGroupDao(final GroupDAO groupDao) {
        this.groupDao = groupDao;
    }

    public void setPermissionServiceLocal(final PermissionServiceLocal permissionService) {
        this.permissionService = permissionService;
    }

    public void setTransferTypeServiceLocal(final TransferTypeServiceLocal transferTypeService) {
        this.transferTypeService = transferTypeService;
    }

    @Override
    public void validate(final AccountType accountType) {
        getValidator().validate(accountType);
    }

    private void fillSystemLimits(final AccountType accountType) {
        if (accountType instanceof SystemAccountType) {
            final SystemAccountType sat = (SystemAccountType) accountType;
            final SystemAccount account = sat.getAccount();
            if (account != null) {
                BigDecimal creditLimit = account.getCreditLimit();
                if (creditLimit != null) {
                    sat.setCreditLimit(creditLimit.abs().negate());
                }
                sat.setUpperCreditLimit(account.getUpperCreditLimit());
            }
        }
    }

    private Cache getCache() {
        return cacheManager.getCache("cyclos.AccountTypes");
    }

    private Validator getValidator() {
        final Validator validator = new Validator("accountType");
        validator.property("name").required().maxLength(100);
        validator.property("description").maxLength(1000);
        validator.property("currency").required();
        return validator;
    }
}
