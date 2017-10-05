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
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.utils.FormatObject;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Calendar;
import java.util.Collection;

/**
 * An SMS mailing is a brodcast SMS sent to a group of members
 * 
 * @author luis
 */
@Cacheable
@Table(name = "sms_mailings")
@javax.persistence.Entity
public class SmsMailing extends Entity {

    public static enum Relationships implements Relationship {
        BY("by"), PENDING_TO_SEND("pendingToSend"), GROUPS("groups"), MEMBER("member");
        private final String name;

        private Relationships(final String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }
    }

    private static final long       serialVersionUID = 2525066930563530937L;

    @Column(name = "date", nullable = false, updatable = false)
    private Calendar                date;

    @ManyToOne
    @JoinColumn(name = "by_id", nullable = false)
	private Element                 by;

    @Column(name = "text", nullable = false)
    private String                  text;

    @Column(name = "sent_sms", nullable = false)
    private int                     sentSms;

    @Column(name = "free", nullable = false)
    private boolean                 free;

    @ManyToMany
    @JoinTable(name = "sms_mailings_groups",
            joinColumns = @JoinColumn(name = "sms_mailing_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id"))
	private Collection<MemberGroup> groups;

    // if it's not null this mailing will be send only to this member
    @ManyToOne
    @JoinColumn(name = "member_id")
	private Member                  member;

    @ManyToMany
    @JoinTable(name = "sms_mailings_pending_to_send",
            joinColumns = @JoinColumn(name = "sms_mailing_id"),
            inverseJoinColumns = @JoinColumn(name = "member_id"))
	private Collection<Member>      pendingToSend;

    @Column(name = "finished", nullable = false)
    private boolean       finished;

    protected SmsMailing() {
	}

	public Element getBy() {
        return by;
    }

    public Calendar getDate() {
        return date;
    }

    public Collection<MemberGroup> getGroups() {
        return groups;
    }

    public Member getMember() {
        return member;
    }

    public Collection<Member> getPendingToSend() {
        return pendingToSend;
    }

    public int getSentSms() {
        return sentSms;
    }

    public String getText() {
        return text;
    }

    public SmsMailingType getType() {
        if (isSingleMember()) {
            return SmsMailingType.INDIVIDUAL;
        } else if (by == null || by.getNature() == Element.Nature.ADMIN) {
            return free ? SmsMailingType.FREE_TO_GROUP : SmsMailingType.PAID_TO_GROUP;
        } else {
            return free ? SmsMailingType.FREE_FROM_BROKER : SmsMailingType.PAID_FROM_BROKER;
        }
    }

    public boolean isFinished() {
        return finished;
    }

    public boolean isFree() {
        return free;
    }

    public boolean isSingleMember() {
        return member != null;
    }

    public void setBy(final Element by) {
        this.by = by;
    }

    public void setDate(final Calendar date) {
        this.date = date;
    }

    public void setFinished(final boolean finished) {
        this.finished = finished;
    }

    public void setFree(final boolean free) {
        this.free = free;
    }

    public void setGroups(final Collection<MemberGroup> groups) {
        this.groups = groups;
    }

    public void setMember(final Member member) {
        this.member = member;
    }

    public void setPendingToSend(final Collection<Member> pendingToSend) {
        this.pendingToSend = pendingToSend;
    }

    public void setSentSms(final int sentSms) {
        this.sentSms = sentSms;
    }

    public void setText(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return getId() + " - " + FormatObject.formatObject(date);
    }

}
