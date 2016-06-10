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
package nl.strohalm.cyclos.dao.accounts.guarantees;

import java.util.Collection;
import java.util.List;

import nl.strohalm.cyclos.dao.BaseDAO;
import nl.strohalm.cyclos.dao.DeletableDAO;
import nl.strohalm.cyclos.dao.InsertableDAO;
import nl.strohalm.cyclos.dao.UpdatableDAO;
import nl.strohalm.cyclos.entities.accounts.guarantees.Guarantee;
import nl.strohalm.cyclos.entities.accounts.guarantees.GuaranteeQuery;
import nl.strohalm.cyclos.entities.accounts.guarantees.GuaranteeType;
import nl.strohalm.cyclos.entities.accounts.transactions.Transfer;
import nl.strohalm.cyclos.entities.exceptions.DaoException;
import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.members.Member;

/**
 * Interface for guarantee type DAO
 * @author ameyer
 */
public interface GuaranteeDAO extends BaseDAO<Guarantee>, InsertableDAO<Guarantee>, UpdatableDAO<Guarantee>, DeletableDAO<Guarantee> {

    /**
     * @return the list of buyers who can create payment obligations to this seller
     */
    public Collection<MemberGroup> getBuyers(Group seller);

    /**
     * @return the list of issuers who can issue the specified guarantee type
     */
    public Collection<MemberGroup> getIssuers(GuaranteeType guaranteeType);

    /**
     * Lists guarantee models which has this member as buyer, seller or issuer without take into account any other property.
     */
    public Collection<GuaranteeType.Model> getRelatedGuaranteeModels(final Member member) throws DaoException;

    /**
     * Lists of sellers for each buyer of the specified issuer
     */
    public Collection<MemberGroup> getSellers(Group issuer);

    /**
     * @return the guarantee (if any) which loan's transfer is the specified.
     */
    public Guarantee loadFromTransfer(final Transfer loanTransfer);

    /**
     * Returns a List containing guarantee related to search parameters. If no guarantee type can be found, returns an empty List. If any exception is
     * thrown by the underlying implementation, it should be wrapped by a DaoException.
     */
    public List<Guarantee> search(GuaranteeQuery queryParameters) throws DaoException;
}
