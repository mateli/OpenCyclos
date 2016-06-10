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

import java.util.Collections;
import java.util.List;

import nl.strohalm.cyclos.access.AdminSystemPermission;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.external.ExternalAccount;
import nl.strohalm.cyclos.entities.accounts.external.ExternalAccountDetailsVO;
import nl.strohalm.cyclos.services.BaseServiceSecurity;

/**
 * Security implementation for {@link ExternalAccountService}
 * 
 * @author jcomas
 */
public class ExternalAccountServiceSecurity extends BaseServiceSecurity implements ExternalAccountService {

    private ExternalAccountServiceLocal externalAccountService;

    @Override
    public List<ExternalAccountDetailsVO> externalAccountOverview() {
        permissionService.permission().admin(AdminSystemPermission.EXTERNAL_ACCOUNTS_DETAILS).check();
        return externalAccountService.externalAccountOverview();
    }

    @Override
    public ExternalAccount load(final Long id, final Relationship... fetch) {
        permissionService.permission().admin(AdminSystemPermission.EXTERNAL_ACCOUNTS_DETAILS, AdminSystemPermission.EXTERNAL_ACCOUNTS_VIEW).check();
        return externalAccountService.load(id, fetch);
    }

    @Override
    public int remove(final Long... ids) {
        permissionService.permission().admin(AdminSystemPermission.EXTERNAL_ACCOUNTS_MANAGE).check();
        return externalAccountService.remove(ids);
    }

    @Override
    public ExternalAccount save(final ExternalAccount externalAccount) {
        permissionService.permission().admin(AdminSystemPermission.EXTERNAL_ACCOUNTS_MANAGE).check();
        return externalAccountService.save(externalAccount);
    }

    @Override
    public List<ExternalAccount> search() {
        if (!permissionService.permission().admin(AdminSystemPermission.EXTERNAL_ACCOUNTS_VIEW).hasPermission()) {
            return Collections.emptyList();
        }
        return externalAccountService.search();
    }

    public void setExternalAccountServiceLocal(final ExternalAccountServiceLocal externalAccountService) {
        this.externalAccountService = externalAccountService;
    }

    @Override
    public void validate(final ExternalAccount externalAccount) {
        // Nothing to check.
        externalAccountService.validate(externalAccount);
    }

}
