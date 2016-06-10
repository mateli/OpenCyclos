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

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import nl.strohalm.cyclos.access.BasicPermission;
import nl.strohalm.cyclos.entities.Entity;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.Account;
import nl.strohalm.cyclos.entities.accounts.AccountOwner;
import nl.strohalm.cyclos.entities.accounts.MemberAccount;
import nl.strohalm.cyclos.entities.accounts.SystemAccount;
import nl.strohalm.cyclos.entities.accounts.SystemAccountType;
import nl.strohalm.cyclos.entities.accounts.guarantees.Guarantee;
import nl.strohalm.cyclos.entities.accounts.guarantees.GuaranteeType;
import nl.strohalm.cyclos.entities.accounts.transactions.AuthorizationLevel;
import nl.strohalm.cyclos.entities.accounts.transactions.Invoice;
import nl.strohalm.cyclos.entities.accounts.transactions.Payment;
import nl.strohalm.cyclos.entities.accounts.transactions.Transfer;
import nl.strohalm.cyclos.entities.alerts.Alert;
import nl.strohalm.cyclos.entities.alerts.ErrorLogEntry;
import nl.strohalm.cyclos.entities.alerts.MemberAlert;
import nl.strohalm.cyclos.entities.alerts.SystemAlert;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.members.Administrator;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.members.messages.Message;
import nl.strohalm.cyclos.entities.members.messages.MessageCategory;
import nl.strohalm.cyclos.entities.members.preferences.AdminNotificationPreferenceQuery;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.entities.settings.MessageSettings;
import nl.strohalm.cyclos.services.fetch.FetchServiceLocal;
import nl.strohalm.cyclos.services.permissions.PermissionServiceLocal;
import nl.strohalm.cyclos.services.preferences.PreferenceServiceLocal;
import nl.strohalm.cyclos.services.settings.SettingsServiceLocal;
import nl.strohalm.cyclos.utils.EnumHelper;
import nl.strohalm.cyclos.utils.LinkGenerator;
import nl.strohalm.cyclos.utils.MailHandler;
import nl.strohalm.cyclos.utils.MessageProcessingHelper;
import nl.strohalm.cyclos.utils.MessageResolver;
import nl.strohalm.cyclos.utils.RelationshipHelper;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.annotation.AfterReturning;

/**
 * Aspect used to notify admins about events on the system
 * @author luis
 */
public class AdminNotificationHandlerImpl implements AdminNotificationHandler {

    private class AdminSendDTO {
        private Administrator admin;
        private SendDTO       sendDTO;

        public Administrator getAdmin() {
            return admin;
        }

        public SendDTO getSendDTO() {
            return sendDTO;
        }

        public void setAdmin(final Administrator admin) {
            this.admin = admin;
        }

        public void setSendDTO(final SendDTO sendDTO) {
            this.sendDTO = sendDTO;
        }
    }

    private class QuerySendDTO {
        private final AdminNotificationPreferenceQuery query;
        private final SendDTO                          sendDTO;

        public QuerySendDTO(final AdminNotificationPreferenceQuery query, final SendDTO sendDTO) {
            this.query = query;
            this.sendDTO = sendDTO;
        }

        public AdminNotificationPreferenceQuery getQuery() {
            return query;
        }

        public SendDTO getSendDTO() {
            return sendDTO;
        }
    }

    private class SendDTO {
        private String              subject;
        private String              body;
        private Entity              relatedEntity;
        private Map<String, Object> variables;
        private boolean             isHtml;
        private MessageCategory     category;
        private Member              fromMember;

        public String getBody() {
            return body;
        }

        public MessageCategory getCategory() {
            return category;
        }

        public Member getFromMember() {
            return fromMember;
        }

        public Entity getRelatedEntity() {
            return relatedEntity;
        }

        public String getSubject() {
            return subject;
        }

        public Map<String, Object> getVariables() {
            return variables;
        }

        public boolean isHtml() {
            return isHtml;
        }

