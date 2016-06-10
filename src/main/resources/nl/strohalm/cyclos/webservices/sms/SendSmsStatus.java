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
 * Possible status for an SMS sending request
 * 
 * @author luis
 */
public enum SendSmsStatus {

    /** The sms was charged and sent successfully */
    SUCCESS,

    /** The sms was not sent because the channel is disabled for target member */
    CHANNEL_DISABLED_FOR_TARGET,

    /** The sms was not send because the channel is disabled for charged member */
    CHANNEL_DISABLED_FOR_CHARGED,

    /** The sms was not sent because the charge couldn't be done, e.g. the member hasn't enough funds or doesn't allow charging sms */
    CHARGE_COULD_NOT_BE_DONE,

    /** The target member was not found in Cyclos according to the principal / principal type */
    TARGET_NOT_FOUND,

    /** The charged member was not found in Cyclos according to the principal / principal type */
    CHARGED_NOT_FOUND,

    /** The SMS controller couldn't send the message */
    SEND_ERROR,

    /**
     * An internal server error has occurred when trying to send the sms. <br>
     * e.g. The SMS charging is not well configured, or the server is configured with <br>
     * an additional sms package of size 0 and the member doesn't have available sms.
     */
    INTERNAL_ERROR,

    /** The info text was not found in Cyclos according to the alias */
    INFO_TEXT_NOT_FOUND,

    /** The sms type code specified is not defined in Cyclos. */
    INVALID_SMS_TYPE_CODE;
}
