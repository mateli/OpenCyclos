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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.reports.members.transactions.MembersTransactionsReportDTO.DetailsLevel;
import nl.strohalm.cyclos.entities.accounts.AccountType;
import nl.strohalm.cyclos.entities.accounts.transactions.PaymentFilter;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.members.MemberTransactionDetailsReportData;
import nl.strohalm.cyclos.entities.members.MemberTransactionSummaryReportData;
import nl.strohalm.cyclos.entities.members.MembersTransactionsReportParameters;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.services.accounts.AccountService;
import nl.strohalm.cyclos.services.accounts.AccountTypeService;
import nl.strohalm.cyclos.services.transfertypes.PaymentFilterService;
import nl.strohalm.cyclos.utils.EntityHelper;
import nl.strohalm.cyclos.utils.Pair;
import nl.strohalm.cyclos.utils.binding.BeanBinder;
import nl.strohalm.cyclos.utils.binding.DataBinderHelper;
import nl.strohalm.cyclos.utils.binding.PropertyBinder;
import nl.strohalm.cyclos.utils.binding.SimpleCollectionBinder;
import nl.strohalm.cyclos.utils.conversion.ReferenceConverter;
import nl.strohalm.cyclos.utils.query.PageParameters;
import nl.strohalm.cyclos.utils.validation.ValidationError;
import nl.strohalm.cyclos.utils.validation.ValidationException;

import org.apache.commons.collections.CollectionUtils;

public class MembersReportHandler {

    private AccountService                           accountService;
    private AccountTypeService                       accountTypeService;
    private PaymentFilterService                     paymentFilterService;

    private BeanBinder<MembersTransactionsReportDTO> binder;

    private LocalSettings                            settings = null;

    public MembersReportHandler(final LocalSettings settings) {
        this.settings = settings;
    }

    public BeanBinder<MembersTransactionsReportDTO> getDataBinder() {
        if (binder == null) {
            BeanBinder<MembersTransactionsReportDTO> temp;
            final ReferenceConverter<AccountType> accountTypeConverter = ReferenceConverter.instance(AccountType.class);
            final ReferenceConverter<PaymentFilter> paymentFilterConverter = ReferenceConverter.instance(PaymentFilter.class);
            final ReferenceConverter<MemberGroup> memberGroupConverter = ReferenceConverter.instance(MemberGroup.class);

            temp = BeanBinder.instance(MembersTransactionsReportDTO.class);
            temp.registerBinder("memberName", PropertyBinder.instance(Boolean.TYPE, "memberName"));
            temp.registerBinder("brokerUsername", PropertyBinder.instance(Boolean.TYPE, "brokerUsername"));
            temp.registerBinder("brokerName", PropertyBinder.instance(Boolean.TYPE, "brokerName"));
            temp.registerBinder("accountTypes", SimpleCollectionBinder.instance(AccountType.class, "accountTypes", accountTypeConverter));
            temp.registerBinder("memberGroups", SimpleCollectionBinder.instance(MemberGroup.class, "memberGroups", memberGroupConverter));
            temp.registerBinder("period", DataBinderHelper.periodBinder(settings, "period"));

            // Transactions related binding
            temp.registerBinder("transactionsPaymentFilters", SimpleCollectionBinder.instance(PaymentFilter.class, "transactionsPaymentFilters", paymentFilterConverter));
            temp.registerBinder("incomingTransactions", PropertyBinder.instance(Boolean.TYPE, "incomingTransactions"));
            temp.registerBinder("outgoingTransactions", PropertyBinder.instance(Boolean.TYPE, "outgoingTransactions"));
            temp.registerBinder("includeNoTraders", PropertyBinder.instance(Boolean.TYPE, "includeNoTraders"));
            temp.registerBinder("detailsLevel", PropertyBinder.instance(MembersTransactionsReportDTO.DetailsLevel.class, "detailsLevel"));
            binder = temp;
        }
        return binder;
    }

    public Pair<MembersTransactionsReportDTO, Iterator<MemberTransactionDetailsReportData>> handleTransactionsDetails(final ActionContext context) {
        final MembersTransactionsReportDTO dto = readDTO(context);
        final MembersTransactionsReportParameters params = toTransactionReportParameters(dto);
        final Iterator<MemberTransactionDetailsReportData> iterator = accountService.membersTransactionsDetailsReport(params);
        return new Pair<MembersTransactionsReportDTO, Iterator<MemberTransactionDetailsReportData>>(dto, iterator);

    }

    public Pair<MembersTransactionsReportDTO, Iterator<MemberTransactionSummaryReportData>> handleTransactionsSummary(final ActionContext context) {
        final MembersTransactionsReportDTO dto = readDTO(context);
        final MembersTransactionsReportParameters params = toTransactionReportParameters(dto);
        params.setPageParameters(new PageParameters(-1, 0));
        final Iterator<MemberTransactionSummaryReportData> iterator = accountService.membersTransactionsSummaryReport(params);
        return new Pair<MembersTransactionsReportDTO, Iterator<MemberTransactionSummaryReportData>>(dto, iterator);
    }

    @Inject
    public void setAccountService(final AccountService accountService) {
        this.accountService = accountService;
    }

