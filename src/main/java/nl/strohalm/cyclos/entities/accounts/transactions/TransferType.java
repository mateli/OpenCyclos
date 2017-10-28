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
import nl.strohalm.cyclos.entities.access.Channel;
import nl.strohalm.cyclos.entities.accounts.AccountType;
import nl.strohalm.cyclos.entities.accounts.Currency;
import nl.strohalm.cyclos.entities.accounts.fees.account.AccountFee;
import nl.strohalm.cyclos.entities.accounts.fees.transaction.TransactionFee;
import nl.strohalm.cyclos.entities.accounts.fees.transaction.TransactionFee.ChargeType;
import nl.strohalm.cyclos.entities.accounts.loans.LoanParameters;
import nl.strohalm.cyclos.entities.customization.fields.PaymentCustomField;
import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.members.Reference.Level;
import nl.strohalm.cyclos.entities.utils.TimePeriod;
import nl.strohalm.cyclos.utils.StringValuedEnum;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.core.annotation.Order;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

/**
 * Every transfer has a type, which may contain fees and other relevant data
 * @author luis
 */
@Cacheable
@Table(name = "transfer_types")
@javax.persistence.Entity
public class TransferType extends Entity {

    /**
     * A transfer type context represents where it can be performed
     * @author luis
     */
    @Embeddable
    public static class Context implements Serializable {

        private static final long serialVersionUID = -7966654322680432255L;

        public static Context payment() {
            final Context context = new Context();
            context.setPayment(true);
            return context;
        }

        public static Context self() {
            final Context context = new Context();
            context.setSelfPayment(true);
            return context;
        }

        @Column(name = "allowed_payment", nullable = false)
        private boolean payment     = false;

        @Column(name = "allowed_self_payment", nullable = false)
        private boolean selfPayment = false;

        public Context() {
        }

        public boolean isPayment() {
            return payment;
        }

        public boolean isSelfPayment() {
            return selfPayment;
        }

        public void setPayment(final boolean payment) {
            this.payment = payment;
        }

        public void setSelfPayment(final boolean selfPayment) {
            this.selfPayment = selfPayment;
        }
    }

    public static enum Direction {
        FROM, TO, BOTH
    }

    public static enum Relationships implements Relationship {
        FROM("from"), GROUPS("groups"), GROUPS_AS_MEMBER("groupsAsMember"), TO("to"), TRANSACTION_FEES("transactionFees"), GENERATED_BY_TRANSACTION_FEES("generatedByTransactionFees"), GENERATED_BY_ACCOUNT_FEES("generatedByAccountFees"), PAYMENT_FILTERS("paymentFilters"), AUTHORIZATION_LEVELS("authorizationLevels"), CUSTOM_FIELDS("customFields"), LINKED_CUSTOM_FIELDS("linkedCustomFields"), CHANNELS("channels");
        private final String name;

        private Relationships(final String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }
    }

    public static enum TransactionHierarchyVisibility implements StringValuedEnum {
        ADMIN("A"), BROKER("B"), MEMBER("M");
        private final String value;

        private TransactionHierarchyVisibility(final String value) {
            this.value = value;
        }

        @Override
        public String getValue() {
            return value;
        }

        public boolean isVisibleTo(final Group.Nature groupNature) {
            switch (groupNature) {
                case ADMIN:
                    return true;
                case BROKER:
                    return this != TransactionHierarchyVisibility.ADMIN;
                case MEMBER:
                case OPERATOR:
                    return this == TransactionHierarchyVisibility.MEMBER;
            }
            return false;
        }
    }

    private static final long                    serialVersionUID               = -6248433842449336187L;

    @Column(name = "name", nullable = false, length = 100)
    private String                               name;

    @Column(name = "description", nullable = false, columnDefinition = "text")
    private String                               description;

    @Column(name = "confirmation_message", columnDefinition = "text")
    private String                               confirmationMessage;

    @Embedded
    private Context                              context                        = new Context();

    @Column(name = "priority", nullable = false)
    private boolean                              priority;

