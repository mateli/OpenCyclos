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
package nl.strohalm.cyclos.controls.accounts.external.process;

import java.util.Iterator;
import java.util.List;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.entities.accounts.SystemAccountOwner;
import nl.strohalm.cyclos.entities.accounts.external.ExternalAccount;
import nl.strohalm.cyclos.entities.accounts.external.ExternalTransfer;
import nl.strohalm.cyclos.entities.accounts.loans.Loan;
import nl.strohalm.cyclos.entities.accounts.loans.LoanQuery;
import nl.strohalm.cyclos.entities.accounts.transactions.Transfer;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferQuery;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.services.transactions.LoanService;
import nl.strohalm.cyclos.services.transactions.PaymentService;

/**
 * Iterates over a collection of external transfers, retrieving additional data for each row
 * @author luis
 */
public class ExternalTransferToProcessVOIterator implements Iterator<ExternalTransferToProcessVO> {

    private final Iterator<ExternalTransfer> externalTransferVOs;
    private PaymentService                   paymentService;
    private LoanService                      loanService;
    private final ExternalAccount            externalAccount;

    public ExternalTransferToProcessVOIterator(final Iterator<ExternalTransfer> iterator, final ExternalAccount externalAccount) {
        externalTransferVOs = iterator;
        this.externalAccount = externalAccount;
    }

    public boolean hasNext() {
        return externalTransferVOs.hasNext();
    }

    public ExternalTransferToProcessVO next() {
        final ExternalTransfer transfer = externalTransferVOs.next();
        final ExternalTransferToProcessVO vo = new ExternalTransferToProcessVO();
        vo.setTransfer(transfer);
        switch (transfer.getType().getAction()) {
            case CONCILIATE_PAYMENT:
                vo.setTransfersToConciliate(resolveTransfers(transfer.getMember()));
                break;
            case DISCARD_LOAN:
                vo.setLoansToDiscard(resolveLoans(transfer.getMember()));
                break;
        }
        return vo;
    }

    public void remove() {
        throw new UnsupportedOperationException();
    }

    @Inject
    public void setLoanService(final LoanService loanService) {
        this.loanService = loanService;
    }

    @Inject
    public void setPaymentService(final PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    /**
     * Resolves the open loans for the given member
     */
    private List<Loan> resolveLoans(final Member member) {
        final LoanQuery query = new LoanQuery();
        query.fetch(Loan.Relationships.PAYMENTS);
        query.setStatus(Loan.Status.OPEN);
        query.setMember(member);
        return loanService.search(query);
    }

    /**
     * Resolve the transfers that need conciliation
     */
    private List<Transfer> resolveTransfers(final Member member) {
        final TransferQuery query = new TransferQuery();
        query.setOwner(member);
        query.setType(externalAccount.getMemberAccountType());
        query.setToAccountOwner(SystemAccountOwner.instance());
        query.setConciliated(false);
        query.setReverseOrder(true);
        return paymentService.search(query);
    }
}
