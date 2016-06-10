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
package nl.strohalm.cyclos.controls.operators;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.elements.CreateElementAction;
import nl.strohalm.cyclos.entities.access.OperatorUser;
import nl.strohalm.cyclos.entities.customization.fields.OperatorCustomField;
import nl.strohalm.cyclos.entities.customization.fields.OperatorCustomFieldValue;
import nl.strohalm.cyclos.entities.groups.OperatorGroup;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.members.Operator;
import nl.strohalm.cyclos.services.customization.OperatorCustomFieldService;
import nl.strohalm.cyclos.services.elements.WhenSaving;
import nl.strohalm.cyclos.utils.ActionHelper;
import nl.strohalm.cyclos.utils.transaction.CurrentTransactionData;
import nl.strohalm.cyclos.utils.validation.ValidationException;

import org.apache.struts.action.ActionForward;

/**
 * Action used by a member to create operators
 * @author luis
 */
public class CreateOperatorAction extends CreateElementAction<Operator> {

    private OperatorCustomFieldService operatorCustomFieldService;

    @Inject
    public void setOperatorCustomFieldService(final OperatorCustomFieldService operatorCustomFieldService) {
        this.operatorCustomFieldService = operatorCustomFieldService;
    }

    @Override
    protected ActionForward create(final Element element, final ActionContext context) {
        final CreateOperatorForm form = context.getForm();
        ensureMember(context, element);
        final Operator operator = (Operator) elementService.register(element, false, context.getRequest().getRemoteAddr());

        String successKey = "operator.created";

        boolean sendMessage = false;

        // Check if there's a mail exception
        if (CurrentTransactionData.hasMailError()) {
            successKey += ".mailError";
            sendMessage = true;
        }

        // Redirect to the correct profile
        String paramName;
        Object paramValue;
        ActionForward forward;
        if (form.isOpenProfile()) {
            paramName = "operatorId";
            paramValue = operator.getId();
            forward = context.findForward("profile");
        } else {
            sendMessage = true;
            paramName = "groupId";
            paramValue = operator.getGroup().getId();
            forward = context.findForward("new");
        }
        if (sendMessage) {
            context.sendMessage(successKey);
        }
        return ActionHelper.redirectWithParam(context.getRequest(), forward, paramName, paramValue);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected Class<OperatorCustomField> getCustomFieldClass() {
        return OperatorCustomField.class;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected Class<OperatorCustomFieldValue> getCustomFieldValueClass() {
        return OperatorCustomFieldValue.class;
    }

    @Override
    protected Class<Operator> getElementClass() {
        return Operator.class;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected Class<OperatorGroup> getGroupClass() {
        return OperatorGroup.class;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected Class<OperatorUser> getUserClass() {
        return OperatorUser.class;
    }

    @Override
    protected void prepareForm(final ActionContext context) throws Exception {
        final HttpServletRequest request = context.getRequest();
        final CreateOperatorForm form = context.getForm();

        // Get the selected group
        final long groupId = form.getGroupId();
        if (groupId <= 0L) {
            throw new ValidationException();
        }
        final OperatorGroup group = groupService.load(groupId);

        // Get the custom fields
        final List<OperatorCustomField> customFields = operatorCustomFieldService.list((Member) context.getElement());
        request.setAttribute("customFields", customFields);
        request.setAttribute("group", group);
    }

    @Override
    protected void runValidation(final ActionContext context, final Element element) {
        ensureMember(context, element);
        elementService.validate(element, WhenSaving.OPERATOR, true);
    }

    private void ensureMember(final ActionContext context, final Element element) {
        final Operator operator = (Operator) element;
        if (operator.getMember() == null) {
            operator.setMember(context.getMember());
        }
    }
}
