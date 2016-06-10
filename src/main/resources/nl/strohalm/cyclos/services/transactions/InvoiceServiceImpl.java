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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import nl.strohalm.cyclos.access.AdminMemberPermission;
import nl.strohalm.cyclos.access.BrokerPermission;
import nl.strohalm.cyclos.access.OperatorPermission;
import nl.strohalm.cyclos.dao.accounts.transactions.InvoiceDAO;
import nl.strohalm.cyclos.dao.accounts.transactions.InvoicePaymentDAO;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.access.Channel;
import nl.strohalm.cyclos.entities.accounts.AccountOwner;
import nl.strohalm.cyclos.entities.accounts.AccountType;
import nl.strohalm.cyclos.entities.accounts.Currency;
import nl.strohalm.cyclos.entities.accounts.SystemAccountOwner;
import nl.strohalm.cyclos.entities.accounts.transactions.Invoice;
import nl.strohalm.cyclos.entities.accounts.transactions.InvoicePayment;
import nl.strohalm.cyclos.entities.accounts.transactions.InvoiceQuery;
import nl.strohalm.cyclos.entities.accounts.transactions.InvoiceQuery.Direction;
import nl.strohalm.cyclos.entities.accounts.transactions.InvoiceSummaryDTO;
import nl.strohalm.cyclos.entities.accounts.transactions.Payment;
import nl.strohalm.cyclos.entities.accounts.transactions.ScheduledPayment;
import nl.strohalm.cyclos.entities.accounts.transactions.Transfer;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferType;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferTypeQuery;
import nl.strohalm.cyclos.entities.alerts.Alert;
import nl.strohalm.cyclos.entities.alerts.AlertQuery;
import nl.strohalm.cyclos.entities.alerts.MemberAlert;
import nl.strohalm.cyclos.entities.customization.fields.PaymentCustomFieldValue;
import nl.strohalm.cyclos.entities.exceptions.UnexpectedEntityException;
import nl.strohalm.cyclos.entities.groups.AdminGroup;
import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.members.Operator;
import nl.strohalm.cyclos.entities.reports.InvoiceSummaryType;
import nl.strohalm.cyclos.entities.settings.AlertSettings;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.services.InitializingService;
import nl.strohalm.cyclos.services.accounts.AccountTypeServiceLocal;
import nl.strohalm.cyclos.services.accounts.MemberAccountTypeQuery;
import nl.strohalm.cyclos.services.alerts.AlertServiceLocal;
import nl.strohalm.cyclos.services.customization.PaymentCustomFieldServiceLocal;
import nl.strohalm.cyclos.services.fetch.FetchServiceLocal;
import nl.strohalm.cyclos.services.permissions.PermissionServiceLocal;
import nl.strohalm.cyclos.services.settings.SettingsServiceLocal;
import nl.strohalm.cyclos.services.transactions.exceptions.MaxAmountPerDayExceededException;
import nl.strohalm.cyclos.services.transactions.exceptions.NotEnoughCreditsException;
import nl.strohalm.cyclos.services.transactions.exceptions.UpperCreditLimitReachedException;
import nl.strohalm.cyclos.services.transfertypes.TransferTypeServiceLocal;
import nl.strohalm.cyclos.utils.CacheCleaner;
import nl.strohalm.cyclos.utils.DataIteratorHelper;
import nl.strohalm.cyclos.utils.DateHelper;
import nl.strohalm.cyclos.utils.MessageResolver;
import nl.strohalm.cyclos.utils.Period;
import nl.strohalm.cyclos.utils.PropertyHelper;
import nl.strohalm.cyclos.utils.RelationshipHelper;
import nl.strohalm.cyclos.utils.TimePeriod;
import nl.strohalm.cyclos.utils.TransactionHelper;
import nl.strohalm.cyclos.utils.Transactional;
import nl.strohalm.cyclos.utils.access.LoggedUser;
import nl.strohalm.cyclos.utils.conversion.CalendarConverter;
import nl.strohalm.cyclos.utils.conversion.NumberConverter;
import nl.strohalm.cyclos.utils.notifications.AdminNotificationHandler;
import nl.strohalm.cyclos.utils.notifications.MemberNotificationHandler;
import nl.strohalm.cyclos.utils.query.PageHelper;
import nl.strohalm.cyclos.utils.query.QueryParameters.ResultType;
import nl.strohalm.cyclos.utils.validation.DelegatingValidator;
import nl.strohalm.cyclos.utils.validation.GeneralValidation;
import nl.strohalm.cyclos.utils.validation.InvalidError;
import nl.strohalm.cyclos.utils.validation.PropertyValidation;
import nl.strohalm.cyclos.utils.validation.RequiredError;
import nl.strohalm.cyclos.utils.validation.ValidationError;
import nl.strohalm.cyclos.utils.validation.Validator;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ObjectUtils;
import org.springframework.transaction.TransactionStatus;

