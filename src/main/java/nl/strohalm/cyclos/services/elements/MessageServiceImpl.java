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
package nl.strohalm.cyclos.services.elements;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import javax.mail.internet.InternetAddress;

import nl.strohalm.cyclos.access.AdminMemberPermission;
import nl.strohalm.cyclos.access.MemberPermission;
import nl.strohalm.cyclos.access.OperatorPermission;
import nl.strohalm.cyclos.dao.members.MessageDAO;
import nl.strohalm.cyclos.entities.Entity;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.SystemAccountOwner;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferType;
import nl.strohalm.cyclos.entities.customization.fields.MemberCustomField;
import nl.strohalm.cyclos.entities.exceptions.LockingException;
import nl.strohalm.cyclos.entities.exceptions.UnexpectedEntityException;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.groups.MemberGroupSettings;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.members.messages.Message;
import nl.strohalm.cyclos.entities.members.messages.Message.Direction;
import nl.strohalm.cyclos.entities.members.messages.Message.Type;
import nl.strohalm.cyclos.entities.members.messages.MessageQuery;
import nl.strohalm.cyclos.entities.sms.MemberSmsStatus;
import nl.strohalm.cyclos.entities.sms.SmsLog;
import nl.strohalm.cyclos.entities.sms.SmsLog.ErrorType;
import nl.strohalm.cyclos.services.InitializingService;
import nl.strohalm.cyclos.services.elements.exceptions.MemberWontReceiveNotificationException;
import nl.strohalm.cyclos.services.fetch.FetchServiceLocal;
import nl.strohalm.cyclos.services.permissions.PermissionServiceLocal;
import nl.strohalm.cyclos.services.preferences.MessageChannel;
import nl.strohalm.cyclos.services.preferences.PreferenceServiceLocal;
import nl.strohalm.cyclos.services.settings.SettingsServiceLocal;
import nl.strohalm.cyclos.services.sms.ISmsContext;
import nl.strohalm.cyclos.services.sms.SmsLogServiceLocal;
import nl.strohalm.cyclos.services.transactions.PaymentServiceLocal;
import nl.strohalm.cyclos.services.transactions.TransferDTO;
import nl.strohalm.cyclos.services.transactions.exceptions.MaxAmountPerDayExceededException;
import nl.strohalm.cyclos.services.transactions.exceptions.NotEnoughCreditsException;
import nl.strohalm.cyclos.services.transactions.exceptions.UpperCreditLimitReachedException;
import nl.strohalm.cyclos.utils.DateHelper;
import nl.strohalm.cyclos.utils.LinkGenerator;
import nl.strohalm.cyclos.utils.MailHandler;
import nl.strohalm.cyclos.utils.TimePeriod;
import nl.strohalm.cyclos.utils.TransactionHelper;
import nl.strohalm.cyclos.utils.WorkerThreads;
import nl.strohalm.cyclos.utils.access.LoggedUser;
import nl.strohalm.cyclos.utils.notifications.AdminNotificationHandler;
import nl.strohalm.cyclos.utils.sms.SmsSender;
import nl.strohalm.cyclos.utils.transaction.CurrentTransactionData;
import nl.strohalm.cyclos.utils.transaction.TransactionCommitListener;
import nl.strohalm.cyclos.utils.transaction.TransactionEndListener;
import nl.strohalm.cyclos.utils.validation.InvalidError;
import nl.strohalm.cyclos.utils.validation.PropertyValidation;
import nl.strohalm.cyclos.utils.validation.RequiredValidation;
import nl.strohalm.cyclos.utils.validation.ValidationError;
import nl.strohalm.cyclos.utils.validation.ValidationException;
import nl.strohalm.cyclos.utils.validation.Validator;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.mutable.MutableObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;

/**
 * Implementation for message service
 * @author luis
 */
public class MessageServiceImpl implements MessageServiceLocal, DisposableBean, InitializingService {
    public static class RequiredWhenFromAdminValidation implements PropertyValidation {
        private static final long serialVersionUID = -3593591846871843393L;

        @Override
        public ValidationError validate(final Object object, final Object property, final Object value) {
            if (LoggedUser.hasUser() && LoggedUser.isAdministrator()) {
                return RequiredValidation.instance().validate(object, property, value);
            }
            return null;
        }
    }

