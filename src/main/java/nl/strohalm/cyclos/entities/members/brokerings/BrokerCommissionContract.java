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
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.entities.utils.Amount;
import nl.strohalm.cyclos.entities.utils.Period;
import nl.strohalm.cyclos.utils.StringValuedEnum;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Map;

/**
 * Broker commission contract
 * @author Jefferson Magno
 */
@Table(name = "broker_commission_contracts")
@javax.persistence.Entity
public class BrokerCommissionContract extends Entity {

    public static enum Relationships implements Relationship {
        BROKERING("brokering"), BROKER_COMMISSION("brokerCommission");

        private final String name;

        private Relationships(final String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }
    }

    public static enum Status implements StringValuedEnum {
        PENDING("P"), ACCEPTED("T"), DENIED("D"), EXPIRED("E"), ACTIVE("A"), CLOSED("L"), SUSPENDED("S"), CANCELLED("C");

        private String value;

        private Status(final String value) {
            this.value = value;
        }

        @Override
        public String getValue() {
            return value;
        }
    }

    private static final long serialVersionUID = -4791497274620475610L;

    @ManyToOne
    @JoinColumn(name = "brokering_id")
	private Brokering         brokering;

    @ManyToOne
    @JoinColumn(name = "broker_commission_id")
	private BrokerCommission  brokerCommission;

    @AttributeOverrides({
            @AttributeOverride(name = "begin", column=@Column(name="start_date")),
            @AttributeOverride(name = "end", column=@Column(name="end_date"))
    })
    @Embedded
	private Period            period;

    @Column(name = "amount_value", precision = 15, scale = 6, nullable = false)
    private BigDecimal amountValue;

    @Column(name = "amount_type", length = 1)
    private Amount.Type amountType;

    @Column(name = "status", nullable = false, length = 1)
	private Status            status;

    @Column(name = "status_before_suspension", length = 1)
	private Status            statusBeforeSuspension;

    @ManyToOne
    @JoinColumn(name = "cancelled_by_id")
	private Element           cancelledBy;

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

    public Element getCancelledBy() {
        return cancelledBy;
    }

    public Period getPeriod() {
        return period;
    }

    public Status getStatus() {
        return status;
    }

    public Status getStatusBeforeSuspension() {
        return statusBeforeSuspension;
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

    public void setBrokerCommission(final BrokerCommission brokerCommission) {
        this.brokerCommission = brokerCommission;
    }

    public void setBrokering(final Brokering brokering) {
        this.brokering = brokering;
    }

    public void setCancelledBy(final Element cancelledBy) {
        this.cancelledBy = cancelledBy;
    }

    public void setPeriod(final Period period) {
        this.period = period;
    }

    public void setStatus(final Status status) {
        this.status = status;
    }

    public void setStatusBeforeSuspension(final Status statusBeforeSuspension) {
        this.statusBeforeSuspension = statusBeforeSuspension;
    }

    @Override
    public String toString() {
        return "" + getId();
    }

    @Override
    protected void appendVariableValues(final Map<String, Object> variables, final LocalSettings localSettings) {
        variables.put("broker", getBrokering().getBroker().getName());
        variables.put("broker_login", getBrokering().getBroker().getUsername());
        variables.put("member", getBrokering().getBrokered().getName());
        variables.put("member_login", getBrokering().getBrokered().getUsername());
        variables.put("start_date", localSettings.getRawDateConverter().toString(getPeriod().getBegin()));
        variables.put("end_date", localSettings.getRawDateConverter().toString(getPeriod().getEnd()));
        variables.put("amount", localSettings.getAmountConverter().toString(getAmount()));
    }

}
