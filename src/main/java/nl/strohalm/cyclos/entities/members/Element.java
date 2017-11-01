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
import nl.strohalm.cyclos.entities.Indexable;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.access.User;
import nl.strohalm.cyclos.entities.accounts.AccountOwner;
import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.entities.groups.GroupHistoryLog;
import nl.strohalm.cyclos.entities.members.records.MemberRecord;
import nl.strohalm.cyclos.entities.members.remarks.Remark;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.utils.ElementVO;
import nl.strohalm.cyclos.utils.StringValuedEnum;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Inheritance;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import java.util.Calendar;
import java.util.Collection;
import java.util.Map;

/**
 * A common classes for members and administrators
 * @author luis
 */
@Inheritance
@DiscriminatorColumn(name = "subclass", length = 1)
@Table(name = "members")
@javax.persistence.Entity
public abstract class Element extends Entity implements Indexable {

    public static enum Nature implements StringValuedEnum {
        ADMIN("A"), MEMBER("M"), OPERATOR("O");
        private final String value;

        private Nature(final String value) {
            this.value = value;
        }

        public Class<? extends Element> getElementClass() {
            switch (this) {
                case ADMIN:
                    return Administrator.class;
                case MEMBER:
                    return Member.class;
                case OPERATOR:
                    return Operator.class;
            }
            throw new IllegalStateException("Unknown element nature: " + this);
        }

        public Class<? extends Element> getType() {
            return (this == ADMIN ? Administrator.class : Member.class);
        }

        @Override
        public String getValue() {
            return value;
        }
    }

    public static enum Relationships implements Relationship {
        GROUP("group"), USER("user"), GROUP_HISTORY_LOGS("groupHistoryLogs"), MEMBER_RECORDS("memberRecords"), REMARKS("remarks");
        private final String name;

        private Relationships(final String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }
    }

    private static final long           serialVersionUID = 5024785667579155058L;

    @Column(name = "creation_date", nullable = false)
    private Calendar                    creationDate;

    @Column(length = 100) // index="ix_email"
    private String                      email;

    @ManyToOne
    @JoinColumn(name = "group_id", nullable = false)
	private Group                       group;

    @Column(length = 100, nullable = false)
    private String                      name;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
	private User                        user;

    @OneToMany(mappedBy = "element", cascade = CascadeType.REMOVE)
    @OrderBy("period.begin")
	private Collection<GroupHistoryLog> groupHistoryLogs;

    @OneToMany(mappedBy = "subject", cascade = CascadeType.REMOVE)
	private Collection<Remark>          remarks;

    @OneToMany(mappedBy = "by", cascade = CascadeType.REMOVE)
	private Collection<MemberRecord>    memberRecords;

	/**
     * Returns the account owner that this element represents
     */
    public abstract AccountOwner getAccountOwner();

    public Calendar getCreationDate() {
        return creationDate;
    }

    public String getEmail() {
        return email;
    }

    public Group getGroup() {
        return group;
    }

    public Collection<GroupHistoryLog> getGroupHistoryLogs() {
        return groupHistoryLogs;
    }

    public Collection<MemberRecord> getMemberRecords() {
        return memberRecords;
    }

    @Override
    public String getName() {
        return name;
    }

    public abstract Nature getNature();

    public Collection<Remark> getRemarks() {
        return remarks;
    }

    public User getUser() {
        return user;
    }

    public String getUsername() {
        return user == null ? null : user.getUsername();
    }

    /**
     * Determine whether this element is active or not
     */
    public boolean isActive() {
        return group == null ? false : !group.isRemoved();
    }

    @Override
    public final ElementVO readOnlyView() {
        return new ElementVO(getId(), getName(), getUsername(), getGroup().getId(), getNature());
    }

    public void setCreationDate(final Calendar creationDate) {
        this.creationDate = creationDate;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public void setGroup(final Group group) {
        this.group = group;
    }

    public void setGroupHistoryLogs(final Collection<GroupHistoryLog> groupHistoryLogs) {
        this.groupHistoryLogs = groupHistoryLogs;
    }

    public void setMemberRecords(final Collection<MemberRecord> memberRecords) {
        this.memberRecords = memberRecords;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setRemarks(final Collection<Remark> remarks) {
        this.remarks = remarks;
    }

    public void setUser(final User user) {
        this.user = user;
        if (user != null) {
            user.setElement(this);
        }
    }

    @Override
    public String toString() {
        return getId() + " - " + getUsername() + " (" + name + ")";
    }

    @Override
    protected void appendVariableValues(final Map<String, Object> variables, final LocalSettings localSettings) {
        variables.put("login", getUsername());
        variables.put("name", name);
        variables.put("email", email);
        variables.put("member", name);
    }
}
