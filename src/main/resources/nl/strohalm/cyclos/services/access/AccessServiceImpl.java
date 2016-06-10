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
package nl.strohalm.cyclos.services.access;

import java.math.BigInteger;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.Set;

import nl.strohalm.cyclos.access.AdminMemberPermission;
import nl.strohalm.cyclos.access.AdminSystemPermission;
import nl.strohalm.cyclos.access.BasicPermission;
import nl.strohalm.cyclos.access.BrokerPermission;
import nl.strohalm.cyclos.access.MemberPermission;
import nl.strohalm.cyclos.dao.access.LoginHistoryDAO;
import nl.strohalm.cyclos.dao.access.PasswordHistoryLogDAO;
import nl.strohalm.cyclos.dao.access.PermissionDeniedTraceDAO;
import nl.strohalm.cyclos.dao.access.SessionDAO;
import nl.strohalm.cyclos.dao.access.UserDAO;
import nl.strohalm.cyclos.dao.access.WrongCredentialAttemptsDAO;
import nl.strohalm.cyclos.dao.access.WrongUsernameAttemptsDAO;
import nl.strohalm.cyclos.dao.accounts.cards.CardDAO;
import nl.strohalm.cyclos.dao.members.ElementDAO;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.access.AdminUser;
import nl.strohalm.cyclos.entities.access.Channel;
import nl.strohalm.cyclos.entities.access.Channel.Credentials;
import nl.strohalm.cyclos.entities.access.LoginHistoryLog;
import nl.strohalm.cyclos.entities.access.MemberUser;
import nl.strohalm.cyclos.entities.access.OperatorUser;
import nl.strohalm.cyclos.entities.access.PasswordHistoryLog;
import nl.strohalm.cyclos.entities.access.PasswordHistoryLog.PasswordType;
import nl.strohalm.cyclos.entities.access.Session;
import nl.strohalm.cyclos.entities.access.SessionQuery;
import nl.strohalm.cyclos.entities.access.User;
import nl.strohalm.cyclos.entities.access.User.TransactionPasswordStatus;
import nl.strohalm.cyclos.entities.accounts.cards.Card;
import nl.strohalm.cyclos.entities.accounts.cards.CardType;
import nl.strohalm.cyclos.entities.alerts.MemberAlert;
import nl.strohalm.cyclos.entities.alerts.SystemAlert;
import nl.strohalm.cyclos.entities.customization.fields.CustomField.Type;
import nl.strohalm.cyclos.entities.customization.fields.CustomFieldValue;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.entities.exceptions.UnexpectedEntityException;
import nl.strohalm.cyclos.entities.groups.AdminGroup;
import nl.strohalm.cyclos.entities.groups.BasicGroupSettings;
import nl.strohalm.cyclos.entities.groups.BasicGroupSettings.PasswordPolicy;
import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.groups.MemberGroupSettings;
import nl.strohalm.cyclos.entities.groups.OperatorGroup;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.members.Operator;
import nl.strohalm.cyclos.entities.services.ServiceClient;
import nl.strohalm.cyclos.entities.settings.AccessSettings;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.exceptions.MailSendingException;
import nl.strohalm.cyclos.exceptions.PermissionDeniedException;
import nl.strohalm.cyclos.services.InitializingService;
import nl.strohalm.cyclos.services.access.exceptions.AlreadyConnectedException;
import nl.strohalm.cyclos.services.access.exceptions.BlockedCredentialsException;
import nl.strohalm.cyclos.services.access.exceptions.CredentialsAlreadyUsedException;
import nl.strohalm.cyclos.services.access.exceptions.InactiveMemberException;
import nl.strohalm.cyclos.services.access.exceptions.InvalidCardException;
import nl.strohalm.cyclos.services.access.exceptions.InvalidCredentialsException;
import nl.strohalm.cyclos.services.access.exceptions.InvalidUserForChannelException;
import nl.strohalm.cyclos.services.access.exceptions.NotConnectedException;
import nl.strohalm.cyclos.services.access.exceptions.SessionAlreadyInUseException;
import nl.strohalm.cyclos.services.access.exceptions.SystemOfflineException;
import nl.strohalm.cyclos.services.access.exceptions.UserNotFoundException;
import nl.strohalm.cyclos.services.accounts.cards.CardServiceLocal;
import nl.strohalm.cyclos.services.alerts.AlertServiceLocal;
import nl.strohalm.cyclos.services.application.ApplicationServiceLocal;
import nl.strohalm.cyclos.services.elements.ElementServiceLocal;
import nl.strohalm.cyclos.services.elements.ResetTransactionPasswordDTO;
import nl.strohalm.cyclos.services.fetch.FetchServiceLocal;
import nl.strohalm.cyclos.services.permissions.PermissionServiceLocal;
import nl.strohalm.cyclos.services.settings.SettingsServiceLocal;
import nl.strohalm.cyclos.utils.CacheCleaner;
import nl.strohalm.cyclos.utils.DataIteratorHelper;
import nl.strohalm.cyclos.utils.EntityHelper;
import nl.strohalm.cyclos.utils.HashHandler;
import nl.strohalm.cyclos.utils.MailHandler;
import nl.strohalm.cyclos.utils.PropertyHelper;
import nl.strohalm.cyclos.utils.RangeConstraint;
import nl.strohalm.cyclos.utils.RelationshipHelper;
import nl.strohalm.cyclos.utils.StringHelper;
import nl.strohalm.cyclos.utils.TimePeriod;
import nl.strohalm.cyclos.utils.TransactionHelper;
import nl.strohalm.cyclos.utils.access.LoggedUser;
import nl.strohalm.cyclos.utils.logging.LoggingHandler;
import nl.strohalm.cyclos.utils.logging.TraceLogDTO;
import nl.strohalm.cyclos.utils.notifications.MemberNotificationHandler;
import nl.strohalm.cyclos.utils.query.IteratorList;
import nl.strohalm.cyclos.utils.transaction.CurrentTransactionData;
import nl.strohalm.cyclos.utils.transaction.TransactionEndListener;
import nl.strohalm.cyclos.utils.validation.GeneralValidation;
import nl.strohalm.cyclos.utils.validation.LengthValidation;
import nl.strohalm.cyclos.utils.validation.PropertyValidation;
import nl.strohalm.cyclos.utils.validation.RequiredError;
import nl.strohalm.cyclos.utils.validation.ValidationError;
import nl.strohalm.cyclos.utils.validation.ValidationException;
import nl.strohalm.cyclos.utils.validation.Validator;
import nl.strohalm.cyclos.utils.validation.Validator.Property;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;

/**
 * Implementation class for access services Service.
 * @author rafael
 * @author luis
 */
public class AccessServiceImpl implements AccessServiceLocal, InitializingService {

    private final class LoginPasswordValidation implements PropertyValidation {
        private final Element     element;
        private static final long serialVersionUID = -4369049571487478881L;

        private LoginPasswordValidation(final Element element) {
            this.element = element;
        }

        @Override
        public ValidationError validate(final Object object, final Object property, final Object value) {
            final String loginPassword = (String) value;
            final AccessSettings accessSettings = settingsService.getAccessSettings();
            final boolean numeric = accessSettings.isNumericPassword();
            return resolveValidationError(true, numeric, element, object, property, loginPassword);
        }
    }

    private final class PinValidation implements PropertyValidation {
        private final Member      member;
        private static final long serialVersionUID = -4369049571487478881L;

        private PinValidation(final Member member) {
            this.member = member;
        }

        @Override
        public ValidationError validate(final Object object, final Object property, final Object value) {
            final String loginPassword = (String) value;
            // pin is always numeric
            return resolveValidationError(false, true, member, object, property, loginPassword);
        }
    }

    private static final String        ALLOW_LOGIN_FOR_GROUPS_KEY    = "cyclos.allowLoginForGroups";
    private static final String        DISALLOW_LOGIN_FOR_GROUPS_KEY = "cyclos.disallowLoginForGroups";

