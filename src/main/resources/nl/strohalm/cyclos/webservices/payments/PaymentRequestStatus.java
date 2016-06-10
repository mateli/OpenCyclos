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
 * A possible status code for sending a payment request
 * @author luis
 */
public enum PaymentRequestStatus {

    /**
     * The payment request was received and queued to be sent
     */
    REQUEST_RECEIVED,

    /**
     * The payment was being performed from a channel the payer doesn't have access
     */
    FROM_INVALID_CHANNEL,

    /**
     * The payment was being performed from a channel the receiver doesn't have access
     */
    TO_INVALID_CHANNEL,

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
     * There was an unknown error while sending the request
     */
    UNKNOWN_ERROR;

    /**
     * Returns whether this status is success
     */
    public boolean isSuccessful() {
        return this == REQUEST_RECEIVED;
    }
}
