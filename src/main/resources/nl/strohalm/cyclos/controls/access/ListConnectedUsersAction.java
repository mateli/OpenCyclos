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
package nl.strohalm.cyclos.controls.access;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.access.AdminAdminPermission;
import nl.strohalm.cyclos.access.AdminMemberPermission;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseQueryAction;
import nl.strohalm.cyclos.entities.access.Session;
import nl.strohalm.cyclos.entities.access.SessionQuery;
import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.utils.RequestHelper;
import nl.strohalm.cyclos.utils.binding.BeanBinder;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.binding.DataBinderHelper;
import nl.strohalm.cyclos.utils.binding.SimpleCollectionBinder;
import nl.strohalm.cyclos.utils.query.QueryParameters;

/**
 * Action used to search for the connected users
 * @author luis
 */
public class ListConnectedUsersAction extends BaseQueryAction {

    private DataBinder<SessionQuery> dataBinderAsAdmin;
    private DataBinder<SessionQuery> dataBinderAdMember;

    public DataBinder<SessionQuery> getDataBinderAsAdmin() {
        if (dataBinderAsAdmin == null) {
            final BeanBinder<SessionQuery> binder = BeanBinder.instance(SessionQuery.class);
            binder.registerBinder("natures", SimpleCollectionBinder.instance(Group.Nature.class, "natures"));
            binder.registerBinder("groups", SimpleCollectionBinder.instance(Group.class, "groups"));
            binder.registerBinder("pageParameters", DataBinderHelper.pageBinder());
            dataBinderAsAdmin = binder;
        }
        return dataBinderAsAdmin;
    }

    public DataBinder<SessionQuery> getDataBinderAsMember() {
        if (dataBinderAdMember == null) {
            final BeanBinder<SessionQuery> binder = BeanBinder.instance(SessionQuery.class);
            binder.registerBinder("pageParameters", DataBinderHelper.pageBinder());
            dataBinderAdMember = binder;
        }
        return dataBinderAdMember;
    }

    @Override
    protected void executeQuery(final ActionContext context, final QueryParameters queryParameters) {
        final SessionQuery query = (SessionQuery) queryParameters;
        final List<Session> sessions = accessService.searchSessions(query);
        context.getRequest().setAttribute("sessions", sessions);
    }

    @Override
    protected QueryParameters prepareForm(final ActionContext context) {
        final ListConnectedUsersForm form = context.getForm();
        final HttpServletRequest request = context.getRequest();

        DataBinder<SessionQuery> dataBinder;
        if (context.isAdmin()) {
            dataBinder = getDataBinderAsAdmin();
        } else {
            dataBinder = getDataBinderAsMember();
        }
        final SessionQuery query = dataBinder.readFromString(form.getQuery());
        if (context.isAdmin()) {
            RequestHelper.storeEnum(request, Group.Nature.class, "groupNatures");
        } else {
            query.setNatures(null);
            query.setGroups(null);
            query.setMember(context.getMember());
        }
        request.setAttribute("canDisconnectAdmin", permissionService.hasPermission(AdminAdminPermission.ACCESS_DISCONNECT));
        request.setAttribute("canDisconnectMember", permissionService.hasPermission(AdminMemberPermission.ACCESS_DISCONNECT));
        return query;
    }

    @Override
    protected boolean willExecuteQuery(final ActionContext context, final QueryParameters queryParameters) throws Exception {
        return true;
    }

}
