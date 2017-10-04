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
package nl.strohalm.cyclos.entities.groups;

import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.access.Channel;
import nl.strohalm.cyclos.entities.accounts.AccountType;
import nl.strohalm.cyclos.entities.accounts.MemberAccountType;
import nl.strohalm.cyclos.entities.accounts.MemberGroupAccountSettings;
import nl.strohalm.cyclos.entities.accounts.cards.CardType;
import nl.strohalm.cyclos.entities.accounts.fees.account.AccountFee;
import nl.strohalm.cyclos.entities.accounts.fees.transaction.TransactionFee;
import nl.strohalm.cyclos.entities.customization.fields.CustomField;
import nl.strohalm.cyclos.entities.members.RegistrationAgreement;
import nl.strohalm.cyclos.entities.members.messages.Message;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.Collection;
import java.util.HashSet;

/**
 * A group of regular members
 * @author luis
 */
@DiscriminatorValue("M")
@javax.persistence.Entity
public class MemberGroup extends SystemGroup {

    public static enum Relationships implements Relationship {
        ACCOUNT_SETTINGS("accountSettings"), CAN_VIEW_PROFILE_OF_GROUPS("canViewProfileOfGroups"), CAN_VIEW_ADS_OF_GROUPS("canViewAdsOfGroups"), CAN_VIEW_INFORMATION_OF("canViewInformationOf"), ACCOUNT_FEES("accountFees"), MANAGED_BY_GROUPS("managedByGroups"), CUSTOM_FIELDS("customFields"), FROM_TRANSACTION_FEES("fromTransactionFees"), TO_TRANSACTION_FEES("toTransactionFees"), DEFAULT_MAIL_MESSAGES("defaultMailMessages"), SMS_MESSAGES("smsMessages"), DEFAULT_SMS_MESSAGES("defaultSmsMessages"), CHANNELS("channels"), DEFAULT_CHANNELS("defaultChannels"), REQUEST_PAYMENT_BY_CHANNELS("requestPaymentByChannels"), MEMBER_RECORD_TYPES("memberRecordTypes"), CAN_ISSUE_CERTIFICATION_TO_GROUPS("canIssueCertificationToGroups"), CAN_BUY_WITH_PAYMENT_OBLIGATIONS_FROM_GROUPS("canBuyWithPaymentObligationsFromGroups"), CAN_VIEW_GROUP_FILTERS("canViewGroupFilters"), POSSIBLE_INITIAL_GROUP_OF("possibleInitialGroupOf"), REGISTRATION_AGREEMENT("registrationAgreement"), CARD_TYPE("cardType");
        private final String name;

        private Relationships(final String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }
    }

    private static final long                      serialVersionUID = 653102929460778599L;

    @OneToMany(mappedBy = "group", cascade = CascadeType.REMOVE)
	private Collection<MemberGroupAccountSettings> accountSettings;

    @ManyToMany
    @JoinTable(name = "group_view_profile_permissions",
            joinColumns = @JoinColumn(name = "owner_group_id"),
            inverseJoinColumns = @JoinColumn(name = "related_group_id"))
	private Collection<MemberGroup>                canViewProfileOfGroups;

    @ManyToMany
    @JoinTable(name = "group_view_ads_permissions",
            joinColumns = @JoinColumn(name = "owner_group_id"),
            inverseJoinColumns = @JoinColumn(name = "related_group_id"))
	private Collection<MemberGroup>                canViewAdsOfGroups;

    @ManyToMany
    @JoinTable(name = "group_view_account_information_permissions",
            joinColumns = @JoinColumn(name = "owner_group_id"),
            inverseJoinColumns = @JoinColumn(name = "account_type_id"))
	private Collection<AccountType>                canViewInformationOf;

