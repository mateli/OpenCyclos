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
package nl.strohalm.cyclos.controls.groups;

import java.util.List;

import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseAjaxAction;
import nl.strohalm.cyclos.entities.accounts.MemberAccountType;
import nl.strohalm.cyclos.entities.accounts.transactions.PaymentFilter;
import nl.strohalm.cyclos.entities.groups.AdminGroup;
import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.entities.groups.GroupFilter;
import nl.strohalm.cyclos.entities.groups.GroupQuery;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.utils.binding.BeanBinder;
import nl.strohalm.cyclos.utils.binding.BeanCollectionBinder;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.binding.PropertyBinder;
import nl.strohalm.cyclos.utils.binding.SimpleCollectionBinder;

/**
 * Searches groups and returns the list as an JSON
 * @author jefferson
 */
public class SearchGroupsAjaxAction extends BaseAjaxAction {

    private DataBinder<?>          dataBinder;
    private DataBinder<GroupQuery> groupQueryDataBinder;

    public DataBinder<?> getDataBinder() {
        if (dataBinder == null) {
            final BeanBinder<MemberGroup> binder = BeanBinder.instance(MemberGroup.class);
            binder.registerBinder("id", PropertyBinder.instance(Long.class, "id"));
            binder.registerBinder("name", PropertyBinder.instance(String.class, "name"));
            dataBinder = BeanCollectionBinder.instance(binder);
        }
        return dataBinder;
    }

    public DataBinder<GroupQuery> getGroupQueryDataBinder() {
        if (groupQueryDataBinder == null) {
            final BeanBinder<GroupQuery> binder = BeanBinder.instance(GroupQuery.class);
            binder.registerBinder("memberAccountType", PropertyBinder.instance(MemberAccountType.class, "accountTypeId"));
            binder.registerBinder("paymentFilter", PropertyBinder.instance(PaymentFilter.class, "paymentFilterId"));
            binder.registerBinder("groupFilters", SimpleCollectionBinder.instance(GroupFilter.class, "groupFilterIds"));
            binder.registerBinder("naturesCollection", SimpleCollectionBinder.instance(Group.Nature.class, "natures"));
            binder.registerBinder("statusCollection", SimpleCollectionBinder.instance(Group.Status.class, "status"));
            groupQueryDataBinder = binder;
        }
        return groupQueryDataBinder;
    }

    @Override
    protected ContentType contentType() {
        return ContentType.JSON;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void renderContent(final ActionContext context) throws Exception {
        final SearchGroupsAjaxForm form = context.getForm();
        final GroupQuery groupQuery = getGroupQueryDataBinder().readFromString(form);
        if (context.isAdmin()) {
            groupQuery.setManagedBy((AdminGroup) context.getGroup());
        }
        final List<MemberGroup> groups = (List<MemberGroup>) groupService.search(groupQuery);
        final String json = getDataBinder().readAsString(groups);
        responseHelper.writeJSON(context.getResponse(), json);
    }

}
