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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import nl.strohalm.cyclos.access.AdminMemberPermission;
import nl.strohalm.cyclos.access.BrokerPermission;
import nl.strohalm.cyclos.access.MemberPermission;
import nl.strohalm.cyclos.access.OperatorPermission;
import nl.strohalm.cyclos.dao.members.ReferenceDAO;
import nl.strohalm.cyclos.dao.members.ReferenceHistoryDAO;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.transactions.Payment;
import nl.strohalm.cyclos.entities.accounts.transactions.PaymentAwaitingFeedbackDTO;
import nl.strohalm.cyclos.entities.accounts.transactions.ScheduledPayment;
import nl.strohalm.cyclos.entities.accounts.transactions.Transfer;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferType;
import nl.strohalm.cyclos.entities.alerts.Alert;
import nl.strohalm.cyclos.entities.alerts.AlertQuery;
import nl.strohalm.cyclos.entities.alerts.MemberAlert;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.entities.exceptions.UnexpectedEntityException;
import nl.strohalm.cyclos.entities.groups.AdminGroup;
import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.members.GeneralReference;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.members.PaymentsAwaitingFeedbackQuery;
import nl.strohalm.cyclos.entities.members.Reference;
import nl.strohalm.cyclos.entities.members.Reference.Level;
import nl.strohalm.cyclos.entities.members.Reference.Nature;
import nl.strohalm.cyclos.entities.members.ReferenceHistoryLog;
import nl.strohalm.cyclos.entities.members.ReferenceHistoryLogQuery;
import nl.strohalm.cyclos.entities.members.ReferenceQuery;
import nl.strohalm.cyclos.entities.members.TransactionFeedback;
import nl.strohalm.cyclos.entities.settings.AlertSettings;
import nl.strohalm.cyclos.exceptions.PermissionDeniedException;
import nl.strohalm.cyclos.services.alerts.AlertServiceLocal;
import nl.strohalm.cyclos.services.fetch.FetchServiceLocal;
import nl.strohalm.cyclos.services.permissions.PermissionServiceLocal;
import nl.strohalm.cyclos.services.settings.SettingsServiceLocal;
import nl.strohalm.cyclos.utils.CacheCleaner;
import nl.strohalm.cyclos.utils.DateHelper;
import nl.strohalm.cyclos.utils.EntityHelper;
import nl.strohalm.cyclos.utils.Period;
import nl.strohalm.cyclos.utils.access.LoggedUser;
import nl.strohalm.cyclos.utils.notifications.MemberNotificationHandler;
import nl.strohalm.cyclos.utils.query.PageHelper;
import nl.strohalm.cyclos.utils.query.QueryParameters.ResultType;
import nl.strohalm.cyclos.utils.validation.GeneralValidation;
import nl.strohalm.cyclos.utils.validation.InvalidError;
import nl.strohalm.cyclos.utils.validation.PropertyValidation;
import nl.strohalm.cyclos.utils.validation.RequiredError;
import nl.strohalm.cyclos.utils.validation.ValidationError;
import nl.strohalm.cyclos.utils.validation.ValidationException;
import nl.strohalm.cyclos.utils.validation.Validator;

import org.apache.commons.lang.StringUtils;

/**
 * Implementation class for reference services
 * @author rafael
 * @author luis
 */
public class ReferenceServiceImpl implements ReferenceServiceLocal {

    private SettingsServiceLocal      settingsService;
    private AlertServiceLocal         alertService;
    private FetchServiceLocal         fetchService;
    private PermissionServiceLocal    permissionService;
    private ReferenceDAO              referenceDao;
    private ReferenceHistoryDAO       referenceHistoryDao;
    private MemberNotificationHandler memberNotificationHandler;

    @Override
    public boolean canGiveGeneralReference(final Member member) {
        return !LoggedUser.isAdministrator() && !LoggedUser.element().getAccountOwner().equals(member) && permissionService.permission().member(MemberPermission.REFERENCES_GIVE).operator(OperatorPermission.REFERENCES_MANAGE_MEMBER_REFERENCES).hasPermission() && permissionService.relatesTo(member);
    }

