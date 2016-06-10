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
package nl.strohalm.cyclos.services.transactions;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import nl.strohalm.cyclos.access.AdminMemberPermission;
import nl.strohalm.cyclos.access.AdminSystemPermission;
import nl.strohalm.cyclos.dao.accounts.loans.LoanDAO;
import nl.strohalm.cyclos.dao.accounts.loans.LoanPaymentDAO;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.Currency;
import nl.strohalm.cyclos.entities.accounts.MemberAccount;
import nl.strohalm.cyclos.entities.accounts.SystemAccountOwner;
import nl.strohalm.cyclos.entities.accounts.external.ExternalTransfer;
import nl.strohalm.cyclos.entities.accounts.loans.Loan;
import nl.strohalm.cyclos.entities.accounts.loans.LoanParameters;
import nl.strohalm.cyclos.entities.accounts.loans.LoanPayment;
import nl.strohalm.cyclos.entities.accounts.loans.LoanPayment.Status;
import nl.strohalm.cyclos.entities.accounts.loans.LoanPaymentQuery;
import nl.strohalm.cyclos.entities.accounts.loans.LoanQuery;
import nl.strohalm.cyclos.entities.accounts.loans.LoanRepaymentAmountsDTO;
import nl.strohalm.cyclos.entities.accounts.transactions.Payment;
import nl.strohalm.cyclos.entities.accounts.transactions.Transfer;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferAuthorizationDTO;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferQuery;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferType;
import nl.strohalm.cyclos.entities.alerts.MemberAlert;
import nl.strohalm.cyclos.entities.exceptions.UnexpectedEntityException;
import nl.strohalm.cyclos.entities.groups.AdminGroup;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.services.InitializingService;
import nl.strohalm.cyclos.services.accounts.AccountDTO;
import nl.strohalm.cyclos.services.accounts.AccountServiceLocal;
import nl.strohalm.cyclos.services.accounts.rates.RateServiceLocal;
import nl.strohalm.cyclos.services.alerts.AlertServiceLocal;
import nl.strohalm.cyclos.services.customization.PaymentCustomFieldServiceLocal;
import nl.strohalm.cyclos.services.fetch.FetchServiceLocal;
import nl.strohalm.cyclos.services.permissions.PermissionServiceLocal;
import nl.strohalm.cyclos.services.settings.SettingsServiceLocal;
import nl.strohalm.cyclos.services.transactions.exceptions.AuthorizedPaymentInPastException;
import nl.strohalm.cyclos.utils.Amount;
import nl.strohalm.cyclos.utils.CacheCleaner;
import nl.strohalm.cyclos.utils.DateHelper;
import nl.strohalm.cyclos.utils.Period;
import nl.strohalm.cyclos.utils.RelationshipHelper;
import nl.strohalm.cyclos.utils.TransactionHelper;
import nl.strohalm.cyclos.utils.Transactional;
import nl.strohalm.cyclos.utils.access.LoggedUser;
import nl.strohalm.cyclos.utils.conversion.CoercionHelper;
import nl.strohalm.cyclos.utils.notifications.MemberNotificationHandler;
import nl.strohalm.cyclos.utils.query.QueryParameters.ResultType;
import nl.strohalm.cyclos.utils.validation.DelegatingValidator;
import nl.strohalm.cyclos.utils.validation.GeneralValidation;
import nl.strohalm.cyclos.utils.validation.InvalidError;
import nl.strohalm.cyclos.utils.validation.PositiveNonZeroValidation;
import nl.strohalm.cyclos.utils.validation.PropertyValidation;
import nl.strohalm.cyclos.utils.validation.RequiredError;
import nl.strohalm.cyclos.utils.validation.RequiredValidation;
import nl.strohalm.cyclos.utils.validation.ValidationError;
import nl.strohalm.cyclos.utils.validation.ValidationException;
import nl.strohalm.cyclos.utils.validation.Validator;

import org.apache.commons.lang.mutable.MutableBoolean;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.transaction.TransactionStatus;

/**
 * Implementation for loan service
 * @author luis
 */
public class LoanServiceImpl implements LoanServiceLocal, InitializingService {

    /**
     * Validates a transfer type, forcing it to be a loan type
     * @author luis
     */
    private class LoanTypeValidation implements PropertyValidation {

        private static final long serialVersionUID = -7166494808222423923L;

        public LoanTypeValidation() {
        }