/**
 * Invoice service implementation
 * @author luis
 */
public class InvoiceServiceImpl implements InvoiceServiceLocal, InitializingService {

    private InvoiceDAO                     invoiceDao;
    private InvoicePaymentDAO              invoicePaymentDao;
    private PaymentServiceLocal            paymentService;
    private SettingsServiceLocal           settingsService;
    private TransferTypeServiceLocal       transferTypeService;
    private FetchServiceLocal              fetchService;
    private AccountTypeServiceLocal        accountTypeService;
    private AlertServiceLocal              alertService;
    private MessageResolver                messageResolver;
    private PaymentCustomFieldServiceLocal paymentCustomFieldService;
    private MemberNotificationHandler      memberNotificationHandler;
    private AdminNotificationHandler       adminNotificationHandler;
    private TransactionHelper              transactionHelper;

    private PermissionServiceLocal         permissionService;

    @Override
    public Invoice accept(final Invoice inputInvoice) throws NotEnoughCreditsException, UpperCreditLimitReachedException, MaxAmountPerDayExceededException, UnexpectedEntityException {
        // Remember the selected transfer type
        TransferType inputTransferType = inputInvoice.getTransferType();

        final Invoice invoice = fetchService.fetch(inputInvoice);
        if (invoice.getStatus() != Invoice.Status.OPEN) {
            throw new UnexpectedEntityException();
        }

        // If there was a transfer type on the invoice, use it - no matter what the user selected
        if (invoice.getTransferType() != null) {
            inputTransferType = invoice.getTransferType();
        }

        final TransferType transferType = inputTransferType;

        final Element performedBy = LoggedUser.hasUser() ? LoggedUser.element() : null;

        // Validate transfer type
        final List<TransferType> possibleTypes = getPossibleTransferTypes(invoice);
        if (!possibleTypes.contains(transferType)) {
            throw new UnexpectedEntityException();
        }

        // If invoice has scheduled payments, check if the first one is not expired
        final Calendar today = DateHelper.truncate(Calendar.getInstance());
        if (CollectionUtils.isNotEmpty(invoice.getPayments())) {
            final InvoicePayment invoicePayment = invoice.getPayments().get(0);
            if (invoicePayment.getDate().before(today)) {
                throw new UnexpectedEntityException();
            }
        }

        // The accepting of the invoice and the transfer must be transactional. Plus, any LockingExceptions must be retried
        return transactionHelper.maybeRunInNewTransaction(new Transactional<Invoice>() {
            @Override
            public Invoice afterCommit(final Invoice result) {
                return fetchService.fetch(result);
            }

            @Override
            public Invoice doInTransaction(final TransactionStatus status) {
                return doAccept(invoice, transferType, performedBy);
            }
        });
    }

