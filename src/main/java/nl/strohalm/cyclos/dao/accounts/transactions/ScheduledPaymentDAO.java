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
package nl.strohalm.cyclos.dao.accounts.transactions;

import java.util.Collection;
import java.util.List;

import nl.strohalm.cyclos.dao.BaseDAO;
import nl.strohalm.cyclos.dao.InsertableDAO;
import nl.strohalm.cyclos.dao.UpdatableDAO;
import nl.strohalm.cyclos.entities.accounts.MemberAccountType;
import nl.strohalm.cyclos.entities.accounts.transactions.ScheduledPayment;
import nl.strohalm.cyclos.entities.accounts.transactions.ScheduledPaymentQuery;
import nl.strohalm.cyclos.entities.exceptions.DaoException;
import nl.strohalm.cyclos.entities.members.Member;

/**
 * Data access object for scheduled payments
 * @author luis
 */
public interface ScheduledPaymentDAO extends BaseDAO<ScheduledPayment>, InsertableDAO<ScheduledPayment>, UpdatableDAO<ScheduledPayment> {

    /**
     * @return the open scheduled payments the member has related to accounts different than the specified.
     */
    List<ScheduledPayment> getUnrelatedPendingPayments(final Member member, final Collection<MemberAccountType> accountTypes);

    /**
     * Searches for scheduled payments
     */
    List<ScheduledPayment> search(final ScheduledPaymentQuery query) throws DaoException;
}
