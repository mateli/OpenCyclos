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
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nl.strohalm.cyclos.access.AdminMemberPermission;
import nl.strohalm.cyclos.access.AdminSystemPermission;
import nl.strohalm.cyclos.access.BrokerPermission;
import nl.strohalm.cyclos.access.MemberPermission;
import nl.strohalm.cyclos.access.OperatorPermission;
import nl.strohalm.cyclos.dao.accounts.AccountDAO;
import nl.strohalm.cyclos.dao.accounts.AccountLimitLogDAO;
import nl.strohalm.cyclos.dao.accounts.AmountReservationDAO;
import nl.strohalm.cyclos.dao.accounts.ClosedAccountBalanceDAO;
import nl.strohalm.cyclos.dao.accounts.transactions.TransferDAO;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.Account;
import nl.strohalm.cyclos.entities.accounts.AccountLimitLog;
import nl.strohalm.cyclos.entities.accounts.AccountOwner;
import nl.strohalm.cyclos.entities.accounts.AccountQuery;
import nl.strohalm.cyclos.entities.accounts.AccountStatus;
import nl.strohalm.cyclos.entities.accounts.AccountType;
import nl.strohalm.cyclos.entities.accounts.AmountReservation;
import nl.strohalm.cyclos.entities.accounts.ClosedAccountBalance;
import nl.strohalm.cyclos.entities.accounts.InstallmentAmountReservation;
import nl.strohalm.cyclos.entities.accounts.MemberAccount;
import nl.strohalm.cyclos.entities.accounts.MemberAccountType;
import nl.strohalm.cyclos.entities.accounts.MemberGroupAccountSettings;
import nl.strohalm.cyclos.entities.accounts.PendingAuthorizationAmountReservation;
import nl.strohalm.cyclos.entities.accounts.ScheduledPaymentAmountReservation;
import nl.strohalm.cyclos.entities.accounts.SystemAccount;
import nl.strohalm.cyclos.entities.accounts.SystemAccountOwner;
import nl.strohalm.cyclos.entities.accounts.SystemAccountType;
import nl.strohalm.cyclos.entities.accounts.TransferAuthorizationAmountReservation;
import nl.strohalm.cyclos.entities.accounts.transactions.PaymentFilter;
import nl.strohalm.cyclos.entities.accounts.transactions.ScheduledPayment;
import nl.strohalm.cyclos.entities.accounts.transactions.Transfer;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferAuthorization;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferType;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.entities.groups.AdminGroup;
import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.members.Administrator;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.members.MemberQuery;
import nl.strohalm.cyclos.entities.members.MemberTransactionDetailsReportData;
import nl.strohalm.cyclos.entities.members.MemberTransactionSummaryReportData;
import nl.strohalm.cyclos.entities.members.MemberTransactionSummaryVO;
import nl.strohalm.cyclos.entities.members.MembersTransactionsReportParameters;
import nl.strohalm.cyclos.entities.settings.LocalSettings.MemberResultDisplay;
import nl.strohalm.cyclos.services.accountfees.AccountFeeServiceLocal;
import nl.strohalm.cyclos.services.accounts.CreditLimitDTO.Entry;
import nl.strohalm.cyclos.services.accounts.rates.RateServiceLocal;
import nl.strohalm.cyclos.services.accounts.rates.RatesDTO;
import nl.strohalm.cyclos.services.accounts.rates.RatesResultDTO;
import nl.strohalm.cyclos.services.elements.ElementServiceLocal;
import nl.strohalm.cyclos.services.fetch.FetchServiceLocal;
import nl.strohalm.cyclos.services.groups.GroupServiceLocal;
import nl.strohalm.cyclos.services.permissions.PermissionServiceLocal;
import nl.strohalm.cyclos.services.settings.SettingsServiceLocal;
import nl.strohalm.cyclos.services.transactions.TransactionSummaryVO;
import nl.strohalm.cyclos.utils.CacheCleaner;
import nl.strohalm.cyclos.utils.CombinedIterator;
import nl.strohalm.cyclos.utils.DataIteratorHelper;
import nl.strohalm.cyclos.utils.DateHelper;
import nl.strohalm.cyclos.utils.Period;
import nl.strohalm.cyclos.utils.TransactionHelper;
import nl.strohalm.cyclos.utils.access.LoggedUser;
import nl.strohalm.cyclos.utils.query.IteratorList;
import nl.strohalm.cyclos.utils.query.QueryParameters.ResultType;
import nl.strohalm.cyclos.utils.validation.ValidationException;
import nl.strohalm.cyclos.webservices.model.AccountStatusVO;
import nl.strohalm.cyclos.webservices.model.MemberAccountVO;
import nl.strohalm.cyclos.webservices.utils.AccountHelper;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.IteratorUtils;
import org.apache.commons.lang.ObjectUtils;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;

