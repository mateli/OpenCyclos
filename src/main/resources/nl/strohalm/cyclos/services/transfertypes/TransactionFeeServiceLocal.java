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
package nl.strohalm.cyclos.services.transfertypes;

import java.math.BigDecimal;

import nl.strohalm.cyclos.entities.accounts.AccountOwner;
import nl.strohalm.cyclos.entities.accounts.transactions.Transfer;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferType;
import nl.strohalm.cyclos.services.accounts.rates.RatesResultDTO;

/**
 * Local interface. It must be used only from other services.
 */
public interface TransactionFeeServiceLocal extends TransactionFeeService {

    /**
     * Returns a transfer representing the charging of this fee, if applied. The returned transfer is not persistent (has not been saved yet). The
     * 'simulation' parameter is used by the preview() method to show to the user the fees that he will pay
     */
    public Transfer buildTransfer(final BuildTransferWithFeesDTO params);

    /**
     * Previews which fees would be applied to a transaction of the given type and amount and rates. Use this one only in case of testing a
     * transaction with different rates than set on the account. In all other cases, use the overloaded version.
     * @return A Map where the key is the transaction fee and the value is the fee amount. The Object of the return type is an instance of
     * TransactionFeePreviewForRatesDTO.
     */
    TransactionFeePreviewDTO preview(AccountOwner from, AccountOwner to, TransferType transferType, BigDecimal amount, RatesResultDTO rates);

}