    /**
     * Ensure the message receiver is not the same logged user
     * @author luis
     */
    public static class SameFromAndToValidation implements PropertyValidation {

        private static final long serialVersionUID = -3649308826565152361L;

        @Override
        public ValidationError validate(final Object object, final Object property, final Object value) {
            final SendMessageToMemberDTO dto = (SendMessageToMemberDTO) object;
            final Member loggedMember = (Member) (LoggedUser.hasUser() && LoggedUser.isMember() ? LoggedUser.element() : null);
            final Member toMember = dto.getToMember();
            if (loggedMember != null && loggedMember.equals(toMember)) {
                return new InvalidError();
            }
            return null;
        }
    }

    private class SmsSenderThreads extends WorkerThreads<SendSmsDTO> {

        public SmsSenderThreads(final String name, final int threadCount) {
            super(name, threadCount);
        }

        @Override
        protected void process(final SendSmsDTO params) {
            sendSms(params);
        }
    }

    private static final Log         LOG = LogFactory.getLog(MessageServiceImpl.class);

    private PermissionServiceLocal   permissionService;

    private MessageDAO               messageDao;
    private FetchServiceLocal        fetchService;
    private MemberServiceLocal       memberService;
    private PaymentServiceLocal      paymentService;
    private PreferenceServiceLocal   preferenceService;
    private SettingsServiceLocal     settingsService;
    private SmsLogServiceLocal       smsLogService;
    private LinkGenerator            linkGenerator;
    private MailHandler              mailHandler;
    private SmsSender                smsSender;
    private TransactionHelper        transactionHelper;
    private int                      maxSmsThreads;
    private SmsSenderThreads         smsSenderThreads;
    private AdminNotificationHandler adminNotificationHandler;

    @Override
    public boolean canManage(Message message) {
        message = checkMessageOwner(message);
        if (message == null) {
            return false;
        }

        return permissionService.permission()
                .admin(AdminMemberPermission.MESSAGES_MANAGE)
                .member(MemberPermission.MESSAGES_MANAGE)
                .operator(OperatorPermission.MESSAGES_MANAGE)
                .hasPermission();
    }

    @Override
    public boolean canSendToAdmin() {
        return permissionService.permission()
                .member(MemberPermission.MESSAGES_SEND_TO_ADMINISTRATION)
                .operator(OperatorPermission.MESSAGES_SEND_TO_ADMINISTRATION)
                .hasPermission();
    }

    @Override
    public boolean canSendToMember(final Member member) {
        return permissionService.permission()
                .admin(AdminMemberPermission.MESSAGES_SEND_TO_MEMBER)
                .member(MemberPermission.MESSAGES_SEND_TO_MEMBER)
                .operator(OperatorPermission.MESSAGES_SEND_TO_MEMBER)
                .hasPermission()
                &&
                permissionService.relatesTo(member);
    }

    @Override
    public Message checkMessageOwner(Message message) {
        if (message == null) {
            return null;
        }
        message = fetchService.fetch(message, Message.Relationships.FROM_MEMBER, Message.Relationships.TO_MEMBER);
        final Member loggedMember = (Member) (LoggedUser.hasUser() && !LoggedUser.isAdministrator() ? LoggedUser.accountOwner() : null);
        final Member owner = message.getOwner();
        if ((loggedMember == null && owner != null) || (loggedMember != null && !loggedMember.equals(owner))) {
            return null;
        }
        return message;
    }

    @Override
    public void destroy() throws Exception {
        if (smsSenderThreads != null) {
            smsSenderThreads.interrupt();
            smsSenderThreads = null;
        }
    }

    @Override
    public void initializeService() {
        purgeExpiredMessagesOnTrash(Calendar.getInstance());
    }

    @Override
    public Message load(final Long id, final Relationship... fetch) {
        return messageDao.load(id, fetch);
    }

    @Override
    public Message nextToSend() {
        return messageDao.nextToSend();
    }

