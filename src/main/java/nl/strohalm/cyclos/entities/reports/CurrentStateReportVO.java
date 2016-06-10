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
package nl.strohalm.cyclos.entities.reports;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import nl.strohalm.cyclos.entities.accounts.Currency;
import nl.strohalm.cyclos.entities.accounts.MemberAccountType;
import nl.strohalm.cyclos.entities.accounts.SystemAccountType;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.members.Reference;
import nl.strohalm.cyclos.services.transactions.TransactionSummaryVO;
import nl.strohalm.cyclos.utils.DataObject;

public class CurrentStateReportVO extends DataObject {

    private static final long                                            serialVersionUID = 5310819684945275392L;
    private Integer                                                      numberActiveMembers;
    private Map<MemberGroup, Integer>                                    groupMemberCount;
    private AdReportVO                                                   adReportVO;
    private Map<SystemAccountType, BigDecimal>                           systemAccountTypesBalance;
    private Map<MemberAccountType, BigDecimal>                           memberAccountTypesBalance;
    private Map<Currency, Map<InvoiceSummaryType, TransactionSummaryVO>> invoicesSummaries;
    private Map<Currency, TransactionSummaryVO>                          openLoansSummary;
    private Map<Reference.Level, Integer>                                givenReferences;
    private List<Currency>                                               currencies;

    public AdReportVO getAdReportVO() {
        return adReportVO;
    }

    public List<Currency> getCurrencies() {
        return currencies;
    }

    public Map<Reference.Level, Integer> getGivenReferences() {
        return givenReferences;
    }

    public Map<MemberGroup, Integer> getGroupMemberCount() {
        return groupMemberCount;
    }

    public Map<Currency, Map<InvoiceSummaryType, TransactionSummaryVO>> getInvoicesSummaries() {
        return invoicesSummaries;
    }

    public Map<MemberAccountType, BigDecimal> getMemberAccountTypesBalance() {
        return memberAccountTypesBalance;
    }

    public Integer getNumberActiveMembers() {
        return numberActiveMembers;
    }

    public Map<Currency, TransactionSummaryVO> getOpenLoansSummary() {
        return openLoansSummary;
    }

    public Map<SystemAccountType, BigDecimal> getSystemAccountTypesBalance() {
        return systemAccountTypesBalance;
    }

    public void setAdReportVO(final AdReportVO adReportVO) {
        this.adReportVO = adReportVO;
    }

    public void setCurrencies(final List<Currency> currencies) {
        this.currencies = currencies;
    }

    public void setGivenReferences(final Map<Reference.Level, Integer> givenReferences) {
        this.givenReferences = givenReferences;
    }

    public void setGroupMemberCount(final Map<MemberGroup, Integer> groupMemberCount) {
        this.groupMemberCount = groupMemberCount;
    }

    public void setInvoicesSummaries(final Map<Currency, Map<InvoiceSummaryType, TransactionSummaryVO>> invoicesSummaries) {
        this.invoicesSummaries = invoicesSummaries;
    }

    public void setMemberAccountTypesBalance(final Map<MemberAccountType, BigDecimal> memberAccountTypesBalance) {
        this.memberAccountTypesBalance = memberAccountTypesBalance;
    }

    public void setNumberActiveMembers(final Integer numberActiveMembers) {
        this.numberActiveMembers = numberActiveMembers;
    }

    public void setOpenLoansSummary(final Map<Currency, TransactionSummaryVO> openLoansSummary) {
        this.openLoansSummary = openLoansSummary;
    }

    public void setSystemAccountTypesBalance(final Map<SystemAccountType, BigDecimal> systemAccountTypesBalance) {
        this.systemAccountTypesBalance = systemAccountTypesBalance;
    }

}
