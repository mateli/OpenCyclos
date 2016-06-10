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
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.access.AdminMemberPermission;
import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseFormAction;
import nl.strohalm.cyclos.entities.accounts.AccountType;
import nl.strohalm.cyclos.entities.accounts.SystemAccountType;
import nl.strohalm.cyclos.entities.accounts.loans.Loan;
import nl.strohalm.cyclos.entities.accounts.loans.LoanGroup;
import nl.strohalm.cyclos.entities.accounts.loans.LoanGroupQuery;
import nl.strohalm.cyclos.entities.accounts.loans.LoanParameters;
import nl.strohalm.cyclos.entities.accounts.loans.LoanPayment;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferType;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferTypeQuery;
import nl.strohalm.cyclos.entities.customization.fields.PaymentCustomField;
import nl.strohalm.cyclos.entities.customization.fields.PaymentCustomFieldValue;
import nl.strohalm.cyclos.entities.groups.AdminGroup;
import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.exceptions.PermissionDeniedException;
import nl.strohalm.cyclos.services.loangroups.LoanGroupService;
import nl.strohalm.cyclos.services.transactions.GrantLoanDTO;
import nl.strohalm.cyclos.services.transactions.GrantLoanWithInterestDTO;
import nl.strohalm.cyclos.services.transactions.GrantMultiPaymentLoanDTO;
import nl.strohalm.cyclos.services.transactions.GrantSinglePaymentLoanDTO;
import nl.strohalm.cyclos.services.transactions.LoanService;
import nl.strohalm.cyclos.services.transactions.TransactionContext;
import nl.strohalm.cyclos.services.transfertypes.TransferTypeService;
import nl.strohalm.cyclos.utils.ActionHelper;
import nl.strohalm.cyclos.utils.RelationshipHelper;
import nl.strohalm.cyclos.utils.binding.BeanBinder;
import nl.strohalm.cyclos.utils.binding.BeanCollectionBinder;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.binding.PropertyBinder;
import nl.strohalm.cyclos.utils.conversion.CoercionHelper;
import nl.strohalm.cyclos.utils.conversion.HtmlConverter;
import nl.strohalm.cyclos.utils.conversion.ReferenceConverter;
import nl.strohalm.cyclos.utils.validation.InvalidError;
import nl.strohalm.cyclos.utils.validation.ValidationException;

import org.apache.commons.collections.CollectionUtils;
import org.apache.struts.action.ActionForward;

/**
 * Action used to grant a loan to a given member or loan group
 * @author luis
 */
public class GrantLoanAction extends BaseFormAction {

    private LoanGroupService    loanGroupService;
    private LoanService         loanService;
    private TransferTypeService transferTypeService;

    @Inject
    public void setLoanGroupService(final LoanGroupService loanGroupService) {
        this.loanGroupService = loanGroupService;
    }

    @Inject
    public void setLoanService(final LoanService loanService) {
        this.loanService = loanService;
    }

    @Inject
    public void setTransferTypeService(final TransferTypeService transferTypeService) {
        this.transferTypeService = transferTypeService;
    }

    @Override
    protected ActionForward handleDisplay(final ActionContext context) throws Exception {

        final HttpServletRequest request = context.getRequest();
        final GrantLoanForm form = context.getForm();
        Member member = null;
        LoanGroup loanGroup = null;
        if (form.getMemberId() > 0L) {
            final Element element = elementService.load(form.getMemberId(), Element.Relationships.USER);
            if (element instanceof Member) {
                member = (Member) element;
            }
        } else if (form.getLoanGroupId() > 0L) {
            loanGroup = loanGroupService.load(form.getLoanGroupId(), LoanGroup.Relationships.MEMBERS);
        }

        if (member == null && loanGroup == null) {
            throw new ValidationException();
        }
        request.setAttribute("member", member);
        request.setAttribute("loanGroup", loanGroup);

        if (member != null) {
            form.setLoan("member", member.getId());
            AdminGroup adminGroup = context.getGroup();
            adminGroup = groupService.load(adminGroup.getId(), AdminGroup.Relationships.VIEW_INFORMATION_OF, Group.Relationships.TRANSFER_TYPES);

            // Get the possible transfer types
            List<TransferType> transferTypes;
            final ArrayList<SystemAccountType> systemAccounts = new ArrayList<SystemAccountType>(adminGroup.getViewInformationOf());
            if (CollectionUtils.isEmpty(systemAccounts)) {
                transferTypes = Collections.emptyList();
            } else {
                final TransferTypeQuery ttQuery = new TransferTypeQuery();
                ttQuery.setContext(TransactionContext.LOAN);
                ttQuery.setToOwner(member);
                ttQuery.setUsePriority(true);
                ttQuery.setFromAccountTypes(systemAccounts);
                transferTypes = transferTypeService.search(ttQuery);
                // Remove transfer types without permission
                final Collection<TransferType> transferTypesWithPermission = adminGroup.getTransferTypes();
                for (final Iterator<TransferType> iter = transferTypes.iterator(); iter.hasNext();) {
                    final TransferType transferType = iter.next();
                    if (!transferTypesWithPermission.contains(transferType)) {
                        iter.remove();
                    }
                }
            }
            if (transferTypes.isEmpty()) {
                return context.sendError("loan.error.noTransferType");
            }
            request.setAttribute("transferTypes", transferTypes);

            if (permissionService.hasPermission(AdminMemberPermission.LOAN_GROUPS_VIEW)) {
                // Get the loan groups of this member
                final LoanGroupQuery lgQuery = new LoanGroupQuery();
                lgQuery.setMember(member);
                final List<LoanGroup> loanGroups = loanGroupService.search(lgQuery);
                request.setAttribute("loanGroups", loanGroups);
            }
        } else {
            form.setLoan("loanGroup", loanGroup.getId());
        }

        return context.getInputForward();
    }