    @Override
    public int alertExpiredSystemInvoices(final Calendar time) {
        final AlertSettings alertSettings = settingsService.getAlertSettings();
        final TimePeriod tp = alertSettings.getIdleInvoiceExpiration();
        // don't do anything if expiration period is set to 0
        if (tp == null || tp.getNumber() <= 0) {
            return 0;
        }
        // Get the limit date for open invoices
        final Calendar limit = tp.remove(DateHelper.truncate(time));

        int processed = 0;
        // List the expired invoices
        final InvoiceQuery query = new InvoiceQuery();
        query.fetch(RelationshipHelper.nested(Invoice.Relationships.DESTINATION_ACCOUNT_TYPE, AccountType.Relationships.CURRENCY), Invoice.Relationships.TO_MEMBER);
        query.setOwner(SystemAccountOwner.instance());
        query.setDirection(Direction.OUTGOING);
        query.setPeriod(Period.endingAt(limit));
        query.setStatus(Invoice.Status.OPEN);
        query.setResultType(ResultType.ITERATOR);
        final List<Invoice> invoices = search(query);
        if (!invoices.isEmpty()) {
            final LocalSettings localSettings = settingsService.getLocalSettings();
            final NumberConverter<BigDecimal> numberConverter = localSettings.getNumberConverter();
            final CalendarConverter dateTimeConverter = localSettings.getDateTimeConverter();
            for (final Invoice invoice : invoices) {
                // Create the alert
                String amount;
                if (invoice.getDestinationAccountType() != null) {
                    amount = localSettings.getUnitsConverter(invoice.getDestinationAccountType().getCurrency().getPattern()).toString(invoice.getAmount());
                } else {
                    amount = numberConverter.toString(invoice.getAmount());
                }
                final String date = dateTimeConverter.toString(invoice.getDate());
                alertService.create(invoice.getToMember(), MemberAlert.Alerts.INVOICE_IDLE_TIME_EXCEEDED, amount, date);
                invoice.setStatus(Invoice.Status.EXPIRED);
                invoiceDao.update(invoice);
                processed++;
            }
        }

        return processed;
    }

    @Override
    public boolean canAccept(final Invoice invoice) {
        // Test the basic action
        if (!testAction(invoice, true)) {
            return false;
        }
        boolean asUser = false;
        if (invoice.isToSystem()) {
            // Member to system
            if (!permissionService.hasPermission(AdminMemberPermission.INVOICES_ACCEPT)) {
                return false;
            }
        } else {
            Member member = invoice.getToMember();
            if (invoice.isFromSystem()) {
                // System to member invoice
                if (!permissionService.permission(member)
                        .admin(AdminMemberPermission.INVOICES_ACCEPT_AS_MEMBER_FROM_SYSTEM)
                        .broker(BrokerPermission.INVOICES_ACCEPT_AS_MEMBER_FROM_SYSTEM)
                        .member()
                        .operator(OperatorPermission.INVOICES_MANAGE)
                        .hasPermission()) {
                    return false;
                }
            } else {
                if (!permissionService.permission(member)
                        .admin(AdminMemberPermission.INVOICES_ACCEPT_AS_MEMBER_FROM_MEMBER)
                        .broker(BrokerPermission.INVOICES_ACCEPT_AS_MEMBER_FROM_MEMBER)
                        .member()
                        .operator(OperatorPermission.INVOICES_MANAGE)
                        .hasPermission()) {
                    return false;
                }
            }
            asUser = !ObjectUtils.equals(LoggedUser.member(), member);
        }
        // To this point, the invoice is in an 'acceptable' state, and the user has permission. The check to go is for transfer types
        return hasTTPermission(invoice, asUser);
    }

    @Override
    public boolean canCancel(final Invoice invoice) {
        // Test the basic action
        if (!testAction(invoice, false)) {
            return false;
        }
        if (invoice.isFromSystem()) {
            // System to member invoice
            return permissionService.hasPermission(AdminMemberPermission.INVOICES_CANCEL);
        } else {
            // System to member invoice
            return permissionService.permission(invoice.getFromMember())
                    .admin(AdminMemberPermission.INVOICES_CANCEL_AS_MEMBER)
                    .broker(BrokerPermission.INVOICES_CANCEL_AS_MEMBER)
                    .member()
                    .operator(OperatorPermission.INVOICES_MANAGE)
                    .hasPermission();
        }
    }

    @Override
    public Invoice cancel(Invoice invoice) throws UnexpectedEntityException {
        if (invoice.getStatus() != Invoice.Status.OPEN) {
            throw new UnexpectedEntityException();
        }

        final Element performedBy = LoggedUser.hasUser() ? LoggedUser.element() : null;
        invoice.setPerformedBy(performedBy);
        invoice.setStatus(Invoice.Status.CANCELLED);
        invoice = invoiceDao.update(invoice);

        // Notify
        memberNotificationHandler.cancelledInvoiceNotification(invoice);
        return invoice;
    }

