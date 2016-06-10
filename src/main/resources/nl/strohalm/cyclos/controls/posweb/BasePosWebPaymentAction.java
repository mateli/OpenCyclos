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
package nl.strohalm.cyclos.controls.posweb;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import nl.strohalm.cyclos.access.OperatorPermission;
import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.payments.PaymentAction;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.AccountOwner;
import nl.strohalm.cyclos.entities.accounts.Currency;
import nl.strohalm.cyclos.entities.accounts.MemberAccount;
import nl.strohalm.cyclos.entities.accounts.transactions.Payment;
import nl.strohalm.cyclos.entities.accounts.transactions.ScheduledPayment;
import nl.strohalm.cyclos.entities.accounts.transactions.Transfer;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferTypeQuery;
import nl.strohalm.cyclos.entities.customization.fields.PaymentCustomField;
import nl.strohalm.cyclos.entities.exceptions.UnexpectedEntityException;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.exceptions.PermissionDeniedException;
import nl.strohalm.cyclos.services.access.exceptions.InvalidUserForChannelException;
import nl.strohalm.cyclos.services.customization.PaymentCustomFieldService;
import nl.strohalm.cyclos.services.transactions.DoPaymentDTO;
import nl.strohalm.cyclos.services.transactions.ScheduledPaymentService;
import nl.strohalm.cyclos.services.transactions.exceptions.AuthorizedPaymentInPastException;
import nl.strohalm.cyclos.services.transactions.exceptions.CreditsException;
import nl.strohalm.cyclos.services.transactions.exceptions.NotEnoughCreditsException;
import nl.strohalm.cyclos.utils.CustomFieldHelper;
import nl.strohalm.cyclos.utils.CustomFieldHelper.Entry;
import nl.strohalm.cyclos.utils.RelationshipHelper;
import nl.strohalm.cyclos.utils.validation.ValidationException;

import org.apache.commons.collections.CollectionUtils;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Base class for posweb payments
 * @author luis
 */
public abstract class BasePosWebPaymentAction extends PaymentAction {

    private static final Relationship[] TRANSFER_FETCH  = {
                                                        Payment.Relationships.TYPE,
                                                        RelationshipHelper.nested(Payment.Relationships.FROM, MemberAccount.Relationships.MEMBER, Element.Relationships.USER),
                                                        RelationshipHelper.nested(Payment.Relationships.TO, MemberAccount.Relationships.MEMBER, Element.Relationships.USER) };

    private static final Relationship[] SCHEDULED_FETCH = {
                                                        ScheduledPayment.Relationships.TRANSFERS,
                                                        Payment.Relationships.TYPE,
                                                        RelationshipHelper.nested(Payment.Relationships.FROM, MemberAccount.Relationships.MEMBER, Element.Relationships.USER),
                                                        RelationshipHelper.nested(Payment.Relationships.TO, MemberAccount.Relationships.MEMBER, Element.Relationships.USER) };

    protected PaymentCustomFieldService paymentCustomFieldService;
    protected ScheduledPaymentService   scheduledPaymentService;

    private CustomFieldHelper           customFieldHelper;

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

    protected abstract Payment doPayment(final ActionContext context, final DoPaymentDTO dto);

    @Override
    protected void formAction(final ActionContext context) throws Exception {
        final HttpSession session = context.getSession();

        Payment payment;
        try {
            // Check permissions
            permissionService.permission().member().operator(getPermission()).check();
            payment = doPayment(context, resolvePaymentDTO(context));
            session.removeAttribute("payment");
        } catch (final InvalidUserForChannelException e) {
            throw new ValidationException("posweb.error.channelDisabled");
        } catch (final NotEnoughCreditsException e) {
            throw new ValidationException("posweb.error.notEnoughCredits", actionHelper.resolveParameters(e));
        } catch (final CreditsException e) {
            throw new ValidationException(actionHelper.resolveErrorKey(e), actionHelper.resolveParameters(e));
        } catch (final UnexpectedEntityException e) {
            throw new ValidationException("payment.error.invalidTransferType");
        } catch (final AuthorizedPaymentInPastException e) {
            throw new ValidationException("payment.error.authorizedInPast");
        }

        // Ensure the resulting payment has at least the type and accounts (with their members) fetched
        if (payment instanceof Transfer) {
            payment = paymentService.load(payment.getId(), TRANSFER_FETCH);
        } else {
            payment = scheduledPaymentService.load(payment.getId(), SCHEDULED_FETCH);
        }

        session.setAttribute("lastPayment", payment);
        session.setAttribute("lastPaymentIsScheduled", payment instanceof ScheduledPayment);
        final List<PaymentCustomField> customFields = paymentCustomFieldService.list(payment.getType(), false);
        final Collection<Entry> entries = customFieldHelper.buildEntries(customFields, payment.getCustomValues());
        session.setAttribute("lastPaymentCustomValues", entries);
    }

    @Override
    protected AccountOwner getFromOwner(final ActionContext context) {
        return context.getAccountOwner();
    }

    /**
     * Returns the operation name for permission check
     */
    protected abstract OperatorPermission getPermission();

    @Override
    protected void prepareForm(final ActionContext context) throws Exception {
        if (!permissionService.permission().member().operator(getPermission()).hasPermission()) {
            context.getSession().invalidate();
            throw new PermissionDeniedException();
        }

        final HttpServletRequest request = context.getRequest();

        // Resolve the possible currencies
        final List<Currency> currencies = resolveCurrencies(context);
        request.setAttribute("currencies", currencies);
        if (CollectionUtils.isEmpty(currencies)) {
            throw new ValidationException("payment.error.noTransferType");
        } else if (currencies.size() == 1) {
            request.setAttribute("singleCurrency", currencies.iterator().next());
        }

        final Member loggedMember = (Member) context.getAccountOwner();
        final boolean showDescription = loggedMember.getMemberGroup().getMemberSettings().isShowPosWebPaymentDescription();

        request.setAttribute("loggedMember", loggedMember);
        request.setAttribute("showDescription", showDescription);
    }

    @Override
    protected ActionForward resolveLoginForward(final ActionMapping actionMapping, final HttpServletRequest request) {
        return new ActionForward(PosWebHelper.loginUrl(request), true);
    }

    @Override
    protected TransferTypeQuery resolveTransferTypeQuery(final ActionContext context) {
        return null;
    }
}
