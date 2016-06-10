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
package nl.strohalm.cyclos.controls.groups.groupFilters;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseFormAction;
import nl.strohalm.cyclos.entities.customization.files.CustomizedFileQuery;
import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.entities.groups.GroupFilter;
import nl.strohalm.cyclos.entities.groups.GroupQuery;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.services.customization.CustomizedFileService;
import nl.strohalm.cyclos.services.groups.GroupFilterService;
import nl.strohalm.cyclos.utils.ActionHelper;
import nl.strohalm.cyclos.utils.binding.BeanBinder;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.binding.PropertyBinder;
import nl.strohalm.cyclos.utils.binding.SimpleCollectionBinder;
import nl.strohalm.cyclos.utils.conversion.IdConverter;

import org.apache.struts.action.ActionForward;

/**
 * Action used to edit a group filter
 * @author jefferson
 */
public class EditGroupFilterAction extends BaseFormAction {

    private GroupFilterService      groupFilterService;
    private DataBinder<GroupFilter> dataBinder;
    private CustomizedFileService   customizedFileService;

    public DataBinder<GroupFilter> getDataBinder() {
        if (dataBinder == null) {
            final BeanBinder<GroupFilter> binder = BeanBinder.instance(GroupFilter.class);
            binder.registerBinder("id", PropertyBinder.instance(Long.class, "id", IdConverter.instance()));
            binder.registerBinder("name", PropertyBinder.instance(String.class, "name"));
            binder.registerBinder("rootUrl", PropertyBinder.instance(String.class, "rootUrl"));
            binder.registerBinder("loginPageName", PropertyBinder.instance(String.class, "loginPageName"));
            binder.registerBinder("containerUrl", PropertyBinder.instance(String.class, "containerUrl"));
            binder.registerBinder("description", PropertyBinder.instance(String.class, "description"));
            binder.registerBinder("showInProfile", PropertyBinder.instance(Boolean.TYPE, "showInProfile"));
            binder.registerBinder("groups", SimpleCollectionBinder.instance(MemberGroup.class, "groups"));
            binder.registerBinder("viewableBy", SimpleCollectionBinder.instance(MemberGroup.class, "viewableBy"));
            dataBinder = binder;
        }
        return dataBinder;
    }

    @Inject
    public void setCustomizedFileService(final CustomizedFileService customizedFileService) {
        this.customizedFileService = customizedFileService;
    }

    @Inject
    public void setGroupFilterService(final GroupFilterService groupFilterService) {
        this.groupFilterService = groupFilterService;
    }

    @Override
    protected ActionForward handleSubmit(final ActionContext context) throws Exception {
        final EditGroupFilterForm form = context.getForm();
        GroupFilter groupFilter = getDataBinder().readFromString(form.getGroupFilter());
        final boolean isInsert = (groupFilter.getId() == null);
        groupFilter = groupFilterService.save(groupFilter);
        context.sendMessage(isInsert ? "groupFilter.inserted" : "groupFilter.modified");
        final Map<String, Object> params = new HashMap<String, Object>();
        params.put("groupFilterId", groupFilter.getId());
        return ActionHelper.redirectWithParams(context.getRequest(), context.getSuccessForward(), params);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void prepareForm(final ActionContext context) throws Exception {
        final HttpServletRequest request = context.getRequest();
        final EditGroupFilterForm form = context.getForm();
        final long id = form.getGroupFilterId();
        final boolean isInsert = (id <= 0L);
        if (!isInsert) {
            final GroupFilter groupFilter = groupFilterService.load(id, GroupFilter.Relationships.GROUPS, GroupFilter.Relationships.VIEWABLE_BY, GroupFilter.Relationships.CUSTOMIZED_FILES);
            request.setAttribute("groupFilter", groupFilter);
            getDataBinder().writeAsString(form.getGroupFilter(), groupFilter);

            // Retrieve the associated customized files
            final CustomizedFileQuery cfQuery = new CustomizedFileQuery();
            cfQuery.setGroupFilter(groupFilter);
            request.setAttribute("customizedFiles", customizedFileService.search(cfQuery));
        }

        // Get the groups that can belong to this group filter
        final GroupQuery query = new GroupQuery();
        query.setNatures(Group.Nature.MEMBER, Group.Nature.BROKER);
        final Collection<MemberGroup> groups = (Collection<MemberGroup>) groupService.search(query);

        // Get the groups that can view this group filter
        final Collection<MemberGroup> viewableBy = groups;

        request.setAttribute("isInsert", isInsert);
        request.setAttribute("groups", groups);
        request.setAttribute("viewableBy", viewableBy);
        request.setAttribute("canManageCustomizedFiles", customizedFileService.canViewOrManageInGroupFilters());
    }

    @Override
    protected void validateForm(final ActionContext context) {
        final EditGroupFilterForm form = context.getForm();
        final GroupFilter groupFilter = getDataBinder().readFromString(form.getGroupFilter());
        groupFilterService.validate(groupFilter);
    }

}