    @Override
    public boolean canDeny(final Invoice invoice) {
        // Test the basic action
        if (!testAction(invoice, true)) {
            return false;
        }
        boolean asUser = false;
        if (invoice.isToSystem()) {
            // System to member invoice
            if (!permissionService.hasPermission(AdminMemberPermission.INVOICES_DENY)) {
                return false;
            }
        } else if (invoice.isFromSystem()) {
            // Invoices from system cannot be denied
            return false;
        } else {
            // System to member invoice
            Member member = invoice.getToMember();
            if (!permissionService.permission(member)
                    .admin(AdminMemberPermission.INVOICES_DENY_AS_MEMBER)
                    .broker(BrokerPermission.INVOICES_DENY_AS_MEMBER)
                    .member()
                    .operator(OperatorPermission.INVOICES_MANAGE)
                    .hasPermission()) {
                return false;
            }
            asUser = !ObjectUtils.equals(LoggedUser.member(), member);
        }
        // To this point, the invoice is in a 'deniable' state, and the user has permission. The check to go is for transfer types
        return hasTTPermission(invoice, asUser);
    }

    @Override
    public Invoice deny(Invoice invoice) throws UnexpectedEntityException {
        if (invoice.getStatus() != Invoice.Status.OPEN) {
            throw new UnexpectedEntityException();
        }

        final Element performedBy = LoggedUser.hasUser() ? LoggedUser.element() : null;
        invoice.setPerformedBy(performedBy);
        invoice.setStatus(Invoice.Status.DENIED);
        invoice = invoiceDao.update(invoice);

        // Compute the denied invoices to check if an alert should be created
        final AlertSettings alertSettings = settingsService.getAlertSettings();
        if (alertSettings.getAmountDeniedInvoices() > 0) {
            final Member toMember = invoice.getToMember();
            final InvoiceQuery invoiceQuery = new InvoiceQuery();
            invoiceQuery.setDirection(Direction.INCOMING);
            invoiceQuery.setOwner(toMember);
            invoiceQuery.setStatus(Invoice.Status.DENIED);
            invoiceQuery.setPageForCount();
            final int deniedInvoices = PageHelper.getTotalCount(invoiceDao.search(invoiceQuery));

            if (deniedInvoices >= alertSettings.getAmountDeniedInvoices()) {
                final AlertQuery query = new AlertQuery();
                query.setType(Alert.Type.MEMBER);
                query.setMember(toMember);
                query.setKey(MemberAlert.Alerts.DENIED_INVOICES.getValue());
                query.setPageForCount();
                final boolean hasAlert = PageHelper.getTotalCount(alertService.search(query)) > 0;
                if (!hasAlert) {
                    alertService.create(toMember, MemberAlert.Alerts.DENIED_INVOICES, deniedInvoices);
                }
            }
        }
        memberNotificationHandler.deniedInvoiceNotification(invoice);
        return invoice;
    }

    @Override
    public void expireScheduledInvoices(final Calendar time) {
        // List the invoices with expired scheduled payments
        final InvoiceQuery query = new InvoiceQuery();
        query.fetch(RelationshipHelper.nested(Invoice.Relationships.DESTINATION_ACCOUNT_TYPE, AccountType.Relationships.CURRENCY));
        query.setPaymentPeriod(Period.endingAt(DateHelper.truncatePreviosDay(time)));
        query.setDirection(Direction.OUTGOING);
        query.setStatus(Invoice.Status.OPEN);
        query.setResultType(ResultType.ITERATOR);
        CacheCleaner cacheCleaner = new CacheCleaner(fetchService);
        final List<Invoice> invoices = search(query);
        try {
            for (final Invoice invoice : invoices) {
                invoice.setStatus(Invoice.Status.EXPIRED);
                invoiceDao.update(invoice);
                memberNotificationHandler.expiredInvoiceNotification(invoice);
                cacheCleaner.clearCache();
            }
        } finally {
            DataIteratorHelper.close(invoices);
        }
    }

    public Validator getCalculateValidator() {
        final Validator calculateValidator = new Validator("invoice");
        calculateValidator.property("amount").required().positiveNonZero();
        calculateValidator.property("paymentCount").required().positiveNonZero();
        calculateValidator.property("firstExpirationDate").required().futureOrToday();
        calculateValidator.property("recurrence.number").required().positiveNonZero();
        calculateValidator.property("recurrence.field").required();
        return calculateValidator;
    }

