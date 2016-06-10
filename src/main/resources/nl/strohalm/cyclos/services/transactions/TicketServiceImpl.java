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
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Callable;

import nl.strohalm.cyclos.access.MemberPermission;
import nl.strohalm.cyclos.dao.accounts.transactions.TicketDAO;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.access.Channel;
import nl.strohalm.cyclos.entities.accounts.AccountType;
import nl.strohalm.cyclos.entities.accounts.MemberAccountType;
import nl.strohalm.cyclos.entities.accounts.transactions.PaymentRequestTicket;
import nl.strohalm.cyclos.entities.accounts.transactions.Ticket;
import nl.strohalm.cyclos.entities.accounts.transactions.TicketQuery;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferType;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferTypeQuery;
import nl.strohalm.cyclos.entities.accounts.transactions.WebShopTicket;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.entities.exceptions.UnexpectedEntityException;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.exceptions.PermissionDeniedException;
import nl.strohalm.cyclos.services.access.AccessServiceLocal;
import nl.strohalm.cyclos.services.access.ChannelServiceLocal;
import nl.strohalm.cyclos.services.accounts.AccountTypeServiceLocal;
import nl.strohalm.cyclos.services.fetch.FetchServiceLocal;
import nl.strohalm.cyclos.services.permissions.PermissionServiceLocal;
import nl.strohalm.cyclos.services.transactions.exceptions.AuthorizedPaymentInPastException;
import nl.strohalm.cyclos.services.transactions.exceptions.InvalidChannelException;
import nl.strohalm.cyclos.services.transactions.exceptions.MaxAmountPerDayExceededException;
import nl.strohalm.cyclos.services.transactions.exceptions.NotEnoughCreditsException;
import nl.strohalm.cyclos.services.transactions.exceptions.UpperCreditLimitReachedException;
import nl.strohalm.cyclos.services.transfertypes.TransferTypeServiceLocal;
import nl.strohalm.cyclos.utils.access.LoggedUser;
import nl.strohalm.cyclos.utils.notifications.MemberNotificationHandler;
import nl.strohalm.cyclos.utils.query.QueryParameters.ResultType;
import nl.strohalm.cyclos.utils.validation.ValidationException;
import nl.strohalm.cyclos.utils.validation.Validator;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;

/**
 * Implementation for ticket service
 * @author luis
 */
public class TicketServiceImpl implements TicketServiceLocal {

    private static final int          TICKET_SIZE           = 32;
    private static final String       POSSIBLE_TICKET_CHARS = "012345679ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private TicketDAO                 ticketDao;
    private FetchServiceLocal         fetchService;
    private PaymentServiceLocal       paymentService;
    private PermissionServiceLocal    permissionService;
    private AccountTypeServiceLocal   accountTypeService;
    private TransferTypeServiceLocal  transferTypeService;
    private PaymentRequestHandler     paymentRequestHandler;
    private AccessServiceLocal        accessService;
    private MemberNotificationHandler memberNotificationHandler;
    private ChannelServiceLocal       channelService;

    @Override
    public WebShopTicket cancelWebShopTicket(final long ticketId, final String clientIP) {
        final Ticket loaded = ticketDao.load(ticketId);

        if (!(loaded instanceof WebShopTicket)) {
            throw new EntityNotFoundException(WebShopTicket.class);
        }
        WebShopTicket ticket = (WebShopTicket) loaded;
        if (!ticket.getClientAddress().equals(clientIP)) {
            throw new PermissionDeniedException();
        }
        ticket = fetchService.fetch(ticket);
        ticket.setStatus(Ticket.Status.CANCELLED);
        return ticketDao.update(ticket);
    }

    @Override
    public PaymentRequestTicket expirePaymentRequestTicket(PaymentRequestTicket ticket) {
        ticket = fetchService.fetch(ticket);
        ticket.setStatus(Ticket.Status.EXPIRED);
        ticket = ticketDao.update(ticket);
        memberNotificationHandler.externalChannelPaymentRequestExpired(ticket);
        return ticket;
    }

