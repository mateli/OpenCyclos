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
package nl.strohalm.cyclos.webservices.payments;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import javax.jws.WebService;

import nl.strohalm.cyclos.entities.access.Channel;
import nl.strohalm.cyclos.entities.accounts.AccountOwner;
import nl.strohalm.cyclos.entities.accounts.AccountStatus;
import nl.strohalm.cyclos.entities.accounts.LockedAccountsOnPayments;
import nl.strohalm.cyclos.entities.accounts.SystemAccountOwner;
import nl.strohalm.cyclos.entities.accounts.transactions.Payment;
import nl.strohalm.cyclos.entities.accounts.transactions.PaymentRequestTicket;
import nl.strohalm.cyclos.entities.accounts.transactions.Ticket;
import nl.strohalm.cyclos.entities.accounts.transactions.Transfer;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferAuthorizationDTO;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferType;
import nl.strohalm.cyclos.entities.customization.fields.MemberCustomField;
import nl.strohalm.cyclos.entities.customization.fields.MemberCustomFieldValue;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.services.ServiceClient;
import nl.strohalm.cyclos.entities.services.ServiceOperation;
import nl.strohalm.cyclos.exceptions.PermissionDeniedException;
import nl.strohalm.cyclos.services.access.AccessServiceLocal;
import nl.strohalm.cyclos.services.access.exceptions.BlockedCredentialsException;
import nl.strohalm.cyclos.services.access.exceptions.InvalidCredentialsException;
import nl.strohalm.cyclos.services.accounts.AccountDTO;
import nl.strohalm.cyclos.services.accounts.AccountServiceLocal;
import nl.strohalm.cyclos.services.application.ApplicationServiceLocal;
import nl.strohalm.cyclos.services.customization.MemberCustomFieldServiceLocal;
import nl.strohalm.cyclos.services.elements.ElementServiceLocal;
import nl.strohalm.cyclos.services.services.ServiceClientServiceLocal;
import nl.strohalm.cyclos.services.transactions.BulkChargebackResult;
import nl.strohalm.cyclos.services.transactions.BulkPaymentResult;
import nl.strohalm.cyclos.services.transactions.DoPaymentDTO;
import nl.strohalm.cyclos.services.transactions.PaymentServiceLocal;
import nl.strohalm.cyclos.services.transactions.TicketServiceLocal;
import nl.strohalm.cyclos.services.transactions.TransferAuthorizationServiceLocal;
import nl.strohalm.cyclos.services.transactions.exceptions.InvalidChannelException;
import nl.strohalm.cyclos.utils.Pair;
import nl.strohalm.cyclos.webservices.WebServiceContext;
import nl.strohalm.cyclos.webservices.model.AccountHistoryTransferVO;
import nl.strohalm.cyclos.webservices.model.AccountStatusVO;
import nl.strohalm.cyclos.webservices.utils.AccountHelper;
import nl.strohalm.cyclos.webservices.utils.ChannelHelper;
import nl.strohalm.cyclos.webservices.utils.MemberHelper;
import nl.strohalm.cyclos.webservices.utils.PaymentHelper;
import nl.strohalm.cyclos.webservices.utils.WebServiceHelper;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.StringUtils;

/**
 * Implementation for payment web service
 * @author luis
 */
@WebService(name = "payment", serviceName = "payment")
public class PaymentWebServiceImpl implements PaymentWebService {

    private static class PrepareParametersResult {
        private final PaymentStatus                 status;
        private final AccountOwner                  from;
        private final AccountOwner                  to;
        private final Collection<MemberCustomField> fromRequiredFields;
        private final Collection<MemberCustomField> toRequiredFields;

        public PrepareParametersResult(final PaymentStatus status, final AccountOwner from, final AccountOwner to, final Collection<MemberCustomField> fromRequiredFields, final Collection<MemberCustomField> toRequiredFields) {
            this.status = status;
            this.from = from;
            this.to = to;
            this.fromRequiredFields = fromRequiredFields;
            this.toRequiredFields = toRequiredFields;
        }

        public AccountOwner getFrom() {
            return from;
        }

        public Collection<MemberCustomField> getFromRequiredFields() {
            return fromRequiredFields;
        }

        public PaymentStatus getStatus() {
            return status;
        }

        public AccountOwner getTo() {
            return to;
        }

        public Collection<MemberCustomField> getToRequiredFields() {
            return toRequiredFields;
        }
    }

    /**
     * Common interface used by chargeback / reverse
     * @author andres
     * @param <V>
     */
    private interface TransferLoader<V> {
        Transfer load(V v);
    }

    private AccountServiceLocal               accountServiceLocal;
    private AccessServiceLocal                accessServiceLocal;
    private ApplicationServiceLocal           applicationServiceLocal;
    private PaymentServiceLocal               paymentServiceLocal;
    private TicketServiceLocal                ticketServiceLocal;
    private ElementServiceLocal               elementServiceLocal;
    private MemberCustomFieldServiceLocal     memberCustomFieldServiceLocal;
    private ServiceClientServiceLocal         serviceClientServiceLocal;
    private TransferAuthorizationServiceLocal transferAuthorizationServiceLocal;
    private PaymentHelper                     paymentHelper;
    private MemberHelper                      memberHelper;
    private WebServiceHelper                  webServiceHelper;
    private AccountHelper                     accountHelper;
    private ChannelHelper                     channelHelper;

