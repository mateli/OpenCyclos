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
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.groups.AdminGroup;
import nl.strohalm.cyclos.entities.groups.BrokerGroup;
import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.entities.members.Administrator;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.members.records.AbstractMemberRecordQuery;
import nl.strohalm.cyclos.entities.members.records.FullTextMemberRecordQuery;
import nl.strohalm.cyclos.entities.members.records.MemberRecord;
import nl.strohalm.cyclos.entities.members.records.MemberRecordQuery;
import nl.strohalm.cyclos.entities.members.records.MemberRecordType;
import nl.strohalm.cyclos.exceptions.PermissionDeniedException;
import nl.strohalm.cyclos.services.BaseServiceSecurity;
import nl.strohalm.cyclos.utils.access.LoggedUser;
import nl.strohalm.cyclos.utils.access.PermissionHelper;
import nl.strohalm.cyclos.utils.validation.ValidationException;

/**
 * Security implementation for {@link MemberRecordService}
 * 
 * @author jcomas
 */
public class MemberRecordServiceSecurity extends BaseServiceSecurity implements MemberRecordService {

    private MemberRecordServiceLocal     memberRecordService;
    private MemberRecordTypeServiceLocal memberRecordTypeService;

    @Override
    public Map<MemberRecordType, Integer> countByType(final Element element) {
        permissionService.checkManages(element);
        Map<MemberRecordType, Integer> map = memberRecordService.countByType(element);

        // Remove all the record types that the user isn't allowed to see.
        for (final Iterator<MemberRecordType> iter = map.keySet().iterator(); iter.hasNext();) {
            final MemberRecordType mrt = iter.next();
            if (!memberRecordTypeService.canView(mrt, element.getNature())) {
                iter.remove();
            }
        }
        return map;
    }

    @Override
    public List<MemberRecord> fullTextSearch(final FullTextMemberRecordQuery query) {
        if (!applyQueryRestrictions(query)) {
            return Collections.emptyList();
        }
        return memberRecordService.fullTextSearch(query);
    }

    @Override
    public MemberRecord insert(final MemberRecord memberRecord) throws PermissionDeniedException {
        checkCreate(memberRecord);
        return memberRecordService.insert(memberRecord);
    }

    @Override
    public MemberRecord load(final Long id, final Relationship... fetch) {
        checkView(id);
        return memberRecordService.load(id, fetch);
    }

    @Override
    public int remove(final Long... ids) throws PermissionDeniedException {
        checkRemove(ids);
        return memberRecordService.remove(ids);
    }

    @Override
    public List<MemberRecord> search(final MemberRecordQuery query) {
        if (!applyQueryRestrictions(query)) {
            return Collections.emptyList();
        }
        return memberRecordService.search(query);
    }

    public void setMemberRecordServiceLocal(final MemberRecordServiceLocal memberRecordService) {
        this.memberRecordService = memberRecordService;
    }

    public void setMemberRecordTypeServiceLocal(final MemberRecordTypeServiceLocal memberRecordTypeService) {
        this.memberRecordTypeService = memberRecordTypeService;
    }

    @Override
    public MemberRecord update(final MemberRecord memberRecord) throws PermissionDeniedException {
        checkUpdate(memberRecord);
        return memberRecordService.update(memberRecord);
    }

    @Override
    public void validate(final MemberRecord memberRecord) throws ValidationException {
        // Nothing to check
        memberRecordService.validate(memberRecord);
    }

    private boolean applyQueryRestrictions(final AbstractMemberRecordQuery query) {
        final MemberRecordType type = query.getType();
        if (type == null) {
            return false;
        }

        // Check the management over the element
        final Element element = fetchService.fetch(query.getElement(), Element.Relationships.GROUP);
        if (element != null && !permissionService.manages(element)) {
            return false;
        }

        // Check the visibility over the record type
        if (element != null && !memberRecordTypeService.canView(type, element.getNature())) {
            return false;
        }

        if (LoggedUser.isAdministrator()) {
            // Administrators only can view user records of managed users
            Collection<Group> groups = PermissionHelper.checkSelection(permissionService.getAllVisibleGroups(), query.getGroups());
            if (groups == null) {
                return false;
            }
            query.setGroups(groups);
        } else if (LoggedUser.isBroker()) {
            // A broker only can view user records of his brokered members
            query.setBroker(LoggedUser.member());
        }
        return true;
    }

    private void checkCreate(final MemberRecord memberRecord) {
        boolean canCreate = false;
        Element element = fetchService.fetch(memberRecord.getElement());
        if (element == null) {
            // Will fail validation
            return;
        }
        permissionService.checkManages(element);
        final MemberRecordType type = fetchService.fetch(memberRecord.getType());
        if (LoggedUser.isAdministrator()) {
            if (element instanceof Administrator) {
                AdminGroup adminGroup = fetchService.fetch((AdminGroup) LoggedUser.group(), AdminGroup.Relationships.CREATE_ADMIN_RECORD_TYPES);
                canCreate = adminGroup.getCreateAdminRecordTypes().contains(type);
            } else if (element instanceof Member) {
                AdminGroup adminGroup = fetchService.fetch((AdminGroup) LoggedUser.group(), AdminGroup.Relationships.CREATE_MEMBER_RECORD_TYPES);
                canCreate = adminGroup.getCreateMemberRecordTypes().contains(type);
            }
        } else if (LoggedUser.isBroker()) {
            BrokerGroup brokerGroup = fetchService.fetch((BrokerGroup) LoggedUser.group(), BrokerGroup.Relationships.BROKER_CREATE_MEMBER_RECORD_TYPES);
            if (element instanceof Member) {
                canCreate = brokerGroup.getBrokerCreateMemberRecordTypes().contains(type);
            }
        }
        if (!canCreate) {
            throw new PermissionDeniedException();
        }
    }

