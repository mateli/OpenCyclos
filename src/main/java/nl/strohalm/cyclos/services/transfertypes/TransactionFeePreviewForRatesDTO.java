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
package nl.strohalm.cyclos.services.transfertypes;

import java.math.BigDecimal;
import java.math.MathContext;

import nl.strohalm.cyclos.entities.settings.LocalSettings;

/**
 * Extension of TransactionFeePreviewDTO, also allowing to set rates explicitly on the dto. These rates are used to calculate a preview with rates
 * different than the account's rates.
 * @author Rinke
 */
public class TransactionFeePreviewForRatesDTO extends TransactionFeePreviewDTO {

    private static final long serialVersionUID = 8886564544894519454L;

    private BigDecimal        aRate;
    private BigDecimal        dRate;

    public BigDecimal getARate() {
        return aRate;
    }

    public BigDecimal getDRate() {
        return dRate;
    }

    /**
     * 
     * @return a BigDecimal, being the sum of all fees expressed as a percentage of the total conversion amount.
     */
    public BigDecimal getRatesAsFeePercentage() {
        final BigDecimal resultAmount = getFinalAmount();
        final BigDecimal amountToConvert = getAmount();
        if (amountToConvert.compareTo(BigDecimal.ZERO) <= 0) {
            return null;
        }
        final int scale = LocalSettings.MAX_PRECISION;
        final MathContext mc = new MathContext(scale);
        final BigDecimal totalConversionFeeAsFraction = BigDecimal.ONE.subtract(resultAmount.divide(amountToConvert, mc));
        final BigDecimal totalConversionFeeAsPercentage = totalConversionFeeAsFraction.multiply(new BigDecimal(100.0));
        return totalConversionFeeAsPercentage;
    }

    public void setARate(final BigDecimal rate) {
        aRate = rate;
    }

    public void setDRate(final BigDecimal rate) {
        dRate = rate;
    }

}
