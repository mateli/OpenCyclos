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
package nl.strohalm.cyclos.controls.webshop;

import java.util.concurrent.Callable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BasePublicFormAction;
import nl.strohalm.cyclos.entities.access.Channel;
import nl.strohalm.cyclos.entities.access.Channel.Credentials;
import nl.strohalm.cyclos.entities.accounts.transactions.Payment;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferType;
import nl.strohalm.cyclos.entities.accounts.transactions.WebShopTicket;
import nl.strohalm.cyclos.entities.exceptions.UnexpectedEntityException;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.services.access.AccessService;
import nl.strohalm.cyclos.services.access.ChannelService;
import nl.strohalm.cyclos.services.access.exceptions.BlockedCredentialsException;
import nl.strohalm.cyclos.services.access.exceptions.InvalidCredentialsException;
import nl.strohalm.cyclos.services.elements.ElementService;
import nl.strohalm.cyclos.services.transactions.DoPaymentDTO;
import nl.strohalm.cyclos.services.transactions.PaymentService;
import nl.strohalm.cyclos.services.transactions.TicketService;
import nl.strohalm.cyclos.services.transactions.exceptions.CreditsException;
import nl.strohalm.cyclos.services.transfertypes.TransactionFeePreviewDTO;
import nl.strohalm.cyclos.services.transfertypes.TransactionFeeService;
import nl.strohalm.cyclos.services.transfertypes.TransferTypeService;
import nl.strohalm.cyclos.utils.MessageHelper;
import nl.strohalm.cyclos.utils.access.LoggedUser;
import nl.strohalm.cyclos.utils.validation.RequiredError;
import nl.strohalm.cyclos.utils.validation.ValidationException;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action used to confirm a
 * 
 * @author luis
 */
public class ConfirmWebShopPaymentAction extends BasePublicFormAction {

    public static class ShouldValidateTPParameter {
        public final ActionMapping       mapping;
        public final ActionForm          actionForm;
        public final HttpServletRequest  request;
        public final HttpServletResponse response;
        public final DoPaymentDTO        payment;
        public final ChannelService      channelService;
        public final ElementService      elementService;
        public final TransferTypeService transferTypeService;
        public final MessageHelper       messageHelper;

        public ShouldValidateTPParameter(final ActionMapping mapping, final ActionForm actionForm, final HttpServletRequest request, final HttpServletResponse response, final DoPaymentDTO payment, final ChannelService channelService, final ElementService elementService, final TransferTypeService transferTypeService, final MessageHelper messageHelper) {
            this.mapping = mapping;
            this.actionForm = actionForm;
            this.request = request;
            this.response = response;
            this.payment = payment;
            this.channelService = channelService;
            this.elementService = elementService;
            this.transferTypeService = transferTypeService;
            this.messageHelper = messageHelper;
        }
    }

    private static boolean doShouldValidateTransactionPassword(final ShouldValidateTPParameter parameter) {
        final Channel channel = parameter.channelService.loadByInternalName(Channel.WEBSHOP);
        // Transaction password is only validated on default credentials
        if (channel.getCredentials() != Credentials.DEFAULT) {
            return false;
        }
        final Member member = parameter.elementService.load(((Member) parameter.payment.getFrom()).getId(), Element.Relationships.USER);
        final ActionContext context = new ActionContext(parameter.mapping, parameter.actionForm, parameter.request, parameter.response, member.getUser(), parameter.messageHelper);
        final TransferType transferType = parameter.transferTypeService.load(parameter.payment.getTransferType().getId(), TransferType.Relationships.FROM);
        return context.isTransactionPasswordEnabled(transferType.getFrom());
    }