    @Override
    public List<TransferType> getPossibleTransferTypes(Invoice invoice) {
        if (invoice.isPersistent()) {
            invoice = fetchService.fetch(invoice);
        }
        if (invoice.getTransferType() != null) {
            return Collections.singletonList(invoice.getTransferType());
        }

        final TransferTypeQuery ttQuery = new TransferTypeQuery();
        ttQuery.fetch(TransferType.Relationships.CUSTOM_FIELDS);
        ttQuery.setChannel(Channel.WEB);
        ttQuery.setContext(TransactionContext.PAYMENT);
        ttQuery.setFromOwner(invoice.getTo());
        ttQuery.setToOwner(invoice.getFrom());
        ttQuery.setToAccountType(invoice.getDestinationAccountType());
        ttQuery.setUsePriority(true);
        if (CollectionUtils.isNotEmpty(invoice.getPayments())) {
            ttQuery.setSchedulable(true);
        }
        if (invoice.isToSystem()) {
            if (LoggedUser.hasUser()) {
                ttQuery.setGroup(LoggedUser.group());
            }
        } else {
            ttQuery.setGroup(invoice.getToMember().getGroup());
        }
        return transferTypeService.search(ttQuery);
    }

    @Override
    public TransactionSummaryVO getSummary(final InvoiceSummaryDTO dto) {
        return invoiceDao.getSummary(dto);
    }

    @Override
    public TransactionSummaryVO getSummaryByType(final Currency currency, final InvoiceSummaryType invoiceSummaryType) {
        Collection<MemberGroup> memberGroups = null;
        if (LoggedUser.hasUser()) {
            AdminGroup adminGroup = LoggedUser.group();
            adminGroup = fetchService.fetch(adminGroup, AdminGroup.Relationships.MANAGES_GROUPS);
            memberGroups = adminGroup.getManagesGroups();
        }
        return invoiceDao.getSummaryByType(currency, invoiceSummaryType, memberGroups);
    }

    @Override
    public void initializeService() {
        final Calendar time = Calendar.getInstance();
        expireScheduledInvoices(time);

        // Process the expired system invoices
        alertExpiredSystemInvoices(time);
    }

    public List<Invoice> listFromMember(final Member member) {
        final InvoiceQuery query = new InvoiceQuery();
        query.setDirection(InvoiceQuery.Direction.OUTGOING);
        query.setOwner(member);
        query.setStatus(Invoice.Status.OPEN);
        return invoiceDao.search(query);
    }

    public List<Invoice> listToMember(final Member member) {
        final InvoiceQuery query = new InvoiceQuery();
        query.setDirection(InvoiceQuery.Direction.INCOMING);
        query.setOwner(member);
        query.setStatus(Invoice.Status.OPEN);
        return invoiceDao.search(query);
    }

    @Override
    public Invoice load(final Long id, final Relationship... fetch) {
        return invoiceDao.load(id, fetch);
    }

    @Override
    public Invoice loadByPayment(final Payment payment, final Relationship... fetch) {
        return invoiceDao.loadByPayment(payment, fetch);
    }

    @Override
    public List<Invoice> search(final InvoiceQuery queryParameters) {
        return invoiceDao.search(queryParameters);
    }

    @Override
    public Invoice send(final Invoice invoice) throws UnexpectedEntityException {
        preprocessInvoice(invoice);
        validate(invoice);
        return doSend(invoice, false);
    }

    @Override
    public Invoice sendAutomatically(final Invoice invoice) {
        preprocessInvoice(invoice);
        return doSend(invoice, true);
    }

    public void setAccountTypeServiceLocal(final AccountTypeServiceLocal accountTypeService) {
        this.accountTypeService = accountTypeService;
    }

    public void setAdminNotificationHandler(final AdminNotificationHandler adminNotificationHandler) {
        this.adminNotificationHandler = adminNotificationHandler;
    }

    public void setAlertServiceLocal(final AlertServiceLocal alertService) {
        this.alertService = alertService;
    }

    public void setFetchServiceLocal(final FetchServiceLocal fetchService) {
        this.fetchService = fetchService;
    }