    @Override
    public ChargebackResult chargeback(final Long transferId) {
        return reverse(transferId, new TransferLoader<Long>() {
            @Override
            public Transfer load(final Long transferId) {
                return paymentServiceLocal.load(transferId);
            }
        });
    }

    @Override
    public PaymentResult confirmPayment(final ConfirmPaymentParameters params) {
        Exception errorException = null;
        AccountStatus fromMemberStatus = null;
        AccountStatus toMemberStatus = null;
        Member fromMember = null;
        Member toMember = null;

        // It's nonsense to use this if restricted to a member
        if (WebServiceContext.getMember() != null) {
            throw new PermissionDeniedException();
        }
        final Channel channel = WebServiceContext.getChannel();
        final String channelName = channel == null ? null : channel.getInternalName();

        PaymentStatus status = null;
        AccountHistoryTransferVO transferVO = null;

        // Get the ticket
        PaymentRequestTicket ticket = null;
        try {
            // Check that the ticket is valid
            final Ticket t = ticketServiceLocal.load(params.getTicket());
            fromMember = t.getFrom();
            toMember = t.getTo();

            if (!(t instanceof PaymentRequestTicket) || t.getStatus() != Ticket.Status.PENDING) {
                throw new Exception("Invalid ticket and/or status: " + t.getClass().getName() + ", status: " + t.getStatus());
            }
            // Check that the channel is the expected one
            ticket = (PaymentRequestTicket) t;
            if (!ticket.getToChannel().getInternalName().equals(channelName)) {
                throw new Exception("The ticket's destination channel is not the expected one (expected=" + channelName + "): " + ticket.getToChannel().getInternalName());
            }
        } catch (final Exception e) {
            errorException = e;
            status = PaymentStatus.INVALID_PARAMETERS;
        }

        // Validate the Channel and credentials
        Member member = null;
        if (status == null) {
            member = ticket.getFrom();
            if (!accessServiceLocal.isChannelEnabledForMember(channelName, member)) {
                status = PaymentStatus.INVALID_CHANNEL;
            }
            if (status == null && WebServiceContext.getClient().isCredentialsRequired()) {
                try {
                    checkCredentials(member, channel, params.getCredentials());
                } catch (final InvalidCredentialsException e) {
                    errorException = e;
                    status = PaymentStatus.INVALID_CREDENTIALS;
                } catch (final BlockedCredentialsException e) {
                    errorException = e;
                    status = PaymentStatus.BLOCKED_CREDENTIALS;
                }
            }
        }
        Transfer transfer = null;
        // Confirm the payment
        if (status == null) {
            try {
                transfer = paymentServiceLocal.confirmPayment(ticket.getTicket());
                status = paymentHelper.toStatus(transfer);
                transferVO = accountHelper.toVO(member, transfer, null);

                if (WebServiceContext.getClient().getPermissions().contains(ServiceOperation.ACCOUNT_DETAILS)) {
                    if (WebServiceContext.getMember() == null) {
                        fromMemberStatus = accountServiceLocal.getCurrentStatus(new AccountDTO(fromMember, transfer.getFrom().getType()));
                        toMemberStatus = accountServiceLocal.getCurrentStatus(new AccountDTO(toMember, transfer.getTo().getType()));
                    } else if (WebServiceContext.getMember().equals(fromMember)) {
                        fromMemberStatus = accountServiceLocal.getCurrentStatus(new AccountDTO(fromMember, transfer.getFrom().getType()));
                    } else {
                        toMemberStatus = accountServiceLocal.getCurrentStatus(new AccountDTO(toMember, transfer.getTo().getType()));
                    }
                }
            } catch (final Exception e) {
                errorException = e;
                if (applicationServiceLocal.getLockedAccountsOnPayments() == LockedAccountsOnPayments.NONE) {
                    // The payment is committed on the same transaction so it will be rolled back by this exception, then status is given by this
                    // exception.
                    status = paymentHelper.toStatus(e);
                } else if (status == null) {
                    /*
                     * The payment is committed on a new transaction. So this exception won't roll back the payment. The status sholuldn't be modified
                     * by this exception unless there isn't a status, in which case return this exception.
                     */
                    status = paymentHelper.toStatus(e);
                }
            }
        }

        if (!status.isSuccessful()) {
            if (errorException != null) {
                webServiceHelper.error(errorException);
            } else {
                webServiceHelper.error("Confirm payment status: " + status);
            }
        }
        // Build the result
        return new PaymentResult(status, transferVO, accountHelper.toVO(fromMemberStatus), accountHelper.toVO(toMemberStatus));
    }

    @Override
    public List<ChargebackResult> doBulkChargeback(final List<Long> transfersIds) {
        return doBulkChargeback(transfersIds, new TransferLoader<Long>() {
            @Override
            public Transfer load(final Long transfersId) {
                return paymentServiceLocal.load(transfersId);
            }
        });
    }

