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

import nl.strohalm.cyclos.entities.Entity;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.customization.fields.MemberCustomField;
import nl.strohalm.cyclos.entities.customization.fields.MemberCustomFieldValue;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.settings.LocalSettings;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Calendar;
import java.util.Collection;
import java.util.Map;

/**
 * A pending member is a member which still have not validated it's registration by mail, and is removed if not confirmed within an amount of time (in
 * {@link LocalSettings#getDeletePendingRegistrationsAfter()})
 * @author luis
 */
@Table(name = "pending_members")
@javax.persistence.Entity
public class PendingMember extends Entity implements RegisteredMember {

    public static enum Relationships implements Relationship {
        MEMBER("member"), MEMBER_GROUP("memberGroup"), BROKER("broker"), REGISTRATION_AGREEMENT("registrationAgreement"), CUSTOM_VALUES("customValues");
        private final String name;

        private Relationships(final String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    private static final long                  serialVersionUID = 6961857645089403722L;

    @Column(name = "creation_date", nullable = false)
    private Calendar                           creationDate;

    @Column(name = "last_email_date")
    private Calendar                           lastEmailDate;

    @ManyToOne
    @JoinColumn(name = "group_id", nullable = false)
	private MemberGroup                        memberGroup;

    @Column(name = "name", nullable = false, length = 100)
    private String                             name;

    @Column(name = "salt", length = 32)
    private String                             salt;

    @Column(name = "username", length = 64)
    private String                             username;

    @Column(name = "password", length = 64)
    private String                             password;

    @Column(name = "pin", length = 64)
    private String                             pin;

    @Column(name = "force_change_password", nullable = false)
    private boolean                            forceChangePassword;

    @Column(name = "email", nullable = false, length = 100)
    private String                             email;

    @Column(name = "validation_key", nullable = false, length = 64)
    private String                             validationKey;

    @Column(name = "hide_email", nullable = false)
    private boolean                            hideEmail;

    @ManyToOne
    @JoinColumn(name = "broker_id")
	private Member                             broker;

    @ManyToOne
    @JoinColumn(name = "registration_agreement_id")
	private RegistrationAgreement              registrationAgreement;

    @Column(name = "registration_agreement_date")
    private Calendar                           registrationAgreementDate;

    @ManyToOne
    @JoinColumn(name = "member_id")
	private Member                             member;

    @Column(name = "remote_address", length = 100)
    private String                             remoteAddress;

    @OneToMany(mappedBy = "pendingMember", cascade = CascadeType.REMOVE)
	private Collection<MemberCustomFieldValue> customValues;

	public Member getBroker() {
        return broker;
    }

    public Calendar getCreationDate() {
        return creationDate;
    }

    public Class<MemberCustomField> getCustomFieldClass() {
        return MemberCustomField.class;
    }

    public Class<MemberCustomFieldValue> getCustomFieldValueClass() {
        return MemberCustomFieldValue.class;
    }

    public Collection<MemberCustomFieldValue> getCustomValues() {
        return customValues;
    }

    public String getEmail() {
        return email;
    }

    public Calendar getLastEmailDate() {
        return lastEmailDate;
    }

    public Member getMember() {
        return member;
    }

    public MemberGroup getMemberGroup() {
        return memberGroup;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getPin() {
        return pin;
    }

    public RegistrationAgreement getRegistrationAgreement() {
        return registrationAgreement;
    }

    public Calendar getRegistrationAgreementDate() {
        return registrationAgreementDate;
    }

    public String getRemoteAddress() {
        return remoteAddress;
    }

    public String getSalt() {
        return salt;
    }

    public String getUsername() {
        return username;
    }

    public String getValidationKey() {
        return validationKey;
    }

    public boolean isForceChangePassword() {
        return forceChangePassword;
    }

    public boolean isHideEmail() {
        return hideEmail;
    }

    public void setBroker(final Member broker) {
        this.broker = broker;
    }

    public void setCreationDate(final Calendar creationDate) {
        this.creationDate = creationDate;
    }

    public void setCustomValues(final Collection<MemberCustomFieldValue> customValues) {
        this.customValues = customValues;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public void setForceChangePassword(final boolean forceChangePassword) {
        this.forceChangePassword = forceChangePassword;
    }

    public void setHideEmail(final boolean hideEmail) {
        this.hideEmail = hideEmail;
    }

    public void setLastEmailDate(final Calendar lastEmailDate) {
        this.lastEmailDate = lastEmailDate;
    }

    public void setMember(final Member member) {
        this.member = member;
    }

    public void setMemberGroup(final MemberGroup memberGroup) {
        this.memberGroup = memberGroup;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public void setPin(final String pin) {
        this.pin = pin;
    }

    public void setRegistrationAgreement(final RegistrationAgreement registrationAgreement) {
        this.registrationAgreement = registrationAgreement;
    }

    public void setRegistrationAgreementDate(final Calendar registrationAgreementDate) {
        this.registrationAgreementDate = registrationAgreementDate;
    }

    public void setRemoteAddress(final String remoteAddress) {
        this.remoteAddress = remoteAddress;
    }

    public void setSalt(final String salt) {
        this.salt = salt;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public void setValidationKey(final String validationKey) {
        this.validationKey = validationKey;
    }

    @Override
    public String toString() {
        return getId() + " - " + name;
    }

    @Override
    protected void appendVariableValues(final Map<String, Object> variables, final LocalSettings localSettings) {
        variables.put("login", username);
        variables.put("name", name);
        variables.put("email", email);
        variables.put("member", name);
    }

}
