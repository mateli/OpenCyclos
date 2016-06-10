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

import java.util.List;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseQueryAction;
import nl.strohalm.cyclos.entities.accounts.Account;
import nl.strohalm.cyclos.entities.accounts.AccountType;
import nl.strohalm.cyclos.entities.accounts.MemberAccount;
import nl.strohalm.cyclos.entities.accounts.transactions.Payment;
import nl.strohalm.cyclos.entities.accounts.transactions.PaymentAwaitingFeedbackDTO;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.members.PaymentsAwaitingFeedbackQuery;
import nl.strohalm.cyclos.services.elements.ReferenceService;
import nl.strohalm.cyclos.utils.RelationshipHelper;
import nl.strohalm.cyclos.utils.binding.BeanBinder;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.binding.DataBinderHelper;
import nl.strohalm.cyclos.utils.query.QueryParameters;

/**
 * Action used to search payments that should receive feedback
 * @author luis
 */
public class SearchPaymentsAwaitingFeedbackAction extends BaseQueryAction {

    private DataBinder<PaymentsAwaitingFeedbackQuery> dataBinder;
    private ReferenceService                          referenceService;

    @Inject
    public void setReferenceService(final ReferenceService referenceService) {
        this.referenceService = referenceService;
    }

    @Override
    protected void executeQuery(final ActionContext context, final QueryParameters queryParameters) {
        final PaymentsAwaitingFeedbackQuery query = (PaymentsAwaitingFeedbackQuery) queryParameters;
        final List<PaymentAwaitingFeedbackDTO> list = referenceService.searchPaymentsAwaitingFeedback(query);
        context.getRequest().setAttribute("payments", list);
    }

    @Override
    protected QueryParameters prepareForm(final ActionContext context) {
        final SearchPaymentsAwaitingFeedbackForm form = context.getForm();
        final Member member = (Member) context.getAccountOwner();
        final PaymentsAwaitingFeedbackQuery query = getDataBinder().readFromString(form.getQuery());
        query.setMember(member);
        query.setExpired(false);
        query.fetch(RelationshipHelper.nested(Payment.Relationships.FROM, Account.Relationships.TYPE, AccountType.Relationships.CURRENCY), RelationshipHelper.nested(Payment.Relationships.FROM, MemberAccount.Relationships.MEMBER), RelationshipHelper.nested(Payment.Relationships.TO, MemberAccount.Relationships.MEMBER));
        getDataBinder().writeAsString(form.getQuery(), query);
        return query;
    }

    @Override
    protected boolean willExecuteQuery(final ActionContext context, final QueryParameters queryParameters) throws Exception {
        return true;
    }

    private DataBinder<PaymentsAwaitingFeedbackQuery> getDataBinder() {
        if (dataBinder == null) {
            final BeanBinder<PaymentsAwaitingFeedbackQuery> binder = BeanBinder.instance(PaymentsAwaitingFeedbackQuery.class);
            binder.registerBinder("pageParameters", DataBinderHelper.pageBinder());
            dataBinder = binder;
        }
        return dataBinder;
    }

}