/**
 * Account service implementation
 * @author luis
 */
public class AccountServiceImpl implements AccountServiceLocal {

    /**
     * A combined iterator which iterates members and the combination of payment filters x debits x credits
     * 
     * @author luis
     */
    private class MembersTransactionsSummaryIterator extends CombinedIterator<MemberTransactionSummaryReportData, Member, MemberTransactionSummaryVO, TransactionSummaryReportKey> {
        private final MembersTransactionsReportParameters params;
        private final List<Boolean>                       creditOrDebitToQuery;

        private MembersTransactionsSummaryIterator(final Iterator<Member> masterIterator, final MembersTransactionsReportParameters params) {
            super(masterIterator);
            this.params = params;

            // Check whether to get credits / debits
            creditOrDebitToQuery = new ArrayList<Boolean>();
            if (params.isCredits()) {
                creditOrDebitToQuery.add(true);
            }
            if (params.isDebits()) {
                creditOrDebitToQuery.add(false);
            }
        }

        @Override
        protected boolean belongsToMasterElement(final Member member, final TransactionSummaryReportKey key, final MemberTransactionSummaryVO vo) {
            return vo.getMemberId().equals(member.getId());
        }

        @Override
        protected MemberTransactionSummaryReportData combine(final Member member, final Map<TransactionSummaryReportKey, MemberTransactionSummaryVO> elements) {
            final MemberTransactionSummaryReportData data = new MemberTransactionSummaryReportData();
            data.setMember(member);
            for (final Map.Entry<TransactionSummaryReportKey, MemberTransactionSummaryVO> entry : elements.entrySet()) {
                final TransactionSummaryReportKey key = entry.getKey();
                final MemberTransactionSummaryVO transactions = entry.getValue();
                if (key.credits) {
                    data.addCredits(key.paymentFilter, transactions);
                } else {
                    data.addDebits(key.paymentFilter, transactions);
                }
            }
            return data;
        }

        @Override
        protected void registerInnerIterators() {
            final Collection<PaymentFilter> paymentFilters = params.getPaymentFilters();
            final MemberResultDisplay memberDisplay = settingsService.getLocalSettings().getMemberResultDisplay();
            for (final PaymentFilter paymentFilter : paymentFilters) {
                for (final Boolean isCredit : creditOrDebitToQuery) {
                    final Iterator<MemberTransactionSummaryVO> iterator = accountDao.membersTransactionSummaryReport(params.getMemberGroups(), paymentFilter, params.getPeriod(), isCredit, memberDisplay);
                    final TransactionSummaryReportKey key = new TransactionSummaryReportKey(paymentFilter, isCredit);
                    registerInnerIterator(key, iterator);
                }
            }
        }
    }

    private static class TransactionSummaryReportKey {
        private final PaymentFilter paymentFilter;
        private final boolean       credits;

        public TransactionSummaryReportKey(final PaymentFilter paymentFilter, final boolean credits) {
            this.paymentFilter = paymentFilter;
            this.credits = credits;
        }

        @Override
        public boolean equals(final Object obj) {
            if (obj == null) {
                return false;
            }
            final TransactionSummaryReportKey other = (TransactionSummaryReportKey) obj;
            return ObjectUtils.equals(paymentFilter, other.paymentFilter) && credits == other.credits;
        }

        @Override
        public int hashCode() {
            return paymentFilter.hashCode() * (credits ? 1 : -1);
        }
    }

    private static final float      PRECISION_DELTA  = 0.0001F;
    private static final int        CLOSE_BATCH_SIZE = 30;

    private AccountDAO              accountDao;
    private ClosedAccountBalanceDAO closedAccountBalanceDao;
    private TransferDAO             transferDao;
    private AmountReservationDAO    amountReservationDao;
    private AccountLimitLogDAO      accountLimitLogDao;
    private SettingsServiceLocal    settingsService;
    private FetchServiceLocal       fetchService;
    private AccountTypeServiceLocal accountTypeService;
    private RateServiceLocal        rateService;
    private GroupServiceLocal       groupService;
    private ElementServiceLocal     elementService;
    private PermissionServiceLocal  permissionService;
    private AccountFeeServiceLocal  accountFeeService;
    private TransactionHelper       transactionHelper;
    private AccountHelper           accountHelper;

    @Override
    public boolean canView(final Account account) {
        if (LoggedUser.isSystem()) {
            return true;
        }
        if (account instanceof SystemAccount) {
            if (LoggedUser.isAdministrator()) {
                AdminGroup adminGroup = LoggedUser.group();
                Collection<SystemAccountType> visibleTypes = fetchService.fetch(adminGroup, AdminGroup.Relationships.VIEW_INFORMATION_OF).getViewInformationOf();
                return visibleTypes.contains(account.getType());
            }
            return false;
        } else {
            // As there is currently no specific check for individual member accounts, just check by owner
            return canViewAccountsOf(account.getOwner());
        }
    }

