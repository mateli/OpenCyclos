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

import java.util.Calendar;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import nl.strohalm.cyclos.dao.members.ElementDAO;
import nl.strohalm.cyclos.dao.members.RemarkDAO;
import nl.strohalm.cyclos.dao.members.brokerings.BrokerCommissionContractDAO;
import nl.strohalm.cyclos.dao.members.brokerings.BrokeringCommissionStatusDAO;
import nl.strohalm.cyclos.dao.members.brokerings.BrokeringDAO;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.fees.transaction.BrokerCommission;
import nl.strohalm.cyclos.entities.accounts.fees.transaction.TransactionFeeQuery;
import nl.strohalm.cyclos.entities.exceptions.UnexpectedEntityException;
import nl.strohalm.cyclos.entities.groups.BrokerGroup;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.members.BrokeringQuery;
import nl.strohalm.cyclos.entities.members.BrokeringQuery.Status;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.FullTextMemberQuery;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.members.brokerings.BrokerCommissionContract;
import nl.strohalm.cyclos.entities.members.brokerings.BrokerCommissionContractQuery;
import nl.strohalm.cyclos.entities.members.brokerings.Brokering;
import nl.strohalm.cyclos.entities.members.brokerings.BrokeringCommissionStatus;
import nl.strohalm.cyclos.entities.members.brokerings.BrokeringCommissionStatusQuery;
import nl.strohalm.cyclos.entities.members.remarks.BrokerRemark;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.services.InitializingService;
import nl.strohalm.cyclos.services.elements.exceptions.MemberAlreadyInBrokeringsException;
import nl.strohalm.cyclos.services.fetch.FetchServiceLocal;
import nl.strohalm.cyclos.services.settings.SettingsServiceLocal;
import nl.strohalm.cyclos.services.transfertypes.TransactionFeeServiceLocal;
import nl.strohalm.cyclos.utils.CacheCleaner;
import nl.strohalm.cyclos.utils.DataIteratorHelper;
import nl.strohalm.cyclos.utils.DateHelper;
import nl.strohalm.cyclos.utils.TimePeriod;
import nl.strohalm.cyclos.utils.access.LoggedUser;
import nl.strohalm.cyclos.utils.notifications.MemberNotificationHandler;
import nl.strohalm.cyclos.utils.query.QueryParameters.ResultType;
import nl.strohalm.cyclos.utils.validation.InvalidError;
import nl.strohalm.cyclos.utils.validation.PropertyValidation;
import nl.strohalm.cyclos.utils.validation.RequiredError;
import nl.strohalm.cyclos.utils.validation.ValidationError;
import nl.strohalm.cyclos.utils.validation.ValidationException;
import nl.strohalm.cyclos.utils.validation.Validator;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

/**
 * Implementation for brokering service
 * @author luis
 */
public class BrokeringServiceImpl implements BrokeringServiceLocal, InitializingService {

    /**
     * Validates a property as being a broker
     * @author luis
     */
    public final class BrokerValidation implements PropertyValidation {
        private static final long serialVersionUID = 580603314020373024L;

        @Override
        public ValidationError validate(final Object object, final Object name, final Object value) {
            final ChangeBrokerDTO dto = (ChangeBrokerDTO) object;
            final Member member = fetchService.fetch((Member) value, Element.Relationships.GROUP);
            if (value == null) {
                return null;
            }
            if (member != null && !member.getMemberGroup().isBroker()) {
                return new InvalidError();
            }
            final MemberGroup viewerGroup = (MemberGroup) member.getGroup();
            final Member brokeredMember = fetchService.fetch(dto.getMember());

            if (!viewerGroup.getCanViewProfileOfGroups().contains(brokeredMember.getGroup())) {
                throw new ValidationException();
            }

            return null;
        }
    }

    private BrokerCommissionContractDAO  brokerCommissionContractDao;
    private BrokeringCommissionStatusDAO brokeringCommissionStatusDao;
    private BrokeringDAO                 brokeringDao;
    private CommissionServiceLocal       commissionService;
    private ElementServiceLocal          elementService;
    private ElementDAO                   elementDao;
    private FetchServiceLocal            fetchService;
    private RemarkDAO                    remarkDao;
    private SettingsServiceLocal         settingsService;
    private TransactionFeeServiceLocal   transactionFeeService;

    private MemberNotificationHandler    memberNotificationHandler;

