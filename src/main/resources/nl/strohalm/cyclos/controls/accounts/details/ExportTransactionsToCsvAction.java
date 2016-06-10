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
import nl.strohalm.cyclos.controls.BaseCsvAction;
import nl.strohalm.cyclos.entities.access.User;
import nl.strohalm.cyclos.entities.accounts.Account;
import nl.strohalm.cyclos.entities.accounts.MemberAccount;
import nl.strohalm.cyclos.entities.accounts.transactions.Payment;
import nl.strohalm.cyclos.entities.accounts.transactions.Transfer;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferQuery;
import nl.strohalm.cyclos.entities.customization.fields.PaymentCustomField;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.entities.settings.events.LocalSettingsEvent;
import nl.strohalm.cyclos.services.accounts.AccountDTO;
import nl.strohalm.cyclos.services.accounts.AccountService;
import nl.strohalm.cyclos.services.customization.PaymentCustomFieldService;
import nl.strohalm.cyclos.services.transactions.PaymentService;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.conversion.Converter;
import nl.strohalm.cyclos.utils.conversion.CustomFieldConverter;
import nl.strohalm.cyclos.utils.csv.CSVWriter;
import nl.strohalm.cyclos.utils.query.QueryParameters.ResultType;

/**
 * Action used to export transactions as a CSV file
 * @author luis
 */
public class ExportTransactionsToCsvAction extends BaseCsvAction {

    private DataBinder<TransferQuery> dataBinder;
    private PaymentService            paymentService;
    private AccountService            accountService;
    private PaymentCustomFieldService paymentCustomFieldService;

    public DataBinder<TransferQuery> getDataBinder() {
        if (dataBinder == null) {
            dataBinder = AccountHistoryAction.transferQueryDataBinder(settingsService.getLocalSettings());
        }
        return dataBinder;
    }

    @Override
    public void onLocalSettingsUpdate(final LocalSettingsEvent event) {
        super.onLocalSettingsUpdate(event);
        dataBinder = null;
    }

    @Inject
    public void setAccountService(final AccountService accountService) {
        this.accountService = accountService;
    }

    @Inject
    public void setPaymentCustomFieldService(final PaymentCustomFieldService paymentCustomFieldService) {
        this.paymentCustomFieldService = paymentCustomFieldService;
    }

    @Inject
    public void setPaymentService(final PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @Override
    protected List<?> executeQuery(final ActionContext context) {
        final AccountHistoryForm form = context.getForm();
        final TransferQuery query = getDataBinder().readFromString(form.getQuery());
        final Account account = getAccount(query);
        query.fetch(Payment.Relationships.FROM, Payment.Relationships.TO, Payment.Relationships.TYPE);
        query.setResultType(ResultType.ITERATOR);
        final List<Transfer> transfers = paymentService.search(query);
        return AccountHistoryAction.Entry.build(permissionService, elementService, account, transfers, false);
    }

    @Override
    protected String fileName(final ActionContext context) {
        final User loggedUser = context.getUser();
        return "transactions_" + loggedUser.getUsername() + ".csv";
    }

    @Override
    protected CSVWriter<Transfer> resolveCSVWriter(final ActionContext context) {
        final AccountHistoryForm form = context.getForm();
        final TransferQuery query = getDataBinder().readFromString(form.getQuery());
        final Account account = getAccount(query);

        final LocalSettings settings = settingsService.getLocalSettings();
        final CSVWriter<Transfer> csv = CSVWriter.instance(Transfer.class, settings);
        csv.addColumn(context.message("transfer.date"), "transfer.actualDate", settings.getDateTimeConverter());
        csv.addColumn(context.message("transfer.type"), "transfer.type.name");
        csv.addColumn(context.message("transfer.amount"), "amount", settings.getNumberConverter());
        csv.addColumn(context.message("transfer.fromOrTo"), "related.ownerName");
        csv.addColumn(context.message("transfer.fromOrTo") + " - " + context.message("member.name"), "related", new Converter<Account>() {
            private static final long serialVersionUID = 1L;

            @Override
            public String toString(final Account account) {
                if (account instanceof MemberAccount) {
                    return ((MemberAccount) account).getMember().getName();
                }
                return null;
            }

            @Override
            public Account valueOf(final String string) {
                return null;
            }
        });
        final List<PaymentCustomField> customFields = paymentCustomFieldService.listForList(account, false);
        for (final PaymentCustomField field : customFields) {
            csv.addColumn(field.getName(), "transfer.customValues", new CustomFieldConverter(field, elementService, settings));
        }
        csv.addColumn(context.message("transfer.description"), "transfer.description");
        if (settings.getTransactionNumber() != null && settings.getTransactionNumber().isValid()) {
            csv.addColumn(context.message("transfer.transactionNumber"), "transfer.transactionNumber");
        }
        return csv;
    }

    @Override
    protected boolean shouldLimit() {
        return false;
    }

    private Account getAccount(final TransferQuery query) {
        return accountService.getAccount(new AccountDTO(query.getOwner(), query.getType()));
    }
}
