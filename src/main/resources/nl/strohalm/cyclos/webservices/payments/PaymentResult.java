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

import java.io.Serializable;

import nl.strohalm.cyclos.webservices.model.AccountHistoryTransferVO;
import nl.strohalm.cyclos.webservices.model.AccountStatusVO;

/**
 * Contains the payment status and the payment data
 * @author luis
 */
public class PaymentResult implements Serializable {
    private static final long        serialVersionUID = 5735814382545266054L;
    private PaymentStatus            status;
    private AccountHistoryTransferVO transfer;
    private AccountStatusVO          fromAccountStatus;
    private AccountStatusVO          toAccountStatus;

    public PaymentResult() {
    }

    public PaymentResult(final PaymentStatus status, final AccountHistoryTransferVO transfer) {
        this.status = status;
        this.transfer = transfer;
    }

    public PaymentResult(final PaymentStatus status, final AccountHistoryTransferVO transfer, final AccountStatusVO fromAccountStatus, final AccountStatusVO toAccountStatus) {
        this.status = status;
        this.transfer = transfer;
        this.fromAccountStatus = fromAccountStatus;
        this.toAccountStatus = toAccountStatus;
    }

    public AccountStatusVO getFromAccountStatus() {
        return fromAccountStatus;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public AccountStatusVO getToAccountStatus() {
        return toAccountStatus;
    }

    public AccountHistoryTransferVO getTransfer() {
        return transfer;
    }

    public void setFromAccountStatus(final AccountStatusVO fromAccountStatus) {
        this.fromAccountStatus = fromAccountStatus;
    }

    public void setStatus(final PaymentStatus status) {
        this.status = status;
    }

    public void setToAccountStatus(final AccountStatusVO toAccountStatus) {
        this.toAccountStatus = toAccountStatus;
    }

    public void setTransfer(final AccountHistoryTransferVO transfer) {
        this.transfer = transfer;
    }

}
