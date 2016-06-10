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
package nl.strohalm.cyclos.webservices.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class TransactionFeeVO implements Serializable {

    private static final long serialVersionUID = 1L;
    private String            name;
    private BigDecimal        amount;
    private String            formattedAmount;

    public TransactionFeeVO() {

    }

    public TransactionFeeVO(final String name, final BigDecimal amount, final String formattedAmount) {
        super();
        this.name = name;
        this.amount = amount;
        this.formattedAmount = formattedAmount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getFormattedAmount() {
        return formattedAmount;
    }

    public String getName() {
        return name;
    }

    public void setAmount(final BigDecimal amount) {
        this.amount = amount;
    }

    public void setFormattedAmount(final String formattedAmount) {
        this.formattedAmount = formattedAmount;
    }

    public void setName(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "TransactionFeeVO [name=" + name + ", amount=" + amount + ", formattedAmount=" + formattedAmount + "]";
    }

}
