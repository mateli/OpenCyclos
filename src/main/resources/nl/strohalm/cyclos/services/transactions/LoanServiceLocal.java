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

import java.util.Calendar;

import nl.strohalm.cyclos.entities.accounts.Currency;
import nl.strohalm.cyclos.entities.accounts.external.ExternalTransfer;
import nl.strohalm.cyclos.entities.accounts.loans.Loan;
import nl.strohalm.cyclos.entities.accounts.loans.LoanPayment;
import nl.strohalm.cyclos.entities.exceptions.UnexpectedEntityException;
import nl.strohalm.cyclos.services.transactions.exceptions.AuthorizedPaymentInPastException;
import nl.strohalm.cyclos.services.transactions.exceptions.CreditsException;

/**
 * Local interface. It must be used only from other services.
 */
public interface LoanServiceLocal extends LoanService {

    /**
     * Generates alerts for expired loans
     */
    void alertExpiredLoans(Calendar time);

    /**
     * Discard the first open payment of the given loan, concealing with the given external transfer and returning it.
     * @throws UnexpectedEntityException When the given loan has no open payments
     */
    LoanPayment discardByExternalTransfer(Loan loan, ExternalTransfer externalTransfer) throws UnexpectedEntityException;

    /**
     * Return the open loans summary
     */
    TransactionSummaryVO getOpenLoansSummary(Currency currency);

    /**
     * Grants a loan from an accepted guarantee, returning it
     * @throws CreditsException The loan cannot be granted because for either lack of funds or exceeding an upper credit limit
     * @throws AuthorizedPaymentInPastException An authorized loan cannot be granted in past
     */
    Loan grantForGuarantee(GrantLoanDTO params, final boolean automaticAuthorization) throws CreditsException, AuthorizedPaymentInPastException;

    /**
     * Inserts a loan in the current database transaction
     */
    Loan insert(final GrantLoanDTO params);
}
