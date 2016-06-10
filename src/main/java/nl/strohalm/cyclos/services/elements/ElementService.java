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

import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.access.Channel;
import nl.strohalm.cyclos.entities.access.PrincipalType;
import nl.strohalm.cyclos.entities.access.User;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.entities.exceptions.UnexpectedEntityException;
import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.ElementQuery;
import nl.strohalm.cyclos.entities.members.FullTextElementQuery;
import nl.strohalm.cyclos.entities.members.FullTextMemberQuery;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.members.MemberQuery;
import nl.strohalm.cyclos.entities.members.PendingEmailChange;
import nl.strohalm.cyclos.entities.members.PendingMember;
import nl.strohalm.cyclos.entities.members.PendingMemberQuery;
import nl.strohalm.cyclos.exceptions.MailSendingException;
import nl.strohalm.cyclos.services.Service;
import nl.strohalm.cyclos.services.elements.exceptions.MemberHasBalanceException;
import nl.strohalm.cyclos.services.elements.exceptions.MemberHasOpenInvoicesException;
import nl.strohalm.cyclos.services.elements.exceptions.NoInitialGroupException;
import nl.strohalm.cyclos.services.elements.exceptions.RegistrationAgreementNotAcceptedException;
import nl.strohalm.cyclos.utils.ElementVO;
import nl.strohalm.cyclos.utils.validation.ValidationException;

/**
 * Service interface for elements. Elements are used to control global member related operations. Members and Administrators descend from the same
 * Element entity.
 * @author rafael
 */
public interface ElementService extends Service {

    /**
     * Used for a member to accept to accept the current registration agreement of his group
     */
    void acceptAgreement(String remoteAddress);

    /**
     * Changes the accessible channels of all members returned by the query. For members that can not access an enabled nothing is done.
     */
    BulkMemberActionResultVO bulkChangeMemberChannels(final FullTextMemberQuery query, Collection<Channel> enableChannels, Collection<Channel> disableChannels) throws ValidationException;

    /**
     * Changes the group of all members returned by the query. For members that are already on the selected group, nothing is done.
     */
    BulkMemberActionResultVO bulkChangeMemberGroup(FullTextMemberQuery query, MemberGroup newGroup, String comments) throws ValidationException;

    /**
     * Changes the group of the specified user. If the new group has status of removed:
     * <ul>
     * <li>Only let the group be changed if there are no accounts with balance and no open invoices</li>
     * <li>All contacts must be removed</li>
     * <li>If the member is a broker, all brokered members should have their broker changed to null, generating a remark with
     * messageSettings.brokerRemovedMessage as comments</li>
     * </ul>
     * @throws MemberHasBalanceException When the new group is removed and the member has balance on any of his accounts
     * @throws MemberHasOpenInvoicesException When the new group is removed and the member has open incoming invoices
     * @throws ValidationException When any of the arguments are null or empty, when the new group is the same as the old or when the member is
     * removed
     */
    <E extends Element> E changeGroup(E element, Group newGroup, String comments) throws MemberHasBalanceException, MemberHasOpenInvoicesException, ValidationException;

    /**
     * Used to save the profile of an element
     */
    <E extends Element> E changeProfile(E element);

    /**
     * Confirms a pending e-mail change for the logged user under the given key. If none, throws {@link EntityNotFoundException}
     */
    PendingEmailChange confirmEmailChange(String key) throws EntityNotFoundException;

    /**
     * Search the existing elements using a full text search, based on the given query parameters
     * @return a list of elements
     */
    List<? extends Element> fullTextSearch(FullTextElementQuery query);

    /**
     * Returns a VO with the element information for the given element id.
     */
    ElementVO getElementVO(long id);

    /**
     * Returns the date of the first member activation of the system
     */
    Calendar getFirstMemberActivationDate();

    /**
     * Returns the current {@link PendingEmailChange}, if any
     */
    PendingEmailChange getPendingEmailChange(Member member);

    /**
     * Lists the possible new groups for the given element, ordering by nature (admin / member then broker / operator) and name. If the group is
     * removed, it can't be changed, so, the list will contain only it. Otherwise, returns all other groups, according to the group nature.
     */
    List<? extends Group> getPossibleNewGroups(Element element);

