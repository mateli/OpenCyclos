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
package nl.strohalm.cyclos.controls.accounts.guarantees.guarantees;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.access.AdminMemberPermission;
import nl.strohalm.cyclos.access.BrokerPermission;
import nl.strohalm.cyclos.access.MemberPermission;
import nl.strohalm.cyclos.access.OperatorPermission;
import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseFormAction;
import nl.strohalm.cyclos.entities.Entity;
import nl.strohalm.cyclos.entities.accounts.MemberAccount;
import nl.strohalm.cyclos.entities.accounts.guarantees.Guarantee;
import nl.strohalm.cyclos.entities.accounts.guarantees.Guarantee.Status;
import nl.strohalm.cyclos.entities.accounts.guarantees.GuaranteeLog;
import nl.strohalm.cyclos.entities.accounts.guarantees.GuaranteeType;
import nl.strohalm.cyclos.entities.accounts.loans.Loan;
import nl.strohalm.cyclos.entities.accounts.transactions.Payment;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferType;
import nl.strohalm.cyclos.entities.customization.fields.PaymentCustomField;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.services.accounts.guarantees.GuaranteeService;
import nl.strohalm.cyclos.services.customization.PaymentCustomFieldService;
import nl.strohalm.cyclos.utils.ActionHelper;
import nl.strohalm.cyclos.utils.ActionHelper.ByElementExtractor;
import nl.strohalm.cyclos.utils.CustomFieldHelper;
import nl.strohalm.cyclos.utils.CustomFieldHelper.Entry;
import nl.strohalm.cyclos.utils.RelationshipHelper;

import org.apache.struts.action.ActionForward;

public class GuaranteeDetailsAction extends BaseFormAction {

    private GuaranteeService          guaranteeService;
    private PaymentCustomFieldService paymentCustomFieldService;

    private CustomFieldHelper         customFieldHelper;

    @Inject
    public void setCustomFieldHelper(final CustomFieldHelper customFieldHelper) {
        this.customFieldHelper = customFieldHelper;
    }

    @Inject
    public void setGuaranteeService(final GuaranteeService guaranteeService) {
        this.guaranteeService = guaranteeService;
    }

    @Inject
    public void setPaymentCustomFieldService(final PaymentCustomFieldService paymentCustomFieldService) {
        this.paymentCustomFieldService = paymentCustomFieldService;
    }

    @Override
    protected ActionForward handleSubmit(final ActionContext context) throws Exception {
        return null;
    }

    @Override
    protected void prepareForm(final ActionContext context) throws Exception {
        final HttpServletRequest request = context.getRequest();
        final GuaranteeDetailsForm form = context.getForm();
        final Long id = form.getGuaranteeId();
        Long certificationId = -1L;
        final Guarantee guarantee = guaranteeService.load(id, Guarantee.Relationships.PAYMENT_OBLIGATIONS, Guarantee.Relationships.LOGS, RelationshipHelper.nested(Guarantee.Relationships.LOAN, Loan.Relationships.TRANSFER, Payment.Relationships.TO, MemberAccount.Relationships.MEMBER));

        if (guarantee.getCertification() != null) {
            certificationId = guarantee.getCertification().getId();
        }
        final ByElementExtractor extractor = new ByElementExtractor() {
            @Override
            public Element getByElement(final Entity entity) {
                return ((GuaranteeLog) entity).getBy();
            }
        };

        final boolean showPaymentObligations = !context.isAdmin() || permissionService.hasPermission(AdminMemberPermission.GUARANTEES_VIEW_PAYMENT_OBLIGATIONS);

        final TransferType transferType = guarantee.getGuaranteeType().getLoanTransferType();
        final List<PaymentCustomField> customFields = paymentCustomFieldService.list(transferType, true);
        final Collection<Entry> entries = customFieldHelper.buildEntries(customFields, guarantee.getCustomValues());
        request.setAttribute("customFields", entries);

        request.setAttribute("certificationId", certificationId);
        request.setAttribute("guarantee", guarantee);
        request.setAttribute("showPaymentObligations", showPaymentObligations);
        request.setAttribute("logsBy", ActionHelper.getByElements(context, guarantee.getLogs(), extractor));
        request.setAttribute("canAccept", guaranteeService.canChangeStatus(guarantee, Status.ACCEPTED));
        request.setAttribute("canDeny", guaranteeService.canChangeStatus(guarantee, Status.REJECTED));
        request.setAttribute("canCancel", guaranteeService.canChangeStatus(guarantee, Status.CANCELLED));
        request.setAttribute("canDelete", guaranteeService.canRemoveGuarantee(guarantee));
        request.setAttribute("isWithBuyerOnly", guarantee.getGuaranteeType().getModel() == GuaranteeType.Model.WITH_BUYER_ONLY);
        request.setAttribute("fixedFeeType", GuaranteeType.FeeType.FIXED);
        request.setAttribute("showCurrentFeeValues", showCurrentFeeValues(guarantee));
        request.setAttribute("showLoan", showLoan(context, guarantee));
    }

    private boolean showCurrentFeeValues(final Guarantee guarantee) {
        return guarantee.getLoan() != null && guarantee.getStatus() == Guarantee.Status.ACCEPTED;
    }

    private boolean showLoan(final ActionContext context, final Guarantee guarantee) {
        if (guarantee.getLoan() == null) {
            return false;
        }

        return permissionService.permission(guarantee.getBuyer())
                .admin(guarantee.getLoan().getStatus().isRelatedToAuthorization() ? AdminMemberPermission.LOANS_VIEW_AUTHORIZED : AdminMemberPermission.LOANS_VIEW)
                .member(MemberPermission.LOANS_VIEW)
                .operator(OperatorPermission.LOANS_VIEW)
                .broker(BrokerPermission.LOANS_VIEW)
                .hasPermission();
    }
}