    public void setInvoiceDao(final InvoiceDAO invoiceDao) {
        this.invoiceDao = invoiceDao;
    }

    public void setInvoicePaymentDao(final InvoicePaymentDAO invoicePaymentDao) {
        this.invoicePaymentDao = invoicePaymentDao;
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

    public void setTransferTypeServiceLocal(final TransferTypeServiceLocal transferTypeService) {
        this.transferTypeService = transferTypeService;
    }

    @Override
    public void validate(final Invoice invoice) {
        getValidator(invoice).validate(invoice);
    }

    @Override
    public void validateForAccept(final Invoice invoice) {
        getAcceptValidator().validate(invoice);
    }

    public void validateForCalculation(final ProjectionDTO dto) {
        getCalculateValidator().validate(dto);
    }

    private Invoice doAccept(Invoice invoice, TransferType transferType, Element performedBy) {
        // Ensure to reload data which was (possibly) created in another transaction (associated with another session)
        invoice = fetchService.reload(invoice);
        performedBy = fetchService.reload(performedBy);
        transferType = fetchService.reload(transferType);

        // Perform the payment
        final TransferDTO dto = new TransferDTO();
        dto.setAutomatic(true);
        dto.setFromOwner(invoice.getTo());
        dto.setToOwner(invoice.getFrom());
        dto.setBy(performedBy);
        dto.setTransferType(transferType);
        dto.setAmount(invoice.getAmount());
        // For operators which sent the invoice, we also want to copy as receiver to the transfer
        if (invoice.getSentBy() instanceof Operator) {
            dto.setReceiver(invoice.getSentBy());
        }
        dto.setDescription(invoice.getDescription());
        dto.setAccountFeeLog(invoice.getAccountFeeLog());
        dto.setCustomValues(new ArrayList<PaymentCustomFieldValue>());
        for (final PaymentCustomFieldValue fieldValue : invoice.getCustomValues()) {
            final PaymentCustomFieldValue transferValue = new PaymentCustomFieldValue();
            transferValue.setField(fieldValue.getField());
            transferValue.setValue(fieldValue.getValue());
            dto.getCustomValues().add(transferValue);
        }
        // Check if there are associated invoice payments
        if (CollectionUtils.isNotEmpty(invoice.getPayments())) {
            dto.setShowScheduledToReceiver(true);
            final List<ScheduledPaymentDTO> payments = new ArrayList<ScheduledPaymentDTO>();
            for (final InvoicePayment invoicePayment : invoice.getPayments()) {
                final ScheduledPaymentDTO scheduledPaymentDTO = new ScheduledPaymentDTO();
                scheduledPaymentDTO.setAmount(invoicePayment.getAmount());
                scheduledPaymentDTO.setDate(invoicePayment.getDate());
                payments.add(scheduledPaymentDTO);
            }
            dto.setPayments(payments);
        }
        final Payment payment = paymentService.insertWithoutNotification(dto);

        // Update the invoice
        invoice.setPayment(payment);
        invoice.setStatus(Invoice.Status.ACCEPTED);
        invoice.setPerformedBy(performedBy);
        invoice = invoiceDao.update(invoice);

        // Update invoice payments with scheduled transfers
        if (payment instanceof ScheduledPayment) {
            final ScheduledPayment scheduledPayment = (ScheduledPayment) payment;
            final Iterator<InvoicePayment> invoicePaymentsIterator = invoice.getPayments().iterator();
            final Iterator<Transfer> transfersIterator = scheduledPayment.getTransfers().iterator();
            while (invoicePaymentsIterator.hasNext()) {
                final InvoicePayment invoicePayment = invoicePaymentsIterator.next();
                final Transfer transfer = transfersIterator.next();
                invoicePayment.setTransfer(transfer);
                invoicePaymentDao.update(invoicePayment);
            }
        }

        // Notify
        memberNotificationHandler.acceptedInvoiceNotification(invoice);
        return invoice;
    }

    private Invoice doSend(Invoice invoice, final boolean automatic) {
        validate(invoice);

        // Validate if the selected transfer type is allowed
        final TransferType transferType = fetchService.fetch(invoice.getTransferType(), TransferType.Relationships.TO);
        final List<InvoicePayment> payments = invoice.getPayments();
        if (transferType != null) {
            final TransferTypeQuery ttQuery = new TransferTypeQuery();
            if (!automatic) {
                ttQuery.setContext(TransactionContext.PAYMENT);
            } else {
                ttQuery.setContext(TransactionContext.AUTOMATIC);
            }
            ttQuery.setFromOwner(invoice.getTo());
            ttQuery.setToOwner(invoice.getFrom());
            final List<TransferType> possibleTypes = transferTypeService.search(ttQuery);
            if (!possibleTypes.contains(transferType)) {
                throw new UnexpectedEntityException();
            }
            invoice.setDestinationAccountType(transferType.getTo());
        } else {
            // Validates the destination account type
            final AccountType destinationAccountType = fetchService.fetch(invoice.getDestinationAccountType());
            final MemberAccountTypeQuery atQuery = new MemberAccountTypeQuery();
            atQuery.setCanPay(invoice.getTo());
            atQuery.setOwner(invoice.getFromMember());
            final List<? extends AccountType> possibleTypes = accountTypeService.search(atQuery);
            if (!possibleTypes.contains(destinationAccountType)) {
                throw new UnexpectedEntityException();
            }
        }

        final Collection<PaymentCustomFieldValue> customValues = invoice.getCustomValues();

        // Insert the invoice
        invoice = invoiceDao.insert(invoice);
        invoice.setCustomValues(customValues);
        paymentCustomFieldService.saveValues(invoice);

        if (CollectionUtils.isNotEmpty(payments)) {
            for (final InvoicePayment payment : payments) {
                payment.setInvoice(invoice);
                invoicePaymentDao.insert(payment);
            }
        }

        // Notify
        if (invoice.isToSystem()) {
            adminNotificationHandler.notifySystemInvoice(invoice);
        } else {
            memberNotificationHandler.receivedInvoiceNotification(invoice);
        }

        return invoice;
    }

    private Validator getAcceptValidator() {
        final Validator acceptValidator = new Validator("invoice");
        acceptValidator.property("id").required().positiveNonZero();
        acceptValidator.property("transferType").required();
        return acceptValidator;
    }

    private Validator getValidator(final Invoice invoice) {
        final Validator validator = new Validator("invoice");
        validator.property("from").required();
        validator.property("to").add(new PropertyValidation() {
            private static final long serialVersionUID = -5222363482447066104L;

            @Override
            public ValidationError validate(final Object object, final Object name, final Object value) {
                final Invoice invoice = (Invoice) object;
                // Can't be the same from / to
                if (invoice.getFrom() != null && invoice.getTo() != null && invoice.getFrom().equals(invoice.getTo())) {
                    return new InvalidError();
                }
                return null;
            }
        });
        validator.property("description").required().maxLength(1000);
        validator.property("amount").required().positiveNonZero();

        if (LoggedUser.hasUser()) {
            final boolean asMember = !LoggedUser.accountOwner().equals(invoice.getFrom());
            if (asMember || LoggedUser.isMember() || LoggedUser.isOperator()) {
                validator.property("destinationAccountType").required();
            } else {
                validator.property("transferType").required();
            }
        }
        validator.general(new GeneralValidation() {
            private static final long serialVersionUID = 4085922259108191939L;

            @Override
            public ValidationError validate(final Object object) {
                // Validate the scheduled payments
                final Invoice invoice = (Invoice) object;
                final List<InvoicePayment> payments = invoice.getPayments();
                if (CollectionUtils.isEmpty(payments)) {
                    return null;
                }

                // Validate the from member
                Member fromMember = invoice.getFromMember();
                if (fromMember == null && LoggedUser.isMember()) {
                    fromMember = LoggedUser.element();
                }
                Calendar maxPaymentDate = null;
                if (fromMember != null) {
                    fromMember = fetchService.fetch(fromMember, RelationshipHelper.nested(Element.Relationships.GROUP));
                    final MemberGroup group = fromMember.getMemberGroup();

                    // Validate the max payments
                    final int maxSchedulingPayments = group.getMemberSettings().getMaxSchedulingPayments();
                    if (payments.size() > maxSchedulingPayments) {
                        return new ValidationError("errors.greaterEquals", messageResolver.message("transfer.paymentCount"), maxSchedulingPayments);
                    }

                    // Get the maximum payment date
                    final TimePeriod maxSchedulingPeriod = group.getMemberSettings().getMaxSchedulingPeriod();
                    if (maxSchedulingPeriod != null) {
                        maxPaymentDate = maxSchedulingPeriod.add(DateHelper.truncate(Calendar.getInstance()));
                    }
                }

                final BigDecimal invoiceAmount = invoice.getAmount();
                final BigDecimal minimumPayment = paymentService.getMinimumPayment();
                BigDecimal totalAmount = BigDecimal.ZERO;
                Calendar lastDate = DateHelper.truncate(Calendar.getInstance());
                for (final InvoicePayment payment : payments) {
                    final Calendar date = payment.getDate();

                    // Validate the max payment date
                    if (maxPaymentDate != null && date.after(maxPaymentDate)) {
                        final LocalSettings localSettings = settingsService.getLocalSettings();
                        final CalendarConverter dateConverter = localSettings.getRawDateConverter();
                        return new ValidationError("payment.invalid.schedulingDate", dateConverter.toString(maxPaymentDate));
                    }

                    final BigDecimal amount = payment.getAmount();

                    if (amount == null || amount.compareTo(minimumPayment) < 0) {
                        return new RequiredError(messageResolver.message("transfer.amount"));
                    }
                    if (date == null) {
                        return new RequiredError(messageResolver.message("transfer.date"));
                    } else if (date.before(lastDate)) {
                        return new ValidationError("invoice.invalid.paymentDates");
                    }
                    totalAmount = totalAmount.add(amount);
                    lastDate = date;
                }
                if (invoiceAmount != null && totalAmount.compareTo(invoiceAmount) != 0) {
                    return new ValidationError("invoice.invalid.paymentAmount");
                }
                return null;
            }

        });

        // Custom fields
        final List<TransferType> possibleTransferTypes = getPossibleTransferTypes(invoice);
        if (possibleTransferTypes.size() == 1) {
            validator.chained(new DelegatingValidator(new DelegatingValidator.DelegateSource() {
                @Override
                public Validator getValidator() {
                    final TransferType transferType = possibleTransferTypes.iterator().next();
                    return paymentCustomFieldService.getValueValidator(transferType);
                }
            }));
        }

        return validator;
    }

    private boolean hasTTPermission(final Invoice invoice, final boolean asUser) {
        TransferType transferType = invoice.getTransferType();
        if (transferType != null && !transferType.getContext().isPayment()) {
            // The invoices sent with disabled TTs are those from account fee charges
            // We cannot check for permission, because disabled TTs cannot be assigned to permission groups
            return true;
        }
        Relationship fetch = asUser ? AdminGroup.Relationships.TRANSFER_TYPES_AS_MEMBER /* same relationship for brokers */: Group.Relationships.TRANSFER_TYPES;
        List<TransferType> ttsWithPermission = PropertyHelper.get(fetchService.fetch(LoggedUser.group(), fetch), fetch.getName());
        List<TransferType> possibleTransferTypes = getPossibleTransferTypes(invoice);
        return CollectionUtils.containsAny(possibleTransferTypes, ttsWithPermission);
    }

    /**
     * Pre-process an invoice before sending from the logged user
     */
    private void preprocessInvoice(final Invoice invoice) {
        if (LoggedUser.hasUser()) {
            if (invoice.getFrom() == null) {
                invoice.setFrom(LoggedUser.accountOwner());
            }
            invoice.setSentBy(LoggedUser.element());
        }
        if (invoice.getDate() == null) {
            invoice.setDate(Calendar.getInstance());
        }
        invoice.setStatus(Invoice.Status.OPEN);
    }

    private boolean testAction(final Invoice invoice, final boolean shouldBeIncoming) {
        // Only open invoices can be receive actions
        if (!invoice.isOpen()) {
            return false;
        }
        // Test whether the logged user is or manages the expected account owner
        AccountOwner owner;
        if (shouldBeIncoming) {
            owner = invoice.getTo();
        } else {
            owner = invoice.getFrom();
        }
        if (owner instanceof SystemAccountOwner) {
            return LoggedUser.isAdministrator();
        }
        return permissionService.manages((Member) owner);
    }
}
