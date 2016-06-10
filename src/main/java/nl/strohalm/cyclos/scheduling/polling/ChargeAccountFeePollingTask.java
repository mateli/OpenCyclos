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
package nl.strohalm.cyclos.scheduling.polling;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.strohalm.cyclos.entities.accounts.SystemAccountOwner;
import nl.strohalm.cyclos.entities.accounts.fees.account.AccountFee;
import nl.strohalm.cyclos.entities.accounts.fees.account.AccountFee.InvoiceMode;
import nl.strohalm.cyclos.entities.accounts.fees.account.AccountFee.PaymentDirection;
import nl.strohalm.cyclos.entities.accounts.fees.account.AccountFeeLog;
import nl.strohalm.cyclos.entities.accounts.fees.account.MemberAccountFeeLog;
import nl.strohalm.cyclos.entities.accounts.transactions.Invoice;
import nl.strohalm.cyclos.entities.accounts.transactions.Transfer;
import nl.strohalm.cyclos.entities.alerts.SystemAlert;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.services.accountfees.AccountFeeServiceLocal;
import nl.strohalm.cyclos.services.alerts.AlertServiceLocal;
import nl.strohalm.cyclos.services.fetch.FetchServiceLocal;
import nl.strohalm.cyclos.services.settings.SettingsServiceLocal;
import nl.strohalm.cyclos.services.transactions.InvoiceServiceLocal;
import nl.strohalm.cyclos.services.transactions.PaymentServiceLocal;
import nl.strohalm.cyclos.services.transactions.TransferDTO;
import nl.strohalm.cyclos.services.transactions.exceptions.NotEnoughCreditsException;
import nl.strohalm.cyclos.utils.Amount;
import nl.strohalm.cyclos.utils.MessageProcessingHelper;
import nl.strohalm.cyclos.utils.Period;
import nl.strohalm.cyclos.utils.TransactionHelper;
import nl.strohalm.cyclos.utils.conversion.AmountConverter;
import nl.strohalm.cyclos.utils.conversion.CalendarConverter;
import nl.strohalm.cyclos.utils.conversion.UnitsConverter;
import nl.strohalm.cyclos.utils.logging.LoggingHandler;
import nl.strohalm.cyclos.utils.transaction.CurrentTransactionData;
import nl.strohalm.cyclos.utils.transaction.TransactionEndListener;

import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;

/**
 * A {@link PollingTask} which charges account fees
 * @author luis
 */
public class ChargeAccountFeePollingTask extends PollingTask {

    private Long                   logBeingCharged;
    private LoggingHandler         loggingHandler;
    private AccountFeeServiceLocal accountFeeService;
    private FetchServiceLocal      fetchService;
    private AlertServiceLocal      alertService;
    private InvoiceServiceLocal    invoiceService;
    private PaymentServiceLocal    paymentService;
    private SettingsServiceLocal   settingsService;
    private TransactionHelper      transactionHelper;

    public void setAccountFeeServiceLocal(final AccountFeeServiceLocal accountFeeService) {
        this.accountFeeService = accountFeeService;
    }

    public void setAlertServiceLocal(final AlertServiceLocal alertService) {
        this.alertService = alertService;
    }

    public void setFetchServiceLocal(final FetchServiceLocal fetchService) {
        this.fetchService = fetchService;
    }

    public void setInvoiceServiceLocal(final InvoiceServiceLocal invoiceService) {
        this.invoiceService = invoiceService;
    }

    public void setLoggingHandler(final LoggingHandler loggingHandler) {
        this.loggingHandler = loggingHandler;
    }

    public void setPaymentServiceLocal(final PaymentServiceLocal paymentService) {
        this.paymentService = paymentService;
    }

    public void setSettingsServiceLocal(final SettingsServiceLocal settingsService) {
        this.settingsService = settingsService;
    }

    public void setTransactionHelper(final TransactionHelper transactionHelper) {
        this.transactionHelper = transactionHelper;
    }

