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
package nl.strohalm.cyclos.services.accounts.guarantees;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;

import nl.strohalm.cyclos.access.AdminMemberPermission;
import nl.strohalm.cyclos.access.MemberPermission;
import nl.strohalm.cyclos.access.OperatorPermission;
import nl.strohalm.cyclos.access.Permission;
import nl.strohalm.cyclos.dao.accounts.guarantees.GuaranteeDAO;
import nl.strohalm.cyclos.dao.accounts.guarantees.GuaranteeLogDAO;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.AccountOwner;
import nl.strohalm.cyclos.entities.accounts.SystemAccountOwner;
import nl.strohalm.cyclos.entities.accounts.guarantees.Certification;
import nl.strohalm.cyclos.entities.accounts.guarantees.Guarantee;
import nl.strohalm.cyclos.entities.accounts.guarantees.Guarantee.Status;
import nl.strohalm.cyclos.entities.accounts.guarantees.GuaranteeLog;
import nl.strohalm.cyclos.entities.accounts.guarantees.GuaranteeQuery;
import nl.strohalm.cyclos.entities.accounts.guarantees.GuaranteeType;
import nl.strohalm.cyclos.entities.accounts.guarantees.GuaranteeType.FeeType;
import nl.strohalm.cyclos.entities.accounts.guarantees.PaymentObligation;
import nl.strohalm.cyclos.entities.accounts.guarantees.PaymentObligationLog;
import nl.strohalm.cyclos.entities.accounts.loans.Loan;
import nl.strohalm.cyclos.entities.accounts.transactions.Transfer;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferType;
import nl.strohalm.cyclos.entities.customization.fields.PaymentCustomField;
import nl.strohalm.cyclos.entities.customization.fields.PaymentCustomFieldValue;
import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.entities.groups.GroupQuery;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.exceptions.PermissionDeniedException;
import nl.strohalm.cyclos.services.InitializingService;
import nl.strohalm.cyclos.services.accounts.guarantees.exceptions.ActiveCertificationNotFoundException;
import nl.strohalm.cyclos.services.accounts.guarantees.exceptions.CertificationAmountExceededException;
import nl.strohalm.cyclos.services.accounts.guarantees.exceptions.CertificationValidityExceededException;
import nl.strohalm.cyclos.services.accounts.guarantees.exceptions.GuaranteeStatusChangeException;
import nl.strohalm.cyclos.services.customization.PaymentCustomFieldServiceLocal;
import nl.strohalm.cyclos.services.fetch.FetchServiceLocal;
import nl.strohalm.cyclos.services.groups.GroupServiceLocal;
import nl.strohalm.cyclos.services.permissions.PermissionServiceLocal;
import nl.strohalm.cyclos.services.settings.SettingsServiceLocal;
import nl.strohalm.cyclos.services.transactions.GrantSinglePaymentLoanDTO;
import nl.strohalm.cyclos.services.transactions.LoanServiceLocal;
import nl.strohalm.cyclos.services.transactions.PaymentServiceLocal;
import nl.strohalm.cyclos.services.transactions.TransactionContext;
import nl.strohalm.cyclos.services.transactions.TransferDTO;
import nl.strohalm.cyclos.utils.CustomFieldHelper;
import nl.strohalm.cyclos.utils.CustomFieldsContainer;
import nl.strohalm.cyclos.utils.DateHelper;
import nl.strohalm.cyclos.utils.MessageProcessingHelper;
import nl.strohalm.cyclos.utils.MessageResolver;
import nl.strohalm.cyclos.utils.Period;
import nl.strohalm.cyclos.utils.RelationshipHelper;
import nl.strohalm.cyclos.utils.TimePeriod;
import nl.strohalm.cyclos.utils.TransactionHelper;
import nl.strohalm.cyclos.utils.access.LoggedUser;
import nl.strohalm.cyclos.utils.conversion.NumberConverter;
import nl.strohalm.cyclos.utils.guarantees.GuaranteesHelper;
import nl.strohalm.cyclos.utils.notifications.AdminNotificationHandler;
import nl.strohalm.cyclos.utils.notifications.MemberNotificationHandler;
import nl.strohalm.cyclos.utils.query.PageParameters;
import nl.strohalm.cyclos.utils.query.QueryParameters.ResultType;
import nl.strohalm.cyclos.utils.validation.DelegatingValidator;
import nl.strohalm.cyclos.utils.validation.GeneralValidation;
import nl.strohalm.cyclos.utils.validation.PeriodValidation;
import nl.strohalm.cyclos.utils.validation.PeriodValidation.ValidationType;
import nl.strohalm.cyclos.utils.validation.ValidationError;
import nl.strohalm.cyclos.utils.validation.Validator;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.functors.AndPredicate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;

public class GuaranteeServiceImpl implements GuaranteeServiceLocal, InitializingService, ApplicationContextAware {
    /**
     * Validation to avoid register/accept a guarantee with fees' amount greater than guarantee's amount
     * @author ameyer
     */
    private class GuaranteeFeeValidation implements GeneralValidation {
        private static final long serialVersionUID = 840449718151754491L;

        @Override
        public ValidationError validate(final Object object) {
            final Guarantee guarantee = (Guarantee) object;
            BigDecimal fees = null;
            fees = guarantee.getCreditFee() != null ? guarantee.getCreditFee() : new BigDecimal(0);
            fees = guarantee.getIssueFee() != null ? fees.add(guarantee.getIssueFee()) : fees;

            if (fees.compareTo(guarantee.getAmount()) == 1) {
                return new ValidationError("guarantee.error.invalidGuarantee");
            }

            return null;
        }
    }

