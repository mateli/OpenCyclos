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
package nl.strohalm.cyclos.services.access;

/**
 * The possible statuses for a credential validation
 * @author luis
 */
public enum CredentialError {

    /**
     * 
     */
    INVALID,

    /**
     * The credential must include letters and numbers
     */
    MUST_INCLUDE_LETTERS_AND_NUMBERS,

    /**
     * The credential must be numeric
     */
    MUST_BE_NUMERIC,

    /**
     * The credential is too simple (ex: abcd, 123456 or same value as a custom field, like birth date or phone)
     */
    TOO_SIMPLE,

    /**
     * The credential was already used on the past
     */
    ALREADY_USED,

    /**
     * The credential has the same value as another credential (like a PIN which is equal to the login password)
     */
    SAME_AS_OTHER_CREDENTIAL

}
