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
package nl.strohalm.cyclos.services.accounts.guarantees;

import java.math.BigDecimal;

import nl.strohalm.cyclos.utils.DataObject;
import nl.strohalm.cyclos.utils.Period;

public class GuaranteeFeeCalculationDTO extends DataObject {
    private static final long serialVersionUID = -3978088708134221686L;

    private Period            validity;
    private BigDecimal        amount;
    private GuaranteeFeeVO    feeSpec;
    private Long              guaranteeTypeId;

    public BigDecimal getAmount() {
        return amount;
    }

    public GuaranteeFeeVO getFeeSpec() {
        return feeSpec;
    }

    public Long getGuaranteeTypeId() {
        return guaranteeTypeId;
    }

    public Period getValidity() {
        return validity;
    }

    public void setAmount(final BigDecimal amount) {
        this.amount = amount;
    }

    public void setFeeSpec(final GuaranteeFeeVO feeSpec) {
        this.feeSpec = feeSpec;
    }

    public void setGuaranteeTypeId(final Long guaranteeTypeId) {
        this.guaranteeTypeId = guaranteeTypeId;
    }

    public void setValidity(final Period validity) {
        this.validity = validity;
    }

}