    @Override
    public boolean canViewAccountsOf(final AccountOwner owner) {
        if (LoggedUser.isSystem()) {
            return true;
        }
        if (owner instanceof SystemAccountOwner) {
            // Not an specific account - just test the permission
            return permissionService.hasPermission(AdminSystemPermission.ACCOUNTS_INFORMATION);
        } else {
            return permissionService.permission((Member) owner)
                    .admin(AdminMemberPermission.ACCOUNTS_INFORMATION)
                    .broker(BrokerPermission.ACCOUNTS_INFORMATION)
                    .member()
                    .operator(OperatorPermission.ACCOUNT_ACCOUNT_INFORMATION)
                    .hasPermission();
        }
    }

    @Override
    public boolean canViewAuthorizedInformation(final AccountOwner owner) {
        if (owner instanceof SystemAccountOwner) {
            return permissionService.permission()
                    .admin(AdminSystemPermission.ACCOUNTS_AUTHORIZED_INFORMATION)
                    .hasPermission();
        } else {
            return permissionService.permission((Member) owner)
                    .admin(AdminMemberPermission.ACCOUNTS_AUTHORIZED_INFORMATION)
                    .broker(BrokerPermission.ACCOUNTS_AUTHORIZED_INFORMATION)
                    .member(MemberPermission.ACCOUNT_AUTHORIZED_INFORMATION)
                    .operator(OperatorPermission.ACCOUNT_AUTHORIZED_INFORMATION)
                    .hasPermission();
        }
    }

    @Override
    public void closeBalances(final Calendar time) {
        final Calendar day = DateHelper.truncate(time);

        // Process each batch in a new transaction
        final boolean[] hasMore = new boolean[1];
        hasMore[0] = true;
        while (hasMore[0]) {
            transactionHelper.runInCurrentThread(new TransactionCallbackWithoutResult() {
                @Override
                protected void doInTransactionWithoutResult(final TransactionStatus txStatus) {
                    CacheCleaner cacheCleaner = new CacheCleaner(fetchService);
                    IteratorList<Account> accounts = accountDao.iterateUnclosedAccounts(day, CLOSE_BATCH_SIZE);
                    hasMore[0] = accounts.hasNext();
                    try {
                        for (Account account : accounts) {
                            closeBalance(account, day);
                            // Clear the cache to avoid having too much objects in memory
                            cacheCleaner.clearCache();
                        }
                    } finally {
                        DataIteratorHelper.close(accounts);
                    }
                }
            });
        }
    }

    @Override
    public int countPendingActivation(final MemberGroup group, final MemberAccountType accountType) {
        return accountDao.countAccounts(group, accountType, MemberAccount.Action.ACTIVATE);
    }

    @Override
    public Account getAccount(final AccountDTO params, final Relationship... fetch) {
        // We might receive an account itself, or the owner / type parameters
        Account account = params.getAccount();
        AccountOwner owner;
        AccountType type;
        if (account != null && account.isPersistent()) {
            account = accountDao.load(account.getId(), fetch);
            owner = account.getOwner();
            type = account.getType();
        } else {
            owner = params.getOwner();
            type = params.getType();
        }

        // FIXME this is security logic, and no longer needs to be done here
        if (LoggedUser.hasUser() && LoggedUser.isAdministrator() && (owner instanceof SystemAccountOwner)) {
            // For administrator viewing system accounts, ensure return only the types he can view information about
            AdminGroup group = LoggedUser.group();
            group = fetchService.fetch(group, AdminGroup.Relationships.VIEW_INFORMATION_OF);
            for (final SystemAccountType current : group.getViewInformationOf()) {
                if (current.equals(type)) {
                    return fetchService.fetch(current.getAccount(), fetch);
                }
            }
            throw new EntityNotFoundException(SystemAccount.class);
        }
        account = account == null ? accountDao.load(owner, type, fetch) : account;

        // Update the account on the param, so on a next attempt to reuse the same param, the account is already set
        params.setAccount(account);
        return account;
    }

    @Override
    public List<? extends Account> getAccounts(final AccountOwner owner, final Relationship... fetch) {
        return getAccounts(owner, false, fetch);
    }

