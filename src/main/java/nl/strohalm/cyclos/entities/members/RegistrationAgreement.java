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

/**
 * A registration agreement is used by groups, and are displayed to members on public registration or right after confirming the e-mail
 * 
 * @author luis
 */
public class RegistrationAgreement extends Entity {

    private static final long serialVersionUID = 5487938819900951265L;
    private String            name;
    private String            contents;

    public String getContents() {
        return contents;
    }

    public String getName() {
        return name;
    }

    public void setContents(final String contents) {
        this.contents = contents;
    }

    public void setName(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return getId() + " - " + name;
    }

}
