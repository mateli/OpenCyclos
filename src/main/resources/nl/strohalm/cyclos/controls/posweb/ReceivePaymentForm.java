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
package nl.strohalm.cyclos.controls.posweb;

import nl.strohalm.cyclos.controls.payments.PaymentForm;

/**
 * Form for the posweb member to receive a payment
 * @author luis
 */
public class ReceivePaymentForm extends PaymentForm {

    private static final long serialVersionUID = -518136955565793151L;
    private String            principalType;
    private String            credentials;
    private String            schedulingType;
    private String            paymentCount;
    private String            scheduledFor;
    private String            firstPaymentDate;

    public String getCredentials() {
        return credentials;
    }

    public String getFirstPaymentDate() {
        return firstPaymentDate;
    }

    public String getPaymentCount() {
        return paymentCount;
    }

    public String getPrincipalType() {
        return principalType;
    }

    public String getScheduledFor() {
        return scheduledFor;
    }

    public String getSchedulingType() {
        return schedulingType;
    }

    public void setCredentials(final String credentials) {
        this.credentials = credentials;
    }

    public void setFirstPaymentDate(final String firstPaymentDate) {
        this.firstPaymentDate = firstPaymentDate;
    }

    public void setPaymentCount(final String paymentCount) {
        this.paymentCount = paymentCount;
    }

    public void setPrincipalType(final String principalType) {
        this.principalType = principalType;
    }

    public void setScheduledFor(final String scheduledFor) {
        this.scheduledFor = scheduledFor;
    }

    public void setSchedulingType(final String schedulingType) {
        this.schedulingType = schedulingType;
    }
}
