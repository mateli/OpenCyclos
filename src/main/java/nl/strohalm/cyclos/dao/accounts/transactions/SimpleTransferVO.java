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
package nl.strohalm.cyclos.dao.accounts.transactions;

import java.math.BigDecimal;
import java.util.Calendar;

import nl.strohalm.cyclos.utils.DataObject;
import nl.strohalm.cyclos.utils.FormatObject;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * A simple transfer vo, containg basic data only
 * @author luis
 */
public class SimpleTransferVO extends DataObject {

    private static final long serialVersionUID = 3143051476483667714L;
    private BigDecimal        amount;
    private Calendar          date;

    public SimpleTransferVO() {
    }

    public SimpleTransferVO(final Calendar date, final BigDecimal amount) {
        super();
        this.date = date;
        this.amount = amount;
    }

    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof SimpleTransferVO)) {
            return false;
        }
        final SimpleTransferVO vo = (SimpleTransferVO) obj;
        return new EqualsBuilder().append(date, vo.date).append(amount == null ? 0F : amount.floatValue(), vo.amount == null ? 0F : vo.amount.floatValue()).isEquals();
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Calendar getDate() {
        return date;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(date).append(amount == null ? 0F : amount.floatValue()).toHashCode();
    }

    public void setAmount(final BigDecimal amount) {
        this.amount = amount;
    }

    public void setDate(final Calendar date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return FormatObject.formatObject(date) + " of " + FormatObject.formatObject(amount);
    }

}