        public void setBody(final String body) {
            this.body = body;
        }

        public void setCategory(final MessageCategory category) {
            this.category = category;
        }

        public void setFromMember(final Member fromMember) {
            this.fromMember = fromMember;
        }

        public void setHtml(final boolean isHtml) {
            this.isHtml = isHtml;
        }

        public void setRelatedEntity(final Entity relatedEntity) {
            this.relatedEntity = relatedEntity;
        }

        public void setSubject(final String subject) {
            this.subject = subject;
        }

        public void setVariables(final Map<String, Object> variables) {
            this.variables = variables;
        }
    }

    private static final Relationship[] TRANSFER_FETCH  = { RelationshipHelper.nested(Payment.Relationships.FROM, MemberAccount.Relationships.MEMBER), RelationshipHelper.nested(Payment.Relationships.FROM, Account.Relationships.TYPE), RelationshipHelper.nested(Payment.Relationships.TO, MemberAccount.Relationships.MEMBER), RelationshipHelper.nested(Payment.Relationships.TO, Account.Relationships.TYPE), Payment.Relationships.TYPE };

    private PreferenceServiceLocal      preferenceService;
    private SettingsServiceLocal        settingsService;
    private FetchServiceLocal           fetchService;
    private PermissionServiceLocal      permissionService;
    private MailHandler                 mailHandler;
    private LinkGenerator               linkGenerator;
    private MessageResolver             messageResolver = new MessageResolver.NoOpMessageResolver();

    /**
     * Notifies an alert by e-mail
     */
    @Override
    @AfterReturning(pointcut = "execution(* nl.strohalm.cyclos.services.alerts.AlertServiceLocal.create(..))", returning = "alert", argNames = "alert")
    public void notifyAlert(final Alert alert) {
        final boolean isMember = alert instanceof MemberAlert;
        final AdminNotificationPreferenceQuery query = new AdminNotificationPreferenceQuery();
        final String key = alert.getKey();
        Map<String, Object> variables = null;
        final LocalSettings localSettings = settingsService.getLocalSettings();
        final MessageSettings messageSettings = settingsService.getMessageSettings();
        String subject;
        if (isMember) {
            final MemberAlert memberAlert = fetchService.fetch((MemberAlert) alert, RelationshipHelper.nested(MemberAlert.Relationships.MEMBER, Element.Relationships.GROUP));
            final Member member = memberAlert.getMember();
            query.setMemberAlert(EnumHelper.findByValue(MemberAlert.Alerts.class, key));
            query.setMemberGroup(member.getMemberGroup());
            variables = member.getVariableValues(localSettings);
            subject = messageSettings.getAdminMemberAlertSubject();
        } else {
            query.setSystemAlert(EnumHelper.findByValue(SystemAlert.Alerts.class, key));
            subject = messageSettings.getAdminSystemAlertSubject();
        }

        final List<String> args = Arrays.asList(alert.getArg0(), alert.getArg1(), alert.getArg2(), alert.getArg3(), alert.getArg4());
        final String body = messageResolver.message(key, args);

        final SendDTO sendDTO = new SendDTO();
        sendDTO.setSubject(subject);
        sendDTO.setBody(body);
        sendDTO.setRelatedEntity(null);
        sendDTO.setVariables(variables);
        sendDTO.setHtml(false);
        sendDTO.setCategory(null);
        sendDTO.setFromMember(null);

        final QuerySendDTO queryDTO = new QuerySendDTO(query, sendDTO);

        boolean enqueue = SystemAlert.Alerts.APPLICATION_SHUTDOWN != query.getSystemAlert();
        send(queryDTO, enqueue);
    }

