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

import java.util.Collection;
import java.util.List;

import nl.strohalm.cyclos.entities.access.Channel;
import nl.strohalm.cyclos.entities.access.MemberUser;
import nl.strohalm.cyclos.entities.access.Session;
import nl.strohalm.cyclos.entities.access.SessionQuery;
import nl.strohalm.cyclos.entities.access.User;
import nl.strohalm.cyclos.entities.accounts.cards.Card;
import nl.strohalm.cyclos.entities.exceptions.UnexpectedEntityException;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.exceptions.PermissionDeniedException;
import nl.strohalm.cyclos.services.Service;
import nl.strohalm.cyclos.services.access.exceptions.AlreadyConnectedException;
import nl.strohalm.cyclos.services.access.exceptions.BlockedCredentialsException;
import nl.strohalm.cyclos.services.access.exceptions.CredentialsAlreadyUsedException;
import nl.strohalm.cyclos.services.access.exceptions.InactiveMemberException;
import nl.strohalm.cyclos.services.access.exceptions.InvalidCardException;
import nl.strohalm.cyclos.services.access.exceptions.InvalidCredentialsException;
import nl.strohalm.cyclos.services.access.exceptions.NotConnectedException;
import nl.strohalm.cyclos.services.access.exceptions.SessionAlreadyInUseException;
import nl.strohalm.cyclos.services.access.exceptions.UserNotFoundException;
import nl.strohalm.cyclos.services.elements.ResetTransactionPasswordDTO;
import nl.strohalm.cyclos.utils.validation.ValidationException;

/**
 * Service interface for login/logout and related operations, as well as managing credentials
 * @author luis
 */
public interface AccessService extends Service {

    /**
     * Returns whether the logged user can change channels access to the given member
     */
    boolean canChangeChannelsAccess(Member member);

    /**
     * Change the channels that the given member have access to
     */
    Member changeChannelsAccess(final Member member, final Collection<Channel> channels, boolean verifySmsChannel);

    /**
     * Changes the password of the given user
     */
    User changePassword(ChangeLoginPasswordDTO params) throws InvalidCredentialsException, BlockedCredentialsException, CredentialsAlreadyUsedException;

    /**
     * Changes the pin of the given member user
     * @throws InvalidCredentialsException When the given pin is invalid
     * @throws BlockedCredentialsException When the transaction password was invalid a few times, it becomes blocked
     * @throws CredentialsAlreadyUsedException When the given new password was already used for that user in past
     */
    MemberUser changePin(ChangePinDTO params) throws InvalidCredentialsException, BlockedCredentialsException, CredentialsAlreadyUsedException;

    /**
     * Checks for credentials for the given member, in the given channel
     */
    MemberUser checkCredentials(Channel channel, MemberUser user, String credentials, String remoteAddress, Member relatedMember) throws InvalidCredentialsException, BlockedCredentialsException, InvalidCardException;;

    /**
     * Loads a session by session id, ensuring it is valid
     * @throws NotConnectedException When there is no such session
     */
    Session checkSession(String sessionId) throws NotConnectedException;

    /**
     * Checks whether the given transaction password is valid or not for the logged user. When the number of wrong tries is greater that the element's
     * group basicSettings.maxTransactionPasswordWrongTries, the transaction password must be blocked and a member alert must be generated
     * @throws InvalidCredentialsException Wrong transaction password
     * @throws BlockedCredentialsException The user has missed the password too many times
     */
    User checkTransactionPassword(String transactionPassword) throws InvalidCredentialsException, BlockedCredentialsException;

    /**
     * Disconnects a logged user session
     * @throws NotConnectedException When there's no such session
     */
    User disconnect(final Session session) throws NotConnectedException;

    /**
     * Disconnects all sessions of the given user
     */
    User disconnect(User user) throws NotConnectedException;

    /**
     * Generates a transaction password for the logged user
     * @throws UnexpectedEntityException When the logged user's group doesn't uses transaction password
     * @return The plain generated transaction password
     */
    String generateTransactionPassword() throws UnexpectedEntityException;

