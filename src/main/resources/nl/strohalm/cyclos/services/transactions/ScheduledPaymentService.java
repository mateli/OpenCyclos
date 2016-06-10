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

import java.util.List;

import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.transactions.ScheduledPayment;
import nl.strohalm.cyclos.entities.accounts.transactions.ScheduledPaymentQuery;
import nl.strohalm.cyclos.entities.accounts.transactions.Transfer;
import nl.strohalm.cyclos.entities.exceptions.UnexpectedEntityException;
import nl.strohalm.cyclos.services.Service;
import nl.strohalm.cyclos.services.transactions.exceptions.MaxAmountPerDayExceededException;
import nl.strohalm.cyclos.services.transactions.exceptions.NotEnoughCreditsException;
import nl.strohalm.cyclos.services.transactions.exceptions.UpperCreditLimitReachedException;
import nl.strohalm.cyclos.webservices.model.ScheduledPaymentVO;

/**
 * Service interface for scheduled payments
 * @author Jefferson Magno
 */
public interface ScheduledPaymentService extends Service {

    /**
     * Blocks a scheduled payment
     */
    ScheduledPayment block(ScheduledPayment scheduledPayment);

    /**
     * Returns whether the logged user can block a scheduled payment
     */
    boolean canBlock(ScheduledPayment scheduledPayment);

    /**
     * Returns whether the logged user can cancel a scheduled payment
     */
    boolean canCancel(ScheduledPayment scheduledPayment);

    /**
     * Cancels a scheduled payment
     */
    ScheduledPayment cancel(ScheduledPayment scheduledPayment);

    /**
     * Checks whether the given transfer can be paid immediately
     */
    boolean canPayNow(Transfer transfer);

    /**
     * Returns whether the logged user can unblock a scheduled payment
     */
    boolean canUnblock(ScheduledPayment scheduledPayment);

    /**
     * Converts an scheduled payments into VO
     */
    ScheduledPaymentVO getScheduledPaymentVO(Long scheduledPaymentId);

    /**
     * Returns details of a scheduled payment
     */
    ScheduledPayment load(Long id, Relationship... fetch);

    /**
     * Process a scheduled payment transfer
     * @return The main transfer. It may contain children transfers (generated, for example, by fees)
     * @throws NotEnoughCreditsException The account does not have enough credits
     * @throws MaxAmountPerDayExceededException The account has exceeded the maximum transaction amount per day for the specified transfer type
     * @throws UnexpectedEntityException Either transfer type or payment receiver are invalid
     * @throws UpperCreditLimitReachedException The payment cannot be performed because it would make the receiving account go beyond the upper credit
     * limit
     * @throws InvalidPaymentReceiverException When the given <code>DoPaymentDTO</code> is not a member
     */
    Transfer processTransfer(Transfer transfer);

    /**
     * Searches for scheduled payments
     */
    List<ScheduledPayment> search(ScheduledPaymentQuery query);

    /**
     * Unblocks a scheduled payment
     */
    ScheduledPayment unblock(ScheduledPayment scheduledPayment);

}