    /**
     * Joinpoint that notifies application errors
     */
    @Override
    @AfterReturning(pointcut = "execution(* nl.strohalm.cyclos.services.alerts.ErrorLogServiceLocal.insert(..))", returning = "errorLog", argNames = "errorLog")
    public void notifyApplicationErrors(final ErrorLogEntry errorLog) {
        final AdminNotificationPreferenceQuery query = new AdminNotificationPreferenceQuery();
        query.setApplicationErrors(true);

        final MessageSettings messageSettings = settingsService.getMessageSettings();
        final String subject = messageSettings.getAdminApplicationErrorSubject();
        final String body = messageSettings.getAdminApplicationErrorMessage();

        final Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("path", errorLog.getPath());

        final SendDTO sendDTO = new SendDTO();
        sendDTO.setSubject(subject);
        sendDTO.setBody(body);
        sendDTO.setRelatedEntity(errorLog);
        sendDTO.setVariables(variables);
        sendDTO.setHtml(true);
        sendDTO.setCategory(null);
        sendDTO.setFromMember(null);

        final QuerySendDTO queryDTO = new QuerySendDTO(query, sendDTO);

        send(queryDTO);
    }

    @Override
    @AfterReturning(pointcut = "execution(* nl.strohalm.cyclos.services.elements.MessageServiceLocal.sendFromMemberToAdmin(..))", argNames = "message", returning = "message")
    public void notifyMessage(Message message) {
        message = fetchService.fetch(message, RelationshipHelper.nested(Message.Relationships.FROM_MEMBER, Element.Relationships.GROUP), Message.Relationships.CATEGORY);
        final MessageCategory category = message.getCategory();
        if (category == null) {
            return;
        }
        final Member fromMember = message.getFromMember();
        final boolean isHtml = message.isHtml();

        final AdminNotificationPreferenceQuery query = new AdminNotificationPreferenceQuery();
        query.setMessageCategory(category);
        query.setMemberGroup(fromMember.getMemberGroup());

        final SendDTO sendDTO = new SendDTO();
        sendDTO.setSubject(message.getSubject());
        sendDTO.setBody(message.getBody());
        sendDTO.setRelatedEntity(null);
        sendDTO.setVariables(null);
        sendDTO.setHtml(isHtml);
        sendDTO.setCategory(category);
        sendDTO.setFromMember(fromMember);

        final QuerySendDTO queryDTO = new QuerySendDTO(query, sendDTO);

        send(queryDTO);
    }

    /**
     * Joinpoint that notifies new pending payments
     */
    @Override
    @AfterReturning(pointcut = "(execution(* nl.strohalm.cyclos.services.transactions.PaymentServiceLocal.doPaymentAsMemberToMember(..))) || (execution(* nl.strohalm.cyclos.services.transactions.PaymentServiceLocal.doPaymentFromMemberToMember(..))) || (execution(* nl.strohalm.cyclos.services.transactions.PaymentServiceLocal.doPaymentFromSystemToMember(..))) || (execution(* nl.strohalm.cyclos.services.transactions.TransferAuthorizationServiceLocal.authorize*(..)))", argNames = "transfer", returning = "transfer")
    public void notifyNewPendingPayment(final Transfer transfer) {
        if (transfer.getProcessDate() != null) {
            return;
        }
        final AuthorizationLevel authorizationLevel = fetchService.fetch(transfer.getNextAuthorizationLevel(), AuthorizationLevel.Relationships.ADMIN_GROUPS);
        if (authorizationLevel == null || CollectionUtils.isEmpty(authorizationLevel.getAdminGroups())) {
            return;
        }
        final LocalSettings localSettings = settingsService.getLocalSettings();
        final MessageSettings messageSettings = settingsService.getMessageSettings();
        final AccountOwner fromOwner = transfer.getFromOwner();
        final AdminNotificationPreferenceQuery query = new AdminNotificationPreferenceQuery();
        final Map<String, Object> variables = new HashMap<String, Object>();
        variables.putAll(transfer.getVariableValues(localSettings));
        variables.putAll(fromOwner.getVariableValues(localSettings));
        if (fromOwner instanceof Member) {
            final Member fromMember = fetchService.fetch((Member) fromOwner, Element.Relationships.GROUP);
            query.setMemberGroup(fromMember.getMemberGroup());
        } else {
            variables.put("member", localSettings.getApplicationUsername());
            variables.put("login", transfer.getFrom().getOwnerName());
            query.setAccountTypes(Arrays.asList((SystemAccountType) fetchService.fetch(transfer.getFrom().getType())));
        }

        query.setAdminGroups(authorizationLevel.getAdminGroups());
        query.setNewPendingPayment(transfer.getType());

        final String subject = messageSettings.getAdminNewPendingPaymentSubject();
        final String body = messageSettings.getAdminNewPendingPaymentMessage();

        final SendDTO sendDTO = new SendDTO();
        sendDTO.setSubject(subject);
        sendDTO.setBody(body);
        sendDTO.setRelatedEntity(transfer);
        sendDTO.setVariables(variables);
        sendDTO.setHtml(true);
        sendDTO.setCategory(null);
        sendDTO.setFromMember(null);

        final QuerySendDTO queryDTO = new QuerySendDTO(query, sendDTO);
        send(queryDTO);
    }

