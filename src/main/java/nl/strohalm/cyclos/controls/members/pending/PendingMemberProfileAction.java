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
package nl.strohalm.cyclos.controls.members.pending;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.access.AdminMemberPermission;
import nl.strohalm.cyclos.access.BrokerPermission;
import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseFormAction;
import nl.strohalm.cyclos.entities.customization.fields.CustomField;
import nl.strohalm.cyclos.entities.customization.fields.MemberCustomField;
import nl.strohalm.cyclos.entities.customization.fields.MemberCustomFieldValue;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.members.PendingMember;
import nl.strohalm.cyclos.services.customization.MemberCustomFieldService;
import nl.strohalm.cyclos.utils.ActionHelper;
import nl.strohalm.cyclos.utils.CustomFieldHelper;
import nl.strohalm.cyclos.utils.binding.BeanBinder;
import nl.strohalm.cyclos.utils.binding.BeanCollectionBinder;
import nl.strohalm.cyclos.utils.binding.PropertyBinder;
import nl.strohalm.cyclos.utils.conversion.HtmlConverter;
import nl.strohalm.cyclos.utils.conversion.ReferenceConverter;
import nl.strohalm.cyclos.utils.validation.ValidationException;

import org.apache.struts.action.ActionForward;

/**
 * Action used to view / edit a pending member profile
 * 
 * @author luis
 */
public class PendingMemberProfileAction extends BaseFormAction {

    private MemberCustomFieldService  memberCustomFieldService;
    private BeanBinder<PendingMember> dataBinder;

    private CustomFieldHelper         customFieldHelper;

    public BeanBinder<PendingMember> getDataBinder() {
        if (dataBinder == null) {
            final BeanBinder<MemberCustomFieldValue> customValueBinder = BeanBinder.instance(MemberCustomFieldValue.class);
            customValueBinder.registerBinder("field", PropertyBinder.instance(CustomField.class, "field", ReferenceConverter.instance(CustomField.class)));
            customValueBinder.registerBinder("value", PropertyBinder.instance(String.class, "value", HtmlConverter.instance()));
            customValueBinder.registerBinder("hidden", PropertyBinder.instance(Boolean.TYPE, "hidden"));

            final BeanBinder<PendingMember> binder = BeanBinder.instance(PendingMember.class);
            binder.registerBinder("id", PropertyBinder.instance(Long.class, "id"));
            binder.registerBinder("username", PropertyBinder.instance(String.class, "username"));
            binder.registerBinder("name", PropertyBinder.instance(String.class, "name"));
            binder.registerBinder("broker", PropertyBinder.instance(Member.class, "broker"));
            binder.registerBinder("email", PropertyBinder.instance(String.class, "email"));
            binder.registerBinder("hideEmail", PropertyBinder.instance(Boolean.TYPE, "hideEmail"));
            binder.registerBinder("customValues", BeanCollectionBinder.instance(customValueBinder, "customValues"));

            dataBinder = binder;
        }
        return dataBinder;
    }

    @Inject
    public void setCustomFieldHelper(final CustomFieldHelper customFieldHelper) {
        this.customFieldHelper = customFieldHelper;
    }

    @Inject
    public void setMemberCustomFieldService(final MemberCustomFieldService memberCustomFieldService) {
        this.memberCustomFieldService = memberCustomFieldService;
    }

    @Override
    protected ActionForward handleSubmit(final ActionContext context) throws Exception {
        final PendingMemberProfileForm form = context.getForm();
        PendingMember pendingMember = elementService.loadPendingMember(form.getPendingMemberId());
        getDataBinder().readInto(pendingMember, form.getPendingMember(), true);
        if (context.isBroker()) {
            final Member loggedBroker = context.getElement();
            pendingMember.setBroker(loggedBroker);
        }
        pendingMember = elementService.update(pendingMember);
        context.sendMessage("pendingMember.updated");
        return ActionHelper.redirectWithParam(context.getRequest(), context.getSuccessForward(), "pendingMemberId", pendingMember.getId());
    }

    @Override
    protected void prepareForm(final ActionContext context) throws Exception {
        final HttpServletRequest request = context.getRequest();
        final PendingMemberProfileForm form = context.getForm();
        final long id = form.getPendingMemberId();
        if (id <= 0L) {
            throw new ValidationException();
        }
        final PendingMember pendingMember = elementService.loadPendingMember(id, PendingMember.Relationships.values());
        request.setAttribute("pendingMember", pendingMember);

        final List<MemberCustomField> customFields = customFieldHelper.onlyForGroup(memberCustomFieldService.list(), pendingMember.getMemberGroup());
        for (final Iterator<MemberCustomField> iterator = customFields.iterator(); iterator.hasNext();) {
            final MemberCustomField customField = iterator.next();
            if (!customField.getVisibilityAccess().granted(context.getGroup(), false, context.isBroker(), true, false)) {
                iterator.remove();
            }
        }
        request.setAttribute("customFields", customFieldHelper.buildEntries(customFields, pendingMember.getCustomValues()));

        boolean editable = false;
        if (context.isAdmin()) {
            editable = permissionService.hasPermission(AdminMemberPermission.MEMBERS_MANAGE_PENDING);
        } else if (context.isBroker()) {
            editable = permissionService.hasPermission(BrokerPermission.MEMBERS_MANAGE_PENDING);
        }
        request.setAttribute("editable", editable);
    }

    @Override
    protected void validateForm(final ActionContext context) {
        final PendingMemberProfileForm form = context.getForm();
        final PendingMember pendingMember = getDataBinder().readFromString(form.getPendingMember());
        elementService.validate(pendingMember);
    }

}
