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
package nl.strohalm.cyclos.utils.notifications;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nl.strohalm.cyclos.entities.access.Channel;
import nl.strohalm.cyclos.entities.access.Channel.Credentials;
import nl.strohalm.cyclos.entities.access.MemberUser;
import nl.strohalm.cyclos.entities.access.User;
import nl.strohalm.cyclos.entities.accounts.Account;
import nl.strohalm.cyclos.entities.accounts.AccountOwner;
import nl.strohalm.cyclos.entities.accounts.AccountStatus;
import nl.strohalm.cyclos.entities.accounts.AccountType;
import nl.strohalm.cyclos.entities.accounts.MemberAccount;
import nl.strohalm.cyclos.entities.accounts.MemberAccountType;
import nl.strohalm.cyclos.entities.accounts.MemberGroupAccountSettings;
import nl.strohalm.cyclos.entities.accounts.SystemAccountOwner;
import nl.strohalm.cyclos.entities.accounts.guarantees.Certification;
import nl.strohalm.cyclos.entities.accounts.guarantees.Guarantee;
import nl.strohalm.cyclos.entities.accounts.guarantees.GuaranteeType;
import nl.strohalm.cyclos.entities.accounts.guarantees.PaymentObligation;
import nl.strohalm.cyclos.entities.accounts.loans.Loan;
import nl.strohalm.cyclos.entities.accounts.loans.LoanPayment;
import nl.strohalm.cyclos.entities.accounts.pos.MemberPos;
import nl.strohalm.cyclos.entities.accounts.pos.Pos;
import nl.strohalm.cyclos.entities.accounts.transactions.AuthorizationLevel;
import nl.strohalm.cyclos.entities.accounts.transactions.AuthorizationLevel.Authorizer;
import nl.strohalm.cyclos.entities.accounts.transactions.Invoice;
import nl.strohalm.cyclos.entities.accounts.transactions.Payment;
import nl.strohalm.cyclos.entities.accounts.transactions.PaymentRequestTicket;
import nl.strohalm.cyclos.entities.accounts.transactions.ScheduledPayment;
import nl.strohalm.cyclos.entities.accounts.transactions.Transfer;
import nl.strohalm.cyclos.entities.ads.Ad;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.members.Reference;
import nl.strohalm.cyclos.entities.members.TransactionFeedback;
import nl.strohalm.cyclos.entities.members.TransactionFeedbackRequest;
import nl.strohalm.cyclos.entities.members.brokerings.BrokerCommissionContract;
import nl.strohalm.cyclos.entities.members.brokerings.Brokering;
import nl.strohalm.cyclos.entities.members.messages.Message;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.entities.settings.MessageSettings;
import nl.strohalm.cyclos.services.access.ChannelServiceLocal;
import nl.strohalm.cyclos.services.accounts.AccountDTO;
import nl.strohalm.cyclos.services.accounts.AccountServiceLocal;
import nl.strohalm.cyclos.services.accounts.GetTransactionsDTO;
import nl.strohalm.cyclos.services.accounts.guarantees.CertificationServiceLocal;
import nl.strohalm.cyclos.services.elements.BrokeringServiceLocal;
import nl.strohalm.cyclos.services.elements.ChangeBrokerDTO;
import nl.strohalm.cyclos.services.elements.MessageServiceLocal;
import nl.strohalm.cyclos.services.elements.SendMessageFromSystemDTO;
import nl.strohalm.cyclos.services.fetch.FetchServiceLocal;
import nl.strohalm.cyclos.services.groups.GroupServiceLocal;
import nl.strohalm.cyclos.services.preferences.MessageChannel;
import nl.strohalm.cyclos.services.preferences.PreferenceServiceLocal;
import nl.strohalm.cyclos.services.settings.SettingsServiceLocal;
import nl.strohalm.cyclos.services.transactions.DoPaymentDTO;
import nl.strohalm.cyclos.services.transactions.InvoiceServiceLocal;
import nl.strohalm.cyclos.services.transactions.TransferDTO;
import nl.strohalm.cyclos.utils.DateHelper;
import nl.strohalm.cyclos.utils.MessageProcessingHelper;
import nl.strohalm.cyclos.utils.MessageResolver;
import nl.strohalm.cyclos.utils.RelationshipHelper;
import nl.strohalm.cyclos.utils.access.LoggedUser;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

/**
 * Implementation for {@link MemberNotificationHandler}
 * @author luis
 */
public class MemberNotificationHandlerImpl implements MemberNotificationHandler {

    private static final float        PRECISION_DELTA = 0.0001F;
    private AccountServiceLocal       accountService;
    private BrokeringServiceLocal     brokeringService;
    private CertificationServiceLocal certificationService;
    private ChannelServiceLocal       channelService;
    private FetchServiceLocal         fetchService;
    private GroupServiceLocal         groupService;
    private InvoiceServiceLocal       invoiceService;
    private MessageServiceLocal       messageService;
    private SettingsServiceLocal      settingsService;
    private MessageResolver           messageResolver;
    private PreferenceServiceLocal    preferenceService;
    private AdminNotificationHandler  adminNotificationHandler;

    @Override
    public void acceptedInvoiceNotification(final Invoice invoice) {
        // Get the destination
        final Member destinationMember = invoice.getFromMember();

        if (destinationMember == null) {
            // The invoice was sent from system
            return;
        }

        // Get the message settings
        final MessageSettings messageSettings = settingsService.getMessageSettings();
        final String subject = messageSettings.getInvoiceAcceptedSubject();
        final String body = messageSettings.getInvoiceAcceptedMessage();
        final String sms = messageSettings.getInvoiceAcceptedSms();

        // Process message body
        final LocalSettings localSettings = settingsService.getLocalSettings();
        final String processedSubject = MessageProcessingHelper.processVariables(subject, localSettings, invoice.getTo(), invoice);
        final String processedBody = MessageProcessingHelper.processVariables(body, localSettings, invoice.getTo(), invoice);
        final String processedSms = MessageProcessingHelper.processVariables(sms, localSettings, invoice.getTo(), invoice);

        // Create the DTO
        final SendMessageFromSystemDTO message = new SendMessageFromSystemDTO();
        message.setType(Message.Type.INVOICE);
        message.setEntity(invoice);
        message.setToMember(destinationMember);
        message.setSubject(processedSubject);
        message.setBody(processedBody);
        message.setSms(processedSms);

        // Send the message
        messageService.sendFromSystem(message);

        notifyTransactionFeedbackRequest(invoice.getTransfer());
    }

    @Override
    public void automaticPaymentReceivedNotification(Transfer transfer, final TransferDTO dto) {
        transfer = fetchService.fetch(transfer, RelationshipHelper.nested(Payment.Relationships.TO, MemberAccount.Relationships.MEMBER), Payment.Relationships.TYPE);
        if (transfer.isRoot() && !transfer.isToSystem()) {
            // Get the destination
            final Member destinationMember = (Member) transfer.getTo().getOwner();
            final Set<MessageChannel> channels = preferenceService.receivedChannels(destinationMember, Message.Type.PAYMENT);
            if (!channels.isEmpty()) {
                AccountStatus status = null;
                final boolean sendSmsNotification = transfer.getType().isAllowSmsNotification() && channels.contains(MessageChannel.SMS);
                if (sendSmsNotification) {
                    status = accountService.getCurrentStatus(new AccountDTO(transfer.getTo()));
                }

                // Get the message settings
                final LocalSettings localSettings = settingsService.getLocalSettings();
                final MessageSettings messageSettings = settingsService.getMessageSettings();
                String subject = null;
                String body = null;
                String sms = null;

                if (transfer.getAccountFeeLog() != null) {
                    subject = messageSettings.getAccountFeeReceivedSubject();
                    body = messageSettings.getAccountFeeReceivedMessage();
                    if (sendSmsNotification) {
                        sms = messageSettings.getAccountFeeReceivedSms();
                    }

                    // Process message content
                    subject = MessageProcessingHelper.processVariables(subject, localSettings, destinationMember, transfer, transfer.getAccountFeeLog().getAccountFee());
                    body = MessageProcessingHelper.processVariables(body, localSettings, destinationMember, transfer, transfer.getAccountFeeLog().getAccountFee());
                    if (sendSmsNotification) {
                        sms = MessageProcessingHelper.processVariables(sms, localSettings, destinationMember, status, transfer, transfer.getAccountFeeLog().getAccountFee());
                    }
                } else {
                    // Check if the transfer has been processed or awaits authorization
                    if (transfer.getProcessDate() == null) {
                        subject = messageSettings.getPendingPaymentReceivedSubject();
                        body = messageSettings.getPendingPaymentReceivedMessage();
                        if (sendSmsNotification) {
                            sms = messageSettings.getPendingPaymentReceivedSms();
                        }
                    } else {
                        subject = messageSettings.getPaymentReceivedSubject();
                        body = messageSettings.getPaymentReceivedMessage();
                        if (sendSmsNotification) {
                            sms = messageSettings.getPaymentReceivedSms();
                        }
                    }

                    // Process message content
                    subject = MessageProcessingHelper.processVariables(subject, localSettings, transfer.getFrom().getOwner(), transfer);
                    body = MessageProcessingHelper.processVariables(body, localSettings, transfer.getFrom().getOwner(), transfer);
                    if (sendSmsNotification) {
                        sms = MessageProcessingHelper.processVariables(sms, localSettings, transfer.getFrom().getOwner(), status, transfer);
                    }
                }

                // Create the DTO
                final SendMessageFromSystemDTO message = new SendMessageFromSystemDTO();
                message.setType(Message.Type.PAYMENT);
                message.setEntity(transfer);
                message.setToMember(destinationMember);
                message.setSubject(subject);
                message.setBody(body);
                message.setSms(sms);

                // Send the message
                messageService.sendFromSystem(message);
            }
        }
    }

