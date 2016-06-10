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
package nl.strohalm.cyclos.webservices.payments;

/**
 * Contains the possible status for a chargeback attempt
 * @author luis
 */
public enum ChargebackStatus {

    /**
     * The chargeback was successful
     */
    SUCCESS,

    /**
     * The transfer to chargeback / reverse was not found
     */
    TRANSFER_NOT_FOUND,

    /**
     * The given transfer was already charged back / reversed
     */
    TRANSFER_ALREADY_CHARGEDBACK,

    /**
     * An invalid transfer to chargeback / reverse was passed
     */
    INVALID_PARAMETERS,

    /**
     * The given transfer cannot be charged back, for example, because the period has passed
     */
    TRANSFER_CANNOT_BE_CHARGEDBACK,

    /**
     * The transfer haven't been tried to be charged back
     */
    NOT_PERFORMED;

    /**
     * Returns whether this status is success
     */
    public boolean isSuccessful() {
        return this == SUCCESS;
    }

}