    /**
     * THIS METHOD MUST BE USED INSTEAD OF member.getChannels()
     * @return the channels enabled for the specified member taking into account the group's channels.
     */
    Collection<Channel> getChannelsEnabledForMember(Member member);

    /**
     * Returns the user logged in the given session
     * @param sessionId Id of the session
     * @return The user logged in
     * @throws NotConnectedException No user logged under this session id
     */
    User getLoggedUser(String sessionId) throws NotConnectedException;

    /**
     * Check if the login password for the logged user has expired
     */
    boolean hasPasswordExpired();

    /**
     * Returns whether the security code for the given card is blocked
     */
    boolean isCardSecurityCodeBlocked(Card card);

    /**
     * Check if the channel is enabled for the member
     */
    boolean isChannelEnabledForMember(Channel channel, Member member);

    /**
     * Check if the channel is enabled for the member
     */
    boolean isChannelEnabledForMember(String channelInternalName, Member member);

    /**
     * Returns the session id for the given user if it is logged
     * @param user The user
     * @return The session id if the user logged in, null otherwise
     */
    boolean isLoggedIn(User user);

    /**
     * Checks if the given user's login is blocked. The user's login could be blocked because he missed several times the password or he got too many
     * access denied exceptions.
     */
    boolean isLoginBlocked(User user);

    /**
     * Checks whether the given member's pin is blocked for exceeding wrong tries
     */
    boolean isPinBlocked(MemberUser user);

    /**
     * Logs the specified user in the system
     * @param user The user to login
     * @param password The MD5 hashed password
     * @param remoteAddress The IP address for the requesting login
     * @param sessionId The session id for the user
     * @return the user logged in
     * @throws UserNotFoundException Invalid username
     * @throws InvalidCredentialsException Invalid password
     * @throws BlockedCredentialsException When the user is blocked by reaching the max login tries
     * @throws SessionAlreadyInUseException The given session id is already in use
     * @throws AlreadyConnectedException The user is already logged in, and the access settings prevents multiple connections
     */
    User login(User user, String password, String channel, boolean isPosWeb, String remoteAddress, String sessionId) throws UserNotFoundException, InvalidCredentialsException, BlockedCredentialsException, SessionAlreadyInUseException, AlreadyConnectedException;

    /**
     * Logs out of the system the user corresponding to the given <br>
     * session id and removes it from the logged users list
     * @param sessionId Id of the session to be logged out
     */
    User logout(String sessionId);

    /**
     * Notifies the system of a permission denied exception. The system keeps track of how many have arisen and blocks the user if it detects that
     * there have been too many since the last login.
     * @return true if the user's login has been blocked.
     */
    boolean notifyPermissionDeniedException();

    /**
     * Immediately re-enables an user to login after wrong password tries
     */
    User reenableLogin(User user);

    /**
     * Reset an user's password, sending it by mail
     */
    MemberUser resetPassword(MemberUser user);

    /**
     * Resets the transaction password of the specified user
     */
    User resetTransactionPassword(ResetTransactionPasswordDTO dto);

    /**
     * Searches for connected user sessions
     */
    List<Session> searchSessions(SessionQuery query);

    /**
     * Unblocks the given member's pin
     */
    MemberUser unblockPin(MemberUser user);

    /**
     * Validates the password change
     */
    void validateChangePassword(ChangeLoginPasswordDTO params) throws ValidationException;

    /**
     * Validates the parameters of a pin change
     */
    void validateChangePin(ChangePinDTO params) throws ValidationException;

    /**
     * Checks if the login can be performed for the given username and address
     * @throws UserNotFoundException When the given user does not exist
     * @throws InactiveMemberException When the member has no active password
     * @throws PermissionDeniedException When the member does not have permission to login
     */
    User verifyLogin(String member, String username, String remoteAddress) throws UserNotFoundException, InactiveMemberException, PermissionDeniedException;
}
