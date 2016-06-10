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
import java.util.Map;

import nl.strohalm.cyclos.entities.accounts.AccountType;
import nl.strohalm.cyclos.entities.ads.Ad;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.members.Reference;
import nl.strohalm.cyclos.utils.DataObject;

public class MembersReportVO extends DataObject {

    private static final long             serialVersionUID = -5332217653629585693L;

    private Member                        member;
    private Map<Ad.Status, Integer>       ads;
    private Map<Reference.Level, Integer> givenReferences;
    private Map<Reference.Level, Integer> receivedReferences;
    private Map<AccountType, BigDecimal>  accountsCredits;
    private Map<AccountType, BigDecimal>  accountsBalances;
    private Map<AccountType, BigDecimal>  accountsUpperCredits;

    public Map<AccountType, BigDecimal> getAccountsBalances() {
        return accountsBalances;
    }

    public Map<AccountType, BigDecimal> getAccountsCredits() {
        return accountsCredits;
    }

    public Map<AccountType, BigDecimal> getAccountsUpperCredits() {
        return accountsUpperCredits;
    }

    public Map<Ad.Status, Integer> getAds() {
        return ads;
    }

    public Map<Reference.Level, Integer> getGivenReferences() {
        return givenReferences;
    }

    public Member getMember() {
        return member;
    }

    public Map<Reference.Level, Integer> getReceivedReferences() {
        return receivedReferences;
    }

    public void setAccountsBalances(final Map<AccountType, BigDecimal> accountsBalances) {
        this.accountsBalances = accountsBalances;
    }

    public void setAccountsCredits(final Map<AccountType, BigDecimal> accountsCredits) {
        this.accountsCredits = accountsCredits;
    }

    public void setAccountsUpperCredits(final Map<AccountType, BigDecimal> accountsUpperCredits) {
        this.accountsUpperCredits = accountsUpperCredits;
    }

    public void setAds(final Map<Ad.Status, Integer> ads) {
        this.ads = ads;
    }

    public void setGivenReferences(final Map<Reference.Level, Integer> givenReferences) {
        this.givenReferences = givenReferences;
    }

    public void setMember(final Member member) {
        this.member = member;
    }

    public void setReceivedReferences(final Map<Reference.Level, Integer> receivedReferences) {
        this.receivedReferences = receivedReferences;
    }

}