    @Override
    public void performAction(final MessageAction action, final Long... ids) {
        for (final Long id : ids) {
            final Message message = messageDao.load(id);
            if (action == MessageAction.DELETE) {
                messageDao.delete(message.getId());
            } else {
                switch (action) {
                    case MOVE_TO_TRASH:
                        message.setRemovedAt(Calendar.getInstance());
                        break;
                    case RESTORE:
                        message.setRemovedAt(null);
                        break;
                    case MARK_AS_READ:
                        message.setRead(true);
                        break;
                    case MARK_AS_UNREAD:
                        message.setRead(false);
                        break;
                }
                messageDao.update(message);
            }
        }
    }

    @Override
    public void purgeExpiredMessagesOnTrash(final Calendar time) {
        final TimePeriod timePeriod = settingsService.getLocalSettings().getDeleteMessagesOnTrashAfter();
        if (timePeriod == null || timePeriod.getNumber() <= 0) {
            return;
        }
        final Calendar limit = timePeriod.remove(DateHelper.truncate(time));
        messageDao.removeMessagesOnTrashBefore(limit);
    }

    @Override
    public Message read(final Long id, final Relationship... fetch) {
        final Message message = load(id, fetch);
        message.setRead(true);
        return message;
    }

    @Override
    public List<Message> search(final MessageQuery query) {
        return messageDao.search(query);
    }

    @Override
    public Message send(final SendMessageDTO message) {
        if (message instanceof SendMessageToGroupDTO || message instanceof SendMessageFromBrokerToMembersDTO) {
            return doSendBulk(message);
        } else if (message instanceof SendMessageToAdminDTO) {
            Message sentMessage = doSendSingle(message);
            adminNotificationHandler.notifyMessage(sentMessage);
            return sentMessage;
        } else if (message instanceof SendDirectMessageToMemberDTO) {
            return doSendSingle(message);
        }
        return doSendSingle(message);
    }

    @Override
    public void sendEmailIfNeeded(final Message message) {
        Member member = message.getToMember();
        Set<MessageChannel> receivedChannels = preferenceService.receivedChannels(member, message.getType());
        if (receivedChannels.contains(MessageChannel.EMAIL)) {
            InternetAddress replyTo = mailHandler.getInternetAddress(message.getFromMember());
            InternetAddress to = mailHandler.getInternetAddress(member);
            mailHandler.send(message.getSubject(), replyTo, to, message.getBody(), message.isHtml());
        }
    }

    @Override
    public void sendFromSystem(final SendMessageFromSystemDTO message) {
        final Entity entity = message.getEntity();
        String link = "";
        if (entity != null && linkGenerator != null) {
            link = linkGenerator.generateLinkFor(message.getToMember(), entity);
        }
        final String body = StringUtils.replace(message.getBody(), "#link#", link);
        message.setBody(body);
        message.setHtml(true);
        doSendSingle(message);
    }