    /**
     * gets a Set with accounts belonging to the allowedTTs AND to the member
     * @param member the members whose accounts are checked on this
     * @param allowedTTs the transfer types to be checked
     * @param direction a TransferType.Direction enum.
     * <ul>
     * <li>If FROM, only accounts from which the checked transfer types come from are included.
     * <li>If TO, only accounts to which the checked transfer types go are included.
     * <li>If BOTH, both from and to accounts of the transfer types are included.
     * @return a Set with accounts belonging to the member, and containing the transfer types in allowedTTs.
     */
    @Override
    @SuppressWarnings("unchecked")
    public Set<? extends Account> getAccountsFromTTs(final Member member, final Collection<TransferType> allowedTTs, final TransferType.Direction direction) {
        final Set<MemberAccount> allowedAccounts = new HashSet<MemberAccount>(allowedTTs.size());
        final List<MemberAccount> accounts = (List<MemberAccount>) getAccounts(member);
        for (final TransferType currentTT : allowedTTs) {
            for (final MemberAccount currentAccount : accounts) {
                if (direction.equals(TransferType.Direction.BOTH)) {
                    if (currentAccount.getType().equals(currentTT.getFrom()) || (currentAccount.getType().equals(currentTT.getTo()))) {
                        allowedAccounts.add(currentAccount);
                    }
                } else if (direction.equals(TransferType.Direction.FROM) && currentAccount.getType().equals(currentTT.getFrom())) {
                    allowedAccounts.add(currentAccount);
                } else if (direction.equals(TransferType.Direction.TO) && currentAccount.getType().equals(currentTT.getTo())) {
                    allowedAccounts.add(currentAccount);
                }
            }
        }
        return allowedAccounts;
    }

    @Override
    public BigDecimal getBalance(final AccountDateDTO params) {
        Account account = getAccount(params);
        Calendar date = params.getDate();
        if (date == null) {
            date = Calendar.getInstance();
        }

        // Get the last closed balance before the given date
        ClosedAccountBalance closedBalance = closedAccountBalanceDao.get(account, date);
        BigDecimal balance = closedBalance == null ? BigDecimal.ZERO : closedBalance.getBalance();

        Calendar beginDate = (closedBalance == null) ? null : closedBalance.getDate();
        Period balanceDiffPeriod = Period.between(beginDate, date).useTime();
        BigDecimal diff = transferDao.balanceDiff(account, balanceDiffPeriod);
        balance = balance.add(diff);
        return balance;
    }

    @Override
    public BigDecimal getBalanceAtTimePoint(final Account account, final Calendar date, final boolean inclusive, final boolean compensateChargebacks) {
        AccountDateDTO param = new AccountDateDTO(account, date);
        BigDecimal balance = (inclusive) ? getBalance(param) : getExclusiveBalance(param);
        if (compensateChargebacks) {
            Period period = Period.endingAt(date).useTime();
            period.setInclusiveEnd(inclusive);
            BigDecimal chargebackBalance = transferDao.getChargebackBalance(account, period);
            balance = balance.add(chargebackBalance);
        }
        return balance;
    }

    @Override
    public BigDecimal getBalanceAtTransfer(final Account account, final Transfer transfer, final boolean compensateChargebacks, final boolean inclusive) {
        if (transfer.getProcessDate() == null) {
            throw new IllegalArgumentException("transfer must be processed.");
        }
        // Get the last closed balance before the given date
        ClosedAccountBalance closedBalance = closedAccountBalanceDao.get(account, transfer.getProcessDate());
        BigDecimal balance = closedBalance == null ? BigDecimal.ZERO : closedBalance.getBalance();

        Period diffPeriod = Period.begginingAt((closedBalance == null) ? null : closedBalance.getDate());
        diffPeriod.setInclusiveEnd(inclusive);
        BigDecimal diff = transferDao.balanceDiff(account, diffPeriod, transfer);
        balance = balance.add(diff);
        if (compensateChargebacks) {
            BigDecimal chargebackBalance = transferDao.getChargebackBalance(account, transfer, inclusive);
            balance = balance.add(chargebackBalance);
        }
        return balance;
    }

    @Override
    public TransactionSummaryVO getBrokerCommissions(final GetTransactionsDTO params) {
        return accountDao.getBrokerCommissions(params);
    }

    @Override
    public BigDecimal getCreditLimit(final AccountDTO params) {
        final Account account = getAccount(params);
        return account.getCreditLimit();
    }

    @Override
    public CreditLimitDTO getCreditLimits(final Member owner) {
        final Map<AccountType, BigDecimal> limits = new HashMap<AccountType, BigDecimal>();
        final Map<AccountType, BigDecimal> upperLimits = new HashMap<AccountType, BigDecimal>();
        final List<? extends Account> accts = getAccounts(owner, true);
        for (final Account acct : accts) {
            final AccountType type = acct.getType();
            limits.put(type, acct.getCreditLimit());
            upperLimits.put(type, acct.getUpperCreditLimit());
        }
        final CreditLimitDTO dto = new CreditLimitDTO();
        dto.setLimitPerType(limits);
        dto.setUpperLimitPerType(upperLimits);
        return dto;
    }