    @ManyToMany
    @JoinTable(name = "groups_account_fees",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "account_fee_id"))
	private Collection<AccountFee>                 accountFees;

    @ManyToMany(mappedBy = "managesGroups")
	private Collection<AdminGroup>                 managedByGroups;

    @ManyToMany
    @JoinTable(name = "member_groups_custom_fields",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "custom_field_id"))
	private Collection<CustomField>                customFields;

    @ManyToMany(mappedBy = "fromGroups")
	private Collection<TransactionFee>             fromTransactionFees;

    @ManyToMany(mappedBy = "toGroups")
	private Collection<TransactionFee>             toTransactionFees;

    @ManyToMany
    @JoinTable(name = "group_issue_certification_to",
            joinColumns = @JoinColumn(name = "owner_group_id"),
            inverseJoinColumns = @JoinColumn(name = "related_group_id"))
	private Collection<MemberGroup>                canIssueCertificationToGroups;

    @ManyToMany
    @JoinTable(name = "group_buy_with_payment_obligations_from",
            joinColumns = @JoinColumn(name = "owner_group_id"),
            inverseJoinColumns = @JoinColumn(name = "related_group_id"))
	private Collection<MemberGroup>                canBuyWithPaymentObligationsFromGroups;

    @ElementCollection
    @CollectionTable(name = "member_groups_message_types",
            joinColumns = @JoinColumn(name = "group_id"))
    @Column(name = "type", length = 3, nullable = false)
	private Collection<Message.Type>               defaultMailMessages;

    @ElementCollection
    @CollectionTable(name = "member_groups_sms_message_types",
            joinColumns = @JoinColumn(name = "group_id"))
    @Column(name = "type", length = 3, nullable = false)
	private Collection<Message.Type>               smsMessages;

    @ElementCollection
    @CollectionTable(name = "member_groups_default_sms_message_types",
            joinColumns = @JoinColumn(name = "group_id"))
    @Column(name = "type", length = 3, nullable = false)
	private Collection<Message.Type>               defaultSmsMessages;

    @ManyToMany
    @JoinTable(name = "groups_channels",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "channel_id"))
	private Collection<Channel>                    channels;

    @ManyToMany
    @JoinTable(name = "groups_default_channels",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "channel_id"))
	private Collection<Channel>                    defaultChannels;

    @ManyToMany
    @JoinTable(name = "groups_request_payment_channels",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "channel_id"))
	private Collection<Channel>                    requestPaymentByChannels;

    @ManyToMany(mappedBy = "viewableBy")
	private Collection<GroupFilter>                canViewGroupFilters;

    @ManyToMany(mappedBy = "possibleInitialGroups")
	private Collection<BrokerGroup>                possibleInitialGroupOf;

    @ManyToOne
    @JoinColumn(name = "card_type_id")
	private CardType                               cardType;

    @Embedded
	private MemberGroupSettings                    memberSettings   = new MemberGroupSettings();

    @Column(name = "initial_group_show", length = 100)
    private String                                 initialGroupShow;

    @Column(name = "initial_group", nullable = false)
    private boolean                                initialGroup;

    @Column(name = "member_active", nullable = false)
    private boolean                                active;

    @Column(name = "member_default_allow_charging_sms", nullable = false)
    private boolean                                defaultAllowChargingSms;

    @Column(name = "member_default_accept_free_mailing", nullable = false)
    private boolean                                defaultAcceptFreeMailing;

    @Column(name = "member_default_accept_paid_mailing", nullable = false)
    private boolean                                defaultAcceptPaidMailing;

    @ManyToOne
    @JoinColumn(name = "registration_agreement_id")
	private RegistrationAgreement                  registrationAgreement;

    protected MemberGroup() {
	}

	public Collection<AccountFee> getAccountFees() {
        return accountFees;
    }

    public Collection<MemberGroupAccountSettings> getAccountSettings() {
        return accountSettings;
    }

    public Collection<MemberAccountType> getAccountTypes() {
        final Collection<MemberAccountType> accountTypes = new HashSet<MemberAccountType>();
        if (accountSettings == null) {
            return null;
        } else {
            for (final MemberGroupAccountSettings accountSetting : accountSettings) {
                accountTypes.add(accountSetting.getAccountType());
            }
        }
        return accountTypes;
    }

    public Collection<MemberGroup> getCanBuyWithPaymentObligationsFromGroups() {
        return canBuyWithPaymentObligationsFromGroups;
    }

    public Collection<MemberGroup> getCanIssueCertificationToGroups() {
        return canIssueCertificationToGroups;
    }

    public Collection<MemberGroup> getCanViewAdsOfGroups() {
        return canViewAdsOfGroups;
    }

    public Collection<GroupFilter> getCanViewGroupFilters() {
        return canViewGroupFilters;
    }

    public Collection<AccountType> getCanViewInformationOf() {
        return canViewInformationOf;
    }

    public Collection<MemberGroup> getCanViewProfileOfGroups() {
        return canViewProfileOfGroups;
    }

    public CardType getCardType() {
        return cardType;
    }

    public Collection<Channel> getChannels() {
        return channels;
    }

    public Collection<CustomField> getCustomFields() {
        return customFields;
    }

    public Collection<Channel> getDefaultChannels() {
        return defaultChannels;
    }

    public Collection<Message.Type> getDefaultMailMessages() {
        return defaultMailMessages;
    }

    public Collection<Message.Type> getDefaultSmsMessages() {
        return defaultSmsMessages;
    }

    public Collection<TransactionFee> getFromTransactionFees() {
        return fromTransactionFees;
    }

    public String getInitialGroupShow() {
        return initialGroupShow;
    }

    public Collection<AdminGroup> getManagedByGroups() {
        return managedByGroups;
    }

    public MemberGroupSettings getMemberSettings() {
        if (memberSettings == null) {
            memberSettings = new MemberGroupSettings();
        }
        return memberSettings;
    }

    @Override
    public Nature getNature() {
        return Nature.MEMBER;
    }

    public Collection<BrokerGroup> getPossibleInitialGroupOf() {
        return possibleInitialGroupOf;
    }

    public RegistrationAgreement getRegistrationAgreement() {
        return registrationAgreement;
    }

    public Collection<Channel> getRequestPaymentByChannels() {
        return requestPaymentByChannels;
    }

    public Collection<Message.Type> getSmsMessages() {
        return smsMessages;
    }

    public Collection<TransactionFee> getToTransactionFees() {
        return toTransactionFees;
    }

    public boolean isActive() {
        return active;
    }

    public boolean isBroker() {
        return false;
    }

    public boolean isDefaultAcceptFreeMailing() {
        return defaultAcceptFreeMailing;
    }

    public boolean isDefaultAcceptPaidMailing() {
        return defaultAcceptPaidMailing;
    }

    public boolean isDefaultAllowChargingSms() {
        return defaultAllowChargingSms;
    }

    public boolean isInitialGroup() {
        return initialGroup;
    }

    public void setAccountFees(final Collection<AccountFee> accountFees) {
        this.accountFees = accountFees;
    }

    public void setAccountSettings(final Collection<MemberGroupAccountSettings> settings) {
        accountSettings = settings;
    }

    public void setActive(final boolean active) {
        this.active = active;
    }

    public void setCanBuyWithPaymentObligationsFromGroups(final Collection<MemberGroup> canBuyWithPaymentObligationsFromGroups) {
        this.canBuyWithPaymentObligationsFromGroups = canBuyWithPaymentObligationsFromGroups;
    }

    public void setCanIssueCertificationToGroups(final Collection<MemberGroup> canIssueCertificationToGroups) {
        this.canIssueCertificationToGroups = canIssueCertificationToGroups;
    }

    public void setCanViewAdsOfGroups(final Collection<MemberGroup> canViewAdsOfGroups) {
        this.canViewAdsOfGroups = canViewAdsOfGroups;
    }

    public void setCanViewGroupFilters(final Collection<GroupFilter> canViewGroupFilters) {
        this.canViewGroupFilters = canViewGroupFilters;
    }

    public void setCanViewInformationOf(final Collection<AccountType> canViewInformationOf) {
        this.canViewInformationOf = canViewInformationOf;
    }

    public void setCanViewProfileOfGroups(final Collection<MemberGroup> canViewProfileOfGroups) {
        this.canViewProfileOfGroups = canViewProfileOfGroups;
    }

    public void setCardType(final CardType cardType) {
        this.cardType = cardType;
    }

    public void setChannels(final Collection<Channel> channels) {
        this.channels = channels;
    }

    public void setCustomFields(final Collection<CustomField> customFields) {
        this.customFields = customFields;
    }

    public void setDefaultAcceptFreeMailing(final boolean defaultAcceptFreeMailing) {
        this.defaultAcceptFreeMailing = defaultAcceptFreeMailing;
    }

    public void setDefaultAcceptPaidMailing(final boolean defaultAcceptPaidMailing) {
        this.defaultAcceptPaidMailing = defaultAcceptPaidMailing;
    }

    public void setDefaultAllowChargingSms(final boolean defaultAllowChargingSms) {
        this.defaultAllowChargingSms = defaultAllowChargingSms;
    }

    public void setDefaultChannels(final Collection<Channel> defaultChannels) {
        this.defaultChannels = defaultChannels;
    }

    public void setDefaultMailMessages(final Collection<Message.Type> defaultMailMessages) {
        this.defaultMailMessages = defaultMailMessages;
    }

    public void setDefaultSmsMessages(final Collection<Message.Type> defaultSmsMessages) {
        this.defaultSmsMessages = defaultSmsMessages;
    }

    public void setFromTransactionFees(final Collection<TransactionFee> fromTransactionFees) {
        this.fromTransactionFees = fromTransactionFees;
    }

    public void setInitialGroup(final boolean initialGroup) {
        this.initialGroup = initialGroup;
    }

    public void setInitialGroupShow(final String initialGroupShow) {
        this.initialGroupShow = initialGroupShow;
    }

    public void setManagedByGroups(final Collection<AdminGroup> managedByGroups) {
        this.managedByGroups = managedByGroups;
    }

    public void setMemberSettings(MemberGroupSettings settings) {
        if (settings == null) {
            settings = new MemberGroupSettings();
        }
        memberSettings = settings;
    }

    public void setPossibleInitialGroupOf(final Collection<BrokerGroup> possibleInitialGroupOf) {
        this.possibleInitialGroupOf = possibleInitialGroupOf;
    }

    public void setRegistrationAgreement(final RegistrationAgreement registrationAgreement) {
        this.registrationAgreement = registrationAgreement;
    }

    public void setRequestPaymentByChannels(final Collection<Channel> requestPaymentByChannels) {
        this.requestPaymentByChannels = requestPaymentByChannels;
    }

    public void setSmsMessages(final Collection<Message.Type> smsMessages) {
        this.smsMessages = smsMessages;
    }

    public void setToTransactionFees(final Collection<TransactionFee> toTransactionFees) {
        this.toTransactionFees = toTransactionFees;
    }
}
