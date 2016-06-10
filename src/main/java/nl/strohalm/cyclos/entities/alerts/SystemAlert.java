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

import nl.strohalm.cyclos.utils.EnumHelper;
import nl.strohalm.cyclos.utils.StringValuedEnum;

/**
 * An alert sent to system
 * @author luis
 */
public class SystemAlert extends Alert {
    /**
     * Contains the possible system alerts
     * @author luis
     */
    public static enum Alerts implements StringValuedEnum, AlertType {

        /**
         * Alert when the application has been restarted, or started for the first time. Arguments: 0: The instance id
         */
        APPLICATION_RESTARTED,

        /**
         * Alert when the application has being shutdown. Arguments: 0: The instance id
         */
        APPLICATION_SHUTDOWN,

        /**
         * Alert when an account fee has started running. Arguments: 0: The account fee name
         */
        ACCOUNT_FEE_RUNNING,

        /**
         * Alert when an account fee has successfully finished. Arguments: 0: The account fee name
         */
        ACCOUNT_FEE_FINISHED,

        /**
         * Alert when an account fee has finished with errors. Arguments: 0: The account fee name. 1: The number of members with error
         */
        ACCOUNT_FEE_FINISHED_WITH_ERRORS,

        /**
         * Alert someone tries for a given number of tries (alertSettings.amountIncorrectLogin) to login with an invalid username Arguments: 0: The
         * number of missed times 1: The IP that generated the request
         */
        MAX_INCORRECT_LOGIN_ATTEMPTS,

        /**
         * Alert when an administrator login is temporarily blocked by reaching the maximum login attempts. Arguments: 0: The administrator username
         * 1: The number of tries 2: The IP that generated the request
         */
        ADMIN_LOGIN_BLOCKED_BY_TRIES,

        /**
         * Alert when an administrator had it's login temporarily blocked by too many permission denied exceptions. Arguments: 0: The number of
         * permission denied exceptions 1: The IP address that sent the request
         */
        ADMIN_LOGIN_BLOCKED_BY_PERMISSION_DENIEDS,

        /**
         * Alert when an administrator transaction password is blocked by reaching the maximum attempts. Arguments: 0: The administrator username 1:
         * The number of tries 2: The IP that generated the request
         */
        ADMIN_TRANSACTION_PASSWORD_BLOCKED_BY_TRIES,

        /**
         * Alert when there is a new original version of an application page that was customized Arguments: 0: the relative path (from /pages) of the
         * application page
         */
        NEW_VERSION_OF_APPLICATION_PAGE,

        /**
         * Alert when there is a new original version of a static file that was customized Arguments: 0: the name of the static file
         */
        NEW_VERSION_OF_STATIC_FILE,

        /**
         * Alert when there is a new original version of a help file that was customized Arguments: 0: the name of the help file
         */
        NEW_VERSION_OF_HELP_FILE,

        /**
         * Alert when an index rebuild has started. Arguments: 0: The index type 1: The server instance id
         */
        INDEX_REBUILD_START,

        /**
         * Alert when an index rebuild has ended. Arguments: 0: The index type 1: The server instance id
         */
        INDEX_REBUILD_END,

        /**
         * A negative virtual rated balance has occurred on a system account. Arguments: 0: the account name.
         */
        NEGATIVE_VIRTUAL_RATED_BALANCE,

        /**
         * Alert when a null i-rate on a system account was encountered. Arguments: 0: system account name.
         */
        NULL_IRATE,

        /**
         * When a rate initialization is finished, this alert is fired. It means the system can be put online again. Arguments: 0: the currency name;
         * 1: a string indicating what type of rates (A, D, I).
         */
        RATE_INITIALIZATION_FINISHED,

        /**
         * A Rate initialization has started. The system has been set offline, and cannot be set online until it is finished. Arguments: 0: the
         * currency name; 1: a string indicating what type of rates (A, D, I).
         */
        RATE_INITIALIZATION_STARTED,

        /**
         * A Rate initialization has failed due to some encountered exception. Arguments: 0: the currency name; 1: the id of the last correctly
         * processed transaction.
         */
        RATE_INITIALIZATION_FAILED,

        ;

        private final String name;

        private Alerts() {
            name = "alert.system." + EnumHelper.capitalizeName(this);
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

    private static final long serialVersionUID = -4680889167594248176L;

}
