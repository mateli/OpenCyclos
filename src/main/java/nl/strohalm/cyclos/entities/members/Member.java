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
package nl.strohalm.cyclos.entities.members;

import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.access.Channel;
import nl.strohalm.cyclos.entities.access.MemberUser;
import nl.strohalm.cyclos.entities.accounts.AccountOwner;
import nl.strohalm.cyclos.entities.accounts.cards.Card;
import nl.strohalm.cyclos.entities.accounts.loans.LoanGroup;
import nl.strohalm.cyclos.entities.accounts.pos.MemberPos;
import nl.strohalm.cyclos.entities.ads.Ad;
import nl.strohalm.cyclos.entities.alerts.MemberAlert;
import nl.strohalm.cyclos.entities.customization.fields.MemberCustomField;
import nl.strohalm.cyclos.entities.customization.fields.MemberCustomFieldValue;
import nl.strohalm.cyclos.entities.customization.images.MemberImage;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.members.brokerings.Brokering;
import nl.strohalm.cyclos.entities.members.preferences.NotificationPreference;
import org.apache.commons.collections.CollectionUtils;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import java.util.Calendar;
import java.util.Collection;

/**
 * A member is a normal Cyclos user. Brokers are members in a broker group.
 * @author luis
 */
@javax.persistence.Entity
public class Member extends Element implements RegisteredMember, AccountOwner {

    public static enum Relationships implements Relationship {
        ADS("ads"), BROKER("broker"), BROKERED_MEMBERS("brokeredMembers"), BROKERINGS("brokerings"), BROKERINGS_AS_BROKERED("brokeringsAsBrokered"), CUSTOM_VALUES("customValues"), IMAGES("images"), LOAN_GROUPS("loanGroups"), MESSAGES("messages"), REFERENCES_FROM("referencesFrom"), REFERENCES_TO("referencesTo"), REMARKS("remarks"), CONTACTS("contacts"), ALERTS("alerts"), NOTIFICATION_PREFERENCES("notificationPreferences"), CHANNELS("channels"), REGISTRATION_AGREEMENT_LOGS("registrationAgreementLogs");
        private final String name;

        private Relationships(final String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }
    }

    private static final long                    serialVersionUID = -1702931176820698931L;

