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
package nl.strohalm.cyclos.controls.posweb;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.Callable;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.access.OperatorPermission;
import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.payments.SchedulingType;
import nl.strohalm.cyclos.entities.access.Channel;
import nl.strohalm.cyclos.entities.access.Channel.Credentials;
import nl.strohalm.cyclos.entities.access.Channel.Principal;
import nl.strohalm.cyclos.entities.access.MemberUser;
import nl.strohalm.cyclos.entities.access.PrincipalType;
import nl.strohalm.cyclos.entities.accounts.AccountOwner;
import nl.strohalm.cyclos.entities.accounts.transactions.Payment;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.services.access.ChannelService;
import nl.strohalm.cyclos.services.access.exceptions.BlockedCredentialsException;
import nl.strohalm.cyclos.services.access.exceptions.InvalidCardException;
import nl.strohalm.cyclos.services.access.exceptions.InvalidCredentialsException;
import nl.strohalm.cyclos.services.access.exceptions.InvalidUserForChannelException;
import nl.strohalm.cyclos.services.transactions.DoPaymentDTO;
import nl.strohalm.cyclos.services.transactions.ProjectionDTO;
import nl.strohalm.cyclos.services.transactions.ScheduledPaymentDTO;
import nl.strohalm.cyclos.services.transactions.TransactionContext;
import nl.strohalm.cyclos.utils.RequestHelper;
import nl.strohalm.cyclos.utils.access.LoggedUser;
import nl.strohalm.cyclos.utils.binding.BeanBinder;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.conversion.CoercionHelper;
import nl.strohalm.cyclos.utils.validation.InvalidError;
import nl.strohalm.cyclos.utils.validation.PositiveNonZeroValidation;
import nl.strohalm.cyclos.utils.validation.RequiredError;
import nl.strohalm.cyclos.utils.validation.TodayValidation;
import nl.strohalm.cyclos.utils.validation.ValidationError;
import nl.strohalm.cyclos.utils.validation.ValidationException;

import org.apache.commons.lang.StringUtils;

/**
 * Action for the posweb member to receive a payment
 * @author luis
 */
public class ReceivePaymentAction extends BasePosWebPaymentAction {

    private ChannelService channelService;

    @Inject
    public void setChannelService(final ChannelService channelService) {
        this.channelService = channelService;
    }

    @Override
    protected Payment doPayment(final ActionContext context, final DoPaymentDTO dto) {
        final Member from;
        try {
            // Load the member that is making the payment
            from = elementService.load(((Member) dto.getFrom()).getId(), Element.Relationships.USER);
            if (from == null) {
                throw new Exception();
            }
        } catch (final Exception e) {
            throw new ValidationException();
        }

        // run as the member, to ensure the model layer will test permissions as that member
        return LoggedUser.runAs(from.getUser(), new Callable<Payment>() {
            @Override
            public Payment call() throws Exception {
                // Check if the member has access to the "posweb" channel;
                if (!accessService.isChannelEnabledForMember(Channel.POSWEB, from)) {
                    throw new InvalidUserForChannelException(from.getUsername());
                }

                // Check the paying member credentials
                final ValidationException validation = checkCredentials(context, from);
                validation.throwIfHasErrors();

                // Ensure that the scheduled payment, if any, is shown to the user
                dto.setShowScheduledToReceiver(true);

                // Perform the payment itself
                return getPaymentService().doPayment(dto);
            }
        });
    }

    @Override
    protected OperatorPermission getPermission() {
        return OperatorPermission.PAYMENTS_POSWEB_RECEIVE_PAYMENT;
    }

    @Override
    protected DataBinder<DoPaymentDTO> initDataBinder() {
        final BeanBinder<DoPaymentDTO> binder = (BeanBinder<DoPaymentDTO>) super.initDataBinder();
        // As we will handle from member by principal, remove from the regular binder
        binder.getMappings().remove("from");
        return binder;
    }

