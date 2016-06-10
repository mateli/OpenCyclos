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
package nl.strohalm.cyclos.services.transactions;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Collection;

import nl.strohalm.cyclos.entities.accounts.loans.Loan;
import nl.strohalm.cyclos.entities.accounts.loans.LoanGroup;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferType;
import nl.strohalm.cyclos.entities.customization.fields.PaymentCustomField;
import nl.strohalm.cyclos.entities.customization.fields.PaymentCustomFieldValue;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.utils.DataObject;

/**
 * Parameters for a loan grant
 * @author luis
 */
public abstract class GrantLoanDTO extends DataObject {

    private static final long                   serialVersionUID = 8614021962891654164L;
    private BigDecimal                          amount;
    private Collection<PaymentCustomFieldValue> customValues;
    private String                              description;
    private LoanGroup                           loanGroup;
    private Member                              member;
    private TransferType                        transferType;
    private Calendar                            date;
    private boolean                             automatic;

    public BigDecimal getAmount() {
        return amount;
    }

    public Class<PaymentCustomField> getCustomFieldClass() {
        return PaymentCustomField.class;
    }

    public Class<PaymentCustomFieldValue> getCustomFieldValueClass() {
        return PaymentCustomFieldValue.class;
    }

    public Collection<PaymentCustomFieldValue> getCustomValues() {
        return customValues;
    }

    public Calendar getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public LoanGroup getLoanGroup() {
        return loanGroup;
    }

    public abstract Loan.Type getLoanType();

    public Member getMember() {
        return member;
    }

    public TransferType getTransferType() {
        return transferType;
    }

    public boolean isAutomatic() {
        return automatic;
    }

    public void setAmount(final BigDecimal amount) {
        this.amount = amount;
    }

    public void setAutomatic(final boolean automatic) {
        this.automatic = automatic;
    }

    public void setCustomValues(final Collection<PaymentCustomFieldValue> customValues) {
        this.customValues = customValues;
    }

    public void setDate(final Calendar date) {
        this.date = date;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public void setLoanGroup(final LoanGroup loanGroup) {
        this.loanGroup = loanGroup;
    }

    public void setMember(final Member to) {
        member = to;
    }

    public void setTransferType(final TransferType transferType) {
        this.transferType = transferType;
    }

}