    private static final Relationship  FETCH                         = RelationshipHelper.nested(User.Relationships.ELEMENT, Element.Relationships.GROUP);
    private static final Log           LOG                           = LogFactory.getLog(AccessServiceImpl.class);
    private AlertServiceLocal          alertService;
    private FetchServiceLocal          fetchService;
    private ElementServiceLocal        elementService;
    private PermissionServiceLocal     permissionService;
    private SettingsServiceLocal       settingsService;
    private ElementDAO                 elementDao;
    private UserDAO                    userDao;
    private CardDAO                    cardDao;
    private SessionDAO                 sessionDao;
    private WrongCredentialAttemptsDAO wrongCredentialAttemptsDao;
    private WrongUsernameAttemptsDAO   wrongUsernameAttemptsDao;
    private PermissionDeniedTraceDAO   permissionDeniedTraceDao;
    private LoginHistoryDAO            loginHistoryDao;
    private PasswordHistoryLogDAO      passwordHistoryLogDao;
    private MailHandler                mailHandler;
    private LoggingHandler             loggingHandler;
    private HashHandler                hashHandler;
    private ChannelServiceLocal        channelService;
    private ApplicationServiceLocal    applicationService;
    private CardServiceLocal           cardService;
    private MemberNotificationHandler  memberNotificationHandler;
    private Collection<Long>           allowLoginForGroups;
    private Collection<Long>           disallowLoginForGroups;

    private TransactionHelper          transactionHelper;

    @Override
    public void addLoginPasswordValidation(final Element element, final Property property) {
        property.add(new LoginPasswordValidation(element));
    }

    @Override
    public void addPinValidation(final Member member, final Property property) {
        property.add(new PinValidation(member));
    }

    @Override
    public boolean canChangeChannelsAccess(final Member member) {
        return permissionService.permission(member)
                .admin(AdminMemberPermission.ACCESS_CHANGE_CHANNELS_ACCESS)
                .broker(BrokerPermission.MEMBER_ACCESS_CHANGE_CHANNELS_ACCESS)
                .member(MemberPermission.ACCESS_CHANGE_CHANNELS_ACCESS)
                .hasPermission();
    }

    @Override
    public Member changeChannelsAccess(Member member, final Collection<Channel> channels, final boolean verifySmsChannel) {
        member = fetchService.fetch(member, Member.Relationships.CHANNELS);
        final Channel smsChannel = channelService.getSmsChannel();
        // When SMS channel is enabled, it is not set directly by this method. So, we need to ensure it remains related to the member after saving
        if (verifySmsChannel && smsChannel != null && member.getChannels().contains(smsChannel)) {
            channels.add(smsChannel);
        }
        member.setChannels(channels);
        return elementDao.update(member);
    }

    @Override
    public void changeCredentials(final MemberUser user, final String newCredentials) throws CredentialsAlreadyUsedException {
        ServiceClient client = fetchService.fetch(LoggedUser.serviceClient(), RelationshipHelper.nested(ServiceClient.Relationships.CHANNEL, Channel.Relationships.PRINCIPALS));
        switch (client.getChannel().getCredentials()) {
            case LOGIN_PASSWORD:
                changePassword(user, null, newCredentials, false);
                break;
            case PIN:
                changePin(user, newCredentials);
                break;
        }
    }

    @Override
    public User changePassword(final ChangeLoginPasswordDTO params) throws InvalidCredentialsException, BlockedCredentialsException, CredentialsAlreadyUsedException {
        validateChangePassword(params);

        User loggedUser = LoggedUser.user();
        boolean myPassword = loggedUser.equals(params.getUser());

        if (myPassword) {
            // We'll only check the old password if it's not expired
            final boolean isExpired = hasPasswordExpired();
            if (!isExpired) {
                // Check the current password
                final Element loggedElement = LoggedUser.element();
                String member = null;
                if (LoggedUser.isOperator()) {
                    final Operator operator = LoggedUser.element();
                    member = operator.getMember().getUsername();
                }
                checkPassword(member, loggedElement.getUsername(), params.getOldPassword(), LoggedUser.remoteAddress());
            }
        }

        final User user = fetchService.fetch(params.getUser(), RelationshipHelper.nested(User.Relationships.ELEMENT, Element.Relationships.GROUP));
        return changePassword(user, params.getNewPassword(), params.isForceChange());
    }

    @Override
    public MemberUser changePin(final ChangePinDTO params) throws InvalidCredentialsException, BlockedCredentialsException, CredentialsAlreadyUsedException {
        validateChangePin(params);

        User loggedUser = LoggedUser.user();
        boolean myPin = loggedUser.equals(params.getUser());

        if (myPin) {
            // Check whether to enforce the login or transaction password
            final Member loggedMember = (Member) fetchService.fetch(loggedUser.getElement(), Element.Relationships.GROUP);
            final boolean usesTransactionPassword = loggedMember.getMemberGroup().getBasicSettings().getTransactionPassword().isUsed();

            // If the password (or transaction password) is incorrect an exception is thrown
            if (usesTransactionPassword) {
                checkTransactionPassword(loggedMember.getUser(), params.getCredentials(), LoggedUser.remoteAddress());
            } else {
                checkPassword(loggedMember.getUser(), params.getCredentials(), LoggedUser.remoteAddress());
            }
        }
        final MemberUser user = fetchService.fetch(params.getUser(), RelationshipHelper.nested(User.Relationships.ELEMENT, Element.Relationships.GROUP));
        return changePin(user, params.getNewPin());
    }

    @Override
    public MemberUser checkCredentials(Channel channel, MemberUser user, final String credentials, final String remoteAddress, final Member relatedMember) {
        if (StringUtils.isEmpty(credentials)) {
            throw new InvalidCredentialsException(channel.getCredentials(), user);
        }
        channel = fetchService.fetch(channel, Channel.Relationships.PRINCIPALS);

        user = fetchService.fetch(user);
        switch (channel.getCredentials()) {
            case DEFAULT:
            case LOGIN_PASSWORD:
                checkPassword(user, credentials, remoteAddress);
                break;
            case PIN:
                checkPin(user, credentials, channel.getInternalName(), relatedMember);
                break;
            case TRANSACTION_PASSWORD:
                checkTransactionPassword(user, credentials, remoteAddress);
                break;
            case CARD_SECURITY_CODE:
                final Card card = cardService.getActiveCard(user.getMember());
                checkCardSecurityCode(card.getCardNumber(), credentials, channel.getInternalName());
                break;
        }
        return user;
    }

    @Override
    public User checkPassword(final String member, final String username, final String plainPassword, final String remoteAddress) {
        final User user = loadUser(member, username);
        return checkPassword(user, plainPassword, remoteAddress);
    }

    @Override
    public Session checkSession(final String sessionId) throws NotConnectedException {
        try {
            Session session = sessionDao.load(sessionId, false);
            User user = session.getUser();

            final Long id = session.getId();
            final Calendar newExpiration = getSessionTimeout(user, session.isPosWeb()).add(Calendar.getInstance());

            // After the main transaction ends, we need to update the session
            CurrentTransactionData.addTransactionEndListener(new TransactionEndListener() {
                @Override
                protected void onTransactionEnd(final boolean commit) {
                    updateSessionExpiration(id, newExpiration);
                }
            });
            return session;
        } catch (EntityNotFoundException e) {
            throw new NotConnectedException();
        }
    }

    @Override
    public User checkTransactionPassword(final String transactionPassword) throws InvalidCredentialsException, BlockedCredentialsException {
        final User user = LoggedUser.user();
        return checkTransactionPassword(user, transactionPassword, LoggedUser.remoteAddress());
    }

    @Override
    public User disconnect(Session session) throws NotConnectedException {
        try {
            session = fetchService.fetch(session, RelationshipHelper.nested(Session.Relationships.USER, User.Relationships.ELEMENT));
        } catch (EntityNotFoundException e) {
            throw new NotConnectedException();
        }
        sessionDao.delete(session.getId());
        User user = session.getUser();
        if (!isLoggedIn(user)) {
            // If there are no other sessions for that user, set the lastLogin
            user.setLastLogin(Calendar.getInstance());
        }
        return user;
    }

