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
package nl.strohalm.cyclos.controls.loans;

import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseCsvAction;
import nl.strohalm.cyclos.entities.access.User;
import nl.strohalm.cyclos.entities.accounts.MemberAccount;
import nl.strohalm.cyclos.entities.accounts.loans.Loan;
import nl.strohalm.cyclos.entities.accounts.loans.LoanPayment;
import nl.strohalm.cyclos.entities.accounts.loans.LoanPaymentQuery;
import nl.strohalm.cyclos.entities.accounts.transactions.Payment;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferType;
import nl.strohalm.cyclos.entities.customization.fields.PaymentCustomField;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.entities.settings.events.LocalSettingsEvent;
import nl.strohalm.cyclos.services.customization.PaymentCustomFieldService;
import nl.strohalm.cyclos.services.transactions.LoanService;
import nl.strohalm.cyclos.utils.RelationshipHelper;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.conversion.CustomFieldConverter;
import nl.strohalm.cyclos.utils.conversion.MessageConverter;
import nl.strohalm.cyclos.utils.csv.CSVWriter;
import nl.strohalm.cyclos.utils.query.QueryParameters.ResultType;

/**
 * Action used to export loan payments to csv
 * @author luis
 */
public class ExportLoanPaymentsToCsvAction extends BaseCsvAction {

    private DataBinder<LoanPaymentQuery> dataBinder;
    private LoanService                  loanService;
    private PaymentCustomFieldService    paymentCustomFieldService;
    private ReadWriteLock                lock = new ReentrantReadWriteLock(true);

    public DataBinder<LoanPaymentQuery> getDataBinder() {
        try {
            lock.readLock().lock();
            if (dataBinder == null) {
                dataBinder = SearchLoanPaymentsAction.loanPaymentQueryDataBinder(settingsService.getLocalSettings());
            }
            return dataBinder;
        } finally {
            lock.readLock().unlock();
        }
    }

    public LoanService getLoanService() {
        return loanService;
    }

    public PaymentCustomFieldService getPaymentCustomFieldService() {
        return paymentCustomFieldService;
    }

    @Override
    public void onLocalSettingsUpdate(final LocalSettingsEvent event) {
        try {
            lock.writeLock().lock();
            super.onLocalSettingsUpdate(event);
            dataBinder = null;
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Inject
    public void setLoanService(final LoanService loanService) {
        this.loanService = loanService;
    }

    @Inject
    public void setPaymentCustomFieldService(final PaymentCustomFieldService paymentCustomFieldService) {
        this.paymentCustomFieldService = paymentCustomFieldService;
    }

    @Override
    protected List<?> executeQuery(final ActionContext context) {
        final SearchLoanPaymentsForm form = context.getForm();
        final LoanPaymentQuery query = getDataBinder().readFromString(form.getQuery());
        query.setResultType(ResultType.ITERATOR);
        query.fetch(RelationshipHelper.nested(LoanPayment.Relationships.LOAN, Loan.Relationships.TRANSFER, Payment.Relationships.TO, MemberAccount.Relationships.MEMBER, Element.Relationships.USER));
        return loanService.search(query);
    }

    @Override
    protected String fileName(final ActionContext context) {
        final User loggedUser = context.getUser();
        return "loan_payments_" + loggedUser.getUsername() + ".csv";
    }

    @Override
    @SuppressWarnings({ "unchecked", "rawtypes" })
    protected CSVWriter resolveCSVWriter(final ActionContext context) {
        final SearchLoanPaymentsForm form = context.getForm();
        final LoanPaymentQuery query = getDataBinder().readFromString(form.getQuery());

        final LocalSettings settings = settingsService.getLocalSettings();
        final CSVWriter<LoanPayment> csv = CSVWriter.instance(LoanPayment.class, settings);
        // Get the custom fields
        final TransferType transferType = query.getTransferType();
        if (transferType != null) {
            final List<PaymentCustomField> customFields = paymentCustomFieldService.list(transferType, true);
            for (final PaymentCustomField field : customFields) {
                csv.addColumn(field.getName(), "loan.transfer.customValues", new CustomFieldConverter(field, elementService, settings));
            }
        }
        csv.addColumn(context.message("member.username"), "loan.transfer.to.member.username");
        csv.addColumn(context.message("loanPayment.expirationDate"), "expirationDate", settings.getRawDateConverter());
        csv.addColumn(context.message("loanPayment.repaymentDate"), "repaymentDate", settings.getDateTimeConverter());
        csv.addColumn(context.message("loanPayment.amount"), "amount", settings.getNumberConverter());
        csv.addColumn(context.message("loanPayment.status"), "status", new MessageConverter(context, "loanPayment.status."));
        csv.addColumn(context.message("loanPayment.search.repaidAmount"), "repaidAmount", settings.getNumberConverter());
        csv.addColumn(context.message("loanPayment.search.discardedAmount"), "discardedAmount", settings.getNumberConverter());
        return csv;
    }
}
