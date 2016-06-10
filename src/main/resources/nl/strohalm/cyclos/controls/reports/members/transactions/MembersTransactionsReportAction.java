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
package nl.strohalm.cyclos.controls.reports.members.transactions;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseFormAction;
import nl.strohalm.cyclos.entities.groups.AdminGroup;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.members.MemberTransactionSummaryReportData;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.utils.NamedPeriod;
import nl.strohalm.cyclos.utils.Pair;
import nl.strohalm.cyclos.utils.Period;
import nl.strohalm.cyclos.utils.RequestHelper;
import nl.strohalm.cyclos.utils.SpringHelper;
import nl.strohalm.cyclos.utils.binding.BeanBinder;
import nl.strohalm.cyclos.utils.binding.DataBinderHelper;
import nl.strohalm.cyclos.utils.validation.ValidationException;

import org.apache.struts.action.ActionForward;

public class MembersTransactionsReportAction extends BaseFormAction {

    private MembersReportHandler reportHandler;

    public MembersReportHandler getReportHandler() {
        if (reportHandler == null) {
            reportHandler = new MembersReportHandler(settingsService.getLocalSettings());
            SpringHelper.injectBeans(getServlet().getServletContext(), reportHandler);
        }
        return reportHandler;
    }

    @Override
    protected void formAction(final ActionContext context) throws Exception {
        final HttpServletRequest request = context.getRequest();
        final Pair<MembersTransactionsReportDTO, Iterator<MemberTransactionSummaryReportData>> pair = getReportHandler().handleTransactionsSummary(context);
        final MembersTransactionsReportDTO dto = pair.getFirst();
        if (dto.getDetailsLevel() != MembersTransactionsReportDTO.DetailsLevel.SUMMARY) {
            throw new ValidationException();
        }
        request.setAttribute("iterator", pair.getSecond());
        request.setAttribute("dto", dto);
    }

    @Override
    protected ActionForward handleDisplay(final ActionContext context) throws Exception {
        final HttpServletRequest request = context.getRequest();
        // Set initial values for period
        final MembersTransactionsReportForm form = context.getForm();
        final NamedPeriod period = NamedPeriod.getLastQuarterPeriod();
        final LocalSettings settings = settingsService.getLocalSettings();
        final BeanBinder<Period> periodBinder = DataBinderHelper.periodBinder(settings, "period");
        periodBinder.writeAsString(form.getMembersTransactionsReport(), period);

        // Send member groups to JSP
        AdminGroup adminGroup = context.getGroup();
        adminGroup = groupService.load(adminGroup.getId(), AdminGroup.Relationships.MANAGES_GROUPS);
        final Collection<MemberGroup> groups = new TreeSet<MemberGroup>(adminGroup.getManagesGroups());
        request.setAttribute("memberGroups", groups);

        RequestHelper.storeEnum(request, MembersTransactionsReportDTO.DetailsLevel.class, "detailsLevels");
        return context.getInputForward();
    }

    @Override
    protected void validateForm(final ActionContext context) {
        final MembersTransactionsReportForm form = context.getForm();
        final Map<String, Object> membersTransactionsReport = form.getMembersTransactionsReport();
        final MembersReportHandler handler = getReportHandler();
        final BeanBinder<MembersTransactionsReportDTO> dataBinder = handler.getDataBinder();
        final MembersTransactionsReportDTO dto = dataBinder.readFromString(membersTransactionsReport);
        handler.validateDTO(dto);
    }

}
