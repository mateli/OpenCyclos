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
package nl.strohalm.cyclos.controls.admins;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseAjaxAction;
import nl.strohalm.cyclos.services.application.ApplicationService;
import nl.strohalm.cyclos.services.application.ApplicationStatusVO;
import nl.strohalm.cyclos.utils.binding.BeanBinder;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.binding.PropertyBinder;

/**
 * Action used to retrieve the application status via Ajax
 * @author luis
 */
public class GetApplicationStatusAjaxAction extends BaseAjaxAction {

    private ApplicationService              applicationService;
    private DataBinder<ApplicationStatusVO> dataBinder;

    public ApplicationService getApplicationService() {
        return applicationService;
    }

    public DataBinder<ApplicationStatusVO> getDataBinder() {
        if (dataBinder == null) {
            final BeanBinder<ApplicationStatusVO> binder = BeanBinder.instance(ApplicationStatusVO.class);
            binder.registerBinder("connectedAdmins", PropertyBinder.instance(Integer.TYPE, "connectedAdmins"));
            binder.registerBinder("connectedMembers", PropertyBinder.instance(Integer.TYPE, "connectedMembers"));
            binder.registerBinder("connectedBrokers", PropertyBinder.instance(Integer.TYPE, "connectedBrokers"));
            binder.registerBinder("connectedOperators", PropertyBinder.instance(Integer.TYPE, "connectedOperators"));
            binder.registerBinder("memberAlerts", PropertyBinder.instance(Integer.TYPE, "memberAlerts"));
            binder.registerBinder("systemAlerts", PropertyBinder.instance(Integer.TYPE, "systemAlerts"));
            binder.registerBinder("errors", PropertyBinder.instance(Integer.TYPE, "errors"));
            binder.registerBinder("uptimeDays", PropertyBinder.instance(Integer.TYPE, "uptimeDays"));
            binder.registerBinder("uptimeHours", PropertyBinder.instance(Integer.TYPE, "uptimeHours"));
            binder.registerBinder("openInvoices", PropertyBinder.instance(Integer.TYPE, "openInvoices"));
            binder.registerBinder("unreadMessages", PropertyBinder.instance(Integer.TYPE, "unreadMessages"));
            dataBinder = binder;
        }
        return dataBinder;
    }

    @Inject
    public void setApplicationService(final ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @Override
    protected ContentType contentType() {
        return ContentType.JSON;
    }

    @Override
    protected void renderContent(final ActionContext context) throws Exception {
        final ApplicationStatusVO status = applicationService.getApplicationStatus();
        final String json = getDataBinder().readAsString(status);
        responseHelper.writeJSON(context.getResponse(), json);
    }
}