    @Override
    @SuppressWarnings("unchecked")
    public BulkMemberActionResultVO bulkChangeMemberBroker(final FullTextMemberQuery query, Member newBroker, final boolean suspendCommission, final String comments) {
        if (newBroker == null || newBroker.isTransient()) {
            throw new ValidationException();
        }
        if (StringUtils.isEmpty(comments)) {
            throw new ValidationException();
        }
        newBroker = fetchService.fetch(newBroker);

        int changed = 0;
        int unchanged = 0;
        // force the result type to ITERATOR to avoid load all members in memory
        query.setIterateAll();
        final List<Member> members = (List<Member>) elementService.fullTextSearch(query);
        CacheCleaner cacheCleaner = new CacheCleaner(fetchService);
        for (final Member member : members) {
            if (newBroker.equals(member.getBroker())) {
                unchanged++;
            } else {
                final ChangeBrokerDTO dto = new ChangeBrokerDTO();
                dto.setMember(member);
                dto.setNewBroker(newBroker);
                dto.setSuspendCommission(suspendCommission);
                dto.setComments(comments);
                changeBroker(dto);
                changed++;

                cacheCleaner.clearCache();
            }
        }
        return new BulkMemberActionResultVO(changed, unchanged);
    }

    @Override
    public Brokering changeBroker(final ChangeBrokerDTO dto) {
        memberNotificationHandler.removedBrokeringNotification(dto);
        return doChangeBroker(dto);
    }

    /**
     * Creates a new brokering
     */
    @Override
    public Brokering create(final Member broker, final Member brokered) {
        Brokering brokering = new Brokering();
        brokering.setBroker(broker);
        brokering.setBrokered(brokered);
        brokering.setStartDate(Calendar.getInstance());
        brokering = brokeringDao.insert(brokering);

        // Create brokering commission status
        createBrokeringCommissionStatus(brokering);

        return brokering;
    }

