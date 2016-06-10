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
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import nl.strohalm.cyclos.entities.accounts.AccountOwner;
import nl.strohalm.cyclos.entities.accounts.AccountType;
import nl.strohalm.cyclos.entities.accounts.SystemAccountOwner;
import nl.strohalm.cyclos.entities.accounts.loans.Loan;
import nl.strohalm.cyclos.entities.accounts.loans.LoanParameters;
import nl.strohalm.cyclos.entities.accounts.loans.LoanPayment;
import nl.strohalm.cyclos.entities.accounts.loans.LoanRepaymentAmountsDTO;
import nl.strohalm.cyclos.entities.accounts.transactions.Transfer;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferType;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.services.accounts.AccountDTO;
import nl.strohalm.cyclos.services.transactions.exceptions.PartialInterestsAmountException;
import nl.strohalm.cyclos.utils.DateHelper;
import nl.strohalm.cyclos.utils.RelationshipHelper;

/**
 * Handler for single payment loans
 * @author luis
 */
public class LoanWithInterestHandler extends BaseMultiplePaymentLoanHandler {

    public LoanWithInterestHandler() {
        super(Loan.Type.SINGLE_PAYMENT);
    }

    @Override
    public List<TransferDTO> buildTransfersForRepayment(final RepayLoanDTO repayDTO, final LoanRepaymentAmountsDTO amountsDTO) {
        // The super implementation will have a single transfer
        final List<TransferDTO> transfers = new ArrayList<TransferDTO>(super.buildTransfersForRepayment(repayDTO, amountsDTO));
        final LocalSettings localSettings = settingsService.getLocalSettings();

        // Get some required data
        final TransferDTO baseRepayment = transfers.get(0);
        final Loan loan = repayDTO.getLoan();
        final Calendar repaymentDate = repayDTO.getDate() == null ? Calendar.getInstance() : repayDTO.getDate();
        final int paymentCount = loan.getPaymentCount();
        final Transfer loanTransfer = loan.getTransfer();
        final BigDecimal originalAmount = loanTransfer.getAmount();
        final LoanParameters parameters = loanTransfer.getType().getLoan();
        final LoanPayment payment = baseRepayment.getLoanPayment();
        final BigDecimal paymentAmount = payment.getAmount();
        final BigDecimal repaidAmount = payment.getRepaidAmount();
        final BigDecimal amountToRepay = repayDTO.getAmount();

        // Build a transfer for each amount part, as following:
        // Imagine the loan amount = 1000, grant fee = 30, monthly interest = 20, in 10 payments = 10 x 105
        // Each payment should generate:
        // * A base repayment of 100 (original amount repayment, excluding fees and interests)
        // * The grant fee repayment of 3
        // * The monthly interest repayment of 2
        // * When expired, ie: expiration fee = 4, expiring 5 days of 1%/day
        // *** The expiration fee repayment of 4
        // *** The expiration daily interest repayment of 5 (1% of 100 * 5 days)
        final MathContext mathContext = localSettings.getMathContext();
        final BigDecimal baseAmount = localSettings.round(originalAmount.divide(new BigDecimal(paymentCount), mathContext));

        final BigDecimal totalExtraAmount = paymentAmount.subtract(baseAmount);
        final BigDecimal extraAmount = localSettings.round(amountToRepay.add(repaidAmount).subtract(baseAmount));

        if (extraAmount.compareTo(BigDecimal.ZERO) == 1) {
            // When what is already repaid + this payment amount is greater than the payment without interest or fees,
            // there are parts of the amount that are interest or fees. Those parts must be charged on separate transfers
            baseRepayment.setAmount(baseAmount.subtract(repaidAmount));

            // Allow partial payment only if amount is the total loan amount or less or equals than
            // base amount. That's to avoid: baseAmount = 200, extraAmount = 2, the user pays 201.
            // How should we interpret the extra 1?!? grantFee? monthlyInterest?
            // We just avoid the problem by not allowing it to happen ;-)
            if (totalExtraAmount.subtract(extraAmount).floatValue() > PRECISION_DELTA) {
                throw new PartialInterestsAmountException(baseAmount.subtract(repaidAmount), paymentAmount.subtract(baseAmount));
            }

            // Calculate the grant fee repayment
            final BigDecimal totalGrantFee = localSettings.round(parameters.calculateGrantFee(originalAmount));
            final BigDecimal paymentGrantFee = componentAmount(payment, totalGrantFee);
            if (generateComponentTransferForAmount(paymentGrantFee)) {
                final TransferDTO transfer = generateComponentTransfer(payment, baseRepayment, parameters, parameters.getGrantFeeRepaymentType(), paymentGrantFee);
                transfers.add(transfer);
            }

            // Calculate the monthly interest repayment
            final Calendar firstExpirationDate = loan.getPayments().get(0).getExpirationDate();
            final BigDecimal totalMonthlyInterest = localSettings.round(parameters.calculateMonthlyInterests(originalAmount, paymentCount, loanTransfer.getDate(), firstExpirationDate, mathContext));
            final BigDecimal paymentMonthlyInterest = componentAmount(payment, totalMonthlyInterest);
            if (generateComponentTransferForAmount(paymentMonthlyInterest)) {
                final TransferDTO transfer = generateComponentTransfer(payment, baseRepayment, parameters, parameters.getMonthlyInterestRepaymentType(), paymentMonthlyInterest);
                transfers.add(transfer);
            }

            // Calculate expiration fee / interest
            final int diff = DateHelper.daysBetween(payment.getExpirationDate(), repaymentDate);

            // Expiration fee
            final BigDecimal paymentExpirationFee = localSettings.round(parameters.calculatePaymentExpirationFee(paymentAmount, diff));
            if (generateComponentTransferForAmount(paymentExpirationFee)) {
                final TransferDTO transfer = generateComponentTransfer(payment, baseRepayment, parameters, parameters.getExpirationFeeRepaymentType(), paymentExpirationFee);
                transfers.add(transfer);
            }

            // Expiration interest
            final BigDecimal paymentExpirationInterest = localSettings.round(parameters.calculatePaymentExpirationInterest(paymentAmount, diff, mathContext));
            if (generateComponentTransferForAmount(paymentExpirationInterest)) {
                final TransferDTO transfer = generateComponentTransfer(payment, baseRepayment, parameters, parameters.getExpirationDailyInterestRepaymentType(), paymentExpirationInterest);
                transfers.add(transfer);
            }
        }
        return transfers;
    }

