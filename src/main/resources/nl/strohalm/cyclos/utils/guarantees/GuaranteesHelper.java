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
package nl.strohalm.cyclos.utils.guarantees;

import java.math.BigDecimal;

import nl.strohalm.cyclos.services.accounts.guarantees.GuaranteeFeeVO;
import nl.strohalm.cyclos.utils.Amount;
import nl.strohalm.cyclos.utils.DateHelper;
import nl.strohalm.cyclos.utils.Period;

public class GuaranteesHelper {
    public static BigDecimal calculateFee(final Period period, final BigDecimal amount, final GuaranteeFeeVO guaranteeFee) {
        Amount result = null;
        switch (guaranteeFee.getType()) {
            case FIXED:
                result = Amount.fixed(guaranteeFee.getFee());
                // fall down!
            case PERCENTAGE:
                result = result != null ? result : Amount.percentage(guaranteeFee.getFee());
                return result.apply(amount);
            case VARIABLE_ACCORDING_TO_TIME: // it's a compound interest calculation
                if (period.getBegin().after(period.getEnd())) {
                    throw new IllegalArgumentException("Can't calculate the guarantee fee. Invalid period time: the begin date must be less than the end date");
                } else if (amount.doubleValue() == 0d || guaranteeFee.getFee().doubleValue() == 0d) {
                    return BigDecimal.ZERO;
                }
                final double periodInDays = DateHelper.daysBetween(period.getBegin(), period.getEnd());
                final double pow = periodInDays / 365D;
                final double value = (Math.pow(1 + guaranteeFee.getFee().doubleValue() / 100D, pow) - 1) * amount.doubleValue();
                return new BigDecimal(value);
            default:
                throw new IllegalArgumentException("Can't calculate the guarantee fee: unknown fee type: " + guaranteeFee.getType());
        }
    }
}
