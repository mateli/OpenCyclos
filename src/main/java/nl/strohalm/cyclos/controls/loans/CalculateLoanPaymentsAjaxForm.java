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
package nl.strohalm.cyclos.controls.loans;

import org.apache.struts.action.ActionForm;

/**
 * Form used to calculate loan payments
 * @author luis
 */
public class CalculateLoanPaymentsAjaxForm extends ActionForm {
    private static final long serialVersionUID = 8149174963151761861L;
    private String            amount;
    private String            date;
    private String            firstExpirationDate;
    private String            paymentCount;
    private long              transferType;

    public String getAmount() {
        return amount;
    }

    public String getDate() {
        return date;
    }

    public String getFirstExpirationDate() {
        return firstExpirationDate;
    }

    public String getPaymentCount() {
        return paymentCount;
    }

    public long getTransferType() {
        return transferType;
    }

    public void setAmount(final String amount) {
        this.amount = amount;
    }

    public void setDate(final String date) {
        this.date = date;
    }

    public void setFirstExpirationDate(final String firstExpirationDate) {
        this.firstExpirationDate = firstExpirationDate;
    }

    public void setPaymentCount(final String paymentCount) {
        this.paymentCount = paymentCount;
    }

    public void setTransferType(final long transferType) {
        this.transferType = transferType;
    }
}
