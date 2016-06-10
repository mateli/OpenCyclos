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
package nl.strohalm.cyclos.controls.accounts.accountfees;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseQueryAction;
import nl.strohalm.cyclos.entities.accounts.fees.account.AccountFee;
import nl.strohalm.cyclos.entities.accounts.fees.account.AccountFee.InvoiceMode;
import nl.strohalm.cyclos.entities.accounts.fees.account.AccountFeeLog;
import nl.strohalm.cyclos.entities.accounts.fees.account.AccountFeeLogDetailsDTO;
import nl.strohalm.cyclos.entities.accounts.fees.account.MemberAccountFeeLog;
import nl.strohalm.cyclos.entities.accounts.fees.account.MemberAccountFeeLogQuery;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.services.accountfees.AccountFeeService;
import nl.strohalm.cyclos.utils.binding.BeanBinder;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.binding.DataBinderHelper;
import nl.strohalm.cyclos.utils.binding.PropertyBinder;
import nl.strohalm.cyclos.utils.binding.SimpleCollectionBinder;
import nl.strohalm.cyclos.utils.query.QueryParameters;

/**
 * Action used to show details of an account fee execution and to search for charged members within that log
 * @author luis
 */
public class AccountFeeLogDetailsAction extends BaseQueryAction {

    private AccountFeeService                    accountFeeService;

    private DataBinder<MemberAccountFeeLogQuery> dataBinder;

    @Inject
    public void setAccountFeeService(final AccountFeeService accountFeeService) {
        this.accountFeeService = accountFeeService;
    }

    @Override
    protected void executeQuery(final ActionContext context, final QueryParameters queryParameters) {
        final HttpServletRequest request = context.getRequest();
        final MemberAccountFeeLogQuery query = (MemberAccountFeeLogQuery) queryParameters;
        final List<MemberAccountFeeLog> members = accountFeeService.searchMembers(query);
        request.setAttribute("members", members);
    }

    @Override
    protected QueryParameters prepareForm(final ActionContext context) {
        final HttpServletRequest request = context.getRequest();
        final AccountFeeLogDetailsForm form = context.getForm();
        final Long accountFeeLogId = form.getAccountFeeLogId();

        // Get the details
        final AccountFeeLogDetailsDTO details = accountFeeService.getLogDetails(accountFeeLogId);
        final AccountFeeLog log = details.getAccountFeeLog();
        final AccountFee fee = log.getAccountFee();
        final boolean invoiceAlways = fee.getInvoiceMode() == InvoiceMode.ALWAYS;
        final boolean invoiceNever = fee.getInvoiceMode() == InvoiceMode.NEVER;

        // Prepare the query
        final MemberAccountFeeLogQuery query = getDataBinder().readFromString(form.getQuery());
        query.setAccountFeeLog(log);
        if (query.getStatus() == null) {
            query.setStatus(log.getFailedMembers() == 0 ? MemberAccountFeeLogQuery.Status.PROCESSED : MemberAccountFeeLogQuery.Status.ERROR);
            form.setQuery("status", query.getStatus().name());
        }
        if (query.getMember() != null) {
            query.setMember((Member) elementService.load(query.getMember().getId()));
        }

        // Get the possible statuses for search
        final Set<MemberAccountFeeLogQuery.Status> statuses = EnumSet.allOf(MemberAccountFeeLogQuery.Status.class);
        if (invoiceAlways) {
            statuses.remove(MemberAccountFeeLogQuery.Status.TRANSFER);
        } else if (invoiceNever) {
            statuses.remove(MemberAccountFeeLogQuery.Status.INVOICE);
            statuses.remove(MemberAccountFeeLogQuery.Status.ACCEPTED_INVOICE);
            statuses.remove(MemberAccountFeeLogQuery.Status.OPEN_INVOICE);
        }

        // Get the possible groups
        final List<MemberGroup> groups = new ArrayList<MemberGroup>(permissionService.getManagedMemberGroups());
        Collections.sort(groups);

        final boolean isRunning = log.getFinishDate() == null || log.isRechargingFailed();

        // Store the request attributes
        request.setAttribute("details", details);
        request.setAttribute("log", log);
        request.setAttribute("fee", fee);
        request.setAttribute("currencyPattern", fee.getAccountType().getCurrency().getPattern());
        request.setAttribute("invoiceAlways", invoiceAlways);
        request.setAttribute("invoiceNever", invoiceNever);
        request.setAttribute("statuses", statuses);
        request.setAttribute("groups", groups);
        request.setAttribute("isRunning", isRunning);
        return query;
    }

    @Override
    protected boolean willExecuteQuery(final ActionContext context, final QueryParameters queryParameters) throws Exception {
        return true;
    }

    private DataBinder<MemberAccountFeeLogQuery> getDataBinder() {
        if (dataBinder == null) {
            final BeanBinder<MemberAccountFeeLogQuery> binder = BeanBinder.instance(MemberAccountFeeLogQuery.class);
            binder.registerBinder("accountFeeLog", PropertyBinder.instance(AccountFeeLog.class, "accountFeeLog"));
            binder.registerBinder("status", PropertyBinder.instance(MemberAccountFeeLogQuery.Status.class, "status"));
            binder.registerBinder("groups", SimpleCollectionBinder.instance(MemberGroup.class, "groups"));
            binder.registerBinder("member", PropertyBinder.instance(Member.class, "member"));
            binder.registerBinder("pageParameters", DataBinderHelper.pageBinder());
            dataBinder = binder;
        }
        return dataBinder;
    }

}
