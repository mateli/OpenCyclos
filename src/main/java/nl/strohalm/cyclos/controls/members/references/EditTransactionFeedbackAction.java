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
package nl.strohalm.cyclos.controls.members.references;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.access.AdminMemberPermission;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.entities.accounts.Account;
import nl.strohalm.cyclos.entities.accounts.AccountType;
import nl.strohalm.cyclos.entities.accounts.MemberAccount;
import nl.strohalm.cyclos.entities.accounts.transactions.Payment;
import nl.strohalm.cyclos.entities.accounts.transactions.ScheduledPayment;
import nl.strohalm.cyclos.entities.accounts.transactions.Transfer;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.members.Reference;
import nl.strohalm.cyclos.entities.members.TransactionFeedback;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.services.elements.TransactionFeedbackAction;
import nl.strohalm.cyclos.utils.ActionHelper;
import nl.strohalm.cyclos.utils.EntityHelper;
import nl.strohalm.cyclos.utils.RelationshipHelper;
import nl.strohalm.cyclos.utils.access.LoggedUser;
import nl.strohalm.cyclos.utils.binding.BeanBinder;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.binding.PropertyBinder;
import nl.strohalm.cyclos.utils.validation.RequiredError;
import nl.strohalm.cyclos.utils.validation.ValidationException;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForward;

/**
 * Action used to view / edit a transaction feedback
 * @author luis
 */
public class EditTransactionFeedbackAction extends BaseEditReferenceAction<TransactionFeedback> {

    private DataBinder<TransactionFeedback> dataBinder;

    public DataBinder<TransactionFeedback> getDataBinder() {
        if (dataBinder == null) {
            final BeanBinder<TransactionFeedback> binder = BeanBinder.instance(TransactionFeedback.class);
            initBinder(binder);
            binder.registerBinder("transfer", PropertyBinder.instance(Transfer.class, "transfer"));
            binder.registerBinder("scheduledPayment", PropertyBinder.instance(ScheduledPayment.class, "scheduledPayment"));
            binder.registerBinder("replyComments", PropertyBinder.instance(String.class, "replyComments"));
            binder.registerBinder("adminComments", PropertyBinder.instance(String.class, "adminComments"));
            dataBinder = binder;
        }
        return dataBinder;
    }

    @Override
    protected ActionForward handleSubmit(final ActionContext context) throws Exception {
        final EditReferenceForm form = context.getForm();
        final long memberId = form.getMemberId();
        TransactionFeedback feedback = resolveReference(context);
        final TransactionFeedbackAction tfa = referenceService.getPossibleAction(feedback);
        feedback = referenceService.save(feedback);

        switch (tfa) {
            case COMMENTS:
                context.sendMessage("reference.transactionFeedback.saved");
                break;
            case REPLY_COMMENTS:
                context.sendMessage("reference.transactionFeedback.replyComments.saved");
                break;
            case ADMIN_EDIT:
                context.sendMessage("reference.transactionFeedback.saved");
                break;
        }
        final Map<String, Object> params = new HashMap<String, Object>();
        params.put("memberId", memberId);
        params.put("referenceId", feedback.getId());
        return ActionHelper.redirectWithParams(context.getRequest(), context.getSuccessForward(), params);
    }

