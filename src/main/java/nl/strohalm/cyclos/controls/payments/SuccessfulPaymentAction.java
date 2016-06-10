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

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseAction;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.Account;
import nl.strohalm.cyclos.entities.accounts.MemberAccount;
import nl.strohalm.cyclos.entities.accounts.SystemAccount;
import nl.strohalm.cyclos.entities.accounts.transactions.Payment;
import nl.strohalm.cyclos.entities.accounts.transactions.Transfer;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.services.transactions.PaymentService;
import nl.strohalm.cyclos.utils.RelationshipHelper;
import nl.strohalm.cyclos.utils.conversion.CoercionHelper;
import nl.strohalm.cyclos.utils.validation.ValidationException;

import org.apache.struts.action.ActionForward;

/**
 * Action that shows the details of a successful payment
 * @author luis
 */
public class SuccessfulPaymentAction extends BaseAction {

    private static final Relationship[] FETCH = { RelationshipHelper.nested(Payment.Relationships.FROM, MemberAccount.Relationships.MEMBER), RelationshipHelper.nested(Payment.Relationships.TO, MemberAccount.Relationships.MEMBER), Payment.Relationships.TYPE };
    private PaymentService              paymentService;

    @Inject
    public void setPaymentService(final PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @Override
    protected ActionForward executeAction(final ActionContext context) throws Exception {
        final SuccessfulPaymentForm form = context.getForm();
        final long transferId = form.getTransferId();
        if (transferId <= 0L) {
            throw new ValidationException();
        }
        final HttpServletRequest request = context.getRequest();

        // Load the transfer. If it's pending payment wait a bit and try again
        final Transfer transfer = paymentService.load(transferId, FETCH);

        boolean toSystem = false;
        boolean selfPayment = false;
        boolean pendingAuthorization = false;
        boolean selectMember = false;
        final int nextAttempt = 0;

        final Account from = transfer.getFrom();
        Member fromMember = null;
        if (from instanceof MemberAccount) {
            fromMember = ((MemberAccount) from).getMember();
        }
        final Account to = transfer.getTo();
        Member toMember = null;
        if (to instanceof MemberAccount) {
            toMember = ((MemberAccount) to).getMember();
        }
        // Find the related member
        Member relatedMember = null;
        if (fromMember != null && !context.getElement().equals(fromMember)) {
            relatedMember = fromMember;
        } else if (toMember != null && !context.getElement().equals(toMember)) {
            relatedMember = toMember;
        }

        pendingAuthorization = transfer.getProcessDate() == null;
        selectMember = CoercionHelper.coerce(Boolean.TYPE, form.getSelectMember());
        toSystem = to instanceof SystemAccount;
        selfPayment = from.getOwner().equals(to.getOwner());

        request.setAttribute("transfer", transfer);
        if (relatedMember != null) {
            request.setAttribute("relatedMember", relatedMember.getId());
        }
        if (fromMember != null && !context.getElement().equals(fromMember)) {
            request.setAttribute("from", fromMember.getId());
        }
        if (toMember != null) {
            request.setAttribute("to", toMember.getId());
        }
        request.setAttribute("selectMember", selectMember);
        request.setAttribute("pendingAuthorization", pendingAuthorization);
        request.setAttribute("nextAttempt", nextAttempt);
        request.setAttribute("toSystem", toSystem);
        request.setAttribute("selfPayment", selfPayment);

        return context.getInputForward();
    }
}