    @Override
    public boolean canManage(final Reference ref) {
        switch (ref.getNature()) {
            case GENERAL:
                return canManageGeneralReference(ref.getFrom());
            case TRANSACTION:

                // If it's a new transaction feedback (no comments have been added) then if the logged user is a member or an operator, they must
                // manage the from of the transaction feedback.
                if (ref.isTransient() && (LoggedUser.isMember() || LoggedUser.isOperator()) && !permissionService.manages(ref.getFrom())) {
                    return false;
                }

                // If it's a reply feedback (no reply comments have been added) then if the logged user is a member or an operator, he must manage the
                // to of the transaction feedback.
                if (!ref.isTransient() && (LoggedUser.isMember() || LoggedUser.isOperator()) && !permissionService.manages(ref.getTo())) {
                    return false;
                }

                if (!(permissionService.manages(ref.getFrom()) || permissionService.manages(ref.getTo()))) {
                    return false;

                    // If the Logged user is a broker, then he can only manage his own transaction feedbacks.
                } else if (LoggedUser.isBroker() && !(LoggedUser.element().equals(ref.getFrom()) || LoggedUser.element().equals(ref.getTo()))) {
                    return false;
                }

                return permissionService.permission()
                        .admin(AdminMemberPermission.TRANSACTION_FEEDBACKS_MANAGE)
                        .member()
                        .operator(OperatorPermission.REFERENCES_MANAGE_MEMBER_TRANSACTION_FEEDBACKS).hasPermission();
        }
        return false;
    }

    @Override
    public boolean canManageGeneralReference(final Member member) {
        return permissionService.permission(member)
                .admin(AdminMemberPermission.REFERENCES_MANAGE)
                .broker(BrokerPermission.REFERENCES_MANAGE)
                .member(MemberPermission.REFERENCES_GIVE)
                .operator(OperatorPermission.REFERENCES_MANAGE_MEMBER_REFERENCES).hasPermission();
    }

    @Override
    public boolean canReplyFeedbackNow(final TransactionFeedback transactionFeedback) {
        Payment payment = transactionFeedback.getPayment();
        Calendar date = transactionFeedback.getDate();
        final Calendar replyLimit = payment.getType().getFeedbackReplyExpirationTime().add(date);
        return replyLimit.after(Calendar.getInstance());
    }

    @Override
    public Map<Level, Integer> countGivenReferencesByLevel(final Reference.Nature nature) {
        Collection<MemberGroup> memberGroups = null;
        if (LoggedUser.hasUser()) {
            AdminGroup adminGroup = LoggedUser.group();
            adminGroup = fetchService.fetch(adminGroup, AdminGroup.Relationships.MANAGES_GROUPS);
            memberGroups = adminGroup.getManagesGroups();
        }
        return referenceDao.countGivenReferencesByLevel(nature, memberGroups);
    }

    @Override
    public Map<Level, Integer> countReferencesByLevel(final Reference.Nature nature, final Member member, final boolean received) {
        return normalizeCountByLevel(referenceDao.countReferencesByLevel(nature, null, member, received));
    }

    @Override
    public Map<Level, Integer> countReferencesHistoryByLevel(final Reference.Nature nature, final Member member, final Period period, final boolean received) {
        Map<Level, Integer> countByLevel;
        if (nature == Reference.Nature.TRANSACTION) {
            countByLevel = referenceDao.countReferencesByLevel(nature, period, member, received);
        } else {
            countByLevel = referenceHistoryDao.countReferencesHistoryByLevel(member, period, received);
        }
        return normalizeCountByLevel(countByLevel);
    }

    @Override
    public Collection<Nature> getNaturesByGroup(MemberGroup group) {
        final Collection<Nature> natures = EnumSet.noneOf(Nature.class);

        // Check for transaction references
        group = fetchService.fetch(group, Group.Relationships.TRANSFER_TYPES);
        for (final TransferType transferType : group.getTransferTypes()) {
            if (transferType.isRequiresFeedback()) {
                natures.add(Nature.TRANSACTION);
                break;
            }
        }

        if (permissionService.hasPermission(group, MemberPermission.REFERENCES_GIVE)) {
            natures.add(Reference.Nature.GENERAL);
        }

        return natures;
    }