    @Override
    public TransactionSummaryVO getCredits(final GetTransactionsDTO params) {
        return accountDao.getCredits(params);
    }

    @Override
    public AccountStatusVO getCurrentAccountStatusVO(final AccountDTO accountDTO) {
        AccountStatus accountStatus = getCurrentStatus(accountDTO);
        return accountHelper.toVO(accountStatus);
    }

    @Override
    public AccountStatus getCurrentStatus(final AccountDTO params) {
        final Account account = getAccount(params);
        AccountStatus status = getStatus(account, null);

        // Member accounts could also have reserved amounts for volume account fees
        if (account instanceof MemberAccount) {
            BigDecimal diff = accountFeeService.calculateReservedAmountForVolumeFee((MemberAccount) account);
            status.setReservedAmount(status.getReservedAmount().add(diff));
        }
        return status;
    }

    @Override
    public TransactionSummaryVO getDebits(final GetTransactionsDTO params) {
        return accountDao.getDebits(params);
    }

    @Override
    public MemberAccount getDefaultAccount() {
        MemberAccount account = null;
        if (LoggedUser.hasUser()) {
            MemberGroup group = LoggedUser.group();
            MemberAccountType defaultType = accountTypeService.getDefault(group);
            if (defaultType != null) {
                account = (MemberAccount) getAccount(new AccountDTO(LoggedUser.accountOwner(), defaultType));
            }
        }
        if (account == null) {
            throw new EntityNotFoundException(Account.class);
        }
        return account;
    }

    @Override
    public Account getDefaultAccountFromList(Member member, final List<Account> allowedAccounts) {
        member = fetchService.fetch(member, Element.Relationships.GROUP);
        // check if the default account is amongst this
        final MemberAccountType defaultType = accountTypeService.getDefault(member.getMemberGroup());
        for (final Account currentAccount : allowedAccounts) {
            if (currentAccount.getType().equals(defaultType)) {
                // Found the default account: return the DTO based on it
                return currentAccount;
            }
        }
        // if no default account, just take the first
        if (allowedAccounts.size() > 0) {
            return allowedAccounts.get(0);
        }
        // No accounts: return null
        return null;
    }

    @Override
    public BigDecimal getExclusiveBalance(final AccountDateDTO params) {
        Account account = getAccount(params);
        Calendar date = params.getDate();

        // Get the last closed balance before the given date
        ClosedAccountBalance closedBalance = closedAccountBalanceDao.get(account, date);
        BigDecimal balance = closedBalance == null ? BigDecimal.ZERO : closedBalance.getBalance();

        if (date == null || closedBalance == null || !date.equals(closedBalance.getDate())) {
            Calendar beginDate = (closedBalance == null) ? null : closedBalance.getDate();
            Period balanceDiffPeriod = Period.between(beginDate, date).useTime();
            balanceDiffPeriod.setInclusiveEnd(false);
            BigDecimal diff = transferDao.balanceDiff(account, balanceDiffPeriod);
            balance = balance.add(diff);
        }
        return balance;
    }

    @Override
    public MemberAccountVO getMemberAccountVO(final Long memberAccountId) {
        if (memberAccountId == null) {
            return null;
        }
        MemberAccount memberAccount = load(memberAccountId);
        return accountHelper.toVO(memberAccount);
    }

    @Override
    public AccountStatus getRatedStatus(final Account account, final Calendar date) {
        AccountStatus status = getStatusAt(account, date, false);
        // status may not be null here, so we skip a null check
        RatesDTO dto = new RatesDTO();
        dto.setDate(date);
        dto.setAccount(account);
        dto.setAmount(status.getBalance());
        RatesResultDTO rates = rateService.getRates(dto);
        status.setRates(rates);
        return status;
    }

    @Override
    public AccountStatus getStatus(final Account account, final Calendar date) {
        return getStatusAt(account, date, false);
    }

    @Override
    public Map<PaymentFilter, TransactionSummaryVO> getTransactionsSummary(final Member member, final AccountType accountType, final Period period, final Collection<PaymentFilter> paymentFilters, final boolean credits) {
        final Map<PaymentFilter, TransactionSummaryVO> summary = new HashMap<PaymentFilter, TransactionSummaryVO>();
        final GetTransactionsDTO params = new GetTransactionsDTO();
        params.setOwner(member);
        params.setType(accountType);
        params.setPeriod(period);
        for (final PaymentFilter paymentFilter : paymentFilters) {
            params.setPaymentFilter(paymentFilter);
            TransactionSummaryVO vo;
            if (credits) {
                vo = accountDao.getCredits(params);
            } else {
                vo = accountDao.getDebits(params);
            }
            summary.put(paymentFilter, vo);
        }
        return summary;
    }

