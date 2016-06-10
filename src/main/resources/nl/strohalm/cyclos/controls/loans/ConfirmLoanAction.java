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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseFormAction;
import nl.strohalm.cyclos.entities.accounts.AccountType;
import nl.strohalm.cyclos.entities.accounts.loans.Loan;
import nl.strohalm.cyclos.entities.accounts.loans.LoanGroup;
import nl.strohalm.cyclos.entities.accounts.loans.LoanPayment;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferType;
import nl.strohalm.cyclos.entities.customization.fields.CustomField;
import nl.strohalm.cyclos.entities.customization.fields.CustomFieldValue;
import nl.strohalm.cyclos.entities.customization.fields.PaymentCustomField;
import nl.strohalm.cyclos.entities.customization.fields.PaymentCustomFieldValue;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.services.customization.PaymentCustomFieldService;
import nl.strohalm.cyclos.services.loangroups.LoanGroupService;
import nl.strohalm.cyclos.services.transactions.DoPaymentDTO;
import nl.strohalm.cyclos.services.transactions.GrantLoanDTO;
import nl.strohalm.cyclos.services.transactions.GrantLoanWithInterestDTO;
import nl.strohalm.cyclos.services.transactions.GrantMultiPaymentLoanDTO;
import nl.strohalm.cyclos.services.transactions.LoanService;
import nl.strohalm.cyclos.services.transactions.PaymentService;
import nl.strohalm.cyclos.services.transactions.ProjectionDTO;
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

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForward;

/**
 * Action used to confirm a loan
 * @author luis
 */
public class ConfirmLoanAction extends BaseFormAction {

    private LoanService               loanService;
    private LoanGroupService          loanGroupService;
    private TransactionFeeService     transactionFeeService;
    private PaymentCustomFieldService paymentCustomFieldService;
    private PaymentService            paymentService;
    private TransferTypeService       transferTypeService;

    private CustomFieldHelper         customFieldHelper;

    @Inject
    public void setCustomFieldHelper(final CustomFieldHelper customFieldHelper) {
        this.customFieldHelper = customFieldHelper;
    }

    @Inject
    public void setLoanGroupService(final LoanGroupService loanGroupService) {
        this.loanGroupService = loanGroupService;
    }

    @Inject
    public void setLoanService(final LoanService loanService) {
        this.loanService = loanService;
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
        final ConfirmLoanForm form = context.getForm();
        final GrantLoanDTO dto = validateLoan(context);

        // Check for the transaction password, if needed
        if (context.isTransactionPasswordEnabled()) {
            context.checkTransactionPassword(form.getTransactionPassword());
        }

        // Check which method we need to use to grant the loan
        Loan loan;
        try {
            loan = loanService.grant(dto);
        } catch (final CreditsException e) {
            return context.sendError(actionHelper.resolveErrorKey(e), actionHelper.resolveParameters(e));
        } catch (final AuthorizedPaymentInPastException e) {
            return context.sendError("payment.error.authorizedInPast");
        }
        final boolean pending = loan.getTransfer().getProcessDate() == null;
        context.sendMessage(pending ? "loan.awaitingAuthorization" : "loan.granted");
        if (form.getMemberId() > 0) {
            return ActionHelper.redirectWithParam(context.getRequest(), context.findForward("member"), "memberId", form.getMemberId());
        } else {
            return ActionHelper.redirectWithParam(context.getRequest(), context.findForward("loanGroup"), "loanGroupId", form.getLoanGroupId());
        }
    }