        @Override
        public ValidationError validate(final Object object, final Object name, final Object value) {
            final TransferType transferType = (TransferType) value;
            if (transferType == null) {
                return null;
            }
            // Ensure transfer type is a loan
            if (!transferType.isLoanType()) {
                return new InvalidError();
            }
            return null;
        }
    }

    /**
     * Validator for multi-payment loan's collection of loan payment
     * @author luis
     */
    private final class MultiPaymentValidation implements PropertyValidation {
        private static final long serialVersionUID = -7905875152926109032L;

        @Override
        @SuppressWarnings("unchecked")
        public ValidationError validate(final Object object, final Object name, final Object value) {
            final GrantMultiPaymentLoanDTO dto = (GrantMultiPaymentLoanDTO) object;
            final Collection<LoanPayment> payments = (Collection<LoanPayment>) value;
            if (payments == null || payments.isEmpty()) {
                return null;
            }
            Calendar lastExpiration = DateHelper.truncate(Calendar.getInstance());
            BigDecimal paymentAmounts = BigDecimal.ZERO;
            final BigDecimal totalAmount = dto.getAmount();
            final boolean processAmount = totalAmount != null && totalAmount.compareTo(BigDecimal.ZERO) == 1;
            // Validate each payment
            for (final LoanPayment payment : payments) {
                // Check for required expiration date
                ValidationError error = RequiredValidation.instance().validate(object, name, payment.getExpirationDate());
                // Check for required amount
                final BigDecimal paymentAmount = payment.getAmount();
                if (error == null) {
                    error = RequiredValidation.instance().validate(object, name, paymentAmount);
                }
                if (error == null) {
                    error = PositiveNonZeroValidation.instance().validate(object, name, paymentAmount);
                }
                // Check if the current expiration date is after the last expiration date
                if (error == null && lastExpiration.after(payment.getExpirationDate())) {
                    error = new ValidationError("loan.grant.error.unsortedPayments");
                }
                // Return any error for this payment
                if (error != null) {
                    return error;
                }
                // Sum an accumulator for total amount comparision
                if (processAmount) {
                    paymentAmounts = paymentAmounts.add(paymentAmount);
                }
                lastExpiration = payment.getExpirationDate();
            }
            // Check if the payment amount sum == total amount
            if ((paymentAmounts.subtract(totalAmount)).abs().floatValue() > PRECISION_DELTA) {
                return new ValidationError("loan.grant.error.invalidAmount");
            }
            return null;
        }
    }

    private TransferAuthorizationServiceLocal transferAuthorizationService;
    private static final float                PRECISION_DELTA = 0.0001F;
    private AccountServiceLocal               accountService;
    private AlertServiceLocal                 alertService;
    private PaymentCustomFieldServiceLocal    paymentCustomFieldService;
    private FetchServiceLocal                 fetchService;
    private LoanDAO                           loanDao;
    private LoanPaymentDAO                    loanPaymentDao;
    private PaymentServiceLocal               paymentService;
    private RateServiceLocal                  rateService;
    private SettingsServiceLocal              settingsService;
    private final Map<Loan.Type, LoanHandler> handlersByType  = new EnumMap<Loan.Type, LoanHandler>(Loan.Type.class);
    private PermissionServiceLocal            permissionService;
    private MemberNotificationHandler         memberNotificationHandler;

    private TransactionHelper                 transactionHelper;

    @Override
    public void alertExpiredLoans(final Calendar time) {
        final Calendar deadline = DateHelper.truncate(time);
        deadline.add(Calendar.DATE, -1);
        final LoanPaymentQuery query = new LoanPaymentQuery();
        query.setResultType(ResultType.ITERATOR);
        query.fetch(RelationshipHelper.nested(LoanPayment.Relationships.LOAN, Loan.Relationships.TRANSFER, Payment.Relationships.TO, MemberAccount.Relationships.MEMBER, Element.Relationships.GROUP));
        query.setExpirationPeriod(Period.endingAt(deadline));
        query.setStatus(LoanPayment.Status.OPEN);
        CacheCleaner cacheCleaner = new CacheCleaner(fetchService);
        final List<LoanPayment> payments = search(query);
        for (final LoanPayment payment : payments) {
            final Loan loan = payment.getLoan();
            payment.setStatus(LoanPayment.Status.EXPIRED);
            loanPaymentDao.update(payment);
            // Create an alert
            final Member member = (Member) loan.getTransfer().getTo().getOwner();
            alertService.create(member, MemberAlert.Alerts.EXPIRED_LOAN);
            // Notify the member
            memberNotificationHandler.expiredLoanNotification(payment);
            cacheCleaner.clearCache();
        }
    }

