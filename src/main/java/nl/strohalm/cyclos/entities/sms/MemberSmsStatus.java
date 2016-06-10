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
package nl.strohalm.cyclos.entities.sms;

import java.util.Calendar;

import nl.strohalm.cyclos.entities.Entity;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.members.Member;

/**
 * Stores the SMS status for an specific member
 * 
 * @author luis
 */
public class MemberSmsStatus extends Entity {

    public static enum Relationships implements Relationship {
        MEMBER("member");
        private final String name;

        private Relationships(final String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    private static final long serialVersionUID = 5082258765577170190L;
    private Member            member;
    private int               freeSmsSent;
    private Calendar          freeSmsExpiration;
    private int               paidSmsLeft;
    private Calendar          paidSmsExpiration;
    private boolean           allowChargingSms;
    private boolean           acceptFreeMailing;
    private boolean           acceptPaidMailing;

    public Calendar getFreeSmsExpiration() {
        return freeSmsExpiration;
    }

    public int getFreeSmsSent() {
        return freeSmsSent;
    }

    public Member getMember() {
        return member;
    }

    public Calendar getPaidSmsExpiration() {
        return paidSmsExpiration;
    }

    public int getPaidSmsLeft() {
        return paidSmsLeft;
    }

    public boolean isAcceptFreeMailing() {
        return acceptFreeMailing;
    }

    public boolean isAcceptPaidMailing() {
        return acceptPaidMailing;
    }

    public boolean isAllowChargingSms() {
        return allowChargingSms;
    }

    public void setAcceptFreeMailing(final boolean acceptFreeMailing) {
        this.acceptFreeMailing = acceptFreeMailing;
    }

    public void setAcceptPaidMailing(final boolean acceptPaidMailing) {
        this.acceptPaidMailing = acceptPaidMailing;
    }

    public void setAllowChargingSms(final boolean allowChargingSms) {
        this.allowChargingSms = allowChargingSms;
    }

    public void setFreeSmsExpiration(final Calendar freeSmsExpiration) {
        this.freeSmsExpiration = freeSmsExpiration;
    }

    public void setFreeSmsSent(final int freeSmsSent) {
        this.freeSmsSent = freeSmsSent;
    }

    public void setMember(final Member member) {
        this.member = member;
    }

    public void setPaidSmsExpiration(final Calendar paidSmsExpiration) {
        this.paidSmsExpiration = paidSmsExpiration;
    }

    public void setPaidSmsLeft(final int paidSmsLeft) {
        this.paidSmsLeft = paidSmsLeft;
    }

    @Override
    public String toString() {
        return getId() + " - member: " + member;
    }

}