    @Override
    public void blockedCredentialsNotification(final User user, final Credentials credentialsType) {
        if (user instanceof MemberUser) {
            boolean skipSms = false;
            try {
                // FIXME we will add the ServiceClient to LoggedUser. This hack shouldn't be needed anymore
                // Try to get the current channel from the web services context, which will only return something when running on web services
                final Channel currentChannel = (Channel) Class.forName("nl.strohalm.cyclos.webservices.WebServiceContext").getMethod("getChannel").invoke(null);
                if (currentChannel != null) {
                    // Ok, we are running on a web service. skip if this is the SMS channel, as it has it's own notification
                    final Channel smsChannel = channelService.getSmsChannel();
                    if (currentChannel.equals(smsChannel)) {
                        // Do not notify by SMS
                        skipSms = true;
                    }
                }
            } catch (final Exception ex) {
                // SMS won't be skipped
            }

            final Member member = (Member) user.getElement();

            // Get message settings
            final MessageSettings messageSettings = settingsService.getMessageSettings();
            String subject;
            String body;
            String sms;
            switch (credentialsType) {
                case LOGIN_PASSWORD:
                    subject = messageSettings.getLoginBlockedSubject();
                    body = messageSettings.getLoginBlockedMessage();
                    sms = skipSms ? null : messageSettings.getLoginBlockedSms();
                    break;
                case TRANSACTION_PASSWORD:
                    subject = messageSettings.getMaxTransactionPasswordTriesSubject();
                    body = messageSettings.getMaxTransactionPasswordTriesMessage();
                    sms = skipSms ? null : messageSettings.getMaxTransactionPasswordTriesSms();
                    break;
                case PIN:
                    subject = messageSettings.getPinBlockedSubject();
                    body = messageSettings.getPinBlockedMessage();
                    sms = skipSms ? null : messageSettings.getPinBlockedSms();
                    break;
                case CARD_SECURITY_CODE:
                    subject = messageSettings.getCardSecurityCodeBlockedSubject();
                    body = messageSettings.getCardSecurityCodeBlockedMessage();
                    sms = skipSms ? null : messageSettings.getCardSecurityCodeBlockedSms();
                    break;
                default:
                    return;
            }

            // Process message content
            final LocalSettings localSettings = settingsService.getLocalSettings();
            final String processedSubject = MessageProcessingHelper.processVariables(subject, member, localSettings);
            final String processedBody = MessageProcessingHelper.processVariables(body, member, localSettings);
            final String processedSms = sms == null ? null : MessageProcessingHelper.processVariables(sms, member, localSettings);

            // Create the DTO
            final SendMessageFromSystemDTO message = new SendMessageFromSystemDTO();
            message.setType(Message.Type.ACCESS);
            message.setToMember(member);
            message.setSubject(processedSubject);
            message.setBody(processedBody);
            message.setSms(processedSms);

            // Send the message
            messageService.sendFromSystem(message);
        }
    }

    @Override
    public void cancelledInvoiceNotification(final Invoice invoice) {
        if (!invoice.isToSystem()) {
            // Get the destination
            final Member destinationMember = invoice.getToMember();

            // Get the message settings
            final MessageSettings messageSettings = settingsService.getMessageSettings();
            final String subject = messageSettings.getInvoiceCancelledSubject();
            final String body = messageSettings.getInvoiceCancelledMessage();
            final String sms = messageSettings.getInvoiceCancelledSms();

            // Process message body
            final LocalSettings localSettings = settingsService.getLocalSettings();
            final String processedSubject = MessageProcessingHelper.processVariables(subject, localSettings, invoice.getFrom(), invoice);
            final String processedBody = MessageProcessingHelper.processVariables(body, localSettings, invoice.getFrom(), invoice);
            final String processedSms = MessageProcessingHelper.processVariables(sms, localSettings, invoice.getFrom(), invoice);

            // Create the DTO
            final SendMessageFromSystemDTO message = new SendMessageFromSystemDTO();
            message.setType(Message.Type.INVOICE);
            message.setEntity(invoice);
            message.setToMember(destinationMember);
            message.setSubject(processedSubject);
            message.setBody(processedBody);
            message.setSms(processedSms);

            // Send the message
            messageService.sendFromSystem(message);
        }
    }

    @Override
    public void certificationCanceledNotification(final Long certificationId) {
        // Load the certification
        final Certification certification = certificationService.load(certificationId, Certification.Relationships.BUYER, Certification.Relationships.ISSUER);

        certificationStatusChangedNotification(certification);
    }

    @Override
    public void certificationIssuedNotification(final Certification certification) {
        // If the certification has been registered and is active, notify buyer
        if (certification.getStatus() == Certification.Status.ACTIVE) {

            // Get local and message settings
            final LocalSettings localSettings = settingsService.getLocalSettings();
            final MessageSettings messageSettings = settingsService.getMessageSettings();

            // Get the destination
            final Member buyer = certification.getBuyer();

            // Get the message settings
            final String subjectBuyer = messageSettings.getCertificationIssuedSubject();
            final String bodyBuyer = messageSettings.getCertificationIssuedMessage();
            final String smsBuyer = messageSettings.getCertificationIssuedSms();

            // Process message content
            final String processedSubjectBuyer = MessageProcessingHelper.processVariables(subjectBuyer, certification, localSettings);
            final String processedBodyBuyer = MessageProcessingHelper.processVariables(bodyBuyer, certification, localSettings);
            final String processedSmsBuyer = MessageProcessingHelper.processVariables(smsBuyer, certification, localSettings);

            // Create the DTO
            final SendMessageFromSystemDTO messageToBuyer = new SendMessageFromSystemDTO();
            messageToBuyer.setEntity(certification);
            messageToBuyer.setType(Message.Type.CERTIFICATION);
            messageToBuyer.setToMember(buyer);
            messageToBuyer.setSubject(processedSubjectBuyer);
            messageToBuyer.setBody(processedBodyBuyer);
            messageToBuyer.setSms(processedSmsBuyer);

            // Send the message
            messageService.sendFromSystem(messageToBuyer);
        }
    }

    @Override
    public void certificationStatusChangedNotification(final Certification certification) {

        // Only notify if it's an activation or suspension, SHCEDULED is none of these
        if (certification.getStatus() == Certification.Status.SCHEDULED) {
            return;
        }

        // Get local and message settings
        final LocalSettings localSettings = settingsService.getLocalSettings();
        final MessageSettings messageSettings = settingsService.getMessageSettings();

        // Process new status string
        final String statusString = messageResolver.message("certification.status." + certification.getStatus().toString());
        final Map<String, Object> variables = certification.getVariableValues(localSettings);
        variables.put("status", statusString);

        // Get the destination (buyer)
        final Member buyer = certification.getBuyer();

        // Get the message settings (buyer)
        final String subjectBuyer = messageSettings.getCertificationStatusChangedSubject();
        final String bodyBuyer = messageSettings.getCertificationStatusChangedMessage();
        final String smsBuyer = messageSettings.getCertificationStatusChangedSms();

        // Process message content (buyer)
        final String processedSubjectBuyer = MessageProcessingHelper.processVariables(subjectBuyer, variables);
        final String processedBodyBuyer = MessageProcessingHelper.processVariables(bodyBuyer, variables);
        final String processedSmsBuyer = MessageProcessingHelper.processVariables(smsBuyer, variables);

        // Create the DTO (buyer)
        final SendMessageFromSystemDTO messageToBuyer = new SendMessageFromSystemDTO();
        messageToBuyer.setEntity(certification);
        messageToBuyer.setType(Message.Type.CERTIFICATION);
        messageToBuyer.setToMember(buyer);
        messageToBuyer.setSubject(processedSubjectBuyer);
        messageToBuyer.setBody(processedBodyBuyer);
        messageToBuyer.setSms(processedSmsBuyer);

        // Send the message (to buyer)
        messageService.sendFromSystem(messageToBuyer);

        // If the new status is "EXPIRED" notify the issuer too
        if (certification.getStatus() == Certification.Status.EXPIRED) {

            // Get the destination (issuer)
            final Member issuer = certification.getIssuer();

            // Get the message settings (issuer)
            final String subjectIssuer = messageSettings.getExpiredCertificationSubject();
            final String bodyIssuer = messageSettings.getExpiredCertificationMessage();
            final String smsIssuer = messageSettings.getExpiredCertificationSms();

            // Process message content (issuer)
            final String processedSubjectIssuer = MessageProcessingHelper.processVariables(subjectIssuer, certification, localSettings);
            final String processedBodyIssuer = MessageProcessingHelper.processVariables(bodyIssuer, certification, localSettings);
            final String processedSmsIssuer = MessageProcessingHelper.processVariables(smsIssuer, certification, localSettings);

            // Create the DTO (issuer)
            final SendMessageFromSystemDTO messageToIssuer = new SendMessageFromSystemDTO();
            messageToIssuer.setEntity(certification);
            messageToIssuer.setType(Message.Type.CERTIFICATION);
            messageToIssuer.setToMember(issuer);
            messageToIssuer.setSubject(processedSubjectIssuer);
            messageToIssuer.setBody(processedBodyIssuer);
            messageToIssuer.setSms(processedSmsIssuer);

            // Send the message (to buyer)
            messageService.sendFromSystem(messageToIssuer);
        }
    }

    @Override
    public void commissionContractAcceptedNotification(final BrokerCommissionContract brokerCommissionContract) {
        // Get the destination
        final Member destinationMember = brokerCommissionContract.getBrokering().getBroker();

        // Get the message settings
        final MessageSettings messageSettings = settingsService.getMessageSettings();
        final String subject = messageSettings.getCommissionContractAcceptedSubject();
        final String body = messageSettings.getCommissionContractAcceptedMessage();
        final String sms = messageSettings.getCommissionContractAcceptedSms();

        // Process message content
        final LocalSettings localSettings = settingsService.getLocalSettings();
        final String processedSubject = MessageProcessingHelper.processVariables(subject, brokerCommissionContract, localSettings);
        final String processedBody = MessageProcessingHelper.processVariables(body, brokerCommissionContract, localSettings);
        final String processedSms = MessageProcessingHelper.processVariables(sms, brokerCommissionContract, localSettings);

        // Create the DTO
        final SendMessageFromSystemDTO message = new SendMessageFromSystemDTO();
        message.setEntity(brokerCommissionContract);
        message.setType(Message.Type.BROKERING);
        message.setToMember(destinationMember);
        message.setSubject(processedSubject);
        message.setBody(processedBody);
        message.setSms(processedSms);

        // Send the message
        messageService.sendFromSystem(message);
    }

    @Override
    public void commissionContractCancelledNotification(final BrokerCommissionContract brokerCommissionContract) {
        // Get the destination
        final Member destinationMember = brokerCommissionContract.getBrokering().getBrokered();

        // Get the message settings
        final MessageSettings messageSettings = settingsService.getMessageSettings();
        final String subject = messageSettings.getCommissionContractCancelledSubject();
        final String body = messageSettings.getCommissionContractCancelledMessage();
        final String sms = messageSettings.getCommissionContractCancelledSms();

        // Process message content
        final LocalSettings localSettings = settingsService.getLocalSettings();
        final String processedSubject = MessageProcessingHelper.processVariables(subject, brokerCommissionContract, localSettings);
        final String processedBody = MessageProcessingHelper.processVariables(body, brokerCommissionContract, localSettings);
        final String processedSms = MessageProcessingHelper.processVariables(sms, brokerCommissionContract, localSettings);

        // Create the DTO
        final SendMessageFromSystemDTO message = new SendMessageFromSystemDTO();
        message.setEntity(brokerCommissionContract);
        message.setType(Message.Type.BROKERING);
        message.setToMember(destinationMember);
        message.setSubject(processedSubject);
        message.setBody(processedBody);
        message.setSms(processedSms);

        // Send the message
        messageService.sendFromSystem(message);
    }

