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
package nl.strohalm.cyclos.services.accountfees;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import nl.strohalm.cyclos.dao.accounts.AccountDAO;
import nl.strohalm.cyclos.dao.accounts.AccountDailyDifference;
import nl.strohalm.cyclos.dao.accounts.fee.account.AccountFeeDAO;
import nl.strohalm.cyclos.dao.accounts.fee.account.AccountFeeLogDAO;
import nl.strohalm.cyclos.dao.accounts.fee.account.MemberAccountFeeLogDAO;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.AccountStatus;
import nl.strohalm.cyclos.entities.accounts.AccountType;
import nl.strohalm.cyclos.entities.accounts.MemberAccount;
import nl.strohalm.cyclos.entities.accounts.MemberAccountType;
import nl.strohalm.cyclos.entities.accounts.fees.account.AccountFee;
import nl.strohalm.cyclos.entities.accounts.fees.account.AccountFee.ChargeMode;
import nl.strohalm.cyclos.entities.accounts.fees.account.AccountFee.InvoiceMode;
import nl.strohalm.cyclos.entities.accounts.fees.account.AccountFee.PaymentDirection;
import nl.strohalm.cyclos.entities.accounts.fees.account.AccountFee.RunMode;
import nl.strohalm.cyclos.entities.accounts.fees.account.AccountFeeLog;
import nl.strohalm.cyclos.entities.accounts.fees.account.AccountFeeLogDetailsDTO;
import nl.strohalm.cyclos.entities.accounts.fees.account.AccountFeeLogQuery;
import nl.strohalm.cyclos.entities.accounts.fees.account.AccountFeeQuery;
import nl.strohalm.cyclos.entities.accounts.fees.account.MemberAccountFeeLog;
import nl.strohalm.cyclos.entities.accounts.fees.account.MemberAccountFeeLogQuery;
import nl.strohalm.cyclos.entities.accounts.transactions.Invoice;
import nl.strohalm.cyclos.entities.accounts.transactions.Transfer;
import nl.strohalm.cyclos.entities.exceptions.UnexpectedEntityException;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.scheduling.polling.ChargeAccountFeePollingTask;
import nl.strohalm.cyclos.services.InitializingService;
import nl.strohalm.cyclos.services.accounts.AccountDTO;
import nl.strohalm.cyclos.services.accounts.AccountDateDTO;
import nl.strohalm.cyclos.services.accounts.AccountServiceLocal;
import nl.strohalm.cyclos.services.application.ApplicationServiceLocal;
import nl.strohalm.cyclos.services.fetch.FetchServiceLocal;
import nl.strohalm.cyclos.services.settings.SettingsServiceLocal;
import nl.strohalm.cyclos.services.transactions.PaymentServiceLocal;
import nl.strohalm.cyclos.services.transactions.TransactionSummaryVO;
import nl.strohalm.cyclos.utils.BigDecimalHelper;
import nl.strohalm.cyclos.utils.DataIteratorHelper;
import nl.strohalm.cyclos.utils.DateHelper;
import nl.strohalm.cyclos.utils.FormatObject;
import nl.strohalm.cyclos.utils.Pair;
import nl.strohalm.cyclos.utils.Period;
import nl.strohalm.cyclos.utils.RelationshipHelper;
import nl.strohalm.cyclos.utils.TimePeriod;
import nl.strohalm.cyclos.utils.TimePeriod.Field;
import nl.strohalm.cyclos.utils.cache.Cache;
import nl.strohalm.cyclos.utils.cache.CacheCallback;
import nl.strohalm.cyclos.utils.cache.CacheManager;
import nl.strohalm.cyclos.utils.query.IteratorList;
import nl.strohalm.cyclos.utils.query.PageHelper;
import nl.strohalm.cyclos.utils.query.QueryParameters.ResultType;
import nl.strohalm.cyclos.utils.validation.ValidationException;
import nl.strohalm.cyclos.utils.validation.Validator;
import nl.strohalm.cyclos.utils.validation.Validator.Property;

import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Implementation class for account fee service
 * @author rafael
 * @author luis
 */
public class AccountFeeServiceImpl implements AccountFeeServiceLocal, InitializingService {

    private static int              ACCOUNT_FEE_CHARGE_BATCH_SIZE = 20;

    private static final Log        LOG                           = LogFactory.getLog(AccountFeeServiceImpl.class);
    private AccountFeeDAO           accountFeeDao;
    private AccountFeeLogDAO        accountFeeLogDao;
    private AccountDAO              accountDao;
    private FetchServiceLocal       fetchService;
    private AccountServiceLocal     accountService;
    private MemberAccountFeeLogDAO  memberAccountFeeLogDao;
    private CacheManager            cacheManager;
    private SettingsServiceLocal    settingsService;
    private PaymentServiceLocal     paymentService;
    private ApplicationServiceLocal applicationService;

