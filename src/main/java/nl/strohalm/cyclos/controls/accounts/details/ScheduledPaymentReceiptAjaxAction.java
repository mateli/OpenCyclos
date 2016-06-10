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

import java.util.List;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseReceiptPrintAjaxAction;
import nl.strohalm.cyclos.controls.payments.scheduled.ScheduledPaymentForm;
import nl.strohalm.cyclos.entities.accounts.Account;
import nl.strohalm.cyclos.entities.accounts.MemberAccount;
import nl.strohalm.cyclos.entities.accounts.SystemAccount;
import nl.strohalm.cyclos.entities.accounts.transactions.ScheduledPayment;
import nl.strohalm.cyclos.entities.accounts.transactions.Transfer;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.members.printsettings.ReceiptPrinterSettings;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.services.transactions.ScheduledPaymentService;
import nl.strohalm.cyclos.utils.conversion.CalendarConverter;
import nl.strohalm.cyclos.utils.conversion.UnitsConverter;

import org.apache.commons.lang.StringUtils;

/**
 * Action used to get details for printing a scheduled payment in a local receipt printer
 * 
 * @author luis
 */
public class ScheduledPaymentReceiptAjaxAction extends BaseReceiptPrintAjaxAction {

    protected ScheduledPaymentService scheduledPaymentService;

    @Inject
    public void setScheduledPaymentService(final ScheduledPaymentService scheduledPaymentService) {
        this.scheduledPaymentService = scheduledPaymentService;
    }

    @Override
    protected String resolveText(final ActionContext context, final ReceiptPrinterSettings receiptPrinterSettings) {
        final ScheduledPaymentForm form = context.getForm();
        final long paymentId = form.getPaymentId();
        final ScheduledPayment scheduledPayment = scheduledPaymentService.load(paymentId, ViewScheduledPaymentAction.FETCH);

        final LocalSettings localSettings = settingsService.getLocalSettings();
        final CalendarConverter dateTimeConverter = localSettings.getDateTimeConverter();
        final CalendarConverter dateConverter = localSettings.getDateConverter();
        final UnitsConverter unitsConverter = localSettings.getUnitsConverter(scheduledPayment.getFrom().getType().getCurrency().getPattern());

        final int installmentCount = scheduledPayment.getTransfers().size();
        final boolean singleInstallment = installmentCount == 1;

        final StringBuilder out = new StringBuilder();
        out.append(context.message("receipt.transfer.header", localSettings.getApplicationName())).append('\n');
        out.append(context.message("receipt.transfer.textBefore")).append('\n');
        out.append(context.message("receipt.transfer.from", ownerString(scheduledPayment.getFrom()))).append('\n');
        out.append(context.message("receipt.transfer.to", ownerString(scheduledPayment.getTo()))).append('\n');
        out.append(context.message("receipt.transfer.date", dateTimeConverter.toString(scheduledPayment.getDate()))).append('\n');
        out.append(context.message("receipt.transfer.amount", unitsConverter.toString(scheduledPayment.getAmount()))).append('\n');
        if (singleInstallment) {
            out.append(context.message("receipt.transfer.scheduledFor", dateConverter.toString(scheduledPayment.getTransfers().get(0).getDate()))).append('\n');
        }
        if (!singleInstallment) {
            final List<Transfer> installments = scheduledPayment.getTransfers();
            out.append(context.message("receipt.transfer.installments", installments.size())).append('\n');
            final int maxDigits = String.valueOf(installments.size()).length();
            for (int i = 0; i < installmentCount; i++) {
                final Transfer installment = installments.get(i);
                final String index = StringUtils.leftPad(String.valueOf(i + 1), maxDigits, '0');
                final String date = dateConverter.toString(installment.getDate());
                final String amount = unitsConverter.toString(installment.getAmount());
                out.append(context.message("receipt.transfer.installment", index, date, amount)).append('\n');
            }
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
