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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseFormAction;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferType;
import nl.strohalm.cyclos.entities.customization.fields.CustomField;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.members.records.MemberRecordType;
import nl.strohalm.cyclos.services.customization.AdCustomFieldService;
import nl.strohalm.cyclos.services.customization.AdminCustomFieldService;
import nl.strohalm.cyclos.services.customization.BaseCustomFieldService;
import nl.strohalm.cyclos.services.customization.BaseGlobalCustomFieldService;
import nl.strohalm.cyclos.services.customization.LoanGroupCustomFieldService;
import nl.strohalm.cyclos.services.customization.MemberCustomFieldService;
import nl.strohalm.cyclos.services.customization.MemberRecordCustomFieldService;
import nl.strohalm.cyclos.services.customization.OperatorCustomFieldService;
import nl.strohalm.cyclos.services.customization.PaymentCustomFieldService;
import nl.strohalm.cyclos.services.elements.MemberRecordTypeService;
import nl.strohalm.cyclos.services.transfertypes.TransferTypeService;
import nl.strohalm.cyclos.utils.ActionHelper;
import nl.strohalm.cyclos.utils.validation.ValidationException;

import org.apache.struts.action.ActionForward;

/**
 * Action used to set the custom field order
 * @author luis
 */
public class SetCustomFieldOrderAction extends BaseFormAction {

    private AdCustomFieldService           adCustomFieldService;
    private AdminCustomFieldService        adminCustomFieldService;
    private LoanGroupCustomFieldService    loanGroupCustomFieldService;
    private MemberCustomFieldService       memberCustomFieldService;
    private MemberRecordCustomFieldService memberRecordCustomFieldService;
    private OperatorCustomFieldService     operatorCustomFieldService;
    private PaymentCustomFieldService      paymentCustomFieldService;

    private MemberRecordTypeService        memberRecordTypeService;
    private TransferTypeService            transferTypeService;

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
    public void setMemberRecordTypeService(final MemberRecordTypeService memberRecordTypeService) {
        this.memberRecordTypeService = memberRecordTypeService;
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
    public void setTransferTypeService(final TransferTypeService transferTypeService) {
        this.transferTypeService = transferTypeService;
    }

    @Override
    protected ActionForward handleSubmit(final ActionContext context) throws Exception {
        final SetCustomFieldOrderForm form = context.getForm();
        CustomField.Nature nature;
        try {
            nature = CustomField.Nature.valueOf(form.getNature());
        } catch (final Exception e) {
            throw new ValidationException();
        }
        resolveService(nature).setOrder(Arrays.asList(form.getFieldIds()));
        final Map<String, Object> params = new HashMap<String, Object>();
        ActionForward forward;
        switch (nature) {
            case MEMBER_RECORD:
                params.put("memberRecordTypeId", form.getMemberRecordTypeId());
                forward = context.findForward("editMemberRecordType");
                break;
            case PAYMENT:
                final TransferType transferType = transferTypeService.load(form.getTransferTypeId());
                params.put("transferTypeId", transferType.getId());
                params.put("accountTypeId", transferType.getFrom().getId());
                forward = context.findForward("editTransferType");
                break;
            default:
                params.put("nature", nature);
                forward = context.getSuccessForward();
                break;
        }
        context.sendMessage("customField.orderModified");
        return ActionHelper.redirectWithParams(context.getRequest(), forward, params);
    }

    @Override
    @SuppressWarnings({ "unchecked", "rawtypes" })
    protected void prepareForm(final ActionContext context) throws Exception {
        final HttpServletRequest request = context.getRequest();
        final SetCustomFieldOrderForm form = context.getForm();
        CustomField.Nature nature;
        try {
            nature = CustomField.Nature.valueOf(form.getNature());
        } catch (final Exception e) {
            throw new ValidationException();
        }
        List<? extends CustomField> fields;
        switch (nature) {
            case OPERATOR:
                final Member member = (Member) context.getElement();
                fields = operatorCustomFieldService.list(member);
                break;
            case MEMBER_RECORD:
                final MemberRecordType memberRecordType = memberRecordTypeService.load(form.getMemberRecordTypeId());
                fields = memberRecordCustomFieldService.list(memberRecordType);
                request.setAttribute("memberRecordType", memberRecordType);
                break;
            case PAYMENT:
                final TransferType transferType = transferTypeService.load(form.getTransferTypeId());
                // Don't use the paymentCustomFieldService.list() because we need only the direct fields, not linked ones
                fields = (List<? extends CustomField>) transferType.getCustomFields();
                request.setAttribute("transferType", transferType);
                break;
            default:
                fields = ((BaseGlobalCustomFieldService) resolveService(nature)).list();
                break;
        }
        // Remove all fields with a parent one
        fields = new ArrayList<CustomField>(fields);
        for (final Iterator<? extends CustomField> it = fields.iterator(); it.hasNext();) {
            final CustomField customField = it.next();
            if (customField.getParent() != null) {
                it.remove();
            }
        }
        request.setAttribute("customFields", fields);
        request.setAttribute("nature", nature.name());
        super.prepareForm(context);
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
