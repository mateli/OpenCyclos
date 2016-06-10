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
package nl.strohalm.cyclos.services.accounts;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.Account;
import nl.strohalm.cyclos.entities.accounts.AccountOwner;
import nl.strohalm.cyclos.entities.accounts.AccountStatus;
import nl.strohalm.cyclos.entities.accounts.MemberAccount;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferType;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.members.MemberTransactionDetailsReportData;
import nl.strohalm.cyclos.entities.members.MemberTransactionSummaryReportData;
import nl.strohalm.cyclos.entities.members.MembersTransactionsReportParameters;
import nl.strohalm.cyclos.services.Service;
import nl.strohalm.cyclos.webservices.model.AccountStatusVO;
import nl.strohalm.cyclos.webservices.model.MemberAccountVO;

/**
 * Account service: manages accounts and account types related operations and calculations broker commission, credit limits, transaction volumes etc.
 * @author luis
 */
public interface AccountService extends Service {

    /**
     * Returns whether the logged user is allowed to view details of the given account
     */
    boolean canView(Account account);

    /**
     * Returns whether the logged user is allowed to view account information for the given owner
     */
    boolean canViewAccountsOf(AccountOwner owner);

    /**
     * Returns whether the logged user is allowed to view authorized payments for the given owner
     */
    boolean canViewAuthorizedInformation(AccountOwner owner);

    /**
     * Returns the account that matches the owner and type
     */
    Account getAccount(AccountDTO params, Relationship... fetch);

    /**
     * Returns all the accounts of the given owner
     */
    List<? extends Account> getAccounts(AccountOwner owner, Relationship... fetch);

    /**
     * Returns a Set with accounts belonging to the allowedTTs AND to the member
     * @param member the members whose accounts are checked on this
     * @param allowedTTs the transfer types to be checked
     * @param direction a TransferType.Direction enum.
     * <ul>
     * <li>If FROM, only accounts from which the checked transfer types come from are included.
     * <li>If TO, only accounts to which the checked transfer types go are included.
     * <li>If BOTH, both from and to accounts of the transfer types are included.
     * @return a Set with accounts belonging to the member, and containing the transfer types in allowedTTs.
     */
    Set<? extends Account> getAccountsFromTTs(final Member member, final Collection<TransferType> allowedTTs, final TransferType.Direction direction);

    /**
     * Returns the account balance at a given time point. When the date is null, returns the current balance. Note that the date is always inclusive.
     * If you need an exclusive date, use AccountServiceLocal.getExclusiveBalance(AccountDateDTO)
     */
    BigDecimal getBalance(AccountDateDTO params);

    /**
     * Return the credit limit for a given account
     */
    BigDecimal getCreditLimit(AccountDTO params);

    /**
     * Retrieves the current credit limit
     */
    CreditLimitDTO getCreditLimits(Member member);

    /**
     * Returns an accountStatusVO for the given account status id.
     */
    AccountStatusVO getCurrentAccountStatusVO(AccountDTO accountDTO);

    /**
     * Returns the current account status for the given account
     */
    AccountStatus getCurrentStatus(AccountDTO account);

    /**
     * Returns the default account for the current member, but only if there is a logged member. Throws {@link EntityNotFoundException} otherwise, or
     * if the member has no accounts
     */
    MemberAccount getDefaultAccount();

    /**
     * gets the default account of the member from a list of allowed accounts. If the default account is not in this list, it gets the first account
     * in the list. If the list is empty, it returns null.
     */
    Account getDefaultAccountFromList(Member member, final List<Account> allowedAccounts);

    /**
     * Returns a memberAccountVO for the given member account id.
     */
    MemberAccountVO getMemberAccountVO(Long memberAccountId);

    /**
     * gets the AccountStatus with rates included. The normal getStatus methods don't bother getting rates, as it involves several other db tables to
     * be checked, and in 9 out of 10 cases the retrieved status doesn't do anything with them. So use this as an alternative to getCurrentStatus or
     * getStatus in case rates do matter. For the rest, works exactly the same as getStatus.
     * @param account
     * @param date may be null, in which the current date is used.
     */
    AccountStatus getRatedStatus(Account account, Calendar date);

    /**
     * 
     * @return true if the member has associated accounts
     */
    boolean hasAccounts(Member member);

    /**
     * Loads an account by id
     */
    <T extends Account> T load(final Long id, final Relationship... fetch);

    /**
     * Runs a member report with transactions details
     */
    Iterator<MemberTransactionDetailsReportData> membersTransactionsDetailsReport(MembersTransactionsReportParameters params);

    /**
     * Runs a member report with transactions summaries
     */
    Iterator<MemberTransactionSummaryReportData> membersTransactionsSummaryReport(MembersTransactionsReportParameters params);

    /**
     * Sets the new credit limit
     */
    void setCreditLimit(Member member, CreditLimitDTO limits);

    /**
     * Validates a credit limit DTO
     */
    void validate(Member member, CreditLimitDTO creditLimit);

}
