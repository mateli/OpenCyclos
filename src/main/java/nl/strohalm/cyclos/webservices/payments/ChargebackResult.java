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

import javax.xml.bind.annotation.XmlType;

import nl.strohalm.cyclos.webservices.model.AccountHistoryTransferVO;

/**
 * Result of a chargeback operation
 * @author luis
 */
@XmlType(name = "chargebackResult")
public class ChargebackResult {

    private ChargebackStatus         status;
    private AccountHistoryTransferVO originalTransfer;
    private AccountHistoryTransferVO chargebackTransfer;

    public ChargebackResult() {
    }

    public ChargebackResult(final ChargebackStatus status, final AccountHistoryTransferVO originalTransfer, final AccountHistoryTransferVO chargebackTransfer) {
        this.status = status;
        this.originalTransfer = originalTransfer;
        this.chargebackTransfer = chargebackTransfer;
    }

    public AccountHistoryTransferVO getChargebackTransfer() {
        return chargebackTransfer;
    }

    public AccountHistoryTransferVO getOriginalTransfer() {
        return originalTransfer;
    }

    public ChargebackStatus getStatus() {
        return status;
    }

    public void setChargebackTransfer(final AccountHistoryTransferVO chargebackTransfer) {
        this.chargebackTransfer = chargebackTransfer;
    }

    public void setOriginalTransfer(final AccountHistoryTransferVO originalTransfer) {
        this.originalTransfer = originalTransfer;
    }

    public void setStatus(final ChargebackStatus status) {
        this.status = status;
    }
}
