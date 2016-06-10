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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.strohalm.cyclos.entities.accounts.SystemAccountOwner;
import nl.strohalm.cyclos.entities.accounts.loans.Loan;
import nl.strohalm.cyclos.entities.accounts.loans.Loan.Type;
import nl.strohalm.cyclos.entities.accounts.loans.LoanGroup;
import nl.strohalm.cyclos.entities.accounts.loans.LoanParameters;
import nl.strohalm.cyclos.entities.accounts.loans.LoanPayment;
import nl.strohalm.cyclos.entities.accounts.loans.LoanRepaymentAmountsDTO;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferType;
import nl.strohalm.cyclos.entities.exceptions.UnexpectedEntityException;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.services.accounts.AccountDTO;
import nl.strohalm.cyclos.services.accounts.AccountServiceLocal;
import nl.strohalm.cyclos.services.fetch.FetchServiceLocal;
import nl.strohalm.cyclos.services.settings.SettingsServiceLocal;
import nl.strohalm.cyclos.utils.MessageProcessingHelper;
import nl.strohalm.cyclos.utils.access.LoggedUser;
import nl.strohalm.cyclos.utils.conversion.UnitsConverter;

import org.apache.commons.lang.StringUtils;

/**
 * Base implementation for loan handler
 * @author luis
 */
public abstract class BaseLoanHandler implements LoanHandler {

    protected static final float   PRECISION_DELTA = 0.0001F;
    protected FetchServiceLocal    fetchService;
    protected SettingsServiceLocal settingsService;
    protected AccountServiceLocal  accountService;
    protected LoanServiceLocal     loanService;
    protected PaymentServiceLocal  paymentService;
    private final Loan.Type        type;

    public BaseLoanHandler(final Type type) {
        this.type = type;
    }

    @Override
    public final Loan buildLoan(final GrantLoanDTO params) {

        // Get some data
        final TransferType transferType = params.getTransferType();
        final LoanParameters loanParameters = transferType.getLoan().clone();
        final LoanGroup loanGroup = fetchService.fetch(params.getLoanGroup(), LoanGroup.Relationships.MEMBERS);

        // Build the loan
        final Loan loan = new Loan();
        loan.setLoanGroup(loanGroup);
        loan.setParameters(loanParameters);
        // Associate the member(s) to the loan
        loan.setToMembers(new ArrayList<Member>());
        if (loanGroup != null) {
            final Collection<Member> members = loanGroup.getMembers();
            if (!members.contains(params.getMember())) {
                throw new UnexpectedEntityException();
            }
            loan.getToMembers().addAll(members);
        } else {
            loan.getToMembers().add(params.getMember());
        }

        // Process specific grant parameters for the loan
        processGrant(loan, params);

        return loan;
    }

    @Override
    public List<TransferDTO> buildTransfersForRepayment(final RepayLoanDTO dto, final LoanRepaymentAmountsDTO paymentDto) {
        final Loan loan = dto.getLoan();
        final LoanPayment payment = loan.getFirstOpenPayment();
        final LoanParameters parameters = loan.getTransfer().getType().getLoan();
        final TransferType repaymentType = parameters.getRepaymentType();
        final TransferDTO transfer = new TransferDTO();
        transfer.setCustomValues(dto.getCustomValues());
        transfer.setAutomatic(true);
        if (dto.getDate() != null) {
            transfer.setDate(dto.getDate());
        }
        if (LoggedUser.member() == null) {
            transfer.setFrom(accountService.getAccount(new AccountDTO(loan.getMember(), repaymentType.getFrom())));
        } else {
            transfer.setFrom(accountService.getAccount(new AccountDTO(LoggedUser.member(), repaymentType.getFrom())));
        }
        transfer.setTo(accountService.getAccount(new AccountDTO(SystemAccountOwner.instance(), repaymentType.getTo())));
        transfer.setAmount(dto.getAmount());
        transfer.setTransferType(repaymentType);
        transfer.setLoanPayment(payment);
        transfer.setDescription(buildDescriptionForRepayment(repaymentType, payment));
        return Collections.singletonList(transfer);
    }

    public Loan.Type getType() {
        return type;
    }

    public void setAccountServiceLocal(final AccountServiceLocal accountService) {
        this.accountService = accountService;
    }

    public void setFetchServiceLocal(final FetchServiceLocal fetchService) {
        this.fetchService = fetchService;
    }

    public void setLoanServiceLocal(final LoanServiceLocal loanService) {
        this.loanService = loanService;
    }

    public void setPaymentServiceLocal(final PaymentServiceLocal paymentService) {
        this.paymentService = paymentService;
    }

    public void setSettingsServiceLocal(final SettingsServiceLocal settingsService) {
        this.settingsService = settingsService;
    }

    protected String buildDescriptionForRepayment(final TransferType repaymentType, final LoanPayment payment) {
        final String description = StringUtils.trimToEmpty(repaymentType.getDescription());
        final UnitsConverter uc = settingsService.getLocalSettings().getUnitsConverter(repaymentType.getFrom().getCurrency().getPattern());
        final Loan loan = payment.getLoan();
        final Map<String, Object> values = new HashMap<String, Object>();
        values.put("loanAmount", uc.toString(loan.getTransfer().getAmount()));
        values.put("loanTotalAmount", uc.toString(loan.getTotalAmount()));
        values.put("paymentAmount", uc.toString(payment.getAmount()));
        values.put("paymentNumber", String.valueOf(payment.getIndex()));
        return MessageProcessingHelper.processVariables(description, values);
    }

    /**
     * Should be overriden to perform specific processing for loan grant
     */
    protected abstract void processGrant(Loan loan, GrantLoanDTO params);

}
