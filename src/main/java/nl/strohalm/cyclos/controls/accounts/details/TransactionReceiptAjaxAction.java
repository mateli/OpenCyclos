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

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseReceiptPrintAjaxAction;
import nl.strohalm.cyclos.entities.accounts.Account;
import nl.strohalm.cyclos.entities.accounts.MemberAccount;
import nl.strohalm.cyclos.entities.accounts.SystemAccount;
import nl.strohalm.cyclos.entities.accounts.transactions.Transfer;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.members.printsettings.ReceiptPrinterSettings;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.services.transactions.PaymentService;

import org.apache.commons.lang.StringUtils;

/**
 * Action used to get details for printing a transaction in a local receipt printer
 * 
 * @author luis
 */
public class TransactionReceiptAjaxAction extends BaseReceiptPrintAjaxAction {

    protected PaymentService paymentService;

    @Inject
    public void setPaymentService(final PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @Override
    protected String resolveText(final ActionContext context, final ReceiptPrinterSettings receiptPrinterSettings) {
        final ViewTransactionForm form = context.getForm();
        final long transferId = form.getTransferId();
        final Transfer transfer = paymentService.load(transferId, ViewTransactionAction.FETCH);

        final LocalSettings localSettings = settingsService.getLocalSettings();
        final StringBuilder out = new StringBuilder();
        out.append(context.message("receipt.transfer.header", localSettings.getApplicationName())).append('\n');
        out.append(context.message("receipt.transfer.textBefore")).append('\n');
        out.append(context.message("receipt.transfer.from", ownerString(transfer.getActualFrom()))).append('\n');
        out.append(context.message("receipt.transfer.to", ownerString(transfer.getActualTo()))).append('\n');
        out.append(context.message("receipt.transfer.date", localSettings.getDateTimeConverter().toString(transfer.getActualDate()))).append('\n');
        if (StringUtils.isNotEmpty(transfer.getTransactionNumber())) {
            out.append(context.message("receipt.transfer.transactionNumber", transfer.getTransactionNumber())).append('\n');
        }
        out.append(context.message("receipt.transfer.amount", localSettings.getUnitsConverter(transfer.getFrom().getType().getCurrency().getPattern()).toString(transfer.getActualAmount()))).append('\n');
        if (transfer.getProcessDate() == null) {
            out.append(context.message("receipt.transfer.status", context.message("payment.status." + transfer.getStatus()))).append('\n');
        }
        if (StringUtils.isNotEmpty(receiptPrinterSettings.getPaymentAdditionalMessage())) {
            out.append('\n').append(receiptPrinterSettings.getPaymentAdditionalMessage()).append('\n');
        }
        out.append(context.message("receipt.transfer.textAfter"));
        return out.toString();
    }

    private String ownerString(final Account account) {
        if (account instanceof SystemAccount) {
            return account.getOwnerName();
        } else {
            final Member member = ((MemberAccount) account).getMember();
            return member.getUsername() + " - " + member.getName();
        }
    }

}
