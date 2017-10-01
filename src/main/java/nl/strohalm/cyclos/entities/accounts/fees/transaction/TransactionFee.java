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
package nl.strohalm.cyclos.entities.accounts.fees.transaction;

import nl.strohalm.cyclos.entities.Entity;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.transactions.Transfer;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferType;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.utils.Amount;
import nl.strohalm.cyclos.utils.StringValuedEnum;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Inheritance;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Collection;

/**
 * A fee is applied on transfers
 * 
 * @author luis
 */
@Cacheable
@Inheritance
@DiscriminatorColumn(name = "subclass")
@Table(name = "transaction_fees")
@javax.persistence.Entity
public abstract class TransactionFee extends Entity {

    public static enum ChargeType implements StringValuedEnum {
        FIXED("F"), PERCENTAGE("P"), A_RATE("A"), D_RATE("D"), MIXED_A_D_RATES("M");

        public static ChargeType from(final Amount.Type type) {
            if (type == null) {
                return null;
            }
            switch (type) {
                case FIXED:
                    return FIXED;
                case PERCENTAGE:
                    return PERCENTAGE;
            }
            return null;
        }

        private final String value;

        private ChargeType(final String value) {
            this.value = value;
        }

        @Override
        public String getValue() {
            return value;
        }

        public Amount.Type toAmountType() {
            switch (this) {
                case FIXED:
                    return Amount.Type.FIXED;
                case PERCENTAGE:
                case A_RATE:
                case D_RATE:
                case MIXED_A_D_RATES:
                    return Amount.Type.PERCENTAGE;
                default:
                    return null;
            }
        }
    }

    public static enum Nature implements StringValuedEnum {
        SIMPLE("S"), BROKER("B");

        private final String value;

        private Nature(final String value) {
            this.value = value;
        }

        public Class<? extends TransactionFee> getFeeClass() {
            if (this == SIMPLE) {
                return SimpleTransactionFee.class;
            } else {
                return BrokerCommission.class;
            }
        }

        @Override
        public String getValue() {
            return value;
        }
    }

    public static enum Relationships implements Relationship {
        GENERATED_TRANSFER_TYPE("generatedTransferType"), ORIGINAL_TRANSFER_TYPE("originalTransferType"), TRANSFERS("transfers"), FROM_GROUPS("fromGroups"), TO_GROUPS("toGroups"), FROM_FIXED_MEMBER("fromFixedMember");
        private final String name;

        private Relationships(final String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }
    }

    public static enum Subject implements StringValuedEnum {
        SYSTEM("sys"), SOURCE("src"), SOURCE_BROKER("sbr"), DESTINATION("dst"), DESTINATION_BROKER("dbr"), FIXED_MEMBER("mem");
        private final String value;

        private Subject(final String value) {
            this.value = value;
        }

        @Override
        public String getValue() {
            return value;
        }
    }

    private static final long       serialVersionUID = -3840663900697716307L;

    @Column(name = "name", nullable = false, length = 100)
    private String                  name;

    @Column(name = "description", columnDefinition = "text")
    private String                  description;

    @Column(name = "payer", nullable = false, length = 3)
	private Subject                 payer;

    @Column(name = "enabled", nullable = false)
    private boolean                 enabled;

    @ManyToOne
    @JoinColumn(name = "original_type_id", nullable = false)
	private TransferType            originalTransferType;

    @ManyToOne
    @JoinColumn(name = "generated_type_id", nullable = false)
	private TransferType            generatedTransferType;

    @Column(name = "amount_type", nullable = false, length = 1)
	private ChargeType              chargeType;

    @Column(name = "amount", precision = 15, scale = 6)
    private BigDecimal              value;

    @Column(name = "max_fixed_value", precision = 15, scale = 6)
    private BigDecimal              maxFixedValue;

    @Column(name = "max_percentage_value", precision = 15, scale = 6)
    private BigDecimal              maxPercentageValue;

    @Column(name = "initial_amount", precision = 15, scale = 6)
    private BigDecimal              initialAmount;

    @Column(name = "final_amount", precision = 15, scale = 6)
    private BigDecimal              finalAmount;

    @Column(name = "deduct_amount", nullable = false)
    private boolean                 deductAmount;

    @OneToMany(mappedBy = "transactionFee")
	private Collection<Transfer>    transfers;

    @Column(name = "from_all_groups", nullable = false)
    private boolean                 fromAllGroups    = true;

    @ManyToMany(targetEntity = MemberGroup.class)
    @JoinTable(name = "groups_from_transaction_fees",
            joinColumns = @JoinColumn(name = "transaction_fee_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id"))
	private Collection<MemberGroup> fromGroups;

    @Column(name = "to_all_groups", nullable = false)
    private boolean                 toAllGroups      = true;

