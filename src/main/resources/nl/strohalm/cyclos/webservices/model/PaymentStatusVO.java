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
package nl.strohalm.cyclos.webservices.model;

/**
 * Contains the possible status for a payment
 * @author luis
 */
public enum PaymentStatusVO {
    /**
     * The payment is subject to authorization
     */
    PENDING,

    /**
     * The payment has been successfully processed
     */
    PROCESSED,

    /**
     * The authorizer has denied the payment
     */
    DENIED,

    /**
     * The payment performer has canceled this payment before it's been completely processed
     */
    CANCELED,

    /**
     * The payment is scheduled for future processing
     */
    SCHEDULED,

    /**
     * The payment couldn't be processed (i.e: not enough credits)
     */
    FAILED,

    /**
     * The payment has been blocked to avoid it being automatically processed at the scheduled date
     */
    BLOCKED
}