    private GuaranteeDAO                   guaranteeDao;
    private GuaranteeLogDAO                guaranteeLogDao;
    private PermissionServiceLocal         permissionService;
    private GuaranteeTypeServiceLocal      guaranteeTypeService;
    private GroupServiceLocal              groupService;
    private PaymentObligationServiceLocal  paymentObligationService;
    private CertificationServiceLocal      certificationService;
    private FetchServiceLocal              fetchService;
    private LoanServiceLocal               loanService;
    private PaymentServiceLocal            paymentService;
    private SettingsServiceLocal           settingsService;
    private PaymentCustomFieldServiceLocal paymentCustomFieldService;
    private ApplicationContext             applicationContext;
    private MessageResolver                messageResolver = new MessageResolver.NoOpMessageResolver();
    private MemberNotificationHandler      memberNotificationHandler;
    private AdminNotificationHandler       adminNotificationHandler;

    private TransactionHelper              transactionHelper;
    private CustomFieldHelper              customFieldHelper;

    @Override
    public Guarantee acceptGuarantee(Guarantee guarantee, final boolean automaticLoanAuthorization) {
        validate(guarantee, LoggedUser.isAdministrator() ? automaticLoanAuthorization : false);

        if (LoggedUser.isAdministrator()) {
            guarantee = doChangeStatus(guarantee, Guarantee.Status.ACCEPTED, automaticLoanAuthorization);

            memberNotificationHandler.guaranteeAcceptedNotification(guarantee);
        } else {
            Status currentStatus = load(guarantee.getId()).getStatus();
            guarantee = doChangeStatus(guarantee, Guarantee.Status.ACCEPTED, false);

            memberNotificationHandler.guaranteeStatusChangedNotification(guarantee, currentStatus);
            adminNotificationHandler.notifyPendingGuarantee(guarantee);
        }
        return guarantee;
    }

    @Override
    public BigDecimal calculateFee(final GuaranteeFeeCalculationDTO dto) {
        return GuaranteesHelper.calculateFee(dto.getValidity(), dto.getAmount(), dto.getFeeSpec());
    }

    @Override
    public boolean canChangeStatus(final Guarantee guarantee, final Status newStatus) {
        switch (newStatus) {
            case ACCEPTED:
            case REJECTED:
                if (isInSomeStatus(guarantee, Status.PENDING_ADMIN)) {
                    Permission permission = Guarantee.Status.ACCEPTED == newStatus ? AdminMemberPermission.GUARANTEES_ACCEPT_GUARANTEES_AS_MEMBER : AdminMemberPermission.GUARANTEES_CANCEL_GUARANTEES_AS_MEMBER;
                    final boolean hasPermission = permissionService.hasPermission(permission);
                    return hasPermission;
                } else if (isInSomeStatus(guarantee, Status.PENDING_ISSUER)) {
                    return isIssuer() && guarantee.getIssuer().equals(LoggedUser.accountOwner());
                } else {
                    return false;
                }
            case CANCELLED:
                final boolean hasPermission = permissionService.hasPermission(AdminMemberPermission.GUARANTEES_CANCEL_GUARANTEES_AS_MEMBER);
                return hasPermission && isInSomeStatus(guarantee, Status.ACCEPTED, Status.PENDING_ADMIN, Status.PENDING_ISSUER) && guarantee.getLoan() == null;
            default:
                throw new GuaranteeStatusChangeException(newStatus, "Can't change guarantee's status, unsupported target status: " + newStatus);
        }
    }

    /**
     * Only an administrator can delete a just registered (pending by administrator) guarantee registered by himself
     * @param guarantee
     */
    @Override
    public boolean canRemoveGuarantee(Guarantee guarantee) {
        guarantee = fetchService.fetch(guarantee, Guarantee.Relationships.LOGS, Guarantee.Relationships.GUARANTEE_TYPE);
        final boolean currentStatusIsPendingByAdmin = guarantee.getStatus() == Status.PENDING_ADMIN;
        final GuaranteeLog log = guarantee.getLogs().iterator().next();
        final boolean isOnlyPendingByAdmin = guarantee.getLogs().size() == 1 && log.getStatus() == Status.PENDING_ADMIN;

        return LoggedUser.isAdministrator() && currentStatusIsPendingByAdmin && isOnlyPendingByAdmin && log.getBy().equals(LoggedUser.element()) && guarantee.getGuaranteeType().getModel() != GuaranteeType.Model.WITH_PAYMENT_OBLIGATION;
    }

    @Override
    public Guarantee changeStatus(final Long guaranteeId, final Status newStatus) {
        Guarantee current = load(guaranteeId);
        Status currentStatus = current.getStatus();
        current = doChangeStatus(current, newStatus, false);
        switch (newStatus) {
            case CANCELLED:
                memberNotificationHandler.guaranteeCancelledNotification(current);
                break;
            case REJECTED:
                memberNotificationHandler.guaranteeDeniedNotification(current);
                break;
            default:
                memberNotificationHandler.guaranteeStatusChangedNotification(current, currentStatus);
                adminNotificationHandler.notifyPendingGuarantee(current);
        }

        return current;
    }

    @Override
    public Collection<? extends MemberGroup> getBuyers() {
        if (LoggedUser.isAdministrator()) {
            return filterBuyers();
        } else if (isIssuer()) {
            final MemberGroup group = fetchService.fetch((MemberGroup) ((Member) LoggedUser.accountOwner()).getGroup(), MemberGroup.Relationships.CAN_ISSUE_CERTIFICATION_TO_GROUPS);
            return group.getCanIssueCertificationToGroups();
        } else { // is a seller
            return guaranteeDao.getBuyers(((Member) LoggedUser.accountOwner()).getGroup());
        }
    }

