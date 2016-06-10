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
import nl.strohalm.cyclos.entities.members.Element;

/**
 * Logs an username changing
 * @author luis
 */
public class UsernameChangeLog extends Entity {

    public static enum Relationships implements Relationship {
        BY("by"), USER("user");
        private final String name;

        private Relationships(final String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    private static final long serialVersionUID = 545429548353183777L;
    private Calendar          date;
    private Element           by;
    private User              user;
    private String            previousUsername;
    private String            newUsername;

    public Element getBy() {
        return by;
    }

    public Calendar getDate() {
        return date;
    }

    public String getNewUsername() {
        return newUsername;
    }

    public String getPreviousUsername() {
        return previousUsername;
    }

    public User getUser() {
        return user;
    }

    public void setBy(final Element by) {
        this.by = by;
    }

    public void setDate(final Calendar date) {
        this.date = date;
    }

    public void setNewUsername(final String newUsername) {
        this.newUsername = newUsername;
    }

    public void setPreviousUsername(final String previousUsername) {
        this.previousUsername = previousUsername;
    }

    public void setUser(final User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return getId() + ", Previous username: " + previousUsername + ", New username: " + newUsername + ", user: " + user;
    }
}