    @Override
    protected void prepareForm(final ActionContext context) throws Exception {
        super.prepareForm(context);
        final ReceivePaymentForm form = context.getForm();
        final HttpServletRequest request = context.getRequest();
        final Channel channel = posWebChannel();
        final Credentials credentials = channel.getCredentials();
        boolean numericCredentials = false;
        boolean uppercasedCredentials = false;
        switch (credentials) {
            case PIN:
            case CARD_SECURITY_CODE:
                numericCredentials = true;
                break;
            case LOGIN_PASSWORD:
                numericCredentials = settingsService.getAccessSettings().isNumericPassword();
                break;
            case TRANSACTION_PASSWORD:
                numericCredentials = StringUtils.containsOnly(settingsService.getAccessSettings().getTransactionPasswordChars(), "0123456789");
                uppercasedCredentials = true;
                break;
        }
        final PrincipalType selectedPrincipalType = channelService.resolvePrincipalType(channel.getInternalName(), form.getPrincipalType());
        form.setPrincipalType(selectedPrincipalType.toString());
        final Map<String, PrincipalType> principalTypes = new TreeMap<String, PrincipalType>();
        for (final PrincipalType principalType : channel.getPrincipalTypes()) {
            principalTypes.put(principalTypeLabel(principalType), principalType);
        }
        request.setAttribute("principalTypes", principalTypes);
        request.setAttribute("selectedPrincipalType", selectedPrincipalType);
        request.setAttribute("selectedPrincipalLabel", principalTypeLabel(selectedPrincipalType));
        request.setAttribute("credentials", credentials);
        request.setAttribute("numericCredentials", numericCredentials);
        request.setAttribute("uppercasedCredentials", uppercasedCredentials);
        request.setAttribute("credentialsKey", getCredentialsKey());
        final LocalSettings localSettings = settingsService.getLocalSettings();
        request.setAttribute("today", localSettings.getRawDateConverter().toString(Calendar.getInstance()));
        RequestHelper.storeEnum(request, SchedulingType.class, "schedulingTypes");
    }

    @Override
    protected DoPaymentDTO resolvePaymentDTO(final ActionContext context) {
        final Member member = (Member) context.getAccountOwner();
        final ReceivePaymentForm form = context.getForm();
        final DoPaymentDTO payment = getDataBinder().readFromString(form);

        try {
            final Member fromMember = CoercionHelper.coerce(Member.class, form.getFrom());
            payment.setFrom(fromMember);
        } catch (final EntityNotFoundException e) {
            // Leave null - will fail validation
        }

        payment.setChannel(Channel.POSWEB);
        payment.setContext(TransactionContext.PAYMENT);
        payment.setTo(member);
        if (context.isOperator()) {
            payment.setReceiver(context.getElement());
        }
        final LocalSettings localSettings = settingsService.getLocalSettings();

        // Handle scheduling
        SchedulingType schedulingType = CoercionHelper.coerce(SchedulingType.class, form.getSchedulingType());
        if (schedulingType == null) {
            schedulingType = SchedulingType.IMMEDIATELY;
        }
        List<ScheduledPaymentDTO> installments = null;
        switch (schedulingType) {
            case SINGLE_FUTURE:
                final ScheduledPaymentDTO installment = new ScheduledPaymentDTO();
                installment.setAmount(payment.getAmount());
                installment.setDate(localSettings.getRawDateConverter().valueOf(form.getScheduledFor()));
                installments = Collections.singletonList(installment);
                break;
            case MULTIPLE_FUTURE:
                final int paymentCount = CoercionHelper.coerce(int.class, form.getPaymentCount());
                final Calendar firstPaymentDate = localSettings.getRawDateConverter().valueOf(form.getFirstPaymentDate());
                if (paymentCount > 0 && firstPaymentDate != null) {
                    final ProjectionDTO projection = new ProjectionDTO();
                    projection.setTransferType(payment.getTransferType());
                    projection.setAmount(payment.getAmount());
                    projection.setFirstExpirationDate(firstPaymentDate);
                    projection.setPaymentCount(paymentCount);
                    installments = getPaymentService().calculatePaymentProjection(projection);
                }
                break;
        }
        payment.setPayments(installments);

        return payment;
    }

