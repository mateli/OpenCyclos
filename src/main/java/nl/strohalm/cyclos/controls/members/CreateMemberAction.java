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

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.access.AdminMemberPermission;
import nl.strohalm.cyclos.access.BrokerPermission;
import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.elements.CreateElementAction;
import nl.strohalm.cyclos.controls.elements.CreateElementForm;
import nl.strohalm.cyclos.entities.access.MemberUser;
import nl.strohalm.cyclos.entities.access.User;
import nl.strohalm.cyclos.entities.customization.fields.MemberCustomField;
import nl.strohalm.cyclos.entities.customization.fields.MemberCustomFieldValue;
import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.entities.groups.GroupQuery;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.members.RegisteredMember;
import nl.strohalm.cyclos.exceptions.MailSendingException;
import nl.strohalm.cyclos.services.customization.MemberCustomFieldService;
import nl.strohalm.cyclos.services.elements.WhenSaving;
import nl.strohalm.cyclos.utils.ActionHelper;
import nl.strohalm.cyclos.utils.CustomFieldHelper;
import nl.strohalm.cyclos.utils.binding.BeanBinder;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.binding.PropertyBinder;
import nl.strohalm.cyclos.utils.query.PageHelper;
import nl.strohalm.cyclos.utils.transaction.CurrentTransactionData;
import nl.strohalm.cyclos.utils.validation.ValidationException;

import org.apache.struts.action.ActionForward;

/**
 * Action used to create members
 * @author luis
 */
public class CreateMemberAction extends CreateElementAction<Member> {

    private MemberCustomFieldService memberCustomFieldService;

    private CustomFieldHelper        customFieldHelper;

    @Override
    public DataBinder<? extends Element> getDataBinder() {
        if (dataBinder == null) {
            final DataBinder<? extends Element> binder = getBaseBinder();
            ((BeanBinder<? extends Element>) binder).registerBinder("broker", PropertyBinder.instance(Member.class, "broker"));
            dataBinder = binder;
        }
        return dataBinder;
    }

    public MemberCustomFieldService getMemberCustomFieldService() {
        return memberCustomFieldService;
    }

    @Inject
    public void setCustomFieldHelper(final CustomFieldHelper customFieldHelper) {
        this.customFieldHelper = customFieldHelper;
    }

    @Inject
    public void setMemberCustomFieldService(final MemberCustomFieldService memberCustomFieldService) {
        this.memberCustomFieldService = memberCustomFieldService;
    }

    @Override
    protected ActionForward create(final Element element, final ActionContext context) {
        final CreateElementForm form = context.getForm();
        final Member member = (Member) element;
        ensureBrokerIsSet(context, element);

        final boolean sendPasswordByEmail = member.getMemberGroup().getMemberSettings().isSendPasswordByEmail();
        final boolean canChangePassword = permissionService.hasPermission(context.isAdmin() ? AdminMemberPermission.ACCESS_CHANGE_PASSWORD : BrokerPermission.MEMBER_ACCESS_CHANGE_PASSWORD);
        final boolean allowSetPassword = !sendPasswordByEmail || canChangePassword;

        // When password cannot be set, ensure it's null
        if (!allowSetPassword) {
            final User user = member.getUser();
            if (user != null) {
                user.setPassword(null);
            }
        }

        // When password is not sent by e-mail and can't set a definitive password, ensure the force change is set
        if (!sendPasswordByEmail && !canChangePassword) {
            form.setForceChangePassword(true);
        }

        RegisteredMember registeredMember;
        String successKey = "createMember.created";
        try {
            registeredMember = (RegisteredMember) elementService.register(member, form.isForceChangePassword(), context.getRequest().getRemoteAddr());
        } catch (final MailSendingException e) {
            return context.sendError("createMember.error.mailSending");
        }

        boolean sendMessage = false;

        // Check if there's a mail exception
        if (CurrentTransactionData.hasMailError()) {
            successKey = "createMember.created.mailError";
            sendMessage = true;
        }

        // Resolve the forward
        String paramName;
        Object paramValue;
        ActionForward forward;
        if (form.isOpenProfile()) {
            if (registeredMember instanceof Member) {
                // The member was already created
                paramName = "memberId";
                forward = context.findForward("profile");
                paramValue = registeredMember.getId();
            } else {
                // It's a PendingMember, awaiting mail confirmation
                if (permissionService.permission().admin(AdminMemberPermission.MEMBERS_MANAGE_PENDING).broker(BrokerPermission.MEMBERS_MANAGE_PENDING).hasPermission()) {
                    // The logged user can view pending members: redirect to the details
                    paramName = "pendingMemberId";
                    forward = context.findForward("pendingMemberProfile");
                    paramValue = registeredMember.getId();
                } else {
                    // No permission to view pending members - redirect to new with a message
                    successKey = "createMember.created.pending";
                    sendMessage = true;
                    paramName = "groupId";
                    paramValue = registeredMember.getMemberGroup().getId();
                    forward = context.findForward("new");
                }
            }
        } else {
            sendMessage = true;
            paramName = "groupId";
            paramValue = registeredMember.getMemberGroup().getId();
            forward = context.findForward("new");
        }

        if (sendMessage) {
            context.sendMessage(successKey);
        }
        return ActionHelper.redirectWithParam(context.getRequest(), forward, paramName, paramValue);
    }