    @Override
    public List<LoanPayment> calculatePaymentProjection(final ProjectionDTO params) {
        params.setTransferType(fetchService.fetch(params.getTransferType()));
        if (params.getDate() == null) {
            params.setDate(Calendar.getInstance());
        }
        getProjectionValidator().validate(params);
        return handlersByType.get(params.getTransferType().getLoan().getType()).calculatePaymentProjection(params);
    }

    @Override
    public LoanPayment discard(final LoanPaymentDTO dto) {
        final LoanPaymentDTO dateDto = new LoanPaymentDTO();
        dateDto.setLoan(dto.getLoan());
        dateDto.setLoanPayment(dto.getLoanPayment());
        return doDiscard(dateDto);
    }

    @Override
    public LoanPayment discardByExternalTransfer(final Loan loan, final ExternalTransfer externalTransfer) throws UnexpectedEntityException {
        final LoanPaymentDTO dto = new LoanPaymentDTO();
        dto.setLoan(loan);
        final LoanPayment loanPayment = doDiscard(dto);
        loanPayment.setExternalTransfer(externalTransfer);
        return loanPaymentDao.update(loanPayment);
    }

    @Override
    public LoanRepaymentAmountsDTO getLoanPaymentAmount(final LoanPaymentDTO dto) {
        final LoanRepaymentAmountsDTO ret = new LoanRepaymentAmountsDTO();
        Calendar date = dto.getDate();
        if (date == null) {
            date = Calendar.getInstance();
        }
        final Loan loan = fetchService.fetch(dto.getLoan(), Loan.Relationships.TRANSFER, Loan.Relationships.PAYMENTS);
        LoanPayment payment = fetchService.fetch(dto.getLoanPayment());
        if (payment == null) {
            payment = loan.getFirstOpenPayment();
        }
        ret.setLoanPayment(payment);

        // Update the dto with fetched values
        dto.setLoan(loan);
        dto.setLoanPayment(payment);

        if (payment != null) {
            payment = fetchService.fetch(payment, LoanPayment.Relationships.TRANSFERS);
            final BigDecimal paymentAmount = payment.getAmount();
            BigDecimal remainingAmount = paymentAmount;
            Calendar expirationDate = payment.getExpirationDate();
            Calendar lastPaymentDate = (Calendar) expirationDate.clone();
            expirationDate = DateUtils.truncate(expirationDate, Calendar.DATE);
            final LoanParameters parameters = loan.getParameters();
            Collection<Transfer> transfers = payment.getTransfers();
            if (transfers == null) {
                transfers = Collections.emptyList();
            }
            final BigDecimal expirationDailyInterest = CoercionHelper.coerce(BigDecimal.class, parameters.getExpirationDailyInterest());
            final LocalSettings localSettings = settingsService.getLocalSettings();
            final MathContext mathContext = localSettings.getMathContext();
            for (final Transfer transfer : transfers) {
                Calendar trfDate = transfer.getDate();
                trfDate = DateUtils.truncate(trfDate, Calendar.DATE);
                final BigDecimal trfAmount = transfer.getAmount();
                BigDecimal actualAmount = trfAmount;
                final int diffDays = (int) ((trfDate.getTimeInMillis() - expirationDate.getTimeInMillis()) / DateUtils.MILLIS_PER_DAY);
                if (diffDays > 0 && expirationDailyInterest != null) {
                    // Apply interest
                    actualAmount = actualAmount.subtract(remainingAmount.multiply(new BigDecimal(diffDays)).multiply(expirationDailyInterest.divide(new BigDecimal(100), mathContext)));
                }
                remainingAmount = remainingAmount.subtract(actualAmount);
                lastPaymentDate = (Calendar) trfDate.clone();
            }
            date = DateHelper.truncate(date);
            BigDecimal remainingAmountAtDate = remainingAmount;
            final int diffDays = (int) ((date.getTimeInMillis() - (expirationDate.before(lastPaymentDate) ? lastPaymentDate.getTimeInMillis() : expirationDate.getTimeInMillis())) / DateUtils.MILLIS_PER_DAY);
            if (diffDays > 0 && expirationDailyInterest != null) {
                // Apply interest
                remainingAmountAtDate = remainingAmountAtDate.add(remainingAmount.multiply(new BigDecimal(diffDays)).multiply(expirationDailyInterest.divide(new BigDecimal(100), mathContext)));
            }
            final Amount expirationFee = parameters.getExpirationFee();
            if (expirationFee != null && (remainingAmountAtDate.compareTo(BigDecimal.ZERO) == 1) && expirationDate.before(date) && (expirationFee.getValue().compareTo(BigDecimal.ZERO) == 1)) {
                // Apply expiration fee
                remainingAmountAtDate = remainingAmountAtDate.add(expirationFee.apply(remainingAmount));
            }
            // Round the result
            ret.setRemainingAmountAtExpirationDate(localSettings.round(remainingAmount));
            ret.setRemainingAmountAtDate(localSettings.round(remainingAmountAtDate));
        }
        return ret;
    }

