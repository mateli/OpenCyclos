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
package nl.strohalm.cyclos.services.services;

import java.util.List;

import nl.strohalm.cyclos.access.AdminSystemPermission;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.access.Channel;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferType;
import nl.strohalm.cyclos.entities.services.ServiceClient;
import nl.strohalm.cyclos.entities.services.ServiceClientQuery;
import nl.strohalm.cyclos.exceptions.PermissionDeniedException;
import nl.strohalm.cyclos.services.BaseServiceSecurity;

/**
 * Security implementation for {@link ServiceClientService}
 * 
 * @author jcomas
 */
public class ServiceClientServiceSecurity extends BaseServiceSecurity implements ServiceClientService {

    private ServiceClientServiceLocal serviceClientService;

    @Override
    public int delete(final Long... ids) {
        permissionService.permission()
                .admin(AdminSystemPermission.SERVICE_CLIENTS_MANAGE)
                .check();
        return serviceClientService.delete(ids);
    }

    @Override
    public List<Channel> listPossibleChannels() {
        permissionService.permission()
                .admin(AdminSystemPermission.SERVICE_CLIENTS_VIEW)
                .check();
        return serviceClientService.listPossibleChannels();
    }

    @Override
    public List<TransferType> listPossibleDoPaymentTypes(final ServiceClient client) {
        permissionService.permission()
                .admin(AdminSystemPermission.SERVICE_CLIENTS_VIEW)
                .check();
        return serviceClientService.listPossibleDoPaymentTypes(client);
    }

    @Override
    public List<TransferType> listPossibleReceivePaymentTypes(final ServiceClient client) {
        permissionService.permission()
                .admin(AdminSystemPermission.SERVICE_CLIENTS_VIEW)
                .check();
        return serviceClientService.listPossibleReceivePaymentTypes(client);
    }

    @Override
    public ServiceClient load(final Long id, final Relationship... fetch) {
        permissionService.permission()
                .admin(AdminSystemPermission.SERVICE_CLIENTS_VIEW)
                .check();
        return serviceClientService.load(id, fetch);
    }

    @Override
    public ServiceClient save(final ServiceClient host) {
        permissionService.permission()
                .admin(AdminSystemPermission.SERVICE_CLIENTS_MANAGE)
                .check();
        return serviceClientService.save(host);
    }

    @Override
    public List<ServiceClient> search(final ServiceClientQuery query) {
        if (!permissionService.hasPermission(AdminSystemPermission.SERVICE_CLIENTS_VIEW)) {
            throw new PermissionDeniedException();
        }
        return serviceClientService.search(query);
    }

    public void setServiceClientServiceLocal(final ServiceClientServiceLocal serviceClientService) {
        this.serviceClientService = serviceClientService;
    }

    @Override
    public void validate(final ServiceClient client) {
        // Nothing to check.
        serviceClientService.validate(client);
    }
}
