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

import java.awt.Color;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
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

import nl.strohalm.cyclos.access.AdminMemberPermission;
import nl.strohalm.cyclos.access.AdminSystemPermission;
import nl.strohalm.cyclos.access.BrokerPermission;
import nl.strohalm.cyclos.access.MemberPermission;
import nl.strohalm.cyclos.access.OperatorPermission;
import nl.strohalm.cyclos.dao.accounts.transactions.ScheduledPaymentDAO;
import nl.strohalm.cyclos.dao.accounts.transactions.TraceNumberDAO;
import nl.strohalm.cyclos.dao.accounts.transactions.TransferDAO;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.access.Channel;
import nl.strohalm.cyclos.entities.accounts.Account;
import nl.strohalm.cyclos.entities.accounts.AccountOwner;
import nl.strohalm.cyclos.entities.accounts.AccountStatus;
import nl.strohalm.cyclos.entities.accounts.AccountType;
import nl.strohalm.cyclos.entities.accounts.Currency;
import nl.strohalm.cyclos.entities.accounts.LockedAccountsOnPayments;
import nl.strohalm.cyclos.entities.accounts.MemberAccount;
import nl.strohalm.cyclos.entities.accounts.SystemAccountOwner;
import nl.strohalm.cyclos.entities.accounts.SystemAccountType;
import nl.strohalm.cyclos.entities.accounts.external.ExternalTransfer;
import nl.strohalm.cyclos.entities.accounts.fees.transaction.BrokerCommission;
import nl.strohalm.cyclos.entities.accounts.fees.transaction.SimpleTransactionFee;
import nl.strohalm.cyclos.entities.accounts.fees.transaction.TransactionFee;
import nl.strohalm.cyclos.entities.accounts.fees.transaction.TransactionFee.ChargeType;
import nl.strohalm.cyclos.entities.accounts.fees.transaction.TransactionFeeQuery;
import nl.strohalm.cyclos.entities.accounts.transactions.AuthorizationLevel;
import nl.strohalm.cyclos.entities.accounts.transactions.Invoice;
import nl.strohalm.cyclos.entities.accounts.transactions.Payment;
import nl.strohalm.cyclos.entities.accounts.transactions.PaymentRequestTicket;
import nl.strohalm.cyclos.entities.accounts.transactions.ScheduledPayment;
import nl.strohalm.cyclos.entities.accounts.transactions.Ticket;
import nl.strohalm.cyclos.entities.accounts.transactions.TraceNumber;
import nl.strohalm.cyclos.entities.accounts.transactions.Transfer;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferListener;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferQuery;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferType;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferType.Relationships;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferTypeQuery;
import nl.strohalm.cyclos.entities.alerts.MemberAlert;
import nl.strohalm.cyclos.entities.customization.fields.CustomField;
import nl.strohalm.cyclos.entities.customization.fields.PaymentCustomField;
import nl.strohalm.cyclos.entities.customization.fields.PaymentCustomFieldValue;
import nl.strohalm.cyclos.entities.exceptions.DaoException;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.entities.exceptions.UnexpectedEntityException;
import nl.strohalm.cyclos.entities.groups.AdminGroup;
import nl.strohalm.cyclos.entities.groups.BrokerGroup;
import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.groups.OperatorGroup;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.members.Operator;
import nl.strohalm.cyclos.entities.members.brokerings.BrokerCommissionContract;
import nl.strohalm.cyclos.entities.members.brokerings.BrokerCommissionContractQuery;
import nl.strohalm.cyclos.entities.reports.StatisticalNumber;
import nl.strohalm.cyclos.entities.services.ServiceClient;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.entities.settings.LocalSettings.TransactionNumber;
import nl.strohalm.cyclos.exceptions.ApplicationException;
import nl.strohalm.cyclos.exceptions.PermissionDeniedException;
import nl.strohalm.cyclos.services.accounts.AccountDTO;
import nl.strohalm.cyclos.services.accounts.AccountDateDTO;
import nl.strohalm.cyclos.services.accounts.AccountServiceLocal;
import nl.strohalm.cyclos.services.accounts.rates.ConversionSimulationDTO;
import nl.strohalm.cyclos.services.accounts.rates.RateServiceLocal;
import nl.strohalm.cyclos.services.accounts.rates.RatesPreviewDTO;
import nl.strohalm.cyclos.services.accounts.rates.RatesResultDTO;
import nl.strohalm.cyclos.services.accounts.rates.RatesToSave;
import nl.strohalm.cyclos.services.alerts.AlertServiceLocal;
import nl.strohalm.cyclos.services.application.ApplicationServiceLocal;
import nl.strohalm.cyclos.services.customization.PaymentCustomFieldServiceLocal;
import nl.strohalm.cyclos.services.elements.CommissionServiceLocal;
import nl.strohalm.cyclos.services.fetch.FetchServiceLocal;
import nl.strohalm.cyclos.services.permissions.PermissionServiceLocal;
import nl.strohalm.cyclos.services.settings.SettingsServiceLocal;
import nl.strohalm.cyclos.services.stats.StatisticalResultDTO;
import nl.strohalm.cyclos.services.transactions.exceptions.AuthorizedPaymentInPastException;
import nl.strohalm.cyclos.services.transactions.exceptions.MaxAmountPerDayExceededException;
import nl.strohalm.cyclos.services.transactions.exceptions.NotEnoughCreditsException;
import nl.strohalm.cyclos.services.transactions.exceptions.TransferMinimumPaymentException;
import nl.strohalm.cyclos.services.transactions.exceptions.UpperCreditLimitReachedException;
import nl.strohalm.cyclos.services.transfertypes.BuildTransferWithFeesDTO;
import nl.strohalm.cyclos.services.transfertypes.TransactionFeePreviewDTO;
import nl.strohalm.cyclos.services.transfertypes.TransactionFeePreviewForRatesDTO;
import nl.strohalm.cyclos.services.transfertypes.TransactionFeeServiceLocal;
import nl.strohalm.cyclos.services.transfertypes.TransferTypeServiceLocal;
import nl.strohalm.cyclos.utils.BaseTransactional;
import nl.strohalm.cyclos.utils.BigDecimalHelper;
import nl.strohalm.cyclos.utils.CacheCleaner;
import nl.strohalm.cyclos.utils.CustomObjectHandler;
import nl.strohalm.cyclos.utils.DataIteratorHelper;
import nl.strohalm.cyclos.utils.DateHelper;
import nl.strohalm.cyclos.utils.MessageProcessingHelper;
import nl.strohalm.cyclos.utils.MessageResolver;
import nl.strohalm.cyclos.utils.Period;
import nl.strohalm.cyclos.utils.RelationshipHelper;
import nl.strohalm.cyclos.utils.TimePeriod;
import nl.strohalm.cyclos.utils.TransactionHelper;
import nl.strohalm.cyclos.utils.Transactional;
import nl.strohalm.cyclos.utils.access.LoggedUser;
import nl.strohalm.cyclos.utils.conversion.CalendarConverter;
import nl.strohalm.cyclos.utils.conversion.CoercionHelper;
import nl.strohalm.cyclos.utils.lock.LockHandler;
import nl.strohalm.cyclos.utils.lock.LockHandlerFactory;
import nl.strohalm.cyclos.utils.logging.LoggingHandler;
import nl.strohalm.cyclos.utils.notifications.AdminNotificationHandler;
import nl.strohalm.cyclos.utils.notifications.MemberNotificationHandler;
import nl.strohalm.cyclos.utils.query.QueryParameters.ResultType;
import nl.strohalm.cyclos.utils.statistics.GraphHelper;
import nl.strohalm.cyclos.utils.transaction.CurrentTransactionData;
import nl.strohalm.cyclos.utils.transaction.TransactionCommitListener;
import nl.strohalm.cyclos.utils.validation.CompareToValidation;
import nl.strohalm.cyclos.utils.validation.DelegatingValidator;
import nl.strohalm.cyclos.utils.validation.GeneralValidation;
import nl.strohalm.cyclos.utils.validation.InvalidError;
import nl.strohalm.cyclos.utils.validation.PropertyValidation;
import nl.strohalm.cyclos.utils.validation.RequiredError;
import nl.strohalm.cyclos.utils.validation.UniqueError;
import nl.strohalm.cyclos.utils.validation.ValidationError;
import nl.strohalm.cyclos.utils.validation.ValidationException;
import nl.strohalm.cyclos.utils.validation.Validator;
import nl.strohalm.cyclos.utils.validation.Validator.Property;
import nl.strohalm.cyclos.webservices.accounts.AccountHistoryResultPage;
import nl.strohalm.cyclos.webservices.model.AccountHistoryTransferVO;
import nl.strohalm.cyclos.webservices.model.AccountStatusVO;
import nl.strohalm.cyclos.webservices.payments.AccountHistoryParams;
import nl.strohalm.cyclos.webservices.utils.AccountHelper;
import nl.strohalm.cyclos.webservices.utils.PaymentHelper;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jfree.chart.plot.CategoryMarker;
import org.jfree.chart.plot.Marker;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;

/**
 * Implementation for payment service
 * @author luis
 * @author rinke (rates stuff)
 */
public class PaymentServiceImpl implements PaymentServiceLocal {

    /**
     * A key to monitor which fees have been charged, in order to detect loops
     * @author luis
     */
    private static class ChargedFee {
        private final TransactionFee fee;
        private final Account        from;
        private final Account        to;

        private ChargedFee(final TransactionFee fee, final Account from, final Account to) {
            this.fee = fee;
            this.from = from;
            this.to = to;
        }

        @Override
        public boolean equals(final Object obj) {
            if (!(obj instanceof ChargedFee)) {
                return false;
            }
            final ChargedFee f = (ChargedFee) obj;
            return new EqualsBuilder().append(fee, f.fee).append(from, f.from).append(to, f.to).isEquals();
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder().append(fee).append(from).append(to).toHashCode();
        }
    }

    /**
     * validator always returning a validationError. To be called if the final amount of a payment (after applying all fees) is negative
     * @author rinke
     */
    private final class FinalAmountValidator implements GeneralValidation {
        private static final long serialVersionUID = -2789145696000017181L;

        @Override
        public ValidationError validate(final Object object) {
            return new ValidationError("payment.error.negativeFinalAmount");
        }
    }

    /**
     * validator which always returns a validationError. To be called if a past date on a transfer is combined with rates.
     * @author Rinke
     * 
     */
    private static final class NoPastDateWithRatesValidator implements GeneralValidation {

        private static final long serialVersionUID = -6914314732478889087L;

        @Override
        public ValidationError validate(final Object object) {
            return new ValidationError("payment.error.pastDateWithRates");
        }
    }

    private final class PendingContractValidator implements GeneralValidation {

        private static final long serialVersionUID = 5608258953479316287L;

        @Override
        @SuppressWarnings("unchecked")
        public ValidationError validate(final Object object) {
            // Validate the scheduled payments
            final DoPaymentDTO payment = (DoPaymentDTO) object;

            Member fromMember = (Member) (payment.getFrom() instanceof Member ? payment.getFrom() : payment.getFrom() == null ? LoggedUser.member() : null);
            if (fromMember != null) {
                fromMember = fetchService.fetch(fromMember, Element.Relationships.GROUP);

                // Validate if there is a fee (broker commission) with a pending contract
                if (payment.getTo() != null && payment.getTo() instanceof Member && payment.getTransferType() != null) {
                    final TransferType transferType = fetchService.fetch(payment.getTransferType(), TransferType.Relationships.TRANSACTION_FEES);
                    final Collection<TransactionFee> transactionFees = (Collection<TransactionFee>) fetchService.fetch(transferType.getTransactionFees(), TransactionFee.Relationships.GENERATED_TRANSFER_TYPE);
                    for (final TransactionFee transactionFee : transactionFees) {
                        if (transactionFee instanceof BrokerCommission && transactionFee.isFromMember()) {
                            final BrokerCommission brokerCommission = (BrokerCommission) transactionFee;
                            final BrokerCommissionContractQuery contractsQuery = new BrokerCommissionContractQuery();
                            contractsQuery.setBrokerCommission(brokerCommission);
                            contractsQuery.setStatus(BrokerCommissionContract.Status.PENDING);
                            switch (brokerCommission.getWhichBroker()) {
                                case SOURCE:
                                    contractsQuery.setMember(fromMember);
                                    break;
                                case DESTINATION:
                                    contractsQuery.setMember((Member) payment.getTo());
                                    break;
                            }
                            final List<BrokerCommissionContract> commissionContracts = commissionService.searchBrokerCommissionContracts(contractsQuery);
                            if (CollectionUtils.isNotEmpty(commissionContracts)) {
                                return new ValidationError("payment.error.pendingCommissionContract", brokerCommission.getName());
                            }
                        }
                    }
                }
            }
            return null;
        }
    }

    private final class SchedulingValidator implements GeneralValidation {

        private static final long serialVersionUID = 4085922259108191939L;

