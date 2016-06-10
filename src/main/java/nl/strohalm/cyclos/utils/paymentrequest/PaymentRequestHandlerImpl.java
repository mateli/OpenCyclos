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
package nl.strohalm.cyclos.utils.paymentrequest;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import nl.strohalm.cyclos.entities.access.Channel;
import nl.strohalm.cyclos.entities.accounts.transactions.PaymentRequestTicket;
import nl.strohalm.cyclos.entities.accounts.transactions.Ticket;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.services.access.ChannelServiceLocal;
import nl.strohalm.cyclos.services.alerts.ErrorLogServiceLocal;
import nl.strohalm.cyclos.services.settings.SettingsServiceLocal;
import nl.strohalm.cyclos.services.transactions.PaymentRequestHandler;
import nl.strohalm.cyclos.services.transactions.TicketServiceLocal;
import nl.strohalm.cyclos.utils.TransactionHelper;
import nl.strohalm.cyclos.utils.WorkerThreads;
import nl.strohalm.cyclos.utils.cache.CacheListener;
import nl.strohalm.cyclos.utils.transaction.CurrentTransactionData;
import nl.strohalm.cyclos.utils.transaction.TransactionCommitListener;
import nl.strohalm.cyclos.webservices.external.ExternalWebServiceHelper;
import nl.strohalm.cyclos.webservices.external.paymentrequest.PaymentRequestWebService;
import nl.strohalm.cyclos.webservices.model.PaymentRequestTicketVO;
import nl.strohalm.cyclos.webservices.utils.TicketHelper;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;

/**
 * Implementation for payment request handler that invokes the PaymentRequestWebService
 * 
 * @author luis
 */
public class PaymentRequestHandlerImpl implements PaymentRequestHandler, BeanFactoryAware, DisposableBean {

    /**
     * A payment request handler which never sends a payment request
     * @author luis
     */
    private static class OfflineHandler implements PaymentRequestWebService {
        @Override
        public boolean requestPayment(final String cyclosId, final PaymentRequestTicketVO ticket) {
            return false;
        }
    }

    private class PaymentRequestSenderThreads extends WorkerThreads<PaymentRequestTicket> {
        protected PaymentRequestSenderThreads(final String name, final int threadCount) {
            super(name, threadCount);
        }

        @Override
        protected void process(final PaymentRequestTicket t) {
            transactionHelper.runInCurrentThread(new TransactionCallbackWithoutResult() {
                @Override
                protected void doInTransactionWithoutResult(final TransactionStatus status) {
                    final PaymentRequestTicket ticket = ticketService.load(t.getTicket(), Ticket.Relationships.FROM, Ticket.Relationships.TO);
                    final LocalSettings localSettings = settingsService.getLocalSettings();
                    final Channel channel = channelService.load(ticket.getToChannel().getId());

                    try {
                        final PaymentRequestWebService ws = proxyFor(channel);
                        final boolean result = ws.requestPayment(localSettings.getCyclosId(), ticketHelper.toVO(ticket, channel.getPrincipalCustomFields()));
                        if (!result) {
                            throw new Exception("The PaymentRequestWebService returned an error status");
                        }
                    } catch (final Exception e) {
                        ticketService.markAsFailedtoSend(ticket);
                        final Map<String, String> params = new HashMap<String, String>();
                        params.put("ticket", ticket.getTicket());
                        params.put("payer username", ticket.getFrom().getUsername());
                        params.put("receiver username", ticket.getTo().getUsername());
                        errorLogService.insert(e, channel.getPaymentRequestWebServiceUrl(), params);
                    }
                }
            });
        }
    }

    private TicketHelper                                ticketHelper;
    private SettingsServiceLocal                        settingsService;
    private TicketServiceLocal                          ticketService;
    private ErrorLogServiceLocal                        errorLogService;
    private ChannelServiceLocal                         channelService;
    private BeanFactory                                 beanFactory;
    private boolean                                     initialized;
    private PaymentRequestSenderThreads                 senderThreads;
    private int                                         maxThreads    = 5;
    private TransactionHelper                           transactionHelper;

    /** This map is cached, but we do have a {@link CacheListener} to make it consistent in a cluster */
    private final Map<String, PaymentRequestWebService> cachedProxies = new ConcurrentHashMap<String, PaymentRequestWebService>();

    @Override
    public void destroy() throws Exception {
        if (senderThreads != null) {
            senderThreads.interrupt();
            senderThreads = null;
        }
    }

    @Override
    public void invalidateCache() {
        cachedProxies.clear();
    }

    @Override
    public void sendRequest(final PaymentRequestTicket ticket) {
        maybeInitialize();

        if (senderThreads == null) {
            return;
        }
        CurrentTransactionData.addTransactionCommitListener(new TransactionCommitListener() {
            @Override
            public void onTransactionCommit() {
                senderThreads.enqueue(ticket);
            }
        });
    }

    @Override
    public void setBeanFactory(final BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    public void setChannelServiceLocal(final ChannelServiceLocal channelService) {
        this.channelService = channelService;
    }

    public void setMaxThreads(final int maxThreads) {
        this.maxThreads = maxThreads;
    }

    private synchronized void maybeInitialize() {
        // As the standard setter injection was causing problems with other beans, for recursive injection,
        // the setter injection is no longer used here.
        if (!initialized) {
            settingsService = beanFactory.getBean(SettingsServiceLocal.class);
            ticketHelper = beanFactory.getBean(TicketHelper.class);
            errorLogService = beanFactory.getBean(ErrorLogServiceLocal.class);
            ticketService = beanFactory.getBean(TicketServiceLocal.class);
            transactionHelper = beanFactory.getBean(TransactionHelper.class);

            senderThreads = new PaymentRequestSenderThreads("Payment request sender for " + settingsService.getLocalSettings().getApplicationName(), maxThreads);

            initialized = true;
        }
    }

    private PaymentRequestWebService proxyFor(final Channel channel) throws IOException {
        final String url = StringUtils.trimToEmpty(channel.getPaymentRequestWebServiceUrl());
        PaymentRequestWebService proxy = cachedProxies.get(url);
        if (proxy == null) {
            // Create the proxy
            if (url.isEmpty()) {
                // No payment request URL. Assume the system is offline
                proxy = new OfflineHandler();
            } else {
                // Create the proxy
                proxy = ExternalWebServiceHelper.proxyFor(PaymentRequestWebService.class, url);
            }
            // Store on cache
            cachedProxies.put(url, proxy);
        }
        return proxy;
    }
}