    @Column(name = "member_activation_date")
    private Calendar                             activationDate;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.REMOVE)
	private Collection<Ad>                       ads;

    @ManyToOne
    @JoinColumn(name = "member_broker_id")
	private Member                               broker;

    @Column(name = "hide_email")
    private boolean                              hideEmail;

    @OneToMany(mappedBy = "broker")
	private Collection<Member>                   brokeredMembers;

    @OneToMany(mappedBy = "brokered", cascade = CascadeType.REMOVE)
    @OrderBy("startDate desc")
	private Collection<Brokering>                brokerings;

    @OneToMany(mappedBy = "broker", cascade = CascadeType.REMOVE)
    @OrderBy("startDate desc")
	private Collection<Brokering>                brokeringsAsBrokered;

    @OneToMany(mappedBy = "contact", cascade = CascadeType.REMOVE)
	private Collection<Contact>                  contacts;

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
	private Collection<MemberCustomFieldValue>   customValues;

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
	private Collection<MemberImage>              images;

    @ManyToMany
    @JoinTable(name = "members_loan_groups",
            joinColumns = @JoinColumn(name = "member_id"),
            inverseJoinColumns = @JoinColumn(name = "loan_group_id"))
    private Collection<LoanGroup>                loanGroups;

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
	private Collection<MemberAlert>              alerts;

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
	private Collection<NotificationPreference>   notificationPreferences;

    @ManyToMany
    @JoinTable(name = "members_channels",
            joinColumns = @JoinColumn(name = "member_id"),
            inverseJoinColumns = @JoinColumn(name = "channel_id"))
	private Collection<Channel>                  channels;

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
	private Collection<RegistrationAgreementLog> registrationAgreementLogs;

    @OneToMany(mappedBy = "owner")
	private Collection<Card>                     cards;

    @OneToMany(mappedBy = "member")
	private Collection<MemberPos>                posDevices;

	@Override
    public AccountOwner getAccountOwner() {
        return this;
    }

    public Calendar getActivationDate() {
        return activationDate;
    }

    public Collection<Ad> getAds() {
        return ads;
    }

    public Collection<MemberAlert> getAlerts() {
        return alerts;
    }

    @Override
    public Member getBroker() {
        return broker;
    }

    public Collection<Member> getBrokeredMembers() {
        return brokeredMembers;
    }

    public Collection<Brokering> getBrokerings() {
        return brokerings;
    }

    public Collection<Brokering> getBrokeringsAsBrokered() {
        return brokeringsAsBrokered;
    }

    public Collection<Card> getCards() {
        return cards;
    }

    /**
     * PLEASE DON'T USE THIS ACCESSOR TO GET THE MEMBER'S ENABLED CHANNELS, INSTEAD OF USE
     * {@link nl.strohalm.cyclos.services.access.AccessService#getChannelsEnabledForMember(Member)}
     * @return the channels associated to the member (not the enabled ones)
     */
    public Collection<Channel> getChannels() {
        return channels;
    }

    public Collection<Contact> getContacts() {
        return contacts;
    }

    @Override
    public Class<MemberCustomField> getCustomFieldClass() {
        return MemberCustomField.class;
    }

    @Override
    public Class<MemberCustomFieldValue> getCustomFieldValueClass() {
        return MemberCustomFieldValue.class;
    }

    @Override
    public Collection<MemberCustomFieldValue> getCustomValues() {
        return customValues;
    }

    public Collection<MemberImage> getImages() {
        return images;
    }

    public Collection<LoanGroup> getLoanGroups() {
        return loanGroups;
    }

    @Override
    public MemberGroup getMemberGroup() {
        return (MemberGroup) super.getGroup();
    }

    public MemberUser getMemberUser() {
        return (MemberUser) super.getUser();
    }

    @Override
    public Element.Nature getNature() {
        return Element.Nature.MEMBER;
    }

    public Collection<NotificationPreference> getNotificationPreferences() {
        return notificationPreferences;
    }

    public Collection<MemberPos> getPosDevices() {
        return posDevices;
    }

    public Collection<RegistrationAgreementLog> getRegistrationAgreementLogs() {
        return registrationAgreementLogs;
    }

    @Override
    public boolean isActive() {
        return getMemberGroup().isActive() && activationDate != null;
    }

    public boolean isHasImages() {
        return CollectionUtils.isNotEmpty(getImages());
    }

    @Override
    public boolean isHideEmail() {
        return hideEmail;
    }

    public void setActivationDate(final Calendar activationDate) {
        this.activationDate = activationDate;
    }

    public void setAds(final Collection<Ad> ads) {
        this.ads = ads;
    }

    public void setAlerts(final Collection<MemberAlert> alerts) {
        this.alerts = alerts;
    }

    public void setBroker(final Member broker) {
        this.broker = broker;
    }

    public void setBrokeredMembers(final Collection<Member> brokeredMembers) {
        this.brokeredMembers = brokeredMembers;
    }

    public void setBrokerings(final Collection<Brokering> brokerings) {
        this.brokerings = brokerings;
    }

    public void setBrokeringsAsBrokered(final Collection<Brokering> brokeringsAsBrokered) {
        this.brokeringsAsBrokered = brokeringsAsBrokered;
    }

    public void setCards(final Collection<Card> cards) {
        this.cards = cards;
    }

    public void setChannels(final Collection<Channel> channels) {
        this.channels = channels;
    }

    public void setContacts(final Collection<Contact> contacts) {
        this.contacts = contacts;
    }

    @Override
    public void setCustomValues(final Collection<MemberCustomFieldValue> customValues) {
        this.customValues = customValues;
    }

    public void setHideEmail(final boolean hideEmail) {
        this.hideEmail = hideEmail;
    }

    public void setImages(final Collection<MemberImage> images) {
        this.images = images;
    }

    public void setLoanGroups(final Collection<LoanGroup> loanGroups) {
        this.loanGroups = loanGroups;
    }

    public void setNotificationPreferences(final Collection<NotificationPreference> notificationPreferences) {
        this.notificationPreferences = notificationPreferences;
    }

    public void setPosDevices(final Collection<MemberPos> posDevices) {
        this.posDevices = posDevices;
    }

    public void setRegistrationAgreementLogs(final Collection<RegistrationAgreementLog> registrationAgreementLogs) {
        this.registrationAgreementLogs = registrationAgreementLogs;
    }

}
