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
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;

import nl.strohalm.cyclos.access.AdminAdminPermission;
import nl.strohalm.cyclos.access.AdminMemberPermission;
import nl.strohalm.cyclos.access.AdminSystemPermission;
import nl.strohalm.cyclos.access.BasicPermission;
import nl.strohalm.cyclos.access.BrokerPermission;
import nl.strohalm.cyclos.access.MemberPermission;
import nl.strohalm.cyclos.entities.access.Channel;
import nl.strohalm.cyclos.entities.access.MemberUser;
import nl.strohalm.cyclos.entities.access.Session;
import nl.strohalm.cyclos.entities.access.SessionQuery;
import nl.strohalm.cyclos.entities.access.User;
import nl.strohalm.cyclos.entities.accounts.cards.Card;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.entities.exceptions.UnexpectedEntityException;
import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.entities.groups.Group.Nature;
import nl.strohalm.cyclos.entities.groups.GroupQuery;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.members.Operator;
import nl.strohalm.cyclos.exceptions.PermissionDeniedException;
import nl.strohalm.cyclos.services.BaseServiceSecurity;
import nl.strohalm.cyclos.services.access.exceptions.BlockedCredentialsException;
import nl.strohalm.cyclos.services.access.exceptions.CredentialsAlreadyUsedException;
import nl.strohalm.cyclos.services.access.exceptions.InactiveMemberException;
import nl.strohalm.cyclos.services.access.exceptions.InvalidCardException;
import nl.strohalm.cyclos.services.access.exceptions.InvalidCredentialsException;
import nl.strohalm.cyclos.services.access.exceptions.NotConnectedException;
import nl.strohalm.cyclos.services.access.exceptions.SessionAlreadyInUseException;
import nl.strohalm.cyclos.services.access.exceptions.UserNotFoundException;
import nl.strohalm.cyclos.services.elements.ResetTransactionPasswordDTO;
import nl.strohalm.cyclos.services.groups.GroupServiceLocal;
import nl.strohalm.cyclos.utils.RelationshipHelper;
import nl.strohalm.cyclos.utils.access.LoggedUser;
import nl.strohalm.cyclos.utils.access.PermissionHelper;
import nl.strohalm.cyclos.utils.validation.ValidationException;

import org.apache.commons.collections.CollectionUtils;

/**
 * Security layer for {@link AccessService}
 * 
 * @author luis
 */
public class AccessServiceSecurity extends BaseServiceSecurity implements AccessService {

    private AccessServiceLocal accessService;
    private GroupServiceLocal  groupService;

    @Override
    public boolean canChangeChannelsAccess(final Member member) {
        // Nothing to check
        return accessService.canChangeChannelsAccess(member);
    }

    @Override
    public Member changeChannelsAccess(Member member, final Collection<Channel> channels, final boolean verifySmsChannel) {
        member = fetchService.fetch(member, Element.Relationships.GROUP);
        PermissionHelper.checkSelection(member.getMemberGroup().getChannels(), channels);
        if (!canChangeChannelsAccess(member)) {
            throw new PermissionDeniedException();
        }
        return accessService.changeChannelsAccess(member, channels, verifySmsChannel);
    }

    @Override
    public User changePassword(final ChangeLoginPasswordDTO params) throws InvalidCredentialsException, BlockedCredentialsException, CredentialsAlreadyUsedException {
        checkChangePassword(params);
        return accessService.changePassword(params);
    }

    @Override
    public MemberUser changePin(final ChangePinDTO params) throws InvalidCredentialsException, BlockedCredentialsException, CredentialsAlreadyUsedException {
        checkChangePin(params);
        return accessService.changePin(params);
    }

    @Override
    public MemberUser checkCredentials(final Channel channel, final MemberUser user, final String credentials, final String remoteAddress, final Member relatedMember) throws InvalidCredentialsException, BlockedCredentialsException, InvalidCardException {
        // We cannot enforce permissions here, as this method is used under different contexts, like web services, webshop receive payments, etc...
        return accessService.checkCredentials(channel, user, credentials, remoteAddress, relatedMember);
    }

