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

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.Account;
import nl.strohalm.cyclos.entities.accounts.AccountOwner;
import nl.strohalm.cyclos.entities.accounts.external.ExternalTransfer;
import nl.strohalm.cyclos.entities.accounts.transactions.Payment;
import nl.strohalm.cyclos.entities.accounts.transactions.Transfer;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferType;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.entities.exceptions.UnexpectedEntityException;
import nl.strohalm.cyclos.entities.services.ServiceClient;
import nl.strohalm.cyclos.services.transactions.exceptions.MaxAmountPerDayExceededException;
import nl.strohalm.cyclos.services.transactions.exceptions.NotEnoughCreditsException;
import nl.strohalm.cyclos.services.transactions.exceptions.UpperCreditLimitReachedException;
import nl.strohalm.cyclos.utils.Period;

/**
 * Local interface. It must be used only from other services.
 */
public interface PaymentServiceLocal extends PaymentService {

    /**
     * Validates the max amount at the given day using the max amount specified as a parameter instead of the transfer type's max amount (but if the
     * amount is null, uses the transfer type amount). <br>
     * NOTE: It is exposed in the local interface to be invoked by customs Payment CF validations.
     * @throws MaxAmountPerDayExceededException
     */
    public void validateMaxAmountAtDate(Calendar date, final Account account, final TransferType transferType, final BigDecimal maxAmount, final BigDecimal amount);

    /**
     * Chargebacks all the given transfer atomically, returning, in the same order, a list with results. As web services can pre-process transfers,
     * the list may contain null entries. In this case, the corresponding result will also be null.
     */
    List<BulkChargebackResult> bulkChargeback(List<Transfer> transfers);

    /**
     * To perform a payment, the logged user must either manage the from owner or to owner and must be allowed to use the specified transfer type (if
     * any)
     */
    boolean canMakePayment(AccountOwner from, final AccountOwner to, final TransferType transferType);

    /**
     * Conciliates the given transfer with the given external transfer
     */
    Transfer conciliate(Transfer transfer, ExternalTransfer externalTransfer);

    /**
     * Confirms a payment which is pending for the given ticket
     * @return The main transfer. It may contain children transfers (generated, for example, by fees)
     * @throws NotEnoughCreditsException The account does not have enough credits
     * @throws MaxAmountPerDayExceededException The account has exceeded the maximum transaction amount per day for the specified transfer type
     * @throws EntityNotFoundException Invalid ticket
     * @throws UpperCreditLimitReachedException The payment cannot be performed because it would make the receiving account go beyond the upper credit
     * limit
     * @throws InvalidPaymentReceiverException When the given <code>DoPaymentDTO</code> is not the system
     */
    Transfer confirmPayment(String ticket) throws NotEnoughCreditsException, MaxAmountPerDayExceededException, EntityNotFoundException, UpperCreditLimitReachedException;

    /**
     * Performs all the given payments atomically, returning, in the same order, a list with results
     */
    List<BulkPaymentResult> doBulkPayment(List<DoPaymentDTO> dtos);

    /**
     * Inserts a payment, notifying users. Intended to be called by automatically generated transfers, like loan repayments or fee charges.
     * @throws NotEnoughCreditsException The account does not have enough credits
     * @throws MaxAmountPerDayExceededException The account has exceeded the maximum transaction amount per day for the specified transfer type
     * @throws UnexpectedEntityException Either transfer type or payment receiver are invalid
     * @throws UpperCreditLimitReachedException The payment cannot be performed because it would make the receiving account go beyond the upper credit
     * limit
     * @throws InvalidPaymentReceiverException When the given <code>DoPaymentDTO</code> is not a member
     */
    Payment insertWithNotification(TransferDTO dto) throws NotEnoughCreditsException, MaxAmountPerDayExceededException, UnexpectedEntityException, UpperCreditLimitReachedException;

    /**
     * Inserts a payment, without notifying users. Intended to be called by automatically generated transfers, like loan repayments or fee charges.
     * @throws NotEnoughCreditsException The account does not have enough credits
     * @throws MaxAmountPerDayExceededException The account has exceeded the maximum transaction amount per day for the specified transfer type
     * @throws UnexpectedEntityException Either transfer type or payment receiver are invalid
     * @throws UpperCreditLimitReachedException The payment cannot be performed because it would make the receiving account go beyond the upper credit
     * limit
     * @throws InvalidPaymentReceiverException When the given <code>DoPaymentDTO</code> is not a member
     */
    Payment insertWithoutNotification(TransferDTO dto) throws NotEnoughCreditsException, MaxAmountPerDayExceededException, UnexpectedEntityException, UpperCreditLimitReachedException;

    /**
     * Loads a transfer by the trace number for the current {@link ServiceClient} <br>
     * If the transfer was not found insert a reverse for the transfer.
     * @return the unique transfer or exception
     * @throws EntityNotFoundException if the transfer was not found
     */
    Transfer loadTransferForReverse(String traceNumber, Relationship... fetch) throws EntityNotFoundException;

    /**
     * Notifies registered listeners that a transfer has been processed
     */
    void notifyTransferProcessed(Transfer transfer);

    /**
     * Process all scheduled payments a scheduled payment component.
     */
    void processScheduled(Period period);

    /**
     * Pay a scheduled payment installment setting the specified date as the transfer's process date
     */
    Transfer processScheduled(Transfer transfer);

    /**
     * It removes the trace numbers before (or at) one day before the specified date.
     */
    void purgeOldTraceNumbers(Calendar time);

}