    @Override
    public boolean hasAccounts(final Member member) {
        return !getAccounts(member).isEmpty();
    }

    @Override
    public <T extends Account> T load(final Long id, final Relationship... fetch) {
        return accountDao.<T> load(id, fetch);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Iterator<MemberTransactionDetailsReportData> membersTransactionsDetailsReport(final MembersTransactionsReportParameters params) {
        // Ensure the parameters are valid
        if (!isValid(params)) {
            return IteratorUtils.EMPTY_ITERATOR;
        }

        return accountDao.membersTransactionsDetailsReport(params);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Iterator<MemberTransactionSummaryReportData> membersTransactionsSummaryReport(final MembersTransactionsReportParameters params) {

        // Ensure the parameters are valid
        if (!isValid(params)) {
            return IteratorUtils.EMPTY_ITERATOR;
        }

        // Retrieve the members
        final Iterator<Member> membersIterator = resolveMembersForTransactionsReport(params);

        return new MembersTransactionsSummaryIterator(membersIterator, params);
    }

    @Override
    public void removeClosedBalancesAfter(final Account account, final Calendar date) {
        closedAccountBalanceDao.removeClosedBalancesAfter(account, date);
    }

    @Override
    public ScheduledPaymentAmountReservation reserve(final ScheduledPayment scheduledPayment) {
        ScheduledPaymentAmountReservation reservation = new ScheduledPaymentAmountReservation();
        reservation.setDate(Calendar.getInstance());
        reservation.setAccount(scheduledPayment.getFrom());
        reservation.setAmount(scheduledPayment.getAmount());
        reservation.setScheduledPayment(scheduledPayment);
        return insertReservation(reservation);
    }

    @Override
    public PendingAuthorizationAmountReservation reservePending(final Transfer transfer) {
        PendingAuthorizationAmountReservation reservation = new PendingAuthorizationAmountReservation();
        reservation.setDate(Calendar.getInstance());
        reservation.setAccount(transfer.getFrom());
        reservation.setAmount(transfer.getAmount());
        reservation.setTransfer(transfer);
        return insertReservation(reservation);
    }

    @Override
    public TransferAuthorizationAmountReservation returnReservation(final TransferAuthorization authorization, final Transfer transfer) {
        TransferAuthorizationAmountReservation reservation = new TransferAuthorizationAmountReservation();
        reservation.setDate(Calendar.getInstance());
        reservation.setAccount(transfer.getFrom());
        reservation.setAmount(transfer.getAmount().negate());
        reservation.setTransferAuthorization(authorization);
        reservation.setTransfer(transfer);
        return insertReservation(reservation);
    }

    @Override
    public InstallmentAmountReservation returnReservationForInstallment(final Transfer transfer) {
        InstallmentAmountReservation reservation = new InstallmentAmountReservation();
        reservation.setDate(Calendar.getInstance());
        reservation.setAccount(transfer.getFrom());
        reservation.setAmount(transfer.getAmount().negate());
        reservation.setTransfer(transfer);
        return insertReservation(reservation);
    }

    public void setAccountDao(final AccountDAO accountDao) {
        this.accountDao = accountDao;
    }

    public void setAccountFeeServiceLocal(final AccountFeeServiceLocal accountFeeService) {
        this.accountFeeService = accountFeeService;
    }

    public void setAccountHelper(final AccountHelper accountHelper) {
        this.accountHelper = accountHelper;
    }

    public void setAccountLimitLogDao(final AccountLimitLogDAO accountLimitLogDao) {
        this.accountLimitLogDao = accountLimitLogDao;
    }

    public void setAccountTypeServiceLocal(final AccountTypeServiceLocal accountTypeService) {
        this.accountTypeService = accountTypeService;
    }

    public void setAmountReservationDao(final AmountReservationDAO amountReservationDao) {
        this.amountReservationDao = amountReservationDao;
    }

    public void setClosedAccountBalanceDao(final ClosedAccountBalanceDAO closedAccountBalanceDao) {
        this.closedAccountBalanceDao = closedAccountBalanceDao;
    }

    @Override
    public void setCreditLimit(final Member owner, final CreditLimitDTO limits) {
        validate(owner, limits);

        Map<? extends AccountType, BigDecimal> limitPerType = limits.getLimitPerType();
        final Map<AccountType, BigDecimal> newLimitPerType = new HashMap<AccountType, BigDecimal>();
        if (limitPerType != null) {
            for (AccountType accountType : limitPerType.keySet()) {
                final BigDecimal limit = limitPerType.get(accountType);
                accountType = fetchService.fetch(accountType);
                newLimitPerType.put(accountType, limit);
            }
        }
        limitPerType = newLimitPerType;
        limits.setLimitPerType(limitPerType);

        Map<? extends AccountType, BigDecimal> upperLimitPerType = limits.getUpperLimitPerType();
        final Map<AccountType, BigDecimal> newUpperLimitPerType = new HashMap<AccountType, BigDecimal>();
        if (upperLimitPerType != null) {
            for (AccountType accountType : upperLimitPerType.keySet()) {
                final BigDecimal limit = upperLimitPerType.get(accountType);
                accountType = fetchService.fetch(accountType);
                newUpperLimitPerType.put(accountType, limit);
            }
        }
        upperLimitPerType = newUpperLimitPerType;
        limits.setUpperLimitPerType(upperLimitPerType);

        final List<Entry> entries = limits.getEntries();
        for (final Entry entry : entries) {
            final AccountType type = entry.getAccountType();
            final BigDecimal limit = entry.getCreditLimit();
            final BigDecimal upperLimit = entry.getUpperCreditLimit();
            if (limit == null && upperLimit == null) {
                continue;
            }
            List<? extends Account> accts;
            if (owner == null) {
                accts = getAccounts(type);
            } else {
                accts = Arrays.asList(getAccount(new AccountDTO(owner, type)));
            }
            for (Account account : accts) {

                boolean limitHasChanged = false;

                if (limit != null && !account.getCreditLimit().equals(limit.abs())) {
                    account.setCreditLimit(limit.abs());
                    limitHasChanged = true;
                }
                if (upperLimit != null && (account.getUpperCreditLimit() == null || !account.getUpperCreditLimit().equals(upperLimit.abs()))) {
                    account.setUpperCreditLimit(upperLimit.abs());
                    limitHasChanged = true;
                }

                if (limitHasChanged) {
                    // Update the account
                    account = accountDao.update(account);

                    // Generate the log
                    AccountLimitLog log = new AccountLimitLog();
                    log.setAccount(account);
                    log.setBy((Administrator) LoggedUser.element());
                    log.setDate(Calendar.getInstance());
                    log.setCreditLimit(limit);
                    log.setUpperCreditLimit(upperLimit);
                    accountLimitLogDao.insert(log);
                }
            }
        }
    }

    public void setElementServiceLocal(final ElementServiceLocal elementService) {
        this.elementService = elementService;
    }

    public void setFetchServiceLocal(final FetchServiceLocal fetchService) {
        this.fetchService = fetchService;
    }

    public void setGroupServiceLocal(final GroupServiceLocal groupService) {
        this.groupService = groupService;
    }

    public void setPermissionServiceLocal(final PermissionServiceLocal permissionService) {
        this.permissionService = permissionService;
    }

    public void setRateServiceLocal(final RateServiceLocal rateService) {
        this.rateService = rateService;
    }

    public void setSettingsServiceLocal(final SettingsServiceLocal settingsService) {
        this.settingsService = settingsService;
    }

    public void setTransactionHelper(final TransactionHelper transactionHelper) {
        this.transactionHelper = transactionHelper;
    }

    public void setTransferDao(final TransferDAO transferDao) {
        this.transferDao = transferDao;
    }

    @Override
    public void validate(Member member, final CreditLimitDTO creditLimit) {
        // Fetch the member
        try {
            member = fetchService.fetch(member);
        } catch (final Exception e) {
            throw new ValidationException();
        }

        // Retrieve all given account types
        final Map<? extends AccountType, BigDecimal> limitPerType = creditLimit.getLimitPerType();
        final Map<? extends AccountType, BigDecimal> upperLimitPerType = creditLimit.getUpperLimitPerType();
        final Set<AccountType> accountTypes = new HashSet<AccountType>();
        if (limitPerType != null) {
            for (final AccountType at : limitPerType.keySet()) {
                accountTypes.add(at);
            }
        }
        if (upperLimitPerType != null) {
            for (final AccountType at : upperLimitPerType.keySet()) {
                accountTypes.add(at);
            }
        }

        // Check if the member has all account types
        for (final AccountType type : accountTypes) {
            try {
                getAccount(new AccountDTO(member, type));
            } catch (final EntityNotFoundException e) {
                throw new ValidationException();
            }
        }
    }

    private void closeBalance(final Account account, final Calendar day) {
        AccountStatus status = getStatusAt(account, day, true);
        if (status != null) {
            // Insert a new closed balance
            ClosedAccountBalance closedBalance = new ClosedAccountBalance();
            closedBalance.setDate(day);
            closedBalance.setAccount(account);
            closedBalance.setBalance(status.getBalance());
            closedBalance.setReserved(status.getReservedAmount());
            closedAccountBalanceDao.insert(closedBalance);
        }

        // Update the last closing date
        account.setLastClosingDate(day);
    }

    private List<? extends Account> getAccounts(final AccountOwner owner, final boolean forceAllAccounts, final Relationship... fetch) {
        final AccountQuery query = new AccountQuery();
        query.setOwner(owner);
        query.fetch(fetch);
        List<? extends Account> accounts = accountDao.search(query);
        if (forceAllAccounts) {
            return accounts;
        } else if (owner instanceof Member) {
            accounts = new ArrayList<Account>(accounts);
            final Member member = fetchService.fetch((Member) owner);
            for (final Iterator<? extends Account> iterator = accounts.iterator(); iterator.hasNext();) {
                final Account account = iterator.next();
                MemberGroupAccountSettings accountSettings;
                boolean remove = false;
                final Group group = member.getGroup();
                if (group.getStatus() == Group.Status.NORMAL) {
                    try {
                        accountSettings = groupService.loadAccountSettings(group.getId(), account.getType().getId());
                    } catch (final EntityNotFoundException e) {
                        accountSettings = null;
                        remove = true;
                    }
                } else {
                    // Removed group
                    accountSettings = null;
                }
                // Check whether the account is hidden
                if (accountSettings != null && accountSettings.isHideWhenNoCreditLimit()) {
                    // Hide the account: it should be visible only when has credit limit, and credit limit is zero or there are at least one transfer
                    final boolean hasCreditLimit = Math.abs(account.getCreditLimit().floatValue()) > PRECISION_DELTA;
                    if (!hasCreditLimit && !transferDao.hasTransfers(account)) {
                        remove = true;
                    }
                }
                if (remove) {
                    iterator.remove();
                }
            }
        }
        return accounts;
    }

    private List<? extends Account> getAccounts(final AccountType type) {
        final AccountQuery query = new AccountQuery();
        query.setType(type);
        return accountDao.search(query);
    }

    private AccountStatus getStatusAt(final Account account, final Calendar date, final boolean onlyIfThereAreDiffs) {
        // Fill the status with basic data
        AccountStatus status = new AccountStatus();
        status.setAccount(account);
        status.setCreditLimit(account.getCreditLimit());
        status.setUpperCreditLimit(account.getUpperCreditLimit());
        status.setDate(date);

        // Get the last closed balance
        ClosedAccountBalance closedBalance = closedAccountBalanceDao.get(account, date);
        Calendar closedDate = closedBalance == null ? null : closedBalance.getDate();
        Calendar endDate = (Calendar) (date == null ? null : date.clone());
        if (endDate != null) {
            endDate.add(Calendar.SECOND, -1);
        }
        Period period = Period.between(closedDate, endDate);

        // Get the balance diff
        BigDecimal balanceDiff = transferDao.balanceDiff(account, period);
        status.setBalance(closedBalance == null ? balanceDiff : closedBalance.getBalance().add(balanceDiff));

        // Get the reserved amount diff
        BigDecimal reservationDiff = amountReservationDao.reservationDiff(account, period);
        status.setReservedAmount(closedBalance == null ? reservationDiff : closedBalance.getReserved().add(reservationDiff));

        if (onlyIfThereAreDiffs && balanceDiff.equals(BigDecimal.ZERO) && reservationDiff.equals(BigDecimal.ZERO)) {
            // If should return only if there are diffs, and there were none, return null
            return null;
        }

        return status;

    }

    private <R extends AmountReservation> R insertReservation(R reservation) {
        reservation = amountReservationDao.insert(reservation);
        // Make sure there are no closed balances on the future
        removeClosedBalancesAfter(reservation.getAccount(), reservation.getDate());
        return reservation;
    }

    private boolean isValid(final MembersTransactionsReportParameters params) {
        final Collection<MemberGroup> memberGroups = params.getMemberGroups();
        if (CollectionUtils.isEmpty(memberGroups)) {
            return false;
        }
        if (!params.isDebits() && !params.isCredits()) {
            return false;
        }
        return true;
    }

    @SuppressWarnings("unchecked")
    private Iterator<Member> resolveMembersForTransactionsReport(final MembersTransactionsReportParameters params) {
        final Collection<MemberGroup> groups = params.getMemberGroups();
        final Period period = params.getPeriod();
        final MemberQuery query = new MemberQuery();
        query.setPageParameters(params.getPageParameters());
        if (params.isFetchBroker()) {
            query.fetch(Member.Relationships.BROKER);
        }
        query.setGroups(groups);
        if (period != null && period.getEnd() != null) {
            query.setActivationPeriod(Period.endingAt(period.getEnd()));
        }
        query.setResultType(ResultType.ITERATOR);
        final List<Member> members = (List<Member>) elementService.search(query);
        final Iterator<Member> membersIterator = members.iterator();
        return membersIterator;
    }

}
