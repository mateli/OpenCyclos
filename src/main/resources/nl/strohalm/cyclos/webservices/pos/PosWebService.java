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
package nl.strohalm.cyclos.webservices.pos;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import nl.strohalm.cyclos.webservices.accounts.AccountHistoryResultPage;
import nl.strohalm.cyclos.webservices.model.AccountStatusVO;
import nl.strohalm.cyclos.webservices.model.PosInitializationVO;
import nl.strohalm.cyclos.webservices.payments.ChargebackResult;
import nl.strohalm.cyclos.webservices.payments.PaymentResult;

/**
 * Web service used to interact with POS devices
 * @author luis
 */
@WebService
public interface PosWebService {

    /**
     * A chargeback of a performed payment
     */
    @WebMethod
    ChargebackResult chargeback(@WebParam(name = "params") ChargebackParameters params);

    /**
     * Returns the current status of an account
     */
    @WebMethod
    AccountStatusVO getAccountStatus(@WebParam(name = "params") AccountStatusPosParameters parameters);

    /**
     * Returns the initialization data for a POS device
     */
    @WebMethod
    PosInitializationVO getInitializationData(@WebParam(name = "params") InitializationParameters parameters);

    /**
     * The POS owner performs a payment to another member
     */
    @WebMethod
    PaymentResult makePayment(@WebParam(name = "params") MakePaymentParameters params);

    /**
     * The POS owner receives a payment from another member
     */
    @WebMethod
    PaymentResult receivePayment(@WebParam(name = "params") ReceivePaymentParameters params);

    /**
     * Returns the account history of an account
     */
    @WebMethod
    AccountHistoryResultPage searchAccountHistory(@WebParam(name = "params") AccountHistoryPosParameters parameters);
}