    @Override
    public void commissionContractDeniedNotification(final BrokerCommissionContract brokerCommissionContract) {
        // Get the destination
        final Member destinationMember = brokerCommissionContract.getBrokering().getBroker();

        // Get the message settings
        final MessageSettings messageSettings = settingsService.getMessageSettings();
        final String subject = messageSettings.getCommissionContractDeniedSubject();
        final String body = messageSettings.getCommissionContractDeniedMessage();
        final String sms = messageSettings.getCommissionContractDeniedSms();

        // Process message content
        final LocalSettings localSettings = settingsService.getLocalSettings();
        final String processedSubject = MessageProcessingHelper.processVariables(subject, brokerCommissionContract, localSettings);
        final String processedBody = MessageProcessingHelper.processVariables(body, brokerCommissionContract, localSettings);
        final String processedSms = MessageProcessingHelper.processVariables(sms, brokerCommissionContract, localSettings);

        // Create the DTO
        final SendMessageFromSystemDTO message = new SendMessageFromSystemDTO();
        message.setEntity(brokerCommissionContract);
        message.setType(Message.Type.BROKERING);
        message.setToMember(destinationMember);
        message.setSubject(processedSubject);
        message.setBody(processedBody);
        message.setSms(processedSms);

        // Send the message
        messageService.sendFromSystem(message);
    }

    @Override
    public void deniedInvoiceNotification(final Invoice invoice) {
        if (!invoice.isFromSystem()) {
            // Get the destination
            final Member destinationMember = invoice.getFromMember();

            // Get the message settings
            final MessageSettings messageSettings = settingsService.getMessageSettings();
            final String subject = messageSettings.getInvoiceDeniedSubject();
            final String body = messageSettings.getInvoiceDeniedMessage();
            final String sms = messageSettings.getInvoiceDeniedSms();

            // Process message content
            final LocalSettings localSettings = settingsService.getLocalSettings();
            final String processedSubject = MessageProcessingHelper.processVariables(subject, localSettings, invoice.getTo(), invoice);
            final String processedBody = MessageProcessingHelper.processVariables(body, localSettings, invoice.getTo(), invoice);
            final String processedSms = MessageProcessingHelper.processVariables(sms, localSettings, invoice.getTo(), invoice);

            // Create the DTO
            final SendMessageFromSystemDTO message = new SendMessageFromSystemDTO();
            message.setType(Message.Type.INVOICE);
            message.setEntity(invoice);
            message.setToMember(destinationMember);
            message.setSubject(processedSubject);
            message.setBody(processedBody);
            message.setSms(processedSms);

            // Send the message
            messageService.sendFromSystem(message);
        }
    }

    @Override
    public void expiredAdNotification(final Ad ad) {
        final LocalSettings localSettings = settingsService.getLocalSettings();

        // Get message settings
        final MessageSettings messageSettings = settingsService.getMessageSettings();
        final String subject = messageSettings.getAdExpirationSubject();
        final String body = messageSettings.getAdExpirationMessage();
        final String sms = messageSettings.getAdExpirationSms();

        // Create the DTO
        final SendMessageFromSystemDTO message = new SendMessageFromSystemDTO();
        message.setType(Message.Type.AD_EXPIRATION);

        // Get the destination
        final Member owner = ad.getOwner();
        message.setToMember(owner);

        // Process message content
        final String processedSubject = MessageProcessingHelper.processVariables(subject, ad, localSettings);
        final String processedBody = MessageProcessingHelper.processVariables(body, ad, localSettings);
        final String processedSms = MessageProcessingHelper.processVariables(sms, ad, localSettings);
        message.setSubject(processedSubject);
        message.setBody(processedBody);
        message.setSms(processedSms);

        // Process link and send message
        message.setEntity(ad);
        messageService.sendFromSystem(message);
    }

    @Override
    public void expiredBrokeringNotification(final Brokering brokering) {
        final LocalSettings localSettings = settingsService.getLocalSettings();

        // Get message settings
        final MessageSettings messageSettings = settingsService.getMessageSettings();
        final String subject = messageSettings.getBrokeringExpirationSubject();
        final String body = messageSettings.getBrokeringExpirationMessage();
        final String sms = messageSettings.getBrokeringExpirationSms();

        // Create the DTO
        final SendMessageFromSystemDTO message = new SendMessageFromSystemDTO();
        message.setType(Message.Type.BROKERING);

        // Get the destination
        final Member broker = brokering.getBroker();
        message.setToMember(broker);

        Member member = brokering.getBrokered();

        // Process message content
        final String processedSubject = MessageProcessingHelper.processVariables(subject, member, localSettings);
        final String processedBody = MessageProcessingHelper.processVariables(body, member, localSettings);
        final String processedSms = MessageProcessingHelper.processVariables(sms, member, localSettings);
        message.setSubject(processedSubject);
        message.setBody(processedBody);
        message.setSms(processedSms);

        // Send message
        messageService.sendFromSystem(message);
    }

    @Override
    public void expiredInvoiceNotification(final Invoice invoice) {
        // Get the settings
        final MessageSettings messageSettings = settingsService.getMessageSettings();
        final LocalSettings localSettings = settingsService.getLocalSettings();

        // Send message to the sender of the invoice
        if (!invoice.isFromSystem()) {
            final String subjectSender = messageSettings.getSentInvoiceExpiredSubject();
            final String bodySender = messageSettings.getSentInvoiceExpiredMessage();
            final String smsSender = messageSettings.getSentInvoiceExpiredSms();
            final String processedSubjectSender = MessageProcessingHelper.processVariables(subjectSender, localSettings, invoice.getTo(), invoice);
            final String processedBodySender = MessageProcessingHelper.processVariables(bodySender, localSettings, invoice.getTo(), invoice);
            final String processedSmsSender = MessageProcessingHelper.processVariables(smsSender, localSettings, invoice.getTo(), invoice);
            final SendMessageFromSystemDTO messageSender = new SendMessageFromSystemDTO();
            messageSender.setType(Message.Type.INVOICE);
            messageSender.setEntity(invoice);
            messageSender.setToMember(invoice.getFromMember());
            messageSender.setSubject(processedSubjectSender);
            messageSender.setBody(processedBodySender);
            messageSender.setSms(processedSmsSender);
            messageService.sendFromSystem(messageSender);
        }

        // Send message to the receiver of the invoice
        if (!invoice.isToSystem()) {
            final String subjectReceiver = messageSettings.getReceivedInvoiceExpiredSubject();
            final String bodyReceiver = messageSettings.getReceivedInvoiceExpiredMessage();
            final String smsReceiver = messageSettings.getReceivedInvoiceExpiredSms();
            final String processedSubjectReceiver = MessageProcessingHelper.processVariables(subjectReceiver, localSettings, invoice.getFrom(), invoice);
            final String processedBodyReceiver = MessageProcessingHelper.processVariables(bodyReceiver, localSettings, invoice.getFrom(), invoice);
            final String processedSmsReceiver = MessageProcessingHelper.processVariables(smsReceiver, localSettings, invoice.getFrom(), invoice);
            final SendMessageFromSystemDTO messageReceiver = new SendMessageFromSystemDTO();
            messageReceiver.setType(Message.Type.INVOICE);
            messageReceiver.setEntity(invoice);
            messageReceiver.setToMember(invoice.getToMember());
            messageReceiver.setSubject(processedSubjectReceiver);
            messageReceiver.setBody(processedBodyReceiver);
            messageReceiver.setSms(processedSmsReceiver);
            messageService.sendFromSystem(messageReceiver);
        }
    }

    @Override
    public void expiredLoanNotification(final LoanPayment payment) {
        final LocalSettings localSettings = settingsService.getLocalSettings();

        // Get the message settings
        final MessageSettings messageSettings = settingsService.getMessageSettings();
        final String subject = messageSettings.getLoanExpirationSubject();
        final String body = messageSettings.getLoanExpirationMessage();
        final String sms = messageSettings.getLoanExpirationSms();

        // Create the DTO
        final SendMessageFromSystemDTO message = new SendMessageFromSystemDTO();
        message.setType(Message.Type.LOAN);

        // Set entity for link processing
        final Loan loan = payment.getLoan();
        message.setEntity(loan);

        // Get the destination
        final Member member = (Member) loan.getTransfer().getTo().getOwner();
        message.setToMember(member);

        // Process message content
        final String processedSubject = MessageProcessingHelper.processVariables(subject, loan, localSettings);
        final String processedBody = MessageProcessingHelper.processVariables(body, loan, localSettings);
        final String processedSms = MessageProcessingHelper.processVariables(sms, loan, localSettings);
        message.setSubject(processedSubject);
        message.setBody(processedBody);
        message.setSms(processedSms);

        // Send the message
        messageService.sendFromSystem(message);
    }

    @Override
    public void externalChannelPaymentConfirmed(final PaymentRequestTicket ticket) {
        externalChannelPaymentNotification(ticket.getTransfer(), ticket.getFromChannel(), ticket.getToChannel());
    }

    @Override
    public void externalChannelPaymentPerformed(final DoPaymentDTO dto, final Payment payment) {
        final String channelInternalName = dto.getChannel();
        final Channel channel = channelService.loadByInternalName(channelInternalName);
        externalChannelPaymentNotification(payment, channel, null);
    }

    @Override
    public void externalChannelPaymentRequestExpired(final PaymentRequestTicket ticket) {
        // Get local and message settings
        final LocalSettings localSettings = settingsService.getLocalSettings();
        final MessageSettings messageSettings = settingsService.getMessageSettings();

        final Channel smsChannel = channelService.getSmsChannel();

        // Only notify the payer if not on SMS channel
        final Channel toChannel = ticket.getToChannel();
        final boolean skipToSms = toChannel.equals(smsChannel);
        final Map<String, Object> toVariables = ticket.getVariableValues(localSettings);
        toVariables.put("channel", toChannel.getDisplayName());

        // Get payer message
        final Member payer = ticket.getFrom();
        final String subjectPayer = messageSettings.getExternalChannelPaymentRequestExpiredPayerSubject();
        final String bodyPayer = messageSettings.getExternalChannelPaymentRequestExpiredPayerMessage();
        final String smsPayer = skipToSms ? null : messageSettings.getExternalChannelPaymentRequestExpiredPayerSms();

        // Process payer message content
        final String processedSubjectPayer = MessageProcessingHelper.processVariables(subjectPayer, toVariables);
        final String processedBodyPayer = MessageProcessingHelper.processVariables(bodyPayer, toVariables);
        final String processedSmsPayer = smsPayer == null ? null : MessageProcessingHelper.processVariables(smsPayer, toVariables);

        // Create the payer DTO
        final SendMessageFromSystemDTO messageToPayer = new SendMessageFromSystemDTO();
        messageToPayer.setToMember(payer);
        messageToPayer.setType(Message.Type.EXTERNAL_PAYMENT);
        messageToPayer.setSubject(processedSubjectPayer);
        messageToPayer.setBody(processedBodyPayer);
        messageToPayer.setSms(processedSmsPayer);

        // Send message to payer
        messageService.sendFromSystem(messageToPayer);

        // Only notify the payee if not on SMS channel
        final Channel fromChannel = ticket.getFromChannel();
        final boolean skipFromSms = fromChannel.equals(smsChannel);
        final Map<String, Object> variableValues = ticket.getVariableValues(localSettings);
        variableValues.put("channel", fromChannel.getDisplayName());

        // Get receiver message
        final Member receiver = ticket.getTo();
        final String subjectReceiver = messageSettings.getExternalChannelPaymentRequestExpiredReceiverSubject();
        final String bodyReceiver = messageSettings.getExternalChannelPaymentRequestExpiredReceiverMessage();
        final String smsReceiver = skipFromSms ? null : messageSettings.getExternalChannelPaymentRequestExpiredReceiverSms();

        // Process receiver message content
        final String processedSubjectReceiver = MessageProcessingHelper.processVariables(subjectReceiver, variableValues);
        final String processedBodyReceiver = MessageProcessingHelper.processVariables(bodyReceiver, variableValues);
        final String processedSmsReceiver = smsReceiver == null ? null : MessageProcessingHelper.processVariables(smsReceiver, variableValues);

        // Create the receiver DTO
        final SendMessageFromSystemDTO messageToReceiver = new SendMessageFromSystemDTO();
        messageToReceiver.setToMember(receiver);
        messageToReceiver.setType(Message.Type.PAYMENT);
        messageToReceiver.setSubject(processedSubjectReceiver);
        messageToReceiver.setBody(processedBodyReceiver);
        messageToReceiver.setSms(processedSmsReceiver);

        // Send message to receiver
        messageService.sendFromSystem(messageToReceiver);
    }