    @Override
    public TransactionFeedbackAction getPossibleAction(final TransactionFeedback transactionFeedback) {
        if (transactionFeedback.isTransient()) {
            if (canFeedbackNow(transactionFeedback)) {
                return TransactionFeedbackAction.COMMENTS;
            } else {
                return TransactionFeedbackAction.VIEW;
            }
        } else {
            // Check the current feedback
            final TransactionFeedback current = (TransactionFeedback) load(transactionFeedback.getId());
            if (LoggedUser.isAdministrator()) {
                return TransactionFeedbackAction.ADMIN_EDIT;
            } else if ((LoggedUser.element().getAccountOwner().equals(current.getTo()) && StringUtils.isEmpty(current.getReplyComments())) && canReplyFeedbackNow(current)) {
                return TransactionFeedbackAction.REPLY_COMMENTS;
            } else {
                return TransactionFeedbackAction.VIEW;
            }
        }
    }

    @Override
    public Reference load(final Long id, final Relationship... fetch) {
        return referenceDao.load(id, fetch);
    }

    @Override
    @SuppressWarnings("unchecked")
    public GeneralReference loadGeneral(final Member from, final Member to, final Relationship... fetch) throws EntityNotFoundException {
        final ReferenceQuery query = new ReferenceQuery();
        query.fetch(fetch);
        query.setNature(Reference.Nature.GENERAL);
        query.setFrom(from);
        query.setTo(to);
        final List<GeneralReference> list = (List<GeneralReference>) search(query);
        if (list.isEmpty()) {
            throw new EntityNotFoundException(GeneralReference.class);
        }
        return list.iterator().next();
    }

    @Override
    @SuppressWarnings("unchecked")
    public TransactionFeedback loadTransactionFeedback(final Payment payment, final Relationship... fetch) throws EntityNotFoundException {
        final ReferenceQuery query = new ReferenceQuery();
        query.setNature(Nature.TRANSACTION);
        if (payment instanceof ScheduledPayment) {
            query.setScheduledPayment((ScheduledPayment) payment);
        } else {
            query.setTransfer((Transfer) payment);
        }
        query.setUniqueResult();
        final List<TransactionFeedback> refs = (List<TransactionFeedback>) search(query);
        if (refs.isEmpty()) {
            throw new EntityNotFoundException(TransactionFeedback.class);
        }
        return refs.iterator().next();
    }

    @Override
    public int processExpiredFeedbacks(Calendar time) {
        time = DateHelper.truncate(time);
        final PaymentsAwaitingFeedbackQuery query = new PaymentsAwaitingFeedbackQuery();
        query.setExpired(true);
        query.setResultType(ResultType.ITERATOR);
        final List<PaymentAwaitingFeedbackDTO> paymentsAwaitingFeedback = searchPaymentsAwaitingFeedback(query);
        int processed = 0;
        CacheCleaner cleaner = new CacheCleaner(fetchService);
        for (final PaymentAwaitingFeedbackDTO dto : paymentsAwaitingFeedback) {
            // Fetch the transfer type
            TransferType transferType = EntityHelper.reference(TransferType.class, dto.getTransferTypeId());
            transferType = fetchService.fetch(transferType);

            // Fetch the payment
            Class<? extends Payment> paymentClass = dto.isScheduled() ? ScheduledPayment.class : Transfer.class;
            Payment payment = fetchService.fetch(EntityHelper.reference(paymentClass, dto.getId()), Payment.Relationships.FROM, Payment.Relationships.TO);

            // Insert the feedback with the default comments
            final TransactionFeedback feedback = new TransactionFeedback();
            feedback.setDate(time);
            feedback.setComments(transferType.getDefaultFeedbackComments());
            feedback.setLevel(transferType.getDefaultFeedbackLevel());
            feedback.setFrom((Member) payment.getFromOwner());
            feedback.setTo((Member) payment.getToOwner());
            feedback.setPayment(payment);
            referenceDao.insert(feedback, false);
            cleaner.clearCache();
            processed++;
        }
        return processed;
    }

    @Override
    public int remove(final Long... ids) {
        // Before remove the references, update their last reference history logs
        for (final Long id : ids) {
            final Reference reference = load(id, (Relationship[]) null);
            updatePreviousReferenceHistoryLog(reference);
        }
        return referenceDao.delete(ids);
    }

    @Override
    public GeneralReference save(final GeneralReference reference) {
        reference.setDate(Calendar.getInstance());
        return (GeneralReference) doSave(reference);
    }

