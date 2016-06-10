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
import java.util.Collection;
import java.util.Map;

import nl.strohalm.cyclos.entities.Entity;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.Account;
import nl.strohalm.cyclos.entities.accounts.AccountOwner;
import nl.strohalm.cyclos.entities.accounts.SystemAccountOwner;
import nl.strohalm.cyclos.entities.customization.fields.PaymentCustomField;
import nl.strohalm.cyclos.entities.customization.fields.PaymentCustomFieldValue;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.utils.CustomFieldsContainer;
import nl.strohalm.cyclos.utils.FormatObject;
import nl.strohalm.cyclos.utils.StringValuedEnum;

/**
 * Base class for payments
 * @author luis
 */
public abstract class Payment extends Entity implements CustomFieldsContainer<PaymentCustomField, PaymentCustomFieldValue> {

    public static enum Nature {
        TRANSFER, SCHEDULED_PAYMENT;
    }

    public static enum Relationships implements Relationship {
        FROM("from"), TO("to"), BY("by"), TYPE("type"), CUSTOM_VALUES("customValues");
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
        /**
         * The payment is subject to authorization
         */
        PENDING("P"),

        /**
         * The payment has been successfully processed
         */
        PROCESSED("O"),

        /**
         * The authorizer has denied the payment
         */
        DENIED("D"),

        /**
         * The payment performer has canceled this payment before it's been completely processed
         */
        CANCELED("C"),

        /**
         * The payment is scheduled for future processing
         */
        SCHEDULED("S"),

        /**
         * The payment couldn't be processed (i.e: not enough credits)
         */
        FAILED("F"),

        /**
         * The payment has been blocked to avoid it being automatically processed at the scheduled date
         */
        BLOCKED("B");

        private final String value;

        private Status(final String value) {
            this.value = value;
        }

        public boolean canPayNow() {
            switch (this) {
                case BLOCKED:
                case FAILED:
                case SCHEDULED:
                    return true;
                default:
                    return false;
            }
        }

        @Override
        public String getValue() {
            return value;
        }
    }

    private static final long                   serialVersionUID = -3197630501722484236L;

    private Calendar                            date;
    private Calendar                            transactionFeedbackDeadline;
    private BigDecimal                          amount;
    private String                              description;
    private Account                             from;
    private Account                             to;
    private TransferType                        type;
    private Element                             by;
    private Calendar                            processDate;
    private Status                              status           = Status.PROCESSED;
    private Collection<PaymentCustomFieldValue> customValues;

    /**
     * Returns the amount as a positive number, even when it's negative (i.e. chargeback)
     */
    public BigDecimal getActualAmount() {
        final BigDecimal amount = getAmount();
        return amount == null ? null : amount.abs();
    }

    public abstract Calendar getActualDate();

    public abstract Account getActualFrom();

    public abstract AccountOwner getActualFromOwner();

    public abstract Account getActualTo();

    public abstract AccountOwner getActualToOwner();

    public BigDecimal getAmount() {
        return amount;
    }

    public Element getBy() {
        return by;
    }

    @Override
    public Class<PaymentCustomField> getCustomFieldClass() {
        return PaymentCustomField.class;
    }

    @Override
    public Class<PaymentCustomFieldValue> getCustomFieldValueClass() {
        return PaymentCustomFieldValue.class;
    }

    @Override
    public Collection<PaymentCustomFieldValue> getCustomValues() {
        return customValues;
    }

    public Calendar getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public Account getFrom() {
        return from;
    }

    public AccountOwner getFromOwner() {
        return from.getOwner();
    }

    public abstract Nature getNature();

    public Calendar getProcessDate() {
        return processDate;
    }

    public Status getStatus() {
        return status;
    }

    public Account getTo() {
        return to;
    }

    public AccountOwner getToOwner() {
        return to.getOwner();
    }

    public Calendar getTransactionFeedbackDeadline() {
        return transactionFeedbackDeadline;
    }

    public TransferType getType() {
        return type;
    }

    public boolean isFromSystem() {
        return getFromOwner() instanceof SystemAccountOwner;
    }

    public boolean isToSystem() {
        return getToOwner() instanceof SystemAccountOwner;
    }

    public void setAmount(final BigDecimal amount) {
        this.amount = amount;
    }

    public void setBy(final Element by) {
        this.by = by;
    }

    @Override
    public void setCustomValues(final Collection<PaymentCustomFieldValue> customValues) {
        this.customValues = customValues;
    }

    public void setDate(final Calendar date) {
        this.date = date;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public void setFrom(final Account from) {
        this.from = from;
    }

    public void setProcessDate(final Calendar processDate) {
        this.processDate = processDate;
    }

    public void setStatus(final Status status) {
        this.status = status;
    }

    public void setTo(final Account to) {
        this.to = to;
    }

    public void setTransactionFeedbackDeadline(final Calendar transactionFeedbackDeadline) {
        this.transactionFeedbackDeadline = transactionFeedbackDeadline;
    }

    public void setType(final TransferType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return getId() + " - amount: " + FormatObject.formatObject(getAmount()) + ", from: " + getFrom() + ", to: " + getTo() + ", type: " + getType();
    }

    @Override
    protected void appendVariableValues(final Map<String, Object> variables, final LocalSettings localSettings) {
        String typeName;
        try {
            typeName = getType().getName();
        } catch (final Exception e) {
            typeName = "";
        }
        variables.put("payment_type", typeName);
        String fromAccountName;
        try {
            fromAccountName = getFrom().getType().getName();
        } catch (final Exception e) {
            fromAccountName = "";
        }
        if (from.getOwner() instanceof Member) {
            final Member fromMember = (Member) from.getOwner();
            variables.put("from_member", fromMember.getName());
            variables.put("from_login", fromMember.getUsername());
        }
        if (to.getOwner() instanceof Member) {
            final Member toMember = (Member) to.getOwner();
            variables.put("to_member", toMember.getName());
            variables.put("to_login", toMember.getUsername());
        }
        variables.put("from_account", fromAccountName);
        String toAccountName;
        try {
            toAccountName = getTo().getType().getName();
        } catch (final Exception e) {
            toAccountName = "";
        }
        variables.put("to_account", toAccountName);
        try {
            variables.put("amount", localSettings.getUnitsConverter(getTo().getType().getCurrency().getPattern()).toString(getAmount()));
        } catch (final Exception e) {
            variables.put("amount", localSettings.getNumberConverter().toString(getAmount()));
        }
        variables.put("date", localSettings.getDateConverter().toString(getDate()));
        variables.put("description", getDescription());
    }

}