    @Override
    public Session checkSession(final String sessionId) throws NotConnectedException {
        // This is invoked even before LoggedUser is initialized. So, there's nothing to enforce...
        return accessService.checkSession(sessionId);
    }

    @Override
    public User checkTransactionPassword(final String transactionPassword) throws InvalidCredentialsException, BlockedCredentialsException {
        // Nothing to check, as this method only affects the logged user
        return accessService.checkTransactionPassword(transactionPassword);
    }

    @Override
    public User disconnect(Session session) throws NotConnectedException {
        try {
            session = fetchService.fetch(session, RelationshipHelper.nested(Session.Relationships.USER, User.Relationships.ELEMENT));
        } catch (EntityNotFoundException e) {
            throw new NotConnectedException();
        }
        Element element = session.getUser().getElement();
        checkDisconnect(element);
        accessService.disconnect(session);
        return session.getUser();
    }

    @Override
    public User disconnect(User user) throws NotConnectedException {
        user = fetchService.fetch(user, User.Relationships.ELEMENT);
        checkDisconnect(user.getElement());
        return accessService.disconnect(user);
    }

    @Override
    public String generateTransactionPassword() throws UnexpectedEntityException {
        // Nothing to check, as this method only affects the logged user
        return accessService.generateTransactionPassword();
    }

    @Override
    public Collection<Channel> getChannelsEnabledForMember(final Member member) {
        permissionService.checkManages(member);

        return accessService.getChannelsEnabledForMember(member);
    }

    @Override
    public User getLoggedUser(final String sessionId) throws NotConnectedException {
        User user = accessService.getLoggedUser(sessionId);
        permissionService.checkManages(user.getElement());
        return user;
    }

    @Override
    public boolean hasPasswordExpired() {
        // Nothing to check, as this method only affects the logged user
        return accessService.hasPasswordExpired();
    }

    @Override
    public boolean isCardSecurityCodeBlocked(Card card) {
        card = fetchService.fetch(card, Card.Relationships.OWNER);
        permissionService.permission(card.getOwner())
                .admin(AdminMemberPermission.CARDS_VIEW)
                .broker(BrokerPermission.CARDS_VIEW)
                .member(MemberPermission.CARDS_VIEW)
                .check();
        return accessService.isCardSecurityCodeBlocked(card);
    }

    @Override
    public boolean isChannelEnabledForMember(final Channel channel, final Member member) {
        // We cannot check management here, as this is invoked, for example, on web services restricted to a member -
        // we just need to know whether a channel is enabled
        permissionService.checkRelatesTo(member);
        return accessService.isChannelEnabledForMember(channel, member);
    }

    @Override
    public boolean isChannelEnabledForMember(final String channelInternalName, final Member member) {
        // We cannot check management here, as this is invoked, for example, on web services restricted to a member -
        // we just need to know whether a channel is enabled
        permissionService.checkRelatesTo(member);
        return accessService.isChannelEnabledForMember(channelInternalName, member);
    }

    @Override
    public boolean isLoggedIn(User user) throws NotConnectedException {
        user = fetchService.fetch(user, User.Relationships.ELEMENT);
        // We cannot just throw PermissionDeniedException here, as this method is used over related (not managed) users.
        // In that case, just assume the user is not logged in
        if (!permissionService.manages(user.getElement())) {
            return false;
        }
        return accessService.isLoggedIn(user);
    }

    @Override
    public boolean isLoginBlocked(User user) {
        user = fetchService.fetch(user, User.Relationships.ELEMENT);
        // We cannot just throw PermissionDeniedException here, as this method is used over related (not managed) users.
        // In that case, just assume the user login is not blocked
        if (!permissionService.manages(user.getElement())) {
            return false;
        }
        return accessService.isLoginBlocked(user);
    }

    @Override
    public boolean isPinBlocked(MemberUser user) {
        user = fetchService.fetch(user, User.Relationships.ELEMENT);
        // We cannot just throw PermissionDeniedException here, as this method is used over related (not managed) users.
        // In that case, just assume the user pin is not blocked
        if (!permissionService.manages(user.getElement())) {
            return false;
        }
        return accessService.isPinBlocked(user);
    }

