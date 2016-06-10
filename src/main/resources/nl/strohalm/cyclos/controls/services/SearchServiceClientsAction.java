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
package nl.strohalm.cyclos.controls.services;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseQueryAction;
import nl.strohalm.cyclos.entities.services.ServiceClient;
import nl.strohalm.cyclos.entities.services.ServiceClientQuery;
import nl.strohalm.cyclos.entities.services.ServiceOperation;
import nl.strohalm.cyclos.services.services.ServiceClientService;
import nl.strohalm.cyclos.utils.RequestHelper;
import nl.strohalm.cyclos.utils.binding.BeanBinder;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.binding.DataBinderHelper;
import nl.strohalm.cyclos.utils.query.QueryParameters;

/**
 * Action used to search web services clients
 * @author luis
 */
public class SearchServiceClientsAction extends BaseQueryAction {

    private ServiceClientService           serviceClientService;
    private DataBinder<ServiceClientQuery> dataBinder;

    @Inject
    public void setServiceClientService(final ServiceClientService serviceClientService) {
        this.serviceClientService = serviceClientService;
    }

    @Override
    protected void executeQuery(final ActionContext context, final QueryParameters queryParameters) {
        final ServiceClientQuery query = (ServiceClientQuery) queryParameters;
        final List<ServiceClient> clients = serviceClientService.search(query);
        context.getRequest().setAttribute("serviceClients", clients);
    }

    @Override
    protected QueryParameters prepareForm(final ActionContext context) {
        final HttpServletRequest request = context.getRequest();
        final SearchServiceClientsForm form = context.getForm();
        final ServiceClientQuery query = getDataBinder().readFromString(form.getQuery());
        RequestHelper.storeEnum(request, ServiceOperation.class, "operations");
        return query;
    }

    @Override
    protected boolean willExecuteQuery(final ActionContext context, final QueryParameters queryParameters) throws Exception {
        return true;
    }

    private DataBinder<ServiceClientQuery> getDataBinder() {
        if (dataBinder == null) {
            final BeanBinder<ServiceClientQuery> binder = BeanBinder.instance(ServiceClientQuery.class);
            binder.registerBinder("pageParameters", DataBinderHelper.pageBinder());
            dataBinder = binder;
        }
        return dataBinder;
    }
}
