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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.access.AdminMemberPermission;
import nl.strohalm.cyclos.access.AdminSystemPermission;
import nl.strohalm.cyclos.access.BrokerPermission;
import nl.strohalm.cyclos.access.MemberPermission;
import nl.strohalm.cyclos.access.OperatorPermission;
import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseFormAction;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.Account;
import nl.strohalm.cyclos.entities.accounts.AccountOwner;
import nl.strohalm.cyclos.entities.accounts.MemberAccount;
import nl.strohalm.cyclos.entities.accounts.transactions.AuthorizationLevel;
import nl.strohalm.cyclos.entities.accounts.transactions.Payment;
import nl.strohalm.cyclos.entities.accounts.transactions.ScheduledPayment;
import nl.strohalm.cyclos.entities.accounts.transactions.Transfer;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferAuthorization;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferType;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferType.TransactionHierarchyVisibility;
import nl.strohalm.cyclos.entities.customization.fields.PaymentCustomField;
import nl.strohalm.cyclos.entities.groups.AdminGroup;
import nl.strohalm.cyclos.entities.groups.SystemGroup;
import nl.strohalm.cyclos.entities.members.Administrator;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.members.Operator;
import nl.strohalm.cyclos.services.accounts.guarantees.GuaranteeService;
import nl.strohalm.cyclos.services.accounts.rates.RateService;
import nl.strohalm.cyclos.services.customization.PaymentCustomFieldService;
import nl.strohalm.cyclos.services.transactions.PaymentService;
import nl.strohalm.cyclos.services.transactions.ScheduledPaymentService;
import nl.strohalm.cyclos.services.transactions.TransferAuthorizationService;
import nl.strohalm.cyclos.utils.CustomFieldHelper;
import nl.strohalm.cyclos.utils.CustomFieldHelper.Entry;
import nl.strohalm.cyclos.utils.RelationshipHelper;
import nl.strohalm.cyclos.utils.validation.RequiredError;
import nl.strohalm.cyclos.utils.validation.ValidationException;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

/**
 * Action used to view the details of a transaction
 * @author luis
 * @author jefferson
 */
public class ViewTransactionAction extends BaseFormAction {

    public static final Relationship[]     FETCH = { Transfer.Relationships.CHILDREN, Payment.Relationships.CUSTOM_VALUES, RelationshipHelper.nested(Payment.Relationships.FROM, MemberAccount.Relationships.MEMBER, Element.Relationships.GROUP), RelationshipHelper.nested(Payment.Relationships.FROM, Account.Relationships.TYPE), RelationshipHelper.nested(Payment.Relationships.TO, MemberAccount.Relationships.MEMBER, Element.Relationships.GROUP), RelationshipHelper.nested(Payment.Relationships.TO, Account.Relationships.TYPE), RelationshipHelper.nested(Payment.Relationships.TYPE, TransferType.Relationships.TO), RelationshipHelper.nested(Transfer.Relationships.SCHEDULED_PAYMENT, ScheduledPayment.Relationships.TRANSFERS), Payment.Relationships.BY, Transfer.Relationships.RECEIVER, Transfer.Relationships.NEXT_AUTHORIZATION_LEVEL, Transfer.Relationships.AUTHORIZATIONS, Transfer.Relationships.CHARGEBACK_OF, Transfer.Relationships.CHARGED_BACK_BY };

    protected ScheduledPaymentService      scheduledPaymentService;

    protected TransferAuthorizationService transferAuthorizationService;
    protected PaymentService               paymentService;
    protected PaymentCustomFieldService    paymentCustomFieldService;
    protected RateService                  rateService;
    protected GuaranteeService             guaranteeService;

    private CustomFieldHelper              customFieldHelper;

    @Inject
    public void setCustomFieldHelper(final CustomFieldHelper customFieldHelper) {
        this.customFieldHelper = customFieldHelper;
    }

    @Inject
    public final void setGuaranteeService(final GuaranteeService guaranteeService) {
        this.guaranteeService = guaranteeService;
    }

    @Inject
    public final void setPaymentCustomFieldService(final PaymentCustomFieldService paymentCustomFieldService) {
        this.paymentCustomFieldService = paymentCustomFieldService;
    }

