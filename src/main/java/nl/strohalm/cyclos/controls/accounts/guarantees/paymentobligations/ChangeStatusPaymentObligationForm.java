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

/**
 * Form used by all the actions related to payment Obligation's status change
 * @see ChangeStatusPaymentObligationAction
 * @author ameyer
 * 
 */
public class ChangeStatusPaymentObligationForm extends ActionForm {
    private static final long serialVersionUID = -6582635060190306791L;

    private Long              paymentObligationId;

    public Long getPaymentObligationId() {
        return paymentObligationId;
    }

    public void setPaymentObligationId(final Long paymentObligationId) {
        this.paymentObligationId = paymentObligationId;
    }
}
