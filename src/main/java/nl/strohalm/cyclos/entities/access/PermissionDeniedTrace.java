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
import nl.strohalm.cyclos.utils.FormatObject;

/**
 * Stores a permission denied trace
 * 
 * @author luis
 */
public class PermissionDeniedTrace extends Entity {

    private static final long serialVersionUID = -2818655809400309210L;
    private User              user;
    private Calendar          date;

    public Calendar getDate() {
        return date;
    }

    public User getUser() {
        return user;
    }

    public void setDate(final Calendar date) {
        this.date = date;
    }

    public void setUser(final User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return getId() + " - date: " + FormatObject.formatObject(date) + ", user: " + user;
    }

}