    @Override
    public List<PaymentResult> doBulkPayment(final List<PaymentParameters> params) {
        final int size = params == null ? 0 : params.size();
        final List<PaymentResult> results = new ArrayList<PaymentResult>(size);
        final DoPaymentDTO[] dtos = new DoPaymentDTO[size];
        final PrepareParametersResult[] loadedPrepareParameters = new PrepareParametersResult[size];
        if (size > 0) {
            // We should lock at once all from accounts for all payments, but only if all accounts are passed ok
            boolean hasError = false;
            final List<AccountDTO> allAccounts = new ArrayList<AccountDTO>();
            for (int i = 0; i < params.size(); i++) {
                final PaymentParameters param = params.get(i);
                final PrepareParametersResult result = prepareParameters(param);

                if (result.getStatus() == null) {
                    try {
                        final DoPaymentDTO dto = paymentHelper.toExternalPaymentDTO(param, result.getFrom(), result.getTo());
                        if (!validateTransferType(dto)) {
                            results.add(new PaymentResult(PaymentStatus.INVALID_PARAMETERS, null));
                            webServiceHelper.error("The specified transfer type is invalid: " + dto.getTransferType());
                            hasError = true;
                        } else {
                            allAccounts.add(new AccountDTO(result.getFrom(), dto.getTransferType().getFrom()));
                            results.add(new PaymentResult(PaymentStatus.NOT_PERFORMED, null));
                        }
                        loadedPrepareParameters[i] = result;
                        dtos[i] = dto;
                    } catch (final Exception e) {
                        webServiceHelper.error(e);
                        hasError = true;
                        results.add(new PaymentResult(paymentHelper.toStatus(e), null));
                    }
                } else {
                    hasError = true;
                    results.add(new PaymentResult(result.getStatus(), null));
                    webServiceHelper.error("Bulk payment validation status [" + i + "]: " + result.getStatus());
                }
            }
            if (!hasError) {
                // No static validation error. Perform all payments
                final List<BulkPaymentResult> bulkResults = paymentServiceLocal.doBulkPayment(Arrays.asList(dtos));
                for (int i = 0; i < params.size(); i++) {
                    final PaymentParameters param = params.get(i);
                    BulkPaymentResult bulkResult;
                    try {
                        bulkResult = bulkResults.get(i);
                    } catch (final IndexOutOfBoundsException e) {
                        bulkResult = null;
                    }
                    PaymentResult result;
                    if (hasError || bulkResult == null) {
                        result = new PaymentResult(PaymentStatus.NOT_PERFORMED, null);
                    } else {
                        result = new PaymentResult();
                        final Transfer transfer = (Transfer) bulkResult.getPayment();
                        PaymentStatus status = null;

                        try {
                            if (transfer == null) {
                                status = paymentHelper.toStatus(bulkResult.getException());
                            } else {
                                status = paymentHelper.toStatus(transfer);
                                result.setTransfer(accountHelper.toVO(WebServiceContext.getMember(), transfer, null, loadedPrepareParameters[i].getFromRequiredFields(), loadedPrepareParameters[i].getToRequiredFields()));
                            }
                            if (!status.isSuccessful()) {
                                hasError = true;
                            }

                            // Set the account status, as requested
                            final AccountStatusVO[] statuses = getAccountStatusesForPayment(param, transfer);
                            result.setFromAccountStatus(statuses[0]);
                            result.setToAccountStatus(statuses[1]);
                        } catch (Exception e) {
                            webServiceHelper.error(e);
                            if (status == null) {
                                /*
                                 * doBulkPayment always opens a new transaction to perform the payments. So this exception won't roll back the
                                 * payments. The status sholuldn't be modified by this exception unless there isn't a status, in which case return
                                 * this exception.
                                 */

                                status = paymentHelper.toStatus(e);
                            }
                        }
                        result.setStatus(status);

                    }
                    results.set(i, result);
                }
            }
        }
        return results;
    }

    @Override
    public List<ChargebackResult> doBulkReverse(final List<String> traces) {
        return doBulkChargeback(traces, new TransferLoader<String>() {
            @Override
            public Transfer load(final String traceNumber) {
                return paymentServiceLocal.loadTransferForReverse(traceNumber);
            }
        });
    }

    @Override
    public PaymentResult doPayment(final PaymentParameters params) {
        AccountHistoryTransferVO transferVO = null;
        Transfer transfer = null;
        PaymentStatus status = null;
        AccountStatusVO fromMemberStatus = null;
        AccountStatusVO toMemberStatus = null;
        try {
            final PrepareParametersResult result = prepareParameters(params);
            status = result.getStatus();

            if (status == null) {
                // Status null means no "pre-payment" errors (like validation, pin, channel...)
                // Perform the payment
                final DoPaymentDTO dto = paymentHelper.toExternalPaymentDTO(params, result.getFrom(), result.getTo());

                // Validate the transfer type
                if (!validateTransferType(dto)) {
                    status = PaymentStatus.INVALID_PARAMETERS;
                    webServiceHelper.trace(status + ". Reason: The service client doesn't have permission to the specified transfer type: " + dto.getTransferType());
                } else {
                    transfer = (Transfer) paymentServiceLocal.doPayment(dto);
                    status = paymentHelper.toStatus(transfer);
                    transferVO = accountHelper.toVO(WebServiceContext.getMember(), transfer, null, result.getFromRequiredFields(), result.getToRequiredFields());
                    final AccountStatusVO[] statuses = getAccountStatusesForPayment(params, transfer);
                    fromMemberStatus = statuses[0];
                    toMemberStatus = statuses[1];
                }
            }
        } catch (final Exception e) {
            webServiceHelper.error(e);
            if (applicationServiceLocal.getLockedAccountsOnPayments() == LockedAccountsOnPayments.NONE) {
                // The payment is committed on the same transaction so it will be rolled back by this exception, then status is given by this
                // exception.
                status = paymentHelper.toStatus(e);
            } else if (status == null) {
                /*
                 * The payment is committed on a new transaction. So this exception won't roll back the payment. The status sholuldn't be modified by
                 * this exception unless there isn't a status, in which case return this exception.
                 */
                status = paymentHelper.toStatus(e);
            }
        }

        if (!status.isSuccessful()) {
            webServiceHelper.error("Payment error status: " + status);
        }

        return new PaymentResult(status, transferVO, fromMemberStatus, toMemberStatus);
    }

