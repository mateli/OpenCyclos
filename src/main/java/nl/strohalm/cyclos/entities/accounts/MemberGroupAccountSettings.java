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
package nl.strohalm.cyclos.entities.accounts;

import nl.strohalm.cyclos.entities.Entity;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferType;
import nl.strohalm.cyclos.entities.groups.MemberGroup;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * This is an associative class between MemberGroup and MemberAccountType. It represents those settings for a particular association
 * ofMemberAccountType and MemberGroup, i.e. initial credit, credit limit, etc.
 */
@Cacheable
@Table(name = "member_group_account_settings")
@javax.persistence.Entity
public class MemberGroupAccountSettings extends Entity {

    public static enum Relationships implements Relationship {
        ACCOUNT_TYPE("accountType"), GROUP("group"), INITIAL_CREDIT_TRANSFER_TYPE("initialCreditTransferType");
        private final String name;

        private Relationships(final String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    private static final long serialVersionUID   = -3433010484398168393L;

    @ManyToOne
    @JoinColumn(name = "group_id", nullable = false)
	private MemberGroup       group;

    @ManyToOne
    @JoinColumn(name = "type_id", nullable = false)
	private MemberAccountType accountType;

    @Column(name = "default_credit_limit", nullable = false, precision = 15, scale = 6)
    private BigDecimal        defaultCreditLimit = BigDecimal.ZERO;

    @Column(name = "default_upper_credit_limit", nullable = false, precision = 15, scale = 6)
    private BigDecimal        defaultUpperCreditLimit;

    @Column(name = "initial_credit", precision = 15, scale = 6)
    private BigDecimal        initialCredit;

    @ManyToOne
    @JoinColumn(name = "initial_credit_transfer_type_id")
	private TransferType      initialCreditTransferType;

    @Column(name = "default_type", nullable = false)
    private boolean           isDefault;

    @Column(name = "transaction_password_required", nullable = false)
    private boolean           transactionPasswordRequired;

    @Column(name = "hide_when_no_credit_limit", nullable = false)
    private boolean           hideWhenNoCreditLimit;

    @Column(name = "low_units", precision = 15, scale = 6)
    private BigDecimal        lowUnits;

    @Column(name = "low_units_message", columnDefinition = "text")
    private String            lowUnitsMessage;

	public MemberAccountType getAccountType() {
        return accountType;
    }

    public BigDecimal getDefaultCreditLimit() {
        return defaultCreditLimit;
    }

    public BigDecimal getDefaultUpperCreditLimit() {
        return defaultUpperCreditLimit;
    }

    public MemberGroup getGroup() {
        return group;
    }

    public BigDecimal getInitialCredit() {
        return initialCredit;
    }

    public TransferType getInitialCreditTransferType() {
        return initialCreditTransferType;
    }

    public BigDecimal getLowUnits() {
        return lowUnits;
    }

    public String getLowUnitsMessage() {
        return lowUnitsMessage;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public boolean isHideWhenNoCreditLimit() {
        return hideWhenNoCreditLimit;
    }

    public boolean isTransactionPasswordRequired() {
        return transactionPasswordRequired;
    }

    public void setAccountType(final MemberAccountType accountType) {
        this.accountType = accountType;
    }

    public void setDefault(final boolean isDefault) {
        this.isDefault = isDefault;
    }

    public void setDefaultCreditLimit(final BigDecimal defaultCreditLimit) {
        this.defaultCreditLimit = defaultCreditLimit;
    }

    public void setDefaultUpperCreditLimit(final BigDecimal defaultUpperCreditLimit) {
        this.defaultUpperCreditLimit = defaultUpperCreditLimit;
    }

    public void setGroup(final MemberGroup group) {
        this.group = group;
    }

    public void setHideWhenNoCreditLimit(final boolean hideWhenNoCreditLimit) {
        this.hideWhenNoCreditLimit = hideWhenNoCreditLimit;
    }

    public void setInitialCredit(final BigDecimal initialCredit) {
        this.initialCredit = initialCredit;
    }

    public void setInitialCreditTransferType(final TransferType initialCreditTransferType) {
        this.initialCreditTransferType = initialCreditTransferType;
    }

    public void setLowUnits(final BigDecimal lowUnits) {
        this.lowUnits = lowUnits;
    }

    public void setLowUnitsMessage(final String lowUnitsMessage) {
        this.lowUnitsMessage = lowUnitsMessage;
    }

    public void setTransactionPasswordRequired(final boolean transactionPasswordRequired) {
        this.transactionPasswordRequired = transactionPasswordRequired;
    }

    @Override
    public String toString() {
        return getId() + " - " + accountType + " of " + group;
    }
}