    /**
     * Sends an invitational e-mail to some person.
     */
    void invitePerson(String email);

    /**
     * Loads the element, fetching the specified relationships
     * @throws EntityNotFoundException When the given id does not exist
     */
    <T extends Element> T load(Long id, Relationship... fetch) throws EntityNotFoundException;

    /**
     * Loads a member based on the given principal
     */
    Member loadByPrincipal(PrincipalType principalType, String principal, Relationship... fetch) throws EntityNotFoundException;

    /**
     * Loads a PendingMember by id
     */
    PendingMember loadPendingMember(Long id, Relationship... fetch);

    /**
     * Loads a PendingMember given the activation key
     */
    PendingMember loadPendingMemberByKey(String key, Relationship... fetch);

    /**
     * Loads the user, fetching the specified relationships
     * @throws EntityNotFoundException When the given id does not exist
     */
    <T extends User> T loadUser(Long id, Relationship... fetch) throws EntityNotFoundException;

    /**
     * Loads the user by username, fetching the specified relationships May only return users of type AdminUser or MemberUser. Operators have the
     * {@link #loadOperatorUser(Member, String, Relationship[])}
     * @throws EntityNotFoundException When the given username does not exist
     */
    <T extends User> T loadUser(String username, Relationship... fetch) throws EntityNotFoundException;

    /**
     * Confirms the validation key
     * @throws EntityNotFoundException When there is no pending validation for the given key
     * @throws RegistrationAgreementNotAcceptedException When the user should accept an agreement before validating the registration
     */
    Member publicValidateRegistration(String key) throws EntityNotFoundException, RegistrationAgreementNotAcceptedException;

    /**
     * Registers a new user. The result type is Object because when registering a member, if the e-mail validation is enabled, the result is a
     * {@link PendingMember} instead.
     * @throws NoInitialGroupException When there aren't possible groups to be used as initial
     */
    Object register(Element element, boolean forceChangePassword, String remoteAddress);

    /**
     * Reloads the user from the Database
     */
    <T extends User> T reloadUser(Long id, Relationship... fetch) throws EntityNotFoundException;

    /**
     * Removes the specified user. Unless the user had no activity on the system, this operation won't be permitted. In that case, the user should be
     * moved into a removed group.
     * @throws UnexpectedEntityException When the given id is for a member, and he/she is currently active (has open loans / invoices or non-zero
     * accounts, for example)
     */
    void remove(Long id) throws UnexpectedEntityException;

    /**
     * Removes the given pending members
     */
    int removePendingMembers(Long... ids);

    /**
     * Re-sends the activation e-mail
     */
    PendingMember resendEmail(PendingMember pendingMember) throws MailSendingException;

    /**
     * Re-sends the pending e-mail change e-mail for the member with the given id
     */
    PendingEmailChange resendEmailChange(Long memberId) throws MailSendingException;

    /**
     * Search the existing elements based on the ElementQuery object
     * @return a list of elements
     */
    List<? extends Element> search(ElementQuery query);

    /**
     * Searches for pending members
     */
    List<PendingMember> search(PendingMemberQuery params);

    /**
     * Search the existing elements based on the ElementQuery object and a date
     * @return a list of elements
     */
    List<? extends Element> searchAtDate(MemberQuery query, Calendar date);

    /**
     * Updates the PendingMember, setting the given agreement as agreed
     */
    void setRegistrationAgreementAgreed(PendingMember pendingMember);

    /**
     * Returns if there is a pending agreement to be accepted
     */
    boolean shallAcceptAgreement(Member member);

    /**
     * Updates the pending member
     */
    PendingMember update(PendingMember pendingMember);

    /**
     * Validates the specified element
     */
    void validate(Element element, WhenSaving when, boolean manualPassword) throws ValidationException;

    /**
     * Validates the given PendingMember
     */
    void validate(PendingMember pendingMember) throws ValidationException;

    /**
     * Validates the channels selection for bulk action "Enable/disable channels"
     */
    void validateBulkChangeChannels(FullTextMemberQuery query, Collection<Channel> enableChannels, Collection<Channel> disableChannels);
}
