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
package nl.strohalm.cyclos.controls.reports.members.list;

import java.util.Collection;
import java.util.List;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseFormAction;
import nl.strohalm.cyclos.entities.accounts.AccountType;
import nl.strohalm.cyclos.entities.ads.Ad;
import nl.strohalm.cyclos.entities.groups.AdminGroup;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.members.Reference;
import nl.strohalm.cyclos.utils.RequestHelper;
import nl.strohalm.cyclos.utils.SpringHelper;

import org.apache.struts.action.ActionForward;

public class MembersListReportAction extends BaseFormAction {

    private MembersListReportHandler reportHandler;

    @Override
    protected ActionForward handleDisplay(final ActionContext context) throws Exception {
        AdminGroup adminGroup = context.getGroup();
        adminGroup = groupService.load(adminGroup.getId(), AdminGroup.Relationships.MANAGES_GROUPS);
        final Collection<MemberGroup> groups = new TreeSet<MemberGroup>(adminGroup.getManagesGroups());
        final HttpServletRequest request = context.getRequest();
        request.setAttribute("groups", groups);
        return context.getInputForward();
    }

    @Override
    protected ActionForward handleSubmit(final ActionContext context) throws Exception {
        final MembersListReportVOIterator voIterator = getReportHandler().handleReport(context);

        final HttpServletRequest request = context.getRequest();
        final MembersListReportForm form = context.getForm();
        final MembersListReportDTO dto = getReportHandler().getDataBinder().readFromString(form.getMembersListReport());
        request.setAttribute("dto", dto);

        if (dto.isAccountsInformation()) {
            final List<MemberGroup> groups = (List<MemberGroup>) dto.getGroups();
            final Collection<AccountType> accountTypes = getReportHandler().getAccountTypes(groups);
            request.setAttribute("accountTypes", accountTypes);
        }

        request.setAttribute("voIterator", voIterator);
        RequestHelper.storeEnumMap(request, Ad.Status.class, "adStatus");
        RequestHelper.storeEnumMap(request, Reference.Level.class, "referenceLevels");
        return context.getSuccessForward();
    }

    private MembersListReportHandler getReportHandler() {
        if (reportHandler == null) {
            reportHandler = new MembersListReportHandler(settingsService.getLocalSettings());
            SpringHelper.injectBeans(getServlet().getServletContext(), reportHandler);
        }
        return reportHandler;
    }

}
