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
package nl.strohalm.cyclos.dao.accounts;

import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import nl.strohalm.cyclos.dao.BaseDAO;
import nl.strohalm.cyclos.dao.DeletableDAO;
import nl.strohalm.cyclos.dao.InsertableDAO;
import nl.strohalm.cyclos.dao.UpdatableDAO;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.Account;
import nl.strohalm.cyclos.entities.accounts.AccountOwner;
import nl.strohalm.cyclos.entities.accounts.AccountQuery;
import nl.strohalm.cyclos.entities.accounts.AccountType;
import nl.strohalm.cyclos.entities.accounts.MemberAccount;
import nl.strohalm.cyclos.entities.accounts.MemberAccount.Action;
import nl.strohalm.cyclos.entities.accounts.MemberAccountType;
import nl.strohalm.cyclos.entities.accounts.transactions.PaymentFilter;
import nl.strohalm.cyclos.entities.exceptions.DaoException;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.members.MemberTransactionDetailsReportData;
import nl.strohalm.cyclos.entities.members.MemberTransactionSummaryVO;
import nl.strohalm.cyclos.entities.members.MembersTransactionsReportParameters;
import nl.strohalm.cyclos.entities.settings.LocalSettings.MemberResultDisplay;
import nl.strohalm.cyclos.services.accounts.AccountDTO;
import nl.strohalm.cyclos.services.accounts.BulkUpdateAccountDTO;
import nl.strohalm.cyclos.services.accounts.GetTransactionsDTO;
import nl.strohalm.cyclos.services.transactions.TransactionSummaryVO;
import nl.strohalm.cyclos.utils.Period;
import nl.strohalm.cyclos.utils.query.IteratorList;

/**
 * Interface DAO for accounts
 * @author rafael
 */
public interface AccountDAO extends BaseDAO<Account>, InsertableDAO<Account>, UpdatableDAO<Account>, DeletableDAO<Account> {

    /**
     * bulk updates all accounts specified in dto.group with the specified creditLimits.
     */
    void bulkUpdateCreditLimites(BulkUpdateAccountDTO dto);

    /**
     * Returns the number of accounts with the given group, type and pending action
     */
    int countAccounts(MemberGroup group, MemberAccountType accountType, Action activate);

    /**
     * Returns the broker commissions for the given account
     * @throws EntityNotFoundException The expected account does not exists
     * @throws DaoException General error
     */
    TransactionSummaryVO getBrokerCommissions(GetTransactionsDTO params) throws EntityNotFoundException, DaoException;

    /**
     * Returns the credits for the given account
     * @throws EntityNotFoundException The expected account does not exists
     * @throws DaoException General error
     */
    TransactionSummaryVO getCredits(GetTransactionsDTO params) throws EntityNotFoundException, DaoException;

    /**
     * Returns the debits for the given account
     * @throws EntityNotFoundException The expected account does not exists
     * @throws DaoException General error
     */
    TransactionSummaryVO getDebits(GetTransactionsDTO params) throws EntityNotFoundException, DaoException;

    /**
     * Returns loans received for the given account
     * @throws EntityNotFoundException The expected account does not exists
     * @throws DaoException General error
     */
    TransactionSummaryVO getLoans(GetTransactionsDTO params) throws EntityNotFoundException, DaoException;

    /**
     * gets the first MemberAccount it can find with a non null action field.
     */
    MemberAccount getNextPendingProcessing();

    /**
     * Returns the credits with status=PENDING for the given account
     * @throws EntityNotFoundException The expected account does not exists
     * @throws DaoException General error
     */
    TransactionSummaryVO getPendingCredits(GetTransactionsDTO params) throws EntityNotFoundException, DaoException;

    /**
     * Returns the debits with status=PENDING for the given account
     * @throws EntityNotFoundException The expected account does not exists
     * @throws DaoException General error
     */
    TransactionSummaryVO getPendingDebits(GetTransactionsDTO params) throws EntityNotFoundException, DaoException;

    /**
     * Returns an iterator list with the differences of either balance or reserved amount for the given account, within the given period
     */
    IteratorList<AccountDailyDifference> iterateDailyDifferences(MemberAccount account, Period period);

    /**
     * Iterates the next 'n' accounts which don't have a closed balance for the given day
     */
    IteratorList<Account> iterateUnclosedAccounts(Calendar day, int maxResults);

    /**
     * Loads an account using an owner and type, with the specified fetch
     * @throws EntityNotFoundException The given account does not exists
     * @throws DaoException General error
     */
    Account load(AccountOwner owner, AccountType type, Relationship... fetch) throws EntityNotFoundException, DaoException;

    /**
     * Loads all the given accounts
     */
    List<Account> loadAll(List<AccountDTO> dtos, Relationship... fetch) throws EntityNotFoundException, DaoException;

    /**
     * creates member accounts in a bulk insert, and marks them for further activation.
     * {@link nl.strohalm.cyclos.services.accounts.MemberAccountHandler#ActivateAccountThread} will handle this.
     */
    void markForActivation(BulkUpdateAccountDTO dto);

    /**
     * marks accounts for deactivation. {@link nl.strohalm.cyclos.services.accounts.MemberAccountHandler#ActivateAccountThread} will handle this.
     */
    void markForDeactivation(MemberAccountType type, MemberGroup group);

    /**
     * Returns data for the transaction details report
     */
    Iterator<MemberTransactionDetailsReportData> membersTransactionsDetailsReport(MembersTransactionsReportParameters params);

    /**
     * Iterates either credits or debits on the transaction summary report
     */
    Iterator<MemberTransactionSummaryVO> membersTransactionSummaryReport(Collection<MemberGroup> memberGroups, PaymentFilter paymentFilter, Period period, boolean credits, MemberResultDisplay order);

    /**
     * Searches for accounts, ordering by the type name. If no entity can be found, returns an empty list. If any exception is thrown by the
     * underlying implementation, it should be wrapped by a DaoException.
     * 
     * @throws DaoException General error
     */
    List<? extends Account> search(AccountQuery query) throws DaoException;

}
