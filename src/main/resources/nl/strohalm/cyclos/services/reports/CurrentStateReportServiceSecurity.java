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
package nl.strohalm.cyclos.services.reports;

import java.util.Collection;

import nl.strohalm.cyclos.access.AdminSystemPermission;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.reports.CurrentStateReportVO;
import nl.strohalm.cyclos.exceptions.PermissionDeniedException;
import nl.strohalm.cyclos.services.BaseServiceSecurity;

import org.springframework.util.CollectionUtils;

/**
 * Security implementation for {@link CurrentStateReportService}
 * 
 * @author Rinke
 */
public class CurrentStateReportServiceSecurity extends BaseServiceSecurity implements CurrentStateReportService {

    private CurrentStateReportServiceLocal currentStateReportService;

    @Override
    public CurrentStateReportVO getCurrentStateReport(final CurrentStateReportParameters params) {
        permissionService.permission().admin(AdminSystemPermission.REPORTS_CURRENT).check();
        Collection<MemberGroup> memberGroups = permissionService.getManagedMemberGroups();
        if (CollectionUtils.isEmpty(memberGroups)) {
            throw new PermissionDeniedException();
        }
        params.setMemberGroups(memberGroups);
        return currentStateReportService.getCurrentStateReport(params);
    }

    public void setCurrentStateReportServiceLocal(final CurrentStateReportServiceLocal currentStateReportService) {
        this.currentStateReportService = currentStateReportService;
    }

}
