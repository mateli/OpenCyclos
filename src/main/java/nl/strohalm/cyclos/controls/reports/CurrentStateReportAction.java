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
package nl.strohalm.cyclos.controls.reports;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseFormAction;
import nl.strohalm.cyclos.entities.reports.CurrentStateReportVO;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.services.reports.CurrentStateReportParameters;
import nl.strohalm.cyclos.services.reports.CurrentStateReportParameters.TimePointType;
import nl.strohalm.cyclos.services.reports.CurrentStateReportService;
import nl.strohalm.cyclos.utils.binding.BeanBinder;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.binding.PropertyBinder;

import org.apache.struts.action.ActionForward;

public class CurrentStateReportAction extends BaseFormAction {

    private CurrentStateReportService                currentStateReportService;
    private DataBinder<CurrentStateReportParameters> dataBinder;

    @Inject
    public void setCurrentStateReportService(final CurrentStateReportService currentStateReportService) {
        this.currentStateReportService = currentStateReportService;
    }

    @Override
    protected ActionForward handleDisplay(final ActionContext context) throws Exception {
        try {
            prepareForm(context);
        } catch (final Exception e) {
            return context.sendError("reports.error.formDisplayError");
        }
        return context.getInputForward();
    }

    @Override
    protected ActionForward handleSubmit(final ActionContext context) throws Exception {
        final HttpServletRequest request = context.getRequest();
        final CurrentStateReportForm form = context.getForm();
        final CurrentStateReportParameters params = getDataBinder().readFromString(form.getCurrentStateReport());
        // no historic record for loans, refs and .. so..
        if (params.getTimePointType() == TimePointType.TIME_POINT_HISTORY) {
            params.setInvoices(false);
            params.setLoans(false);
            params.setReferences(false);
            request.setAttribute("historyTime", params.getTimePoint());
        }
        final CurrentStateReportVO report = currentStateReportService.getCurrentStateReport(params);

        request.setAttribute("dto", params);
        request.setAttribute("singleCurrency", report.getCurrencies().size() == 1);
        request.setAttribute("report", report);
        return context.getSuccessForward();
    }

    @Override
    protected void prepareForm(final ActionContext context) throws Exception {
        final CurrentStateReportForm form = context.getForm();
        final CurrentStateReportParameters params = new CurrentStateReportParameters();
        getDataBinder().writeAsString(form.getCurrentStateReport(), params);
    }

    private DataBinder<CurrentStateReportParameters> getDataBinder() {
        if (dataBinder == null) {
            final LocalSettings localSettings = settingsService.getLocalSettings();
            final BeanBinder<CurrentStateReportParameters> binder = BeanBinder.instance(CurrentStateReportParameters.class);
            binder.registerBinder("ads", PropertyBinder.instance(Boolean.TYPE, "ads"));
            binder.registerBinder("invoices", PropertyBinder.instance(Boolean.TYPE, "invoices"));
            binder.registerBinder("loans", PropertyBinder.instance(Boolean.TYPE, "loans"));
            binder.registerBinder("memberAccountInformation", PropertyBinder.instance(Boolean.TYPE, "memberAccountInformation"));
            binder.registerBinder("memberGroupInformation", PropertyBinder.instance(Boolean.TYPE, "memberGroupInformation"));
            binder.registerBinder("references", PropertyBinder.instance(Boolean.TYPE, "references"));
            binder.registerBinder("systemAccountInformation", PropertyBinder.instance(Boolean.TYPE, "systemAccountInformation"));
            binder.registerBinder("timePoint", PropertyBinder.instance(Calendar.class, "timePoint", localSettings.getDateTimeConverter()));
            binder.registerBinder("timePointType", PropertyBinder.instance(TimePointType.class, "timePointType"));
            dataBinder = binder;
        }
        return dataBinder;
    }

}