    @Override
    public void grantedLoanNotification(final Loan loan) {
        // Return when the loan is pending
        if (loan.getTransfer().getProcessDate() == null) {
            return;
        }

        // Get the destination
        final Member destinationMember = loan.getMember();

        // Get the message settings
        final MessageSettings messageSettings = settingsService.getMessageSettings();
        final String subject = messageSettings.getLoanGrantedSubject();
        final String body = messageSettings.getLoanGrantedMessage();
        final String sms = messageSettings.getLoanGrantedSms();

        // Process message contents
        final LocalSettings localSettings = settingsService.getLocalSettings();
        final String processedSubject = MessageProcessingHelper.processVariables(subject, localSettings, destinationMember, loan);
        final String processedBody = MessageProcessingHelper.processVariables(body, localSettings, destinationMember, loan);
        final String processedSms = MessageProcessingHelper.processVariables(sms, localSettings, destinationMember, loan);

        // Create the DTO
        final SendMessageFromSystemDTO message = new SendMessageFromSystemDTO();
        message.setType(Message.Type.LOAN);
        message.setEntity(loan);
        message.setToMember(destinationMember);
        message.setSubject(processedSubject);
        message.setBody(processedBody);
        message.setSms(processedSms);

        // Send the message
        messageService.sendFromSystem(message);
    }

    @Override
    public void guaranteeAcceptedNotification(final Guarantee guarantee) {
        doGuaranteeStatusChangedNotification(guarantee, null);
    }

    @Override
    public void guaranteeCancelledNotification(final Guarantee guarantee) {
        doGuaranteeStatusChangedNotification(guarantee, null);
    }

    @Override
    public void guaranteeDeniedNotification(final Guarantee guarantee) {
        doGuaranteeStatusChangedNotification(guarantee, null);
    }

    @Override
    public void guaranteePendingIssuerNotification(final Guarantee guarantee) {
        if (guarantee.getStatus() == Guarantee.Status.PENDING_ISSUER) { // only send notification if the status is PENDING_ISSUER
            // Get local and message settings
            final LocalSettings localSettings = settingsService.getLocalSettings();
            final MessageSettings messageSettings = settingsService.getMessageSettings();

            // Get the destination
            final Member toMember = guarantee.getIssuer();

            // Get the message settings
            String subject = null;
            String body = null;
            String sms = null;
            if (guarantee.getGuaranteeType().getModel() == GuaranteeType.Model.WITH_BUYER_ONLY) {
                subject = messageSettings.getPendingBuyerOnlyGuaranteeIssuerSubject();
                body = messageSettings.getPendingBuyerOnlyGuaranteeIssuerMessage();
                sms = messageSettings.getPendingBuyerOnlyGuaranteeIssuerSms();
            } else {
                subject = messageSettings.getPendingGuaranteeIssuerSubject();
                body = messageSettings.getPendingGuaranteeIssuerMessage();
                sms = messageSettings.getPendingGuaranteeIssuerSms();
            }

            // Process message content
            final String processedSubject = MessageProcessingHelper.processVariables(subject, guarantee, localSettings);
            final String processedBody = MessageProcessingHelper.processVariables(body, guarantee, localSettings);
            final String processedSms = MessageProcessingHelper.processVariables(sms, guarantee, localSettings);

            // Create the DTO
            final SendMessageFromSystemDTO message = new SendMessageFromSystemDTO();
            message.setEntity(guarantee);
            message.setType(Message.Type.GUARANTEE);
            message.setToMember(toMember);
            message.setSubject(processedSubject);
            message.setBody(processedBody);
            message.setSms(processedSms);

            // Send the message
            messageService.sendFromSystem(message);
        }
    }

    @Override
    public void guaranteeStatusChangedNotification(final Guarantee guarantee, final Guarantee.Status prevStatus) {
        doGuaranteeStatusChangedNotification(guarantee, prevStatus);
    }

    @Override
    public void newCommissionContractNotification(final BrokerCommissionContract brokerCommissionContract) {
        // Get message settings
        final MessageSettings messageSettings = settingsService.getMessageSettings();
        final String subject = messageSettings.getNewCommissionContractSubject();
        final String body = messageSettings.getNewCommissionContractMessage();
        final String sms = messageSettings.getNewCommissionContractSms();

        // Process message content
        final LocalSettings localSettings = settingsService.getLocalSettings();
        final String processedSubject = MessageProcessingHelper.processVariables(subject, brokerCommissionContract, localSettings);
        final String processedBody = MessageProcessingHelper.processVariables(body, brokerCommissionContract, localSettings);
        final String processedSms = MessageProcessingHelper.processVariables(sms, brokerCommissionContract, localSettings);

        // Create the DTO
        final SendMessageFromSystemDTO message = new SendMessageFromSystemDTO();
        message.setEntity(brokerCommissionContract);
        message.setType(Message.Type.BROKERING);
        message.setToMember(brokerCommissionContract.getBrokering().getBrokered());
        message.setSubject(processedSubject);
        message.setBody(processedBody);
        message.setSms(processedSms);

        // Send the message
        messageService.sendFromSystem(message);
    }

    @Override
    public void paymentAuthorizedOrDeniedNotification(Transfer transfer, final boolean notifyTransactionFeedbackRequest) {
        transfer = fetchService.reload(transfer, Transfer.Relationships.AUTHORIZATIONS);
        if (CollectionUtils.isEmpty(transfer.getAuthorizations())) {
            return;
        }
        final AccountOwner fromOwner = transfer.getFromOwner();
        final AccountOwner toOwner = transfer.getToOwner();
        final List<Member> sendMessageTo = new ArrayList<Member>();
        final boolean loggedAsPayer = LoggedUser.hasUser() && LoggedUser.isMember() && LoggedUser.accountOwner().equals(fromOwner);
        if (!loggedAsPayer && (fromOwner instanceof Member)) {
            sendMessageTo.add((Member) fromOwner);
        }
        final boolean loggedAsReceiver = LoggedUser.hasUser() && LoggedUser.isMember() && LoggedUser.accountOwner().equals(toOwner);
        if (!loggedAsReceiver && (toOwner instanceof Member)) {
            sendMessageTo.add((Member) toOwner);
        }

        if (!sendMessageTo.isEmpty()) {
            // Get the message settings
            final MessageSettings messageSettings = settingsService.getMessageSettings();
            String subject;
            String body;
            String sms;
            switch (transfer.getStatus()) {
                case PROCESSED:
                    // Was authorized and processed
                    subject = messageSettings.getPendingPaymentAuthorizedSubject();
                    body = messageSettings.getPendingPaymentAuthorizedMessage();
                    sms = messageSettings.getPendingPaymentAuthorizedSms();
                    break;
                case PENDING:
                    // Was authorized but needs higher level
                    if (fromOwner instanceof Member) {
                        final Authorizer authorizer = transfer.getNextAuthorizationLevel().getAuthorizer();
                        final LocalSettings localSettings = settingsService.getLocalSettings();
                        final Member fromMember;
                        switch (authorizer) {
                            case ADMIN:
                                adminNotificationHandler.notifyNewPendingPayment(transfer);
                                break;
                            case BROKER:
                                // Notify the broker if he currently has to authorize
                                fromMember = fetchService.fetch((Member) fromOwner, Member.Relationships.BROKER);
                                final Member broker = fromMember.getBroker();
                                if (broker != null) {
                                    subject = messageSettings.getNewPendingPaymentByBrokerSubject();
                                    body = messageSettings.getNewPendingPaymentByBrokerMessage();
                                    sms = messageSettings.getNewPendingPaymentByBrokerSms();
                                    final String processedSubject = MessageProcessingHelper.processVariables(subject, localSettings, fromMember, transfer);
                                    final String processedBody = MessageProcessingHelper.processVariables(body, localSettings, fromMember, transfer);
                                    final String processedSms = MessageProcessingHelper.processVariables(sms, localSettings, fromMember, transfer);

                                    // Send the message
                                    final SendMessageFromSystemDTO message = new SendMessageFromSystemDTO();
                                    message.setType(Message.Type.BROKERING);
                                    message.setEntity(transfer);
                                    message.setToMember(broker);
                                    message.setSubject(processedSubject);
                                    message.setBody(processedBody);
                                    message.setSms(processedSms);
                                    messageService.sendFromSystem(message);
                                }
                                break;
                            case PAYER:
                                // Notify the payer if he currently has to authorize
                                fromMember = (Member) fromOwner;
                                final Member toMember = (Member) toOwner;
                                subject = messageSettings.getNewPendingPaymentByPayerSubject();
                                body = messageSettings.getNewPendingPaymentByPayerMessage();
                                sms = messageSettings.getNewPendingPaymentByPayerSms();
                                final String processedSubject = MessageProcessingHelper.processVariables(subject, localSettings, toMember, transfer);
                                final String processedBody = MessageProcessingHelper.processVariables(body, localSettings, toMember, transfer);
                                final String processedSms = MessageProcessingHelper.processVariables(sms, localSettings, toMember, transfer);

                                // Send the message
                                final SendMessageFromSystemDTO message = new SendMessageFromSystemDTO();
                                message.setType(Message.Type.PAYMENT);
                                message.setEntity(transfer);
                                message.setToMember(fromMember);
                                message.setSubject(processedSubject);
                                message.setBody(processedBody);
                                message.setSms(processedSms);
                                messageService.sendFromSystem(message);
                                break;
                            case RECEIVER:
                                // nothing to do because this level could be only the first
                                // and this method is called after the first authorization
                                break;
                        }
                    }
                    // If necessary, the message was already sent or the payment needs another authorization level
                    return;
                case DENIED:
                    // Was denied
                    subject = messageSettings.getPendingPaymentDeniedSubject();
                    body = messageSettings.getPendingPaymentDeniedMessage();
                    sms = messageSettings.getPendingPaymentDeniedSms();
                    break;
                default:
                    // Unknown status.
                    return;
            }

            // Send the messages
            sendPaymentMessages(transfer, sendMessageTo, subject, body, sms);
        }

        // Send the transaction feedback request message
        if (notifyTransactionFeedbackRequest) {
            notifyTransactionFeedbackRequest(transfer);
        }
    }

