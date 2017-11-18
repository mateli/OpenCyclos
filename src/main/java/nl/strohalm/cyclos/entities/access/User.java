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
package nl.strohalm.cyclos.entities.access;

import nl.strohalm.cyclos.entities.Entity;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.utils.StringValuedEnum;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Inheritance;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Calendar;
import java.util.Collection;

/**
 * An user is the entity that contains login / password / transaction
 * @author luis
 */
@Inheritance
@DiscriminatorColumn(name = "subclass", length = 1)
@Table(name = "users")
@javax.persistence.Entity
public abstract class User extends Entity {

    public static enum Relationships implements Relationship {
        ELEMENT("element"), LOGIN_HISTORY("loginHistory");
        private final String name;

        private Relationships(final String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }
    }

    public static enum TransactionPasswordStatus implements StringValuedEnum {
        ACTIVE("A"), PENDING("P"), BLOCKED("B"), NEVER_CREATED("N");

        private final String value;

        private TransactionPasswordStatus(final String value) {
            this.value = value;
        }

        @Override
        public String getValue() {
            return value;
        }

        public boolean isGenerationAllowed() {
            return this == PENDING || this == NEVER_CREATED;
        }
    }

    private static final long           serialVersionUID          = 545429548353183777L;

    @OneToOne(mappedBy = "user")
	private Element                     element;

    @Column(length = 32)
    private String                      salt;

    @Column(length = 64, unique = true, nullable = false) // index="ix_username"
    private String                      username;

    @Column(name = "last_login")
    private Calendar                    lastLogin;

    @Column(length = 64)
    private String                      password;

    @Column(name = "password_date")
    private Calendar                    passwordDate;

    @Column(name = "password_blocked_until")
    private Calendar                    passwordBlockedUntil;

    @Column(name = "transaction_password", length = 64)
    private String                      transactionPassword;

    @Convert(converter = TransactionPasswordStatusAttributeConverter.class)
    @Column(name = "transaction_password_status", length = 1, nullable = false)
	private TransactionPasswordStatus   transactionPasswordStatus = TransactionPasswordStatus.NEVER_CREATED;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
	private Collection<LoginHistoryLog> loginHistory;

	public Element getElement() {
        return element;
    }

    public Calendar getLastLogin() {
        return lastLogin;
    }

    public Collection<LoginHistoryLog> getLoginHistory() {
        return loginHistory;
    }

    public String getPassword() {
        return password;
    }

    public Calendar getPasswordBlockedUntil() {
        return passwordBlockedUntil;
    }

    public Calendar getPasswordDate() {
        return passwordDate;
    }

    public String getSalt() {
        return salt;
    }

    public String getTransactionPassword() {
        return transactionPassword;
    }

    public TransactionPasswordStatus getTransactionPasswordStatus() {
        return transactionPasswordStatus;
    }

    public String getUsername() {
        return username;
    }

    public void setElement(final Element element) {
        this.element = element;
    }

    public void setLastLogin(final Calendar lastLogin) {
        this.lastLogin = lastLogin;
    }

    public void setLoginHistory(final Collection<LoginHistoryLog> loginHistory) {
        this.loginHistory = loginHistory;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public void setPasswordBlockedUntil(final Calendar passwordBlockedUntil) {
        this.passwordBlockedUntil = passwordBlockedUntil;
    }

    public void setPasswordDate(final Calendar passwordGeneratedAt) {
        passwordDate = passwordGeneratedAt;
    }

    public void setSalt(final String salt) {
        this.salt = salt;
    }

    public void setTransactionPassword(final String transactionPassword) {
        this.transactionPassword = transactionPassword;
    }

    public void setTransactionPasswordStatus(final TransactionPasswordStatus transactionPasswordStatus) {
        this.transactionPasswordStatus = transactionPasswordStatus;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return element == null ? getId() + " - " + getUsername() : element.toString();
    }
}