    /**
     * This methods runs a new transaction for handling the sms status, charging, etc... Then, the log is always persisted in another transaction
     * (with a {@link TransactionEndListener}). So, even if the sending fails, the log is persisted
     */
    @Override
    public SmsLog sendSms(final SendSmsDTO params) {
        final MutableObject result = new MutableObject();
        transactionHelper.runInNewTransaction(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(final TransactionStatus status) {
                // The returned log is transient...
                SmsLog log;
                try {
                    log = doSendSms(params);
                } catch (LockingException e) {
                    throw e;
                } catch (Exception e) {
                    LOG.error("Unknown error sending sms", e);
                    log = newSmsLog(params, ErrorType.SEND_ERROR, false);
                }
                if (log.getErrorType() != null) {
                    status.setRollbackOnly();
                }
                // ... we can only actually persist it after this transaction ends, because we don't know if it will be committed
                final SmsLog toSave = log;
                CurrentTransactionData.addTransactionEndListener(new TransactionEndListener() {
                    @Override
                    protected void onTransactionEnd(final boolean commit) {
                        transactionHelper.runInCurrentThread(new TransactionCallbackWithoutResult() {
                            @Override
                            protected void doInTransactionWithoutResult(final TransactionStatus status) {
                                result.setValue(smsLogService.save(toSave));
                            }
                        });
                    }
                });
            }
        });
        return (SmsLog) result.getValue();
    }

    @Override
    public synchronized void sendSmsAfterTransactionCommit(final SendSmsDTO params) {
        if (smsSenderThreads == null) {
            smsSenderThreads = new SmsSenderThreads("SMS sender for " + settingsService.getLocalSettings().getApplicationName(), maxSmsThreads);
        }
        CurrentTransactionData.addTransactionCommitListener(new TransactionCommitListener() {
            @Override
            public void onTransactionCommit() {
                smsSenderThreads.enqueue(params);
            }
        });
    }

    public void setAdminNotificationHandler(final AdminNotificationHandler adminNotificationHandler) {
        this.adminNotificationHandler = adminNotificationHandler;
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

    public void setMaxSmsThreads(final int maxSmsThreads) {
        this.maxSmsThreads = maxSmsThreads;
    }

    public void setMemberServiceLocal(final MemberServiceLocal memberService) {
        this.memberService = memberService;
    }

    public void setMessageDao(final MessageDAO messageDao) {
        this.messageDao = messageDao;
    }

    public void setPaymentServiceLocal(final PaymentServiceLocal paymentService) {
        this.paymentService = paymentService;
    }

    public final void setPermissionServiceLocal(final PermissionServiceLocal permissionService) {
        this.permissionService = permissionService;
    }

    public void setPreferenceServiceLocal(final PreferenceServiceLocal preferenceService) {
        this.preferenceService = preferenceService;
    }

    public void setSettingsServiceLocal(final SettingsServiceLocal settingsService) {
        this.settingsService = settingsService;
    }

    public void setSmsLogServiceLocal(final SmsLogServiceLocal smsLogService) {
        this.smsLogService = smsLogService;
    }

    public void setSmsSender(final SmsSender smsSender) {
        this.smsSender = smsSender;
    }

    public void setTransactionHelper(final TransactionHelper transactionHelper) {
        this.transactionHelper = transactionHelper;
    }

    @Override
    public void validate(final SendMessageDTO message) throws ValidationException {
        getValidator(message.getClass()).validate(message);
    }

    private Validator basicToMemberValidator() {
        final Validator validator = basicValidator();
        validator.property("toMember").required();
        return validator;
    }

    private Validator basicValidator() {
        final Validator validator = new Validator("message");
        validator.property("subject").required();
        validator.property("body").required();
        return validator;
    }

    /**
     * Returns a Message instance, filled with data from the given DTO
     */
    private Message buildFromDTO(final SendMessageDTO dto, final Message.Direction direction) {
        return buildFromDTO(dto, dto, direction);
    }

    /**
     * Returns a Message instance, filled with data from the given DTO
     */
    private Message buildFromDTO(final SendMessageDTO original, final SendMessageDTO dto, final Message.Direction direction) {
        final Message message = new Message();
        message.setDate(Calendar.getInstance());
        message.setHtml(dto.isHtml());
        message.setSubject(dto.getSubject());
        message.setBody(dto.getBody());
        if (!(dto instanceof SendMessageFromSystemDTO)) {
            message.setFromMember((Member) (LoggedUser.hasUser() && !LoggedUser.isAdministrator() ? LoggedUser.accountOwner() : null));
        }
        message.setType(dto.getType());
        message.setDirection(direction);
        if (direction == Message.Direction.OUTGOING) {
            message.setRead(true);
            message.setEmailSent(true);
        }
        if (dto instanceof SendMessageToMemberDTO) {
            message.setToMember(((SendMessageToMemberDTO) dto).getToMember());
        } else if (dto instanceof SendMessageToGroupDTO) {
            message.setToGroups(new ArrayList<MemberGroup>(((SendMessageToGroupDTO) dto).getToGroups()));
        }
        if (message.isFromAMember() && message.isToAMember()) {
            message.setCategory(null);
        } else {
            message.setCategory(dto.getCategory());
        }
        return message;
    }

    private TransferDTO buildSmsChargeDto(final Member member, final ISmsContext smsContext) {
        final MemberGroupSettings memberSettings = member.getMemberGroup().getMemberSettings();
        final TransferType smsChargeTransferType = memberSettings.getSmsChargeTransferType();
        final BigDecimal smsChargeAmount = smsContext.getAdditionalChargeAmount(member);

        // There are no charge settings, so don't charge
        if (smsChargeTransferType == null || smsChargeAmount == null) {
            return null;
        }

        // Build charge DTO
        final TransferDTO transferDto = new TransferDTO();
        if (smsChargeTransferType.isFromMember()) {
            transferDto.setFromOwner(member);
        } else {
            transferDto.setFromOwner(SystemAccountOwner.instance());
        }
        transferDto.setToOwner(SystemAccountOwner.instance());
        transferDto.setTransferType(smsChargeTransferType);
        transferDto.setDescription(smsChargeTransferType.getDescription());
        transferDto.setAmount(smsChargeAmount);
        transferDto.setAutomatic(true);
        return transferDto;
    }

    private Message doSendBulk(final SendMessageDTO message) {
        // Validate the message parameters
        validate(message);

        // Insert the sender copy only. The BulkMessageSendingPollingTask will actually deliver each copy
        return insertSenderCopy(message);
    }

    private Message doSendSingle(final SendMessageDTO message) {
        // Validate the message parameters
        validate(message);

        Set<MessageChannel> messageChannels = null;
        if (message instanceof SendMessageToMemberDTO) {
            // If is a message to member, get the received channels
            final SendMessageToMemberDTO toMemberMessage = (SendMessageToMemberDTO) message;
            messageChannels = preferenceService.receivedChannels(toMemberMessage.getToMember(), message.getType());
            if (toMemberMessage.requiresMemberToReceive() && CollectionUtils.isEmpty(messageChannels)) {
                // The message dto requires the member to receive and his preferences say he doesn't - throw an exception
                throw new MemberWontReceiveNotificationException();
            }
        }

        // Then insert the sender copy
        final Message senderCopy = insertSenderCopy(message);
        final Message toSend = buildFromDTO(message, Direction.INCOMING);
        String sms = null;
        String smsTraceData = null;
        if (message instanceof SendMessageFromSystemDTO) {
            SendMessageFromSystemDTO messageFromSystem = (SendMessageFromSystemDTO) message;
            sms = messageFromSystem.getSms();
            smsTraceData = messageFromSystem.getSmsTraceData();
        }
        send(toSend, sms, smsTraceData, messageChannels);
        return senderCopy;
    }

    private SmsLog doSendSms(final SendSmsDTO params) {
        final Member target = params.getTargetMember();
        final MemberCustomField customField = settingsService.getSmsCustomField();
        if (customField == null || !memberService.hasValueForField(target, customField)) {
            // Either no custom field, or the member didn't have value for the mobile phone
            return newSmsLog(params, ErrorType.NO_PHONE, false);
        }
        Member charged = params.getChargedMember();
        final boolean freeMailing = params.getSmsMailing() != null && params.getSmsMailing().isFree();
        ErrorType errorType = null;
        boolean boughtNewMessages = false;
        MemberSmsStatus memberSmsStatus = null;
        int additionalChargedSms = 0;
        boolean statusChanged = false;
        boolean freeBaseUsed = false;
        ISmsContext smsContext = null;
        if (!freeMailing) {
            // Charge the SMS
            if (charged == null) {
                charged = target;
                params.setChargedMember(charged);
            }
            charged = fetchService.reload(charged, Element.Relationships.GROUP);
            smsContext = memberService.getSmsContext(charged);
            memberSmsStatus = memberService.getSmsStatus(charged, true);
            additionalChargedSms = smsContext.getAdditionalChargedSms(charged);
            final int freeSms = smsContext.getFreeSms(charged);
            if (memberSmsStatus.getFreeSmsSent() < freeSms) {
                // There are free messages left
                memberSmsStatus.setFreeSmsSent(memberSmsStatus.getFreeSmsSent() + 1);
                freeBaseUsed = true;
                statusChanged = true;
            } else if (memberSmsStatus.getPaidSmsLeft() > 0) {
                // There are paid messages left
                memberSmsStatus.setPaidSmsLeft(memberSmsStatus.getPaidSmsLeft() - 1);
                statusChanged = true;
            } else {
                // Check if paid messages are enabled
                if (additionalChargedSms > 0) {
                    // Paid messages are enabled
                    if (memberSmsStatus.isAllowChargingSms()) {
                        // The member allows charge
                        final TransferDTO chargeDTO = buildSmsChargeDto(charged, smsContext);
                        try {
                            if (chargeDTO == null) {
                                throw new UnexpectedEntityException();
                            }
                            paymentService.insertWithoutNotification(chargeDTO);
                            // The status will only be updated if the SMS sending is successful, to avoid updating the status and having to undo later
                            boughtNewMessages = true;
                        } catch (final NotEnoughCreditsException e) {
                            errorType = ErrorType.NOT_ENOUGH_FUNDS;
                        } catch (final MaxAmountPerDayExceededException e) {
                            errorType = ErrorType.NOT_ENOUGH_FUNDS;
                        } catch (final UpperCreditLimitReachedException e) {
                            errorType = ErrorType.NOT_ENOUGH_FUNDS;
                        } catch (final UnexpectedEntityException e) {
                            ValidationException exc = new ValidationException("The SMS charging is not well configured. Please, check the charging transfer type.");
                            exc.setShowDetailMessage(true);
                            throw exc;
                        }
                    } else {
                        // The member have disallowed charging
                        errorType = ErrorType.ALLOW_CHARGING_DISABLED;
                    }
                } else {
                    if (freeSms == 0) {
                        ValidationException exc = new ValidationException("SMS cannot be sent becasue both free messages and aditional messages are zero for member: " + charged.getUsername());
                        exc.setShowDetailMessage(true);
                        throw exc;

                    } else {
                        errorType = ErrorType.NO_SMS_LEFT;
                    }
                }
            }
        }
        // Send the message itself if no error so far
        if (errorType == null) {
            try {
                if (!smsSender.send(target, params.getText(), params.getTraceData())) {
                    throw new Exception("Sms sender returns false when sending a sms (trace=" + params.getTraceData() + ")");
                }
                // The message was sent. Update the member sms status
                if (boughtNewMessages) {
                    final int left = additionalChargedSms - 1;
                    Calendar expiration = null;
                    if (left > 0) {
                        TimePeriod additionalChargedPeriod = smsContext.getAdditionalChargedPeriod(charged);
                        if (additionalChargedPeriod == null) {
                            additionalChargedPeriod = TimePeriod.ONE_MONTH;
                        }
                        expiration = additionalChargedPeriod.add(Calendar.getInstance());
                    }
                    memberSmsStatus.setPaidSmsLeft(left);
                    memberSmsStatus.setPaidSmsExpiration(expiration);
                    statusChanged = true;
                }
            } catch (final Exception e) {
                LOG.error("Unknown error sending sms", e);
                errorType = ErrorType.SEND_ERROR;
                // Ensure the status won't be changed on errors
                statusChanged = false;
            }
        }
        // Update the SMS status if it has changed
        if (statusChanged) {
            memberService.updateSmsStatus(memberSmsStatus);
        }
        // Generate the SMS log
        return newSmsLog(params, errorType, freeBaseUsed);
    }

    private Validator getValidator(final Class<? extends SendMessageDTO> type) {
        if (type == SendDirectMessageToMemberDTO.class) {
            final Validator toMember = basicToMemberValidator();
            toMember.property("toMember").add(new SameFromAndToValidation());
            toMember.property("category").add(new RequiredWhenFromAdminValidation());
            return toMember;
        } else if (type == SendMessageToAdminDTO.class) {
            final Validator toAdmin = basicValidator();
            toAdmin.property("category").required();
            return toAdmin;
        } else if (type == SendMessageFromBrokerToMembersDTO.class) {
            final Validator toRegisteredMembers = basicValidator();
            return toRegisteredMembers;
        } else if (type == SendMessageToGroupDTO.class) {
            final Validator toGroup = basicValidator();
            toGroup.property("toGroups").required();
            toGroup.property("category").add(new RequiredWhenFromAdminValidation());
            return toGroup;
        } else if (type == SendMessageFromSystemDTO.class) {
            final Validator fromSystem = basicToMemberValidator();
            fromSystem.property("type").required();
            return fromSystem;
        } else {
            throw new IllegalArgumentException("Unexpected type " + type);
        }
    }

    private Message insertSenderCopy(final SendMessageDTO dto) throws MemberWontReceiveNotificationException {
        // Validate the message being replied
        final Message inReplyTo = checkMessageOwner(dto.getInReplyTo());

        // Update the original message as replied
        if (inReplyTo != null) {
            markAsReplied(inReplyTo);
        }

        if (dto instanceof SendMessageFromSystemDTO) {
            // There is no sender copy for messages from system (a.k.a notifications)
            return null;
        }

        // Insert the sender copy
        Message message = buildFromDTO(dto, Message.Direction.OUTGOING);
        message = messageDao.insert(message);

        // If the message is bulk, assign all members which should receive the messages
        if (dto instanceof SendMessageToGroupDTO) {
            final SendMessageToGroupDTO toGroup = (SendMessageToGroupDTO) dto;
            messageDao.assignPendingToSendByGroups(message, toGroup.getToGroups());
        } else if (dto instanceof SendMessageFromBrokerToMembersDTO) {
            final Member broker = LoggedUser.element();
            messageDao.assignPendingToSendByBroker(message, broker);
        }

        return message;
    }

    /**
     * Marks a given message as replied
     */
    private void markAsReplied(final Message message) {
        if (message != null) {
            message.setReplied(true);
            messageDao.update(message);
        }
    }

    private SmsLog newSmsLog(final SendSmsDTO params, final ErrorType errorType, final boolean freeBaseUsed) {
        final SmsLog newLog = new SmsLog();
        newLog.setDate(Calendar.getInstance());
        newLog.setTargetMember(params.getTargetMember());
        newLog.setChargedMember(params.getChargedMember());
        newLog.setErrorType(errorType);
        newLog.setFreeBaseUsed(freeBaseUsed);
        newLog.setMessageType(params.getMessageType());
        newLog.setSmsMailing(params.getSmsMailing());
        newLog.setSmsType(params.getSmsType());
        newLog.setSmsTypeArgs(params.getSmsTypeArgs());
        return newLog;
    }

    /**
     * Sends the message, returning a set containing the channels actually delivered
     */
    private Set<MessageChannel> send(final Message message, final String smsMessage, final String smsTraceData, Set<MessageChannel> messageChannels) {
        final Member toMember = fetchService.fetch(message.getToMember(), Element.Relationships.GROUP);
        message.setCategory(fetchService.fetch(message.getCategory()));
        final Set<MessageChannel> result = EnumSet.noneOf(MessageChannel.class);

        if (toMember != null) {
            final MemberGroup group = toMember.getMemberGroup();
            final Type type = message.getType();

            // Check which message channels will be used
            if (messageChannels == null) {
                messageChannels = preferenceService.receivedChannels(toMember, type);
            }
            if (CollectionUtils.isEmpty(messageChannels)) {
                // Nothing to send for this member
                return result;
            }

            // Send an e-mail if needed
            final String email = toMember.getEmail();
            if (messageChannels.contains(MessageChannel.EMAIL) && StringUtils.isNotEmpty(email)) {
                InternetAddress replyTo = mailHandler.getInternetAddress(message.getFromMember());
                InternetAddress to = mailHandler.getInternetAddress(toMember);
                mailHandler.sendAfterTransactionCommit(message.getSubject(), replyTo, to, message.getBody(), message.isHtml());
                message.setEmailSent(true);
                result.add(MessageChannel.EMAIL);
            }

            // Check if we need to send a SMS
            if (StringUtils.isNotEmpty(smsMessage) && messageChannels.contains(MessageChannel.SMS) && group.getSmsMessages().contains(type)) {
                // Send the SMS
                final SendSmsDTO sendDTO = new SendSmsDTO();
                sendDTO.setTargetMember(toMember);
                sendDTO.setMessageType(type);
                sendDTO.setText(smsMessage);
                sendDTO.setTraceData(smsTraceData);
                sendSmsAfterTransactionCommit(sendDTO);
                result.add(MessageChannel.SMS);
            }

            // If the user does not want the internal message, return now
            if (!messageChannels.contains(MessageChannel.MESSAGE)) {
                // If not, return now
                return result;
            }
            result.add(MessageChannel.MESSAGE);

        }

        // Insert the internal message
        messageDao.insert(message);

        return result;
    }
}
