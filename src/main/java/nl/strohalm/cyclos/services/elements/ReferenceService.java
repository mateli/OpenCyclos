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

import java.util.Collection;
import java.util.List;
import java.util.Map;

import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.transactions.Payment;
import nl.strohalm.cyclos.entities.accounts.transactions.PaymentAwaitingFeedbackDTO;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.members.GeneralReference;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.members.PaymentsAwaitingFeedbackQuery;
import nl.strohalm.cyclos.entities.members.Reference;
import nl.strohalm.cyclos.entities.members.Reference.Level;
import nl.strohalm.cyclos.entities.members.Reference.Nature;
import nl.strohalm.cyclos.entities.members.ReferenceQuery;
import nl.strohalm.cyclos.entities.members.TransactionFeedback;
import nl.strohalm.cyclos.services.Service;
import nl.strohalm.cyclos.utils.Period;
import nl.strohalm.cyclos.utils.validation.ValidationException;

/**
 * Service interface for member references
 * @author luis
 */
public interface ReferenceService extends Service {

    /**
     * Returns true if the logged user can give reference to the specified member.
     * @param member
     * @return
     */
    boolean canGiveGeneralReference(Member member);

    /**
     * Returns true if the logged user can manage the reference.
     * @param ref
     * @return
     */
    boolean canManage(final Reference ref);

    /**
     * Returns whether the logged user can manage the reference of the given member
     * @param member the member to whom the reference is given.
     * @return
     */
    boolean canManageGeneralReference(Member member);

    /**
     * Returns true if the transaction feedback is still in time to be replied.
     * @param transactionFeedback
     * @return
     */
    boolean canReplyFeedbackNow(final TransactionFeedback transactionFeedback);

    /**
     * Count the number of references of a member by level (Reference.Level)
     * @param member the member
     * @param received true to count received references, false to count given references
     */
    Map<Level, Integer> countReferencesByLevel(Reference.Nature nature, Member member, boolean received);

    /**
     * Searches on the history for the references by member and a date.
     * @param member
     * @param received
     */
    Map<Level, Integer> countReferencesHistoryByLevel(Reference.Nature nature, Member member, Period date, boolean received);

    /**
     * Returns the reference natures a member group is related to
     */

    Collection<Nature> getNaturesByGroup(MemberGroup group);

    /**
     * Returns the possible transaction feedback action according to the "status" of the transaction feedback and the logged user.
     */
    TransactionFeedbackAction getPossibleAction(final TransactionFeedback transactionFeedback);

    /**
     * Loads a reference fetching the specified relationships
     * @param id Id of the reference to be loaded
     * @param fetch array of relationships to be fetched
     * @return The loaded reference
     * @throws EntityNotFoundException When no such reference exists
     */
    Reference load(Long id, Relationship... fetch) throws EntityNotFoundException;

    /**
     * Loads a general reference by from / to fetching the specified relationships
     * @param fetch array of relationships to be fetched
     * @return The loaded reference
     * @throws EntityNotFoundException When no such reference exists
     */
    GeneralReference loadGeneral(Member from, Member to, Relationship... fetch) throws EntityNotFoundException;

    /**
     * Loads the transaction feedback for the given payment
     * @throws EntityNotFoundException When no such transaction feedback exists
     */
    TransactionFeedback loadTransactionFeedback(Payment payment, Relationship... fetch) throws EntityNotFoundException;

    /**
     * Removes the specified references
     * @param ids Array of references to be removed
     * @return The number of references removed
     */
    int remove(Long... ids);

    /**
     * Saves the given reference
     * @param reference Reference to be saved
     * @return The reference saved
     */
    GeneralReference save(GeneralReference reference);

    /**
     * Saves a transaction feedback.
     * @param transactionFeedback
     * @return
     */
    TransactionFeedback save(TransactionFeedback transactionFeedback);

    /**
     * Searches for references
     */
    List<? extends Reference> search(ReferenceQuery query);

    /**
     * Searches for payments awaiting buyer / seller feedback
     */
    List<PaymentAwaitingFeedbackDTO> searchPaymentsAwaitingFeedback(PaymentsAwaitingFeedbackQuery query);

    /**
     * Validates the specified reference
     * @param reference Reference to be validated
     * @throws ValidationException if validation fails
     */
    void validate(Reference reference) throws ValidationException;

}