    @Override
    protected void prepareForm(final ActionContext context) throws Exception {
        final GrantLoanDTO loan = validateLoan(context);

        // Check for transaction password
        final HttpServletRequest request = context.getRequest();
        final boolean requestTransactionPassword = context.isTransactionPasswordEnabled();
        if (requestTransactionPassword) {
            context.validateTransactionPassword();
        }
        request.setAttribute("requestTransactionPassword", requestTransactionPassword);

        // Fetch related data
        final Member member = elementService.load(loan.getMember().getId(), Element.Relationships.USER);
        final TransferType transferType = transferTypeService.load(loan.getTransferType().getId(), RelationshipHelper.nested(TransferType.Relationships.FROM, AccountType.Relationships.CURRENCY), TransferType.Relationships.TO);
        final LoanGroup loanGroup = loan.getLoanGroup() == null ? null : loanGroupService.load(loan.getLoanGroup().getId(), LoanGroup.Relationships.MEMBERS);
        final BigDecimal amount = loan.getAmount();
        loan.setLoanGroup(loanGroup);
        loan.setMember(member);
        loan.setTransferType(transferType);
        request.setAttribute("unitsPattern", transferType.getFrom().getCurrency().getPattern());

        if (loanGroup != null) {
            // Ensure the responsible is the first member shown and the list will be sorted
            final List<Member> membersInGroup = new ArrayList<Member>(loanGroup.getMembers());
            final LocalSettings localSettings = settingsService.getLocalSettings();
            Collections.sort(membersInGroup, localSettings.getMemberComparator());
            membersInGroup.remove(member);
            membersInGroup.add(0, member);
            request.setAttribute("membersInGroup", membersInGroup);
        }

        // Get the loan payments
        List<LoanPayment> payments = null;
        switch (loan.getLoanType()) {
            case MULTI_PAYMENT:
                payments = ((GrantMultiPaymentLoanDTO) loan).getPayments();
                break;
            case WITH_INTEREST:
                final GrantLoanWithInterestDTO dto = (GrantLoanWithInterestDTO) loan;
                final ProjectionDTO projection = new ProjectionDTO();
                projection.setAmount(dto.getAmount());
                projection.setDate(dto.getDate());
                projection.setFirstExpirationDate(dto.getFirstRepaymentDate());
                projection.setTransferType(dto.getTransferType());
                projection.setPaymentCount(dto.getPaymentCount());
                payments = loanService.calculatePaymentProjection(projection);
                break;
        }
        request.setAttribute("payments", payments);

        // Return the custom field values
        final Collection<PaymentCustomFieldValue> customValues = loan.getCustomValues();
        if (customValues != null) {
            final List<PaymentCustomField> customFields = paymentCustomFieldService.list(transferType, false);
            final Collection<Entry> entries = customFieldHelper.buildEntries(customFields, customValues);
            // Load the value for enumerated and member values, since this collection was built from direct databinding with ids only
            for (final Entry entry : entries) {
                final CustomField field = entry.getField();
                final CustomFieldValue fieldValue = entry.getValue();
                if (field.getType() == CustomField.Type.ENUMERATED) {
                    final Long possibleValueId = CoercionHelper.coerce(Long.class, fieldValue.getValue());
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

        // Store the transaction fees
        final TransactionFeePreviewDTO preview = transactionFeeService.preview(context.getAccountOwner(), member, transferType, amount);
        request.setAttribute("finalAmount", preview.getFinalAmount());
        request.setAttribute("fees", preview.getFees());

        // Check if would require authorization
        final DoPaymentDTO payment = new DoPaymentDTO();
        payment.setTransferType(loan.getTransferType());
        payment.setAmount(loan.getAmount());
        payment.setTo(member);
        request.setAttribute("wouldRequireAuthorization", paymentService.wouldRequireAuthorization(payment));
    }

    @Override
    protected void validateForm(final ActionContext context) {
        if (context.isTransactionPasswordEnabled()) {
            final ConfirmLoanForm form = context.getForm();
            if (StringUtils.isEmpty(form.getTransactionPassword())) {
                throw new ValidationException("_transactionPassword", "login.transactionPassword", new RequiredError());
            }
        }
    }

    private GrantLoanDTO validateLoan(final ActionContext context) {
        final GrantLoanDTO payment = (GrantLoanDTO) context.getSession().getAttribute("loan");
        if (payment == null) {
            throw new ValidationException();
        }
        return payment;
    }
}
