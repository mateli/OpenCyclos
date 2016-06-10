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
package nl.strohalm.cyclos.controls.accounts.guarantees.guarantees;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseAction;
import nl.strohalm.cyclos.entities.accounts.guarantees.Guarantee;
import nl.strohalm.cyclos.services.accounts.guarantees.GuaranteeService;
import nl.strohalm.cyclos.services.accounts.guarantees.exceptions.GuaranteeStatusChangeException;
import nl.strohalm.cyclos.utils.ActionHelper;

import org.apache.struts.action.ActionForward;

/**
 * Base class used to change the guarantee's status. It must be extended to support a new status change
 * @author ameyer
 * 
 */
public abstract class ChangeStatusGuaranteeAction extends BaseAction {

    protected GuaranteeService guaranteeService;

    @Override
    public ActionForward executeAction(final ActionContext context) throws Exception {
        final ChangeStatusGuaranteeForm form = context.getForm();
        try {
            changeStatus(context);
        } catch (final GuaranteeStatusChangeException e) {
            context.sendMessage("guarantee.error.changeStatus", context.message("guarantee.status." + e.getNewstatus()));
        }
        return ActionHelper.redirectWithParam(context.getRequest(), context.getSuccessForward(), "guaranteeId", form.getGuaranteeId());
    }

    @Inject
    public void setGuaranteeService(final GuaranteeService guaranteeService) {
        this.guaranteeService = guaranteeService;
    }

    protected void changeStatus(final ActionContext context) {
        final ChangeStatusGuaranteeForm form = context.getForm();
        guaranteeService.changeStatus(form.getGuaranteeId(), getNewStatus());
    }

    /**
     * @return the new guarantee's status
     */
    protected abstract Guarantee.Status getNewStatus();
}
