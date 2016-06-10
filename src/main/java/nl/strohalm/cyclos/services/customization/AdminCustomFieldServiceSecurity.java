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

import nl.strohalm.cyclos.access.AdminSystemPermission;
import nl.strohalm.cyclos.entities.customization.fields.AdminCustomField;
import nl.strohalm.cyclos.entities.customization.fields.CustomFieldPossibleValue;
import nl.strohalm.cyclos.entities.exceptions.DaoException;
import nl.strohalm.cyclos.services.BaseServiceSecurity;
import nl.strohalm.cyclos.utils.validation.ValidationException;
import nl.strohalm.cyclos.webservices.model.FieldVO;
import nl.strohalm.cyclos.webservices.model.PossibleValueVO;

/**
 * Security layer for {@link AdminCustomFieldService}
 * 
 * @author luis
 */
public class AdminCustomFieldServiceSecurity extends BaseServiceSecurity implements AdminCustomFieldService {

    private AdminCustomFieldServiceLocal adminCustomFieldService;

    @Override
    public FieldVO getFieldVO(final Long customFieldId) {
        if (customFieldId == null) {
            return null;
        }
        checkView();
        return adminCustomFieldService.getFieldVO(customFieldId);
    }

    @Override
    public List<FieldVO> getFieldVOs(final List<Long> customFieldIds) {
        if (customFieldIds == null) {
            return null;
        }
        checkView();
        return adminCustomFieldService.getFieldVOs(customFieldIds);
    }

    @Override
    public List<PossibleValueVO> getPossibleValueVOs(final Long customFieldId, final Long possibleValueParentId) {
        if (customFieldId == null) {
            return null;
        }
        checkView();
        return adminCustomFieldService.getPossibleValueVOs(customFieldId, possibleValueParentId);
    }

    @Override
    public List<AdminCustomField> list() {
        checkView();
        return adminCustomFieldService.list();
    }

    @Override
    public List<AdminCustomField> listPossibleParentFields(final AdminCustomField field) {
        checkView();
        return adminCustomFieldService.listPossibleParentFields(field);
    }

    @Override
    public List<AdminCustomField> load(final Collection<Long> ids) {
        checkView();
        return adminCustomFieldService.load(ids);
    }

    @Override
    public AdminCustomField load(final Long id) {
        checkView();
        return adminCustomFieldService.load(id);
    }

    @Override
    public CustomFieldPossibleValue loadPossibleValue(final Long id) {
        checkView();
        return adminCustomFieldService.loadPossibleValue(id);
    }

    @Override
    public List<CustomFieldPossibleValue> loadPossibleValues(final Collection<Long> ids) {
        checkView();
        return adminCustomFieldService.loadPossibleValues(ids);
    }

    @Override
    public int remove(final Long... ids) {
        checkManage();
        return adminCustomFieldService.remove(ids);
    }

    @Override
    public int removePossibleValue(final Long... ids) {
        checkManage();
        return adminCustomFieldService.removePossibleValue(ids);
    }

    @Override
    public int replacePossibleValues(final CustomFieldPossibleValue oldValue, final CustomFieldPossibleValue newValue) {
        checkManage();
        return adminCustomFieldService.replacePossibleValues(oldValue, newValue);
    }

    @Override
    public AdminCustomField save(final AdminCustomField field) throws ValidationException, DaoException {
        checkManage();
        return adminCustomFieldService.save(field);
    }

    @Override
    public CustomFieldPossibleValue save(final CustomFieldPossibleValue possibleValue) throws ValidationException, DaoException {
        checkManage();
        return adminCustomFieldService.save(possibleValue);
    }

    public void setAdminCustomFieldServiceLocal(final AdminCustomFieldServiceLocal adminCustomFieldService) {
        this.adminCustomFieldService = adminCustomFieldService;
    }

    @Override
    public void setOrder(final List<Long> ids) {
        checkManage();
        adminCustomFieldService.setOrder(ids);
    }

    @Override
    public void validate(final AdminCustomField field) throws ValidationException {
        // No permission check needed on validate
        adminCustomFieldService.validate(field);
    }

    @Override
    public void validate(final CustomFieldPossibleValue possibleValue) throws ValidationException {
        // No permission check needed on validate
        adminCustomFieldService.validate(possibleValue);
    }

    private void checkManage() {
        permissionService.permission().admin(AdminSystemPermission.CUSTOM_FIELDS_MANAGE).check();
    }

    /**
     * An admin can see any admin custom fields.
     */
    private void checkView() {
        permissionService.permission().admin().check();
    }
}