    @Override
    public User disconnect(final User user) {
        int sessions = sessionDao.delete(user);
        if (sessions > 0) {
            // Store the last login
            user.setLastLogin(Calendar.getInstance());
        }
        return fetchService.fetch(user, User.Relationships.ELEMENT);
    }

    @Override
    public void disconnectAllButLogged() {
        final User loggedUser = LoggedUser.user();
        IteratorList<User> iterator = sessionDao.listLoggedUsers();
        try {
            CacheCleaner cacheCleaner = new CacheCleaner(fetchService);
            for (User user : iterator) {
                if (!loggedUser.equals(user)) {
                    disconnect(user);
                    cacheCleaner.clearCache();
                }
            }
        } finally {
            DataIteratorHelper.close(iterator);
        }
    }

    @Override
    public String generatePassword(Group group) {
        if (group instanceof OperatorGroup) {
            group = fetchService.fetch(group, RelationshipHelper.nested(OperatorGroup.Relationships.MEMBER, Element.Relationships.GROUP));
        }
        final Integer min = group.getBasicSettings().getPasswordLength().getMin();
        final boolean onlyNumbers = settingsService.getAccessSettings().isNumericPassword();
        return RandomStringUtils.random(min == null ? 4 : min, !onlyNumbers, true).toLowerCase();
    }