    @Override
    public void paymentCancelledNotification(final Transfer transfer) {
        final AccountOwner fromOwner = transfer.getFromOwner();
        final AccountOwner toOwner = transfer.getToOwner();
        final boolean loggedAsSender = LoggedUser.hasUser() && LoggedUser.isMember() && LoggedUser.accountOwner().equals(fromOwner);
        final List<Member> sendMessageTo = new ArrayList<Member>();
        if (!loggedAsSender && (fromOwner instanceof Member)) {
            sendMessageTo.add((Member) fromOwner);
        }
        if (toOwner instanceof Member) {
            sendMessageTo.add((Member) toOwner);
        }

        // Get the message settings
        final MessageSettings messageSettings = settingsService.getMessageSettings();
        final String subject = messageSettings.getPendingPaymentCanceledSubject();
        final String body = messageSettings.getPendingPaymentCanceledMessage();
        final String sms = messageSettings.getPendingPaymentCanceledSms();

        // Send the messages
        sendPaymentMessages(transfer, sendMessageTo, subject, body, sms);
    }

    @Override
    public void paymentObligationPublishedNotification(final PaymentObligation paymentObligation) {
        // Get local and message settings
        final LocalSettings localSettings = settingsService.getLocalSettings();
        final MessageSettings messageSettings = settingsService.getMessageSettings();

        // Notify the seller
        final Member toMember = paymentObligation.getSeller();
        final String subject = messageSettings.getPaymentObligationRegisteredSubject();
        final String body = messageSettings.getPaymentObligationRegisteredMessage();
        final String sms = messageSettings.getPaymentObligationRegisteredSms();

        // Process message content
        final String processedSubject = MessageProcessingHelper.processVariables(subject, paymentObligation, localSettings);
        final String processedBody = MessageProcessingHelper.processVariables(body, paymentObligation, localSettings);
        final String processedSms = MessageProcessingHelper.processVariables(sms, paymentObligation, localSettings);

        // Create the message DTO
        final SendMessageFromSystemDTO message = new SendMessageFromSystemDTO();
        message.setEntity(paymentObligation);
        message.setType(Message.Type.PAYMENT_OBLIGATION);
        message.setSubject(processedSubject);
        message.setBody(processedBody);
        message.setSms(processedSms);
        message.setToMember(toMember);
        messageService.sendFromSystem(message);
    }

    @Override
    public void paymentObligationRejectedNotification(final PaymentObligation paymentObligation) {

        // Get local and message settings
        final LocalSettings localSettings = settingsService.getLocalSettings();
        final MessageSettings messageSettings = settingsService.getMessageSettings();

        // Notify the buyer
        final Member toMember = paymentObligation.getBuyer();
        final String subject = messageSettings.getPaymentObligationRejectedSubject();
        final String body = messageSettings.getPaymentObligationRejectedMessage();
        final String sms = messageSettings.getPaymentObligationRejectedSms();

        // Process message content
        final String processedSubject = MessageProcessingHelper.processVariables(subject, paymentObligation, localSettings);
        final String processedBody = MessageProcessingHelper.processVariables(body, paymentObligation, localSettings);
        final String processedSms = MessageProcessingHelper.processVariables(sms, paymentObligation, localSettings);

        // Create the message DTO
        final SendMessageFromSystemDTO message = new SendMessageFromSystemDTO();
        message.setEntity(paymentObligation);
        message.setType(Message.Type.PAYMENT_OBLIGATION);
        message.setSubject(processedSubject);
        message.setBody(processedBody);
        message.setSms(processedSms);
        message.setToMember(toMember);
        messageService.sendFromSystem(message);
    }

    @Override
    public void paymentReceivedNotification(final Payment payment) {
        // A message is sent only when the payment is a transfer and the destination is a member (not system)
        if (payment instanceof Transfer && payment.getToOwner() instanceof Member) {
            final Transfer transfer = (Transfer) payment;

            // Only send notifications for root transfers
            if (!transfer.isRoot()) {
                return;
            }
            final MessageSettings messageSettings = settingsService.getMessageSettings();
            final LocalSettings localSettings = settingsService.getLocalSettings();

            // Get the transfer authorizer, if any
            final AuthorizationLevel nextAuthorizationLevel = transfer.getNextAuthorizationLevel();
            final Authorizer authorizer = nextAuthorizationLevel == null ? null : nextAuthorizationLevel.getAuthorizer();

            // Get the destination
            final Member destinationMember = (Member) payment.getToOwner();
            final Set<MessageChannel> channels = preferenceService.receivedChannels(destinationMember, Message.Type.PAYMENT);
            if (!channels.isEmpty()) {
                // The account status is only used in messages via SMS
                final boolean sendSmsNotification = transfer.getType().isAllowSmsNotification() && channels.contains(MessageChannel.SMS);
                AccountStatus status = null;
                if (sendSmsNotification) {
                    status = accountService.getCurrentStatus(new AccountDTO(transfer.getTo()));
                }

                // Get the message settings
                String subject = null;
                String body = null;
                String sms = null;

                // Check if the transfer has been processed or awaits authorization
                if (transfer.getProcessDate() == null) {
                    if (authorizer == Authorizer.RECEIVER) {
                        subject = messageSettings.getNewPendingPaymentByReceiverSubject();
                        body = messageSettings.getNewPendingPaymentByReceiverMessage();
                        if (sendSmsNotification) {
                            sms = messageSettings.getNewPendingPaymentByReceiverSms();
                        }
                    } else {
                        subject = messageSettings.getPendingPaymentReceivedSubject();
                        body = messageSettings.getPendingPaymentReceivedMessage();
                        if (sendSmsNotification) {
                            sms = messageSettings.getPendingPaymentReceivedSms();
                        }
                    }
                } else {
                    subject = messageSettings.getPaymentReceivedSubject();
                    body = messageSettings.getPaymentReceivedMessage();
                    if (sendSmsNotification) {
                        sms = messageSettings.getPaymentReceivedSms();
                    }
                }

                // Process message contents
                final AccountOwner fromAccountOwner = payment.getFromOwner();
                final String processedSubject = MessageProcessingHelper.processVariables(subject, localSettings, fromAccountOwner, payment);
                final String processedBody = MessageProcessingHelper.processVariables(body, localSettings, fromAccountOwner, payment);
                final String processedSms = sendSmsNotification ? MessageProcessingHelper.processVariables(sms, localSettings, fromAccountOwner, status, payment) : null;

                // Create the DTO
                final SendMessageFromSystemDTO message = new SendMessageFromSystemDTO();
                message.setType(Message.Type.PAYMENT);
                message.setEntity(payment);
                message.setToMember(destinationMember);
                message.setSubject(processedSubject);
                message.setBody(processedBody);
                message.setSms(processedSms);
                message.setSmsTraceData(transfer.getTraceData());

                // Send the message
                messageService.sendFromSystem(message);
            }

            // Notify the broker
            final Member fromMember = fetchService.fetch(payment.isFromSystem() ? null : (Member) payment.getFromOwner(), Member.Relationships.BROKER);
            final Member broker = fromMember == null ? null : fromMember.getBroker();
            if (authorizer == Authorizer.BROKER && broker != null) {
                final String subject = messageSettings.getNewPendingPaymentByBrokerSubject();
                final String body = messageSettings.getNewPendingPaymentByBrokerMessage();
                final String sms = messageSettings.getNewPendingPaymentByBrokerSms();
                final String processedSubject = MessageProcessingHelper.processVariables(subject, localSettings, fromMember, payment);
                final String processedBody = MessageProcessingHelper.processVariables(body, localSettings, fromMember, payment);
                final String processedSms = MessageProcessingHelper.processVariables(sms, localSettings, fromMember, payment);

                final SendMessageFromSystemDTO message = new SendMessageFromSystemDTO();
                message.setType(Message.Type.BROKERING);
                message.setEntity(payment);
                message.setToMember(broker);
                message.setSubject(processedSubject);
                message.setBody(processedBody);
                message.setSms(processedSms);

                // Send the message
                messageService.sendFromSystem(message);
            }
        }

        // Request a transaction feedback for the payment source
        notifyTransactionFeedbackRequest(payment);

        // Perform the low units notification, if needed
        notifyLowUnits(payment);
    }

    @Override
    public void posPinBlockedNotification(final MemberPos memberPos) {

        Member member = memberPos.getMember();
        Pos pos = memberPos.getPos();

        // Get message settings
        final MessageSettings messageSettings = settingsService.getMessageSettings();
        final String subject = messageSettings.getPosPinBlockedSubject();
        final String body = messageSettings.getPosPinBlockedMessage();
        final String sms = messageSettings.getPosPinBlockedSms();

        final LocalSettings localSettings = settingsService.getLocalSettings();
        final String processedSubject = MessageProcessingHelper.processVariables(subject, pos, localSettings);
        final String processedBody = MessageProcessingHelper.processVariables(body, pos, localSettings);
        final String processedSms = MessageProcessingHelper.processVariables(sms, pos, localSettings);

        // Create the DTO
        final SendMessageFromSystemDTO message = new SendMessageFromSystemDTO();
        message.setType(Message.Type.ACCESS);
        message.setToMember(member);
        message.setSubject(processedSubject);
        message.setBody(processedBody);
        message.setSms(processedSms);

        // Send the message
        messageService.sendFromSystem(message);
    }

    @Override
    public void receivedInvoiceNotification(final Invoice invoice) {
        // Get the destination
        final Member destinationMember = invoice.getToMember();

        // Get the message settings
        final MessageSettings messageSettings = settingsService.getMessageSettings();
        final String subject = messageSettings.getInvoiceReceivedSubject();
        final String body = messageSettings.getInvoiceReceivedMessage();
        final String sms = messageSettings.getInvoiceReceivedSms();

        // Process message content
        final LocalSettings localSettings = settingsService.getLocalSettings();
        final String processedSubject = MessageProcessingHelper.processVariables(subject, localSettings, invoice.getFrom(), invoice);
        final String processedBody = MessageProcessingHelper.processVariables(body, localSettings, invoice.getFrom(), invoice);
        final String processedSms = MessageProcessingHelper.processVariables(sms, localSettings, invoice.getFrom(), invoice);

        // Create the DTO
        final SendMessageFromSystemDTO message = new SendMessageFromSystemDTO();
        message.setType(Message.Type.INVOICE);
        message.setEntity(invoice);
        message.setToMember(destinationMember);
        message.setSubject(processedSubject);
        message.setBody(processedBody);
        message.setSms(processedSms);

        // Send the message
        messageService.sendFromSystem(message);
    }

