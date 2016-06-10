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

import java.util.Collection;

import nl.strohalm.cyclos.entities.accounts.MemberAccountType;
import nl.strohalm.cyclos.entities.accounts.transactions.ScheduledPayment;
import nl.strohalm.cyclos.entities.members.Member;

/**
 * Local interface. It must be used only from other services.
 */
public interface ScheduledPaymentServiceLocal extends ScheduledPaymentService {

    /**
     * Cancel all scheduled payments related to the specified member if he/she has pending scheduled payments<br>
     * related to accounts different than the specified accounts. For each scheduled payment cancelled it send a member notification to the payer and
     * to the receiver if the payment is marked to be showed to the receiver or the payment was generated from an invoice.
     */
    void cancelScheduledPaymentsAndNotify(final Member member, final Collection<MemberAccountType> accountTypes);

    /**
     * Updates the scheduled payment status based on its first open transfer status
     */
    ScheduledPayment updateScheduledPaymentStatus(final ScheduledPayment scheduledPayment);

}
