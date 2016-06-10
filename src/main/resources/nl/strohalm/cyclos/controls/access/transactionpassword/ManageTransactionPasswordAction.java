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
package nl.strohalm.cyclos.controls.access.transactionpassword;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseFormAction;
import nl.strohalm.cyclos.entities.access.OperatorUser;
import nl.strohalm.cyclos.entities.access.TransactionPassword;
import nl.strohalm.cyclos.entities.access.User;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.Operator;
import nl.strohalm.cyclos.services.elements.ResetTransactionPasswordDTO;
import nl.strohalm.cyclos.utils.ActionHelper;
import nl.strohalm.cyclos.utils.RelationshipHelper;
import nl.strohalm.cyclos.utils.RequestHelper;
import nl.strohalm.cyclos.utils.validation.ValidationException;

import org.apache.struts.action.ActionForward;

/**
 * Action used to reset a member's transaction password
 * @author luis
 */
public class ManageTransactionPasswordAction extends BaseFormAction {

    @Override
    protected ActionForward handleDisplay(final ActionContext context) throws Exception {
        final HttpServletRequest request = context.getRequest();
        final ManageTransactionPasswordForm form = context.getForm();
        final User user = retrieveUser(context);
        boolean canReset = false;
        boolean canBlock = false;
        switch (user.getTransactionPasswordStatus()) {
            case ACTIVE:
                canReset = true;
                canBlock = true;
                break;
            case BLOCKED:
                canReset = true;
                break;
            case PENDING:
                canBlock = true;
                break;
            case NEVER_CREATED:
                if (user.getElement().getGroup().getBasicSettings().getTransactionPassword() == TransactionPassword.MANUAL) {
                    canReset = true;
                }
                break;
        }
        request.setAttribute("groupStatus", user.getElement().getGroup().getBasicSettings().getTransactionPassword());
        request.setAttribute("user", user);
        request.setAttribute("canReset", canReset);
        request.setAttribute("canBlock", canBlock);
        RequestHelper.storeEnum(request, TransactionPassword.class, "globalTransactionPasswordStatus");
        RequestHelper.storeEnum(request, User.TransactionPasswordStatus.class, "userTransactionPasswordStatus");

        if (form.isEmbed()) {
            return new ActionForward("/pages/access/transactionPassword/manageTransactionPassword.jsp");
        } else {
            return context.getInputForward();
        }

    }

    @Override
    protected ActionForward handleSubmit(final ActionContext context) throws Exception {
        final ManageTransactionPasswordForm form = context.getForm();
        User user = retrieveUser(context);
        final boolean block = form.isBlock();
        final ResetTransactionPasswordDTO dto = new ResetTransactionPasswordDTO();
        dto.setUser(user);
        dto.setAllowGeneration(!block);
        user = accessService.resetTransactionPassword(dto);
        context.sendMessage(block ? "transactionPassword.blocked" : "transactionPassword.reset");
        return ActionHelper.redirectWithParam(context.getRequest(), context.getSuccessForward(), "userId", user.getId());
    }

    private User retrieveUser(final ActionContext context) {
        final HttpServletRequest request = context.getRequest();
        if (request.getAttribute("element") != null) {
            // The element may be already retrieved on the manage passwords action
            return ((Element) request.getAttribute("element")).getUser();
        }

        final ManageTransactionPasswordForm form = context.getForm();
        User user;
        final long userId = form.getUserId();
        try {
            user = elementService.loadUser(userId, RelationshipHelper.nested(User.Relationships.ELEMENT, Element.Relationships.GROUP));
            if (user instanceof OperatorUser) {
                Element element = user.getElement();
                element = elementService.load(element.getId(), RelationshipHelper.nested(Operator.Relationships.MEMBER, Element.Relationships.GROUP));
            }
        } catch (final Exception e) {
            throw new ValidationException();
        }
        return user;
    }

}
