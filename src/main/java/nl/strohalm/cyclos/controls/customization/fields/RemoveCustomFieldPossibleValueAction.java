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
package nl.strohalm.cyclos.controls.customization.fields;

import java.util.HashMap;
import java.util.Map;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseAction;
import nl.strohalm.cyclos.entities.customization.fields.CustomField;
import nl.strohalm.cyclos.entities.customization.fields.CustomField.Nature;
import nl.strohalm.cyclos.entities.customization.fields.CustomFieldPossibleValue;
import nl.strohalm.cyclos.entities.customization.fields.MemberRecordCustomField;
import nl.strohalm.cyclos.entities.customization.fields.PaymentCustomField;
import nl.strohalm.cyclos.entities.exceptions.DaoException;
import nl.strohalm.cyclos.services.customization.AdCustomFieldService;
import nl.strohalm.cyclos.services.customization.AdminCustomFieldService;
import nl.strohalm.cyclos.services.customization.BaseCustomFieldService;
import nl.strohalm.cyclos.services.customization.LoanGroupCustomFieldService;
import nl.strohalm.cyclos.services.customization.MemberCustomFieldService;
import nl.strohalm.cyclos.services.customization.MemberRecordCustomFieldService;
import nl.strohalm.cyclos.services.customization.OperatorCustomFieldService;
import nl.strohalm.cyclos.services.customization.PaymentCustomFieldService;
import nl.strohalm.cyclos.utils.ActionHelper;
import nl.strohalm.cyclos.utils.validation.ValidationException;

import org.apache.struts.action.ActionForward;

/**
 * Action used to remove a custom field possible value
 * @author luis
 */
public class RemoveCustomFieldPossibleValueAction extends BaseAction {

    private AdCustomFieldService           adCustomFieldService;
    private AdminCustomFieldService        adminCustomFieldService;
    private LoanGroupCustomFieldService    loanGroupCustomFieldService;
    private MemberCustomFieldService       memberCustomFieldService;
    private MemberRecordCustomFieldService memberRecordCustomFieldService;
    private OperatorCustomFieldService     operatorCustomFieldService;
    private PaymentCustomFieldService      paymentCustomFieldService;

    @Inject
    public void setAdCustomFieldService(final AdCustomFieldService adCustomFieldService) {
        this.adCustomFieldService = adCustomFieldService;
    }

    @Inject
    public void setAdminCustomFieldService(final AdminCustomFieldService adminCustomFieldService) {
        this.adminCustomFieldService = adminCustomFieldService;
    }

    @Inject
    public void setLoanGroupCustomFieldService(final LoanGroupCustomFieldService loanGroupCustomFieldService) {
        this.loanGroupCustomFieldService = loanGroupCustomFieldService;
    }

    @Inject
    public void setMemberCustomFieldService(final MemberCustomFieldService memberCustomFieldService) {
        this.memberCustomFieldService = memberCustomFieldService;
    }

    @Inject
    public void setMemberRecordCustomFieldService(final MemberRecordCustomFieldService memberRecordCustomFieldService) {
        this.memberRecordCustomFieldService = memberRecordCustomFieldService;
    }

    @Inject
    public void setOperatorCustomFieldService(final OperatorCustomFieldService operatorCustomFieldService) {
        this.operatorCustomFieldService = operatorCustomFieldService;
    }

    @Inject
    public void setPaymentCustomFieldService(final PaymentCustomFieldService paymentCustomFieldService) {
        this.paymentCustomFieldService = paymentCustomFieldService;
    }

    @Override
    protected ActionForward executeAction(final ActionContext context) throws Exception {
        final RemoveCustomFieldPossibleValueForm form = context.getForm();
        final long id = form.getPossibleValueId();
        if (id <= 0) {
            throw new ValidationException();
        }
        final Nature nature = getNature(form);
        final Map<String, Object> params = new HashMap<String, Object>();
        String key;
        try {
            final BaseCustomFieldService<CustomField> service = resolveService(nature);
            final CustomFieldPossibleValue possibleValue = service.loadPossibleValue(id);
            final CustomField customField = possibleValue.getField();
            switch (customField.getNature()) {
                case PAYMENT:
                    final PaymentCustomField paymentField = (PaymentCustomField) customField;
                    params.put("transferTypeId", paymentField.getTransferType().getId());
                    break;
                case MEMBER_RECORD:
                    final MemberRecordCustomField memberRecordField = (MemberRecordCustomField) customField;
                    params.put("memberRecordTypeId", memberRecordField.getMemberRecordType().getId());
                default:
                    service.removePossibleValue(id);
                    break;
            }
            key = "customField.possibleValue.removed";
        } catch (final DaoException e) {
            key = "customField.possibleValue.error.removing";
        }
        params.put("fieldId", form.getFieldId());
        params.put("nature", nature);
        context.sendMessage(key);

        return ActionHelper.redirectWithParams(context.getRequest(), context.getSuccessForward(), params);
    }

    private CustomField.Nature getNature(final RemoveCustomFieldPossibleValueForm form) {
        CustomField.Nature nature;
        try {
            nature = CustomField.Nature.valueOf(form.getNature());
        } catch (final Exception e) {
            throw new ValidationException();
        }
        return nature;
    }

    @SuppressWarnings("unchecked")
    private <CF extends CustomField> BaseCustomFieldService<CF> resolveService(final CustomField.Nature nature) {
        switch (nature) {
            case AD:
                return (BaseCustomFieldService<CF>) adCustomFieldService;
            case ADMIN:
                return (BaseCustomFieldService<CF>) adminCustomFieldService;
            case LOAN_GROUP:
                return (BaseCustomFieldService<CF>) loanGroupCustomFieldService;
            case MEMBER:
                return (BaseCustomFieldService<CF>) memberCustomFieldService;
            case MEMBER_RECORD:
                return (BaseCustomFieldService<CF>) memberRecordCustomFieldService;
            case OPERATOR:
                return (BaseCustomFieldService<CF>) operatorCustomFieldService;
            case PAYMENT:
                return (BaseCustomFieldService<CF>) paymentCustomFieldService;
        }
        return null;
    }

}
