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
import nl.strohalm.cyclos.entities.accounts.AccountType;
import nl.strohalm.cyclos.entities.accounts.fees.transaction.BrokerCommission;
import nl.strohalm.cyclos.entities.accounts.fees.transaction.TransactionFeeQuery;
import nl.strohalm.cyclos.entities.groups.BrokerGroup;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.members.brokerings.BrokerCommissionContract;
import nl.strohalm.cyclos.entities.members.brokerings.BrokerCommissionContractQuery;
import nl.strohalm.cyclos.entities.members.brokerings.DefaultBrokerCommission;
import nl.strohalm.cyclos.entities.settings.events.LocalSettingsChangeListener;
import nl.strohalm.cyclos.entities.settings.events.LocalSettingsEvent;
import nl.strohalm.cyclos.services.elements.CommissionService;
import nl.strohalm.cyclos.services.transfertypes.TransactionFeeService;
import nl.strohalm.cyclos.utils.RequestHelper;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.query.QueryParameters;
import nl.strohalm.cyclos.utils.validation.ValidationException;

/**
 * Action used by admin to manage broker commissions
 * @author Jefferson Magno
 */
public class ManageBrokerCommissionsAction extends BaseQueryAction implements LocalSettingsChangeListener {

    private CommissionService                         commissionService;
    private TransactionFeeService                     transactionFeeService;

    private DataBinder<BrokerCommissionContractQuery> dataBinder;

    public DataBinder<BrokerCommissionContractQuery> getDataBinder() {
        if (dataBinder == null) {
            dataBinder = SearchBrokerCommissionContractsAction.prepareQueryDataBinder(settingsService);
        }
        return dataBinder;
    }

    @Override
    public void onLocalSettingsUpdate(final LocalSettingsEvent event) {
        dataBinder = null;
    }

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
        final HttpServletRequest request = context.getRequest();
        final BrokerCommissionContractQuery query = (BrokerCommissionContractQuery) queryParameters;
        final List<BrokerCommissionContract> brokerCommissioncontracts = commissionService.searchBrokerCommissionContracts(query);
        request.setAttribute("brokerCommissionContracts", brokerCommissioncontracts);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected QueryParameters prepareForm(final ActionContext context) {
        final HttpServletRequest request = context.getRequest();
        final ManageBrokerCommissionsForm form = context.getForm();
        final long brokerId = form.getBrokerId();

        if (!context.isAdmin() || brokerId < 0) {
            throw new ValidationException();
        }

        // Get broker and broker group
        final Member broker = elementService.load(brokerId, Element.Relationships.GROUP);
        final BrokerGroup brokerGroup = (BrokerGroup) broker.getGroup();

        // Get broker commission transaction fees (from member) related to the broker group (including not enabled)
        final TransactionFeeQuery transactionFeeQuery = new TransactionFeeQuery();
        transactionFeeQuery.setEntityType(BrokerCommission.class);
        transactionFeeQuery.setGeneratedTransferTypeFromNature(AccountType.Nature.MEMBER);
        transactionFeeQuery.setBrokerGroup(brokerGroup);
        transactionFeeQuery.setReturnDisabled(true);
        final List<BrokerCommission> brokerCommissions = (List<BrokerCommission>) transactionFeeService.search(transactionFeeQuery);

        // Get current default broker commissions
        final List<DefaultBrokerCommission> defaultBrokerCommissions = commissionService.loadDefaultBrokerCommissions(broker, DefaultBrokerCommission.Relationships.BROKER_COMMISSION);

        request.setAttribute("broker", broker);
        request.setAttribute("brokerCommissions", brokerCommissions);
        request.setAttribute("defaultBrokerCommissions", defaultBrokerCommissions);
        RequestHelper.storeEnum(request, BrokerCommissionContract.Status.class, "statusList");

        // Create the query object
        final BrokerCommissionContractQuery query = getDataBinder().readFromString(form.getQuery());
        query.setBroker(broker);

        return query;
    }

    @Override
    protected boolean willExecuteQuery(final ActionContext context, final QueryParameters queryParameters) throws Exception {
        return true;
    }

}
