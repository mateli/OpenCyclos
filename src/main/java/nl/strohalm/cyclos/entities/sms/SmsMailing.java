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
import java.util.Collection;

import nl.strohalm.cyclos.entities.Entity;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.utils.FormatObject;

/**
 * An SMS mailing is a brodcast SMS sent to a group of members
 * 
 * @author luis
 */
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
    private Calendar                date;
    private Element                 by;
    private String                  text;
    private int                     sentSms;
    private boolean                 free;
    private Collection<MemberGroup> groups;
    // if it's not null this mailing will be send only to this member
    private Member                  member;
    private Collection<Member>      pendingToSend;

    private transient boolean       finished;

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
