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
package nl.strohalm.cyclos.entities.accounts.guarantees;

import nl.strohalm.cyclos.utils.DataObject;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.math.BigDecimal;

@Embeddable
public class GuaranteeTypeFee extends DataObject {

    private static final long serialVersionUID = 4259992289841136740L;

    @Column(name = "type", length = 1)
    private GuaranteeType.FeeType type;

    @Column(name = "fee", precision = 15, scale = 6)
    private BigDecimal fee;

    @Column(name = "readonly", nullable = false)
    private boolean           readonly;

    public boolean isReadonly() {
        return readonly;
    }

    public void setReadonly(final boolean readonly) {
        this.readonly = readonly;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public GuaranteeType.FeeType getType() {
        return type;
    }

    public void setFee(final BigDecimal fee) {
        this.fee = fee;
    }

    public void setType(final GuaranteeType.FeeType feeType) {
        type = feeType;
    }

}
