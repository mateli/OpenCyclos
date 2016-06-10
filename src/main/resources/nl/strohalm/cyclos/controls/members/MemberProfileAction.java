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
package nl.strohalm.cyclos.controls.members;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.access.AdminMemberPermission;
import nl.strohalm.cyclos.access.BrokerPermission;
import nl.strohalm.cyclos.access.MemberPermission;
import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.elements.ProfileAction;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.access.Channel;
import nl.strohalm.cyclos.entities.access.MemberUser;
import nl.strohalm.cyclos.entities.access.User;
import nl.strohalm.cyclos.entities.customization.fields.CustomFieldValue;
import nl.strohalm.cyclos.entities.customization.fields.MemberCustomField;
import nl.strohalm.cyclos.entities.customization.fields.MemberCustomField.Access;
import nl.strohalm.cyclos.entities.customization.fields.MemberCustomFieldValue;
import nl.strohalm.cyclos.entities.customization.images.MemberImage;
import nl.strohalm.cyclos.entities.groups.AdminGroup;
import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.entities.groups.GroupFilter;
import nl.strohalm.cyclos.entities.groups.GroupFilterQuery;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.groups.MemberGroupSettings;
import nl.strohalm.cyclos.entities.members.Administrator;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.members.Operator;
import nl.strohalm.cyclos.entities.members.PendingEmailChange;
import nl.strohalm.cyclos.entities.members.Reference.Nature;
import nl.strohalm.cyclos.entities.settings.AccessSettings.UsernameGeneration;
import nl.strohalm.cyclos.exceptions.MailSendingException;
import nl.strohalm.cyclos.exceptions.PermissionDeniedException;
import nl.strohalm.cyclos.services.access.exceptions.NotConnectedException;
import nl.strohalm.cyclos.services.accounts.AccountService;
import nl.strohalm.cyclos.services.customization.ImageService;
import nl.strohalm.cyclos.services.customization.MemberCustomFieldService;
import nl.strohalm.cyclos.services.elements.MemberRecordService;
import nl.strohalm.cyclos.services.elements.ReferenceService;
import nl.strohalm.cyclos.services.elements.WhenSaving;
import nl.strohalm.cyclos.services.groups.GroupFilterService;
import nl.strohalm.cyclos.utils.ActionHelper;
import nl.strohalm.cyclos.utils.CustomFieldHelper;
import nl.strohalm.cyclos.utils.ImageHelper.ImageType;
import nl.strohalm.cyclos.utils.RelationshipHelper;
import nl.strohalm.cyclos.utils.binding.BeanBinder;
import nl.strohalm.cyclos.utils.binding.BeanCollectionBinder;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.binding.PropertyBinder;
import nl.strohalm.cyclos.utils.validation.ValidationException;

import org.apache.struts.action.ActionForward;
import org.apache.struts.upload.FormFile;

/**
 * Profile action for members
 * @author luis
 * @author Jefferson Magno
 */
public class MemberProfileAction extends ProfileAction<Member> {

    private static final Relationship[] FETCH = {
                                              RelationshipHelper.nested(User.Relationships.ELEMENT, Element.Relationships.GROUP),
                                              RelationshipHelper.nested(User.Relationships.ELEMENT, Member.Relationships.BROKER),
                                              RelationshipHelper.nested(User.Relationships.ELEMENT, Member.Relationships.CUSTOM_VALUES)
                                              };

    private AccountService              accountService;
    private MemberCustomFieldService    memberCustomFieldService;
    private GroupFilterService          groupFilterService;
    private ImageService                imageService;
    private MemberRecordService         memberRecordService;
    private ReferenceService            referenceService;

    private CustomFieldHelper           customFieldHelper;

    @Inject
    public void setAccountService(final AccountService accountService) {
        this.accountService = accountService;
    }

    @Inject
    public void setCustomFieldHelper(final CustomFieldHelper customFieldHelper) {
        this.customFieldHelper = customFieldHelper;
    }

    @Inject
    public void setGroupFilterService(final GroupFilterService groupFilterService) {
        this.groupFilterService = groupFilterService;
    }

    @Inject
    public void setImageService(final ImageService imageService) {
        this.imageService = imageService;
    }

    @Inject
    public void setMemberCustomFieldService(final MemberCustomFieldService memberCustomFieldService) {
        this.memberCustomFieldService = memberCustomFieldService;
    }

    @Inject
    public void setMemberRecordService(final MemberRecordService memberRecordService) {
        this.memberRecordService = memberRecordService;
    }

