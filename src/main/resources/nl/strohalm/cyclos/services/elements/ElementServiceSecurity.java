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
import java.util.Collections;
import java.util.List;

import nl.strohalm.cyclos.access.AdminAdminPermission;
import nl.strohalm.cyclos.access.AdminMemberPermission;
import nl.strohalm.cyclos.access.AdminSystemPermission;
import nl.strohalm.cyclos.access.BasicPermission;
import nl.strohalm.cyclos.access.BrokerPermission;
import nl.strohalm.cyclos.access.MemberPermission;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.access.Channel;
import nl.strohalm.cyclos.entities.access.PrincipalType;
import nl.strohalm.cyclos.entities.access.User;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.entities.exceptions.UnexpectedEntityException;
import nl.strohalm.cyclos.entities.groups.BrokerGroup;
import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.entities.groups.Group.Nature;
import nl.strohalm.cyclos.entities.groups.GroupQuery;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.members.AdminQuery;
import nl.strohalm.cyclos.entities.members.Administrator;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.ElementQuery;
import nl.strohalm.cyclos.entities.members.FullTextElementQuery;
import nl.strohalm.cyclos.entities.members.FullTextMemberQuery;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.members.MemberQuery;
import nl.strohalm.cyclos.entities.members.OperatorQuery;
import nl.strohalm.cyclos.entities.members.PendingEmailChange;
import nl.strohalm.cyclos.entities.members.PendingMember;
import nl.strohalm.cyclos.entities.members.PendingMemberQuery;
import nl.strohalm.cyclos.exceptions.MailSendingException;
import nl.strohalm.cyclos.exceptions.PermissionDeniedException;
import nl.strohalm.cyclos.services.BaseServiceSecurity;
import nl.strohalm.cyclos.services.access.ChannelServiceLocal;
import nl.strohalm.cyclos.services.elements.exceptions.MemberHasBalanceException;
import nl.strohalm.cyclos.services.elements.exceptions.MemberHasOpenInvoicesException;
import nl.strohalm.cyclos.services.elements.exceptions.RegistrationAgreementNotAcceptedException;
import nl.strohalm.cyclos.services.groups.GroupServiceLocal;
import nl.strohalm.cyclos.utils.ElementVO;
import nl.strohalm.cyclos.utils.EntityHelper;
import nl.strohalm.cyclos.utils.access.LoggedUser;
import nl.strohalm.cyclos.utils.access.PermissionHelper;
import nl.strohalm.cyclos.utils.validation.ValidationException;

/**
 * Security implementation for {@link ElementService}
 * 
 * @author Rinke
 */
public class ElementServiceSecurity extends BaseServiceSecurity implements ElementService {

    private ElementServiceLocal elementService;
    private GroupServiceLocal   groupService;
    private ChannelServiceLocal channelService;

    @Override
    public void acceptAgreement(final String remoteAddress) {
        // Only members can accept an agreement. There's really not much to check
        if (!LoggedUser.isMember()) {
            throw new PermissionDeniedException();
        }
        elementService.acceptAgreement(remoteAddress);
    }

    @Override
    public BulkMemberActionResultVO bulkChangeMemberChannels(final FullTextMemberQuery query, final Collection<Channel> enableChannels, final Collection<Channel> disableChannels) throws ValidationException {
        if (!elementService.applyQueryRestrictions(query)) {
            throw new PermissionDeniedException();
        }
        permissionService.permission().admin(AdminMemberPermission.BULK_ACTIONS_CHANGE_CHANNELS).check();

        // The "web" channel can not be customized by any user
        final Channel webChannel = channelService.loadByInternalName(Channel.WEB);
        if (enableChannels.contains(webChannel) || disableChannels.contains(webChannel)) {
            throw new PermissionDeniedException();
        }

        return elementService.bulkChangeMemberChannels(query, enableChannels, disableChannels);
    }

    @Override
    public BulkMemberActionResultVO bulkChangeMemberGroup(final FullTextMemberQuery query, final MemberGroup newGroup, final String comments) throws ValidationException {
        if (!elementService.applyQueryRestrictions(query)) {
            throw new PermissionDeniedException();
        }
        permissionService.permission().admin(AdminMemberPermission.BULK_ACTIONS_CHANGE_GROUP).check();
        permissionService.checkManages(newGroup);
        return elementService.bulkChangeMemberGroup(query, newGroup, comments);
    }