    @Override
    public void receivedReferenceNotification(final Reference reference) {
        // Get the destination
        final Member destinationMember = reference.getTo();

        // Get the message settings
        final MessageSettings messageSettings = settingsService.getMessageSettings();
        final String subject = messageSettings.getReferenceReceivedSubject();
        final String body = messageSettings.getReferenceReceivedMessage();
        final String sms = messageSettings.getReferenceReceivedSms();

        // Process message contents
        final LocalSettings localSettings = settingsService.getLocalSettings();
        final Member fromMember = reference.getFrom();
        final String processedSubject = MessageProcessingHelper.processVariables(subject, localSettings, fromMember, reference);
        final String processedBody = MessageProcessingHelper.processVariables(body, localSettings, fromMember, reference);
        final String processedSms = MessageProcessingHelper.processVariables(sms, localSettings, fromMember, reference);

        // Create the DTO
        final SendMessageFromSystemDTO message = new SendMessageFromSystemDTO();
        message.setType(Message.Type.REFERENCE);
        message.setEntity(reference);
        message.setToMember(destinationMember);
        message.setSubject(processedSubject);
        message.setBody(processedBody);
        message.setSms(processedSms);

        // Send the message
        messageService.sendFromSystem(message);
    }

    @Override
    public void removedBrokeringNotification(final ChangeBrokerDTO dto) {
        final Member member = fetchService.fetch(dto.getMember());
        final Brokering oldBrokering = brokeringService.getActiveBrokering(member);
        final Member oldBroker = (oldBrokering == null) ? null : oldBrokering.getBroker();
        final Member newBroker = dto.getNewBroker();

        final boolean justSuspendCommission = (oldBroker != null && oldBroker.equals(newBroker) && dto.isSuspendCommission());
        if (!justSuspendCommission && oldBroker != null) {
            // Get message settings
            final MessageSettings messageSettings = settingsService.getMessageSettings();
            final String subject = messageSettings.getBrokeringRemovedSubject();
            final String body = messageSettings.getBrokeringRemovedMessage();
            final String sms = messageSettings.getBrokeringRemovedSms();

            // Process message body
            final LocalSettings localSettings = settingsService.getLocalSettings();
            final String processedSubject = MessageProcessingHelper.processVariables(subject, member, localSettings);
            final String processedBody = MessageProcessingHelper.processVariables(body, member, localSettings);
            final String processedSms = MessageProcessingHelper.processVariables(sms, member, localSettings);

            // Create the DTO
            final SendMessageFromSystemDTO message = new SendMessageFromSystemDTO();
            message.setType(Message.Type.BROKERING);
            message.setToMember(oldBroker);
            message.setSubject(processedSubject);
            message.setBody(processedBody);
            message.setSms(processedSms);

            // Send the message
            messageService.sendFromSystem(message);
        }
    }

    @Override
    public void removedFromBrokerGroupNotification(final Member member) {
        Group group = member.getGroup();

        // Get message settings
        final MessageSettings messageSettings = settingsService.getMessageSettings();
        final String subject = messageSettings.getRemovedFromBrokerGroupSubject();
        final String body = messageSettings.getRemovedFromBrokerGroupMessage();
        final String sms = messageSettings.getRemovedFromBrokerGroupSms();

        // Process message content
        final LocalSettings localSettings = settingsService.getLocalSettings();
        final String processedSubject = MessageProcessingHelper.processVariables(subject, group, localSettings);
        final String processedBody = MessageProcessingHelper.processVariables(body, group, localSettings);
        final String processedSms = MessageProcessingHelper.processVariables(sms, group, localSettings);

        // Create the DTO
        final SendMessageFromSystemDTO message = new SendMessageFromSystemDTO();
        message.setType(Message.Type.BROKERING);
        message.setToMember(member);
        message.setSubject(processedSubject);
        message.setBody(processedBody);
        message.setSms(processedSms);

        // Send the message
        messageService.sendFromSystem(message);
    }

    @Override
    public void scheduledPaymentProcessingNotification(final Transfer transfer, final boolean notifyPayer, final boolean notifyReceiver) {
        notifyScheduledPaymentProcessing(transfer, notifyPayer, notifyReceiver);
    }

    @Override
    public void scheduledPaymentsCancelledNotification(final Member member, final boolean notifyMember, final Set<Member> membersToNotify, final Set<MemberAccountType> removedAccounts) {
        final MessageSettings messageSettings = settingsService.getMessageSettings();
        final LocalSettings localSettings = settingsService.getLocalSettings();

        Map<String, Object> context = new HashMap<String, Object>();
        context.putAll(member.getVariableValues(localSettings));

        String processedSubject = MessageProcessingHelper.processVariables(messageSettings.getScheduledPaymentsCancelledToOtherSubject(), context);
        String processedBody = MessageProcessingHelper.processVariables(messageSettings.getScheduledPaymentsCancelledToOtherMessage(), context);
        String processedSms = MessageProcessingHelper.processVariables(messageSettings.getScheduledPaymentsCancelledToOtherSms(), context);

        for (Member m : membersToNotify) {
            // Create the DTO
            final SendMessageFromSystemDTO message = new SendMessageFromSystemDTO();
            message.setType(Message.Type.PAYMENT);
            message.setToMember(m);
            message.setSubject(processedSubject);
            message.setBody(processedBody);
            message.setSms(processedSms);

            // Send the message
            messageService.sendFromSystem(message);
        }

        if (notifyMember) {
            context.put("accounts", getAccountNames(removedAccounts));
            processedSubject = MessageProcessingHelper.processVariables(messageSettings.getScheduledPaymentsCancelledSubject(), context);
            processedBody = MessageProcessingHelper.processVariables(messageSettings.getScheduledPaymentsCancelledMessage(), context);
            processedSms = MessageProcessingHelper.processVariables(messageSettings.getScheduledPaymentsCancelledSms(), context);

            // Create the DTO
            final SendMessageFromSystemDTO message = new SendMessageFromSystemDTO();
            message.setType(Message.Type.PAYMENT);
            message.setToMember(member);
            message.setSubject(processedSubject);
            message.setBody(processedBody);
            message.setSms(processedSms);

            // Send the message
            messageService.sendFromSystem(message);
        }
    }

    public void setAccountServiceLocal(final AccountServiceLocal accountService) {
        this.accountService = accountService;
    }

    public void setAdminNotificationHandler(final AdminNotificationHandler adminNotificationHandler) {
        this.adminNotificationHandler = adminNotificationHandler;
    }

    public void setBrokeringServiceLocal(final BrokeringServiceLocal brokeringService) {
        this.brokeringService = brokeringService;
    }

    public void setCertificationServiceLocal(final CertificationServiceLocal certificationService) {
        this.certificationService = certificationService;
    }

    public void setChannelServiceLocal(final ChannelServiceLocal channelService) {
        this.channelService = channelService;
    }

    public void setFetchServiceLocal(final FetchServiceLocal fetchService) {
        this.fetchService = fetchService;
    }

    public void setGroupServiceLocal(final GroupServiceLocal groupService) {
        this.groupService = groupService;
    }

    public void setInvoiceServiceLocal(final InvoiceServiceLocal invoiceService) {
        this.invoiceService = invoiceService;
    }

    public void setMessageResolver(final MessageResolver messageResolver) {
        this.messageResolver = messageResolver;
    }

    public void setMessageServiceLocal(final MessageServiceLocal messageService) {
        this.messageService = messageService;
    }

    public void setPreferenceServiceLocal(final PreferenceServiceLocal preferenceService) {
        this.preferenceService = preferenceService;
    }

    public void setSettingsServiceLocal(final SettingsServiceLocal settingsService) {
        this.settingsService = settingsService;
    }

    @Override
    public void transactionFeedBackAdminCommentsNotification(final TransactionFeedback transactionFeedback) {
        // Get the message settings
        final MessageSettings messageSettings = settingsService.getMessageSettings();
        final String subject = messageSettings.getTransactionFeedbackAdminCommentsSubject();
        final String body = messageSettings.getTransactionFeedbackAdminCommentsMessage();
        final String sms = messageSettings.getTransactionFeedbackAdminCommentsSms();

        // Process message body
        final LocalSettings localSettings = settingsService.getLocalSettings();

        // Send the notification to the feedback writer
        {
            final String processedSubject = MessageProcessingHelper.processVariables(subject, localSettings, transactionFeedback.getTo(), transactionFeedback.getTransfer());
            final String processedBody = MessageProcessingHelper.processVariables(body, localSettings, transactionFeedback.getTo(), transactionFeedback.getTransfer());
            final String processedSms = MessageProcessingHelper.processVariables(sms, localSettings, transactionFeedback.getTo(), transactionFeedback.getTransfer());

            // Create the DTO
            final SendMessageFromSystemDTO message = new SendMessageFromSystemDTO();
            message.setType(Message.Type.TRANSACTION_FEEDBACK);
            message.setEntity(transactionFeedback);
            message.setToMember(transactionFeedback.getFrom());
            message.setSubject(processedSubject);
            message.setBody(processedBody);
            message.setSms(processedSms);

            // Send the message
            messageService.sendFromSystem(message);
        }

        // Send the notification to the feedback receiver
        {
            final String processedSubject = MessageProcessingHelper.processVariables(subject, localSettings, transactionFeedback.getFrom(), transactionFeedback.getTransfer());
            final String processedBody = MessageProcessingHelper.processVariables(body, localSettings, transactionFeedback.getFrom(), transactionFeedback.getTransfer());

            // Create the DTO
            final SendMessageFromSystemDTO message = new SendMessageFromSystemDTO();
            message.setType(Message.Type.TRANSACTION_FEEDBACK);
            message.setEntity(transactionFeedback);
            message.setToMember(transactionFeedback.getTo());
            message.setSubject(processedSubject);
            message.setBody(processedBody);

            // Send the message
            messageService.sendFromSystem(message);
        }
    }

    @Override
    public void transactionFeedBackReceivedNotification(final TransactionFeedback transactionFeedback) {
        // Get the message settings
        final MessageSettings messageSettings = settingsService.getMessageSettings();
        final String subject = messageSettings.getTransactionFeedbackReceivedSubject();
        final String body = messageSettings.getTransactionFeedbackReceivedMessage();
        final String sms = messageSettings.getTransactionFeedbackReceivedSms();

        final LocalSettings localSettings = settingsService.getLocalSettings();
        final Map<String, Object> extraVariables = new HashMap<String, Object>();
        final Payment payment = transactionFeedback.getPayment();
        final Calendar limit = payment.getType().getFeedbackReplyExpirationTime().add(Calendar.getInstance());
        extraVariables.put("limit", localSettings.getDateConverter().toString(limit));

        // Process the message
        String processedSubject = MessageProcessingHelper.processVariables(subject, localSettings, transactionFeedback.getFrom(), payment);
        processedSubject = MessageProcessingHelper.processVariables(processedSubject, extraVariables);
        String processedBody = MessageProcessingHelper.processVariables(body, localSettings, transactionFeedback.getFrom(), payment);
        processedBody = MessageProcessingHelper.processVariables(processedBody, extraVariables);
        String processedSms = MessageProcessingHelper.processVariables(sms, localSettings, transactionFeedback.getFrom(), payment);
        processedSms = MessageProcessingHelper.processVariables(processedSms, extraVariables);

        // Create the DTO
        final SendMessageFromSystemDTO message = new SendMessageFromSystemDTO();
        message.setType(Message.Type.TRANSACTION_FEEDBACK);
        message.setEntity(transactionFeedback);
        message.setToMember(transactionFeedback.getTo());
        message.setSubject(processedSubject);
        message.setBody(processedBody);
        message.setSms(processedSms);

        // Send the message
        messageService.sendFromSystem(message);
    }