    @Override
    public TransactionFeedback save(final TransactionFeedback transactionFeedback) {
        switch (getPossibleAction(transactionFeedback)) {
            case ADMIN_EDIT:
                return saveTransactionFeedbackByAdmin(transactionFeedback);
            case COMMENTS:
                return saveTransactionFeedbackComments(transactionFeedback);
            case REPLY_COMMENTS:
                return saveTransactionFeedbackReplyComments(transactionFeedback);
            default:
                throw new PermissionDeniedException();
        }
    }

    @Override
    public List<? extends Reference> search(final ReferenceQuery query) {
        return referenceDao.search(query);
    }

    @Override
    public List<PaymentAwaitingFeedbackDTO> searchPaymentsAwaitingFeedback(final PaymentsAwaitingFeedbackQuery query) {
        return referenceDao.searchPaymentsAwaitingFeedback(query);
    }

    public void setAlertServiceLocal(final AlertServiceLocal alertService) {
        this.alertService = alertService;
    }

    public void setFetchServiceLocal(final FetchServiceLocal fetchService) {
        this.fetchService = fetchService;
    }

    public void setMemberNotificationHandler(final MemberNotificationHandler memberNotificationHandler) {
        this.memberNotificationHandler = memberNotificationHandler;
    }

    public void setPermissionServiceLocal(final PermissionServiceLocal permissionService) {
        this.permissionService = permissionService;
    }

    public void setReferenceDao(final ReferenceDAO referenceDAO) {
        referenceDao = referenceDAO;
    }

    public void setReferenceHistoryDao(final ReferenceHistoryDAO referenceHistoryDao) {
        this.referenceHistoryDao = referenceHistoryDao;
    }

    public void setSettingsServiceLocal(final SettingsServiceLocal settingsService) {
        this.settingsService = settingsService;
    }

    @Override
    public void validate(final Reference reference) throws ValidationException {
        Validator validator;
        if (reference instanceof GeneralReference) {
            validator = getGeneralValidator();
        } else {
            validator = getTransactionFeedbackValidator((TransactionFeedback) reference);
        }
        validator.validate(reference);
    }

    /**
     * Returns true if a member or an operator are in time to make the feedback.
     * @param transactionFeedback
     * @return
     */
    private boolean canFeedbackNow(final TransactionFeedback transactionFeedback) {
        Payment payment = transactionFeedback.getPayment();
        payment = fetchService.fetch(payment, Payment.Relationships.TYPE);
        Calendar paymentDate;
        if (payment instanceof ScheduledPayment) {
            paymentDate = payment.getDate();
        } else {
            paymentDate = payment.getProcessDate();
        }
        final Calendar commentLimit = payment.getType().getFeedbackExpirationTime().add(paymentDate);
        return commentLimit.after(Calendar.getInstance());
    }

    private Validator createBasicValidator() {
        final Validator validator = new Validator("reference");
        validator.property("level").required();
        validator.property("from").required();
        validator.property("to").required().add(new PropertyValidation() {
            private static final long serialVersionUID = 5881069152089528552L;

            @Override
            public ValidationError validate(final Object object, final Object name, final Object value) {
                final Reference reference = (Reference) object;
                // From and to cannot be the same
                final Member from = reference.getFrom();
                if (from != null && from.equals(value)) {
                    return new InvalidError();
                }
                return null;
            }
        });
        validator.property("comments").required().maxLength(1000);
        return validator;
    }

    /**
     * Create a reference history log of the reference
     */
    private void createNewReferenceHistoryLog(final Reference reference) {
        final ReferenceHistoryLog log = new ReferenceHistoryLog();
        log.setFrom(reference.getFrom());
        log.setTo(reference.getTo());
        log.setLevel(reference.getLevel());
        log.setPeriod(Period.begginingAt(reference.getDate()));
        referenceHistoryDao.insert(log);
    }

