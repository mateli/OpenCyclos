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
package nl.strohalm.cyclos.services.reports;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import nl.strohalm.cyclos.entities.accounts.AccountType;
import nl.strohalm.cyclos.entities.accounts.Currency;
import nl.strohalm.cyclos.entities.accounts.MemberAccountType;
import nl.strohalm.cyclos.entities.accounts.SystemAccountType;
import nl.strohalm.cyclos.entities.ads.Ad;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.members.Reference;
import nl.strohalm.cyclos.entities.reports.AdReportVO;
import nl.strohalm.cyclos.entities.reports.CurrentStateReportVO;
import nl.strohalm.cyclos.entities.reports.InvoiceSummaryType;
import nl.strohalm.cyclos.services.accounts.AccountTypeServiceLocal;
import nl.strohalm.cyclos.services.accounts.CurrencyServiceLocal;
import nl.strohalm.cyclos.services.ads.AdServiceLocal;
import nl.strohalm.cyclos.services.elements.MemberServiceLocal;
import nl.strohalm.cyclos.services.elements.ReferenceServiceLocal;
import nl.strohalm.cyclos.services.fetch.FetchServiceLocal;
import nl.strohalm.cyclos.services.reports.CurrentStateReportParameters.TimePointType;
import nl.strohalm.cyclos.services.transactions.InvoiceServiceLocal;
import nl.strohalm.cyclos.services.transactions.LoanServiceLocal;
import nl.strohalm.cyclos.services.transactions.TransactionSummaryVO;

/**
 * Implementation for {@link CurrentStateReportService}
 * 
 * @author luis
 */
public class CurrentStateReportServiceImpl implements CurrentStateReportServiceLocal {

    private CurrencyServiceLocal    currencyService;
    private MemberServiceLocal      memberService;
    private AdServiceLocal          adService;
    private AccountTypeServiceLocal accountTypeService;
    private InvoiceServiceLocal     invoiceService;
    private LoanServiceLocal        loanService;
    private ReferenceServiceLocal   referenceService;
    private FetchServiceLocal       fetchService;

