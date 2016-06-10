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
package nl.strohalm.cyclos.controls.alerts;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseAction;
import nl.strohalm.cyclos.services.alerts.AlertService;

import org.apache.struts.action.ActionForward;

/**
 * Action used to remove the selected alerts
 * @author luis
 */
public class RemoveAlertsAction extends BaseAction {

    private AlertService alertService;

    public AlertService getAlertService() {
        return alertService;
    }

    @Inject
    public void setAlertService(final AlertService alertService) {
        this.alertService = alertService;
    }

    @Override
    protected ActionForward executeAction(final ActionContext context) throws Exception {
        final RemoveAlertsForm form = context.getForm();
        final boolean isMember = "MEMBER".equals(form.getAlertType());
        alertService.removeAlerts(form.getAlertIds());
        context.sendMessage("alert.removed");
        return context.findForward(isMember ? "toMember" : "toSystem");
    }

}
