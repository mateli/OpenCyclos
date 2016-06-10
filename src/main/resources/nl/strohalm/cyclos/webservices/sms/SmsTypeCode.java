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
package nl.strohalm.cyclos.webservices.sms;

/**
 * Sms type codes used by built-in sms commands sent by Controller
 * 
 * @author ameyer
 */
public enum SmsTypeCode {

    /** A sms originated by the Controller when processing a request payment */
    REQUEST_PAYMENT,

    /** A sms originated by the Controller when processing a request payment but there is an error to notify */
    REQUEST_PAYMENT_ERROR,

    /** A sms originated by the Controller when processing a payment */
    PAYMENT,

    /** A Sms originated by the Controller when processing a payment but there is an error to notify */
    PAYMENT_ERROR,

    /** A Sms originated by the Controller when processing an account details */
    ACCOUNT_DETAILS,

    /** A Sms originated by the Controller when processing an account details but there is an error to notify */
    ACCOUNT_DETAILS_ERROR,

    /** A Sms originated by the Controller when processing a help command */
    HELP,

    /** A Sms originated by the Controller when processing a help command but there is an error to notify */
    HELP_ERROR,

    /** A Sms originated by the Controller when processing an info text */
    INFO_TEXT,

    /** A Sms originated by the Controller when processing an info text but there is an error to notify */
    INFO_TEXT_ERROR,

    /** A sms originated by the Controller when requesting a command confirmation */
    OPERATION_CONFIRMATION,

    /** A general notification */
    GENERAL;

    public String code() {
        return name();
    }
}
