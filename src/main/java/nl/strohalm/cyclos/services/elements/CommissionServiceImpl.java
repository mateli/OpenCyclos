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
package nl.strohalm.cyclos.services.elements;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import nl.strohalm.cyclos.access.AdminMemberPermission;
import nl.strohalm.cyclos.access.BrokerPermission;
import nl.strohalm.cyclos.dao.members.brokerings.BrokerCommissionContractDAO;
import nl.strohalm.cyclos.dao.members.brokerings.BrokeringCommissionStatusDAO;
import nl.strohalm.cyclos.dao.members.brokerings.DefaultBrokerCommissionDAO;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.AccountType.Nature;
import nl.strohalm.cyclos.entities.accounts.fees.transaction.BrokerCommission;
import nl.strohalm.cyclos.entities.accounts.fees.transaction.TransactionFeeQuery;
import nl.strohalm.cyclos.entities.groups.BrokerGroup;
import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.entities.groups.GroupQuery;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.members.BrokeringQuery;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.members.brokerings.BrokerCommissionContract;
import nl.strohalm.cyclos.entities.members.brokerings.BrokerCommissionContract.Status;
import nl.strohalm.cyclos.entities.members.brokerings.BrokerCommissionContractQuery;
import nl.strohalm.cyclos.entities.members.brokerings.Brokering;
import nl.strohalm.cyclos.entities.members.brokerings.BrokeringCommissionStatus;
import nl.strohalm.cyclos.entities.members.brokerings.BrokeringCommissionStatusQuery;
import nl.strohalm.cyclos.entities.members.brokerings.DefaultBrokerCommission;
import nl.strohalm.cyclos.entities.members.brokerings.DefaultBrokerCommissionQuery;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.services.InitializingService;
import nl.strohalm.cyclos.services.fetch.FetchServiceLocal;
import nl.strohalm.cyclos.services.groups.GroupServiceLocal;
import nl.strohalm.cyclos.services.permissions.PermissionServiceLocal;
import nl.strohalm.cyclos.services.settings.SettingsServiceLocal;
import nl.strohalm.cyclos.services.transactions.TransactionSummaryVO;
import nl.strohalm.cyclos.services.transfertypes.TransactionFeeServiceLocal;
import nl.strohalm.cyclos.utils.Amount;
import nl.strohalm.cyclos.utils.CacheCleaner;
import nl.strohalm.cyclos.utils.DataIteratorHelper;
import nl.strohalm.cyclos.utils.DateHelper;
import nl.strohalm.cyclos.utils.Period;
import nl.strohalm.cyclos.utils.TimePeriod;
import nl.strohalm.cyclos.utils.TimePeriod.Field;
import nl.strohalm.cyclos.utils.access.LoggedUser;
import nl.strohalm.cyclos.utils.notifications.MemberNotificationHandler;
import nl.strohalm.cyclos.utils.query.IteratorList;
import nl.strohalm.cyclos.utils.query.PageHelper;
import nl.strohalm.cyclos.utils.query.QueryParameters.ResultType;
import nl.strohalm.cyclos.utils.validation.GeneralValidation;
import nl.strohalm.cyclos.utils.validation.PropertyValidation;
import nl.strohalm.cyclos.utils.validation.ValidationError;
import nl.strohalm.cyclos.utils.validation.ValidationException;
import nl.strohalm.cyclos.utils.validation.Validator;

import org.apache.commons.collections.CollectionUtils;

/**
 * Implementation for commission service
 * @author Jefferson Magno
 */
public class CommissionServiceImpl implements CommissionServiceLocal, InitializingService {

    /**
     * Validates if there is an active or pending contract with conflicting period
     * @author Jefferson Magno
     */
    public class ConflictingContractValidation implements GeneralValidation {

        private static final long serialVersionUID = -2265778870845933590L;

        @Override
        public ValidationError validate(final Object object) {
            final BrokerCommissionContract brokerCommissionContract = (BrokerCommissionContract) object;
            if (brokerCommissionContractDao.isConflictingContract(brokerCommissionContract)) {
                return new ValidationError("brokerCommissionContract.error.conflictingContract");
            }
            return null;
        }
    }

    /**
     * Validates the default broker commission
     * @author Jefferson Magno
     */
    public class DefaultBrokerCommissionValidation implements GeneralValidation {

        private static final long serialVersionUID = -7493901526206665521L;

        @Override
        public ValidationError validate(final Object object) {
            final DefaultBrokerCommission defaultBrokerCommission = (DefaultBrokerCommission) object;

            if (defaultBrokerCommission.getBrokerCommission() != null) {
                final BrokerCommission brokerCommission = fetchService.fetch(defaultBrokerCommission.getBrokerCommission());
                final Amount defaultAmount = defaultBrokerCommission.getAmount();
                final Amount.Type type = defaultAmount.getType();
                final BigDecimal defaultValue = defaultAmount.getValue();

                BigDecimal feeValue = null;
                if (type == Amount.Type.FIXED) {
                    feeValue = brokerCommission.getMaxFixedValue();
                } else {
                    feeValue = brokerCommission.getMaxPercentageValue();
                }
                if (defaultValue != null && feeValue != null && defaultValue.compareTo(feeValue) > 0) {
                    final LocalSettings localSettings = settingsService.getLocalSettings();
                    final String brokerCommissionName = brokerCommission.getName();
                    final Amount feeAmount = new Amount(feeValue, type);
                    final String formattedFeeAmount = localSettings.getAmountConverter().toString(feeAmount);
                    return new ValidationError("defaultBrokerCommission.error.maxValueExceeded", brokerCommissionName, formattedFeeAmount);
                }
            }
            return null;
        }
    }

    /**
     * Validates if the status of the contract is pending before saving it
     * @author Jefferson Magno
     */
    public class StatusValidation implements PropertyValidation {

        private static final long serialVersionUID = 1390235157926363221L;

        @Override
        public ValidationError validate(final Object object, final Object property, final Object value) {
            final BrokerCommissionContract.Status status = (BrokerCommissionContract.Status) value;
            if (status != BrokerCommissionContract.Status.PENDING) {
                return new ValidationError("brokerCommissionContract.error.notPendingStatus");
            }
            return null;
        }
    }

    private PermissionServiceLocal       permissionService;

    private BrokerCommissionContractDAO  brokerCommissionContractDao;

