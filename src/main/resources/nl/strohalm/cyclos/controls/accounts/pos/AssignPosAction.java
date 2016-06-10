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
package nl.strohalm.cyclos.controls.accounts.pos;

import java.util.HashMap;
import java.util.Map;

import nl.strohalm.cyclos.access.AdminMemberPermission;
import nl.strohalm.cyclos.access.BrokerPermission;
import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseFormAction;
import nl.strohalm.cyclos.entities.accounts.pos.Pos;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.exceptions.PermissionDeniedException;
import nl.strohalm.cyclos.services.accounts.pos.PosService;
import nl.strohalm.cyclos.utils.ActionHelper;
import nl.strohalm.cyclos.utils.ResponseHelper;
import nl.strohalm.cyclos.utils.transaction.CurrentTransactionData;
import nl.strohalm.cyclos.utils.validation.ValidationException;

import org.apache.struts.action.ActionForward;

/**
 * 
 * @author rodrigo
 */
public class AssignPosAction extends BaseFormAction {

    private PosService posService;

    @Inject
    public void setPosService(final PosService posService) {
        this.posService = posService;
    }

    @Override
    protected ActionForward handleSubmit(final ActionContext context) throws Exception {
        final EditPosForm form = context.getForm();
        final String posId = form.getPosId();
        final long memberId = form.getMemberId();
        if (memberId <= 0) {
            throw new PermissionDeniedException();
        }
        final Member member = elementService.load(memberId);

        Pos pos;
        try {
            pos = posService.loadByPosId(posId);
            pos = posService.assignPos(member, pos.getId());
            context.sendMessage("pos.assigned", member.getUsername());
        } catch (final EntityNotFoundException e) {
            // Clear the current transaction error, to avoid a rollback
            CurrentTransactionData.clearError();
            pos = new Pos();
            pos.setPosId(posId);
            pos = posService.save(pos);
            pos = posService.assignPos(member, pos.getId());
            context.sendMessage("pos.createdAndAssigned", member.getUsername());
        }
        return ActionHelper.redirectWithParam(context.getRequest(), context.getSuccessForward(), "memberId", memberId);
    }

    @Override
    protected ActionForward handleValidation(final ActionContext context) {
        final Map<String, Object> fields = new HashMap<String, Object>();
        try {
            validateForm(context);
            final EditPosForm form = context.getForm();
            final Pos pos = posService.loadByPosId(form.getPosId());
            if (pos.getStatus() == Pos.Status.UNASSIGNED) {
                fields.put("validationMessage", "POS_AVAILABLE");
            } else {
                fields.put("validationMessage", "POS_UNAVAILABLE");
                fields.put("errorMessage", context.message("pos.error.unvailable"));
            }
            responseHelper.writeStatus(context.getResponse(), ResponseHelper.Status.SUCCESS, fields);
        } catch (final EntityNotFoundException e) {
            if (permissionService.hasPermission(AdminMemberPermission.POS_MANAGE) || permissionService.hasPermission(BrokerPermission.POS_MANAGE)) {
                fields.put("validationMessage", "POS_NOT_FOUND");
                fields.put("confirmationMessage", context.message("pos.createAndAssign"));
            } else {
                fields.put("validationMessage", "POS_UNAVAILABLE");
                fields.put("errorMessage", context.message("pos.error.noAssignPermission"));
            }
            responseHelper.writeStatus(context.getResponse(), ResponseHelper.Status.SUCCESS, fields);
        } catch (final ValidationException e) {
            responseHelper.writeValidationErrors(context.getResponse(), e);
        }

        return null;
    }

    @Override
    protected void validateForm(final ActionContext context) {
        final EditPosForm form = context.getForm();
        if (form.getMemberId() <= 0) {
            throw new ValidationException();
        }
    }
}