    private void checkRemove(final Long... ids) {
        boolean canRemove = true;
        for (Long id : ids) {
            MemberRecord memberRecord = memberRecordService.load(id, MemberRecord.Relationships.ELEMENT, MemberRecord.Relationships.TYPE);
            permissionService.checkManages(memberRecord.getElement());
            MemberRecordType type = fetchService.fetch(memberRecord.getType());
            if (LoggedUser.isAdministrator()) {
                if (memberRecord.getElement() instanceof Administrator) {
                    AdminGroup adminGroup = fetchService.fetch((AdminGroup) LoggedUser.group(), AdminGroup.Relationships.DELETE_ADMIN_RECORD_TYPES);
                    canRemove = adminGroup.getDeleteAdminRecordTypes().contains(type);
                } else if (memberRecord.getElement() instanceof Member) {
                    AdminGroup adminGroup = fetchService.fetch((AdminGroup) LoggedUser.group(), AdminGroup.Relationships.DELETE_MEMBER_RECORD_TYPES);
                    canRemove = adminGroup.getDeleteMemberRecordTypes().contains(type);
                }
            } else if (LoggedUser.isBroker()) {
                BrokerGroup brokerGroup = fetchService.fetch((BrokerGroup) LoggedUser.group(), BrokerGroup.Relationships.BROKER_DELETE_MEMBER_RECORD_TYPES);
                if (memberRecord.getElement() instanceof Member) {
                    canRemove = brokerGroup.getBrokerDeleteMemberRecordTypes().contains(type);
                }
            }
            if (!canRemove) {
                throw new PermissionDeniedException();
            }
        }
    }

    private void checkUpdate(final MemberRecord memberRecord) {
        boolean canUpdate = false;
        MemberRecord saved = memberRecordService.load(memberRecord.getId(), MemberRecord.Relationships.ELEMENT, MemberRecord.Relationships.TYPE);
        memberRecord.setElement(saved.getElement());
        memberRecord.setType(saved.getType());
        permissionService.checkManages(memberRecord.getElement());
        if (LoggedUser.isAdministrator()) {
            if (memberRecord.getElement() instanceof Administrator) {
                AdminGroup adminGroup = fetchService.fetch((AdminGroup) LoggedUser.group(), AdminGroup.Relationships.MODIFY_ADMIN_RECORD_TYPES);
                canUpdate = adminGroup.getModifyAdminRecordTypes().contains(memberRecord.getType());
            } else if (memberRecord.getElement() instanceof Member) {
                AdminGroup adminGroup = fetchService.fetch((AdminGroup) LoggedUser.group(), AdminGroup.Relationships.MODIFY_MEMBER_RECORD_TYPES);
                canUpdate = adminGroup.getModifyMemberRecordTypes().contains(memberRecord.getType());
            }
        } else if (LoggedUser.isBroker()) {
            BrokerGroup brokerGroup = fetchService.fetch((BrokerGroup) LoggedUser.group(), BrokerGroup.Relationships.BROKER_MODIFY_MEMBER_RECORD_TYPES);
            if (memberRecord.getElement() instanceof Member) {
                canUpdate = brokerGroup.getBrokerModifyMemberRecordTypes().contains(memberRecord.getType());
            }
        }
        if (!canUpdate) {
            throw new PermissionDeniedException();
        }
    }

    private void checkView(final Long id) {
        boolean canView = false;
        MemberRecord memberRecord = memberRecordService.load(id, MemberRecord.Relationships.ELEMENT, MemberRecord.Relationships.TYPE);
        permissionService.checkManages(memberRecord.getElement());
        MemberRecordType type = memberRecord.getType();
        if (LoggedUser.isAdministrator()) {
            if (memberRecord.getElement() instanceof Administrator) {
                AdminGroup adminGroup = fetchService.fetch((AdminGroup) LoggedUser.group(), AdminGroup.Relationships.VIEW_ADMIN_RECORD_TYPES);
                canView = adminGroup.getViewAdminRecordTypes().contains(type);
            } else if (memberRecord.getElement() instanceof Member) {
                AdminGroup adminGroup = fetchService.fetch((AdminGroup) LoggedUser.group(), AdminGroup.Relationships.VIEW_MEMBER_RECORD_TYPES);
                canView = adminGroup.getViewMemberRecordTypes().contains(type);
            }
        } else if (LoggedUser.isBroker()) {
            BrokerGroup brokerGroup = fetchService.fetch((BrokerGroup) LoggedUser.group(), BrokerGroup.Relationships.BROKER_MEMBER_RECORD_TYPES);
            if (memberRecord.getElement() instanceof Member) {
                canView = brokerGroup.getBrokerMemberRecordTypes().contains(type);
            }
        }
        if (!canView) {
            throw new PermissionDeniedException();
        }
    }

}