    @Override
    protected void formAction(final ActionContext context) throws Exception {
        super.formAction(context);
        context.sendMessage("createMember.created");
    }

    @Override
    @SuppressWarnings("unchecked")
    protected Class<MemberCustomField> getCustomFieldClass() {
        return MemberCustomField.class;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected Class<MemberCustomFieldValue> getCustomFieldValueClass() {
        return MemberCustomFieldValue.class;
    }

    @Override
    protected Class<Member> getElementClass() {
        return Member.class;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected Class<MemberGroup> getGroupClass() {
        return MemberGroup.class;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected Class<MemberUser> getUserClass() {
        return MemberUser.class;
    }

    @Override
    protected void prepareForm(final ActionContext context) throws Exception {
        final HttpServletRequest request = context.getRequest();
        final CreateElementForm form = context.getForm();

        // Get the initial group
        if (form.getGroupId() <= 0L) {
            throw new ValidationException();
        }

        final MemberGroup group = groupService.load(form.getGroupId());
        // Get the custom fields for the initial group
        final List<MemberCustomField> customFields = customFieldHelper.onlyForGroup(memberCustomFieldService.list(), group);
        final boolean byBroker = context.isBroker();
        for (final Iterator<MemberCustomField> iterator = customFields.iterator(); iterator.hasNext();) {
            final MemberCustomField field = iterator.next();
            if (!field.getUpdateAccess().granted(context.getGroup(), false, byBroker, true, false)) {
                iterator.remove();
            }
        }
        request.setAttribute("customFields", customFields);
        request.setAttribute("group", group);

        // Store the password control flags
        final boolean sendPasswordByEmail = group.getMemberSettings().isSendPasswordByEmail();
        final boolean canChangePassword = permissionService.hasPermission(context.isAdmin() ? AdminMemberPermission.ACCESS_CHANGE_PASSWORD : BrokerPermission.MEMBER_ACCESS_CHANGE_PASSWORD);
        request.setAttribute("allowAutomaticPassword", sendPasswordByEmail && canChangePassword);
        request.setAttribute("allowSetPassword", !sendPasswordByEmail || canChangePassword);
        request.setAttribute("allowSetForceChangePassword", canChangePassword);

        if (context.isAdmin()) {
            final GroupQuery query = new GroupQuery();
            query.setNatures(Group.Nature.BROKER);
            query.setStatus(Group.Status.NORMAL);
            query.setPageForCount();
            final boolean allowSetBroker = PageHelper.getTotalCount(groupService.search(query)) > 0;
            request.setAttribute("allowSetBroker", allowSetBroker);
        } else if (context.isBroker()) {
            request.setAttribute("byBroker", true);
        } else {
            throw new ValidationException();
        }
    }

    @Override
    protected void runValidation(final ActionContext context, final Element element) {
        final CreateElementForm form = context.getForm();
        final boolean manualPassword = form.isManualPassword();
        final WhenSaving when = context.isAdmin() ? WhenSaving.MEMBER_BY_ADMIN : WhenSaving.BY_BROKER;
        ensureBrokerIsSet(context, element);
        elementService.validate(element, when, manualPassword);
    }

    private void ensureBrokerIsSet(final ActionContext context, final Element element) {
        if (context.isBroker()) {
            final Member member = (Member) element;
            member.setBroker(context.<Member> getElement());
        }
    }

}