    @Override
    protected boolean runTask() {
        if (logBeingCharged == null) {
            AccountFeeLog feeLog = accountFeeService.nextLogToCharge();
            logBeingCharged = feeLog == null ? null : feeLog.getId();
            if (logBeingCharged == null) {
                // No current log to charge. Force sleep
                return false;
            } else {
                // Prepare the charge
                boolean firstTime = accountFeeService.prepareCharge(getFeeLog());
                // The first time this fee log is being charged, generate the alert
                if (firstTime) {
                    alertService.create(SystemAlert.Alerts.ACCOUNT_FEE_RUNNING, feeLog.getAccountFee().getName());
                }
                // Log that the fee log is being charged
                loggingHandler.logAccountFeeStarted(getFeeLog());
            }
        }

        // Charge each member
        AccountFeeLog feeLog = getFeeLog();
        final List<Member> toCharge = accountFeeService.nextMembersToCharge(feeLog);
        if (toCharge.isEmpty()) {
            // No more members to charge. Set the finish date
            feeLog.setFinishDate(Calendar.getInstance());
            feeLog.setRechargingFailed(false);
            accountFeeService.save(feeLog);

            // Log that the fee has finished
            loggingHandler.logAccountFeeFinished(feeLog);
            int errors = feeLog.getFailedMembers();
            if (errors == 0) {
                alertService.create(SystemAlert.Alerts.ACCOUNT_FEE_FINISHED, feeLog.getAccountFee().getName());
            } else {
                alertService.create(SystemAlert.Alerts.ACCOUNT_FEE_FINISHED_WITH_ERRORS, feeLog.getAccountFee().getName(), errors);
            }

            // Mark the current account fee log as finished
            logBeingCharged = null;
        } else {
            // Charge the next batch of members
            charge(feeLog, toCharge);
        }

        // Keep processing
        return true;
    }

    /**
     * Runs the charging within a transaction, charging the next batch of members
     */
    private void charge(final AccountFeeLog feeLog, final List<Member> toCharge) {
        // Iterate each member
        for (final Member member : toCharge) {
            BigDecimal amount = null;
            try {
                // Calculate the charging amount
                amount = accountFeeService.calculateAmount(feeLog, member);

                if (amount == null) {
                    // Null amount means that the member shouldn't be charged, ie, moved to another group
                    // Just remove from pending and decrement the total members
                    accountFeeService.removeFromPending(feeLog, member);
                    feeLog.setTotalMembers(feeLog.getTotalMembers() - 1);
                } else {
                    // Actually charge the member
                    doCharge(feeLog, amount, member);
                }
            } catch (Exception e) {
                final BigDecimal theAmount = amount;

                // When the transaction ends, make sure to record the error for this member
                CurrentTransactionData.addTransactionEndListener(new TransactionEndListener() {
                    @Override
                    protected void onTransactionEnd(final boolean commit) {
                        transactionHelper.runInCurrentThread(new TransactionCallbackWithoutResult() {
                            @Override
                            protected void doInTransactionWithoutResult(final TransactionStatus status) {
                                // Reload the account fee log
                                AccountFeeLog feeLog = getFeeLog();

                                // Increment the error counter if not recharging failures
                                if (!feeLog.isRechargingFailed()) {
                                    feeLog.setFailedMembers(feeLog.getFailedMembers() + 1);
                                }

                                // Add the charging result for this member with no transfer / invoice, so it won't be taken again in case of failures
                                accountFeeService.setChargingError(feeLog, member, theAmount);
                            }
                        });
                    }
                });

                // Break the batch in case of exceptions
                return;
            }
        }
    }

    private MemberAccountFeeLog doCharge(final AccountFeeLog feeLog, final BigDecimal amount, final Member member) {
        Transfer transfer = null;
        Invoice invoice = null;

        // Charge the account fee if the amount is ok
        if (amount.compareTo(BigDecimal.ZERO) > 0) {
            AccountFee fee = feeLog.getAccountFee();

            // Check who is paying: the member or the system
            if (fee.getPaymentDirection() == PaymentDirection.TO_SYSTEM) {
                // Member paying to system
                if (fee.getInvoiceMode() == InvoiceMode.ALWAYS) {
                    invoice = sendInvoice(fee, feeLog, member, amount);
                } else {
                    try {
                        transfer = insertTransfer(fee, feeLog, member, amount);
                    } catch (final NotEnoughCreditsException e) {
                        // Sends an Invoice to this member!
                        invoice = sendInvoice(fee, feeLog, member, amount);
                    }
                }
            } else {
                // System paying to member
                transfer = insertTransfer(fee, feeLog, member, amount);
            }
        }

        // Compute the result
        MemberAccountFeeLog result = accountFeeService.setChargingSuccess(feeLog, member, amount, transfer, invoice);

        // When recharging failed members, decrement the error counter
        if (feeLog.isRechargingFailed()) {
            feeLog.setFailedMembers(feeLog.getFailedMembers() - 1);
        }

        return result;
    }

