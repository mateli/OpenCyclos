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

import nl.strohalm.cyclos.access.AdminSystemPermission;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.Account;
import nl.strohalm.cyclos.entities.accounts.AccountType;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferType;
import nl.strohalm.cyclos.entities.customization.fields.CustomFieldPossibleValue;
import nl.strohalm.cyclos.entities.customization.fields.PaymentCustomField;
import nl.strohalm.cyclos.entities.exceptions.DaoException;
import nl.strohalm.cyclos.exceptions.PermissionDeniedException;
import nl.strohalm.cyclos.services.BaseServiceSecurity;
import nl.strohalm.cyclos.services.accounts.AccountTypeServiceLocal;
import nl.strohalm.cyclos.utils.validation.ValidationException;
import nl.strohalm.cyclos.webservices.model.FieldVO;
import nl.strohalm.cyclos.webservices.model.PossibleValueVO;

/**
 * Security layer for {@link PaymentCustomFieldService}
 * 
 * @author luis
 */
public class PaymentCustomFieldServiceSecurity extends BaseServiceSecurity implements PaymentCustomFieldService {
    private PaymentCustomFieldServiceLocal paymentCustomFieldService;
    private AccountTypeServiceLocal        accountTypeService;

    @Override
    public FieldVO getFieldVO(final Long customFieldId) {
        if (customFieldId == null) {
            return null;
        }
        checkVisible(load(customFieldId));
        return paymentCustomFieldService.getFieldVO(customFieldId);
    }

    @Override
    public List<FieldVO> getFieldVOs(final List<Long> customFieldIds) {
        if (customFieldIds == null) {
            return null;
        }
        for (Long customFieldId : customFieldIds) {
            checkVisible(load(customFieldId));
        }
        return paymentCustomFieldService.getFieldVOs(customFieldIds);
    }

    @Override
    public List<PossibleValueVO> getPossibleValueVOs(final Long customFieldId, final Long possibleValueParentId) {
        if (customFieldId == null) {
            return null;
        }
        checkVisible(load(customFieldId));
        return paymentCustomFieldService.getPossibleValueVOs(customFieldId, possibleValueParentId);
    }

    @Override
    public void link(final TransferType transferType, final PaymentCustomField customField) {
        checkManage();
        paymentCustomFieldService.link(transferType, customField);
    }

    @Override
    public List<PaymentCustomField> list(final TransferType transferType, final boolean includeDisabled) {
        return filterVisible(paymentCustomFieldService.list(transferType, includeDisabled));
    }

    @Override
    public List<PaymentCustomField> listForList(final Account account, final boolean loan) {
        return filterVisible(paymentCustomFieldService.listForList(account, loan));
    }

    @Override
    public List<PaymentCustomField> listForSearch(final Account account, final boolean loan) {
        return filterVisible(paymentCustomFieldService.listForSearch(account, loan));
    }

    @Override
    public List<PaymentCustomField> listPossibleParentFields(final PaymentCustomField field) {
        checkVisible(field);
        return paymentCustomFieldService.listPossibleParentFields(field);
    }

    @Override
    public List<PaymentCustomField> load(final Collection<Long> ids) {
        List<PaymentCustomField> fields = paymentCustomFieldService.load(ids);
        for (PaymentCustomField field : fields) {
            checkVisible(field);
        }
        return fields;
    }

    @Override
    public PaymentCustomField load(final Long id) {
        PaymentCustomField field = paymentCustomFieldService.load(id);
        checkVisible(field);
        return field;
    }

    @Override
    public PaymentCustomField loadByInternalName(final String internalName, final Relationship... fetch) {
        PaymentCustomField field = paymentCustomFieldService.loadByInternalName(internalName, fetch);
        checkVisible(field);
        return field;
    }

    @Override
    public CustomFieldPossibleValue loadPossibleValue(final Long id) {
        CustomFieldPossibleValue possibleValue = paymentCustomFieldService.loadPossibleValue(id);
        checkVisible((PaymentCustomField) possibleValue.getField());
        return possibleValue;
    }

