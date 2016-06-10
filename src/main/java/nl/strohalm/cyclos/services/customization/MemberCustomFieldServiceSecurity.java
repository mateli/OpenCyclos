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
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import nl.strohalm.cyclos.access.AdminSystemPermission;
import nl.strohalm.cyclos.entities.customization.fields.CustomFieldPossibleValue;
import nl.strohalm.cyclos.entities.customization.fields.MemberCustomField;
import nl.strohalm.cyclos.entities.exceptions.DaoException;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.exceptions.PermissionDeniedException;
import nl.strohalm.cyclos.services.BaseServiceSecurity;
import nl.strohalm.cyclos.utils.access.LoggedUser;
import nl.strohalm.cyclos.utils.validation.ValidationException;
import nl.strohalm.cyclos.webservices.model.FieldVO;
import nl.strohalm.cyclos.webservices.model.PossibleValueVO;

import org.springframework.util.CollectionUtils;

/**
 * Security layer for {@link MemberCustomFieldService}
 * 
 * @author luis
 */
public class MemberCustomFieldServiceSecurity extends BaseServiceSecurity implements MemberCustomFieldService {
    private MemberCustomFieldServiceLocal memberCustomFieldService;

    @Override
    public FieldVO getFieldVO(final Long customFieldId) {
        if (customFieldId == null) {
            return null;
        }
        checkVisible(load(customFieldId));
        return memberCustomFieldService.getFieldVO(customFieldId);
    }

    @Override
    public List<FieldVO> getFieldVOs(final List<Long> customFieldIds) {
        if (customFieldIds == null) {
            return null;
        }

        for (Long customFieldId : customFieldIds) {
            checkVisible(load(customFieldId));
        }

        return memberCustomFieldService.getFieldVOs(customFieldIds);
    }

    @Override
    public List<PossibleValueVO> getPossibleValueVOs(final Long customFieldId, final Long possibleValueParentId) {
        if (customFieldId == null) {
            return null;
        }
        checkVisible(load(customFieldId));
        return memberCustomFieldService.getPossibleValueVOs(customFieldId, possibleValueParentId);
    }

    @Override
    public List<MemberCustomField> list() {
        List<MemberCustomField> fields = memberCustomFieldService.list();
        for (Iterator<MemberCustomField> iterator = fields.iterator(); iterator.hasNext();) {
            MemberCustomField field = iterator.next();
            if (!isVisible(field)) {
                iterator.remove();
            }
        }
        return fields;
    }

    @Override
    public List<MemberCustomField> listPossibleParentFields(final MemberCustomField field) {
        checkVisible(field);
        return memberCustomFieldService.listPossibleParentFields(field);
    }

    @Override
    public List<MemberCustomField> load(final Collection<Long> ids) {
        List<MemberCustomField> fields = memberCustomFieldService.load(ids);
        for (MemberCustomField field : fields) {
            checkVisible(field);
        }
        return fields;
    }

    @Override
    public MemberCustomField load(final Long id) {
        MemberCustomField field = memberCustomFieldService.load(id);
        checkVisible(field);
        return field;
    }

    @Override
    public CustomFieldPossibleValue loadPossibleValue(final Long id) {
        CustomFieldPossibleValue possibleValue = memberCustomFieldService.loadPossibleValue(id);
        MemberCustomField field = (MemberCustomField) possibleValue.getField();
        checkVisible(field);
        return possibleValue;
    }

    @Override
    public List<CustomFieldPossibleValue> loadPossibleValues(final Collection<Long> ids) {
        List<CustomFieldPossibleValue> possibleValues = memberCustomFieldService.loadPossibleValues(ids);
        Set<MemberCustomField> fields = new HashSet<MemberCustomField>();
        for (CustomFieldPossibleValue possibleValue : possibleValues) {
            fields.add((MemberCustomField) possibleValue.getField());
        }
        for (MemberCustomField field : fields) {
            checkVisible(field);
        }
        return possibleValues;
    }

    @Override
    public int remove(final Long... ids) {
        checkManage();
        return memberCustomFieldService.remove(ids);
    }

    @Override
    public int removePossibleValue(final Long... ids) {
        checkManage();
        return memberCustomFieldService.removePossibleValue(ids);
    }

    @Override
    public int replacePossibleValues(final CustomFieldPossibleValue oldValue, final CustomFieldPossibleValue newValue) {
        checkManage();
        return memberCustomFieldService.replacePossibleValues(oldValue, newValue);
    }

    @Override
    public CustomFieldPossibleValue save(final CustomFieldPossibleValue possibleValue) throws ValidationException, DaoException {
        checkManage();
        return memberCustomFieldService.save(possibleValue);
    }

    @Override
    public MemberCustomField save(final MemberCustomField field) throws ValidationException, DaoException {
        checkManage();
        return memberCustomFieldService.save(field);
    }

    public void setMemberCustomFieldServiceLocal(final MemberCustomFieldServiceLocal memberCustomFieldService) {
        this.memberCustomFieldService = memberCustomFieldService;
    }

    @Override
    public void setOrder(final List<Long> ids) {
        checkManage();
        memberCustomFieldService.setOrder(ids);
    }

    @Override
    public void validate(final CustomFieldPossibleValue possibleValue) throws ValidationException {
        // No permission check needed on validate
        memberCustomFieldService.validate(possibleValue);
    }

    @Override
    public void validate(final MemberCustomField field) throws ValidationException {
        // No permission check needed on validate
        memberCustomFieldService.validate(field);
    }

    private void checkManage() {
        permissionService.permission().admin(AdminSystemPermission.CUSTOM_FIELDS_MANAGE).check();
    }

    private void checkVisible(final MemberCustomField field) {
        if (!isVisible(field)) {
            throw new PermissionDeniedException();
        }
    }

    /**
     * To view member fields, it's either required the customFields.view permission or that field must be related to any visible member group
     */
    private boolean isVisible(MemberCustomField field) {
        // An admin with view custom fields permission can see all fields
        if (LoggedUser.isAdministrator() && permissionService.permission().admin(AdminSystemPermission.CUSTOM_FIELDS_VIEW).hasPermission()) {
            return true;
        }
        // Otherwise, any visible groups's fields are visible
        Collection<MemberGroup> visibleGroups = permissionService.getVisibleMemberGroups();
        field = fetchService.fetch(field, MemberCustomField.Relationships.GROUPS);
        return CollectionUtils.containsAny(field.getGroups(), visibleGroups);
    }

}