    @Inject
    public void setReferenceService(final ReferenceService referenceService) {
        this.referenceService = referenceService;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected <CFV extends CustomFieldValue> Class<CFV> getCustomFieldValueClass() {
        return (Class<CFV>) MemberCustomFieldValue.class;
    }

    @Override
    protected Class<Member> getElementClass() {
        return Member.class;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected <G extends Group> Class<G> getGroupClass() {
        return (Class<G>) MemberGroup.class;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected <U extends User> Class<U> getUserClass() {
        return (Class<U>) MemberUser.class;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected ActionForward handleDisplay(final ActionContext context) throws Exception {

        final MemberProfileForm form = context.getForm();
        final boolean profileOfBrokered = false;
        boolean myProfile = false;
        boolean profileOfOtherMember = false;
        boolean operatorCanViewReports = false;
        MemberUser memberUser = null;
        final HttpServletRequest request = context.getRequest();

        final Element loggedElement = context.getElement();
        // Load the user
        if (form.getMemberId() > 0 && form.getMemberId() != loggedElement.getId()) {
            final User loaded = elementService.loadUser(form.getMemberId(), FETCH);
            if (loaded instanceof MemberUser) {
                memberUser = (MemberUser) loaded;
                profileOfOtherMember = true;
            }
            if (context.isAdmin()) {
                try {
                    request.setAttribute("isLoggedIn", accessService.isLoggedIn(memberUser));
                } catch (final NotConnectedException e) {
                    // Ok - user is not online
                }
            }
            if (context.isOperator()) {
                final Operator operator = context.getElement();
                if (!memberUser.getMember().equals(operator.getMember())) {
                    // Operator viewing other member's profile
                    operatorCanViewReports = permissionService.hasPermission(MemberPermission.REPORTS_VIEW);
                }
            }
        }
        if (memberUser == null && context.isMember()) {
            memberUser = elementService.loadUser(context.getUser().getId(), FETCH);
            myProfile = true;
        }
        if (memberUser == null) {
            throw new ValidationException();
        }

        // Check whether the logged member can see this profile
        final Member member = memberUser.getMember();
        if (!loggedElement.equals(member)) {
            if (loggedElement instanceof Administrator) {
                // An admin must manage the member's group
                final AdminGroup group = groupService.load(context.getGroup().getId(), AdminGroup.Relationships.MANAGES_GROUPS);
                if (!group.getManagesGroups().contains(member.getGroup())) {
                    throw new PermissionDeniedException();
                }
            } else {
                // A member must be able to view the member's profile...
                final MemberGroup group = groupService.load(((Member) context.getAccountOwner()).getGroup().getId(), MemberGroup.Relationships.CAN_VIEW_PROFILE_OF_GROUPS);
                if (!group.getCanViewProfileOfGroups().contains(member.getGroup())) {
                    // ... but when he's the broker, show anyway
                    if (!context.isBrokerOf(member)) {
                        throw new PermissionDeniedException();
                    }
                }
            }
        }
        // Check if the member can access external channels
        boolean memberCanAccessExternalChannels = false;
        final MemberGroup group = groupService.load(member.getMemberGroup().getId(), MemberGroup.Relationships.CHANNELS);
        for (final Channel current : group.getChannels()) {
            if (!Channel.WEB.equals(current.getInternalName())) {
                memberCanAccessExternalChannels = true;
            }
        }
        request.setAttribute("memberCanAccessExternalChannels", memberCanAccessExternalChannels);

        // Check whether the given member has transaction feedbacks
        final Collection<Nature> referenceNatures = referenceService.getNaturesByGroup(member.getMemberGroup());
        final boolean hasTransactionFeedbacks = referenceNatures.contains(Nature.TRANSACTION);
        request.setAttribute("hasTransactionFeedbacks", hasTransactionFeedbacks);

        // Check if the member belongs to a group managed by the admin
        if (context.isAdmin()) {
            AdminGroup adminGroup = context.getGroup();
            adminGroup = groupService.load(adminGroup.getId(), AdminGroup.Relationships.MANAGES_GROUPS);
            if (!adminGroup.getManagesGroups().contains(member.getGroup())) {
                throw new PermissionDeniedException();
            }
        }

        getReadDataBinder(context).writeAsString(form.getMember(), member);

        // Retrieve the group filters
        if (context.isMember()) {
            final GroupFilterQuery groupFilterQuery = new GroupFilterQuery();
            groupFilterQuery.setGroup(memberUser.getMember().getMemberGroup());
            final Collection<GroupFilter> groupFilters = groupFilterService.search(groupFilterQuery);
            if (groupFilters.size() > 0) {
                final StringBuilder groupFiltersStr = new StringBuilder();
                for (final GroupFilter groupFilter : groupFilters) {
                    if (groupFilter.isShowInProfile()) {
                        if (!"".equals(groupFiltersStr.toString())) {
                            groupFiltersStr.append(", ");
                        }
                        groupFiltersStr.append(groupFilter.getName());
                    }
                }
                if (!"".equals(groupFiltersStr.toString())) {
                    request.setAttribute("groupFilters", groupFiltersStr.toString());
                }
            }
        }

        // Retrieve the images
        final List<MemberImage> images = (List<MemberImage>) imageService.listByOwner(member);
        final MemberGroupSettings groupSettings = member.getMemberGroup().getMemberSettings();
        final boolean maxImages = groupSettings == null ? true : images.size() >= groupSettings.getMaxImagesPerMember();

        // Check the permissions
        final boolean usernameGenerated = settingsService.getAccessSettings().getUsernameGeneration() != UsernameGeneration.NONE;
        boolean editable = myProfile;
        boolean byBroker = false;
        boolean canChangeName = false;
        boolean canChangeUsername = false;
        boolean canChangeEmail = false;
        final boolean removed = member.getGroup().getStatus() == Group.Status.REMOVED;
        if (!myProfile) {
            boolean canViewRecords = false;
            if (context.isAdmin()) {
                // Check if the member has remarks
                editable = permissionService.hasPermission(AdminMemberPermission.MEMBERS_CHANGE_PROFILE);
                canViewRecords = permissionService.hasPermission(AdminMemberPermission.RECORDS_VIEW);
                canChangeName = editable && permissionService.hasPermission(AdminMemberPermission.MEMBERS_CHANGE_NAME);
                canChangeEmail = editable && permissionService.hasPermission(AdminMemberPermission.MEMBERS_CHANGE_EMAIL);
                canChangeUsername = !usernameGenerated && editable && permissionService.hasPermission(AdminMemberPermission.MEMBERS_CHANGE_USERNAME);
            } else {
                // Check if the member is by broker
                byBroker = context.isBrokerOf(member);
                if (byBroker) {
                    editable = permissionService.hasPermission(BrokerPermission.MEMBERS_CHANGE_PROFILE);
                    canViewRecords = permissionService.hasPermission(BrokerPermission.MEMBER_RECORDS_VIEW);
                    canChangeName = editable && permissionService.hasPermission(BrokerPermission.MEMBERS_CHANGE_NAME);
                    canChangeEmail = editable && permissionService.hasPermission(BrokerPermission.MEMBERS_CHANGE_EMAIL);
                    canChangeUsername = !usernameGenerated && editable && permissionService.hasPermission(BrokerPermission.MEMBERS_CHANGE_USERNAME);
                }
            }
            if (canViewRecords) {
                request.setAttribute("countByRecordType", memberRecordService.countByType(member));
            }
        } else {
            canChangeName = permissionService.hasPermission(MemberPermission.PROFILE_CHANGE_NAME);
            canChangeEmail = permissionService.hasPermission(MemberPermission.PROFILE_CHANGE_EMAIL);
            canChangeUsername = !usernameGenerated && permissionService.hasPermission(MemberPermission.PROFILE_CHANGE_USERNAME);
        }

        final Group loggedGroup = context.getGroup();
        // Retrieve the custom fields
        final List<MemberCustomField> allFields = memberCustomFieldService.list();
        List<MemberCustomField> customFields;
        if (removed) {
            // Removed members are view-only, and will display the values for all fields the member had a value
            customFields = allFields;
        } else {
            customFields = customFieldHelper.onlyForGroup(allFields, member.getMemberGroup());
        }
        // This map will store, for each field, if it is editable or not
        final Map<MemberCustomField, Boolean> editableFields = new HashMap<MemberCustomField, Boolean>();
        for (final Iterator<MemberCustomField> it = customFields.iterator(); it.hasNext();) {
            final MemberCustomField field = it.next();
            // Check if the field is visible
            final Access visibility = field.getVisibilityAccess();
            if (visibility != null && !visibility.granted(loggedGroup, myProfile, byBroker, false, false)) {
                it.remove();
            }
            // Check if the field can be updated
            final Access update = field.getUpdateAccess();
            editableFields.put(field, update != null && update.granted(loggedGroup, myProfile, byBroker, false, false));
        }

        // Check if logged user belongs to a group with card type associated - for members only
        boolean hasCardType = false;
        if (member.getMemberGroup().getCardType() != null) {
            hasCardType = true;
        }

        PendingEmailChange pendingEmailChange = null;
        if (editable) {
            pendingEmailChange = elementService.getPendingEmailChange(member);
        }

        // Store the request attributes
        request.setAttribute("member", member);
        request.setAttribute("removed", member.getGroup().getStatus() == Group.Status.REMOVED);
        request.setAttribute("hasAccounts", accountService.hasAccounts(member));
        request.setAttribute("disabledLogin", accessService.isLoginBlocked(member.getUser()));
        request.setAttribute("customFields", customFieldHelper.buildEntries(customFields, member.getCustomValues()));
        request.setAttribute("editableFields", editableFields);
        request.setAttribute("canChangeName", canChangeName);
        request.setAttribute("canChangeEmail", canChangeEmail);
        request.setAttribute("canChangeUsername", canChangeUsername);
        request.setAttribute("pendingEmailChange", pendingEmailChange);
        request.setAttribute("images", images);
        request.setAttribute("maxImages", maxImages);
        request.setAttribute("editable", editable);
        request.setAttribute("byBroker", byBroker);
        request.setAttribute("myProfile", myProfile);
        request.setAttribute("profileOfOtherMember", profileOfOtherMember);
        request.setAttribute("profileOfBrokered", profileOfBrokered);
        request.setAttribute("operatorCanViewReports", operatorCanViewReports);
        request.setAttribute("hasCardType", hasCardType);

        if (editable) {
            return context.getInputForward();
        } else {
            return context.findForward("view");
        }
    }

    @Override
    protected ActionForward handleSubmit(final ActionContext context) throws Exception {
        final MemberProfileForm form = context.getForm();
        // Save the member
        Member member = resolveMember(context);

        // Load the member's broker
        Member currentMember;
        try {
            currentMember = elementService.load(member.getId(), Member.Relationships.BROKER);
        } catch (final ClassCastException e) {
            throw new ValidationException();
        }
        final Member broker = currentMember.getBroker();
        member.setBroker(broker);

        if (member.isTransient()) {
            throw new ValidationException();
        }

        // Save the member, checking if a pending e-mail change has been created
        final boolean hadPendingEmailChange = elementService.getPendingEmailChange(member) != null;
        try {
            member = elementService.changeProfile(member);
        } catch (final MailSendingException e) {
            return context.sendError("profile.error.changeEmailValidationFailed");
        }
        final PendingEmailChange pendingEmailChange = elementService.getPendingEmailChange(member);

        // Save the uploaded image
        final FormFile upload = form.getPicture();
        if (upload != null && upload.getFileSize() > 0) {
            try {
                imageService.save(member, form.getPictureCaption(), ImageType.getByContentType(upload.getContentType()), upload.getFileName(), upload.getInputStream());
            } finally {
                upload.destroy();
            }
        }

        if (!hadPendingEmailChange && pendingEmailChange != null) {
            context.sendMessage("profile.modified.emailPending", pendingEmailChange.getNewEmail());
        } else {
            context.sendMessage("profile.modified");
        }

        return ActionHelper.redirectWithParam(context.getRequest(), super.handleSubmit(context), "memberId", member.getId());
    }

    @Override
    protected DataBinder<Member> initDataBinderForRead(final ActionContext context) {
        final BeanBinder<Member> dataBinder = (BeanBinder<Member>) super.initDataBinderForRead(context);
        dataBinder.registerBinder("hideEmail", PropertyBinder.instance(Boolean.TYPE, "hideEmail"));
        return dataBinder;
    }

    @Override
    @SuppressWarnings({ "unchecked", "rawtypes" })
    protected DataBinder<Member> initDataBinderForWrite(final ActionContext context) {
        final BeanBinder<Member> dataBinder = (BeanBinder<Member>) super.initDataBinderForWrite(context);
        dataBinder.registerBinder("hideEmail", PropertyBinder.instance(Boolean.TYPE, "hideEmail"));

        final BeanBinder<? extends User> userBinder = BeanBinder.instance(getUserClass(), "user");
        userBinder.registerBinder("username", PropertyBinder.instance(String.class, "username"));
        dataBinder.registerBinder("user", userBinder);

        // Add another custom field value attribute: hidden
        final BeanCollectionBinder collectionBinder = (BeanCollectionBinder) dataBinder.getMappings().get("customValues");
        final BeanBinder elementBinder = (BeanBinder) collectionBinder.getElementBinder();
        elementBinder.registerBinder("hidden", PropertyBinder.instance(Boolean.TYPE, "hidden"));

        return dataBinder;
    }

    @Override
    protected void validateForm(final ActionContext context) {
        final Member member = resolveMember(context);
        elementService.validate(member, WhenSaving.PROFILE, false);
    }

    private Member resolveMember(final ActionContext context) {
        final MemberProfileForm form = context.getForm();
        return getWriteDataBinder(context).readFromString(form.getMember());
    }

}
