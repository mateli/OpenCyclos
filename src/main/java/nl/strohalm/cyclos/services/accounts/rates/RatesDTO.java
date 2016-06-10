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
import nl.strohalm.cyclos.entities.accounts.Currency;
import nl.strohalm.cyclos.entities.accounts.Rated;
import nl.strohalm.cyclos.utils.DataObject;

/**
 * DTO for rates.
 * 
 * @author rinke
 */
public class RatesDTO extends DataObject implements Rated {

    private static final long serialVersionUID = 1286727551181755315L;

    /**
     * pseudo-constructor for passing a set of units to merge. See {@link RateHandlerImpl#createSetOfUnitsForMerge}
     */
    public static RatesDTO createSetOfUnitsForMerge(final BigDecimal amount, final BigDecimal rate, final Currency currency) {
        final RatesDTO instance = new RatesDTO();
        instance.setAmount(amount);
        instance.setGeneralRate(rate);
        instance.setCurrency(currency);
        return instance;
    }

    /**
     * pseudo-constructor for passing a set of units to merge. Returned instance contains the amount, the raw rate as Calendar (in field
     * generalRawRate), and the currency.
     * @param amount the amount of the set of units.
     * @param rawRate the raw rate as Calendar belonging to this set of units.
     * @param currency the currency of the amount.
     * @return an instance of RatesDTO containing all necessary data.
     */
    public static RatesDTO createSetOfUnitsForMerge(final BigDecimal amount, final Calendar rawRate, final Currency currency) {
        final RatesDTO instance = new RatesDTO();
        instance.setAmount(amount);
        instance.setGeneralRawRate(rawRate);
        instance.setCurrency(currency);
        return instance;
    }

    private Calendar   emissionDate;
    private Calendar   expirationDate;
    private BigDecimal iRate;
    private Calendar   generalRawRate;
    private BigDecimal generalRate;
    private Calendar   date;
    private Account    account;
    private BigDecimal amount;
    private BigDecimal rateBalanceCorrection;
    private Currency   currency;

    public Account getAccount() {
        return account;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public Calendar getDate() {
        return date;
    }

    @Override
    public Calendar getEmissionDate() {
        return emissionDate;
    }

    @Override
    public Calendar getExpirationDate() {
        return expirationDate;
    }

    public BigDecimal getGeneralRate() {
        return generalRate;
    }

    public Calendar getGeneralRawRate() {
        return generalRawRate;
    }

    @Override
    public BigDecimal getiRate() {
        return iRate;
    }

    public BigDecimal getRateBalanceCorrection() {
        return rateBalanceCorrection;
    }

    public void setAccount(final Account account) {
        this.account = account;
    }

    public void setAmount(final BigDecimal amount) {
        this.amount = amount;
    }

    public void setCurrency(final Currency currency) {
        this.currency = currency;
    }

    public void setDate(final Calendar date) {
        this.date = date;
    }

    public void setEmissionDate(final Calendar emissionDate) {
        this.emissionDate = emissionDate;
    }

    public void setExpirationDate(final Calendar expirationDate) {
        this.expirationDate = expirationDate;
    }

    public void setGeneralRate(final BigDecimal generalRate) {
        this.generalRate = generalRate;
    }

    public void setGeneralRawRate(final Calendar generalRawRate) {
        this.generalRawRate = generalRawRate;
    }

    public void setiRate(final BigDecimal iRate) {
        this.iRate = iRate;
    }

    public void setRateBalanceCorrection(final BigDecimal rateBalanceCorrection) {
        this.rateBalanceCorrection = rateBalanceCorrection;
    }

}