    @Override
    public List<CustomFieldPossibleValue> loadPossibleValues(final Collection<Long> ids) {
        List<CustomFieldPossibleValue> possibleValues = paymentCustomFieldService.loadPossibleValues(ids);
        for (CustomFieldPossibleValue possibleValue : possibleValues) {
            checkVisible((PaymentCustomField) possibleValue.getField());
        }
        return possibleValues;
    }

    @Override
    public int remove(final Long... ids) {
        checkManage();
        return paymentCustomFieldService.remove(ids);
    }

    @Override
    public int removePossibleValue(final Long... ids) {
        checkManage();
        return paymentCustomFieldService.removePossibleValue(ids);
    }

    @Override
    public int replacePossibleValues(final CustomFieldPossibleValue oldValue, final CustomFieldPossibleValue newValue) {
        checkManage();
        return paymentCustomFieldService.replacePossibleValues(oldValue, newValue);
    }

    @Override
    public CustomFieldPossibleValue save(final CustomFieldPossibleValue possibleValue) throws ValidationException, DaoException {
        checkManage();
        return paymentCustomFieldService.save(possibleValue);
    }

    @Override
    public PaymentCustomField save(final PaymentCustomField field) throws ValidationException, DaoException {
        checkManage();
        return paymentCustomFieldService.save(field);
    }

    public void setAccountTypeServiceLocal(final AccountTypeServiceLocal accountTypeService) {
        this.accountTypeService = accountTypeService;
    }

    @Override
    public void setOrder(final List<Long> ids) {
        checkManage();
        paymentCustomFieldService.setOrder(ids);
    }

    public void setPaymentCustomFieldServiceLocal(final PaymentCustomFieldServiceLocal paymentCustomFieldService) {
        this.paymentCustomFieldService = paymentCustomFieldService;
    }

    @Override
    public void validate(final CustomFieldPossibleValue possibleValue) throws ValidationException {
        // No permission check needed on validate
        paymentCustomFieldService.validate(possibleValue);
    }

    @Override
    public void validate(final PaymentCustomField field) throws ValidationException {
        // No permission check needed on validate
        paymentCustomFieldService.validate(field);
    }

    private void checkManage() {
        permissionService.permission().admin(AdminSystemPermission.ACCOUNTS_MANAGE).check();
    }

    private void checkVisible(final PaymentCustomField field) {
        if (!isVisible(field)) {
            throw new PermissionDeniedException();
        }
    }

    private List<PaymentCustomField> filterVisible(final List<PaymentCustomField> fields) {
        for (Iterator<PaymentCustomField> iterator = fields.iterator(); iterator.hasNext();) {
            PaymentCustomField field = iterator.next();
            if (!isVisible(field)) {
                iterator.remove();
            }
        }
        return fields;
    }

    /**
     * A field is visible if the logged user has the accounts.view permission or the field is either defined or linked by a transfer type from or to
     * any of the visible account types for the logged user
     */
    private boolean isVisible(PaymentCustomField field) {
        if (permissionService.hasPermission(AdminSystemPermission.ACCOUNTS_VIEW)) {
            return true;
        }
        field = fetchService.fetch(field, PaymentCustomField.Relationships.TRANSFER_TYPE, PaymentCustomField.Relationships.LINKED_TRANSFER_TYPES);
        Collection<AccountType> visibleAccountTypes = accountTypeService.getVisibleAccountTypes();
        Collection<TransferType> transferTypes = new HashSet<TransferType>();
        transferTypes.add(field.getTransferType());
        transferTypes.addAll(field.getLinkedTransferTypes());
        for (TransferType transferType : transferTypes) {
            if (visibleAccountTypes.contains(transferType.getFrom()) || visibleAccountTypes.contains(transferType.getTo())) {
                return true;
            }
        }
        return false;
    }
}
