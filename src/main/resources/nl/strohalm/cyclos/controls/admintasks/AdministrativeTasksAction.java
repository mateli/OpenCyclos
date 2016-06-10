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

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.access.AdminSystemPermission;
import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseAction;
import nl.strohalm.cyclos.entities.IndexStatus;
import nl.strohalm.cyclos.entities.Indexable;
import nl.strohalm.cyclos.exceptions.PermissionDeniedException;
import nl.strohalm.cyclos.services.application.ApplicationService;
import nl.strohalm.cyclos.utils.ClassHelper;

import org.apache.struts.action.ActionForward;

/**
 * Action used to assemble the administrative tasks page
 * 
 * @author luis
 */
public class AdministrativeTasksAction extends BaseAction {

    private ApplicationService applicationService;

    @Inject
    public void setApplicationService(final ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @Override
    protected ActionForward executeAction(final ActionContext context) throws Exception {
        final HttpServletRequest request = context.getRequest();

        final boolean canViewIndexes = permissionService.hasPermission(AdminSystemPermission.TASKS_MANAGE_INDEXES);
        if (canViewIndexes) {
            final Map<Class<? extends Indexable>, IndexStatus> indexesStatus = applicationService.getFullTextIndexesStatus();
            final Map<String, IndexStatus> indexesStatusAsString = new LinkedHashMap<String, IndexStatus>();
            boolean allOptimized = true;
            for (final Map.Entry<Class<? extends Indexable>, IndexStatus> entry : indexesStatus.entrySet()) {
                final String name = ClassHelper.getClassName(entry.getKey());
                final IndexStatus status = entry.getValue();
                final boolean optimized = status == IndexStatus.ACTIVE;
                if (!optimized) {
                    allOptimized = false;
                }
                indexesStatusAsString.put(name, status);
            }
            request.setAttribute("allOptimized", allOptimized);
            request.setAttribute("indexesStatus", indexesStatusAsString);
        }
        request.setAttribute("canViewIndexes", canViewIndexes);

        final boolean canManageOnlineState = permissionService.hasPermission(AdminSystemPermission.TASKS_ONLINE_STATE);
        if (canManageOnlineState) {
            final boolean systemOnline = applicationService.isOnline();
            request.setAttribute("systemOnline", systemOnline);
        }
        request.setAttribute("canManageOnlineState", canManageOnlineState);

        if (!canViewIndexes && !canManageOnlineState) {
            throw new PermissionDeniedException();
        }

        return context.getInputForward();
    }

}