    @Override
    public boolean expireTicket(final String ticketStr) {
        try {
            final PaymentRequestTicket ticket = (PaymentRequestTicket) ticketServiceLocal.load(ticketStr, PaymentRequestTicket.Relationships.FROM_CHANNEL);
            // Check the member restriction
            final Member restricted = WebServiceContext.getMember();
            if (restricted != null && !restricted.equals(ticket.getTo())) {
                throw new Exception();
            }

            // Check the from channel
            final Channel resolvedChannel = WebServiceContext.getChannel();
            final Channel fromChannel = ticket.getFromChannel();
            final Channel toChannel = ticket.getToChannel();
            if ((fromChannel == null || !fromChannel.equals(resolvedChannel)) && (toChannel == null || !toChannel.equals(resolvedChannel))) {
                throw new Exception();
            }

            // Check by status
            if (ticket.getStatus() == Ticket.Status.PENDING) {
                ticketServiceLocal.expirePaymentRequestTicket(ticket);
                return true;
            }
        } catch (final Exception e) {
            webServiceHelper.error(e);
            // Ignore exceptions
        }
        return false;
    }

    @Override
    public RequestPaymentResult requestPaymentConfirmation(final RequestPaymentParameters params) {
        Exception errorException = null;
        PaymentRequestStatus status = null;
        // Get the to member
        Member toMember = null;
        final Member restricted = WebServiceContext.getMember();
        if (restricted != null) {
            // When restricted to a member, he is always the to
            toMember = restricted;
        } else {
            try {
                toMember = paymentHelper.resolveToMember(params);
            } catch (final EntityNotFoundException e) {
                status = PaymentRequestStatus.TO_NOT_FOUND;
            }
            // When not restricted to a member, check the channel access of the payment receiver
            if (status == null && !memberHelper.isChannelEnabledForMember(toMember)) {
                status = PaymentRequestStatus.TO_INVALID_CHANNEL;
            }
        }
        // Get the from member
        Member fromMember = null;
        if (status == null) {
            try {
                fromMember = paymentHelper.resolveFromMember(params);
            } catch (final EntityNotFoundException e) {
                status = PaymentRequestStatus.FROM_NOT_FOUND;
            }
        }

        // Generate the ticket if no error so far
        PaymentRequestTicket ticket = null;
        if (status == null) {
            try {
                ticket = paymentHelper.toTicket(params, null);
                ticket.setFrom(fromMember);
                ticket.setTo(toMember);
                ticket = ticketServiceLocal.generate(ticket);
                status = PaymentRequestStatus.REQUEST_RECEIVED;
            } catch (final InvalidChannelException e) {
                status = PaymentRequestStatus.FROM_INVALID_CHANNEL;
            } catch (final Exception e) {
                errorException = e;
                final PaymentStatus paymentStatus = paymentHelper.toStatus(e);
                try {
                    // Probably it's a payment status also present on payment request status
                    status = PaymentRequestStatus.valueOf(paymentStatus.name());
                } catch (final Exception e1) {
                    e1.initCause(e);
                    errorException = e1;
                    status = PaymentRequestStatus.UNKNOWN_ERROR;
                }
            }
        }

        if (!status.isSuccessful()) {
            if (errorException != null) {
                webServiceHelper.error(errorException);
            } else {
                webServiceHelper.error("Request payment confirmation status: " + status);
            }
        }

        // Build a result
        final RequestPaymentResult result = new RequestPaymentResult();
        result.setStatus(status);
        if (ticket != null) {
            result.setTicket(ticket.getTicket());
        }
        return result;
    }

    @Override
    public ChargebackResult reverse(final String traceNumber) {
        return reverse(traceNumber, new TransferLoader<String>() {
            @Override
            public Transfer load(final String traceNumber) {
                return paymentServiceLocal.loadTransferForReverse(traceNumber);
            }
        });
    }

    public void setAccessServiceLocal(final AccessServiceLocal accessService) {
        accessServiceLocal = accessService;
    }

    public void setAccountHelper(final AccountHelper accountHelper) {
        this.accountHelper = accountHelper;
    }

    public void setAccountServiceLocal(final AccountServiceLocal accountService) {
        accountServiceLocal = accountService;
    }

    public void setApplicationServiceLocal(final ApplicationServiceLocal applicationServiceLocal) {
        this.applicationServiceLocal = applicationServiceLocal;
    }

    public void setChannelHelper(final ChannelHelper channelHelper) {
        this.channelHelper = channelHelper;
    }

    public void setElementServiceLocal(final ElementServiceLocal elementService) {
        elementServiceLocal = elementService;
    }

