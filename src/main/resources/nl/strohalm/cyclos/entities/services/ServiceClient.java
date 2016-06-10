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
package nl.strohalm.cyclos.entities.services;

import java.util.Set;

import nl.strohalm.cyclos.entities.Entity;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.access.Channel;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferType;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.members.Member;

/**
 * A remote host that may access specific web services
 * @author luis
 */
public class ServiceClient extends Entity {

    public static enum Relationships implements Relationship {
        MEMBER("member"), CHANNEL("channel"), PERMISSIONS("permissions"), DO_PAYMENT_TYPES("doPaymentTypes"), RECEIVE_PAYMENT_TYPES("receivePaymentTypes"), CHARGEBACK_PAYMENT_TYPES("chargebackPaymentTypes"), MANAGE_GROUPS("mnageGroups");
        private final String name;

        private Relationships(final String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }
    }

    private static final long     serialVersionUID = -7219751905408334999L;
    private String                name;
    private String                username;
    private String                password;
    private String                addressBegin;
    private String                addressEnd;
    private String                hostname;
    private Member                member;
    private boolean               credentialsRequired;
    private boolean               ignoreRegistrationValidations;
    private Channel               channel;
    private Set<ServiceOperation> permissions;
    private Set<TransferType>     doPaymentTypes;
    private Set<TransferType>     receivePaymentTypes;
    private Set<TransferType>     chargebackPaymentTypes;
    private Set<MemberGroup>      manageGroups;

    public String getAddressBegin() {
        return addressBegin;
    }

    public String getAddressEnd() {
        return addressEnd;
    }

    public Channel getChannel() {
        return channel;
    }

    public Set<TransferType> getChargebackPaymentTypes() {
        return chargebackPaymentTypes;
    }

    public Set<TransferType> getDoPaymentTypes() {
        return doPaymentTypes;
    }

    public String getHostname() {
        return hostname;
    }

    public Set<MemberGroup> getManageGroups() {
        return manageGroups;
    }

    public Member getMember() {
        return member;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public Set<ServiceOperation> getPermissions() {
        return permissions;
    }

    public Set<TransferType> getReceivePaymentTypes() {
        return receivePaymentTypes;
    }

    public String getUsername() {
        return username;
    }

    public boolean isCredentialsRequired() {
        return credentialsRequired;
    }

    public boolean isIgnoreRegistrationValidations() {
        return ignoreRegistrationValidations;
    }

    public void setAddressBegin(final String addressBegin) {
        this.addressBegin = addressBegin;
    }

    public void setAddressEnd(final String addressEnd) {
        this.addressEnd = addressEnd;
    }

    public void setChannel(final Channel channel) {
        this.channel = channel;
    }

    public void setChargebackPaymentTypes(final Set<TransferType> chargebackPaymentTypes) {
        this.chargebackPaymentTypes = chargebackPaymentTypes;
    }

    public void setCredentialsRequired(final boolean credentialsRequired) {
        this.credentialsRequired = credentialsRequired;
    }

    public void setDoPaymentTypes(final Set<TransferType> doPaymentTypes) {
        this.doPaymentTypes = doPaymentTypes;
    }

    public void setHostname(final String hostname) {
        this.hostname = hostname;
    }

    public void setIgnoreRegistrationValidations(final boolean ignoreRegistrationValidations) {
        this.ignoreRegistrationValidations = ignoreRegistrationValidations;
    }

    public void setManageGroups(final Set<MemberGroup> manageGroups) {
        this.manageGroups = manageGroups;
    }

    public void setMember(final Member member) {
        this.member = member;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public void setPermissions(final Set<ServiceOperation> permissions) {
        this.permissions = permissions;
    }

    public void setReceivePaymentTypes(final Set<TransferType> receivePaymentTypes) {
        this.receivePaymentTypes = receivePaymentTypes;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return getId() + " - " + name;
    }

}
