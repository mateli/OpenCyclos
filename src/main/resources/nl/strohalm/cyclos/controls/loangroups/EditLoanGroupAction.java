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

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.access.AdminSystemPermission;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.entities.accounts.loans.LoanGroup;
import nl.strohalm.cyclos.entities.customization.fields.CustomFieldValue;
import nl.strohalm.cyclos.entities.customization.fields.LoanGroupCustomField;
import nl.strohalm.cyclos.entities.customization.fields.LoanGroupCustomFieldValue;
import nl.strohalm.cyclos.entities.groups.AdminGroup;
import nl.strohalm.cyclos.utils.ActionHelper;
import nl.strohalm.cyclos.utils.binding.BeanBinder;
import nl.strohalm.cyclos.utils.binding.BeanCollectionBinder;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.binding.PropertyBinder;
import nl.strohalm.cyclos.utils.conversion.HtmlConverter;
import nl.strohalm.cyclos.utils.conversion.IdConverter;
import nl.strohalm.cyclos.utils.conversion.ReferenceConverter;

import org.apache.struts.action.ActionForward;

/**
 * Action used to edit a loan group
 * @author luis
 */
public class EditLoanGroupAction extends BaseLoanGroupAction {

    private DataBinder<LoanGroup> readDataBinder;
    private DataBinder<LoanGroup> writeDataBinder;

    public DataBinder<LoanGroup> getReadDataBinder() {
        if (readDataBinder == null) {
            final BeanBinder<LoanGroup> binder = BeanBinder.instance(LoanGroup.class);
            binder.registerBinder("id", PropertyBinder.instance(Long.class, "id", IdConverter.instance()));
            binder.registerBinder("name", PropertyBinder.instance(String.class, "name"));
            binder.registerBinder("description", PropertyBinder.instance(String.class, "description"));
            readDataBinder = binder;
        }
        return readDataBinder;
    }

    public DataBinder<LoanGroup> getWriteDataBinder() {
        if (writeDataBinder == null) {

            final BeanBinder<? extends CustomFieldValue> customValueBinder = BeanBinder.instance(LoanGroupCustomFieldValue.class);
            customValueBinder.registerBinder("field", PropertyBinder.instance(LoanGroupCustomField.class, "field", ReferenceConverter.instance(LoanGroupCustomField.class)));
            customValueBinder.registerBinder("value", PropertyBinder.instance(String.class, "value", HtmlConverter.instance()));

            final BeanBinder<LoanGroup> binder = BeanBinder.instance(LoanGroup.class);
            binder.registerBinder("id", PropertyBinder.instance(Long.class, "id", IdConverter.instance()));
            binder.registerBinder("name", PropertyBinder.instance(String.class, "name"));
            binder.registerBinder("description", PropertyBinder.instance(String.class, "description"));
            binder.registerBinder("customValues", BeanCollectionBinder.instance(customValueBinder, "customValues"));
            writeDataBinder = binder;
        }
        return writeDataBinder;
    }

    @Override
    public ActionForward handleDisplay(final ActionContext context) throws Exception {
        prepareForm(context);
        final HttpServletRequest request = context.getRequest();
        final EditLoanGroupForm form = context.getForm();
        final LoanGroup loanGroup = (LoanGroup) request.getAttribute("loanGroup");
        boolean editable = false;
        if (context.isAdmin()) {
            editable = permissionService.hasPermission(AdminSystemPermission.LOAN_GROUPS_MANAGE);
        }
        if (!editable) {
            return ActionHelper.redirectWithParam(request, context.findForward("view"), "loanGroupId", loanGroup.getId());
        }
        if (loanGroup.getId() != null) {
            getReadDataBinder().writeAsString(form.getLoanGroup(), loanGroup);
        }
        request.setAttribute("editable", editable);

        AdminGroup adminGroup = context.getGroup();
        adminGroup = groupService.load(adminGroup.getId(), AdminGroup.Relationships.MANAGES_GROUPS);
        request.setAttribute("managesGroups", adminGroup.getManagesGroups());

        return context.getInputForward();
    }

    @Override
    protected void formAction(final ActionContext context) throws Exception {
        final EditLoanGroupForm form = context.getForm();
        final LoanGroup loanGroup = getWriteDataBinder().readFromString(form.getLoanGroup());
        final boolean isInsert = loanGroup.getId() == null;
        getLoanGroupService().save(loanGroup);
        context.sendMessage(isInsert ? "loanGroup.inserted" : "loanGroup.modified");
    }

    @Override
    protected void validateForm(final ActionContext context) {
        final EditLoanGroupForm form = context.getForm();
        final LoanGroup loanGroup = getWriteDataBinder().readFromString(form.getLoanGroup());
        getLoanGroupService().validate(loanGroup);
    }

}
