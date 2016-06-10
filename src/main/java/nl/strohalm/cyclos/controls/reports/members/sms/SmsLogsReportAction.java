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
package nl.strohalm.cyclos.controls.reports.members.sms;

import java.util.Collection;
import java.util.EnumSet;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseQueryAction;
import nl.strohalm.cyclos.entities.groups.AdminGroup;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.members.Administrator;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.members.messages.Message;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.entities.settings.events.LocalSettingsChangeListener;
import nl.strohalm.cyclos.entities.settings.events.LocalSettingsEvent;
import nl.strohalm.cyclos.entities.sms.SmsLog;
import nl.strohalm.cyclos.entities.sms.SmsLogReportQuery;
import nl.strohalm.cyclos.entities.sms.SmsLogReportVO;
import nl.strohalm.cyclos.entities.sms.SmsLogStatus;
import nl.strohalm.cyclos.entities.sms.SmsLogType;
import nl.strohalm.cyclos.entities.sms.SmsMailing;
import nl.strohalm.cyclos.entities.sms.SmsMailingType;
import nl.strohalm.cyclos.entities.sms.SmsType;
import nl.strohalm.cyclos.services.sms.SmsLogService;
import nl.strohalm.cyclos.utils.EntityHelper;
import nl.strohalm.cyclos.utils.RelationshipHelper;
import nl.strohalm.cyclos.utils.RequestHelper;
import nl.strohalm.cyclos.utils.binding.BeanBinder;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.binding.DataBinderHelper;
import nl.strohalm.cyclos.utils.binding.PropertyBinder;
import nl.strohalm.cyclos.utils.binding.SimpleCollectionBinder;
import nl.strohalm.cyclos.utils.query.QueryParameters;

public class SmsLogsReportAction extends BaseQueryAction implements LocalSettingsChangeListener {

    public static DataBinder<SmsLogReportQuery> getSmsLogReportQueryBinder(final LocalSettings settings) {
        final BeanBinder<SmsLogReportQuery> binder = BeanBinder.instance(SmsLogReportQuery.class);
        binder.registerBinder("period", DataBinderHelper.periodBinder(settings, "period"));
        binder.registerBinder("memberGroups", SimpleCollectionBinder.instance(MemberGroup.class, "memberGroups"));
        binder.registerBinder("member", PropertyBinder.instance(Member.class, "member"));
        binder.registerBinder("status", PropertyBinder.instance(SmsLogStatus.class, "status"));
        binder.registerBinder("type", PropertyBinder.instance(SmsLogType.class, "type"));
        binder.registerBinder("mailingTypes", SimpleCollectionBinder.instance(SmsMailingType.class, "mailingTypes"));
        binder.registerBinder("messageTypes", SimpleCollectionBinder.instance(Message.Type.class, "messageTypes"));
        binder.registerBinder("smsTypes", SimpleCollectionBinder.instance(SmsType.class, "smsTypes"));
        binder.registerBinder("pageParameters", DataBinderHelper.pageBinder());
        return binder;
    }

    private DataBinder<SmsLogReportQuery> dataBinder;
    private SmsLogService                 smsLogService;

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
        final HttpServletRequest request = context.getRequest();
        final SmsLogReportQuery query = (SmsLogReportQuery) queryParameters.clone();
        final SmsLogReportVO report = smsLogService.getSmsLogReport(query);
        request.setAttribute("totals", report.getTotals());
        request.setAttribute("totalsByType", report.getTotalsByType());
        request.setAttribute("totalsByStatus", report.getTotalsByStatus());
        request.setAttribute("total", report.getTotal());
        request.setAttribute("smsLogs", report.getLogs());
    }

    @Override
    protected QueryParameters prepareForm(final ActionContext context) {
        final HttpServletRequest request = context.getRequest();
        final Administrator admin = elementService.load(context.getElement().getId(), RelationshipHelper.nested(Element.Relationships.GROUP, AdminGroup.Relationships.MANAGES_GROUPS));
        request.setAttribute("memberGroups", admin.getAdminGroup().getManagesGroups());
        RequestHelper.storeEnum(request, SmsLogType.class, "typesList");
        RequestHelper.storeEnum(request, SmsLogStatus.class, "statusList");
        RequestHelper.storeEnum(request, SmsMailingType.class, "mailingTypes");
        request.setAttribute("smsTypes", smsLogService.getSmsTypes());

        // Filter the message types which can never be delivered by sms
        final EnumSet<Message.Type> messageTypes = EnumSet.allOf(Message.Type.class);
        messageTypes.remove(Message.Type.FROM_MEMBER);
        messageTypes.remove(Message.Type.FROM_ADMIN_TO_GROUP);
        messageTypes.remove(Message.Type.FROM_ADMIN_TO_MEMBER);
        request.setAttribute("messagesTypes", messageTypes);

        final SmsLogsReportForm form = context.getForm();
        final SmsLogReportQuery query = getDataBinder().readFromString(form.getQuery());
        query.setReturnTotals(true);
        query.fetch(RelationshipHelper.nested(SmsLog.Relationships.TARGET_MEMBER, Element.Relationships.USER), RelationshipHelper.nested(SmsLog.Relationships.SMS_MAILING, SmsMailing.Relationships.BY));
        if (query.getMember() != null) {
            query.setMember((Member) elementService.load(query.getMember().getId(), Element.Relationships.USER));
        }
        final Collection<MemberGroup> grps = groupService.load(EntityHelper.toIdsAsList(query.getMemberGroups()));
        query.setMemberGroups(grps);
        query.setSmsTypes(smsLogService.loadSmsTypes(EntityHelper.toIdsAsList(query.getSmsTypes())));
        return query;
    }

    private DataBinder<SmsLogReportQuery> getDataBinder() {
        if (dataBinder == null) {
            final LocalSettings settings = settingsService.getLocalSettings();
            dataBinder = getSmsLogReportQueryBinder(settings);
        }
        return dataBinder;
    }

}