    @Override
    public BigDecimal calculateAmount(final AccountFeeLog feeLog, final Member member) {
        AccountFee fee = feeLog.getAccountFee();

        if (!fee.getGroups().contains(member.getGroup())) {
            // The member is not affected by this fee log
            return null;
        }

        final Period period = feeLog.getPeriod();
        final MemberAccountType accountType = fee.getAccountType();
        final ChargeMode chargeMode = fee.getChargeMode();
        final BigDecimal freeBase = fee.getFreeBase();

        // Calculate the charge amount
        BigDecimal chargedAmount = BigDecimal.ZERO;
        BigDecimal amount = BigDecimal.ZERO;
        Calendar endDate = (period != null) ? period.getEnd() : null;
        final AccountDateDTO balanceParams = new AccountDateDTO(member, accountType, endDate);
        if (chargeMode.isFixed()) {
            boolean charge = true;
            if (freeBase != null) {
                final BigDecimal balance = accountService.getBalance(balanceParams);
                if (balance.compareTo(freeBase) <= 0) {
                    charge = false;
                }
            }
            // Fixed fee amount
            if (charge) {
                amount = feeLog.getAmount();
            }
        } else if (chargeMode.isBalance()) {
            // Percentage over balance
            final boolean positiveBalance = !chargeMode.isNegative();
            BigDecimal balance = accountService.getBalance(balanceParams);
            // Skip if balance is out of range
            boolean charge = true;
            // Apply the free base
            if (freeBase != null) {
                if (positiveBalance) {
                    balance = balance.subtract(freeBase);
                } else {
                    balance = balance.add(freeBase);
                }
            }
            // Check if something will be charged
            if ((positiveBalance && balance.compareTo(BigDecimal.ZERO) <= 0) || (!positiveBalance && balance.compareTo(BigDecimal.ZERO) >= 0)) {
                charge = false;
            }
            if (charge) {
                // Get the charged amount
                chargedAmount = feeLog.getAmountValue().apply(balance.abs());
                amount = settingsService.getLocalSettings().round(chargedAmount);
            }
        } else if (chargeMode.isVolume()) {
            // Percentage over average transactioned volume
            amount = calculateChargeOverTransactionedVolume(feeLog, member);
        }

        // Ensure the amount is valid
        final BigDecimal minPayment = paymentService.getMinimumPayment();
        if (amount.compareTo(minPayment) < 0) {
            amount = BigDecimal.ZERO;
        }
        return amount;
    }

    @Override
    public BigDecimal calculateReservedAmountForVolumeFee(final MemberAccount account) {
        MemberGroup group = (MemberGroup) fetchService.fetch(account.getMember().getGroup());
        AccountFee volumeFee = getVolumeFee(account.getType(), group);
        if (volumeFee == null) {
            return BigDecimal.ZERO;
        }
        // Get the last period which was charged for this account
        AccountFeeLog lastCharged = memberAccountFeeLogDao.getLastChargedLog(account.getMember(), volumeFee);
        Calendar fromDate;
        if (lastCharged == null || lastCharged.getDate().before(volumeFee.getEnabledSince())) {
            // Never charged, or fee re-enabled after the last charge: consider either the account creation date or the fee enabled since -
            // whatever happened later
            fromDate = account.getCreationDate().after(volumeFee.getEnabledSince()) ? account.getCreationDate() : volumeFee.getEnabledSince();
        } else {
            // Already charged - consider the first day on the next period
            fromDate = lastCharged.getPeriod().getEnd();
        }
        // As we calculate by whole days, make sure we're on the next day, so the balance will be ok
        fromDate = DateHelper.truncateNextDay(fromDate);

        // As the volume is an average of days, if there are previous uncharged periods, we must compute each period separately.
        // For example, if the last charge was 2 months ago (a charge failed), we cannot assume that a single charge over 2 months is the
        // same as 2 charges of 1 month, as we charge over the average. So, in the example, if an account has 100 over all time, and we charge 1%,
        // the average for 1 month is 100, so we charge 1. The next month, is the same, and we charge another 1. If the period would be 2 months,
        // The average over those 2 months is still 100, so a single charge of 1 is done, unlike the previous 2 charges.
        // In most normal cases, the loop should be executed only once. Only when an account fee log has failed, it should be executed more than once.
        TimePeriod recurrence = volumeFee.getRecurrence();
        BigDecimal result = BigDecimal.ZERO;
        Calendar now = Calendar.getInstance();
        boolean done = false;
        if (LOG.isDebugEnabled()) {
            LOG.debug("Getting current status for " + account);
        }
        while (!done) {
            Period period = recurrence.currentPeriod(fromDate);
            if (!period.getEnd().after(now)) {
                // Still a past uncharged period
                period.setBegin(fromDate);
                fromDate = DateHelper.truncateNextDay(period.getEnd());
            } else {
                period = Period.between(fromDate, DateHelper.truncate(now));
                done = true;
            }
            BigDecimal chargeForPeriod = calculateVolumeCharge(account, volumeFee, period, result, done);
            if (LOG.isDebugEnabled()) {
                LOG.debug("Charge for period " + FormatObject.formatObject(period.getBegin()) + "\t" + FormatObject.formatObject(period.getEnd()) + "\t" + chargeForPeriod);
            }
            result = result.add(chargeForPeriod);
        }
        return result;
    }