    /**
     * Joinpoint that notifies new public registrations
     */
    @Override
    @AfterReturning(pointcut = "execution(* nl.strohalm.cyclos.services.elements.ElementServiceLocal.publicRegisterMember(..)) || execution(* nl.strohalm.cyclos.services.elements.ElementServiceLocal.publicValidateRegistration(..))", returning = "registeredMember", argNames = "registeredMember")
    public void notifyNewPublicRegistration(final Member member) {
        final MemberGroup group = member.getMemberGroup();

        final AdminNotificationPreferenceQuery query = new AdminNotificationPreferenceQuery();
        query.setNewMemberGroup(group);

        final LocalSettings localSettings = settingsService.getLocalSettings();
        final MessageSettings messageSettings = settingsService.getMessageSettings();
        final String subject = messageSettings.getAdminNewMemberSubject();
        final String body = messageSettings.getAdminNewMemberMessage();

        final Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("group", group.getName());
        variables.putAll(member.getVariableValues(localSettings));

        final SendDTO sendDTO = new SendDTO();
        sendDTO.setSubject(subject);
        sendDTO.setBody(body);
        sendDTO.setRelatedEntity(member);
        sendDTO.setVariables(variables);
        sendDTO.setHtml(true);
        sendDTO.setCategory(null);
        sendDTO.setFromMember(null);

        final QuerySendDTO queryDTO = new QuerySendDTO(query, sendDTO);

        send(queryDTO);
    }

    /**
     * Joinpoint that notifies system payments
     */
    @Override
    @AfterReturning(pointcut = "execution(* nl.strohalm.cyclos.services.transactions.PaymentServiceLocal.doPayment*(..)) || execution(* nl.strohalm.cyclos.services.transactions.PaymentServiceLocal.doSelfPayment(..))", argNames = "transfer", returning = "transfer")
    public void notifyPayment(Transfer transfer) {
        transfer = fetchService.fetch(transfer, TRANSFER_FETCH);
        final Account from = transfer.getFrom();
        final Account to = transfer.getTo();
        if (!(from instanceof SystemAccount || to instanceof SystemAccount)) {
            // Only notify payments from or to system
            return;
        }

        final LocalSettings localSettings = settingsService.getLocalSettings();
        final MessageSettings messageSettings = settingsService.getMessageSettings();
        String subject;
        String body;
        Member member;
        final Map<String, Object> variables = new HashMap<String, Object>();
        final Collection<SystemAccountType> accountTypes = new HashSet<SystemAccountType>();
        if (from instanceof MemberAccount) {
            subject = messageSettings.getAdminPaymentFromMemberToSystemSubject();
            body = messageSettings.getAdminPaymentFromMemberToSystemMessage();
            accountTypes.add((SystemAccountType) to.getType());
            member = ((MemberAccount) from).getMember();
        } else if (to instanceof MemberAccount) {
            subject = messageSettings.getAdminPaymentFromSystemToMemberSubject();
            body = messageSettings.getAdminPaymentFromSystemToMemberMessage();
            accountTypes.add((SystemAccountType) from.getType());
            member = ((MemberAccount) to).getMember();
        } else {
            subject = messageSettings.getAdminPaymentFromSystemToSystemSubject();
            body = messageSettings.getAdminPaymentFromSystemToSystemMessage();
            accountTypes.add((SystemAccountType) from.getType());
            accountTypes.add((SystemAccountType) to.getType());
            member = null;
        }
        if (member != null) {
            variables.putAll(member.getVariableValues(localSettings));
        }
        variables.putAll(transfer.getVariableValues(localSettings));

        final AdminNotificationPreferenceQuery query = new AdminNotificationPreferenceQuery();
        query.setTransferType(transfer.getType());
        query.setAccountTypes(accountTypes);
        if (member != null) {
            member = fetchService.fetch(member, Element.Relationships.GROUP);
            query.setMemberGroup(member.getMemberGroup());
        }

        final SendDTO sendDTO = new SendDTO();
        sendDTO.setSubject(subject);
        sendDTO.setBody(body);
        sendDTO.setRelatedEntity(transfer);
        sendDTO.setVariables(variables);
        sendDTO.setHtml(true);
        sendDTO.setCategory(null);
        sendDTO.setFromMember(null);

        final QuerySendDTO queryDTO = new QuerySendDTO(query, sendDTO);

        send(queryDTO);
    }

