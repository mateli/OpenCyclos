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

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

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
import nl.strohalm.cyclos.utils.StringValuedEnum;
import nl.strohalm.cyclos.utils.TimePeriod;

import org.apache.commons.collections.CollectionUtils;

/**
 * Every transfer has a type, which may contain fees and other relevant data
 * @author luis
 */
public class TransferType extends Entity {

    /**
     * A transfer type context represents where it can be performed
     * @author luis
     */
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

        private boolean payment     = false;
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

    private String                               name;
    private String                               description;
    private String                               confirmationMessage;
    private Context                              context                        = new Context();
    private boolean                              priority;
    private boolean                              conciliable;
    private AccountType                          from;
    private AccountType                          to;
    private BigDecimal                           maxAmountPerDay;
    private BigDecimal                           minAmount;
    private LoanParameters                       loan;
    private Collection<? extends TransactionFee> transactionFees;
    private Collection<? extends TransactionFee> generatedByTransactionFees;
    private Collection<? extends AccountFee>     generatedByAccountFees;
    private Collection<? extends Group>          groups;
    private Collection<? extends Group>          groupsAsMember;
    private Collection<PaymentFilter>            paymentFilters;
    private boolean                              requiresAuthorization;
    private Collection<AuthorizationLevel>       authorizationLevels;
    private boolean                              allowsScheduledPayments;
    private boolean                              requiresFeedback;
    private boolean                              reserveTotalAmountOnScheduling;
    private boolean                              allowCancelScheduledPayments;
    private boolean                              allowBlockScheduledPayments;
    private boolean                              showScheduledPaymentsToDestination;
    private boolean                              allowSmsNotification;
    private Calendar                             feedbackEnabledSince;
    private TimePeriod                           feedbackExpirationTime;
    private TimePeriod                           feedbackReplyExpirationTime;
    private String                               defaultFeedbackComments;
    private Level                                defaultFeedbackLevel;
    private Collection<Channel>                  channels;
    private Member                               fixedDestinationMember;
    private Collection<PaymentCustomField>       customFields;
    private Collection<PaymentCustomField>       linkedCustomFields;
    private String                               transferListenerClass;
    private TransactionHierarchyVisibility       transactionHierarchyVisibility = TransactionHierarchyVisibility.MEMBER;

    /**
     * gets all the transaction fees based on a-rate, being the fees with ChargeType A_RATE or MIXED_A_D_RATES
     * @return a Collection with A-rated TransactionFees
     * @see #getRatedFees()
     */
    public Collection<? extends TransactionFee> getARatedFees() {
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
    public Collection<? extends TransactionFee> getDRatedFees() {
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

    public Collection<? extends AccountFee> getGeneratedByAccountFees() {
        return generatedByAccountFees;
    }

    public Collection<? extends TransactionFee> getGeneratedByTransactionFees() {
        return generatedByTransactionFees;
    }

    public Collection<? extends Group> getGroups() {
        return groups;
    }

    public Collection<? extends Group> getGroupsAsMember() {
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
    public Collection<? extends TransactionFee> getRatedFees() {
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

    public Collection<? extends TransactionFee> getTransactionFees() {
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

    public void setGeneratedByAccountFees(final Collection<? extends AccountFee> generatedByAccountFees) {
        this.generatedByAccountFees = generatedByAccountFees;
    }

    public void setGeneratedByTransactionFees(final Collection<? extends TransactionFee> generatedByTransactionFees) {
        this.generatedByTransactionFees = generatedByTransactionFees;
    }

    public void setGroups(final Collection<? extends Group> groups) {
        this.groups = groups;
    }

    public void setGroupsAsMember(final Collection<? extends Group> groupsAsMember) {
        this.groupsAsMember = groupsAsMember;
    }

    public void setLinkedCustomFields(final Collection<PaymentCustomField> linkedCustomFields) {
        this.linkedCustomFields = linkedCustomFields;
    }

    public void setLoan(final LoanParameters loan) {
        this.loan = loan;
        if (loan != null) {
            loan.setOriginalTransferType(this);
        }
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

    public void setTransactionFees(final Collection<? extends TransactionFee> transactionFees) {
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