    @Inject
    public void setAccountTypeService(final AccountTypeService accountTypeService) {
        this.accountTypeService = accountTypeService;
    }

    @Inject
    public void setPaymentFilterService(final PaymentFilterService paymentFilterService) {
        this.paymentFilterService = paymentFilterService;
    }

    public void validateDTO(final MembersTransactionsReportDTO dto) throws ValidationException {
        adjustDto(dto);
        final ValidationException validationException = new ValidationException();

        if (CollectionUtils.isEmpty(dto.getMemberGroups())) {
            validationException.addGeneralError(new ValidationError("reports.members_reports.transactions.memberGroupsRequired"));
        }

        if (CollectionUtils.isEmpty(dto.getAccountTypes())) {
            validationException.addGeneralError(new ValidationError("reports.members_reports.transactions.accountTypesRequired"));
        }
        if (dto.getDetailsLevel() == DetailsLevel.SUMMARY) {
            if (CollectionUtils.isEmpty(dto.getTransactionsPaymentFilters())) {
                validationException.addGeneralError(new ValidationError("reports.members_reports.transactions.paymentFilterRequired"));
            } else if (!dto.isIncomingTransactions() && !dto.isOutgoingTransactions()) {
                validationException.addGeneralError(new ValidationError("reports.members_reports.transactions.transactionModeRequired"));
            }
        }
        validationException.throwIfHasErrors();
    }

    /*
     * Clean data that is not used by current 'what to show' selection
     */
    private void adjustDto(final MembersTransactionsReportDTO dto) {

        // Initialize account types
        final Collection<AccountType> accountTypes = accountTypeService.load(EntityHelper.toIdsAsList(dto.getAccountTypes()));
        dto.setAccountTypes(accountTypes);

        // Separate payment filters by account type
        final Collection<PaymentFilter> transactionsPaymentFilters = paymentFilterService.load(EntityHelper.toIdsAsList(dto.getTransactionsPaymentFilters()), PaymentFilter.Relationships.ACCOUNT_TYPE);
        final Map<AccountType, Collection<PaymentFilter>> paymentFiltersByAccountType = new HashMap<AccountType, Collection<PaymentFilter>>();
        for (final AccountType accountType : accountTypes) {
            final Collection<PaymentFilter> accountTypePaymentFilters = new ArrayList<PaymentFilter>();
            for (final PaymentFilter paymentFilter : transactionsPaymentFilters) {
                if (paymentFilter.getAccountType().equals(accountType)) {
                    accountTypePaymentFilters.add(paymentFilter);
                }
            }
            paymentFiltersByAccountType.put(accountType, accountTypePaymentFilters);
        }
        dto.setPaymentFiltersByAccountType(paymentFiltersByAccountType);

        // Calculate the colspan for each payment filter
        final Map<PaymentFilter, Integer> transactionsColSpan = new HashMap<PaymentFilter, Integer>();
        final boolean incomingTransactions = dto.isIncomingTransactions();
        final boolean outgoingTransactions = dto.isOutgoingTransactions();
        if (incomingTransactions || outgoingTransactions) {
            for (final Iterator<PaymentFilter> it = transactionsPaymentFilters.iterator(); it.hasNext();) {
                final PaymentFilter paymentFilter = it.next();
                int colSpan = 0;
                if (incomingTransactions) {
                    colSpan += 2;
                }
                if (outgoingTransactions) {
                    colSpan += 2;
                }
                if (colSpan == 0) {
                    it.remove();
                } else {
                    transactionsColSpan.put(paymentFilter, colSpan);
                }
            }
        }
        dto.setTransactionsPaymentFilters(transactionsPaymentFilters);
        dto.setTransactionsColSpan(transactionsColSpan);
    }

    private MembersTransactionsReportDTO readDTO(final ActionContext context) {
        final MembersTransactionsReportForm form = context.getForm();
        final MembersTransactionsReportDTO dto = getDataBinder().readFromString(form.getMembersTransactionsReport());
        dto.setTransactionsPaymentFilters(paymentFilterService.load(EntityHelper.toIdsAsList(dto.getTransactionsPaymentFilters()), PaymentFilter.Relationships.TRANSFER_TYPES));
        adjustDto(dto);
        return dto;
    }

    private MembersTransactionsReportParameters toTransactionReportParameters(final MembersTransactionsReportDTO dto) {
        final MembersTransactionsReportParameters params = new MembersTransactionsReportParameters();
        params.setFetchBroker(dto.isBrokerName() || dto.isBrokerUsername());
        params.setMemberGroups(dto.getMemberGroups());
        params.setPeriod(dto.getPeriod());
        params.setCredits(dto.isIncomingTransactions());
        params.setDebits(dto.isOutgoingTransactions());
        final Map<AccountType, Collection<PaymentFilter>> paymentFiltersByAccountType = dto.getPaymentFiltersByAccountType();
        if (paymentFiltersByAccountType != null) {
            final Set<PaymentFilter> allPaymentFilters = new HashSet<PaymentFilter>();
            for (final Collection<PaymentFilter> paymentFilters : paymentFiltersByAccountType.values()) {
                allPaymentFilters.addAll(paymentFilters);
            }
            params.setPaymentFilters(allPaymentFilters);
        }
        return params;
    }

}