    @Override
    protected void processGrant(final Loan loan, final GrantLoanDTO params) {
        final Calendar grantDate = DateHelper.truncate(params.getDate() == null ? Calendar.getInstance() : params.getDate());
        final GrantLoanWithInterestDTO dto = (GrantLoanWithInterestDTO) params;
        final Calendar firstExpirationDate = DateHelper.truncate(dto.getFirstRepaymentDate());
        final int parcelCount = dto.getPaymentCount();

        final LocalSettings localSettings = settingsService.getLocalSettings();
        final MathContext mathContext = localSettings.getMathContext();

        // Calculate the total amount
        final BigDecimal totalAmount = loan.getParameters().calculateLoanTotal(dto.getAmount(), parcelCount, grantDate, firstExpirationDate, mathContext);

        // Build the parcels
        final List<LoanPayment> payments = new ArrayList<LoanPayment>(parcelCount);
        final BigDecimal parcelAmount = localSettings.round(totalAmount.divide(new BigDecimal(parcelCount), mathContext));
        for (int i = 0; i < parcelCount; i++) {
            final LoanPayment payment = new LoanPayment();
            final Calendar expiration = (Calendar) firstExpirationDate.clone();
            expiration.add(Calendar.MONTH, i);
            payment.setExpirationDate(expiration);
            payment.setAmount(parcelAmount);
            payments.add(payment);
        }
        loan.setTotalAmount(totalAmount);
        loan.setPayments(payments);
    }

    @Override
    protected BigDecimal retrieveTotalAmount(final ProjectionDTO params) {
        // Calculate the delay
        final Calendar grantDate = params.getDate();
        final Calendar firstRepaymentDate = params.getFirstExpirationDate();

        // Calculate the loan total
        final int paymentCount = params.getPaymentCount();
        final BigDecimal amount = params.getAmount();
        final MathContext mathContext = settingsService.getLocalSettings().getMathContext();
        return params.getTransferType().getLoan().calculateLoanTotal(amount, paymentCount, grantDate, firstRepaymentDate, mathContext);
    }

    /**
     * Returns a component amount for the given payment. For example: grant fee = 10.01, for 10 payments, it will be 1.00 for the first 9 and 1.01 for
     * the last one
     */
    private BigDecimal componentAmount(final LoanPayment payment, final BigDecimal totalComponentAmount) {
        if (totalComponentAmount.floatValue() < PRECISION_DELTA) {
            return BigDecimal.ZERO;
        }
        final int paymentCount = payment.getLoan().getPaymentCount();
        final int number = payment.getNumber();
        final boolean isLast = number == paymentCount;
        final LocalSettings localSettings = settingsService.getLocalSettings();
        final MathContext mathContext = localSettings.getMathContext();
        final BigDecimal perPaymentAmount = localSettings.round(totalComponentAmount.divide(new BigDecimal(paymentCount), mathContext));
        if (isLast) {
            return totalComponentAmount.subtract(perPaymentAmount.multiply(new BigDecimal(number).subtract(new BigDecimal(1))));
        } else {
            return perPaymentAmount;
        }
    }

    /**
     * Return a transfer for the given parameters, or null if not applicable
     */
    private TransferDTO generateComponentTransfer(final LoanPayment payment, final TransferDTO baseRepayment, final LoanParameters parameters, TransferType transferType, final BigDecimal amount) {
        final Loan loan = payment.getLoan();
        AccountOwner fromOwner;
        AccountOwner toOwner;
        transferType = fetchService.fetch(transferType, RelationshipHelper.nested(TransferType.Relationships.FROM, AccountType.Relationships.CURRENCY), TransferType.Relationships.TO);
        if (transferType.isFromSystem()) {
            fromOwner = SystemAccountOwner.instance();
            toOwner = loan.getMember();
        } else {
            fromOwner = loan.getMember();
            toOwner = SystemAccountOwner.instance();
        }
        final TransferDTO transfer = new TransferDTO();
        transfer.setAutomatic(true);
        transfer.setDate(baseRepayment.getDate());
        transfer.setTransferType(transferType);
        transfer.setFrom(accountService.getAccount(new AccountDTO(fromOwner, transferType.getFrom())));
        transfer.setTo(accountService.getAccount(new AccountDTO(toOwner, transferType.getTo())));
        transfer.setAmount(amount);
        transfer.setDescription(buildDescriptionForRepayment(transferType, payment));
        return transfer;
    }

    /**
     * Check if the given amount is greater than the minimum payment
     */
    private boolean generateComponentTransferForAmount(final BigDecimal amount) {
        return amount.subtract(paymentService.getMinimumPayment()).floatValue() > PRECISION_DELTA;
    }

}
