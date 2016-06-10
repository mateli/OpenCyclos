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
package nl.strohalm.cyclos.webservices.access;

/**
 * Possible statuses for changing credentials
 * 
 * @author luis
 */
public enum ChangeCredentialsStatus {

    /** The credentials were successfully changed */
    SUCCESS,

    /** The member was not found */
    MEMBER_NOT_FOUND,

    /** Invalid current credentials are invalid */
    INVALID_CREDENTIALS,

    /** The credentials are blocked by exceeding attempts */
    BLOCKED_CREDENTIALS,

    /** Thrown when the given credentials contains invalid characters, or when required passwords were not present */
    INVALID_CHARACTERS,

    /** The given credentials are too simple */
    TOO_SIMPLE,

    /** The given credentials were already used in past */
    CREDENTIALS_ALREADY_USED
}