    private BrokeringCommissionStatusDAO brokeringCommissionStatusDao;
    private BrokeringServiceLocal        brokeringService;
    private DefaultBrokerCommissionDAO   defaultBrokerCommissionDao;
    private FetchServiceLocal            fetchService;
    private GroupServiceLocal            groupService;
    private MemberServiceLocal           memberService;
    private SettingsServiceLocal         settingsService;
    private TransactionFeeServiceLocal   transactionFeeService;
    private MemberNotificationHandler    memberNotificationHandler;

    @Override
    public BrokerCommissionContract acceptBrokerCommissionContract(final Long brokerCommissionContractId) {
        BrokerCommissionContract brokerCommissionContract = loadBrokerCommissionContract(brokerCommissionContractId);
        final Period period = brokerCommissionContract.getPeriod();
        final boolean alreadyActive = period != null && period.includes(Calendar.getInstance());
        brokerCommissionContract.setStatus(alreadyActive ? Status.ACTIVE : Status.ACCEPTED);
        brokerCommissionContract = brokerCommissionContractDao.update(brokerCommissionContract);
        memberNotificationHandler.commissionContractAcceptedNotification(brokerCommissionContract);
        return brokerCommissionContract;
    }

    @Override
    public void activateAcceptedBrokerCommissionContracts(final Calendar time) {
        final Period today = Period.endingAt(DateHelper.truncate(time));

        // Activate accepted contracts that begin today
        final BrokerCommissionContractQuery query = new BrokerCommissionContractQuery();
        query.setResultType(ResultType.ITERATOR);
        query.setStatus(BrokerCommissionContract.Status.ACCEPTED);
        query.setStartPeriod(today);
        final List<BrokerCommissionContract> contractsToActivate = brokerCommissionContractDao.search(query);
        try {
            for (final BrokerCommissionContract contractToActivate : contractsToActivate) {
                contractToActivate.setStatus(BrokerCommissionContract.Status.ACTIVE);
                brokerCommissionContractDao.update(contractToActivate);
            }
        } finally {
            DataIteratorHelper.close(contractsToActivate);
        }
    }

    @Override
    public BrokerCommissionContract cancelBrokerCommissionContract(final Long brokerCommissionContractId) {
        final Element cancelledBy = LoggedUser.element();
        BrokerCommissionContract brokerCommissionContract = loadBrokerCommissionContract(brokerCommissionContractId);
        brokerCommissionContract.setStatus(Status.CANCELLED);
        brokerCommissionContract.setCancelledBy(cancelledBy);
        brokerCommissionContract = brokerCommissionContractDao.update(brokerCommissionContract);
        memberNotificationHandler.commissionContractCancelledNotification(brokerCommissionContract);
        return brokerCommissionContract;
    }

