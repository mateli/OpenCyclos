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

import nl.strohalm.cyclos.entities.Entity;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.utils.FormatObject;

/**
 * Represents a session for an user
 * 
 * @author luis
 */
public class Session extends Entity {

    public static enum Relationships implements Relationship {
        USER("user");
        private final String name;

        private Relationships(final String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }
    }

    private static final long serialVersionUID = 4557047760868996021L;
    private String            identifier;
    private Calendar          creationDate;
    private Calendar          expirationDate;
    private User              user;
    private String            remoteAddress;
    private boolean           posWeb;

    public Calendar getCreationDate() {
        return creationDate;
    }

    public Calendar getExpirationDate() {
        return expirationDate;
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getRemoteAddress() {
        return remoteAddress;
    }

    public User getUser() {
        return user;
    }

    public boolean isPosWeb() {
        return posWeb;
    }

    public void setCreationDate(final Calendar creationDate) {
        this.creationDate = creationDate;
    }

    public void setExpirationDate(final Calendar expirationDate) {
        this.expirationDate = expirationDate;
    }

    public void setIdentifier(final String identifier) {
        this.identifier = identifier;
    }

    public void setPosWeb(final boolean posWeb) {
        this.posWeb = posWeb;
    }

    public void setRemoteAddress(final String remoteAddress) {
        this.remoteAddress = remoteAddress;
    }

    public void setUser(final User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return identifier + ", created at: " + FormatObject.formatObject(creationDate) + ", expires at: " + FormatObject.formatObject(expirationDate) + ", user: " + user;
    }

}