    @Override
    public void chargeManual(AccountFee fee) {
        // Validates the fee
        if (fee == null || fee.isTransient()) {
            throw new UnexpectedEntityException();
        }
        fee = fetchService.fetch(fee, RelationshipHelper.nested(AccountFee.Relationships.ACCOUNT_TYPE, AccountType.Relationships.CURRENCY), AccountFee.Relationships.TRANSFER_TYPE);
        if (fee.getRunMode() != RunMode.MANUAL) {
            throw new UnexpectedEntityException();
        }

        // Insert the log with the RUNNING status, so it will be charged
        insertNextExecution(fee);

        applicationService.awakePollingTaskOnTransactionCommit(ChargeAccountFeePollingTask.class);
    }

    @Override
    public int chargeScheduledFees(final Calendar time) {
        final AccountFeeQuery query = new AccountFeeQuery();
        query.setReturnDisabled(false);
        query.setResultType(ResultType.LIST);
        query.setHour((byte) time.get(Calendar.HOUR_OF_DAY));
        query.setType(RunMode.SCHEDULED);
        query.fetch(AccountFee.Relationships.LOGS);
        query.setEnabledBefore(time);

        final List<AccountFee> list = new ArrayList<AccountFee>();
        // Get the daily fees
        query.setRecurrence(TimePeriod.Field.DAYS);
        list.addAll(search(query));
        // Get the weekly fees
        query.setRecurrence(TimePeriod.Field.WEEKS);
        query.setDay((byte) time.get(Calendar.DAY_OF_WEEK));
        list.addAll(search(query));
        // Get the monthly fees
        query.setRecurrence(TimePeriod.Field.MONTHS);
        query.setDay((byte) time.get(Calendar.DAY_OF_MONTH));
        list.addAll(search(query));
        int count = 0;
        for (final AccountFee fee : list) {
            final AccountFeeLog lastExecution = fee.getLastExecution();
            boolean charge;
            if (lastExecution == null) {
                // Was never executed. Charge now
                charge = true;
            } else {
                final TimePeriod recurrence = fee.getRecurrence();
                if (recurrence.getNumber() == 1) {
                    // When recurrence is every day or week or month, charge now
                    charge = true;
                } else {
                    // Check the recurrence
                    final Calendar lastExecutionDate = lastExecution.getDate();
                    if (lastExecutionDate.after(time)) {
                        // Consistency check: don't charge if last execution was after the current time
                        charge = false;
                    }
                    // Find the number of elapsed periods
                    int number = 0;
                    final Calendar cal = DateHelper.truncate(lastExecutionDate);
                    final int calendarField = recurrence.getField().getCalendarValue();
                    final Calendar date = DateHelper.truncate(time);
                    while (cal.before(date)) {
                        number++;
                        cal.add(calendarField, 1);
                    }
                    // Charge each 'x' periods
                    charge = number % recurrence.getNumber() == 0;
                }
            }
            // Charge the fee
            if (charge) {
                insertNextExecution(fee);
                count++;
            }
        }
        return count;
    }

    @Override
    public AccountFeeLog getLastLog(final AccountFee fee) {
        final AccountFeeLogQuery query = new AccountFeeLogQuery();
        query.setAccountFee(fee);
        query.setUniqueResult();
        final List<AccountFeeLog> list = accountFeeLogDao.search(query);
        if (list.isEmpty()) {
            return null;
        } else {
            return list.iterator().next();
        }
    }

