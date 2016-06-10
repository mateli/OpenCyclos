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
package nl.strohalm.cyclos.services.customization;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import nl.strohalm.cyclos.access.AdminSystemPermission;
import nl.strohalm.cyclos.entities.customization.fields.CustomFieldPossibleValue;
import nl.strohalm.cyclos.entities.customization.fields.MemberRecordCustomField;
import nl.strohalm.cyclos.entities.exceptions.DaoException;
import nl.strohalm.cyclos.entities.groups.AdminGroup;
import nl.strohalm.cyclos.entities.groups.BrokerGroup;
import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.entities.members.records.MemberRecordType;
import nl.strohalm.cyclos.exceptions.PermissionDeniedException;
import nl.strohalm.cyclos.services.BaseServiceSecurity;
import nl.strohalm.cyclos.utils.access.LoggedUser;
import nl.strohalm.cyclos.utils.validation.ValidationException;
import nl.strohalm.cyclos.webservices.model.FieldVO;
import nl.strohalm.cyclos.webservices.model.PossibleValueVO;

/**
 * Security layer for {@link MemberRecordCustomFieldService}
 * 
 * @author luis
 */
public class MemberRecordCustomFieldServiceSecurity extends BaseServiceSecurity implements MemberRecordCustomFieldService {
    private MemberRecordCustomFieldServiceLocal memberRecordCustomFieldService;

    @Override
    public FieldVO getFieldVO(final Long customFieldId) {
        if (customFieldId == null) {
            return null;
        }
        checkVisible(load(customFieldId));
        return memberRecordCustomFieldService.getFieldVO(customFieldId);
    }

    @Override
    public List<FieldVO> getFieldVOs(final List<Long> customFieldIds) {
        if (customFieldIds == null) {
            return null;
        }

        for (Long customFieldId : customFieldIds) {
            checkVisible(load(customFieldId));
        }
        return memberRecordCustomFieldService.getFieldVOs(customFieldIds);
    }

    @Override
    public List<PossibleValueVO> getPossibleValueVOs(final Long customFieldId, final Long possibleValueParentId) {
        if (customFieldId == null) {
            return null;
        }
        checkVisible(load(customFieldId));
        return memberRecordCustomFieldService.getPossibleValueVOs(customFieldId, possibleValueParentId);
    }

    @Override
    public List<MemberRecordCustomField> list(final MemberRecordType recordType) {
        List<MemberRecordCustomField> fields = memberRecordCustomFieldService.list(recordType);
        for (Iterator<MemberRecordCustomField> iterator = fields.iterator(); iterator.hasNext();) {
            MemberRecordCustomField field = iterator.next();
            if (!isVisible(field)) {
                iterator.remove();
            }
        }
        return fields;
    }

    @Override
    public List<MemberRecordCustomField> listPossibleParentFields(final MemberRecordCustomField field) {
        checkVisible(field);
        return memberRecordCustomFieldService.listPossibleParentFields(field);
    }

    @Override
    public List<MemberRecordCustomField> load(final Collection<Long> ids) {
        List<MemberRecordCustomField> fields = memberRecordCustomFieldService.load(ids);
        for (MemberRecordCustomField field : fields) {
            checkVisible(field);
        }
        return fields;
    }

    @Override
    public MemberRecordCustomField load(final Long id) {
        MemberRecordCustomField field = memberRecordCustomFieldService.load(id);
        checkVisible(field);
        return field;
    }

    @Override
    public CustomFieldPossibleValue loadPossibleValue(final Long id) {
        CustomFieldPossibleValue possibleValue = memberRecordCustomFieldService.loadPossibleValue(id);
        checkVisible((MemberRecordCustomField) possibleValue.getField());
        return possibleValue;
    }

    @Override
    public List<CustomFieldPossibleValue> loadPossibleValues(final Collection<Long> ids) {
        List<CustomFieldPossibleValue> possibleValues = memberRecordCustomFieldService.loadPossibleValues(ids);
        for (CustomFieldPossibleValue possibleValue : possibleValues) {
            checkVisible((MemberRecordCustomField) possibleValue.getField());
        }

        return possibleValues;
    }

    @Override
    public int remove(final Long... ids) {
        checkManage();
        return memberRecordCustomFieldService.remove(ids);
    }

    @Override
    public int removePossibleValue(final Long... ids) {
        checkManage();
        return memberRecordCustomFieldService.removePossibleValue(ids);
    }

    @Override
    public int replacePossibleValues(final CustomFieldPossibleValue oldValue, final CustomFieldPossibleValue newValue) {
        checkManage();
        return memberRecordCustomFieldService.replacePossibleValues(oldValue, newValue);
    }

    @Override
    public CustomFieldPossibleValue save(final CustomFieldPossibleValue possibleValue) throws ValidationException, DaoException {
        checkManage();
        return memberRecordCustomFieldService.save(possibleValue);
    }

    @Override
    public MemberRecordCustomField save(final MemberRecordCustomField field) throws ValidationException, DaoException {
        checkManage();
        return memberRecordCustomFieldService.save(field);
    }

    public void setMemberRecordCustomFieldServiceLocal(final MemberRecordCustomFieldServiceLocal memberRecordCustomFieldService) {
        this.memberRecordCustomFieldService = memberRecordCustomFieldService;
    }

    @Override
    public void setOrder(final List<Long> ids) {
        checkManage();
        memberRecordCustomFieldService.setOrder(ids);
    }

    @Override
    public void validate(final CustomFieldPossibleValue possibleValue) throws ValidationException {
        // No permission check needed on validate
        memberRecordCustomFieldService.validate(possibleValue);
    }

    @Override
    public void validate(final MemberRecordCustomField field) throws ValidationException {
        // No permission check needed on validate
        memberRecordCustomFieldService.validate(field);
    }

    private void checkManage() {
        permissionService.permission().admin(AdminSystemPermission.MEMBER_RECORD_TYPES_MANAGE).check();
    }

    private void checkVisible(final MemberRecordCustomField field) {
        if (!isVisible(field)) {
            throw new PermissionDeniedException();
        }
    }

    /**
     * Admins with memberRecordTypes.view are always allowed. Otherwise, the admin / broker must view the record type. Besides, for broker, it is
     * possible to control by field if the field is visible or not.
     */
    private boolean isVisible(final MemberRecordCustomField field) {
        if (permissionService.hasPermission(AdminSystemPermission.MEMBER_RECORD_TYPES_VIEW)) {
            return true;
        }
        Group group = LoggedUser.group();
        Collection<MemberRecordType> visibleRecordTypes = Collections.emptyList();
        if (group instanceof AdminGroup) {
            visibleRecordTypes = fetchService.fetch((AdminGroup) group, AdminGroup.Relationships.VIEW_MEMBER_RECORD_TYPES).getViewMemberRecordTypes();
        } else if (group instanceof BrokerGroup) {
            // For brokers, it is possible that specific fields are not visible
            if (field.getBrokerAccess() != MemberRecordCustomField.Access.NONE) {
                visibleRecordTypes = fetchService.fetch((BrokerGroup) group, BrokerGroup.Relationships.BROKER_MEMBER_RECORD_TYPES).getBrokerMemberRecordTypes();
            }
        }
        return visibleRecordTypes.contains(field.getMemberRecordType());
    }

}