    @Column(name = "conciliable", nullable = false)
    private boolean                              conciliable;

    @ManyToOne
    @JoinColumn(name = "from_account_type_id", nullable = false)
    private AccountType                          from;

    @ManyToOne
    @JoinColumn(name = "to_account_type_id", nullable = false)
    private AccountType                          to;

    @Column(name = "max_amount_per_day", precision = 15, scale = 6)
    private BigDecimal                           maxAmountPerDay;

    @Column(name = "min_amount", precision = 15, scale = 6)
    private BigDecimal                           minAmount;

    @Embedded
    private LoanParameters                       loan;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "originalTransferType")
    @OrderBy("name")
    private Collection<TransactionFee> transactionFees;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "generatedTransferType")
    private Collection<TransactionFee> generatedByTransactionFees;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "transferType")
    private Collection<AccountFee>     generatedByAccountFees;

    @ManyToMany(mappedBy = "transferTypes")
    private Collection<Group>          groups;

    @ManyToMany
    @JoinTable(name = "groups_transfer_types_as_member",
            joinColumns = @JoinColumn(name = "transfer_type_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id"))
    private Collection<Group>          groupsAsMember;

    @ManyToMany
    @JoinTable(name = "transfer_types_payment_filters",
            joinColumns = @JoinColumn(name = "transfer_type_id"),
            inverseJoinColumns = @JoinColumn(name = "payment_filter_id"))
    private Collection<PaymentFilter>            paymentFilters;

    @Column(name = "requires_authorization", nullable = false)
    private boolean                              requiresAuthorization;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "transferType")
    @OrderBy("level")
    private Collection<AuthorizationLevel>       authorizationLevels;

    @Column(name = "allows_scheduled_payments", nullable = false)
    private boolean                              allowsScheduledPayments;

    @Column(name = "requires_feedback", nullable = false)
    private boolean                              requiresFeedback;

    @Column(name = "reserve_total_on_sched", nullable = false)
    private boolean                              reserveTotalAmountOnScheduling;

    @Column(name = "allow_cancel_sched", nullable = false)
    private boolean                              allowCancelScheduledPayments;

    @Column(name = "allow_block_sched", nullable = false)
    private boolean                              allowBlockScheduledPayments;

    @Column(name = "show_sched_to_dest", nullable = false)
    private boolean                              showScheduledPaymentsToDestination;

    @Column(name = "allow_sms_notification", nullable = false)
    private boolean                              allowSmsNotification;

    @Column(name = "feedback_enabled_since")
    private Calendar                             feedbackEnabledSince;

    @AttributeOverrides({
            @AttributeOverride(name = "number", column=@Column(name="feedback_expiration_time_number")),
            @AttributeOverride(name = "field", column=@Column(name="feedback_expiration_time_field"))
    })
    @Embedded
    private TimePeriod                           feedbackExpirationTime;

    @AttributeOverrides({
            @AttributeOverride(name = "number", column=@Column(name="feedback_reply_expiration_time_number")),
            @AttributeOverride(name = "field", column=@Column(name="feedback_reply_expiration_time_field"))
    })
    @Embedded
    private TimePeriod                           feedbackReplyExpirationTime;

    @Column(name = "default_feedback_comments", columnDefinition = "text")
    private String                               defaultFeedbackComments;

    @Column(name = "default_feedback_level")
    private Level                                defaultFeedbackLevel;

    @ManyToMany
    @JoinTable(name = "transfer_types_channels",
            joinColumns = @JoinColumn(name = "transfer_type_id"),
            inverseJoinColumns = @JoinColumn(name = "channel_id"))
    private Collection<Channel>                  channels;

    @ManyToOne
    @JoinColumn(name = "fixed_destination_member_id")
    private Member                               fixedDestinationMember;

    @ManyToMany(cascade = CascadeType.REMOVE)
    @JoinTable(name = "transfer_types_linked_custom_fields",
            joinColumns = @JoinColumn(name = "transfer_type_id"),
            inverseJoinColumns = @JoinColumn(name = "field_id"))
    @OrderBy("order_number")
    private Collection<PaymentCustomField>       customFields;

    @ManyToMany
    @JoinTable(name = "transfer_types_linked_custom_fields",
            joinColumns = @JoinColumn(name = "transfer_type_id"),
            inverseJoinColumns = @JoinColumn(name = "field_id"))
    private Collection<PaymentCustomField>       linkedCustomFields;

    @Column(name = "transfer_listener_class", length = 200)
    private String                               transferListenerClass;

    @Column(name = "tx_hierarchy_visibility", length = 1)
    private TransactionHierarchyVisibility       transactionHierarchyVisibility = TransactionHierarchyVisibility.MEMBER;

	/**
     * gets all the transaction fees based on a-rate, being the fees with ChargeType A_RATE or MIXED_A_D_RATES
     * @return a Collection with A-rated TransactionFees
     * @see #getRatedFees()
     */
    public Collection< TransactionFee> getARatedFees() {
        final List<TransactionFee> result = new ArrayList<TransactionFee>(transactionFees.size());
        for (final TransactionFee fee : transactionFees) {
            if (fee.getChargeType() == ChargeType.A_RATE || fee.getChargeType() == ChargeType.MIXED_A_D_RATES) {
                result.add(fee);
            }
        }
        return result;
    }

    public Collection<AuthorizationLevel> getAuthorizationLevels() {
        return authorizationLevels;
    }

    public Collection<Channel> getChannels() {
        return channels;
    }

    public String getConfirmationMessage() {
        return confirmationMessage;
    }

    public Context getContext() {
        return context;
    }

    public Currency getCurrency() {
        try {
            return from.getCurrency();
        } catch (final Exception e) {
            return null;
        }
    }

    public Collection<PaymentCustomField> getCustomFields() {
        return customFields;
    }

    public String getDefaultFeedbackComments() {
        return defaultFeedbackComments;
    }

    public Level getDefaultFeedbackLevel() {
        return defaultFeedbackLevel;
    }

    public String getDescription() {
        return description;
    }

    /**
     * gets all the transaction fees based on d-rate, that is: having ChargeType = D_RATE
     * @return a Collection with TransactionFees
     * @see #getRatedFees()
     */
    public Collection< TransactionFee> getDRatedFees() {
        final List<TransactionFee> result = new ArrayList<TransactionFee>(transactionFees.size());
        for (final TransactionFee fee : transactionFees) {
            if (fee.getChargeType() == ChargeType.D_RATE || fee.getChargeType() == ChargeType.MIXED_A_D_RATES) {
                result.add(fee);
            }
        }
        return result;
    }

    public Calendar getFeedbackEnabledSince() {
        return feedbackEnabledSince;
    }

    public TimePeriod getFeedbackExpirationTime() {
        return feedbackExpirationTime;
    }

    public TimePeriod getFeedbackReplyExpirationTime() {
        return feedbackReplyExpirationTime;
    }

    public Member getFixedDestinationMember() {
        return fixedDestinationMember;
    }

    public AccountType getFrom() {
        return from;
    }

    public Collection< AccountFee> getGeneratedByAccountFees() {
        return generatedByAccountFees;
    }

    public Collection< TransactionFee> getGeneratedByTransactionFees() {
        return generatedByTransactionFees;
    }

    public Collection< Group> getGroups() {
        return groups;
    }

    public Collection< Group> getGroupsAsMember() {
        return groupsAsMember;
    }

    public Collection<PaymentCustomField> getLinkedCustomFields() {
        return linkedCustomFields;
    }

    public LoanParameters getLoan() {
        return loan;
    }

    public BigDecimal getMaxAmountPerDay() {
        return maxAmountPerDay;
    }

    public BigDecimal getMinAmount() {
        return minAmount;
    }

    @Override
    public String getName() {
        return name;
    }

    public Collection<PaymentFilter> getPaymentFilters() {
        return paymentFilters;
    }

    /**
     * gets all the transaction fees which do use a-rate or d-rate
     * @return a Collection with TransactionFees using a-rate or d-rate
     * @see #getRatedFees()
     */
    public Collection< TransactionFee> getRatedFees() {
        final List<TransactionFee> result = new ArrayList<TransactionFee>(transactionFees.size());
        for (final TransactionFee fee : transactionFees) {
            if (fee.getChargeType() == ChargeType.A_RATE || fee.getChargeType() == ChargeType.D_RATE || fee.getChargeType() == ChargeType.MIXED_A_D_RATES) {
                result.add(fee);
            }
        }
        return result;
    }

    public AccountType getTo() {
        return to;
    }

    public Collection< TransactionFee> getTransactionFees() {
        return transactionFees;
    }

    public TransactionHierarchyVisibility getTransactionHierarchyVisibility() {
        return transactionHierarchyVisibility;
    }

    public String getTransferListenerClass() {
        return transferListenerClass;
    }

    /**
     * @return true if there are any TransactionFees on this TT.
     */
    public boolean hasTransactionFees() {
        return !CollectionUtils.isEmpty(transactionFees);
    }

    public boolean isAllowBlockScheduledPayments() {
        return allowBlockScheduledPayments;
    }

    public boolean isAllowCancelScheduledPayments() {
        return allowCancelScheduledPayments;
    }

    public boolean isAllowSmsNotification() {
        return allowSmsNotification;
    }

    public boolean isAllowsScheduledPayments() {
        return allowsScheduledPayments;
    }

    public boolean isConciliable() {
        return conciliable;
    }

    public boolean isFromMember() {
        return !isFromSystem();
    }

    public boolean isFromSystem() {
        return from.getNature() == AccountType.Nature.SYSTEM;
    }

    /**
     * returns true if the TransferType has TransactionFees based on a-rate.
     */
    public boolean isHavingAratedFees() {
        if (getARatedFees().size() == 0) {
            return false;
        }
        return true;
    }

    /**
     * returns true if the TransferType has TransactionFees based on d-rate.
     */
    public boolean isHavingDratedFees() {
        if (getDRatedFees().size() == 0) {
            return false;
        }
        return true;
    }

    /**
     * returns true if the TransferType has TransactionFees based on a-rate or d-rate.
     */
    public boolean isHavingRatedFees() {
        if (getRatedFees().size() == 0) {
            return false;
        }
        return true;
    }

    public boolean isLoanType() {
        return loan != null && loan.getType() != null;
    }

    public boolean isPriority() {
        return priority;
    }

    public boolean isRequiresAuthorization() {
        return requiresAuthorization;
    }

    public boolean isRequiresFeedback() {
        return requiresFeedback;
    }

    public boolean isReserveTotalAmountOnScheduling() {
        return reserveTotalAmountOnScheduling;
    }

    public boolean isShowScheduledPaymentsToDestination() {
        return showScheduledPaymentsToDestination;
    }

    public boolean isToMember() {
        return !isToSystem();
    }

    public boolean isToSystem() {
        return to.getNature() == AccountType.Nature.SYSTEM;
    }

    public void setAllowBlockScheduledPayments(final boolean allowBlockScheduledPayments) {
        this.allowBlockScheduledPayments = allowBlockScheduledPayments;
    }

    public void setAllowCancelScheduledPayments(final boolean allowCancelScheduledPayments) {
        this.allowCancelScheduledPayments = allowCancelScheduledPayments;
    }

    public void setAllowSmsNotification(final boolean allowSmsNotification) {
        this.allowSmsNotification = allowSmsNotification;
    }

    public void setAllowsScheduledPayments(final boolean allowsScheduledPayments) {
        this.allowsScheduledPayments = allowsScheduledPayments;
    }

    public void setAuthorizationLevels(final Collection<AuthorizationLevel> authorizationLevels) {
        this.authorizationLevels = authorizationLevels;
    }

    public void setChannels(final Collection<Channel> channels) {
        this.channels = channels;
    }

    public void setConciliable(final boolean conciliable) {
        this.conciliable = conciliable;
    }

    public void setConfirmationMessage(final String confirmationMessage) {
        this.confirmationMessage = confirmationMessage;
    }

    public void setContext(final Context context) {
        this.context = context;
    }

    public void setCustomFields(final Collection<PaymentCustomField> customFields) {
        this.customFields = customFields;
    }

    public void setDefaultFeedbackComments(final String defaultFeedbackComments) {
        this.defaultFeedbackComments = defaultFeedbackComments;
    }

    public void setDefaultFeedbackLevel(final Level defaultFeedbackLevel) {
        this.defaultFeedbackLevel = defaultFeedbackLevel;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public void setFeedbackEnabledSince(final Calendar feedbackEnabledSince) {
        this.feedbackEnabledSince = feedbackEnabledSince;
    }

    public void setFeedbackExpirationTime(final TimePeriod feedbackExpirationTime) {
        this.feedbackExpirationTime = feedbackExpirationTime;
    }

    public void setFeedbackReplyExpirationTime(final TimePeriod feedbackReplyExpirationTime) {
        this.feedbackReplyExpirationTime = feedbackReplyExpirationTime;
    }

    public void setFixedDestinationMember(final Member fixedDestinationMember) {
        this.fixedDestinationMember = fixedDestinationMember;
    }

    public void setFrom(final AccountType from) {
        this.from = from;
    }

    public void setGeneratedByAccountFees(final Collection< AccountFee> generatedByAccountFees) {
        this.generatedByAccountFees = generatedByAccountFees;
    }

    public void setGeneratedByTransactionFees(final Collection< TransactionFee> generatedByTransactionFees) {
        this.generatedByTransactionFees = generatedByTransactionFees;
    }

    public void setGroups(final Collection< Group> groups) {
        this.groups = groups;
    }

    public void setGroupsAsMember(final Collection< Group> groupsAsMember) {
        this.groupsAsMember = groupsAsMember;
    }

    public void setLinkedCustomFields(final Collection<PaymentCustomField> linkedCustomFields) {
        this.linkedCustomFields = linkedCustomFields;
    }

    public void setLoan(final LoanParameters loan) {
        this.loan = loan;
        // Never used, not saved in database (no field in table)
//        if (loan != null) {
//            loan.setOriginalTransferType(this);
//        }
    }

    public void setMaxAmountPerDay(final BigDecimal maxAmountPerDay) {
        this.maxAmountPerDay = maxAmountPerDay;
    }

    public void setMinAmount(final BigDecimal minAmount) {
        this.minAmount = minAmount;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setPaymentFilters(final Collection<PaymentFilter> paymentFilters) {
        this.paymentFilters = paymentFilters;
    }

    public void setPriority(final boolean priority) {
        this.priority = priority;
    }

    public void setRequiresAuthorization(final boolean requiresAuthorization) {
        this.requiresAuthorization = requiresAuthorization;
    }

    public void setRequiresFeedback(final boolean requiresFeedback) {
        this.requiresFeedback = requiresFeedback;
    }

    public void setReserveTotalAmountOnScheduling(final boolean reserveTotalAmountOnScheduling) {
        this.reserveTotalAmountOnScheduling = reserveTotalAmountOnScheduling;
    }

    public void setShowScheduledPaymentsToDestination(final boolean showScheduledPaymentsToDestination) {
        this.showScheduledPaymentsToDestination = showScheduledPaymentsToDestination;
    }

    public void setTo(final AccountType to) {
        this.to = to;
    }

    public void setTransactionFees(final Collection< TransactionFee> transactionFees) {
        this.transactionFees = transactionFees;
    }

    public void setTransactionHierarchyVisibility(final TransactionHierarchyVisibility transactionHierarchyVisibility) {
        this.transactionHierarchyVisibility = transactionHierarchyVisibility;
    }

    public void setTransferListenerClass(final String transferListenerClass) {
        this.transferListenerClass = transferListenerClass;
    }

    @Override
    public String toString() {
        return getId() + " - " + name;
    }

}