        @Override
        @SuppressWarnings("unchecked")
        public ValidationError validate(final Object object) {
            // Validate the scheduled payments
            final DoPaymentDTO payment = (DoPaymentDTO) object;
            final List<ScheduledPaymentDTO> payments = payment.getPayments();
            if (CollectionUtils.isEmpty(payments)) {
                return null;
            }

            final TransferType transferType = fetchService.fetch(payment.getTransferType(), TransferType.Relationships.TRANSACTION_FEES);

            // It is assumed that the validation where this Validator is used, checks the requirement of the transferType.
            // So it's safe to return, cause the validation will fail.
            if (transferType == null) {
                return null;
            }

            // Validate the from member
            Member fromMember = null;
            if (payment.getFrom() instanceof Member) {
                fromMember = fetchService.fetch((Member) payment.getFrom(), Element.Relationships.GROUP);
            } else if (LoggedUser.hasUser() && LoggedUser.isMember()) {
                fromMember = LoggedUser.element();
            }
            Calendar maxPaymentDate = null;
            if (fromMember != null) {
                final MemberGroup group = fromMember.getMemberGroup();

                // Validate the max payments
                final int maxSchedulingPayments = transferType.isAllowsScheduledPayments() ? group.getMemberSettings().getMaxSchedulingPayments() : 0;
                if (payments.size() > maxSchedulingPayments) {
                    return new ValidationError("errors.greaterEquals", messageResolver.message("transfer.paymentCount"), maxSchedulingPayments);
                }

                // Get the maximum payment date
                final TimePeriod maxSchedulingPeriod = group.getMemberSettings().getMaxSchedulingPeriod();
                if (maxSchedulingPeriod != null) {
                    maxPaymentDate = maxSchedulingPeriod.add(DateHelper.truncate(Calendar.getInstance()));
                }

                // Validate if there is a fee with a pending contract
                if (payment.getTo() != null && payment.getTo() instanceof Member) {
                    final Collection<TransactionFee> transactionFees = (Collection<TransactionFee>) fetchService.fetch(transferType.getTransactionFees(), TransactionFee.Relationships.GENERATED_TRANSFER_TYPE);
                    for (final TransactionFee transactionFee : transactionFees) {
                        if (transactionFee instanceof BrokerCommission && transactionFee.isFromMember()) {
                            final BrokerCommission brokerCommission = (BrokerCommission) transactionFee;
                            final BrokerCommissionContractQuery contractsQuery = new BrokerCommissionContractQuery();
                            contractsQuery.setBrokerCommission(brokerCommission);
                            contractsQuery.setStatus(BrokerCommissionContract.Status.PENDING);
                            switch (brokerCommission.getWhichBroker()) {
                                case SOURCE:
                                    contractsQuery.setMember(fromMember);
                                    break;
                                case DESTINATION:
                                    contractsQuery.setMember((Member) payment.getTo());
                                    break;
                            }
                            final List<BrokerCommissionContract> commissionContracts = commissionService.searchBrokerCommissionContracts(contractsQuery);
                            if (CollectionUtils.isNotEmpty(commissionContracts)) {
                                return new ValidationError("payment.error.pendingCommissionContract", brokerCommission.getName());
                            }
                        }
                    }
                }

            }

            // Validate the total payment amount and dates
            final BigDecimal paymentAmount = payment.getAmount();
            final BigDecimal minimumPayment = getMinimumPayment();
            BigDecimal totalAmount = BigDecimal.ZERO;
            Calendar lastDate = DateHelper.truncatePreviosDay(Calendar.getInstance());
            for (final ScheduledPaymentDTO dto : payments) {
                final Calendar date = dto.getDate();
                // Validate the max payment date
                if (maxPaymentDate != null && date.after(maxPaymentDate)) {
                    final LocalSettings localSettings = settingsService.getLocalSettings();
                    final CalendarConverter dateConverter = localSettings.getRawDateConverter();
                    return new ValidationError("payment.invalid.schedulingDate", dateConverter.toString(maxPaymentDate));
                }

                final BigDecimal amount = dto.getAmount();

                if (amount == null || amount.compareTo(minimumPayment) < 0) {
                    return new RequiredError(messageResolver.message("transfer.amount"));
                }

                BigDecimal minAmount = transferType.getMinAmount();
                if (minAmount != null) {
                    if (amount.compareTo(minAmount) < 0) {
                        return new ValidationError("errors.greaterEquals", amount, minAmount);
                    }
                }

                if (date == null) {
                    return new RequiredError(messageResolver.message("transfer.date"));
                } else if (date.before(lastDate) || DateUtils.isSameDay(date, lastDate)) {
                    return new ValidationError("payment.invalid.paymentDates");
                }
                totalAmount = totalAmount.add(amount);
                lastDate = date;
            }
            // Validate the total payment amount
            if (paymentAmount != null && totalAmount.compareTo(paymentAmount) != 0) {
                return new ValidationError("payment.invalid.paymentAmount");
            }
            return null;
        }
    }

    private class TicketValidation implements PropertyValidation {

        private static final long serialVersionUID = 1L;

        @Override
        public ValidationError validate(final Object object, final Object property, final Object value) {
            if (value != null) {
                DoPaymentDTO dto = (DoPaymentDTO) object;
                Ticket ticket = (Ticket) value;
                if (ticket != null && dto.getChannel() != Channel.WEBSHOP) {
                    return new InvalidError();
                } else {
                    try {
                        ticket = fetchService.fetch(ticket);
                        if (ticket != null && ticket.getStatus() != Ticket.Status.PENDING) {
                            throw new EntityNotFoundException(Ticket.class);
                        }
                    } catch (EntityNotFoundException e) {
                        return new InvalidError();
                    }
                }
            }
            return null;
        }

    }

    private class TraceNumberValidation implements PropertyValidation {

        private static final long serialVersionUID = 2424106851078796317L;

        @Override
        public ValidationError validate(final Object object, final Object property, final Object value) {
            final TransferDTO dto = (TransferDTO) object;
            final Long clientId = dto.getClientId();
            final String traceNumber = dto.getTraceNumber();
            if (clientId == null || StringUtils.isEmpty(traceNumber)) {
                return null;
            }
            try {
                transferDao.loadTransferByTraceNumber(traceNumber, clientId);
                traceNumberDao.load(clientId, traceNumber);
                // Invalid, as if it reaches here, there is at least one other transfer with the given trace number
                return new UniqueError(traceNumber);
            } catch (final EntityNotFoundException e) {
                // Is valid, as there are no other transfer using that trace number for that client id
                return null;
            }
        }
    }

    private static final float                PRECISION_DELTA    = 0.0001F;
    private static final Relationship[]       CONCILIATION_FETCH = { Transfer.Relationships.EXTERNAL_TRANSFER, RelationshipHelper.nested(Payment.Relationships.FROM, MemberAccount.Relationships.MEMBER) };

    private AccountServiceLocal               accountService;
    private CommissionServiceLocal            commissionService;
    private SettingsServiceLocal              settingsService;
    private TransferAuthorizationServiceLocal transferAuthorizationService;
    private TicketServiceLocal                ticketService;
    private TransactionFeeServiceLocal        transactionFeeService;
    private TransferDAO                       transferDao;
    private TraceNumberDAO                    traceNumberDao;
    private ScheduledPaymentDAO               scheduledPaymentDao;
    private TransferTypeServiceLocal          transferTypeService;
    private FetchServiceLocal                 fetchService;
    private LoggingHandler                    loggingHandler;
    private PermissionServiceLocal            permissionService;
    private AlertServiceLocal                 alertService;
    private MessageResolver                   messageResolver;
    private PaymentCustomFieldServiceLocal    paymentCustomFieldService;
    private RateServiceLocal                  rateService;
    private MemberNotificationHandler         memberNotificationHandler;
    private AdminNotificationHandler          adminNotificationHandler;
    private TransactionHelper                 transactionHelper;
    private ApplicationServiceLocal           applicationService;
    private LockHandlerFactory                lockHandlerFactory;
    private PaymentHelper                     paymentHelper;
    private AccountHelper                     accountHelper;
    private CustomObjectHandler               customObjectHandler;

    private static final Log                  LOG                = LogFactory.getLog(PaymentServiceImpl.class);

    @Override
    public List<BulkChargebackResult> bulkChargeback(final List<Transfer> transfers) {

        return transactionHelper.runInNewTransaction(new Transactional<List<BulkChargebackResult>>() {
            @Override
            public List<BulkChargebackResult> afterCommit(final List<BulkChargebackResult> result) {
                // Make sure all transfers are attached to the current session
                for (BulkChargebackResult bulkChargebackResult : result) {
                    bulkChargebackResult.setTransfer(fetchService.fetch(bulkChargebackResult.getTransfer()));
                }
                return result;
            }

            @Override
            public List<BulkChargebackResult> doInTransaction(final TransactionStatus status) {
                List<BulkChargebackResult> results = new ArrayList<BulkChargebackResult>(transfers.size());
                try {
                    for (Transfer transfer : transfers) {
                        if (transfer == null) {
                            results.add(null);
                        } else {
                            Transfer chargeback = insertChargeback(transfer, false);
                            results.add(new BulkChargebackResult(chargeback));
                        }
                    }
                } catch (ApplicationException e) {
                    results.add(new BulkChargebackResult(e));
                    status.setRollbackOnly();
                }
                return results;
            }
        });
    }

    @Override
    public List<ScheduledPaymentDTO> calculatePaymentProjection(final ProjectionDTO params) {
        getProjectionValidator().validate(params);

        final LocalSettings localSettings = settingsService.getLocalSettings();

        final int paymentCount = params.getPaymentCount();
        final TimePeriod recurrence = params.getRecurrence();
        final BigDecimal totalAmount = params.getAmount();
        final BigDecimal paymentAmount = localSettings.round(totalAmount.divide(CoercionHelper.coerce(BigDecimal.class, paymentCount), localSettings.getMathContext()));
        BigDecimal accumulatedAmount = BigDecimal.ZERO;
        Calendar currentDate = DateHelper.truncate(params.getFirstExpirationDate());
        final List<ScheduledPaymentDTO> payments = new ArrayList<ScheduledPaymentDTO>(paymentCount);
        for (int i = 0; i < paymentCount; i++) {
            final ScheduledPaymentDTO dto = new ScheduledPaymentDTO();
            dto.setDate(currentDate);
            dto.setAmount(i == paymentCount - 1 ? totalAmount.subtract(accumulatedAmount) : paymentAmount);
            payments.add(dto);
            accumulatedAmount = accumulatedAmount.add(dto.getAmount(), localSettings.getMathContext());
            currentDate = recurrence.add(currentDate);
        }
        return payments;
    }

    @Override
    public boolean canChargeback(Transfer transfer, final boolean ignorePendingPayment) {
        transfer = fetchService.fetch(transfer, Payment.Relationships.FROM, Payment.Relationships.TO, Transfer.Relationships.PARENT, Transfer.Relationships.CHARGEBACK_OF, Transfer.Relationships.CHILDREN);
        if (transfer == null) {
            return false;
        }
        // Pending payments cannot be charged back
        final Calendar processDate = transfer.getProcessDate();
        if (!ignorePendingPayment && processDate == null) {
            return false;
        }

        // Check the max chargeback time
        final LocalSettings localSettings = settingsService.getLocalSettings();
        final TimePeriod maxChargebackTime = localSettings.getMaxChargebackTime();
        final Calendar maxDate = maxChargebackTime.add(processDate);
        if (Calendar.getInstance().after(maxDate)) {
            return false;
        }

        // Nested transfers cannot be charged back
        if (transfer.getParent() != null) {
            return false;
        }
        // Payments which has already been charged back cannot be charged back again
        if (transfer.getChargedBackBy() != null) {
            return false;
        }
        // Payments which are chargebacks cannot be charged back
        if (transfer.getChargebackOf() != null) {
            return false;
        }
        // Cannot chargeback if from owner is removed
        if (!transfer.isFromSystem()) {
            final Member fromOwner = (Member) transfer.getFromOwner();
            if (fromOwner.getGroup().getStatus() == Group.Status.REMOVED) {
                return false;
            }
        }
        // Cannot chargeback if to owner is removed
        if (!transfer.isToSystem()) {
            final Member toOwner = (Member) transfer.getToOwner();
            if (toOwner.getGroup().getStatus() == Group.Status.REMOVED) {
                return false;
            }
        }
        return true;
    }

