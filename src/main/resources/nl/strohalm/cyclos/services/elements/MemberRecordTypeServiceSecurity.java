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

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import nl.strohalm.cyclos.access.AdminSystemPermission;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.groups.AdminGroup;
import nl.strohalm.cyclos.entities.groups.BrokerGroup;
import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.entities.members.records.MemberRecordType;
import nl.strohalm.cyclos.entities.members.records.MemberRecordTypeQuery;
import nl.strohalm.cyclos.exceptions.PermissionDeniedException;
import nl.strohalm.cyclos.services.BaseServiceSecurity;
import nl.strohalm.cyclos.utils.access.LoggedUser;
import nl.strohalm.cyclos.utils.access.PermissionHelper;
import nl.strohalm.cyclos.utils.validation.ValidationException;

/**
 * Security implementation for {@link MemberRecordTypeService}
 * 
 * @author jcomas
 */
public class MemberRecordTypeServiceSecurity extends BaseServiceSecurity implements MemberRecordTypeService {

    private MemberRecordTypeServiceLocal memberRecordTypeService;

    @Override
    public MemberRecordType load(final Long id, final Relationship... fetch) {
        MemberRecordType memberRecordType = memberRecordTypeService.load(id, fetch);
        if (!memberRecordTypeService.canView(memberRecordType, null)) {
            throw new PermissionDeniedException();
        }
        return memberRecordType;
    }

    @Override
    public int remove(final Long... id) throws PermissionDeniedException {
        permissionService.permission().admin(AdminSystemPermission.MEMBER_RECORD_TYPES_MANAGE).check();
        return memberRecordTypeService.remove(id);
    }

    @Override
    public MemberRecordType save(final MemberRecordType memberRecordType) throws PermissionDeniedException {
        permissionService.permission().admin(AdminSystemPermission.MEMBER_RECORD_TYPES_MANAGE).check();
        return memberRecordTypeService.save(memberRecordType);
    }

    @Override
    public List<MemberRecordType> search(final MemberRecordTypeQuery query) {
        if (!applyRestrictions(query)) {
            return Collections.emptyList();
        }
        return memberRecordTypeService.search(query);
    }

    public void setMemberRecordTypeServiceLocal(final MemberRecordTypeServiceLocal memberRecordTypeService) {
        this.memberRecordTypeService = memberRecordTypeService;
    }

    @Override
    public void validate(final MemberRecordType memberRecordType) throws ValidationException {
        // No permissions to check.
        memberRecordTypeService.validate(memberRecordType);
    }

    private boolean applyRestrictions(final MemberRecordTypeQuery query) {
        if (!permissionService.hasPermission(AdminSystemPermission.MEMBER_RECORD_TYPES_VIEW)) {
            if (LoggedUser.isAdministrator()) {
                query.setViewableByAdminGroup((AdminGroup) LoggedUser.group());
            } else if (LoggedUser.isBroker()) {
                query.setViewableByBrokerGroup((BrokerGroup) LoggedUser.group());
            }
            Collection<Group> groups = PermissionHelper.checkSelection(permissionService.getAllVisibleGroups(), query.getGroups());
            if (groups == null) {
                return false;
            }
            query.setGroups(groups);
        }
        return true;
    }

}
