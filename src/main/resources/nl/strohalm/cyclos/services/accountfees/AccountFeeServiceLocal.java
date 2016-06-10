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
package nl.strohalm.cyclos.services.accountfees;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

import nl.strohalm.cyclos.entities.accounts.MemberAccount;
import nl.strohalm.cyclos.entities.accounts.fees.account.AccountFee;
import nl.strohalm.cyclos.entities.accounts.fees.account.AccountFeeLog;
import nl.strohalm.cyclos.entities.accounts.fees.account.MemberAccountFeeLog;
import nl.strohalm.cyclos.entities.accounts.transactions.Invoice;
import nl.strohalm.cyclos.entities.accounts.transactions.Transfer;
import nl.strohalm.cyclos.entities.members.Member;

/**
 * Local interface. It must be used only from other services.
 */
public interface AccountFeeServiceLocal extends AccountFeeService {

    /**
     * Returns the amount that would be charged for the given account fee log and member
     */
    BigDecimal calculateAmount(AccountFeeLog feeLog, Member member);

    /**
     * Returns the amount which should be reserved on the given account for all volume account fees
     */
    BigDecimal calculateReservedAmountForVolumeFee(MemberAccount account);

    /**
     * Charges scheduled fees that should run on the given time, returning the number of fees that were charged
     */
    int chargeScheduledFees(Calendar time);

    /**
     * Returns the last account fee log
     */
    AccountFeeLog getLastLog(AccountFee acctFee);

    /**
     * Returns the next account fee log which can be charged
     */
    AccountFeeLog nextLogToCharge();

    /**
     * Returns the next batch of members which should be charged by the given account fee log
     */
    List<Member> nextMembersToCharge(AccountFeeLog feeLog);

    /**
     * Prepares the given account fee log to start charging
     */
    boolean prepareCharge(AccountFeeLog feeLog);

    /**
     * Removes the given member from the pending list of the given fee log
     */
    void removeFromPending(AccountFeeLog feeLog, Member member);

    /**
     * Save the specified account fee log
     */
    AccountFeeLog save(AccountFeeLog accountFee);

    /**
     * Sets the result of the charging as error for the given member and account fee log
     */
    MemberAccountFeeLog setChargingError(AccountFeeLog feeLog, Member member, BigDecimal amount);

    /**
     * Sets the result for the charging as success of the given member and account fee log
     */
    MemberAccountFeeLog setChargingSuccess(AccountFeeLog feeLog, Member member, BigDecimal amount, Transfer transfer, Invoice invoice);

}
