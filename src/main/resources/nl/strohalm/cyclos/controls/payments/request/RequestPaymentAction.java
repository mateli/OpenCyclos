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
package nl.strohalm.cyclos.controls.payments.request;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseFormAction;
import nl.strohalm.cyclos.entities.access.Channel;
import nl.strohalm.cyclos.entities.accounts.Currency;
import nl.strohalm.cyclos.entities.accounts.transactions.PaymentRequestTicket;
import nl.strohalm.cyclos.entities.accounts.transactions.TicketQuery;
import nl.strohalm.cyclos.entities.exceptions.UnexpectedEntityException;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.entities.settings.events.LocalSettingsChangeListener;
import nl.strohalm.cyclos.entities.settings.events.LocalSettingsEvent;
import nl.strohalm.cyclos.services.access.ChannelService;
import nl.strohalm.cyclos.services.accounts.CurrencyService;
import nl.strohalm.cyclos.services.transactions.TicketService;
import nl.strohalm.cyclos.services.transactions.exceptions.AuthorizedPaymentInPastException;
import nl.strohalm.cyclos.services.transactions.exceptions.CreditsException;
import nl.strohalm.cyclos.services.transactions.exceptions.InvalidChannelException;
import nl.strohalm.cyclos.utils.RequestHelper;
import nl.strohalm.cyclos.utils.binding.BeanBinder;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.binding.PropertyBinder;
import nl.strohalm.cyclos.utils.validation.ValidationException;

/**
 * Action used by a member to request a payment to be confirmed by other channel
 * @author luis
 */
public class RequestPaymentAction extends BaseFormAction implements LocalSettingsChangeListener {

    private ChannelService                   channelService;
    private CurrencyService                  currencyService;
    private TicketService                    ticketService;
    private DataBinder<PaymentRequestTicket> dataBinder;
    private ReadWriteLock                    lock = new ReentrantReadWriteLock(true);

    @Override
    public void onLocalSettingsUpdate(final LocalSettingsEvent event) {
        try {
            lock.writeLock().lock();
            dataBinder = null;
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Inject
    public void setChannelService(final ChannelService channelService) {
        this.channelService = channelService;
    }

    @Inject
    public void setCurrencyService(final CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @Inject
    public void setTicketService(final TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @Override
    protected void formAction(final ActionContext context) throws Exception {
        PaymentRequestTicket ticket = resolveTicket(context);
        try {
            ticket = ticketService.generate(ticket);
            context.sendMessage("paymentRequest.sent", ticket.getFrom().getName());
        } catch (final CreditsException e) {
            throw new ValidationException(actionHelper.resolveErrorKey(e), actionHelper.resolveParameters(e));
        } catch (final InvalidChannelException e) {
            throw new ValidationException("paymentRequest.error.invalidChannel", e.getUsername(), e.getChannelName());
        } catch (final UnexpectedEntityException e) {
            throw new ValidationException("payment.error.invalidTransferType");
        } catch (final AuthorizedPaymentInPastException e) {
            throw new ValidationException("payment.error.authorizedInPast");
        }
    }

    @Override
    protected void prepareForm(final ActionContext context) throws Exception {
        final HttpServletRequest request = context.getRequest();
        final Member member = (Member) context.getAccountOwner();
        request.setAttribute("toMember", member);
        final MemberGroup group = groupService.load(member.getMemberGroup().getId(), MemberGroup.Relationships.REQUEST_PAYMENT_BY_CHANNELS);

        // Get the possible currencies
        final List<Currency> currencies = currencyService.listByMemberGroup(group);
        if (currencies.isEmpty()) {
            throw new ValidationException("payment.error.noTransferType");
        }
        if (currencies.size() == 1) {
            request.setAttribute("singleCurrency", currencies.iterator().next());
        }
        request.setAttribute("currencies", currencies);

        // Get the possible channels
        final Collection<Channel> channels = new ArrayList<Channel>(group.getRequestPaymentByChannels());
        for (final Iterator<Channel> it = channels.iterator(); it.hasNext();) {
            if (!it.next().isPaymentRequestSupported()) {
                it.remove();
            }
        }
        if (channels.isEmpty()) {
            throw new ValidationException("paymentRequest.error.noChannels");
        }
        if (channels.size() == 1) {
            request.setAttribute("singleChannel", channels.iterator().next());
        }
        request.setAttribute("channels", channels);

        RequestHelper.storeEnum(request, TicketQuery.GroupedStatus.class, "status");
    }

    @Override
    protected void validateForm(final ActionContext context) {
        final PaymentRequestTicket ticket = resolveTicket(context);
        ticketService.validate(ticket);
    }

    private DataBinder<PaymentRequestTicket> getDataBinder() {
        try {
            lock.readLock().lock();
            if (dataBinder == null) {
                final LocalSettings localSettings = settingsService.getLocalSettings();
                final BeanBinder<PaymentRequestTicket> binder = BeanBinder.instance(PaymentRequestTicket.class);
                binder.registerBinder("from", PropertyBinder.instance(Member.class, "from"));
                binder.registerBinder("amount", PropertyBinder.instance(BigDecimal.class, "amount", localSettings.getNumberConverter()));
                binder.registerBinder("currency", PropertyBinder.instance(Currency.class, "currency"));
                binder.registerBinder("toChannel", PropertyBinder.instance(Channel.class, "toChannel"));
                binder.registerBinder("description", PropertyBinder.instance(String.class, "description"));
                dataBinder = binder;
            }
            return dataBinder;
        } finally {
            lock.readLock().unlock();
        }
    }

    private PaymentRequestTicket resolveTicket(final ActionContext context) {
        final RequestPaymentForm form = context.getForm();
        final PaymentRequestTicket ticket = getDataBinder().readFromString(form.getTicket());
        ticket.setTo((Member) context.getAccountOwner());
        ticket.setFromChannel(channelService.loadByInternalName(Channel.WEB));
        return ticket;
    }
}
