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
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseFormAction;
import nl.strohalm.cyclos.entities.access.Channel;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferType;
import nl.strohalm.cyclos.entities.groups.AdminGroup;
import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.entities.groups.GroupQuery;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.services.ServiceClient;
import nl.strohalm.cyclos.entities.services.ServiceOperation;
import nl.strohalm.cyclos.services.services.ServiceClientService;
import nl.strohalm.cyclos.utils.ActionHelper;
import nl.strohalm.cyclos.utils.RequestHelper;
import nl.strohalm.cyclos.utils.binding.BeanBinder;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.binding.PropertyBinder;
import nl.strohalm.cyclos.utils.binding.SimpleCollectionBinder;
import nl.strohalm.cyclos.utils.conversion.IdConverter;

import org.apache.struts.action.ActionForward;

/**
 * Action used to edit an web services client
 * @author luis
 */
public class EditServiceClientAction extends BaseFormAction {

    private ServiceClientService      serviceClientService;
    private DataBinder<ServiceClient> dataBinder;

    @Inject
    public void setServiceClientService(final ServiceClientService serviceClientService) {
        this.serviceClientService = serviceClientService;
    }

    @Override
    protected ActionForward handleSubmit(final ActionContext context) throws Exception {
        final EditServiceClientForm form = context.getForm();
        final ServiceClient client = getDataBinder().readFromString(form.getServiceClient());
        final boolean isInsert = client.isTransient();
        serviceClientService.save(client);
        context.sendMessage(isInsert ? "serviceClient.inserted" : "serviceClient.modified");
        return ActionHelper.redirectWithParam(context.getRequest(), context.getSuccessForward(), "clientId", client.getId());
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void prepareForm(final ActionContext context) throws Exception {
        final HttpServletRequest request = context.getRequest();
        final EditServiceClientForm form = context.getForm();
        final long id = form.getClientId();
        ServiceClient client;
        if (id <= 0) {
            client = new ServiceClient();
        } else {
            client = serviceClientService.load(id, ServiceClient.Relationships.MEMBER, ServiceClient.Relationships.PERMISSIONS);
            final List<TransferType> doPaymentTypes = serviceClientService.listPossibleDoPaymentTypes(client);
            final List<TransferType> receivePaymentTypes = serviceClientService.listPossibleReceivePaymentTypes(client);
            request.setAttribute("doPaymentTypes", doPaymentTypes);
            request.setAttribute("receivePaymentTypes", receivePaymentTypes);
            request.setAttribute("chargebackPaymentTypes", client.getMember() == null ? doPaymentTypes : receivePaymentTypes);
        }
        final GroupQuery groupQuery = new GroupQuery();
        groupQuery.setManagedBy((AdminGroup) context.getGroup());
        groupQuery.setNatures(Group.Nature.BROKER, Group.Nature.MEMBER);
        final List<MemberGroup> memberGroups = (List<MemberGroup>) groupService.search(groupQuery);

        getDataBinder().writeAsString(form.getServiceClient(), client);
        request.setAttribute("serviceClient", client);
        request.setAttribute("channels", serviceClientService.listPossibleChannels());
        request.setAttribute("memberGroups", memberGroups);
        RequestHelper.storeEnum(request, ServiceOperation.class, "operations");
    }

    @Override
    protected void validateForm(final ActionContext context) {
        final EditServiceClientForm form = context.getForm();
        final ServiceClient client = getDataBinder().readFromString(form.getServiceClient());
        serviceClientService.validate(client);
    }

    private DataBinder<ServiceClient> getDataBinder() {
        if (dataBinder == null) {
            final BeanBinder<ServiceClient> binder = BeanBinder.instance(ServiceClient.class);
            binder.registerBinder("id", PropertyBinder.instance(Long.class, "id", IdConverter.instance()));
            binder.registerBinder("name", PropertyBinder.instance(String.class, "name"));
            binder.registerBinder("hostname", PropertyBinder.instance(String.class, "hostname"));
            binder.registerBinder("username", PropertyBinder.instance(String.class, "username"));
            binder.registerBinder("password", PropertyBinder.instance(String.class, "password"));
            binder.registerBinder("credentialsRequired", PropertyBinder.instance(Boolean.TYPE, "credentialsRequired"));
            binder.registerBinder("ignoreRegistrationValidations", PropertyBinder.instance(Boolean.TYPE, "ignoreRegistrationValidations"));
            binder.registerBinder("member", PropertyBinder.instance(Member.class, "member"));
            binder.registerBinder("channel", PropertyBinder.instance(Channel.class, "channel"));
            binder.registerBinder("permissions", SimpleCollectionBinder.instance(ServiceOperation.class, Set.class, "permissions"));
            binder.registerBinder("doPaymentTypes", SimpleCollectionBinder.instance(TransferType.class, Set.class, "doPaymentTypes"));
            binder.registerBinder("receivePaymentTypes", SimpleCollectionBinder.instance(TransferType.class, Set.class, "receivePaymentTypes"));
            binder.registerBinder("chargebackPaymentTypes", SimpleCollectionBinder.instance(TransferType.class, Set.class, "chargebackPaymentTypes"));
            binder.registerBinder("manageGroups", SimpleCollectionBinder.instance(MemberGroup.class, Set.class, "manageGroups"));
            dataBinder = binder;
        }
        return dataBinder;
    }

}