    @Override
    @AfterReturning(pointcut = "(execution(* nl.strohalm.cyclos.services.accounts.guarantees.GuaranteeServiceLocal.changeStatus(..)))", argNames = "guarantee", returning = "guarantee")
    public void notifyPendingGuarantee(final Guarantee guarantee) {
        doNotifyPendingGuarantee(guarantee);
    }

    /**
     * Joinpoint that notifies system invoices
     */
    @Override
    @AfterReturning(pointcut = "execution(* nl.strohalm.cyclos.services.transactions.InvoiceServiceLocal.sendFromMemberToSystem(..))", returning = "invoice", argNames = "invoice")
    public void notifySystemInvoice(final Invoice invoice) {
        final AdminNotificationPreferenceQuery query = new AdminNotificationPreferenceQuery();
        query.setSystemInvoices(true);

        final MessageSettings messageSettings = settingsService.getMessageSettings();
        final String subject = messageSettings.getAdminSystemInvoiceSubject();
        final String body = messageSettings.getAdminSystemInvoiceMessage();
        final LocalSettings localSettings = settingsService.getLocalSettings();

        final Map<String, Object> variables = new HashMap<String, Object>();
        variables.putAll(invoice.getVariableValues(localSettings));
        variables.putAll(invoice.getFromMember().getVariableValues(localSettings));

        final SendDTO sendDTO = new SendDTO();
        sendDTO.setSubject(subject);
        sendDTO.setBody(body);
        sendDTO.setRelatedEntity(invoice);
        sendDTO.setVariables(variables);
        sendDTO.setHtml(true);
        sendDTO.setCategory(null);
        sendDTO.setFromMember(null);

        final QuerySendDTO queryDTO = new QuerySendDTO(query, sendDTO);
        send(queryDTO);
    }

    public void setFetchServiceLocal(final FetchServiceLocal fetchService) {
        this.fetchService = fetchService;
    }

    public void setLinkGenerator(final LinkGenerator linkGenerator) {
        this.linkGenerator = linkGenerator;
    }

    public void setMailHandler(final MailHandler mailHandler) {
        this.mailHandler = mailHandler;
    }

    public void setMessageResolver(final MessageResolver messageResolver) {
        this.messageResolver = messageResolver;
    }

    public void setPermissionServiceLocal(final PermissionServiceLocal permissionService) {
        this.permissionService = permissionService;
    }

    public void setPreferenceServiceLocal(final PreferenceServiceLocal preferenceService) {
        this.preferenceService = preferenceService;
    }

