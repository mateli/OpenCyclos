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

import nl.strohalm.cyclos.entities.Entity;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.AccountOwner;
import nl.strohalm.cyclos.entities.accounts.AccountType;
import nl.strohalm.cyclos.entities.accounts.SystemAccountOwner;
import nl.strohalm.cyclos.entities.accounts.fees.account.AccountFeeLog;
import nl.strohalm.cyclos.entities.customization.fields.PaymentCustomField;
import nl.strohalm.cyclos.entities.customization.fields.PaymentCustomFieldValue;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.utils.CustomFieldsContainer;
import nl.strohalm.cyclos.utils.FormatObject;
import nl.strohalm.cyclos.utils.StringValuedEnum;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * An invoice sent to a member or to the system
 * @author luis
 */
@Table(name = "invoices")
@javax.persistence.Entity
public class Invoice extends Entity implements CustomFieldsContainer<PaymentCustomField, PaymentCustomFieldValue> {

    public static enum Relationships implements Relationship {
        ACCOUNT_FEE_LOG("accountFeeLog"), DESTINATION_ACCOUNT_TYPE("destinationAccountType"), FROM_MEMBER("fromMember"), TO_MEMBER("toMember"), SENT_BY("sentBy"), PERFORMED_BY("performedBy"), TRANSFER_TYPE("transferType"), TRANSFER("transfer"), PAYMENTS("payments"), SCHEDULED_PAYMENT("scheduledPayment"), CUSTOM_VALUES("customValues");
        private final String name;

        private Relationships(final String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    public static enum Status implements StringValuedEnum {
        OPEN("O"), ACCEPTED("A"), DENIED("D"), CANCELLED("C"), EXPIRED("E");
        private final String value;

        private Status(final String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    private static final long                   serialVersionUID = -2221939158739233962L;

    @ManyToOne
    @JoinColumn(name = "account_fee_log_id")
	private AccountFeeLog                       accountFeeLog;

    @Column(name = "date", nullable = false, updatable = false)
    private Calendar                            date;

    @Column(name = "description", updatable = false, columnDefinition = "text")
    private String                              description;

    @ManyToOne
    @JoinColumn(name = "dest_type_id")
	private AccountType                         destinationAccountType;

    @ManyToOne
    @JoinColumn(name = "from_member_id")
	private Member                              fromMember;

    @Column(name = "status", nullable = false, length = 1)
	private Status                              status           = Status.OPEN;

    @ManyToOne
    @JoinColumn(name = "to_member_id")
	private Member                              toMember;

    @ManyToOne
    @JoinColumn(name = "transfer_type_id")
	private TransferType                        transferType;

    @ManyToOne
    @JoinColumn(name = "transfer_id")
	private Transfer                            transfer;

    @Column(name = "amount", nullable = false, updatable = false, precision = 15, scale = 6)
    private BigDecimal                          amount;

    @ManyToOne
    @JoinColumn(name = "sent_by_id")
	private Element                             sentBy;

    @ManyToOne
    @JoinColumn(name = "performed_by_id")
	private Element                             performedBy;

    @OneToMany(mappedBy = "invoice")
	private List<InvoicePayment>                payments;

    @ManyToOne
    @JoinColumn(name = "scheduled_payment_id")
	private ScheduledPayment                    scheduledPayment;

    @OneToMany(mappedBy = "invoice")
	private Collection<PaymentCustomFieldValue> customValues;

	public AccountFeeLog getAccountFeeLog() {
        return accountFeeLog;
    }

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

    public AccountType getDestinationAccountType() {
        return destinationAccountType;
    }

    public AccountOwner getFrom() {
        return isFromSystem() ? SystemAccountOwner.instance() : fromMember;
    }

    public Member getFromMember() {
        return fromMember;
    }

    public Payment getPayment() {
        if (transfer == null) {
            return scheduledPayment;
        }
        return transfer;
    }

    public List<InvoicePayment> getPayments() {
        return payments;
    }

    public Element getPerformedBy() {
        return performedBy;
    }

    public ScheduledPayment getScheduledPayment() {
        return scheduledPayment;
    }

    public Element getSentBy() {
        return sentBy;
    }

    public Status getStatus() {
        return status;
    }

    public AccountOwner getTo() {
        return isToSystem() ? SystemAccountOwner.instance() : toMember;
    }

    public Member getToMember() {
        return toMember;
    }

    public Transfer getTransfer() {
        return transfer;
    }

    public TransferType getTransferType() {
        return transferType;
    }

    public boolean isFromSystem() {
        return fromMember == null;
    }

    public boolean isOpen() {
        return status == Status.OPEN;
    }

    public boolean isToSystem() {
        return toMember == null;
    }

    public void setAccountFeeLog(final AccountFeeLog taxLog) {
        accountFeeLog = taxLog;
    }

    public void setAmount(final BigDecimal value) {
        amount = value;
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

    public void setDestinationAccountType(final AccountType destinationAccountType) {
        this.destinationAccountType = destinationAccountType;
    }

    public void setFrom(final AccountOwner owner) {
        fromMember = (owner instanceof Member) ? (Member) owner : null;
    }

    public void setFromMember(final Member fromMember) {
        this.fromMember = fromMember;
    }

    public void setPayment(final Payment payment) {
        if (payment instanceof Transfer) {
            setTransfer((Transfer) payment);
        } else {
            setScheduledPayment((ScheduledPayment) payment);
        }
    }

    public void setPayments(final List<InvoicePayment> payments) {
        this.payments = payments;
    }

    public void setPerformedBy(final Element performedBy) {
        this.performedBy = performedBy;
    }

    public void setScheduledPayment(final ScheduledPayment scheduledPayment) {
        this.scheduledPayment = scheduledPayment;
    }

    public void setSentBy(final Element sentBy) {
        this.sentBy = sentBy;
    }

    public void setStatus(final Status status) {
        this.status = status;
    }

    public void setTo(final AccountOwner owner) {
        toMember = (owner instanceof Member) ? (Member) owner : null;
    }

    public void setToMember(final Member toMember) {
        this.toMember = toMember;
    }

    public void setTransfer(final Transfer transfer) {
        this.transfer = transfer;
    }

    public void setTransferType(final TransferType transferType) {
        this.transferType = transferType;
    }

    @Override
    public String toString() {
        return getId() + " - amount: " + FormatObject.formatObject(amount) + ", from: " + getFrom() + ", to: " + getTo();
    }

    @Override
    protected void appendVariableValues(final Map<String, Object> variables, final LocalSettings localSettings) {
        if (destinationAccountType == null) {
            variables.put("amount", localSettings.getNumberConverter().toString(amount));
        } else {
            variables.put("amount", localSettings.getUnitsConverter(destinationAccountType.getCurrency().getPattern()).toString(amount));
        }
        variables.put("date", localSettings.getDateConverter().toString(date));
        variables.put("description", description);
        variables.put("desc", description);
    }
}
