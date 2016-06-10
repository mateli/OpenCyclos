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
package nl.strohalm.cyclos.webservices.members;

import nl.strohalm.cyclos.webservices.utils.ObjectHelper;

/**
 * Parameters for searching members via web services
 * @author luis
 */
public class MemberSearchParameters extends AbstractMemberSearchParameters {
    private static final long serialVersionUID = 4339524447658080671L;
    private String            username;
    private String            name;
    private String            email;
    private Boolean           randomOrder      = false;

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public boolean getRandomOrder() {
        return ObjectHelper.valueOf(randomOrder);
    }

    public String getUsername() {
        return username;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setRandomOrder(final boolean randomOrder) {
        this.randomOrder = randomOrder;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "MemberSearchParameters [username=" + username + ", name=" + name + ", email=" + email + ", randomOrder=" + randomOrder + ", " + super.toString() + "]";
    }
}
