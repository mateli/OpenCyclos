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
package nl.strohalm.cyclos.services.elements;

public enum WhenSaving {

    /**
     * An user saving his profile
     */
    PROFILE,

    /**
     * A public member registration
     */
    PUBLIC,

    /**
     * Using the registerMember web service
     */
    WEB_SERVICE,

    /**
     * An admin registering a member
     */
    MEMBER_BY_ADMIN,

    /**
     * An admin registering another admin
     */
    ADMIN_BY_ADMIN,

    /**
     * A brokering registering a member
     */
    BY_BROKER,

    /**
     * A member registering an operator
     */
    OPERATOR,

    /**
     * A member import
     */
    IMPORT,

    /**
     * An e-mail validation
     */
    EMAIL_VALIDATION;

    public boolean isPreHashed() {
        return this == IMPORT || this == EMAIL_VALIDATION;
    }
}
