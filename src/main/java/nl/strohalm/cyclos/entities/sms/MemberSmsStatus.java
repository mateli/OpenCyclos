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

import nl.strohalm.cyclos.entities.Entity;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.members.Member;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Calendar;

/**
 * Stores the SMS status for an specific member
 * 
 * @author luis
 */
@Table(name = "member_sms_status")
@javax.persistence.Entity
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

    @ManyToOne
    @JoinColumn(name = "member_id", unique = true, nullable = false)
	private Member            member;

    @Column(name = "free_sms_sent", nullable = false)
    private int               freeSmsSent;

    @Column(name = "free_sms_expiration")
    private Calendar          freeSmsExpiration;

    @Column(name = "paid_sms_left", nullable = false)
    private int               paidSmsLeft;

    @Column(name = "paid_sms_expiration")
    private Calendar          paidSmsExpiration;

    @Column(name = "allow_charging_sms", nullable = false)
    private boolean           allowChargingSms;

    @Column(name = "accept_free_mailing", nullable = false)
    private boolean           acceptFreeMailing;

    @Column(name = "accept_paid_mailing", nullable = false)
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