    private Reference doSave(Reference reference) {
        validate(reference);
        boolean isInsert = reference.isTransient();
        if (isInsert) {
            // Insert the reference
            reference = referenceDao.insert(reference);
        } else {
            // Update the current reference
            reference = referenceDao.update(reference);
            // Update the log of the previous reference
            if (reference instanceof GeneralReference) {
                updatePreviousReferenceHistoryLog(reference);
            }
        }
        // Specific operators for general references
        if (reference instanceof GeneralReference) {
            // Insert a log for the new reference
            createNewReferenceHistoryLog(reference);

            // Compute the given / received references to check if should create an alert
            final AlertSettings alertSettings = settingsService.getAlertSettings();
            // Given
            final Member from = reference.getFrom();
            if (alertSettings.getGivenVeryBadRefs() > 0) {
                final int givenVeryBad = referenceDao.countReferencesByLevel(reference.getNature(), null, from, false).get(Reference.Level.VERY_BAD);
                if (givenVeryBad >= alertSettings.getGivenVeryBadRefs()) {
                    final AlertQuery query = new AlertQuery();
                    query.setType(Alert.Type.MEMBER);
                    query.setMember(from);
                    query.setKey(MemberAlert.Alerts.GIVEN_VERY_BAD_REFS.getValue());
                    query.setPageForCount();
                    final boolean hasAlert = PageHelper.getTotalCount(alertService.search(query)) > 0;
                    if (!hasAlert) {
                        alertService.create(from, MemberAlert.Alerts.GIVEN_VERY_BAD_REFS, givenVeryBad);
                    }
                }
            }
            // Received
            final Member to = reference.getTo();
            if (alertSettings.getReceivedVeryBadRefs() > 0) {
                final int receivedVeryBad = referenceDao.countReferencesByLevel(reference.getNature(), null, to, true).get(Reference.Level.VERY_BAD);
                if (receivedVeryBad >= alertSettings.getReceivedVeryBadRefs()) {
                    final AlertQuery query = new AlertQuery();
                    query.setType(Alert.Type.MEMBER);
                    query.setMember(from);
                    query.setKey(MemberAlert.Alerts.RECEIVED_VERY_BAD_REFS.getValue());
                    query.setPageForCount();
                    final boolean hasAlert = PageHelper.getTotalCount(alertService.search(query)) > 0;
                    if (!hasAlert) {
                        alertService.create(to, MemberAlert.Alerts.RECEIVED_VERY_BAD_REFS, receivedVeryBad);
                    }
                }
            }
        }
        if (isInsert && reference instanceof GeneralReference) {
            memberNotificationHandler.receivedReferenceNotification(reference);
        }
        return reference;
    }

    private Validator getGeneralValidator() {
        return createBasicValidator();
    }

    private Validator getTransactionFeedbackValidator(final TransactionFeedback transactionFeedback) {
        final Validator transactionFeedbackValidator = createBasicValidator();
        transactionFeedbackValidator.property("payment").required();

        transactionFeedbackValidator.general(new GeneralValidation() {

            private static final long serialVersionUID = 1L;

            @Override
            public ValidationError validate(final Object object) {
                TransactionFeedback transactionFeedback = (TransactionFeedback) object;
                // Check if the time to make the feedback has expired.
                if (transactionFeedback.isTransient()) {
                    boolean readyForFeedback = !LoggedUser.isAdministrator() && (LoggedUser.element().getAccountOwner().equals(transactionFeedback.getFrom()));
                    if (readyForFeedback && !canFeedbackNow(transactionFeedback)) {
                        return new ValidationError("reference.transactionFeedback.feedbackPeriodExpired");
                    }
                }

                // Check if the time to make the reply has expired.
                if (!transactionFeedback.isTransient()) {
                    TransactionFeedback current = (TransactionFeedback) load(transactionFeedback.getId());
                    boolean readyForReply = (!LoggedUser.isAdministrator()) && (LoggedUser.element().getAccountOwner().equals(current.getTo()) && StringUtils.isEmpty(current.getReplyComments()));
                    if (readyForReply && !canReplyFeedbackNow(current)) {
                        return new ValidationError("reference.transactionFeedback.feedbackPeriodExpired");
                    }
                }
                return null;
            }
        });

        return transactionFeedbackValidator;
    }

    private Map<Level, Integer> normalizeCountByLevel(final Map<Level, Integer> countMap) {
        final Map<Level, Integer> countByLevel = new LinkedHashMap<Level, Integer>();
        for (final Level level : settingsService.getLocalSettings().getReferenceLevelList()) {
            final Integer count = countMap.get(level);
            countByLevel.put(level, count == null ? 0 : count);
        }
        return countByLevel;
    }

