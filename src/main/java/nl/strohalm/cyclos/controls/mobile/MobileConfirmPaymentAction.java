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
package nl.strohalm.cyclos.controls.mobile;

import java.math.BigDecimal;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.mobile.exceptions.MobileException;
import nl.strohalm.cyclos.entities.access.Channel;
import nl.strohalm.cyclos.entities.access.Channel.Credentials;
import nl.strohalm.cyclos.entities.accounts.fees.transaction.TransactionFee;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferType;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.services.access.AccessService;
import nl.strohalm.cyclos.services.access.ChannelService;
import nl.strohalm.cyclos.services.access.exceptions.BlockedCredentialsException;
import nl.strohalm.cyclos.services.access.exceptions.InvalidCredentialsException;
import nl.strohalm.cyclos.services.transactions.DoPaymentDTO;
import nl.strohalm.cyclos.services.transactions.PaymentService;
import nl.strohalm.cyclos.services.transactions.exceptions.CreditsException;
import nl.strohalm.cyclos.services.transfertypes.TransactionFeePreviewDTO;
import nl.strohalm.cyclos.services.transfertypes.TransactionFeeService;
import nl.strohalm.cyclos.services.transfertypes.TransferTypeService;
import nl.strohalm.cyclos.utils.RequestHelper;
import nl.strohalm.cyclos.utils.conversion.UnitsConverter;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForward;

/**
 * Action used to confirm the payment
 * @author luis
 */
public class MobileConfirmPaymentAction extends MobileBaseAction {

    private TransferTypeService   transferTypeService;
    private TransactionFeeService transactionFeeService;
    private PaymentService        paymentService;
    private AccessService         accessService;
    private ChannelService        channelService;

    public PaymentService getPaymentService() {
        return paymentService;
    }

    public TransactionFeeService getTransactionFeeService() {
        return transactionFeeService;
    }

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
    public void setTransactionFeeService(final TransactionFeeService transactionFeeService) {
        this.transactionFeeService = transactionFeeService;
    }

    @Inject
    public void setTransferTypeService(final TransferTypeService transferTypeService) {
        this.transferTypeService = transferTypeService;
    }

    @Override
    protected ActionForward executeAction(final MobileActionContext context) throws Exception {
        final HttpServletRequest request = context.getRequest();
        final HttpSession session = request.getSession();
        final DoPaymentDTO payment = validatePayment(session);
        final Channel channel = channelService.loadByInternalName(MobileHelper.mobileChannel(request));
        final boolean requestTransactionPassword = channel.getCredentials() == Credentials.DEFAULT && context.isTransactionPasswordEnabled(context.getCurrentAccountType());

        if (RequestHelper.isGet(request)) {
            // Form preparation - retrieve the confirmation message

            final UnitsConverter unitsConverter = getUnitsConverter(context);
            final TransferType transferType = payment.getTransferType() == null ? null : transferTypeService.load(payment.getTransferType().getId());

            final String amount = unitsConverter.toString(payment.getAmount());
            final Member toMember = ((Member) payment.getTo());
            final String to = toMember.getName();
            final StringBuilder messages = new StringBuilder();

            // Check if fees will be applied
            final TransactionFeePreviewDTO preview = transactionFeeService.preview(context.getAccountOwner(), toMember, transferType, payment.getAmount());
            if (preview != null && preview.getFees() != null && !preview.getFees().isEmpty()) {
                messages.append("\n\n").append(context.message("payment.confirmation.fees"));
                for (final Map.Entry<TransactionFee, BigDecimal> entry : preview.getFees().entrySet()) {
                    messages.append('\n').append(entry.getKey().getName()).append(": ").append(unitsConverter.toString(entry.getValue()));
                }
            }

            // Check if the transfer type has a confirmation message
            final String ttConfirmation = transferType.getConfirmationMessage();
            if (StringUtils.isNotEmpty(ttConfirmation)) {
                messages.append("\n\n").append(ttConfirmation);
            }

            // Retrieve the message
            final String message = context.message("mobile.payment.confirmation", amount, to, messages.toString());
            request.setAttribute("confirmationMessage", message);

            // Check if the transaction password will be requested
            request.setAttribute("requestTransactionPassword", requestTransactionPassword);

            return context.getInputForward();
        } else {
            if (requestTransactionPassword) {
                // Check the transaction password
                final String transactionPassword = validateTransactionPassword(context);
                try {
                    accessService.checkTransactionPassword(transactionPassword);
                } catch (final InvalidCredentialsException e) {
                    throw new MobileException("transactionPassword.error.invalid");
                } catch (final BlockedCredentialsException e) {
                    throw new MobileException("transactionPassword.error.blockedByTrials");
                }
            }
            // Perform the payment
            try {
                paymentService.doPayment(payment);
                return context.getSuccessForward();
            } catch (final CreditsException e) {
                throw new MobileException(actionHelper.resolveErrorKey(e), actionHelper.resolveParameters(e));
            }
        }
    }

    /**
     * Validate if the paymentDTO was correctly generated
     */
    private DoPaymentDTO validatePayment(final HttpSession session) {
        final DoPaymentDTO payment = (DoPaymentDTO) session.getAttribute("mobileDoPaymentDTO");
        if (payment == null || payment.getAmount().compareTo(new BigDecimal(0)) != 1 || (!(payment.getTo() instanceof Member)) || payment.getTransferType() == null) {
            throw new MobileException("mobile.payment.error.invalid");
        }
        return payment;
    }

    /**
     * Validate and retrieve the transaction password, applying the MD5 hash
     */
    private String validateTransactionPassword(final MobileActionContext context) {

        context.validateTransactionPassword();

        final MobileConfirmPaymentForm form = context.getForm();
        final String transactionPassword = StringUtils.trimToNull(form.getTransactionPassword());
        if (transactionPassword == null) {
            throw new MobileException("errors.required", context.message("mobile.payment.transactionPassword"));
        }
        return transactionPassword.toUpperCase();
    }

}
