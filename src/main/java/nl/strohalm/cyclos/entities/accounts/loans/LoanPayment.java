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
package nl.strohalm.cyclos.entities.accounts.loans;

import nl.strohalm.cyclos.entities.Entity;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.external.ExternalTransfer;
import nl.strohalm.cyclos.entities.accounts.transactions.Transfer;
import nl.strohalm.cyclos.utils.StringValuedEnum;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Collection;

/**
 * A loan payment (parcel)
 * @author luis
 */
@Table(name = "loan_payments")
@javax.persistence.Entity
public class LoanPayment extends Entity {

    public static enum Relationships implements Relationship {
        LOAN("loan"), TRANSFERS("transfers");
        private final String name;

        private Relationships(final String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    public static enum Status implements StringValuedEnum {
        OPEN("O"), REPAID("R"), DISCARDED("D"), EXPIRED("E"), IN_PROCESS("P"), UNRECOVERABLE("U"), RECOVERED("V");

        private final String value;

        private Status(final String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public boolean isClosed() {
            return !isOpen();
        }

        public boolean isOpen() {
            return this == OPEN || this == EXPIRED || this == IN_PROCESS;
        }
    }

    private static final long    serialVersionUID = 3972322253540292312L;

    @Column(name = "expiration_date", nullable = false)
    private Calendar             expirationDate;

    @Column(name = "payment_index", nullable = false)
    private int                  index;

    @ManyToOne
    @JoinColumn(name = "loan_id", nullable = false)
	private Loan                 loan;

    @Column(name = "repaid_amount", nullable = false, precision = 15, scale = 6)
    private BigDecimal           repaidAmount     = BigDecimal.ZERO;

    @Column(name = "repayment_date")
    private Calendar             repaymentDate;

    @Column(name = "status", nullable = false, length = 1)
	private Status               status           = Status.OPEN;

    @OneToMany(mappedBy = "loanPayment")
	private Collection<Transfer> transfers;

    @Column(name = "amount", nullable = false, precision = 15, scale = 6)
    private BigDecimal           amount;

    @ManyToOne
    @JoinColumn(name = "external_transfer_id")
	private ExternalTransfer     externalTransfer;

	public BigDecimal getAmount() {
        return amount;
    }

    public BigDecimal getDiscardedAmount() {
        if (status != Status.DISCARDED) {
            return BigDecimal.ZERO;
        }
        try {
            return amount.subtract(repaidAmount);
        } catch (final NullPointerException e) {
            return BigDecimal.ZERO;
        }
    }

    public Calendar getExpirationDate() {
        return expirationDate;
    }

    public ExternalTransfer getExternalTransfer() {
        return externalTransfer;
    }

    public int getIndex() {
        return index;
    }

    public Loan getLoan() {
        return loan;
    }

    public int getNumber() {
        return index + 1;
    }

    public BigDecimal getRemainingAmount() {
        if (status == null || status.isClosed()) {
            return BigDecimal.ZERO;
        }
        return amount.subtract(repaidAmount);
    }

    public BigDecimal getRepaidAmount() {
        return repaidAmount;
    }

    public Calendar getRepaymentDate() {
        return repaymentDate;
    }

    public Status getStatus() {
        return status;
    }

    public Collection<Transfer> getTransfers() {
        return transfers;
    }

    public void setAmount(final BigDecimal value) {
        amount = value;
    }

    public void setExpirationDate(final Calendar expirationDate) {
        this.expirationDate = expirationDate;
    }

    public void setExternalTransfer(final ExternalTransfer externalTransfer) {
        this.externalTransfer = externalTransfer;
    }

    public void setIndex(final int index) {
        this.index = index;
    }

    public void setLoan(final Loan loan) {
        this.loan = loan;
    }

    public void setRepaidAmount(final BigDecimal repaidValue) {
        repaidAmount = repaidValue;
    }

    public void setRepaymentDate(final Calendar repaymentDate) {
        this.repaymentDate = repaymentDate;
    }

    public void setStatus(final Status status) {
        this.status = status;
    }

    public void setTransfers(final Collection<Transfer> transfers) {
        this.transfers = transfers;
    }

    @Override
    public String toString() {
        return getId() + " - number: " + (index + 1) + " of " + loan;
    }

}
