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
package nl.strohalm.cyclos.webservices.payments;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import nl.strohalm.cyclos.entities.services.ServiceOperation;
import nl.strohalm.cyclos.webservices.Permission;

/**
 * Web service interfaces for payments
 * @author luis
 */
@WebService
public interface PaymentWebService {

    /**
     * @return Status for the chargedback payments.
     */
    public List<ChargebackResult> doBulkChargeback(@WebParam(name = "transferId") final List<Long> transfersIds);

    /**
     * @return Status for the reversed payments.
     */
    public List<ChargebackResult> doBulkReverse(@WebParam(name = "traceNumber") final List<String> traces);

    /**
     * Performs a chargeback, returning the status, the original transfer and the chargeback transfer
     */
    @Permission(ServiceOperation.CHARGEBACK)
    @WebMethod
    ChargebackResult chargeback(@WebParam(name = "transferId") Long transferId);

    /**
     * Confirms a payment originated from another channel
     */
    @Permission({ ServiceOperation.DO_PAYMENT, ServiceOperation.RECEIVE_PAYMENT })
    @WebMethod
    PaymentResult confirmPayment(@WebParam(name = "params") ConfirmPaymentParameters params);

    /**
     * Performs several payments under the same transaction payment, returning the status and the transfer data for each payment
     */
    @Permission({ ServiceOperation.DO_PAYMENT, ServiceOperation.RECEIVE_PAYMENT })
    @WebMethod
    List<PaymentResult> doBulkPayment(@WebParam(name = "params") List<PaymentParameters> params);

    /**
     * Performs a payment, returning the status and the transfer data
     */
    @Permission({ ServiceOperation.DO_PAYMENT, ServiceOperation.RECEIVE_PAYMENT })
    @WebMethod
    PaymentResult doPayment(@WebParam(name = "params") PaymentParameters params);

    /**
     * Expires the given ticket, invalidating it, returning whether the ticket was expired
     */
    @Permission({ ServiceOperation.DO_PAYMENT, ServiceOperation.RECEIVE_PAYMENT })
    @WebMethod
    boolean expireTicket(@WebParam(name = "ticket") String ticket);

    /**
     * Makes a payment request, in order to be confirmed in another channel
     */
    @Permission({ ServiceOperation.DO_PAYMENT, ServiceOperation.RECEIVE_PAYMENT })
    @WebMethod
    RequestPaymentResult requestPaymentConfirmation(@WebParam(name = "params") RequestPaymentParameters params);

    /**
     * Performs a reverse, returning the status, the original transfer and the chargeback transfer
     */
    @Permission(ServiceOperation.CHARGEBACK)
    @WebMethod
    ChargebackResult reverse(@WebParam(name = "traceNumber") String traceNumber);

    /**
     * Check whether a payment would be executed, returning the status, without actually performing it
     */
    @Permission({ ServiceOperation.DO_PAYMENT, ServiceOperation.RECEIVE_PAYMENT })
    @WebMethod
    PaymentStatus simulatePayment(@WebParam(name = "params") PaymentParameters params);
}
