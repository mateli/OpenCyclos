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
package nl.strohalm.cyclos.dao.members.brokerings;

import java.util.List;

import nl.strohalm.cyclos.dao.BaseDAO;
import nl.strohalm.cyclos.dao.DeletableDAO;
import nl.strohalm.cyclos.dao.InsertableDAO;
import nl.strohalm.cyclos.dao.UpdatableDAO;
import nl.strohalm.cyclos.entities.members.brokerings.BrokerCommissionContract;
import nl.strohalm.cyclos.entities.members.brokerings.BrokerCommissionContractQuery;

/**
 * DAO interface for the broker commission contract
 * @author Jefferson Magno
 */
public interface BrokerCommissionContractDAO extends BaseDAO<BrokerCommissionContract>, InsertableDAO<BrokerCommissionContract>, UpdatableDAO<BrokerCommissionContract>, DeletableDAO<BrokerCommissionContract> {

    /**
     * Check if this contract is in conflict with an existing contract
     */
    boolean isConflictingContract(BrokerCommissionContract brokerCommissionContract);

    /**
     * Search for broker commission contracts
     */
    List<BrokerCommissionContract> search(BrokerCommissionContractQuery query);

}
