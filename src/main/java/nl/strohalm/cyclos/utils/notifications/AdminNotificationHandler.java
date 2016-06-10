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
package nl.strohalm.cyclos.utils.notifications;

import nl.strohalm.cyclos.entities.accounts.guarantees.Guarantee;
import nl.strohalm.cyclos.entities.accounts.transactions.Invoice;
import nl.strohalm.cyclos.entities.accounts.transactions.Transfer;
import nl.strohalm.cyclos.entities.alerts.Alert;
import nl.strohalm.cyclos.entities.alerts.ErrorLogEntry;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.members.messages.Message;

/**
 * Used to send notifications to administrators
 * 
 * @author luis
 */
public interface AdminNotificationHandler {

    /**
     * Notifies both system and member alerts
     */
    void notifyAlert(final Alert alert);

    /**
     * Notifies application errors
     */
    void notifyApplicationErrors(final ErrorLogEntry errorLog);

    /**
     * Notifies a system message
     */
    void notifyMessage(Message message);

    /**
     * Notifies new pending payments
     */
    void notifyNewPendingPayment(final Transfer transfer);

    /**
     * Notifies new public registrations
     */
    void notifyNewPublicRegistration(final Member member);

    /**
     * Notifies system payments
     */
    void notifyPayment(Transfer transfer);

    /**
     * Notifies about new pending guarantees
     */
    void notifyPendingGuarantee(final Guarantee guarantee);

    /**
     * Notifies system invoices
     */
    void notifySystemInvoice(final Invoice invoice);

}
