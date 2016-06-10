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
package nl.strohalm.cyclos.services.accounts.rates;

import java.math.BigDecimal;
import java.util.Calendar;

import nl.strohalm.cyclos.entities.accounts.Account;
import nl.strohalm.cyclos.entities.accounts.fees.transaction.SimpleTransactionFee.ARateRelation;
import nl.strohalm.cyclos.entities.accounts.fees.transaction.TransactionFee;
import nl.strohalm.cyclos.entities.accounts.transactions.Transfer;
import nl.strohalm.cyclos.services.transactions.GrantLoanDTO;
import nl.strohalm.cyclos.services.transactions.TransferDTO;
import nl.strohalm.cyclos.utils.validation.Validator;

/**
 * Local interface. It must be used only from other services.
 */
public interface RateServiceLocal extends RateService {

    /**
     * does the validation for fields when configuring an A-rated fee.
     */
    Validator applyARateFieldsValidation(Validator validator, ARatedFeeDTO dto, ARateRelation arateRelation);

    /**
     * applies the rates for a newly created loan
     */
    RatesResultDTO applyLoan(TransferDTO transferDto, GrantLoanDTO params);

    /**
     * applies the needed rate changes which result from the transfer to the account. These changes mean:
     * <ul>
     * <li>rate of the transfer is calculated
     * <li>if needed, the from-account's rateBalanceCorrection is saved to the RateSaver object, ready for future persisting.
     * <li>this transfer rate is merged with the to-account's rate.
     * <li>the result is saved to the resulting RateSaver object, ready for future persisting.
     * </ul>
     * NO changes are made to the transfer itself, nor to the accounts. <br>
     * Note that this method requires that all accounts (both from and to) are locked. The calling method should take care of that.
     * @param transfer
     * @return RateSaver object with the transfer's rate, and with a que of AccountRates entities to be persisted. These can be persisted as soon as
     * the tranfer itself is persisted. If the specific rate is null, it's corresponding field on the result will be null. If no rates enabled, it
     * will have all fields set to null.
     */
    RatesToSave applyTransfer(Transfer transfer);

    /**
     * performs correcting of the rates in a chargeback transaction. This is to be called after all other actions are done, that is: after the
     * original transfer is marked as chargedback.
     * <p>
     * Chargebacks are treated as transactions which are returned/undone. This leads to the following strategy:
     * <ul>
     * <li>the chargeback transfer is a transfer in the same direction as the original transfer, but with a negated transfer amount. Rates are not
     * applied as usual in such a case, but treated in an exceptional way (see next point). Just applying rates with a negated transfer amount would
     * lead to deviations in case the accounts have been used in the meantime.
     * <li>both involved accounts get their rates recalculated from the date of the original chargedback transfer.
     * <li>no deeper levels correction; only correction on the primary involved accounts. This means that any other accounts than the original
     * transfer's from and to account will not be updated for any rates that might have been changed due to the chargeback. So there are no side
     * effects for other accounts, even if such an other account was involved in a transfer from or to the original from and to accounts, that took
     * place between an original transfer and it's chargeback.
     * <li>Other transfers are NOT affected by the chargeback. A Transfer is considered an agreement under the then valid circumstances. This means
     * that a transfer's rates are fixed from the moment the tranfer is processed, and that these rates are NOT changed as a result of earlier
     * chargebacks, even if the account from which the transfer was paid had it's rates changed as a result of a chargeback.
     * </ul>
     * @param original The transfer to be chargedback
     * @param chargeback the transfer that was chargedback
     */
    void chargeback(Transfer original, Transfer chargeback);

    /**
     * converts the rate from raw Calendar rates to BigDecimal Rates
     * @param ratesDTO a RatesDTO which needs to have either emissionDate, expirationDate or both set. It must also have a date set, and in case of
     * D-rate, a Currency
     * @return sets the calculated BigDecimal rates to a new RatesResultDTO object. If a particular rate in the input is null, the corresponding field
     * in the output will be null too.
     */
    RatesResultDTO dateToRate(RatesDTO ratesDTO);