    @Override
    protected void validateForm(final ActionContext context) {
        final DoPaymentDTO dto = resolvePaymentDTO(context);
        dto.setPayments(null);

        ValidationException validation = null;
        try {
            getPaymentService().validate(dto);
            validation = new ValidationException();
        } catch (final ValidationException e) {
            validation = e;
        }
        final ReceivePaymentForm form = context.getForm();
        final LocalSettings localSettings = settingsService.getLocalSettings();

        final AccountOwner from = dto.getFrom();
        if (from instanceof Member) {
            validation.setPropertyKey("paymentCount", "transfer.paymentCount");
            validation.setPropertyKey("firstPaymentDate", "transfer.firstPaymentDate");
            validation.setPropertyKey("scheduledFor", "transfer.scheduledFor");
            validation.setPropertyKey("_credentials", getCredentialsKey());

            SchedulingType schedulingType = CoercionHelper.coerce(SchedulingType.class, form.getSchedulingType());
            if (schedulingType == null) {
                schedulingType = SchedulingType.IMMEDIATELY;
            }

            switch (schedulingType) {
                case SINGLE_FUTURE:
                    // Validate the scheduled for date
                    Calendar scheduledFor = null;
                    try {
                        scheduledFor = localSettings.getRawDateConverter().valueOf(form.getScheduledFor());
                        ValidationError error = null;
                        if (scheduledFor == null) {
                            error = new RequiredError();
                        } else {
                            error = TodayValidation.future().validate(null, null, scheduledFor);
                        }
                        if (error != null) {
                            validation.addPropertyError("scheduledFor", error);
                        }
                    } catch (final Exception e) {
                        validation.addPropertyError("scheduledFor", new InvalidError());
                    }
                    break;
                case MULTIPLE_FUTURE:
                    // Validate the payment count
                    Integer paymentCount;
                    try {
                        paymentCount = CoercionHelper.coerce(Integer.class, form.getPaymentCount());
                        ValidationError error = null;
                        if (paymentCount == null) {
                            error = new RequiredError();
                        } else {
                            error = PositiveNonZeroValidation.instance().validate(null, null, paymentCount);
                        }
                        if (error != null) {
                            validation.addPropertyError("paymentCount", new InvalidError());
                        }
                    } catch (final Exception e) {
                        validation.addPropertyError("paymentCount", new InvalidError());
                    }

                    // Validate the first payment date
                    Calendar firstPaymentDate = null;
                    try {
                        firstPaymentDate = localSettings.getRawDateConverter().valueOf(form.getFirstPaymentDate());
                        ValidationError error = null;
                        if (firstPaymentDate == null) {
                            error = new RequiredError();
                        } else {
                            error = TodayValidation.futureOrToday().validate(null, null, firstPaymentDate);
                        }
                        if (error != null) {
                            validation.addPropertyError("firstPaymentDate", error);
                        }
                    } catch (final Exception e) {
                        validation.addPropertyError("firstPaymentDate", new InvalidError());
                    }
                    break;
            }

            // Validate the credentials
            if (StringUtils.isEmpty(form.getCredentials())) {
                validation.addPropertyError("_credentials", new RequiredError());
            } else {
                if (!validation.hasErrors()) {
                    final Member member = elementService.load(((Member) from).getId());
                    validation = checkCredentials(context, member);
                }
            }
        }
        validation.throwIfHasErrors();
    }

    private ValidationException checkCredentials(final ActionContext context, final Member member) {
        final String cardFormProperty = "_card";
        final String credentialsFormProperty = "_credentials";

        final ValidationException validation = new ValidationException();
        validation.setPropertyKey(cardFormProperty, "posweb.client.card");
        validation.setPropertyKey(credentialsFormProperty, getCredentialsKey());

        final ReceivePaymentForm form = context.getForm();
        final String credentials = form.getCredentials();

        // Validate the credentials
        if (StringUtils.isEmpty(credentials)) {
            // Missing
            validation.addPropertyError(credentialsFormProperty, new RequiredError());
        } else {
            // Check it
            final Member relatedMember = (Member) context.getAccountOwner();
            try {
                final MemberUser payer = CoercionHelper.coerce(MemberUser.class, form.getFrom());
                accessService.checkCredentials(posWebChannel(), payer, credentials, context.getRequest().getRemoteAddr(), relatedMember);
            } catch (final InvalidCardException e) {
                validation.addPropertyError(cardFormProperty, new InvalidError());
            } catch (final InvalidCredentialsException e) {
                validation.addPropertyError(credentialsFormProperty, new InvalidError());
            } catch (final BlockedCredentialsException e) {
                String key;
                switch (e.getCredentialsType()) {
                    case TRANSACTION_PASSWORD:
                        key = "transactionPassword.error.blockedByTrials";
                        break;
                    case PIN:
                        key = "pin.error.blocked";
                        break;
                    case CARD_SECURITY_CODE:
                        key = "cardSecurityCode.error.blocked";
                        break;
                    default:
                        key = "login.error.blocked";
                        break;
                }
                validation.addGeneralError(new ValidationError(key));
            }
        }

        return validation;
    }

    private String getCredentialsKey() {
        final Channel channel = posWebChannel();
        String credentialsKey;
        switch (channel.getCredentials()) {
            case PIN:
                credentialsKey = "posweb.client.pin";
                break;
            case LOGIN_PASSWORD:
                credentialsKey = "posweb.client.loginPassword";
                break;
            case TRANSACTION_PASSWORD:
                credentialsKey = "posweb.client.transactionPassword";
                break;
            case CARD_SECURITY_CODE:
                credentialsKey = "posweb.client.cardSecurityCode";
                break;
            default:
                throw new IllegalStateException("Cannot use credentials type " + channel.getCredentials() + " for PosWeb");
        }
        return credentialsKey;
    }

    private Channel posWebChannel() {
        final Channel channel = channelService.loadByInternalName(Channel.POSWEB);
        return channel;
    }

    private String principalTypeLabel(final PrincipalType principalType) {
        final Principal principal = principalType.getPrincipal();
        switch (principal) {
            case USER:
                return messageHelper.message("posweb.client.username");
            case CUSTOM_FIELD:
                return principalType.getCustomField().getName();
            default:
                return messageHelper.message(principal.getKey());
        }
    }
}