    @Override
    public List<Guarantee> getGuarantees(final Certification certification, final PageParameters pageParameters, final List<Status> statusList) {
        final GuaranteeQuery guaranteeQuery = new GuaranteeQuery();
        guaranteeQuery.setResultType(ResultType.PAGE);
        guaranteeQuery.setPageParameters(pageParameters);
        guaranteeQuery.setCertification(certification);
        guaranteeQuery.setStatusList(statusList);
        guaranteeQuery.fetch(RelationshipHelper.nested(Guarantee.Relationships.LOAN, Loan.Relationships.PAYMENTS));
        return guaranteeDao.search(guaranteeQuery);
    }

    @Override
    public Collection<? extends MemberGroup> getIssuers() {
        return filterIssuers();
    }

    @Override
    public Collection<? extends MemberGroup> getIssuers(final GuaranteeType guaranteeType) {
        final Collection<? extends Group> groups = guaranteeDao.getIssuers(guaranteeType);

        // we must filter the list because it might contains System or removed (issuers) groups
        return filterMemberGroups(null, groups);

    }

    public MessageResolver getMessageResolver(final MessageResolver messageResolver) {
        return messageResolver;
    }

    @Override
    public Collection<GuaranteeType.Model> getRelatedGuaranteeModels() {
        return guaranteeDao.getRelatedGuaranteeModels((Member) LoggedUser.accountOwner());
    }

    @Override
    public Collection<? extends MemberGroup> getSellers() {
        if (LoggedUser.isAdministrator()) {
            return filterSellers();
        } else {
            if (isBuyer()) {
                final MemberGroup group = fetchService.fetch((MemberGroup) ((Member) LoggedUser.accountOwner()).getGroup(), MemberGroup.Relationships.CAN_BUY_WITH_PAYMENT_OBLIGATIONS_FROM_GROUPS);
                return group.getCanBuyWithPaymentObligationsFromGroups();
            } else { // is an issuer
                return guaranteeDao.getSellers(((Member) LoggedUser.accountOwner()).getGroup());
            }
        }
    }

    @Override
    public List<Guarantee> guaranteesToProcess(Calendar time) {
        time = DateHelper.truncate(time);
        final GuaranteeQuery query = new GuaranteeQuery();
        query.setResultType(ResultType.ITERATOR);
        final Set<Relationship> fetch = new HashSet<Relationship>();
        fetch.add(Guarantee.Relationships.GUARANTEE_TYPE);
        fetch.add(Guarantee.Relationships.LOGS);
        query.setFetch(fetch);
        query.setStatusList(Arrays.asList(Guarantee.Status.PENDING_ADMIN, Guarantee.Status.PENDING_ISSUER));

        final List<Guarantee> result = new ArrayList<Guarantee>();

        final List<Guarantee> guarantees = guaranteeDao.search(query);
        for (final Guarantee guarantee : guarantees) {
            final TimePeriod period = guarantee.getGuaranteeType().getPendingGuaranteeExpiration();
            final Calendar lowerBound = period.remove(time);
            final Calendar registrationDate = DateHelper.truncate(guarantee.getRegistrationDate());
            if (registrationDate.before(lowerBound)) {
                result.add(guarantee);
            }
        }
        return result;
    }

    @Override
    public void initializeService() {
        processGuaranteeLoans(Calendar.getInstance());

        // We need a proxy here in order to run AOP
        GuaranteeServiceLocal proxy = applicationContext.getBean(GuaranteeServiceLocal.class);

        final Calendar time = Calendar.getInstance();
        final List<Guarantee> guarantees = guaranteesToProcess(time);
        for (final Guarantee guarantee : guarantees) {
            proxy.processGuarantee(guarantee, time);
        }

    }

    @Override
    public boolean isBuyer() {
        return permissionService.permission()
                .member(MemberPermission.GUARANTEES_BUY_WITH_PAYMENT_OBLIGATIONS)
                .operator(OperatorPermission.GUARANTEES_BUY_WITH_PAYMENT_OBLIGATIONS)
                .hasPermission();
    }

    @Override
    public boolean isIssuer() {
        return permissionService.permission()
                .member(MemberPermission.GUARANTEES_ISSUE_GUARANTEES)
                .operator(OperatorPermission.GUARANTEES_ISSUE_GUARANTEES)
                .hasPermission();
    }

    @Override
    public boolean isSeller() {
        return permissionService.permission()
                .member(MemberPermission.GUARANTEES_SELL_WITH_PAYMENT_OBLIGATIONS)
                .operator(OperatorPermission.GUARANTEES_SELL_WITH_PAYMENT_OBLIGATIONS)
                .hasPermission();
    }

    @Override
    public Guarantee load(final Long id, final Relationship... fetch) {
        Guarantee guarantee = guaranteeDao.load(id, fetch);
        guarantee = fetchService.fetch(guarantee, Guarantee.Relationships.GUARANTEE_TYPE, Guarantee.Relationships.ISSUER, RelationshipHelper.nested(Guarantee.Relationships.BUYER, Element.Relationships.GROUP), RelationshipHelper.nested(Guarantee.Relationships.SELLER, Element.Relationships.GROUP));
        return guarantee;
    }

    @Override
    public Guarantee loadFromTransfer(final Transfer transfer) {
        Transfer root = transfer.getRootTransfer();
        return guaranteeDao.loadFromTransfer(root);
    }

