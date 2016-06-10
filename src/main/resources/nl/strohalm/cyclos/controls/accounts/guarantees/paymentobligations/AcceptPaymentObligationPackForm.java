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
package nl.strohalm.cyclos.controls.accounts.guarantees.paymentobligations;

import org.apache.struts.action.ActionForm;

public class AcceptPaymentObligationPackForm extends ActionForm {

    private static final long serialVersionUID = -1386827274668193669L;
    private Long[]            paymentObligationIds;
    private Long              issuerId;

    public AcceptPaymentObligationPackForm() {
        paymentObligationIds = new Long[0];
    }

    public Long getIssuerId() {
        return issuerId;
    }

    public Long[] getPaymentObligationIds() {
        return paymentObligationIds;
    }

    public void setIssuerId(final Long issuerId) {
        this.issuerId = issuerId;
    }

    public void setPaymentObligationIds(final Long[] paymentObligationIds) {
        this.paymentObligationIds = paymentObligationIds;
    }
}
