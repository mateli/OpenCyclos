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
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import nl.strohalm.cyclos.access.AdminMemberPermission;
import nl.strohalm.cyclos.access.BrokerPermission;
import nl.strohalm.cyclos.access.MemberPermission;
import nl.strohalm.cyclos.access.OperatorPermission;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.Account;
import nl.strohalm.cyclos.entities.accounts.AccountOwner;
import nl.strohalm.cyclos.entities.accounts.AccountType;
import nl.strohalm.cyclos.entities.accounts.MemberAccount;
import nl.strohalm.cyclos.entities.accounts.transactions.Invoice;
import nl.strohalm.cyclos.entities.accounts.transactions.Payment;
import nl.strohalm.cyclos.entities.accounts.transactions.Transfer;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferQuery;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferType;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.entities.exceptions.UnexpectedEntityException;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.exceptions.PermissionDeniedException;
import nl.strohalm.cyclos.services.BaseServiceSecurity;
import nl.strohalm.cyclos.services.accounts.AccountDTO;
import nl.strohalm.cyclos.services.accounts.AccountServiceLocal;
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
import nl.strohalm.cyclos.webservices.utils.PaymentHelper;

/**
 * Security for {@link PaymentService}
 * 
 * @author luis
 */
public class PaymentServiceSecurity extends BaseServiceSecurity implements PaymentService {

    private PaymentServiceLocal paymentService;
    private AccountServiceLocal accountService;
    private InvoiceServiceLocal invoiceService;
    private PaymentHelper       paymentHelper;

    @Override
    public List<ScheduledPaymentDTO> calculatePaymentProjection(final ProjectionDTO params) {
        // No security is needed here
        return paymentService.calculatePaymentProjection(params);
    }

    @Override
    public boolean canChargeback(final Transfer transfer, final boolean ignorePendingPayment) {
        // No security is needed here
        return paymentService.canChargeback(transfer, ignorePendingPayment);
    }

    @Override
    public Transfer chargeback(final Transfer transfer) throws UnexpectedEntityException {
        checkChargeback(transfer);
        return paymentService.chargeback(transfer);
    }

    @Override
    public Payment doPayment(final DoPaymentDTO params) throws NotEnoughCreditsException, MaxAmountPerDayExceededException, UnexpectedEntityException, UpperCreditLimitReachedException, AuthorizedPaymentInPastException {
        checkPayment(params);
        return paymentService.doPayment(params);
    }

    @Override
    public AccountHistoryResultPage getAccountHistoryResultPage(final AccountHistoryParams params) {
        TransferQuery query = paymentHelper.toTransferQuery(params);
        checkQuery(query);
        return paymentService.getAccountHistoryResultPage(params);
    }

    @Override
    public AccountHistoryTransferVO getAccountHistoryTransferVO(final Long id) {
        check(load(id));
        return paymentService.getAccountHistoryTransferVO(id);
    }

    @Override
    public ConversionSimulationDTO getDefaultConversionDTO(final MemberAccount account, final List<TransferType> transferTypes) {
        Member member = fetchService.fetch(account, MemberAccount.Relationships.MEMBER).getMember();
        checkConversionSimulation(member);
        return paymentService.getDefaultConversionDTO(account, transferTypes);
    }

    @Override
    public BigDecimal getMinimumPayment() {
        // No permission check is needed
        return paymentService.getMinimumPayment();
    }

    @Override
    public StatisticalResultDTO getSimulateConversionGraph(final ConversionSimulationDTO dto) {
        Member member = fetchService.fetch(dto.getAccount(), MemberAccount.Relationships.MEMBER).getMember();
        checkConversionSimulation(member);
        return paymentService.getSimulateConversionGraph(dto);
    }

    @Override
    public boolean hasPermissionsToChargeback(final Transfer transfer) {
        return paymentService.hasPermissionsToChargeback(transfer);
    }

    @Override
    public boolean isVisible(final Payment payment) {
        return paymentService.isVisible(payment);
    }

    @Override
    public Transfer load(final Long id, final Relationship... fetch) throws EntityNotFoundException {
        Set<Relationship> relationships = new HashSet<Relationship>(Arrays.asList(fetch));
        relationships.add(Payment.Relationships.FROM);
        relationships.add(Payment.Relationships.TO);
        Transfer transfer = paymentService.load(id, relationships.toArray(new Relationship[relationships.size()]));
        check(transfer);
        return transfer;
    }