    @Override
    public CurrentStateReportVO getCurrentStateReport(final CurrentStateReportParameters params) {

        final CurrentStateReportVO report = new CurrentStateReportVO();

        final List<Currency> currencies = currencyService.listAll();

        TimePointType timePointType = params.getTimePointType();
        Calendar timePoint = timePointType == TimePointType.TIME_POINT_HISTORY ? params.getTimePoint() : null;

        Collection<MemberGroup> groups = fetchService.fetch(params.getMemberGroups());
        Collection<MemberGroup> activeGroups = new ArrayList<MemberGroup>();
        for (MemberGroup group : groups) {
            if (group.isActive()) {
                activeGroups.add(group);
            }
        }
        if (params.isMemberGroupInformation()) {
            final Map<MemberGroup, Integer> groupMemberCount = memberService.getGroupMemberCount(groups, timePoint);
            int activeMembers = 0;
            for (Entry<MemberGroup, Integer> entry : groupMemberCount.entrySet()) {
                if (entry.getKey().isActive()) {
                    activeMembers += entry.getValue();
                }
            }
            report.setNumberActiveMembers(activeMembers);
            report.setGroupMemberCount(groupMemberCount);
        }

        if (params.isAds()) {
            final Integer numberActiveMembersWithAds = adService.countMembersWithAds(activeGroups, timePoint);
            final Integer numberActiveAdvertisements = adService.getNumberOfAds(groups, Ad.Status.ACTIVE, timePoint);
            final Integer numberExpiredAdvertisements = adService.getNumberOfAds(groups, Ad.Status.EXPIRED, timePoint);
            final Integer numberScheduledAdvertisements = adService.getNumberOfAds(groups, Ad.Status.SCHEDULED, timePoint);
            final Integer numberPermanentAdvertisements = adService.getNumberOfAds(groups, Ad.Status.PERMANENT, timePoint);
            final AdReportVO adReportVO = new AdReportVO();
            adReportVO.setNumberActiveMembersWithAds(numberActiveMembersWithAds);
            adReportVO.setNumberActiveAdvertisements(numberActiveAdvertisements);
            adReportVO.setNumberExpiredAdvertisements(numberExpiredAdvertisements);
            adReportVO.setNumberScheduledAdvertisements(numberScheduledAdvertisements);
            adReportVO.setNumberPermanentAdvertisements(numberPermanentAdvertisements);
            report.setAdReportVO(adReportVO);
        }

        Collection<AccountType> visibleAccountTypes = accountTypeService.getVisibleAccountTypes();

        if (params.isSystemAccountInformation()) {
            Collection<SystemAccountType> systemAccountTypes = new ArrayList<SystemAccountType>();
            for (AccountType accountType : visibleAccountTypes) {
                if (accountType instanceof SystemAccountType) {
                    systemAccountTypes.add((SystemAccountType) accountType);
                }
            }

            final Map<SystemAccountType, BigDecimal> systemAccountTypesBalance = accountTypeService.getSystemAccountTypesBalance(systemAccountTypes, timePoint);
            report.setSystemAccountTypesBalance(systemAccountTypesBalance);
        }

        if (params.isMemberAccountInformation()) {
            Collection<MemberAccountType> memberAccountTypes = new ArrayList<MemberAccountType>();
            for (AccountType accountType : visibleAccountTypes) {
                if (accountType instanceof MemberAccountType) {
                    memberAccountTypes.add((MemberAccountType) accountType);
                }
            }

            final Map<MemberAccountType, BigDecimal> memberAccountTypesBalance = accountTypeService.getMemberAccountTypesBalance(memberAccountTypes, groups, timePoint);
            report.setMemberAccountTypesBalance(memberAccountTypesBalance);
        }

        if (params.isInvoices()) {
            final Map<Currency, Map<InvoiceSummaryType, TransactionSummaryVO>> invoicesSummaries = new LinkedHashMap<Currency, Map<InvoiceSummaryType, TransactionSummaryVO>>(currencies.size());

            for (final Currency currency : currencies) {
                final TransactionSummaryVO memberInvoicesSummary = invoiceService.getSummaryByType(currency, InvoiceSummaryType.MEMBER);
                final TransactionSummaryVO systemIncomingInvoicesSummary = invoiceService.getSummaryByType(currency, InvoiceSummaryType.SYSTEM_INCOMING);
                final TransactionSummaryVO systemOutgoingInvoicesSummary = invoiceService.getSummaryByType(currency, InvoiceSummaryType.SYSTEM_OUTGOING);
                final Map<InvoiceSummaryType, TransactionSummaryVO> currencyInvoices = new EnumMap<InvoiceSummaryType, TransactionSummaryVO>(InvoiceSummaryType.class);
                currencyInvoices.put(InvoiceSummaryType.MEMBER, memberInvoicesSummary);
                currencyInvoices.put(InvoiceSummaryType.SYSTEM_INCOMING, systemIncomingInvoicesSummary);
                currencyInvoices.put(InvoiceSummaryType.SYSTEM_OUTGOING, systemOutgoingInvoicesSummary);
                invoicesSummaries.put(currency, currencyInvoices);
            }

            report.setInvoicesSummaries(invoicesSummaries);
        }

        if (params.isLoans()) {
            final Map<Currency, TransactionSummaryVO> loansSummaries = new LinkedHashMap<Currency, TransactionSummaryVO>();

            for (final Currency currency : currencies) {
                final TransactionSummaryVO openLoansSummary = loanService.getOpenLoansSummary(currency);
                loansSummaries.put(currency, openLoansSummary);
            }

            report.setOpenLoansSummary(loansSummaries);
        }

        if (params.isReferences()) {
            final Map<Reference.Level, Integer> givenReferences = referenceService.countGivenReferencesByLevel(Reference.Nature.GENERAL);
            report.setGivenReferences(givenReferences);
        }

        report.setCurrencies(currencies);
        return report;
    }

    public void setAccountTypeServiceLocal(final AccountTypeServiceLocal accountTypeService) {
        this.accountTypeService = accountTypeService;
    }

    public void setAdServiceLocal(final AdServiceLocal adService) {
        this.adService = adService;
    }

    public void setCurrencyServiceLocal(final CurrencyServiceLocal currencyService) {
        this.currencyService = currencyService;
    }

    public void setFetchServiceLocal(final FetchServiceLocal fetchService) {
        this.fetchService = fetchService;
    }

    public void setInvoiceServiceLocal(final InvoiceServiceLocal invoiceService) {
        this.invoiceService = invoiceService;
    }

    public void setLoanServiceLocal(final LoanServiceLocal loanService) {
        this.loanService = loanService;
    }

    public void setMemberServiceLocal(final MemberServiceLocal memberService) {
        this.memberService = memberService;
    }

    public void setReferenceServiceLocal(final ReferenceServiceLocal referenceService) {
        this.referenceService = referenceService;
    }

}
