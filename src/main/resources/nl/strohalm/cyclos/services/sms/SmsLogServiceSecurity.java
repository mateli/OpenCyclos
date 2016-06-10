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
package nl.strohalm.cyclos.services.sms;

import java.util.Collection;
import java.util.List;

import nl.strohalm.cyclos.access.AdminMemberPermission;
import nl.strohalm.cyclos.access.AdminSystemPermission;
import nl.strohalm.cyclos.access.BrokerPermission;
import nl.strohalm.cyclos.access.MemberPermission;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.sms.SmsLog;
import nl.strohalm.cyclos.entities.sms.SmsLogQuery;
import nl.strohalm.cyclos.entities.sms.SmsLogReportQuery;
import nl.strohalm.cyclos.entities.sms.SmsLogReportVO;
import nl.strohalm.cyclos.entities.sms.SmsType;
import nl.strohalm.cyclos.exceptions.PermissionDeniedException;
import nl.strohalm.cyclos.services.BaseServiceSecurity;
import nl.strohalm.cyclos.utils.access.PermissionHelper;
import nl.strohalm.cyclos.utils.validation.ValidationException;

import org.apache.commons.collections.CollectionUtils;

/**
 * Security implementation for {@link SmsLogService}
 * 
 * @author jcomas
 */
public class SmsLogServiceSecurity extends BaseServiceSecurity implements SmsLogService {

    private SmsLogServiceLocal smsLogService;

    @Override
    public SmsLogReportVO getSmsLogReport(final SmsLogReportQuery query) {
        permissionService.permission()
                .admin(AdminSystemPermission.REPORTS_SMS_LOGS)
                .check();

        // Ensure that all groups are managed
        Collection<MemberGroup> allowed = permissionService.getManagedMemberGroups();
        if (CollectionUtils.isEmpty(allowed)) {
            throw new PermissionDeniedException();
        }

        query.setMemberGroups(PermissionHelper.checkSelection(allowed, query.getMemberGroups()));

        return smsLogService.getSmsLogReport(query);
    }

    @Override
    public Collection<SmsType> getSmsTypes() {
        permissionService.permission()
                .admin(AdminSystemPermission.REPORTS_SMS_LOGS)
                .check();

        return smsLogService.getSmsTypes();
    }

    @Override
    public Collection<SmsType> loadSmsTypes(final Collection<Long> ids) {
        permissionService.permission()
                .admin(AdminSystemPermission.REPORTS_SMS_LOGS)
                .check();

        return smsLogService.loadSmsTypes(ids);
    }

    @Override
    public List<SmsLog> search(final SmsLogQuery query) {
        if (query.getMember() == null) {
            throw new ValidationException();
        }

        permissionService.permission(query.getMember())
                .admin(AdminMemberPermission.SMS_VIEW)
                .broker(BrokerPermission.MEMBER_SMS_VIEW)
                .member(MemberPermission.SMS_VIEW)
                .check();

        return smsLogService.search(query);
    }

    public void setSmsLogServiceLocal(final SmsLogServiceLocal smsLogService) {
        this.smsLogService = smsLogService;
    }
}