    @Override
    public Guarantee processGuarantee(final Guarantee guarantee, final Calendar time) {
        guarantee.setStatus(Guarantee.Status.WITHOUT_ACTION);
        final GuaranteeLog log = guarantee.changeStatus(Guarantee.Status.WITHOUT_ACTION, null);
        saveLog(log);
        save(guarantee, true);

        return guarantee;
    }

    /**
     * Generates a new loan for each guarantee if its status is ACCEPTED and the begin period is before or equals to the time parameter
     * @param time the times used as the current time
     */
    @Override
    public void processGuaranteeLoans(final Calendar time) {
        final GuaranteeQuery query = new GuaranteeQuery();
        query.fetch(Guarantee.Relationships.GUARANTEE_TYPE, Guarantee.Relationships.SELLER, Guarantee.Relationships.BUYER);
        query.setStatusList(Collections.singletonList(Guarantee.Status.ACCEPTED));
        query.setStartIn(Period.endingAt(time));
        query.setLoanFilter(GuaranteeQuery.LoanFilter.WITHOUT_LOAN);

        final List<Guarantee> guarantees = guaranteeDao.search(query);

        for (final Guarantee guarantee : guarantees) {
            grantLoan(time, guarantee, false);
            save(guarantee, true);
        }
    }

    @Override
    public Guarantee registerGuarantee(Guarantee guarantee) {
        validate(guarantee, false);

        guarantee = save(guarantee, true);
        grantLoan(Calendar.getInstance(), guarantee, false);
        memberNotificationHandler.guaranteePendingIssuerNotification(guarantee);
        adminNotificationHandler.notifyPendingGuarantee(guarantee);
        return guarantee;
    }

    @Override
    public int remove(final Long guaranteeId) {
        return guaranteeDao.delete(guaranteeId);
    }

    @Override
    public Guarantee requestGuarantee(final PaymentObligationPackDTO pack) {
        final Long[] poIds = pack.getPaymentObligations();
        // verifies the pack validity
        if (pack.getIssuer() == null) {
            throw new IllegalArgumentException("Invalid guarantee request: Issuer is null");
        } else if (poIds == null || poIds.length == 0) {
            throw new IllegalArgumentException("Invalid guarantee request: payment obligations pack is empty");
        }

        // take the first payment obligation to get the currency and buyer (all belong to the same buyer and have the same currency)
        final PaymentObligation firstPaymentObligation = paymentObligationService.load(poIds[0]);
        final Member buyer = firstPaymentObligation.getBuyer();
        final Member seller = firstPaymentObligation.getSeller();
        final Member issuer = pack.getIssuer();
        final AccountOwner accOwner = LoggedUser.accountOwner();

        if (!accOwner.equals(seller)) {
            throw new PermissionDeniedException();
        }

        final Certification certification = certificationService.getActiveCertification(firstPaymentObligation.getCurrency(), buyer, issuer);

        // verifies if there is an active certification
        if (certification == null) {
            throw new ActiveCertificationNotFoundException(buyer, issuer, firstPaymentObligation.getCurrency());
        }

        // calculates the guarantee's amount and expirationDate
        BigDecimal amount = firstPaymentObligation.getAmount();
        final ArrayList<PaymentObligation> paymentObligations = new ArrayList<PaymentObligation>();
        paymentObligations.add(firstPaymentObligation);
        Calendar lastExpirationdate = firstPaymentObligation.getExpirationDate();
        for (int i = 1; i < poIds.length; i++) {
            final PaymentObligation po = paymentObligationService.load(poIds[i]);
            if (!accOwner.equals(po.getSeller())) {
                throw new PermissionDeniedException();
            }
            amount = amount.add(po.getAmount());
            if (po.getExpirationDate().after(lastExpirationdate)) {
                lastExpirationdate = po.getExpirationDate();
            }

            paymentObligations.add(po);
        }

        // verify that the certificatin's amount is not exceeded
        final BigDecimal usedCertificationAmount = certificationService.getUsedAmount(certification, true);
        final BigDecimal remainingAmount = certification.getAmount().subtract(usedCertificationAmount);
        if (amount.compareTo(remainingAmount) > 0) {
            throw new CertificationAmountExceededException(certification, remainingAmount, amount);
        }

        // verify that the certificatin's validity is not exceeded
        if (lastExpirationdate.after(certification.getValidity().getEnd())) {
            throw new CertificationValidityExceededException(certification);
        }

        final GuaranteeType guaranteeType = certification.getGuaranteeType();
        Guarantee guarantee = new Guarantee();

        guarantee.setBuyer(buyer);
        guarantee.setSeller(seller);
        guarantee.setIssuer(pack.getIssuer());
        guarantee.setCertification(certification);
        guarantee.setGuaranteeType(guaranteeType);
        guarantee.setAmount(amount);
        guarantee.setValidity(new Period(null, lastExpirationdate));
        guarantee.setPaymentObligations(paymentObligations);
        guarantee.setCreditFeeSpec((GuaranteeFeeVO) guaranteeType.getCreditFee().clone());
        guarantee.setIssueFeeSpec((GuaranteeFeeVO) guaranteeType.getIssueFee().clone());

        guarantee = save(guarantee, false);

        for (int i = 0; i < poIds.length; i++) {
            final PaymentObligation po = paymentObligations.get(i);
            po.setGuarantee(guarantee);
            paymentObligationService.changeStatus(po.getId(), PaymentObligation.Status.ACCEPTED);
        }

        memberNotificationHandler.guaranteePendingIssuerNotification(guarantee);

        return guarantee;
    }

    public GuaranteeLog saveLog(final GuaranteeLog guaranteeLog) {
        if (guaranteeLog.isTransient()) {
            return guaranteeLogDao.insert(guaranteeLog);
        } else {
            return guaranteeLogDao.update(guaranteeLog);
        }
    }

