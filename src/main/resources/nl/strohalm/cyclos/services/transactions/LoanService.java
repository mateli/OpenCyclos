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

import java.util.List;

import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.loans.Loan;
import nl.strohalm.cyclos.entities.accounts.loans.LoanPayment;
import nl.strohalm.cyclos.entities.accounts.loans.LoanPaymentQuery;
import nl.strohalm.cyclos.entities.accounts.loans.LoanQuery;
import nl.strohalm.cyclos.entities.accounts.loans.LoanRepaymentAmountsDTO;
import nl.strohalm.cyclos.entities.accounts.transactions.Transfer;
import nl.strohalm.cyclos.entities.exceptions.UnexpectedEntityException;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.services.Service;
import nl.strohalm.cyclos.services.transactions.exceptions.AuthorizedPaymentInPastException;
import nl.strohalm.cyclos.services.transactions.exceptions.CreditsException;
import nl.strohalm.cyclos.services.transactions.exceptions.UpperCreditLimitReachedException;

/**
 * Service interface for loan management
 * @author luis
 */
public interface LoanService extends Service {

    /**
     * Calculates the payments for the given loan parameters
     */
    public List<LoanPayment> calculatePaymentProjection(ProjectionDTO params);

    /**
     * Returns a LoanPaymentAmountDTO for the given parameters
     */
    public LoanRepaymentAmountsDTO getLoanPaymentAmount(LoanPaymentDTO dto);

    /**
     * Discard the given loan, or the first open payment if just the loan is given
     * @throws UnexpectedEntityException When the given loan has no open payments
     */
    LoanPayment discard(LoanPaymentDTO dto) throws UnexpectedEntityException;

    /**
     * Grants a loan, returning it
     * @throws CreditsException The loan cannot be granted because for either lack of funds or exceeding an upper credit limit
     * @throws AuthorizedPaymentInPastException An authorized loan cannot be granted in past
     */
    Loan grant(GrantLoanDTO params) throws CreditsException, AuthorizedPaymentInPastException;

    /**
     * Loads a loan with the specified fetch
     */
    Loan load(Long id, Relationship... fetch);

    /**
     * Return the loan summary of the given member
     */
    TransactionSummaryVO loanSummary(Member member);

    /**
     * Marks an expired loan payment (or the first expired payment) as "in-process"
     * @throws UnexpectedEntityException When the given loan has no expired payments
     */
    Loan markAsInProcess(Loan loan) throws UnexpectedEntityException;

    /**
     * Marks an in-process loan (or the first in-process payment) as "recovered"
     * @throws UnexpectedEntityException When the given loan has no in-process payments
     */
    Loan markAsRecovered(Loan loan) throws UnexpectedEntityException;

    /**
     * Marks an in-process loan (or the first in-process payment) as "unrecoverable"
     * @throws UnexpectedEntityException When the given loan has no in-process payments
     */
    Loan markAsUnrecoverable(Loan loan) throws UnexpectedEntityException;

    /**
     * Returns a transaction summary of loan payments for the specified query
     */
    TransactionSummaryVO paymentsSummary(LoanPaymentQuery query);

    /**
     * Repay a loan, returning the generated transfer. The amount may be smaller than the payment amount, and at most, the exact payment amount. If
     * the loan has interest, the partial payment may only be between the base amount and the total amount, ie: loan of 1000, with interests, 1050.
     * has 5 payments 210, in which, 200 is the base amount and 10, interests. The payment may be between 0.01 and 200, or exactly 210. If there's
     * already another repayment of 150, the payment may be between 0.01 and 50, or 60.
     * @return The generated transfer. When loan has interest, the order of generated child transfers (if any) must be: grant fee repayment, monthly
     * interest repayment, expiration fee repayment, expiration daily interest repayment
     * @throws UpperCreditLimitReachedException The loan cannot be granted because it would make the system account go beyond the upper credit limit
     * @throws UnexpectedEntityException The loan is not open
     */
    Transfer repay(RepayLoanDTO params) throws UpperCreditLimitReachedException, UnexpectedEntityException;

    /**
     * Searches for loan payments
     */
    List<LoanPayment> search(LoanPaymentQuery vo);

    /**
     * Searches loans
     */
    List<Loan> search(LoanQuery query);

    /**
     * Validates a loan grant
     */
    void validate(GrantLoanDTO params);

}