    @Override
    protected ActionForward handleSubmit(final ActionContext context) throws Exception {
        final GrantLoanDTO dto = resolveDTO(context);
        final GrantLoanForm form = context.getForm();
        context.getSession().setAttribute("loan", dto);

        final Map<String, Object> params = new HashMap<String, Object>();
        params.put("memberId", form.getMemberId());
        params.put("loanGroupId", form.getLoanGroupId());
        return ActionHelper.redirectWithParams(context.getRequest(), context.getSuccessForward(), params);
    }

    @Override
    protected void validateForm(final ActionContext context) {
        final GrantLoanDTO dto = resolveDTO(context);
        loanService.validate(dto);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private DataBinder<GrantLoanDTO> getDataBinder(final Loan.Type type) {
        final LocalSettings settings = settingsService.getLocalSettings();

        final BeanBinder<PaymentCustomFieldValue> customValueBinder = BeanBinder.instance(PaymentCustomFieldValue.class);
        customValueBinder.registerBinder("field", PropertyBinder.instance(PaymentCustomField.class, "field", ReferenceConverter.instance(PaymentCustomField.class)));
        customValueBinder.registerBinder("value", PropertyBinder.instance(String.class, "value", HtmlConverter.instance()));

        final BeanBinder binder = new BeanBinder();
        binder.registerBinder("member", PropertyBinder.instance(Member.class, "member", ReferenceConverter.instance(Member.class)));
        binder.registerBinder("loanGroup", PropertyBinder.instance(LoanGroup.class, "loanGroup", ReferenceConverter.instance(LoanGroup.class)));
        binder.registerBinder("amount", PropertyBinder.instance(BigDecimal.class, "amount", settings.getNumberConverter()));
        binder.registerBinder("date", PropertyBinder.instance(Calendar.class, "date", settings.getRawDateConverter()));
        binder.registerBinder("description", PropertyBinder.instance(String.class, "description"));
        binder.registerBinder("transferType", PropertyBinder.instance(TransferType.class, "transferType", ReferenceConverter.instance(TransferType.class)));
        binder.registerBinder("customValues", BeanCollectionBinder.instance(customValueBinder, "customValues"));

        switch (type) {
            case SINGLE_PAYMENT:
                binder.setType(GrantSinglePaymentLoanDTO.class);
                binder.registerBinder("repaymentDate", PropertyBinder.instance(Calendar.class, "repaymentDate", settings.getRawDateConverter()));
                break;
            case MULTI_PAYMENT:
                binder.setType(GrantMultiPaymentLoanDTO.class);
                final BeanBinder<LoanPayment> paymentBinder = BeanBinder.instance(LoanPayment.class);
                paymentBinder.registerBinder("expirationDate", PropertyBinder.instance(Calendar.class, "expirationDate", settings.getRawDateConverter()));
                paymentBinder.registerBinder("amount", PropertyBinder.instance(BigDecimal.class, "amount", settings.getNumberConverter()));
                binder.registerBinder("payments", BeanCollectionBinder.instance(paymentBinder, "payments"));
                break;
            case WITH_INTEREST:
                binder.setType(GrantLoanWithInterestDTO.class);
                binder.registerBinder("firstRepaymentDate", PropertyBinder.instance(Calendar.class, "firstRepaymentDate", settings.getRawDateConverter()));
                binder.registerBinder("paymentCount", PropertyBinder.instance(Integer.TYPE, "paymentCount"));
                break;
            default:
                throw new IllegalArgumentException("Invalid loan type: " + type);
        }
        return binder;
    }

    /**
     * Resolve the GrantLoanDTO
     */
    private GrantLoanDTO resolveDTO(final ActionContext context) {
        final GrantLoanForm form = context.getForm();
        final long transferTypeId = CoercionHelper.coerce(Long.TYPE, form.getLoan("transferType"));
        if (transferTypeId <= 0L) {
            throw new ValidationException();
        }
        final TransferType transferType = transferTypeService.load(transferTypeId, RelationshipHelper.nested(TransferType.Relationships.TO, AccountType.Relationships.CURRENCY));
        final LoanParameters loanParameters = transferType.getLoan();
        if (loanParameters == null || loanParameters.getType() == null) {
            throw new ValidationException("transferType", "transfer.type", new InvalidError());
        }
        final GrantLoanDTO dto = getDataBinder(loanParameters.getType()).readFromString(form.getLoan());
        dto.setTransferType(transferType);
        if (dto.getLoanGroup() != null && !permissionService.hasPermission(AdminMemberPermission.LOAN_GROUPS_VIEW)) {
            throw new PermissionDeniedException();
        }
        return dto;
    }

}
