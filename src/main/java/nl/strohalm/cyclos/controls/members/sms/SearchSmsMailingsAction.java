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
package nl.strohalm.cyclos.controls.members.sms;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.access.AdminMemberPermission;
import nl.strohalm.cyclos.access.BrokerPermission;
import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseQueryAction;
import nl.strohalm.cyclos.entities.groups.AdminGroup;
import nl.strohalm.cyclos.entities.groups.GroupQuery;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.entities.settings.events.LocalSettingsChangeListener;
import nl.strohalm.cyclos.entities.settings.events.LocalSettingsEvent;
import nl.strohalm.cyclos.entities.sms.SmsMailing;
import nl.strohalm.cyclos.entities.sms.SmsMailingQuery;
import nl.strohalm.cyclos.services.sms.SmsMailingService;
import nl.strohalm.cyclos.utils.RelationshipHelper;
import nl.strohalm.cyclos.utils.binding.BeanBinder;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.binding.DataBinderHelper;
import nl.strohalm.cyclos.utils.binding.PropertyBinder;
import nl.strohalm.cyclos.utils.query.QueryParameters;

/**
 * Action used to search for sms mailings
 * 
 * @author luis
 */
public class SearchSmsMailingsAction extends BaseQueryAction implements LocalSettingsChangeListener {
    private DataBinder<SmsMailingQuery> dataBinder;
    private SmsMailingService           smsMailingService;

    @Override
    public void onLocalSettingsUpdate(final LocalSettingsEvent event) {
        super.onLocalSettingsUpdate(event);
        dataBinder = null;
    }

    @Inject
    public void setSmsMailingService(final SmsMailingService smsMailingService) {
        this.smsMailingService = smsMailingService;
    }

    @Override
    protected void executeQuery(final ActionContext context, final QueryParameters queryParameters) {
        final SmsMailingQuery query = (SmsMailingQuery) queryParameters;
        query.fetch(SmsMailing.Relationships.BY, SmsMailing.Relationships.GROUPS);
        final List<SmsMailing> smsMailings = smsMailingService.search(query);
        final HttpServletRequest request = context.getRequest();
        request.setAttribute("smsMailings", smsMailings);
    }

    @Override
    protected QueryParameters prepareForm(final ActionContext context) {
        final SearchSmsMailingsForm form = context.getForm();
        final HttpServletRequest request = context.getRequest();
        final SmsMailingQuery query = getDataBinder().readFromString(form.getQuery());

        boolean viewFree;
        boolean viewPaid;
        boolean canSend;
        if (context.isAdmin()) {
            viewPaid = viewFree = permissionService.hasPermission(AdminMemberPermission.SMS_MAILINGS_VIEW); // 2 assignments
            canSend = permissionService.hasPermission(AdminMemberPermission.SMS_MAILINGS_FREE_SMS_MAILINGS) || permissionService.hasPermission(AdminMemberPermission.SMS_MAILINGS_PAID_SMS_MAILINGS);

            final GroupQuery groupQuery = new GroupQuery();
            groupQuery.setManagedBy((AdminGroup) context.getGroup());
            groupQuery.setOnlyActive(true);
            request.setAttribute("groups", groupService.search(groupQuery));
        } else {
            viewFree = permissionService.hasPermission(BrokerPermission.SMS_MAILINGS_FREE_SMS_MAILINGS);
            viewPaid = permissionService.hasPermission(BrokerPermission.SMS_MAILINGS_PAID_SMS_MAILINGS);
            canSend = viewFree || viewPaid; // At least one permission (free / paid) the broker has
        }
        // Ensure to fetch the member, so the name / username will be displayed, if one is selected
        if (query.getMember() != null) {
            query.setMember((Member) elementService.load(query.getMember().getId(), Element.Relationships.USER));
        }
        request.setAttribute("viewFree", viewFree);
        request.setAttribute("viewPaid", viewPaid);
        request.setAttribute("canSend", canSend);
        query.fetch(RelationshipHelper.nested(SmsMailing.Relationships.BY, Element.Relationships.GROUP));
        return query;
    }

    @Override
    protected boolean willExecuteQuery(final ActionContext context, final QueryParameters queryParameters) throws Exception {
        return true;
    }

    private DataBinder<SmsMailingQuery> getDataBinder() {
        if (dataBinder == null) {
            final LocalSettings settings = settingsService.getLocalSettings();
            final BeanBinder<SmsMailingQuery> binder = BeanBinder.instance(SmsMailingQuery.class);
            binder.registerBinder("period", DataBinderHelper.periodBinder(settings, "period"));
            binder.registerBinder("pageParameters", DataBinderHelper.pageBinder());
            binder.registerBinder("recipient", PropertyBinder.instance(SmsMailingQuery.Recipient.class, "recipient"));
            binder.registerBinder("group", PropertyBinder.instance(MemberGroup.class, "group"));
            binder.registerBinder("member", PropertyBinder.instance(Member.class, "member"));
            dataBinder = binder;
        }
        return dataBinder;
    }

}
