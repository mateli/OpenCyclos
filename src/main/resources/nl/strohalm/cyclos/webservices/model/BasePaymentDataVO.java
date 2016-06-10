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

import java.math.BigDecimal;
import java.util.Calendar;

/**
 * Contains very basic data on a payment
 * 
 * @author luis
 */
public abstract class BasePaymentDataVO extends EntityVO {
    private static final long serialVersionUID = 6396122736179378711L;
    protected Calendar        date;
    protected String          formattedDate;
    protected Calendar        processDate;
    protected String          formattedProcessDate;
    protected BigDecimal      amount;
    protected String          formattedAmount;
    protected PaymentStatusVO status;

    public BigDecimal getAmount() {
        return amount;
    }

    public Calendar getDate() {
        return date;
    }

    public String getFormattedAmount() {
        return formattedAmount;
    }

    public String getFormattedDate() {
        return formattedDate;
    }

    public String getFormattedProcessDate() {
        return formattedProcessDate;
    }

    public Calendar getProcessDate() {
        return processDate;
    }

    public PaymentStatusVO getStatus() {
        return status;
    }

    public void setAmount(final BigDecimal amount) {
        this.amount = amount;
    }

    public void setDate(final Calendar date) {
        this.date = date;
    }

    public void setFormattedAmount(final String formattedAmount) {
        this.formattedAmount = formattedAmount;
    }

    public void setFormattedDate(final String formattedDate) {
        this.formattedDate = formattedDate;
    }

    public void setFormattedProcessDate(final String formattedProcessDate) {
        this.formattedProcessDate = formattedProcessDate;
    }

    public void setProcessDate(final Calendar processDate) {
        this.processDate = processDate;
    }

    public void setStatus(final PaymentStatusVO status) {
        this.status = status;
    }

}
