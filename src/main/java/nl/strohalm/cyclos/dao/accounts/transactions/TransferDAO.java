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
package nl.strohalm.cyclos.dao.accounts.transactions;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

import nl.strohalm.cyclos.dao.BaseDAO;
import nl.strohalm.cyclos.dao.InsertableDAO;
import nl.strohalm.cyclos.dao.UpdatableDAO;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.Account;
import nl.strohalm.cyclos.entities.accounts.Currency;
import nl.strohalm.cyclos.entities.accounts.Rated;
import nl.strohalm.cyclos.entities.accounts.external.ExternalTransfer;
import nl.strohalm.cyclos.entities.accounts.transactions.AuthorizationLevel;
import nl.strohalm.cyclos.entities.accounts.transactions.Payment;
import nl.strohalm.cyclos.entities.accounts.transactions.Transfer;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferQuery;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferType;
import nl.strohalm.cyclos.entities.accounts.transactions.TransfersAwaitingAuthorizationQuery;
import nl.strohalm.cyclos.entities.exceptions.DaoException;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.members.Operator;
import nl.strohalm.cyclos.entities.reports.StatisticalDTO;
import nl.strohalm.cyclos.services.stats.general.KeyDevelopmentsStatsPerMonthVO;
import nl.strohalm.cyclos.utils.Pair;
import nl.strohalm.cyclos.utils.Period;

/**
 * Data access object interface for transfers
 * @author rafael
 */
public interface TransferDAO extends BaseDAO<Transfer>, InsertableDAO<Transfer>, UpdatableDAO<Transfer> {

    /**
     * Sums the balance diff within the given period (optionally null, summing all transfers)
     */
    BigDecimal balanceDiff(Account account, Period period);

    /**
     * sums the balance diff for the given account at the moment of the processing of the specified transfer. You can specify if the transfer itself
     * is included or not.
     * @param account
     * @param period this period is used to specify the start date of the difference period. This is usually the official date/time of the last
     * ClosedAccountBalance. The begin date of the period is inclusive, and the endDate of the period will be ignored, as the transfer parameter will
     * be used to specify the end date.<br>
     * However, you can decide to include or exclude the transfer itself into the balance, by setting the period's <code>inclusiveEnd</code> flag.
     * This flag is false by default, meaning that the balance diff is up to the moment when the specified transfer is about to be processed. If the
     * flag is true, the balance is included in the balanceDiff.
     * @param transfer the transfer itself is not included in the balance diff
     * @return
     * @author rinke
     */
    BigDecimal balanceDiff(Account account, Period period, Transfer transfer);

    /**
     * gets the sum of all chargebacks and their original chargedback transfers. This is of course always zero for the present date, but it will not
     * be zero for a time between the original transfer and its chargeback.
     * @param account
     * @param period null is allowed, though this is quite meaningless, as it will then by definition return zero.
     * @return
     * @author rinke
     */
    BigDecimal getChargebackBalance(Account account, Period period);

    /**
     * gets the sum of all chargebacks and their original chargedback transfers. This is of course always zero for the present date, but it will not
     * be zero for a time between the original transfer and its chargeback.
     * @param account
     * @param transfer the transfer's processDate is used as the (inclusive) date. If the transfer itself is included is determined by the
     * <code>inclusive</code> parameter.
     * @param inclusive a boolean determining if the transfer itself is included in the balance.
     * @return
     * @author rinke
     */
    BigDecimal getChargebackBalance(Account account, Transfer transfer, boolean inclusive);

    /**
     * List of sums of incoming transactions amounts per member. Used by Activity: all using GrossProduct. <b>Important NOTE:</b> Beware that this
     * method only gets the members who were actually trading. Members with NO incoming trades are NOT included. If you need those, you need to
     * manually add them to the list.
     * @return a List of Pair objects where the first element is the member and the second is the gross product of the member (sum of incoming
     * transactions).
     * @author rinke
     */
    List<Pair<Member, BigDecimal>> getGrossProductPerMember(StatisticalDTO dto) throws DaoException;

    /**
     * gets the gross product summed over all members in a list, where each element in the list specifies a specific month in the period.
     * @return a list with <code>KeyDevelopmentsStatsPerMonthVO</code>s, containing for each element the gross products, the year and the month.
     * @author rinke
     */
    List<KeyDevelopmentsStatsPerMonthVO> getGrossProductPerMonth(final StatisticalDTO dto);

    /**
     * List of numbers of incoming transactions per member. Used by Activity Stats > all which use number of trans, % not trading
     * @return a List of Pair objects where the first element is the member and the second is the number of transactions
     * @param dto parameters that filter the query
     * @author rinke
     */
    List<Pair<Member, Integer>> getNumberOfTransactionsPerMember(StatisticalDTO dto) throws DaoException;

    /**
     * gets the number of transactions summed over all members in a list, where each element in the list specifies a specific month in the period.
     * @return a list with <code>KeyDevelopmentsStatsPerMonthVO</code>s, containing for each element the gross products, the year and the month.
     * @author rinke
     */
    List<KeyDevelopmentsStatsPerMonthVO> getNumberOfTransactionsPerMonth(final StatisticalDTO dto);

    /**
     * gets the oldest transfer processDate available.
     * @param currency may be null
     * @param account may be null
     * @param period may be null
     * @param excludeChargebacks if true, chargebacks are ignored
     * @return
     * @author rinke
     */
    Calendar getOldestTransfer(Currency currency, Account account, Period period, boolean excludeChargebacks);

