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
package nl.strohalm.cyclos.services.alerts;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

import nl.strohalm.cyclos.access.AdminSystemPermission;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.alerts.ErrorLogEntry;
import nl.strohalm.cyclos.entities.alerts.ErrorLogEntryQuery;
import nl.strohalm.cyclos.services.BaseServiceSecurity;

/**
 * Security implementation for {@link ErrorLogService}
 * 
 * @author jcomas
 */
public class ErrorLogServiceSecurity extends BaseServiceSecurity implements ErrorLogService {

    private ErrorLogServiceLocal errorLogService;

    @Override
    public Future<ErrorLogEntry> insert(final Throwable t, final String path, final Map<String, ?> parameters) {
        // Nothing to check
        return errorLogService.insert(t, path, parameters);
    }

    @Override
    public ErrorLogEntry load(final Long id, final Relationship... fetch) {
        permissionService.permission().admin(AdminSystemPermission.ERROR_LOG_VIEW).check();
        return errorLogService.load(id, fetch);
    }

    @Override
    public int remove(final Long... ids) {
        permissionService.permission().admin(AdminSystemPermission.ERROR_LOG_MANAGE).check();
        return errorLogService.remove(ids);
    }

    @Override
    public List<ErrorLogEntry> search(final ErrorLogEntryQuery query) {
        if (!permissionService.permission().admin(AdminSystemPermission.ERROR_LOG_VIEW).hasPermission()) {
            return Collections.emptyList();
        }
        return errorLogService.search(query);
    }

    public void setErrorLogServiceLocal(final ErrorLogServiceLocal errorLogService) {
        this.errorLogService = errorLogService;
    }

}