    public void setMemberCustomFieldServiceLocal(final MemberCustomFieldServiceLocal memberCustomFieldService) {
        memberCustomFieldServiceLocal = memberCustomFieldService;
    }

    public void setMemberHelper(final MemberHelper memberHelper) {
        this.memberHelper = memberHelper;
    }

    public void setPaymentHelper(final PaymentHelper paymentHelper) {
        this.paymentHelper = paymentHelper;
    }

    public void setPaymentServiceLocal(final PaymentServiceLocal paymentService) {
        paymentServiceLocal = paymentService;
    }

    public void setServiceClientServiceLocal(final ServiceClientServiceLocal serviceClientService) {
        serviceClientServiceLocal = serviceClientService;
    }

    public void setTicketServiceLocal(final TicketServiceLocal ticketService) {
        ticketServiceLocal = ticketService;
    }

    public void setTransferAuthorizationServiceLocal(final TransferAuthorizationServiceLocal transferAuthorizationService) {
        transferAuthorizationServiceLocal = transferAuthorizationService;
    }

    public void setWebServiceHelper(final WebServiceHelper webServiceHelper) {
        this.webServiceHelper = webServiceHelper;
    }

    @Override
    public PaymentStatus simulatePayment(final PaymentParameters params) {

        PaymentStatus status = null;

        try {
            final PrepareParametersResult result = prepareParameters(params);
            status = result.getStatus();

            if (status == null) {
                final DoPaymentDTO dto = paymentHelper.toExternalPaymentDTO(params, result.getFrom(), result.getTo());
                if (!validateTransferType(dto)) {
                    webServiceHelper.trace(PaymentStatus.INVALID_PARAMETERS + ". Reason: The service client doesn't have permission to the specified transfer type: " + dto.getTransferType());
                    return PaymentStatus.INVALID_PARAMETERS;
                } else {
                    // Simulate the payment
                    final Transfer transfer = (Transfer) paymentServiceLocal.simulatePayment(dto);
                    return paymentHelper.toStatus(transfer);
                }
            }
        } catch (final Exception e) {
            webServiceHelper.error(e);
            return paymentHelper.toStatus(e);
        }

        if (!status.isSuccessful()) {
            webServiceHelper.error("Simulate payment status: " + status);
        }
        return status;
    }

    /**
     * Checks the given member's pin
     */
    private void checkCredentials(Member member, final Channel channel, final String credentials) {
        if (member == null) {
            return;
        }
        final ServiceClient client = WebServiceContext.getClient();
        final Member restrictedMember = client.getMember();
        if (restrictedMember == null) {
            // Non-restricted clients use the flag credentials required
            if (!client.isCredentialsRequired()) {
                // No credentials should be checked
                throw new InvalidCredentialsException();
            }
        } else {
            // Restricted clients don't need check if is the same member
            if (restrictedMember.equals(member)) {
                throw new InvalidCredentialsException();
            }
        }
        if (StringUtils.isEmpty(credentials)) {
            throw new InvalidCredentialsException();
        }
        member = elementServiceLocal.load(member.getId(), Element.Relationships.USER);
        accessServiceLocal.checkCredentials(channel, member.getMemberUser(), credentials, WebServiceContext.getRequest().getRemoteAddr(), WebServiceContext.getMember());
    }