    /**
     * gets the precentage of the fee with chargeType A_RATED or MIXED_A_D_RATES.
     * @param fee
     * @param rawARate a Calendar being the raw A-rate to be used. May NOT be null.
     * @param rawDRate a Calendar being the raw D-rate to be used. May be null in case of A_RATED; in that case the param is not used.
     * @param date the processdate
     * @return the percentage of the fee charge.
     */
    BigDecimal getARatedFeePercentage(TransactionFee fee, Calendar rawARate, Calendar rawDRate, Calendar date);

    /**
     * calculates the conversion result according to the D-rate.
     * @param setOfUnits a RatesDTO. Needs to have set:
     * <ul>
     * <li>the currency
     * <li>the amount to be converted
     * <li>the expiration date.
     * <li>the date
     * </ul>
     * @return null if D-rate is not enabled. Otherwise, the sum the member gets from converting the original amount.
     */
    BigDecimal getDRateConversionResult(RatesDTO setOfUnits);

    /**
     * gets a validator for the currency's RateParameters.
     */
    Validator getRateParametersValidator(Validator validator, WhatRate whatRate);

    /**
     * gets all the rates, plus the virtualRatedBalance at the given date. If the date in the param is null, it gets the present rates.
     * <ul>
     * <li>if no rates are enabled on the requested date, it returns a RatesResultDTO with all rate fields set to null.
     * <li>If some rate (A, D, I) is not enabled on the requested date, it's corresponding field on the returned result will be null.
     * <li>rates are updated with transfers which happened between the last available AccountRatesEntity and the requested date.
     * <li>If a null rate is encountered while the rate is enabled, that rate is initialized. This also counts for rateBalanceCorrection/VRB. An
     * exception is the I-rate, as this cannot be initialized. In this case an alert is fired
     * <li>If the account is an unlimited type, the virtualRatedBalance is set to zero. See next item for consequences.
     * <li>If the virtualRatedBalance is zero, rates are set to their creation value.
     * </ul>
     * 
     * @param a RatesDTO with the following set:
     * <ul>
     * <li>date - may be null, in which case the present rates are retrieved, and the history is not looped
     * <li>account
     * <li>amount (balance)
     * </ul>
     * @return a RatesResultDTO containing enabled rates and VRB. Rates are included as raw rates and as BigDecimals.
     */
    RatesResultDTO getRates(RatesDTO dto);

    /**
     * gets all the Rates for a transfer from the from-account. Does this by checking if the account has sufficient balance. If not, it creates extra
     * units by going negative. Created units get a rate as specifically defined for newly created units. Note that the method does NOT check if the
     * lower account limit is violated; this is a task to be done by the calling method. It either doesn't set a new ratebalancecorrection on the from
     * account if it finds out new units have to be created.
     * 
     * @param from the from Account
     * @param amount the amount to be taken from the from account
     * @param date the date of the (supposed) transfer; if null, this is now.
     * 
     * @return a RatesResultDTO containing the raw rates as well as their BigDecimal numerical equivalents to be used for this transfer. If new units
     * had to be created because of this account going negative, then the DTO also contains the new value for rateBalanceCorrection. Calling methods
     * should check on this, and save this if appropriate. Returns null if the rate is not enabled on the currency.
     * @throws IllegalArgumentException in case of payments in past, that is, if transfers exist for this account after or ON the requested date. NOTE
     * that the method also raises the exception in case of an already existing, processed transfer with the exact same date/time.
     */
    RatesResultDTO getRatesForTransferFrom(Account from, BigDecimal amount, Calendar date);

    /**
     * checks if any of the rates is enabled on the given date.
     * @param account
     * @param date
     * @return true if any rate enabled
     */
    boolean isAnyRateEnabled(final Account account, final Calendar date);

    /**
     * persists the rates in the argument.
     * @param rates
     */
    void persist(RatesToSave rates);

    /**
     * converts the rate from BigDecimals to raw Calendar dates. As I-rate doesn't have a raw calendar date, i-rate is ignored.
     * @param ratesResultDTO a RatesResultDTO which needs to have either emissionDate, expirationDate or both set. It also needs to have a date set.
     * @return sets the calculated Calendar raw rates to a new RatesResultDTO object. If a particular rate in the input is null, the corresponding
     * field in the output will be null too.
     */
    RatesResultDTO rateToDate(final RatesResultDTO params);

}
