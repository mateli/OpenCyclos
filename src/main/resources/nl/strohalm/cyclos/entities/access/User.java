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

import java.util.Calendar;
import java.util.Collection;

import nl.strohalm.cyclos.entities.Entity;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.utils.StringValuedEnum;

/**
 * An user is the entity that contains login / password / transaction
 * @author luis
 */
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
    private Element                     element;
    private String                      salt;
    private String                      username;
    private Calendar                    lastLogin;
    private String                      password;
    private Calendar                    passwordDate;
    private Calendar                    passwordBlockedUntil;
    private String                      transactionPassword;
    private TransactionPasswordStatus   transactionPasswordStatus = TransactionPasswordStatus.NEVER_CREATED;
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
