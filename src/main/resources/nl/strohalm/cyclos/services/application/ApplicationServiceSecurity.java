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
package nl.strohalm.cyclos.services.application;

import java.util.Map;

import nl.strohalm.cyclos.access.AdminSystemPermission;
import nl.strohalm.cyclos.entities.IndexStatus;
import nl.strohalm.cyclos.entities.Indexable;
import nl.strohalm.cyclos.services.BaseServiceSecurity;

/**
 * Security implementation for {@link ApplicationService}
 * 
 * @author Rinke
 */
public class ApplicationServiceSecurity extends BaseServiceSecurity implements ApplicationService {

    private ApplicationServiceLocal applicationService;

    @Override
    public ApplicationStatusVO getApplicationStatus() {
        if (permissionService.hasPermission(AdminSystemPermission.STATUS_VIEW)) {
            return applicationService.getApplicationStatus();
        }
        return null;
    }

    @Override
    public String getCyclosVersion() {
        return applicationService.getCyclosVersion();
    }

    @Override
    public Map<Class<? extends Indexable>, IndexStatus> getFullTextIndexesStatus() {
        checkManageIndexes();
        return applicationService.getFullTextIndexesStatus();
    }

    @Override
    public void initialize() {
        checkIsSystem();
        applicationService.initialize();
    }

    @Override
    public boolean isOnline() {
        // The isOnline is used even to display for guests, so no check is needed
        return applicationService.isOnline();
    }

    @Override
    public void rebuildIndexes(final Class<? extends Indexable> entityType) {
        checkManageIndexes();
        applicationService.rebuildIndexes(entityType);
    }

    public void setApplicationServiceLocal(final ApplicationServiceLocal applicationService) {
        this.applicationService = applicationService;
    }

    @Override
    public void setOnline(final boolean online) {
        checkOnlineState();
        applicationService.setOnline(online);
    }

    @Override
    public void shutdown() {
        checkIsSystem();
        applicationService.shutdown();
    }

    private void checkManageIndexes() {
        permissionService.permission().admin(AdminSystemPermission.TASKS_MANAGE_INDEXES).check();
    }

    private void checkOnlineState() {
        permissionService.permission().admin(AdminSystemPermission.TASKS_ONLINE_STATE).check();
    }

}
