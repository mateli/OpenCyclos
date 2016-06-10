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
package nl.strohalm.cyclos.entities.accounts;

/**
 * Determines which accounts are locked in payments
 * 
 * @author luis
 */
public enum LockedAccountsOnPayments {
    /**
     * No accounts are locked. Uses a single DB transaction on payments. Concurrent payments from / to the same account might allow an account to go
     * beyond it's lower / upper limit. However, if used in conjunction with not letting the same user have multiple logins largely reduces this
     * possibility. Also, by not having a separated DB transaction, is the only method supported in Cyclos standalone, which uses HSQLDB (which only
     * allows a single DB connection)
     */
    NONE,

    /**
     * Only the origin account of the main transfer is locked. Will never allow it to go beyond the lower limit, but might allow the receiver account
     * to go beyond the upper limit. Uses a separated DB transaction for the payment.
     */
    ORIGIN,

    /**
     * Locks all involved accounts, even on fees. Absolutely guarantees that no account will ever go beyond any of it's limits (lower / upper).
     * However, might cause a big performance impact in very active systems. Uses a separated DB transaction for the payment.
     */
    ALL
}