    /**
     * List of sums of outgoing transaction amounts (payments) per member. Used by Taxes Stats. <b>Important NOTE:</b> Beware that this method only
     * gets the members who were actually trading. Members with NO incoming trades are NOT included. If you need those, you need to manually add them
     * to the list.
     * @param dto parameters that filter the query
     * @return a List of Pair objects where the first element is the member and the second is the sum of payments done by this member
     * @author rinke
     */
    List<Pair<Member, BigDecimal>> getPaymentsPerMember(StatisticalDTO dto) throws DaoException;

    /**
     * Sum of the amounts of the transactions. Used by Key Dev Stats > gross product
     * @param dto a StatisticalDTO object passing the query
     * @author rinke
     */
    BigDecimal getSumOfTransactions(StatisticalDTO dto) throws DaoException;

    /**
     * Calculates the sum of transactions there was on this SystemAccountType for any payments NOT belonging to the set of paymentFilters, during the
     * period
     * @author rinke
     */
    BigDecimal getSumOfTransactionsRest(TransferQuery query);

    /**
     * gets a list with transaction amounts and their id's. There is no separate query for the number of transactions; just use this one and the size
     * of the resulting list is the number of transactions. This is more efficient than a separate query.
     * @author rinke
     */
    List<Number> getTransactionAmounts(final StatisticalDTO dto);

    /**
     * Returns the total amount of transfers at the given date from the given account, with the given transfer type. Only payments in the following
     * status are considered: {@link Payment.Status#PROCESSED}, {@link Payment.Status#PENDING} and {@link Payment.Status#SCHEDULED}.
     */
    BigDecimal getTransactionedAmountAt(Calendar date, Account account, TransferType transferType);

    /**
     * Returns the total amount of transfers today from the given account, with the given transfer type, performed by the given operator. Only
     * payments in the following status are considered: {@link Payment.Status#PROCESSED}, {@link Payment.Status#PENDING} and
     * {@link Payment.Status#SCHEDULED}.
     */
    BigDecimal getTransactionedAmountAt(Calendar date, Operator operator, Account account, TransferType transferType);

    /**
     * gets a List of Transfers, depending on the parameters. Each parameter may be null, in which case it is ignored. Transfers which have not been
     * processed (so with <code>processDate</code> is null) are always excluded.
     * @param currency if there is only one currency, it is advised to pass null here, as this makes the query more efficient.
     * @param account
     * @param period the period over which the transfers are requested. Remember to set Period.setUseTime(true) in case you want the period's begin
     * and end date not rounded to whole days. If having set Period.setUseTime(true), then the begin date is INCLUSIVE, and the end date is EXCLUSIVE,
     * unless otherwise speficied at the period.
     * @param sinceTransfer Any transfer processed after this transfer will be included. The method first checks the <code>processDate</code> of the
     * parameter transfer: any transfer with a newer <code>processDate</code> is returned, and transfers with a <code>processDate</code> equal to
     * <code>sinceTransfer</code>'s <code>processDate</code> are only included if their <code>id</code> is greater than the <code>sinceTransfer</code>
     * 's <code>id</code>. This gives a reasonable chance that the method gets all transfers which were processed after <code>sinceTransfer</code>;
     * note however that this is NO guarantee. For example, an authorized payment which was processed 0.1 seconds after <code>sinceTransfer</code>
     * will be missed, as their stored dates in mysql are equal, but the authorized payment's id will be much lower.
     * @param includeChargebacks
     * @param maxResults an Integer specifying the number of entities returned. If null it is ignored.
     * @return a List of Transfers, ordered by processDate and id.
     * @author rinke
     */
    List<Transfer> getTransfers(Currency currency, Account account, Period period, Transfer sinceTransfer, boolean includeChargebacks, Integer maxResults);

    /**
     * Returns whether the given account has at least a transfer
     */
    boolean hasTransfers(Account account);

    /**
     * Loads a transfer generated by the client and with the specified trace number and generated by the client id
     */
    Transfer loadTransferByTraceNumber(String traceNumber, Long clientId, Relationship... fetch);

    /**
     * Returns simple transfers VOs for a given account and period, ordering results by date ascending
     */
    List<SimpleTransferVO> paymentVOs(Account account, Period period) throws DaoException;

    /**
     * Searches for transfers. If no entity can be found, returns an empty list. If any exception is thrown by the underlying implementation, it
     * should be wrapped by a DaoException.
     * 
     * <p>
     * The condition specified by <code>query.getMember()</code> should only be taken in account when <code>query.getOwner() != null</code> and
     * <code>query.getType() != null</code>.
     */
    List<Transfer> search(TransferQuery query) throws DaoException;

    /**
     * Searches for transfers awaiting authorization
     */
    List<Transfer> searchTransfersAwaitingAuthorization(TransfersAwaitingAuthorizationQuery query);

    /**
     * Updates the transfer with authorization data
     * @param rates may be null if rates not enabled. Otherwise, must contain an emissionDate and/or an expirationDate. Rates can only be updated if
     * the processDate is not null. The method checks on this.
     */
    Transfer updateAuthorizationData(Long id, Transfer.Status status, AuthorizationLevel nextLevel, Calendar processDate, final Rated rates);

    /**
     * Updates the transfer with the chargeback
     */
    Transfer updateChargeBack(Transfer transfer, Transfer chargeback);

    /**
     * Updates the transfer with the external transfer
     */
    Transfer updateExternalTransfer(Long id, ExternalTransfer externalTransfer);

    /**
     * Updates the transfer with the status
     */
    Transfer updateStatus(Long id, Payment.Status status);

    /**
     * Updates the transfer with the generated transaction number
     */
    Transfer updateTransactionNumber(Long id, String transactionNumber);
}