    @ManyToMany
    @JoinTable(name = "groups_to_transaction_fees",
            joinColumns = @JoinColumn(name = "transaction_fee_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id"))
	private Collection<MemberGroup> toGroups;

    @ManyToOne
    @JoinColumn(name = "from_member_id")
	private Member                  fromFixedMember;

    protected TransactionFee() {
	}

	public Amount getAmount() {
        if (chargeType == null || value == null) {
            return null;
        }
        return new Amount(value, chargeType.toAmountType());
    }

    public ChargeType getChargeType() {
        return chargeType;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getFinalAmount() {
        return finalAmount;
    }

    public Member getFromFixedMember() {
        return fromFixedMember;
    }

    public Collection<MemberGroup> getFromGroups() {
        return fromGroups;
    }

    public TransferType getGeneratedTransferType() {
        return generatedTransferType;
    }

    public BigDecimal getInitialAmount() {
        return initialAmount;
    }

    public BigDecimal getMaxFixedValue() {
        return maxFixedValue;
    }

    public BigDecimal getMaxPercentageValue() {
        return maxPercentageValue;
    }

    @Override
    public String getName() {
        return name;
    }

    public abstract Nature getNature();

    public TransferType getOriginalTransferType() {
        return originalTransferType;
    }

    public Subject getPayer() {
        return payer;
    }

    public Collection<MemberGroup> getToGroups() {
        return toGroups;
    }

    public Collection<Transfer> getTransfers() {
        return transfers;
    }

    public BigDecimal getValue() {
        return value;
    }

    public boolean isDeductAmount() {
        return deductAmount;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public boolean isFromAllGroups() {
        return fromAllGroups;
    }

    public boolean isFromMember() {
        return !isFromSystem();
    }

    public boolean isFromSystem() {
        return generatedTransferType.isFromSystem();
    }

    public boolean isToAllGroups() {
        return toAllGroups;
    }

    public void setAmount(final Amount amount) {
        if (amount == null) {
            value = null;
            chargeType = null;
        } else {
            value = amount.getValue();
            chargeType = ChargeType.from(amount.getType());
        }
    }

    /**
     * for rates, the amount needs to be set without resetting the charge type. So call this one only in case of rates.
     * @throws IllegalArgumentException in case the method is called outside of a A- or D-rated context, that is: when the chargeType is not related
     * to A- or D-rate.
     */
    public void setAmountForRates(final Amount amount) {
        if (chargeType != ChargeType.A_RATE && chargeType != ChargeType.D_RATE && chargeType != ChargeType.MIXED_A_D_RATES) {
            throw new IllegalArgumentException("TransactionFee.setAmountForRates can only be called in case of a charge type related to A-rate or D-rate.");
        }
        if (amount == null) {
            value = null;
        } else {
            value = amount.getValue();
        }
    }

    public void setChargeType(final ChargeType chargeType) {
        this.chargeType = chargeType;
    }

    public void setDeductAmount(final boolean deductAmount) {
        this.deductAmount = deductAmount;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public void setEnabled(final boolean enabled) {
        this.enabled = enabled;
    }

    public void setFinalAmount(final BigDecimal finalAmount) {
        this.finalAmount = finalAmount;
    }

    public void setFromAllGroups(final boolean fromAllGroups) {
        this.fromAllGroups = fromAllGroups;
    }

    public void setFromFixedMember(final Member fromFixedMember) {
        this.fromFixedMember = fromFixedMember;
    }

    public void setFromGroups(final Collection<MemberGroup> fromGroups) {
        this.fromGroups = fromGroups;
    }

    public void setGeneratedTransferType(final TransferType generatedTransferType) {
        this.generatedTransferType = generatedTransferType;
    }

    public void setInitialAmount(final BigDecimal initialAmount) {
        this.initialAmount = initialAmount;
    }

    public void setMaxFixedValue(final BigDecimal maxFixedValue) {
        this.maxFixedValue = maxFixedValue;
    }

    public void setMaxPercentageValue(final BigDecimal maxPercentageValue) {
        this.maxPercentageValue = maxPercentageValue;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setOriginalTransferType(final TransferType originalTransferType) {
        this.originalTransferType = originalTransferType;
    }

    public void setPayer(final Subject payer) {
        this.payer = payer;
    }

    public void setToAllGroups(final boolean toAllGroups) {
        this.toAllGroups = toAllGroups;
    }

    public void setToGroups(final Collection<MemberGroup> toGroups) {
        this.toGroups = toGroups;
    }

    public void setTransfers(final Collection<Transfer> transfers) {
        this.transfers = transfers;
    }

    public void setValue(final BigDecimal value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return getId() + " - " + name;
    }
}