    @Override
    public AccountFeeLogDetailsDTO getLogDetails(final Long id) {
        AccountFeeLog log = loadLog(id, AccountFeeLog.Relationships.ACCOUNT_FEE);
        AccountFee fee = log.getAccountFee();
        AccountFeeLogDetailsDTO dto = new AccountFeeLogDetailsDTO();
        dto.setAccountFeeLog(log);
        dto.setSkippedMembers(memberAccountFeeLogDao.countSkippedMembers(log));
        dto.setTransfers(fee.getInvoiceMode() == InvoiceMode.ALWAYS ? new TransactionSummaryVO() : memberAccountFeeLogDao.getTransfersSummary(log));
        dto.setInvoices(fee.getInvoiceMode() == InvoiceMode.NEVER ? new TransactionSummaryVO() : memberAccountFeeLogDao.getInvoicesSummary(log));
        dto.setAcceptedInvoices(fee.getInvoiceMode() == InvoiceMode.NEVER ? new TransactionSummaryVO() : memberAccountFeeLogDao.getAcceptedInvoicesSummary(log));
        dto.setOpenInvoices(dto.getInvoices().subtract(dto.getAcceptedInvoices()));
        return dto;
    }

    @Override
    public void initializeService() {
        insertMissingLogs();
    }

    @Override
    public AccountFee load(final Long id, final Relationship... fetch) {
        return accountFeeDao.load(id, fetch);
    }

    @Override
    public AccountFeeLog loadLog(final Long id, final Relationship... fetch) {
        return accountFeeLogDao.load(id, fetch);
    }

    @Override
    public AccountFeeLog nextLogToCharge() {
        return accountFeeLogDao.nextToCharge();
    }

    @Override
    public List<Member> nextMembersToCharge(final AccountFeeLog feeLog) {
        if (feeLog.isRechargingFailed()) {
            return memberAccountFeeLogDao.nextFailedToCharge(feeLog, ACCOUNT_FEE_CHARGE_BATCH_SIZE);
        } else {
            return memberAccountFeeLogDao.nextToCharge(feeLog, ACCOUNT_FEE_CHARGE_BATCH_SIZE);
        }
    }

    @Override
    public boolean prepareCharge(final AccountFeeLog feeLog) {
        if (feeLog.getTotalMembers() != null) {
            // Already prepared
            return false;
        }
        int totalMembers = memberAccountFeeLogDao.prepareCharge(feeLog);
        feeLog.setTotalMembers(totalMembers);
        accountFeeLogDao.update(feeLog);
        return true;
    }

    @Override
    public void rechargeFailed(final AccountFeeLog accountFeeLog) {
        AccountFeeLog log = fetchService.fetch(accountFeeLog);
        if (log.isRechargingFailed()) {
            // Already recharging failed
            return;
        }
        if (log.getFailedMembers() == 0) {
            // No failures
            return;
        }
        log.setRechargingFailed(true);
        log.setRechargeAttempt(log.getRechargeAttempt() + 1);
        accountFeeLogDao.update(log);

        applicationService.awakePollingTaskOnTransactionCommit(ChargeAccountFeePollingTask.class);
    }

    @Override
    public int remove(final Long... ids) {
        getVolumeFeeByAccountCache().clear();
        return accountFeeDao.delete(ids);
    }

    @Override
    public void removeFromPending(final AccountFeeLog feeLog, final Member member) {
        if (feeLog.isRechargingFailed()) {
            // Remove the MemberAccountFeeLog entirely
            memberAccountFeeLogDao.remove(feeLog, member);
        } else {
            // Just remove the pending charge
            memberAccountFeeLogDao.removePendingCharge(feeLog, member);
        }
    }

    @Override
    public AccountFee save(final AccountFee accountFee) {
        validate(accountFee);

        // Set some attributes to null depending on others
        if (accountFee.getPaymentDirection() == PaymentDirection.TO_MEMBER) {
            // A to member fee never uses invoices
            accountFee.setInvoiceMode(null);
        }
        if (accountFee.getRunMode() == RunMode.MANUAL) {
            // A manual fee does not have recurrence
            accountFee.setRecurrence(null);
            accountFee.setDay(null);
            accountFee.setHour(null);
        }

        // Ensure the cache for volume fees is cleared
        getVolumeFeeByAccountCache().clear();

        // Persist the account fee
        if (accountFee.isTransient()) {
            if (accountFee.isEnabled() && accountFee.getEnabledSince() == null) {
                accountFee.setEnabledSince(Calendar.getInstance());
            }
            return accountFeeDao.insert(accountFee);
        } else {
            final AccountFee current = load(accountFee.getId());
            // Correctly handle the enabled since
            if (accountFee.isEnabled() && current.getEnabledSince() == null) {
                // When was not previously enabled, initialize the enabled since
                if (accountFee.getEnabledSince() == null) {
                    accountFee.setEnabledSince(Calendar.getInstance());
                }
            } else if (!accountFee.isEnabled() && current.isEnabled()) {
                // When is disabling, set the date to null
                accountFee.setEnabledSince(null);
            } else if (accountFee.getEnabledSince() == null) {
                // Just updating other fields - keep the enabled since
                accountFee.setEnabledSince(current.getEnabledSince());
            }
            return accountFeeDao.update(accountFee);
        }
    }

