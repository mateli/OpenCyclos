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
package nl.strohalm.cyclos.services.elements;

import java.util.List;

import nl.strohalm.cyclos.access.AdminAdminPermission;
import nl.strohalm.cyclos.access.AdminMemberPermission;
import nl.strohalm.cyclos.access.MemberPermission;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.Operator;
import nl.strohalm.cyclos.entities.members.remarks.BrokerRemark;
import nl.strohalm.cyclos.entities.members.remarks.GroupRemark;
import nl.strohalm.cyclos.exceptions.PermissionDeniedException;
import nl.strohalm.cyclos.services.BaseServiceSecurity;
import nl.strohalm.cyclos.utils.access.LoggedUser;

/**
 * Security implementation for {@link RemarkService}
 * 
 * @author Rinke
 */
public class RemarkServiceSecurity extends BaseServiceSecurity implements RemarkService {

    private RemarkServiceLocal remarkService;

    @Override
    public List<BrokerRemark> listBrokerRemarksFor(final Element subject) {
        // called by ChangeBrokerAction
        permissionService.permission(subject)
                .admin(AdminMemberPermission.BROKERINGS_CHANGE_BROKER)
                .check();
        return remarkService.listBrokerRemarksFor(subject);
    }

    @Override
    public List<GroupRemark> listGroupChangeHistory(final Element subject) {
        // called by ChangeElementGroupAction
        permissionService.permission(subject)
                .admin(AdminAdminPermission.ADMINS_CHANGE_GROUP, AdminMemberPermission.MEMBERS_CHANGE_GROUP)
                .member(MemberPermission.OPERATORS_MANAGE)
                .check();
        // members can only view the list of changes of a managed operator's group.
        if (LoggedUser.isMember() && !(subject instanceof Operator)) {
            throw new PermissionDeniedException();
        }
        return remarkService.listGroupChangeHistory(subject);
    }

    public void setRemarkServiceLocal(final RemarkServiceLocal remarkService) {
        this.remarkService = remarkService;
    }

}
