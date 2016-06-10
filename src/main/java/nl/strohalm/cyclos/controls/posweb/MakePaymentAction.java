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

import nl.strohalm.cyclos.access.OperatorPermission;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.entities.access.Channel;
import nl.strohalm.cyclos.entities.accounts.transactions.Transfer;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferType;
import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.entities.groups.OperatorGroup;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.services.access.exceptions.BlockedCredentialsException;
import nl.strohalm.cyclos.services.access.exceptions.InvalidCredentialsException;
import nl.strohalm.cyclos.services.transactions.DoPaymentDTO;
import nl.strohalm.cyclos.services.transactions.TransactionContext;
import nl.strohalm.cyclos.utils.RelationshipHelper;
import nl.strohalm.cyclos.utils.validation.InvalidError;
import nl.strohalm.cyclos.utils.validation.ValidationError;
import nl.strohalm.cyclos.utils.validation.ValidationException;
import nl.strohalm.cyclos.utils.validation.Validator;

/**
 * Action for the posweb member to make a payment
 * @author luis
 */
public class MakePaymentAction extends BasePosWebPaymentAction {

    @Override
    protected Transfer doPayment(final ActionContext context, final DoPaymentDTO dto) {
        final MakePaymentForm form = context.getForm();

        // Check the operator transaction password if needed
        try {
            if (shouldValidateTransactionPassword(context, dto)) {
                // Check the transaction password
                accessService.checkTransactionPassword(form.getTransactionPassword());
            }
        } catch (final InvalidCredentialsException e) {
            throw new ValidationException("transactionPassword", "login.transactionPassword", new InvalidError());
        } catch (final BlockedCredentialsException e) {
            throw new ValidationException(new ValidationError("transactionPassword.error.blockedByTrials"));
        }

        // Perform the payment itself
        return (Transfer) getPaymentService().doPayment(dto);
    }

    @Override
    protected OperatorPermission getPermission() {
        return OperatorPermission.PAYMENTS_POSWEB_MAKE_PAYMENT;
    }

    @Override
    protected DoPaymentDTO resolvePaymentDTO(final ActionContext context) {
        final Member member = (Member) context.getAccountOwner();
        final MakePaymentForm form = context.getForm();
        final DoPaymentDTO payment = getDataBinder().readFromString(form);
        payment.setChannel(Channel.WEB);
        payment.setContext(TransactionContext.PAYMENT);
        payment.setFrom(member);
        return payment;
    }

    @Override
    protected void validateForm(final ActionContext context) {
        final DoPaymentDTO dto = resolvePaymentDTO(context);

        // validates payment
        getPaymentService().validate(dto);

        Group group = context.getGroup();
        if (group instanceof OperatorGroup) {
            group = groupService.load(group.getId(), RelationshipHelper.nested(OperatorGroup.Relationships.MEMBER, Element.Relationships.GROUP));
        }

        validateTransactionPassword(context, dto);

    }

    private boolean shouldValidateTransactionPassword(final ActionContext context, final DoPaymentDTO payment) {
        final TransferType transferType = transferTypeService.load(payment.getTransferType().getId(), TransferType.Relationships.FROM);
        if (transferType == null) {
            return context.isTransactionPasswordEnabled();
        } else {
            return context.isTransactionPasswordEnabled(transferType.getFrom());
        }
    }

    private void validateTransactionPassword(final ActionContext context, final DoPaymentDTO dto) {
        if (shouldValidateTransactionPassword(context, dto)) {
            // validate if TP is active
            context.validateTransactionPassword();

            final Validator validator = new Validator();
            validator.property("transactionPassword").required().key("login.transactionPassword");
            validator.validate(context.getForm());
        }
    }

}