    @Override
    public User login(User user, final String password, final String channel, final boolean isPosWeb, final String remoteAddress, final String sessionId) throws UserNotFoundException, InvalidCredentialsException, BlockedCredentialsException, SessionAlreadyInUseException {
        user = fetchService.fetch(user, RelationshipHelper.nested(User.Relationships.ELEMENT, Element.Relationships.GROUP));
        permissionService.permission(user.getElement().getGroup())
                .basic(BasicPermission.BASIC_LOGIN)
                .check();
        return accessService.login(user, password, channel, isPosWeb, remoteAddress, sessionId);
    }

    @Override
    public User logout(final String sessionId) {
        // This method is invoked by a session listener, so, probably there's nothing on LoggedUser => nothing to check
        return accessService.logout(sessionId);
    }

    @Override
    public boolean notifyPermissionDeniedException() {
        // Nothing to check, as this method only affects the logged user
        return accessService.notifyPermissionDeniedException();
    }

    @Override
    public User reenableLogin(User user) {
        user = fetchService.fetch(user, RelationshipHelper.nested(User.Relationships.ELEMENT, Element.Relationships.GROUP));
        // Check that the user doesn't belong to a removed group.
        if (user.getElement().getGroup().isRemoved()) {
            throw new PermissionDeniedException();
        }
        permissionService.permission(user.getElement())
                .admin(AdminAdminPermission.ACCESS_ENABLE_LOGIN, AdminMemberPermission.ACCESS_ENABLE_LOGIN)
                .member(MemberPermission.OPERATORS_MANAGE)
                .check();
        return accessService.reenableLogin(user);
    }

    @Override
    public MemberUser resetPassword(MemberUser user) {
        user = fetchService.fetch(user, User.Relationships.ELEMENT);
        permissionService.permission(user.getElement())
                .admin(AdminMemberPermission.ACCESS_RESET_PASSWORD)
                .broker(BrokerPermission.MEMBER_ACCESS_RESET_PASSWORD)
                .check();
        return accessService.resetPassword(user);
    }

    @Override
    public User resetTransactionPassword(final ResetTransactionPasswordDTO dto) {
        User user = fetchService.fetch(dto.getUser(), User.Relationships.ELEMENT);
        dto.setUser(user);
        permissionService.permission(user.getElement())
                .admin(AdminAdminPermission.ACCESS_TRANSACTION_PASSWORD, AdminMemberPermission.ACCESS_TRANSACTION_PASSWORD)
                .broker(BrokerPermission.MEMBER_ACCESS_TRANSACTION_PASSWORD)
                .member(MemberPermission.OPERATORS_MANAGE)
                .check();
        return accessService.resetTransactionPassword(dto);
    }

    @Override
    public List<Session> searchSessions(final SessionQuery query) {
        if (LoggedUser.isAdministrator()) {
            Collection<Nature> natures = query.getNatures();
            if (CollectionUtils.isEmpty(natures)) {
                // As usual, empty means all. We want to ensure one-by-one, so we add them here
                natures = EnumSet.allOf(Nature.class);
            }
            if (!permissionService.hasPermission(AdminSystemPermission.STATUS_VIEW_CONNECTED_ADMINS)) {
                natures.remove(Nature.ADMIN);
            }
            if (!permissionService.hasPermission(AdminSystemPermission.STATUS_VIEW_CONNECTED_MEMBERS)) {
                natures.remove(Nature.MEMBER);
            }
            if (!permissionService.hasPermission(AdminSystemPermission.STATUS_VIEW_CONNECTED_BROKERS)) {
                natures.remove(Nature.BROKER);
            }
            if (!permissionService.hasPermission(AdminSystemPermission.STATUS_VIEW_CONNECTED_OPERATORS)) {
                natures.remove(Nature.OPERATOR);
            }
            if (natures.isEmpty()) {
                // Nothing left to see
                throw new PermissionDeniedException();
            }
            // Apply the allowed groups
            Collection<Group> allowedGroups = new HashSet<Group>();
            allowedGroups.addAll(permissionService.getVisibleMemberGroups());
            if (natures.contains(Nature.ADMIN)) {
                // Add all admin groups, as they are not present on the permissionService.getVisibleMemberGroups()
                GroupQuery admins = new GroupQuery();
                admins.setNatures(Group.Nature.ADMIN);
                allowedGroups.addAll(groupService.search(admins));
            }
            if (natures.contains(Nature.OPERATOR)) {
                // Add all operator groups, as they are not present on the permissionService.getVisibleMemberGroups()
                GroupQuery operators = new GroupQuery();
                operators.setIgnoreManagedBy(true);
                operators.setNatures(Group.Nature.OPERATOR);
                allowedGroups.addAll(groupService.search(operators));
            }
            query.setGroups(PermissionHelper.checkSelection(allowedGroups, query.getGroups()));
        } else {
            // Members can only view connected operators
            permissionService.permission(query.getMember()).member(MemberPermission.OPERATORS_MANAGE).check();
        }
        return accessService.searchSessions(query);
    }