    @Override
    public TransactionSummaryVO getOpenLoansSummary(final Currency currency) {
        final LoanQuery query = new LoanQuery();
        query.setStatus(Loan.Status.OPEN);
        query.setCurrency(currency);
        if (LoggedUser.hasUser()) {
            AdminGroup adminGroup = LoggedUser.group();
            adminGroup = fetchService.fetch(adminGroup, AdminGroup.Relationships.MANAGES_GROUPS);
            query.setGroups(adminGroup.getManagesGroups());
        }
        return buildSummary(query);
    }

    @Override
    public Loan grant(final GrantLoanDTO params) {
        return doGrant(params, true);
    }

    @Override
    public Loan grantForGuarantee(final GrantLoanDTO params, final boolean automaticAuthorize) {
        final Loan loan = insert(params);

        if (automaticAuthorize && permissionService.hasPermission(AdminSystemPermission.PAYMENTS_AUTHORIZE) && (loan.getTransfer().getNextAuthorizationLevel() != null)) {
            final TransferAuthorizationDTO transferAuthorizationDto = new TransferAuthorizationDTO();
            transferAuthorizationDto.setTransfer(loan.getTransfer());
            transferAuthorizationService.authorize(transferAuthorizationDto, false);
        }

        return loan;
    }

    @Override
    public void initializeService() {
        alertExpiredLoans(Calendar.getInstance());
    }

    @Override
    public Loan insert(final GrantLoanDTO params) {
        return doGrant(params, false);
    }

    @Override
    public Loan load(final Long id, final Relationship... fetch) {
        return loanDao.load(id, fetch);
    }

    @Override
    public TransactionSummaryVO loanSummary(final Member member) {
        final LoanQuery query = new LoanQuery();
        query.setMember(member);
        query.setStatus(Loan.Status.OPEN);
        return buildSummary(query);
    }

    @Override
    public Loan markAsInProcess(final Loan loan) throws UnexpectedEntityException {
        return markAs(loan, LoanPayment.Status.EXPIRED, LoanPayment.Status.IN_PROCESS);
    }

    @Override
    public Loan markAsRecovered(final Loan loan) throws UnexpectedEntityException {
        return markAs(loan, LoanPayment.Status.IN_PROCESS, LoanPayment.Status.RECOVERED);
    }

    @Override
    public Loan markAsUnrecoverable(final Loan loan) throws UnexpectedEntityException {
        return markAs(loan, LoanPayment.Status.IN_PROCESS, LoanPayment.Status.UNRECOVERABLE);
    }

    @Override
    public TransactionSummaryVO paymentsSummary(final LoanPaymentQuery query) {
        final Status status = query.getStatus();
        if (status == null) {
            throw new ValidationException("status", "loanPayment.status", new RequiredError());
        }
        return loanPaymentDao.paymentsSummary(query);
    }

    @Override
    public Transfer repay(final RepayLoanDTO params) {
        return transactionHelper.maybeRunInNewTransaction(new Transactional<Transfer>() {

            @Override
            public Transfer afterCommit(final Transfer result) {
                return fetchService.fetch(result);
            }

            @Override
            public Transfer doInTransaction(final TransactionStatus status) {
                return doRepay(params);
            }

        });
    }

    @Override
    public List<LoanPayment> search(final LoanPaymentQuery query) {
        return loanPaymentDao.search(query);
    }

    @Override
    public List<Loan> search(final LoanQuery query) {
        if (query.getQueryStatus() == null) {
            query.setHideAuthorizationRelated(!permissionService.hasPermission(AdminMemberPermission.LOANS_VIEW_AUTHORIZED));
        }
        return loanDao.search(query);
    }