    static boolean shouldValidateTransactionPassword(final ShouldValidateTPParameter parameter) {
        return LoggedUser.runAsSystem(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return doShouldValidateTransactionPassword(parameter);
            }
        });
    }

    private AccessService         accessService;
    private ChannelService        channelService;
    private PaymentService        paymentService;
    private TransactionFeeService transactionFeeService;
    private TransferTypeService   transferTypeService;

    private TicketService         ticketService;

    @Inject
    public void setAccessService(final AccessService accessService) {
        this.accessService = accessService;
    }

    @Inject
    public void setChannelService(final ChannelService channelService) {
        this.channelService = channelService;
    }

    @Inject
    public void setPaymentService(final PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @Inject
    public void setTicketService(final TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @Inject
    public void setTransactionFeeService(final TransactionFeeService transactionFeeService) {
        this.transactionFeeService = transactionFeeService;
    }

    @Inject
    public void setTransferTypeService(final TransferTypeService transferTypeService) {
        this.transferTypeService = transferTypeService;
    }

    @Override
    protected ActionForward handleSubmit(final ActionMapping mapping, final ActionForm actionForm, final HttpServletRequest request, final HttpServletResponse response) {
        final ConfirmWebShopPaymentForm form = (ConfirmWebShopPaymentForm) actionForm;
        final DoPaymentDTO paymentDTO = resolvePayment(request);
        final Member from = (Member) paymentDTO.getFrom();

        final HttpSession session = request.getSession();
        session.setAttribute("errorReturnTo", "/do/webshop/confirmPayment");
        // We must fool the model layer, pretending that there is a logged user
        return LoggedUser.runAs(from.getUser(), request.getRemoteAddr(), new Callable<ActionForward>() {
            @Override
            public ActionForward call() throws Exception {
                try {
                    // Check for transaction password
                    if (shouldValidateTransactionPassword(new ShouldValidateTPParameter(mapping, actionForm, request, response, paymentDTO, channelService, elementService, transferTypeService, messageHelper))) {
                        accessService.checkTransactionPassword(form.getTransactionPassword());
                    }
                } catch (final InvalidCredentialsException e) {
                    throw new ValidationException("transactionPassword.error.invalid");
                } catch (final BlockedCredentialsException e) {
                    cancelTicket(request, paymentDTO);
                    throw new ValidationException("transactionPassword.error.blockedByTrials");
                }

                // Perform the actual payment
                Payment payment;
                try {
                    payment = paymentService.doPayment(paymentDTO);

                    // Store the payment on the session
                    WebShopHelper.setPerformedPayment(session, payment);

                    return mapping.findForward("success");
                } catch (final CreditsException e) {
                    cancelTicket(request, paymentDTO);
                    throw new ValidationException(actionHelper.resolveErrorKey(e), actionHelper.resolveParameters(e));
                } catch (final UnexpectedEntityException e) {
                    cancelTicket(request, paymentDTO);
                    throw new ValidationException("payment.error.invalidTransferType");
                }
            }
        });
    }

    @Override
    protected void prepareForm(final ActionMapping mapping, final ActionForm actionForm, final HttpServletRequest request, final HttpServletResponse response) {
        final DoPaymentDTO payment = resolvePayment(request);
        request.setAttribute("payment", payment);
        request.setAttribute("requestTransactionPassword", shouldValidateTransactionPassword(new ShouldValidateTPParameter(mapping, actionForm, request, response, payment, channelService, elementService, transferTypeService, messageHelper)));

        TransactionFeePreviewDTO fees;
        fees = LoggedUser.runAsSystem(new Callable<TransactionFeePreviewDTO>() {
            @Override
            public TransactionFeePreviewDTO call() throws Exception {
                return transactionFeeService.preview(payment.getFrom(), payment.getTo(), payment.getTransferType(), payment.getAmount());
            }
        });
        request.setAttribute("finalAmount", fees.getFinalAmount());
        request.setAttribute("fees", fees.getFees());
    }

    @Override
    protected void validateForm(final ActionMapping mapping, final ActionForm actionForm, final HttpServletRequest request, final HttpServletResponse response) throws ValidationException {
        final DoPaymentDTO payment = resolvePayment(request);
        if (shouldValidateTransactionPassword(new ShouldValidateTPParameter(mapping, actionForm, request, response, payment, channelService, elementService, transferTypeService, messageHelper))) {
            final ConfirmWebShopPaymentForm form = (ConfirmWebShopPaymentForm) actionForm;
            if (StringUtils.isEmpty(form.getTransactionPassword())) {
                throw new ValidationException("_transactionPassword", "login.transactionPassword", new RequiredError());
            }
        }
    }

    private void cancelTicket(final HttpServletRequest request, final DoPaymentDTO payment) {
        final WebShopTicket ticket = ticketService.cancelWebShopTicket(payment.getTicket().getId(), request.getRemoteAddr());

        final HttpSession session = request.getSession();
        session.removeAttribute("forceBack");
        session.setAttribute("errorReturnTo", ticket.getReturnUrl());
    }

    private DoPaymentDTO resolvePayment(final HttpServletRequest request) {
        final DoPaymentDTO payment = WebShopHelper.getUpdatedPayment(request.getSession());
        if (payment == null) {
            throw new ValidationException();
        }
        if (StringUtils.isEmpty(payment.getDescription())) {
            payment.setDescription(payment.getTransferType().getDescription());
        }
        return payment;
    }
}