    /**
     * To perform a payment, the logged user must either manage the from owner or to owner and must be allowed<br>
     * to use the specified transfer type (if any).
     */
    @Override
    public boolean canMakePayment(AccountOwner from, final AccountOwner to, final TransferType transferType) {
        if (LoggedUser.isSystem()) {
            return true;
        }
        boolean checkToMember = false;
        boolean hasPermission = false;
        if (from == null) {
            from = LoggedUser.accountOwner();
        }
        if (from instanceof SystemAccountOwner) {
            if (to instanceof SystemAccountOwner) {
                // System to system payment
                hasPermission = permissionService.permission().admin(AdminSystemPermission.PAYMENTS_PAYMENT).hasPermission();
            } else {
                // System to member payment
                if (transferType == null) {
                    // No information about the TT. Can be either loan or payment
                    hasPermission = permissionService.permission().admin(AdminMemberPermission.PAYMENTS_PAYMENT, AdminMemberPermission.LOANS_GRANT).hasPermission();
                } else {
                    // We know whether is a loan type or payment type: check the specific permission
                    AdminMemberPermission permission = transferType.isLoanType() ? AdminMemberPermission.LOANS_GRANT : AdminMemberPermission.PAYMENTS_PAYMENT;
                    hasPermission = permissionService.permission().admin(permission).hasPermission();
                }
                checkToMember = true;
            }
        } else {
            Member member = (Member) from;
            if (from.equals(to)) {
                // Member self payment
                hasPermission = permissionService.permission(member)
                        .admin(AdminMemberPermission.PAYMENTS_PAYMENT_AS_MEMBER_TO_SELF)
                        .broker(BrokerPermission.MEMBER_PAYMENTS_PAYMENT_AS_MEMBER_TO_SELF)
                        .member(MemberPermission.PAYMENTS_PAYMENT_TO_SELF)
                        .operator(OperatorPermission.PAYMENTS_PAYMENT_TO_SELF)
                        .hasPermission();
            } else if (to instanceof SystemAccountOwner) {
                // Member to system
                hasPermission = permissionService.permission(member)
                        .admin(AdminMemberPermission.PAYMENTS_PAYMENT_AS_MEMBER_TO_SYSTEM)
                        .broker(BrokerPermission.MEMBER_PAYMENTS_PAYMENT_AS_MEMBER_TO_SYSTEM)
                        .member(MemberPermission.PAYMENTS_PAYMENT_TO_SYSTEM)
                        .operator(OperatorPermission.PAYMENTS_PAYMENT_TO_SYSTEM)
                        .hasPermission();
            } else {
                // Member to member
                hasPermission = permissionService.permission(member)
                        .admin(AdminMemberPermission.PAYMENTS_PAYMENT_AS_MEMBER_TO_MEMBER)
                        .broker(BrokerPermission.MEMBER_PAYMENTS_PAYMENT_AS_MEMBER_TO_MEMBER)
                        .member(MemberPermission.PAYMENTS_PAYMENT_TO_MEMBER)
                        .operator(OperatorPermission.PAYMENTS_PAYMENT_TO_MEMBER, OperatorPermission.PAYMENTS_POSWEB_MAKE_PAYMENT)
                        .hasPermission();
                checkToMember = true;
            }
        }

        if (hasPermission && transferType != null) {
            Collection<TransferType> allowedTypes = Collections.emptyList();
            // checks if the specified TT can be used
            if (LoggedUser.accountOwner().equals(from)) {
                Group group = fetchService.fetch(LoggedUser.isOperator() ? LoggedUser.member().getGroup() : LoggedUser.group(), Group.Relationships.TRANSFER_TYPES);
                allowedTypes = group.getTransferTypes();
            } else if (LoggedUser.isBroker()) {
                BrokerGroup brokerGroup = fetchService.fetch((BrokerGroup) LoggedUser.group(), BrokerGroup.Relationships.TRANSFER_TYPES_AS_MEMBER);
                allowedTypes = brokerGroup.getTransferTypesAsMember();
            } else if (LoggedUser.isAdministrator()) {
                AdminGroup admGroup = fetchService.fetch((AdminGroup) LoggedUser.group(), AdminGroup.Relationships.TRANSFER_TYPES_AS_MEMBER);
                allowedTypes = admGroup.getTransferTypesAsMember();
            }

            hasPermission = allowedTypes.contains(transferType);
        }

        // Besides, if the payment is to a member, ensure he is visible
        if (hasPermission && checkToMember) {
            return permissionService.relatesTo((Member) to);
        } else {
            return hasPermission;
        }
    }

    @Override
    public Transfer chargeback(final Transfer transfer) throws UnexpectedEntityException {
        if (!canChargeback(transfer, false)) {
            throw new UnexpectedEntityException();
        }

        // Insert the chargeback
        return insertChargeback(transfer, true);
    }

    @Override
    public Transfer conciliate(Transfer transfer, final ExternalTransfer externalTransfer) {
        transfer = fetchService.fetch(transfer, CONCILIATION_FETCH);
        if (transfer != null && transfer.getExternalTransfer() != null) {
            // If the transfer is already conciliated, ignore it
            transfer = null;
        }
        if (transfer != null) {
            final Account from = transfer.getFrom();
            final AccountOwner owner = from.getOwner();
            if (!owner.equals(externalTransfer.getMember())) {
                // The account does not belong to the expected member, ignore it
                transfer = null;
            }
        }
        if (transfer == null) {
            throw new UnexpectedEntityException();
        }
        return transferDao.updateExternalTransfer(transfer.getId(), externalTransfer);
    }

    @Override
    public Transfer confirmPayment(final String ticketStr) throws NotEnoughCreditsException, MaxAmountPerDayExceededException, EntityNotFoundException, UpperCreditLimitReachedException, AuthorizedPaymentInPastException {
        // Get and validate the ticket
        final PaymentRequestTicket ticket = ticketService.loadPendingPaymentRequest(ticketStr);
        final Member fromMember = ticket.getFrom();
        final Member toMember = ticket.getTo();
        final String channel = ticket.getToChannel().getInternalName();
        final String description = ticket.getDescription();

        // Create the payment
        final TransferDTO dto = new TransferDTO();
        dto.setFromOwner(fromMember);
        dto.setToOwner(toMember);
        dto.setTransferType(ticket.getTransferType());
        dto.setAmount(ticket.getAmount());
        dto.setChannel(channel);
        dto.setTicket(ticket);
        dto.setDescription(description);
        final Transfer transfer = (Transfer) insert(dto, true, false);

        // Update the ticket
        ticket.setStatus(Ticket.Status.OK);
        ticket.setTransfer(transfer);

        // Notify
        memberNotificationHandler.externalChannelPaymentConfirmed(ticket);

        return transfer;
    }

    @Override
    public List<BulkPaymentResult> doBulkPayment(final List<DoPaymentDTO> dtos) {
        return transactionHelper.runInNewTransaction(new Transactional<List<BulkPaymentResult>>() {
            @Override
            public List<BulkPaymentResult> afterCommit(final List<BulkPaymentResult> result) {
                // Make sure all transfers are attached to the current session
                for (BulkPaymentResult bulkPaymentResult : result) {
                    bulkPaymentResult.setPayment(fetchService.fetch(bulkPaymentResult.getPayment()));
                }
                return result;
            }

            @Override
            public List<BulkPaymentResult> doInTransaction(final TransactionStatus status) {
                List<BulkPaymentResult> results = new ArrayList<BulkPaymentResult>(dtos.size());
                try {
                    for (DoPaymentDTO dto : dtos) {
                        Payment payment = doPayment(dto, false, true, false);
                        results.add(new BulkPaymentResult(payment));
                    }
                } catch (ApplicationException e) {
                    results.add(new BulkPaymentResult(e));
                    status.setRollbackOnly();
                }
                return results;
            }
        });
    }

    @Override
    public Payment doPayment(final DoPaymentDTO params) {
        return doPayment(params, true, true, false);
    }

    @Override
    public AccountHistoryResultPage getAccountHistoryResultPage(final AccountHistoryParams params) {
        TransferQuery query = paymentHelper.toTransferQuery(params);
        List<Transfer> transfers = search(query);
        AccountHistoryResultPage result = accountHelper.toAccountHistoryResultPage(query.getOwner(), transfers);
        // Get the account status if requested
        if (params.getShowStatus()) {
            AccountStatusVO statusVO = accountService.getCurrentAccountStatusVO(new AccountDTO(query.getOwner(), query.getType()));
            result.setAccountStatus(statusVO);
        }
        return result;
    }

    @Override
    public AccountHistoryTransferVO getAccountHistoryTransferVO(final Long id) {
        Transfer transfer = load(id);
        List<PaymentCustomField> fields = paymentCustomFieldService.list(transfer.getType(), false);
        return accountHelper.toVO(LoggedUser.member(), transfer, fields, null, null);
    }

    @Override
    public ConversionSimulationDTO getDefaultConversionDTO(MemberAccount account, final List<TransferType> transferTypes) {
        account = fetchService.fetch(account, Account.Relationships.TYPE, MemberAccount.Relationships.MEMBER);
        // Get the current account status
        final AccountStatus status = accountService.getRatedStatus(account, null);

        final ConversionSimulationDTO dto = new ConversionSimulationDTO();
        dto.setAccount(account);

        // Find the default amount: the balance of the current account
        BigDecimal defaultAmount = status.getAvailableBalanceWithoutCreditLimit();
        if (BigDecimal.ZERO.compareTo(defaultAmount) > 0) {
            defaultAmount = BigDecimal.ZERO;
        }
        dto.setAmount(defaultAmount);

        // find the first rated TT, and choose this.
        for (final TransferType currentTT : transferTypes) {
            if (currentTT.isHavingRatedFees()) {
                dto.setTransferType(currentTT);
                break;
            }
        }
        // If not any rated TT, just choose the first
        if (dto.getTransferType() == null) {
            dto.setTransferType(transferTypes.get(0));
        }

        dto.setDate(Calendar.getInstance());
        // erase any present content
        dto.setArate(null);
        dto.setDrate(null);
        // rates on a zero balance are meaningless, so...
        if (dto.getTransferType().isHavingRatedFees() && BigDecimal.ZERO.compareTo(defaultAmount) < 0) {
            if (dto.getTransferType().isHavingAratedFees()) {
                final BigDecimal aRate = status.getaRate();
                dto.setArate(aRate);
            }
            if (dto.getTransferType().isHavingDratedFees()) {
                final BigDecimal dRate = status.getdRate();
                dto.setDrate(dRate);
            }
        }

        return dto;
    }

    @Override
    public BigDecimal getMinimumPayment() {
        final LocalSettings localSettings = settingsService.getLocalSettings();
        final int precision = localSettings.getPrecision().getValue();
        final BigDecimal minimumPayment = new BigDecimal(new BigInteger("1"), precision);
        return minimumPayment;
    }

    @Override
    public StatisticalResultDTO getSimulateConversionGraph(final ConversionSimulationDTO input) {
        final LocalSettings localSettings = settingsService.getLocalSettings();
        final byte precision = (byte) localSettings.getPrecision().getValue();

        // get series
        final TransactionFeePreviewForRatesDTO temp = simulateConversion(input);
        final int series = temp.getFees().size();
        // get range of points, but without values for A < 0
        BigDecimal initialARate = null;
        RatesResultDTO rates = new RatesResultDTO();
        if (input.isUseActualRates()) {
            rates = rateService.getRatesForTransferFrom(input.getAccount(), input.getAmount(), null);
            rates.setDate(input.getDate());
            initialARate = rates.getaRate();
        } else {
            initialARate = input.getArate();
        }

        // lowerlimit takes care that values for A < 0 are left out of the graph
        final Double lowerLimit = (initialARate == null) ? null : initialARate.negate().doubleValue();
        final Number[] xRange = GraphHelper.getOptimalRangeAround(0, 33, 0, 0.8, lowerLimit);

        // Data structure to build the table
        final Number[][] tableCells = new Number[xRange.length][series];
        // initialize series names and x labels
        final String[] seriesNames = new String[series];
        final byte[] seriesOrder = new byte[series];
        final Calendar[] xPointDates = new Calendar[xRange.length];
        final Calendar now = Calendar.getInstance();
        BigDecimal inputARate = temp.getARate();
        BigDecimal inputDRate = temp.getDRate();
        // assign data
        for (int i = 0; i < xRange.length; i++) {
            final ConversionSimulationDTO inputPointX = (ConversionSimulationDTO) input.clone();
            final Calendar date = (Calendar) ((input.isUseActualRates()) ? input.getDate().clone() : now.clone());
            date.add(Calendar.DAY_OF_YEAR, xRange[i].intValue());
            xPointDates[i] = date;
            // Set useActualRates for this input to false, otherwise simulateConversion will use the account's the balance and rates of that date, and
            // we don't want that.
            inputPointX.setUseActualRates(false);
            if (inputARate != null) {
                final BigDecimal aRate = inputARate.add(new BigDecimal(xRange[i].doubleValue()));
                inputPointX.setArate(aRate);
            }
            if (inputDRate != null) {
                final BigDecimal dRate = inputDRate.subtract(new BigDecimal(xRange[i].doubleValue()));
                inputPointX.setDrate(dRate);
            }

            final TransactionFeePreviewDTO tempResult = simulateConversion(inputPointX);
            int j = 0;
            for (final TransactionFee fee : tempResult.getFees().keySet()) {
                tableCells[i][j] = new StatisticalNumber(tempResult.getFees().get(fee).doubleValue(), precision);
                byte index;
                switch (fee.getChargeType()) {
                    case D_RATE:
                        index = 2;
                        break;
                    case A_RATE:
                    case MIXED_A_D_RATES:
                        index = 3;
                        break;
                    default:
                        index = 1;
                        break;
                }
                seriesOrder[j] = index;
                seriesNames[j++] = fee.getName();
            }
        }

        // create the graph object
        final StatisticalResultDTO result = new StatisticalResultDTO(tableCells);
        result.setBaseKey("conversionSimulation.result.graph");
        result.setHelpFile("account_management");
        // date labels along x-axis
        final String[] rowKeys = new String[xRange.length];
        Arrays.fill(rowKeys, "");
        result.setRowKeys(rowKeys);
        for (int i = 0; i < rowKeys.length; i++) {
            final String rowHeader = localSettings.getDateConverterForGraphs().toString(xPointDates[i]);
            result.setRowHeader(rowHeader, i);
        }
        // mark the actual date upon which the x-axis is based as a vertical line
        final Calendar baseDate = (input.isUseActualRates()) ? (Calendar) input.getDate().clone() : now;
        final String baseDateString = localSettings.getDateConverterForGraphs().toString(baseDate);
        final Marker[] markers = new Marker[1];
        markers[0] = new CategoryMarker(baseDateString);
        markers[0].setPaint(Color.ORANGE);
        final String todayString = localSettings.getDateConverterForGraphs().toString(now);
        if (todayString.equals(baseDateString)) {
            markers[0].setLabel("global.today");
        }
        result.setDomainMarkers(markers);

        // Series labels indicate fee names
        final String[] columnKeys = new String[series];
        Arrays.fill(columnKeys, "");
        result.setColumnKeys(columnKeys);
        for (int j = 0; j < columnKeys.length; j++) {
            result.setColumnHeader(seriesNames[j], j);
        }

        // order the series
        result.orderSeries(seriesOrder);

        final TransferType tt = fetchService.fetch(input.getTransferType(), RelationshipHelper.nested(TransferType.Relationships.FROM, AccountType.Relationships.CURRENCY));
        result.setYAxisUnits(tt.getCurrency().getSymbol());
        result.setShowTable(false);
        result.setGraphType(StatisticalResultDTO.GraphType.STACKED_AREA);
        return result;
    }

