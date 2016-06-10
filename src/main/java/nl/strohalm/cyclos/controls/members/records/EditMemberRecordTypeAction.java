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
package nl.strohalm.cyclos.controls.members.records;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.access.AdminSystemPermission;
import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseFormAction;
import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.entities.groups.GroupQuery;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.members.records.MemberRecordType;
import nl.strohalm.cyclos.services.elements.MemberRecordTypeService;
import nl.strohalm.cyclos.utils.ActionHelper;
import nl.strohalm.cyclos.utils.RequestHelper;
import nl.strohalm.cyclos.utils.binding.BeanBinder;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.binding.PropertyBinder;
import nl.strohalm.cyclos.utils.binding.SimpleCollectionBinder;
import nl.strohalm.cyclos.utils.conversion.IdConverter;

import org.apache.struts.action.ActionForward;

/**
 * Action used to edit a member record type
 * @author Jefferson Magno
 */
public class EditMemberRecordTypeAction extends BaseFormAction {

    private MemberRecordTypeService      memberRecordTypeService;
    private DataBinder<MemberRecordType> dataBinder;

    @Inject
    public void setMemberRecordTypeService(final MemberRecordTypeService memberRecordTypeService) {
        this.memberRecordTypeService = memberRecordTypeService;
    }

    @Override
    protected ActionForward handleSubmit(final ActionContext context) throws Exception {
        final EditMemberRecordTypeForm form = context.getForm();
        MemberRecordType memberRecordType = getDataBinder().readFromString(form.getMemberRecordType());
        final boolean isInsert = memberRecordType.isTransient();
        memberRecordType = memberRecordTypeService.save(memberRecordType);
        if (isInsert) {
            context.sendMessage("memberRecordType.inserted");
        } else {
            context.sendMessage("memberRecordType.modified");
        }
        return ActionHelper.redirectWithParam(context.getRequest(), context.getSuccessForward(), "memberRecordTypeId", memberRecordType.getId());
    }

    @Override
    protected void prepareForm(final ActionContext context) throws Exception {
        final HttpServletRequest request = context.getRequest();
        final EditMemberRecordTypeForm form = context.getForm();

        final long id = form.getMemberRecordTypeId();
        final boolean isInsert = id <= 0L;
        boolean editable = permissionService.hasPermission(AdminSystemPermission.MEMBER_RECORD_TYPES_MANAGE);
        MemberRecordType memberRecordType;
        if (isInsert) {
            memberRecordType = new MemberRecordType();
            editable = true;
        } else {
            memberRecordType = memberRecordTypeService.load(id, MemberRecordType.Relationships.FIELDS, MemberRecordType.Relationships.GROUPS);
        }
        getDataBinder().writeAsString(form.getMemberRecordType(), memberRecordType);
        request.setAttribute("memberRecordType", memberRecordType);
        request.setAttribute("editable", editable);
        request.setAttribute("isInsert", isInsert);

        // Search groups and send to JSP
        final GroupQuery groupQuery = new GroupQuery();
        groupQuery.setNatures(Group.Nature.MEMBER, Group.Nature.BROKER, Group.Nature.ADMIN);
        groupQuery.setStatus(Group.Status.NORMAL);
        final List<? extends Group> groups = groupService.search(groupQuery);
        request.setAttribute("groups", groups);

        // Send layouts enum to JSP
        RequestHelper.storeEnum(request, MemberRecordType.Layout.class, "layouts");
    }

    @Override
    protected void validateForm(final ActionContext context) {
        final EditMemberRecordTypeForm form = context.getForm();
        final MemberRecordType memberRecordType = getDataBinder().readFromString(form.getMemberRecordType());
        memberRecordTypeService.validate(memberRecordType);
    }

    private DataBinder<MemberRecordType> getDataBinder() {
        if (dataBinder == null) {
            final BeanBinder<MemberRecordType> binder = BeanBinder.instance(MemberRecordType.class);
            binder.registerBinder("id", PropertyBinder.instance(Long.class, "id", IdConverter.instance()));
            binder.registerBinder("name", PropertyBinder.instance(String.class, "name"));
            binder.registerBinder("label", PropertyBinder.instance(String.class, "label"));
            binder.registerBinder("description", PropertyBinder.instance(String.class, "description"));
            binder.registerBinder("groups", SimpleCollectionBinder.instance(MemberGroup.class, "groups"));
            binder.registerBinder("layout", PropertyBinder.instance(MemberRecordType.Layout.class, "layout"));
            binder.registerBinder("editable", PropertyBinder.instance(Boolean.TYPE, "editable"));
            binder.registerBinder("showMenuItem", PropertyBinder.instance(Boolean.TYPE, "showMenuItem"));
            dataBinder = binder;
        }
        return dataBinder;
    }

}