    @Override
    public List<Guarantee> search(final GuaranteeQuery queryParameters) {
        return guaranteeDao.search(queryParameters);
    }

    public void setAdminNotificationHandler(final AdminNotificationHandler adminNotificationHandler) {
        this.adminNotificationHandler = adminNotificationHandler;
    }

    @Override
    public void setApplicationContext(final ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public void setCertificationServiceLocal(final CertificationServiceLocal certificationService) {
        this.certificationService = certificationService;
    }

    public void setCustomFieldHelper(final CustomFieldHelper customFieldHelper) {
        this.customFieldHelper = customFieldHelper;
    }

    public void setFetchServiceLocal(final FetchServiceLocal fetchService) {
        this.fetchService = fetchService;
    }

    public void setGroupServiceLocal(final GroupServiceLocal groupService) {
        this.groupService = groupService;
    }

    public void setGuaranteeDao(final GuaranteeDAO guaranteeDao) {
        this.guaranteeDao = guaranteeDao;
    }

    public void setGuaranteeLogDao(final GuaranteeLogDAO guaranteeLogDao) {
        this.guaranteeLogDao = guaranteeLogDao;
    }

    public void setGuaranteeTypeServiceLocal(final GuaranteeTypeServiceLocal guaranteeTypeService) {
        this.guaranteeTypeService = guaranteeTypeService;
    }

    public void setLoanServiceLocal(final LoanServiceLocal loanService) {
        this.loanService = loanService;
    }

    public void setMemberNotificationHandler(final MemberNotificationHandler memberNotificationHandler) {
        this.memberNotificationHandler = memberNotificationHandler;
    }

    public void setMessageResolver(final MessageResolver messageResolver) {
        this.messageResolver = messageResolver;
    }

    public void setPaymentCustomFieldServiceLocal(final PaymentCustomFieldServiceLocal paymentCustomFieldService) {
        this.paymentCustomFieldService = paymentCustomFieldService;
    }

    public void setPaymentObligationServiceLocal(final PaymentObligationServiceLocal paymentObligationService) {
        this.paymentObligationService = paymentObligationService;
    }

    public void setPaymentServiceLocal(final PaymentServiceLocal paymentService) {
        this.paymentService = paymentService;
    }

    public void setPermissionServiceLocal(final PermissionServiceLocal permissionService) {
        this.permissionService = permissionService;
    }

    public void setSettingsServiceLocal(final SettingsServiceLocal settingsService) {
        this.settingsService = settingsService;
    }

    public void setTransactionHelper(final TransactionHelper transactionHelper) {
        this.transactionHelper = transactionHelper;
    }

    @Override
    public void validate(final Guarantee guarantee, final boolean isAuthorization) {
        final Validator validator = isAuthorization ? getValidatorForAuthorization(guarantee) : getValidator(guarantee);
        validator.validate(guarantee);
    }

    /**
     * Adds the fee's amount to the loan's amount only if the fee payer is the buyer and the model is different from WITH_BUYER_ONLY
     * @param loanAmount
     * @param feePayer
     * @param guarantee
     */
    private BigDecimal addFeeToLoanAmount(BigDecimal loanAmount, final AccountOwner feePayer, final BigDecimal fee, final Guarantee guarantee) {
        final LocalSettings localSettings = settingsService.getLocalSettings();
        final boolean hasFee = BigDecimal.ZERO.compareTo(localSettings.round(fee)) == -1;
        final GuaranteeType.Model model = guarantee.getGuaranteeType().getModel();

        if (model != GuaranteeType.Model.WITH_BUYER_ONLY && feePayer == guarantee.getBuyer().getAccountOwner() && hasFee) {
            loanAmount = loanAmount.add(localSettings.round(fee));
        }
        return loanAmount;
    }

    /**
     * Calculates the initial guarantee's status according to the guarantee's type
     * @param guarantee
     */
    private Status calcInitialStatus(final Guarantee guarantee) {
        final GuaranteeType guaranteeType = guaranteeTypeService.load(guarantee.getGuaranteeType().getId());
        if (guaranteeType.getModel() == GuaranteeType.Model.WITH_PAYMENT_OBLIGATION) {
            return Status.PENDING_ISSUER;
        } else {
            switch (guaranteeType.getAuthorizedBy()) {
                case ISSUER:
                case BOTH:
                    return Status.PENDING_ISSUER;
                case ADMIN:
                    return Status.PENDING_ADMIN;
                case NONE:
                    return Status.ACCEPTED;
                default:
                    throw new IllegalArgumentException("Unsupported authorizer value: " + guaranteeType.getAuthorizedBy());
            }
        }
    }

    private String convertFee(final boolean isCreditFee, final Guarantee guarantee) {
        final LocalSettings localSettings = settingsService.getLocalSettings();
        NumberConverter<BigDecimal> numberConverter;

        final GuaranteeType guaranteeType = guarantee.getGuaranteeType();
        final GuaranteeFeeVO feeSpec = isCreditFee ? guarantee.getCreditFeeSpec() : guarantee.getIssueFeeSpec();

        if (feeSpec.getType() == FeeType.FIXED) {
            numberConverter = localSettings.getUnitsConverter(guaranteeType.getCurrency().getPattern());
            return numberConverter.toString(feeSpec.getFee());
        } else {
            numberConverter = localSettings.getNumberConverter();
            return numberConverter.toString(feeSpec.getFee()) + " " + messageResolver.message("guaranteeType.feeType." + feeSpec.getType());
        }
    }

    private Guarantee doChangeStatus(Guarantee guarantee, Status newStatus, final boolean automaticLoanAuthorization) {
        // this is necessary to ensure an instance of (the entity) Guarantee
        guarantee = fetchService.fetch(guarantee, Guarantee.Relationships.PAYMENT_OBLIGATIONS);

        final boolean changeAllowed = canChangeStatus(guarantee, newStatus);

        if (!changeAllowed) {
            throw new GuaranteeStatusChangeException(newStatus);
        } else {
            // Force the status to PENDING_ADMIN if the condition is true
            if (newStatus == Guarantee.Status.ACCEPTED && isInSomeStatus(guarantee, Status.PENDING_ISSUER) && guarantee.getGuaranteeType().getAuthorizedBy() == GuaranteeType.AuthorizedBy.BOTH) {
                newStatus = Status.PENDING_ADMIN;
            }

            // Create log of the status changing
            final GuaranteeLog log = guarantee.changeStatus(newStatus, LoggedUser.user().getElement());
            saveLog(log);

            // Generates a new loan if the status of guarantee is ACCEPTED and the begin period is now or before.
            grantLoan(Calendar.getInstance(), guarantee, automaticLoanAuthorization);

            // Save guarantee
            save(guarantee, true);

            // If the guarantee was cancelled, change the status of associated payment obligations
            if (newStatus == Status.CANCELLED) {
                updateAssociatedPaymentObligations(guarantee);
            }
        }
        return guarantee; // the return is used in the aspects
    }

    /**
     * Generates a new loan only if the guarantee' status is ACCEPTED and the begin period is now or before.
     * @param guarantee
     * @param time the times used as the current time
     */
    private void doGrantLoan(final Calendar time, final Guarantee guarantee, final boolean automaticLoanAuthorization) {
        if (guarantee.getStatus() != Guarantee.Status.ACCEPTED || guarantee.getValidity().getBegin().after(time)) {
            return;
        }

        transactionHelper.maybeRunInNewTransaction(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(final TransactionStatus status) {
                performGrantLoan(guarantee, automaticLoanAuthorization);
            }
        });
    }

