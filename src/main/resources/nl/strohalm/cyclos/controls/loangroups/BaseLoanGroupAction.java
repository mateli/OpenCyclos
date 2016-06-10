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
package nl.strohalm.cyclos.controls.loangroups;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseFormAction;
import nl.strohalm.cyclos.entities.accounts.loans.LoanGroup;
import nl.strohalm.cyclos.entities.customization.fields.LoanGroupCustomField;
import nl.strohalm.cyclos.services.customization.LoanGroupCustomFieldService;
import nl.strohalm.cyclos.services.loangroups.LoanGroupService;
import nl.strohalm.cyclos.utils.CustomFieldHelper;

/**
 * Action used to edit a loan group
 * @author luis
 */
public class BaseLoanGroupAction extends BaseFormAction {

    private LoanGroupService            loanGroupService;
    private LoanGroupCustomFieldService loanGroupCustomFieldService;

    private CustomFieldHelper           customFieldHelper;

    public LoanGroupCustomFieldService getLoanGroupCustomFieldService() {
        return loanGroupCustomFieldService;
    }

    public LoanGroupService getLoanGroupService() {
        return loanGroupService;
    }

    @Inject
    public void setCustomFieldHelper(final CustomFieldHelper customFieldHelper) {
        this.customFieldHelper = customFieldHelper;
    }

    @Inject
    public void setLoanGroupCustomFieldService(final LoanGroupCustomFieldService loanGroupCustomFieldService) {
        this.loanGroupCustomFieldService = loanGroupCustomFieldService;
    }

    @Inject
    public void setLoanGroupService(final LoanGroupService loanGroupService) {
        this.loanGroupService = loanGroupService;
    }

    @Override
    protected void prepareForm(final ActionContext context) throws Exception {
        final HttpServletRequest request = context.getRequest();
        final EditLoanGroupForm form = context.getForm();
        final long id = form.getLoanGroupId();
        LoanGroup loanGroup;
        final List<LoanGroupCustomField> customFields = loanGroupCustomFieldService.list();
        if (id > 0L) {
            loanGroup = loanGroupService.load(id, LoanGroup.Relationships.CUSTOM_VALUES, LoanGroup.Relationships.MEMBERS);
        } else {
            loanGroup = new LoanGroup();
        }
        request.setAttribute("loanGroup", loanGroup);
        request.setAttribute("customFields", customFieldHelper.buildEntries(customFields, loanGroup.getCustomValues()));
    }
}
