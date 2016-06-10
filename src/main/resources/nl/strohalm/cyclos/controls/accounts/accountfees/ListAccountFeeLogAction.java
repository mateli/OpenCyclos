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

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseQueryAction;
import nl.strohalm.cyclos.entities.accounts.AccountType;
import nl.strohalm.cyclos.entities.accounts.fees.account.AccountFee;
import nl.strohalm.cyclos.entities.accounts.fees.account.AccountFeeLog;
import nl.strohalm.cyclos.entities.accounts.fees.account.AccountFeeLogQuery;
import nl.strohalm.cyclos.entities.accounts.fees.account.AccountFeeQuery;
import nl.strohalm.cyclos.entities.groups.AdminGroup;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.services.accountfees.AccountFeeService;
import nl.strohalm.cyclos.utils.RelationshipHelper;
import nl.strohalm.cyclos.utils.binding.BeanBinder;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.binding.DataBinderHelper;
import nl.strohalm.cyclos.utils.query.QueryParameters;

/**
 * Action used to list an account fee log
 */
public class ListAccountFeeLogAction extends BaseQueryAction {

    private AccountFeeService              accountFeeService;

    private DataBinder<AccountFeeLogQuery> dataBinder;

    public AccountFeeService getAccountFeeService() {
        return accountFeeService;
    }

    @Inject
    public void setAccountFeeService(final AccountFeeService accountFeeService) {
        this.accountFeeService = accountFeeService;
    }

    @Override
    protected void executeQuery(final ActionContext context, final QueryParameters queryParameters) {
        final HttpServletRequest request = context.getRequest();
        final AccountFeeLogQuery query = (AccountFeeLogQuery) queryParameters;
        final List<AccountFeeLog> logs = accountFeeService.searchLogs(query);
        request.setAttribute("accountFeeLogs", logs);
    }

    @Override
    protected QueryParameters prepareForm(final ActionContext context) {
        final HttpServletRequest request = context.getRequest();

        // Groups managed by the admin group
        AdminGroup adminGroup = context.getGroup();
        adminGroup = groupService.load(adminGroup.getId(), AdminGroup.Relationships.MANAGES_GROUPS);
        final Collection<MemberGroup> managedGroups = (adminGroup.getManagesGroups());

        final AccountFeeQuery feeQuery = new AccountFeeQuery();
        feeQuery.fetch(AccountFee.Relationships.LOGS, RelationshipHelper.nested(AccountFee.Relationships.ACCOUNT_TYPE, AccountType.Relationships.CURRENCY));
        feeQuery.setReturnDisabled(false);
        feeQuery.setGroups(managedGroups);
        final List<AccountFee> fees = accountFeeService.search(feeQuery);

        feeQuery.setReturnDisabled(true);

        final AccountFeeLogForm form = context.getForm();

        final AccountFeeLogQuery logQuery = getDataBinder().readFromString(form.getQuery());
        logQuery.setAccountFees(fees);

        // Check if there is at least one fee which is currently running
        boolean hasRunningFees = false;
        for (final AccountFee fee : fees) {
            final AccountFeeLog lastExecution = fee.getLastExecution();
            if (lastExecution != null && !lastExecution.isFinished()) {
                hasRunningFees = true;
                break;
            }
        }
        request.setAttribute("hasRunningFees", hasRunningFees);

        request.setAttribute("accountFees", fees);

        return logQuery;
    }

    @Override
    protected boolean willExecuteQuery(final ActionContext context, final QueryParameters queryParameters) throws Exception {
        return true;
    }

    private DataBinder<AccountFeeLogQuery> getDataBinder() {
        if (dataBinder == null) {
            final BeanBinder<AccountFeeLogQuery> binder = BeanBinder.instance(AccountFeeLogQuery.class);
            binder.registerBinder("pageParameters", DataBinderHelper.pageBinder());
            dataBinder = binder;
        }
        return dataBinder;
    }

}
