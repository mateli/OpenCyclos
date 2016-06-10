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
package nl.strohalm.cyclos.controls.reports.members.list;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.Account;
import nl.strohalm.cyclos.entities.accounts.AccountType;
import nl.strohalm.cyclos.entities.ads.Ad;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.members.Reference;
import nl.strohalm.cyclos.entities.reports.MembersReportVO;
import nl.strohalm.cyclos.services.accounts.AccountDTO;
import nl.strohalm.cyclos.services.accounts.AccountDateDTO;
import nl.strohalm.cyclos.services.accounts.AccountService;
import nl.strohalm.cyclos.services.ads.AdService;
import nl.strohalm.cyclos.services.elements.ReferenceService;
import nl.strohalm.cyclos.utils.Period;

public class MembersListReportVOIterator implements Iterator<MembersReportVO> {

    private final MembersListReportDTO    dto;
    private final Collection<AccountType> accountTypes;
    private final Iterator<Member>        membersIterator;

    private AdService                     adService;
    private ReferenceService              referenceService;
    private AccountService                accountService;

    MembersListReportVOIterator(final MembersListReportDTO dto, final Collection<AccountType> accountTypes, final List<Member> members) {
        this.dto = dto;
        this.accountTypes = accountTypes;
        membersIterator = members.iterator();
    }

    @Override
    public boolean hasNext() {
        return membersIterator.hasNext();
    }

    @Override
    public MembersReportVO next() {
        final MembersReportVO membersReportVO = new MembersReportVO();

        final Member member = membersIterator.next();
        membersReportVO.setMember(member);
        // Calendar date = null;
        Period period = null;
        if (dto.getPeriod() != null) {
            period = Period.exact(dto.getPeriod());
            period.setUseTime(true);
        }

        if (dto.isAds()) {
            final Map<Ad.Status, Integer> ads = adService.getNumberOfAds(period != null ? period.getEnd() : null, member);
            membersReportVO.setAds(ads);
        }

        if (dto.isGivenReferences()) {
            final Map<Reference.Level, Integer> givenReferences = referenceService.countReferencesHistoryByLevel(Reference.Nature.GENERAL, member, period, false);
            membersReportVO.setGivenReferences(givenReferences);
        }

        if (dto.isReceivedReferences()) {
            final Map<Reference.Level, Integer> receivedReferences = referenceService.countReferencesHistoryByLevel(Reference.Nature.GENERAL, member, period, true);
            membersReportVO.setReceivedReferences(receivedReferences);
        }

        if (dto.isAccountsInformation()) {
            final Map<AccountType, BigDecimal> accountsCreditsMap = new HashMap<AccountType, BigDecimal>();
            final Map<AccountType, BigDecimal> accountsUpperCreditsMap = new HashMap<AccountType, BigDecimal>();
            final Map<AccountType, BigDecimal> accountsBalancesMap = new HashMap<AccountType, BigDecimal>();
            for (final AccountType accountType : accountTypes) {
                final AccountDTO accountDto = new AccountDTO();
                accountDto.setType(accountType);
                accountDto.setOwner(member);

                try {
                    final Account account = accountService.getAccount(accountDto, (Relationship[]) null);
                    if (dto.isAccountsCredits()) {
                        final BigDecimal creditLimit = account.getCreditLimit();
                        accountsCreditsMap.put(accountType, creditLimit);
                    }
                    if (dto.isAccountsUpperCredits()) {
                        final BigDecimal upperCreditLimit = account.getUpperCreditLimit();
                        accountsUpperCreditsMap.put(accountType, upperCreditLimit);
                    }
                    if (dto.isAccountsBalances()) {
                        final BigDecimal balance = accountService.getBalance(new AccountDateDTO(account, dto.getPeriod()));
                        accountsBalancesMap.put(accountType, balance);
                    }
                } catch (final EntityNotFoundException e) {
                    // the member doesn't have the account of the given type, just ignore it
                }
            }
            membersReportVO.setAccountsCredits(accountsCreditsMap);
            membersReportVO.setAccountsUpperCredits(accountsUpperCreditsMap);
            membersReportVO.setAccountsBalances(accountsBalancesMap);
        }
        return membersReportVO;
    }

    @Override
    public void remove() {
    }

    @Inject
    public void setAccountService(final AccountService accountService) {
        this.accountService = accountService;
    }

    @Inject
    public void setAdService(final AdService adService) {
        this.adService = adService;
    }

    @Inject
    public void setReferenceService(final ReferenceService referenceService) {
        this.referenceService = referenceService;
    }

}
