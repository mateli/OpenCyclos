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

import nl.strohalm.cyclos.entities.accounts.loans.Loan;
import nl.strohalm.cyclos.entities.accounts.loans.LoanPayment;
import nl.strohalm.cyclos.entities.accounts.loans.LoanRepaymentAmountsDTO;

/**
 * Handles specific loan type details
 * @author luis
 */
public interface LoanHandler {
    /**
     * Builds a loan with all payments from a grant dto
     */
    Loan buildLoan(GrantLoanDTO dto);

    /**
     * Builds a list of transfers for a loan repayment
     */
    List<TransferDTO> buildTransfersForRepayment(RepayLoanDTO dto, LoanRepaymentAmountsDTO paymentDto);

    /**
     * Calculates the projection for the given payments
     */
    List<LoanPayment> calculatePaymentProjection(ProjectionDTO params);
}
