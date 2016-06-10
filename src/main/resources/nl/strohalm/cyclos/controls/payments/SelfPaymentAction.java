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

import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.entities.accounts.AccountOwner;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferTypeQuery;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.services.transactions.DoPaymentDTO;
import nl.strohalm.cyclos.services.transactions.TransactionContext;
import nl.strohalm.cyclos.utils.conversion.IdConverter;

/**
 * Action for payments between accounts of a given account owner
 * @author luis
 */
public class SelfPaymentAction extends BasePaymentAction {

    /**
     * gets the owner of the account. This is made a method because it is overridden in the child class SelfPaymentForMemberByAdminAction.
     * @param context
     * @return the owner of the account on which the transfer is made.
     */
    @Override
    protected AccountOwner getFromOwner(final ActionContext context) {
        final SelfPaymentForm form = context.getForm();
        final Long memberId = IdConverter.instance().valueOf(form.getFrom());
        if (memberId == null) {
            return context.getAccountOwner();
        } else {
            return (Member) elementService.load(memberId);
        }
    }

    @Override
    protected void prepareForm(final ActionContext context) throws Exception {
        super.prepareForm(context);
        final HttpServletRequest request = context.getRequest();
        final boolean asMember = (Boolean) request.getAttribute("asMember");
        String titleKey;
        if (asMember) {
            titleKey = "payment.title.asMemberToSelf";
        } else if (context.isAdmin()) {
            titleKey = "payment.title.systemToSystem";
        } else {
            titleKey = "payment.title.memberToSelf";
        }
        request.setAttribute("titleKey", titleKey);
    }

    @Override
    protected DoPaymentDTO resolvePaymentDTO(final ActionContext context) {
        final DoPaymentDTO dto = super.resolvePaymentDTO(context);
        dto.setContext(TransactionContext.SELF_PAYMENT);
        dto.setTo(getFromOwner(context));
        dto.setFrom(getFromOwner(context));
        // Self payment TTs don't use channel
        dto.setChannel(null);
        return dto;
    }

    @Override
    protected TransferTypeQuery resolveTransferTypeQuery(final ActionContext context) {

        final SelfPaymentForm form = context.getForm();
        final Long memberId = IdConverter.instance().valueOf(form.getFrom());

        final TransferTypeQuery query = new TransferTypeQuery();
        query.setUsePriority(true);
        query.setContext(TransactionContext.SELF_PAYMENT);
        final AccountOwner owner = getFromOwner(context);
        query.setFromOwner(owner);
        query.setToOwner(owner);
        if (memberId != null) {
            query.setBy(context.getElement());
        } else {
            query.setGroup(context.getGroup());
        }
        return query;
    }
}
