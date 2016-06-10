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
package nl.strohalm.cyclos.controls.payments.request;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseQueryAction;
import nl.strohalm.cyclos.entities.access.Channel;
import nl.strohalm.cyclos.entities.accounts.transactions.Ticket;
import nl.strohalm.cyclos.entities.accounts.transactions.TicketQuery;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.services.transactions.TicketService;
import nl.strohalm.cyclos.utils.binding.BeanBinder;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.binding.DataBinderHelper;
import nl.strohalm.cyclos.utils.binding.PropertyBinder;
import nl.strohalm.cyclos.utils.query.QueryParameters;
import nl.strohalm.cyclos.utils.validation.ValidationException;

/**
 * Action used to search payment requests
 * @author luis
 */
public class SearchPaymentRequestsAction extends BaseQueryAction {

    private TicketService           ticketService;
    private DataBinder<TicketQuery> dataBinder;

    @Inject
    public void setTicketService(final TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @Override
    protected void executeQuery(final ActionContext context, final QueryParameters queryParameters) {
        final TicketQuery query = (TicketQuery) queryParameters;
        context.getRequest().setAttribute("tickets", ticketService.search(query));
    }

    @Override
    protected QueryParameters prepareForm(final ActionContext context) {
        final HttpServletRequest request = context.getRequest();
        final SearchPaymentRequestsForm form = context.getForm();
        final TicketQuery query = getDataBinder().readFromString(form.getQuery());
        query.setNature(Ticket.Nature.PAYMENT_REQUEST);
        final Member to = (Member) context.getAccountOwner();
        query.setTo(to);
        if (query.getGroupedStatus() == null) {
            query.setGroupedStatus(TicketQuery.GroupedStatus.OK_PENDING);
        }
        final MemberGroup group = groupService.load(to.getMemberGroup().getId(), MemberGroup.Relationships.REQUEST_PAYMENT_BY_CHANNELS);
        // Get the possible channels
        final Collection<Channel> channels = group.getRequestPaymentByChannels();
        if (channels.isEmpty()) {
            throw new ValidationException("paymentRequest.error.noChannels");
        }
        if (channels.size() == 1) {
            request.setAttribute("singleChannel", channels.iterator().next());
        }

        return query;
    }

    private DataBinder<TicketQuery> getDataBinder() {
        if (dataBinder == null) {
            final BeanBinder<TicketQuery> binder = BeanBinder.instance(TicketQuery.class);
            binder.registerBinder("groupedStatus", PropertyBinder.instance(TicketQuery.GroupedStatus.class, "groupedStatus"));
            binder.registerBinder("from", PropertyBinder.instance(Member.class, "from"));
            binder.registerBinder("pageParameters", DataBinderHelper.pageBinder());
            dataBinder = binder;
        }
        return dataBinder;
    }

}
