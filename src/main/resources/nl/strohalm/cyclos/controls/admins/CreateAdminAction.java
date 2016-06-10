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
package nl.strohalm.cyclos.controls.admins;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.elements.CreateElementAction;
import nl.strohalm.cyclos.entities.access.AdminUser;
import nl.strohalm.cyclos.entities.customization.fields.AdminCustomField;
import nl.strohalm.cyclos.entities.customization.fields.AdminCustomFieldValue;
import nl.strohalm.cyclos.entities.groups.AdminGroup;
import nl.strohalm.cyclos.entities.members.Administrator;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.services.customization.AdminCustomFieldService;
import nl.strohalm.cyclos.services.elements.WhenSaving;
import nl.strohalm.cyclos.utils.ActionHelper;
import nl.strohalm.cyclos.utils.CustomFieldHelper;
import nl.strohalm.cyclos.utils.validation.ValidationException;

import org.apache.struts.action.ActionForward;

/**
 * Action used to create administrators
 * @author luis
 */
public class CreateAdminAction extends CreateElementAction<Administrator> {

    private AdminCustomFieldService adminCustomFieldService;

    private CustomFieldHelper       customFieldHelper;

    public AdminCustomFieldService getAdminCustomFieldService() {
        return adminCustomFieldService;
    }

    @Inject
    public void setAdminCustomFieldService(final AdminCustomFieldService adminCustomFieldService) {
        this.adminCustomFieldService = adminCustomFieldService;
    }

    @Inject
    public void setCustomFieldHelper(final CustomFieldHelper customFieldHelper) {
        this.customFieldHelper = customFieldHelper;
    }

    @Override
    protected ActionForward create(final Element element, final ActionContext context) {
        final CreateAdminForm form = context.getForm();
        Administrator administrator = (Administrator) element;
        administrator = (Administrator) elementService.register(administrator, form.isForceChangePassword(), context.getRequest().getRemoteAddr());
        String paramName;
        Object paramValue;
        ActionForward forward;
        if (form.isOpenProfile()) {
            paramName = "adminId";
            paramValue = administrator.getId();
            forward = context.findForward("profile");
        } else {
            context.sendMessage("createAdmin.created");
            paramName = "groupId";
            paramValue = administrator.getGroup().getId();
            forward = context.findForward("new");
        }
        return ActionHelper.redirectWithParam(context.getRequest(), forward, paramName, paramValue);
    }

    @Override
    protected void formAction(final ActionContext context) throws Exception {
        super.formAction(context);
        context.sendMessage("createAdmin.created");
    }

    @Override
    @SuppressWarnings("unchecked")
    protected Class<AdminCustomField> getCustomFieldClass() {
        return AdminCustomField.class;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected Class<AdminCustomFieldValue> getCustomFieldValueClass() {
        return AdminCustomFieldValue.class;
    }

    @Override
    protected Class<Administrator> getElementClass() {
        return Administrator.class;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected Class<AdminGroup> getGroupClass() {
        return AdminGroup.class;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected Class<AdminUser> getUserClass() {
        return AdminUser.class;
    }

    @Override
    protected void prepareForm(final ActionContext context) throws Exception {
        final HttpServletRequest request = context.getRequest();
        final CreateAdminForm form = context.getForm();

        // Get the initial group
        final long groupId = form.getGroupId();
        if (groupId <= 0L) {
            throw new ValidationException();
        }
        final AdminGroup group = groupService.load(groupId);

        // Get the custom fields visible to that group
        final List<AdminCustomField> customFields = customFieldHelper.onlyForGroup(adminCustomFieldService.list(), group);
        request.setAttribute("customFields", customFields);

        request.setAttribute("group", group);
    }

    @Override
    protected void runValidation(final ActionContext context, final Element element) {
        elementService.validate(element, WhenSaving.ADMIN_BY_ADMIN, true);
    }
}
