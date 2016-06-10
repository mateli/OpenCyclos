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
package nl.strohalm.cyclos.controls.members.pending;

import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseAction;
import nl.strohalm.cyclos.entities.members.PendingMember;
import nl.strohalm.cyclos.utils.ActionHelper;
import nl.strohalm.cyclos.utils.validation.ValidationException;

import org.apache.struts.action.ActionForward;

/**
 * Action used to resend a validation e-mail
 * 
 * @author luis
 */
public class ResendEmailValidationAction extends BaseAction {

    @Override
    protected ActionForward executeAction(final ActionContext context) throws Exception {
        final ResendEmailValidationForm form = context.getForm();
        final long id = form.getPendingMemberId();
        PendingMember pendingMember;
        try {
            pendingMember = elementService.loadPendingMember(id);
        } catch (final Exception e) {
            throw new ValidationException();
        }
        elementService.resendEmail(pendingMember);
        context.sendMessage("pendingMember.emailResent");
        return ActionHelper.redirectWithParam(context.getRequest(), context.getSuccessForward(), "pendingMemberId", pendingMember.getId());
    }

}
