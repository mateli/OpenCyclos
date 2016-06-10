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

import nl.strohalm.cyclos.entities.accounts.AccountRates;
import nl.strohalm.cyclos.entities.accounts.Rated;
import nl.strohalm.cyclos.utils.DataObject;

/**
 * DTO for the result of rate calculations.
 * 
 * @author rinke
 */
public class RatesResultDTO extends DataObject implements Rated {

    private static final long serialVersionUID = 204246198199725208L;
    private BigDecimal        aRate;
    private BigDecimal        dRate;
    private BigDecimal        iRate;
    private Calendar          emissionDate;
    private Calendar          expirationDate;
    private BigDecimal        virtualRatedBalance;
    private BigDecimal        rateBalanceCorrection;
    private Calendar          date;

    public RatesResultDTO() {
        super();
    }

    public RatesResultDTO(final AccountRates accountRates) {
        super();
        if (accountRates != null) {
            emissionDate = accountRates.getEmissionDate();
            expirationDate = accountRates.getExpirationDate();
            iRate = accountRates.getiRate();
            rateBalanceCorrection = accountRates.getRateBalanceCorrection();
            if (accountRates.getLastTransfer() != null) {
                date = accountRates.getLastTransfer().getProcessDate();
            }
        }
    }

    public RatesResultDTO(final RatesDTO ratesDTO) {
        super();
        if (ratesDTO != null) {
            emissionDate = ratesDTO.getEmissionDate();
            expirationDate = ratesDTO.getExpirationDate();
            iRate = ratesDTO.getiRate();
            rateBalanceCorrection = ratesDTO.getRateBalanceCorrection();
            date = ratesDTO.getDate();
        }
    }

    public BigDecimal getaRate() {
        return aRate;
    }

    public Calendar getDate() {
        return date;
    }

    public BigDecimal getdRate() {
        return dRate;
    }

    @Override
    public Calendar getEmissionDate() {
        return emissionDate;
    }

    @Override
    public Calendar getExpirationDate() {
        return expirationDate;
    }

    @Override
    public BigDecimal getiRate() {
        return iRate;
    }

    public BigDecimal getRateBalanceCorrection() {
        return rateBalanceCorrection;
    }

    public BigDecimal getVirtualRatedBalance() {
        return virtualRatedBalance;
    }

    public void setaRate(final BigDecimal aRate) {
        this.aRate = aRate;
    }

    public void setDate(final Calendar date) {
        this.date = date;
    }

    public void setdRate(final BigDecimal dRate) {
        this.dRate = dRate;
    }

    public void setEmissionDate(final Calendar emissionDate) {
        this.emissionDate = emissionDate;
    }

    public void setExpirationDate(final Calendar expirationDate) {
        this.expirationDate = expirationDate;
    }

    public void setiRate(final BigDecimal iRate) {
        this.iRate = iRate;
    }

    public void setRateBalanceCorrection(final BigDecimal rateBalanceCorrection) {
        this.rateBalanceCorrection = rateBalanceCorrection;
    }

    public void setRates(final Rated rated) {
        emissionDate = rated.getEmissionDate();
        expirationDate = rated.getExpirationDate();
        iRate = rated.getiRate();
    }

    public void setVirtualRatedBalance(final BigDecimal virtualRatedBalance) {
        this.virtualRatedBalance = virtualRatedBalance;
    }

}
