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
import nl.strohalm.cyclos.entities.accounts.loans.LoanGroupQuery;
import nl.strohalm.cyclos.entities.customization.fields.CustomFieldPossibleValue;
import nl.strohalm.cyclos.entities.customization.fields.LoanGroupCustomField;
import nl.strohalm.cyclos.entities.exceptions.DaoException;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.exceptions.PermissionDeniedException;
import nl.strohalm.cyclos.services.BaseServiceSecurity;
import nl.strohalm.cyclos.services.loangroups.LoanGroupServiceLocal;
import nl.strohalm.cyclos.utils.access.LoggedUser;
import nl.strohalm.cyclos.utils.query.PageHelper;
import nl.strohalm.cyclos.utils.validation.ValidationException;
import nl.strohalm.cyclos.webservices.model.FieldVO;
import nl.strohalm.cyclos.webservices.model.PossibleValueVO;

/**
 * Security layer for {@link LoanGroupCustomFieldService}
 * 
 * @author luis
 */
public class LoanGroupCustomFieldServiceSecurity extends BaseServiceSecurity implements LoanGroupCustomFieldService {
    private LoanGroupCustomFieldServiceLocal loanGroupCustomFieldService;
    private LoanGroupServiceLocal            loanGroupService;

    @Override
    public FieldVO getFieldVO(final Long customFieldId) {
        if (customFieldId == null) {
            return null;
        }
        checkView();
        return loanGroupCustomFieldService.getFieldVO(customFieldId);
    }

    @Override
    public List<FieldVO> getFieldVOs(final List<Long> customFieldIds) {
        if (customFieldIds == null) {
            return null;
        }
        checkView();
        return loanGroupCustomFieldService.getFieldVOs(customFieldIds);
    }

    @Override
    public List<PossibleValueVO> getPossibleValueVOs(final Long customFieldId, final Long possibleValueParentId) {
        if (customFieldId == null) {
            return null;
        }
        checkView();
        return loanGroupCustomFieldService.getPossibleValueVOs(customFieldId, possibleValueParentId);
    }

    @Override
    public List<LoanGroupCustomField> list() {
        checkView();
        return loanGroupCustomFieldService.list();
    }

    @Override
    public List<LoanGroupCustomField> listPossibleParentFields(final LoanGroupCustomField field) {
        checkView();
        return loanGroupCustomFieldService.listPossibleParentFields(field);
    }

    @Override
    public List<LoanGroupCustomField> load(final Collection<Long> ids) {
        checkView();
        return loanGroupCustomFieldService.load(ids);
    }

    @Override
    public LoanGroupCustomField load(final Long id) {
        checkView();
        return loanGroupCustomFieldService.load(id);
    }

    @Override
    public CustomFieldPossibleValue loadPossibleValue(final Long id) {
        checkView();
        return loanGroupCustomFieldService.loadPossibleValue(id);
    }

    @Override
    public List<CustomFieldPossibleValue> loadPossibleValues(final Collection<Long> ids) {
        checkView();
        return loanGroupCustomFieldService.loadPossibleValues(ids);
    }

    @Override
    public int remove(final Long... ids) {
        checkManage();
        return loanGroupCustomFieldService.remove(ids);
    }

    @Override
    public int removePossibleValue(final Long... ids) {
        checkManage();
        return loanGroupCustomFieldService.removePossibleValue(ids);
    }

    @Override
    public int replacePossibleValues(final CustomFieldPossibleValue oldValue, final CustomFieldPossibleValue newValue) {
        checkManage();
        return loanGroupCustomFieldService.replacePossibleValues(oldValue, newValue);
    }

    @Override
    public CustomFieldPossibleValue save(final CustomFieldPossibleValue possibleValue) throws ValidationException, DaoException {
        checkManage();
        return loanGroupCustomFieldService.save(possibleValue);
    }

    @Override
    public LoanGroupCustomField save(final LoanGroupCustomField field) throws ValidationException, DaoException {
        checkManage();
        return loanGroupCustomFieldService.save(field);
    }

    public void setLoanGroupCustomFieldServiceLocal(final LoanGroupCustomFieldServiceLocal loanGroupCustomFieldService) {
        this.loanGroupCustomFieldService = loanGroupCustomFieldService;
    }

    public void setLoanGroupServiceLocal(final LoanGroupServiceLocal loanGroupService) {
        this.loanGroupService = loanGroupService;
    }

    @Override
    public void setOrder(final List<Long> ids) {
        checkManage();
        loanGroupCustomFieldService.setOrder(ids);
    }

    @Override
    public void validate(final CustomFieldPossibleValue possibleValue) throws ValidationException {
        // No permission check needed on validate
        loanGroupCustomFieldService.validate(possibleValue);
    }

    @Override
    public void validate(final LoanGroupCustomField field) throws ValidationException {
        // No permission check needed on validate
        loanGroupCustomFieldService.validate(field);
    }

    private void checkManage() {
        permissionService.permission().admin(AdminSystemPermission.CUSTOM_FIELDS_MANAGE).check();
    }

    /**
     * To view loan group fields, it's either an admin with the regular customFields.view permission, or an admin which can view loan groups or a loan
     * group's members. Otherwise, it can also be members which are in loan groups, and therefore, can view them. There is no permission check in this
     * case.
     */
    private void checkView() {
        if (LoggedUser.isAdministrator()) {
            permissionService.permission()
                    .admin(AdminSystemPermission.CUSTOM_FIELDS_VIEW, AdminSystemPermission.LOAN_GROUPS_VIEW, AdminMemberPermission.LOAN_GROUPS_VIEW)
                    .check();
            return;
        }
        Member member = LoggedUser.member();
        if (member != null) {
            // If passed as member, ensure he can only see fields if is member of any loan groups
            final LoanGroupQuery lgq = new LoanGroupQuery();
            lgq.setPageForCount();
            lgq.setMember(member);
            boolean hasLoanGroups = PageHelper.getTotalCount(loanGroupService.search(lgq)) > 0;
            if (hasLoanGroups) {
                // Ok - he has loan groups
                return;
            }
        }
        throw new PermissionDeniedException();
    }
}
