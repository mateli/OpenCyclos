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
import java.util.List;

import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.AccountOwner;
import nl.strohalm.cyclos.entities.accounts.MemberAccount;
import nl.strohalm.cyclos.entities.accounts.transactions.Invoice;
import nl.strohalm.cyclos.entities.accounts.transactions.Payment;
import nl.strohalm.cyclos.entities.accounts.transactions.ScheduledPayment;
import nl.strohalm.cyclos.entities.accounts.transactions.Transfer;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferQuery;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferType;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.entities.exceptions.UnexpectedEntityException;
import nl.strohalm.cyclos.services.Service;
import nl.strohalm.cyclos.services.accounts.rates.ConversionSimulationDTO;
import nl.strohalm.cyclos.services.stats.StatisticalResultDTO;
import nl.strohalm.cyclos.services.transactions.exceptions.AuthorizedPaymentInPastException;
import nl.strohalm.cyclos.services.transactions.exceptions.MaxAmountPerDayExceededException;
import nl.strohalm.cyclos.services.transactions.exceptions.NotEnoughCreditsException;
import nl.strohalm.cyclos.services.transactions.exceptions.UpperCreditLimitReachedException;
import nl.strohalm.cyclos.services.transfertypes.TransactionFeePreviewForRatesDTO;
import nl.strohalm.cyclos.webservices.accounts.AccountHistoryResultPage;
import nl.strohalm.cyclos.webservices.model.AccountHistoryTransferVO;
import nl.strohalm.cyclos.webservices.payments.AccountHistoryParams;

/**
 * Service interface for payments
 * @author luis
 */
public interface PaymentService extends Service {

    /**
     * Calculates the scheduled payment projection
     */
    public List<ScheduledPaymentDTO> calculatePaymentProjection(ProjectionDTO params);

    /**
     * Returns an AccountHistoryResultPage for the given parameters.
     * @param params
     * @return
     */
    public AccountHistoryResultPage getAccountHistoryResultPage(AccountHistoryParams params);

    /**
     * Returns an account history transfer vo for the given transfer id.
     */
    public AccountHistoryTransferVO getAccountHistoryTransferVO(Long id);

    /**
     * Checks whether the given transfer may be charged back, but without taking in account the logged user. The criteria is:
     * <ul>
     * <li>The payment must be processed (have a non-null processDate)
     * <li>The maximum chargeback time on local settings must be respected
     * <li>The payment must be a root payment (cannot have a parent transfer)
     * <li>The payment must not have been already charged back
     * <li>The payment must not be a chargeback itself
     * </ul>
     * @param ignorePendingPayment if true it won't check if the transfer was processed (is pending)
     */
    boolean canChargeback(Transfer transfer, boolean ignorePendingPayment);

    /**
     * Chargebacks a transfer, returning it's amount to the original account, and does this for all generated transfer tree
     * @return The chargeback payment
     * @throws UnexpectedEntityException When the payment cannot be charged back
     */
    Transfer chargeback(Transfer transfer) throws UnexpectedEntityException;

    /**
     * Performs a payment
     * @return The main payment. May be a {@link ScheduledPayment} or {@link Transfer}. It may contain children transfers (generated, for example, by
     * fees)
     * @throws NotEnoughCreditsException The account does not have enough credits
     * @throws MaxAmountPerDayExceededException The account has exceeded the maximum transaction amount per day for the specified transfer type
     * @throws UnexpectedEntityException Either transfer type or payment receiver are invalid
     * @throws UpperCreditLimitReachedException The payment cannot be performed because it would make the receiving account go beyond the upper credit
     * limit
     * @throws AuthorizedPaymentInPastException When the payment would require authorization and is set to a past date
     */
    Payment doPayment(DoPaymentDTO params) throws NotEnoughCreditsException, MaxAmountPerDayExceededException, UnexpectedEntityException, UpperCreditLimitReachedException, AuthorizedPaymentInPastException;

    /**
     * Returns a ConversionSimulationDTO containing the default values for the given member's default account, and chooses the transferType from the
     * transferTypes param which belongs to the member and has rated fees. If no TransferType with rated fees available, it just uses the first
     * transferType
     */
    ConversionSimulationDTO getDefaultConversionDTO(MemberAccount account, List<TransferType> transferTypes);

    /**
     * Returns the minimum payment
     */
    BigDecimal getMinimumPayment();

    /**
     * creates data for a graph showing the decline of the fees over time on a conversion. Called from Simulate conversion.
     */
    StatisticalResultDTO getSimulateConversionGraph(ConversionSimulationDTO dto);

    /**
     * Checks if the logged user has the right permissions to perform a chargeback of the given transfer.
     */
    boolean hasPermissionsToChargeback(Transfer transfer);

    /**
     * Returns whether the given payment is visible by the current user
     */
    boolean isVisible(Payment payment);

    /**
     * Returns details of a single transfer
     * @throws EntityNotFoundException No such id
     */
    Transfer load(Long id, Relationship... fetch) throws EntityNotFoundException;

    /**
     * Searches for transfers
     */
    List<Transfer> search(TransferQuery query);

    /**
     * Simulates the conversion fees
     */
    TransactionFeePreviewForRatesDTO simulateConversion(ConversionSimulationDTO params);

    /**
     * Simulates a payment, without actually performing it. The result (and exception) are the same as doPayment*
     * @return The main transfer. It may contain children transfers (generated, for example, by fees)
     * @throws NotEnoughCreditsException The account does not have enough credits
     * @throws MaxAmountPerDayExceededException The account has exceeded the maximum transaction amount per day for the specified transfer type
     * @throws UnexpectedEntityException Either transfer type or payment receiver are invalid
     * @throws UpperCreditLimitReachedException The payment cannot be performed because it would make the receiving account go beyond the upper credit
     * limit
     * @throws InvalidPaymentReceiverException When the given <code>DoPaymentDTO</code> is not the system
     * @throws AuthorizedPaymentInPastException When the payment would require authorization and is set to a past date
     */
    Payment simulatePayment(DoPaymentDTO params) throws NotEnoughCreditsException, MaxAmountPerDayExceededException, UnexpectedEntityException, UpperCreditLimitReachedException, AuthorizedPaymentInPastException;

    /**
     * validates the conversion simulation form
     */
    void validate(ConversionSimulationDTO dto);

    /**
     * Validates the specified payment
     */
    void validate(DoPaymentDTO payment);

    /**
     * Checks whether the given payment would require authorization
     */
    boolean wouldRequireAuthorization(DoPaymentDTO dto);

    /**
     * Checks whether accepting the given invoice would require authorization
     */
    boolean wouldRequireAuthorization(Invoice invoice);

    /**
     * Checks whether the given transfer would require authorization
     */
    boolean wouldRequireAuthorization(Transfer transfer);

    /**
     * Checks whether a payment would require authorization
     */
    boolean wouldRequireAuthorization(TransferType transferType, final BigDecimal amount, AccountOwner from);

}