    /**
     * Performs some static checks and loads the transfers to be chargedback by the bulk reverse operation.
     * @param ids: Could be trace numbers or transfer ids.
     * @param loader: The object responsible for loading the transfer by trace number or transferId.
     */
    private <V> List<ChargebackResult> doBulkChargeback(final List<V> ids, final TransferLoader<V> loader) {
        final List<Transfer> transfers = new LinkedList<Transfer>();
        final List<Transfer> actualTransfers = new LinkedList<Transfer>();
        final Member member = WebServiceContext.getMember();
        final List<ChargebackResult> results = new LinkedList<ChargebackResult>();
        boolean hasError = false;

        // Load each transfer
        for (final V id : ids) {
            Transfer transfer = null;
            try {
                transfer = loader.load(id);
                transfers.add(transfer);
                if (member != null && !transfer.getToOwner().equals(member)) {
                    throw new EntityNotFoundException();
                }

                // Preprocess the chargeback
                final Pair<ChargebackStatus, Transfer> preprocessResult = preprocessChargeback(transfer);
                final ChargebackStatus status = preprocessResult.getFirst();
                final Transfer preprocessTransfer = preprocessResult.getSecond();

                if (status == ChargebackStatus.SUCCESS) {
                    // This transfer was considered succes by other means (for example, by canceling a pending payment). Don't pass it to
                    // bulkChargeback
                    actualTransfers.add(null);
                    final AccountHistoryTransferVO chargebackVO = accountHelper.toVO(WebServiceContext.getMember(), preprocessTransfer, null);
                    final AccountHistoryTransferVO transferVO = accountHelper.toVO(WebServiceContext.getMember(), transfer, null);
                    results.add(new ChargebackResult(status, chargebackVO, transferVO));
                } else {
                    // The transfer could have already failed if status != null or ok if status == null;
                    actualTransfers.add(transfer);
                    results.add(new ChargebackResult(status == null ? ChargebackStatus.NOT_PERFORMED : status, null, null));
                    if (status != null) {
                        hasError = true;
                    }
                }
            } catch (final EntityNotFoundException e) {
                hasError = true;
                results.add(new ChargebackResult(ChargebackStatus.TRANSFER_NOT_FOUND, null, null));
                webServiceHelper.error(new Exception("Bulk status [Id=" + id + "]: " + ChargebackStatus.TRANSFER_NOT_FOUND, e));
            }
        }

        if (hasError) {
            // No need to go on with bulkChargeback as we already know something has failed
            return results;
        }

        // Do the bulk chargeback
        final List<BulkChargebackResult> bulkResults = paymentServiceLocal.bulkChargeback(actualTransfers);
        for (int i = 0; i < actualTransfers.size(); i++) {
            final Transfer actualTransfer = actualTransfers.get(i);
            if (actualTransfer == null) {
                // It was not passed to bulkChargeback, as we already knew the status. Skip this one
                continue;
            }
            BulkChargebackResult bulkResult;
            try {
                bulkResult = bulkResults.get(i);
            } catch (final IndexOutOfBoundsException e) {
                bulkResult = null;
            }

            ChargebackResult result;
            if (hasError || bulkResult == null) {
                result = new ChargebackResult(ChargebackStatus.NOT_PERFORMED, null, null);
            } else {
                result = new ChargebackResult();
                final Transfer chargeback = bulkResult.getTransfer();
                ChargebackStatus status = null;
                if (chargeback == null) {
                    status = ChargebackStatus.TRANSFER_CANNOT_BE_CHARGEDBACK;
                    hasError = true;
                } else {
                    status = ChargebackStatus.SUCCESS;
                    try {
                        result.setChargebackTransfer(accountHelper.toVO(WebServiceContext.getMember(), chargeback, null));
                        result.setOriginalTransfer(accountHelper.toVO(WebServiceContext.getMember(), actualTransfer, null));
                    } catch (Exception e) {
                        webServiceHelper.error(e);
                        if (status == null) {
                            /*
                             * doBulkChargeback always opens a new transaction to perform the payments. So this exception won't roll back the
                             * chargebacks. The status sholuldn't be modified by this exception unless there isn't a status, in which case return this
                             * exception.
                             */

                            status = ChargebackStatus.TRANSFER_CANNOT_BE_CHARGEDBACK;
                        }
                    }
                }
                result.setStatus(status);
            }
            results.set(i, result);
        }
        return results;
    }

    private ChargebackResult doChargeback(final Transfer transfer) {

        final Pair<ChargebackStatus, Transfer> preprocessResult = preprocessChargeback(transfer);
        ChargebackStatus status = preprocessResult.getFirst();
        Transfer chargebackTransfer = preprocessResult.getSecond();

        // Do the chargeback
        if (status == null) {
            chargebackTransfer = paymentServiceLocal.chargeback(transfer);
            status = ChargebackStatus.SUCCESS;
        }

        if (!status.isSuccessful()) {
            webServiceHelper.error("Chargeback result: " + status);
        }

        Member member = WebServiceContext.getMember();
        // Build the result
        if (status == ChargebackStatus.SUCCESS || status == ChargebackStatus.TRANSFER_ALREADY_CHARGEDBACK) {
            AccountHistoryTransferVO originalVO = null;
            AccountHistoryTransferVO chargebackVO = null;
            try {
                final AccountOwner owner = member == null ? transfer.getToOwner() : member;
                originalVO = accountHelper.toVO(owner, transfer, null);
                chargebackVO = accountHelper.toVO(owner, chargebackTransfer, null);
            } catch (Exception e) {
                webServiceHelper.error(e);
                if (applicationServiceLocal.getLockedAccountsOnPayments() == LockedAccountsOnPayments.NONE) {
                    // The chargeback is committed on the same transaction so it will be rolled back by this exception, then status is given by this
                    // exception.
                    status = ChargebackStatus.TRANSFER_CANNOT_BE_CHARGEDBACK;
                }
                // When the locking method is not NONE, the chargeback is committed on a new transaction so we'll preserve the status.
            }
            return new ChargebackResult(status, originalVO, chargebackVO);
        } else {
            return new ChargebackResult(status, null, null);
        }
    }

    /**
     * Returns the account status for the from account and to account, if the given {@link PaymentParameters#isReturnStatus()} is true and according
     * to the current invocation permissions
     */
    private AccountStatusVO[] getAccountStatusesForPayment(final PaymentParameters params, final Transfer transfer) {
        AccountStatus fromMemberStatus = null;
        AccountStatus toMemberStatus = null;
        if (WebServiceContext.getClient().getPermissions().contains(ServiceOperation.ACCOUNT_DETAILS) && params.getReturnStatus()) {
            if (WebServiceContext.getMember() == null) {
                fromMemberStatus = transfer.isFromSystem() ? null : accountServiceLocal.getCurrentStatus(new AccountDTO(transfer.getFrom()));
                toMemberStatus = transfer.isToSystem() ? null : accountServiceLocal.getCurrentStatus(new AccountDTO(transfer.getTo()));
            } else if (WebServiceContext.getMember().equals(paymentHelper.resolveFromMember(params))) {
                fromMemberStatus = transfer.isFromSystem() ? null : accountServiceLocal.getCurrentStatus(new AccountDTO(transfer.getFrom()));
            } else {
                toMemberStatus = transfer.isToSystem() ? null : accountServiceLocal.getCurrentStatus(new AccountDTO(transfer.getTo()));
            }
        }
        return new AccountStatusVO[] {
                accountHelper.toVO(fromMemberStatus),
                accountHelper.toVO(toMemberStatus) };
    }

