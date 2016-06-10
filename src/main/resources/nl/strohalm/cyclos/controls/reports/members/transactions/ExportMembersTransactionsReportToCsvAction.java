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

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseCsvAction;
import nl.strohalm.cyclos.entities.access.User;
import nl.strohalm.cyclos.entities.accounts.transactions.PaymentFilter;
import nl.strohalm.cyclos.entities.members.MemberTransactionSummaryReportData;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.services.transactions.TransactionSummaryVO;
import nl.strohalm.cyclos.services.transfertypes.PaymentFilterService;
import nl.strohalm.cyclos.utils.EntityHelper;
import nl.strohalm.cyclos.utils.IteratorListImpl;
import nl.strohalm.cyclos.utils.Pair;
import nl.strohalm.cyclos.utils.SpringHelper;
import nl.strohalm.cyclos.utils.conversion.Converter;
import nl.strohalm.cyclos.utils.csv.CSVWriter;

import org.apache.commons.collections.IteratorUtils;
import org.apache.commons.collections.Predicate;

public class ExportMembersTransactionsReportToCsvAction extends BaseCsvAction {

    class SummaryByPaymentFilterConverter implements Converter<Map<PaymentFilter, TransactionSummaryVO>> {

        private static final long   serialVersionUID = -4821798713533063020L;

        private final PaymentFilter paymentFilter;
        private final boolean       isCount;

        SummaryByPaymentFilterConverter(final PaymentFilter paymentFilter, final boolean isCount) {
            this.paymentFilter = paymentFilter;
            this.isCount = isCount;
        }

        @Override
        public String toString(final Map<PaymentFilter, TransactionSummaryVO> map) {
            String string = "";
            final TransactionSummaryVO vo = map.get(paymentFilter);
            if (vo != null) {
                if (isCount) {
                    final int count = vo.getCount();
                    string = "" + count;
                } else {
                    final BigDecimal amount = vo.getAmount();
                    string = settingsService.getLocalSettings().getNumberConverter().toString(amount);
                }
            }
            return string;
        }

        @Override
        public Map<PaymentFilter, TransactionSummaryVO> valueOf(final String string) {
            return null;
        }
    }

    class TransactionSummaryVOConverter implements Converter<TransactionSummaryVO> {

        private static final long serialVersionUID = -3481170993171107591L;

        private final boolean     isCount;

        TransactionSummaryVOConverter(final boolean isCount) {
            this.isCount = isCount;
        }

        @Override
        public String toString(final TransactionSummaryVO vo) {
            String string = "";
            if (vo != null) {
                if (isCount) {
                    final int count = vo.getCount();
                    string = "" + count;
                } else {
                    final BigDecimal amount = vo.getAmount();
                    string = settingsService.getLocalSettings().getNumberConverter().toString(amount);
                }
            }
            return string;
        }

        @Override
        public TransactionSummaryVO valueOf(final String string) {
            return null;
        }
    }

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
        final Pair<MembersTransactionsReportDTO, Iterator<MemberTransactionSummaryReportData>> pair = reportHandler.handleTransactionsSummary(context);
        final MembersTransactionsReportDTO dto = pair.getFirst();
        final Iterator<MemberTransactionSummaryReportData> reportIterator = pair.getSecond();
        final Iterator iterator = IteratorUtils.filteredIterator(reportIterator, new Predicate() {
            @Override
            public boolean evaluate(final Object element) {
                final MemberTransactionSummaryReportData data = (MemberTransactionSummaryReportData) element;
                if (dto.isIncludeNoTraders()) {
                    return true;
                }
                return data.isHasData();
            }
        });
        return new IteratorListImpl(iterator);
    }

    @Override
    protected String fileName(final ActionContext context) {
        final User loggedUser = context.getUser();
        return "members_transactions_summaries_" + loggedUser.getUsername() + ".csv";
    }

    @Override
    protected CSVWriter<MemberTransactionSummaryReportData> resolveCSVWriter(final ActionContext context) {
        final MembersTransactionsReportForm form = context.getForm();
        final MembersTransactionsReportDTO dto = getReportHandler().getDataBinder().readFromString(form.getMembersTransactionsReport());
        dto.setTransactionsPaymentFilters(paymentFilterService.load(EntityHelper.toIdsAsList(dto.getTransactionsPaymentFilters())));

        final LocalSettings settings = settingsService.getLocalSettings();
        final CSVWriter<MemberTransactionSummaryReportData> csv = CSVWriter.instance(MemberTransactionSummaryReportData.class, settings);

        csv.addColumn(context.message("member.username"), "member.username");

        // Add the conditional columns
        if (dto.isMemberName()) {
            csv.addColumn(context.message("member.name"), "member.name");
        }
        if (dto.isBrokerUsername()) {
            csv.addColumn(context.message("member.brokerUsername"), "member.broker.username");
        }
        if (dto.isBrokerName()) {
            csv.addColumn(context.message("member.brokerName"), "member.broker.name");
        }
        final Collection<PaymentFilter> transactionsPaymentFilters = dto.getTransactionsPaymentFilters();

        final boolean incomingTransactions = dto.isIncomingTransactions();
        final boolean outgoingTransactions = dto.isOutgoingTransactions();

        for (final PaymentFilter paymentFilter : transactionsPaymentFilters) {
            if (incomingTransactions) {
                csv.addColumn( // number column
                        context.message("reports.transactions_report.transactions", paymentFilter.getName(), // message arg0
                                context.message("reports.transactions_report.number"), // message arg1
                                context.message("reports.transactions_report.credits") // message arg2
                        ), "credits", new SummaryByPaymentFilterConverter(paymentFilter, true));

                csv.addColumn( // amount column
                        context.message("reports.transactions_report.transactions", paymentFilter.getName(), // message arg0
                                context.message("reports.transactions_report.amount"), // message arg1
                                context.message("reports.transactions_report.credits") // message arg2
                        ), "credits", new SummaryByPaymentFilterConverter(paymentFilter, false));
            }
            if (outgoingTransactions) {
                csv.addColumn( // number column
                        context.message("reports.transactions_report.transactions", paymentFilter.getName(), // message arg0
                                context.message("reports.transactions_report.number"), // message arg1
                                context.message("reports.transactions_report.debits") // message arg2
                        ), "debits", new SummaryByPaymentFilterConverter(paymentFilter, true));
                csv.addColumn( // amount column
                        context.message("reports.transactions_report.transactions", paymentFilter.getName(), // message arg0
                                context.message("reports.transactions_report.amount"), // message arg1
                                context.message("reports.transactions_report.debits") // message arg2
                        ), "debits", new SummaryByPaymentFilterConverter(paymentFilter, false));
            }
        }

        return csv;
    }

}
