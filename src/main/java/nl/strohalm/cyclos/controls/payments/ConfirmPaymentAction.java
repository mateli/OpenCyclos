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
package nl.strohalm.cyclos.controls.payments;

import java.math.BigDecimal;
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
import nl.strohalm.cyclos.entities.accounts.transactions.Payment;
import nl.strohalm.cyclos.entities.accounts.transactions.ScheduledPayment;
import nl.strohalm.cyclos.entities.accounts.transactions.Transfer;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferType;
import nl.strohalm.cyclos.entities.customization.fields.CustomField;
import nl.strohalm.cyclos.entities.customization.fields.CustomFieldPossibleValue;
import nl.strohalm.cyclos.entities.customization.fields.CustomFieldValue;
import nl.strohalm.cyclos.entities.customization.fields.PaymentCustomField;
import nl.strohalm.cyclos.entities.customization.fields.PaymentCustomFieldValue;
import nl.strohalm.cyclos.entities.exceptions.UnexpectedEntityException;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.services.customization.PaymentCustomFieldService;
import nl.strohalm.cyclos.services.transactions.DoPaymentDTO;
import nl.strohalm.cyclos.services.transactions.PaymentService;
import nl.strohalm.cyclos.services.transactions.ScheduledPaymentDTO;
import nl.strohalm.cyclos.services.transactions.exceptions.AuthorizedPaymentInPastException;
import nl.strohalm.cyclos.services.transactions.exceptions.CreditsException;
import nl.strohalm.cyclos.services.transfertypes.TransactionFeePreviewDTO;
import nl.strohalm.cyclos.services.transfertypes.TransactionFeeService;
import nl.strohalm.cyclos.services.transfertypes.TransferTypeService;
import nl.strohalm.cyclos.utils.ActionHelper;
import nl.strohalm.cyclos.utils.CustomFieldHelper;
import nl.strohalm.cyclos.utils.CustomFieldHelper.Entry;
import nl.strohalm.cyclos.utils.RelationshipHelper;
import nl.strohalm.cyclos.utils.conversion.CoercionHelper;
import nl.strohalm.cyclos.utils.validation.RequiredError;
import nl.strohalm.cyclos.utils.validation.ValidationException;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForward;

/**
 * Action used to display a message and optionally request the transaction password in order to make a payment
 * @author luis
 */
public class ConfirmPaymentAction extends BaseFormAction {

    private PaymentService            paymentService;
    private TransferTypeService       transferTypeService;
    private TransactionFeeService     transactionFeeService;
    private PaymentCustomFieldService paymentCustomFieldService;

    private CustomFieldHelper         customFieldHelper;

    @Inject
    public void setCustomFieldHelper(final CustomFieldHelper customFieldHelper) {
        this.customFieldHelper = customFieldHelper;
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
        final ConfirmPaymentForm form = context.getForm();
        final DoPaymentDTO paymentDTO = validatePayment(context);
        // Validate the transaction password if needed
        if (shouldValidateTransactionPassword(context, paymentDTO)) {
            context.checkTransactionPassword(form.getTransactionPassword());
        }
        // Perform the actual payment
        Payment payment;
        try {
            payment = paymentService.doPayment(paymentDTO);
            context.getSession().removeAttribute("payment");
        } catch (final CreditsException e) {
            return context.sendError(actionHelper.resolveErrorKey(e), actionHelper.resolveParameters(e));
        } catch (final UnexpectedEntityException e) {
            return context.sendError("payment.error.invalidTransferType");
        } catch (final AuthorizedPaymentInPastException e) {
            return context.sendError("payment.error.authorizedInPast");
        }
        // Redirect to the next action
        final Map<String, Object> params = new HashMap<String, Object>();
        ActionForward forward;
        if (payment instanceof Transfer) {
            params.put("transferId", payment.getId());
            forward = context.getSuccessForward();
        } else if (payment instanceof ScheduledPayment) {
            params.put("paymentId", payment.getId());
            forward = context.findForward("scheduledPayment");
        } else {
            throw new IllegalStateException("Unknown payment type: " + payment);
        }
        params.put("selectMember", form.getSelectMember());
        params.put("from", form.getFrom());
        return ActionHelper.redirectWithParams(context.getRequest(), forward, params);
    }

