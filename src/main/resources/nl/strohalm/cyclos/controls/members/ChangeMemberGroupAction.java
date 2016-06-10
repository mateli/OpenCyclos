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

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.elements.ChangeElementGroupAction;
import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.services.elements.MemberService;
import nl.strohalm.cyclos.services.elements.exceptions.ChangeMemberGroupException;
import nl.strohalm.cyclos.utils.ActionHelper;
import nl.strohalm.cyclos.utils.ResponseHelper;
import nl.strohalm.cyclos.utils.transaction.CurrentTransactionData;
import nl.strohalm.cyclos.utils.validation.ValidationException;

import org.apache.struts.action.ActionForward;

/**
 * Action used to change a member's group
 * @author luis
 */
public class ChangeMemberGroupAction extends ChangeElementGroupAction {

    private MemberService memberService;

    public MemberService getMemberService() {
        return memberService;
    }

    @Inject
    public void setMemberService(final MemberService memberService) {
        this.memberService = memberService;
    }

    @Override
    protected ActionForward handleSubmit(final ActionContext context) throws Exception {
        try {
            final ChangeMemberGroupForm form = context.getForm();
            final ActionForward forward = ActionHelper.redirectWithParam(context.getRequest(), super.handleSubmit(context), "memberId", form.getMemberId());
            String key = "changeGroup.member.changed";
            if (CurrentTransactionData.hasMailError()) {
                key += ".mailError";
            }
            context.sendMessage(key);
            return forward;
        } catch (final ChangeMemberGroupException e) {
            return context.sendError(e.getErrorKey(), e.getErrorArgument());
        }
    }

    @Override
    protected ActionForward handleValidation(final ActionContext context) {
        try {
            validateForm(context);
            final ChangeMemberGroupForm form = context.getForm();
            final Group newGroup = groupService.load(form.getNewGroupId());
            if (newGroup.getStatus() == Group.Status.REMOVED) {
                final Map<String, Object> fields = new HashMap<String, Object>();
                fields.put("confirmationMessage", context.message("changeGroup.confirmRemove", newGroup.getName()));
                responseHelper.writeStatus(context.getResponse(), ResponseHelper.Status.SUCCESS, fields);
            } else {
                responseHelper.writeValidationSuccess(context.getResponse());
            }
        } catch (final ValidationException e) {
            responseHelper.writeValidationErrors(context.getResponse(), e);
        }
        return null;
    }

    @Override
    protected void prepareForm(final ActionContext context) throws Exception {
        super.prepareForm(context);
        final HttpServletRequest request = context.getRequest();
        final Member member = (Member) request.getAttribute("element");
        final boolean isActive = member.getActivationDate() != null;
        request.setAttribute("canRemove", !isActive && member.getGroup().getStatus().isEnabled());
        request.setAttribute("member", member);
    }
}
