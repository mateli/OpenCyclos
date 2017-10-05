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
import nl.strohalm.cyclos.entities.accounts.fees.transaction.BrokerCommission.When;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.utils.Amount;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * Default commission settings for a broker
 * @author Jefferson Magno
 */
@Table(name = "default_broker_commissions")
@javax.persistence.Entity
public class DefaultBrokerCommission extends Entity {

    public static enum Relationships implements Relationship {
        BROKER("broker"), BROKER_COMMISSION("brokerCommission");

        private final String name;

        private Relationships(final String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    public static enum Status {
        ACTIVE, INACTIVE, SUSPENDED,
    }

    private static final long serialVersionUID = -4791497274620475610L;

    @ManyToOne
    @JoinColumn(name = "broker_id")
	private Member            broker;

    @ManyToOne
    @JoinColumn(name = "broker_commission_id")
	private BrokerCommission  brokerCommission;

    @Column(name = "amount", precision = 15, scale = 6, nullable = false)
    private BigDecimal amountValue;

    @Column(name = "amount_type", length = 1)
    private Amount.Type amountType;

    @Column(name = "when_count")
    private Integer           count;

    @Column(name = "when_apply", nullable = false, length = 1)
	private When              when;

    @Column(name = "set_by_broker")
    private boolean           setByBroker;

    @Column(name = "suspended")
    private boolean           suspended;

    public Amount getAmount() {
        if (amountType == null || amountValue == null) {
            return null;
        }
        return new Amount(amountValue, amountType);
    }

    public Member getBroker() {
        return broker;
    }

    public BrokerCommission getBrokerCommission() {
        return brokerCommission;
    }

    public Integer getCount() {
        return count;
    }

    public Status getStatus() {
        if (suspended) {
            return Status.SUSPENDED;
        } else if (brokerCommission.isEnabled()) {
            return Status.ACTIVE;
        } else {
            return Status.INACTIVE;
        }
    }

    public When getWhen() {
        return when;
    }

    public boolean isSetByBroker() {
        return setByBroker;
    }

    public boolean isSuspended() {
        return suspended;
    }

    public void setAmount(final Amount amount) {
        if (amount == null) {
            amountValue = null;
            amountType = null;
        } else {
            amountValue = amount.getValue();
            amountType = amount.getType();
        }
    }

    public void setBroker(final Member broker) {
        this.broker = broker;
    }

    public void setBrokerCommission(final BrokerCommission brokerCommission) {
        this.brokerCommission = brokerCommission;
    }

    public void setCount(final Integer count) {
        this.count = count;
    }

    public void setSetByBroker(final boolean setByBroker) {
        this.setByBroker = setByBroker;
    }

    public void setSuspended(final boolean suspended) {
        this.suspended = suspended;
    }

    public void setWhen(final When when) {
        this.when = when;
    }

    @Override
    public String toString() {
        return getId() + " - " + broker;
    }

}