    @Override
    public AccountFeeLog save(final AccountFeeLog accountFeeLog) {
        if (accountFeeLog.isTransient()) {
            return accountFeeLogDao.insert(accountFeeLog);
        } else {
            return accountFeeLogDao.update(accountFeeLog);
        }
    }

    @Override
    public List<AccountFee> search(final AccountFeeQuery query) {
        return accountFeeDao.search(query);
    }

    @Override
    public List<AccountFeeLog> searchLogs(final AccountFeeLogQuery query) {
        return accountFeeLogDao.search(query);
    }

    @Override
    public List<MemberAccountFeeLog> searchMembers(final MemberAccountFeeLogQuery query) {
        LocalSettings localSettings = settingsService.getLocalSettings();
        return memberAccountFeeLogDao.search(query, localSettings.getMemberResultDisplay());
    }

    public void setAccountDao(final AccountDAO accountDao) {
        this.accountDao = accountDao;
    }

    public void setAccountFeeDao(final AccountFeeDAO dao) {
        accountFeeDao = dao;
    }

    public void setAccountFeeLogDao(final AccountFeeLogDAO accountFeeLogDao) {
        this.accountFeeLogDao = accountFeeLogDao;
    }

    public void setAccountServiceLocal(final AccountServiceLocal accountService) {
        this.accountService = accountService;
    }

    public void setApplicationServiceLocal(final ApplicationServiceLocal applicationService) {
        this.applicationService = applicationService;
    }

