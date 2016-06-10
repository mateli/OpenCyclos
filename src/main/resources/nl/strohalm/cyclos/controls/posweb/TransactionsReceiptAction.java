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

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseReceiptPrintAjaxAction;
import nl.strohalm.cyclos.controls.posweb.SearchTransactionsAction.Entry;
import nl.strohalm.cyclos.entities.accounts.transactions.ScheduledPayment;
import nl.strohalm.cyclos.entities.accounts.transactions.Transfer;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.members.printsettings.ReceiptPrinterSettings;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.services.transactions.PaymentService;
import nl.strohalm.cyclos.services.transactions.ScheduledPaymentService;
import nl.strohalm.cyclos.utils.DataIteratorHelper;
import nl.strohalm.cyclos.utils.conversion.CalendarConverter;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

/**
 * Action used to print on a receipt printer a list of transfers and scheduled payments
 * 
 * @author luis
 */
public class TransactionsReceiptAction extends BaseReceiptPrintAjaxAction {

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
    protected String resolveText(final ActionContext context, final ReceiptPrinterSettings receiptPrinterSettings) {
        final SearchTransactionsForm form = context.getForm();
        final LocalSettings localSettings = settingsService.getLocalSettings();
        final CalendarConverter rawDateConverter = localSettings.getRawDateConverter();
        Calendar date = rawDateConverter.valueOf(form.getDate());
        if (date == null) {
            date = Calendar.getInstance();
        }
        final Member owner = context.getMember();

        final CalendarConverter timeConverter = localSettings.getTimeConverter();

        final boolean txNumberUsed = localSettings.getTransactionNumber() != null && localSettings.getTransactionNumber().isValid();

        final StringBuilder out = new StringBuilder();
        out.append(context.message("receipt.posweb.transactions.header", localSettings.getApplicationName())).append('\n');
        out.append(context.message("receipt.posweb.transactions.member", owner.getName())).append('\n');
        out.append(context.message("receipt.posweb.transactions.date", rawDateConverter.toString(date))).append('\n');
        out.append('\n').append(context.message("receipt.posweb.transactions.transfers")).append('\n');
        final List<Entry> transfers = SearchTransactionsAction.listTransfers(paymentService, owner, date);
        if (transfers.isEmpty()) {
            out.append(context.message("receipt.posweb.transactions.noTransfers")).append('\n');
        } else {
            for (final Entry entry : transfers) {
                final Transfer transfer = (Transfer) entry.getPayment();
                final String time = timeConverter.toString(transfer.getActualDate());
                final String transactionNumber = StringUtils.trimToEmpty(txNumberUsed ? transfer.getTransactionNumber() : null);
                final String amount = localSettings.getUnitsConverter(transfer.getFrom().getType().getCurrency().getPattern()).toString(transfer.getActualAmount());
                final String username = entry.getRelatedAccount().getOwnerName();
                out.append(context.message("receipt.posweb.transactions.transfer", time, transactionNumber, amount, username)).append('\n');
            }
        }
        DataIteratorHelper.close(transfers);

        final List<Entry> scheduledPayments = SearchTransactionsAction.listScheduledPayments(scheduledPaymentService, owner, date);
        if (CollectionUtils.isNotEmpty(scheduledPayments)) {
            out.append('\n').append(context.message("receipt.posweb.transactions.scheduledPayments")).append('\n');
            for (final Entry entry : scheduledPayments) {
                final ScheduledPayment scheduledPayment = (ScheduledPayment) entry.getPayment();
                final String time = timeConverter.toString(scheduledPayment.getDate());
                final int installmentsCount = scheduledPayment.getTransfers().size();
                final Integer installmentNumber = scheduledPayment.getFirstOpenPaymentIndex();
                final String installments = installmentNumber == null ? String.valueOf(installmentsCount) : installmentNumber + "/" + installmentsCount;
                final String firstDate = rawDateConverter.toString(scheduledPayment.getActualDate());
                final String amount = localSettings.getUnitsConverter(scheduledPayment.getFrom().getType().getCurrency().getPattern()).toString(scheduledPayment.getAmount());
                final String username = entry.getRelatedAccount().getOwnerName();
                out.append(context.message("receipt.posweb.transactions.scheduledPayment", time, installments, firstDate, amount, username)).append('\n');
            }
            DataIteratorHelper.close(scheduledPayments);
        }

        return out.toString();
    }
}
