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

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import nl.strohalm.cyclos.access.MemberPermission;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.customization.fields.CustomFieldPossibleValue;
import nl.strohalm.cyclos.entities.customization.fields.OperatorCustomField;
import nl.strohalm.cyclos.entities.exceptions.DaoException;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.services.BaseServiceSecurity;
import nl.strohalm.cyclos.utils.RelationshipHelper;
import nl.strohalm.cyclos.utils.validation.ValidationException;
import nl.strohalm.cyclos.webservices.model.FieldVO;
import nl.strohalm.cyclos.webservices.model.PossibleValueVO;

/**
 * Security layer for {@link OperatorCustomFieldService}
 * 
 * @author luis
 */
public class OperatorCustomFieldServiceSecurity extends BaseServiceSecurity implements OperatorCustomFieldService {
    private OperatorCustomFieldServiceLocal operatorCustomFieldService;

    @Override
    public FieldVO getFieldVO(final Long customFieldId) {
        if (customFieldId == null) {
            return null;
        }
        checkView(load(customFieldId).getMember());
        return operatorCustomFieldService.getFieldVO(customFieldId);
    }

    @Override
    public List<FieldVO> getFieldVOs(final List<Long> customFieldIds) {
        if (customFieldIds == null) {
            return null;
        }
        for (Long customFieldId : customFieldIds) {
            checkView(load(customFieldId).getMember());
        }
        return operatorCustomFieldService.getFieldVOs(customFieldIds);
    }

    @Override
    public List<PossibleValueVO> getPossibleValueVOs(final Long customFieldId, final Long possibleValueParentId) {
        if (customFieldId == null) {
            return null;
        }
        checkView(load(customFieldId).getMember());
        return operatorCustomFieldService.getPossibleValueVOs(customFieldId, possibleValueParentId);
    }

    @Override
    public List<OperatorCustomField> list(final Member member) {
        checkView(member);
        return operatorCustomFieldService.list(member);
    }

    @Override
    public List<OperatorCustomField> listPossibleParentFields(final OperatorCustomField field) {
        Member member = fetchService.fetch(field.getMember());
        checkView(member);
        return operatorCustomFieldService.listPossibleParentFields(field);
    }

    @Override
    public List<OperatorCustomField> load(final Collection<Long> ids) {
        List<OperatorCustomField> fields = operatorCustomFieldService.load(ids);
        for (OperatorCustomField field : fields) {
            checkView(field.getMember());
        }
        return fields;
    }

    @Override
    public OperatorCustomField load(final Long id) {
        OperatorCustomField field = operatorCustomFieldService.load(id);
        checkView(field.getMember());
        return field;
    }

    @Override
    public CustomFieldPossibleValue loadPossibleValue(final Long id) {
        CustomFieldPossibleValue possibleValue = operatorCustomFieldService.loadPossibleValue(id);
        OperatorCustomField field = (OperatorCustomField) possibleValue.getField();
        checkView(field.getMember());
        return possibleValue;
    }

    @Override
    public List<CustomFieldPossibleValue> loadPossibleValues(final Collection<Long> ids) {
        List<CustomFieldPossibleValue> possibleValues = operatorCustomFieldService.loadPossibleValues(ids);
        Set<Member> members = new HashSet<Member>();
        for (CustomFieldPossibleValue possibleValue : possibleValues) {
            OperatorCustomField field = (OperatorCustomField) possibleValue.getField();
            members.add(field.getMember());
        }
        for (Member member : members) {
            checkView(member);
        }
        return possibleValues;
    }

    @Override
    public int remove(final Long... ids) {
        checkManageIds(Arrays.asList(ids));
        return operatorCustomFieldService.remove(ids);
    }

    @Override
    public int removePossibleValue(final Long... ids) {
        Set<Member> members = new HashSet<Member>();
        List<CustomFieldPossibleValue> possibleValues = operatorCustomFieldService.loadPossibleValues(Arrays.asList(ids));
        for (CustomFieldPossibleValue possibleValue : possibleValues) {
            OperatorCustomField field = (OperatorCustomField) possibleValue.getField();
            members.add(field.getMember());
        }
        for (Member member : members) {
            checkManage(member);
        }
        return operatorCustomFieldService.removePossibleValue(ids);
    }

    @Override
    public int replacePossibleValues(CustomFieldPossibleValue oldValue, CustomFieldPossibleValue newValue) {
        Relationship fetch = RelationshipHelper.nested(CustomFieldPossibleValue.Relationships.FIELD, OperatorCustomField.Relationships.MEMBER);
        oldValue = fetchService.fetch(oldValue, fetch);
        newValue = fetchService.fetch(newValue, fetch);
        Set<Member> members = new HashSet<Member>();
        members.add(((OperatorCustomField) oldValue.getField()).getMember());
        members.add(((OperatorCustomField) newValue.getField()).getMember());
        for (Member member : members) {
            checkManage(member);
        }
        return operatorCustomFieldService.replacePossibleValues(oldValue, newValue);
    }

    @Override
    public CustomFieldPossibleValue save(final CustomFieldPossibleValue possibleValue) throws ValidationException, DaoException {
        OperatorCustomField field = (OperatorCustomField) fetchService.fetch(possibleValue.getField(), OperatorCustomField.Relationships.MEMBER);
        checkManage(field.getMember());
        return operatorCustomFieldService.save(possibleValue);
    }

    @Override
    public OperatorCustomField save(final OperatorCustomField field) throws ValidationException, DaoException {
        Member member = fetchService.fetch(field.getMember());
        checkManage(member);
        return operatorCustomFieldService.save(field);
    }

    public void setOperatorCustomFieldServiceLocal(final OperatorCustomFieldServiceLocal operatorCustomFieldService) {
        this.operatorCustomFieldService = operatorCustomFieldService;
    }

    @Override
    public void setOrder(final List<Long> ids) {
        checkManageIds(ids);
        operatorCustomFieldService.setOrder(ids);
    }

    @Override
    public void validate(final CustomFieldPossibleValue possibleValue) throws ValidationException {
        // No permission check needed on validate
        operatorCustomFieldService.validate(possibleValue);
    }

    @Override
    public void validate(final OperatorCustomField field) throws ValidationException {
        // No permission check needed on validate
        operatorCustomFieldService.validate(field);
    }

    private void checkManage(final Member member) {
        permissionService.permission(member)
                .member(MemberPermission.OPERATORS_MANAGE)
                .check();
    }

    private void checkManageIds(final List<Long> ids) {
        Set<Member> members = new HashSet<Member>();
        List<OperatorCustomField> fields = operatorCustomFieldService.load(ids);
        for (OperatorCustomField field : fields) {
            members.add(field.getMember());
        }
        for (Member member : members) {
            checkManage(member);
        }
    }

    private void checkView(final Member member) {
        permissionService.permission(member)
                .member(MemberPermission.OPERATORS_MANAGE)
                .operator()
                .check();
    }
}
