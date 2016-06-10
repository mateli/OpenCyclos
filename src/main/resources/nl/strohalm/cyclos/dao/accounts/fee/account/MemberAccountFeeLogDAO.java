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
package nl.strohalm.cyclos.dao.accounts.fee.account;

import java.util.List;

import nl.strohalm.cyclos.dao.BaseDAO;
import nl.strohalm.cyclos.dao.InsertableDAO;
import nl.strohalm.cyclos.dao.UpdatableDAO;
import nl.strohalm.cyclos.entities.accounts.fees.account.AccountFee;
import nl.strohalm.cyclos.entities.accounts.fees.account.AccountFeeLog;
import nl.strohalm.cyclos.entities.accounts.fees.account.MemberAccountFeeLog;
import nl.strohalm.cyclos.entities.accounts.fees.account.MemberAccountFeeLogQuery;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.settings.LocalSettings.MemberResultDisplay;
import nl.strohalm.cyclos.services.transactions.TransactionSummaryVO;

/**
 * DAO interface for member account fee logs
 * 
 * @author luis
 */
public interface MemberAccountFeeLogDAO extends BaseDAO<MemberAccountFeeLog>, InsertableDAO<MemberAccountFeeLog>, UpdatableDAO<MemberAccountFeeLog> {

    /**
     * Returns the number of members which where not charged, because the charge amount would be too low or because the freebase made them not being
     * charged
     */
    int countSkippedMembers(AccountFeeLog log);

    /**
     * Returns the accepted invoices summary for the given fee log
     */
    TransactionSummaryVO getAcceptedInvoicesSummary(AccountFeeLog log);

    /**
     * Returns the total invoices summary for the given log
     */
    TransactionSummaryVO getInvoicesSummary(AccountFeeLog log);

    /**
     * Returns the last log charged for the given member and account fee
     */
    AccountFeeLog getLastChargedLog(Member member, AccountFee fee);

    /**
     * Returns the transfers summary for the given log
     */
    TransactionSummaryVO getTransfersSummary(AccountFeeLog log);

    /**
     * Returns the MemberAccountFeeLog for the given fee log and member
     */
    MemberAccountFeeLog load(AccountFeeLog feeLog, Member member);

    /**
     * Returns the next members which have failed on the given log
     */
    List<Member> nextFailedToCharge(AccountFeeLog log, int count);

    /**
     * Returns the next members which should be charged by the given account fee log
     */
    List<Member> nextToCharge(AccountFeeLog log, int count);

    /**
     * Prepares the given account fee log to start charging. Populates the AccountFeeLog.pendingToCharge relationship with all affected members
     */
    int prepareCharge(AccountFeeLog log);

    /**
     * Removes the MemberAccountFeeLog with the given feeLog and member
     */
    void remove(AccountFeeLog feeLog, Member member);

    /**
     * Removes the member as pending to charge for the given feeLog
     */
    void removePendingCharge(AccountFeeLog feeLog, Member member);

    /**
     * Searches for charged members
     */
    List<MemberAccountFeeLog> search(MemberAccountFeeLogQuery query, MemberResultDisplay sort);

}