    public void setAccessServiceLocal(final AccessServiceLocal accessService) {
        this.accessService = accessService;
    }

    public void setGroupServiceLocal(final GroupServiceLocal groupService) {
        this.groupService = groupService;
    }

    @Override
    public MemberUser unblockPin(MemberUser user) {
        user = fetchService.fetch(user, User.Relationships.ELEMENT);
        permissionService.permission(user.getElement())
                .admin(AdminMemberPermission.ACCESS_UNBLOCK_PIN)
                .broker(BrokerPermission.MEMBER_ACCESS_CHANGE_PIN)
                .member(MemberPermission.ACCESS_UNBLOCK_PIN)
                .check();
        return accessService.unblockPin(user);
    }

    @Override
    public void validateChangePassword(final ChangeLoginPasswordDTO params) throws ValidationException {
        checkChangePassword(params);
        accessService.validateChangePassword(params);
    }

    @Override
    public void validateChangePin(final ChangePinDTO params) throws ValidationException {
        checkChangePin(params);
        accessService.validateChangePin(params);
    }

    @Override
    public User verifyLogin(final String member, final String username, final String remoteAddress) throws UserNotFoundException, InactiveMemberException, PermissionDeniedException {
        // This method is invoked before logging in, so we cannot check anything here
        return accessService.verifyLogin(member, username, remoteAddress);
    }

    private void checkChangePassword(final ChangeLoginPasswordDTO params) {
        User user = fetchService.fetch(params.getUser(), RelationshipHelper.nested(User.Relationships.ELEMENT, Element.Relationships.GROUP));

        params.setUser(user);
        if (LoggedUser.user().equals(user)) {
            // No permission to check - an user can always change his own password
            return;
        }
        // Can't change password of a removed element.
        if (user.getElement().getGroup().isRemoved()) {
            throw new PermissionDeniedException();
        }

        permissionService.permission(user.getElement())
                .admin(AdminAdminPermission.ACCESS_CHANGE_PASSWORD, AdminMemberPermission.ACCESS_CHANGE_PASSWORD)
                .broker(BrokerPermission.MEMBER_ACCESS_CHANGE_PASSWORD)
                .member(MemberPermission.OPERATORS_MANAGE)
                .check();
    }

    private void checkChangePin(final ChangePinDTO params) {
        MemberUser user = fetchService.fetch(params.getUser(), User.Relationships.ELEMENT);
        params.setUser(user);
        permissionService.permission(user.getElement())
                .admin(AdminMemberPermission.ACCESS_CHANGE_PIN)
                .broker(BrokerPermission.MEMBER_ACCESS_CHANGE_PIN)
                .member()
                .check();
    }

    private void checkDisconnect(final Element element) {
        if (LoggedUser.isAdministrator() && element instanceof Operator) {
            // Special case: admins can disconnect logged operators (even without managing them), as long as the member can be disconnected
            Operator operator = (Operator) element;
            permissionService.permission(operator.getMember())
                    .admin(AdminMemberPermission.ACCESS_DISCONNECT_OPERATOR)
                    .check();
        } else {
            permissionService.permission(element)
                    .admin(AdminAdminPermission.ACCESS_DISCONNECT, AdminMemberPermission.ACCESS_DISCONNECT)
                    .member(MemberPermission.OPERATORS_MANAGE)
                    .check();
        }
    }
}
