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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.entities.accounts.loans.Loan;
import nl.strohalm.cyclos.entities.accounts.loans.LoanPayment;
import nl.strohalm.cyclos.entities.accounts.loans.LoanPayment.Status;
import nl.strohalm.cyclos.entities.accounts.loans.LoanPaymentQuery;
import nl.strohalm.cyclos.entities.accounts.transactions.Payment;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferType;
import nl.strohalm.cyclos.entities.customization.fields.PaymentCustomField;
import nl.strohalm.cyclos.services.transactions.TransactionSummaryVO;
import nl.strohalm.cyclos.utils.RelationshipHelper;
import nl.strohalm.cyclos.utils.query.QueryParameters;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

/**
 * Action used to print loan results
 * @author luis
 */
public class PrintLoanPaymentsAction extends SearchLoanPaymentsAction {

    @Override
    protected Integer pageSize(final ActionContext context) {
        return null;
    }

    @Override
    protected QueryParameters prepareForm(final ActionContext context) {
        final HttpServletRequest request = context.getRequest();

        final LoanPaymentQuery query = (LoanPaymentQuery) super.prepareForm(context);
        query.fetch(RelationshipHelper.nested(LoanPayment.Relationships.LOAN, Loan.Relationships.TRANSFER, Payment.Relationships.CUSTOM_VALUES));

        if (query.getTransferType() != null) {
            query.setTransferType(transferTypeService.load(query.getTransferType().getId()));
        }
        if (query.getLoanGroup() != null) {
            query.setLoanGroup(loanGroupService.load(query.getLoanGroup().getId()));
        }

        final TransferType transferType = query.getTransferType();
        if (transferType != null) {
            final List<PaymentCustomField> allFields = paymentCustomFieldService.list(transferType, true);
            final List<PaymentCustomField> customFieldsForList = new ArrayList<PaymentCustomField>();
            for (final PaymentCustomField customField : allFields) {
                if (customField.getListAccess() != PaymentCustomField.Access.NONE) {
                    customFieldsForList.add(customField);
                }
            }
            request.setAttribute("loanCustomFields", customFieldsForList);

        }

        // Store the summaries
        request.setAttribute("paymentsToReceive", buildSummary(query, Status.OPEN, Status.EXPIRED));
        request.setAttribute("discardedPayments", buildSummary(query, Status.DISCARDED));
        request.setAttribute("receivedPayments", buildSummary(query, Status.REPAID));
        request.setAttribute("inProcessPayments", buildSummary(query, Status.IN_PROCESS));
        request.setAttribute("unrecoverablePayments", buildSummary(query, Status.UNRECOVERABLE));
        request.setAttribute("recoveredPayments", buildSummary(query, Status.RECOVERED));

        final List<Status> status = query.getStatusList();
        String statusLabel;
        if (CollectionUtils.isEmpty(status) || (status.size() == Status.values().length)) {
            statusLabel = context.message("global.search.all.male");
        } else {
            final Collection<String> labels = new ArrayList<String>(status.size());
            for (final Status current : status) {
                labels.add(context.message("loanPayment.status." + current));
            }
            ;
            statusLabel = StringUtils.join(labels.iterator(), ", ");
        }
        request.setAttribute("statusLabel", statusLabel);
        return query;
    }

    private TransactionSummaryVO buildSummary(final LoanPaymentQuery query, final Status... requiredStatus) {
        final List<Status> selectedStatus = query.getStatusList();
        // Determine the used status
        List<Status> usedStatus;
        if (CollectionUtils.isEmpty(selectedStatus)) {
            // When all status are selected, use all status
            usedStatus = Arrays.asList(requiredStatus);
        } else {
            // When specific status are selected, only use them if they match the required status
            usedStatus = new ArrayList<Status>();
            for (final Status current : requiredStatus) {
                if (selectedStatus.contains(current)) {
                    usedStatus.add(current);
                }
            }
        }
        if (usedStatus.isEmpty()) {
            // No status match: return zero
            return new TransactionSummaryVO(0, BigDecimal.ZERO);
        }
        // Return the summary
        final LoanPaymentQuery newQuery = (LoanPaymentQuery) query.clone();
        newQuery.clearFetch();
        newQuery.setStatusList(usedStatus);
        return loanService.paymentsSummary(newQuery);
    }
}