    @Override
    public List<Transfer> search(final TransferQuery query) {
        checkQuery(query);
        return paymentService.search(query);
    }

    public void setAccountServiceLocal(final AccountServiceLocal accountService) {
        this.accountService = accountService;
    }

    public void setInvoiceServiceLocal(final InvoiceServiceLocal invoiceService) {
        this.invoiceService = invoiceService;
    }

    public void setPaymentHelper(final PaymentHelper paymentHelper) {
        this.paymentHelper = paymentHelper;
    }

    public void setPaymentServiceLocal(final PaymentServiceLocal paymentService) {
        this.paymentService = paymentService;
    }

    @Override
    public TransactionFeePreviewForRatesDTO simulateConversion(final ConversionSimulationDTO dto) {
        Member member = fetchService.fetch(dto.getAccount(), MemberAccount.Relationships.MEMBER).getMember();
        checkConversionSimulation(member);
        return paymentService.simulateConversion(dto);
    }

    @Override
    public Payment simulatePayment(final DoPaymentDTO params) throws NotEnoughCreditsException, MaxAmountPerDayExceededException, UnexpectedEntityException, UpperCreditLimitReachedException, AuthorizedPaymentInPastException {
        checkPayment(params.getFrom(), params.getTo());
        return paymentService.simulatePayment(params);
    }

    @Override
    public void validate(final ConversionSimulationDTO dto) {
        // No permission needed for validating
        paymentService.validate(dto);
    }

    @Override
    public void validate(final DoPaymentDTO payment) {
        // No permission needed for validating
        paymentService.validate(payment);
    }

    @Override
    public boolean wouldRequireAuthorization(final DoPaymentDTO dto) {
        checkPayment(dto.getFrom(), dto.getTo());
        return paymentService.wouldRequireAuthorization(dto);
    }

    @Override
    public boolean wouldRequireAuthorization(final Invoice invoice) {
        if (!invoiceService.canAccept(invoice)) {
            throw new PermissionDeniedException();
        }
        return paymentService.wouldRequireAuthorization(invoice);
    }

    @Override
    public boolean wouldRequireAuthorization(Transfer transfer) {
        transfer = fetchService.fetch(transfer, Payment.Relationships.FROM, Payment.Relationships.TO);
        checkPayment(transfer.getFromOwner(), transfer.getToOwner());
        return paymentService.wouldRequireAuthorization(transfer);
    }

    @Override
    public boolean wouldRequireAuthorization(final TransferType transferType, final BigDecimal amount, final AccountOwner from) {
        // Nothing to check
        return paymentService.wouldRequireAuthorization(transferType, amount, from);
    }

    private void check(final Payment payment) {
        if (payment != null && !isVisible(payment)) {
            throw new PermissionDeniedException();
        }
    }

    private void checkAccountAccess(final AccountOwner owner, final AccountType type) {
        if (owner == null) {
            return;
        }
        boolean ok;
        if (type == null) {
            ok = accountService.canViewAccountsOf(owner);
        } else {
            Account account = accountService.getAccount(new AccountDTO(owner, type));
            ok = accountService.canView(account);
        }
        if (!ok) {
            throw new PermissionDeniedException();
        }
    }

    private void checkChargeback(final Transfer transfer) {
        if (!hasPermissionsToChargeback(transfer)) {
            throw new PermissionDeniedException();
        }
    }

    private void checkConversionSimulation(final Member member) {
    }

    private void checkPayment(final AccountOwner from, final AccountOwner to) {
        if (!paymentService.canMakePayment(from, to, null)) {
            throw new PermissionDeniedException();
        }
    }

    private void checkPayment(final DoPaymentDTO params) {
        if (params.getDate() != null) {
            // Payments in past require a separate permission check
            permissionService.permission().admin(AdminMemberPermission.PAYMENTS_PAYMENT_WITH_DATE).check();
        }

        // we dont's set the transfer type as a parameter because it will be checked later by the payment service
        // when the payment is done.
        checkPayment(params.getFrom(), params.getTo());
    }

    private void checkQuery(final TransferQuery query) {
        if (query.getOwner() == null) {
            throw new PermissionDeniedException();
        }
        // Check all possible use cases
        checkAccountAccess(query.getOwner(), query.getType());
        checkAccountAccess(query.getFromAccountOwner(), query.getFromAccountType());
        checkAccountAccess(query.getToAccountOwner(), query.getToAccountType());
        check(query.getParent());
    }
}