    @Override
    public <E extends Element> E changeGroup(final E element, final Group newGroup, final String comments) throws MemberHasBalanceException, MemberHasOpenInvoicesException, ValidationException {
        checkChangeGroup(element);
        // No matter what, an admin cannot change his own group. Also, the new group must be visible
        if (LoggedUser.element().equals(element) || !permissionService.getAllVisibleGroups().contains(newGroup)) {
            throw new PermissionDeniedException();
        }
        return elementService.changeGroup(element, newGroup, comments);
    }

    @Override
    public <E extends Element> E changeProfile(final E element) {
        // An user can always change his own profile. Otherwise, needs permissions
        if (!LoggedUser.element().equals(element)) {
            permissionService.permission(element)
                    .admin(AdminAdminPermission.ADMINS_CHANGE_PROFILE, AdminMemberPermission.MEMBERS_CHANGE_PROFILE)
                    .broker(BrokerPermission.MEMBERS_CHANGE_PROFILE)
                    .member(MemberPermission.OPERATORS_MANAGE)
                    .check();
        }
        return elementService.changeProfile(element);
    }

    @Override
    public PendingEmailChange confirmEmailChange(final String key) throws EntityNotFoundException {
        // The impl already checks that the given key belongs to the logged user, or throws ENFE. Here, we just validate the logged user is a member
        if (!LoggedUser.isMember()) {
            throw new PermissionDeniedException();
        }
        return elementService.confirmEmailChange(key);
    }

    @Override
    public List<? extends Element> fullTextSearch(final FullTextElementQuery query) {
        if (!elementService.applyQueryRestrictions(query)) {
            throw new PermissionDeniedException();
        }
        return elementService.fullTextSearch(query);
    }

    @Override
    public ElementVO getElementVO(final long id) {
        // Nothing to check.
        return elementService.getElementVO(id);
    }

    @Override
    public Calendar getFirstMemberActivationDate() {
        // Used only on statistics
        permissionService.permission().admin(AdminSystemPermission.REPORTS_STATISTICS).check();
        return elementService.getFirstMemberActivationDate();
    }

    @Override
    public PendingEmailChange getPendingEmailChange(final Member member) {
        permissionService.checkManages(member);
        return elementService.getPendingEmailChange(member);
    }

    @Override
    public List<? extends Group> getPossibleNewGroups(final Element element) {
        checkChangeGroup(element);
        return elementService.getPossibleNewGroups(element);
    }

    @Override
    public void invitePerson(final String email) {
        permissionService.permission().basic(BasicPermission.BASIC_INVITE_MEMBER).check();
        elementService.invitePerson(email);
    }

    @Override
    public <T extends Element> T load(final Long id, final Relationship... fetch) throws EntityNotFoundException {
        T element = elementService.<T> load(id, fetch);
        permissionService.checkRelatesTo(element);
        return element;
    }

    @Override
    public Member loadByPrincipal(final PrincipalType principalType, final String principal, final Relationship... fetch) throws EntityNotFoundException {
        // This method is used, for example, to load users about to login. So, we cannot enforce anything here...
        return elementService.loadByPrincipal(principalType, principal, fetch);
    }

    @Override
    public PendingMember loadPendingMember(final Long id, final Relationship... fetch) {
        PendingMember pendingMember = elementService.loadPendingMember(id, fetch);
        checkManagePending(pendingMember);
        return pendingMember;
    }

    @Override
    public PendingMember loadPendingMemberByKey(final String key, final Relationship... fetch) {
        // This method is invoked by guests, exactly to validate the registration. Nothing to check
        return elementService.loadPendingMemberByKey(key, fetch);
    }

    @Override
    public <T extends User> T loadUser(final Long id, final Relationship... fetch) throws EntityNotFoundException {
        T user = elementService.<T> loadUser(id, fetch);
        permissionService.checkRelatesTo(user.getElement());
        return user;
    }

    @Override
    public <T extends User> T loadUser(final String username, final Relationship... fetch) throws EntityNotFoundException {
        T user = elementService.<T> loadUser(username, fetch);
        permissionService.checkRelatesTo(user.getElement());
        return user;
    }

