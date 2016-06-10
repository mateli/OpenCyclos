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

import nl.strohalm.cyclos.entities.access.Channel;
import nl.strohalm.cyclos.entities.access.MemberUser;
import nl.strohalm.cyclos.entities.access.User;
import nl.strohalm.cyclos.entities.accounts.cards.Card;
import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.services.access.exceptions.BlockedCredentialsException;
import nl.strohalm.cyclos.services.access.exceptions.CredentialsAlreadyUsedException;
import nl.strohalm.cyclos.services.access.exceptions.InvalidCredentialsException;
import nl.strohalm.cyclos.services.access.exceptions.UserNotFoundException;
import nl.strohalm.cyclos.utils.validation.Validator.Property;

/**
 * Local interface. It must be used only from other services.
 */
public interface AccessServiceLocal extends AccessService {

    /**
     * Adds the login password validation to the given property
     */
    void addLoginPasswordValidation(Element element, Property property);

    /**
     * Appends a validation for pin on the given property
     */
    void addPinValidation(Member member, Property pin);

    /**
     * Changes a member credentials according to the current web service channel. Must be invoked by web services.
     */
    void changeCredentials(final MemberUser user, final String newCredentials) throws CredentialsAlreadyUsedException;

    /**
     * Checks the password for the given user
     * @throws UserNotFoundException Invalid username
     * @throws InvalidCredentialsException Invalid password
     * @throws BlockedCredentialsException The user is blocked by exceding wrong login attempts
     */
    User checkPassword(String member, String username, String password, String remoteAddress) throws UserNotFoundException, InvalidCredentialsException, BlockedCredentialsException;

    /**
     * Disconnects all users, except the one currently logged-in
     */
    void disconnectAllButLogged();

    /**
     * Generates a new password, according to the given group settings
     */
    String generatePassword(Group group);

    /**
     * @return true if the channeld could be enabled (assigned) to the member.
     */
    boolean isChannelAllowedToBeEnabledForMember(final Channel channel, Member member);

    /**
     * Returns whether the given credential is obvious
     */
    boolean isObviousCredential(final Element element, final String credential);

    /**
     * Purges expired sessions
     */
    void purgeExpiredSessions();

    /**
     * Purges old traces for invalid credential attempts, permission denied's and expired sessions
     */
    void purgeTraces(Calendar time);

    /**
     * Unblocks the security code of given card
     */
    Card unblockCardSecurityCode(BigInteger cardNumber);

    /**
     * Returns the time limit wrong credential attempts should be considered
     */
    Calendar wrongAttemptsLimit();

}
