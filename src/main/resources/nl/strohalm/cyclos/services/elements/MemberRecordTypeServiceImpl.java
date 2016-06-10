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
import java.util.List;

import nl.strohalm.cyclos.access.AdminSystemPermission;
import nl.strohalm.cyclos.dao.FetchDAO;
import nl.strohalm.cyclos.dao.groups.GroupDAO;
import nl.strohalm.cyclos.dao.members.MemberRecordTypeDAO;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.groups.AdminGroup;
import nl.strohalm.cyclos.entities.groups.BrokerGroup;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.records.MemberRecordType;
import nl.strohalm.cyclos.entities.members.records.MemberRecordTypeQuery;
import nl.strohalm.cyclos.exceptions.PermissionDeniedException;
import nl.strohalm.cyclos.services.fetch.FetchServiceLocal;
import nl.strohalm.cyclos.services.permissions.PermissionServiceLocal;
import nl.strohalm.cyclos.utils.access.LoggedUser;
import nl.strohalm.cyclos.utils.validation.ValidationException;
import nl.strohalm.cyclos.utils.validation.Validator;

/**
 * Implementation class for member record types service
 * @author Jefferson Magno
 */
public class MemberRecordTypeServiceImpl implements MemberRecordTypeServiceLocal {

    private FetchDAO               fetchDao;
    private GroupDAO               groupDao;
    private MemberRecordTypeDAO    memberRecordTypeDao;
    private FetchServiceLocal      fetchService;
    private PermissionServiceLocal permissionService;

    @Override
    public boolean canView(final MemberRecordType memberRecordType, final Element.Nature nature) {
        if (permissionService.hasPermission(AdminSystemPermission.MEMBER_RECORD_TYPES_VIEW)) {
            return true;
        }
        if (LoggedUser.isAdministrator()) {
            AdminGroup adminGroup = LoggedUser.group();
            adminGroup = fetchService.fetch(adminGroup, AdminGroup.Relationships.VIEW_ADMIN_RECORD_TYPES, AdminGroup.Relationships.VIEW_MEMBER_RECORD_TYPES);
            boolean isVisible = false;
            if (nature == null) {
                isVisible = adminGroup.getViewAdminRecordTypes().contains(memberRecordType)
                        || adminGroup.getViewMemberRecordTypes().contains(memberRecordType);
            } else if (nature.equals(Element.Nature.ADMIN)) {
                isVisible = adminGroup.getViewAdminRecordTypes().contains(memberRecordType);
            } else if (nature.equals(Element.Nature.MEMBER)) {
                isVisible = adminGroup.getViewMemberRecordTypes().contains(memberRecordType);
            } else {
                // in case of operator, but should be impossible
                return false;
            }
            return isVisible;
        } else if (LoggedUser.isBroker()) {
            BrokerGroup brokerGroup = LoggedUser.group();
            brokerGroup = fetchService.fetch(brokerGroup, BrokerGroup.Relationships.BROKER_MEMBER_RECORD_TYPES);
            return brokerGroup.getBrokerMemberRecordTypes().contains(memberRecordType);
        }
        return false;
    }

    private Validator getValidator() {
        final Validator validator = new Validator("memberRecordType");
        validator.property("name").required();
        validator.property("label").required();
        validator.property("layout").required();
        validator.property("editable").required();
        validator.property("showMenuItem").required();
        return validator;
    }

    @Override
    public MemberRecordType load(final Long id, final Relationship... fetch) {
        return memberRecordTypeDao.load(id, fetch);
    }

    @Override
    public int remove(final Long... ids) throws PermissionDeniedException {
        for (final Long id : ids) {
            final MemberRecordType memberRecordType = load(id, MemberRecordType.Relationships.VIEWABLE_BY_ADMIN_GROUPS, MemberRecordType.Relationships.VIEWABLE_BY_BROKER_GROUPS);
            removeFromViewerCollections(memberRecordType);
        }
        return memberRecordTypeDao.delete(ids);
    }

    private void removeFromViewerCollections(final MemberRecordType memberRecordType) {
        final Collection<AdminGroup> viewableByAdminGroups = memberRecordType.getViewableByAdminGroups();
        for (AdminGroup adminGroup : viewableByAdminGroups) {
            adminGroup = fetchDao.fetch(adminGroup, AdminGroup.Relationships.VIEW_ADMIN_RECORD_TYPES, AdminGroup.Relationships.VIEW_MEMBER_RECORD_TYPES);
            adminGroup.getViewAdminRecordTypes().remove(memberRecordType);
            adminGroup.getViewMemberRecordTypes().remove(memberRecordType);
        }

        final Collection<BrokerGroup> viewableByBrokerGroups = memberRecordType.getViewableByBrokerGroups();
        for (BrokerGroup brokerGroup : viewableByBrokerGroups) {
            brokerGroup = fetchDao.fetch(brokerGroup, BrokerGroup.Relationships.BROKER_MEMBER_RECORD_TYPES);
            brokerGroup.getBrokerMemberRecordTypes().remove(memberRecordType);
        }
    }

    @Override
    public MemberRecordType save(MemberRecordType memberRecordType) throws PermissionDeniedException {
        validate(memberRecordType);
        if (memberRecordType.isTransient()) {
            memberRecordType = memberRecordTypeDao.insert(memberRecordType);

            // Grant permissions to the admin group
            AdminGroup adminGroup = LoggedUser.group();
            adminGroup = fetchDao.fetch(adminGroup, AdminGroup.Relationships.VIEW_MEMBER_RECORD_TYPES, AdminGroup.Relationships.VIEW_ADMIN_RECORD_TYPES);

            // Grant view and create permissions
            adminGroup.getViewMemberRecordTypes().add(memberRecordType);
            adminGroup.getCreateMemberRecordTypes().add(memberRecordType);

            // If the member record type is editable, grant modify and delete permissions
            if (memberRecordType.isEditable()) {
                adminGroup.getModifyMemberRecordTypes().add(memberRecordType);
                adminGroup.getDeleteMemberRecordTypes().add(memberRecordType);
            }
            groupDao.update(adminGroup);

            return memberRecordType;
        } else {
            return memberRecordTypeDao.update(memberRecordType);
        }
    }

    @Override
    public List<MemberRecordType> search(final MemberRecordTypeQuery query) {
        return memberRecordTypeDao.search(query);
    }

    public void setFetchDao(final FetchDAO fetchDao) {
        this.fetchDao = fetchDao;
    }

    public void setFetchServiceLocal(final FetchServiceLocal fetchService) {
        this.fetchService = fetchService;
    }

    public void setGroupDao(final GroupDAO groupDao) {
        this.groupDao = groupDao;
    }

    public void setMemberRecordTypeDao(final MemberRecordTypeDAO memberRecordTypeDao) {
        this.memberRecordTypeDao = memberRecordTypeDao;
    }

    public void setPermissionServiceLocal(final PermissionServiceLocal permissionService) {
        this.permissionService = permissionService;
    }

    @Override
    public void validate(final MemberRecordType memberRecordType) throws ValidationException {
        getValidator().validate(memberRecordType);
    }

}
