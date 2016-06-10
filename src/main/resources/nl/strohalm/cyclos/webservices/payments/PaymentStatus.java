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
 * Possible statuses for a payment
 * @author luis
 */
public enum PaymentStatus {

    /**
     * The payment was successfully processed
     */
    PROCESSED,

    /**
     * The payment was created, but is awaiting authorization
     */
    PENDING_AUTHORIZATION,

    /**
     * Invalid credentials was entered
     */
    INVALID_CREDENTIALS,

    /**
     * Credentials were blocked by exceeding allowed tries
     */
    BLOCKED_CREDENTIALS,

    /**
     * The payment was being performed from a channel the member doesn't have access
     */
    INVALID_CHANNEL,

    /**
     * One or more parameters were invalid
     */
    INVALID_PARAMETERS,

    /**
     * The given from member was not found
     */
    FROM_NOT_FOUND,

    /**
     * The given to member was not found
     */
    TO_NOT_FOUND,

    /**
     * The payment couldn't be performed, because there was not enough amount on the source account
     */
    NOT_ENOUGH_CREDITS,

    /**
     * The payment couldn't be performed, because the maximum amount today has been exceeded
     */
    MAX_DAILY_AMOUNT_EXCEEDED,

    /**
     * The payment couldn't be performed, because the destination account would surpass it's upper credit limit
     */
    RECEIVER_UPPER_CREDIT_LIMIT_REACHED,

    /**
     * In a bulk action, when a payment result in error, all next payments will have this status
     */
    NOT_PERFORMED,

    /**
     * Any other unexpected error will fall in this category
     */
    UNKNOWN_ERROR;

    /**
     * Returns whether this status is success
     */
    public boolean isSuccessful() {
        return this == PROCESSED || this == PENDING_AUTHORIZATION;
    }
}