    @Override
    protected void prepareForm(final ActionContext context) throws Exception {
        final EditReferenceForm form = context.getForm();
        final HttpServletRequest request = context.getRequest();
        long feedbackId = form.getReferenceId();
        final long transferId = form.getTransferId();
        final long scheduledPaymentId = form.getScheduledPaymentId();

        // Retrieve the reference
        TransactionFeedback transactionFeedback = null;
        boolean showPayment = false;
        boolean canComment = false;
        boolean canReply = false;
        boolean editable = false;

        // Check whether it's by payment
        if (transferId > 0 || scheduledPaymentId > 0) {
            // Is a new transaction feedback
            Payment payment;
            if (transferId > 0) {
                payment = paymentService.load(transferId, RelationshipHelper.nested(Payment.Relationships.FROM, Account.Relationships.TYPE, AccountType.Relationships.CURRENCY), RelationshipHelper.nested(Payment.Relationships.FROM, MemberAccount.Relationships.MEMBER), RelationshipHelper.nested(Payment.Relationships.TO, MemberAccount.Relationships.MEMBER));
            } else {
                payment = scheduledPaymentService.load(scheduledPaymentId, RelationshipHelper.nested(Payment.Relationships.FROM, Account.Relationships.TYPE, AccountType.Relationships.CURRENCY), RelationshipHelper.nested(Payment.Relationships.FROM, MemberAccount.Relationships.MEMBER), RelationshipHelper.nested(Payment.Relationships.TO, MemberAccount.Relationships.MEMBER));
            }

            // Check whether the payment already exists
            try {
                final TransactionFeedback feedback = referenceService.loadTransactionFeedback(payment);

                // Already exists - set the id
                feedbackId = feedback.getId();
            } catch (final EntityNotFoundException e) {
                // Don't exists - create a new one
                final Member loggedMember = (Member) context.getAccountOwner();
                if (!loggedMember.equals(payment.getFromOwner())) {
                    throw new ValidationException();
                }
                transactionFeedback = new TransactionFeedback();
                transactionFeedback.setPayment(payment);
                transactionFeedback.setFrom(loggedMember);
                transactionFeedback.setTo((Member) payment.getToOwner());
                showPayment = true;
                canComment = true;
            }

        }

        // Check by id
        if (feedbackId > 0) {
            try {
                transactionFeedback = (TransactionFeedback) referenceService.load(feedbackId, Reference.Relationships.FROM, Reference.Relationships.TO, RelationshipHelper.nested(TransactionFeedback.Relationships.TRANSFER, Payment.Relationships.FROM, MemberAccount.Relationships.MEMBER), RelationshipHelper.nested(TransactionFeedback.Relationships.TRANSFER, Payment.Relationships.TO, MemberAccount.Relationships.MEMBER), RelationshipHelper.nested(TransactionFeedback.Relationships.TRANSFER, Payment.Relationships.TYPE), RelationshipHelper.nested(TransactionFeedback.Relationships.SCHEDULED_PAYMENT, Payment.Relationships.FROM, MemberAccount.Relationships.MEMBER), RelationshipHelper.nested(TransactionFeedback.Relationships.SCHEDULED_PAYMENT, Payment.Relationships.TO, MemberAccount.Relationships.MEMBER), RelationshipHelper.nested(TransactionFeedback.Relationships.SCHEDULED_PAYMENT, Payment.Relationships.TYPE));
            } catch (final Exception e) {
                throw new ValidationException();
            }
            final Payment payment = transactionFeedback.getPayment();
            if (payment instanceof ScheduledPayment) {
                form.setScheduledPaymentId(payment.getId());
            } else {
                form.setTransferId(payment.getId());
            }
            showPayment = context.isAdmin() || context.getAccountOwner().equals(payment.getFromOwner()) || context.getAccountOwner().equals(payment.getToOwner());
            canReply = (!LoggedUser.isAdministrator()) && (LoggedUser.element().getAccountOwner().equals(transactionFeedback.getTo()) && StringUtils.isEmpty(transactionFeedback.getReplyComments())) && referenceService.canReplyFeedbackNow(transactionFeedback);
            editable = permissionService.hasPermission(AdminMemberPermission.TRANSACTION_FEEDBACKS_MANAGE);
        }

        // Couldn't find the feedback
        if (transactionFeedback == null) {
            throw new ValidationException();
        }

        getDataBinder().writeAsString(form.getReference(), transactionFeedback);

        if (editable) {
            canComment = true;
            canReply = true;
        }

        final LocalSettings localSettings = settingsService.getLocalSettings();

        request.setAttribute("transactionFeedback", transactionFeedback);
        request.setAttribute("levels", localSettings.getReferenceLevelList());
        request.setAttribute("showPayment", showPayment);
        request.setAttribute("canComment", canComment);
        request.setAttribute("canReply", canReply);
        request.setAttribute("editable", editable);
    }

    @Override
    protected TransactionFeedback resolveReference(final ActionContext context) {
        final EditReferenceForm form = context.getForm();
        final TransactionFeedback feedback = getDataBinder().readFromString(form.getReference());
        if (form.getTransferId() > 0) {
            feedback.setTransfer(EntityHelper.reference(Transfer.class, form.getTransferId()));
        } else if (form.getScheduledPaymentId() > 0) {
            feedback.setScheduledPayment(EntityHelper.reference(ScheduledPayment.class, form.getScheduledPaymentId()));
        }
        return feedback;
    }

    @Override
    protected void validateForm(final ActionContext context) {
        final TransactionFeedback transactionFeedback = resolveReference(context);
        switch (referenceService.getPossibleAction(transactionFeedback)) {
            case COMMENTS:
                referenceService.validate(transactionFeedback);
                break;
            case REPLY_COMMENTS:
                if (StringUtils.isEmpty(transactionFeedback.getReplyComments())) {
                    throw new ValidationException("replyComments", "reference.replyComments", new RequiredError());
                }
                break;
        }
    }
}
