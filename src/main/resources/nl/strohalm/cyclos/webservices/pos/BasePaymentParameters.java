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
package nl.strohalm.cyclos.webservices.pos;

import java.math.BigDecimal;

/**
 * Base class for payments in POS devices
 * @author luis
 */
public abstract class BasePaymentParameters extends BasePosParameters {
    private BigDecimal amount;
    private Long       transferTypeId;

    public BigDecimal getAmount() {
        return amount;
    }

    public Long getTransferTypeId() {
        return transferTypeId;
    }

    public void setAmount(final BigDecimal amount) {
        this.amount = amount;
    }

    public void setTransferTypeId(final Long transferTypeId) {
        this.transferTypeId = transferTypeId;
    }

    @Override
    public String toString() {
        return "BasePaymentParameters [amount=" + amount + ", transferTypeId=" + transferTypeId + ", " + super.toString() + "]";
    }
}
