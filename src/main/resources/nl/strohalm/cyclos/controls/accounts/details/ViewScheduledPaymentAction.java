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
package nl.strohalm.cyclos.controls.accounts.details;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseFormAction;
import nl.strohalm.cyclos.controls.payments.scheduled.ScheduledPaymentForm;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.AccountOwner;
import nl.strohalm.cyclos.entities.accounts.MemberAccount;
import nl.strohalm.cyclos.entities.accounts.transactions.Payment;
import nl.strohalm.cyclos.entities.accounts.transactions.ScheduledPayment;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferType;
import nl.strohalm.cyclos.entities.customization.fields.PaymentCustomField;
import nl.strohalm.cyclos.entities.members.Administrator;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.members.Operator;
import nl.strohalm.cyclos.services.customization.PaymentCustomFieldService;
import nl.strohalm.cyclos.services.transactions.ScheduledPaymentService;
import nl.strohalm.cyclos.utils.CustomFieldHelper;
import nl.strohalm.cyclos.utils.CustomFieldHelper.Entry;
import nl.strohalm.cyclos.utils.RelationshipHelper;
import nl.strohalm.cyclos.utils.validation.RequiredError;
import nl.strohalm.cyclos.utils.validation.ValidationException;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;

/**
 * Action used to view the details of a scheduled payment
 * @author Jefferson Magno
 */
public class ViewScheduledPaymentAction extends BaseFormAction {

    public static final Relationship[] FETCH = { Payment.Relationships.CUSTOM_VALUES, RelationshipHelper.nested(Payment.Relationships.FROM, MemberAccount.Relationships.MEMBER), RelationshipHelper.nested(Payment.Relationships.TO, MemberAccount.Relationships.MEMBER), RelationshipHelper.nested(Payment.Relationships.TYPE, TransferType.Relationships.TO), Payment.Relationships.BY, ScheduledPayment.Relationships.TRANSFERS };

    private PaymentCustomFieldService  paymentCustomFieldService;
    protected ScheduledPaymentService  scheduledPaymentService;

    private CustomFieldHelper          customFieldHelper;

    @Inject
    public void setCustomFieldHelper(final CustomFieldHelper customFieldHelper) {
        this.customFieldHelper = customFieldHelper;
    }

    @Inject
    public void setPaymentCustomFieldService(final PaymentCustomFieldService paymentCustomFieldService) {
        this.paymentCustomFieldService = paymentCustomFieldService;
    }

    @Inject
    public void setScheduledPaymentService(final ScheduledPaymentService scheduledPaymentService) {
        this.scheduledPaymentService = scheduledPaymentService;
    }

    protected void checkTransactionPassword(final ActionContext context, final ScheduledPayment payment) {
        if (shouldValidateTransactionPassword(context, payment)) {
            String transactionPassword;
            final ActionForm form = context.getForm();
            if (form instanceof ViewTransactionForm) {
                transactionPassword = ((ViewTransactionForm) form).getTransactionPassword();
            } else {
                transactionPassword = ((ScheduledPaymentForm) form).getTransactionPassword();
            }
            context.checkTransactionPassword(transactionPassword);
        }
    }

    @Override
    protected void prepareForm(final ActionContext context) throws Exception {
        final HttpServletRequest request = context.getRequest();

        // Load the scheduled payment
        final ScheduledPayment payment = resolveScheduledPayment(context);

        // Check if the logged user can block, cancel, or pay next open payment
        final boolean canCancel = scheduledPaymentService.canCancel(payment);
        final boolean canBlock = scheduledPaymentService.canBlock(payment);
        final boolean canUnblock = scheduledPaymentService.canUnblock(payment);
        boolean canPayNow = false;
        if (payment.getTransfers().size() == 1) {
            canPayNow = scheduledPaymentService.canPayNow(payment.getTransfers().iterator().next());
        }
        final String comments = null;

        // Resolve the by element
        boolean showBy = false;
        final Element by = payment.getBy();
        if (by != null) {
            if (by instanceof Administrator) {
                if (context.isAdmin()) {
                    request.setAttribute("byAdmin", by);
                } else {
                    // Don't disclose to member which admin made the payment
                    request.setAttribute("bySystem", true);
                }
                showBy = true;
            } else if ((by instanceof Operator) && (context.isMemberOf((Operator) by) || context.getElement().equals(by))) {
                request.setAttribute("byOperator", by);
                showBy = true;
            } else {
                final AccountOwner fromOwner = payment.getFrom().getOwner();
                final Member member = (Member) by.getAccountOwner();
                request.setAttribute("byMember", member);
                showBy = !member.equals(fromOwner);
            }
        }

        // Resolve the custom fields
        final List<PaymentCustomField> customFields = paymentCustomFieldService.list(payment.getType(), true);
        final Collection<Entry> entries = customFieldHelper.buildEntries(customFields, payment.getCustomValues());
        request.setAttribute("customFields", entries);

        // Store the request attributes
        if (canCancel || canBlock || canUnblock) {
            request.setAttribute("requestTransactionPassword", shouldValidateTransactionPassword(context, payment));
        }
        if (canPayNow) {
            final ScheduledPaymentForm form = context.getForm();
            form.setShowActions(true);
        }
        request.setAttribute("canCancel", canCancel);
        request.setAttribute("canBlock", canBlock);
        request.setAttribute("canPayNow", canPayNow);
        request.setAttribute("canUnblock", canUnblock);
        request.setAttribute("comments", comments);
        request.setAttribute("showBy", showBy);
        request.setAttribute("payment", payment);
    }

    protected ScheduledPayment resolveScheduledPayment(final ActionContext context) {
        final ScheduledPaymentForm form = context.getForm();
        final long id = form.getPaymentId();
        if (id <= 0L) {
            throw new ValidationException();
        }
        return scheduledPaymentService.load(form.getPaymentId(), FETCH);
    }

    protected boolean shouldValidateTransactionPassword(final ActionContext context, final ScheduledPayment scheduledPayment) {
        if (context.getAccountOwner().equals(scheduledPayment.getFromOwner())) {
            // When the logged member is the payment performer
            return context.isTransactionPasswordEnabled(scheduledPayment.getType().getFrom());
        } else {
            return context.isTransactionPasswordEnabled();
        }
    }

    @Override
    protected void validateForm(final ActionContext context) {
        if (shouldValidateTransactionPassword(context, resolveScheduledPayment(context))) {
            final ScheduledPaymentForm form = context.getForm();
            if (StringUtils.isEmpty(form.getTransactionPassword())) {
                throw new ValidationException("_transactionPassword", "login.transactionPassword", new RequiredError());
            }
        }
    }

}