    @Override
    public Member publicValidateRegistration(final String key) throws EntityNotFoundException, RegistrationAgreementNotAcceptedException {
        // Nothing to check on public e-mail validation
        return elementService.publicValidateRegistration(key);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object register(final Element element, final boolean forceChangePassword, final String remoteAddress) {
        // We need the group in order to check anything
        if (element.getGroup() == null) {
            throw new ValidationException();
        }

        if (LoggedUser.hasUser()) {
            // When no logged user, we don't need to check permission here - it will only validate the given initial group
            permissionService.checkManages(element.getGroup());
            Nature groupNature = element.getGroup().getNature();
            permissionService.permission()
                    .admin(groupNature == Group.Nature.MEMBER || groupNature == Group.Nature.BROKER ? AdminMemberPermission.MEMBERS_REGISTER : AdminAdminPermission.ADMINS_REGISTER)
                    .broker(BrokerPermission.MEMBERS_REGISTER)
                    .member(MemberPermission.OPERATORS_MANAGE)
                    .check();
        }
        // Check the initial group
        PermissionHelper.checkContains((Collection<Group>) getPossibleInitialGroups(element), element.getGroup());

        return elementService.register(element, forceChangePassword, remoteAddress);
    }

    @Override
    public <T extends User> T reloadUser(final Long id, final Relationship... fetch) throws EntityNotFoundException {
        T user = elementService.<T> reloadUser(id, fetch);
        permissionService.checkRelatesTo(user.getElement());
        return user;
    }

    @Override
    public void remove(final Long id) throws UnexpectedEntityException {
        Element element = elementService.load(id);
        permissionService.permission(element)
                .admin(AdminMemberPermission.MEMBERS_REMOVE, AdminAdminPermission.ADMINS_REMOVE)
                .member(MemberPermission.OPERATORS_MANAGE)
                .check();
        elementService.remove(id);
    }

    @Override
    public int removePendingMembers(final Long... ids) {
        // We can reuse the same checkManagePending() method as there is a single permission for pending members
        for (Long id : ids) {
            PendingMember pendingMember = elementService.loadPendingMember(id);
            checkManagePending(pendingMember);
        }
        return elementService.removePendingMembers(ids);
    }

    @Override
    public PendingMember resendEmail(PendingMember pendingMember) throws MailSendingException {
        // We can reuse the same checkManagePending() method as there is a single permission for pending members
        pendingMember = fetchService.fetch(pendingMember);
        checkManagePending(pendingMember);
        return elementService.resendEmail(pendingMember);
    }

    @Override
    public PendingEmailChange resendEmailChange(final Long memberId) throws MailSendingException {
        Member member = fetchService.fetch(EntityHelper.reference(Member.class, memberId));
        permissionService.permission(member)
                .admin(AdminMemberPermission.MEMBERS_CHANGE_EMAIL)
                .broker(BrokerPermission.MEMBERS_CHANGE_EMAIL)
                .member(MemberPermission.PROFILE_CHANGE_EMAIL)
                .check();
        return elementService.resendEmailChange(memberId);
    }

    @Override
    public List<? extends Element> search(final ElementQuery query) {
        if (!applyRestrictions(query)) {
            throw new PermissionDeniedException();
        }
        return elementService.search(query);
    }

    @Override
    public List<PendingMember> search(final PendingMemberQuery params) {
        permissionService.permission()
                .admin(AdminMemberPermission.MEMBERS_MANAGE_PENDING)
                .broker(BrokerPermission.MEMBERS_MANAGE_PENDING)
                .check();
        if (LoggedUser.isBroker()) {
            params.setBroker(LoggedUser.<Member> element());
        } else { // an admin
            params.setGroups(PermissionHelper.checkSelection(permissionService.getManagedMemberGroups(), params.getGroups()));
        }
        return elementService.search(params);
    }

    @Override
    public List<? extends Element> searchAtDate(final MemberQuery query, final Calendar date) {
        if (!applyRestrictions(query)) {
            throw new PermissionDeniedException();
        }
        return elementService.searchAtDate(query, date);
    }

    public void setChannelServiceLocal(final ChannelServiceLocal channelService) {
        this.channelService = channelService;
    }

    public void setElementServiceLocal(final ElementServiceLocal elementService) {
        this.elementService = elementService;
    }

    public void setGroupServiceLocal(final GroupServiceLocal groupService) {
        this.groupService = groupService;
    }

    @Override
    public void setRegistrationAgreementAgreed(final PendingMember pendingMember) {
        // This method is invoked before actually having a Member, so, nothing to check
        elementService.setRegistrationAgreementAgreed(pendingMember);
    }

    @Override
    public boolean shallAcceptAgreement(final Member member) {
        permissionService.checkManages(member);
        return elementService.shallAcceptAgreement(member);
    }

    @Override
    public PendingMember update(final PendingMember pendingMember) {
        checkManagePending(pendingMember);
        return elementService.update(pendingMember);
    }

    @Override
    public void validate(final Element element, final WhenSaving when, final boolean manualPassword) throws ValidationException {
        // No permission check needed for validation
        elementService.validate(element, when, manualPassword);
    }

    @Override
    public void validate(final PendingMember pendingMember) throws ValidationException {
        // No permission check needed for validation
        elementService.validate(pendingMember);
    }

    @Override
    public void validateBulkChangeChannels(final FullTextMemberQuery query, final Collection<Channel> enableChannels, final Collection<Channel> disableChannels) {
        // No permission check needed for validation
        elementService.validateBulkChangeChannels(query, enableChannels, disableChannels);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private boolean applyRestrictions(final ElementQuery query) {
        if (query instanceof AdminQuery) {
            // Only admins with permissions can search admins
            permissionService.permission().admin(AdminAdminPermission.ADMINS_VIEW).check();
        } else if (query instanceof OperatorQuery) {
            // Only members and operators can see other operators from the member himself
            Member member = ((OperatorQuery) query).getMember();
            if (member == null) {
                throw new ValidationException();
            }
            permissionService.permission(member).member().operator().check();
        } else {
            // For members, just enforce the visible groups
            Collection<Group> visibleGroups = (Collection) permissionService.getVisibleMemberGroups(false);
            if (visibleGroups.isEmpty()) {
                return false;
            }
            Collection<Group> queryGroups = (Collection<Group>) query.getGroups();
            query.setGroups(PermissionHelper.checkSelection(visibleGroups, queryGroups));
        }
        // Ensure that only enabled users will be returned
        if (!LoggedUser.isAdministrator()) {
            query.setEnabled(true);
        }
        return true;
    }

    private void checkChangeGroup(final Element element) {
        permissionService.permission(element)
                .admin(AdminAdminPermission.ADMINS_CHANGE_GROUP, AdminMemberPermission.MEMBERS_CHANGE_GROUP)
                .member(MemberPermission.OPERATORS_MANAGE)
                .check();
    }

    private void checkManagePending(final PendingMember pendingMember) {
        permissionService.permission()
                .admin(AdminMemberPermission.MEMBERS_MANAGE_PENDING)
                .broker(BrokerPermission.MEMBERS_MANAGE_PENDING)
                .check();
        if (LoggedUser.isAdministrator() && !permissionService.manages(pendingMember.getMemberGroup())) {
            throw new PermissionDeniedException();
        }
        if (LoggedUser.isBroker() && !LoggedUser.element().equals(pendingMember.getBroker())) {
            throw new PermissionDeniedException();
        }
    }

    private Collection<? extends Group> getPossibleInitialGroups(final Element element) {
        Collection<? extends Group> groups;
        if (LoggedUser.getAccessType() == null) {
            // Public registration
            if (element instanceof Member) {
                groups = groupService.getPossibleInitialGroups(null);
            } else {
                groups = Collections.emptyList();
            }
        } else {
            if (element instanceof Administrator) {
                // Registering an administrator
                GroupQuery query = new GroupQuery();
                query.setStatus(Group.Status.NORMAL);
                query.setNature(Group.Nature.ADMIN);
                groups = groupService.search(query);
            } else if (element instanceof Member) {
                // Registering a member
                if (LoggedUser.isAdministrator()) {
                    // By admin
                    groups = permissionService.getManagedMemberGroups();
                } else if (LoggedUser.isBroker()) {
                    // By broker
                    BrokerGroup brokerGroup = (BrokerGroup) fetchService.fetch(LoggedUser.group(), BrokerGroup.Relationships.POSSIBLE_INITIAL_GROUPS);
                    groups = brokerGroup.getPossibleInitialGroups();
                } else {
                    groups = Collections.emptyList();
                }
            } else {
                // Registering an operator
                GroupQuery query = new GroupQuery();
                query.setMember(LoggedUser.member());
                query.setNature(Group.Nature.OPERATOR);
                query.setStatus(Group.Status.NORMAL);
                groups = groupService.search(query);
            }
        }
        return groups;
    }
}
