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
package nl.strohalm.cyclos.controls.admintasks;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseAction;
import nl.strohalm.cyclos.services.application.ApplicationService;

import org.apache.struts.action.ActionForward;

/**
 * Action used to set the system online / offline
 * 
 * @author luis
 */
public class SetSystemOnlineAction extends BaseAction {

    private ApplicationService applicationService;

    @Inject
    public void setApplicationService(final ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @Override
    protected ActionForward executeAction(final ActionContext context) throws Exception {
        final SetSystemOnlineForm form = context.getForm();
        final boolean online = form.isOnline();
        applicationService.setOnline(online);
        context.getServletContext().setAttribute("systemOnline", online);
        return context.getSuccessForward();
    }

}
