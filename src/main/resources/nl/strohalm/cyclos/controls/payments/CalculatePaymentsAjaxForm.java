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
package nl.strohalm.cyclos.controls.payments;

import nl.strohalm.cyclos.utils.binding.MapBean;

import org.apache.struts.action.ActionForm;

/**
 * Form used to generate and validate scheduled payments
 * @author luis
 */
public class CalculatePaymentsAjaxForm extends ActionForm {
    private static final long serialVersionUID = 1690275600967013091L;
    private String            from;
    private String            amount;
    private String            date;
    private String            firstPaymentDate;
    private String            paymentCount;
    private Object            recurrence       = new MapBean("number", "field");

    public String getAmount() {
        return amount;
    }

    public String getDate() {
        return date;
    }

    public String getFirstPaymentDate() {
        return firstPaymentDate;
    }

    public String getFrom() {
        return from;
    }

    public String getPaymentCount() {
        return paymentCount;
    }

    public Object getRecurrence() {
        return recurrence;
    }

    public void setAmount(final String amount) {
        this.amount = amount;
    }

    public void setDate(final String date) {
        this.date = date;
    }

    public void setFirstPaymentDate(final String firstExpirationDate) {
        firstPaymentDate = firstExpirationDate;
    }

    public void setFrom(final String from) {
        this.from = from;
    }

    public void setPaymentCount(final String paymentCount) {
        this.paymentCount = paymentCount;
    }

    public void setRecurrence(final Object recurrence) {
        this.recurrence = recurrence;
    }

}