    private Collection<? extends MemberGroup> filterBuyers() {
        return filterMemberGroups(new Predicate() {
            @Override
            public boolean evaluate(final Object object) {
                return isBuyerMember((Group) object);
            }
        });
    }

    private Collection<? extends MemberGroup> filterIssuers() {
        return filterMemberGroups(new Predicate() {
            @Override
            public boolean evaluate(final Object object) {
                return isIssuerMember((Group) object);
            }
        });
    }

    private Collection<? extends MemberGroup> filterMemberGroups(final Predicate predicate) {
        return filterMemberGroups(predicate, null);
    }

    @SuppressWarnings("unchecked")
    private Collection<? extends MemberGroup> filterMemberGroups(final Predicate predicate, Collection<? extends Group> groups) {
        Predicate predicateToApply = predicate;

        if (groups == null) { // search for not removed member and broker groups
            final GroupQuery query = new GroupQuery();
            query.setStatus(Group.Status.NORMAL);
            query.setNatures(Group.Nature.MEMBER, Group.Nature.BROKER);

            groups = groupService.search(query);
        } else if (groups.isEmpty()) { // if the group list is empty then return the same (empty) list
            return (Collection<? extends MemberGroup>) groups;
        } else { // it creates a predicate to filter not removed member and broker groups
            final Predicate memberGroupPredicate = new Predicate() {
                @Override
                public boolean evaluate(final Object object) {
                    final Group grp = (Group) object;
                    return Group.Status.NORMAL == grp.getStatus() && (Group.Nature.MEMBER == grp.getNature() || Group.Nature.BROKER == grp.getNature());
                }
            };

            predicateToApply = predicate == null ? memberGroupPredicate : new AndPredicate(memberGroupPredicate, predicate);
        }

        CollectionUtils.filter(groups, predicateToApply);
        return (Collection<? extends MemberGroup>) groups;
    }

    private Collection<? extends MemberGroup> filterSellers() {
        return filterMemberGroups(new Predicate() {
            @Override
            public boolean evaluate(final Object object) {
                return isSellerMember((Group) object);
            }
        });
    }

    /**
     * 
     * @param isCreditFee if false it is issue fee payer
     * @param guaranteeType
     * @param guarantee
     * @return the guarantee's fee payer according to GuaranteeType
     */
    private AccountOwner getFeePayer(final boolean isCreditFee, final Guarantee guarantee) {
        Member payer = null;
        final GuaranteeType guaranteeType = guarantee.getGuaranteeType();
        if (isCreditFee) {
            payer = guaranteeType.getCreditFeePayer() == GuaranteeType.FeePayer.BUYER ? guarantee.getBuyer() : guarantee.getSeller();
        } else {
            payer = guaranteeType.getIssueFeePayer() == GuaranteeType.FeePayer.BUYER ? guarantee.getBuyer() : guarantee.getSeller();
        }

        return payer.getAccountOwner();
    }

    private Validator getValidator(final Guarantee guarantee) {
        final Validator validator = new Validator("guarantee");
        validator.property("guaranteeType").required();

        final GuaranteeType guaranteeType = fetchService.fetch(guarantee.getGuaranteeType());
        if (guaranteeType != null) {
            if (guaranteeType.getModel() != GuaranteeType.Model.WITH_BUYER_ONLY) {
                validator.property("seller").required().key("guarantee.sellerUsername");
            }

            // Custom fields
            validator.chained(new DelegatingValidator(new DelegatingValidator.DelegateSource() {
                @Override
                public Validator getValidator() {
                    final TransferType transferType = guaranteeType.getLoanTransferType();
                    return paymentCustomFieldService.getValueValidator(transferType);
                }
            }));
        }

        validator.property("buyer").required().key("guarantee.buyerUsername");
        validator.property("issuer").required().key("guarantee.issuerUsername");
        validator.property("amount").required().positiveNonZero();
        validator.property("validity").add(new PeriodValidation(ValidationType.BOTH_REQUIRED_AND_NOT_EXPIRED)).key("guarantee.validity");

        return validator;
    }