    @Override
    public void transactionFeedBackReplyNotification(final TransactionFeedback transactionFeedback) {
        // Get the message settings
        final MessageSettings messageSettings = settingsService.getMessageSettings();
        final String subject = messageSettings.getTransactionFeedbackReplySubject();
        final String body = messageSettings.getTransactionFeedbackReplyMessage();
        final String sms = messageSettings.getTransactionFeedbackReplySms();

        // Process message body
        final LocalSettings localSettings = settingsService.getLocalSettings();
        final String processedSubject = MessageProcessingHelper.processVariables(subject, localSettings, transactionFeedback.getTo(), transactionFeedback.getTransfer());
        final String processedBody = MessageProcessingHelper.processVariables(body, localSettings, transactionFeedback.getTo(), transactionFeedback.getTransfer());
        final String processedSms = MessageProcessingHelper.processVariables(sms, localSettings, transactionFeedback.getTo(), transactionFeedback.getTransfer());

        // Create the DTO
        final SendMessageFromSystemDTO message = new SendMessageFromSystemDTO();
        message.setType(Message.Type.TRANSACTION_FEEDBACK);
        message.setEntity(transactionFeedback);
        message.setToMember(transactionFeedback.getFrom());
        message.setSubject(processedSubject);
        message.setBody(processedBody);
        message.setSms(processedSms);

        // Send the message
        messageService.sendFromSystem(message);
    }

    private void doGuaranteeStatusChangedNotification(final Guarantee guarantee, final Guarantee.Status prevStatus) {
        // Get local and message settings
        final LocalSettings localSettings = settingsService.getLocalSettings();
        final MessageSettings messageSettings = settingsService.getMessageSettings();

        // Process new status string
        final String statusString = messageResolver.message("guarantee.status." + guarantee.getStatus().toString());
        final Map<String, Object> variables = guarantee.getVariableValues(localSettings);
        variables.put("status", statusString);

        final Guarantee.Status newStatus = guarantee.getStatus();

        // If the guarantee was accepted, denied or cancelled, notify members
        if (newStatus == Guarantee.Status.ACCEPTED || newStatus == Guarantee.Status.REJECTED || newStatus == Guarantee.Status.CANCELLED) {

            // Check if the model of the guarantee is "buyer only"
            final boolean buyerOnly = guarantee.getGuaranteeType().getModel() == GuaranteeType.Model.WITH_BUYER_ONLY;

            // Get the destination
            final Member buyer = guarantee.getBuyer();
            final Member seller = guarantee.getSeller();
            final Member issuer = guarantee.getIssuer();

            // Get the message settings
            String subject = null;
            String body = null;
            String sms = null;
            if (buyerOnly) {
                subject = messageSettings.getBuyerOnlyGuaranteeStatusChangedSubject();
                body = messageSettings.getBuyerOnlyGuaranteeStatusChangedMessage();
                sms = messageSettings.getBuyerOnlyGuaranteeStatusChangedSms();
            } else {
                subject = messageSettings.getGuaranteeStatusChangedSubject();
                body = messageSettings.getGuaranteeStatusChangedMessage();
                sms = messageSettings.getGuaranteeStatusChangedSms();
            }

            // Process message content
            final String processedSubject = MessageProcessingHelper.processVariables(subject, variables);
            final String processedBody = MessageProcessingHelper.processVariables(body, variables);
            final String processedSms = MessageProcessingHelper.processVariables(sms, variables);

            // Create the message DTO
            final SendMessageFromSystemDTO message = new SendMessageFromSystemDTO();
            message.setEntity(guarantee);
            message.setType(Message.Type.GUARANTEE);

            // Send the message to the buyer
            message.setToMember(buyer);
            message.setSubject(processedSubject);
            message.setBody(processedBody);
            message.setSms(processedSms);

            messageService.sendFromSystem(message);

            // If the guarantee was cancelled, notify the issuer too
            if (newStatus == Guarantee.Status.CANCELLED) {
                message.setToMember(issuer);
                message.setSubject(processedSubject);
                message.setBody(processedBody);
                messageService.sendFromSystem(message);
            }

            // If the model is not "buyer only", notify the seller too
            if (!buyerOnly) {
                message.setToMember(seller);
                message.setSubject(processedSubject);
                message.setBody(processedBody);
                messageService.sendFromSystem(message);
            }
        } else if (newStatus == Guarantee.Status.WITHOUT_ACTION && prevStatus == Guarantee.Status.PENDING_ISSUER) { // If the guarantee has expired,
            // Notify the issuer
            // Get the destination
            final Member issuer = guarantee.getIssuer();

            // Get the message settings
            final String subject = messageSettings.getExpiredGuaranteeSubject();
            final String body = messageSettings.getExpiredGuaranteeMessage();
            final String sms = messageSettings.getExpiredGuaranteeSms();

            // Process message content
            final String processedSubject = MessageProcessingHelper.processVariables(subject, variables);
            final String processedBody = MessageProcessingHelper.processVariables(body, variables);
            final String processedSms = MessageProcessingHelper.processVariables(sms, variables);

            // Create the message DTO
            final SendMessageFromSystemDTO message = new SendMessageFromSystemDTO();
            message.setEntity(guarantee);
            message.setType(Message.Type.GUARANTEE);
            message.setSubject(processedSubject);
            message.setBody(processedBody);
            message.setToMember(issuer);
            message.setSms(processedSms);

            // Send the message to the issuer
            messageService.sendFromSystem(message);
        }
    }

    /**
     * Notify the owner of the given account if it is getting low units. In a separated method as it need to be synchronized
     */
    private synchronized void doSendLowUnitsNotification(final MemberAccount account, final MemberGroupAccountSettings mgas) {
        final Member fromOwner = account.getOwner();
        // We could send the message. Check the last date it was sent
        Calendar lastLowUnitsSent = account.getLastLowUnitsSent();
        if (lastLowUnitsSent != null && DateHelper.decimalDaysBetween(lastLowUnitsSent, Calendar.getInstance()).doubleValue() < 1) {
            // Already sent a low units notification in less than 1 day. Skip.
            return;
        }
        AccountStatus status = accountService.getCurrentStatus(new AccountDTO(account));
        if (status.getAvailableBalance().compareTo(mgas.getLowUnits()) > 0) {
            // The balance is greater than the low units
            return;
        }
        // Get the message settings
        final MessageSettings messageSettings = settingsService.getMessageSettings();
        final String subject = messageSettings.getLowUnitsSubject();
        String body = mgas.getLowUnitsMessage();
        // The message in the account settings overrides the message setting
        if (StringUtils.isEmpty(body)) {
            body = messageSettings.getLowUnitsMessage();
        }
        final String sms = messageSettings.getLowUnitsSms();

        // Process message content
        final LocalSettings localSettings = settingsService.getLocalSettings();
        final Map<String, Object> variables = new HashMap<String, Object>();
        variables.putAll(fromOwner.getVariableValues(localSettings));
        variables.putAll(account.getVariableValues(localSettings));
        variables.putAll(status.getVariableValues(localSettings));
        final String processedSubject = MessageProcessingHelper.processVariables(subject, variables);
        final String processedBody = MessageProcessingHelper.processVariables(body, variables);
        final String processedSms = MessageProcessingHelper.processVariables(sms, variables);

        // Create the DTO
        final SendMessageFromSystemDTO message = new SendMessageFromSystemDTO();
        message.setType(Message.Type.ACCOUNT);
        message.setToMember(fromOwner);
        message.setSubject(processedSubject);
        message.setBody(processedBody);
        message.setSms(processedSms);

        // Send the message
        messageService.sendFromSystem(message);

        // Update the last low units sent, so we won't send another message in the same day
        account.setLastLowUnitsSent(Calendar.getInstance());
    }

    private void externalChannelPaymentNotification(final Payment payment, final Channel fromChannel, final Channel toChannel) {
        // Get local and message settings
        final LocalSettings localSettings = settingsService.getLocalSettings();
        final MessageSettings messageSettings = settingsService.getMessageSettings();
        final boolean isPaymentConfirmation = toChannel != null;

        final Channel smsChannel = channelService.getSmsChannel();

        // if it isn't then is a payment from System
        if (payment.getFromOwner() instanceof Member) {
            final Channel channelToCheckForPayer = isPaymentConfirmation ? toChannel : fromChannel;
            final boolean skipSms = channelToCheckForPayer != null && channelToCheckForPayer.equals(smsChannel);

            // Get the origin
            final Member fromMember = (Member) payment.getFromOwner();

            // Get the message settings
            final String subject = messageSettings.getExternalChannelPaymentPerformedSubject();
            final String body = messageSettings.getExternalChannelPaymentPerformedMessage();
            final String sms = skipSms ? null : messageSettings.getExternalChannelPaymentPerformedSms();

            // Process message content
            final Map<String, Object> variableValues = payment.getVariableValues(localSettings);
            variableValues.put("channel", channelToCheckForPayer.getDisplayName());
            final AccountOwner toOwner = payment.getToOwner();
            String processedSubject = MessageProcessingHelper.processVariables(subject, localSettings, toOwner, payment);
            processedSubject = MessageProcessingHelper.processVariables(processedSubject, variableValues);
            String processedBody = MessageProcessingHelper.processVariables(body, localSettings, toOwner, payment);
            processedBody = MessageProcessingHelper.processVariables(processedBody, variableValues);
            String processedSms = null;
            if (sms != null) {
                processedSms = MessageProcessingHelper.processVariables(sms, localSettings, toOwner, payment);
                processedSms = MessageProcessingHelper.processVariables(processedSms, variableValues);
            }

            // Create the DTO
            final SendMessageFromSystemDTO message = new SendMessageFromSystemDTO();
            message.setToMember(fromMember);
            message.setType(Message.Type.EXTERNAL_PAYMENT);
            message.setEntity(payment);
            message.setSubject(processedSubject);
            message.setBody(processedBody);
            message.setSms(processedSms);

            // Send the message
            messageService.sendFromSystem(message);
        }
        // Now, notify the member that received the payment, only if not to channel is SMS
        final Channel channelToCheckForReceiver = isPaymentConfirmation ? fromChannel : toChannel;
        if (channelToCheckForReceiver == null || !channelToCheckForReceiver.equals(smsChannel)) {
            paymentReceivedNotification(payment);
        }
    }

    private String getAccountNames(final Set<MemberAccountType> removedAccounts) {
        StringBuilder str = new StringBuilder();
        for (MemberAccountType type : removedAccounts) {
            if (str.length() > 0) {
                str.append(", ");
            }
            str.append(type.getName());
        }

        return str.toString();
    }

