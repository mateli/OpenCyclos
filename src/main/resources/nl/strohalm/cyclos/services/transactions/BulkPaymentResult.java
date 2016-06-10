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
package nl.strohalm.cyclos.services.transactions;

import nl.strohalm.cyclos.entities.accounts.transactions.Payment;
import nl.strohalm.cyclos.exceptions.ApplicationException;
import nl.strohalm.cyclos.utils.DataObject;

/**
 * Contains the result for an individual entry in a bulk payment
 * 
 * @author luis
 */
public class BulkPaymentResult extends DataObject {

    private static final long    serialVersionUID = -1906179458983666364L;
    private Payment              payment;
    private ApplicationException exception;

    public BulkPaymentResult(final ApplicationException exception) {
        this.exception = exception;
        payment = null;
    }

    public BulkPaymentResult(final Payment payment) {
        this.payment = payment;
        exception = null;
    }

    public ApplicationException getException() {
        return exception;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setException(final ApplicationException exception) {
        this.exception = exception;
    }

    public void setPayment(final Payment payment) {
        this.payment = payment;
    }

}
