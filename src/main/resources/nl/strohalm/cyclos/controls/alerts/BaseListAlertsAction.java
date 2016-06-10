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

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseAction;
import nl.strohalm.cyclos.entities.alerts.Alert;
import nl.strohalm.cyclos.entities.alerts.Alert.Type;
import nl.strohalm.cyclos.entities.alerts.AlertQuery;
import nl.strohalm.cyclos.entities.alerts.MemberAlert;
import nl.strohalm.cyclos.entities.groups.AdminGroup;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.services.alerts.AlertService;
import nl.strohalm.cyclos.utils.RelationshipHelper;
import nl.strohalm.cyclos.utils.query.PageParameters;
import nl.strohalm.cyclos.utils.query.QueryParameters.ResultType;

import org.apache.struts.action.ActionForward;

/**
 * Base action for listing alerts
 * @author luis
 */
public abstract class BaseListAlertsAction extends BaseAction {

    private static final int MAX_ALERTS = 200;

    private AlertService     alertService;

    public AlertService getAlertService() {
        return alertService;
    }

    @Inject
    public void setAlertService(final AlertService alertService) {
        this.alertService = alertService;
    }

    @Override
    protected ActionForward executeAction(final ActionContext context) throws Exception {
        final HttpServletRequest request = context.getRequest();

        final AlertQuery query = new AlertQuery();
        query.setResultType(ResultType.ITERATOR);
        query.setPageParameters(PageParameters.max(MAX_ALERTS));
        query.setShowRemoved(false);

        final Type type = getType();
        query.setType(type);

        if (Type.MEMBER.equals(type) && context.isAdmin()) {
            AdminGroup adminGroup = context.getGroup();
            adminGroup = groupService.load(adminGroup.getId(), AdminGroup.Relationships.MANAGES_GROUPS);
            query.setGroups(adminGroup.getManagesGroups());

            query.fetch(RelationshipHelper.nested(MemberAlert.Relationships.MEMBER, Element.Relationships.USER));
        }

        final List<? extends Alert> alerts = alertService.search(query);
        request.setAttribute("alerts", alerts);
        request.setAttribute("isSystem", type == Type.SYSTEM);
        request.setAttribute("isMember", type == Type.MEMBER);
        return context.getInputForward();
    }

    protected abstract Alert.Type getType();

}
