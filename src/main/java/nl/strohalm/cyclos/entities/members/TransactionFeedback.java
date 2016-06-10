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
package nl.strohalm.cyclos.entities.members;

import java.util.Calendar;

import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.transactions.Payment;
import nl.strohalm.cyclos.entities.accounts.transactions.ScheduledPayment;
import nl.strohalm.cyclos.entities.accounts.transactions.Transfer;

/**
 * A transaction reference represents the option about a buyer to seller or a seller to buyer on a specific transaction
 * 
 * @author luis
 */
public class TransactionFeedback extends Reference {

    public static enum Relationships implements Relationship {
        TRANSFER("transfer"), SCHEDULED_PAYMENT("scheduledPayment");
        private final String name;

        private Relationships(final String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    private static final long serialVersionUID = -2759397836741489295L;
    private Transfer          transfer;
    private ScheduledPayment  scheduledPayment;
    private String            replyComments;
    private Calendar          replyCommentsDate;
    private String            adminComments;
    private Calendar          adminCommentsDate;

    public String getAdminComments() {
        return adminComments;
    }

    public Calendar getAdminCommentsDate() {
        return adminCommentsDate;
    }

    @Override
    public Nature getNature() {
        return Nature.TRANSACTION;
    }

    public Payment getPayment() {
        return transfer == null ? scheduledPayment : transfer;
    }

    public String getReplyComments() {
        return replyComments;
    }

    public Calendar getReplyCommentsDate() {
        return replyCommentsDate;
    }

    public ScheduledPayment getScheduledPayment() {
        return scheduledPayment;
    }

    public Transfer getTransfer() {
        return transfer;
    }

    public void setAdminComments(final String adminComments) {
        this.adminComments = adminComments;
    }

    public void setAdminCommentsDate(final Calendar adminCommentsDate) {
        this.adminCommentsDate = adminCommentsDate;
    }

    public void setPayment(final Payment payment) {
        if (payment instanceof Transfer) {
            transfer = (Transfer) payment;
            scheduledPayment = null;
        } else if (payment instanceof ScheduledPayment) {
            scheduledPayment = (ScheduledPayment) payment;
            transfer = null;
        } else {
            transfer = null;
            scheduledPayment = null;
        }
    }

    public void setReplyComments(final String replyComments) {
        this.replyComments = replyComments;
    }

    public void setReplyCommentsDate(final Calendar replyCommentsDate) {
        this.replyCommentsDate = replyCommentsDate;
    }

    public void setScheduledPayment(final ScheduledPayment scheduledPayment) {
        this.scheduledPayment = scheduledPayment;
    }

    public void setTransfer(final Transfer transfer) {
        this.transfer = transfer;
    }

    @Override
    public String toString() {
        return getId() + " - " + getLevel() + " for " + (transfer == null ? scheduledPayment : transfer);
    }
}