    @Override
    public BrokeringCommissionStatus closeBrokeringCommissionStatus(BrokeringCommissionStatus brokeringCommissionStatus) {
        brokeringCommissionStatus.getPeriod().setEnd(Calendar.getInstance());
        brokeringCommissionStatus = brokeringCommissionStatusDao.update(brokeringCommissionStatus);
        return brokeringCommissionStatus;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void createBrokeringCommissionStatus(final BrokerCommission brokerCommission) {
        Collection<BrokerGroup> brokerGroups = null;
        if (brokerCommission.isAllBrokerGroups()) {
            final GroupQuery query = new GroupQuery();
            query.setNature(Group.Nature.BROKER);
            query.setStatus(Group.Status.NORMAL);
            query.setOnlyActive(true);
            brokerGroups = (Collection<BrokerGroup>) groupService.search(query);
        } else {
            brokerGroups = fetchService.fetch(brokerCommission.getBrokerGroups());
        }
        for (final BrokerGroup brokerGroup : brokerGroups) {
            createBrokeringCommissionStatus(brokerGroup, brokerCommission);
        }
    }

    @Override
    public BrokeringCommissionStatus createBrokeringCommissionStatus(final Brokering brokering, final BrokerCommission brokerCommission) {
        // Create new instance
        final BrokeringCommissionStatus brokeringCommissionStatus = new BrokeringCommissionStatus();
        brokeringCommissionStatus.setBrokering(brokering);
        brokeringCommissionStatus.setBrokerCommission(brokerCommission);
        brokeringCommissionStatus.setCreationDate(Calendar.getInstance());
        boolean fillFromCommission = true;

        if (brokerCommission.isFromMember()) {
            // Get the data from the default broker commission
            final DefaultBrokerCommission defaultBrokerCommission = getDefaultBrokerCommission(brokering.getBroker(), brokerCommission);
            if (defaultBrokerCommission != null) {
                brokeringCommissionStatus.setAmount(defaultBrokerCommission.getAmount());
                brokeringCommissionStatus.setWhen(defaultBrokerCommission.getWhen());
                brokeringCommissionStatus.setMaxCount(defaultBrokerCommission.getCount());
                fillFromCommission = false;
            }
        }
        if (fillFromCommission) {
            // Get the data from the broker commission
            brokeringCommissionStatus.setAmount(brokerCommission.getAmount());
            brokeringCommissionStatus.setWhen(brokerCommission.getWhen());
            brokeringCommissionStatus.setMaxCount(brokerCommission.getCount());
        }

        // Set total
        final TransactionSummaryVO total = new TransactionSummaryVO();
        total.setCount(0);
        total.setAmount(new BigDecimal(0));
        brokeringCommissionStatus.setTotal(total);

        // Period
        final Period period = new Period();
        period.setBegin(brokering.getStartDate());

        // If the commission has a validity (number of days), set expiration date on the commission status and check if it is not expired
        if (brokeringCommissionStatus.getWhen() == BrokerCommission.When.DAYS) {

            // Calculate and set expiration date
            final Calendar brokeringStartDate = brokering.getStartDate();
            final int daysCount = brokeringCommissionStatus.getMaxCount();
            final Calendar expirationDate = new TimePeriod(daysCount, Field.DAYS).add(brokeringStartDate);
            brokeringCommissionStatus.setExpiryDate(expirationDate);

            // Check if the brokering commission status is expired
            final Calendar now = Calendar.getInstance();
            if (now.compareTo(expirationDate) >= 0) {
                period.setEnd(expirationDate);
            }
        }

        // Set period
        brokeringCommissionStatus.setPeriod(period);

        return brokeringCommissionStatusDao.insert(brokeringCommissionStatus);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void createDefaultBrokerCommissions(final BrokerCommission brokerCommission) {
        Collection<BrokerGroup> brokerGroups = null;
        if (brokerCommission.isAllBrokerGroups()) {
            final GroupQuery query = new GroupQuery();
            query.setNature(Group.Nature.BROKER);
            query.setStatus(Group.Status.NORMAL);
            query.setOnlyActive(true);
            brokerGroups = (Collection<BrokerGroup>) groupService.search(query);
        } else {
            brokerGroups = fetchService.fetch(brokerCommission.getBrokerGroups());
        }
        for (final BrokerGroup brokerGroup : brokerGroups) {
            createDefaultBrokerCommissions(brokerCommission, brokerGroup);
        }
    }

    @Override
    public BrokerCommissionContract denyBrokerCommissionContract(final Long brokerCommissionContractId) {
        BrokerCommissionContract brokerCommissionContract = loadBrokerCommissionContract(brokerCommissionContractId);
        brokerCommissionContract.setStatus(Status.DENIED);
        brokerCommissionContract = brokerCommissionContractDao.update(brokerCommissionContract);
        memberNotificationHandler.commissionContractDeniedNotification(brokerCommissionContract);
        return brokerCommissionContract;
    }

    @Override
    public void expireBrokerCommissionContracts(final Calendar time) {
        final Period endOfYesterday = Period.endingAt(DateHelper.truncatePreviosDay(time));

        // Close contracts that expired at the end of yesterday
        BrokerCommissionContractQuery query = new BrokerCommissionContractQuery();
        query.setResultType(ResultType.ITERATOR);
        query.setStatus(BrokerCommissionContract.Status.ACTIVE);
        query.setEndPeriod(endOfYesterday);
        final List<BrokerCommissionContract> contractsToClose = brokerCommissionContractDao.search(query);
        try {
            for (final BrokerCommissionContract contractToClose : contractsToClose) {
                contractToClose.setStatus(BrokerCommissionContract.Status.CLOSED);
                brokerCommissionContractDao.update(contractToClose);
            }
        } finally {
            DataIteratorHelper.close(contractsToClose);
        }

        // Expire contracts that were not accepted until it´s beginning
        query = new BrokerCommissionContractQuery();
        query.setResultType(ResultType.ITERATOR);
        query.setStatus(BrokerCommissionContract.Status.PENDING);
        query.setStartPeriod(endOfYesterday);
        final List<BrokerCommissionContract> contractsToExpire = brokerCommissionContractDao.search(query);
        try {
            for (final BrokerCommissionContract contractToExpire : contractsToExpire) {
                contractToExpire.setStatus(BrokerCommissionContract.Status.EXPIRED);
                brokerCommissionContractDao.update(contractToExpire);
            }
        } finally {
            DataIteratorHelper.close(contractsToExpire);
        }
    }

    @Override
    public void expireBrokeringCommissionStatus(final Calendar date) {
        brokeringCommissionStatusDao.expireBrokeringCommissionStatus(date);
    }

    @Override
    public BrokerCommissionContract getActiveBrokerCommissionContract(final Brokering brokering, final BrokerCommission brokerCommission) {
        final BrokerCommissionContractQuery query = new BrokerCommissionContractQuery();
        query.setBroker(brokering.getBroker());
        query.setMember(brokering.getBrokered());
        query.setBrokerCommission(brokerCommission);
        query.setStatus(Status.ACTIVE);
        query.setUniqueResult();
        final List<BrokerCommissionContract> contracts = brokerCommissionContractDao.search(query);
        if (CollectionUtils.isEmpty(contracts)) {
            return null;
        } else {
            return contracts.iterator().next();
        }
    }

    @Override
    public BrokeringCommissionStatus getBrokeringCommissionStatus(final Brokering brokering, final BrokerCommission brokerCommission) {
        return brokeringCommissionStatusDao.load(brokering, brokerCommission);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<CommissionChargeStatusDTO> getCommissionChargeStatus(final Member member) {
        final List<CommissionChargeStatusDTO> commissionChargeStatusList = new ArrayList<CommissionChargeStatusDTO>();

        final Brokering brokering = brokeringService.getActiveBrokering(member);

        if (brokering != null) {
            final Member broker = fetchService.fetch(brokering.getBroker(), Element.Relationships.GROUP);

            // Get broker commission transaction fees related to the member group and to the broker group
            final TransactionFeeQuery transactionFeeQuery = new TransactionFeeQuery();
            transactionFeeQuery.setGeneratedTransferTypeFromNature(Nature.MEMBER);
            transactionFeeQuery.setEntityType(BrokerCommission.class);
            transactionFeeQuery.setBrokerGroup((BrokerGroup) broker.getGroup());
            transactionFeeQuery.setMemberGroup((MemberGroup) member.getGroup());
            final List<BrokerCommission> brokerCommissions = (List<BrokerCommission>) transactionFeeService.search(transactionFeeQuery);

            // For each broker commission get the commission charge status dto
            for (final BrokerCommission brokerCommission : brokerCommissions) {
                final CommissionChargeStatusDTO commissionChargeStatusDto = new CommissionChargeStatusDTO();
                commissionChargeStatusDto.setBrokerCommission(brokerCommission);
                final BrokerCommissionContract contract = getBrokerCommissionContract(member, broker, brokerCommission);
                if (contract != null) {
                    commissionChargeStatusDto.setBrokerCommissionContract(contract);
                    commissionChargeStatusDto.setChargeStatus(CommissionChargeStatusDTO.ChargeStatus.CONTRACT);
                } else {
                    final BrokeringCommissionStatus status = getBrokeringCommissionStatus(brokering, brokerCommission);
                    final DefaultBrokerCommission defaultBrokerCommission = getDefaultBrokerCommission(broker, brokerCommission);
                    if (defaultBrokerCommission != null && status.getPeriod().getEnd() == null) {
                        commissionChargeStatusDto.setBrokeringCommissionStatus(status);
                        commissionChargeStatusDto.setChargeStatus(CommissionChargeStatusDTO.ChargeStatus.DEFAULT_COMMISSION);
                    } else {
                        commissionChargeStatusDto.setChargeStatus(CommissionChargeStatusDTO.ChargeStatus.NONE);
                    }
                }
                commissionChargeStatusList.add(commissionChargeStatusDto);
            }
        }
        return commissionChargeStatusList;
    }

    @Override
    public DefaultBrokerCommission getDefaultBrokerCommission(final Member broker, final BrokerCommission brokerCommission) {
        final DefaultBrokerCommissionQuery query = new DefaultBrokerCommissionQuery();
        query.setBroker(broker);
        query.setBrokerCommission(brokerCommission);
        query.setUniqueResult();
        final List<DefaultBrokerCommission> defaultBrokerCommissions = defaultBrokerCommissionDao.search(query);
        if (CollectionUtils.isEmpty(defaultBrokerCommissions)) {
            return null;
        } else {
            return defaultBrokerCommissions.iterator().next();
        }
    }

    @Override
    public BrokeringCommissionStatus getOrUpdateBrokeringCommissionStatus(final Brokering brokering, final BrokerCommission brokerCommission) {
        return getOrUpdateBrokeringCommissionStatus(brokering, brokerCommission, brokerCommission.getAmount(), brokerCommission.getWhen(), brokerCommission.getCount());
    }

    @Override
    public boolean hasAllPermissions() {
        return permissionService.permission()
                .admin(AdminMemberPermission.BROKERINGS_MANAGE_COMMISSIONS)
                .broker(BrokerPermission.MEMBERS_MANAGE_CONTRACTS)
                .member()
                .hasPermission();
    }

    @Override
    public boolean hasBrokerCommissionContracts() {
        if (!hasAllPermissions()) {
            return false;
        }
        final BrokerCommissionContractQuery commissionQuery = new BrokerCommissionContractQuery();
        commissionQuery.setPageForCount();
        commissionQuery.setMember(LoggedUser.member());
        return PageHelper.hasResults(brokerCommissionContractDao.search(commissionQuery));
    }

    @Override
    public void initializeService() {
        Calendar now = Calendar.getInstance();
        activateAcceptedBrokerCommissionContracts(now);
        expireBrokerCommissionContracts(now);
        expireBrokeringCommissionStatus(now);
    }

    @Override
    public List<BrokerCommission> listPossibleCommissionsForNewContract(Member brokered) {
        brokered = fetchService.fetch(brokered, Element.Relationships.GROUP);
        final BrokerGroup brokerGroup = LoggedUser.group();
        final MemberGroup memberGroup = brokered.getMemberGroup();

        // Get broker commission transaction fees related to the broker group
        final TransactionFeeQuery transactionFeeQuery = new TransactionFeeQuery();
        transactionFeeQuery.setGeneratedTransferTypeFromNature(Nature.MEMBER);
        transactionFeeQuery.setEntityType(BrokerCommission.class);
        transactionFeeQuery.setBrokerGroup(brokerGroup);
        transactionFeeQuery.setMemberGroup(memberGroup);

        @SuppressWarnings("unchecked")
        final List<BrokerCommission> brokerCommissions = new ArrayList<BrokerCommission>((List<BrokerCommission>) transactionFeeService.search(transactionFeeQuery));

        // Get the existing contracts
        BrokerCommissionContractQuery query = new BrokerCommissionContractQuery();
        query.setBroker(LoggedUser.member());
        query.setMember(brokered);
        query.setStatusList(Arrays.asList(BrokerCommissionContract.Status.ACCEPTED, BrokerCommissionContract.Status.ACTIVE, BrokerCommissionContract.Status.PENDING));
        List<BrokerCommissionContract> contracts = searchBrokerCommissionContracts(query);

        // Filter out the commissions
        for (Iterator<BrokerCommission> it = brokerCommissions.iterator(); it.hasNext();) {
            BrokerCommission brokerCommission = it.next();
            // Commissions already used by existing contracts are not returned
            for (BrokerCommissionContract contract : contracts) {
                if (contract.getBrokerCommission().equals(brokerCommission)) {
                    it.remove();
                    break;
                }
            }
        }

        return brokerCommissions;
    }

    @Override
    public BrokerCommissionContract loadBrokerCommissionContract(final Long id, final Relationship... fetch) {
        return brokerCommissionContractDao.load(id, fetch);
    }

    public BrokeringCommissionStatus loadBrokeringCommissionStatus(final Long id, final Relationship... fetch) {
        return brokeringCommissionStatusDao.load(id, fetch);
    }

    @Override
    public List<DefaultBrokerCommission> loadDefaultBrokerCommissions(final Member broker, final Relationship... fetch) {
        return defaultBrokerCommissionDao.load(broker, fetch);
    }

    @Override
    public int removeBrokerCommissionContracts(final Long... ids) {
        return brokerCommissionContractDao.delete(ids);
    }

    @Override
    public BrokerCommissionContract saveBrokerCommissionContract(BrokerCommissionContract brokerCommissionContract) {
        validateBrokerCommissionContract(brokerCommissionContract);
        if (brokerCommissionContract.isTransient()) {
            brokerCommissionContract = brokerCommissionContractDao.insert(brokerCommissionContract);
            memberNotificationHandler.newCommissionContractNotification(brokerCommissionContract);
            return brokerCommissionContract;
        } else {
            return brokerCommissionContractDao.update(brokerCommissionContract);
        }
    }

    @Override
    public List<DefaultBrokerCommission> saveDefaultBrokerCommissions(final List<DefaultBrokerCommission> commissions) {
        final List<DefaultBrokerCommission> savedList = new ArrayList<DefaultBrokerCommission>();
        for (DefaultBrokerCommission defaultBrokerCommission : commissions) {
            // Skip suspended default broker commissions
            if (!defaultBrokerCommission.isSuspended()) {
                defaultBrokerCommission.setSetByBroker(true);
                defaultBrokerCommission = saveDefaultBrokerCommission(defaultBrokerCommission);
                savedList.add(defaultBrokerCommission);
            }
        }
        return savedList;
    }

    @Override
    public List<BrokerCommissionContract> searchBrokerCommissionContracts(final BrokerCommissionContractQuery query) {
        return brokerCommissionContractDao.search(query);
    }

    public void setBrokerCommissionContractDao(final BrokerCommissionContractDAO brokerCommissionContractDao) {
        this.brokerCommissionContractDao = brokerCommissionContractDao;
    }

    public void setBrokeringCommissionStatusDao(final BrokeringCommissionStatusDAO brokeringCommissionStatusDao) {
        this.brokeringCommissionStatusDao = brokeringCommissionStatusDao;
    }

    public void setBrokeringServiceLocal(final BrokeringServiceLocal brokeringService) {
        this.brokeringService = brokeringService;
    }

    public void setDefaultBrokerCommissionDao(final DefaultBrokerCommissionDAO defaultBrokerCommissionDao) {
        this.defaultBrokerCommissionDao = defaultBrokerCommissionDao;
    }

    public void setFetchServiceLocal(final FetchServiceLocal fetchService) {
        this.fetchService = fetchService;
    }

    public void setGroupServiceLocal(final GroupServiceLocal groupService) {
        this.groupService = groupService;
    }

    public void setMemberNotificationHandler(final MemberNotificationHandler memberNotificationHandler) {
        this.memberNotificationHandler = memberNotificationHandler;
    }

    public void setMemberServiceLocal(final MemberServiceLocal memberService) {
        this.memberService = memberService;
    }

    public void setPermissionServiceLocal(final PermissionServiceLocal permissionService) {
        this.permissionService = permissionService;
    }

    public void setSettingsServiceLocal(final SettingsServiceLocal settingsService) {
        this.settingsService = settingsService;
    }

    public void setTransactionFeeServiceLocal(final TransactionFeeServiceLocal transactionFeeService) {
        this.transactionFeeService = transactionFeeService;
    }

    public void stopCommissions(final BrokerCommission brokerCommission, final BrokerGroup brokerGroup) {
        CacheCleaner cacheCleaner = new CacheCleaner(fetchService);
        final IteratorList<Member> brokers = memberService.iterateByGroup(brokerGroup);
        try {
            for (final Member broker : brokers) {
                stopCommissions(brokerCommission, broker, true);
                cacheCleaner.clearCache();
            }
        } finally {
            DataIteratorHelper.close(brokers);
        }
    }

    @Override
    public void stopCommissions(BrokerCommission brokerCommission, final Member broker, final boolean removeDefaultBrokerCommission) {
        brokerCommission = fetchService.fetch(brokerCommission);
        if (brokerCommission.isFromMember()) {
            final DefaultBrokerCommission defaultBrokerCommission = getDefaultBrokerCommission(broker, brokerCommission);

            if (defaultBrokerCommission != null) {
                if (removeDefaultBrokerCommission) {
                    // Remove default broker commission
                    defaultBrokerCommissionDao.delete(defaultBrokerCommission.getId());
                } else {
                    // Set default broker commission value to 0
                    final Amount zeroAmount = new Amount();
                    zeroAmount.setType(defaultBrokerCommission.getAmount().getType());
                    zeroAmount.setValue(new BigDecimal(0));
                    defaultBrokerCommission.setAmount(zeroAmount);
                    defaultBrokerCommissionDao.update(defaultBrokerCommission);
                }
            }

            // Close broker commission contracts
            closeBrokerCommissionContracts(broker, brokerCommission);
        }

        // Close brokering commission status
        closeBrokeringCommissionStatus(broker, brokerCommission);
    }

    @Override
    public void suspendCommissions(BrokerCommission brokerCommission, final Member broker) {
        brokerCommission = fetchService.fetch(brokerCommission);
        if (brokerCommission.isFromMember()) {
            final DefaultBrokerCommission defaultBrokerCommission = getDefaultBrokerCommission(broker, brokerCommission);

            // Suspend default broker commission
            if (defaultBrokerCommission != null) {
                defaultBrokerCommission.setSuspended(true);
                defaultBrokerCommissionDao.update(defaultBrokerCommission);
            }

            // Suspend broker commission contracts
            suspendBrokerCommissionContracts(broker, brokerCommission);
        }
    }

    @Override
    public void unsuspendCommissions(BrokerCommission brokerCommission, final Member broker) {
        brokerCommission = fetchService.fetch(brokerCommission);
        if (brokerCommission.isFromMember()) {
            final DefaultBrokerCommission defaultBrokerCommission = getDefaultBrokerCommission(broker, brokerCommission);

            // Unsuspend default broker commission
            if (defaultBrokerCommission != null) {
                defaultBrokerCommission.setSuspended(false);
                defaultBrokerCommissionDao.update(defaultBrokerCommission);
            }

            // Unsuspend broker commission contracts
            unsuspendBrokerCommissionContracts(broker, brokerCommission);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void updateBrokerCommissions(final Member broker, final MemberGroup oldGroup, final MemberGroup newGroup) {
        List<BrokerCommission> oldBrokerCommissions = new ArrayList<BrokerCommission>();
        List<BrokerCommission> newBrokerCommissions = new ArrayList<BrokerCommission>();

        if (oldGroup instanceof BrokerGroup) {
            final BrokerGroup oldBrokerGroup = (BrokerGroup) oldGroup;
            final TransactionFeeQuery query = new TransactionFeeQuery();
            query.setEntityType(BrokerCommission.class);
            query.setBrokerGroup(oldBrokerGroup);
            query.setReturnDisabled(true);
            oldBrokerCommissions = (List<BrokerCommission>) transactionFeeService.search(query);
        }

        if (newGroup instanceof BrokerGroup) {
            final BrokerGroup newBrokerGroup = (BrokerGroup) newGroup;
            final TransactionFeeQuery query = new TransactionFeeQuery();
            query.setEntityType(BrokerCommission.class);
            query.setBrokerGroup(newBrokerGroup);
            query.setReturnDisabled(true);
            newBrokerCommissions = (List<BrokerCommission>) transactionFeeService.search(query);
        }

        // Stop charging commissions that are not applicable anymore and remove default broker commissions
        for (final BrokerCommission oldBrokerCommission : oldBrokerCommissions) {
            if (!newBrokerCommissions.contains(oldBrokerCommission)) {
                stopCommissions(oldBrokerCommission, broker, true);
            }
        }

        // Create commissions that now are applicable
        for (final BrokerCommission newBrokerCommission : newBrokerCommissions) {
            if (!oldBrokerCommissions.contains(newBrokerCommission)) {
                if (newBrokerCommission.isFromMember()) {
                    createDefaultBrokerCommission(newBrokerCommission, broker);
                }
                createBrokeringCommissionStatus(broker, newBrokerCommission);
            }
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void updateBrokeringCommissionStatus(final BrokerCommission brokerCommission, final BrokerCommission savedBrokerCommission) {

        Collection<BrokerGroup> allBrokerGroups = null;
        if (brokerCommission.isAllBrokerGroups() || savedBrokerCommission.isAllBrokerGroups()) {
            final GroupQuery query = new GroupQuery();
            query.setNature(Group.Nature.BROKER);
            query.setStatus(Group.Status.NORMAL);
            query.setOnlyActive(true);
            allBrokerGroups = (Collection<BrokerGroup>) groupService.search(query);
        }

        Collection<BrokerGroup> brokerGroups = null;
        Collection<BrokerGroup> savedBrokerGroups = null;

        // Current groups of brokers
        if (brokerCommission.isAllBrokerGroups()) {
            brokerGroups = allBrokerGroups;
        } else {
            brokerGroups = fetchService.fetch(brokerCommission.getBrokerGroups());
        }

        // Previous groups of brokers
        if (savedBrokerCommission.isAllBrokerGroups()) {
            savedBrokerGroups = allBrokerGroups;
        } else {
            savedBrokerGroups = savedBrokerCommission.getBrokerGroups();
        }

        // Create brokering commission status for groups added to the commission
        for (final BrokerGroup brokerGroup : brokerGroups) {
            if (!savedBrokerGroups.contains(brokerGroup)) {
                createBrokeringCommissionStatus(brokerGroup, brokerCommission);
            } else {
                updateBrokeringCommissionStatus(brokerGroup, brokerCommission);
            }
        }

        // Stop commissions charging for groups removed from the commission
        for (final BrokerGroup savedBrokerGroup : savedBrokerGroups) {
            if (!brokerGroups.contains(savedBrokerGroup)) {
                stopCommissions(brokerCommission, savedBrokerGroup);
            }
        }
    }

    @Override
    public BrokeringCommissionStatus updateBrokeringCommissionStatus(final BrokeringCommissionStatus brokeringCommissionStatus) {
        return brokeringCommissionStatusDao.update(brokeringCommissionStatus);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void updateDefaultBrokerCommissions(final BrokerCommission brokerCommission, final BrokerCommission savedBrokerCommission) {

        Collection<BrokerGroup> allBrokerGroups = null;
        if (brokerCommission.isAllBrokerGroups() || savedBrokerCommission.isAllBrokerGroups()) {
            final GroupQuery query = new GroupQuery();
            query.setNature(Group.Nature.BROKER);
            query.setStatus(Group.Status.NORMAL);
            query.setOnlyActive(true);
            allBrokerGroups = (Collection<BrokerGroup>) groupService.search(query);
        }

        Collection<BrokerGroup> brokerGroups = null;
        Collection<BrokerGroup> savedBrokerGroups = null;

        // Current groups of brokers
        if (brokerCommission.isAllBrokerGroups()) {
            brokerGroups = allBrokerGroups;
        } else {
            brokerGroups = fetchService.fetch(brokerCommission.getBrokerGroups());
        }

        // Previous groups of brokers
        if (savedBrokerCommission.isAllBrokerGroups()) {
            savedBrokerGroups = allBrokerGroups;
        } else {
            savedBrokerGroups = savedBrokerCommission.getBrokerGroups();
        }

        for (final BrokerGroup brokerGroup : brokerGroups) {
            if (!savedBrokerGroups.contains(brokerGroup)) {
                // Create default broker commissions for groups added to the commission
                createDefaultBrokerCommissions(brokerCommission, brokerGroup);
            } else {
                // If the broker commission was already applicable to the broker group, update default broker commissions that were not customized by
                // the broker and that had not been charged yet
                updateDefaultBrokerCommissions(brokerCommission, brokerGroup);
            }
        }

        // Stop commissions charging for groups removed from the commission
        for (final BrokerGroup savedBrokerGroup : savedBrokerGroups) {
            if (!brokerGroups.contains(savedBrokerGroup)) {
                stopCommissions(brokerCommission, savedBrokerGroup);
            }
        }
    }

    @Override
    public void validateBrokerCommissionContract(final BrokerCommissionContract brokerCommissionContract) throws ValidationException {
        getBrokerCommissionContractValidator().validate(brokerCommissionContract);
    }

    @Override
    public void validateDefaultBrokerCommissions(final List<DefaultBrokerCommission> defaultBrokerCommissions) throws ValidationException {
        final Validator defaultBrokerCommissionValidator = getDefaultBrokerCommissionValidator();
        for (final DefaultBrokerCommission defaultBrokerCommission : defaultBrokerCommissions) {
            defaultBrokerCommissionValidator.validate(defaultBrokerCommission);
        }
    }

    private void closeBrokerCommissionContracts(final Member broker, final BrokerCommission brokerCommission) {
        final List<BrokerCommissionContract.Status> statusList = new ArrayList<BrokerCommissionContract.Status>();
        statusList.add(BrokerCommissionContract.Status.PENDING);
        statusList.add(BrokerCommissionContract.Status.ACTIVE);

        final BrokerCommissionContractQuery query = new BrokerCommissionContractQuery();
        query.setBroker(broker);
        query.setBrokerCommission(brokerCommission);
        query.setStatusList(statusList);
        final List<BrokerCommissionContract> brokerCommissionContracts = brokerCommissionContractDao.search(query);
        for (final BrokerCommissionContract brokerCommissionContract : brokerCommissionContracts) {
            brokerCommissionContract.setStatus(BrokerCommissionContract.Status.CLOSED);
            brokerCommissionContractDao.update(brokerCommissionContract);
        }
    }

    private void closeBrokeringCommissionStatus(final Member broker, final BrokerCommission brokerCommission) {
        final Calendar now = Calendar.getInstance();
        final BrokeringCommissionStatusQuery query = new BrokeringCommissionStatusQuery();
        query.setBroker(broker);
        query.setBrokerCommission(brokerCommission);
        query.setOnlyActive(true);
        final List<BrokeringCommissionStatus> statusList = brokeringCommissionStatusDao.search(query);
        for (final BrokeringCommissionStatus status : statusList) {
            status.getPeriod().setEnd(now);
            brokeringCommissionStatusDao.update(status);
        }
    }

    private void createBrokeringCommissionStatus(final BrokerGroup brokerGroup, final BrokerCommission brokerCommission) {
        CacheCleaner cacheCleaner = new CacheCleaner(fetchService);
        final IteratorList<Member> brokers = memberService.iterateByGroup(brokerGroup);
        try {
            for (final Member broker : brokers) {
                createBrokeringCommissionStatus(broker, brokerCommission);
                cacheCleaner.clearCache();
            }
        } finally {
            DataIteratorHelper.close(brokers);
        }
    }

    private void createBrokeringCommissionStatus(final Member broker, final BrokerCommission brokerCommission) {
        final BrokeringQuery brokeringQuery = new BrokeringQuery();
        brokeringQuery.setBroker(broker);
        final List<Brokering> brokerings = brokeringService.search(brokeringQuery);
        for (final Brokering brokering : brokerings) {
            getOrUpdateBrokeringCommissionStatus(brokering, brokerCommission);
        }
    }

    private DefaultBrokerCommission createDefaultBrokerCommission(final BrokerCommission brokerCommission, final Member broker) {
        final DefaultBrokerCommission defaultBrokerCommission = new DefaultBrokerCommission();
        defaultBrokerCommission.setBroker(broker);
        defaultBrokerCommission.setBrokerCommission(brokerCommission);
        defaultBrokerCommission.setAmount(brokerCommission.getAmount());
        defaultBrokerCommission.setWhen(brokerCommission.getWhen());
        defaultBrokerCommission.setCount(brokerCommission.getCount());
        saveDefaultBrokerCommission(defaultBrokerCommission);
        return defaultBrokerCommission;
    }

    private void createDefaultBrokerCommissions(final BrokerCommission brokerCommission, final BrokerGroup brokerGroup) {
        CacheCleaner cacheCleaner = new CacheCleaner(fetchService);
        final IteratorList<Member> brokers = memberService.iterateByGroup(brokerGroup);
        try {
            for (final Member broker : brokers) {
                createDefaultBrokerCommission(brokerCommission, broker);
                cacheCleaner.clearCache();
            }
        } finally {
            DataIteratorHelper.close(brokers);
        }
    }

    private BrokerCommissionContract getBrokerCommissionContract(final Member member, final Member broker, final BrokerCommission brokerCommission) {
        // First, check if there is an active contract ...
        final BrokerCommissionContractQuery query = new BrokerCommissionContractQuery();
        query.setMember(member);
        query.setBroker(broker);
        query.setBrokerCommission(brokerCommission);
        query.setStatus(BrokerCommissionContract.Status.ACTIVE);
        query.setUniqueResult();
        Collection<BrokerCommissionContract> contracts = searchBrokerCommissionContracts(query);
        if (CollectionUtils.isNotEmpty(contracts)) {
            return contracts.iterator().next();
        }

        // ... if there is not, check if there is a pending contract ...
        query.setStatus(BrokerCommissionContract.Status.PENDING);
        contracts = searchBrokerCommissionContracts(query);
        if (CollectionUtils.isNotEmpty(contracts)) {
            return contracts.iterator().next();
        }

        // ... else, return null
        return null;
    }

    private Validator getBrokerCommissionContractValidator() {
        final Validator brokerCommissionContractValidator = new Validator("brokerCommissionContract");
        brokerCommissionContractValidator.property("brokering").required();
        brokerCommissionContractValidator.property("brokerCommission").required();
        brokerCommissionContractValidator.property("period.begin").key("brokerCommissionContract.startDate").required().futureOrToday();
        brokerCommissionContractValidator.property("amount.type").key("brokerCommissionContract.amount").required();
        brokerCommissionContractValidator.property("amount.value").key("brokerCommissionContract.amount").required();
        brokerCommissionContractValidator.property("status").required().add(new StatusValidation());
        brokerCommissionContractValidator.general(new ConflictingContractValidation());
        return brokerCommissionContractValidator;
    }

    private Validator getDefaultBrokerCommissionValidator() {
        final Validator defaultBrokerCommissionValidator = new Validator("defaultBrokerCommission");
        defaultBrokerCommissionValidator.property("broker").required();
        defaultBrokerCommissionValidator.property("brokerCommission").required();
        defaultBrokerCommissionValidator.property("amount.type").key("transactionFee.amount").required();
        defaultBrokerCommissionValidator.property("amount.value").key("transactionFee.amount").required();
        defaultBrokerCommissionValidator.property("when").required();
        defaultBrokerCommissionValidator.general(new DefaultBrokerCommissionValidation());
        return defaultBrokerCommissionValidator;
    }

    private BrokeringCommissionStatus getOrUpdateBrokeringCommissionStatus(final Brokering brokering, final BrokerCommission brokerCommission, final Amount amount, final BrokerCommission.When when, final Integer count) {
        BrokeringCommissionStatus brokeringCommissionStatus = getBrokeringCommissionStatus(brokering, brokerCommission);
        if (brokeringCommissionStatus == null) {
            brokeringCommissionStatus = createBrokeringCommissionStatus(brokering, brokerCommission);
        } else {
            // If the commission had not been charged yet and the brokering commission status is not closed, the brokering commission status can be
            // updated
            if (brokeringCommissionStatus.getTotal().getCount() == 0 && brokeringCommissionStatus.getPeriod().getEnd() == null) {
                brokeringCommissionStatus.setAmount(amount);
                brokeringCommissionStatus.setWhen(when);
                brokeringCommissionStatus.setMaxCount(count);
                brokeringCommissionStatus = brokeringCommissionStatusDao.update(brokeringCommissionStatus);
            }
        }
        return brokeringCommissionStatus;
    }

    /**
     * Saves the default broker commission, returning the updated instance
     */
    private DefaultBrokerCommission saveDefaultBrokerCommission(DefaultBrokerCommission defaultBrokerCommission) {
        if (defaultBrokerCommission.getWhen() == BrokerCommission.When.ALWAYS) {
            defaultBrokerCommission.setCount(null);
        }
        if (defaultBrokerCommission.isTransient()) {
            defaultBrokerCommission = defaultBrokerCommissionDao.insert(defaultBrokerCommission);
        } else {
            defaultBrokerCommission = defaultBrokerCommissionDao.update(defaultBrokerCommission);
        }
        updateBrokeringCommissionStatus(defaultBrokerCommission);
        return defaultBrokerCommission;
    }

    private void suspendBrokerCommissionContracts(final Member broker, final BrokerCommission brokerCommission) {
        final BrokerCommissionContractQuery query = new BrokerCommissionContractQuery();
        query.setBroker(broker);
        query.setBrokerCommission(brokerCommission);
        query.setStatusList(Arrays.asList(BrokerCommissionContract.Status.PENDING, BrokerCommissionContract.Status.ACCEPTED, BrokerCommissionContract.Status.ACTIVE));
        final List<BrokerCommissionContract> brokerCommissionContracts = brokerCommissionContractDao.search(query);
        for (final BrokerCommissionContract brokerCommissionContract : brokerCommissionContracts) {
            brokerCommissionContract.setStatusBeforeSuspension(brokerCommissionContract.getStatus());
            brokerCommissionContract.setStatus(BrokerCommissionContract.Status.SUSPENDED);
            brokerCommissionContractDao.update(brokerCommissionContract);
        }
    }

    private void unsuspendBrokerCommissionContracts(final Member broker, final BrokerCommission brokerCommission) {
        final Calendar today = DateHelper.truncate(Calendar.getInstance());
        final BrokerCommissionContractQuery query = new BrokerCommissionContractQuery();
        query.setBroker(broker);
        query.setBrokerCommission(brokerCommission);
        query.setStatus(BrokerCommissionContract.Status.SUSPENDED);
        final List<BrokerCommissionContract> brokerCommissionContracts = brokerCommissionContractDao.search(query);
        for (final BrokerCommissionContract brokerCommissionContract : brokerCommissionContracts) {
            final BrokerCommissionContract.Status statusBeforeSuspension = brokerCommissionContract.getStatusBeforeSuspension();
            final Calendar beginOfContract = brokerCommissionContract.getPeriod().getBegin();
            final Calendar endOfContract = brokerCommissionContract.getPeriod().getEnd();
            if (statusBeforeSuspension == BrokerCommissionContract.Status.ACTIVE) {
                // If the contract is expired (ends before the beginning of today), close it
                if (endOfContract.compareTo(today) < 0) {
                    brokerCommissionContract.setStatus(BrokerCommissionContract.Status.CLOSED);
                } else {
                    brokerCommissionContract.setStatus(BrokerCommissionContract.Status.ACTIVE);
                }
            } else if (statusBeforeSuspension == BrokerCommissionContract.Status.ACCEPTED) {
                // If the contract is expired (ends before the beginning of today), close it
                if (endOfContract.compareTo(today) < 0) {
                    brokerCommissionContract.setStatus(BrokerCommissionContract.Status.CLOSED);
                } else if (beginOfContract.compareTo(today) <= 0) {
                    // If the contract begins before today and it was already accepted, activate it
                    brokerCommissionContract.setStatus(BrokerCommissionContract.Status.ACTIVE);
                } else {
                    // The contract goes back to "ACCEPTED" status
                    brokerCommissionContract.setStatus(BrokerCommissionContract.Status.ACCEPTED);
                }
            } else {
                // If the contract begins before today and was not accepted yet, expire it
                if (beginOfContract.compareTo(today) <= 0) {
                    brokerCommissionContract.setStatus(BrokerCommissionContract.Status.EXPIRED);
                } else {
                    brokerCommissionContract.setStatus(BrokerCommissionContract.Status.PENDING);
                }
            }
            brokerCommissionContract.setStatusBeforeSuspension(null);
            brokerCommissionContractDao.update(brokerCommissionContract);
        }
    }

    private void updateBrokeringCommissionStatus(final BrokerGroup brokerGroup, final BrokerCommission brokerCommission) {
        CacheCleaner cacheCleaner = new CacheCleaner(fetchService);
        final IteratorList<Member> brokers = memberService.iterateByGroup(brokerGroup);
        try {
            for (final Member broker : brokers) {
                updateBrokeringCommissionStatus(broker, brokerCommission);
                cacheCleaner.clearCache();
            }
        } finally {
            DataIteratorHelper.close(brokers);
        }
    }

    /*
     * Create a new brokering commission status (for each brokering) if it does not exist and update the existing ones
     */
    private void updateBrokeringCommissionStatus(final DefaultBrokerCommission defaultBrokerCommission) {
        final Member broker = defaultBrokerCommission.getBroker();
        final BrokerCommission brokerCommission = defaultBrokerCommission.getBrokerCommission();

        final BrokeringQuery brokeringQuery = new BrokeringQuery();
        brokeringQuery.setBroker(broker);
        final List<Brokering> brokerings = brokeringService.search(brokeringQuery);
        for (final Brokering brokering : brokerings) {
            getOrUpdateBrokeringCommissionStatus(brokering, brokerCommission, defaultBrokerCommission.getAmount(), defaultBrokerCommission.getWhen(), defaultBrokerCommission.getCount());
        }
    }

    private void updateBrokeringCommissionStatus(final Member broker, final BrokerCommission brokerCommission) {
        final BrokeringQuery brokeringQuery = new BrokeringQuery();
        brokeringQuery.setBroker(broker);
        final List<Brokering> brokerings = brokeringService.search(brokeringQuery);
        for (final Brokering brokering : brokerings) {
            getOrUpdateBrokeringCommissionStatus(brokering, brokerCommission);
        }
    }

    private void updateDefaultBrokerCommission(final BrokerCommission brokerCommission, final Member broker) {
        DefaultBrokerCommission defaultBrokerCommission = getDefaultBrokerCommission(broker, brokerCommission);
        if (defaultBrokerCommission == null) {
            defaultBrokerCommission = createDefaultBrokerCommission(brokerCommission, broker);
        }
        if (!defaultBrokerCommission.isSetByBroker()) {
            final BrokeringCommissionStatusQuery query = new BrokeringCommissionStatusQuery();
            query.setBroker(broker);
            query.setBrokerCommission(brokerCommission);
            final List<BrokeringCommissionStatus> statusList = brokeringCommissionStatusDao.search(query);
            boolean canUpdate = true;
            for (final BrokeringCommissionStatus status : statusList) {
                if (status.getTotal().getAmount().compareTo(new BigDecimal(0)) > 0) {
                    canUpdate = false;
                    break;
                }
            }
            if (canUpdate) {
                defaultBrokerCommission.setAmount(brokerCommission.getAmount());
                defaultBrokerCommissionDao.update(defaultBrokerCommission);
                updateBrokeringCommissionStatus(defaultBrokerCommission);
            }
        }
    }

    private void updateDefaultBrokerCommissions(final BrokerCommission brokerCommission, final BrokerGroup brokerGroup) {
        CacheCleaner cacheCleaner = new CacheCleaner(fetchService);
        final IteratorList<Member> brokers = memberService.iterateByGroup(brokerGroup);
        try {
            for (final Member broker : brokers) {
                updateDefaultBrokerCommission(brokerCommission, broker);
                cacheCleaner.clearCache();
            }
        } finally {
            DataIteratorHelper.close(brokers);
        }
    }

}
