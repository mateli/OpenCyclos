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
package nl.strohalm.cyclos.entities.accounts.transactions;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.Account;
import nl.strohalm.cyclos.entities.accounts.AccountOwner;

/**
 * A scheduled payment is a set of transfers grouped in a single logical entity.
 * @author luis, Jefferson Magno
 */
public class ScheduledPayment extends Payment {

    public static enum Relationships implements Relationship {
        TRANSFERS("transfers");
        private final String name;

        private Relationships(final String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }
    }

    private static final long serialVersionUID = 7335050802424888764L;
    private boolean           reserveAmount;
    private boolean           showToReceiver;
    private List<Transfer>    transfers;

    /**
     * Returns the date of the first not processed transfer
     */
    @Override
    public Calendar getActualDate() {
        Calendar actualDate = null;
        Calendar lastDate = null;
        for (final Transfer transfer : transfers) {
            lastDate = transfer.getDate();
            if (transfer.getProcessDate() == null) {
                actualDate = transfer.getDate();
                break;
            }
        }
        return actualDate != null ? actualDate : lastDate;
    }

    @Override
    public Account getActualFrom() {
        return getFrom();
    }

    @Override
    public AccountOwner getActualFromOwner() {
        return getFromOwner();
    }

    @Override
    public Account getActualTo() {
        return getTo();
    }

    @Override
    public AccountOwner getActualToOwner() {
        return getToOwner();
    }

    /**
     * Returns the index of the first not processed transfer
     */
    public Integer getFirstOpenPaymentIndex() {
        int index = 0;
        for (final Transfer transfer : transfers) {
            index++;
            if (transfer.getProcessDate() == null) {
                break;
            }
        }
        return (index > 0) ? index : null;
    }

    /**
     * Returns the first not processed transfer or null if all transfers had already been processed
     */
    public Transfer getFirstOpenTransfer() {
        Transfer firstOpenPayment = null;
        for (final Transfer transfer : transfers) {
            if (transfer.getProcessDate() == null) {
                firstOpenPayment = transfer;
                break;
            }
        }
        return firstOpenPayment;
    }

    @Override
    public Nature getNature() {
        return Nature.SCHEDULED_PAYMENT;
    }

    public int getNumberOfParcels() {
        return transfers.size();
    }

    public BigDecimal getProcessedPaymentAmount() {
        BigDecimal total = new BigDecimal(0);
        for (final Transfer transfer : transfers) {
            if (transfer.getProcessDate() != null) {
                total = total.add(transfer.getAmount());
            }
        }
        return total;
    }

    /**
     * Returns thenumber of processed payments
     */
    public int getProcessedPaymentCount() {
        int count = 0;
        for (final Transfer transfer : transfers) {
            if (transfer.getProcessDate() != null) {
                count++;
            }
        }
        return count;
    }

    public List<Transfer> getTransfers() {
        return transfers;
    }

    public boolean isReserveAmount() {
        return reserveAmount;
    }

    public boolean isShowToReceiver() {
        return showToReceiver;
    }

    public void setReserveAmount(final boolean reserveAmount) {
        this.reserveAmount = reserveAmount;
    }

    public void setShowToReceiver(final boolean showToReceiver) {
        this.showToReceiver = showToReceiver;
    }

    public void setTransfers(final List<Transfer> transfers) {
        this.transfers = transfers;
    }

}
