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

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseAction;
import nl.strohalm.cyclos.entities.accounts.Account;
import nl.strohalm.cyclos.entities.accounts.transactions.Payment;
import nl.strohalm.cyclos.entities.accounts.transactions.ScheduledPayment;
import nl.strohalm.cyclos.entities.accounts.transactions.ScheduledPaymentQuery;
import nl.strohalm.cyclos.entities.accounts.transactions.ScheduledPaymentQuery.SearchType;
import nl.strohalm.cyclos.entities.accounts.transactions.Transfer;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferQuery;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.services.transactions.PaymentService;
import nl.strohalm.cyclos.services.transactions.ScheduledPaymentService;
import nl.strohalm.cyclos.utils.Period;
import nl.strohalm.cyclos.utils.TransformedIteratorList;
import nl.strohalm.cyclos.utils.conversion.CalendarConverter;
import nl.strohalm.cyclos.utils.conversion.Transformer;

import org.apache.struts.action.ActionForward;

/**
 * Action used to search transactions on PosWeb
 * 
 * @author luis
 */
public class SearchTransactionsAction extends BaseAction {

    /**
     * An entry which abstracts a transfer or scheduled payment as being a debit or a credit, depending on the currently logged member
     * 
     * @author luis
     */
    public static class Entry {
        private Payment    payment;
        private BigDecimal amount;
        private Account    relatedAccount;

        public BigDecimal getAmount() {
            return amount;
        }

        public Payment getPayment() {
            return payment;
        }

        public Account getRelatedAccount() {
            return relatedAccount;
        }

        public void setAmount(final BigDecimal amount) {
            this.amount = amount;
        }

        public void setPayment(final Payment payment) {
            this.payment = payment;
        }

        public void setRelatedAccount(final Account relatedAccount) {
            this.relatedAccount = relatedAccount;
        }

    }

    private static class TransformPaymentToEntry<P extends Payment> implements Transformer<P, Entry> {
        private Member owner;

        private TransformPaymentToEntry(final Member owner) {
            this.owner = owner;
        }

        @Override
        public Entry transform(final P payment) {
            final Entry entry = new Entry();
            entry.setPayment(payment);
            final boolean isCredit = owner.equals(payment.getActualToOwner());
            final BigDecimal amount = payment.getActualAmount();
            entry.setAmount(isCredit ? amount : amount.negate());
            entry.setRelatedAccount(isCredit ? payment.getActualFrom() : payment.getActualTo());
            return entry;
        }
    }

    public static List<Entry> listScheduledPayments(final ScheduledPaymentService scheduledPaymentService, final Member owner, final Calendar date) {
        final ScheduledPaymentQuery schedQuery = new ScheduledPaymentQuery();
        schedQuery.setIterateAll();
        schedQuery.setOwner(owner);
        schedQuery.setSearchType(SearchType.INCOMING);
        schedQuery.setPeriod(Period.day(date));
        final List<ScheduledPayment> scheduledPayments = scheduledPaymentService.search(schedQuery);
        final TransformedIteratorList<ScheduledPayment, Entry> scheduledPaymentEntries = new TransformedIteratorList<ScheduledPayment, Entry>(new TransformPaymentToEntry<ScheduledPayment>(owner), scheduledPayments);
        return scheduledPaymentEntries;
    }

    public static List<Entry> listTransfers(final PaymentService paymentService, final Member owner, final Calendar date) {
        final TransferQuery transferQuery = new TransferQuery();
        transferQuery.setIterateAll();
        transferQuery.setOwner(owner);
        transferQuery.setPeriod(Period.day(date));
        transferQuery.setStatus(Payment.Status.PROCESSED);
        final List<Transfer> transfers = paymentService.search(transferQuery);
        final TransformedIteratorList<Transfer, Entry> transferEntries = new TransformedIteratorList<Transfer, Entry>(new TransformPaymentToEntry<Transfer>(owner), transfers);
        return transferEntries;
    }

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
        final HttpServletRequest request = context.getRequest();
        final CalendarConverter converter = settingsService.getLocalSettings().getRawDateConverter();
        Calendar date = converter.valueOf(form.getDate());

        if (date == null) {
            date = Calendar.getInstance();
            form.setDate(converter.toString(date));
        }
        final Member owner = context.getMember();

        // Search for transfers
        request.setAttribute("transfers", listTransfers(paymentService, owner, date));

        // Search for scheduled payments
        request.setAttribute("scheduledPayments", listScheduledPayments(scheduledPaymentService, owner, date));

        return context.getInputForward();
    }
}
