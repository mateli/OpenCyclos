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
package nl.strohalm.cyclos.services.accounts.external;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import nl.strohalm.cyclos.access.AdminSystemPermission;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.external.ExternalTransfer;
import nl.strohalm.cyclos.entities.accounts.external.ExternalTransferAction;
import nl.strohalm.cyclos.entities.accounts.external.ExternalTransferQuery;
import nl.strohalm.cyclos.entities.exceptions.UnexpectedEntityException;
import nl.strohalm.cyclos.services.BaseServiceSecurity;

/**
 * Security implementation for {@link ExternalTransferService}
 * 
 * @author jcomas
 */
public class ExternalTransferServiceSecurity extends BaseServiceSecurity implements ExternalTransferService {

    private ExternalTransferServiceLocal externalTransferService;

    @Override
    public ExternalTransfer load(final Long id, final Relationship... fetch) {
        Relationship[] newFetch = addToFetch(fetch, ExternalTransfer.Relationships.MEMBER);
        ExternalTransfer et = externalTransferService.load(id, newFetch);
        if (et.getMember() != null) {
            permissionService.checkManages(et.getMember());
        }
        permissionService.permission().admin(AdminSystemPermission.EXTERNAL_ACCOUNTS_DETAILS).check();
        return et;
    }

    @Override
    public void performAction(final ExternalTransferAction action, final Long... ids) {
        switch (action) {
            case DELETE:
                permissionService.permission().admin(AdminSystemPermission.EXTERNAL_ACCOUNTS_MANAGE_PAYMENT).check();
                break;
            case MARK_AS_CHECKED:
                permissionService.permission().admin(AdminSystemPermission.EXTERNAL_ACCOUNTS_CHECK_PAYMENT).check();
                break;
            case MARK_AS_UNCHECKED:
                permissionService.permission().admin(AdminSystemPermission.EXTERNAL_ACCOUNTS_CHECK_PAYMENT).check();
                break;
            default:
                throw new IllegalArgumentException("Unexpected action " + action);
        }
        for (Long id : ids) {
            final ExternalTransfer externalTransfer = externalTransferService.load(id, ExternalTransfer.Relationships.MEMBER);
            if (externalTransfer.getMember() != null) {
                permissionService.checkManages(externalTransfer.getMember());
            }
        }
        externalTransferService.performAction(action, ids);
    }

    @Override
    public int process(final Collection<ProcessExternalTransferDTO> dtos) throws UnexpectedEntityException {
        permissionService.permission().admin(AdminSystemPermission.EXTERNAL_ACCOUNTS_PROCESS_PAYMENT).check();
        return externalTransferService.process(dtos);
    }

    @Override
    public ExternalTransfer save(final ExternalTransfer externalTransfer) {
        if (externalTransfer.getMember() != null) {
            permissionService.checkManages(externalTransfer.getMember());
        }
        permissionService.permission().admin(AdminSystemPermission.EXTERNAL_ACCOUNTS_MANAGE_PAYMENT).check();
        return externalTransferService.save(externalTransfer);
    }

    /**
     * There are no restrictions to see the external transfers of an external account but to have the appropriate permission for viewing the details
     * of the account.
     */
    @Override
    public List<ExternalTransfer> search(final ExternalTransferQuery query) {
        if (!permissionService.permission().admin(AdminSystemPermission.EXTERNAL_ACCOUNTS_DETAILS).hasPermission()) {
            return Collections.emptyList();
        }
        if (query.getMember() != null && !permissionService.manages(query.getMember())) {
            return Collections.emptyList();
        }
        return externalTransferService.search(query);
    }

    public void setExternalTransferServiceLocal(final ExternalTransferServiceLocal externalTransferService) {
        this.externalTransferService = externalTransferService;
    }

    @Override
    public void validate(final ExternalTransfer externalTransfer) {
        // Nothing to check.
        externalTransferService.validate(externalTransfer);
    }

}