    @Override
    public PaymentRequestTicket generate(final PaymentRequestTicket ticket) throws InvalidChannelException, NotEnoughCreditsException, MaxAmountPerDayExceededException, UnexpectedEntityException, UpperCreditLimitReachedException, AuthorizedPaymentInPastException {
        validate(ticket);

        // Get and validate the destination member
        final Member to = fetchService.fetch(ticket.getTo(), Element.Relationships.GROUP, Member.Relationships.CHANNELS);
        final Channel toChannel = channelService.load(ticket.getToChannel().getId());

        // Validate that the from member participates on the channel
        Member fromMember = fetchService.fetch(ticket.getFrom());
        if (!accessService.isChannelEnabledForMember(toChannel.getInternalName(), fromMember)) {
            throw new InvalidChannelException(fromMember.getUsername(), toChannel.getName());
        }

        // Set the transfer type when none passed
        if (ticket.getTransferType() == null) {
            final TransferType transferType = defaultTransferTypeFor(ticket);
            if (transferType == null) {
                throw new ValidationException("payment.error.noTransferType");
            } else {
                ticket.setTransferType(transferType);
            }
        }

        // Set the transfer type description when the ticket description is empty
        if (StringUtils.isEmpty(ticket.getDescription())) {
            ticket.setDescription(ticket.getTransferType().getDescription());
        }

        // Complete the data
        ticket.setCreationDate(Calendar.getInstance());
        ticket.setStatus(Ticket.Status.PENDING);
        ticket.setTicket(generateTicket());
        if (ticket.getCurrency() == null && ticket.getAmount() != null && ticket.getAmount().compareTo(BigDecimal.ZERO) > 0) {
            // When there is an amount but no currency, get the default currency
            final MemberAccountType defaultAccount = accountTypeService.getDefault(to.getMemberGroup(), AccountType.Relationships.CURRENCY);
            ticket.setCurrency(defaultAccount.getCurrency());
        }
        final PaymentRequestTicket persistentTicket = ticketDao.insert(ticket);

        // We must simulate the payment running as the payer before sending the request
        LoggedUser.runAs(fromMember.getUser(), new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                simulate(persistentTicket);
                return null;
            }
        });
        // If the payment was successful, send the payment request
        paymentRequestHandler.sendRequest(persistentTicket);

        return persistentTicket;
    }

    @Override
    public WebShopTicket generate(final WebShopTicket ticket) {
        validate(ticket);
        final Member to = fetchService.fetch(ticket.getTo(), Element.Relationships.GROUP);

        if (!permissionService.hasPermission(to.getGroup(), MemberPermission.PAYMENTS_TICKET)) {
            throw new PermissionDeniedException();
        }

        // Complete the data
        ticket.setCreationDate(Calendar.getInstance());
        ticket.setStatus(Ticket.Status.PENDING);
        ticket.setTicket(generateTicket());
        if (ticket.getCurrency() == null && ticket.getAmount() != null && ticket.getAmount().compareTo(BigDecimal.ZERO) > 0) {
            // When there is an amount but no currency, get the default currency
            final MemberAccountType defaultAccount = accountTypeService.getDefault(to.getMemberGroup(), AccountType.Relationships.CURRENCY);
            ticket.setCurrency(defaultAccount.getCurrency());
        }
        return ticketDao.insert(ticket);
    }

    public TicketDAO getTicketDao() {
        return ticketDao;
    }

    @Override
    public <T extends Ticket> T load(final String ticket, final Relationship... fetch) {
        return ticketDao.<T> load(ticket, fetch);
    }

    @Override
    public PaymentRequestTicket loadPendingPaymentRequest(final String ticket, final Relationship... fetch) {
        final Ticket loaded = ticketDao.load(ticket, fetch);

        if (loaded.getStatus() != Ticket.Status.PENDING || !(loaded instanceof PaymentRequestTicket)) {
            throw new EntityNotFoundException(PaymentRequestTicket.class);
        }
        return (PaymentRequestTicket) loaded;
    }

    @Override
    public WebShopTicket loadPendingWebShopTicket(final String ticket, final String clientIP, final Relationship... fetch) {
        final Ticket loaded = ticketDao.load(ticket, fetch);

        if (loaded.getStatus() != Ticket.Status.PENDING || !(loaded instanceof WebShopTicket)) {
            throw new EntityNotFoundException(WebShopTicket.class);
        }
        final WebShopTicket webShopTicket = (WebShopTicket) loaded;
        if (!webShopTicket.getClientAddress().equals(clientIP)) {
            throw new PermissionDeniedException();
        }
        return webShopTicket;
    }

    @Override
    public PaymentRequestTicket markAsFailedtoSend(PaymentRequestTicket ticket) {
        ticket = fetchService.fetch(ticket);
        ticket.setStatus(Ticket.Status.FAILED);
        return ticketDao.update(ticket);
    }

    @Override
    public int processExpiredTickets(final Calendar time) {
        // Get tickets that expired before the last hour
        final Calendar createdBefore = (Calendar) time.clone();
        createdBefore.add(Calendar.HOUR_OF_DAY, -1);
        // Search tickets
        final TicketQuery query = new TicketQuery();
        query.setResultType(ResultType.ITERATOR);
        query.setStatus(Ticket.Status.PENDING);
        query.setCreatedBefore(createdBefore);
        int count = 0;
        // Expire each one
        final List<? extends Ticket> expired = ticketDao.search(query);
        for (final Ticket ticket : expired) {
            ticket.setStatus(Ticket.Status.EXPIRED);
            ticketDao.update(ticket);
            count++;
        }
        return count;
    }

    @Override
    public List<? extends Ticket> search(final TicketQuery query) {
        return ticketDao.search(query);
    }

    public void setAccessServiceLocal(final AccessServiceLocal accessService) {
        this.accessService = accessService;
    }

    public void setAccountTypeServiceLocal(final AccountTypeServiceLocal accountTypeService) {
        this.accountTypeService = accountTypeService;
    }

    public void setChannelServiceLocal(final ChannelServiceLocal channelService) {
        this.channelService = channelService;
    }

    public void setFetchServiceLocal(final FetchServiceLocal fetchService) {
        this.fetchService = fetchService;
    }

    public void setMemberNotificationHandler(final MemberNotificationHandler memberNotificationHandler) {
        this.memberNotificationHandler = memberNotificationHandler;
    }

    public void setPaymentRequestHandler(final PaymentRequestHandler paymentRequestHandler) {
        this.paymentRequestHandler = paymentRequestHandler;
    }

    public void setPaymentServiceLocal(final PaymentServiceLocal paymentService) {
        this.paymentService = paymentService;
    }

    public void setPermissionServiceLocal(final PermissionServiceLocal permissionService) {
        this.permissionService = permissionService;
    }

    public void setTicketDao(final TicketDAO ticketDao) {
        this.ticketDao = ticketDao;
    }

    public void setTransferTypeServiceLocal(final TransferTypeServiceLocal transferTypeService) {
        this.transferTypeService = transferTypeService;
    }

    @Override
    public void validate(final Ticket ticket) {
        if (ticket instanceof PaymentRequestTicket) {
            getPaymentRequestValidator().validate(ticket);
        } else {
            getWebShopValidator().validate(ticket);
        }
    }

    /**
     * Returns the transfer type that would be used for the given ticket if none were specified
     */
    private TransferType defaultTransferTypeFor(final PaymentRequestTicket ticket) {
        final Channel channel = fetchService.fetch(ticket.getToChannel());
        final Member from = fetchService.fetch(ticket.getFrom(), Element.Relationships.GROUP);

        // Find the first transfer type matching the payment
        final TransferTypeQuery ttQuery = new TransferTypeQuery();
        ttQuery.setUniqueResult();
        ttQuery.setUsePriority(true);
        ttQuery.setContext(TransactionContext.PAYMENT);
        ttQuery.setFromOwner(from);
        ttQuery.setToOwner(ticket.getTo());
        ttQuery.setCurrency(ticket.getCurrency());
        ttQuery.setChannel(channel.getInternalName());
        ttQuery.setGroup(from.getGroup());
        final List<TransferType> transferTypes = transferTypeService.search(ttQuery);
        if (transferTypes.isEmpty()) {
            return null;
        } else {
            return transferTypes.iterator().next();
        }
    }

    private String generateTicket() {
        String ticket = null;
        while (ticket == null || ticketDao.exists(ticket)) {
            ticket = RandomStringUtils.random(TICKET_SIZE, POSSIBLE_TICKET_CHARS);
        }
        return ticket;
    }

    private Validator getPaymentRequestValidator() {
        final Validator paymentRequestValidator = new Validator("transfer");
        paymentRequestValidator.property("amount").positiveNonZero();
        paymentRequestValidator.property("from").required();
        paymentRequestValidator.property("to").required();
        paymentRequestValidator.property("description").maxLength(1000);
        paymentRequestValidator.property("fromChannel").required();
        paymentRequestValidator.property("toChannel").required();
        return paymentRequestValidator;
    }

    private Validator getWebShopValidator() {
        final Validator webshopValidator = new Validator("transfer");
        webshopValidator.property("amount").positiveNonZero();
        webshopValidator.property("to").required();
        webshopValidator.property("description").maxLength(1000);
        webshopValidator.property("returnUrl").required();
        webshopValidator.property("clientAddress").required().inetAddr();
        webshopValidator.property("memberAddress").required().inetAddr();
        return webshopValidator;
    }

    private void simulate(final PaymentRequestTicket ticket) {
        final DoPaymentDTO dto = new DoPaymentDTO();
        dto.setContext(TransactionContext.PAYMENT);
        dto.setChannel(ticket.getToChannel().getInternalName());
        dto.setFrom(ticket.getFrom());
        dto.setTo(ticket.getTo());
        dto.setAmount(ticket.getAmount());
        dto.setCurrency(ticket.getCurrency());
        dto.setTransferType(ticket.getTransferType());
        dto.setDescription(ticket.getDescription());
        paymentService.simulatePayment(dto);
    }
}
