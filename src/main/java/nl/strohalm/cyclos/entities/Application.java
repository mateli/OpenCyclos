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
package nl.strohalm.cyclos.entities;

import java.util.Calendar;

import nl.strohalm.cyclos.utils.StringValuedEnum;

/**
 * Contains basic information about the Cyclos application
 * @author luis
 */
public class Application extends Entity {

    /**
     * Defines which algorithm will be used for password hash
     * @author luis
     */
    public static enum PasswordHash implements StringValuedEnum {
        /**
         * SHA-256 with per-user salt: The most secure option, used for new databases since Cyclos 3.6
         */
        SHA2_SALT("T"),

        /**
         * SHA-256 alone. Used for compatibility of databases migrated from Cyclos 3.5
         */
        SHA2("S"),

        /**
         * SHA-256 over MD5. Used for compatibility of databases migrated from Cyclos 3.0
         */
        SHA2_MD5("M");

        private final String value;

        private PasswordHash(final String value) {
            this.value = value;
        }

        @Override
        public String getValue() {
            return value;
        }
    }

    private static final long serialVersionUID = -7552932556180401229L;
    private String            version;
    private Calendar          accountStatusEnabledSince;
    private Calendar          lastIndexRebuidingTime;
    private PasswordHash      passwordHash;
    private boolean           online;

    public Calendar getAccountStatusEnabledSince() {
        return accountStatusEnabledSince;
    }

    public Calendar getLastIndexRebuidingTime() {
        return lastIndexRebuidingTime;
    }

    public PasswordHash getPasswordHash() {
        return passwordHash;
    }

    public String getVersion() {
        return version;
    }

    public boolean isOnline() {
        return online;
    }

    public void setAccountStatusEnabledSince(final Calendar accountStatusEnabledSince) {
        this.accountStatusEnabledSince = accountStatusEnabledSince;
    }

    public void setLastIndexRebuidingTime(final Calendar lastIndexRebuidingTime) {
        this.lastIndexRebuidingTime = lastIndexRebuidingTime;
    }

    public void setOnline(final boolean online) {
        this.online = online;
    }

    public void setPasswordHash(final PasswordHash passwordHash) {
        this.passwordHash = passwordHash;
    }

    public void setVersion(final String version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "Cyclos version " + version;
    }
}
