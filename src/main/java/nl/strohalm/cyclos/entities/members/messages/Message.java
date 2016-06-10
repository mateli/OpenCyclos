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
package nl.strohalm.cyclos.entities.members.messages;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import nl.strohalm.cyclos.entities.Entity;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.utils.StringValuedEnum;

import org.apache.commons.collections.CollectionUtils;

/**
 * A message sent to a member or to system
 * @author luis
 */
public class Message extends Entity {

    /**
     * A direction for a given message
     * @author luis
     */
    public static enum Direction implements StringValuedEnum {
        INCOMING("I"), OUTGOING("O");
        private final String value;

        private Direction(final String value) {
            this.value = value;
        }

        @Override
        public String getValue() {
            return value;
        }

        public Direction invert() {
            if (this == INCOMING) {
                return OUTGOING;
            } else {
                return INCOMING;
            }
        }
    }

    public static enum Relationships implements Relationship {
        CATEGORY("category"), FROM_MEMBER("fromMember"), TO_MEMBER("toMember"), TO_GROUPS("toGroups"), PENDING_TO_SEND("pendingToSend");
        private final String name;

        private Relationships(final String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }
    }

    /**
     * A first level message type
     * @author luis
     */
    public static enum RootType {
        MEMBER, ADMIN, SYSTEM;
    }

    /**
     * A second level message type
     * @author luis
     */
    public static enum Type implements StringValuedEnum {
        FROM_MEMBER(RootType.MEMBER, "mbr"), FROM_ADMIN_TO_MEMBER(RootType.ADMIN, "a2m"), FROM_ADMIN_TO_GROUP(RootType.ADMIN, "a2g"), ACCESS(RootType.SYSTEM, "acs"), ACCOUNT(RootType.SYSTEM, "act"), BROKERING(RootType.SYSTEM, "brk"), PAYMENT(RootType.SYSTEM, "pmt"), EXTERNAL_PAYMENT(RootType.SYSTEM, "ept"), LOAN(RootType.SYSTEM, "loa"), AD_EXPIRATION(RootType.SYSTEM, "ade"), AD_INTEREST(RootType.SYSTEM, "adi"), INVOICE(RootType.SYSTEM, "inv"), REFERENCE(RootType.SYSTEM, "ref"), TRANSACTION_FEEDBACK(RootType.SYSTEM, "tfb"), CERTIFICATION(RootType.SYSTEM, "cer"), GUARANTEE(RootType.SYSTEM, "gua"), PAYMENT_OBLIGATION(RootType.SYSTEM, "pob");

        /**
         * Returns the second level message types given a first level one
         */
        public static List<Type> listByRootType(final RootType rootType) {
            final List<Type> types = new ArrayList<Type>();
            if (rootType != null) {
                for (final Type type : values()) {
                    if (type.getRootType() == rootType) {
                        types.add(type);
                    }
                }
            }
            return types;
        }

        private final String   value;
        private final RootType rootType;

        private Type(final RootType rootType, final String value) {
            this.rootType = rootType;
            this.value = value;
        }

        public RootType getRootType() {
            return rootType;
        }

        @Override
        public String getValue() {
            return value;
        }

        /**
         * Returns whether this type can send multiple mails at once
         */
        public boolean isMass() {
            return this == FROM_ADMIN_TO_GROUP || this == BROKERING;
        }
    }

    private static final long serialVersionUID = -2421844991813820706L;

    private Calendar          date;
    private Member            fromMember;
    private Member            toMember;
    private Direction         direction;
    private String            subject;
    private String            body;
    private MessageCategory   category;
    private Type              type;
    private boolean           read;
    private Calendar          removedAt;
    private boolean           replied;
    private boolean           html;
    private boolean           emailSent;
    private List<MemberGroup> toGroups;

    public String getBody() {
        return body;
    }

    public MessageCategory getCategory() {
        return category;
    }

    public Calendar getDate() {
        return date;
    }

    public Direction getDirection() {
        return direction;
    }

    public Member getFromMember() {
        return fromMember;
    }

    public MessageBox getMessageBox() {
        if (direction == null) {
            throw new IllegalStateException("Cannot determine message box - direction is null");
        }
        if (removedAt != null) {
            return MessageBox.TRASH;
        } else {
            if (direction == Message.Direction.INCOMING) {
                return MessageBox.INBOX;
            } else {
                return MessageBox.SENT;
            }
        }
    }

    /**
     * Resolve the message's owner: toMember when incoming, fromMember when outgoing
     */
    public Member getOwner() {
        if (direction == null) {
            throw new IllegalStateException("Cannot determine message owner - direction is null");
        }
        switch (direction) {
            case INCOMING:
                return toMember;
            case OUTGOING:
                return fromMember;
            default:
                return null;
        }
    }

    /**
     * Resolve the message's related member: toMember when outgoing, fromMember when incoming
     */
    public Member getRelatedMember() {
        if (direction == null) {
            throw new IllegalStateException("Cannot determine message related member - direction is null");
        }
        switch (direction) {
            case INCOMING:
                return fromMember;
            case OUTGOING:
                return toMember;
            default:
                return null;
        }
    }

    public Calendar getRemovedAt() {
        return removedAt;
    }

    public String getSubject() {
        return subject;
    }

    public List<MemberGroup> getToGroups() {
        return toGroups;
    }

    public Member getToMember() {
        return toMember;
    }

    public Type getType() {
        return type;
    }

    public boolean isBulk() {
        return type == Type.FROM_ADMIN_TO_GROUP;
    }

    public boolean isEmailSent() {
        return emailSent;
    }

    public boolean isFromAdministration() {
        return !isFromSystem() && !isFromAMember();
    }

    public boolean isFromAMember() {
        return fromMember != null;
    }

    public boolean isFromSystem() {
        return type != null && type.getRootType() == RootType.SYSTEM;
    }

    public boolean isHtml() {
        return html;
    }

    public boolean isRead() {
        return read;
    }

    public boolean isRemoved() {
        return removedAt != null;
    }

    public boolean isReplied() {
        return replied;
    }

    public boolean isToAdministration() {
        return isFromAMember() && !isToAMember() && type == Type.FROM_MEMBER;
    }

    public boolean isToAGroup() {
        return CollectionUtils.isNotEmpty(toGroups);
    }

    public boolean isToAMember() {
        return toMember != null;
    }

    public boolean isToBrokeredMembers() {
        return isBulk() && isFromAMember();
    }

    public void setBody(final String body) {
        this.body = body;
    }

    public void setCategory(final MessageCategory category) {
        this.category = category;
    }

    public void setDate(final Calendar date) {
        this.date = date;
    }

    public void setDirection(final Direction direction) {
        this.direction = direction;
    }

    public void setEmailSent(final boolean emailSent) {
        this.emailSent = emailSent;
    }

    public void setFromMember(final Member fromMember) {
        this.fromMember = fromMember;
    }

    public void setHtml(final boolean html) {
        this.html = html;
    }

    public void setRead(final boolean read) {
        this.read = read;
    }

    public void setRemovedAt(final Calendar removedAt) {
        this.removedAt = removedAt;
    }

    public void setReplied(final boolean replied) {
        this.replied = replied;
    }

    public void setSubject(final String subject) {
        this.subject = subject;
    }

    public void setToGroups(final List<MemberGroup> toGroups) {
        this.toGroups = toGroups;
    }

    public void setToMember(final Member toMember) {
        this.toMember = toMember;
    }

    public void setType(final Type type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return getId() + " - " + subject;
    }

}
