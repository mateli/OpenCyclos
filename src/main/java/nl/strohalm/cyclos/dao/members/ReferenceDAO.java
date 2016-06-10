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
package nl.strohalm.cyclos.dao.members;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import nl.strohalm.cyclos.dao.BaseDAO;
import nl.strohalm.cyclos.dao.DeletableDAO;
import nl.strohalm.cyclos.dao.InsertableDAO;
import nl.strohalm.cyclos.dao.UpdatableDAO;
import nl.strohalm.cyclos.entities.accounts.transactions.PaymentAwaitingFeedbackDTO;
import nl.strohalm.cyclos.entities.exceptions.DaoException;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.members.PaymentsAwaitingFeedbackQuery;
import nl.strohalm.cyclos.entities.members.Reference;
import nl.strohalm.cyclos.entities.members.Reference.Level;
import nl.strohalm.cyclos.entities.members.ReferenceQuery;
import nl.strohalm.cyclos.utils.Period;

/**
 * Data access object interface for contact
 * @author rafael
 */
public interface ReferenceDAO extends BaseDAO<Reference>, InsertableDAO<Reference>, UpdatableDAO<Reference>, DeletableDAO<Reference> {

    /**
     * Count the number of given references by level (Reference.Level)
     * @return number of given references by level
     */
    public Map<Level, Integer> countGivenReferencesByLevel(Reference.Nature nature, Collection<MemberGroup> memberGroups);

    /**
     * Count the number of references of a member by level (Reference.Level)
     * @param member the member
     * @param received true to count received references, false to count given references
     */
    public Map<Level, Integer> countReferencesByLevel(Reference.Nature nature, Period period, Member member, boolean received);

    /**
     * Searches for references, ordering results by the reference date. If no Reference can be found, returns an empty List. If any exception is
     * thrown by the underlying implementation, wrapped it in a DaoException.
     * 
     * @throws DaoException
     */
    public List<? extends Reference> search(ReferenceQuery params) throws DaoException;

    /**
     * Searches for payments that should have but still have no feedback
     */
    public List<PaymentAwaitingFeedbackDTO> searchPaymentsAwaitingFeedback(PaymentsAwaitingFeedbackQuery query);

}