    private AccountFeeLog getFeeLog() {
        return accountFeeService.loadLog(logBeingCharged);
    }

    /**
     * Returns a description for a transfer
     */
    private String getPaymentDescription(AccountFee fee, final AccountFeeLog feeLog, final Member member, final BigDecimal amount) {

        final LocalSettings localSettings = settingsService.getLocalSettings();
        final AmountConverter amountConverter = localSettings.getAmountConverter();
        final UnitsConverter unitsConverter = localSettings.getUnitsConverter(fee.getAccountType().getCurrency().getPattern());
        final CalendarConverter dateConverter = localSettings.getRawDateConverter();

        final Map<String, Object> values = new HashMap<String, Object>();
        final Amount amountValue = feeLog.getAmountValue();
        if (amountValue.getType() == Amount.Type.PERCENTAGE) {
            values.put("fee_amount", amountConverter.toString(amountValue));
        } else {
            values.put("fee_amount", unitsConverter.toString(amountValue.getValue()));
        }
        values.put("free_base", unitsConverter.toString(fee.getFreeBase()));
        values.put("result", unitsConverter.toString(amount));
        final Period period = feeLog.getPeriod();
        values.put("begin_date", dateConverter.toString(period == null ? null : period.getBegin()));
        values.put("end_date", dateConverter.toString(period == null ? null : period.getEnd()));

        fee = fetchService.fetch(fee, AccountFee.Relationships.TRANSFER_TYPE);

        return MessageProcessingHelper.processVariables(fee.getTransferType().getDescription(), values);
    }

    /**
     * Insert the transfer that charges a fee
     */
    private Transfer insertTransfer(final AccountFee fee, final AccountFeeLog feeLog, final Member member, final BigDecimal amount) {
        final TransferDTO dto = new TransferDTO();
        dto.setAutomatic(true);
        // Determine who pays
        if (fee.getPaymentDirection() == PaymentDirection.TO_SYSTEM) {
            // Member paying to system
            dto.setFromOwner(member);
            dto.setToOwner(SystemAccountOwner.instance());

            // We force the payment if either a volume fee or the invoice mode is never
            dto.setForced(fee.getChargeMode().isVolume() || fee.getInvoiceMode() == InvoiceMode.NEVER);
        } else {
            // System paying to member
            dto.setFromOwner(SystemAccountOwner.instance());
            dto.setToOwner(member);
        }
        dto.setAmount(amount);
        dto.setTransferType(fee.getTransferType());
        dto.setDescription(getPaymentDescription(fee, feeLog, member, amount));
        dto.setAccountFeeLog(feeLog);
        final Transfer transfer = (Transfer) paymentService.insertWithNotification(dto);
        loggingHandler.logAccountFeePayment(transfer);
        return transfer;
    }

    /**
     * Sends an invoice for a fee
     */
    private Invoice sendInvoice(final AccountFee fee, final AccountFeeLog feeLog, final Member member, final BigDecimal amount) {
        Invoice invoice = new Invoice();
        invoice.setFromMember(null);
        invoice.setFrom(SystemAccountOwner.instance());
        invoice.setTo(member);
        invoice.setAmount(amount);
        invoice.setTransferType(fee.getTransferType());
        invoice.setDescription(getPaymentDescription(fee, feeLog, member, amount));
        invoice.setAccountFeeLog(feeLog);
        invoice = invoiceService.sendAutomatically(invoice);
        loggingHandler.logAccountFeeInvoice(invoice);
        return invoice;
    }

}
