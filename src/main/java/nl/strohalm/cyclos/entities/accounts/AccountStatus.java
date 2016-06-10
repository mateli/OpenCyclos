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
package nl.strohalm.cyclos.entities.accounts;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.services.accounts.rates.RatesResultDTO;
import nl.strohalm.cyclos.utils.DataObject;
import nl.strohalm.cyclos.utils.conversion.UnitsConverter;

/**
 * Holds the current status for a given account
 * 
 * @author luis
 * @author rinke (rated stuff)
 */
public class AccountStatus extends DataObject implements Rated {

    private static final long serialVersionUID = -7864009622327627438L;
    private Account           account;
    private BigDecimal        balance          = BigDecimal.ZERO;
    private BigDecimal        reservedAmount   = BigDecimal.ZERO;
    private BigDecimal        creditLimit      = BigDecimal.ZERO;
    private BigDecimal        upperCreditLimit = BigDecimal.ZERO;
    private RatesResultDTO    rates;
    private Calendar          date;

    public Account getAccount() {
        return account;
    }

    public BigDecimal getaRate() {
        if (rates != null) {
            return rates.getaRate();
        }
        return null;
    }

    public BigDecimal getAvailableBalance() {
        return creditLimit == null ? null : balance.subtract(reservedAmount).add(creditLimit.abs());
    }

    public BigDecimal getAvailableBalanceWithoutCreditLimit() {
        return balance.subtract(reservedAmount);
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public BigDecimal getCreditLimit() {
        return creditLimit;
    }

    public Calendar getDate() {
        return date;
    }

    public BigDecimal getdRate() {
        if (rates != null) {
            return rates.getdRate();
        }
        return null;
    }

    @Override
    public Calendar getEmissionDate() {
        if (rates != null) {
            return rates.getEmissionDate();
        }
        return null;
    }

    @Override
    public Calendar getExpirationDate() {
        if (rates != null) {
            return rates.getExpirationDate();
        }
        return null;
    }

    @Override
    public BigDecimal getiRate() {
        if (rates != null) {
            return rates.getiRate();
        }
        return null;
    }

    public RatesResultDTO getRates() {
        return rates;
    }

    public BigDecimal getReservedAmount() {
        return reservedAmount;
    }

    public BigDecimal getUpperCreditLimit() {
        return upperCreditLimit;
    }

    public Map<String, Object> getVariableValues(final LocalSettings localSettings) {
        final Map<String, Object> values = new HashMap<String, Object>();
        final UnitsConverter unitsConverter = localSettings.getUnitsConverter(getAccount().getType().getCurrency().getPattern());
        values.put("balance", unitsConverter.toString(getBalance()));
        values.put("available_balance", unitsConverter.toString(getAvailableBalance()));
        values.put("reserved_amount", unitsConverter.toString(getReservedAmount()));
        values.put("credit_limit", unitsConverter.toString(getCreditLimit().abs().negate()));
        values.put("upper_credit_limit", unitsConverter.toString(getUpperCreditLimit()));
        return values;
    }

    public BigDecimal getVirtualRatedBalance() {
        if (rates != null) {
            return rates.getVirtualRatedBalance();
        }
        return null;
    }

    public void setAccount(final Account account) {
        this.account = account;
    }

    public void setBalance(final BigDecimal balance) {
        this.balance = balance;
    }

    public void setCreditLimit(final BigDecimal creditLimit) {
        this.creditLimit = creditLimit;
    }

    public void setDate(final Calendar date) {
        this.date = date;
    }

    public void setRates(final RatesResultDTO rates) {
        this.rates = rates;
    }

    public void setReservedAmount(final BigDecimal reservedAmount) {
        this.reservedAmount = reservedAmount;
    }

    public void setUpperCreditLimit(final BigDecimal upperCreditLimit) {
        this.upperCreditLimit = upperCreditLimit;
    }

}
