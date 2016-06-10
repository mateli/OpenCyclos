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
import java.util.List;

import nl.strohalm.cyclos.access.AdminMemberPermission;
import nl.strohalm.cyclos.access.AdminSystemPermission;
import nl.strohalm.cyclos.access.MemberPermission;
import nl.strohalm.cyclos.entities.customization.fields.AdCustomField;
import nl.strohalm.cyclos.entities.customization.fields.CustomFieldPossibleValue;
import nl.strohalm.cyclos.entities.exceptions.DaoException;
import nl.strohalm.cyclos.services.BaseServiceSecurity;
import nl.strohalm.cyclos.utils.validation.ValidationException;
import nl.strohalm.cyclos.webservices.model.FieldVO;
import nl.strohalm.cyclos.webservices.model.PossibleValueVO;

/**
 * Security layer for {@link AdCustomFieldService}
 * 
 * @author luis
 */
public class AdCustomFieldServiceSecurity extends BaseServiceSecurity implements AdCustomFieldService {

    private AdCustomFieldServiceLocal adCustomFieldService;

    @Override
    public FieldVO getFieldVO(final Long customFieldId) {
        if (customFieldId == null) {
            return null;
        }
        FieldVO fieldVO = adCustomFieldService.getFieldVO(customFieldId);
        checkView();
        return fieldVO;
    }

    @Override
    public List<FieldVO> getFieldVOs(final List<Long> customFieldIds) {
        if (customFieldIds == null) {
            return null;
        }
        checkView();
        List<FieldVO> fieldVOs = adCustomFieldService.getFieldVOs(customFieldIds);
        return fieldVOs;
    }

    @Override
    public List<PossibleValueVO> getPossibleValueVOs(final Long customFieldId, final Long possibleValueParentId) {
        if (customFieldId == null) {
            return null;
        }
        checkView();
        return adCustomFieldService.getPossibleValueVOs(customFieldId, possibleValueParentId);
    }

    @Override
    public List<AdCustomField> list() {
        checkView();
        return adCustomFieldService.list();
    }

    @Override
    public List<AdCustomField> listPossibleParentFields(final AdCustomField field) {
        checkView();
        return adCustomFieldService.listPossibleParentFields(field);
    }

    @Override
    public List<AdCustomField> load(final Collection<Long> ids) {
        checkView();
        return adCustomFieldService.load(ids);
    }

    @Override
    public AdCustomField load(final Long id) {
        checkView();
        return adCustomFieldService.load(id);
    }

    @Override
    public CustomFieldPossibleValue loadPossibleValue(final Long id) {
        checkView();
        return adCustomFieldService.loadPossibleValue(id);
    }

    @Override
    public List<CustomFieldPossibleValue> loadPossibleValues(final Collection<Long> ids) {
        checkView();
        return adCustomFieldService.loadPossibleValues(ids);
    }

    @Override
    public int remove(final Long... ids) {
        checkManage();
        return adCustomFieldService.remove(ids);
    }

    @Override
    public int removePossibleValue(final Long... ids) {
        checkManage();
        return adCustomFieldService.removePossibleValue(ids);
    }

    @Override
    public int replacePossibleValues(final CustomFieldPossibleValue oldValue, final CustomFieldPossibleValue newValue) {
        checkManage();
        return adCustomFieldService.replacePossibleValues(oldValue, newValue);
    }

    @Override
    public AdCustomField save(final AdCustomField field) throws ValidationException, DaoException {
        checkManage();
        return adCustomFieldService.save(field);
    }

    @Override
    public CustomFieldPossibleValue save(final CustomFieldPossibleValue possibleValue) throws ValidationException, DaoException {
        checkManage();
        return adCustomFieldService.save(possibleValue);
    }

    public void setAdCustomFieldServiceLocal(final AdCustomFieldServiceLocal adCustomFieldService) {
        this.adCustomFieldService = adCustomFieldService;
    }

    @Override
    public void setOrder(final List<Long> ids) {
        checkManage();
        adCustomFieldService.setOrder(ids);
    }

    @Override
    public void validate(final AdCustomField field) throws ValidationException {
        // No permission check needed on validate
        adCustomFieldService.validate(field);
    }

    @Override
    public void validate(final CustomFieldPossibleValue possibleValue) throws ValidationException {
        // No permission check needed on validate
        adCustomFieldService.validate(possibleValue);
    }

    private void checkManage() {
        permissionService.permission().admin(AdminSystemPermission.CUSTOM_FIELDS_MANAGE).check();
    }

    /**
     * To view ad fields, besides the customFields.view permission, any user which is allowed to view ads can see their fields
     */
    private void checkView() {
        permissionService.permission()
                .admin(AdminSystemPermission.CUSTOM_FIELDS_VIEW, AdminMemberPermission.ADS_VIEW)
                .member(MemberPermission.ADS_VIEW)
                .operator(MemberPermission.ADS_VIEW) // There's no specific operator view ads permission. Use his member's
                .check();
    }
}