    @Override
    public Brokering getActiveBrokering(Member member) {
        member = fetchService.fetch(member, Member.Relationships.BROKER);
        if (member.getBroker() == null) {
            return null;
        }

        // Verifies if there is an active broker for the subject
        final BrokeringQuery query = new BrokeringQuery();
        query.fetch(Brokering.Relationships.BROKER);
        query.setBroker(member.getBroker());
        query.setBrokered(member);
        query.setStatus(BrokeringQuery.Status.ACTIVE);
        query.setUniqueResult();
        final List<Brokering> list = search(query);
        if (list != null && !list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public Brokering getBrokering(final Member broker, final Member member) throws ValidationException {
        final BrokeringQuery brokeringQuery = new BrokeringQuery();
        brokeringQuery.setBroker(broker);
        brokeringQuery.setBrokered(member);
        brokeringQuery.setStatus(BrokeringQuery.Status.ACTIVE);
        final List<Brokering> brokerings = search(brokeringQuery);
        if (CollectionUtils.isEmpty(brokerings)) {
            throw new ValidationException();
        }
        final Brokering brokering = brokerings.iterator().next();
        return brokering;
    }

    public ElementDAO getElementDao() {
        return elementDao;
    }

    @Override
    public void initializeService() {
        removeExpiredBrokerings(Calendar.getInstance());
    }

    @Override
    public Collection<Status> listPossibleStatuses(BrokerGroup brokerGroup) {
        brokerGroup = fetchService.fetch(brokerGroup, BrokerGroup.Relationships.POSSIBLE_INITIAL_GROUPS);
        boolean hasInactive = false;
        Collection<MemberGroup> possibleInitialGroups = brokerGroup.getPossibleInitialGroups();
        for (MemberGroup group : possibleInitialGroups) {
            if (!group.isActive()) {
                hasInactive = true;
                break;
            }
        }
        return hasInactive ? EnumSet.allOf(BrokeringQuery.Status.class) : EnumSet.complementOf(EnumSet.of(BrokeringQuery.Status.PENDING));
    }

    @Override
    public Brokering load(final Long id, final Relationship... fetch) {
        return brokeringDao.load(id, fetch);
    }

    @Override
    public Brokering remove(final Brokering brokering, final String remark) throws UnexpectedEntityException {
        if (brokering == null || brokering.isTransient() || brokering.getEndDate() != null) {
            throw new UnexpectedEntityException();
        }

        final ChangeBrokerDTO dto = new ChangeBrokerDTO();
        dto.setComments(remark);
        dto.setMember(brokering.getBrokered());
        dto.setNewBroker(null);
        return doChangeBroker(dto);
    }

    @Override
    public void removeExpiredBrokerings(final Calendar time) {
        final LocalSettings localSettings = settingsService.getLocalSettings();
        final TimePeriod brokeringExpirationPeriod = localSettings.getBrokeringExpirationPeriod();
        if (brokeringExpirationPeriod == null || brokeringExpirationPeriod.getNumber() <= 0) {
            return;
        }

        final Calendar startDate = brokeringExpirationPeriod.remove(DateHelper.truncate(time));
        final BrokeringQuery query = new BrokeringQuery();
        query.setResultType(ResultType.ITERATOR);
        query.setStatus(BrokeringQuery.Status.ACTIVE);
        query.setStartExpirationDate(startDate);
        CacheCleaner cleaner = new CacheCleaner(fetchService);
        final List<Brokering> expired = search(query);
        try {
            for (final Brokering brokering : expired) {
                // Update the brokering, expiring it
                brokering.setEndDate(time);
                brokeringDao.update(brokering);
                memberNotificationHandler.expiredBrokeringNotification(brokering);
                cleaner.clearCache();
            }
        } finally {
            DataIteratorHelper.close(expired);
        }
    }

    @Override
    public List<Brokering> search(final BrokeringQuery query) {
        return brokeringDao.search(query);
    }

    public void setBrokerCommissionContractDao(final BrokerCommissionContractDAO brokerCommissionContractDao) {
        this.brokerCommissionContractDao = brokerCommissionContractDao;
    }

    public void setBrokeringCommissionStatusDao(final BrokeringCommissionStatusDAO brokeringCommissionStatusDao) {
        this.brokeringCommissionStatusDao = brokeringCommissionStatusDao;
    }

    public void setBrokeringDao(final BrokeringDAO brokeringDAO) {
        brokeringDao = brokeringDAO;
    }

    public void setCommissionServiceLocal(final CommissionServiceLocal commissionService) {
        this.commissionService = commissionService;
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

    public void setMemberNotificationHandler(final MemberNotificationHandler memberNotificationHandler) {
        this.memberNotificationHandler = memberNotificationHandler;
    }

    public void setRemarkDao(final RemarkDAO remarkDao) {
        this.remarkDao = remarkDao;
    }

    public void setSettingsServiceLocal(final SettingsServiceLocal settingsService) {
        this.settingsService = settingsService;
    }

    public void setTransactionFeeServiceLocal(final TransactionFeeServiceLocal transactionFeeService) {
        this.transactionFeeService = transactionFeeService;
    }

    @Override
    public void validate(final Brokering brokering) throws ValidationException {
        getBrokeringValidator().validate(brokering);
    }

    @Override
    public void validate(final ChangeBrokerDTO dto) throws ValidationException {
        getDtoValidator().validate(dto);
    }

    private void closeBrokerContractsAndBrokeringStatus(final Brokering brokering) {
        // Close active broker commission contracts
        final BrokerCommissionContractQuery bccQuery = new BrokerCommissionContractQuery();
        bccQuery.setBroker(brokering.getBroker());
        bccQuery.setMember(brokering.getBrokered());
        bccQuery.setStatus(BrokerCommissionContract.Status.ACTIVE);
        final List<BrokerCommissionContract> brokerCommissionContracts = brokerCommissionContractDao.search(bccQuery);
        for (final BrokerCommissionContract brokerCommissionContract : brokerCommissionContracts) {
            brokerCommissionContract.setStatus(BrokerCommissionContract.Status.CLOSED);
            brokerCommissionContractDao.update(brokerCommissionContract);
        }

        // Close active brokering commission status
        final Calendar now = Calendar.getInstance();
        final BrokeringCommissionStatusQuery bcsQuery = new BrokeringCommissionStatusQuery();
        bcsQuery.setBrokering(brokering);
        bcsQuery.setOnlyActive(true);
        final List<BrokeringCommissionStatus> brokeringCommissionStatusList = brokeringCommissionStatusDao.search(bcsQuery);
        for (final BrokeringCommissionStatus brokeringCommissionStatus : brokeringCommissionStatusList) {
            brokeringCommissionStatus.getPeriod().setEnd(now);
            brokeringCommissionStatusDao.update(brokeringCommissionStatus);
        }

    }

    /*
     * Creates a new brokering commission status for each broker commission and the specified brokering. This method is called by doChangeBroker()
     */
    @SuppressWarnings("unchecked")
    private void createBrokeringCommissionStatus(final Brokering brokering) {
        final Member broker = fetchService.fetch(brokering.getBroker(), Element.Relationships.GROUP);
        final BrokerGroup brokerGroup = (BrokerGroup) broker.getGroup();

        // Create a brokering commission status for each broker commission applicable to the broker
        final TransactionFeeQuery query = new TransactionFeeQuery();
        query.setEntityType(BrokerCommission.class);
        query.setBrokerGroup(brokerGroup);
        query.setReturnDisabled(true);
        final List<BrokerCommission> brokerCommissions = (List<BrokerCommission>) transactionFeeService.search(query);
        for (final BrokerCommission brokerCommission : brokerCommissions) {
            commissionService.createBrokeringCommissionStatus(brokering, brokerCommission);
        }
    }

    private Brokering doChangeBroker(final ChangeBrokerDTO dto) {
        validate(dto);
        final Calendar now = Calendar.getInstance();
        final Member member = fetchService.reload(dto.getMember(), Element.Relationships.USER);
        final Brokering oldBrokering = getActiveBrokering(member);
        final Member oldBroker = (oldBrokering == null) ? null : oldBrokering.getBroker();
        final Member newBroker = fetchService.fetch(dto.getNewBroker(), Member.Relationships.BROKER);

        // Check if it's just to suspend commission
        final boolean justSuspendCommission = (oldBroker != null && oldBroker.equals(newBroker) && dto.isSuspendCommission());

        if (!justSuspendCommission) {

            // Check for circular brokerings
            if (member.equals(newBroker)) {
                throw new ValidationException("brokering.error.circularBrokering");
            }
            Member current = newBroker;
            final Set<Member> visited = new HashSet<Member>();
            while (current != null) {
                if (visited.contains(current)) {
                    throw new ValidationException("brokering.error.circularBrokering");
                }
                visited.add(current);
                current = fetchService.fetch(current, Member.Relationships.BROKER).getBroker();
            }

            // Check if the member is already on the brokering list
            if (newBroker != null) {
                final BrokeringQuery query = new BrokeringQuery();
                query.setBroker(newBroker);
                query.setBrokered(member);
                query.setStatus(BrokeringQuery.Status.ACTIVE);
                if (!search(query).isEmpty()) {
                    throw new MemberAlreadyInBrokeringsException();
                }
            }

            // Update the brokered member
            member.setBroker(newBroker);
            elementDao.update(member, false);

            // Reindex the element
            elementDao.addToIndex(member);
        }

        // Update the active brokering
        if (oldBrokering != null) {
            if (!justSuspendCommission) {
                oldBrokering.setEndDate(now);
            }
            brokeringDao.update(oldBrokering, false);

            // Close broker commission contracts and brokering commission status
            closeBrokerContractsAndBrokeringStatus(oldBrokering);
        }

        // Build a brokering to return
        Brokering brokering;
        if (justSuspendCommission) {
            // If just suspending commission, the result will be the same brokering
            brokering = oldBrokering;
        } else if (newBroker != null) {
            // Create a new brokering if not just suspending commission
            brokering = new Brokering();
            brokering.setBroker(newBroker);
            brokering.setBrokered(member);
            brokering.setStartDate(now);
            brokering = brokeringDao.insert(brokering, false);
            createBrokeringCommissionStatus(brokering);
        } else {
            // No new broker - return null
            brokering = null;
        }

        // Create the broker remark
        final BrokerRemark remark = new BrokerRemark();
        remark.setWriter(LoggedUser.element());
        remark.setSubject(member);
        remark.setDate(now);
        remark.setComments(dto.getComments());
        remark.setOldBroker(oldBroker);
        remark.setNewBroker(newBroker);
        remark.setSuspendCommission(dto.isSuspendCommission());
        remarkDao.insert(remark);

        return brokering;
    }

    private Validator getBrokeringValidator() {
        final Validator brokeringValidator = new Validator();
        brokeringValidator.property("broker").key("member.broker").required().add(new BrokerValidation());
        brokeringValidator.property("brokered").key("member.member").required();
        brokeringValidator.property("notes").key("brokering.notes").maxLength(1000);
        return brokeringValidator;
    }

    private Validator getDtoValidator() {
        final Validator dtoValidator = new Validator();
        dtoValidator.property("member").required();
        dtoValidator.property("newBroker").key("changeBroker.new").add(new BrokerValidation()).add(new PropertyValidation() {
            private static final long serialVersionUID = 580603314020373024L;

            @Override
            public ValidationError validate(final Object object, final Object name, final Object value) {
                final ChangeBrokerDTO dto = (ChangeBrokerDTO) object;
                final Member newBroker = (Member) value;
                if (dto.isSuspendCommission()) {
                    // When commission is suspended, there must be a new broker
                    if (newBroker == null || newBroker.isTransient()) {
                        return new RequiredError();
                    }
                } else {
                    // The old and new brokers can be the same only when suspending commission
                    final Member member = fetchService.fetch(dto.getMember());
                    if (member != null && newBroker != null && newBroker.equals(member.getBroker())) {
                        return new InvalidError();
                    }
                }
                return null;
            }
        });
        dtoValidator.property("comments").key("remark.comments").required().maxLength(4000);
        return dtoValidator;
    }
}
