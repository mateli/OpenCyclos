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

import java.util.Calendar;

import nl.strohalm.cyclos.entities.accounts.fees.transaction.BrokerCommission;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.members.brokerings.BrokerCommissionContract;
import nl.strohalm.cyclos.entities.members.brokerings.Brokering;
import nl.strohalm.cyclos.entities.members.brokerings.BrokeringCommissionStatus;
import nl.strohalm.cyclos.entities.members.brokerings.DefaultBrokerCommission;

/**
 * Local interface. It must be used only from other services.
 */
public interface CommissionServiceLocal extends CommissionService {

    /**
     * Activates broker commission contracts that were accepted and begin today
     */
    void activateAcceptedBrokerCommissionContracts(final Calendar time);

    /**
     * This method closes the brokering commission status setting an end date (= now) when the max number of transactions that generates this
     * commission is reached
     */
    BrokeringCommissionStatus closeBrokeringCommissionStatus(BrokeringCommissionStatus brokeringCommissionStatus);

    /**
     * Creates the brokering commission status objects for each broker (and his/her brokereds) of the broker groups selected in the broker commission
     */
    void createBrokeringCommissionStatus(final BrokerCommission brokerCommission);

    /**
     * Creates the brokering commission status object
     */
    BrokeringCommissionStatus createBrokeringCommissionStatus(final Brokering brokering, final BrokerCommission brokerCommission);

    /**
     * Creates a default broker commission for each broker of the broker groups selected in the broker commission
     */
    void createDefaultBrokerCommissions(final BrokerCommission brokerCommission);

    /**
     * Expires broker commission contracts that were not accepted until it´s beginning and close contracts that expired at the end of the previous day
     */
    void expireBrokerCommissionContracts(final Calendar time);

    /**
     * Expires brokering commission status
     */
    void expireBrokeringCommissionStatus(final Calendar date);

    /**
     * Returns the active broker commission contract or null if it doesn't exist
     */
    BrokerCommissionContract getActiveBrokerCommissionContract(Brokering brokering, BrokerCommission brokerCommission);

    /**
     * Shows the present status of the brokering commission
     */
    BrokeringCommissionStatus getBrokeringCommissionStatus(final Brokering brokering, final BrokerCommission brokerCommission);

    /**
     * Returns the default broker commission
     */
    DefaultBrokerCommission getDefaultBrokerCommission(Member broker, BrokerCommission brokerCommission);

    /**
     * Returns the brokering commission status or null if it doesn't exist
     */
    BrokeringCommissionStatus getOrUpdateBrokeringCommissionStatus(final Brokering brokering, final BrokerCommission brokerCommission);

    /**
     * 
     * @return
     */
    boolean hasAllPermissions();

    /**
     * Create default broker commissions or brokering commission status applicable to the new group and suspend commissions that are not applicable to
     * new group
     */
    void updateBrokerCommissions(Member broker, MemberGroup oldGroup, MemberGroup newGroup);

    /**
     * Create brokering commission status for groups added to the commission and suspend commissions for groups removed from the commission
     */
    void updateBrokeringCommissionStatus(final BrokerCommission brokerCommission, final BrokerCommission savedBrokerCommission);

    /**
     * Saves the brokering commission status
     */
    BrokeringCommissionStatus updateBrokeringCommissionStatus(BrokeringCommissionStatus brokeringCommissionStatus);

    /**
     * Create default broker commissions for groups added to the commission and suspend commissions for groups removed from the commission
     */
    void updateDefaultBrokerCommissions(final BrokerCommission brokerCommission, final BrokerCommission savedBrokerCommission);

}
