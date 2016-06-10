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

import nl.strohalm.cyclos.dao.BaseDAO;
import nl.strohalm.cyclos.dao.InsertableDAO;
import nl.strohalm.cyclos.dao.UpdatableDAO;
import nl.strohalm.cyclos.entities.accounts.Account;
import nl.strohalm.cyclos.entities.accounts.AccountRates;
import nl.strohalm.cyclos.entities.accounts.Currency;
import nl.strohalm.cyclos.services.accounts.rates.WhatRate;
import nl.strohalm.cyclos.utils.Period;

/**
 * DAO interface for {@link AccountRates}. AccountRates are saved on daily basis. If there is already an existing AccountRates for a particular day,
 * that one is used an updated. If none exists for this day or future days, a new AccountRates entity is created. <br>
 * @author rinke
 */
public interface AccountRatesDAO extends BaseDAO<AccountRates>, InsertableDAO<AccountRates>, UpdatableDAO<AccountRates> {

    /**
     * Deletes all entities for this account which are in the give period.
     * @param account the account for which this is deleted.
     * @param period You can specify on the Period if the boundaries are to be included or excluded. Normally, you would only want to set the begin
     * date (and have it exclusive).
     */
    void bulkDelete(final Account account, final Period period);

    /**
     * performs a bulk update of all the AccountRates, and sets the fields specified in whatRate to null. This is typically performed before a
     * reinitialization.
     * @param currency the currency. It may be advisable to test beforehand if there's only one currency. If so, this param can be null, which will
     * make the query much more efficient.
     * @param period may be null, in which case the query will be much more efficient. You can set the period's inclusiveBegin and inclusiveEnd flags.
     * Normally you'd want to use this with setUseTime to true, only the start specified, and an inclusive begin.
     * @param whatRate speficies the fields to be set to null. RateBalanceCorrection is always set to null, except when nothing is specified in
     * whatRate. In that case the method just returns without doing anything.
     */
    void bulkUpdateWithNull(final Currency currency, final Period period, final WhatRate whatRate);

    /**
     * Gets the last AccountRates entity by date. That means:
     * <ul>
     * <li>Always gets the last AccountRate available for this account, regardless of what date that is from.
     * <li>If this AccountRate is from the same date as the date parameter, then it is returned.
     * <li>If this AccountRate is of a later date than the date parameter, then we deal with payments in past, so an exception is raised.
     * <li>If this AccountRate is of an earlier date than the date parameter, a new record should be created. A duplicate of the last known
     * AccountRate is made, and returned. Note that this duplicated is NOT persisted, and that the date is NOT reset to the requested date.
     * </ul>
     * So this one is only usefull if you want to persist/update it. Otherwise, use the other method (getRates).
     * 
     * @param account
     * @param date
     * @return a new day record of the account's rates, if needed.
     * @throws IllegalArgumentException if payment in past.
     */
    AccountRates getLatestRates(Account account, final Calendar date) throws IllegalArgumentException;

    /**
     * gets the last available non null rates for the specified rate and account, on the same day as the specified end of the period, or earlier. This
     * method is specifically for relooping all transfers in order to reinitialize a rate.<br>
     * The method checks if there is an entity available on the specified date. If the entity has a null rate for the rate specified in
     * <code>whatRate</code>, then it will return the previous entity (that is: the last available entity of an earlier date). If no entity with a non
     * null rate for the specified <code>whatRate</code> can be found, then it returns a new, blank entity with only null fields.
     * @param account
     * @param date rates should be of this day or earlier.
     * @param whatRate only rates specified in whatRate are considered; any other rates are completely ignored.
     * @return
     */
    AccountRates getLatestReinitializedRates(Account account, Calendar date, WhatRate whatRate);

    /**
     * gets the latest AccountRates entity.
     * @param account
     * @param date rates newer than this date are not considered. So this method may return an AccountRates entity which is not the latest available.
     * However, this parameter may be null, in which case the latest available should be returned. The date is INCLUSIVE, so rates with exactly the
     * same date are included.
     */
    AccountRates getRates(Account account, final Calendar date);

    /**
     * gets the entity only for the specified date. If there is not entity found on that date, then a new one is created, with fields copied from the
     * last available.
     * @param account
     * @param date
     * @return
     */
    AccountRates getTodaysRates(Account account, Calendar date);

}