    @Override
    public boolean hasPermissionsToChargeback(Transfer transfer) {
        transfer = fetchService.fetch(transfer, RelationshipHelper.nested(Payment.Relationships.TO, MemberAccount.Relationships.MEMBER));
        // If it's a payment from member.. check it's related
        boolean isFromMember = !transfer.isFromSystem();
        if (isFromMember) {
            if (!permissionService.relatesTo((Member) transfer.getFromOwner())) {
                return false;
            }
        }
        if (transfer.isToSystem()) {
            // Payment to system
            return permissionService.permission()
                    .adminFor(AdminSystemPermission.PAYMENTS_CHARGEBACK, transfer.getType())
                    .hasPermission();
        } else {
            // Payment to member
            return permissionService.permission((Member) transfer.getToOwner())
                    .adminFor(AdminMemberPermission.PAYMENTS_CHARGEBACK, transfer.getType())
                    .memberFor(MemberPermission.PAYMENTS_CHARGEBACK, transfer.getType())
                    .hasPermission();
        }
    }

    @Override
    public Payment insertWithNotification(final TransferDTO dto) throws NotEnoughCreditsException, MaxAmountPerDayExceededException, UnexpectedEntityException, UpperCreditLimitReachedException {
        Payment payment = insert(dto, false, false);
        if (payment instanceof Transfer) {
            memberNotificationHandler.automaticPaymentReceivedNotification((Transfer) payment, dto);
        }
        return payment;
    }

    @Override
    public Payment insertWithoutNotification(final TransferDTO dto) throws NotEnoughCreditsException, MaxAmountPerDayExceededException, UnexpectedEntityException, UpperCreditLimitReachedException {
        return insert(dto, false, false);
    }

    @Override
    public boolean isVisible(Payment payment) {
        if (LoggedUser.isSystem()) {
            return true;
        }
        if (payment instanceof Transfer && LoggedUser.isOperator()) {
            // An operator should be able to view transfers he has received
            Transfer transfer = (Transfer) payment;
            if (LoggedUser.element().equals(transfer.getReceiver()) || LoggedUser.element().equals(transfer.getBy())) {
                return true;
            }
        }
        if (payment instanceof Transfer) {
            payment = fetchService.fetch((Transfer) payment, Payment.Relationships.FROM, Payment.Relationships.TO);
        } else {
            payment = fetchService.fetch((ScheduledPayment) payment, Payment.Relationships.FROM, Payment.Relationships.TO);
        }
        return accountService.canView(payment.getFrom()) || accountService.canView(payment.getTo());
    }

    @Override
    public Transfer load(final Long id, final Relationship... fetch) {
        return transferDao.<Transfer> load(id, fetch);
    }

    @Override
    public Transfer loadTransferForReverse(final String traceNumber, final Relationship... fetch) throws EntityNotFoundException {
        long clientId = LoggedUser.serviceClient().getId();
        Transfer transfer = transferDao.loadTransferByTraceNumber(traceNumber, clientId);
        if (transfer == null) {
            if (!insertTN(LoggedUser.serviceClient().getId(), traceNumber)) {
                // the TN already exists
                transfer = transferDao.loadTransferByTraceNumber(traceNumber, clientId);
            }
            if (transfer == null) {
                throw new EntityNotFoundException(Transfer.class, null, String.format("TraceNumber and client id used to load: <%1$s, %2$s>", traceNumber, clientId));
            }
        }
        return transfer;
    }

    @Override
    public void notifyTransferProcessed(final Transfer transfer) {
        if (transfer.getProcessDate() == null) {
            // Transfer is not processed
            return;
        }

        final Collection<TransferListener> listeners = getTransferListeners(transfer);
        if (CollectionUtils.isNotEmpty(listeners)) {
            CurrentTransactionData.addTransactionCommitListener(new TransactionCommitListener() {
                @Override
                public void onTransactionCommit() {
                    transactionHelper.runInCurrentThread(new TransactionCallbackWithoutResult() {
                        @Override
                        protected void doInTransactionWithoutResult(final TransactionStatus status) {
                            Transfer fetchedTransfer = fetchService.fetch(transfer, Payment.Relationships.FROM, Payment.Relationships.TO);
                            for (TransferListener listener : listeners) {
                                try {
                                    listener.onTransferProcessed(fetchedTransfer);
                                } catch (Exception e) {
                                    LOG.warn("Error running TransferListener " + listener, e);
                                }
                            }
                        }
                    });
                }
            });
        }
    }

    @Override
    public void processScheduled(final Period period) {
        // Process each transfer
        final TransferQuery query = new TransferQuery();
        query.setResultType(ResultType.ITERATOR);
        query.setPeriod(period);
        query.setStatus(Payment.Status.SCHEDULED);
        query.setUnordered(true);
        CacheCleaner cacheCleaner = new CacheCleaner(fetchService);
        final List<Transfer> transfers = transferDao.search(query);
        try {
            for (final Transfer transfer : transfers) {
                processScheduledTransfer(transfer, true, true, true);
                cacheCleaner.clearCache();
            }
        } finally {
            DataIteratorHelper.close(transfers);
        }
    }

    @Override
    public Transfer processScheduled(final Transfer transfer) {
        return processScheduledTransfer(transfer, false, false, true);
    }

    @Override
    public void purgeOldTraceNumbers(final Calendar time) {
        Calendar c = (Calendar) time.clone();
        c.add(Calendar.DAY_OF_MONTH, -1);

        traceNumberDao.delete(c);
    }

    @Override
    public List<Transfer> search(final TransferQuery query) {
        return transferDao.search(query);
    }

    public void setAccountHelper(final AccountHelper accountHelper) {
        this.accountHelper = accountHelper;
    }

    public void setAccountServiceLocal(final AccountServiceLocal accountService) {
        this.accountService = accountService;
    }

    public void setAdminNotificationHandler(final AdminNotificationHandler adminNotificationHandler) {
        this.adminNotificationHandler = adminNotificationHandler;
    }

    public void setAlertServiceLocal(final AlertServiceLocal alertService) {
        this.alertService = alertService;
    }

    public void setApplicationServiceLocal(final ApplicationServiceLocal applicationService) {
        this.applicationService = applicationService;
    }

    public void setCommissionServiceLocal(final CommissionServiceLocal commissionService) {
        this.commissionService = commissionService;
    }

    public void setCustomObjectHandler(final CustomObjectHandler customObjectHandler) {
        this.customObjectHandler = customObjectHandler;
    }

    public void setFetchServiceLocal(final FetchServiceLocal fetchService) {
        this.fetchService = fetchService;
    }

    public void setLockHandlerFactory(final LockHandlerFactory lockHandlerFactory) {
        this.lockHandlerFactory = lockHandlerFactory;
    }

