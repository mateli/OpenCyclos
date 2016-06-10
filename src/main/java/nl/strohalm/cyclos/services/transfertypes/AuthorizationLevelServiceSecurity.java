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
package nl.strohalm.cyclos.services.transfertypes;

import nl.strohalm.cyclos.access.AdminSystemPermission;
import nl.strohalm.cyclos.entities.accounts.transactions.AuthorizationLevel;
import nl.strohalm.cyclos.services.BaseServiceSecurity;
import nl.strohalm.cyclos.utils.validation.ValidationException;

/**
 * Security implementation for {@link AuthorizationLevelService}
 * 
 * @author Rinke
 */
public class AuthorizationLevelServiceSecurity extends BaseServiceSecurity implements AuthorizationLevelService {

    private AuthorizationLevelServiceLocal authorizationLevelService;

    @Override
    public int remove(final Long... ids) {
        permissionService.permission().admin(AdminSystemPermission.ACCOUNTS_MANAGE).check();
        return authorizationLevelService.remove(ids);
    }

    @Override
    public AuthorizationLevel save(final AuthorizationLevel level) {
        permissionService.permission().admin(AdminSystemPermission.ACCOUNTS_MANAGE).check();
        return authorizationLevelService.save(level);
    }

    public void setAuthorizationLevelServiceLocal(final AuthorizationLevelServiceLocal authorizationLevelService) {
        this.authorizationLevelService = authorizationLevelService;
    }

    @Override
    public void validate(final AuthorizationLevel level) throws ValidationException {
        // no permissions needed for validation
        authorizationLevelService.validate(level);
    }

}
