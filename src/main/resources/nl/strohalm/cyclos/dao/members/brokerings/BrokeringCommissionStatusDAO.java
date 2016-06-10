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

import java.util.Calendar;
import java.util.List;

import nl.strohalm.cyclos.dao.BaseDAO;
import nl.strohalm.cyclos.dao.DeletableDAO;
import nl.strohalm.cyclos.dao.InsertableDAO;
import nl.strohalm.cyclos.dao.UpdatableDAO;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.fees.transaction.BrokerCommission;
import nl.strohalm.cyclos.entities.members.brokerings.Brokering;
import nl.strohalm.cyclos.entities.members.brokerings.BrokeringCommissionStatus;
import nl.strohalm.cyclos.entities.members.brokerings.BrokeringCommissionStatusQuery;

/**
 * DAO interface for the status of a brokering commission
 * @author Jefferson Magno
 */
public interface BrokeringCommissionStatusDAO extends BaseDAO<BrokeringCommissionStatus>, InsertableDAO<BrokeringCommissionStatus>, UpdatableDAO<BrokeringCommissionStatus>, DeletableDAO<BrokeringCommissionStatus> {

    /**
     * Expires brokering commission status
     */
    void expireBrokeringCommissionStatus(final Calendar date);

    /**
     * Returns the brokering commission status or null if it does not exist
     */
    BrokeringCommissionStatus load(final Brokering brokering, final BrokerCommission brokerCommission, final Relationship... fetch);

    /**
     * Search brokering commission status, given the query parameters
     */
    List<BrokeringCommissionStatus> search(final BrokeringCommissionStatusQuery query);

}
