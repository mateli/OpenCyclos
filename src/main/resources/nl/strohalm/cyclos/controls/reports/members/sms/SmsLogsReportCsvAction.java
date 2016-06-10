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

import java.util.List;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseCsvAction;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.entities.settings.events.LocalSettingsChangeListener;
import nl.strohalm.cyclos.entities.settings.events.LocalSettingsEvent;
import nl.strohalm.cyclos.entities.sms.SmsLog;
import nl.strohalm.cyclos.entities.sms.SmsLogReportQuery;
import nl.strohalm.cyclos.entities.sms.SmsMailing;
import nl.strohalm.cyclos.services.sms.SmsLogService;
import nl.strohalm.cyclos.utils.RelationshipHelper;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.conversion.Converter;
import nl.strohalm.cyclos.utils.csv.CSVWriter;
import nl.strohalm.cyclos.utils.query.QueryParameters.ResultType;

/**
 * Action used to export the sent SMS messages report result as CSV
 * @author Jefferson Magno
 */
public class SmsLogsReportCsvAction extends BaseCsvAction implements LocalSettingsChangeListener {

    private class SmsTypeConverter implements Converter<SmsLog> {
        private static final long serialVersionUID = 1L;

        @Override
        public String toString(final SmsLog smsLog) {
            if (smsLog.getSmsType() != null) {
                return messageHelper.message("sms.type." + smsLog.getSmsType().getCode() + ".description", smsLog.getArg0(), smsLog.getArg1(), smsLog.getArg2(), smsLog.getArg3(), smsLog.getArg4());
            } else {
                return null;
            }
        }

        @Override
        public SmsLog valueOf(final String string) {
            throw new UnsupportedOperationException("SmsLog from String not supported");
        }
    }

    private SmsLogService                 smsLogService;
    private DataBinder<SmsLogReportQuery> dataBinder;

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
    protected List<?> executeQuery(final ActionContext context) {
        final SmsLogsReportForm form = context.getForm();
        final SmsLogReportQuery query = getDataBinder().readFromString(form.getQuery());
        query.setResultType(ResultType.ITERATOR);
        query.fetch(SmsLog.Relationships.SMS_TYPE, RelationshipHelper.nested(SmsLog.Relationships.TARGET_MEMBER, Element.Relationships.USER), RelationshipHelper.nested(SmsLog.Relationships.SMS_MAILING, SmsMailing.Relationships.BY));
        return smsLogService.getSmsLogReport(query).getLogs();
    }

    @Override
    protected String fileName(final ActionContext context) {
        return "sent_sms_report.csv";
    }

    @Override
    @SuppressWarnings({ "unchecked", "rawtypes" })
    protected CSVWriter resolveCSVWriter(final ActionContext context) {
        final LocalSettings settings = settingsService.getLocalSettings();
        final CSVWriter<SmsLog> csv = CSVWriter.instance(SmsLog.class, settings);
        csv.addColumn(context.message("member.username"), "targetMember.username");
        csv.addColumn(context.message("member.name"), "targetMember.name");
        csv.addColumn(context.message("smsLog.date"), "date", settings.getDateTimeConverter());
        csv.addColumn(context.message("smsLog.type"), "type", messageHelper.getMessageConverter(getServlet().getServletContext(), "smsLog.type."));
        csv.addColumn(context.message("smsLog.mailingType"), "smsMailing.type", messageHelper.getMessageConverter(getServlet().getServletContext(), "smsMailing.mailingType."));
        csv.addColumn(context.message("smsLog.messageType"), "messageType", messageHelper.getMessageConverter(getServlet().getServletContext(), "message.type."));
        csv.addColumn(context.message("smsLog.smsType"), null, new SmsTypeConverter());
        csv.addColumn(context.message("smsLog.free"), "free", messageHelper.getBooleanConverter(getServlet().getServletContext()));
        csv.addColumn(context.message("smsLog.status"), "status", messageHelper.getMessageConverter(getServlet().getServletContext(), "smsLog.status."));
        return csv;
    }

    private DataBinder<SmsLogReportQuery> getDataBinder() {
        if (dataBinder == null) {
            final LocalSettings settings = settingsService.getLocalSettings();
            dataBinder = SmsLogsReportAction.getSmsLogReportQueryBinder(settings);
        }
        return dataBinder;
    }

}
