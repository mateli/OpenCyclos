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
import java.util.Map;

import nl.strohalm.cyclos.entities.accounts.Account;
import nl.strohalm.cyclos.entities.accounts.AccountStatus;
import nl.strohalm.cyclos.entities.accounts.AccountType;
import nl.strohalm.cyclos.entities.accounts.InstallmentAmountReservation;
import nl.strohalm.cyclos.entities.accounts.MemberAccountType;
import nl.strohalm.cyclos.entities.accounts.PendingAuthorizationAmountReservation;
import nl.strohalm.cyclos.entities.accounts.ScheduledPaymentAmountReservation;
import nl.strohalm.cyclos.entities.accounts.TransferAuthorizationAmountReservation;
import nl.strohalm.cyclos.entities.accounts.transactions.PaymentFilter;
import nl.strohalm.cyclos.entities.accounts.transactions.ScheduledPayment;
import nl.strohalm.cyclos.entities.accounts.transactions.Transfer;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferAuthorization;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.services.transactions.TransactionSummaryVO;
import nl.strohalm.cyclos.utils.Period;

/**
 * Local interface. It must be used only from other services.
 */
public interface AccountServiceLocal extends AccountService {

    /**
     * Closes the account balances up to the previous day of the given time
     */
    void closeBalances(Calendar time);

    /**
     * Returns the number of accounts which are pending activation with the given group and type
     */
    int countPendingActivation(MemberGroup group, MemberAccountType accountType);

    /**
     * gets the balance at a point in time.
     * @param account
     * @param date If the date is null, the present balance will be returned.
     * @param inclusive if true, the date is inclusive: transfers happening at exact this date/time are included in the balance.
     * @param compensateChargebacks if true, the balance is corrected for chargebacks. That means that a balance is returned as if any chargebacked
     * transfer or any transfer which is a chargeback of another, didn't happen.
     * @author rinke
     * <p>
     * Note: may be integrated with getBalance() at a later time
     */
    BigDecimal getBalanceAtTimePoint(final Account account, final Calendar date, final boolean inclusive, final boolean compensateChargebacks);

    /**
     * gets the balance at the moment of the specified transfer processing.
     * @param account
     * @param transfer
     * @param compensateChargebacks if true, the balance is corrected for chargebacks. That means that a balance is returned as if any chargebacked
     * transfer or any transfer which is a chargeback of another, didn't happen.
     * @param inclusive if true, gets the balance AFTER the specified transfer was processed. If false, gets the balance at the moment the specified
     * transfer was about to be processed, so just before the processing.
     * @return
     * @author rinke
     * <p>
     * Note: may be integrated with getBalance() at a later time
     */
    BigDecimal getBalanceAtTransfer(final Account account, final Transfer transfer, final boolean compensateChargebacks, final boolean inclusive);

    /**
     * Returns the a summary of received broker commission
     */
    TransactionSummaryVO getBrokerCommissions(GetTransactionsDTO params);

    /**
     * Retrieves the credits summary for the given arguments
     */
    TransactionSummaryVO getCredits(GetTransactionsDTO params);

    /**
     * Retrieves the debits summary for the given arguments
     */
    TransactionSummaryVO getDebits(GetTransactionsDTO params);

    /**
     * gets the balance at a point in time, but with the date/time <strong>exclusive</strong>. That is: transfers happening exactly on the requested
     * date/time are NOT included in the resulting calculated balance. This behavior is in contrast to getBalance, which always returns the result
     * calculated with an <strong>inclusive</strong> requested date.
     * <p>
     * NOTE: this method was included late in a testing phase, just before a release. For future versions, it might be better to just have one
     * getBalance method, and have an extra inclusiveDate field in the AccountDateDTO (which is true by default).
     * @author rinke
     * <p>
     * Note: may be integrated with getBalance() at a later time
     */
    BigDecimal getExclusiveBalance(final AccountDateDTO params);

    /**
     * Returns the current account status for the given account, with date less than the given date (optionally null, returning the current status})
     */
    AccountStatus getStatus(Account account, Calendar date);

    /**
     * Retrieves transactions summary
     * @param member the owner of the account
     * @param accountType the account type
     * @param period the period
     * @param paymentFilters collection of the payment filters
     * @param credits true = credits, false = debits
     */
    Map<PaymentFilter, TransactionSummaryVO> getTransactionsSummary(Member member, AccountType accountType, Period period, Collection<PaymentFilter> paymentFilters, boolean credits);

    /**
     * Removes closed account balances for the given account after the given date
     */
    void removeClosedBalancesAfter(Account account, Calendar date);

    /**
     * Inserts an amount reservation for the given scheduled payment
     */
    ScheduledPaymentAmountReservation reserve(ScheduledPayment scheduledPayment);

    /**
     * Inserts an amount reservation for a transfer pending authorization
     */
    PendingAuthorizationAmountReservation reservePending(Transfer transfer);

    /**
     * Inserts an amount reservation to return the reserved amount for a payment authorization
     */
    TransferAuthorizationAmountReservation returnReservation(TransferAuthorization authorization, Transfer transfer);

    /**
     * Inserts an amount reservation to return the reserved amount for an installment of a scheduled payment that reserved the total amount
     */
    InstallmentAmountReservation returnReservationForInstallment(Transfer transfer);

}
