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

import java.util.Calendar;
import java.util.Collection;
import java.util.Map;

import nl.strohalm.cyclos.entities.Entity;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.customization.fields.MemberCustomField;
import nl.strohalm.cyclos.entities.customization.fields.MemberCustomFieldValue;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.settings.LocalSettings;

/**
 * A pending member is a member which still have not validated it's registration by mail, and is removed if not confirmed within an amount of time (in
 * {@link LocalSettings#getDeletePendingRegistrationsAfter()})
 * @author luis
 */
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
    private Calendar                           creationDate;
    private Calendar                           lastEmailDate;
    private MemberGroup                        memberGroup;
    private String                             name;
    private String                             salt;
    private String                             username;
    private String                             password;
    private String                             pin;
    private boolean                            forceChangePassword;
    private String                             email;
    private String                             validationKey;
    private boolean                            hideEmail;
    private Member                             broker;
    private RegistrationAgreement              registrationAgreement;
    private Calendar                           registrationAgreementDate;
    private Member                             member;
    private String                             remoteAddress;
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
