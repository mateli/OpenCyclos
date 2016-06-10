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
package nl.strohalm.cyclos.controls.reports.members.transactions;

import java.util.Iterator;
import java.util.List;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseCsvAction;
import nl.strohalm.cyclos.entities.members.MemberTransactionDetailsReportData;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.services.transfertypes.PaymentFilterService;
import nl.strohalm.cyclos.utils.EntityHelper;
import nl.strohalm.cyclos.utils.IteratorListImpl;
import nl.strohalm.cyclos.utils.Pair;
import nl.strohalm.cyclos.utils.SpringHelper;
import nl.strohalm.cyclos.utils.csv.CSVWriter;

import org.apache.commons.collections.IteratorUtils;
import org.apache.commons.collections.Predicate;

public class ExportMembersTransactionsDetailsToCsvAction extends BaseCsvAction {

    private MembersReportHandler reportHandler;
    private PaymentFilterService paymentFilterService;

    public MembersReportHandler getReportHandler() {
        if (reportHandler == null) {
            reportHandler = new MembersReportHandler(settingsService.getLocalSettings());
            SpringHelper.injectBeans(getServlet().getServletContext(), reportHandler);
        }
        return reportHandler;
    }

    @Inject
    public void setPaymentFilterService(final PaymentFilterService paymentFilterService) {
        this.paymentFilterService = paymentFilterService;
    }

    @Override
    @SuppressWarnings({ "unchecked", "rawtypes" })
    protected List<?> executeQuery(final ActionContext context) {
        final MembersReportHandler reportHandler = getReportHandler();
        final Pair<MembersTransactionsReportDTO, Iterator<MemberTransactionDetailsReportData>> pair = reportHandler.handleTransactionsDetails(context);
        final MembersTransactionsReportDTO dto = pair.getFirst();
        final Iterator<MemberTransactionDetailsReportData> reportIterator = pair.getSecond();
        final Iterator iterator = IteratorUtils.filteredIterator(reportIterator, new Predicate() {
            @Override
            public boolean evaluate(final Object element) {
                final MemberTransactionDetailsReportData data = (MemberTransactionDetailsReportData) element;
                if (dto.isIncludeNoTraders()) {
                    return true;
                }
                return data.getAmount() != null;
            }
        });
        return new IteratorListImpl(iterator);
    }

    @Override
    protected String fileName(final ActionContext context) {
        return "members_transactions_details_" + context.getUser().getUsername() + ".csv";
    }

    @Override
    protected CSVWriter<?> resolveCSVWriter(final ActionContext context) {
        final LocalSettings localSettings = settingsService.getLocalSettings();
        final MembersTransactionsReportForm form = context.getForm();
        final MembersTransactionsReportDTO dto = getReportHandler().getDataBinder().readFromString(form.getMembersTransactionsReport());
        dto.setTransactionsPaymentFilters(paymentFilterService.load(EntityHelper.toIdsAsList(dto.getTransactionsPaymentFilters())));

        responseHelper.setDownload(context.getResponse(), "members_transactions_details.csv");
        final CSVWriter<MemberTransactionDetailsReportData> csv = CSVWriter.instance(MemberTransactionDetailsReportData.class, localSettings);

        csv.addColumn(context.message("member.username"), "username");
        if (dto.isMemberName()) {
            csv.addColumn(context.message("member.name"), "name");
        }
        if (dto.isBrokerUsername()) {
            csv.addColumn(context.message("member.brokerUsername"), "brokerUsername");
        }
        if (dto.isBrokerName()) {
            csv.addColumn(context.message("member.brokerName"), "brokerName");
        }
        csv.addColumn(context.message("account.type"), "accountTypeName");
        csv.addColumn(context.message("transfer.type"), "transferTypeName");
        csv.addColumn(context.message("transfer.date"), "date", localSettings.getDateConverter());
        csv.addColumn(context.message("transfer.amount"), "amount", localSettings.getNumberConverter());
        csv.addColumn(context.message("transfer.fromOrTo"), "relatedUsername");
        if (dto.isMemberName()) {
            csv.addColumn(context.message("transfer.fromOrTo"), "relatedName");
        }
        if (localSettings.getTransactionNumber() != null && localSettings.getTransactionNumber().isValid()) {
            csv.addColumn(context.message("transfer.transactionNumber"), "transactionNumber");
        }
        csv.addColumn(context.message("transfer.description"), "description");
        return csv;
    }

}