    /**
     * Send the low units notification if needed
     */
    private void notifyLowUnits(final Payment payment) {
        if (!(payment instanceof Transfer)) {
            return;
        }
        final Account account = fetchService.fetch(payment.getFrom(), RelationshipHelper.nested(Account.Relationships.TYPE, AccountType.Relationships.CURRENCY), RelationshipHelper.nested(Payment.Relationships.FROM, MemberAccount.Relationships.MEMBER, Element.Relationships.GROUP));
        if (!(account instanceof MemberAccount)) {
            return;
        }
        final MemberAccount memberAccount = (MemberAccount) account;
        final Group group = memberAccount.getMember().getGroup();
        final AccountType accountType = account.getType();
        final MemberGroupAccountSettings mgas = groupService.loadAccountSettings(group.getId(), accountType.getId());
        final BigDecimal lowUnits = mgas.getLowUnits() == null ? BigDecimal.ZERO : mgas.getLowUnits();
        // If low units message is used...
        if (lowUnits.floatValue() > PRECISION_DELTA && StringUtils.isNotEmpty(mgas.getLowUnitsMessage())) {
            doSendLowUnitsNotification(memberAccount, mgas);
        }
    }

    private void notifyScheduledPaymentProcessing(final Transfer transfer, final boolean notifyPayer, final boolean notifyReceiver) {
        // Get the required data
        final Member payer = transfer.isFromSystem() ? null : (Member) transfer.getFrom().getOwner();
        final Member payee = transfer.isToSystem() ? null : (Member) transfer.getTo().getOwner();
        final AuthorizationLevel nextAuthorizationLevel = transfer.getNextAuthorizationLevel();
        final Authorizer authorizer = nextAuthorizationLevel == null ? null : nextAuthorizationLevel.getAuthorizer();
        final MessageSettings messageSettings = settingsService.getMessageSettings();

        // Resolve the message
        String payerSubject;
        String payerBody;
        String payerSms;
        String payeeSubject;
        String payeeBody;
        String payeeSms;
        switch (transfer.getStatus()) {
            case PROCESSED:
                if (notifyPayer) {
                    payerSubject = messageSettings.getScheduledPaymentProcessedSubject();
                    payerBody = messageSettings.getScheduledPaymentProcessedMessage();
                    payerSms = messageSettings.getScheduledPaymentProcessedSms();
                } else {
                    payerSubject = null;
                    payerBody = null;
                    payerSms = null;
                }
                if (notifyReceiver) {
                    payeeSubject = messageSettings.getPaymentReceivedSubject();
                    payeeBody = messageSettings.getPaymentReceivedMessage();
                    payeeSms = messageSettings.getPaymentReceivedSms();
                } else {
                    payeeSubject = null;
                    payeeBody = null;
                    payeeSms = null;
                }
                break;
            case PENDING:
                // Payer don't get notified
                payerSubject = null;
                payerBody = null;
                payerSms = null;
                // Check whether the payee should authorize
                if (authorizer == Authorizer.RECEIVER) {
                    payeeSubject = messageSettings.getNewPendingPaymentByReceiverSubject();
                    payeeBody = messageSettings.getNewPendingPaymentByReceiverMessage();
                    payeeSms = messageSettings.getNewPendingPaymentByReceiverSms();
                } else {
                    payeeSubject = messageSettings.getPendingPaymentReceivedSubject();
                    payeeBody = messageSettings.getPendingPaymentReceivedMessage();
                    payeeSms = messageSettings.getPendingPaymentReceivedSms();

                }
                break;
            case FAILED:
                payerSubject = messageSettings.getScheduledPaymentFailedToPayerSubject();
                payerBody = messageSettings.getScheduledPaymentFailedToPayerMessage();
                payerSms = messageSettings.getScheduledPaymentFailedToPayerSms();
                try {
                    final ScheduledPayment scheduledPayment = transfer.getScheduledPayment();
                    boolean notify = scheduledPayment != null && scheduledPayment.isShowToReceiver();
                    if (!notify) { // try by invoice
                        invoiceService.loadByPayment(scheduledPayment == null ? transfer : scheduledPayment);
                        notify = true;
                    }

                    if (notify) {
                        payeeSubject = messageSettings.getScheduledPaymentFailedToPayeeSubject();
                        payeeBody = messageSettings.getScheduledPaymentFailedToPayeeMessage();
                        payeeSms = messageSettings.getScheduledPaymentFailedToPayeeSms();
                    } else {
                        payeeSubject = null;
                        payeeBody = null;
                        payeeSms = null;
                    }
                } catch (final EntityNotFoundException e) {
                    // Don't send message to payee when there's no associated invoice
                    payeeSubject = null;
                    payeeBody = null;
                    payeeSms = null;
                }
                break;
            default:
                // Unknown status here!!!
                return;
        }

        // Prepare the message
        final LocalSettings localSettings = settingsService.getLocalSettings();
        SendMessageFromSystemDTO message = new SendMessageFromSystemDTO();
        message.setType(Message.Type.PAYMENT);
        message.setEntity(transfer);

        // Send the message to the payer
        final Set<MessageChannel> payerChannels = payer == null || payerSubject == null ? null : preferenceService.receivedChannels(payer, Message.Type.PAYMENT);
        if (CollectionUtils.isNotEmpty(payerChannels)) {
            AccountStatus statusPayer = null;
            final boolean sendSmsNotification = transfer.getType().isAllowSmsNotification() && payerChannels.contains(MessageChannel.SMS);
            if (sendSmsNotification) {
                statusPayer = accountService.getCurrentStatus(new AccountDTO(transfer.getFrom()));
            }
            final AccountOwner payeeOwner = payee == null ? SystemAccountOwner.instance() : payee;
            message.setToMember(payer);
            message.setSubject(MessageProcessingHelper.processVariables(payerSubject, localSettings, payeeOwner, transfer));
            message.setBody(MessageProcessingHelper.processVariables(payerBody, localSettings, payeeOwner, transfer));
            if (sendSmsNotification) {
                message.setSms(MessageProcessingHelper.processVariables(payerSms, localSettings, payeeOwner, statusPayer, transfer));
            }
            messageService.sendFromSystem(message);
        }

        // Send the message to the payee
        final Set<MessageChannel> payeeChannels = payee == null || payeeSubject == null ? null : preferenceService.receivedChannels(payee, Message.Type.PAYMENT);
        if (CollectionUtils.isNotEmpty(payeeChannels)) {
            AccountStatus statusPayee = null;
            final boolean sendSmsNotification = transfer.getType().isAllowSmsNotification() && payeeChannels.contains(MessageChannel.SMS);
            if (sendSmsNotification) {
                statusPayee = accountService.getCurrentStatus(new GetTransactionsDTO(transfer.getTo()));
            }
            final AccountOwner payerOwner = payer == null ? SystemAccountOwner.instance() : payer;
            message.setToMember(payee);
            message.setSubject(MessageProcessingHelper.processVariables(payeeSubject, localSettings, payerOwner, transfer));
            message.setBody(MessageProcessingHelper.processVariables(payeeBody, localSettings, payerOwner, transfer));
            if (sendSmsNotification) {
                message.setSms(MessageProcessingHelper.processVariables(payeeSms, localSettings, payerOwner, statusPayee, transfer));
            }
            messageService.sendFromSystem(message);
        }

        // Notify the broker
        final Member broker = payer == null ? null : payer.getBroker();
        if (authorizer == Authorizer.BROKER && broker != null) {
            final String subject = messageSettings.getNewPendingPaymentByBrokerSubject();
            final String body = messageSettings.getNewPendingPaymentByBrokerMessage();
            final String sms = messageSettings.getNewPendingPaymentByBrokerSms();

            // Send the message
            message = new SendMessageFromSystemDTO();
            message.setType(Message.Type.BROKERING);
            message.setEntity(transfer);
            message.setToMember(broker);
            message.setSubject(MessageProcessingHelper.processVariables(subject, localSettings, payer, transfer));
            message.setBody(MessageProcessingHelper.processVariables(body, localSettings, payer, transfer));
            message.setSms(MessageProcessingHelper.processVariables(sms, localSettings, payer, transfer));
            messageService.sendFromSystem(message);
        }

        // Send a low units notification, if needed
        notifyLowUnits(transfer);
    }

    private void notifyTransactionFeedbackRequest(Payment payment) {
        // Payments from/to system, that hasn't been authorized yet or that don't use feedbacks are not notified
        payment = fetchService.fetch(payment, Payment.Relationships.FROM, Payment.Relationships.TO, Payment.Relationships.TYPE);
        if (payment == null || payment.isFromSystem() || payment.isToSystem() || !payment.getType().isRequiresFeedback()) {
            return;
        }
        // Transfers without process dates are skipped too
        if (payment instanceof Transfer) {
            final Transfer transfer = (Transfer) payment;
            if (transfer.getProcessDate() == null) {
                return;
            }
        }
        final Member from = (Member) payment.getFromOwner();
        final Member to = (Member) payment.getToOwner();

        // Get the message settings
        final MessageSettings messageSettings = settingsService.getMessageSettings();
        final String subject = messageSettings.getTransactionFeedbackRequestSubject();
        final String body = messageSettings.getTransactionFeedbackRequestMessage();
        final String sms = messageSettings.getTransactionFeedbackRequestSms();

        // Process message body
        final LocalSettings localSettings = settingsService.getLocalSettings();
        final Map<String, Object> extraVariables = new HashMap<String, Object>();
        final Calendar limit = payment.getType().getFeedbackExpirationTime().add(Calendar.getInstance());
        extraVariables.put("limit", localSettings.getDateConverter().toString(limit));

        String processedSubject = MessageProcessingHelper.processVariables(subject, localSettings, to, payment);
        String processedBody = MessageProcessingHelper.processVariables(body, localSettings, to, payment);
        String processedSms = MessageProcessingHelper.processVariables(sms, localSettings, to, payment);

        processedSubject = MessageProcessingHelper.processVariables(processedSubject, extraVariables);
        processedBody = MessageProcessingHelper.processVariables(processedBody, extraVariables);
        processedSms = MessageProcessingHelper.processVariables(processedSms, extraVariables);

        // Create the DTO
        final SendMessageFromSystemDTO message = new SendMessageFromSystemDTO();
        message.setType(Message.Type.TRANSACTION_FEEDBACK);
        message.setEntity(new TransactionFeedbackRequest(payment));
        message.setToMember(from);
        message.setSubject(processedSubject);
        message.setBody(processedBody);
        message.setSms(processedSms);

        // Send the message
        messageService.sendFromSystem(message);
    }

    private void sendPaymentMessages(final Transfer transfer, final List<Member> sendMessageTo, final String subject, final String body, final String sms) {
        // Process message contents
        final LocalSettings localSettings = settingsService.getLocalSettings();
        final String processedSubject = MessageProcessingHelper.processVariables(subject, transfer, localSettings);
        final String processedBody = MessageProcessingHelper.processVariables(body, transfer, localSettings);
        final String processedSms = transfer.getType().isAllowSmsNotification() ? MessageProcessingHelper.processVariables(sms, transfer, localSettings) : null;

        // Send each message
        for (final Member member : sendMessageTo) {

            // Create the DTO
            final SendMessageFromSystemDTO message = new SendMessageFromSystemDTO();
            message.setType(Message.Type.PAYMENT);
            message.setEntity(transfer);
            message.setToMember(member);
            message.setSubject(processedSubject);
            message.setBody(processedBody);
            message.setSms(processedSms);

            // Send the message
            messageService.sendFromSystem(message);
        }
    }

}