    private Collection<MemberCustomField> getMemberCustomFields(final Member member, final List<String> fieldInternalNames) {
        Collection<MemberCustomField> fields = new HashSet<MemberCustomField>();

        for (final String internalName : fieldInternalNames) {
            MemberCustomFieldValue mcfv = (MemberCustomFieldValue) CollectionUtils.find(member.getCustomValues(), new Predicate() {
                @Override
                public boolean evaluate(final Object object) {
                    MemberCustomFieldValue mcfv = (MemberCustomFieldValue) object;
                    return mcfv.getField().getInternalName().equals(internalName);
                }
            });
            if (mcfv == null) {
                webServiceHelper.trace(String.format("Required field '%1$s' was not found for member %2$s", internalName, member));
                return null;
            } else {
                fields.add(memberCustomFieldServiceLocal.load(mcfv.getField().getId()));
            }
        }

        return fields;
    }

    /**
     * Prepares the parameters for a payment. The resulting status is null when no problem found
     */
    private PrepareParametersResult prepareParameters(final PaymentParameters params) {

        final Member restricted = WebServiceContext.getMember();
        final boolean fromSystem = params.getFromSystem();
        final boolean toSystem = params.getToSystem();
        PaymentStatus status = null;
        Member fromMember = null;
        Member toMember = null;
        // Load the from member
        if (!fromSystem) {
            try {
                fromMember = paymentHelper.resolveFromMember(params);
            } catch (final EntityNotFoundException e) {
                webServiceHelper.error(e);
                status = PaymentStatus.FROM_NOT_FOUND;
            }
        }
        // Load the to member
        if (status == null && !toSystem) {
            try {
                toMember = paymentHelper.resolveToMember(params);
            } catch (final EntityNotFoundException e) {
                webServiceHelper.error(e);
                status = PaymentStatus.TO_NOT_FOUND;
            }
        }

        if (status == null) {
            if (restricted == null) {
                // Ensure has the do payment permission
                if (!WebServiceContext.hasPermission(ServiceOperation.DO_PAYMENT)) {
                    throw new PermissionDeniedException("The service client doesn't have the following permission: " + ServiceOperation.DO_PAYMENT);
                }
                // Check the channel immediately, as needed by SMS controller
                if (fromMember != null && !accessServiceLocal.isChannelEnabledForMember(channelHelper.restricted(), fromMember)) {
                    status = PaymentStatus.INVALID_CHANNEL;
                }
            } else {
                // Enforce the restricted to member parameters
                if (fromSystem) {
                    // Restricted to member can't perform payment from system
                    status = PaymentStatus.FROM_NOT_FOUND;
                } else {
                    if (fromMember == null) {
                        fromMember = restricted;
                    } else if (toMember == null && !toSystem) {
                        toMember = restricted;
                    }
                }
                if (status == null) {
                    // Check make / receive payment permissions
                    if (fromMember.equals(restricted)) {
                        if (!WebServiceContext.hasPermission(ServiceOperation.DO_PAYMENT)) {
                            throw new PermissionDeniedException("The service client doesn't have the following permission: " + ServiceOperation.DO_PAYMENT);
                        }
                    } else {
                        if (!WebServiceContext.hasPermission(ServiceOperation.RECEIVE_PAYMENT)) {
                            throw new PermissionDeniedException("The service client doesn't have the following permission: " + ServiceOperation.RECEIVE_PAYMENT);
                        }
                    }
                    // Ensure that either from or to member is the restricted one
                    if (!fromMember.equals(restricted) && !toMember.equals(restricted)) {
                        status = PaymentStatus.INVALID_PARAMETERS;
                        webServiceHelper.trace(status + ". Reason: Neither the origin nor the destination members are equal to the restricted: " + restricted);
                    }
                }
                if (status == null) {
                    // Enforce the permissions
                    if (restricted.equals(fromMember) && !WebServiceContext.hasPermission(ServiceOperation.DO_PAYMENT)) {
                        throw new PermissionDeniedException("The service client doesn't have the following permission: " + ServiceOperation.DO_PAYMENT);
                    } else if (restricted.equals(toMember) && !WebServiceContext.hasPermission(ServiceOperation.RECEIVE_PAYMENT)) {
                        throw new PermissionDeniedException("The service client doesn't have the following permission: " + ServiceOperation.RECEIVE_PAYMENT);
                    }
                }
            }
        }

        // Ensure both from and to member are present
        if (status == null) {
            if (fromMember == null && !fromSystem) {
                status = PaymentStatus.FROM_NOT_FOUND;
            } else if (toMember == null && !toSystem) {
                status = PaymentStatus.TO_NOT_FOUND;
            } else if (fromMember != null && toMember != null) {
                // Ensure the to member is visible by the from member
                final Collection<MemberGroup> visibleGroups = fromMember.getMemberGroup().getCanViewProfileOfGroups();
                if (CollectionUtils.isEmpty(visibleGroups) || !visibleGroups.contains(toMember.getGroup())) {
                    status = PaymentStatus.TO_NOT_FOUND;
                }
            }
        }

        // Ensure required CF are present ONLY for unrestricted client
        Collection<MemberCustomField> fromMemberfields = null, toMemberfields = null;
        if (status == null) {
            boolean hasFromRequired = CollectionUtils.isNotEmpty(params.getFromMemberFieldsToReturn());
            boolean hasToRequired = CollectionUtils.isNotEmpty(params.getToMemberFieldsToReturn());
            if (restricted != null && (hasFromRequired || hasToRequired) || hasFromRequired && fromSystem || hasToRequired && toSystem) {
                webServiceHelper.trace(restricted != null ? "Restricted web service clients are not allowed to require member custom field values" : "Can't require custom field values for a system payment");
                status = PaymentStatus.INVALID_PARAMETERS;
            }
            if (status == null && hasFromRequired) {
                fromMemberfields = getMemberCustomFields(fromMember, params.getFromMemberFieldsToReturn());
                status = fromMemberfields == null ? PaymentStatus.INVALID_PARAMETERS : null;
            }

            if (status == null && hasToRequired) {
                toMemberfields = getMemberCustomFields(toMember, params.getToMemberFieldsToReturn());
                status = toMemberfields == null ? PaymentStatus.INVALID_PARAMETERS : null;
            }
        }

        if (status == null) {
            // Check the channel
            if (fromMember != null && !accessServiceLocal.isChannelEnabledForMember(channelHelper.restricted(), fromMember)) {
                status = PaymentStatus.INVALID_CHANNEL;
            }
        }
        if (status == null) {
            // Check the credentials
            boolean checkCredentials;
            if (restricted != null) {
                checkCredentials = !fromMember.equals(restricted);
            } else {
                checkCredentials = !fromSystem && WebServiceContext.getClient().isCredentialsRequired();
            }
            if (checkCredentials) {
                try {
                    checkCredentials(fromMember, WebServiceContext.getChannel(), params.getCredentials());
                } catch (final InvalidCredentialsException e) {
                    webServiceHelper.error(e);
                    status = PaymentStatus.INVALID_CREDENTIALS;
                } catch (final BlockedCredentialsException e) {
                    webServiceHelper.error(e);
                    status = PaymentStatus.BLOCKED_CREDENTIALS;
                }
            }
        }

        // No error
        final AccountOwner fromOwner = fromSystem ? SystemAccountOwner.instance() : fromMember;
        final AccountOwner toOwner = toSystem ? SystemAccountOwner.instance() : toMember;
        return new PrepareParametersResult(status, fromOwner, toOwner, fromMemberfields, toMemberfields);
    }