    public void setLoggingHandler(final LoggingHandler loggingHandler) {
        this.loggingHandler = loggingHandler;
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

    public void setPaymentHelper(final PaymentHelper paymentHelper) {
        this.paymentHelper = paymentHelper;
    }

    public void setPermissionServiceLocal(final PermissionServiceLocal permissionService) {
        this.permissionService = permissionService;
    }

    public void setRateServiceLocal(final RateServiceLocal rateService) {
        this.rateService = rateService;
    }

    public void setScheduledPaymentDao(final ScheduledPaymentDAO scheduledPaymentDao) {
        this.scheduledPaymentDao = scheduledPaymentDao;
    }

    public void setSettingsServiceLocal(final SettingsServiceLocal settingsService) {
        this.settingsService = settingsService;
    }

    public void setTicketServiceLocal(final TicketServiceLocal ticketService) {
        this.ticketService = ticketService;
    }

    public void setTraceNumberDao(final TraceNumberDAO traceNumberDao) {
        this.traceNumberDao = traceNumberDao;
    }

    public void setTransactionFeeServiceLocal(final TransactionFeeServiceLocal transactionFeeService) {
        this.transactionFeeService = transactionFeeService;
    }

    public void setTransactionHelper(final TransactionHelper transactionHelper) {
        this.transactionHelper = transactionHelper;
    }

    public void setTransferAuthorizationServiceLocal(final TransferAuthorizationServiceLocal transferAuthorizationService) {
        this.transferAuthorizationService = transferAuthorizationService;
    }

    public void setTransferDao(final TransferDAO transferDao) {
        this.transferDao = transferDao;
    }

    public void setTransferTypeServiceLocal(final TransferTypeServiceLocal transferTypeService) {
        this.transferTypeService = transferTypeService;
    }

    @Override
    public TransactionFeePreviewForRatesDTO simulateConversion(final ConversionSimulationDTO params) {
        TransferType transferType = params.getTransferType();
        transferType = fetchService.fetch(transferType, TransferType.Relationships.TO, TransferType.Relationships.TRANSACTION_FEES,
                RelationshipHelper.nested(SystemAccountType.Relationships.ACCOUNT));
        final MemberAccount account = fetchService.fetch(params.getAccount(), Account.Relationships.TYPE, MemberAccount.Relationships.MEMBER);

        RatesPreviewDTO rates = new RatesPreviewDTO();
        if (params.isUseActualRates()) {
            rates = new RatesPreviewDTO(rateService.getRatesForTransferFrom(account, params.getAmount(), params.getDate()));
        } else {
            if (transferType.isHavingAratedFees()) {
                rates.setaRate(params.getArate());
            }
            if (transferType.isHavingDratedFees()) {
                rates.setdRate(params.getDrate());
            }
            rates.setDate(params.getDate());
            rates = new RatesPreviewDTO(rateService.rateToDate(rates));
        }
        rates.setGraph(params.isGraph());

        final Member from = account.getMember();
        final BigDecimal amount = params.getAmount();
        final SystemAccountOwner to = SystemAccountOwner.instance();
        final TransactionFeePreviewForRatesDTO preview = (TransactionFeePreviewForRatesDTO) transactionFeeService.preview(from, to, transferType, amount, rates);
        return preview;
    }

    @Override
    public Payment simulatePayment(final DoPaymentDTO params) throws NotEnoughCreditsException, MaxAmountPerDayExceededException, UnexpectedEntityException, UpperCreditLimitReachedException, AuthorizedPaymentInPastException {
        return transactionHelper.runInNewTransaction(new BaseTransactional<Payment>() {
            @Override
            public Payment doInTransaction(final TransactionStatus status) {
                status.setRollbackOnly();
                return doPayment(params, false, false, true);
            }
        });
    }

    @Override
    public void validate(final ConversionSimulationDTO dto) {
        final Validator validator = new Validator("");
        validator.property("amount").key("conversionSimulation.amount").required().positiveNonZero();
        if (dto.isUseActualRates()) {
            validator.property("date").key("conversionSimulation.date").required();
        } else {
            final Account account = fetchService.fetch(dto.getAccount(), RelationshipHelper.nested(Account.Relationships.TYPE, AccountType.Relationships.CURRENCY));
            final TransferType transferType = fetchService.fetch(dto.getTransferType(), TransferType.Relationships.TRANSACTION_FEES);
            final Currency currency = account.getType().getCurrency();
            if (currency.isEnableARate() && transferType.isHavingAratedFees()) {
                validator.property("arate").key("conversionSimulation.aRate.targeted").required().positive();
            }
            if (currency.isEnableDRate() && transferType.isHavingDratedFees()) {
                validator.property("drate").key("conversionSimulation.dRate.targeted").required();
            }
        }
        validator.validate(dto);
    }

    @Override
    public void validate(final DoPaymentDTO payment) {
        getPaymentValidator(payment).validate(payment);
    }

    /**
     * Validates the max amount per day
     */
    @Override
    public void validateMaxAmountAtDate(final Calendar date, final Account account, final TransferType transferType, BigDecimal maxAmountPerDay, final BigDecimal amount) {
        // Test the max amount per day
        maxAmountPerDay = maxAmountPerDay == null ? transferType.getMaxAmountPerDay() : maxAmountPerDay;
        if (maxAmountPerDay != null && maxAmountPerDay.floatValue() > PRECISION_DELTA) {
            // Get the amount on today
            BigDecimal amountOnDay = transferDao.getTransactionedAmountAt(date, account, transferType);

            // Validate
            if (amountOnDay.add(amount).compareTo(maxAmountPerDay) > 0) {
                throw new MaxAmountPerDayExceededException(date, transferType, account, amount);
            }
        }

        // Test the operator max amount per day
        if (LoggedUser.hasUser() && LoggedUser.isOperator()) {
            final Operator operator = LoggedUser.element();
            OperatorGroup group = operator.getOperatorGroup();
            group = fetchService.fetch(group, OperatorGroup.Relationships.MAX_AMOUNT_PER_DAY_BY_TRANSFER_TYPE);
            final BigDecimal maxAmount = group.getMaxAmountPerDayByTransferType().get(transferType);
            if (maxAmount != null && maxAmount.floatValue() > PRECISION_DELTA) {
                // Get the amount on today
                BigDecimal amountOnDay = transferDao.getTransactionedAmountAt(date, operator, account, transferType);
                // Validate
                if (amountOnDay.add(amount).compareTo(maxAmount) == 1) {
                    throw new MaxAmountPerDayExceededException(date, transferType, account, amount);
                }
            }
        }
    }

    @Override
    public boolean wouldRequireAuthorization(final DoPaymentDTO params) {
        AuthorizationLevel firstAuthorizationLevel = null;
        // Scheduled payments shouldn't be authorized, only it's payments
        if (CollectionUtils.isEmpty(params.getPayments())) {
            firstAuthorizationLevel = firstAuthorizationLevel(params.getTransferType(), params.getAmount(), params.getFrom());
        }
        return firstAuthorizationLevel != null;
    }

    @Override
    public boolean wouldRequireAuthorization(final Invoice invoice) {
        final DoPaymentDTO payment = new DoPaymentDTO();
        payment.setFrom(invoice.getTo());
        payment.setTo(invoice.getFrom());
        payment.setTransferType(invoice.getTransferType());
        payment.setAmount(invoice.getAmount());
        return wouldRequireAuthorization(payment);
    }

    @Override
    public boolean wouldRequireAuthorization(final Transfer transfer) {
        return firstAuthorizationLevel(transfer) != null;
    }

    @Override
    public boolean wouldRequireAuthorization(final TransferType transferType, final BigDecimal amount, final AccountOwner from) {
        return firstAuthorizationLevel(transferType, amount, from) != null;
    }

    private void addAmountValidator(final Validator validator, final TransferType tt) {
        Property amountProperty = validator.property("amount").required().positiveNonZero();

        // Max amount & min amount
        if (tt != null && tt.getMinAmount() != null) {
            amountProperty.greaterEquals(tt.getMinAmount());
        }

    }

    /**
     * Runs the {@link #performInsert(TransferDTO, AuthorizationLevel)} method optionally in a new transaction. Does this while deadlocks occur. Other
     * errors are just rethrown.
     */
    private Payment doInsert(final TransferDTO dto, final boolean newTransaction, final boolean simulation) {
        return transactionHelper.maybeRunInNewTransaction(new Transactional<Payment>() {
            @Override
            public Payment afterCommit(final Payment payment) {
                return fetchService.fetch(payment);
            }

            @Override
            public Payment doInTransaction(final TransactionStatus status) {
                return performInsert(dto, simulation);
            }
        }, newTransaction);
    }

    private Payment doPayment(final DoPaymentDTO params, final boolean newTransaction, final boolean notify, final boolean simulation) {
        // Check permission to pay with date
        if (params.getDate() != null && !permissionService.hasPermission(AdminMemberPermission.PAYMENTS_PAYMENT_WITH_DATE)) {
            throw new PermissionDeniedException();
        }

        // Validate dto
        validate(params);

        // Insert the transfer
        final TransferDTO dto = verify(params);

        final Payment payment = doInsert(dto, newTransaction, simulation);

        // Notify
        if (notify) {
            if (LoggedUser.isWebService()) {
                memberNotificationHandler.externalChannelPaymentPerformed(params, payment);
            } else {
                memberNotificationHandler.paymentReceivedNotification(payment);
            }
            if (payment instanceof Transfer) {
                Transfer transfer = (Transfer) payment;
                if (payment.getProcessDate() == null) {
                    adminNotificationHandler.notifyNewPendingPayment(transfer);
                } else {
                    adminNotificationHandler.notifyPayment(transfer);
                }
            }
        }
        return payment;
    }

    private Transfer doProcessScheduledTransfer(final LockHandler lockHandler, Transfer transfer, final boolean failOnError, final boolean notifyPayer, final boolean notifyReceiver) {
        transfer = fetchService.fetch(transfer, Transfer.Relationships.SCHEDULED_PAYMENT);
        ScheduledPayment scheduledPayment = transfer.getScheduledPayment();
        if (scheduledPayment == null || !transfer.getStatus().canPayNow()) {
            throw new UnexpectedEntityException();
        }

        final Account from = transfer.getFrom();
        final Account to = transfer.getTo();

        // Lock the required accounts
        LockedAccountsOnPayments lockedAccountsOnPayments = applicationService.getLockedAccountsOnPayments();
        if (lockedAccountsOnPayments == LockedAccountsOnPayments.ORIGIN) {
            lockHandler.lock(from);
        } else if (lockedAccountsOnPayments == LockedAccountsOnPayments.ALL) {
            lockHandler.lock(from, to);
        }

        // We have to refresh the transfer after locking, to make sure it's still possible to pay now
        transfer = fetchService.reload(transfer, Transfer.Relationships.SCHEDULED_PAYMENT);
        scheduledPayment = transfer.getScheduledPayment();
        if (!transfer.getStatus().canPayNow()) {
            throw new UnexpectedEntityException();
        }

        final BigDecimal amount = transfer.getAmount();
        final TransferType transferType = transfer.getType();
        final AuthorizationLevel firstAuthorizationLevel = firstAuthorizationLevel(transfer);
        try {
            Account fromAccountToValidate = from;
            if (scheduledPayment.isReserveAmount()) {
                // When the scheduled payment has reserved the amount, we don't need to validate the from amount, because it's guaranteed to have a
                // reserved amount, so, we pass fromAccount = null
                fromAccountToValidate = null;
            }
            Collection<TransferListener> listeners = getTransferListeners(transfer);

            // Notify the listeners before the amount is validated
            for (TransferListener listener : listeners) {
                listener.onBeforeValidateBalance(transfer);
            }

            validateAmount(amount, fromAccountToValidate, to, transfer);
            final TransactionFeePreviewDTO preview = transactionFeeService.preview(from.getOwner(), to.getOwner(), transferType, amount);
            transfer.setAmount(preview.getFinalAmount());
            if (LoggedUser.hasUser()) {
                transfer.setBy(LoggedUser.element());
            }
            boolean shouldLiberateAmount = false;
            if (firstAuthorizationLevel != null) {
                transfer.setStatus(Transfer.Status.PENDING);
                transfer.setNextAuthorizationLevel(firstAuthorizationLevel);

                // Insert an amount reservation for this pending transfer, unless the scheduled payment has already reserved it
                if (!scheduledPayment.isReserveAmount()) {
                    accountService.reservePending(transfer);
                }
            } else {
                // apply rates
                RatesToSave rates = rateService.applyTransfer(transfer);
                /*
                 * set processDate AFTER applying rates, but before persisting them. This is important, because the transfer itself must not sum up
                 * for rates or balances when the rates are processed, and it does if processdate is already set. In that case, the transfer's
                 * processDate can equal the fromRates's date.
                 */
                Calendar processDate = (rates.getFromRates() == null) ? Calendar.getInstance() : rates.getFromRates().getDate();
                transfer.setStatus(Transfer.Status.PROCESSED);
                transfer.setProcessDate(processDate);
                rateService.persist(rates);
                transfer.setEmissionDate(rates.getEmissionDate());
                transfer.setExpirationDate(rates.getExpirationDate());
                transfer.setiRate(rates.getiRate());
                shouldLiberateAmount = scheduledPayment.isReserveAmount();

                // Generate the transaction number
                final TransactionNumber transactionNumber = settingsService.getLocalSettings().getTransactionNumber();
                if (transactionNumber != null && transactionNumber.isValid()) {
                    final String generated = transactionNumber.generate(transfer.getId(), transfer.getProcessDate());
                    transfer.setTransactionNumber(generated);
                }
            }

            // Notify the listeners before the payment is updated (may be seen as an insert)
            for (TransferListener listener : listeners) {
                listener.onTransferInserted(transfer);
            }

            transferDao.update(transfer);

            // Make sure no closed balances exist on the future, to fix eventual future closed balances
            accountService.removeClosedBalancesAfter(transfer.getFrom(), transfer.getProcessDate());
            accountService.removeClosedBalancesAfter(transfer.getTo(), transfer.getProcessDate());

            // Notify listeners - Should be before inserting fees to ensure the correct notification order
            notifyTransferProcessed(transfer);

            // Insert fees
            insertFees(lockHandler, transfer, false, amount, false, new HashSet<ChargedFee>());

            // Insert the corresponding amount reservation if the scheduled payment had reserved the total amount
            if (shouldLiberateAmount) {
                accountService.returnReservationForInstallment(transfer);
            }

            // Update scheduled payment status
            updateScheduledPaymentStatus(scheduledPayment);

            memberNotificationHandler.scheduledPaymentProcessingNotification(transfer, notifyPayer, notifyReceiver);
            // Notify admins
            if (transfer.getProcessDate() == null) {
                adminNotificationHandler.notifyNewPendingPayment(transfer);
            }

        } catch (final RuntimeException e) {
            if (failOnError) {
                transferDao.updateStatus(transfer.getId(), Payment.Status.FAILED);
                updateScheduledPaymentStatus(scheduledPayment);

                // Ensure the amount is liberated
                if (scheduledPayment.isReserveAmount()) {
                    accountService.returnReservationForInstallment(transfer);
                }

                memberNotificationHandler.scheduledPaymentProcessingNotification(transfer, notifyPayer, notifyReceiver);

                // Generate an alert when it's from system
                if (transfer.isFromSystem()) {
                    final Member member = (Member) transfer.getToOwner();
                    final LocalSettings settings = settingsService.getLocalSettings();
                    final Object[] arguments = { settings.getUnitsConverter(transfer.getType().getFrom().getCurrency().getPattern()).toString(transfer.getAmount()), transfer.getType().getName() };
                    alertService.create(member, MemberAlert.Alerts.SCHEDULED_PAYMENT_FAILED, arguments);
                }
            } else {
                throw e;
            }
        }
        return transfer;
    }

    private Transfer doProcessScheduledTransfer(final Transfer transfer, final boolean failOnError, final boolean notifyPayer, final boolean notifyReceiver) {
        LockHandler lockHandler = lockHandlerFactory.getLockHandlerIfLockingAccounts();
        try {
            return doProcessScheduledTransfer(lockHandler, transfer, failOnError, notifyPayer, notifyReceiver);
        } finally {
            if (lockHandler != null) {
                lockHandler.release();
            }
        }
    }

    /**
     * Resolve the first authorization level for the given payment, if any. When the payment wouldn't be authorizable, return null
     */
    private AuthorizationLevel firstAuthorizationLevel(final Transfer transfer) {
        // If the transfer is an installment of a scheduled payment, when exists another installment which was already authorized, no new auth is
        // needed
        if (transfer.getScheduledPayment() != null) {
            for (Transfer installment : transfer.getScheduledPayment().getTransfers()) {
                if (installment.getProcessDate() != null) {
                    // A processed installment. No further authorization needed
                    return null;
                }
            }
        }
        return firstAuthorizationLevel(transfer.getType(), transfer.getAmount(), transfer.getFromOwner());

    }

    /**
     * Resolve the first authorization level for the given payment, if any. When the payment wouldn't be authorizable, return null
     */
    private AuthorizationLevel firstAuthorizationLevel(TransferType transferType, final BigDecimal amount, AccountOwner from) {
        transferType = fetchService.fetch(transferType, TransferType.Relationships.AUTHORIZATION_LEVELS);
        if (transferType.isRequiresAuthorization() && CollectionUtils.isNotEmpty(transferType.getAuthorizationLevels())) {
            if (from == null) {
                from = LoggedUser.accountOwner();
            }
            final Account account = accountService.getAccount(new AccountDTO(from, transferType.getFrom()));
            BigDecimal amountSoFarToday = transferDao.getTransactionedAmountAt(null, account, transferType);
            final AuthorizationLevel authorization = transferType.getAuthorizationLevels().iterator().next();

            // When the amount is greater than the authorization, return true
            final BigDecimal amountToTest = amountSoFarToday.add(amount);
            if (amountToTest.compareTo(authorization.getAmount()) >= 0) {
                return transferType.getAuthorizationLevels().iterator().next();
            }
        }
        return null;
    }

    private Validator getPaymentValidator(final DoPaymentDTO payment) {
        final Validator validator = new Validator("transfer");
        Collection<TransactionContext> possibleContexts = new ArrayList<TransactionContext>();
        possibleContexts.add(TransactionContext.PAYMENT);
        if (LoggedUser.isWebService() || LoggedUser.isSystem()) {
            possibleContexts.add(TransactionContext.AUTOMATIC);
        } else {
            possibleContexts.add(TransactionContext.SELF_PAYMENT);
        }
        validator.property("context").required().anyOf(possibleContexts);
        validator.property("to").required().key("payment.recipient");
        // as currency is maybe not set on the DTO, we get it from the TT in stead of directly from the DTO
        final TransferType tt = fetchService.fetch(payment.getTransferType(), Relationships.TRANSACTION_FEES, RelationshipHelper.nested(TransferType.Relationships.FROM, TransferType.Relationships.TO, AccountType.Relationships.CURRENCY, Currency.Relationships.A_RATE_PARAMETERS), RelationshipHelper.nested(TransferType.Relationships.FROM, TransferType.Relationships.TO, AccountType.Relationships.CURRENCY, Currency.Relationships.D_RATE_PARAMETERS));
        final Currency currency = tt == null ? null : tt.getCurrency();
        if (currency != null && (currency.isEnableARate() || currency.isEnableDRate())) {
            // if the date is not null at this moment, it is in the past, which is not allowed with rates.
            if (payment.getDate() != null) {
                validator.general(new NoPastDateWithRatesValidator());
            }
        } else {
            validator.property("date").key("payment.manualDate").past();
        }

        validator.property("ticket").add(new TicketValidation());

        addAmountValidator(validator, tt);
        validator.property("transferType").key("transfer.type").required();
        validator.property("description").maxLength(1000);
        validator.general(new SchedulingValidator());
        validator.general(new PendingContractValidator());
        if (payment.getTransferType() != null && payment.getTo() != null && payment.getAmount() != null) {

            /*
             * For user validation, we need to check if the transaction amount is high enough to cover all fees. This depends on all fees, but only in
             * case of fixed fees it makes sense to increase the transaction amount. The formula for this is: given transactionamount > (sum of fixed
             * fees )/ (1 minus sum of percentage fees expressed as fractions). This of course only applies for fees with deductAmount; fees which are
             * not deducted are excluded from this calculation.
             */
            final TransactionFeePreviewDTO preview = transactionFeeService.preview(payment.getFrom(), payment.getTo(), tt, payment.getAmount());
            final Property amount = validator.property("amount");
            final Collection<? extends TransactionFee> fees = preview.getFees().keySet();
            BigDecimal sumOfFixedFees = BigDecimal.ZERO;
            BigDecimal sumOfPercentageFees = BigDecimal.ZERO;
            for (final TransactionFee fee : fees) {
                if (fee.isDeductAmount()) {
                    if (fee.getChargeType() == ChargeType.FIXED) {
                        sumOfFixedFees = sumOfFixedFees.add(preview.getFees().get(fee));
                    } else {
                        sumOfPercentageFees = sumOfPercentageFees.add(preview.getFees().get(fee));
                    }
                }
            }
            // Show a warning if there are fixed fees and if the amount is not enough to cover them
            if (sumOfFixedFees.signum() == 1) {
                final int scale = LocalSettings.MAX_PRECISION;
                final MathContext mc = new MathContext(scale);
                final BigDecimal sumOfPercentages = sumOfPercentageFees.divide(payment.getAmount(), mc);
                final BigDecimal minimalAmount = sumOfFixedFees.divide((BigDecimal.ONE.subtract(sumOfPercentages)), mc);
                amount.comparable(minimalAmount, ">", new ValidationError("errors.greaterThan", messageResolver.message("transactionFee.invalidChargeValue", minimalAmount)));
            } else if (preview.getFinalAmount().signum() == -1) {
                validator.general(new FinalAmountValidator());
            }

            // Custom fields
            validator.chained(new DelegatingValidator(new DelegatingValidator.DelegateSource() {
                @Override
                public Validator getValidator() {
                    return paymentCustomFieldService.getValueValidator(payment.getTransferType());
                }
            }));
        }
        return validator;
    }

    private Validator getProjectionValidator() {
        final Validator projectionValidator = new Validator("transfer");
        projectionValidator.property("paymentCount").required().positiveNonZero().add(new PropertyValidation() {
            private static final long serialVersionUID = 5022911381764849941L;

            @Override
            public ValidationError validate(final Object object, final Object property, final Object value) {
                final Integer paymentCount = (Integer) value;
                if (paymentCount == null) {
                    return null;
                }
                final ProjectionDTO dto = (ProjectionDTO) object;
                final AccountOwner from = dto.getFrom();
                if (from instanceof Member) {
                    final Member member = fetchService.fetch((Member) from, Element.Relationships.GROUP);
                    final int maxSchedulingPayments = member.getMemberGroup().getMemberSettings().getMaxSchedulingPayments();
                    return CompareToValidation.lessEquals(maxSchedulingPayments).validate(object, property, value);
                }
                return null;
            }
        });
        projectionValidator.property("amount").required().positiveNonZero();
        projectionValidator.property("firstExpirationDate").key("transfer.firstPaymentDate").required().add(new PropertyValidation() {
            private static final long serialVersionUID = -3612786027250751763L;

            @Override
            public ValidationError validate(final Object object, final Object property, final Object value) {
                final Calendar firstDate = CoercionHelper.coerce(Calendar.class, value);
                if (firstDate == null) {
                    return null;
                }
                if (firstDate.before(DateHelper.truncate(Calendar.getInstance()))) {
                    return new InvalidError();
                }
                return null;
            }
        });
        projectionValidator.property("recurrence.number").key("transfer.paymentEvery").required().between(1, 100);
        projectionValidator.property("recurrence.field").key("transfer.paymentEvery").required().anyOf(TimePeriod.Field.DAYS, TimePeriod.Field.WEEKS, TimePeriod.Field.MONTHS);
        return projectionValidator;
    }

    /**
     * gets the topmost parent of the transfer
     */
    private Transfer getTopMost(final Transfer transfer) {
        Transfer topMost = transfer;
        while (topMost.getParent() != null) {
            topMost = topMost.getParent();
        }
        return topMost;
    }

    private Collection<TransferListener> getTransferListeners(final Transfer transfer) {
        TransferType type = transfer.getType();
        Collection<TransferListener> result = new ArrayList<TransferListener>(2);

        // Get the listener from transfer type, if any
        if (StringUtils.isNotEmpty(type.getTransferListenerClass())) {
            TransferListener listener = customObjectHandler.get(type.getTransferListenerClass());
            result.add(listener);
        }

        // Get the listener from settings, if any
        LocalSettings settings = settingsService.getLocalSettings();
        if (StringUtils.isNotEmpty(settings.getTransferListenerClass())) {
            TransferListener listener = customObjectHandler.get(settings.getTransferListenerClass());
            result.add(listener);
        }
        return result;
    }

    private Validator getTransferValidator(final TransferDTO transfer) {
        final Validator validator = new Validator("transfer");
        // as currency is sometimes not set on the DTO, we get it from the TT in stead of directly from the DTO
        final TransferType tt = fetchService.fetch(transfer.getTransferType(), RelationshipHelper.nested(TransferType.Relationships.FROM, AccountType.Relationships.CURRENCY, Currency.Relationships.A_RATE_PARAMETERS, Currency.Relationships.D_RATE_PARAMETERS));
        final Currency currency = tt.getCurrency();
        // if rates enabled, it is not allowed to have a date in the past.
        if (currency.isEnableARate() || currency.isEnableDRate()) {
            final Calendar now = Calendar.getInstance();
            // make a few minutes earlier, because if the transfer's date has just before been set to Calendar.getInstance(), it may already be a
            // few milliseconds or even seconds later.
            now.add(Calendar.MINUTE, -4);
            final Calendar date = transfer.getDate();
            if (date != null && date.before(now)) {
                validator.general(new NoPastDateWithRatesValidator());
            }
        } else {
            validator.property("date").key("payment.manualDate").pastOrToday();
        }

        validator.property("fromOwner").required();
        validator.property("toOwner").required();
        addAmountValidator(validator, tt);

        validator.property("transferType").key("transfer.type").required();
        validator.property("description").maxLength(1000);
        validator.property("traceNumber").add(new TraceNumberValidation());

        if (transfer.getTransferType() != null) {
            // Custom fields
            validator.chained(new DelegatingValidator(new DelegatingValidator.DelegateSource() {
                @Override
                public Validator getValidator() {
                    return paymentCustomFieldService.getValueValidator(transfer.getTransferType());
                }
            }));
        }
        return validator;
    }

    private Payment insert(final TransferDTO dto, final boolean newTransaction, final boolean simulation) {
        // Verify the parameters
        verify(dto);
        return doInsert(dto, newTransaction, simulation);
    }

    private Transfer insertChargeback(final Transfer transfer, final boolean newTransaction) {
        return transactionHelper.maybeRunInNewTransaction(new Transactional<Transfer>() {
            @Override
            public Transfer afterCommit(final Transfer result) {
                // Ensure the transfer is attached to the current transaction
                return fetchService.fetch(result);
            }

            @Override
            public Transfer doInTransaction(final TransactionStatus status) {
                return performChargeback(transfer);
            }
        }, newTransaction);
    }

    private void insertFees(final LockHandler lockHandler, final Transfer transfer, final boolean forced, final BigDecimal originalAmount, final boolean simulation, final Set<ChargedFee> chargedFees) {
        final TransferType transferType = transfer.getType();
        final Account from = transfer.getFrom();
        final Account to = transfer.getTo();
        final TransactionFeeQuery query = new TransactionFeeQuery();
        query.setTransferType(transferType);
        final List<? extends TransactionFee> fees = transactionFeeService.search(query);
        BigDecimal totalPercentage = BigDecimal.ZERO;
        BigDecimal feeTotalAmount = BigDecimal.ZERO;
        Transfer topMost = getTopMost(transfer);
        final Calendar date = topMost.getDate();
        transfer.setChildren(new ArrayList<Transfer>());
        for (final TransactionFee fee : fees) {
            final Account fromAccount = fetchService.fetch(from, Account.Relationships.TYPE, MemberAccount.Relationships.MEMBER);
            final Account toAccount = fetchService.fetch(to, Account.Relationships.TYPE, MemberAccount.Relationships.MEMBER);

            final ChargedFee key = new ChargedFee(fee, fromAccount, toAccount);
            if (chargedFees.contains(key)) {
                throw new ValidationException("payment.error.circularFees");
            }
            chargedFees.add(key);

            // Build the fee transfer
            final BuildTransferWithFeesDTO params = new BuildTransferWithFeesDTO(date, fromAccount, toAccount, originalAmount, fee, false);
            // rate stuff; buildTransfer MUST have these set.
            params.setEmissionDate(transfer.getEmissionDate());
            params.setExpirationDate(transfer.getExpirationDate());
            final Transfer feeTransfer = transactionFeeService.buildTransfer(params);

            // If the fee transfer is null, the fee should not be applied
            if (feeTransfer == null) {
                continue;
            }
            // Ensure the last fee when 100% will be the exact amount left
            if (fee instanceof SimpleTransactionFee && fee.getAmount().isPercentage()) {
                final BigDecimal feeValue = fee.getAmount().getValue();
                // Only when it's not a single fee
                if (!(totalPercentage.equals(BigDecimal.ZERO) && feeValue.doubleValue() == 100.0)) {
                    totalPercentage = totalPercentage.add(feeValue);
                    // TODO: shouldn't this be >= 0 in stead of == 0 (Rinke) ?
                    if (totalPercentage.compareTo(new BigDecimal(100)) == 0 && feeTransfer != null) {
                        feeTransfer.setAmount(originalAmount.subtract(feeTotalAmount));
                    }
                }
            }

            // Insert the fee transfer
            if (feeTransfer != null && feeTransfer.getAmount().floatValue() > PRECISION_DELTA) {
                feeTotalAmount = feeTotalAmount.add(feeTransfer.getAmount());
                feeTransfer.setParent(transfer);
                feeTransfer.setDate(transfer.getDate());
                feeTransfer.setStatus(transfer.getStatus());
                feeTransfer.setNextAuthorizationLevel(transfer.getNextAuthorizationLevel());
                feeTransfer.setProcessDate(transfer.getProcessDate());
                feeTransfer.setExternalTransfer(transfer.getExternalTransfer());
                feeTransfer.setBy(transfer.getBy());

                // Copy custom values of common custom fields from the parent to the fee transfer
                final List<PaymentCustomField> customFields = paymentCustomFieldService.list(feeTransfer.getType(), false);
                if (!CollectionUtils.isEmpty(transfer.getCustomValues())) {
                    final Collection<PaymentCustomFieldValue> feeTransferCustomValues = new ArrayList<PaymentCustomFieldValue>();
                    for (final PaymentCustomFieldValue fieldValue : transfer.getCustomValues()) {
                        final CustomField field = fieldValue.getField();
                        if (customFields.contains(field)) {
                            final PaymentCustomFieldValue newFieldValue = new PaymentCustomFieldValue();
                            newFieldValue.setField(field);
                            newFieldValue.setValue(fieldValue.getValue());
                            feeTransferCustomValues.add(newFieldValue);
                        }
                    }
                    feeTransfer.setCustomValues(feeTransferCustomValues);
                }

                insertTransferAndPayFees(lockHandler, feeTransfer, forced, simulation, chargedFees);
                transfer.getChildren().add(feeTransfer);
            }
        }
    }

    /**
     * Inserts a TN for a transfer with the specified trace number, for the current service client
     * @return true if the TN was inserted
     */
    private boolean insertTN(final Long clientId, final String traceNumber) {
        return transactionHelper.runInNewTransaction(new TransactionCallback<Boolean>() {
            @Override
            public Boolean doInTransaction(final TransactionStatus status) {
                final TraceNumber tn = new TraceNumber();
                tn.setDate(Calendar.getInstance());
                tn.setClientId(clientId);
                tn.setTraceNumber(traceNumber);
                try {
                    traceNumberDao.insert(tn);
                    return true;
                } catch (DaoException e) {
                    status.setRollbackOnly();
                    if (ExceptionUtils.indexOfThrowable(e, DataIntegrityViolationException.class) != -1) {
                        // the unique constraint was violated - It means the trace number was already stored by a payment or by other reverse.
                        // If it was inserted by a payment then we must reverse it.
                        // If was inserted by other reverse then just ignore it.
                        return false;
                    } else {
                        throw e;
                    }
                }
            }
        });
    }

    /**
     * Insert a transfer and it's generated fees
     * @param simulation
     */
    private Transfer insertTransferAndPayFees(final LockHandler lockHandler, Transfer transfer, final boolean forced, final boolean simulation, final Set<ChargedFee> chargedFees) {
        final TransferType transferType = transfer.getType();
        final Collection<PaymentCustomFieldValue> customValues = transfer.getCustomValues();

        final Account fromAccount = transfer.getFrom();
        final Account toAccount = transfer.getTo();
        if (fromAccount.equals(toAccount)) {
            throw new ValidationException("payment.error.sameFromAntToInFee");
        }
        if (applicationService.getLockedAccountsOnPayments() == LockedAccountsOnPayments.ALL) {
            lockHandler.lock(fromAccount, toAccount);
        }
        final AccountOwner from = fromAccount.getOwner();
        final AccountOwner to = toAccount.getOwner();

        // Preview fees to determine the deducted amount
        final BigDecimal originalAmount = transfer.getAmount();
        final TransactionFeePreviewDTO preview = transactionFeeService.preview(from, to, transferType, transfer.getAmount());
        transfer.setAmount(preview.getFinalAmount());

        final Collection<TransferListener> listeners = getTransferListeners(transfer);

        // validate parent amount
        if (!forced) {
            // Notify any registered listener before validating the amount
            if (!simulation) {
                for (final TransferListener listener : listeners) {
                    listener.onBeforeValidateBalance(transfer);
                }
            }

            validateAmount(transfer.getAmount(), fromAccount, toAccount, transfer);
        }
        transfer.setCustomValues(null);

        // apply rates, but NOT on inserting authorized payments
        RatesToSave rates = new RatesToSave();
        if (transfer.getProcessDate() != null) {
            rates = rateService.applyTransfer(transfer);
            transfer.setEmissionDate(rates.getEmissionDate());
            transfer.setExpirationDate(rates.getExpirationDate());
            transfer.setiRate(rates.getiRate());
        }

        // insert transfer
        transfer = transferDao.insert(transfer);

        // now we have the tranfers' id, we can persist rate info:
        rateService.persist(rates);

        final TransactionNumber transactionNumber = settingsService.getLocalSettings().getTransactionNumber();
        if (transactionNumber != null && transactionNumber.isValid()) {
            final String generated = transactionNumber.generate(transfer.getId(), transfer.getDate());
            transferDao.updateTransactionNumber(transfer.getId(), generated);
        }

        transfer.setCustomValues(customValues);
        paymentCustomFieldService.saveValues(transfer);

        if (transfer.getProcessDate() == null) {
            // Reserve the amount if pending authorization
            accountService.reservePending(transfer);
        } else {
            // Make sure no closed balances exist after the payment. This works both for payments in past and to fix eventual future closed balances
            accountService.removeClosedBalancesAfter(transfer.getFrom(), transfer.getProcessDate());
            accountService.removeClosedBalancesAfter(transfer.getTo(), transfer.getProcessDate());
        }

        // Notify any registered listener after inserting the transfer
        if (!simulation) {
            for (TransferListener listener : listeners) {
                listener.onTransferInserted(transfer);
            }
        }

        // Log this transfer if the transaction succeeds
        final Transfer toLog = transfer;
        CurrentTransactionData.addTransactionCommitListener(new TransactionCommitListener() {
            @Override
            public void onTransactionCommit() {
                loggingHandler.logTransfer(toLog);
                // Notify the registered listeners if the transfer is processed
                if (!simulation && toLog.getProcessDate() != null && !listeners.isEmpty()) {
                    transactionHelper.runInCurrentThread(new TransactionCallbackWithoutResult() {
                        @Override
                        protected void doInTransactionWithoutResult(final TransactionStatus status) {
                            Transfer fetchedTransfer = fetchService.fetch(toLog, Payment.Relationships.FROM, Payment.Relationships.TO);
                            for (TransferListener listener : listeners) {
                                try {
                                    listener.onTransferProcessed(fetchedTransfer);
                                } catch (Exception e) {
                                    LOG.warn("Error running TransferListener " + listener, e);
                                }
                            }
                        }
                    });
                }
            }
        });

        insertFees(lockHandler, transfer, forced, originalAmount, simulation, chargedFees);
        return transfer;
    }

    private Transfer performChargeback(final LockHandler lockHandler, Transfer transfer, final Transfer parentChargeback) {

        transfer = fetchService.fetch(transfer, Transfer.Relationships.CHILDREN);

        if (applicationService.getLockedAccountsOnPayments() == LockedAccountsOnPayments.ALL) {
            lockHandler.lock(transfer.getFrom(), transfer.getTo());
        }

        // Validate the amount
        validateAmount(transfer.getAmount(), transfer.getTo(), transfer.getFrom(), transfer);

        // Duplicate the transfer, setting relevant properties on the charge-back
        Transfer chargeback = transferDao.duplicate(transfer);
        chargeback.setTraceNumber(null);
        ServiceClient serviceClient = LoggedUser.serviceClient();
        if (serviceClient != null) {
            chargeback.setClientId(serviceClient.getId());
        }
        chargeback.setChargebackOf(transfer);
        chargeback.setParent(parentChargeback);
        chargeback.setAmount(chargeback.getAmount().negate());
        final Calendar now = Calendar.getInstance();
        chargeback.setDate(now);
        chargeback.setProcessDate(now);
        chargeback.setStatus(Payment.Status.PROCESSED);
        if (LoggedUser.hasUser()) {
            chargeback.setBy(LoggedUser.element());
        }
        chargeback.setReceiver(null);
        chargeback.setScheduledPayment(null);

        // Build the description according to settings
        final LocalSettings localSettings = settingsService.getLocalSettings();
        final Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("description", transfer.getDescription());
        variables.put("date", localSettings.getDateConverter().toString(transfer.getDate()));
        chargeback.setDescription(MessageProcessingHelper.processVariables(localSettings.getChargebackDescription(), variables));

        // Insert the chargeback
        chargeback = transferDao.insert(chargeback, false);

        // Copy the custom values from the original transfer
        if (CollectionUtils.isNotEmpty(transfer.getCustomValues())) {
            final Collection<PaymentCustomFieldValue> customValues = new ArrayList<PaymentCustomFieldValue>();
            if (transfer.getCustomValues() != null) {
                for (final PaymentCustomFieldValue original : transfer.getCustomValues()) {
                    final PaymentCustomFieldValue newValue = new PaymentCustomFieldValue();
                    newValue.setTransfer(chargeback);
                    newValue.setField(original.getField());
                    newValue.setStringValue(original.getStringValue());
                    newValue.setPossibleValue(original.getPossibleValue());
                    customValues.add(newValue);
                }
            }
            chargeback.setCustomValues(customValues);
            paymentCustomFieldService.saveValues(chargeback);
        }

        // Update the original transfer
        transfer = transferDao.updateChargeBack(transfer, chargeback);

        // Assign the transaction number
        final TransactionNumber transactionNumber = settingsService.getLocalSettings().getTransactionNumber();
        if (transactionNumber != null && transactionNumber.isValid()) {
            final String generated = transactionNumber.generate(chargeback.getId(), chargeback.getDate());
            transferDao.updateTransactionNumber(chargeback.getId(), generated);
        }

        // Make sure no closed balances exist on the future, to fix eventual future closed balances
        accountService.removeClosedBalancesAfter(chargeback.getFrom(), chargeback.getProcessDate());
        accountService.removeClosedBalancesAfter(chargeback.getTo(), chargeback.getProcessDate());

        // Correct the rates, if available
        rateService.chargeback(transfer, chargeback);

        // Insert children chargebacks
        for (final Transfer child : transfer.getChildren()) {
            performChargeback(lockHandler, child, chargeback);
        }

        return chargeback;
    }

    private Transfer performChargeback(final Transfer transfer) {
        LockHandler lockHandler = lockHandlerFactory.getLockHandlerIfLockingAccounts();
        try {
            // If only the source account needs to be locked, lock it here
            if (applicationService.getLockedAccountsOnPayments() == LockedAccountsOnPayments.ORIGIN && transfer.getTo().getCreditLimit() != null) {
                lockHandler.lock(transfer.getTo());
            }
            return performChargeback(lockHandler, transfer, null);
        } finally {
            if (lockHandler != null) {
                lockHandler.release();
            }
        }
    }

    /**
     * Locks the accounts which need to be locked, and perform the insert
     */
    private Payment performInsert(final LockHandler lockHandler, final TransferDTO dto, final boolean simulation) {
        final TransferType transferType = dto.getTransferType();
        final Account fromAccount = fetchService.fetch(dto.getFrom(), RelationshipHelper.nested(Account.Relationships.TYPE, AccountType.Relationships.CURRENCY));
        final Account toAccount = fetchService.fetch(dto.getTo(), MemberAccount.Relationships.MEMBER);
        if (applicationService.getLockedAccountsOnPayments() == LockedAccountsOnPayments.ALL) {
            lockHandler.lock(fromAccount, toAccount);
        }

        // Get the feedback deadline
        Calendar feedbackDeadline = null;
        if (transferType.isRequiresFeedback()) {
            feedbackDeadline = transferType.getFeedbackExpirationTime().add(Calendar.getInstance());
        }
        boolean hasMaxAmountPerDay = BigDecimalHelper.nvl(transferType.getMaxAmountPerDay()).compareTo(BigDecimal.ZERO) > 0;

        Payment payment;
        // Check scheduling
        final Calendar now = Calendar.getInstance();
        if (CollectionUtils.isEmpty(dto.getPayments())) {
            // Not scheduled - build a transfer
            final String traceNumber = dto.getTraceNumber();
            final Long clientId = dto.getClientId();

            Transfer transfer = new Transfer();
            transfer.setFrom(fromAccount);
            transfer.setTo(toAccount);
            transfer.setBy(dto.getBy());
            transfer.setDate(now);
            transfer.setAmount(dto.getAmount());
            transfer.setType(transferType);
            transfer.setDescription(dto.getDescription());
            transfer.setAccountFeeLog(dto.getAccountFeeLog());
            transfer.setLoanPayment(dto.getLoanPayment());
            transfer.setParent(dto.getParent());
            transfer.setReceiver(dto.getReceiver());
            transfer.setExternalTransfer(dto.getExternalTransfer());
            transfer.setCustomValues(dto.getCustomValues());
            transfer.setTraceNumber(traceNumber);
            transfer.setClientId(clientId);
            transfer.setTraceData(dto.getTraceData());
            transfer.setTransactionFeedbackDeadline(feedbackDeadline);
            if (transferType.isLoanType()) {
                transfer.setEmissionDate(dto.getEmissionDate());
                transfer.setExpirationDate(dto.getExpirationDate());
                transfer.setiRate(dto.getiRate());
            }
            // Lock the accounts
            if (applicationService.getLockedAccountsOnPayments() == LockedAccountsOnPayments.ORIGIN && (fromAccount.getCreditLimit() != null || hasMaxAmountPerDay)) {
                lockHandler.lock(fromAccount);
            }

            // Tests whether there is a valid ticket to be used
            final Ticket ticket = fetchService.reload(dto.getTicket());
            if (ticket != null) {
                if (ticket.getStatus() != Ticket.Status.PENDING) {
                    throw new EntityNotFoundException(Ticket.class);
                }
                // Force the ticket parameters on the payment
                if (ticket.getAmount() != null && !ticket.getAmount().equals(transfer.getAmount())) {
                    // TODO add a translation key
                    throw new ValidationException("The payment amount is not the expected one according to the ticket");
                }
                if (!ticket.getTo().equals(transfer.getToOwner())) {
                    // TODO add a translation key
                    throw new ValidationException("The payment destination member is not the expected one according to the ticket");
                }
                if (StringUtils.isNotEmpty(ticket.getDescription()) && StringUtils.isEmpty(transfer.getDescription())) {
                    transfer.setDescription(ticket.getDescription());
                }
            }

            // Determine whether the transfer is authorized
            AuthorizationLevel firstAuthorizationLevel;
            final Transfer parent = fetchService.fetch(dto.getParent(), Transfer.Relationships.NEXT_AUTHORIZATION_LEVEL);
            if (parent != null && parent.getNextAuthorizationLevel() != null) {
                firstAuthorizationLevel = parent.getNextAuthorizationLevel();
            } else {
                firstAuthorizationLevel = firstAuthorizationLevel(transferType, transfer.getAmount(), transfer.getFromOwner());
            }

            // Authorized payments are not allowed in past date
            if (firstAuthorizationLevel != null) {
                if (dto.getDate() != null && !DateUtils.isSameDay(dto.getDate(), Calendar.getInstance())) {
                    throw new AuthorizedPaymentInPastException();
                }
            }

            // Set the status according to the authorization level
            if (firstAuthorizationLevel == null) {
                transfer.setProcessDate(dto.getDate() == null ? now : dto.getDate());
                transfer.setStatus(Transfer.Status.PROCESSED);
            } else {
                transfer.setStatus(Transfer.Status.PENDING);
                transfer.setNextAuthorizationLevel(firstAuthorizationLevel);
            }

            // Within the critical session, we must check the trace number again, as another thread could have inserted a reverse on it
            if (clientId != null && StringUtils.isNotEmpty(traceNumber)) {
                // If the TN was not inserted then this payment was already reversed, and should fail
                if (!insertTN(clientId, traceNumber)) {
                    throw new ValidationException("traceNumber", "transfer.traceNumber", new UniqueError(traceNumber));
                }
            }

            // Validate the max amount today
            if (!dto.isForced()) {
                validateMaxAmountAtDate(null, fromAccount, transferType, null, transfer.getAmount());
            }

            // Insert the transfer and pay fees
            transfer = insertTransferAndPayFees(lockHandler, transfer, dto.isForced(), simulation, new HashSet<ChargedFee>());

            // Process the authorization automatically when the authorizer is performing a payment as member
            payment = transferAuthorizationService.authorizeOnInsert(lockHandler, transfer);

            // Complete the ticket, if it exists
            if (ticket != null) {
                ticket.setAmount(payment.getAmount());
                ticket.setDescription(payment.getDescription());
                if (payment.getFrom().getOwner() instanceof Member) {
                    ticket.setFrom((Member) payment.getFrom().getOwner());
                } else {
                    ticket.setFrom(null);
                }
                ticket.setTo((Member) payment.getTo().getOwner());
                ticket.setStatus(Ticket.Status.OK);
                ticket.setTransfer((Transfer) payment);
            }

        } else {
            // Scheduled payment

            final boolean reserveTotalAmount = transferType.isReserveTotalAmountOnScheduling();
            if (!dto.isForced() && (reserveTotalAmount || hasMaxAmountPerDay)) {
                // Ensure the from account is locked, to prevent concurrent access to available balance (which could allow an account pass the limit)
                lockHandler.lock(fromAccount);

                // Validate the account has balance for the total amount
                if (reserveTotalAmount) {
                    validateAmount(dto.getAmount(), fromAccount, null, null);
                }

                // Validate the max amount today
                if (hasMaxAmountPerDay) {
                    for (final ScheduledPaymentDTO current : dto.getPayments()) {
                        validateMaxAmountAtDate(current.getDate(), fromAccount, transferType, null, current.getAmount());
                    }
                    // TODO now we're controlling the total amount, but should control by installment
                }
            }

            final Collection<PaymentCustomFieldValue> customValues = dto.getCustomValues();
            ScheduledPayment scheduledPayment = new ScheduledPayment();
            scheduledPayment.setFrom(fromAccount);
            scheduledPayment.setTo(toAccount);
            scheduledPayment.setBy(dto.getBy());
            scheduledPayment.setDate(now);
            scheduledPayment.setAmount(dto.getAmount());
            scheduledPayment.setType(transferType);
            scheduledPayment.setDescription(dto.getDescription());
            scheduledPayment.setStatus(Payment.Status.SCHEDULED);
            scheduledPayment.setReserveAmount(reserveTotalAmount);
            scheduledPayment.setShowToReceiver(transferType.isShowScheduledPaymentsToDestination() || dto.isShowScheduledToReceiver());
            scheduledPayment.setTransactionFeedbackDeadline(feedbackDeadline);

            scheduledPayment = scheduledPaymentDao.insert(scheduledPayment);
            scheduledPayment.setCustomValues(new ArrayList<PaymentCustomFieldValue>(customValues));
            paymentCustomFieldService.saveValues(scheduledPayment);

            final List<Transfer> scheduledTransfers = new ArrayList<Transfer>();
            Transfer transferToProcess = null;
            for (final ScheduledPaymentDTO current : dto.getPayments()) {
                final TransferDTO currentDTO = (TransferDTO) dto.clone();
                currentDTO.setDate(current.getDate());
                currentDTO.setAmount(current.getAmount());
                currentDTO.setScheduledPayment(scheduledPayment);
                Transfer transfer = new Transfer();
                transfer.setFrom(fromAccount);
                transfer.setTo(dto.getTo());
                transfer.setBy(dto.getBy());
                transfer.setDate(current.getDate());
                transfer.setAmount(current.getAmount());
                transfer.setType(transferType);
                transfer.setDescription(dto.getDescription());
                transfer.setStatus(Transfer.Status.SCHEDULED);
                transfer.setScheduledPayment(scheduledPayment);
                // When the payment is scheduled for today, process it now
                if (DateUtils.isSameDay(now, transfer.getDate())) {
                    transferToProcess = transfer;
                    transfer.setDate(now);
                }
                transfer = transferDao.insert(transfer);
                transfer.setCustomValues(new ArrayList<PaymentCustomFieldValue>());
                if (customValues != null) {
                    for (final PaymentCustomFieldValue fieldValue : customValues) {
                        final PaymentCustomFieldValue newValue = new PaymentCustomFieldValue();
                        newValue.setField(fieldValue.getField());
                        newValue.setStringValue(fieldValue.getStringValue());
                        newValue.setPossibleValue(fieldValue.getPossibleValue());
                        transfer.getCustomValues().add(newValue);
                    }
                }
                paymentCustomFieldService.saveValues(transfer);
                scheduledTransfers.add(transfer);
            }
            scheduledPayment.setTransfers(scheduledTransfers);

            // When the scheduled payment is set to reserve the amount, add the corresponding amount reservation
            if (scheduledPayment.isReserveAmount()) {
                accountService.reserve(scheduledPayment);
            }

            // When the first transfer should already by processed, do it now
            if (transferToProcess != null) {
                doProcessScheduledTransfer(lockHandler, transferToProcess, true, false, true);
            }
            payment = scheduledPayment;
        }

        // Return the transfer object
        return payment;
    }

    /**
     * Locks the accounts which need to be locked, and perform the insert
     */
    private Payment performInsert(final TransferDTO dto, final boolean simulation) {
        LockHandler lockHandler = lockHandlerFactory.getLockHandlerIfLockingAccounts();
        try {
            return performInsert(lockHandler, dto, simulation);
        } finally {
            if (lockHandler != null) {
                lockHandler.release();
            }
        }
    }

    private Transfer processScheduledTransfer(final Transfer transfer, final boolean failOnError, final boolean notifyPayer, final boolean notifyReceiver) {
        return transactionHelper.maybeRunInNewTransaction(new Transactional<Transfer>() {
            @Override
            public Transfer afterCommit(final Transfer result) {
                // Ensure the transfer is attached to the current transaction
                return fetchService.fetch(result);
            }

            @Override
            public Transfer doInTransaction(final TransactionStatus status) {
                return doProcessScheduledTransfer(transfer, failOnError, notifyPayer, notifyReceiver);
            };
        });
    }

    private ScheduledPayment updateScheduledPaymentStatus(ScheduledPayment scheduledPayment) {
        scheduledPayment = fetchService.fetch(scheduledPayment, ScheduledPayment.Relationships.TRANSFERS);
        scheduledPayment.setStatus(Payment.Status.PROCESSED);
        for (final Transfer transfer : scheduledPayment.getTransfers()) {
            if (transfer.getProcessDate() == null) {
                scheduledPayment.setStatus(transfer.getStatus());
                break;
            }
        }
        return scheduledPaymentDao.update(scheduledPayment);
    }

    private void validate(final TransferDTO params) {
        getTransferValidator(params).validate(params);
    }

    /**
     * Validates the given amount
     */
    private void validateAmount(final BigDecimal amount, Account fromAccount, final Account toAccount, final Transfer transfer) {
        // Validate the from account credit limit ...
        final LocalSettings localSettings = settingsService.getLocalSettings();
        if (fromAccount != null) {
            final BigDecimal creditLimit = fromAccount.getCreditLimit();
            if (creditLimit != null) {
                // ... only if not unlimited
                final AccountStatus fromStatus = accountService.getCurrentStatus(new AccountDTO(fromAccount));
                if (creditLimit.abs().floatValue() > -PRECISION_DELTA) {
                    final BigDecimal available = localSettings.round(fromStatus.getAvailableBalance());
                    if (available.subtract(amount).floatValue() < -PRECISION_DELTA) {
                        final boolean isOriginalAccount = transfer == null ? true : fromAccount.equals(transfer.getRootTransfer().getFrom());
                        fromAccount = fetchService.fetch(fromAccount, Account.Relationships.TYPE);
                        throw new NotEnoughCreditsException(fromAccount, amount, isOriginalAccount);
                    }
                }
            }
        }

        // Validate the to account upper credit limit
        if (toAccount != null) {
            final BigDecimal upperCreditLimit = toAccount.getUpperCreditLimit();
            if (upperCreditLimit != null && upperCreditLimit.floatValue() > PRECISION_DELTA) {
                final BigDecimal balance = accountService.getBalance(new AccountDateDTO(toAccount));
                if (upperCreditLimit.subtract(balance).subtract(amount).floatValue() < -PRECISION_DELTA) {
                    throw new UpperCreditLimitReachedException(localSettings.getUnitsConverter(toAccount.getType().getCurrency().getPattern()).toString(toAccount.getUpperCreditLimit()), toAccount, amount);
                }
            }
        }
    }

    /**
     * Validates if a given transfer type is valid
     */
    private TransferType validateTransferType(final TransferDTO params) {
        final TransferType transferType = transferTypeService.load(params.getTransferType().getId(), TransferType.Relationships.FROM, TransferType.Relationships.TO);
        final TransferTypeQuery ttQuery = new TransferTypeQuery();
        ttQuery.setChannel(params.getChannel());
        if (params.isAutomatic()) {
            ttQuery.setContext(transferType.isLoanType() ? TransactionContext.AUTOMATIC_LOAN : TransactionContext.AUTOMATIC);
        } else {
            ttQuery.setContext(params.getContext());
        }
        final TransactionContext context = ttQuery.getContext();
        if (context != TransactionContext.AUTOMATIC && context != TransactionContext.AUTOMATIC_LOAN) {
            ttQuery.setUsePriority(true);
        }
        ttQuery.setCurrency(params.getCurrency());
        ttQuery.setFromAccountType(transferType.getFrom());
        ttQuery.setToAccountType(transferType.getTo());
        final AccountOwner fromOwner = params.getFromOwner();

        // For non-automatic payments, ensure there is permission for the TransferType
        if (context != TransactionContext.AUTOMATIC && context != TransactionContext.AUTOMATIC_LOAN) {
            if (params.getBy() != null && fromOwner != null && !params.getBy().getAccountOwner().equals(fromOwner)) {
                // Set by when performing a payment in behalf of someone
                ttQuery.setBy(params.getBy());
            } else {
                // Test the permission for the payment
                if (fromOwner instanceof Member) {
                    ttQuery.setGroup(((Member) fromOwner).getGroup());
                } else if (LoggedUser.hasUser()) {
                    ttQuery.setGroup(LoggedUser.group());
                }
            }
        }
        ttQuery.setFromOwner(fromOwner);
        ttQuery.setToOwner(params.getToOwner());
        final List<TransferType> possibleTypes = transferTypeService.search(ttQuery);
        if (possibleTypes == null || !possibleTypes.contains(transferType)) {
            throw new UnexpectedEntityException("Transfer type not found for query");
        }
        return transferType;
    }

    private TransferDTO verify(final DoPaymentDTO params) {
        // Build and verify the DTO
        final TransferDTO dto = new TransferDTO();
        dto.setAmount(params.getAmount());
        dto.setCurrency(params.getCurrency());
        dto.setChannel(params.getChannel());
        dto.setContext(params.getContext());
        if (params.getDate() != null) {
            dto.setDate(params.getDate());
        }
        dto.setDescription(params.getDescription());
        dto.setFromOwner(params.getFrom() == null ? LoggedUser.accountOwner() : params.getFrom());

        if (LoggedUser.hasUser() && !LoggedUser.isWebService()) {
            dto.setBy(LoggedUser.element());
        }

        dto.setToOwner(params.getTo());
        dto.setTicket(params.getTicket());
        dto.setTransferType(params.getTransferType());
        dto.setReceiver(params.getReceiver());
        dto.setPayments(params.getPayments());
        dto.setCustomValues(params.getCustomValues());
        dto.setTraceData(params.getTraceData());
        dto.setShowScheduledToReceiver(params.isShowScheduledToReceiver());

        ServiceClient serviceClient = LoggedUser.serviceClient();
        if (serviceClient != null && params.getTraceNumber() != null) {
            dto.setTraceNumber(params.getTraceNumber());
            dto.setClientId(serviceClient.getId());
        }

        verify(dto);

        return dto;
    }

    private void verify(final TransferDTO params) {

        if (params.getFrom() != null) {
            final Account from = fetchService.fetch(params.getFrom(), MemberAccount.Relationships.MEMBER);
            params.setFromOwner(from.getOwner());
        }
        if (params.getTo() != null) {
            final Account to = fetchService.fetch(params.getTo(), MemberAccount.Relationships.MEMBER);
            params.setToOwner(to.getOwner());
        }

        validate(params);

        final AccountOwner fromOwner = params.getFromOwner();
        final AccountOwner toOwner = params.getToOwner();

        // Validate the transfer type
        final TransferType transferType = validateTransferType(params);

        // Retrieve the from and to accounts
        final Account fromAccount = accountService.getAccount(new AccountDTO(fromOwner, transferType.getFrom()));
        final Account toAccount = accountService.getAccount(new AccountDTO(toOwner, transferType.getTo()));

        if (fromAccount.equals(toAccount)) {
            throw new ValidationException(new ValidationError("payment.error.sameAccount"));
        }

        // Retrieve the amount
        final BigDecimal amount = params.getAmount();

        // Check the minimum payment
        if (amount.compareTo(getMinimumPayment()) == -1) {
            final LocalSettings localSettings = settingsService.getLocalSettings();

            throw new TransferMinimumPaymentException(localSettings.getUnitsConverter(fromAccount.getType().getCurrency().getPattern()).toString(getMinimumPayment()), fromAccount, amount);
        }

        // Update some retrieved parameters on the DTO
        params.setTransferType(transferType);
        params.setFrom(fromAccount);
        params.setTo(toAccount);
        if (StringUtils.isBlank(params.getDescription())) {
            params.setDescription(transferType.getDescription());
        }
    }

}
