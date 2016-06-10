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
package nl.strohalm.cyclos.controls.members.brokering;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseAction;
import nl.strohalm.cyclos.entities.accounts.fees.transaction.BrokerCommission;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.services.elements.CommissionService;
import nl.strohalm.cyclos.utils.ActionHelper;
import nl.strohalm.cyclos.utils.EntityHelper;
import nl.strohalm.cyclos.utils.validation.ValidationException;

import org.apache.struts.action.ActionForward;

/**
 * Action used to suspend a broker commission
 * @author Jefferson Magno
 */
public class SuspendBrokerCommissionAction extends BaseAction {

    private CommissionService commissionService;

    @Inject
    public void setCommissionService(final CommissionService commissionService) {
        this.commissionService = commissionService;
    }

    @Override
    protected ActionForward executeAction(final ActionContext context) throws Exception {
        final HttpServletRequest request = context.getRequest();
        final SuspendBrokerCommissionForm form = context.getForm();
        final long brokerCommissionId = form.getBrokerCommissionId();
        final long brokerId = form.getBrokerId();

        if (brokerCommissionId < 1 || brokerId < 1) {
            throw new ValidationException();
        }

        final BrokerCommission brokerCommission = EntityHelper.reference(BrokerCommission.class, brokerCommissionId);
        final Member broker = EntityHelper.reference(Member.class, brokerId);
        try {
            commissionService.suspendCommissions(brokerCommission, broker);
            context.sendMessage("brokerCommission.suspended");
        } catch (final Exception e) {
            context.sendMessage("brokerCommission.error.suspending");
        }
        return ActionHelper.redirectWithParam(request, context.getSuccessForward(), "brokerId", brokerId);
    }

}