    private Pair<ChargebackStatus, Transfer> preprocessChargeback(final Transfer transfer) {
        ChargebackStatus status = null;
        Transfer chargebackTransfer = null;

        // Check if the transfer can be charged back
        if (!paymentServiceLocal.canChargeback(transfer, false)) {
            if (transfer.getChargedBackBy() != null) {
                chargebackTransfer = transfer.getChargedBackBy();
                status = ChargebackStatus.TRANSFER_ALREADY_CHARGEDBACK;
            } else {
                if (transfer.getStatus() == Payment.Status.PENDING) {
                    final TransferAuthorizationDTO transferAuthorizationDto = new TransferAuthorizationDTO();
                    transferAuthorizationDto.setTransfer(transfer);
                    transferAuthorizationDto.setShowToMember(false);
                    chargebackTransfer = transferAuthorizationServiceLocal.cancel(transferAuthorizationDto);
                    status = ChargebackStatus.SUCCESS;
                } else {
                    status = ChargebackStatus.TRANSFER_CANNOT_BE_CHARGEDBACK;
                }
            }
        }

        return new Pair<ChargebackStatus, Transfer>(status, chargebackTransfer);
    }

    private <V> ChargebackResult reverse(final V transferId, final TransferLoader<V> loader) {
        Exception errorException = null;
        ChargebackStatus status = null;
        Transfer transfer = null;

        try {
            transfer = loader.load(transferId);
            // Ensure the member is the one who received the payment
            final Member member = WebServiceContext.getMember();
            if (member != null && !transfer.getToOwner().equals(member)) {
                throw new EntityNotFoundException();
            } else {
                final Collection<TransferType> possibleTypes = serviceClientServiceLocal.load(WebServiceContext.getClient().getId(), ServiceClient.Relationships.CHARGEBACK_PAYMENT_TYPES).getChargebackPaymentTypes();
                if (!possibleTypes.contains(transfer.getType())) {
                    throw new EntityNotFoundException();
                }
            }
        } catch (final EntityNotFoundException e) {
            errorException = e;
            status = ChargebackStatus.TRANSFER_NOT_FOUND;
        }

        if (status == null) {
            try {
                return doChargeback(transfer);
            } catch (final Exception e) {
                webServiceHelper.error(e);
                return new ChargebackResult(ChargebackStatus.TRANSFER_CANNOT_BE_CHARGEDBACK, null, null);
            }
        } else {
            if (!status.isSuccessful()) {
                if (errorException != null) {
                    webServiceHelper.error(errorException);
                } else {
                    webServiceHelper.error("Chargeback status: " + status);
                }
            }
            return new ChargebackResult(status, null, null);
        }
    }

    private boolean validateTransferType(final DoPaymentDTO dto) {
        final Collection<TransferType> possibleTypes = paymentHelper.listPossibleTypes(dto);
        return possibleTypes != null && possibleTypes.contains(dto.getTransferType());
    }
}
