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

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseAction;
import nl.strohalm.cyclos.entities.customization.fields.CustomField;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.services.customization.AdCustomFieldService;
import nl.strohalm.cyclos.services.customization.AdminCustomFieldService;
import nl.strohalm.cyclos.services.customization.BaseGlobalCustomFieldService;
import nl.strohalm.cyclos.services.customization.LoanGroupCustomFieldService;
import nl.strohalm.cyclos.services.customization.MemberCustomFieldService;
import nl.strohalm.cyclos.services.customization.OperatorCustomFieldService;
import nl.strohalm.cyclos.utils.validation.ValidationException;

import org.apache.struts.action.ActionForward;

/**
 * Action used to list custom fields
 * @author luis
 */
public class ListCustomFieldsAction extends BaseAction {

    private AdCustomFieldService        adCustomFieldService;
    private AdminCustomFieldService     adminCustomFieldService;
    private LoanGroupCustomFieldService loanGroupCustomFieldService;
    private MemberCustomFieldService    memberCustomFieldService;
    private OperatorCustomFieldService  operatorCustomFieldService;

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
    public void setOperatorCustomFieldService(final OperatorCustomFieldService operatorCustomFieldService) {
        this.operatorCustomFieldService = operatorCustomFieldService;
    }

    @Override
    protected ActionForward executeAction(final ActionContext context) throws Exception {
        final HttpServletRequest request = context.getRequest();
        final ListCustomFieldsForm form = context.getForm();
        CustomField.Nature nature;
        try {
            nature = CustomField.Nature.valueOf(form.getNature());
        } catch (final Exception e) {
            throw new ValidationException();
        }
        List<? extends CustomField> fields = null;
        switch (nature) {
            case MEMBER_RECORD:
            case PAYMENT:
                // Member record and payment fields are listed in their owner entity details, not here
                throw new ValidationException();
            case OPERATOR:
                final Member member = (Member) context.getElement();
                fields = operatorCustomFieldService.list(member);
                break;
            default:
                fields = resolveGlobalService(nature).list();
        }
        request.setAttribute("customFields", fields);
        request.setAttribute("nature", nature.name());
        return context.getInputForward();
    }

    @SuppressWarnings("unchecked")
    private <CF extends CustomField> BaseGlobalCustomFieldService<CF> resolveGlobalService(final CustomField.Nature nature) {
        switch (nature) {
            case AD:
                return (BaseGlobalCustomFieldService<CF>) adCustomFieldService;
            case ADMIN:
                return (BaseGlobalCustomFieldService<CF>) adminCustomFieldService;
            case LOAN_GROUP:
                return (BaseGlobalCustomFieldService<CF>) loanGroupCustomFieldService;
            case MEMBER:
                return (BaseGlobalCustomFieldService<CF>) memberCustomFieldService;
        }
        return null;
    }

}
