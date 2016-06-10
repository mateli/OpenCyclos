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

import java.util.List;

import nl.strohalm.cyclos.access.AdminSystemPermission;
import nl.strohalm.cyclos.entities.alerts.Alert;
import nl.strohalm.cyclos.entities.alerts.AlertQuery;
import nl.strohalm.cyclos.entities.alerts.MemberAlert;
import nl.strohalm.cyclos.entities.alerts.SystemAlert;
import nl.strohalm.cyclos.exceptions.PermissionDeniedException;
import nl.strohalm.cyclos.services.BaseServiceSecurity;
import nl.strohalm.cyclos.utils.access.PermissionHelper;

/**
 * Security implementation for {@link AlertService}
 * 
 * @author Rinke
 */
public class AlertServiceSecurity extends BaseServiceSecurity implements AlertService {

    private AlertServiceLocal alertService;

    @Override
    public int removeAlerts(final Long... ids) {
        boolean system = false;
        boolean member = false;
        // loop the ids to see what types they are
        for (Long id : ids) {
            Alert alert = alertService.load(id);
            if (alert instanceof MemberAlert) {
                permissionService.checkManages(((MemberAlert) alert).getMember());
                member = true;
            }
            if (alert instanceof SystemAlert) {
                system = true;
            }
        }
        if (member) {
            permissionService.permission().admin(AdminSystemPermission.ALERTS_MANAGE_MEMBER_ALERTS).check();
        }
        if (system) {
            permissionService.permission().admin(AdminSystemPermission.ALERTS_MANAGE_SYSTEM_ALERTS).check();
        }
        return alertService.removeAlerts(ids);
    }

    @Override
    public List<? extends Alert> search(final AlertQuery queryParameters) {
        if (queryParameters.getType() == Alert.Type.MEMBER) {
            permissionService.permission().admin(AdminSystemPermission.ALERTS_VIEW_MEMBER_ALERTS).check();
        } else if (queryParameters.getType() == Alert.Type.SYSTEM) {
            permissionService.permission().admin(AdminSystemPermission.ALERTS_VIEW_SYSTEM_ALERTS).check();
        } else { // null or unknown type
            throw new PermissionDeniedException();
        }
        if (queryParameters.getMember() != null) {
            permissionService.checkManages(queryParameters.getMember());
        }
        queryParameters.setGroups(PermissionHelper.checkSelection(permissionService.getVisibleMemberGroups(), queryParameters.getGroups()));
        return alertService.search(queryParameters);
    }

    public void setAlertServiceLocal(final AlertServiceLocal alertService) {
        this.alertService = alertService;
    }

}