    public void setSettingsServiceLocal(final SettingsServiceLocal settingsService) {
        this.settingsService = settingsService;
    }

    private void doNotifyPendingGuarantee(Guarantee guarantee) {
        if (guarantee.getStatus() == Guarantee.Status.PENDING_ADMIN) {
            guarantee = fetchService.fetch(guarantee, Guarantee.Relationships.GUARANTEE_TYPE);
            final MessageSettings messageSettings = settingsService.getMessageSettings();
            final LocalSettings localSettings = settingsService.getLocalSettings();

            final AdminNotificationPreferenceQuery query = new AdminNotificationPreferenceQuery();
            query.setGuaranteeType(guarantee.getGuaranteeType());

            String subject = null;
            String body = null;
            if (guarantee.getGuaranteeType().getModel() == GuaranteeType.Model.WITH_BUYER_ONLY) {
                subject = messageSettings.getAdminPendingBuyerOnlyGuaranteeSubject();
                body = messageSettings.getAdminPendingBuyerOnlyGuaranteeMessage();
            } else {
                subject = messageSettings.getAdminPendingGuaranteeSubject();
                body = messageSettings.getAdminPendingGuaranteeMessage();
            }

            final Map<String, Object> variables = new HashMap<String, Object>();
            variables.putAll(guarantee.getVariableValues(localSettings));

            final SendDTO sendDTO = new SendDTO();
            sendDTO.setSubject(subject);
            sendDTO.setBody(body);
            sendDTO.setRelatedEntity(guarantee);
            sendDTO.setVariables(variables);
            sendDTO.setHtml(true);
            sendDTO.setCategory(null);
            sendDTO.setFromMember(null);

            final QuerySendDTO queryDTO = new QuerySendDTO(query, sendDTO);
            send(queryDTO);
        }
    }

    private void send(final AdminSendDTO adminDTO, final boolean enqueue) {
        // Ensure we won't notify inactive admins
        final boolean active = permissionService.hasPermission(adminDTO.getAdmin().getGroup(), BasicPermission.BASIC_LOGIN);
        if (!active) {
            return;
        }
        // Check the mail is filled
        final String email = adminDTO.getAdmin().getEmail();
        if (StringUtils.isEmpty(email)) {
            return;
        }
        final SendDTO sendDTO = adminDTO.getSendDTO();
        // Fill in the link
        if (sendDTO.getRelatedEntity() != null && linkGenerator != null) {
            sendDTO.getVariables().put("link", linkGenerator.generateLinkFor(adminDTO.getAdmin(), sendDTO.getRelatedEntity()));
        }
        // Process the variables and send
        final String body = mailHandler.processBody(adminDTO.getAdmin(), sendDTO.getBody(), sendDTO.getFromMember(), sendDTO.getCategory(), sendDTO.isHtml());
        final String processedSubject = MessageProcessingHelper.processVariables(sendDTO.getSubject(), sendDTO.getVariables());
        final String processedBody = MessageProcessingHelper.processVariables(body, sendDTO.getVariables());

        if (enqueue) {
            mailHandler.sendAfterTransactionCommit(processedSubject, null, mailHandler.getInternetAddress(adminDTO.getAdmin()), processedBody, sendDTO.isHtml());
        } else {
            mailHandler.send(processedSubject, null, mailHandler.getInternetAddress(adminDTO.getAdmin()), processedBody, sendDTO.isHtml());
        }
    }

    private void send(final QuerySendDTO queryDTO) {
        send(queryDTO, true);
    }

    private void send(final QuerySendDTO queryDTO, final boolean enqueue) {
        final List<Administrator> admins = preferenceService.listAdminsForNotification(queryDTO.getQuery());

        final AdminSendDTO adminDTO = new AdminSendDTO();
        adminDTO.setSendDTO(queryDTO.getSendDTO());

        for (final Administrator admin : admins) {
            adminDTO.setAdmin(admin);
            send(adminDTO, enqueue);
        }
    }
}