    @Inject
    public final void setPaymentService(final PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @Inject
    public final void setRateService(final RateService rateService) {
        this.rateService = rateService;
    }

    @Inject
    public final void setScheduledPaymentService(final ScheduledPaymentService scheduledPaymentService) {
        this.scheduledPaymentService = scheduledPaymentService;
    }

    @Inject
    public final void setTransferAuthorizationService(final TransferAuthorizationService transferAuthorizationService) {
        this.transferAuthorizationService = transferAuthorizationService;
    }

    protected void checkTransactionPassword(final ActionContext context, final Transfer transfer) {
        if (shouldValidateTransactionPassword(context, transfer)) {
            final ViewTransactionForm form = context.getForm();
            context.checkTransactionPassword(form.getTransactionPassword());
        }
    }

    @Override
    protected void prepareForm(final ActionContext context) throws Exception {
        final HttpServletRequest request = context.getRequest();

        final ViewTransactionForm form = context.getForm();

        // Load the transfer
        final Transfer transfer = resolveTransfer(context);

        final String unitsPattern = transfer.getType().getFrom().getCurrency().getPattern();
        request.setAttribute("unitsPattern", unitsPattern);

        // Resolve the custom fields
        final List<PaymentCustomField> customFields = paymentCustomFieldService.list(transfer.getType(), true);
        final Collection<Entry> entries = customFieldHelper.buildEntries(customFields, transfer.getCustomValues());
        request.setAttribute("customFields", entries);

        // Set the scheduled payment details
        ScheduledPayment scheduledPayment = transfer.getScheduledPayment();
        scheduledPayment = scheduledPayment == null ? null : scheduledPaymentService.load(scheduledPayment.getId(), ScheduledPayment.Relationships.TRANSFERS);
        if (scheduledPayment != null) {
            final int number = scheduledPayment.getTransfers().indexOf(transfer) + 1;
            request.setAttribute("scheduledPayment", scheduledPayment);
            request.setAttribute("scheduledPaymentNumber", number);
            request.setAttribute("scheduledPaymentCount", scheduledPayment.getTransfers().size());
        }

        // Check if the logged user can authorize or deny the transfer and if the user can see comments
        boolean canCancel = false;
        boolean canAuthorize = false;
        boolean suppressDeny = false;
        final boolean canChargeback = false;
        String comments = null;

        if (transfer.isRoot() && transfer.getProcessDate() == null || !CollectionUtils.isEmpty(transfer.getAuthorizations())) {

            if (transfer.getStatus() == Transfer.Status.PENDING) {
                final AuthorizationLevel currentAuthorizationLevel = transfer.getNextAuthorizationLevel();
                final AuthorizationLevel.Authorizer authorizer = currentAuthorizationLevel.getAuthorizer();

                // Check if can authorize / deny
                final AccountOwner fromOwner = transfer.getFromOwner();
                if (context.isAdmin() && (authorizer == AuthorizationLevel.Authorizer.ADMIN || authorizer == AuthorizationLevel.Authorizer.BROKER)) {
                    final AdminGroup adminGroup = (AdminGroup) context.getGroup();
                    if (currentAuthorizationLevel.getAdminGroups().contains(adminGroup)) {
                        if (transfer.isActuallyFromSystem()) {
                            canAuthorize = permissionService.hasPermission(AdminSystemPermission.PAYMENTS_AUTHORIZE);
                        } else {
                            canAuthorize = permissionService.hasPermission(AdminMemberPermission.PAYMENTS_AUTHORIZE);
                        }
                    }
                } else if (context.isBroker() && authorizer == AuthorizationLevel.Authorizer.BROKER && !transfer.isFromSystem()) {
                    // Brokers can authorize or deny payments from their brokered members
                    final Member member = (Member) fromOwner;
                    if (context.isBrokerOf(member)) {
                        canAuthorize = permissionService.hasPermission(BrokerPermission.MEMBER_PAYMENTS_AUTHORIZE);
                    }
                } else if ((context.isMember() || context.isOperator()) && authorizer == AuthorizationLevel.Authorizer.RECEIVER && !transfer.isToSystem()) {
                    // As the member who received the payment
                    if (transfer.getToOwner().equals(context.getAccountOwner())) {
                        canAuthorize = (context.isMember() && permissionService.hasPermission(MemberPermission.PAYMENTS_AUTHORIZE)) || (context.isOperator() && permissionService.hasPermission(OperatorPermission.PAYMENTS_AUTHORIZE));
                    }
                } else if ((context.isMember() || context.isOperator()) && authorizer == AuthorizationLevel.Authorizer.PAYER && !transfer.isFromSystem()) {
                    // As the member who performed the payment
                    if (transfer.getFromOwner().equals(context.getAccountOwner())) {
                        canAuthorize = (context.isMember() && permissionService.hasPermission(MemberPermission.PAYMENTS_AUTHORIZE)) || (context.isOperator() && permissionService.hasPermission(OperatorPermission.PAYMENTS_AUTHORIZE));
                    }
                    // Don't show deny as the cancel will be available (and both are equivalent),
                    // The payer is the one who created the payment and can cancel it before being processed
                    suppressDeny = true;
                }

                // Check if can cancel
                if (!canAuthorize && scheduledPayment == null) {
                    // Can never cancel and authorize at the same time - he can already deny
                    if (fromOwner.equals(context.getAccountOwner())) {
                        if (context.isAdmin()) {
                            canCancel = permissionService.hasPermission(AdminSystemPermission.PAYMENTS_CANCEL);
                        } else {
                            canCancel = (context.isMember() && permissionService.hasPermission(MemberPermission.PAYMENTS_CANCEL_AUTHORIZED)) || (context.isOperator() && permissionService.hasPermission(OperatorPermission.PAYMENTS_CANCEL_AUTHORIZED));
                        }
                    } else if (transfer.getToOwner().equals(context.getAccountOwner())) {
                        // canceling a pending transfer by the receiver is like a charge back / reverse
                        // because of that we're checking the chargeback permission
                        final SystemGroup group = groupService.load(context.getGroup().getId(), SystemGroup.Relationships.CHARGEBACK_TRANSFER_TYPES);
                        if (group.getChargebackTransferTypes().contains(transfer.getType()) && paymentService.canChargeback(transfer, true)) {
                            canCancel = context.isMember() && permissionService.hasPermission(MemberPermission.PAYMENTS_CHARGEBACK);
                        }
                    } else {
                        if (context.isAdmin()) {
                            canCancel = permissionService.hasPermission(AdminMemberPermission.PAYMENTS_CANCEL_AUTHORIZED_AS_MEMBER);
                        } else if (fromOwner instanceof Member) {
                            final Member fromMember = (Member) fromOwner;
                            canCancel = context.isBrokerOf(fromMember) && permissionService.hasPermission(BrokerPermission.MEMBER_PAYMENTS_CANCEL_AUTHORIZED_AS_MEMBER);
                        }
                    }
                }
            }
            if (CollectionUtils.isNotEmpty(transfer.getAuthorizations())) {
                final TransferAuthorization lastAuthorization = new LinkedList<TransferAuthorization>(transfer.getAuthorizations()).getLast();
                if (lastAuthorization.isShowToMember() || (lastAuthorization.getLevel().getAuthorizer() == AuthorizationLevel.Authorizer.BROKER && context.getElement().equals(lastAuthorization.getBy()))) {
                    comments = lastAuthorization.getComments();
                }
            }

        }

        final boolean canPayNow = scheduledPaymentService.canPayNow(transfer);

        // Check if the payment may be charged back
        request.setAttribute("canChargeback", paymentService.hasPermissionsToChargeback(transfer) && paymentService.canChargeback(transfer, false));

        // Determine whether the transaction hierarchy will be shown
        final TransactionHierarchyVisibility transactionHierarchyVisibility = transfer.getType().getTransactionHierarchyVisibility();
        final boolean showHierarchy = transactionHierarchyVisibility == null ? false : transactionHierarchyVisibility.isVisibleTo(context.getGroup().getNature());
        if (showHierarchy) {
            // Load the parent, if any
            Transfer parent = transfer.getParent();
            if (parent != null && paymentService.isVisible(parent)) {
                parent = paymentService.load(parent.getId(), FETCH);

                // Get the parent custom fields
                final List<PaymentCustomField> parentCustomFields = paymentCustomFieldService.list(parent.getType(), true);
                final Collection<Entry> parentEntries = customFieldHelper.buildEntries(parentCustomFields, parent.getCustomValues());
                request.setAttribute("parentCustomFields", parentEntries);
                request.setAttribute("parent", parent);
            }

            // List only the visible children
            final List<Transfer> children = new ArrayList<Transfer>(transfer.getChildren());
            for (final Iterator<Transfer> it = children.iterator(); it.hasNext();) {
                if (!paymentService.isVisible(it.next())) {
                    it.remove();
                }
            }
            request.setAttribute("children", children);
        }

        // Resolve the by element
        Element by = transfer.getReceiver();
        if (by == null) {
            by = transfer.getBy();
        }
        boolean showBy = false;
        if (by != null) {
            if (by instanceof Administrator) {
                if (context.isAdmin()) {
                    request.setAttribute("byAdmin", by);
                } else {
                    // Don't disclose to member which admin made the payment
                    request.setAttribute("bySystem", true);
                }
                showBy = true;
            } else if ((by instanceof Operator) && (context.isMemberOf((Operator) by)
                    || context.getElement().equals(by))
                    || (by instanceof Operator) && context.getAccountOwner().equals(transfer.getFromOwner())) {
                request.setAttribute("byOperator", by);
                showBy = true;
            } else {
                final AccountOwner fromOwner = transfer.getFrom().getOwner();
                final Member member = (Member) by.getAccountOwner();
                request.setAttribute("byMember", member);
                showBy = !member.equals(fromOwner);
            }
        }

        // Store the request attributes
        if (canCancel || canAuthorize) {
            // Check if the logged user has already authorized this payment
            final boolean alreadyAuthorized = transferAuthorizationService.hasAlreadyAuthorized(transfer);
            // Only show authorization when the logged member hasn't authorized this payment already (on another level)
            if (!alreadyAuthorized) {
                request.setAttribute("canCancel", canCancel);
                request.setAttribute("canAuthorize", canAuthorize);
                request.setAttribute("suppressDeny", suppressDeny);
                if (!context.getAccountOwner().equals(transfer.getToOwner())) {
                    request.setAttribute("showCommentsCheckBox", canAuthorize);
                }
            }
            request.setAttribute("alreadyAuthorized", alreadyAuthorized);
        }
        // Check if transaction password will be requested
        if (canCancel || canAuthorize || canChargeback) {
            request.setAttribute("requestTransactionPassword", shouldValidateTransactionPassword(context, transfer));
            request.setAttribute("showActions", true);
        }
        if (context.isAdmin()) {
            request.setAttribute("authorizations", transfer.getAuthorizations());
        }
        request.setAttribute("canPayNow", canPayNow);
        request.setAttribute("comments", comments);
        request.setAttribute("showBy", showBy);
        request.setAttribute("transfer", transfer);
        // final Calendar date = (transfer.getProcessDate() != null) ? transfer.getProcessDate() : transfer.getDate();
        // rates are not shown in transaction details, but the code still exists in the jsp, in case later on we might decide otherwise.
        // request.setAttribute("aRate", aRateService.emissionDateToRate(transfer.getEmissionDate(), date));
        if (form.getMemberId() > 0L) {
            request.setAttribute("memberId", form.getMemberId());
        }
        if (form.getTypeId() > 0L) {
            request.setAttribute("typeId", form.getTypeId());
        }

        request.setAttribute("guarantee", guaranteeService.loadFromTransfer(transfer));
    }

    /**
     * Resolve a Map containing parameters for the next request after a form submit
     */
    protected Map<String, Object> resolveForwardParams(final ActionContext context) {
        final ViewTransactionForm form = context.getForm();
        final Map<String, Object> params = new HashMap<String, Object>();
        params.put("transferId", form.getTransferId());
        params.put("memberId", form.getMemberId());
        params.put("typeId", form.getTypeId());
        return params;
    }

    protected Transfer resolveTransfer(final ActionContext context) {
        final ViewTransactionForm form = context.getForm();
        final long id = form.getTransferId();
        if (id <= 0L) {
            throw new ValidationException();
        }
        return paymentService.load(id, FETCH);
    }

    protected boolean shouldValidateTransactionPassword(final ActionContext context, final Transfer transfer) {
        if (context.getAccountOwner().equals(transfer.getToOwner())) {
            // When the logged member is the payment receiver
            return context.isTransactionPasswordEnabled(transfer.getType().getTo());
        } else if (context.getAccountOwner().equals(transfer.getFromOwner())) {
            // When the logged member is the payment performer
            return context.isTransactionPasswordEnabled(transfer.getType().getFrom());
        } else {
            return context.isTransactionPasswordEnabled();
        }
    }

    @Override
    protected void validateForm(final ActionContext context) {
        if (shouldValidateTransactionPassword(context, resolveTransfer(context))) {
            final ViewTransactionForm form = context.getForm();
            if (StringUtils.isEmpty(form.getTransactionPassword())) {
                throw new ValidationException("_transactionPassword", "login.transactionPassword", new RequiredError());
            }
        }
    }

}