    public void setAccountServiceLocal(final AccountServiceLocal accountService) {
        this.accountService = accountService;
    }

    public void setAlertServiceLocal(final AlertServiceLocal alertService) {
        this.alertService = alertService;
    }

    public void setFetchServiceLocal(final FetchServiceLocal fetchService) {
        this.fetchService = fetchService;
    }

    public void setLoanDao(final LoanDAO loanDao) {
        this.loanDao = loanDao;
    }

    public void setLoanPaymentDao(final LoanPaymentDAO loanPaymentDao) {
        this.loanPaymentDao = loanPaymentDao;
    }

    public void setMemberNotificationHandler(final MemberNotificationHandler memberNotificationHandler) {
        this.memberNotificationHandler = memberNotificationHandler;
    }

    public void setMultiPaymentHandler(final LoanHandler handler) {
        handlersByType.put(Loan.Type.MULTI_PAYMENT, handler);
    }

    public void setPaymentCustomFieldServiceLocal(final PaymentCustomFieldServiceLocal paymentCustomFieldService) {
        this.paymentCustomFieldService = paymentCustomFieldService;
    }

    public void setPaymentServiceLocal(final PaymentServiceLocal paymentService) {
        this.paymentService = paymentService;
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

    public void setSinglePaymentHandler(final LoanHandler handler) {
        handlersByType.put(Loan.Type.SINGLE_PAYMENT, handler);
    }

    public void setTransactionHelper(final TransactionHelper transactionHelper) {
        this.transactionHelper = transactionHelper;
    }

    public void setTransferAuthorizationServiceLocal(final TransferAuthorizationServiceLocal transferAuthorizationService) {
        this.transferAuthorizationService = transferAuthorizationService;
    }

    public void setWithInterestHandler(final LoanHandler handler) {
        handlersByType.put(Loan.Type.WITH_INTEREST, handler);
    }

    @Override
    public void validate(final GrantLoanDTO params) {
        final Validator validator = getValidator(params);
        if (validator == null) {
            throw new ValidationException("transferType", "loan.type", new InvalidError());
        }
        validator.validate(params);
    }

    private TransactionSummaryVO buildSummary(final LoanQuery query) {
        BigDecimal amount = BigDecimal.ZERO;
        int count = 0;
        final List<Loan> loans = loanDao.search(query);
        for (Loan loan : loans) {
            count++;
            loan = fetchService.fetch(loan, Loan.Relationships.PAYMENTS);
            final List<LoanPayment> payments = loan.getPayments();
            if (payments != null) {
                for (final LoanPayment payment : payments) {
                    amount = amount.add(payment.getRemainingAmount());
                }
            }
        }
        final TransactionSummaryVO ret = new TransactionSummaryVO();
        ret.setCount(count);
        ret.setAmount(amount);
        return ret;
    }

    private LoanPayment doDiscard(final LoanPaymentDTO dto) {
        final Loan loan = fetchService.fetch(dto.getLoan(), Loan.Relationships.PAYMENTS);
        LoanPayment payment = fetchService.fetch(dto.getLoanPayment());
        final Calendar date = dto.getDate() == null ? Calendar.getInstance() : dto.getDate();
        if (payment == null) {
            payment = loan.getFirstOpenPayment();
        }
        if (payment == null) {
            throw new UnexpectedEntityException();
        }
        payment.setStatus(LoanPayment.Status.DISCARDED);
        payment.setRepaymentDate(date);
        return loanPaymentDao.update(payment);
    }

    private Loan doGrant(final GrantLoanDTO params, final boolean newTransaction) {
        validate(params);

        // Fetch and update the transfer type
        final TransferType transferType = fetchService.fetch(params.getTransferType());
        params.setTransferType(transferType);

        // Insert the loan
        final Loan loan = handlersByType.get(params.getLoanType()).buildLoan(params);

        return transactionHelper.maybeRunInNewTransaction(new Transactional<Loan>() {

            @Override
            public Loan afterCommit(final Loan result) {
                return fetchService.fetch(result);
            }

            @Override
            public Loan doInTransaction(final TransactionStatus status) {
                return doGrant(loan, params);
            }
        }, newTransaction);
    }

    private Loan doGrant(Loan loan, final GrantLoanDTO params) {
        final LocalSettings localSettings = settingsService.getLocalSettings();
        final TransferType transferType = params.getTransferType();

        // Create the loan transfer
        final TransferDTO transferDto = new TransferDTO();
        if (params.isAutomatic()) {
            transferDto.setContext(TransactionContext.AUTOMATIC_LOAN);
        } else {
            transferDto.setContext(TransactionContext.LOAN);
        }
        if (params.getDate() != null) {
            transferDto.setDate(params.getDate());
        }
        transferDto.setToOwner(params.getMember());
        transferDto.setFrom(accountService.getAccount(new AccountDTO(SystemAccountOwner.instance(), transferType.getFrom())));
        transferDto.setTo(accountService.getAccount(new AccountDTO(transferDto.getToOwner(), transferType.getTo())));
        transferDto.setAmount(params.getAmount());
        transferDto.setDescription(params.getDescription());
        transferDto.setTransferType(transferType);
        transferDto.setCustomValues(params.getCustomValues());
        transferDto.setRates(rateService.applyLoan(transferDto, params));
        final Transfer transfer = (Transfer) paymentService.insertWithoutNotification(transferDto);
        if (transfer.getProcessDate() == null && params.getDate() != null && DateHelper.daysBetween(params.getDate(), Calendar.getInstance()) != 0) {
            throw new AuthorizedPaymentInPastException();
        }

        // Persist the loan
        loan.setTransfer(transfer);
        final List<LoanPayment> payments = loan.getPayments();
        loan = loanDao.insert(loan);
        loan.setPayments(new ArrayList<LoanPayment>());

        // Insert the installments
        int index = 0;
        BigDecimal total = BigDecimal.ZERO;
        for (final LoanPayment payment : payments) {
            payment.setLoan(loan);
            payment.setIndex(index++);
            BigDecimal amount = localSettings.round(payment.getAmount());
            if (index == payments.size()) {
                // The last payment should round to total amount
                amount = localSettings.round(loan.getTotalAmount().subtract(total));
            } else {
                total = total.add(amount);
            }
            payment.setAmount(amount);
            loan.getPayments().add(loanPaymentDao.insert(payment));
        }

        // Notify
        memberNotificationHandler.grantedLoanNotification(loan);

        return loan;
    }

    private Transfer doRepay(final RepayLoanDTO params) {
        BigDecimal amount = params.getAmount();

        // Check if the amount is valid
        if (amount.compareTo(paymentService.getMinimumPayment()) < 0) {
            throw new ValidationException("amount", "loan.amount", new InvalidError());
        }

        // Get the loan payment to repay
        Calendar date = params.getDate();
        if (date == null) {
            date = Calendar.getInstance();
            params.setDate(date);
        }
        final LoanRepaymentAmountsDTO amountsDTO = getLoanPaymentAmount(params);
        final LoanPayment payment = amountsDTO.getLoanPayment();
        if (payment == null) {
            throw new UnexpectedEntityException();
        }

        // Validate the amount
        final BigDecimal remainingAmount = amountsDTO.getRemainingAmountAtDate();
        final BigDecimal diff = remainingAmount.subtract(amount);
        final MutableBoolean totallyRepaid = new MutableBoolean();
        // If the amount is on an acceptable delta, set the transfer value = parcel value
        if (diff.abs().floatValue() < PRECISION_DELTA) {
            amount = remainingAmount;
            totallyRepaid.setValue(true);
        } else if (diff.compareTo(BigDecimal.ZERO) < 0 || !params.getLoan().getTransfer().getType().getLoan().getType().allowsPartialRepayments()) {
            throw new ValidationException("amount", "loan.amount", new InvalidError());
        }
        final LocalSettings localSettings = settingsService.getLocalSettings();
        Loan loan = fetchService.fetch(params.getLoan(), Loan.Relationships.PAYMENTS, RelationshipHelper.nested(Loan.Relationships.TRANSFER, Payment.Relationships.TO, MemberAccount.Relationships.MEMBER), Loan.Relationships.TO_MEMBERS);

        // Build the transfers for repayment
        final List<TransferDTO> transfers = handlersByType.get(loan.getParameters().getType()).buildTransfersForRepayment(params, amountsDTO);
        Transfer root = null;
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (final TransferDTO dto : transfers) {
            if (dto.getAmount().floatValue() < PRECISION_DELTA) {
                // If the root amount is zero, it means that the parent transfer should be the last transfer for this loan payment
                final TransferQuery tq = new TransferQuery();
                tq.setLoanPayment(payment);
                tq.setReverseOrder(true);
                tq.setUniqueResult();
                final List<Transfer> paymentTransfers = paymentService.search(tq);
                if (paymentTransfers.isEmpty()) {
                    throw new IllegalStateException("The root transfer has amount 0 and there is no other transfers for this payment");
                }
                root = paymentTransfers.iterator().next();
            } else {
                totalAmount = totalAmount.add(dto.getAmount());
                dto.setParent(root);
                dto.setLoanPayment(payment);
                final Transfer transfer = (Transfer) paymentService.insertWithoutNotification(dto);
                if (root == null) {
                    // The first will be the root. All others are it's children
                    root = transfer;
                }
            }
        }

        // Update the loan payment
        final BigDecimal totalRepaid = localSettings.round(payment.getRepaidAmount().add(totalAmount));
        payment.setRepaidAmount(totalRepaid);
        if (totallyRepaid.booleanValue()) {
            // Mark the payment as repaid, if is the case
            payment.setStatus(LoanPayment.Status.REPAID);
            payment.setRepaymentDate(params.getDate());
        }
        payment.setTransfers(null); // Avoid 2 representations of the transfers collection. It's inverse="true", no problem setting null
        loanPaymentDao.update(payment);

        // Return the generated root transfer
        return root;
    }

    private Validator getProjectionValidator() {
        final Validator projectionValidator = new Validator("loan");
        projectionValidator.property("transferType").key("loan.type").required().add(new LoanTypeValidation());
        projectionValidator.property("amount").required().positiveNonZero();
        projectionValidator.property("firstExpirationDate").future().required();
        projectionValidator.property("paymentCount").required().positiveNonZero();
        return projectionValidator;
    }

    private Validator getValidator(final GrantLoanDTO params) {
        // The transfer type is implicitly validated by returning null on non-loan types
        final TransferType transferType = fetchService.fetch(params.getTransferType());
        Loan.Type type;
        try {
            type = transferType.getLoan().getType();
        } catch (final Exception e) {
            return null;
        }

        final Validator validator = new Validator("loan");
        validator.property("amount").required().positiveNonZero();
        final Currency currency = fetchService.fetch(transferType.getCurrency(), Currency.Relationships.A_RATE_PARAMETERS, Currency.Relationships.D_RATE_PARAMETERS);
        if (currency.isEnableARate() || currency.isEnableDRate()) {
            // if the date is not null, it might be a payment in past, which is not allowed with rates enabled.
            if (params.getDate() != null) {
                final Calendar now = Calendar.getInstance();
                // make a few minutes earlier, because if the transfer's date has just before been set to Calendar.getInstance(), it may already be a
                // few milliseconds or even seconds later.
                now.add(Calendar.MINUTE, -4);
                if (params.getDate().before(now)) {
                    validator.general(new GeneralValidation() {
                        private static final long serialVersionUID = -7221645724425619586L;

                        @Override
                        public ValidationError validate(final Object object) {
                            return new ValidationError("payment.error.pastDateWithRates");
                        }
                    });
                }
            }
        } else {
            validator.property("date").key("loan.grant.manualDate").pastOrToday();
        }
        validator.property("description").required().maxLength(1000);
        validator.property("member").key("member.member").required();
        switch (type) {
            case SINGLE_PAYMENT:
                validator.property("repaymentDate").required();
                break;
            case MULTI_PAYMENT:
                validator.property("payments").required().add(new MultiPaymentValidation());
                break;
            case WITH_INTEREST:
                validator.property("firstRepaymentDate").future().required();
                validator.property("paymentCount").required().positiveNonZero();
        }
        // Custom fields
        validator.chained(new DelegatingValidator(new DelegatingValidator.DelegateSource() {
            @Override
            public Validator getValidator() {
                return paymentCustomFieldService.getValueValidator(transferType);
            }
        }));
        return validator;
    }

    private Loan markAs(Loan loan, final LoanPayment.Status expectedStatus, final LoanPayment.Status newStatus) {
        loan = fetchService.fetch(loan, Loan.Relationships.PAYMENTS);
        for (final LoanPayment current : loan.getPayments()) {
            if (current.getStatus() == expectedStatus) {
                current.setStatus(newStatus);
                loanPaymentDao.update(current);
            }
        }
        return fetchService.reload(loan);
    }

}