    public void setCacheManager(final CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @Override
    public MemberAccountFeeLog setChargingError(final AccountFeeLog feeLog, final Member member, final BigDecimal amount) {
        removeFromPending(feeLog, member);

        final MemberAccountFeeLog mafl = new MemberAccountFeeLog();
        mafl.setDate(Calendar.getInstance());
        mafl.setAccountFeeLog(feeLog);
        mafl.setMember(member);
        mafl.setAmount(amount);
        mafl.setSuccess(false);
        mafl.setRechargeAttempt(feeLog.getRechargeAttempt());
        return memberAccountFeeLogDao.insert(mafl);
    }

    @Override
    public MemberAccountFeeLog setChargingSuccess(final AccountFeeLog feeLog, final Member member, final BigDecimal amount, final Transfer transfer, final Invoice invoice) {
        removeFromPending(feeLog, member);

        MemberAccountFeeLog mafl = null;

        if (feeLog.isRechargingFailed()) {
            // Load the failed log
            mafl = memberAccountFeeLogDao.load(feeLog, member);
            if (mafl != null && mafl.isSuccess()) {
                // Nothing to do with this member, as it was not a charge failure
                return null;
            }
        }

        if (mafl == null) {
            mafl = new MemberAccountFeeLog();
            mafl.setAccountFeeLog(feeLog);
            mafl.setMember(member);
        }
        mafl.setDate(Calendar.getInstance());
        mafl.setAmount(amount);
        mafl.setSuccess(true);
        mafl.setTransfer(transfer);
        mafl.setInvoice(invoice);

        if (mafl.isTransient()) {
            return memberAccountFeeLogDao.insert(mafl);
        } else {
            return memberAccountFeeLogDao.update(mafl);
        }
    }

    public void setFetchServiceLocal(final FetchServiceLocal fetchService) {
        this.fetchService = fetchService;
    }

    public void setMemberAccountFeeLogDao(final MemberAccountFeeLogDAO memberAccountFeeLogDao) {
        this.memberAccountFeeLogDao = memberAccountFeeLogDao;
    }

    public void setPaymentServiceLocal(final PaymentServiceLocal paymentService) {
        this.paymentService = paymentService;
    }

    public void setSettingsServiceLocal(final SettingsServiceLocal settingsService) {
        this.settingsService = settingsService;
    }

    @Override
    public void validate(final AccountFee accountFee) {
        getValidator(accountFee).validate(accountFee);
    }

    private BigDecimal calculateChargeOverTransactionedVolume(final AccountFeeLog feeLog, final Member member) {
        AccountFee fee = feeLog.getAccountFee();
        if (!fee.isEnabled()) {
            return BigDecimal.ZERO;
        }
        MemberAccount account = (MemberAccount) accountService.getAccount(new AccountDTO(member, fee.getAccountType()));

        // We want to limit for diffs within the fee log period
        Period logPeriod = feeLog.getPeriod();
        Calendar beginDate = logPeriod.getBegin();
        if (fee.getEnabledSince().after(beginDate)) {
            // However, if the fee was enabled in the middle of the period, consider this date instead
            beginDate = fee.getEnabledSince();
        }
        if (account.getCreationDate().after(beginDate)) {
            // If the account was created after, use it's creation date
            beginDate = account.getCreationDate();
        }
        // As we calculate by whole days, make sure we're on the next day, so the balance will be ok
        beginDate = DateHelper.truncateNextDay(beginDate);

        Period period = Period.between(beginDate, logPeriod.getEnd());
        if (period.getBegin().after(period.getEnd())) {
            // In case of single days, the begin is the next day, and the end is the last second of the current day
            period.setEnd(period.getBegin());
        }
        return calculateVolumeCharge(account, fee, period, BigDecimal.ZERO, false);
    }

    private BigDecimal calculateVolumeCharge(final MemberAccount account, final AccountFee volumeFee, final Period period, final BigDecimal additionalReserved, final boolean currentPeriod) {
        Calendar fromDate = period.getBegin();

        // Get the account status right after the last charged period
        AccountStatus status = accountService.getStatus(account, fromDate);

        // When there is some additional amount to consider as reserved, add it to the status
        status.setReservedAmount(status.getReservedAmount().add(additionalReserved));

        // Calculate the total days. We want the entire charged period. For example: if the fee was enabled in the middle of a month, it would be the
        // entire month. Likewise, if no end limit was given, the current period will be used (ie, and the last day in the current month)
        TimePeriod recurrence = volumeFee.getRecurrence();
        Period totalPeriod = recurrence.currentPeriod(period.getBegin());
        int totalDays = totalPeriod.getDays() + 1;

        // Calculate each difference, with the corresponding reserved amount
        Calendar lastDay = fromDate;
        Calendar lastChargedDay = fromDate;
        BigDecimal result = BigDecimal.ZERO;
        IteratorList<AccountDailyDifference> diffs = accountDao.iterateDailyDifferences(account, period);
        if (LOG.isDebugEnabled()) {
            LOG.debug("********************************");
            LOG.debug(FormatObject.formatObject(period.getBegin()) + "\t" + status.getBalance() + "\t" + status.getAvailableBalance());
        }
        try {
            if (diffs.hasNext()) {
                // There are differences - the lastChargedAvailable balance will be obtained within the loop
                for (AccountDailyDifference diff : diffs) {
                    Calendar day = diff.getDay();
                    int days = DateHelper.daysBetween(lastDay, day);
                    // Get the available balance at that day
                    BigDecimal available = status.getAvailableBalanceWithoutCreditLimit();
                    if (volumeFee.getChargeMode().isNegative()) {
                        // If the charge is over negative amounts, consider the negated amount
                        available = available.negate();
                    }
                    // Take the free base into account
                    if (volumeFee.getFreeBase() != null) {
                        available = available.subtract(volumeFee.getFreeBase());
                    }
                    // If the available balance was significant, calculate the charge
                    if (available.compareTo(BigDecimal.ZERO) > 0) {
                        BigDecimal volume = new BigDecimal(available.doubleValue() * days / totalDays);
                        if (LOG.isDebugEnabled()) {
                            LOG.debug(FormatObject.formatObject(day) + "\t" + diff.getBalance() + "\t" + status.getAvailableBalanceWithoutCreditLimit().add(diff.getBalance()) + "\t" + days + "\t" + totalDays + "\t" + volume);
                        }
                        BigDecimal toCharge = volume.multiply(volumeFee.getAmount()).divide(BigDecimalHelper.ONE_HUNDRED);
                        // status.setReservedAmount(status.getReservedAmount().add(toCharge));
                        result = result.add(toCharge);
                        lastChargedDay = day;
                    }
                    lastDay = day;
                    status.setBalance(status.getBalance().add(diff.getBalance()));
                    status.setReservedAmount(status.getReservedAmount().add(diff.getReserved()));
                }
            }
        } finally {
            DataIteratorHelper.close(diffs);
        }

        Calendar toDate = period.getEnd();
        boolean lastPaymentInPeriodEnd = !toDate.before(lastChargedDay);
        LocalSettings settings = settingsService.getLocalSettings();

        // Only if the last payment was not today we have to take into account the results so far
        if (DateHelper.daysBetween(lastChargedDay, Calendar.getInstance()) != 0) {
            BigDecimal resultSoFar = settings.round(result);
            status.setReservedAmount(status.getReservedAmount().add(resultSoFar));
        }

        // Calculate the avaliable balance after the last diff, which will remain the same until the period end
        BigDecimal finalAvailableBalance = status.getAvailableBalanceWithoutCreditLimit();
        if (volumeFee.getChargeMode().isNegative()) {
            finalAvailableBalance = finalAvailableBalance.negate();
        }
        if (volumeFee.getFreeBase() != null) {
            finalAvailableBalance = finalAvailableBalance.subtract(volumeFee.getFreeBase());
        }

        // Consider the last time slice, between the last diff and the period end, if any
        if (lastPaymentInPeriodEnd && finalAvailableBalance.compareTo(BigDecimal.ZERO) > 0) {
            int days = DateHelper.daysBetween(lastChargedDay, toDate) + (currentPeriod ? 0 : 1);
            // Here, the lastChargedAvailableBalance is already subtracted from the free base (if any)
            BigDecimal volume = new BigDecimal(finalAvailableBalance.doubleValue() * days / totalDays);
            BigDecimal toCharge = volume.multiply(volumeFee.getAmount()).divide(BigDecimalHelper.ONE_HUNDRED);
            result = result.add(toCharge);
            if (LOG.isDebugEnabled()) {
                status.setReservedAmount(settings.round(status.getReservedAmount().add(toCharge)));
                LOG.debug(FormatObject.formatObject(lastChargedDay) + "\t0\t" + status.getAvailableBalanceWithoutCreditLimit() + "\t" + days + "\t" + totalDays + "\t" + volume);
            }
        }

        return settings.round(result);
    }

    /**
     * Returns the missing periods for an account fee
     */
    private List<Period> getMissingPeriods(final AccountFee fee) {
        final TimePeriod recurrence = fee.getRecurrence();
        Calendar since;
        final Calendar now = DateUtils.truncate(Calendar.getInstance(), Calendar.HOUR_OF_DAY);

        // Determine since when the fee should have run
        final AccountFeeLog lastLog = getLastLog(fee);
        if (lastLog == null || lastLog.getDate().before(fee.getEnabledSince())) {
            // May be 2 cases: Either the fee never ran or was re-enabled after the last run
            since = fee.getEnabledSince();
        } else {
            // The fee ran and is enabled, just has missing logs
            since = lastLog.getDate();
        }

        // Resolve the periods
        final List<Period> periods = new ArrayList<Period>();
        Calendar date = DateHelper.truncate(since);
        Period period = recurrence.previousPeriod(date);
        while (true) {
            date = (Calendar) period.getEnd().clone();
            date.add(Calendar.SECOND, 1);
            period = recurrence.periodStartingAt(date);
            if (period.getEnd().before(now)) {
                periods.add(period);
            } else {
                break;
            }
        }

        // Check if the last one should be really there
        if (!periods.isEmpty()) {
            // Do not use the last period if the listener has not run yet
            final byte thisDay = (byte) now.get(Calendar.DAY_OF_MONTH);
            final byte thisHour = (byte) now.get(Calendar.HOUR_OF_DAY);
            boolean removeLast = false;

            final Byte feeDay = fee.getDay();
            if (feeDay != null && thisDay < feeDay) {
                removeLast = true;
            } else if (feeDay == null || thisDay == feeDay) {
                removeLast = thisHour < fee.getHour();
            }
            if (removeLast) {
                periods.remove(periods.size() - 1);
            }
        }

        // Check if any of those logs are present
        final Iterator<Period> it = periods.iterator();
        while (it.hasNext()) {
            final Period current = it.next();
            final AccountFeeLogQuery logQuery = new AccountFeeLogQuery();
            logQuery.setPageForCount();
            logQuery.setAccountFee(fee);
            logQuery.setPeriodStartAt(current.getBegin());
            final int count = PageHelper.getTotalCount(accountFeeLogDao.search(logQuery));
            if (count > 0) {
                it.remove();
            }
        }
        return periods;
    }

    private Validator getValidator(final AccountFee fee) {
        final Validator validator = new Validator("accountFee");
        validator.property("accountType").required();
        validator.property("transferType").required();
        validator.property("name").required().maxLength(100);
        validator.property("description").maxLength(1000);
        validator.property("amount").required().positiveNonZero();
        validator.property("chargeMode").required();
        validator.property("paymentDirection").required();
        Property runMode = validator.property("runMode").required();
        if (fee.getChargeMode() != null && fee.getChargeMode().isVolume()) {
            runMode.anyOf(RunMode.SCHEDULED);
        }
        if (fee.getRunMode() == RunMode.SCHEDULED) {
            validator.property("recurrence.number").key("accountFee.recurrence").required().positiveNonZero().lessThan(28);
            validator.property("recurrence.field").key("accountFee.recurrence").required().anyOf(TimePeriod.Field.DAYS, TimePeriod.Field.WEEKS, TimePeriod.Field.MONTHS);
            Property day = validator.property("day");
            Field field = fee.getRecurrence() == null ? null : fee.getRecurrence().getField();
            if (field != null && field != Field.DAYS) {
                day.required();
                if (field == Field.WEEKS) {
                    day.between(1, 7);
                } else {
                    day.between(1, 28);
                }
            }
            validator.property("hour").required().between(0, 23);
        }
        if (fee.isMemberToSystem()) {
            validator.property("invoiceMode").required();
        }
        if (fee.isTransient()) {
            validator.property("enabledSince").key("accountFee.firstPeriodAfter").futureOrToday();
        }
        return validator;
    }

    private AccountFee getVolumeFee(final AccountType accountType, final MemberGroup group) {
        Pair<Long, Long> key = new Pair<Long, Long>(accountType.getId(), group.getId());
        return getVolumeFeeByAccountCache().get(key, new CacheCallback() {
            @Override
            public Object retrieve() {
                final AccountFeeQuery query = new AccountFeeQuery();
                query.setAccountType(accountType);
                query.setGroups(Collections.singleton(group));
                query.setType(RunMode.SCHEDULED);
                List<AccountFee> fees = search(query);
                for (Iterator<AccountFee> iterator = fees.iterator(); iterator.hasNext();) {
                    if (!iterator.next().getChargeMode().isVolume()) {
                        iterator.remove();
                    }
                }
                if (fees.size() > 1) {
                    throw new ValidationException("accountFee.error.multipleVolumeFees");
                }
                return fees.isEmpty() ? null : fees.iterator().next();
            }
        });
    }

    private Cache getVolumeFeeByAccountCache() {
        return cacheManager.getCache("cyclos.VolumeFeeByAccount");
    }

    private void insertMissingLogs() {
        final AccountFeeQuery query = new AccountFeeQuery();
        query.setReturnDisabled(false);
        query.setType(RunMode.SCHEDULED);
        Calendar thisHour = DateUtils.truncate(Calendar.getInstance(), Calendar.HOUR_OF_DAY);
        final List<AccountFee> accountFees = accountFeeDao.search(query);
        for (final AccountFee fee : accountFees) {
            final Field recurrenceField = fee.getRecurrence().getField();
            final List<Period> missingPeriods = getMissingPeriods(fee);
            if (!missingPeriods.isEmpty()) {
                for (final Period period : missingPeriods) {
                    final Calendar shouldHaveChargedAt = DateHelper.truncate(period.getEnd());
                    shouldHaveChargedAt.add(Calendar.DAY_OF_MONTH, 1);
                    switch (recurrenceField) {
                        case WEEKS:
                            // Go to the day it should have been charged
                            int max = 7;
                            while (max > 0 && shouldHaveChargedAt.get(Calendar.DAY_OF_WEEK) < fee.getDay()) {
                                shouldHaveChargedAt.add(Calendar.DAY_OF_MONTH, 1);
                                max--;
                            }
                            break;
                        case MONTHS:
                            shouldHaveChargedAt.set(Calendar.DAY_OF_MONTH, fee.getDay());
                            break;
                    }
                    shouldHaveChargedAt.set(Calendar.HOUR_OF_DAY, fee.getHour());
                    // Only insert the missing log if it should have run before or at this hour
                    if (!shouldHaveChargedAt.after(thisHour)) {
                        final AccountFeeLog log = new AccountFeeLog();
                        log.setAccountFee(fee);
                        log.setDate(shouldHaveChargedAt);
                        log.setPeriod(period);
                        log.setAmount(fee.getAmount());
                        log.setFreeBase(fee.getFreeBase());
                        accountFeeLogDao.insert(log);
                    }
                }
            }
        }
    }

    private AccountFeeLog insertNextExecution(AccountFee fee) {
        fee = fetchService.fetch(fee);
        if (!fee.isEnabled()) {
            return null;
        }

        // Resolve the period
        Period period = null;
        Calendar executionDate = null;
        if (fee.getRunMode() == RunMode.MANUAL) {
            // Manual fee
            executionDate = Calendar.getInstance();
        } else {
            // Scheduled fee
            executionDate = fee.getNextExecutionDate();
            // Do not insert future account fees.
            if (executionDate.after(Calendar.getInstance())) {
                return null;
            }
            period = fee.getRecurrence().previousPeriod(executionDate);
        }

        // Create the log
        AccountFeeLog nextExecution = new AccountFeeLog();
        nextExecution.setAccountFee(fee);
        nextExecution.setDate(executionDate);
        nextExecution.setPeriod(period);
        nextExecution.setAmount(fee.getAmount());
        nextExecution.setFreeBase(fee.getFreeBase());
        return accountFeeLogDao.insert(nextExecution);
    }

}
