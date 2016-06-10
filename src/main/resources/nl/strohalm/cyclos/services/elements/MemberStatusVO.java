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
package nl.strohalm.cyclos.services.elements;

import nl.strohalm.cyclos.utils.DataObject;

/**
 * Contains information about a member's status
 * @author luis
 */
public class MemberStatusVO extends DataObject {
    private static final long serialVersionUID = -3478432929551388792L;
    private int               unreadMessages;
    private int               openInvoices;
    private int               openLoans;
    private int               paymentsToAuthorize;
    private int               newPayments;
    private int               newReferences;
    private int               paymentsAwaitingFeedback;
    private boolean           hasPendingCommissionContracts;

    public int getNewPayments() {
        return newPayments;
    }

    public int getNewReferences() {
        return newReferences;
    }

    public int getOpenInvoices() {
        return openInvoices;
    }

    public int getOpenLoans() {
        return openLoans;
    }

    public int getPaymentsAwaitingFeedback() {
        return paymentsAwaitingFeedback;
    }

    public int getPaymentsToAuthorize() {
        return paymentsToAuthorize;
    }

    public int getUnreadMessages() {
        return unreadMessages;
    }

    public boolean isHasData() {
        return unreadMessages + openInvoices + openLoans + paymentsToAuthorize + newPayments + newReferences + paymentsAwaitingFeedback > 0;
    }

    public boolean isHasPendingCommissionContracts() {
        return hasPendingCommissionContracts;
    }

    public void setHasPendingCommissionContracts(final boolean hasPendingCommissionContracts) {
        this.hasPendingCommissionContracts = hasPendingCommissionContracts;
    }

    public void setNewPayments(final int newPayments) {
        this.newPayments = newPayments;
    }

    public void setNewReferences(final int newReferences) {
        this.newReferences = newReferences;
    }

    public void setOpenInvoices(final int openInvoices) {
        this.openInvoices = openInvoices;
    }

    public void setOpenLoans(final int openLoans) {
        this.openLoans = openLoans;
    }

    public void setPaymentsAwaitingFeedback(final int paymentsAwaitingFeedback) {
        this.paymentsAwaitingFeedback = paymentsAwaitingFeedback;
    }

    public void setPaymentsToAuthorize(final int paymentsToAuthorize) {
        this.paymentsToAuthorize = paymentsToAuthorize;
    }

    public void setUnreadMessages(final int unreadMessages) {
        this.unreadMessages = unreadMessages;
    }

}