    @Override
    protected void prepareForm(final ActionContext context) throws Exception {
        final DoPaymentDTO payment = validatePayment(context);

        // Clear the from when the same as logged owner
        if (payment.getFrom() != null && context.getAccountOwner().equals(payment.getFrom())) {
            payment.setFrom(null);
        }

        // Check for transaction password
        final HttpServletRequest request = context.getRequest();
        final boolean requestTransactionPassword = shouldValidateTransactionPassword(context, payment);
        if (requestTransactionPassword) {
            context.validateTransactionPassword();
        }

        final boolean wouldRequireAuthorization = paymentService.wouldRequireAuthorization(payment);
        request.setAttribute("requestTransactionPassword", requestTransactionPassword);
        request.setAttribute("wouldRequireAuthorization", wouldRequireAuthorization);

        if (wouldRequireAuthorization && payment.getDate() != null) {
            throw new ValidationException("payment.error.authorizedInPast");
        }

        // Fetch related data
        AccountOwner from = payment.getFrom();
        AccountOwner to = payment.getTo();
        final TransferType transferType = transferTypeService.load(payment.getTransferType().getId(), RelationshipHelper.nested(TransferType.Relationships.FROM, AccountType.Relationships.CURRENCY), TransferType.Relationships.TO);
        final BigDecimal amount = payment.getAmount();
        if (from instanceof Member) {
            from = (Member) elementService.load(((Member) from).getId());
            request.setAttribute("fromMember", from);
            payment.setFrom(from);
        }
        if (to instanceof Member) {
            to = (Member) elementService.load(((Member) to).getId());
            request.setAttribute("toMember", to);
            payment.setTo(to);
        }
        // request.setAttribute("relatedMember", from != null ? from.g : to);
        payment.setTransferType(transferType);
        request.setAttribute("unitsPattern", transferType.getFrom().getCurrency().getPattern());

        // Store the transaction fees
        final TransactionFeePreviewDTO preview = transactionFeeService.preview(from, to, transferType, amount);
        request.setAttribute("finalAmount", preview.getFinalAmount());
        request.setAttribute("fees", preview.getFees());

        // Show the total amount when the original amount has changed (there where fees which deducted from it)
        if (!preview.getAmount().equals(preview.getFinalAmount())) {
            request.setAttribute("totalAmount", preview.getAmount());
        }

        // Calculate the transaction fees for every scheduled payment
        final List<ScheduledPaymentDTO> payments = payment.getPayments();
        final boolean isScheduled = CollectionUtils.isNotEmpty(payments);
        if (isScheduled) {
            for (final ScheduledPaymentDTO current : payments) {
                final TransactionFeePreviewDTO currentPreview = transactionFeeService.preview(from, to, transferType, current.getAmount());
                current.setFinalAmount(currentPreview.getFinalAmount());
            }
        }
        request.setAttribute("isScheduled", isScheduled);

        // Return the custom field values
        final Collection<PaymentCustomFieldValue> customValues = payment.getCustomValues();
        if (customValues != null) {
            final List<PaymentCustomField> customFields = paymentCustomFieldService.list(transferType, false);
            final Collection<Entry> entries = customFieldHelper.buildEntries(customFields, customValues);
            // Load the value for enumerated values, since this collection was built from direct databinding with ids only
            for (final Entry entry : entries) {
                final CustomField field = entry.getField();
                final CustomFieldValue fieldValue = entry.getValue();
                if (field.getType() == CustomField.Type.ENUMERATED) {
                    Long possibleValueId;
                    final CustomFieldPossibleValue possibleValue = fieldValue.getPossibleValue();
                    if (possibleValue != null) {
                        possibleValueId = possibleValue.getId();
                    } else {
                        possibleValueId = CoercionHelper.coerce(Long.class, fieldValue.getValue());
                    }
                    if (possibleValueId != null) {
                        fieldValue.setPossibleValue(paymentCustomFieldService.loadPossibleValue(possibleValueId));
                    }
                } else if (field.getType() == CustomField.Type.MEMBER) {
                    final Long memberId = CoercionHelper.coerce(Long.class, fieldValue.getValue());
                    if (memberId != null) {
                        final Element element = elementService.load(memberId);
                        if (element instanceof Member) {
                            fieldValue.setMemberValue((Member) element);
                        }
                    }
                }
            }
            request.setAttribute("customFields", entries);
        }
    }

    @Override
    protected void validateForm(final ActionContext context) {
        if (shouldValidateTransactionPassword(context, validatePayment(context))) {
            final ConfirmPaymentForm form = context.getForm();
            if (StringUtils.isEmpty(form.getTransactionPassword())) {
                throw new ValidationException("_transactionPassword", "login.transactionPassword", new RequiredError());
            }
        }
    }

    private boolean shouldValidateTransactionPassword(final ActionContext context, final DoPaymentDTO payment) {
        if (payment.getFrom() == null) {
            // When a logged member performing payments from himself
            final TransferType transferType = transferTypeService.load(payment.getTransferType().getId(), TransferType.Relationships.FROM);
            return context.isTransactionPasswordEnabled(transferType.getFrom());
        } else {
            return context.isTransactionPasswordEnabled();
        }
    }

    private DoPaymentDTO validatePayment(final ActionContext context) {
        final DoPaymentDTO payment = (DoPaymentDTO) context.getSession().getAttribute("payment");
        if (payment == null) {
            throw new ValidationException();
        }
        return payment;
    }
}
