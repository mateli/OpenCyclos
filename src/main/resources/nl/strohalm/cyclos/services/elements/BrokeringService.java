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

import nl.strohalm.cyclos.entities.groups.BrokerGroup;
import nl.strohalm.cyclos.entities.members.BrokeringQuery;
import nl.strohalm.cyclos.entities.members.FullTextMemberQuery;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.members.brokerings.Brokering;
import nl.strohalm.cyclos.services.Service;
import nl.strohalm.cyclos.services.elements.exceptions.CircularBrokeringException;
import nl.strohalm.cyclos.services.elements.exceptions.MemberAlreadyInBrokeringsException;
import nl.strohalm.cyclos.utils.validation.ValidationException;

/**
 * Service interface for brokering relationships (broker - registered members by broker)
 * @author luis
 */
public interface BrokeringService extends Service {

    /**
     * Changes the broker of all members returned by the query. For members that already has such broker, nothing is done
     */
    BulkMemberActionResultVO bulkChangeMemberBroker(FullTextMemberQuery query, Member newBroker, boolean suspendCommission, String comments);

    /**
     * Changes de broker of the specified member. Should generate a BrokerRemark to keep track of this change.
     * @param dto The new brokering data
     * @return The result brokering. It may be:
     * <ul>
     * <li>The current open brokering if just suspending commission (the old and new brokers are the same and suspendCommission == true)</li>
     * <li>The new brokering if there's a new broker</li>
     * <li><code>null</code> if the new broker is null</li>
     * </ul>
     * @throws CircularBrokeringException When the member is the broker of the new broker, or recursively
     * @throws MemberAlreadyInBrokeringsException When the member is already on the active broker's member list
     */
    Brokering changeBroker(ChangeBrokerDTO dto) throws MemberAlreadyInBrokeringsException, CircularBrokeringException;

    /**
     * gets the active Brokering from the member and its broker.
     * @param broker a Member being the broker
     * @param member a Member being the brokered member
     * @return the active brokering between them.
     */
    Brokering getBrokering(Member broker, Member member) throws ValidationException;

    /**
     * Lists the available statuses for the given broker group
     */
    Collection<BrokeringQuery.Status> listPossibleStatuses(BrokerGroup brokerGroup);

    /**
     * Search brokerings based on specified parameters
     */
    List<Brokering> search(BrokeringQuery query);

    /**
     * Validates the brokering change DTO
     */
    void validate(ChangeBrokerDTO dto) throws ValidationException;

}
