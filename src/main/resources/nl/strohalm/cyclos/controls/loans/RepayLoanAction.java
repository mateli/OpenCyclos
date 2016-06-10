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
package nl.strohalm.cyclos.controls.loans;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.AccountType;
import nl.strohalm.cyclos.entities.accounts.loans.Loan;
import nl.strohalm.cyclos.entities.accounts.transactions.Payment;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferType;
import nl.strohalm.cyclos.entities.customization.fields.CustomFieldValue;
import nl.strohalm.cyclos.entities.customization.fields.PaymentCustomField;
import nl.strohalm.cyclos.entities.customization.fields.PaymentCustomFieldValue;
import nl.strohalm.cyclos.entities.exceptions.UnexpectedEntityException;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.services.transactions.LoanPaymentDTO;
import nl.strohalm.cyclos.services.transactions.PaymentService;
import nl.strohalm.cyclos.services.transactions.RepayLoanDTO;
import nl.strohalm.cyclos.services.transactions.exceptions.NotEnoughCreditsException;
import nl.strohalm.cyclos.services.transactions.exceptions.PartialInterestsAmountException;
import nl.strohalm.cyclos.services.transactions.exceptions.UpperCreditLimitReachedException;
import nl.strohalm.cyclos.utils.ActionHelper;
import nl.strohalm.cyclos.utils.RelationshipHelper;
import nl.strohalm.cyclos.utils.ResponseHelper;
import nl.strohalm.cyclos.utils.binding.BeanBinder;
import nl.strohalm.cyclos.utils.binding.BeanCollectionBinder;
import nl.strohalm.cyclos.utils.binding.PropertyBinder;
import nl.strohalm.cyclos.utils.conversion.HtmlConverter;
import nl.strohalm.cyclos.utils.conversion.UnitsConverter;
import nl.strohalm.cyclos.utils.validation.RequiredError;
import nl.strohalm.cyclos.utils.validation.ValidationException;

import org.apache.struts.action.ActionForward;

/**
 * Action used to repay a loan
 * @author luis
 */
public class RepayLoanAction extends LoanDetailsAction {

    private static final Relationship FETCH = RelationshipHelper.nested(Loan.Relationships.TRANSFER, Payment.Relationships.TYPE, TransferType.Relationships.FROM, AccountType.Relationships.CURRENCY);
    private PaymentService            paymentService;

    public PaymentService getPaymentService() {
        return paymentService;
    }

    @Override
    @Inject
    public void setPaymentService(final PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @Override
    protected Class<? extends LoanPaymentDTO> getDtoClass() {
        return RepayLoanDTO.class;
    }

    @Override
    protected ActionForward handleSubmit(final ActionContext context) throws Exception {
        final RepayLoanForm form = context.getForm();

        final RepayLoanDTO dto = (RepayLoanDTO) resolveLoanDTO(context);
        final Loan loan = dto.getLoan();
        if (shouldValidateTransactionPassword(context, loan)) {
            context.checkTransactionPassword(form.getTransactionPassword());
        }

        // Check which method we have to call
        try {
            loanService.repay(dto);
        } catch (final NotEnoughCreditsException e) {
            return context.sendError("loan.repayment.error.enoughCredits");
        } catch (final UpperCreditLimitReachedException e) {
            return context.sendError("loan.repayment.error.upperCreditLimit");
        } catch (final PartialInterestsAmountException e) {
            final AccountType accountType = loan.getTransfer().getType().getTo();
            final LocalSettings localSettings = settingsService.getLocalSettings();
            final UnitsConverter nc = localSettings.getUnitsConverter(accountType.getCurrency().getPattern());
            final BigDecimal baseRemainingAmount = e.getBaseRemainingAmount();
            final BigDecimal totalRemainingAmount = baseRemainingAmount.add(e.getInterestsAmount());
            return context.sendError("loan.repayment.error.partialInterestsAmount", nc.toString(baseRemainingAmount), nc.toString(totalRemainingAmount));
        }
        final Map<String, Object> params = new HashMap<String, Object>();
        params.put("loanId", form.getLoanId());
        params.put("memberId", form.getMemberId());
        params.put("loanGroupId", form.getLoanGroupId());
        context.sendMessage("loan.repaid");
        return ActionHelper.redirectWithParams(context.getRequest(), context.getSuccessForward(), params);
    }

    @Override
    protected ActionForward handleValidation(final ActionContext context) {
        try {
            // The super validation will handle the transaction password
            super.validateForm(context);

            final RepayLoanDTO dto = (RepayLoanDTO) resolveLoanDTO(context);
            Loan loan = dto.getLoan();
            final BigDecimal amount = dto.getAmount();

            final ValidationException val = new ValidationException();
            val.setPropertyKey("amount", "loan.repayment.amount");
            if (loan == null) {
                val.addPropertyError("loan", new RequiredError());
            }
            if (amount == null || amount.compareTo(paymentService.getMinimumPayment()) == -1) {
                val.addPropertyError("amount", new RequiredError());
            }
            val.throwIfHasErrors();

            loan = loanService.load(loan.getId(), FETCH);
            AccountType accountType;
            try {
                accountType = loan.getTransfer().getType().getLoan().getRepaymentType().getFrom();
            } catch (final Exception e) {
                throw new UnexpectedEntityException("Unable to retrieve loan account type");
            }
            final LocalSettings settings = settingsService.getLocalSettings();
            final UnitsConverter unitsConverter = settings.getUnitsConverter(accountType.getCurrency().getPattern());

            final Map<String, Object> fields = new HashMap<String, Object>();
            fields.put("confirmationMessage", context.message("loan.repayment.confirmationMessage", unitsConverter.toString(amount)));

            responseHelper.writeStatus(context.getResponse(), ResponseHelper.Status.SUCCESS, fields);
        } catch (final ValidationException e) {
            responseHelper.writeValidationErrors(context.getResponse(), e);
        }
        return null;
    }

    @Override
    protected void initDataBinder(final BeanBinder<? extends LoanPaymentDTO> binder) {
        super.initDataBinder(binder);
        final LocalSettings localSettings = settingsService.getLocalSettings();
        final BeanBinder<? extends CustomFieldValue> customValueBinder = BeanBinder.instance(PaymentCustomFieldValue.class);
        binder.registerBinder("amount", PropertyBinder.instance(BigDecimal.class, "amount", localSettings.getNumberConverter()));
        binder.registerBinder("date", PropertyBinder.instance(Calendar.class, "date", localSettings.getRawDateConverter()));
        customValueBinder.registerBinder("field", PropertyBinder.instance(PaymentCustomField.class, "field"));
        customValueBinder.registerBinder("value", PropertyBinder.instance(String.class, "value", HtmlConverter.instance()));
        binder.registerBinder("customValues", BeanCollectionBinder.instance(customValueBinder, "customValues"));
    }

}
