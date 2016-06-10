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
import java.util.Map;

import nl.strohalm.cyclos.entities.accounts.fees.transaction.TransactionFee;
import nl.strohalm.cyclos.utils.DataObject;

import org.apache.commons.collections.MapUtils;

/**
 * Shows the preview of transaction fees that will be applied on a payment, as well as the payment final amount (fees can deduct from the original
 * amount)
 * @author luis
 */
public class TransactionFeePreviewDTO extends DataObject {

    private static final long               serialVersionUID = -6420573891936655028L;
    private Map<TransactionFee, BigDecimal> fees;
    private BigDecimal                      amount;
    private BigDecimal                      finalAmount;

    public BigDecimal getAmount() {
        return amount;
    }

    public Map<TransactionFee, BigDecimal> getFees() {
        return fees;
    }

    public BigDecimal getFinalAmount() {
        return finalAmount;
    }

    public BigDecimal getTotalFeeAmount() {
        BigDecimal result = BigDecimal.ZERO;
        if (MapUtils.isNotEmpty(fees)) {
            for (final BigDecimal value : fees.values()) {
                result = result.add(value);
            }
        }
        return result;
    }

    public void setAmount(final BigDecimal amount) {
        this.amount = amount;
    }

    public void setFees(final Map<TransactionFee, BigDecimal> fees) {
        this.fees = fees;
    }

    public void setFinalAmount(final BigDecimal finalAmount) {
        this.finalAmount = finalAmount;
    }

}
