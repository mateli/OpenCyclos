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
package nl.strohalm.cyclos.controls.invoices;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseFormAction;
import nl.strohalm.cyclos.entities.accounts.AccountOwner;
import nl.strohalm.cyclos.entities.accounts.AccountType;
import nl.strohalm.cyclos.entities.accounts.transactions.Invoice;
import nl.strohalm.cyclos.entities.accounts.transactions.Transfer;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferType;
import nl.strohalm.cyclos.entities.customization.fields.PaymentCustomField;
import nl.strohalm.cyclos.entities.exceptions.UnexpectedEntityException;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.services.customization.PaymentCustomFieldService;
import nl.strohalm.cyclos.services.transactions.InvoiceService;
import nl.strohalm.cyclos.services.transactions.PaymentService;
import nl.strohalm.cyclos.services.transactions.exceptions.CreditsException;
import nl.strohalm.cyclos.services.transfertypes.TransactionFeePreviewDTO;
import nl.strohalm.cyclos.services.transfertypes.TransactionFeeService;
import nl.strohalm.cyclos.services.transfertypes.TransferTypeService;
import nl.strohalm.cyclos.utils.ActionHelper;
import nl.strohalm.cyclos.utils.CustomFieldHelper;
import nl.strohalm.cyclos.utils.CustomFieldHelper.Entry;
import nl.strohalm.cyclos.utils.RelationshipHelper;
import nl.strohalm.cyclos.utils.binding.BeanBinder;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.binding.PropertyBinder;
import nl.strohalm.cyclos.utils.conversion.IdConverter;
import nl.strohalm.cyclos.utils.conversion.ReferenceConverter;
import nl.strohalm.cyclos.utils.validation.RequiredError;
import nl.strohalm.cyclos.utils.validation.ValidationException;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForward;

/**
 * Action used to accept an invoice
 * @author luis
 */
public class AcceptInvoiceAction extends BaseFormAction {

    private DataBinder<Invoice>       dataBinder;
    private InvoiceService            invoiceService;
    private TransactionFeeService     transactionFeeService;
    private PaymentService            paymentService;
    private PaymentCustomFieldService paymentCustomFieldService;
    private TransferTypeService       transferTypeService;

    private CustomFieldHelper         customFieldHelper;

    public DataBinder<Invoice> getDataBinder() {
        if (dataBinder == null) {
            final BeanBinder<Invoice> binder = BeanBinder.instance(Invoice.class);
            binder.registerBinder("id", PropertyBinder.instance(Long.class, "invoiceId", IdConverter.instance()));
            binder.registerBinder("transferType", PropertyBinder.instance(TransferType.class, "transferTypeId", ReferenceConverter.instance(TransferType.class)));
            dataBinder = binder;
        }
        return dataBinder;
    }

    public InvoiceService getInvoiceService() {
        return invoiceService;
    }

    public TransactionFeeService getTransactionFeeService() {
        return transactionFeeService;
    }

    @Inject
    public void setCustomFieldHelper(final CustomFieldHelper customFieldHelper) {
        this.customFieldHelper = customFieldHelper;
    }

