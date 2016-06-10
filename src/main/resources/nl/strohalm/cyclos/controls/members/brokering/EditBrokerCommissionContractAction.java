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

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.access.BrokerPermission;
import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseFormAction;
import nl.strohalm.cyclos.entities.accounts.fees.transaction.BrokerCommission;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.members.brokerings.BrokerCommissionContract;
import nl.strohalm.cyclos.entities.members.brokerings.BrokerCommissionContract.Status;
import nl.strohalm.cyclos.entities.members.brokerings.Brokering;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.services.elements.BrokeringService;
import nl.strohalm.cyclos.services.elements.CommissionService;
import nl.strohalm.cyclos.services.transfertypes.TransactionFeeService;
import nl.strohalm.cyclos.utils.ActionHelper;
import nl.strohalm.cyclos.utils.Amount;
import nl.strohalm.cyclos.utils.RelationshipHelper;
import nl.strohalm.cyclos.utils.RequestHelper;
import nl.strohalm.cyclos.utils.binding.BeanBinder;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.binding.DataBinderHelper;
import nl.strohalm.cyclos.utils.binding.PropertyBinder;
import nl.strohalm.cyclos.utils.conversion.IdConverter;
import nl.strohalm.cyclos.utils.validation.ValidationException;

import org.apache.struts.action.ActionForward;

/**
 * Edit a broker commission contract
 * @author Jefferson Magno
 */
public class EditBrokerCommissionContractAction extends BaseFormAction {

    private BrokeringService                     brokeringService;
    private CommissionService                    commissionService;
    private TransactionFeeService                transactionFeeService;
    private DataBinder<BrokerCommissionContract> dataBinder;

    public DataBinder<BrokerCommissionContract> getDataBinder() {
        if (dataBinder == null) {
            final LocalSettings localSettings = settingsService.getLocalSettings();
            final BeanBinder<BrokerCommissionContract> binder = BeanBinder.instance(BrokerCommissionContract.class);
            binder.registerBinder("id", PropertyBinder.instance(Long.class, "id", IdConverter.instance()));
            binder.registerBinder("brokering", PropertyBinder.instance(Brokering.class, "brokering"));
            binder.registerBinder("brokerCommission", PropertyBinder.instance(BrokerCommission.class, "brokerCommission"));
            binder.registerBinder("period", DataBinderHelper.rawPeriodBinder(localSettings, "period"));
            binder.registerBinder("amount", DataBinderHelper.highPrecisionAmountConverter("amount", localSettings));
            binder.registerBinder("status", PropertyBinder.instance(BrokerCommissionContract.Status.class, "status"));
            dataBinder = binder;
        }
        return dataBinder;
    }

    @Inject
    public void setBrokeringService(final BrokeringService brokeringService) {
        this.brokeringService = brokeringService;
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
    protected ActionForward handleSubmit(final ActionContext context) throws Exception {
        final EditBrokerCommissionContractForm form = context.getForm();
        BrokerCommissionContract brokerCommissionContract = getDataBinder().readFromString(form.getBrokerCommissionContract());
        final boolean isInsert = brokerCommissionContract.isTransient();
        brokerCommissionContract = commissionService.saveBrokerCommissionContract(brokerCommissionContract);
        context.sendMessage(isInsert ? "brokerCommissionContract.inserted" : "brokerCommissionContract.modified");
        return ActionHelper.redirectWithParam(context.getRequest(), context.getSuccessForward(), "brokerCommissionContractId", brokerCommissionContract.getId());
    }

    @Override
    protected void prepareForm(final ActionContext context) throws Exception {
        final HttpServletRequest request = context.getRequest();
        final EditBrokerCommissionContractForm form = context.getForm();

        final long brokerCommissionContractId = form.getBrokerCommissionContractId();
        final long brokerCommissionId = form.getBrokerCommissionId();
        final long memberId = form.getMemberId();

        boolean byAdmin = false;
        boolean byBroker = false;
        boolean isInsert = false;
        boolean editable = false;
        boolean cancelable = false;
        boolean canAcceptOrDeny = false;

        BrokerCommissionContract brokerCommissionContract = null;

        if (brokerCommissionContractId > 0) {
            try {
                brokerCommissionContract = commissionService.loadBrokerCommissionContract(brokerCommissionContractId, RelationshipHelper.nested(BrokerCommissionContract.Relationships.BROKERING, Brokering.Relationships.BROKER));
            } catch (final EntityNotFoundException e) {
                throw new ValidationException("brokerCommissionContract.error.contractNotFound");
            }
            final Status status = brokerCommissionContract.getStatus();

            if (context.isAdmin()) {
                byAdmin = true;
            } else {
                byBroker = isByBroker(context, brokerCommissionContract);
            }

            if (byAdmin) {
                if ((status == Status.ACTIVE || status == Status.PENDING)) {
                    cancelable = true;
                }
            } else if (byBroker) {
                // Only pending contracts are editable
                if (status == Status.PENDING && permissionService.hasPermission(BrokerPermission.MEMBERS_MANAGE_CONTRACTS)) {
                    editable = true;
                }
                if (status == Status.ACTIVE) {
                    cancelable = true;
                }
            } else {
                // A member can accept or deny a pending contract
                canAcceptOrDeny = status == Status.PENDING;
            }
        } else {
            // Broker inserting a new contract
            byBroker = true;
            isInsert = true;
            editable = true;

            if (context.isAdmin() || memberId < 1 || brokerCommissionId < 1) {
                throw new ValidationException();
            }

            // Fetch the brokering
            final Member broker = context.getElement();
            final Member member = elementService.load(memberId);
            final Brokering brokering = brokeringService.getBrokering(broker, member);

            // Create a BrokerCommissions reference
            final BrokerCommission brokerCommission = (BrokerCommission) transactionFeeService.load(brokerCommissionId);

            // Prepare the new broker commission contract for insert
            brokerCommissionContract = new BrokerCommissionContract();
            brokerCommissionContract.setBrokering(brokering);
            brokerCommissionContract.setBrokerCommission(brokerCommission);
            brokerCommissionContract.setStatus(BrokerCommissionContract.Status.PENDING);
        }

        getDataBinder().writeAsString(form.getBrokerCommissionContract(), brokerCommissionContract);
        request.setAttribute("brokerCommissionContract", brokerCommissionContract);
        request.setAttribute("byAdmin", byAdmin);
        request.setAttribute("byBroker", byBroker);
        request.setAttribute("isInsert", isInsert);
        request.setAttribute("editable", editable);
        request.setAttribute("cancelable", cancelable);
        request.setAttribute("canAcceptOrDeny", canAcceptOrDeny);
        RequestHelper.storeEnum(request, Amount.Type.class, "amountTypes");
    }

    @Override
    protected void validateForm(final ActionContext context) {
        final EditBrokerCommissionContractForm form = context.getForm();
        final BrokerCommissionContract brokerCommissionContract = getDataBinder().readFromString(form.getBrokerCommissionContract());
        commissionService.validateBrokerCommissionContract(brokerCommissionContract);
    }

    private boolean isByBroker(final ActionContext context, final BrokerCommissionContract brokerCommissionContract) {
        final Member broker = brokerCommissionContract.getBrokering().getBroker();
        if (context.getElement().equals(broker)) {
            return true;
        } else {
            return false;
        }
    }

}