    private TransactionFeedback saveTransactionFeedbackByAdmin(TransactionFeedback transactionFeedback) {
        final Calendar now = Calendar.getInstance();
        final Reference.Level level = transactionFeedback.getLevel();
        final String comments = transactionFeedback.getComments();
        final String replyComments = transactionFeedback.getReplyComments();
        final String adminComments = transactionFeedback.getAdminComments();
        transactionFeedback = referenceDao.load(transactionFeedback.getId());
        transactionFeedback.setLevel(level);
        transactionFeedback.setComments(comments);
        if (StringUtils.isNotEmpty(replyComments)) {
            transactionFeedback.setReplyComments(replyComments);
            if (transactionFeedback.getReplyCommentsDate() == null) {
                transactionFeedback.setReplyCommentsDate(now);
            }
        } else {
            transactionFeedback.setReplyComments(null);
        }
        if (StringUtils.isNotEmpty(adminComments)) {
            transactionFeedback.setAdminComments(adminComments);
            transactionFeedback.setAdminCommentsDate(now);
        } else {
            transactionFeedback.setAdminComments(null);
            transactionFeedback.setAdminCommentsDate(null);
        }
        validate(transactionFeedback);
        transactionFeedback = referenceDao.update(transactionFeedback);
        memberNotificationHandler.transactionFeedBackAdminCommentsNotification(transactionFeedback);
        return transactionFeedback;
    }

    private TransactionFeedback saveTransactionFeedbackComments(TransactionFeedback transactionFeedback) {

        final Payment payment = fetchService.fetch(transactionFeedback.getPayment(), Payment.Relationships.TYPE, Payment.Relationships.FROM, Payment.Relationships.TO);
        final Reference.Level level = transactionFeedback.getLevel();
        final String comments = transactionFeedback.getComments();

        if (!payment.getType().isRequiresFeedback()) {
            throw new UnexpectedEntityException();
        }

        // Check whether the feedback exists
        try {
            transactionFeedback = loadTransactionFeedback(payment);
            // It already exists. Return the current one
        } catch (final EntityNotFoundException e) {
            // Doesn't exist yet. Let's save it
            transactionFeedback = new TransactionFeedback();
            transactionFeedback.setDate(Calendar.getInstance());
            transactionFeedback.setPayment(payment);
            transactionFeedback.setFrom((Member) payment.getFromOwner());
            transactionFeedback.setTo((Member) payment.getToOwner());
            transactionFeedback.setLevel(level);
            transactionFeedback.setComments(comments);
            transactionFeedback = (TransactionFeedback) doSave(transactionFeedback);
        }
        memberNotificationHandler.transactionFeedBackReceivedNotification(transactionFeedback);
        return transactionFeedback;
    }

    private TransactionFeedback saveTransactionFeedbackReplyComments(TransactionFeedback transactionFeedback) {
        final String replyComments = transactionFeedback.getReplyComments();
        transactionFeedback = referenceDao.load(transactionFeedback.getId());

        // There was a reply already
        if (transactionFeedback.getReplyCommentsDate() != null) {
            return transactionFeedback;
        }

        // Validate
        if (StringUtils.isEmpty(replyComments)) {
            throw new ValidationException("replyComments", "reference.replyComments", new RequiredError());
        }

        // Set the comments
        transactionFeedback.setReplyCommentsDate(Calendar.getInstance());
        transactionFeedback.setReplyComments(replyComments);
        validate(transactionFeedback);
        transactionFeedback = referenceDao.update(transactionFeedback);
        memberNotificationHandler.transactionFeedBackReplyNotification(transactionFeedback);
        return transactionFeedback;
    }

    /*
     * Update the previous reference history log of the reference This methods set the final date of the log to the date of the current reference
     */
    private void updatePreviousReferenceHistoryLog(final Reference reference) {
        final ReferenceHistoryLogQuery query = new ReferenceHistoryLogQuery();
        query.setFrom(reference.getFrom());
        query.setTo(reference.getTo());
        final ReferenceHistoryLog previousLog = referenceHistoryDao.getOpenReferenceHistoryLog(query);
        if (previousLog != null) {
            final Period period = previousLog.getPeriod();
            period.setEnd(reference.getDate());
            previousLog.setPeriod(period);
            referenceHistoryDao.update(previousLog);
        }
    }

}
