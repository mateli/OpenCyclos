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
package nl.strohalm.cyclos.entities.alerts;

import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.utils.EnumHelper;
import nl.strohalm.cyclos.utils.StringValuedEnum;

/**
 * An alert sent to a member
 * @author luis
 */
public class MemberAlert extends Alert {
    /**
     * Contains the possible member alerts
     * @author luis
     */
    public static enum Alerts implements StringValuedEnum, AlertType {
        /**
         * Alert when a member exceeded the login attempts and had it's login temporarily blocked. Arguments: 0: The number of tries 1: The IP address
         * that sent the request
         */
        LOGIN_BLOCKED_BY_TRIES,

        /**
         * Alert when a member had it's login temporarily blocked by too many permission denied exceptions. Arguments: 0: The number of permission
         * denied exceptions 1: The IP address that sent the request
         */
        LOGIN_BLOCKED_BY_PERMISSION_DENIEDS,

        /**
         * Alert when a member exceeded the pin attempts and had it's pin temporarily blocked. Arguments: 0: The number of tries, 1: the channel, 2:
         * username of the related member
         */
        PIN_BLOCKED_BY_TRIES,

        /**
         * Alert when a member exceeded the transaction password attempts and got his transaction password blocked. Arguments: 0: The number of tries
         * 1: The IP address that sent the request
         */
        TRANSACTION_PASSWORD_BLOCKED_BY_TRIES,

        /**
         * Alert when a member hasn't accepted an invoice and it has expired. Arguments: 0: The invoice amount 1: The invoice date/time
         */
        INVOICE_IDLE_TIME_EXCEEDED,

        /**
         * Alert when a member has denied a number of invoices. Arguments: 0: The invoice count
         */
        DENIED_INVOICES,

        /**
         * Alert when a member has exceeded the max. given very bad references. Arguments: 0: The reference count
         */
        GIVEN_VERY_BAD_REFS,

        /**
         * Alert when a member has exceeded the max. received very bad references. Arguments: 0: The reference count
         */
        RECEIVED_VERY_BAD_REFS,

        /**
         * Alert when a loan is still open and has expired. No arguments
         */
        EXPIRED_LOAN,

        /**
         * Alert when an scheduled payment has failed. Arguments: 0: The payment amount 1: The transfer type name
         */
        SCHEDULED_PAYMENT_FAILED,

        /**
         * Alert to indicate when a blocked POS has been used.Arguments: 0: The posId, 1: the remote IP address
         */
        BLOCKED_POS_USED,

        /**
         * Alert when a member exceeded the card security code attempts and it was blocked. Arguments: 0: The number of tries. 1: The card number
         */
        CARD_SECURITY_CODE_BLOCKED_BY_TRIES,

        /**
         * A negative virtual rated balance has occurred on a member account. Arguments: 0: the account type name.
         */
        NEGATIVE_VIRTUAL_RATED_BALANCE,

        /**
         * Alert when a null rate on a member account was encountered. Arguments: 0: account type name.
         */
        NULL_IRATE,

        /**
         * Alert when the account activation failed.
         */
        ACCOUNT_ACTIVATION_FAILED,

        /**
         * Alert when the initial credit grant has failed. Arguments: 0: account type name.
         */
        INITIAL_CREDIT_FAILED;

        private final String name;

        private Alerts() {
            name = "alert.member." + EnumHelper.capitalizeName(this);
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public String getValue() {
            return name;
        }
    }

    public static enum Relationships implements Relationship {
        MEMBER("member");
        private final String name;

        private Relationships(final String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }
    }

    private static final long serialVersionUID = -3470454153060498193L;

    private Member            member;

    public Member getMember() {
        return member;
    }

    public void setMember(final Member member) {
        this.member = member;
    }
}
