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

import nl.strohalm.cyclos.entities.Entity;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.fees.transaction.BrokerCommission;
import nl.strohalm.cyclos.entities.utils.Amount;
import nl.strohalm.cyclos.entities.utils.Period;
import nl.strohalm.cyclos.services.transactions.TransactionSummaryVO;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Calendar;

/**
 * Status about a brokering commission. Stores data about commissions paid from system to broker or commissions paid by members to brokers.
 * Transaction related to broker commission contracts are not included in the status
 * @author Jefferson Magno
 */
@Table(name = "brokering_commission_status")
@javax.persistence.Entity
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

    @ManyToOne
    @JoinColumn(name = "brokering_id")
	private Brokering             brokering;

    @ManyToOne
    @JoinColumn(name = "broker_commission_id")
	private BrokerCommission      brokerCommission;

    @Column(name = "when_apply", nullable = false, length = 1)
    private BrokerCommission.When when;

    @Column(name = "amount", precision = 15, scale = 6, nullable = false)
    private BigDecimal amountValue;

    @Column(name = "amount_type", length = 1)
    private Amount.Type amountType;

    @AttributeOverrides({
            @AttributeOverride(name = "begin", column=@Column(name="start_date")),
            @AttributeOverride(name = "end", column=@Column(name="end_date"))
    })
    @Embedded
	private Period                period;

    @Column(name = "creation_date")
    private Calendar              creationDate;

    @Column(name = "expiry_date")
    private Calendar              expiryDate;

    @Column(name = "max_count")
    private Integer               maxCount;

    @Column(name = "total_count", nullable = false)
    private int               totalCount;

    @Column(name = "total_amount", nullable = false, precision = 21, scale = 6)
    private BigDecimal        totalAmount;

    public Amount getAmount() {
        if (amountType == null || amountValue == null) {
            return null;
        }
        return new Amount(amountValue, amountType);
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
        return new TransactionSummaryVO(totalCount, totalAmount);
    }

    public BrokerCommission.When getWhen() {
        return when;
    }

    public void setAmount(final Amount amount) {
        if (amount == null) {
            amountValue = null;
            amountType = null;
        } else {
            amountValue = amount.getValue();
            amountType = amountType;
        }
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
        if (total == null) {
            totalCount = 0;
            totalAmount = null;
        } else {
            totalCount = total.getCount();
            totalAmount = total.getAmount();
        }
    }

    public void setWhen(final BrokerCommission.When when) {
        this.when = when;
    }

    @Override
    public String toString() {
        return "" + getId();
    }

}
