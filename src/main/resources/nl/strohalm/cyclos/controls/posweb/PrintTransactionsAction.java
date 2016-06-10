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

import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseAction;
import nl.strohalm.cyclos.controls.posweb.SearchTransactionsAction.Entry;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.services.transactions.PaymentService;
import nl.strohalm.cyclos.services.transactions.ScheduledPaymentService;
import nl.strohalm.cyclos.utils.conversion.CalendarConverter;

import org.apache.struts.action.ActionForward;

/**
 * Action used to print the daily transactions
 * 
 * @author luis
 */
public class PrintTransactionsAction extends BaseAction {

    private PaymentService          paymentService;
    private ScheduledPaymentService scheduledPaymentService;

    @Inject
    public void setPaymentService(final PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @Inject
    public void setScheduledPaymentService(final ScheduledPaymentService scheduledPaymentService) {
        this.scheduledPaymentService = scheduledPaymentService;
    }

    @Override
    protected ActionForward executeAction(final ActionContext context) throws Exception {
        final SearchTransactionsForm form = context.getForm();
        final LocalSettings localSettings = settingsService.getLocalSettings();
        final CalendarConverter rawDateConverter = localSettings.getRawDateConverter();
        Calendar date = rawDateConverter.valueOf(form.getDate());
        if (date == null) {
            date = Calendar.getInstance();
        }
        final Member owner = context.getMember();

        final HttpServletRequest request = context.getRequest();
        request.setAttribute("date", date);
        request.setAttribute("owner", owner);

        final List<Entry> transfers = SearchTransactionsAction.listTransfers(paymentService, owner, date);
        request.setAttribute("transfers", transfers);

        final List<Entry> scheduledPayments = SearchTransactionsAction.listScheduledPayments(scheduledPaymentService, owner, date);
        request.setAttribute("scheduledPayments", scheduledPayments);

        return context.getInputForward();
    }

}
