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

import nl.strohalm.cyclos.entities.Entity;
import nl.strohalm.cyclos.entities.Relationship;

/**
 * Logs when a member has accepted a given registration agreement
 * 
 * @author luis
 */
public class RegistrationAgreementLog extends Entity {

    public static enum Relationships implements Relationship {
        MEMBER("member"), REGISTRATION_AGREEMENT("registrationAgreement");
        private final String name;

        private Relationships(final String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    private static final long     serialVersionUID = 8795634135337909851L;

    private Member                member;
    private RegistrationAgreement registrationAgreement;
    private Calendar              date;
    private String                remoteAddress;

    public Calendar getDate() {
        return date;
    }

    public Member getMember() {
        return member;
    }

    public RegistrationAgreement getRegistrationAgreement() {
        return registrationAgreement;
    }

    public String getRemoteAddress() {
        return remoteAddress;
    }

    public void setDate(final Calendar date) {
        this.date = date;
    }

    public void setMember(final Member member) {
        this.member = member;
    }

    public void setRegistrationAgreement(final RegistrationAgreement registrationAgreement) {
        this.registrationAgreement = registrationAgreement;
    }

    public void setRemoteAddress(final String remoteAddress) {
        this.remoteAddress = remoteAddress;
    }

    @Override
    public String toString() {
        return getId() + " - " + member + " accepted " + registrationAgreement;
    }

}
