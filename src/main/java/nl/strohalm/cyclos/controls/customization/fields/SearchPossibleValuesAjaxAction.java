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

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.BaseAjaxAction.ContentType;
import nl.strohalm.cyclos.controls.BasePublicAction;
import nl.strohalm.cyclos.entities.customization.fields.CustomField;
import nl.strohalm.cyclos.entities.customization.fields.CustomFieldPossibleValue;
import nl.strohalm.cyclos.services.customization.AdCustomFieldService;
import nl.strohalm.cyclos.services.customization.AdminCustomFieldService;
import nl.strohalm.cyclos.services.customization.BaseCustomFieldService;
import nl.strohalm.cyclos.services.customization.LoanGroupCustomFieldService;
import nl.strohalm.cyclos.services.customization.MemberCustomFieldService;
import nl.strohalm.cyclos.services.customization.MemberRecordCustomFieldService;
import nl.strohalm.cyclos.services.customization.OperatorCustomFieldService;
import nl.strohalm.cyclos.services.customization.PaymentCustomFieldService;
import nl.strohalm.cyclos.utils.ResponseHelper;
import nl.strohalm.cyclos.utils.binding.BeanBinder;
import nl.strohalm.cyclos.utils.binding.BeanCollectionBinder;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.binding.PropertyBinder;
import nl.strohalm.cyclos.utils.validation.ValidationException;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action used to list possible values by ajax
 * 
 * @author luis
 */
public class SearchPossibleValuesAjaxAction extends BasePublicAction {

    private AdCustomFieldService           adCustomFieldService;
    private AdminCustomFieldService        adminCustomFieldService;
    private LoanGroupCustomFieldService    loanGroupCustomFieldService;
    private MemberCustomFieldService       memberCustomFieldService;
    private MemberRecordCustomFieldService memberRecordCustomFieldService;
    private OperatorCustomFieldService     operatorCustomFieldService;
    private PaymentCustomFieldService      paymentCustomFieldService;
    private DataBinder<?>                  possibleValueBinder;
    protected ResponseHelper               responseHelper;

    public DataBinder<?> getPossibleValueBinder() {
        if (possibleValueBinder == null) {
            final BeanBinder<CustomFieldPossibleValue> binder = BeanBinder.instance(CustomFieldPossibleValue.class);
            binder.registerBinder("id", PropertyBinder.instance(Long.class, "id"));
            binder.registerBinder("value", PropertyBinder.instance(String.class, "value"));
            binder.registerBinder("defaultValue", PropertyBinder.instance(boolean.class, "defaultValue"));
            possibleValueBinder = BeanCollectionBinder.instance(binder);
        }
        return possibleValueBinder;
    }

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

    @Inject
    public void setResponseHelper(final ResponseHelper responseHelper) {
        this.responseHelper = responseHelper;
    }

    @Override
    protected ActionForward executeAction(final ActionMapping mapping, final ActionForm actionForm, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        response.setContentType(ContentType.JSON.getContentType());
        final SearchPossibleValuesAjaxForm form = (SearchPossibleValuesAjaxForm) actionForm;
        final long fieldId = form.getFieldId();
        final long parentValueId = form.getParentValueId();
        if (fieldId <= 0L || parentValueId <= 0L) {
            throw new ValidationException();
        }
        final BaseCustomFieldService<CustomField> service = resolveService(getNature(form));
        final CustomField field = service.load(fieldId);
        if (field.getType() != CustomField.Type.ENUMERATED) {
            throw new ValidationException();
        }
        final CustomFieldPossibleValue parentValue = service.loadPossibleValue(parentValueId);
        final Collection<CustomFieldPossibleValue> possibleValues = field.getPossibleValuesByParent(parentValue, true);
        final String json = getPossibleValueBinder().readAsString(possibleValues);
        responseHelper.writeJSON(response, json);
        return null;
    }

    private CustomField.Nature getNature(final SearchPossibleValuesAjaxForm form) {
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
