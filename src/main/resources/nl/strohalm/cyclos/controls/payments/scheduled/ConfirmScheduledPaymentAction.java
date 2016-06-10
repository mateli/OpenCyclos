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
package nl.strohalm.cyclos.controls.payments.scheduled;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseFormAction;
import nl.strohalm.cyclos.entities.accounts.AccountOwner;
import nl.strohalm.cyclos.entities.accounts.AccountType;
import nl.strohalm.cyclos.entities.accounts.MemberAccount;
import nl.strohalm.cyclos.entities.accounts.transactions.Payment;
import nl.strohalm.cyclos.entities.accounts.transactions.ScheduledPayment;
import nl.strohalm.cyclos.entities.accounts.transactions.Transfer;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferType;
import nl.strohalm.cyclos.entities.exceptions.UnexpectedEntityException;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.services.transactions.PaymentService;
import nl.strohalm.cyclos.services.transactions.ScheduledPaymentService;
import nl.strohalm.cyclos.services.transactions.exceptions.CreditsException;
import nl.strohalm.cyclos.services.transfertypes.TransactionFeePreviewDTO;
import nl.strohalm.cyclos.services.transfertypes.TransactionFeeService;
import nl.strohalm.cyclos.services.transfertypes.TransferTypeService;
import nl.strohalm.cyclos.utils.ActionHelper;
import nl.strohalm.cyclos.utils.RelationshipHelper;
import nl.strohalm.cyclos.utils.validation.RequiredError;
import nl.strohalm.cyclos.utils.validation.ValidationException;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForward;

public class ConfirmScheduledPaymentAction extends BaseFormAction {

    private PaymentService          paymentService;
    private ScheduledPaymentService scheduledPaymentService;
    private TransactionFeeService   transactionFeeService;
    private TransferTypeService     transferTypeService;

    @Inject
    public void setPaymentService(final PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @Inject
    public void setScheduledPaymentService(final ScheduledPaymentService scheduledPaymentService) {
        this.scheduledPaymentService = scheduledPaymentService;
    }

    @Inject
    public void setTransactionFeeService(final TransactionFeeService transactionFeeService) {
        this.transactionFeeService = transactionFeeService;
    }

    @Inject
    public void setTransferTypeService(final TransferTypeService transferTypeService) {
        this.transferTypeService = transferTypeService;
    }

    @Override
    protected ActionForward handleSubmit(final ActionContext context) throws Exception {
        Transfer transfer = resolveTransfer(context);

        // Validate the transaction password if needed
        if (shouldValidateTransactionPassword(context, transfer)) {
            final ScheduledPaymentForm form = context.getForm();
            context.checkTransactionPassword(form.getTransactionPassword());
        }

        // Perform the actual payment
        try {
            transfer = scheduledPaymentService.processTransfer(transfer);
        } catch (final CreditsException e) {
            return context.sendError(actionHelper.resolveErrorKey(e), actionHelper.resolveParameters(e));
        } catch (final UnexpectedEntityException e) {
            return context.sendError("payment.error.invalidTransferType");
        }
        return ActionHelper.redirectWithParam(context.getRequest(), context.getSuccessForward(), "paymentId", transfer.getScheduledPayment().getId());
    }

    @Override
    protected void prepareForm(final ActionContext context) throws Exception {
        final Transfer transfer = resolveTransfer(context);

        // Check for transaction password
        final HttpServletRequest request = context.getRequest();
        final boolean requestTransactionPassword = shouldValidateTransactionPassword(context, transfer);
        if (requestTransactionPassword) {
            context.validateTransactionPassword();
        }
        request.setAttribute("requestTransactionPassword", requestTransactionPassword);
        request.setAttribute("wouldRequireAuthorization", paymentService.wouldRequireAuthorization(transfer));

        // Transfer number and number of transfers
        final int transferNumber = getTransferNumber(transfer);
        final int numberOfTransfers = getNumberOfTransfer(transfer);
        request.setAttribute("transferNumber", transferNumber);
        request.setAttribute("numberOfTransfers", numberOfTransfers);

        // Fetch related data
        final AccountOwner from = transfer.getFromOwner();
        final AccountOwner to = transfer.getToOwner();
        final TransferType transferType = transferTypeService.load(transfer.getType().getId(), RelationshipHelper.nested(TransferType.Relationships.FROM, AccountType.Relationships.CURRENCY), TransferType.Relationships.TO);
        final BigDecimal amount = transfer.getAmount();
        if (from instanceof Member) {
            request.setAttribute("fromMember", from);
        }
        if (to instanceof Member) {
            request.setAttribute("toMember", to);
        }
        transfer.setType(transferType);
        request.setAttribute("unitsPattern", transferType.getFrom().getCurrency().getPattern());

        // Store the transaction fees
        final TransactionFeePreviewDTO preview = transactionFeeService.preview(from, to, transferType, amount);
        request.setAttribute("finalAmount", preview.getFinalAmount());
        request.setAttribute("fees", preview.getFees());
        request.setAttribute("transfer", transfer);
    }

    @Override
    protected void validateForm(final ActionContext context) {
        if (shouldValidateTransactionPassword(context, resolveTransfer(context))) {
            final ScheduledPaymentForm form = context.getForm();
            if (StringUtils.isEmpty(form.getTransactionPassword())) {
                throw new ValidationException("_transactionPassword", "login.transactionPassword", new RequiredError());
            }
        }
    }

    private int getNumberOfTransfer(final Transfer transfer) {
        final ScheduledPayment scheduledPayment = transfer.getScheduledPayment();
        return scheduledPayment.getTransfers().size();
    }

    private int getTransferNumber(final Transfer transfer) {
        final ScheduledPayment scheduledPayment = transfer.getScheduledPayment();
        int transferNumber = 0;
        for (final Transfer currentTransfer : scheduledPayment.getTransfers()) {
            transferNumber++;
            if (currentTransfer.equals(transfer)) {
                break;
            }
        }
        return transferNumber;
    }

    private Transfer resolveTransfer(final ActionContext context) {
        final ScheduledPaymentForm form = context.getForm();
        final Long transferId = form.getTransferId();
        if (transferId <= 0L) {
            throw new ValidationException();
        }
        return paymentService.load(transferId, RelationshipHelper.nested(Payment.Relationships.FROM, MemberAccount.Relationships.MEMBER), RelationshipHelper.nested(Payment.Relationships.TO, MemberAccount.Relationships.MEMBER), RelationshipHelper.nested(Transfer.Relationships.SCHEDULED_PAYMENT, ScheduledPayment.Relationships.TRANSFERS));
    }

    private boolean shouldValidateTransactionPassword(final ActionContext context, final Transfer transfer) {
        final TransferType transferType = transferTypeService.load(transfer.getType().getId(), TransferType.Relationships.FROM);
        return context.isTransactionPasswordEnabled(transferType.getFrom());
    }
}
