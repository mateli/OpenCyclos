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

import java.util.List;

import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.fees.account.AccountFee;
import nl.strohalm.cyclos.entities.accounts.fees.account.AccountFeeLog;
import nl.strohalm.cyclos.entities.accounts.fees.account.AccountFeeLogDetailsDTO;
import nl.strohalm.cyclos.entities.accounts.fees.account.AccountFeeLogQuery;
import nl.strohalm.cyclos.entities.accounts.fees.account.AccountFeeQuery;
import nl.strohalm.cyclos.entities.accounts.fees.account.MemberAccountFeeLog;
import nl.strohalm.cyclos.entities.accounts.fees.account.MemberAccountFeeLogQuery;
import nl.strohalm.cyclos.services.Service;

/**
 * Service interface for account fees.
 * @author luis
 */
public interface AccountFeeService extends Service {

    /**
     * Charges the specified manual account fee
     */
    void chargeManual(AccountFee accountFee);

    /**
     * Returns the details for an account fee log
     */
    AccountFeeLogDetailsDTO getLogDetails(Long id);

    /**
     * Loads the account fee, fetching the specified relationships
     */
    AccountFee load(Long id, Relationship... fetch);

    /**
     * Loads the account fee log, fetching the specified relationships
     */
    AccountFeeLog loadLog(Long id, Relationship... fetch);

    /**
     * Recharges the failed charges in the given log
     */
    void rechargeFailed(AccountFeeLog accountFeeLog);

    /**
     * Removes the specified account fees, unless any of them has been already charged
     * @return The number of removed account fees
     */
    int remove(Long... ids);

    /**
     * Save the specified account fee
     */
    AccountFee save(AccountFee accountFee);

    /**
     * Search account fees
     */
    List<AccountFee> search(AccountFeeQuery query);

    /**
     * Search account fee logs
     */
    List<AccountFeeLog> searchLogs(AccountFeeLogQuery query);

    /**
     * Searches for charged members in an account fee log
     */
    List<MemberAccountFeeLog> searchMembers(MemberAccountFeeLogQuery query);

    /**
     * Validate the specified account fee. There are some dependent validations:
     * <ul>
     * <li>When paymentDirection == TO_MEMBER then runMode must beMANUAL, chargeMode cannot be VOLUME_PERCENTAGE or NEGATIVE_VOLUME_PERCENTAGE</li>
     * <li>When paymentDirection == TO_SYSTEM then invoiceMode is required</li>
     * <li>When runMode == SCHEDULED then recurrence, day and hour are all required</li>
     * </ul>
     */
    void validate(AccountFee accountFee);

}
