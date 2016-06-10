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
package nl.strohalm.cyclos.services.transactions;

/**
 * Possible contexts for a transaction to happen
 * @author luis
 */
public enum TransactionContext {
    /**
     * A normal payment bewteen different owners
     */
    PAYMENT,

    /**
     * A payment from and to the same owner
     */
    SELF_PAYMENT,

    /**
     * A loan given from system to a member
     */
    LOAN,

    /**
     * An automatically generated payment (i.e: a fee payment). The transfer type does not need to be enabled
     */
    AUTOMATIC,

    /**
     * Any type of payment, used to filter any transaction types
     */
    ANY,

    /**
     * An automatically generated loan (i.e: a loan from guarantee). The transfer type needs to be a "loan type" and does not need to be enabled
     */
    AUTOMATIC_LOAN;
}