    private Validator getValidatorForAuthorization(final Guarantee guarantee) {
        final Guarantee loaded = load(guarantee.getId(), Guarantee.Relationships.GUARANTEE_TYPE);

        final Validator validator = new Validator("guarantee");
        final GuaranteeFeeValidation guaranteeFeValidation = new GuaranteeFeeValidation();
        validator.general(guaranteeFeValidation);
        validator.property("validity").add(new PeriodValidation(ValidationType.BOTH_REQUIRED_AND_NOT_EXPIRED)).key("guarantee.validity");

        // Custom fields
        validator.chained(new DelegatingValidator(new DelegatingValidator.DelegateSource() {
            @Override
            public Validator getValidator() {
                final TransferType transferType = loaded.getGuaranteeType().getLoanTransferType();
                return paymentCustomFieldService.getValueValidator(transferType);
            }
        }));

        return validator;
    }

    /**
     * Generates a new (running as System) loan only if the guarantee' status is ACCEPTED and the begin period is now or before.
     * @param guarantee
     * @param time the times used as the current time
     */
    private void grantLoan(final Calendar time, final Guarantee guarantee, final boolean automaticLoanAuthorization) {
        // automaticLoanAuthorization can be true only for an Administrator
        if (automaticLoanAuthorization) {
            // in this case we don't run as System because the authorization code doesn't support that (there must be a logged user)
            doGrantLoan(time, guarantee, automaticLoanAuthorization);
        } else { // this could be an Administrator or an Issuer accepting a guarantee
            // we run as System to support an issuer accepting a guarantee without authorizers
            LoggedUser.runAsSystem(new Callable<Void>() {
                @Override
                public Void call() throws Exception {
                    doGrantLoan(time, guarantee, automaticLoanAuthorization);
                    return null;
                }
            });
        }
    }

    /**
     * Calculates the initial Guarantee Status according to the associated GuaranteeType
     * @param guarantee
     */
    private void initialize(final Guarantee guarantee) {
        final Status status = calcInitialStatus(guarantee);

        guarantee.setStatus(status);
        guarantee.setRegistrationDate(Calendar.getInstance());

        final GuaranteeType guaranteeType = fetchService.fetch(guarantee.getGuaranteeType());

        if (guaranteeType.getCreditFee().isReadonly()) {
            guarantee.setCreditFeeSpec((GuaranteeFeeVO) guaranteeType.getCreditFee().clone());
        }
        if (guaranteeType.getIssueFee().isReadonly()) {
            guarantee.setIssueFeeSpec((GuaranteeFeeVO) guaranteeType.getIssueFee().clone());
        }
    }

    private boolean isBuyerMember(final Group group) {
        return permissionService.hasPermission(group, MemberPermission.GUARANTEES_BUY_WITH_PAYMENT_OBLIGATIONS);
    }

    private boolean isInSomeStatus(final Guarantee guarantee, final Status... status) {
        for (final Status s : status) {
            if (guarantee.getStatus() == s) {
                return true;
            }
        }
        return false;
    }

    private boolean isIssuerMember(final Group group) {
        return permissionService.hasPermission(group, MemberPermission.GUARANTEES_ISSUE_GUARANTEES);
    }

    private boolean isSellerMember(final Group group) {
        return permissionService.hasPermission(group, MemberPermission.GUARANTEES_SELL_WITH_PAYMENT_OBLIGATIONS);
    }