    @Override
    public String generateTransactionPassword() {

        User user = LoggedUser.user();

        // Load operator´s member and group of operator´s member
        if (user instanceof OperatorUser) {
            Element element = user.getElement();
            element = fetchService.fetch(element, RelationshipHelper.nested(Operator.Relationships.MEMBER, Element.Relationships.GROUP));
        }

        // If the transaction password is not used for the logged user, return null
        if (!requestTransactionPassword(user)) {
            throw new UnexpectedEntityException();
        }

        // If there is already a password, return null
        final String current = user.getTransactionPassword();
        if (current != null) {
            return null;
        }

        // Get the chars
        final AccessSettings accessSettings = settingsService.getAccessSettings();
        final String chars = accessSettings.getTransactionPasswordChars();

        // Get the password length
        Group group = user.getElement().getGroup();
        if (group instanceof OperatorGroup) {
            group = fetchService.fetch(group, RelationshipHelper.nested(OperatorGroup.Relationships.MEMBER, Element.Relationships.GROUP));
        }
        final BasicGroupSettings basicSettings = group.getBasicSettings();
        final int length = basicSettings.getTransactionPasswordLength();

        // Generate a new one, and store it
        final StringBuilder buffer = new StringBuilder(length);
        final Random rnd = new Random();
        for (int i = 0; i < length; i++) {
            buffer.append(chars.charAt(rnd.nextInt(chars.length())));
        }
        final String transactionPassword = buffer.toString();
        user.setTransactionPassword(hashHandler.hash(user.getSalt(), transactionPassword));
        user.setTransactionPasswordStatus(TransactionPasswordStatus.ACTIVE);
        user = userDao.update(user);
        return transactionPassword;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Collection<Channel> getChannelsEnabledForMember(Member member) {
        member = fetchService.fetch(member, Element.Relationships.GROUP);
        return CollectionUtils.retainAll(member.getChannels(), member.getMemberGroup().getChannels());
    }

    @Override
    public User getLoggedUser(final String sessionId) throws NotConnectedException {
        try {
            Session session = sessionDao.load(sessionId, false);
            return session.getUser();
        } catch (EntityNotFoundException e) {
            throw new NotConnectedException();
        }
    }

    @Override
    public boolean hasPasswordExpired() {
        Group group = LoggedUser.group();
        if (group instanceof OperatorGroup) {
            group = fetchService.fetch(group, RelationshipHelper.nested(OperatorGroup.Relationships.MEMBER, Element.Relationships.GROUP));
        }
        final TimePeriod exp = group.getBasicSettings().getPasswordExpiresAfter();
        final Calendar passwordDate = LoggedUser.user().getPasswordDate();
        if (passwordDate == null) {
            return true;
        }
        if (exp != null && exp.getNumber() > 0 && passwordDate != null) {
            final Calendar expiresAt = exp.remove(Calendar.getInstance());
            return expiresAt.after(passwordDate);
        }
        return false;
    }

    @Override
    public void initializeService() {
        purgeTraces(Calendar.getInstance());
        purgeExpiredSessions();
    }

    @Override
    public boolean isCardSecurityCodeBlocked(Card card) {
        card = fetchService.reload(card);
        return isBlocked(card.getCardSecurityCodeBlockedUntil());
    }

    @Override
    public boolean isChannelAllowedToBeEnabledForMember(final Channel channel, Member member) {
        member = fetchService.fetch(member, Element.Relationships.GROUP, Member.Relationships.CHANNELS);

        switch (channel.getCredentials()) {
            case TRANSACTION_PASSWORD:
                // Check if the member's group uses transaction password.
                if (!member.getMemberGroup().getBasicSettings().getTransactionPassword().isUsed()) {
                    LOG.warn("The member's group doesn't use transaction password,  member: " + member);
                    return false;
                }
                break;
            case CARD_SECURITY_CODE:
                // Check if the member's group has a Card Type
                if (member.getMemberGroup().getCardType() == null) {
                    LOG.warn("The member's group doesn't have a card type,  member: " + member);
                    return false;
                }
                break;
        }

        // If the group doesn't have access to the channel, the member can't access it too
        if (!isChannelEnabledForGroup(channel, member.getMemberGroup())) {
            return false;
        }

        return true;
    }

    @Override
    public boolean isChannelEnabledForMember(final Channel channel, Member member) {
        // Check the member access customization too
        member = fetchService.fetch(member, Member.Relationships.CHANNELS);
        return isChannelAllowedToBeEnabledForMember(channel, member) && (channel.getInternalName().equals(Channel.WEB) || member.getChannels().contains(channel));
    }

    @Override
    public boolean isChannelEnabledForMember(final String channelInternalName, final Member member) {
        final Channel channel = channelService.loadByInternalName(channelInternalName);
        return isChannelEnabledForMember(channel, member);
    }

    @Override
    public boolean isLoggedIn(final User user) {
        return sessionDao.isLoggedIn(user);
    }

    @Override
    public boolean isLoginBlocked(User user) {
        user = fetchService.reload(user);
        return isBlocked(user.getPasswordBlockedUntil());
    }

    @Override
    public boolean isObviousCredential(Element element, final String credential) {

        // Ensure not to fetch the element for new records
        if (element.isPersistent()) {
            element = fetchService.fetch(element, Element.Relationships.USER, Member.Relationships.CUSTOM_VALUES);
        }

        // If the credential is equals to the username, it is obvious
        if (credential.equalsIgnoreCase(element.getUsername())) {
            return true;
        }

        // If the credential is equals to any word of the full name, it is obvious
        final String[] nameParts = StringUtils.split(element.getName(), " .,/-\\");
        for (final String part : nameParts) {
            if (credential.equalsIgnoreCase(part)) {
                return true;
            }
        }

        // If the credential is equals to the user part of the e-mail, it is obvious
        final String email = element.getEmail();
        if (StringUtils.isNotEmpty(email) && email.contains("@")) {
            final String mailUser = StringUtils.split(email, "@", 1)[0];
            if (credential.equalsIgnoreCase(mailUser)) {
                return true;
            }
        }

        // If the credential matches custom field values, it is obvious
        final Collection<CustomFieldValue> customValues = PropertyHelper.get(element, "customValues");
        final LocalSettings localSettings = settingsService.getLocalSettings();
        for (final CustomFieldValue fieldValue : customValues) {
            final Type type = fieldValue.getField().getType();
            final String stringValue = fieldValue.getStringValue();
            if (StringUtils.isEmpty(stringValue)) {
                continue;
            }
            switch (type) {
                case DATE:
                    // The credential cannot be the same as a date
                    final String unmasked = StringHelper.removeMask(localSettings.getDatePattern().getPattern(), stringValue);
                    if (credential.equals(unmasked)) {
                        return true;
                    }
                    break;
                case INTEGER:
                    // The credential cannot be equal to an integer
                    if (credential.equals(stringValue)) {
                        return true;
                    }
                    break;
                case STRING:
                    // The credential cannot be contained in a string
                    if (stringValue.contains(credential)) {
                        return true;
                    }
                    break;
            }
        }

        // When all chars have the same distance, it's obvious (things like 1234, 7654, abcdef and so on...)
        if (credential.length() > 1) {
            final Set<Integer> diffs = new HashSet<Integer>();
            for (int i = 1, len = credential.length(); i < len; i++) {
                final char current = credential.charAt(i);
                final char previous = credential.charAt(i - 1);
                diffs.add(current - previous);
                if (diffs.size() > 1) {
                    // More than 1 difference means it's not obvious
                    break;
                }
            }
            if (diffs.size() == 1) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean isPinBlocked(MemberUser user) {
        user = fetchService.reload(user);
        return isBlocked(user.getPinBlockedUntil());
    }

    @Override
    public User login(User user, final String plainCredentials, final String channelName, final boolean isPosWeb, final String remoteAddress, final String sessionId) throws UserNotFoundException, InvalidCredentialsException, BlockedCredentialsException, SessionAlreadyInUseException, AlreadyConnectedException {
        if (user == null) {
            throw new UnexpectedEntityException();
        }
        user = fetchService.fetch(user, RelationshipHelper.nested(User.Relationships.ELEMENT, Element.Relationships.GROUP));

        // When the system is offline, only allow login users with manage online state permission
        if (!applicationService.isOnline()) {
            if (!permissionService.hasPermission(user.getElement().getGroup(), AdminSystemPermission.TASKS_ONLINE_STATE)) {
                throw new SystemOfflineException();
            }
        }

        // Validate the given session id
        try {
            Session session = sessionDao.load(sessionId, false);
            if (session.getUser().equals(user) && remoteAddress.equals(session.getRemoteAddress()) && isPosWeb == session.isPosWeb()) {
                // If the same logged user and remote address are under the same session id, return ok
                return session.getUser();
            }
            // The session is in use by another user / remote address
            throw new SessionAlreadyInUseException(user.getUsername());
        } catch (EntityNotFoundException e) {
            // Ok, no session with the given id
        }

        final Channel channel = channelService.loadByInternalName(channelName);

        final boolean isMainWebChannel = Channel.WEB.equals(channel.getInternalName());
        if (user instanceof MemberUser) {
            // For members, check the channel's credentials
            checkCredentials(channel, (MemberUser) user, plainCredentials, remoteAddress, null);
            // Also, ensure the channel is enabled for the member (if not the main web channel)
            if (!isMainWebChannel && !isChannelEnabledForMember(channelName, ((MemberUser) user).getMember())) {
                throw new InvalidUserForChannelException(user.getUsername());
            }
        } else {
            // For admins or operators, always check the login
            if (channel != null && !isMainWebChannel) {
                // Also, they can only login in the main web channel
                throw new PermissionDeniedException();
            }
            checkPassword(user, plainCredentials, remoteAddress);
        }

        // Check if already connected
        AccessSettings accessSettings = settingsService.getAccessSettings();
        if (!accessSettings.isAllowMultipleLogins() && isLoggedIn(user)) {
            throw new AlreadyConnectedException();
        }

        // Initialize the permission denied counter
        permissionDeniedTraceDao.clear(user);

        Calendar now = Calendar.getInstance();

        // Store the session
        Session session = new Session();
        session.setCreationDate(now);
        session.setExpirationDate(getSessionTimeout(user, isPosWeb).add(now));
        session.setUser(user);
        session.setIdentifier(sessionId);
        session.setRemoteAddress(remoteAddress);
        session.setPosWeb(isPosWeb);
        sessionDao.insert(session);

        // Save an entry in the login history
        final LoginHistoryLog loginHistoryLog = new LoginHistoryLog();
        loginHistoryLog.setUser(user);
        loginHistoryLog.setDate(now);
        loginHistoryLog.setRemoteAddress(remoteAddress);
        loginHistoryDao.insert(loginHistoryLog);

        // Generate log
        TraceLogDTO logParams = new TraceLogDTO();
        logParams.setUser(user);
        logParams.setSessionId(sessionId);
        logParams.setRemoteAddress(remoteAddress);
        loggingHandler.traceLogin(logParams);

        // Initialize the login data
        LoggedUser.init(user);

        return user;
    }

    @Override
    public User logout(final String sessionId) {
        try {
            Session session = sessionDao.load(sessionId, true);
            sessionDao.delete(session.getId());
            User user = session.getUser();

            user.setLastLogin(Calendar.getInstance());

            // Trace to the log file
            TraceLogDTO logParams = new TraceLogDTO();
            logParams.setUser(user);
            logParams.setSessionId(sessionId);
            logParams.setRemoteAddress(session.getRemoteAddress());
            loggingHandler.traceLogout(logParams);

            // Clean up the LoggedUser
            if (LoggedUser.hasUser() && user.equals(LoggedUser.user())) {
                LoggedUser.cleanup();
            }

            return user;
        } catch (EntityNotFoundException e) {
            return null;
        }
    }

    @Override
    public boolean notifyPermissionDeniedException() {
        if (!LoggedUser.hasUser()) {
            return false;
        }

        return transactionHelper.runInNewTransaction(new TransactionCallback<Boolean>() {
            @Override
            public Boolean doInTransaction(final TransactionStatus status) {
                final User user = fetchService.fetch(LoggedUser.user(), RelationshipHelper.nested(User.Relationships.ELEMENT, Element.Relationships.GROUP));

                // Already blocked
                if (isBlocked(user.getPasswordBlockedUntil())) {
                    return true;
                }

                // Check if maxTries has been reached
                final BasicGroupSettings basicSettings = user.getElement().getGroup().getBasicSettings();
                final int maxTries = basicSettings.getMaxPasswordWrongTries();
                if (maxTries > 0) {
                    permissionDeniedTraceDao.record(user);
                    int tries = permissionDeniedTraceDao.count(wrongAttemptsLimit(), user);
                    if (tries == maxTries) {
                        // Send an alert
                        if (user instanceof AdminUser) {
                            alertService.create(SystemAlert.Alerts.ADMIN_LOGIN_BLOCKED_BY_PERMISSION_DENIEDS, user.getUsername(), tries, LoggedUser.remoteAddress());
                        } else if (user instanceof MemberUser) {
                            alertService.create(((MemberUser) user).getMember(), MemberAlert.Alerts.LOGIN_BLOCKED_BY_PERMISSION_DENIEDS, tries, LoggedUser.remoteAddress());
                        }
                        // Block the user and clear any previous traces
                        final Calendar mayLoginAt = basicSettings.getDeactivationAfterMaxPasswordTries().add(Calendar.getInstance());
                        user.setPasswordBlockedUntil(mayLoginAt);
                        permissionDeniedTraceDao.clear(user);
                        return true;
                    }
                }
                return false;
            }
        });
    }

    @Override
    public void purgeExpiredSessions() {
        sessionDao.purgeExpired();
    }

    @Override
    public void purgeTraces(final Calendar time) {
        Calendar limit = (Calendar) time.clone();
        limit.add(Calendar.DAY_OF_MONTH, -1);
        wrongCredentialAttemptsDao.clear(limit);
        wrongUsernameAttemptsDao.clear(limit);
        permissionDeniedTraceDao.clear(limit);
    }

    @Override
    public User reenableLogin(User user) {
        user = fetchService.fetch(user);
        user.setPasswordBlockedUntil(null);
        wrongCredentialAttemptsDao.clear(user, Credentials.LOGIN_PASSWORD);
        return user;
    }

    @Override
    public MemberUser resetPassword(MemberUser user) throws MailSendingException {

        // Check if password will be sent by mail
        user = fetchService.fetch(user, FETCH);
        final MemberGroup group = user.getMember().getMemberGroup();
        final boolean sendPasswordByEmail = group.getMemberSettings().isSendPasswordByEmail();

        String newPassword = null;
        if (sendPasswordByEmail) {
            // If send by mail, generate a new password
            newPassword = generatePassword(group);
        }

        // Update the user
        user.setPassword(hashHandler.hash(user.getSalt(), newPassword));
        user.setPasswordDate(null);
        userDao.update(user);

        if (sendPasswordByEmail) {
            // Send the password by mail
            mailHandler.sendResetPassword(user.getMember(), newPassword);
        }
        return user;
    }

    @Override
    public User resetTransactionPassword(final ResetTransactionPasswordDTO dto) {
        User user = dto.getUser();
        user.setTransactionPassword(null);
        user.setTransactionPasswordStatus(dto.isAllowGeneration() ? TransactionPasswordStatus.PENDING : TransactionPasswordStatus.BLOCKED);
        return userDao.update(user);
    }

    @Override
    public List<Session> searchSessions(final SessionQuery query) {
        return sessionDao.search(query);
    }

    public void setAlertServiceLocal(final AlertServiceLocal alertService) {
        this.alertService = alertService;
    }

    public void setApplicationServiceLocal(final ApplicationServiceLocal applicationService) {
        this.applicationService = applicationService;
    }

    public void setCardDao(final CardDAO cardDao) {
        this.cardDao = cardDao;
    }

    public void setCardServiceLocal(final CardServiceLocal cardService) {
        this.cardService = cardService;
    }

    public void setChannelServiceLocal(final ChannelServiceLocal channelService) {
        this.channelService = channelService;
    }

    public void setCyclosProperties(final Properties cyclosProperties) {
        try {
            allowLoginForGroups = EntityHelper.parseIds(cyclosProperties.getProperty(ALLOW_LOGIN_FOR_GROUPS_KEY));
        } catch (Exception e) {
            throw new IllegalStateException("Invalid value for " + ALLOW_LOGIN_FOR_GROUPS_KEY + " in cyclos.properties", e);
        }
        try {
            disallowLoginForGroups = EntityHelper.parseIds(cyclosProperties.getProperty(DISALLOW_LOGIN_FOR_GROUPS_KEY));
        } catch (Exception e) {
            throw new IllegalStateException("Invalid value for " + DISALLOW_LOGIN_FOR_GROUPS_KEY + " in cyclos.properties", e);
        }
    }

    public void setElementDao(final ElementDAO elementDao) {
        this.elementDao = elementDao;
    }

    public void setElementServiceLocal(final ElementServiceLocal elementService) {
        this.elementService = elementService;
    }

    public void setFetchServiceLocal(final FetchServiceLocal fetchService) {
        this.fetchService = fetchService;
    }

    public void setHashHandler(final HashHandler hashHandler) {
        this.hashHandler = hashHandler;
    }

    public void setLoggingHandler(final LoggingHandler loggingHandler) {
        this.loggingHandler = loggingHandler;
    }

    public void setLoginHistoryDao(final LoginHistoryDAO loginHistoryDao) {
        this.loginHistoryDao = loginHistoryDao;
    }

    public void setMailHandler(final MailHandler mailHandler) {
        this.mailHandler = mailHandler;
    }

    public void setMemberNotificationHandler(final MemberNotificationHandler memberNotificationHandler) {
        this.memberNotificationHandler = memberNotificationHandler;
    }

    public void setPasswordHistoryLogDao(final PasswordHistoryLogDAO passwordHistoryLogDao) {
        this.passwordHistoryLogDao = passwordHistoryLogDao;
    }

    public void setPermissionDeniedTraceDao(final PermissionDeniedTraceDAO permissionDeniedTraceDao) {
        this.permissionDeniedTraceDao = permissionDeniedTraceDao;
    }

    public void setPermissionServiceLocal(final PermissionServiceLocal permissionService) {
        this.permissionService = permissionService;
    }

    public void setSessionDao(final SessionDAO sessionDao) {
        this.sessionDao = sessionDao;
    }

    public void setSettingsServiceLocal(final SettingsServiceLocal settingsService) {
        this.settingsService = settingsService;
    }

    public void setTransactionHelper(final TransactionHelper transactionHelper) {
        this.transactionHelper = transactionHelper;
    }

    public void setUserDao(final UserDAO userDao) {
        this.userDao = userDao;
    }

    public void setWrongCredentialAttemptsDao(final WrongCredentialAttemptsDAO wrongCredentialAttemptsDao) {
        this.wrongCredentialAttemptsDao = wrongCredentialAttemptsDao;
    }

    public void setWrongUsernameAttemptsDao(final WrongUsernameAttemptsDAO wrongUsernameAttemptsDao) {
        this.wrongUsernameAttemptsDao = wrongUsernameAttemptsDao;
    }

    @Override
    public Card unblockCardSecurityCode(final BigInteger cardNumber) {
        Card card = cardDao.loadByNumber(cardNumber);
        card.setCardSecurityCodeBlockedUntil(null);
        wrongCredentialAttemptsDao.clear(card);
        return card;
    }

    @Override
    public MemberUser unblockPin(MemberUser user) {
        user = fetchService.fetch(user);
        user.setPinBlockedUntil(null);
        wrongCredentialAttemptsDao.clear(user, Credentials.PIN);
        return user;
    }

    @Override
    public void validateChangePassword(final ChangeLoginPasswordDTO params) throws ValidationException {
        final Validator validator = new Validator("changePassword");
        validator.property("user").required();
        if (LoggedUser.hasUser() && LoggedUser.user().equals(params.getUser())) {
            // The old password is required if it is not expired
            if (!hasPasswordExpired()) {
                validator.property("oldPassword").required();
            }
        }
        final User user = fetchService.fetch(params.getUser(), RelationshipHelper.nested(User.Relationships.ELEMENT, Element.Relationships.GROUP));
        if (user != null) {
            validator.property("newPassword").required().add(new LoginPasswordValidation(user.getElement()));
            validator.property("newPasswordConfirmation").required();
        }
        validator.general(new GeneralValidation() {
            private static final long serialVersionUID = -4110708889147050967L;

            @Override
            public ValidationError validate(final Object object) {
                final ChangeLoginPasswordDTO params = (ChangeLoginPasswordDTO) object;
                final String newPassword = params.getNewPassword();
                final String newPasswordConfirmation = params.getNewPasswordConfirmation();
                if (StringUtils.isNotEmpty(newPassword) && StringUtils.isNotEmpty(newPasswordConfirmation) && !newPassword.equals(newPasswordConfirmation)) {
                    return new ValidationError("errors.passwords");
                }
                return null;
            }
        });
        validator.validate(params);
    }

    @Override
    public void validateChangePin(final ChangePinDTO params) {
        if (params.getUser() == null) {
            throw new ValidationException("user", "changePin.user", new RequiredError());
        }
        boolean myPin = LoggedUser.user().equals(params.getUser());
        final Validator validator = new Validator("changePin");
        if (myPin) {
            final MemberGroup group = LoggedUser.group();
            final boolean isTP = group.getBasicSettings().getTransactionPassword().isUsed();
            final String key = isTP ? "channel.credentials.TRANSACTION_PASSWORD" : "channel.credentials.LOGIN_PASSWORD";
            validator.property("credentials").key(key).required();
        } else {
            validator.property("user").required();
        }
        final MemberUser user = fetchService.fetch(params.getUser(), User.Relationships.ELEMENT);
        if (user != null) {
            validator.property("newPin").required().add(new PinValidation(user.getMember()));
            validator.property("newPinConfirmation").required();
        }
        validator.general(new GeneralValidation() {
            private static final long serialVersionUID = -4110708889147050967L;

            @Override
            public ValidationError validate(final Object object) {
                final ChangePinDTO params = (ChangePinDTO) object;
                final String newPin = params.getNewPin();
                final String newPinConfirmation = params.getNewPinConfirmation();
                if (StringUtils.isNotEmpty(newPin) && StringUtils.isNotEmpty(newPinConfirmation) && !newPin.equals(newPinConfirmation)) {
                    return new ValidationError("changePin.error.pinsAreNotEqual");
                }
                return null;
            }
        });
        validator.validate(params);
    }

    public ValidationError validateLoginPassword(final Element element, final String loginPassword) {
        return new LoginPasswordValidation(element).validate(element.getUser(), "password", loginPassword);
    }

    @Override
    public User verifyLogin(final String member, final String username, final String remoteAddress) throws UserNotFoundException, InactiveMemberException, PermissionDeniedException {
        try {
            final User user = loadUser(member, username);

            // Check if this user is allowed to login on this Cyclos instance
            Long groupId;
            if (user instanceof OperatorUser) {
                // For operators, check for the member's group id
                groupId = ((OperatorUser) user).getOperator().getMember().getGroup().getId();
            } else {
                groupId = user.getElement().getGroup().getId();
            }

            // Check for the group white-list
            if (!allowLoginForGroups.isEmpty() && !allowLoginForGroups.contains(groupId)) {
                // Not allowed to login - respond just like an invalid user
                throw new UserNotFoundException(username);
            }

            // Check for the group black-list
            if (!disallowLoginForGroups.isEmpty() && disallowLoginForGroups.contains(groupId)) {
                // Not allowed to login - respond just like an invalid user
                throw new UserNotFoundException(username);
            }

            // Check if there's an active password
            if (StringUtils.isEmpty(user.getPassword())) {
                throw new InactiveMemberException(username);
            }

            // Check if the member has permission to login
            if (!permissionService.hasPermission(user.getElement().getGroup(), BasicPermission.BASIC_LOGIN)) {
                throw new PermissionDeniedException();
            }

            // Username exists: remove the records for the given remote address
            wrongUsernameAttemptsDao.clear(remoteAddress);

            return user;
        } catch (final EntityNotFoundException e) {
            // Record the incorrect attempt
            wrongUsernameAttemptsDao.record(remoteAddress);

            // Check if an alert will be sent
            final int maxTries = settingsService.getAlertSettings().getAmountIncorrectLogin();
            if (maxTries > 0) {
                int wrongTries = wrongUsernameAttemptsDao.count(wrongAttemptsLimit(), remoteAddress);
                if (wrongTries == maxTries) {
                    alertService.create(SystemAlert.Alerts.MAX_INCORRECT_LOGIN_ATTEMPTS, wrongTries, remoteAddress);
                    wrongUsernameAttemptsDao.clear(remoteAddress);
                }
            }
            throw new UserNotFoundException(username);
        }
    }

    @Override
    public Calendar wrongAttemptsLimit() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        return calendar;
    }

    private <U extends User> U changePassword(U user, final String oldPassword, final String plainNewPassword, final boolean forceChange) {
        user = fetchService.reload(user, RelationshipHelper.nested(User.Relationships.ELEMENT, Element.Relationships.GROUP));

        // Before changing, ensure that the new password is valid
        final ValidationError validationResult = new LoginPasswordValidation(user.getElement()).validate(user, "password", plainNewPassword);
        if (validationResult != null) {
            throw new ValidationException("password", "channel.credentials.LOGIN_PASSWORD", validationResult);
        }

        final String currentPassword = user.getPassword();
        final String hashedOldPassword = hashHandler.hash(user.getSalt(), oldPassword);
        if (StringUtils.isNotEmpty(oldPassword) && !hashedOldPassword.equalsIgnoreCase(currentPassword)) {
            throw new InvalidCredentialsException(Credentials.LOGIN_PASSWORD, user);
        }

        final String newPassword = hashHandler.hash(user.getSalt(), plainNewPassword);

        final PasswordPolicy passwordPolicy = user.getElement().getGroup().getBasicSettings().getPasswordPolicy();
        if (passwordPolicy != PasswordPolicy.NONE) {
            // Check if it was already in use when there's a password policy
            if (StringUtils.trimToEmpty(currentPassword).equalsIgnoreCase(newPassword) || passwordHistoryLogDao.wasAlreadyUsed(user, PasswordType.LOGIN, newPassword)) {
                throw new CredentialsAlreadyUsedException(Credentials.LOGIN_PASSWORD, user);
            }
        }

        // Ensure the login password is not equals to the pin or transaction password
        if (newPassword.equalsIgnoreCase(user.getTransactionPassword()) || (user instanceof MemberUser && newPassword.equalsIgnoreCase(((MemberUser) user).getPin()))) {
            throw new ValidationException("changePassword.error.sameAsTransactionPasswordOrPin");
        }

        user.setPassword(newPassword);
        user.setPasswordDate(forceChange ? null : Calendar.getInstance());
        // Ensure that the returning user will have the ELEMENT and GROUP fetched
        user = fetchService.fetch(userDao.update(user), FETCH);
        if (user instanceof OperatorUser) {
            // When an operator, also ensure that the member will have the ELEMENT and GROUP fetched
            final Operator operator = ((OperatorUser) user).getOperator();
            final Member member = fetchService.fetch(operator.getMember(), Element.Relationships.USER, Element.Relationships.GROUP);
            operator.setMember(member);
        }

        // Log the password history
        if (StringUtils.isNotEmpty(currentPassword)) {
            final PasswordHistoryLog log = new PasswordHistoryLog();
            log.setDate(Calendar.getInstance());
            log.setUser(user);
            log.setType(PasswordType.LOGIN);
            log.setPassword(currentPassword);
            passwordHistoryLogDao.insert(log);
        }

        return user;
    }

    private User changePassword(User user, final String plainPassword, final boolean forceChange) {
        // Before changing, ensure that the new password is valid
        final ValidationError validationResult = new LoginPasswordValidation(user.getElement()).validate(user, "password", plainPassword);
        if (validationResult != null) {
            throw new ValidationException("password", "channel.credentials.LOGIN_PASSWORD", validationResult);
        }

        final String password = hashHandler.hash(user.getSalt(), plainPassword);
        user = fetchService.fetch(user, RelationshipHelper.nested(User.Relationships.ELEMENT, Element.Relationships.GROUP));
        final String currentPassword = user.getPassword();

        final PasswordPolicy passwordPolicy = user.getElement().getGroup().getBasicSettings().getPasswordPolicy();
        if (passwordPolicy != null && passwordPolicy != PasswordPolicy.NONE) {
            // Check if it was already in use when there's a password policy
            if (StringUtils.trimToEmpty(currentPassword).equalsIgnoreCase(password) || passwordHistoryLogDao.wasAlreadyUsed(user, PasswordType.LOGIN, password)) {
                throw new CredentialsAlreadyUsedException(Credentials.LOGIN_PASSWORD, user);
            }
        }

        // Ensure the login password is not equals to the pin or transaction password
        final String pin = user instanceof MemberUser ? ((MemberUser) user).getPin() : null;
        if (password.equalsIgnoreCase(pin) || password.equalsIgnoreCase(user.getTransactionPassword())) {
            throw new ValidationException("changePassword.error.sameAsTransactionPasswordOrPin");
        }

        // Set the new password
        user.setPassword(password);
        if (forceChange) {
            user.setPasswordDate(null);
        } else {
            user.setPasswordDate(Calendar.getInstance());
        }

        // Log the password history
        if (StringUtils.isNotEmpty(currentPassword)) {
            final PasswordHistoryLog log = new PasswordHistoryLog();
            log.setDate(Calendar.getInstance());
            log.setUser(user);
            log.setType(PasswordType.LOGIN);
            log.setPassword(currentPassword);
            passwordHistoryLogDao.insert(log);
        }
        user = userDao.update(user);
        return user;
    }

    private MemberUser changePin(MemberUser user, final String plainPin) {

        // Before changing, ensure that the new pin is valid
        final ValidationError validationResult = new PinValidation(user.getMember()).validate(user, "pin", plainPin);
        if (validationResult != null) {
            throw new ValidationException("pin", "channel.credentials.PIN", validationResult);
        }

        final String pin = hashHandler.hash(user.getSalt(), plainPin);
        user = fetchService.fetch(user, RelationshipHelper.nested(User.Relationships.ELEMENT, Element.Relationships.GROUP));
        final String currentPin = user.getPin();

        final PasswordPolicy passwordPolicy = user.getElement().getGroup().getBasicSettings().getPasswordPolicy();
        if (passwordPolicy != null && passwordPolicy != PasswordPolicy.NONE) {
            // Check if it was already in use when there's a password policy
            if (StringUtils.trimToEmpty(currentPin).equalsIgnoreCase(pin) || passwordHistoryLogDao.wasAlreadyUsed(user, PasswordType.PIN, pin)) {
                throw new CredentialsAlreadyUsedException(Credentials.PIN, user);
            }
        }

        // Ensure the login password is not equals to the pin or transaction password
        if (pin.equalsIgnoreCase(user.getPassword()) || pin.equalsIgnoreCase(user.getTransactionPassword())) {
            throw new ValidationException("changePin.error.sameAsLoginOrTransactionPassword");
        }

        // Set the new pin
        user.setPin(pin);

        // Log the pin history
        if (StringUtils.isNotEmpty(currentPin)) {
            final PasswordHistoryLog log = new PasswordHistoryLog();
            log.setDate(Calendar.getInstance());
            log.setUser(user);
            log.setType(PasswordType.PIN);
            log.setPassword(currentPin);
            passwordHistoryLogDao.insert(log);
        }

        return user;
    }

    private Card checkCardSecurityCode(final BigInteger cardNumber, String securityCode, final String channel) {
        Card card;
        try {
            card = cardService.loadByNumber(cardNumber, RelationshipHelper.nested(Card.Relationships.OWNER, Element.Relationships.USER), Card.Relationships.CARD_TYPE);
            if (card.getStatus() != Card.Status.ACTIVE) {
                // Ensure the card is active
                throw new Exception();
            }
        } catch (final Exception e) {
            throw new InvalidCardException();
        }
        User user = card.getOwner().getUser();

        // Check if not already blocked
        if (isBlocked(card.getCardSecurityCodeBlockedUntil())) {
            throw new BlockedCredentialsException(Credentials.CARD_SECURITY_CODE, user);
        }

        // If the securiry code is manual, it is stored hashed, so we must hash the input as well
        if (!card.getCardType().isShowCardSecurityCode()) {
            securityCode = hashHandler.hash(user.getSalt(), securityCode);
        }

        final String storedCode = card.getCardSecurityCode();
        if (StringUtils.isEmpty(storedCode) || !securityCode.equals(storedCode)) {
            final CardType cardType = card.getCardType();
            final int maxTries = cardType.getMaxSecurityCodeTries();
            wrongCredentialAttemptsDao.record(card);
            int wrongAttempts = wrongCredentialAttemptsDao.count(wrongAttemptsLimit(), card);
            if (wrongAttempts == maxTries) {
                // Notify blocking
                memberNotificationHandler.blockedCredentialsNotification(user, Credentials.CARD_SECURITY_CODE);

                // Mark the card as blocked, and remove previous attempts
                final TimePeriod blockTime = cardType.getSecurityCodeBlockTime();
                card.setCardSecurityCodeBlockedUntil(blockTime.add(Calendar.getInstance()));
                wrongCredentialAttemptsDao.clear(card);

                throw new BlockedCredentialsException(Credentials.CARD_SECURITY_CODE, user);
            }
            throw new InvalidCredentialsException(Credentials.CARD_SECURITY_CODE, user);
        }

        // Everything ok - remove any previous wrong attempts
        wrongCredentialAttemptsDao.clear(card);
        return card;
    }

    private User checkPassword(User user, final String plainPassword, final String remoteAddress) {
        user = fetchService.fetch(user, RelationshipHelper.nested(User.Relationships.ELEMENT, Element.Relationships.GROUP));

        // Check if not already blocked
        if (isBlocked(user.getPasswordBlockedUntil())) {
            throw new BlockedCredentialsException(Credentials.LOGIN_PASSWORD, user);
        }

        // Check the password
        final String password = hashHandler.hash(user.getSalt(), plainPassword);
        final String userPassword = user.getPassword();
        if (userPassword == null || !userPassword.equalsIgnoreCase(StringUtils.trimToNull(password))) {
            final BasicGroupSettings basicSettings = user.getElement().getGroup().getBasicSettings();

            // Check if maxTries has been reached
            final int maxTries = basicSettings.getMaxPasswordWrongTries();
            wrongCredentialAttemptsDao.record(user, Credentials.LOGIN_PASSWORD);
            int wrongAttempts = wrongCredentialAttemptsDao.count(wrongAttemptsLimit(), user, Credentials.LOGIN_PASSWORD);
            if (wrongAttempts == maxTries) {
                TimePeriod blockTime = basicSettings.getDeactivationAfterMaxPasswordTries();
                // Create the alert
                if (user instanceof AdminUser) {
                    alertService.create(SystemAlert.Alerts.ADMIN_LOGIN_BLOCKED_BY_TRIES, user.getUsername(), wrongAttempts, remoteAddress);
                } else if (user instanceof MemberUser) {
                    alertService.create(((MemberUser) user).getMember(), MemberAlert.Alerts.LOGIN_BLOCKED_BY_TRIES, wrongAttempts, remoteAddress);
                }
                // Notify the user
                memberNotificationHandler.blockedCredentialsNotification(user, Credentials.LOGIN_PASSWORD);

                // Set the block time and clear previous attempts
                user.setPasswordBlockedUntil(blockTime.add(Calendar.getInstance()));
                wrongCredentialAttemptsDao.clear(user, Credentials.LOGIN_PASSWORD);

                throw new BlockedCredentialsException(Credentials.LOGIN_PASSWORD, user);
            }
            throw new InvalidCredentialsException(Credentials.LOGIN_PASSWORD, user);
        }

        // Everything ok - remove any previous wrong attempts
        wrongCredentialAttemptsDao.clear(user, Credentials.LOGIN_PASSWORD);
        return user;
    }

    private MemberUser checkPin(MemberUser user, final String plainPin, final String channel, final Member relatedMember) {
        user = fetchService.fetch(user, RelationshipHelper.nested(User.Relationships.ELEMENT, Element.Relationships.GROUP));

        // Check if not already blocked
        if (isBlocked(user.getPinBlockedUntil())) {
            throw new BlockedCredentialsException(Credentials.PIN, user);
        }

        final Member member = user.getMember();
        final String userPin = user.getPin();
        final String pin = hashHandler.hash(user.getSalt(), plainPin);

        // Check the pin
        if (userPin == null || !userPin.equalsIgnoreCase(StringUtils.trimToNull(pin))) {
            final MemberGroupSettings memberSettings = member.getMemberGroup().getMemberSettings();
            final int maxTries = memberSettings.getMaxPinWrongTries();
            wrongCredentialAttemptsDao.record(user, Credentials.PIN);
            int wrongAttempts = wrongCredentialAttemptsDao.count(wrongAttemptsLimit(), user, Credentials.PIN);
            if (wrongAttempts == maxTries) {
                final TimePeriod blockTime = memberSettings.getPinBlockTimeAfterMaxTries();
                final String relatedUsername = relatedMember == null ? "" : relatedMember.getUsername();

                // Create an alert and notify the member
                alertService.create(member, MemberAlert.Alerts.PIN_BLOCKED_BY_TRIES, maxTries, channel, relatedUsername);
                memberNotificationHandler.blockedCredentialsNotification(user, Credentials.PIN);

                // Mark the pin as blocked, and clear previous wrong attempts
                user.setPinBlockedUntil(blockTime.add(Calendar.getInstance()));
                wrongCredentialAttemptsDao.clear(user, Credentials.PIN);

                throw new BlockedCredentialsException(Credentials.PIN, user);
            }
            throw new InvalidCredentialsException(Credentials.PIN, user);
        }

        // Everything ok - remove any previous wrong attempts
        wrongCredentialAttemptsDao.clear(user, Credentials.PIN);
        return user;
    }

    private User checkTransactionPassword(User user, final String plainTransactionPassword, final String remoteAddress) {
        user = fetchService.fetch(user, RelationshipHelper.nested(User.Relationships.ELEMENT, Element.Relationships.GROUP));

        // Check if not already blocked
        if (user.getTransactionPasswordStatus().equals(MemberUser.TransactionPasswordStatus.BLOCKED)) {
            throw new BlockedCredentialsException(Credentials.TRANSACTION_PASSWORD, user);
        }

        final String transactionPassword = hashHandler.hash(user.getSalt(), plainTransactionPassword == null ? null : plainTransactionPassword.toUpperCase());
        String userTransactionPassword = user.getTransactionPassword();
        if (userTransactionPassword == null || !userTransactionPassword.equalsIgnoreCase(transactionPassword)) {
            final Group group = user.getElement().getGroup();
            final BasicGroupSettings settings = group.getBasicSettings();
            final int maxTries = settings.getMaxTransactionPasswordWrongTries();
            wrongCredentialAttemptsDao.record(user, Credentials.TRANSACTION_PASSWORD);
            int wrongAttempts = wrongCredentialAttemptsDao.count(wrongAttemptsLimit(), user, Credentials.TRANSACTION_PASSWORD);
            if (wrongAttempts == maxTries) {
                // Send an alert
                if (user instanceof AdminUser) {
                    alertService.create(SystemAlert.Alerts.ADMIN_TRANSACTION_PASSWORD_BLOCKED_BY_TRIES, user.getUsername(), wrongAttempts, remoteAddress);
                } else {
                    Member m;
                    if (user instanceof MemberUser) {
                        m = ((MemberUser) user).getMember();
                    } else {
                        m = ((OperatorUser) user).getOperator().getMember();
                    }
                    alertService.create(m, MemberAlert.Alerts.TRANSACTION_PASSWORD_BLOCKED_BY_TRIES, wrongAttempts, remoteAddress);
                    memberNotificationHandler.blockedCredentialsNotification(user, Credentials.TRANSACTION_PASSWORD);
                }

                // Block the transaction password and clear previous attempts
                final ResetTransactionPasswordDTO dto = new ResetTransactionPasswordDTO();
                dto.setAllowGeneration(false);
                dto.setUser(user);
                resetTransactionPassword(dto);
                wrongCredentialAttemptsDao.clear(user, Credentials.TRANSACTION_PASSWORD);

                throw new BlockedCredentialsException(Credentials.TRANSACTION_PASSWORD, user);
            }
            throw new InvalidCredentialsException(Credentials.TRANSACTION_PASSWORD, user);
        }

        // Everything ok - remove any previous wrong attempts
        wrongCredentialAttemptsDao.clear(user, Credentials.TRANSACTION_PASSWORD);
        return user;
    }

    /**
     * Returns the session timeout for the given user
     */
    private TimePeriod getSessionTimeout(final User user, final boolean isPosWeb) {
        AccessSettings accessSettings = settingsService.getAccessSettings();
        TimePeriod sessionTimeout;
        if (isPosWeb) {
            sessionTimeout = accessSettings.getPoswebTimeout();
        } else if (user instanceof AdminUser) {
            sessionTimeout = accessSettings.getAdminTimeout();
        } else {
            sessionTimeout = accessSettings.getMemberTimeout();
        }
        return sessionTimeout;
    }

    private boolean isBlocked(final Calendar date) {
        return date != null && date.after(Calendar.getInstance());
    }

    private boolean isChannelEnabledForGroup(final Channel channel, MemberGroup memberGroup) {
        memberGroup = fetchService.fetch(memberGroup, MemberGroup.Relationships.CHANNELS);
        return memberGroup.getChannels().contains(channel);
    }

    private User loadUser(final String member, final String username) {
        if (StringUtils.isEmpty(member)) {
            // Normal user login
            final User user = elementService.loadUser(username, FETCH);
            if (user instanceof OperatorUser) {
                // Cannot login as operator without giving a member username too
                throw new EntityNotFoundException(User.class);
            }
            return user;
        } else {
            // Member's operator login - First load the member
            final User loadedMember = elementService.loadUser(member, FETCH);
            MemberUser memberUser;
            try {
                memberUser = (MemberUser) loadedMember;
            } catch (final ClassCastException e) {
                throw new EntityNotFoundException(MemberUser.class);
            }
            final Member m = memberUser.getMember();
            // Then the operator
            final OperatorUser user = elementService.loadOperatorUser(m, username, FETCH);
            // Assign the already fetched member to the operator
            user.getOperator().setMember(m);
            return user;
        }
    }

    private boolean requestTransactionPassword(User user) {
        user = fetchService.fetch(user, FETCH);
        Group group = user.getElement().getGroup();
        if (group instanceof OperatorGroup) {
            group = fetchService.fetch(group, RelationshipHelper.nested(OperatorGroup.Relationships.MEMBER, Element.Relationships.GROUP));
        }
        final BasicGroupSettings settings = group.getBasicSettings();
        return settings.getTransactionPassword().isUsed();
    }

    private ValidationError resolveValidationError(final boolean loginPassword, final boolean numeric, final Element element, final Object object, final Object property, final String credential) {
        if (StringUtils.isEmpty(credential)) {
            return null;
        }

        final Group group = fetchService.fetch(element.getGroup());
        final BasicGroupSettings settings = group.getBasicSettings();
        RangeConstraint length;
        if (loginPassword) {
            length = settings.getPasswordLength();
        } else {
            final MemberGroup memberGroup = (MemberGroup) group;
            length = memberGroup.getMemberSettings().getPinLength();
        }

        // Validate the password length
        final ValidationError lengthResult = new LengthValidation(length).validate(object, property, credential);
        if (lengthResult != null) {
            return lengthResult;
        }

        final String keyPrefix = loginPassword ? "changePassword.error." : "changePin.error.";

        // Check for characters
        if (numeric && !(group instanceof AdminGroup)) {
            // Must be numeric
            if (!StringUtils.isNumeric(credential)) {
                return new ValidationError(keyPrefix + "mustBeNumeric");
            }
        }

        if (loginPassword && !numeric) {
            // When the virtual keyboard is enabled, make sure that the login password has no special characters
            final AccessSettings accessSettings = settingsService.getAccessSettings();
            if (accessSettings.isVirtualKeyboard() && StringHelper.hasSpecial(credential)) {
                return new ValidationError("changePassword.error.mustContainOnlyLettersOrNumbers");
            }
        }

        // Validate the password policy
        final PasswordPolicy policy = settings.getPasswordPolicy();
        if (policy == null || policy == PasswordPolicy.NONE) {
            // Nothing to enforce
            return null;
        }

        // Check for characters
        if (loginPassword && !numeric) {
            // Keys are hard coded with changePassword.error because pin is always numeric
            switch (policy) {
                case AVOID_OBVIOUS_LETTERS_NUMBERS:
                    // Must include letters and numbers
                    if (!StringHelper.hasDigits(credential) || !StringHelper.hasLetters(credential)) {
                        return new ValidationError("changePassword.error.mustIncludeLettersNumbers");
                    }
                    break;
                case AVOID_OBVIOUS_LETTERS_NUMBERS_SPECIAL:
                    // Must include letters, numbers and special characters
                    if (!StringHelper.hasDigits(credential) || !StringHelper.hasLetters(credential) || !StringHelper.hasSpecial(credential)) {
                        return new ValidationError("changePassword.error.mustIncludeLettersNumbersSpecial");
                    }
                    break;
            }
        }

        // Check for obvious password
        if (isObviousCredential(element, credential)) {
            return new ValidationError(keyPrefix + "obvious");
        }

        return null;
    }

    private void updateSessionExpiration(final Long id, final Calendar newExpiration) {
        transactionHelper.runAsync(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(final TransactionStatus status) {
                sessionDao.updateExpiration(id, newExpiration);
            }
        });
    }

}
