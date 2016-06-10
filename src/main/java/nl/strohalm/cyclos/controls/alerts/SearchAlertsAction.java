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
package nl.strohalm.cyclos.controls.alerts;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.access.AdminSystemPermission;
import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseQueryAction;
import nl.strohalm.cyclos.entities.EntityReference;
import nl.strohalm.cyclos.entities.alerts.Alert;
import nl.strohalm.cyclos.entities.alerts.AlertQuery;
import nl.strohalm.cyclos.entities.groups.AdminGroup;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.entities.settings.events.LocalSettingsEvent;
import nl.strohalm.cyclos.services.alerts.AlertService;
import nl.strohalm.cyclos.utils.binding.BeanBinder;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.binding.DataBinderHelper;
import nl.strohalm.cyclos.utils.binding.PropertyBinder;
import nl.strohalm.cyclos.utils.conversion.ReferenceConverter;
import nl.strohalm.cyclos.utils.query.QueryParameters;

/**
 * Action used to search alerts
 * @author luis
 */
public class SearchAlertsAction extends BaseQueryAction {

    private DataBinder<AlertQuery> dataBinder;
    private AlertService           alertService;

    public AlertService getAlertService() {
        return alertService;
    }

    public DataBinder<AlertQuery> getDataBinder() {
        if (dataBinder == null) {
            final LocalSettings localSettings = settingsService.getLocalSettings();
            final BeanBinder<AlertQuery> binder = BeanBinder.instance(AlertQuery.class);
            binder.registerBinder("type", PropertyBinder.instance(Alert.Type.class, "type"));
            binder.registerBinder("period", DataBinderHelper.periodBinder(localSettings, "period"));
            binder.registerBinder("member", PropertyBinder.instance(Member.class, "member", ReferenceConverter.instance(Member.class)));
            binder.registerBinder("pageParameters", DataBinderHelper.pageBinder());
            dataBinder = binder;
        }
        return dataBinder;
    }

    @Override
    public void onLocalSettingsUpdate(final LocalSettingsEvent event) {
        super.onLocalSettingsUpdate(event);
        dataBinder = null;
    }

    @Inject
    public void setAlertService(final AlertService alertService) {
        this.alertService = alertService;
    }

    @Override
    protected void executeQuery(final ActionContext context, final QueryParameters queryParameters) {
        final AlertQuery query = (AlertQuery) queryParameters;
        final List<? extends Alert> alerts = alertService.search(query);
        context.getRequest().setAttribute("alerts", alerts);
    }

    @Override
    protected QueryParameters prepareForm(final ActionContext context) {
        final HttpServletRequest request = context.getRequest();
        final SearchAlertsForm form = context.getForm();
        final AlertQuery query = getDataBinder().readFromString(form.getQuery());
        if (query.getMember() instanceof EntityReference) {
            query.setMember((Member) elementService.load(query.getMember().getId(), Element.Relationships.USER));
        }
        if (context.isAdmin()) {
            AdminGroup adminGroup = context.getGroup();
            adminGroup = groupService.load(adminGroup.getId(), AdminGroup.Relationships.MANAGES_GROUPS);
            query.setGroups(adminGroup.getManagesGroups());
        }
        query.setShowRemoved(true);
        final Collection<Alert.Type> types = new ArrayList<Alert.Type>();
        if (permissionService.hasPermission(AdminSystemPermission.ALERTS_VIEW_SYSTEM_ALERTS)) {
            types.add(Alert.Type.SYSTEM);
        }
        if (permissionService.hasPermission(AdminSystemPermission.ALERTS_VIEW_MEMBER_ALERTS)) {
            types.add(Alert.Type.MEMBER);
        }
        request.setAttribute("types", types);
        request.setAttribute("isMember", query.getType() == Alert.Type.MEMBER && query.getMember() == null);
        return query;
    }

}
