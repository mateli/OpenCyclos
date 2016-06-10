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

import java.util.List;

import nl.strohalm.cyclos.entities.accounts.external.ExternalTransfer;
import nl.strohalm.cyclos.entities.accounts.loans.Loan;
import nl.strohalm.cyclos.entities.accounts.transactions.Transfer;
import nl.strohalm.cyclos.utils.DataObject;

/**
 * View object with data for processing external transfers
 * @author luis
 */
public class ExternalTransferToProcessVO extends DataObject {

    private static final long serialVersionUID = 246103847511563332L;

    private ExternalTransfer  transfer;
    private List<Transfer>    transfersToConciliate;
    private List<Loan>        loansToDiscard;

    public List<Loan> getLoansToDiscard() {
        return loansToDiscard;
    }

    public ExternalTransfer getTransfer() {
        return transfer;
    }

    public List<Transfer> getTransfersToConciliate() {
        return transfersToConciliate;
    }

    public void setLoansToDiscard(final List<Loan> loansToDiscard) {
        this.loansToDiscard = loansToDiscard;
    }

    public void setTransfer(final ExternalTransfer transfer) {
        this.transfer = transfer;
    }

    public void setTransfersToConciliate(final List<Transfer> transfersToConciliate) {
        this.transfersToConciliate = transfersToConciliate;
    }
}
