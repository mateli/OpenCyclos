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

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseQueryAction;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.entities.settings.events.LocalSettingsChangeListener;
import nl.strohalm.cyclos.entities.settings.events.LocalSettingsEvent;
import nl.strohalm.cyclos.entities.sms.SmsLog;
import nl.strohalm.cyclos.entities.sms.SmsLogQuery;
import nl.strohalm.cyclos.entities.sms.SmsLogStatus;
import nl.strohalm.cyclos.entities.sms.SmsLogType;
import nl.strohalm.cyclos.services.sms.SmsLogService;
import nl.strohalm.cyclos.utils.RelationshipHelper;
import nl.strohalm.cyclos.utils.RequestHelper;
import nl.strohalm.cyclos.utils.binding.BeanBinder;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.binding.DataBinderHelper;
import nl.strohalm.cyclos.utils.binding.PropertyBinder;
import nl.strohalm.cyclos.utils.query.QueryParameters;

public class SearchSmsLogsAction extends BaseQueryAction implements LocalSettingsChangeListener {

    private DataBinder<SmsLogQuery> dataBinder;
    private SmsLogService           smsLogService;

    @Override
    public void onLocalSettingsUpdate(final LocalSettingsEvent event) {
        super.onLocalSettingsUpdate(event);
        dataBinder = null;
    }

    @Inject
    public void setSmsLogService(final SmsLogService smsLogService) {
        this.smsLogService = smsLogService;
    }

    @Override
    protected void executeQuery(final ActionContext context, final QueryParameters queryParameters) {
        final SmsLogQuery query = (SmsLogQuery) queryParameters;
        final List<SmsLog> smsLogs = smsLogService.search(query);
        final HttpServletRequest request = context.getRequest();
        request.setAttribute("smsLogs", smsLogs);
    }

    @Override
    protected QueryParameters prepareForm(final ActionContext context) {
        final HttpServletRequest request = context.getRequest();
        // Resolve member id
        final SearchSmsLogsForm form = context.getForm();
        long memberId = form.getMemberId();
        if (memberId < 1) {
            memberId = context.getElement().getId();
        }
        final boolean mySmsLogs = memberId == context.getElement().getId();

        // Load member
        final Member member = elementService.load(memberId, RelationshipHelper.nested(Element.Relationships.GROUP, MemberGroup.Relationships.SMS_MESSAGES));
        form.setQuery("member", member.getId());
        request.setAttribute("member", member);
        request.setAttribute("mySmsLogs", mySmsLogs);
        RequestHelper.storeEnum(request, SmsLogStatus.class, "statusList");
        RequestHelper.storeEnum(request, SmsLogType.class, "typesList");

        return getDataBinder().readFromString(form.getQuery());
    }

    private DataBinder<SmsLogQuery> getDataBinder() {
        if (dataBinder == null) {
            final LocalSettings settings = settingsService.getLocalSettings();
            final BeanBinder<SmsLogQuery> binder = BeanBinder.instance(SmsLogQuery.class);
            binder.registerBinder("period", DataBinderHelper.periodBinder(settings, "period"));
            binder.registerBinder("member", PropertyBinder.instance(Member.class, "member"));
            binder.registerBinder("type", PropertyBinder.instance(SmsLogType.class, "type"));
            binder.registerBinder("status", PropertyBinder.instance(SmsLogStatus.class, "status"));
            binder.registerBinder("pageParameters", DataBinderHelper.pageBinder());
            dataBinder = binder;
        }
        return dataBinder;
    }

}