    @Inject
    public void setInvoiceService(final InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @Inject
    public void setPaymentCustomFieldService(final PaymentCustomFieldService paymentCustomFieldService) {
        this.paymentCustomFieldService = paymentCustomFieldService;
    }

    @Inject
    public void setPaymentService(final PaymentService paymentService) {
        this.paymentService = paymentService;
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
        try {
            final AcceptInvoiceForm form = context.getForm();

            Invoice invoice = getDataBinder().readFromString(form);
            final boolean requestTransactionPassword = shouldValidateTransactionPassword(context, invoice, invoice.getTransferType());
            if (requestTransactionPassword) {
                context.checkTransactionPassword(form.getTransactionPassword());
            }

            invoice = invoiceService.accept(invoice);

            final Transfer transfer = invoice.getTransfer();
            if (transfer != null && transfer.getProcessDate() == null) {
                context.sendMessage("invoice.accepted.withAuthorization");
            } else {
                context.sendMessage("invoice.accepted");
            }
            final Map<String, Object> params = new HashMap<String, Object>();
            params.put("invoiceId", invoice.getId());
            params.put("memberId", form.getMemberId());
            params.put("accountFeeLogId", form.getAccountFeeLogId());
            return ActionHelper.redirectWithParams(context.getRequest(), context.getSuccessForward(), params);
        } catch (final CreditsException e) {
            return context.sendError(actionHelper.resolveErrorKey(e), actionHelper.resolveParameters(e));
        } catch (final UnexpectedEntityException e) {
            return context.sendError("payment.error.invalidTransferType");
        }
    }

    @Override
    protected void prepareForm(final ActionContext context) throws Exception {
        final HttpServletRequest request = context.getRequest();
        final AcceptInvoiceForm form = context.getForm();
        Invoice invoice = getDataBinder().readFromString(form);
        // Get the selected transfer type
        TransferType transferType = invoice.getTransferType();
        if (transferType != null) {
            transferType = transferTypeService.load(transferType.getId(), RelationshipHelper.nested(TransferType.Relationships.FROM, AccountType.Relationships.CURRENCY), TransferType.Relationships.TO);
        }

        invoice = invoiceService.load(invoice.getId(), Invoice.Relationships.FROM_MEMBER, Invoice.Relationships.TO_MEMBER);

        // Check if there are multiple transfer type choices - used to show or hide the transfer type
        final List<TransferType> transferTypes = invoiceService.getPossibleTransferTypes(invoice);

        // Get the custom values
        if (transferTypes.size() == 1) {
            transferType = transferTypes.iterator().next();
            invoice.setTransferType(transferType);
            final List<PaymentCustomField> customFields = paymentCustomFieldService.list(transferType, false);
            final Collection<Entry> entries = customFieldHelper.buildEntries(customFields, invoice.getCustomValues());
            request.setAttribute("customFields", entries);
        }

        // When the invoice was already associated with a transfer type, use it
        final boolean preSelectedTransferType = invoice.getTransferType() != null;
        if (preSelectedTransferType) {
            transferType = transferTypeService.load(invoice.getTransferType().getId(), RelationshipHelper.nested(TransferType.Relationships.FROM, AccountType.Relationships.CURRENCY), TransferType.Relationships.TO);
        }

        // Check for transaction password
        final boolean requestTransactionPassword = shouldValidateTransactionPassword(context, invoice, transferType);
        if (requestTransactionPassword) {
            context.validateTransactionPassword();
        }
        request.setAttribute("invoice", invoice);
        request.setAttribute("transferType", transferType);
        request.setAttribute("requestTransactionPassword", requestTransactionPassword);
        request.setAttribute("unitsPattern", transferType.getFrom().getCurrency().getPattern());

        // Check whether authorization would be required
        final Invoice clone = (Invoice) invoice.clone();
        clone.setId(null);
        clone.setTransferType(transferType);
        final boolean wouldRequireAuthorization = paymentService.wouldRequireAuthorization(clone);
        request.setAttribute("wouldRequireAuthorization", wouldRequireAuthorization);

        if (!preSelectedTransferType) {
            if (transferTypes.isEmpty()) {
                throw new ValidationException();
            }
            request.setAttribute("showTransferType", transferTypes.size() > 1);
        }

        final AccountOwner from = invoice.getTo();
        final AccountOwner to = invoice.getFrom();
        if (from instanceof Member) {
            request.setAttribute("fromMember", from);
        }
        if (to instanceof Member) {
            request.setAttribute("toMember", to);
        }

        // Store the transaction fees
        final TransactionFeePreviewDTO preview = transactionFeeService.preview(clone);
        request.setAttribute("finalAmount", preview.getFinalAmount());
        request.setAttribute("fees", preview.getFees());
    }

    @Override
    protected void validateForm(final ActionContext context) {
        final AcceptInvoiceForm form = context.getForm();
        final Invoice invoice = getDataBinder().readFromString(form);
        invoiceService.validateForAccept(invoice);

        if (shouldValidateTransactionPassword(context, invoice, invoice.getTransferType())) {
            if (StringUtils.isEmpty(form.getTransactionPassword())) {
                throw new ValidationException("_transactionPassword", "login.transactionPassword", new RequiredError());
            }
        }
    }

    private boolean shouldValidateTransactionPassword(final ActionContext context, Invoice invoice, TransferType transferType) {
        final AccountOwner loggedOwner = context.getAccountOwner();
        invoice = invoiceService.load(invoice.getId(), Invoice.Relationships.TO_MEMBER);
        if (loggedOwner.equals(invoice.getToMember())) {
            // When a logged member accepting an invoice to himself
            transferType = transferTypeService.load(transferType.getId(), TransferType.Relationships.FROM);
            return context.isTransactionPasswordEnabled(transferType.getFrom());
        } else {
            return context.isTransactionPasswordEnabled();
        }
    }

}
