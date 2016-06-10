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
package nl.strohalm.cyclos.entities.members.brokerings;

import java.util.Calendar;

import nl.strohalm.cyclos.entities.Entity;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.fees.transaction.BrokerCommission;
import nl.strohalm.cyclos.services.transactions.TransactionSummaryVO;
import nl.strohalm.cyclos.utils.Amount;
import nl.strohalm.cyclos.utils.Period;

/**
 * Status about a brokering commission. Stores data about commissions paid from system to broker or commissions paid by members to brokers.
 * Transaction related to broker commission contracts are not included in the status
 * @author Jefferson Magno
 */
public class BrokeringCommissionStatus extends Entity {

    public static enum Relationships implements Relationship {
        BROKERING("brokering"), BROKER_COMMISSION("brokerCommission");

        private final String name;

        private Relationships(final String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    private static final long     serialVersionUID = -4791497274620475610L;

    private Brokering             brokering;
    private BrokerCommission      brokerCommission;
    private BrokerCommission.When when;
    private Amount                amount;
    private Period                period;
    private Calendar              creationDate;
    private Calendar              expiryDate;
    private Integer               maxCount;
    private TransactionSummaryVO  total;

    public Amount getAmount() {
        return amount;
    }

    public BrokerCommission getBrokerCommission() {
        return brokerCommission;
    }

    public Brokering getBrokering() {
        return brokering;
    }

    public Calendar getCreationDate() {
        return creationDate;
    }

    public Calendar getExpiryDate() {
        return expiryDate;
    }

    public Integer getMaxCount() {
        return maxCount;
    }

    public Period getPeriod() {
        return period;
    }

    public TransactionSummaryVO getTotal() {
        return total;
    }

    public BrokerCommission.When getWhen() {
        return when;
    }

    public void setAmount(final Amount amount) {
        this.amount = amount;
    }

    public void setBrokerCommission(final BrokerCommission brokerCommission) {
        this.brokerCommission = brokerCommission;
    }

    public void setBrokering(final Brokering brokering) {
        this.brokering = brokering;
    }

    public void setCreationDate(final Calendar creationDate) {
        this.creationDate = creationDate;
    }

    public void setExpiryDate(final Calendar expiryDate) {
        this.expiryDate = expiryDate;
    }

    public void setMaxCount(final Integer maxCount) {
        this.maxCount = maxCount;
    }

    public void setPeriod(final Period period) {
        this.period = period;
    }

    public void setTotal(final TransactionSummaryVO total) {
        this.total = total;
    }

    public void setWhen(final BrokerCommission.When when) {
        this.when = when;
    }

    @Override
    public String toString() {
        return "" + getId();
    }

}
