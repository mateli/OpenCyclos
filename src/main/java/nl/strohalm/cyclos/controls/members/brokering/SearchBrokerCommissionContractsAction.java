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
package nl.strohalm.cyclos.controls.members.brokering;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseQueryAction;
import nl.strohalm.cyclos.entities.accounts.AccountType.Nature;
import nl.strohalm.cyclos.entities.accounts.fees.transaction.BrokerCommission;
import nl.strohalm.cyclos.entities.accounts.fees.transaction.TransactionFeeQuery;
import nl.strohalm.cyclos.entities.groups.BrokerGroup;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.members.brokerings.BrokerCommissionContract;
import nl.strohalm.cyclos.entities.members.brokerings.BrokerCommissionContractQuery;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.services.elements.CommissionService;
import nl.strohalm.cyclos.services.settings.SettingsService;
import nl.strohalm.cyclos.services.transfertypes.TransactionFeeService;
import nl.strohalm.cyclos.utils.RequestHelper;
import nl.strohalm.cyclos.utils.binding.BeanBinder;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.binding.DataBinderHelper;
import nl.strohalm.cyclos.utils.binding.PropertyBinder;
import nl.strohalm.cyclos.utils.query.QueryParameters;
import nl.strohalm.cyclos.utils.validation.ValidationException;

/**
 * Search broker commission contracts
 * @author Jefferson Magno
 */
public class SearchBrokerCommissionContractsAction extends BaseQueryAction {

    public static DataBinder<BrokerCommissionContractQuery> prepareQueryDataBinder(final SettingsService settingsService) {
        final LocalSettings localSettings = settingsService.getLocalSettings();
        final BeanBinder<BrokerCommissionContractQuery> binder = BeanBinder.instance(BrokerCommissionContractQuery.class);
        binder.registerBinder("member", PropertyBinder.instance(Member.class, "member"));
        binder.registerBinder("brokerCommission", PropertyBinder.instance(BrokerCommission.class, "brokerCommission"));
        binder.registerBinder("startPeriod", DataBinderHelper.periodBinder(localSettings, "startPeriod"));
        binder.registerBinder("endPeriod", DataBinderHelper.periodBinder(localSettings, "endPeriod"));
        binder.registerBinder("status", PropertyBinder.instance(BrokerCommissionContract.Status.class, "status"));
        return binder;
    }

    private CommissionService                         commissionService;
    private TransactionFeeService                     transactionFeeService;

    private DataBinder<BrokerCommissionContractQuery> dataBinder;

    @Inject
    public void setCommissionService(final CommissionService commissionService) {
        this.commissionService = commissionService;
    }

    @Inject
    public void setTransactionFeeService(final TransactionFeeService transactionFeeService) {
        this.transactionFeeService = transactionFeeService;
    }

    @Override
    protected void executeQuery(final ActionContext context, final QueryParameters queryParameters) {
        final BrokerCommissionContractQuery query = (BrokerCommissionContractQuery) queryParameters;
        final List<BrokerCommissionContract> brokerCommissionContracts = commissionService.searchBrokerCommissionContracts(query);
        context.getRequest().setAttribute("brokerCommissionContracts", brokerCommissionContracts);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected QueryParameters prepareForm(final ActionContext context) {
        if (!context.isBroker()) {
            throw new ValidationException();
        }

        final HttpServletRequest request = context.getRequest();
        final SearchBrokerCommissionContractsForm form = context.getForm();

        // Get logged broker and broker group
        final Member broker = (Member) context.getElement();
        final BrokerGroup brokerGroup = (BrokerGroup) context.getGroup();

        // Get broker commission transaction fees related to the broker group
        final TransactionFeeQuery transactionFeeQuery = new TransactionFeeQuery();
        transactionFeeQuery.setGeneratedTransferTypeFromNature(Nature.MEMBER);
        transactionFeeQuery.setEntityType(BrokerCommission.class);
        transactionFeeQuery.setBrokerGroup(brokerGroup);
        final List<BrokerCommission> brokerCommissions = (List<BrokerCommission>) transactionFeeService.search(transactionFeeQuery);
        request.setAttribute("brokerCommissions", brokerCommissions);

        RequestHelper.storeEnum(request, BrokerCommissionContract.Status.class, "statusList");

        // Build query object
        final BrokerCommissionContractQuery query = getDataBinder().readFromString(form.getQuery());
        query.setBroker(broker);
        return query;
    }

    @Override
    protected boolean willExecuteQuery(final ActionContext context, final QueryParameters queryParameters) throws Exception {
        return true;
    }

    private DataBinder<BrokerCommissionContractQuery> getDataBinder() {
        if (dataBinder == null) {
            dataBinder = prepareQueryDataBinder(settingsService);
        }
        return dataBinder;
    }

}