    private void performGrantLoan(final Guarantee guarantee, final boolean automaticLoanAuthorization) {
        final GuaranteeType guaranteeType = guarantee.getGuaranteeType();
        final LocalSettings localSettings = settingsService.getLocalSettings();

        final BigDecimal creditFee = guarantee.getCreditFee();
        final BigDecimal issueFee = guarantee.getIssueFee();

        AccountOwner creditFeePayer = null;
        AccountOwner issueFeePayer = null;

        /* obtains the fees payers */
        if (guaranteeType.getModel() != GuaranteeType.Model.WITH_BUYER_ONLY) {
            creditFeePayer = getFeePayer(true, guarantee);
            issueFeePayer = getFeePayer(false, guarantee);
        } else { /* in this case we only have set the buyer in the guarantee */
            creditFeePayer = guarantee.getBuyer().getAccountOwner();
            issueFeePayer = creditFeePayer;
        }
        /* add the credit fee amount to the loan's amount if the fee payer is the buyer */
        BigDecimal loanAmount = addFeeToLoanAmount(guarantee.getAmount(), creditFeePayer, creditFee, guarantee);

        /* add the issue fee amount to the loan's amount if the fee payer is the buyer */
        loanAmount = addFeeToLoanAmount(loanAmount, issueFeePayer, issueFee, guarantee);

        /* grants loan to buyer */
        final GrantSinglePaymentLoanDTO loanDto = new GrantSinglePaymentLoanDTO();
        loanDto.setAutomatic(true);
        loanDto.setMember(guarantee.getBuyer());
        loanDto.setAmount(loanAmount);
        loanDto.setDescription(guaranteeType.getLoanTransferType().getDescription());
        loanDto.setTransferType(guaranteeType.getLoanTransferType());
        loanDto.setRepaymentDate(guarantee.getValidity().getEnd());
        customFieldHelper.cloneFieldValues(guarantee, new CustomFieldsContainer<PaymentCustomField, PaymentCustomFieldValue>() {

            @Override
            public Class<PaymentCustomField> getCustomFieldClass() {
                return loanDto.getCustomFieldClass();
            }

            @Override
            public Class<PaymentCustomFieldValue> getCustomFieldValueClass() {
                return loanDto.getCustomFieldValueClass();
            }

            @Override
            public Collection<PaymentCustomFieldValue> getCustomValues() {
                return loanDto.getCustomValues();
            }

            @Override
            public void setCustomValues(final Collection<PaymentCustomFieldValue> values) {
                loanDto.setCustomValues(values);
            }

        }, true);

        final Loan loan = loanService.grantForGuarantee(loanDto, automaticLoanAuthorization);

        TransferDTO transferDto = null;
        /* only in this case there is a seller to forward to the loan */
        if (guaranteeType.getModel() != GuaranteeType.Model.WITH_BUYER_ONLY) {
            /* forwards loan's amount from Buyer to Seller */
            transferDto = new TransferDTO();
            transferDto.setForced(true);
            transferDto.setContext(TransactionContext.AUTOMATIC);
            transferDto.setFromOwner(guarantee.getBuyer().getAccountOwner());
            transferDto.setToOwner(guarantee.getSeller().getAccountOwner());
            transferDto.setTransferType(guaranteeType.getForwardTransferType());
            transferDto.setAmount(guarantee.getAmount());
            transferDto.setDescription(guaranteeType.getForwardTransferType().getDescription());
            transferDto.setParent(loan.getTransfer());
            paymentService.insertWithoutNotification(transferDto);
        }

        /* credit fee payment from Buyer/Seller (according to GuaranteeType) to system account */
        if (BigDecimal.ZERO.compareTo(localSettings.round(creditFee)) == -1) {
            final Map<String, String> valuesMap = new HashMap<String, String>();
            valuesMap.put("creditFee", convertFee(true, guarantee));

            transferDto = new TransferDTO();
            transferDto.setForced(true);
            transferDto.setFromOwner(creditFeePayer);
            transferDto.setToOwner(SystemAccountOwner.instance());
            transferDto.setTransferType(guaranteeType.getCreditFeeTransferType());
            transferDto.setAmount(creditFee);

            transferDto.setContext(TransactionContext.AUTOMATIC);
            transferDto.setDescription(MessageProcessingHelper.processVariables(guaranteeType.getCreditFeeTransferType().getDescription(), valuesMap));
            transferDto.setParent(loan.getTransfer());
            paymentService.insertWithoutNotification(transferDto);
        }

        /* issue fee payment from Buyer/Seller (according to GuaranteeType) to Issuer */
        if (BigDecimal.ZERO.compareTo(localSettings.round(issueFee)) == -1) {
            final Map<String, String> valuesMap = new HashMap<String, String>();
            valuesMap.put("emissionFee", convertFee(false, guarantee));

            transferDto = new TransferDTO();
            transferDto.setForced(true);
            transferDto.setFromOwner(issueFeePayer);
            transferDto.setToOwner(guarantee.getIssuer().getAccountOwner());
            transferDto.setTransferType(guaranteeType.getIssueFeeTransferType());
            transferDto.setAmount(issueFee);

            transferDto.setContext(TransactionContext.AUTOMATIC);
            transferDto.setDescription(MessageProcessingHelper.processVariables(guaranteeType.getIssueFeeTransferType().getDescription(), valuesMap));
            transferDto.setParent(loan.getTransfer());
            paymentService.insertWithoutNotification(transferDto);
        }

        /* update guarantee with the generated loan */
        guarantee.setLoan(loan);
    }

    /**
     * It saves the Guarantee taking into account if it is new or an already created guarantee (update)
     */
    private Guarantee save(Guarantee guarantee, final boolean validateCustomFields) {
        final Collection<PaymentCustomFieldValue> customValues = guarantee.getCustomValues();
        if (guarantee.isTransient()) {
            initialize(guarantee);
            guarantee = guaranteeDao.insert(guarantee);
            final GuaranteeLog log = guarantee.getNewLog(guarantee.getStatus(), LoggedUser.user().getElement());
            saveLog(log);
        } else {
            guarantee = guaranteeDao.update(guarantee);
        }
        guarantee.setCustomValues(customValues);
        paymentCustomFieldService.saveValues(guarantee, validateCustomFields);
        return guarantee;
    }

    private void updateAssociatedPaymentObligations(final Guarantee guarantee) {
        final Calendar today = DateHelper.truncateNextDay(Calendar.getInstance());
        for (final PaymentObligation paymentObligation : guarantee.getPaymentObligations()) {
            final Calendar expirationDate = paymentObligation.getExpirationDate();
            final Calendar maxPublishDate = paymentObligation.getMaxPublishDate();
            final Element by = LoggedUser.element();
            PaymentObligationLog log = null;
            if (today.after(expirationDate)) {
                log = paymentObligation.changeStatus(PaymentObligation.Status.EXPIRED, by);
            } else if (today.after(maxPublishDate)) {
                log = paymentObligation.changeStatus(PaymentObligation.Status.REGISTERED, by);
                paymentObligation.setMaxPublishDate(null);
            } else {
                log = paymentObligation.changeStatus(PaymentObligation.Status.PUBLISHED, by);
            }
            paymentObligation.setGuarantee(null);
            paymentObligationService.saveLog(log);
            paymentObligationService.save(paymentObligation, false);
        }
    }
}
